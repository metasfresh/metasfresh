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
 * Copyright (C) 2005 Robert KLEIN.  robeklein@hotmail.com  
 * 
 *****************************************************************************/

package org.adempiere.pipo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.compiere.Adempiere;
import org.compiere.model.X_AD_Package_Imp_Proc;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * IntPackIn Tool.
 * 
 * @author: Robert KLEIN. robeklein@hotmail.com
 */
public class PackIn extends SvrProcess {

	/** Logger */
	private Logger log = LogManager.getLogger("PackIn");
	//update system maintain dictionary, default to true
	public static String m_UpdateMode = "true";
	public static String m_Database = "Oracle";
	public static String m_Package_Dir = null;
	public int p_PackIn_ID = 0;
	
	private Map<String,Integer> tableCache = new HashMap<String,Integer>();
	private Map<String,Integer> columnCache = new HashMap<String,Integer>();
	
	/**
	 * add to table id cache
	 * @param tableName
	 * @param tableId
	 */
	public void addTable(String tableName, int tableId) {
		tableCache.put(tableName, tableId);
	}
	
	/**
	 * Find table id from cache
	 * @param tableName
	 * @return tableId
	 */
	public int getTableId(String tableName) {
		if (tableCache.containsKey(tableName))
			return tableCache.get(tableName).intValue();
		else
			return 0;
	}
	
	/**
	 * add to column id cache
	 * @param tableName
	 * @param columnName
	 * @param columnId
	 */
	public void addColumn(String tableName, String columnName, int columnId) {
		columnCache.put(tableName+"."+columnName, columnId);
	}
	
	/**
	 * find column id from cache
	 * @param tableName
	 * @param columnName
	 * @return column id
	 */
	public int getColumnId(String tableName, String columnName) {
		String key = tableName+"."+columnName;
		if (columnCache.containsKey(key)) 
			return columnCache.get(key).intValue();
		else
			return 0;
	}

	@Override
	protected void prepare() {
		p_PackIn_ID = getRecord_ID();
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++) {
		}
	} // prepare

	/**
	 * Uses PackInHandler to update AD.
	 * 
	 * @param fileName
	 *            xml file to read
	 * @return status message
	 */
	public String importXML(String fileName, Properties ctx, String trxName) throws Exception {
		log.info("importXML:" + fileName);
		File in = new File(fileName);
		if (!in.exists()) {
			String msg = "File does not exist: " + fileName;
			log.info("importXML:" + msg);
			return msg;
		}
		try {
			log.info("starting");
			PackInHandler handler = new PackInHandler();
			handler.set_TrxName(trxName);
			handler.setCtx(ctx);
			handler.setProcess(this);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			String msg = "Start Parser";
			log.info(msg);
			parser.parse(in, handler);
			msg = "End Parser";
			log.info(msg);
			return "OK.";
		} catch (Exception e) {
			log.error("importXML:", e);
			throw e;
		}
	}

	/**
	 * Doit
	 * 
	 * @return ""
	 * 
	 */
	@Override
	protected String doIt() throws Exception {

		X_AD_Package_Imp_Proc adPackageImp = new X_AD_Package_Imp_Proc(getCtx(),
				p_PackIn_ID, null);

		// clear cache of previous runs
		IDFinder.clearIDCache();

		// Create Target directory if required
		String packageDirectory = adPackageImp.getAD_Package_Dir();
		if (packageDirectory == null || packageDirectory.trim().length() == 0) {
			packageDirectory = Adempiere.getAdempiereHome();
		}
		File targetDir = new File( packageDirectory + File.separator
				+ "packages");

		if (!targetDir.exists()) {
			boolean success = (new File(packageDirectory
					+ File.separator + "packages")).mkdirs();
			if (!success) {
				log.info("Target directory creation failed");
			}
		}

		// Unzip package
		File zipFilepath = new File(adPackageImp.getAD_Package_Source());
		log.info("zipFilepath->" + zipFilepath);
		String PackageName = CreateZipFile.getParentDir(zipFilepath);
		CreateZipFile.unpackFile(zipFilepath, targetDir);

		String dict_file = packageDirectory + File.separator
				+ "packages" + File.separator + PackageName + File.separator
				+ "dict" + File.separator + "PackOut.xml";
		log.info("dict file->" + dict_file);
		PackIn packIn = new PackIn();

		if (adPackageImp.isAD_Override_Dict() == true)
			PackIn.m_UpdateMode = "true";
		else
			PackIn.m_UpdateMode = "false";

		PackIn.m_Package_Dir = packageDirectory + File.separator
				+ "packages" + File.separator + PackageName + File.separator;
		
		PackIn.m_Database = "PostgreSQL";

		// call XML Handler
		String msg = packIn.importXML(dict_file, getCtx(), get_TrxName());

		// Generate Model Classes
		// globalqss - don't call Generate Model must be done manual
		// String args[] =
		// {IntPackIn.getAD_Package_Dir()+"/dbPort/src/org/compiere/model/",
		// "org.compiere.model","'U'"};
		// org.compiere.util.GenerateModel.main(args) ;

		return msg;
	} // doIt
} // PackIn
