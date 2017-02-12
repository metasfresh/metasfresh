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
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;


/**
 *	Field Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MField.java,v 1.2 2006/07/30 00:58:04 jjanke Exp $
 */
public class MField extends X_AD_Field
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7124162742037904113L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Field_ID id
	 *	@param trxName transaction
	 */
	public MField (Properties ctx, int AD_Field_ID, String trxName)
	{
		super (ctx, AD_Field_ID, trxName);
		if (AD_Field_ID == 0)
		{
		//	setAD_Tab_ID (0);	//	parent
		//	setAD_Column_ID (0);
		//	setName (null);
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setIsCentrallyMaintained (true);	// Y
			setIsDisplayed (true);	// Y
			setIsEncrypted (false);
			setIsFieldOnly (false);
			setIsHeading (false);
			setIsReadOnly (false);
			setIsSameLine (false);
		//	setObscureType(OBSCURETYPE_ObscureDigitsButLast4);
		}	
	}	//	MField

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MField (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MField

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 */
	public MField (final I_AD_Tab parent)
	{
		this (InterfaceWrapperHelper.getCtx(parent), 0, InterfaceWrapperHelper.getTrxName(parent));
		setClientOrgFromModel(parent);
		setAD_Tab(parent);
	}	//	MField
	
	/**
	 * 	Copy Constructor
	 *	@param parent parent
	 *	@param from copy from
	 */
	public MField (MTab parent, MField from)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		copyValues(from, this);
		setClientOrg(parent);
		setAD_Tab_ID(parent.getAD_Tab_ID());
		setEntityType(parent.getEntityType());
	}	//	M_Field
	
	/**
	 * 	Set Column Values
	 *	@param column column
	 */
	public void setColumn (I_AD_Column column)
	{
		setAD_Column_ID (column.getAD_Column_ID());
		setName (column.getName());
		setDescription(column.getDescription());
		setHelp(column.getHelp());
		setDisplayLength(column.getFieldLength());
		setEntityType(column.getEntityType());
	}	//	setColumn
	
	/**
	 * 	beforeSave
	 *	@see org.compiere.model.PO#beforeSave(boolean)
	 *	@param newRecord
	 *	@return
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//	Sync Terminology
		if ((newRecord || is_ValueChanged("AD_Column_ID")) 
			&& isCentrallyMaintained())
		{
			M_Element element = M_Element.getOfColumn(getCtx(), getAD_Column_ID(), get_TrxName());
			setName (element.getName ());
			setDescription (element.getDescription ());
			setHelp (element.getHelp());
		}

		return true;
	}	//	beforeSave
	
}	//	MField
