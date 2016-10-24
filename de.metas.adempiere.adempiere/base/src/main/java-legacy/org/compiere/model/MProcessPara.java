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

import org.compiere.util.DB;


/**
 *  Process Parameter Model
 *
 *  @author Jorg Janke
 *  @version $Id: MProcessPara.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MProcessPara extends X_AD_Process_Para
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2387741816477468470L;
	
	/**************************************************************************
	 * 	Constructor
	 *	@param ctx context
	 *	@param AD_Process_Para_ID id
	 *	@param trxName transaction
	 */
	public MProcessPara (Properties ctx, int AD_Process_Para_ID, String trxName)
	{
		super (ctx, AD_Process_Para_ID, trxName);
		if (AD_Process_Para_ID == 0)
		{
		//	setAD_Process_ID (0);	Parent
		//	setName (null);
		//	setColumnName (null);
			
			setFieldLength (0);
			setSeqNo (0);
		//	setAD_Reference_ID (0);
			setIsCentrallyMaintained (true);
			setIsRange (false);
			setIsMandatory (false);
			setIsAutocomplete(false);
			setEntityType (ENTITYTYPE_UserMaintained);
		}
	}	//	MProcessPara

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProcessPara (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProcessPara

	/**
	 * Parent constructor
	 * @param parent process
	 */
	MProcessPara(final MProcess parent)
	{
		
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setAD_Process_ID(parent.getAD_Process_ID());
		setEntityType(parent.getEntityType());
	}

	/**
	 * Copy settings from another process parameter
	 * overwrites existing data
	 * (including translations)
	 * and saves
	 * @param source 
	 */
	void copyFrom (final I_AD_Process_Para source)
	{
		log.debug("Copying from:" + source + ", to: " + this);
		setAD_Element_ID(source.getAD_Element_ID());
		setAD_Reference_ID(source.getAD_Reference_ID());
		setAD_Reference_Value_ID(source.getAD_Reference_Value_ID());
		setAD_Val_Rule_ID(source.getAD_Val_Rule_ID());
		setColumnName(source.getColumnName());
		setDefaultValue(source.getDefaultValue());
		setDefaultValue2(source.getDefaultValue2());
		setDescription(source.getDescription());
		setDisplayLogic(source.getDisplayLogic());
		setFieldLength(source.getFieldLength());
		setHelp(source.getHelp());
		setIsActive(source.isActive());
		setIsCentrallyMaintained(source.isCentrallyMaintained());
		setIsMandatory(source.isMandatory());
		setIsRange(source.isRange());
		setName(source.getName());
		setReadOnlyLogic(source.getReadOnlyLogic());
		setSeqNo(source.getSeqNo());
		setValueMax(source.getValueMax());
		setValueMin(source.getValueMin());
		setVFormat(source.getVFormat());
		setIsAutocomplete(source.isAutocomplete());
		saveEx();
		//
		// Delete newly created translations and copy translations from source
		// NOTE: don't use parameterized SQL queries because this script will be logged as a migration script (task 
		{
			final int adProcessParaId = getAD_Process_Para_ID();
			final String sqlDelete = "DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = " + adProcessParaId;
			int count = DB.executeUpdateEx(sqlDelete, get_TrxName());
			log.debug("AD_Process_Para_Trl deleted: " + count);
			
			final String sqlInsert = "INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language, " +
					" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
					" Name, Description, Help, IsTranslated) " +
					" SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
					" Updated, UpdatedBy, Name, Description, Help, IsTranslated " +
					" FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = "+adProcessParaId;
			count = DB.executeUpdateEx(sqlInsert, get_TrxName());
			log.debug("AD_Process_Para_Trl inserted: " + count);
		}
	}
	
}	//	MProcessPara
