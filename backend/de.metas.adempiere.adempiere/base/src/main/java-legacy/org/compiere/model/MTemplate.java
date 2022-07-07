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
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import org.compiere.util.DB;

/**
 * Web Template Model
 * 
 * @author Yves Sandfort
 * @version $Id: MTemplate.java,v 1.12 2006/08/08 18:56:05 comdivision Exp $
 */
@SuppressWarnings("serial")
public class MTemplate extends X_CM_Template
{
	/**
     * Get MTemplate from Cache
     * 
     *	@param ctx context
     *	@param CM_Template_ID id
     *	@param trxName Transaction
     *	@return MWebProject
     */
	public static MTemplate get (Properties ctx, int CM_Template_ID, String trxName)
	{
		MTemplate retValue = new MTemplate (ctx, CM_Template_ID, trxName);
		if (retValue != null)
			return retValue;
		retValue = new MTemplate (ctx, CM_Template_ID, null);
		return retValue;
	}	// get

	/**
     * Standard Constructor
     * 
     *	@param ctx context
     *	@param CM_Template_ID id
     *	@param trxName transaction
     */
	public MTemplate (Properties ctx, int CM_Template_ID, String trxName)
	{
		super (ctx, CM_Template_ID, trxName);
	} // MTemplate

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
	public MTemplate (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	} // MTemplate

	/** Web Project */
	private MWebProject m_project = null;
	/** 
     * preBuildTemplate contains a preset Version including needed Subtemplates
     */
	private StringBuffer m_preBuildTemplate;


	/**
     * Get Web Project
     * @return web project
     */
	public MWebProject getWebProject ()
	{
		if (m_project == null)
			m_project = MWebProject.get (getCtx (), getCM_WebProject_ID ());
		return m_project;
	}	// getWebProject

	/**
     *	Get AD_Tree_ID
     *	@return tree
     */
	public int getAD_Tree_ID ()
	{
		return getWebProject ().getAD_TreeCMT_ID ();
	}	// getAD_Tree_ID;

