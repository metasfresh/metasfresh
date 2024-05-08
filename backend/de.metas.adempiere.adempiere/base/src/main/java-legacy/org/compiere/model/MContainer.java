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

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Container Model
 * 
 * @author Yves Sandfort
 * @version $Id: MContainer.java,v 1.20 2006/09/05 23:22:53 comdivision Exp $
 * FR: [ 2214883 ] Remove SQL code and Replace for Query - red1/trifon
 */
public class MContainer extends X_CM_Container
{
	/**	serialVersionUID	*/
	private static final long serialVersionUID = 395679572291279730L;
	
	/**
	 * 	get Container by Relative URL
	 *	@param ctx
	 *	@param relURL
	 *	@param CM_WebProject_Id
	 *	@param trxName
	 *	@return Container or null if not found
	 */
	public static MContainer get(Properties ctx, String relURL, int CM_WebProject_Id, String trxName) {
		MContainer thisContainer = null;
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1/trifon
		String whereClause = "(RelativeURL LIKE ? OR RelativeURL LIKE ?) AND CM_WebProject_ID=?";
		thisContainer = new Query(ctx, MContainer.Table_Name, whereClause, trxName)
		.setParameters(new Object[]{relURL, relURL+"/",CM_WebProject_Id})
		.first();

		return thisContainer;
	}
	
	
	/**
	 * 	get Container
	 *	@param ctx
	 *	@param CM_Container_ID
	 *	@param CM_WebProject_Id
	 *	@param trxName
	 *	@return Container or null if not found
	 */
	public static MContainer get(Properties ctx, int CM_Container_ID, int CM_WebProject_Id, String trxName) {
		MContainer thisContainer = null;
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1/trifon
		String whereClause = "CM_Container_ID=? AND CM_WebProject_ID=?";
		thisContainer = new Query(ctx, MContainer.Table_Name, whereClause, trxName)
		.setParameters(new Object[]{CM_Container_ID, CM_WebProject_Id})
		.first();
		//
		return thisContainer;
	}
	
	/**
     * Copy Stage into Container
     * 
     * @param project WebProject
     * @param stage Stage to copy from
     * @param path Relative URL to it
     * @return Container
     */
	public static MContainer copy (MWebProject project, MCStage stage,
		String path)
	{
		MContainer cc = getDirect (stage.getCtx(), stage.getCM_CStage_ID (),
			stage.get_TrxName ());
		if (cc == null) // new
			cc = new MContainer (stage.getCtx (), 0, stage.get_TrxName ());
		cc.setStage (project, stage, path);
		cc.save ();
		if (!stage.isSummary ())
		{
			cc.updateElements (project, stage, stage.get_TrxName ());
			cc.updateTTables (project, stage, stage.get_TrxName ());
		}
		return cc;
	}	// copy

	/**
     * Get Container directly from DB (not cached)
     * 
     * @param ctx context
     * @param CM_Container_ID Container ID
     * @param trxName transaction
     * @return Container or null
     */
	public static MContainer getDirect (Properties ctx, int CM_Container_ID,
		String trxName)
	{
		MContainer cc = null;
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1/trifon
		String whereClause = "CM_Container_ID=?";
		cc = new Query(ctx, MContainer.Table_Name, whereClause, trxName)
		.setParameters(new Object[]{CM_Container_ID})
		.first();
		//
		return cc;
	} // getDirect

