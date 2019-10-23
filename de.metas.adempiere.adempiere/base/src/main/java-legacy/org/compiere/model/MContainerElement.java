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
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.util.DB;

/**
 * CStage Element
 * 
 * @author Yves Sandfort
 * @version $Id$
 */
public class MContainerElement extends X_CM_Container_Element
{
	/**	serialVersionUID	*/
	private static final long serialVersionUID = 7230036377422361941L;

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MContainer.class);

	/**
	 * 	get Container Element by ID
	 *	@param ctx
	 *	@param CM_ContainerElement_ID
	 *	@param trxName
	 *	@return ContainerElement
	 */
	public static MContainerElement get(Properties ctx, int CM_ContainerElement_ID, String trxName) {
		MContainerElement thisContainerElement = null;
		String sql = "SELECT * FROM CM_Container_Element WHERE CM_Container_Element_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, CM_ContainerElement_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				thisContainerElement = (new MContainerElement(ctx, rs, trxName));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		return thisContainerElement;
	}

	/***************************************************************************
     * Standard Constructor
     * 
     * @param ctx context
     * @param CM_Container_Element_ID id
     * @param trxName transaction
     */
	public MContainerElement (Properties ctx, int CM_Container_Element_ID, String trxName)
	{
		super (ctx, CM_Container_Element_ID, trxName);
		if (CM_Container_Element_ID == 0)
		{
			setIsValid(false);
		}
	}	// MContainerElement

	/**
     * Load Constructor
     * 
     * @param ctx context
     * @param rs result set
     * @param trxName transaction
     */
	public MContainerElement (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	// MContainerElement

	/** Parent				*/
	private MContainer m_parent = null;
	
	/**
	 * 	Get Container get's related Container
	 *	@return MContainer
	 */
	public MContainer getParent()
	{
		if (m_parent == null)
			m_parent = new MContainer (getCtx(), getCM_Container_ID(), get_TrxName());
		return m_parent;

		/** No reason to do this ?? - should never return null - always there - JJ
		int[] thisContainer = MContainer.getAllIDs("CM_Container","CM_Container_ID=" + this.getCM_Container_ID(), get_TrxName());
		if (thisContainer != null) 
		{
			if (thisContainer.length==1)
				return new MContainer(getCtx(), thisContainer[0], get_TrxName());
		}
		return null;
		**/
	}	//	getContainer
	
	/**
	 * 	After Save.
	 * 	Insert
	 * 	- create / update index
	 *	@param newRecord insert
	 *	@param success save success
	 *	@return true if saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		reIndex(newRecord);
		return success;
	}	//	afterSave
	
	/**
	 * 	reIndex
	 *	@param newRecord
	 */
	public void reIndex(boolean newRecord)
	{
		int CMWebProjectID = 0;
		if (getParent()!=null)
			CMWebProjectID = getParent().getCM_WebProject_ID();
		String [] toBeIndexed = new String[3];
		toBeIndexed[0] = this.getName();
		toBeIndexed[1] = this.getDescription();
		toBeIndexed[2] = this.getContentHTML();
		MIndex.reIndex (newRecord, toBeIndexed, getCtx(), 
			getAD_Client_ID(), get_Table_ID(), get_ID(), CMWebProjectID, this.getUpdated());
	}	// reIndex
	
}	//	MContainerElement
