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

import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * 	Web Media Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MMedia.java,v 1.7 2006/07/30 00:51:02 jjanke Exp $
 */
public class MMedia extends X_CM_Media
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2292852420984727096L;

	/**
	 * 	Get Media
	 *	@param project
	 *	@return server list
	 */
	public static MMedia[] getMedia (MWebProject project)
	{
		ArrayList<MMedia> list = new ArrayList<MMedia>();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM CM_Media WHERE CM_WebProject_ID=? ORDER BY CM_Media_ID";
		try
		{
			pstmt = DB.prepareStatement (sql, project.get_TrxName());
			pstmt.setInt (1, project.getCM_WebProject_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MMedia (project.getCtx(), rs, project.get_TrxName()));
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
		MMedia[] retValue = new MMedia[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getMedia
	
	/**	Logger	*/
	private static Logger s_log = LogManager.getLogger(MMedia.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CM_Media_ID id
	 *	@param trxName transaction
	 */
	public MMedia (Properties ctx, int CM_Media_ID, String trxName)
	{
		super (ctx, CM_Media_ID, trxName);
	}	//	MMedia

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MMedia (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MMedia
	
	/** Web Project			*/
	private MWebProject 	m_project = null;
	
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
		return getWebProject().getAD_TreeCMM_ID();
	}	//	getAD_Tree_ID;
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (isSummary())
		{
			setMediaType(null);
			setAD_Image_ID(0);
		}
			
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
		if (newRecord)
		{
			StringBuffer sb = new StringBuffer ("INSERT INTO AD_TreeNodeCMM "
				+ "(AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, "
				+ "AD_Tree_ID, Node_ID, Parent_ID, SeqNo) "
				+ "VALUES (")
				.append(getAD_Client_ID()).append(",0, 'Y', now(), 0, now(), 0,")
				.append(getAD_Tree_ID()).append(",").append(get_ID())
				.append(", 0, 999)");
			int no = DB.executeUpdateAndSaveErrorOnFail(sb.toString(), get_TrxName());
			if (no > 0)
				log.debug("#" + no + " - TreeType=CMM");
			else
				log.warn("#" + no + " - TreeType=CMM");
			return no > 0;
		}
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
		StringBuffer sb = new StringBuffer ("DELETE FROM AD_TreeNodeCMM ")
			.append(" WHERE Node_ID=").append(get_IDOld())
			.append(" AND AD_Tree_ID=").append(getAD_Tree_ID());
		int no = DB.executeUpdateAndSaveErrorOnFail(sb.toString(), get_TrxName());
		if (no > 0)
			log.debug("#" + no + " - TreeType=CMM");
		else
			log.warn("#" + no + " - TreeType=CMM");
		return no > 0;
	}	//	afterDelete

	/**
	 * 	Get File Name
	 *	@return file name return ID
	 */
	public String getFileName()
	{
		return get_ID() + getExtension();
	}	//	getFileName
	
	/**
	 * 	Get Extension with .
	 *	@return extension
	 */
	public String getExtension()
	{
		String mt = getMediaType();
		if (MEDIATYPE_ApplicationPdf.equals(mt))
			return ".pdf";
		if (MEDIATYPE_ImageGif.equals(mt))
			return ".gif";
		if (MEDIATYPE_ImageJpeg.equals(mt))
			return ".jpg";
		if (MEDIATYPE_ImagePng.equals(mt))
			return ".png";
		if (MEDIATYPE_TextCss.equals(mt))
			return ".css";
		//	Unknown
		return ".dat";
	}	//	getExtension

	/**
	 * 	Get Image
	 *	@return image or null
	 */
	public MImage getImage()
	{
		if (getAD_Image_ID() != 0)
			return MImage.get(getCtx(), getAD_Image_ID());
		return null;
	}	//	getImage

}	//	MMedia
