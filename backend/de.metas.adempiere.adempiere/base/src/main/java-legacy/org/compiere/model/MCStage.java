/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.DB;

/**
 * 	Container Stage Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MCStage.java,v 1.11 2006/09/23 10:44:05 comdivision Exp $
 */
public class MCStage extends X_CM_CStage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1455360873536769969L;


	/**
	 * 	Get Stages
	 *	@param project project
	 *	@return stages 
	 */
	public static MCStage[] getStages (MWebProject project)
	{
		ArrayList<MCStage> list = new ArrayList<MCStage>();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM CM_CStage WHERE CM_WebProject_ID=? ORDER BY CM_CStage_ID";
		try
		{
			pstmt = DB.prepareStatement (sql, project.get_TrxName());
			pstmt.setInt (1, project.getCM_WebProject_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MCStage (project.getCtx(), rs, project.get_TrxName()));
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		MCStage[] retValue = new MCStage[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getStages

	/**	Logger	*/
	private static Logger s_log = LogManager.getLogger(MCStage.class);
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CM_CStage_ID id
	 *	@param trxName tansaction
	 */
	public MCStage (Properties ctx, int CM_CStage_ID, String trxName)
	{
		super (ctx, CM_CStage_ID, trxName);
		if (CM_CStage_ID == 0)
		{
			setIsValid(false);
			setIsModified(false);
			setIsSecure(false);
			setIsSummary(false);
			setIsIndexed(false);
		}
	}	//	MCStage

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MCStage (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MCStage
	
	/** Web Project			*/
	private MWebProject 	m_project = null;
	
	
	/**
	 * 	Set Relative URL
	 *	@param RelativeURL
	 */
	@Override
	public void setRelativeURL (String RelativeURL)
	{
		if (RelativeURL != null)
		{
			if (RelativeURL.endsWith("/"))
				RelativeURL = RelativeURL.substring(0, RelativeURL.length()-1);
			int index = RelativeURL.lastIndexOf('/');
			if (index != -1)
				RelativeURL = RelativeURL.substring(index+1);
		}
		super.setRelativeURL (RelativeURL);
	}	//	setRelativeURL
	
	/**
	 * 	Get Web Project
	 *	@return web project
	 */
	public MWebProject getWebProject()
	{
		if (m_project == null)
			m_project = MWebProject.get(getCtx(), getCM_WebProject_ID());
		return m_project;
	}	//	getWebProject
	
	/**
	 * 	Get AD_Tree_ID
	 *	@return tree
	 */
	public int getAD_Tree_ID()
	{
		return getWebProject().getAD_TreeCMS_ID();
	}	//	getAD_Tree_ID;
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MCStage[")
			.append (get_ID()).append ("-").append (getName()).append ("]");
		return sb.toString ();
	} 	//	toString
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//	Length >0 if not (Binary, Image, Text Long)
		if ((!this.isSummary() || this.getContainerType().equals ("L")) && getCM_Template_ID()==0)
		{
			throw new FillMandatoryException(COLUMNNAME_CM_Template_ID);
		}
		// On Modification set isModified
		if (is_ValueChanged("IsModified"))
			setIsModified(true);
		//	Validate
		setRelativeURL(getRelativeURL());
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save.
	 * 	Insert
	 * 	- create tree
	 *	@param newRecord insert
	 *	@param success save success
	 *	@return true if saved
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		// If Not Summary Node check whether all Elements and Templatetable Records exist.
		if (!isSummary()) { 
			checkElements();
			checkTemplateTable();
		}
		if (newRecord)
		{
			StringBuffer sb = new StringBuffer ("INSERT INTO AD_TreeNodeCMS "
				+ "(AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, "
				+ "AD_Tree_ID, Node_ID, Parent_ID, SeqNo) "
				+ "VALUES (")
				.append(getAD_Client_ID()).append(",0, 'Y', now(), 0, now(), 0,")
				.append(getAD_Tree_ID()).append(",").append(get_ID())
				.append(", 0, 999)");
			int no = DB.executeUpdateAndSaveErrorOnFail(sb.toString(), get_TrxName());
			if (no > 0)
				log.debug("#" + no + " - TreeType=CMS");
			else
				log.warn("#" + no + " - TreeType=CMS");
			return no > 0;
		}
		/*if (success) {
		}*/
		return success;
	}	//	afterSave
	
	/**
	 * 	After Delete
	 *	@param success
	 *	@return deleted
	 */
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;
		//
		StringBuffer sb = new StringBuffer ("DELETE FROM AD_TreeNodeCMS ")
			.append(" WHERE Node_ID=").append(get_IDOld())
			.append(" AND AD_Tree_ID=").append(getAD_Tree_ID());
		int no = DB.executeUpdateAndSaveErrorOnFail(sb.toString(), get_TrxName());
		if (no > 0)
			log.debug("#" + no + " - TreeType=CMS");
		else
			log.warn("#" + no + " - TreeType=CMS");
		return no > 0;
	}	//	afterDelete
	
	/**
	 * 	Validate
	 *	@return info
	 */
	public String validate()
	{
		return "";
	}	//	validate
	
	/**
	 * Check whether all Elements exist
	 * @return true if updated
	 */
	protected boolean checkElements () {
		X_CM_Template thisTemplate = new X_CM_Template(getCtx(), this.getCM_Template_ID(), get_TrxName());
		StringBuffer thisElementList = new StringBuffer(thisTemplate.getElements());
		while (thisElementList.indexOf("\n")>=0) {
			String thisElement = thisElementList.substring(0,thisElementList.indexOf("\n"));
			thisElementList.delete(0,thisElementList.indexOf("\n")+1);
			checkElement(thisElement);
		}
		String thisElement = thisElementList.toString();
		checkElement(thisElement);
		return true;
	}

	/**
	 * Check single Element, if not existing create it...
	 * @param elementName
	 */
	protected void checkElement(String elementName) {
		int [] tableKeys = X_CM_CStage_Element.getAllIDs("CM_CStage_Element", "CM_CStage_ID=" + this.get_ID() + " AND Name like '" + elementName + "'", get_TrxName());
		if (tableKeys==null || tableKeys.length==0) {
			X_CM_CStage_Element thisElement = new X_CM_CStage_Element(getCtx(), 0, get_TrxName());
			thisElement.setAD_Client_ID(getAD_Client_ID());
			thisElement.setAD_Org_ID(getAD_Org_ID());
			thisElement.setCM_CStage_ID(this.get_ID());
			thisElement.setContentHTML(" ");
			thisElement.setName(elementName);
			thisElement.save(get_TrxName());
		}
	}
	
	/**
	 * Check whether all Template Table records exits
	 * @return true if updated
	 */
	protected boolean checkTemplateTable () {
		int [] tableKeys = X_CM_TemplateTable.getAllIDs("CM_TemplateTable", "CM_Template_ID=" + this.getCM_Template_ID(), get_TrxName());
		if (tableKeys!=null) {
			for (int i=0;i<tableKeys.length;i++) {
				X_CM_TemplateTable thisTemplateTable = new X_CM_TemplateTable(getCtx(), tableKeys[i], get_TrxName());
				int [] existingKeys = X_CM_CStageTTable.getAllIDs("CM_CStageTTable", "CM_TemplateTable_ID=" + thisTemplateTable.get_ID(), get_TrxName());
				if (existingKeys==null || existingKeys.length==0) {
					X_CM_CStageTTable newCStageTTable = new X_CM_CStageTTable(getCtx(), 0, get_TrxName());
					newCStageTTable.setAD_Client_ID(getAD_Client_ID());
					newCStageTTable.setAD_Org_ID(getAD_Org_ID());
					newCStageTTable.setCM_CStage_ID(get_ID());
					newCStageTTable.setCM_TemplateTable_ID(thisTemplateTable.get_ID());
					newCStageTTable.setDescription(thisTemplateTable.getDescription());
					newCStageTTable.setName(thisTemplateTable.getName());
					newCStageTTable.setOtherClause(thisTemplateTable.getOtherClause());
					newCStageTTable.setWhereClause(thisTemplateTable.getWhereClause());
					newCStageTTable.save();
				}
			}
		}
		return true;
	}
	
}	//	MCStage
