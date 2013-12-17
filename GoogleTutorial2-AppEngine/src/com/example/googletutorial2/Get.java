package com.example.googletutorial2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.BlobstoreService;


public class Get extends HttpServlet {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -6143086835037960148L;

	@Override
	   public void doGet(HttpServletRequest request, HttpServletResponse response)
	               throws IOException, ServletException {
	      
		// Set the response message's MIME type
	      response.setContentType("text/html;charset=UTF-8");
	      // Allocate a output writer to write the response message into the network socket
	      PrintWriter out = response.getWriter();
	 
	      BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	      
	      // Write the response message, in an HTML page
	      try {
	         out.println(blobstoreService.createUploadUrl("/upload"));
	      } finally {
	         out.close();  // Always close the output writer
	      }
	   }
	
	
	
	
}




