/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * _____________________________________________
 *****************************************************************************/
package org.adempiere.pipo;


import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.GZip;
import org.apache.tools.ant.taskdefs.Tar;
import org.apache.tools.ant.taskdefs.Zip;
/**
 * Compress package
 * 
 * @author Rob Klein
 * @version 	$Id: ImportFAJournal2.java,v 1.0 $
 * 
 */
public class CreateZipFile {
 
	 
	/**
	* Zip the srcFolder into the destFileZipFile. All the folder subtree of the src folder is added to the destZipFile
	* archive.
	* 
	*
	* @param srcFolder File, the path of the srcFolder
	* @param destZipFile File, the path of the destination zipFile. This file will be created or erased.
	*/
	static public void zipFolder(File srcFolder, File destZipFile, String includesdir) 
	{    
	    Zip zipper = new Zip();
	    zipper.setDestFile(destZipFile);		
	    zipper.setBasedir(srcFolder);
	    zipper.setIncludes(includesdir);
	    zipper.setUpdate(true);
	    zipper.setCompress(true);
	    zipper.setCaseSensitive(false);	    
	    zipper.setFilesonly(false);
	    zipper.setTaskName("zip");
	    zipper.setTaskType("zip");	    
	    zipper.setProject(new Project());
	    zipper.setOwningTarget(new Target());
	    zipper.execute();
	    System.out.println(destZipFile);
	 }
	static public void tarFolder(File srcFolder, File destTarFile, String includesdir) 
	{    
	    Tar tarer = new Tar();
	    tarer.setDestFile(destTarFile);
	    tarer.setBasedir(srcFolder);
	    tarer.setIncludes(includesdir);
	    tarer.setCaseSensitive(false);	    
	    tarer.setTaskName("tar");
	    tarer.setTaskType("tar");	    
	    tarer.setProject(new Project());
	    tarer.setOwningTarget(new Target());
	    tarer.execute();
	 }
	static public void gzipFile(File srcFile, File destFile) 
	{    
	    GZip GZiper = new GZip();
	    GZiper.setDestfile(destFile);
	    GZiper.setSrc(srcFile);	    	    
	    GZiper.setTaskName("gzip");
	    GZiper.setTaskType("gzip");
	    GZiper.setProject(new Project());
	    GZiper.setOwningTarget(new Target());
	    GZiper.execute();
	 }
	static public void unpackFile(File zipFilepath, File destinationDir)
    {
            Expand Unzipper = new Expand();
            Unzipper.setDest(destinationDir);
            Unzipper.setSrc(zipFilepath);
            Unzipper.setTaskType ("unzip");
            Unzipper.setTaskName ("unzip");
            Unzipper.setProject(new Project());
            Unzipper.setOwningTarget(new Target());	
            Unzipper.execute();
     }
	static public String getParentDir(File zipFilepath)
    {
		try {
		ZipFile zipFile = new ZipFile(zipFilepath);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		ZipEntry entry = entries.nextElement();
		File tempfile = new File(entry.getName());
		while (tempfile.getParent()!=null)
			tempfile = tempfile.getParentFile();		
		return tempfile.getName();
		} catch (IOException ioe) {
		      System.err.println("Unhandled exception:");
		      ioe.printStackTrace();
		      return "";
	    }		
     }
	}//	CreateZipFile


