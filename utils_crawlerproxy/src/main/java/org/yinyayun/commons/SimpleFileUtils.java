package org.yinyayun.commons;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class SimpleFileUtils {
	public static List<String> readLines(String path) {
		return readLines(path, "UTF-8");
	}

	public static List<String> readLines(String path, String encode) {
		List<String> lines = new ArrayList<String>();
		try {
			File f = new File(path);
			if (f.exists()) {
				lines = FileUtils.readLines(new File(path), encode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	// 获取文件的写对象
	public static BufferedWriter getWriter(
			BufferedOutputStream bufferedOutputStream, String encode) {
		BufferedWriter bufferedWriter = null;
		try {
			OutputStreamWriter streamWriter = new OutputStreamWriter(
					bufferedOutputStream, encode);
			bufferedWriter = new BufferedWriter(streamWriter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufferedWriter;
	}

	// 需要判定该文件是否已经创建缓冲区
	public static BufferedOutputStream getbuOutputStream(String path,
			int bufferSize) {
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(new File(path)), bufferSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufferedOutputStream;
	}

	public static void close(Object obj) {
		try {
			if (obj instanceof BufferedWriter) {
				((BufferedWriter) obj).close();
			} else if (obj instanceof BufferedOutputStream) {
				((BufferedOutputStream) obj).close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
