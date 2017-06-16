package com.test.file.processing.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.jboss.logging.Logger;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.file.processing.service.FileProcessingService;

@RestController
@Api(name="File Processing", description="Processing file related endpoints")
public class FileProcessingController {

	@Autowired
	FileProcessingService fileProcessingService;
	
	static Logger logger = Logger.getLogger(FileProcessingController.class);
	
	@ApiMethod(description="to upload a file with meta data")
	@RequestMapping(value="/uploadFileWithMetadata", method=RequestMethod.POST)
	public ResponseEntity<String> uploadFileWithMetadata(@RequestBody MultipartFile file, @PathVariable("fileName") String fileName, @PathVariable("fileType") String fileType) {
		
		if(file.isEmpty()) {
			//If file is empty notify user.
			return ResponseEntity.status(HttpStatus.OK).body("File is empty. Nothing saved.");
		}
		else {
			//If file is not empty then save and return response to user.
			try {
			fileProcessingService.saveFile(file, fileName, fileType);
			fileProcessingService.saveFileMetadata(fileName, fileType, file.getSize(), new Date());
			logger.info("File and metadata saved successfully.");
			} catch(IOException e) {
				logger.error("Error occured while saving file" , e);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body("File Saved Successfully.");
		
	}
	
	@ApiMethod(description="to get file metadata")
	@RequestMapping(value="/getFileMetadata/fileName/{fileName}", method=RequestMethod.GET)
	public ResponseEntity<String> getFileMetadata(@PathVariable("fileName") String fileName) throws IOException {
		return ResponseEntity.status(HttpStatus.OK).body(fileProcessingService.getFileMetadata(fileName));
	}
	
	@ApiMethod(description="to get file")
	@RequestMapping(value="/searchFile/fileName/{fileName}", method=RequestMethod.GET)
	public ResponseEntity<String> searchFile(@PathVariable("fileName") String fileName) throws IOException {
		return ResponseEntity.status(HttpStatus.OK).body(fileProcessingService.searchFile(fileName));
	}
	
	
}
