package com.test.file.processing.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileProcessingService {

	private String locationOfFiles = "c:\\FilesFromApp";
	public boolean saveFile(MultipartFile file, String fileName, String fileType) throws IOException {
	
		FileOutputStream fileOutputStream = new FileOutputStream(locationOfFiles + File.separator + fileName + ".txt");		
		fileOutputStream.write(file.getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
		return true;
	}
	
	public void saveFileMetadata(String fileName, String fileType, long fileSize, Date savedTime) throws IOException {
		FileWriter fileWriter = new FileWriter(locationOfFiles + File.separator + "metadata_" +  fileName + ".txt");
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		StringBuilder builder = new StringBuilder();
		builder.append(fileName);
		builder.append("|");
		builder.append(fileType);
		builder.append("|");
		builder.append(fileSize);
		builder.append("|");
		builder.append(savedTime);
		bufferedWriter.write(builder.toString());
		bufferedWriter.flush();
		bufferedWriter.close();
	}
	
	public String getFileMetadata(String fileName) throws IOException {
		
		File file = new File(locationOfFiles + File.separator + "metadata_" + fileName + ".txt");
		if(file.exists()) {
			
			InputStream inputStream = new FileInputStream(locationOfFiles + File.separator + "metadata_" + fileName + ".txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			StringBuffer stringBuffer = new StringBuffer();
			String line = bufferedReader.readLine();
			while(line != null) {
				stringBuffer.append(line);
				line = bufferedReader.readLine();
			}
			
			bufferedReader.close();
			inputStream.close();
			return stringBuffer.toString();
		} else {
			return "Invalid file name";
		}
	}
	
	public String searchFile(String fileName) throws IOException {
		File file = new File(locationOfFiles + File.separator + fileName + ".txt");
		if(file.exists()) {
			return "File Exists.";
		} else {
			return "File Do not exist";
		}
	}
}
