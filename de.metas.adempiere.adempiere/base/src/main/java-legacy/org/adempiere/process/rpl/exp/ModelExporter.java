/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
  *****************************************************************************/
package org.adempiere.process.rpl.exp;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.process.rpl.api.IReplicationAccessContext;
import org.compiere.model.MEXPFormat;
import org.compiere.model.MReplicationStrategy;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_ReplicationTable;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.w3c.dom.Document;

/**
 * 
  * @author victor.perez@e-evolution.com 
 * FB  [1963487 ] Is necessary new process to export and import with an Export
 * @see  http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1963487&group_id=176962
 * @version $Id:$
 */
public class ModelExporter extends SvrProcess {

	/** Client Parameter */
	protected int p_AD_Client_ID = 0;

	/** Document Type Parameter */
	protected int p_C_DocType_ID = 0;

	/** Record ID */
	protected int p_Record_ID = 0;
	/** EXP_Format_ID */
	protected int p_EXP_Format_ID = 0;
	/** File Name **/
	protected String p_FileName = "";
	
	protected String p_WhereClause = "";
	
	/** Table ID */
	int AD_Table_ID = 0;
	
	
	/**
	 * Get Parameters
	 */
	@Override
	protected void prepare() {

		p_Record_ID = getRecord_ID();
		if (p_AD_Client_ID == 0)
			p_AD_Client_ID = Env.getAD_Client_ID(getCtx());
		AD_Table_ID = getTable_ID();

		StringBuffer sb = new StringBuffer("AD_Table_ID=").append(AD_Table_ID);
		sb.append("; Record_ID=").append(getRecord_ID());
		// Parameter
		ProcessInfoParameter[] paras = getParametersAsArray();
		for (ProcessInfoParameter para : paras) 
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("EXP_Format_ID"))
				p_EXP_Format_ID = para.getParameterAsInt();
			else if (name.equals("FileName"))
				p_FileName = (String)para.getParameter();
			else if (name.equals("WhereClause"))
				p_WhereClause = (String)para.getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		
		if(p_EXP_Format_ID == 0)
			p_EXP_Format_ID = p_Record_ID;
		if(p_FileName == null)
		{
			// Load XML file and parse it
			String fileNameOr = org.compiere.util.Ini.getAdempiereHome()
			+ System.getProperty("file.separator")  
			+ "data"
			+ System.getProperty("file.separator")
			+ "ExportFile.xml";
			p_FileName = fileNameOr;
		}		
		log.info(sb.toString());
	}

	/**
	 * Process
	 * 
	 * @return info
	 */
	@Override
	protected String doIt() throws Exception 
	{
		ExportHelper expHelper =  new ExportHelper(getCtx(),p_AD_Client_ID);
		MEXPFormat exportFormat = new MEXPFormat (getCtx(), p_EXP_Format_ID, get_TrxName() );
		File file = new File(p_FileName);

		final IReplicationAccessContext racCtx = expHelper.getDefaultIReplicationAccessContext(); // TODO hardcoded default
		Document doc = expHelper.exportRecord(exportFormat,p_WhereClause, MReplicationStrategy.REPLICATION_TABLE, X_AD_ReplicationTable.REPLICATIONTYPE_Merge,ModelValidator.TYPE_AFTER_CHANGE, racCtx);
		// Save the document to the disk file
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        
        //tranFactory.setAttribute("indent-number", 4);
        
        Transformer aTransformer = tranFactory.newTransformer();
        aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source src = new DOMSource(doc);
		
        // =================================== Write to String
        Writer writer = new StringWriter();
        Result dest2 = new StreamResult(writer);
        aTransformer.transform(src, dest2);
        // =================================== Write to Disk
        try {
        	Result dest = new StreamResult(file);
            aTransformer.transform(src, dest);
            writer.flush();
            writer.close();
            
        } catch (TransformerException ex) {
        	ex.printStackTrace();
        	throw ex;
        }
		return "Exported";
	}
}
