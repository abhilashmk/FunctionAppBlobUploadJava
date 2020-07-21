package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;


import java.time.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.azure.functions.ExecutionContext;

import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Optional;


/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function will be invoked periodically according to the specified schedule.
     */
    @FunctionName("TimeTriggerJava")
    public void run(
        @TimerTrigger(name = "timerInfo", schedule = "0 */5 * * * *") String timerInfo,
        final ExecutionContext context
    ) {
        System.out.println("jiiiiiiiiiiiiiiiiiii");
        context.getLogger().info("calling uplaod");
        uploadBlob(context);
        context.getLogger().info("Java Timer trigger function executed at: " + LocalDateTime.now());
    }

     public void uploadBlob(ExecutionContext context) {
    	context.getLogger().info("Azure Blob storage v12 - Java quickstart sample\n");
    	try {
    	String connectStr = "";
    	// Create a BlobServiceClient object which will be used to create a container client
    	BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
    	context.getLogger().info("Step1");
    	//blobServiceClient
    	//Create a unique name for the container
    	//String containerName = "quickstartblobs" + java.util.UUID.randomUUID();

    	// Create the container and return a container client object
    	BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("aad-dr-poc");
    	context.getLogger().info("Step2");
    	String localPath = "D://home/site/";
    	String fileName = "quickstart" + java.util.UUID.randomUUID() + ".txt";
    	context.getLogger().info(fileName);
    	
    	context.getLogger().info("Step3");
    	File localFile = new File(localPath + fileName);
    	context.getLogger().info("Step4");
    	// Write text to the file
    	FileWriter writer;
	
			writer = new FileWriter(localPath + fileName, true);
			writer.write("Hello, World!");
			writer.close();
			TimeUnit.SECONDS.sleep(5);

	    	context.getLogger().info("Step5***#############*");
	    	context.getLogger().info(System.getenv("test"));
		// Get a reference to a blob
		BlobClient blobClient = containerClient.getBlobClient(fileName);
		context.getLogger().info("Step6");
		context.getLogger().info("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());
		context.getLogger().info("Step7");
		// Upload the blob
		context.getLogger().info(localPath+fileName);
		blobClient.uploadFromFile(localPath + fileName);
		
		context.getLogger().info("file uploaded successfully");
    	} catch (IOException e) {
    		StringWriter sw = new StringWriter();
	   	    e.printStackTrace(new PrintWriter(sw));
	   	    context.getLogger().info("some exception occur in io block*******************");
    		context.getLogger().info(sw.toString());
		}catch (Exception e) {
			context.getLogger().info("some exception occue");
			e.printStackTrace();
		}catch(Throwable ex) {
			context.getLogger().info(ex.toString());
		}

    }
}
