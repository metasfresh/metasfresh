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
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.compiere.Adempiere;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class CodeSnipitElementHandler extends AbstractElementHandler {

	@Override
	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		log.info(elementValue);
		Object_Status = "Update";
		Attributes atts = element.attributes;
		String releaseNumber = atts.getValue("ReleaseNo");			
		//Check Release Number
		if (Adempiere.getMainVersion().equals(releaseNumber) || releaseNumber.equals("all"))
		{
			String sourceName = atts.getValue("filename");
			String targetDirectory = atts.getValue("filedir");
			String oldCode = atts.getValue("oldcode");
			String newCode = atts.getValue("newcode");			
			
			int idDetail=0;
			InputStream source;  // Stream for reading from the source file.
			OutputStream copy;   // Stream for writing the copy.
			File currentDirectory = new File(".");
			
			String packagePath=null;       
			String sourcePath=null; 
			
			//get adempiere-all directory
			try {
				packagePath = getPackageDirectory(ctx);
				File parentDirectory = new File(packagePath);
				while (!parentDirectory.getName().equals("packages")){        		
					parentDirectory = parentDirectory.getParentFile();        				
				}
				parentDirectory = parentDirectory.getParentFile();        				
				sourcePath = parentDirectory.getCanonicalPath();
			} catch (IOException e1) {
				System.out.println("Can't find adempiere-all directory.");			
			}
			
			//	Create backup directory if required
			File backupDir = new File(packagePath+File.separator+"backup"+File.separator);
			if (!backupDir.exists()){
				boolean success = (new File(packagePath+File.separator+"backup"+File.separator)).mkdirs();
				if (!success) {
					log.info("Backup directory creation failed");
				}
			}			
			
			//Correct target directory for proper file separator
			String fullDirectory = sourcePath+targetDirectory;
			String targetDirectoryModified=null;
			String fileDate = null;
			char slash1 = '\\'; 
			char slash2 = '/';
			if (File.separator.equals("/"))			
				targetDirectoryModified = fullDirectory.replace(slash1,slash2);		
			else
				targetDirectoryModified = fullDirectory.replace(slash2,slash1);	
			
			File file = new File(targetDirectoryModified+sourceName);		
			log.info(targetDirectoryModified+sourceName);
			//TODO: derived force from user parameter
			boolean force = true;
			//	check to see if overwrites are allowed
			if (file.exists() && force == false) {
				System.out.println(
				"Output file exists.  Use the -f option to replace it.");
				return;  
			}		
			//backup file to package directory
			else if (file.exists() && force == true) {
				Object_Status = "Update";			
				log.info("Target Backup:"+targetDirectoryModified+sourceName);
				source = OpenInputfile(targetDirectoryModified+sourceName);
				SimpleDateFormat formatter_file = new SimpleDateFormat("yyMMddHHmmssSSSSZ");	    	
				Date today = new Date();
				fileDate = formatter_file.format(today);
				copy = OpenOutputfile(packagePath+File.separator+"backup"+File.separator+fileDate+"_"+sourceName);
				log.info("Source Backup:"+packagePath+File.separator+"backup"+File.separator+fileDate+"_"+sourceName);
				copyFile (source,copy);
				log.info("Backup Complete");
			}	        
			
			int success = readReplace(targetDirectoryModified+sourceName, oldCode, newCode);
			
//			Record in log
			int idBackup = DB.getNextID (getClientId(ctx), "AD_Package_Imp_Backup", getTrxName(ctx));        
			if (success != -1){
				try {				
					idDetail = record_log (ctx, 1, sourceName,"codesnipit", 0,0, Object_Status,sourceName,0);
				} catch (SAXException e) {
					log.info ("setfile:"+e);
				}           		        		
			}
			else{
				try {
					idDetail = record_log (ctx, 0, sourceName,"codesnipit", 0,0, Object_Status,sourceName,0);
				} catch (SAXException e) {
					log.info ("setfile:"+e);
				}
			}
			//Record in transaction file 
			StringBuffer sqlB = new StringBuffer ("INSERT INTO AD_Package_Imp_Backup") 
					.append( "(AD_Client_ID, AD_Org_ID, CreatedBy, UpdatedBy, " ) 
					.append( "AD_PACKAGE_IMP_BACKUP_ID, AD_PACKAGE_IMP_DETAIL_ID, AD_PACKAGE_IMP_ID," ) 
					.append( " AD_PACKAGE_IMP_ORG_DIR, AD_PACKAGE_IMP_BCK_DIR)" )
					.append( "VALUES(" )
					.append( " "+ Env.getAD_Client_ID(ctx) )
					.append( ", "+ Env.getAD_Org_ID(ctx) )
					.append( ", "+ Env.getAD_User_ID(ctx) )
					.append( ", "+ Env.getAD_User_ID(ctx) )
					.append( ", " + idBackup )
					.append( ", " + idDetail )
					.append( ", " + getPackageImpId(ctx) )
					.append( ", '" + targetDirectoryModified+sourceName )
					.append( "', '" + packagePath+File.separator+"backup"+File.separator+fileDate+"_"+sourceName )
					.append( "')");
			
			int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
			if (no == -1)
				log.info("Insert to import backup failed");
			
		}
	}
	
	
    /**
     *	Find and replace code
     *
     *      @param file name
     *  	@param old string
     *  	@param new string
     *       	
     */
    public static int readReplace(String fname, String oldPattern, String replPattern){
    	String line;
    	StringBuffer sb = new StringBuffer();
    	
    	try {
    		
    		FileInputStream fis = new FileInputStream(fname);
    		BufferedReader reader=new BufferedReader ( new InputStreamReader(fis));
    		while((line = reader.readLine()) != null) {
    			line = line.replaceAll(oldPattern, replPattern);
    			System.err.println(line);
    			sb.append(line+"\n");    			
    		}    		
    		reader.close();
    		BufferedWriter out=new BufferedWriter ( new FileWriter(fname));
    		out.write(sb.toString());
    		out.close();
    	}
    	catch (Throwable e) {    			
    	            System.err.println("error replacing codesnipit "+e);
    	            return -1;
    	}
    	return 0;
    }


	@Override
	public void endElement(Properties ctx, Element element)
			throws SAXException {
	}


	@Override
	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		String FileDir = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_File_Directory);
		String FileName = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_FileName);
		String OldCode = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_Package_Code_Old);
		String NewCode = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_Package_Code_New);
		String ReleaseNo = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_ReleaseNo);
		AttributesImpl atts = new AttributesImpl();
		createSnipitBinding(atts, FileDir, FileName, OldCode, NewCode, ReleaseNo);
		document.startElement("","","codesnipit",atts);
		document.endElement("","","codesnipit");
	}
	
	private AttributesImpl createSnipitBinding( AttributesImpl atts, String FileDir, String FileName, String OldCode, String NewCode, String ReleaseNo) 
	{
		atts.clear();
		atts.addAttribute("","","filedir","CDATA",FileDir);
		atts.addAttribute("","","filename","CDATA",FileName);			
		String preOldCode = OldCode.toString();
		String preNewCode = NewCode.toString();
		String modOldCode = preOldCode.replaceAll("\\$","\\\\\\$").replaceAll("\\.","\\\\.")				
		.replaceAll("\\^","\\\\^").replaceAll("\\(","\\\\(").replaceAll("\\)","\\\\)")
		.replaceAll("\\[","\\\\[").replaceAll("\\/","\\\\/").replaceAll("\\+","\\\\+")
		.replaceAll("\\*","\\\\*").replaceAll("\\|","\\\\|");				
		String modNewCode = preNewCode.replaceAll("\\$","\\\\\\$").replaceAll("\\.","\\\\.")
		.replaceAll("\\^","\\\\^").replaceAll("\\(","\\\\(").replaceAll("\\)","\\\\)")
		.replaceAll("\\[","\\\\[").replaceAll("\\/","\\\\/").replaceAll("\\+","\\\\+")
		.replaceAll("\\*","\\\\*").replaceAll("\\|","\\\\|");							
		atts.addAttribute("","","oldcode","CDATA",modOldCode);
		atts.addAttribute("","","newcode","CDATA",modNewCode);
		atts.addAttribute("","","ReleaseNo","CDATA",ReleaseNo);
		return atts;
	}
}
