/*
 * Copyright 2012 Decebal Suiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.noahframe.nfcore.api.plugin.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Decebal Suiu
 */
public class FileUtils {

	public static List<String> readLines(File file, boolean ignoreComments) throws IOException {
		if (!file.exists() || !file.isFile()) {
			return new ArrayList<>();
		}

		List<String> lines = new ArrayList<>();

		BufferedReader reader = null;
		try {
	        reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				if (ignoreComments && !line.startsWith("#") && !lines.contains(line)) {
					lines.add(line);
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return lines;
	}

    public static void writeLines(Collection<String> lines, File file) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

	/**
	 * Delete a file or recursively delete a folder.
	 *
	 * @param fileOrFolder
	 * @return true, if successful
	 */
	public static boolean delete(File fileOrFolder) {
		boolean success = false;
		if (fileOrFolder.isDirectory()) {
			File [] files = fileOrFolder.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						success |= delete(file);
					} else {
						success |= file.delete();
					}
				}
			}
		}
		success |= fileOrFolder.delete();

		return success;
	}

}
