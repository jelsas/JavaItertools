/*
 Copyright 2010 Jonathan L. Elsas

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
import static itertools.Itertools.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import itertools.Itertools;
import itertools.functions.Files;
import itertools.iterator.EnumeratingIterator;
import itertools.iterator.FileLineIterator;

/**
 * JavaItertools example showing a external-memory sorting routine. Shows the
 * direct usage of an iterator decorator {@link EnumeratingIterator} (1) as well
 * as file merging via {@link Itertools#merge(Iterable)} (2).
 * 
 * Note: this is not intended to be a high-performance or flexible sorting
 * routine:
 * 
 * - it arbitrarily flushes the interal buffer every BUFFER_LINES lines.
 * Ideally, we'd like to do something that pays more attention to the actual
 * memory usage of the JVM.
 * 
 * - it doesn't adequately clean up if an IO error occurs.
 * 
 * - it might run out of memory if many, many temporary files must be created,
 * or lines in the input are extremely long.
 * 
 * - it only supports ascending lexographic sort.
 * 
 * @author jelsas
 * 
 */
public class ExternalMemorySort {

  /**
   * An arbitrary limit just to show the functionality.
   */
  private static final int BUFFER_LINES = 1000;

  /**
   * Sorts the provided file lexographically with a external-memory mergesort.
   */
  public void run(String input, String output) throws IOException {

    // Buffer to hold a chunk of lines from our file
    String[] buffer = new String[BUFFER_LINES];

    // All the temporary files we create
    List<String> tempFiles = new ArrayList<String>();

    // (1) An iterator that provides a count of items returned by next(),
    // decorating an iterator over lines in the file
    EnumeratingIterator<String> lines = new EnumeratingIterator<String>(
        new FileLineIterator(input));

    // The index into the buffer array
    int bufferIndex = 0;

    System.out.println("Reading: " + input);

    // Loop through the entire input file
    while (lines.hasNext()) {

      // Get the next line & calculate were in the buffer we're storing it
      String nextLine = lines.next();
      bufferIndex = lines.currentCount() % BUFFER_LINES;
      buffer[bufferIndex] = nextLine;

      // If we've just written to the last position in the buffer, sort and dump
      // to a temp file
      if (bufferIndex == BUFFER_LINES - 1) {
        tempFiles.add(sortAndDumpToTmpFile(buffer, buffer.length));
      }
    }

    // Sort and dump the remaining data
    if (bufferIndex < BUFFER_LINES - 1) {
      tempFiles.add(sortAndDumpToTmpFile(buffer, bufferIndex + 1));
    }

    System.out.println("Created " + tempFiles.size() + " temporary files.");
    System.out.println("Merging into " + output);

    BufferedWriter out = new BufferedWriter(new FileWriter(output));

    // Merge all the sorted temporary files.
    // (2) This first maps the file names to iterators over their lines, then
    // wraps those iterators in a Merging iterator
    for (String line : merge(map(tempFiles, Files.OPEN))) {
      out.write(line);
      out.newLine();
    }
    out.close();

    System.out.println("Done.");
  }

  /**
   * Writes the data to a temp file & returns the filename.
   */
  private String sortAndDumpToTmpFile(String[] data, int endIndex)
      throws IOException {
    Arrays.sort(data, 0, endIndex + 1);
    File f = File.createTempFile(this.getClass().getName(), ".tmp");
    f.deleteOnExit();
    BufferedWriter fout = new BufferedWriter(new FileWriter(f));
    for (int i = 0; i < endIndex && i < data.length; ++i) {
      fout.write(data[i]);
      fout.newLine();
    }
    fout.close();
    return f.getAbsolutePath();
  }

  public static void main(String[] args) throws Exception {
    ExternalMemorySort d = new ExternalMemorySort();
    d.run(args[0], args[1]);
  }

}
