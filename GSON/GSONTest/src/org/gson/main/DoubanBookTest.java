package org.gson.main;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.gson.bean.Book;

import com.google.gson.Gson;

public class DoubanBookTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String in = readFile(
				"d:\\Android\\workspace\\GSONTest\\json\\book.json",
				StandardCharsets.UTF_8);
		// Type typeOfBook = new TypeToken<Book>(){}.getType();
		// Gson gson = new GsonBuilder().registerTypeAdapter(typeOfBook, new
		// BookAdapter()).create();
		Gson gson = new Gson();
		Book book = gson.fromJson(in, Book.class);
		System.out.println(book.title);

	}

	public static String readFile(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
}
