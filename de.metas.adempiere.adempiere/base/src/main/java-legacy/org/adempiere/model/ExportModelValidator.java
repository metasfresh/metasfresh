/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Trifon Trifonov.                                      *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Trifon Trifonov (trifonnt@users.sourceforge.net)                  *
*                                                                     *
* Sponsors:                                                           *
* - E-evolution (http://www.e-evolution.com)                          *
***********************************************************************/

package org.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.process.rpl.exp.ExportHelper;
import org.compiere.model.MClient;
import org.compiere.model.MReplicationStrategy;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_AD_ReplicationDocument;
import org.compiere.model.X_AD_ReplicationTable;
import org.slf4j.Logger;
import de.metas.logging.LogManager;


/**
 *	Export Validator which is responsible to create XML document.
 *	
 *	@author Trifon Trifonov
 *	@author victor.perez@e-evolution.com, www.e-evolution.com
 * <li> BF2875989 Deactivate replication records are include to replication
 * <li> https://sourceforge.net/tracker/?func=detail&aid=2875989&group_id=176962&atid=879332
 *	@version $Id$
 */
public class ExportModelValidator implements ModelValidator
{
	
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(ExportModelValidator.class);
	
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	
	/** Organization	*/
	private int m_AD_Org_ID = -1;
	
	/** Role			*/
	private int m_AD_Role_ID = -1;
	
	/** User			*/
	private int m_AD_User_ID = -1;
	
	/** Replication Strategy **/
	private int m_AD_ReplicationStrategy_ID = -1;
	
	/** Export Helper				*/
	ExportHelper expHelper = null;	
	
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ExportModelValidator ()
	{
		super ();
	}
	
	/**
	 *	Initialize Validation
	 *	@param engine validation engine 
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else 
		{
			log.warn("Export Model Validator cannot be used as a global validator, it needs to be defined in a per-client (tenant) basis");
			return;
		}
		
		
		MReplicationStrategy rplStrategy = null;
		
		m_AD_ReplicationStrategy_ID =  client.getAD_ReplicationStrategy_ID();
		log.info("client.getAD_ReplicationStrategy_ID() = " + m_AD_ReplicationStrategy_ID);
		
		if (m_AD_ReplicationStrategy_ID > 0) {
			rplStrategy = new MReplicationStrategy(client.getCtx(), m_AD_ReplicationStrategy_ID, ITrx.TRXNAME_None);
			if(!rplStrategy.isActive())
			{	
				return;
			}
			expHelper = new ExportHelper(client, rplStrategy);
		}
		// Add Tables
		// We want to be informed when records in Replication tables are created/updated/deleted!
		//engine.addModelChange(MBPartner.Table_Name, this);
		//engine.addModelChange(MOrder.Table_Name, this);
		//engine.addModelChange(MOrderLine.Table_Name, this);
		if (rplStrategy != null) {

			for (X_AD_ReplicationTable rplTable : rplStrategy.getReplicationTables()) {
				if (X_AD_ReplicationTable.REPLICATIONTYPE_Merge.equals(rplTable.getReplicationType())
					|| X_AD_ReplicationTable.REPLICATIONTYPE_Reference.equals(rplTable.getReplicationType())) 
				{
					MTable table = MTable.get (client.getCtx(), rplTable.getAD_Table_ID());
					engine.addModelChange(table.getTableName(), this);
				}
			}
		}
		// Add Documents
		// We want to be informed when Replication documents are created/updated/deleted!
		if (rplStrategy != null) {
			for (X_AD_ReplicationDocument rplDocument : rplStrategy.getReplicationDocuments()) {				
				if (X_AD_ReplicationDocument.REPLICATIONTYPE_Merge.equals(rplDocument.getReplicationType())
					|| X_AD_ReplicationDocument.REPLICATIONTYPE_Reference.equals(rplDocument.getReplicationType())) 
				{
					//MDocType docType = MDocType.get(client.getCtx(), rplDocuments[i].getC_DocType_ID());
					MTable table = MTable.get (client.getCtx(), rplDocument.getAD_Table_ID());			
					engine.addDocValidate(table.getTableName(), this);
				}
			}
		}
		
	}

    /**
     *	Model Change of a monitored Table.
     *	Called after PO.beforeSave/PO.beforeDelete
     *	@param po persistent object
     *	@param type TYPE_
     *	@return error message or null
     *	@exception Exception if the recipient wishes the change to be not accept.
     */
	public String modelChange (PO po, int type) throws Exception
	{
		//String Mode = "Table";
		log.info("po.get_TableName() = " + po.get_TableName());
		if (expHelper != null) {
		if (   type == TYPE_AFTER_CHANGE 
			|| type == TYPE_AFTER_NEW 
			|| type == TYPE_BEFORE_DELETE) // After Change or After New
			{
				expHelper.exportRecord(	po,
						MReplicationStrategy.REPLICATION_TABLE,
						MReplicationStrategy.getReplicationTable(po.getCtx(), m_AD_ReplicationStrategy_ID, po.get_Table_ID()).getReplicationType(),
						type);
			}			
		}

		return null;
	}
	
	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt 
     *	when you called addDocValidate for the table.
	 *	@param po persistent object
	 *	@param type see TIMING_ constants
     *	@return error message or null
	 * @throws Exception 
	 */
	public String docValidate (PO po, int type) 
	{
		log.info("po.get_TableName() = " + po.get_TableName());
		String result = null;
		if (expHelper != null) {
			try {
				if (   type == TIMING_AFTER_COMPLETE 
					|| type == TIMING_AFTER_CLOSE 
					|| type == TIMING_AFTER_REVERSECORRECT 
					|| type == TIMING_AFTER_VOID
					|| type == TIMING_AFTER_VOID
					|| type == TIMING_AFTER_PREPARE
				)
				{
					expHelper.exportRecord(	po, 
											MReplicationStrategy.REPLICATION_DOCUMENT ,
											MReplicationStrategy.getReplicationDocument(po.getCtx(), m_AD_ReplicationStrategy_ID, po.get_Table_ID()).getReplicationType(),
											type);
									
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = e.toString();
			}
		}
		return result;
	}

	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		m_AD_Org_ID = AD_Org_ID;
		m_AD_Role_ID = AD_Role_ID;
		m_AD_User_ID = AD_User_ID;
		
		log.info("AD_Org_ID  =" + m_AD_Org_ID);
		log.info("AD_Role_ID =" + m_AD_Role_ID);
		log.info("AD_User_ID =" + m_AD_User_ID);
		return null;
	}

	
	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer (ExportModelValidator.class.getName());
		return sb.toString();
	}
	
}