	/**
     * Get Containers
     * 
     * @param project
     *            Project to use
     * @return stages
     */
	public static MContainer[] getContainers (MWebProject project)
	{
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1/trifon
		String whereClause = "CM_WebProject_ID=?";
		List<MContainer> list = new Query(project.getCtx(), MContainer.Table_Name, whereClause, project.get_TrxName())
		.setParameters(new Object[]{project.getCM_WebProject_ID ()})
		.setOrderBy("CM_Container_ID")
		.list(MContainer.class);
		//
		MContainer[] retValue = new MContainer[list.size ()];
		list.toArray (retValue);
		return retValue;
	} // getContainers

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MContainer.class);

	
	/***************************************************************************
     * Standard Constructor
     * 
     * @param ctx context
     * @param CM_Container_ID id
     * @param trxName transaction
     */
	public MContainer (Properties ctx, int CM_Container_ID, String trxName)
	{
		super (ctx, CM_Container_ID, trxName);
		if (CM_Container_ID == 0)
		{
			setIsValid(false);
			setIsIndexed(false);
			setIsSecure(false);
			setIsSummary(false);
		}
	} // MContainer

	/**
     * Load Constructor
     * 
     * @param ctx
     *            context
     * @param rs
     *            result set
     * @param trxName
     *            transaction
     */
	public MContainer (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	} // MContainer

	/** Web Project */
	private MWebProject m_project = null;

	/** Stage Source */
	private MCStage	 m_stage   = null;
	
	/** Template */
	private MTemplate m_template = null;

	/**
     * Get Web Project
     * 
     * @return web project
     */
	public MWebProject getWebProject ()
	{
		if (m_project == null)
			m_project = MWebProject.get (getCtx (), getCM_WebProject_ID ());
		return m_project;
	} // getWebProject
	
	/**
	 * Get Template from Cache, or load it
	 * @return Template
	 */
	public MTemplate getTemplate() 
	{
		if (getCM_Template_ID()>0 && m_template==null)
			m_template = MTemplate.get(getCtx(), getCM_Template_ID(), null);
		return m_template;
	} // getTemplate

	/**
     * Get AD_Tree_ID
     * 
     * @return tree
     */
	public int getAD_Tree_ID ()
	{
		return getWebProject ().getAD_TreeCMC_ID ();
	} // getAD_Tree_ID;

	/**
     * Set/Copy Stage
     * 
     * @param project
     *            parent
     * @param stage
     *            stage
     * @param path
     *            path
     */
	protected void setStage (MWebProject project, MCStage stage, String path)
	{
		m_stage = stage;
		PO.copyValues (stage, this);
		setAD_Client_ID (project.getAD_Client_ID ());
		setAD_Org_ID (project.getAD_Org_ID ());
		setIsActive (stage.isActive ());
		setCM_ContainerLink_ID (stage.getCM_CStageLink_ID ());
		//
		setRelativeURL (path + stage.getRelativeURL ());
		//
		if (getMeta_Author () == null || getMeta_Author ().length () == 0)
			setMeta_Author (project.getMeta_Author ());
		if (getMeta_Content () == null || getMeta_Content ().length () == 0)
			setMeta_Content (project.getMeta_Content ());
		if (getMeta_Copyright () == null || getMeta_Copyright ().length () == 0)
			setMeta_Copyright (project.getMeta_Copyright ());
		if (getMeta_Publisher () == null || getMeta_Publisher ().length () == 0)
			setMeta_Publisher (project.getMeta_Publisher ());
		if (getMeta_RobotsTag () == null || getMeta_RobotsTag ().length () == 0)
			setMeta_RobotsTag (project.getMeta_RobotsTag ());
	} // setStage

	/**
     * Update Elements in Container from Stage
     * 
     * @param project
     *            project
     * @param stage
     *            stage
     * @param trxName
     *            Transaction
     */
	protected void updateElements (MWebProject project, MCStage stage,
		String trxName)
	{
		org.compiere.cm.CacheHandler thisHandler = new org.compiere.cm.CacheHandler (
			org.compiere.cm.CacheHandler.convertJNPURLToCacheURL (getCtx ()
				.getProperty ("java.naming.provider.url")), log, getCtx (),
			get_TrxName ());
		// First update the new ones...
		int[] tableKeys = X_CM_CStage_Element.getAllIDs ("CM_CStage_Element",
			"CM_CStage_ID=" + stage.get_ID (), trxName);
		if (tableKeys != null && tableKeys.length > 0)
		{
			for (int i = 0; i < tableKeys.length; i++)
			{
				X_CM_CStage_Element thisStageElement = new X_CM_CStage_Element (
					project.getCtx (), tableKeys[i], trxName);
				int[] thisContainerElementKeys = X_CM_Container_Element
					.getAllIDs ("CM_Container_Element", "CM_Container_ID="
						+ stage.get_ID () + " AND Name LIKE '"
						+ thisStageElement.getName () + "'", trxName);
				X_CM_Container_Element thisContainerElement;
				if (thisContainerElementKeys != null
					&& thisContainerElementKeys.length > 0)
				{
					thisContainerElement = new X_CM_Container_Element (project
						.getCtx (), thisContainerElementKeys[0], trxName);
				}
				else
				{
					thisContainerElement = new X_CM_Container_Element (project
						.getCtx (), 0, trxName);
				}
				thisContainerElement.setCM_Container_ID (stage.get_ID ());
				X_CM_CStage_Element stageElement = new X_CM_CStage_Element (
					project.getCtx (), tableKeys[i], trxName);
				thisContainerElement.setName (stageElement.getName ());
				thisContainerElement.setDescription (stageElement.getDescription());
				thisContainerElement.setHelp (stageElement.getHelp ());
				thisContainerElement.setIsActive (stageElement.isActive ());
				thisContainerElement.setIsValid (stageElement.isValid ());
				String contentHTML = thisStageElement.getContentHTML ();
				thisContainerElement.setContentHTML (contentHTML);
				// PO.copyValues(new
				// X_CM_CStage_Element(project.getCtx(),tableKeys[i],trxName),
				// thisContainerElement);
				thisContainerElement.save (trxName);
				// Remove Container from cache
				thisHandler.cleanContainerElement (thisContainerElement
					.get_ID ());
			}
		}
		// Now we are checking the existing ones to delete the unneeded ones...
		tableKeys = X_CM_Container_Element.getAllIDs ("CM_Container_Element",
			"CM_Container_ID=" + stage.get_ID (), trxName);
		if (tableKeys != null && tableKeys.length > 0)
		{
			for (int i = 0; i < tableKeys.length; i++)
			{
				X_CM_Container_Element thisContainerElement = new X_CM_Container_Element (
					project.getCtx (), tableKeys[i], trxName);
				int[] thisCStageElementKeys = X_CM_CStage_Element
					.getAllIDs ("CM_CStage_Element", "CM_CStage_ID="
						+ stage.get_ID () + " AND Name LIKE '"
						+ thisContainerElement.getName () + "'", trxName);
				// If we cannot find a representative in the Stage we will delete from production
				if (thisCStageElementKeys == null
					|| thisCStageElementKeys.length < 1)
				{
					// First delete it from cache, then delete the record itself
					thisHandler.cleanContainerElement (thisContainerElement
						.get_ID ());
					thisContainerElement.delete (true);
				}
			}
		}
	}

	/**
     * Update Elements in Container from Stage
     * 
     * @param project
     *            project
     * @param stage
     *            stage
     * @param trxName
     *            Transaction
     */
	protected void updateTTables (MWebProject project, MCStage stage,
		String trxName)
	{
		int[] tableKeys = X_CM_CStageTTable.getAllIDs (I_CM_CStageTTable.Table_Name,
			"CM_CStage_ID=" + stage.get_ID (), trxName);
		if (tableKeys != null && tableKeys.length > 0)
		{
			for (int i = 0; i < tableKeys.length; i++)
			{
				X_CM_CStageTTable thisStageTTable = new X_CM_CStageTTable (
					project.getCtx (), tableKeys[i], trxName);
				int[] thisContainerTTableKeys = X_CM_ContainerTTable.getAllIDs (
					I_CM_ContainerTTable.Table_Name, "CM_Container_ID=" + stage.get_ID ()
						+ " AND CM_TemplateTable_ID="
						+ thisStageTTable.getCM_TemplateTable_ID (), trxName);
				X_CM_ContainerTTable thisContainerTTable;
				if (thisContainerTTableKeys != null
					&& thisContainerTTableKeys.length > 0)
				{
					thisContainerTTable = new X_CM_ContainerTTable (project
						.getCtx (), thisContainerTTableKeys[0], trxName);
				}
				else
				{
					thisContainerTTable = new X_CM_ContainerTTable (project
						.getCtx (), 0, trxName);
				}
				thisContainerTTable.setCM_Container_ID (stage.get_ID ());
				PO.copyValues (new X_CM_CStageTTable (project.getCtx (),
					tableKeys[i], trxName), thisContainerTTable);
				thisContainerTTable.save (trxName);
			}
		}
	}

	/**
     * SaveNew getID
     * 
     * @return ID
     */
	@Override
	protected int saveNew_getID ()
	{
		if (m_stage != null)
			return m_stage.getCM_CStage_ID ();
		return 0;
	} // saveNew_getID

	/**
     * String Representation
     * 
     * @return info
     */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MContainer[").append (get_ID ())
			.append ("-").append (getName ()).append ("]");
		return sb.toString ();
	} // toString

	/**
     * After Save. Insert - create tree
     * 
     * @param newRecord
     *            insert
     * @param success
     *            save success
     * @return true if saved
     */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (newRecord)
		{
			StringBuffer sb = new StringBuffer (
				"INSERT INTO AD_TreeNodeCMC "
					+ "(AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, "
					+ "AD_Tree_ID, Node_ID, Parent_ID, SeqNo) " + "VALUES (")
				.append (getAD_Client_ID ()).append (
					",0, 'Y', now(), 0, now(), 0,").append (
					getAD_Tree_ID ()).append (",").append (get_ID ()).append (
					", 0, 999)");
			int no = DB.executeUpdate (sb.toString (), get_TrxName ());
			if (no > 0)
				log.debug("#" + no + " - TreeType=CMC");
			else
				log.warn("#" + no + " - TreeType=CMC");
			return no > 0;
		}
		return success;
	} // afterSave
	
	protected MContainerElement[] getAllElements()
	{
		int elements[] = MContainerElement.getAllIDs("CM_Container_Element", "CM_Container_ID=" + get_ID(), get_TrxName());
		if (elements.length>0)
		{
			MContainerElement[] containerElements = new MContainerElement[elements.length];
			for (int i=0;i<elements.length;i++)
			{
				containerElements[i] = new MContainerElement(getCtx(), elements[i], get_TrxName());
			}
			return containerElements;
		} else {
			return null;
		}
	}
	
	@Override
	protected boolean beforeDelete()
	{
		// Clean own index
		MIndex.cleanUp(get_TrxName(), getAD_Client_ID(), get_Table_ID(), get_ID());
		// Clean ElementIndex
		MContainerElement[] theseElements = getAllElements();
		if (theseElements!=null)
		{
			for (int i=0;i<theseElements.length;i++) 
			{
				theseElements[i].delete(false);
			}
		}
		//
		StringBuffer sb = new StringBuffer ("DELETE FROM AD_TreeNodeCMC ")
			.append (" WHERE Node_ID=").append (get_ID ()).append (
				" AND AD_Tree_ID=").append (getAD_Tree_ID ());
		int no = DB.executeUpdate (sb.toString (), get_TrxName ());
		if (no > 0)
			log.debug("#" + no + " - TreeType=CMC");
		else
			log.warn("#" + no + " - TreeType=CMC");
		return no > 0;
	}

	/**
     * After Delete
     * 
     * @param success
     * @return deleted
     */
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;
		//
		StringBuffer sb = new StringBuffer ("DELETE FROM AD_TreeNodeCMC ")
			.append (" WHERE Node_ID=").append (get_IDOld ()).append (
				" AND AD_Tree_ID=").append (getAD_Tree_ID ());
		int no = DB.executeUpdate (sb.toString (), get_TrxName ());
		// If 0 than there is nothing to delete which is okay.
		if (no > 0)
			log.debug("#" + no + " - TreeType=CMC");
		else
			log.warn("#" + no + " - TreeType=CMC");
		return true;
	} // afterDelete
	
	/**
	 * 	reIndex
	 *	@param newRecord
	 */
	public void reIndex(boolean newRecord)
	{
		String [] toBeIndexed = new String[8];
		toBeIndexed[0] = this.getName();
		toBeIndexed[1] = this.getDescription();
		toBeIndexed[2] = this.getRelativeURL();
		toBeIndexed[3] = this.getMeta_Author();
		toBeIndexed[4] = this.getMeta_Copyright();
		toBeIndexed[5] = this.getMeta_Description();
		toBeIndexed[6] = this.getMeta_Keywords();
		toBeIndexed[7] = this.getMeta_Publisher();
		MIndex.reIndex (newRecord, toBeIndexed, getCtx(), getAD_Client_ID(), get_Table_ID(), get_ID(), getCM_WebProject_ID(), this.getUpdated());
		MContainerElement[] theseElements = getAllElements();
		if (theseElements!=null) 
			for (int i=0;i<theseElements.length;i++)
				theseElements[i].reIndex (false);
	} // reIndex
} // MContainer