	/**
     * Get the Template we prebuild (this means with added subtemplates)
     * @return StringBuffer with complete XSL Template
     */
	public StringBuffer getPreBuildTemplate ()
	{
		if (m_preBuildTemplate != null)
		{
			// Check whether the prebuild exists (i.e. we are from cache)
			return m_preBuildTemplate;
		}
		else
		{
			// We will build the prebuild code, so we check which subs are
			// needed and build it depending on them
			m_preBuildTemplate = new StringBuffer (getTemplateXST ());
			// Let's see whether the template calls Subtemplates...
			if (m_preBuildTemplate.indexOf ("<xsl:call-template") >= 0)
			{
				StringBuffer subTemplates = new StringBuffer ();
				int pos = 0;
				// JJ: if you don't use the value, could you use ArrayList ?
				Hashtable<String, String> subTemplateNames = new Hashtable<String, String> ();
				while (m_preBuildTemplate.indexOf ("<xsl:call-template", pos) >= 0)
				{
					String thisName = null;
					int beginPos = m_preBuildTemplate.indexOf (
						"<xsl:call-template", pos);
					int endPos = m_preBuildTemplate.indexOf ("/>", beginPos);
					if (m_preBuildTemplate.indexOf (">", beginPos) < endPos)
					{
						endPos = m_preBuildTemplate.indexOf (">", beginPos) + 1;
					}
					String tempTemplate = m_preBuildTemplate.substring (beginPos,
						endPos);
					pos = m_preBuildTemplate.indexOf ("<xsl:call-template", pos)
						+ tempTemplate.length ();
					if (tempTemplate.indexOf ("name=") >= 0)
					{
						thisName = tempTemplate.substring (tempTemplate
							.indexOf ("name=\"") + 6, tempTemplate.indexOf (
							"\"", tempTemplate.indexOf ("name=\"") + 7));
						if (!subTemplateNames.containsKey (thisName))
							subTemplateNames.put (thisName, "0");
					}
				}
				Enumeration thisEnum = subTemplateNames.keys ();
				while (thisEnum.hasMoreElements ())
				{
					String thisElement = thisEnum.nextElement ().toString ();
					int[] templateIDs = MTemplate.getAllIDs ("CM_Template",
						"Value LIKE '" + thisElement
							+ "' AND CM_WebProject_ID="
							+ getCM_WebProject_ID (), get_TrxName ());
					if (templateIDs == null || templateIDs.length == 0)
					{
						// No result, so we will log an error, but can not
						// help...
					}
					else if (templateIDs.length == 1)
					{
						MTemplate thisSubTemplate = new MTemplate (getCtx (),
							templateIDs[0], get_TrxName ());
						subTemplates.append (thisSubTemplate.getTemplateXST ());
					}
					else
					{
						// Too many results, we will use the first hit, but
						// that's not the clue!
						MTemplate thisSubTemplate = new MTemplate (getCtx (),
							templateIDs[0], get_TrxName ());
						subTemplates.append ("\n"
							+ thisSubTemplate.getTemplateXST () + "\n");
					}
				}
				m_preBuildTemplate.append (subTemplates);
				m_preBuildTemplate = new StringBuffer (m_preBuildTemplate
					.substring (0, m_preBuildTemplate
						.indexOf ("</xsl:stylesheet>"))
					+ subTemplates.toString () + "\n</xsl:stylesheet>");
			}
			return m_preBuildTemplate;
		}
	}	//	getPreBuildTemplate
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		return true;
	}	//	beforeSave

	/**
     * After Save. Insert - create tree
     * @param newRecord insert
     * @param success save success
     * @return true if saved
     */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (newRecord)
		{
			StringBuffer sb = new StringBuffer (
				"INSERT INTO AD_TreeNodeCMT "
					+ "(AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, "
					+ "AD_Tree_ID, Node_ID, Parent_ID, SeqNo) " + "VALUES (")
				.append (getAD_Client_ID ()).append (
					",0, 'Y', now(), 0, now(), 0,").append (
					getAD_Tree_ID ()).append (",").append (get_ID ()).append (
					", 0, 999)");
			int no = DB.executeUpdateAndSaveErrorOnFail(sb.toString (), get_TrxName ());
			if (no > 0)
				log.debug("#" + no + " - TreeType=CMT");
			else
				log.warn("#" + no + " - TreeType=CMT");
			return no > 0;
		}
		if (!newRecord)
		{
			org.compiere.cm.CacheHandler thisHandler = new org.compiere.cm.CacheHandler (
				org.compiere.cm.CacheHandler.convertJNPURLToCacheURL (getCtx ()
					.getProperty ("java.naming.provider.url")), log, getCtx (),
				get_TrxName ());
			if (!isInclude ())
			{
				// Clean Main Templates on a single level.
				thisHandler.cleanTemplate (this.get_ID ());
			}
			else
			{
				// Since we not know which main templates we will clean up all!
				thisHandler.emptyTemplate ();
			}
		}
		return success;
	}	// afterSave

	/**
     * After Delete
     * 
     * @param success
     * @return deleted
     */
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;
		//
		StringBuffer sb = new StringBuffer ("DELETE FROM AD_TreeNodeCMT ")
			.append (" WHERE Node_ID=").append (get_IDOld ()).append (
				" AND AD_Tree_ID=").append (getAD_Tree_ID ());
		int no = DB.executeUpdateAndSaveErrorOnFail(sb.toString (), get_TrxName ());
		if (no > 0)
			log.debug("#" + no + " - TreeType=CMT");
		else
			log.warn("#" + no + " - TreeType=CMT");
		return no > 0;
	}	// afterDelete

	/**
     * Get's all the Ads from Template AD Cat
     * @return Array of MAds
     */
	public MAd[] getAds ()
	{
		int[] AdCats = null;
		String sql = "SELECT count(*) FROM CM_Template_AD_Cat WHERE CM_Template_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			int numberAdCats = 0;
			pstmt = DB.prepareStatement (sql, get_TrxName ());
			pstmt.setInt (1, get_ID ());
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				numberAdCats = rs.getInt (1);
			}
			rs.close ();
			AdCats = new int[numberAdCats];
			int i = 0;
			sql = "SELECT CM_Ad_Cat_ID FROM CM_Template_AD_Cat WHERE CM_Template_ID=?";
			pstmt = DB.prepareStatement (sql, get_TrxName ());
			pstmt.setInt (1, get_ID ());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				AdCats[i] = rs.getInt (1);
				i++;
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error(sql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		if (AdCats != null && AdCats.length > 0)
		{
			MAd[] returnAds = new MAd[AdCats.length];
			for (int i = 0; i < AdCats.length; i++)
			{
				returnAds[i] = MAd.getNext (getCtx (), AdCats[i],
					get_TrxName ());
			}
			return returnAds;
		}
		else
		{
			return null;
		}
	}	//	getAds
	
} // MTemplate
