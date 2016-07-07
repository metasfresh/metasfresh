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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.document.IDocTypeBL;

/**
 *	Document Type Model
 *	
 *  @author Jorg Janke
 *  @author Karsten Thiemann FR [ 1782412 ]
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>BF [ 2476824 ] MDocType.getOfDocBaseType should return ONLY active records
 *  		<li>BF [ -       ] MDocType.getOfClient should return ONLY active records.
 *  							See https://sourceforge.net/forum/message.php?msg_id=6499893
 *  @version $Id: MDocType.java,v 1.3 2006/07/30 00:54:54 jjanke Exp $
 */
public class MDocType extends X_C_DocType
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1406832071359080959L;

	/**
	 * Return the first Doc Type for this BaseType
	 * @param DocBaseType
	 * @return
	 */
	static public int getDocType(String DocBaseType)
	{
		MDocType[] doc = MDocType.getOfDocBaseType(Env.getCtx(), DocBaseType);
		return doc.length > 0 ? doc[0].get_ID() : 0;
	}
	
	/**
	 * 	Get Client Document Type with DocBaseType
	 *	@param ctx context
	 *	@param DocBaseType base document type
	 *	@return array of doc types
	 */
	static public MDocType[] getOfDocBaseType (Properties ctx, String DocBaseType)
	{
		String whereClause  = "AD_Client_ID=? AND DocBaseType=?";
		List<MDocType> list = new Query(ctx, Table_Name, whereClause, null)
									.setParameters(new Object[]{Env.getAD_Client_ID(ctx), DocBaseType})
									.setOnlyActiveRecords(true)
									.setOrderBy("IsDefault DESC, C_DocType_ID")
									.list();
		return list.toArray(new MDocType[list.size()]);
	}	//	getOfDocBaseType
	
	/**
	 * 	Get Client Document Types
	 *	@param ctx context
	 *	@return array of doc types
	 */
	static public MDocType[] getOfClient (Properties ctx)
	{
		String whereClause  = "AD_Client_ID=?";
		List<MDocType> list = new Query(ctx, Table_Name, whereClause, null)
									.setParameters(new Object[]{Env.getAD_Client_ID(ctx)})
									.setOnlyActiveRecords(true)
									.list();
		return list.toArray(new MDocType[list.size()]);
	}	//	getOfClient
	
	/**
	 * 	Get Document Type (cached)
	 *	@param ctx context
	 *	@param C_DocType_ID id
	 *	@return document type
	 */
	public static MDocType get (final Properties ctx, final int C_DocType_ID)
	{
		if(C_DocType_ID <= 0)
		{
			return null;
		}
		
		// NOTE: we assume the C_DocType is cached on table level (i.e. see org.adempiere.model.validator.AdempiereBaseValidator.setupCaching(IModelCacheService))
		final I_C_DocType docType = InterfaceWrapperHelper.create(ctx, C_DocType_ID, I_C_DocType.class, ITrx.TRXNAME_None);
		return LegacyAdapters.convertToPO(docType);
	} 	//	get
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_DocType_ID id
	 *	@param trxName transaction
	 */
	public MDocType(Properties ctx, int C_DocType_ID, String trxName)
	{
		super(ctx, C_DocType_ID, trxName);
		if (C_DocType_ID == 0)
		{
		//	setName (null);
		//	setPrintName (null);
		//	setDocBaseType (null);
		//	setGL_Category_ID (0);
			setDocumentCopies (0);
			setHasCharges (false);
			setIsDefault (false);
			setIsDocNoControlled (false);
			setIsSOTrx (false);
			setIsPickQAConfirm(false);
			setIsShipConfirm(false);
			setIsSplitWhenDifference(false);
			//
			setIsCreateCounter(true);
			setIsDefaultCounterDoc(false);
			setIsIndexed(true);
		}
	}	//	MDocType

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MDocType(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDocType

	/**
	 * 	New Constructor
	 *	@param ctx context
	 *	@param DocBaseType document base type
	 *	@param Name name
	 *	@param trxName transaction
	 */
	public MDocType (Properties ctx, String DocBaseType, String Name, String trxName)
	{
		this (ctx, 0, trxName);
		setAD_Org_ID(0);
		setDocBaseType (DocBaseType);
		setName (Name);
		setPrintName (Name);
		setGL_Category_ID ();
	}	//	MDocType

	/**
	 * 	Set Default GL Category
	 */
	public void setGL_Category_ID()
	{
		final String sql = "SELECT GL_Category_ID FROM GL_Category"
						+" WHERE AD_Client_ID=?"
						+" ORDER BY IsDefault DESC, GL_Category_ID";
		int GL_Category_ID = DB.getSQLValue(get_TrxName(), sql, getAD_Client_ID());
		setGL_Category_ID(GL_Category_ID);
	}	//	setGL_Category_ID

	
	/**
	 * 	Set SOTrx based on document base type
	 */
	public void setIsSOTrx ()
	{
		boolean isSOTrx = DOCBASETYPE_SalesOrder.equals(getDocBaseType()) 
			|| DOCBASETYPE_MaterialDelivery.equals(getDocBaseType())
			|| getDocBaseType().startsWith("AR");
		super.setIsSOTrx (isSOTrx);
	}	//	setIsSOTrx
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MDocType[");
		sb.append(get_ID()).append("-").append(getName())
			.append(",DocNoSequence_ID=").append(getDocNoSequence_ID())
			.append("]");
		return sb.toString();
	}	//	toString

	/**
	 * Is this a Quotation (Binding)
	 *
	 * @return true if Quotation
	 * @deprecated please use {@link IDocTypeBL#isQuotation(I_C_DocType)} instead.
	 */
	@Deprecated
	public boolean isQuotation()
	{
		return Services.get(IDocTypeBL.class).isQuotation(this);
	}	// isQuotation

	/**
	 * Is this a Proposal (Not binding)
	 *
	 * @return true if proposal
	 * @deprecated please use {@link IDocTypeBL#isProposal(I_C_DocType)} instead.
	 */
	@Deprecated
	public boolean isProposal()
	{
		return Services.get(IDocTypeBL.class).isProposal(this);
	}	// isProposal

	/**
	 * Is this a Proposal or Quotation
	 *
	 * @return true if proposal or quotation
	 * @deprecated please use {@link IDocTypeBL#isOffer(I_C_DocType)} instead.
	 */
	@Deprecated
	public boolean isOffer()
	{
		return Services.get(IDocTypeBL.class).isOffer(this);
	}	// isOffer

	
	/**
	 * 	Get Print Name
	 * 	@param AD_Language language
	 *	@return print Name if available translated
	 */
	public String getPrintName (String AD_Language)
	{
		if (AD_Language == null || AD_Language.length() == 0)
			return super.getPrintName();
		return get_Translation (COLUMNNAME_PrintName, AD_Language);
	}	//	getPrintName
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		/*if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);*/
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord && success)
		{
			//	Add doctype/docaction access to all roles of client
			String sqlDocAction = "INSERT INTO AD_Document_Action_Access "
				+ "(AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) " 
				+ "(SELECT "
				+ getAD_Client_ID() + ",0,'Y', now()," 
				+ getUpdatedBy() + ", now()," + getUpdatedBy() 
				+ ", doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID " 
				+ "FROM AD_Client client " 
				+ "INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) "
				+ "INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) "
				+ "INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) "
				+ "WHERE client.AD_Client_ID=" + getAD_Client_ID() 
				+ " AND doctype.C_DocType_ID=" + get_ID()
				+ " AND rol.IsManual='N'"
				+ ")";
			
			int docact = DB.executeUpdate(sqlDocAction, get_TrxName());
			log.debug("AD_Document_Action_Access=" + docact);
		}
		return success;
	}	//	afterSave
	
	/**
	 * 	Executed after Delete operation.
	 * 	@param success true if record deleted
	 *	@return true if delete is a success
	 */
	@Override
	protected boolean afterDelete (boolean success)
	{
		if(success) {
			//delete access records
			int docactDel = DB.executeUpdate("DELETE FROM AD_Document_Action_Access WHERE C_DocType_ID=" + get_IDOld(), get_TrxName());
			log.debug("Deleting AD_Document_Action_Access=" + docactDel + " for C_DocType_ID: " + get_IDOld());
		}
		return success;
	} 	//	afterDelete
	
}	//	MDocType
