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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

public class DistFileElementHandler extends AbstractElementHandler {

	@Override
	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		Attributes atts = element.attributes;
		log.info(elementValue+" "+atts.getValue("name"));
		
		String releaseNumber = atts.getValue("ReleaseNo");
		//log.info(atts.getValue("ReleaseNo"));
		//log.info(Compiere.MAIN_VERSION);
		//Check Release Number
		if (releaseNumber == null || Adempiere.getMainVersion().equals(releaseNumber) || releaseNumber.equals("all"))
		{
			String fileName = atts.getValue("name");
			String sourceDirectory = atts.getValue("sourceDirectory");
			String targetDirectory = atts.getValue("targetDirectory");
			
			Object_Status = "New";
			int idDetail=0;
			InputStream inputStream;  // Stream for reading from the source file.
			OutputStream outputStream;   // Stream for writing the copy.       
			
			String packagePath=null;       
			String adempiereSourcePath=null; 
			
			//get adempiere-all directory
			try {
				packagePath = getPackageDirectory(ctx);
				File parentDirectory = new File(packagePath);
				while (!parentDirectory.getName().equals("packages")){        		
					parentDirectory = parentDirectory.getParentFile();        				
				}
				parentDirectory = parentDirectory.getParentFile();        				
				adempiereSourcePath = parentDirectory.getCanonicalPath();
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
			String fullTargetPath = adempiereSourcePath+targetDirectory;
			char slash1 = '\\'; 
			char slash2 = '/';
			if (File.separator.equals("/"))			
				fullTargetPath = fullTargetPath.replace(slash1,slash2);		
			else
				fullTargetPath = fullTargetPath.replace(slash2,slash1);	
			
			File file = new File(fullTargetPath+fileName);
			//TODO: derive force from user parameter
			boolean force = true;
			String fileDate = null;
			//check to see if overwrites are allowed
			if (file.exists() && force == false) {
				System.out.println(
				"Output file exists.  Use the -f option to replace it.");
				return;  
			}		
			//backup file to package directory
			else if (file.exists() && force == true) {
				Object_Status = "Update";			
				log.info("Target Backup:"+fullTargetPath+fileName);
				inputStream = OpenInputfile(fullTargetPath+fileName);
				SimpleDateFormat formatter_file = new SimpleDateFormat("yyMMddHHmmssSSSSZ");	    	
				Date today = new Date();
				fileDate = formatter_file.format(today);
				outputStream = OpenOutputfile(packagePath+File.separator+"backup"+File.separator+fileDate+"_"+fileName);
				log.info("Source Backup:"+packagePath+File.separator+"backup"+File.separator+fileDate+"_"+fileName);
				copyFile (inputStream, outputStream);
				log.info("Backup Complete");
			}		
			
//			Correct dist directory for proper file separator
			String fullSourcePath=null;		
			if (File.separator.equals("/"))			
				fullSourcePath = sourceDirectory.replace(slash1,slash2);
			else
				fullSourcePath = sourceDirectory.replace(slash2,slash1);		
			inputStream = OpenInputfile(packagePath+fullSourcePath+fileName);
			
//			Create Target directory if required		
			File targetDir = new File(fullTargetPath);				
			if (!targetDir.exists()){
				boolean success = (new File(fullTargetPath)).mkdirs();
				if (!success) {
					log.info("Target directory creation failed");
				}
			}		
			outputStream = OpenOutputfile(fullTargetPath+fileName);
			//Copy File
			int success = copyFile (inputStream,outputStream);        
			//Record in log
			int idBackup = DB.getNextID (getClientId(ctx), "AD_Package_Imp_Backup", getTrxName(ctx));        
			if (success != -1){
				try {				
					idDetail = record_log (ctx, 1, fileName,"file", 0,0, Object_Status,fileName,0);
				} catch (SAXException e) {
					log.info("setfile:"+e);
				}           		        		
			}
			else{
				try {
					idDetail = record_log (ctx, 0, fileName,"file", 0,0, Object_Status,fileName,0);
				} catch (SAXException e) {
					log.info("setfile:"+e);
				}
			}
			//Record in transaction file 
			StringBuffer sqlB = new StringBuffer ("INSERT INTO AD_Package_Imp_Backup" ) 
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
					.append( ", '" + fullTargetPath+fileName )
					.append( "', '" + packagePath+File.separator+"backup"+File.separator+fileDate+"_"+fileName )
					.append( "')");
			
			int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
			if (no == -1)
				log.info("Insert to import backup failed");
			
		}
	}

	@Override
	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	@Override
	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		String FileName = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_FileName);
		String Source_Directory = Env.getContext(ctx, "Source_Directory");
		String Target_Directory = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_Target_Directory);
		String ReleaseNo = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_ReleaseNo);
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("","","name","CDATA",FileName);
		atts.addAttribute("","","sourceDirectory","CDATA",Source_Directory);
		atts.addAttribute("","","targetDirectory","CDATA",Target_Directory);		
		atts.addAttribute("","","ReleaseNo","CDATA",ReleaseNo);		
		document.startElement("","","distfile",atts);
		document.endElement("","","distfile");
	}

}
