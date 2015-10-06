/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *                                                                            *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com                     * 
 * Contributor(s): Low Heng Sin hengsin@avantz.com                            *
 *****************************************************************************/
package org.adempiere.pipo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.X_AD_Package_Imp_Detail;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public abstract class AbstractElementHandler implements ElementHandler {

	protected CLogger log = CLogger.getCLogger("PackIn");
	
	/**
	 * Get ID from Name for a table.
	 * TODO: substitute with PO.getAllIDs
	 *
	 * @param tableName
	 * @param name
	 * 
	 */
	public int get_ID (Properties ctx, String tableName, String name) {
		return IDFinder.get_ID(tableName, name, getClientId(ctx), getTrxName(ctx));
	}

	/**
	 * Get ID from column value for a table.
	 *
	 * @param tableName
	 * @param columName
	 * @param name
	 */
	public int get_IDWithColumn (Properties ctx, String tableName, String columnName, Object value) {
		return IDFinder.get_IDWithColumn(tableName, columnName, value, getClientId(ctx), getTrxName(ctx));
	}
	
	/**
     *	Write results to log and records in history table
     *
     *      @param success
     * 		@param tableName
     * 		@param objectType
     * 		@param objectID
     * 		@param objectStatus
     * 		@throws SAXException
     *       	
     */
    public int record_log (Properties ctx, int success, String objectName,String objectType, int objectID,
    		int objectIDBackup, String objectStatus, String tableName, int AD_Table_ID) throws SAXException{    	
    	StringBuffer recordLayout = new StringBuffer();
    	int id = 0;
    	TransformerHandler hd_document = getLogDocument(ctx);
		AttributesImpl attsOut = new AttributesImpl();
		String result = success == 1 ? "Success" : "Failure";
    	
		//hd_documemt.startElement("","","Successful",attsOut);
    		recordLayout.append("Type:")
    			.append(objectType)
    			.append("  -   Name:")
    			.append(objectName)
    			.append("  -  ID:")
    			.append(objectID)
    			.append("  -  Action:")
    			.append(objectStatus)
    			.append("  -  " + result);
    		
    		hd_document.startElement("","",result,attsOut);
    		hd_document.characters(recordLayout.toString().toCharArray(),0,recordLayout.length());
    		hd_document.endElement("","",result);
    		
    		X_AD_Package_Imp_Detail detail = new X_AD_Package_Imp_Detail(ctx, 0, getTrxName(ctx));
    		detail.setAD_Package_Imp_ID(getPackageImpId(ctx));
    		detail.setAD_Org_ID(Env.getAD_Org_ID(ctx) );
    		detail.setType(objectType);
    		detail.setName(objectName);
    		detail.setAction(objectStatus);
    		detail.setSuccess(result);
    		detail.setAD_Original_ID(objectID);
    		detail.setAd_Backup_ID(objectIDBackup);
    		detail.setTableName(tableName);
    		detail.setAD_Table_ID(AD_Table_ID);
    		
    		if ( !detail.save(getTrxName(ctx)) )
    			log.info("Insert to import detail failed");
    		
    		id = detail.get_ID();
    	
    	return id;  
    }
    
    /**
	 * Get ID from Name for a table with a Master reference.
	 *
	 * @param tableName
	 * @param name
	 * @param tableNameMaster
	 * @param nameMaster
	 */
	public int get_IDWithMaster (Properties ctx, String tableName, String name, String tableNameMaster, String nameMaster) {
		return IDFinder.get_IDWithMaster(tableName, name, tableNameMaster, nameMaster, getTrxName(ctx));
	}

    /**
     * Get ID from Name for a table with a Master reference.
     *
     * @param tableName
     * @param name
     * @param tableNameMaster
     * @param nameMaster
     */    
    
	public int get_IDWithMasterAndColumn (Properties ctx, String tableName, String columnName, String name, String tableNameMaster, int masterID) {
		return IDFinder.get_IDWithMasterAndColumn(tableName, columnName, name, tableNameMaster, masterID, 
				getTrxName(ctx));
	}

	/**
	 * Get ID from Name for a table with a Master reference ID.
	 *
	 * @param tableName
	 * @param name
	 * @param tableNameMaster
	 * @param masterID
	 */    
	public int get_IDWithMaster (Properties ctx, String tableName, String name, String tableNameMaster, int masterID) {
		return IDFinder.get_IDWithMaster(tableName, name, tableNameMaster, masterID, getTrxName(ctx));
	}

	/**
	 * Get ID from Name for a table.
	 * TODO: substitute with PO.getAllIDs
	 *
	 * @param tableName
	 * @param name
	 */
	public int getIDbyName (Properties ctx, String tableName, String name) {
		return IDFinder.getIDbyName(tableName, name, getClientId(ctx), getTrxName(ctx));
	}
	
	/**
	 * Get ID from Value for a table.
	 * TODO: substitute with PO.getAllIDs
	 *
	 * @param tableName
	 * @param name
	 */
	public int getIDbyValue (Properties ctx, String tableName, String value) {
		return IDFinder.getIDbyValue(tableName, value, getClientId(ctx), getTrxName(ctx));
	}
	
    /**
     *	Make backup copy of record.
     *
     *      @param tablename
     *  	
     *  	
     *       	
     */
    public int copyRecord(Properties ctx, String tableName, Object fromModel)
    {
    	final PO from = InterfaceWrapperHelper.getPO(fromModel);
	// Create new record
    	int idBackup = 0;
    	String colValue=null;
    	int tableID = get_IDWithColumn(ctx, "AD_Table", "TableName", tableName);    	
		POInfo poInfo = POInfo.getPOInfo(ctx, tableID, getTrxName(ctx));
		for (int i = 0; i < poInfo.getColumnCount(); i++){
			colValue=null;
			
			    int columnID =get_IDWithMasterAndColumn (ctx, "AD_Column", "ColumnName", poInfo.getColumnName(i), "AD_Table", tableID);
			    StringBuffer sqlD = new StringBuffer("SELECT AD_Reference_ID FROM AD_COLUMN WHERE AD_Column_ID = '"+columnID+"'");
	    		int referenceID = DB.getSQLValue(getTrxName(ctx),sqlD.toString());
	    		
	    		idBackup = DB.getNextID (getClientId(ctx), "AD_Package_Imp_Backup", getTrxName(ctx));
	    		
	    		sqlD = new StringBuffer("SELECT MAX(AD_PACKAGE_IMP_DETAIL_ID) FROM AD_PACKAGE_IMP_DETAIL");
	    		int idDetail = DB.getSQLValue(getTrxName(ctx),sqlD.toString())+1;
	    		
	    		if (referenceID == 10 || referenceID == 14 || referenceID == 34 || referenceID == 17)
	    			if (from != null && from.get_Value(i)!= null)
	    				colValue = from.get_Value(i).toString().replaceAll("'","''");	    		
				else if (referenceID == 20|| referenceID == 28)
					if (from != null && from.get_Value(i)!= null)	    				    				
	    				colValue = from.get_Value(i).toString().replaceAll("'","''");
				else
					;//Ignore
	    			    		
	    		StringBuffer sqlB = new StringBuffer ("INSERT INTO AD_Package_Imp_Backup") 
	    				.append( "(AD_Client_ID, AD_Org_ID, CreatedBy, UpdatedBy, " ) 
	    				.append( "AD_PACKAGE_IMP_BACKUP_ID, AD_PACKAGE_IMP_DETAIL_ID, AD_PACKAGE_IMP_ID," ) 
	    				.append( " AD_TABLE_ID, AD_COLUMN_ID, AD_REFERENCE_ID, COLVALUE)" )
	    				.append( "VALUES(" )
	    				.append( " "+ Env.getAD_Client_ID(ctx) )
	    				.append( ", "+ Env.getAD_Org_ID(ctx) )
	    				.append( ", "+ Env.getAD_User_ID(ctx) )
	    				.append( ", "+ Env.getAD_User_ID(ctx) )
						.append( ", " + idBackup )
						.append( ", " + idDetail )
	    				.append( ", " + getPackageImpId(ctx) )
	    				.append( ", " + tableID )
	    				.append( ", " + (columnID == -1 ? "null" : columnID) )
	    				.append( ", " + (referenceID == -1 ? "null" : referenceID) )
	    				.append( ", '" + (colValue != null ? colValue : (from != null ? from.get_Value(i) : "null")) )
	    				.append( "')");
	    		
	    		int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
	    		if (no == -1)
					log.info("Insert to import backup failed");
	    		//}
		}		
		return idBackup;
    }
    
    /**
     *	Open input file for processing
     *
     * 	@param String file with path
     * 	
     */
    public FileInputStream OpenInputfile (String filePath) {
    	
    	FileInputStream fileTarget = null;
    	
    	try {    	
    		fileTarget = new FileInputStream(filePath);
    	}
    	catch (FileNotFoundException e ) {
    		System.out.println("File not found: " + filePath);
    		
    		return null;
    	}
    	return fileTarget;
    }
    
    /**
     *	Open output file for processing
     *
     * 	@param String file with path
     * 	
     */
    public OutputStream OpenOutputfile (String filePath) {
    	
    	OutputStream fileTarget = null;
    	
    	try {    	
    		fileTarget = new FileOutputStream(filePath);
    	}
    	catch (FileNotFoundException e ) {
    		System.out.println("File not found: " + filePath);
    		
    		return null;
    	}
    	return fileTarget;
    }
    
    /**
     *	Copyfile
     *
     * 	@param String file with path
     * 	
     */
    public int copyFile (InputStream source,OutputStream target) {
    
    	 int byteCount = 0;
    	 int success = 0;
	        try {
	           while (true) {
	              int data = source.read();
	              if (data < 0)
	                 break;
	              target.write(data);
	              byteCount++;
	           }
	           source.close();
	           target.close();
	           //System.out.println("Successfully copied " + byteCount + " bytes.");
	        }
	        catch (Exception e) {
	           System.out.println("Error occurred while copying.  "+ byteCount + " bytes copied.");
	           log.log(Level.SEVERE, e.getLocalizedMessage(), e);
	           
	           success = -1;
	        }
	    return success;
    }
    
    /**
     * Get client id
     * @param ctx
     * @return int
     */
    protected int getClientId(Properties ctx) {
    	return Env.getContextAsInt(ctx, "AD_Client_ID");
    }
    
    /**
     * Get AD_Package_Imp_ID
     * @param ctx
     * @return int
     */
    protected int getPackageImpId(Properties ctx) {
    	return Env.getContextAsInt(ctx, "AD_Package_Imp_ID");
    }
    
    /**
     * Get update system maintained dictionary flag
     * @param ctx
     * @return update mode
     */
    protected String getUpdateMode(Properties ctx) {
    	return Env.getContext(ctx, "UpdateMode");
    }
    
    /**
     * Get current transaction name
     * @param ctx
     * @return transaction name
     */
    protected String getTrxName(Properties ctx) {
    	return Env.getContext(ctx, "TrxName");
    }
    
    /**
     * Get share document
     * @param ctx
     * @return TransformerHandler 
     */
    protected TransformerHandler getLogDocument(Properties ctx) {
    	return (TransformerHandler)ctx.get("LogDocument");
    }

    /**
     * @param ctx
     * @return package directory
     */
    protected String getPackageDirectory(Properties ctx) {
    	return Env.getContext(ctx, "PackageDirectory");
    }
    
    /**
     * Process element by entity type and user setting.
     * @param ctx
     * @param entityType
     * @return boolean
     */
    protected boolean isProcessElement(Properties ctx, String entityType) {
    	if ("D".equals(entityType) || "C".equals(entityType)) {
    		return "true".equalsIgnoreCase(getUpdateMode(ctx));
    	} else {
    		return true;
    	}
    }
    
    /**
     * return null for empty string ("").
     * @param atts
     * @param qName
     * @return string value
     */
    protected String getStringValue(Attributes atts, String qName) {
    	String s = atts.getValue(qName);
    	return ("".equals(s) ? null : s);
    }
    
}
