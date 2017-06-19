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

import org.adempiere.acct.api.IAcctSchemaBL;
import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.DB;

import de.metas.i18n.IMsgBL;

/**
 *  Account Schema Element Object
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: MAcctSchemaElement.java,v 1.4 2006/08/10 01:00:44 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				<li>BF [ 1795817 ] Acct Schema Elements "Account" and "Org" should be mandatory
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 *    			<li>RF [ 2214883 ] Remove SQL code and Replace for Query http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 */
public final class MAcctSchemaElement extends X_C_AcctSchema_Element
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4215184252533527719L;


	/**
	 * Factory: Return ArrayList of Account Schema Elements
	 * @param as Accounting Schema
	 * @return ArrayList with Elements
	 * @deprecated please use {@link IAcctSchemaDAO#retrieveSchemaElements(I_C_AcctSchema)}.
	 */
	@Deprecated
	public static MAcctSchemaElement[] getAcctSchemaElements (I_C_AcctSchema as)
	{
		return LegacyAdapters.convertToPOArray(Services.get(IAcctSchemaDAO.class).retrieveSchemaElements(as), MAcctSchemaElement.class);
	}   //  getAcctSchemaElements

	/**
	 * Get Column Name of ELEMENTTYPE 
	 * @param elementType ELEMENTTYPE 
	 * @return column name or "" if not found
	 * @deprecated please use {@link IAcctSchemaBL#getColumnName(String)}.
	 */
	@Deprecated
	public static String getColumnName(String elementType)
	{
		return Services.get(IAcctSchemaBL.class).getColumnName(elementType);
	}   //  getColumnName

	/**
	 * Get Value Query for ELEMENTTYPE Type
	 * @param elementType ELEMENTTYPE type
	 * @return query "SELECT Value,Name FROM Table WHERE ID=" or "" if not found
	 */
	public static String getValueQuery (String elementType)
	{
		if (elementType.equals(ELEMENTTYPE_Organization))
			return "SELECT Value,Name FROM AD_Org WHERE AD_Org_ID=";
		else if (elementType.equals(ELEMENTTYPE_Account))
			return "SELECT Value,Name FROM C_ElementValue WHERE C_ElementValue_ID=";
		else if (elementType.equals(ELEMENTTYPE_SubAccount))
			return "SELECT Value,Name FROM C_SubAccount WHERE C_SubAccount_ID=";
		else if (elementType.equals(ELEMENTTYPE_BPartner))
			return "SELECT Value,Name FROM C_BPartner WHERE C_BPartner_ID=";
		else if (elementType.equals(ELEMENTTYPE_Product))
			return "SELECT Value,Name FROM M_Product WHERE M_Product_ID=";
		else if (elementType.equals(ELEMENTTYPE_Activity))
			return "SELECT Value,Name FROM C_Activity WHERE C_Activity_ID=";
		else if (elementType.equals(ELEMENTTYPE_LocationFrom))
			return "SELECT City,Address1 FROM C_Location WHERE C_Location_ID=";
		else if (elementType.equals(ELEMENTTYPE_LocationTo))
			return "SELECT City,Address1 FROM C_Location WHERE C_Location_ID=";
		else if (elementType.equals(ELEMENTTYPE_Campaign))
			return "SELECT Value,Name FROM C_Campaign WHERE C_Campaign_ID=";
		else if (elementType.equals(ELEMENTTYPE_OrgTrx))
			return "SELECT Value,Name FROM AD_Org WHERE AD_Org_ID=";
		else if (elementType.equals(ELEMENTTYPE_Project))
			return "SELECT Value,Name FROM C_Project WHERE C_Project_ID=";
		else if (elementType.equals(ELEMENTTYPE_SalesRegion))
			return "SELECT Value,Name FROM C_SalesRegion WHERE C_SalesRegion_ID";
		else if (elementType.equals(ELEMENTTYPE_UserList1))
			return "SELECT Value,Name FROM C_ElementValue WHERE C_ElementValue_ID=";
		else if (elementType.equals(ELEMENTTYPE_UserList2))
			return "SELECT Value,Name FROM C_ElementValue WHERE C_ElementValue_ID=";
		//
		else if (elementType.equals(ELEMENTTYPE_UserElement1))
			return null;
		else if (elementType.equals(ELEMENTTYPE_UserElement2))
			return null;
		//
		return "";
	}   //  getColumnName


	/**	Cache						*/
	private static CCache<Integer,MAcctSchemaElement[]> s_cache = new CCache<Integer,MAcctSchemaElement[]>("C_AcctSchema_Element", 10);
	
	
	/*************************************************************************
	 * Standard Constructor
	 * @param ctx context
	 * @param C_AcctSchema_Element_ID id
	 * @param trxName transaction
	 */
	public MAcctSchemaElement (Properties ctx, int C_AcctSchema_Element_ID, String trxName)
	{
		super (ctx, C_AcctSchema_Element_ID, trxName);
		if (C_AcctSchema_Element_ID == 0)
		{
		//	setC_AcctSchema_Element_ID (0);
		//	setC_AcctSchema_ID (0);
		//	setC_Element_ID (0);
		//	setElementType (null);
			setIsBalanced (false);
			setIsMandatory (false);
		//	setName (null);
		//	setOrg_ID (0);
		//	setSeqNo (0);
		}
	}	//	MAcctSchemaElement

	/**
	 * Load Constructor
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */	
	public MAcctSchemaElement (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAcctSchemaElement

	/**
	 * Parent Constructor
	 * @param as accounting schema
	 */
	public MAcctSchemaElement (MAcctSchema as)
	{
		this (as.getCtx(), 0, as.get_TrxName());
		setClientOrg(as);
		setC_AcctSchema_ID (as.getC_AcctSchema_ID());
		
		//	setC_Element_ID (0);
		//	setElementType (null);
		//	setName (null);
		//	setSeqNo (0);
		
	}	//	MAcctSchemaElement

	/**
	 * Set Organization Type
	 * @param SeqNo sequence
	 * @param Name name 
	 * @param Org_ID id
	 */
	public void setTypeOrg (int SeqNo, String Name, int Org_ID)
	{
		setElementType (ELEMENTTYPE_Organization);
		setSeqNo(SeqNo);
		setName (Name);
		setOrg_ID(Org_ID);
	}	//	setTypeOrg

	/**
	 * Set Type Account
	 * @param SeqNo squence
	 * @param Name name
	 * @param C_Element_ID element
	 * @param C_ElementValue_ID element value
	 */
	public void setTypeAccount (int SeqNo, String Name, int C_Element_ID, int C_ElementValue_ID)
	{
		setElementType (ELEMENTTYPE_Account);
		setSeqNo(SeqNo);
		setName (Name);
		setC_Element_ID (C_Element_ID);
		setC_ElementValue_ID(C_ElementValue_ID);
	}	//	setTypeAccount

	/**
	 * Set Type BPartner
	 * @param SeqNo sequence
	 * @param Name name
	 * @param C_BPartner_ID id
	 */
	public void setTypeBPartner (int SeqNo, String Name, int C_BPartner_ID)
	{
		setElementType (ELEMENTTYPE_BPartner);
		setSeqNo(SeqNo);
		setName (Name);
		setC_BPartner_ID(C_BPartner_ID);
	}	//	setTypeBPartner

	/**
	 * Set Type Product
	 * @param SeqNo sequence
	 * @param Name name
	 * @param M_Product_ID id
	 */
	public void setTypeProduct (int SeqNo, String Name, int M_Product_ID)
	{
		setElementType (ELEMENTTYPE_Product);
		setSeqNo(SeqNo);
		setName (Name);
		setM_Product_ID(M_Product_ID);
	}	//	setTypeProduct
	
	/**
	 * Set Type Project
	 * @param SeqNo sequence
	 * @param Name name
	 * @param C_Project_ID id
	 */
	public void setTypeProject (int SeqNo, String Name, int C_Project_ID)
	{
		setElementType (ELEMENTTYPE_Project);
		setSeqNo(SeqNo);
		setName (Name);
		setC_Project_ID(C_Project_ID);
	}	//	setTypeProject

	/**
	 * Is Element Type
	 * @param elementType type
	 * @return ELEMENTTYPE type
	 */
	public boolean isElementType (String elementType)
	{
		if (elementType == null)
			return false;
		return elementType.equals(getElementType());
	}   //  isElementType

	/**
	 * Get Default element value
	 * @return default
	 * @deprecated please use {@link IAcctSchemaBL#getDefaultValue(I_C_AcctSchema_Element)}.
	 */
	@Deprecated
	public int getDefaultValue()
	{
		return Services.get(IAcctSchemaBL.class).getDefaultValue(this);
	}	//	getDefault


	/**
	 * Get Acct Fact ColumnName
	 * @return column name
	 * @deprecated please use {@link IAcctSchemaBL#getColumnName(I_C_AcctSchema_Element)}.
	 */
	@Deprecated
	public String getColumnName()
	{
		return Services.get(IAcctSchemaBL.class).getColumnName(this);
	}	//	getColumnName

	/**
	 * Get Display ColumnName
	 * @return column name
	 * @deprecated please use {@link IAcctSchemaBL#getDisplayColumnName(I_C_AcctSchema_Element)}
	 */
	@Deprecated
	public String getDisplayColumnName()
	{
		return Services.get(IAcctSchemaBL.class).getDisplayColumnName(this);
	}	//	getDisplayColumnName

	
	/**
	 * String representation
	 * @return info
	 */
	@Override
	public String toString()
	{
		return "AcctSchemaElement[" + get_ID() 
			+ "-" + getName() 
			+ "(" + getElementType() + ")=" + getDefaultValue()  
			+ ",Pos=" + getSeqNo() + "]";
	}   //  toString

	
	
	/**
	 * Before Save
	 * @param newRecord new
	 * @return true if it can be saved
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);
		final String elementType = getElementType();
		if (isMandatory() &&
			(ELEMENTTYPE_UserList1.equals(elementType) || ELEMENTTYPE_UserList2.equals(elementType)
			|| ELEMENTTYPE_UserElement1.equals(elementType) || ELEMENTTYPE_UserElement2.equals(elementType)))
			setIsMandatory(false);
		// Acct Schema Elements "Account" and "Org" should be mandatory - teo_sarca BF [ 1795817 ]
		if (ELEMENTTYPE_Account.equals(elementType) || ELEMENTTYPE_Organization.equals(elementType)) {
			if (!isMandatory())
				setIsMandatory(true);
			if (!isActive())
				setIsActive(true);
		}
		//
		else if (isMandatory())
		{
			String errorField = null;
			if (ELEMENTTYPE_Account.equals(elementType) && getC_ElementValue_ID() == 0)
				errorField = COLUMNNAME_C_ElementValue_ID;
			else if (ELEMENTTYPE_Activity.equals(elementType) && getC_Activity_ID() == 0)
				errorField = COLUMNNAME_C_Activity_ID;
			else if (ELEMENTTYPE_BPartner.equals(elementType) && getC_BPartner_ID() == 0)
				errorField = COLUMNNAME_C_BPartner_ID;
			else if (ELEMENTTYPE_Campaign.equals(elementType) && getC_Campaign_ID() == 0)
				errorField = COLUMNNAME_C_Campaign_ID;
			else if (ELEMENTTYPE_LocationFrom.equals(elementType) && getC_Location_ID() == 0)
				errorField = COLUMNNAME_C_Location_ID;
			else if (ELEMENTTYPE_LocationTo.equals(elementType) && getC_Location_ID() == 0)
				errorField = COLUMNNAME_C_Location_ID;
			else if (ELEMENTTYPE_Organization.equals(elementType) && getOrg_ID() == 0)
				errorField = COLUMNNAME_Org_ID;
			else if (ELEMENTTYPE_OrgTrx.equals(elementType) && getOrg_ID() == 0)
				errorField = COLUMNNAME_Org_ID;
			else if (ELEMENTTYPE_Product.equals(elementType) && getM_Product_ID() == 0)
				errorField = COLUMNNAME_M_Product_ID;
			else if (ELEMENTTYPE_Project.equals(elementType) && getC_Project_ID() == 0)
				errorField = COLUMNNAME_C_Project_ID;
			else if (ELEMENTTYPE_SalesRegion.equals(elementType) && getC_SalesRegion_ID() == 0)
				errorField = COLUMNNAME_C_SalesRegion_ID;
			if (errorField != null)
			{
				throw new AdempiereException(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@IsMandatory@: @" + errorField + "@"));
			}
		}
		//
		if (getAD_Column_ID() <= 0
			&& (ELEMENTTYPE_UserElement1.equals(elementType) || ELEMENTTYPE_UserElement2.equals(elementType)))
		{
			throw new AdempiereException(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@IsMandatory@: @AD_Column_ID@"));
		}
		return true;
	}	//	beforeSave
	
	/**
	 * After Save
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		//	Default Value
		if (isMandatory() && is_ValueChanged(COLUMNNAME_IsMandatory))
		{
			if (ELEMENTTYPE_Activity.equals(getElementType()))
				updateData (COLUMNNAME_C_Activity_ID, getC_Activity_ID());
			else if (ELEMENTTYPE_BPartner.equals(getElementType()))
				updateData (COLUMNNAME_C_BPartner_ID, getC_BPartner_ID());
			else if (ELEMENTTYPE_Product.equals(getElementType()))
				updateData (COLUMNNAME_M_Product_ID, getM_Product_ID());
			else if (ELEMENTTYPE_Project.equals(getElementType()))
				updateData (COLUMNNAME_C_Project_ID, getC_Project_ID());
		}

		//	Clear Cache
		s_cache.clear();
		
		//	Resequence
		if (newRecord || is_ValueChanged(COLUMNNAME_SeqNo))
			MAccount.updateValueDescription(getCtx(), 
				"AD_Client_ID=" + getAD_Client_ID(), get_TrxName());

		return success;
	}	//	afterSave
	
	/**
	 * Update ValidCombination and Fact with mandatory value
	 * @param element element
	 * @param id new default
	 */
	private void updateData (String element, int id)
	{
		MAccount.updateValueDescription(getCtx(),  element + "=" + id, get_TrxName());
		//
		String sql = "UPDATE C_ValidCombination SET " + element + "=" + id
			+ " WHERE " + element + " IS NULL AND AD_Client_ID=" + getAD_Client_ID();
		int noC = DB.executeUpdate(sql, get_TrxName());
		//
		sql = "UPDATE Fact_Acct SET " + element + "=" + id
			+ " WHERE " + element + " IS NULL AND C_AcctSchema_ID=" + getC_AcctSchema_ID();
		int noF = DB.executeUpdate(sql, get_TrxName());
		//
		log.debug("ValidCombination=" + noC + ", Fact=" + noF);
	}	//	updateData
	
	@Override
	protected boolean beforeDelete ()
	{
		final String et = getElementType();
		// Acct Schema Elements "Account" and "Org" should be mandatory - teo_sarca BF [ 1795817 ] 
		if (ELEMENTTYPE_Account.equals(et) || ELEMENTTYPE_Organization.equals(et)) 
		{
			throw new AdempiereException(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@DeleteError@ @IsMandatory@"));
		}
		return true;
	}
	
	/**
	 * After Delete
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterDelete (boolean success)
	{
		//	Update Account Info
		MAccount.updateValueDescription(getCtx(), "AD_Client_ID=" + getAD_Client_ID(), get_TrxName());
		//
		s_cache.clear();
		return success;
	}	//	afterDelete
	
}   //  AcctSchemaElement
