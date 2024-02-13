/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_ValidCombination
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_ValidCombination extends org.compiere.model.PO implements I_C_ValidCombination, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1511410503L;

    /** Standard Constructor */
    public X_C_ValidCombination (Properties ctx, int C_ValidCombination_ID, String trxName)
    {
      super (ctx, C_ValidCombination_ID, trxName);
      /** if (C_ValidCombination_ID == 0)
        {
			setAccount_ID (0);
			setC_AcctSchema_ID (0);
			setC_ValidCombination_ID (0);
			setIsFullyQualified (false);
        } */
    }

    /** Load Constructor */
    public X_C_ValidCombination (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_ValidCombination[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_ElementValue getAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Account_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setAccount(org.compiere.model.I_C_ElementValue Account)
	{
		set_ValueFromPO(COLUMNNAME_Account_ID, org.compiere.model.I_C_ElementValue.class, Account);
	}

	/** Set Konto.
		@param Account_ID 
		Account used
	  */
	@Override
	public void setAccount_ID (int Account_ID)
	{
		if (Account_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Account_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Account_ID, Integer.valueOf(Account_ID));
	}

	/** Get Konto.
		@return Account used
	  */
	@Override
	public int getAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class, AD_OrgTrx);
	}

	/** Set Buchende Organisation.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	@Override
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Buchende Organisation.
		@return Performing or initiating organization
	  */
	@Override
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Alias.
		@param Alias 
		Defines an alternate method of indicating an account combination.
	  */
	@Override
	public void setAlias (java.lang.String Alias)
	{
		set_Value (COLUMNNAME_Alias, Alias);
	}

	/** Get Alias.
		@return Defines an alternate method of indicating an account combination.
	  */
	@Override
	public java.lang.String getAlias () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Alias);
	}

//	@Override
//	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
//	{
//		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
//	}
//
//	@Override
//	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
//	{
//		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
//	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class);
	}

	@Override
	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity)
	{
		set_ValueFromPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class, C_Activity);
	}

	/** Set Kostenstelle.
		@param C_Activity_ID 
		Kostenstelle
	  */
	@Override
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Activity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Kostenstelle.
		@return Kostenstelle
	  */
	@Override
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	/** Set Werbemassnahme.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	@Override
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Werbemassnahme.
		@return Marketing Campaign
	  */
	@Override
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocFrom() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocFrom(org.compiere.model.I_C_Location C_LocFrom)
	{
		set_ValueFromPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class, C_LocFrom);
	}

	/** Set Von Ort.
		@param C_LocFrom_ID 
		Location that inventory was moved from
	  */
	@Override
	public void setC_LocFrom_ID (int C_LocFrom_ID)
	{
		if (C_LocFrom_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_LocFrom_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_LocFrom_ID, Integer.valueOf(C_LocFrom_ID));
	}

	/** Get Von Ort.
		@return Location that inventory was moved from
	  */
	@Override
	public int getC_LocFrom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_LocFrom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocTo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocTo(org.compiere.model.I_C_Location C_LocTo)
	{
		set_ValueFromPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class, C_LocTo);
	}

	/** Set Nach Ort.
		@param C_LocTo_ID 
		Location that inventory was moved to
	  */
	@Override
	public void setC_LocTo_ID (int C_LocTo_ID)
	{
		if (C_LocTo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_LocTo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_LocTo_ID, Integer.valueOf(C_LocTo_ID));
	}

	/** Get Nach Ort.
		@return Location that inventory was moved to
	  */
	@Override
	public int getC_LocTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_LocTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class);
	}

	@Override
	public void setC_Project(org.compiere.model.I_C_Project C_Project)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class, C_Project);
	}

	/** Set Projekt.
		@param C_Project_ID 
		Financial Project
	  */
	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Projekt.
		@return Financial Project
	  */
	@Override
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_SalesRegion getC_SalesRegion() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class);
	}

	@Override
	public void setC_SalesRegion(org.compiere.model.I_C_SalesRegion C_SalesRegion)
	{
		set_ValueFromPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class, C_SalesRegion);
	}

	/** Set Vertriebsgebiet.
		@param C_SalesRegion_ID 
		Sales coverage region
	  */
	@Override
	public void setC_SalesRegion_ID (int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
	}

	/** Get Vertriebsgebiet.
		@return Sales coverage region
	  */
	@Override
	public int getC_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SalesRegion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_SubAcct getC_SubAcct() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_SubAcct_ID, org.compiere.model.I_C_SubAcct.class);
	}

	@Override
	public void setC_SubAcct(org.compiere.model.I_C_SubAcct C_SubAcct)
	{
		set_ValueFromPO(COLUMNNAME_C_SubAcct_ID, org.compiere.model.I_C_SubAcct.class, C_SubAcct);
	}

	/** Set Unter-Konto.
		@param C_SubAcct_ID 
		Sub account for Element Value
	  */
	@Override
	public void setC_SubAcct_ID (int C_SubAcct_ID)
	{
		if (C_SubAcct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SubAcct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SubAcct_ID, Integer.valueOf(C_SubAcct_ID));
	}

	/** Get Unter-Konto.
		@return Sub account for Element Value
	  */
	@Override
	public int getC_SubAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SubAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kombination.
		@param C_ValidCombination_ID 
		Valid Account Combination
	  */
	@Override
	public void setC_ValidCombination_ID (int C_ValidCombination_ID)
	{
		if (C_ValidCombination_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ValidCombination_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ValidCombination_ID, Integer.valueOf(C_ValidCombination_ID));
	}

	/** Get Kombination.
		@return Valid Account Combination
	  */
	@Override
	public int getC_ValidCombination_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ValidCombination_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kombination.
		@param Combination 
		Unique combination of account elements
	  */
	@Override
	public void setCombination (java.lang.String Combination)
	{
		set_ValueNoCheck (COLUMNNAME_Combination, Combination);
	}

	/** Get Kombination.
		@return Unique combination of account elements
	  */
	@Override
	public java.lang.String getCombination () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Combination);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Fully Qualified.
		@param IsFullyQualified 
		This account is fully qualified
	  */
	@Override
	public void setIsFullyQualified (boolean IsFullyQualified)
	{
		set_ValueNoCheck (COLUMNNAME_IsFullyQualified, Boolean.valueOf(IsFullyQualified));
	}

	/** Get Fully Qualified.
		@return This account is fully qualified
	  */
	@Override
	public boolean isFullyQualified () 
	{
		Object oo = get_Value(COLUMNNAME_IsFullyQualified);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	/** Set Nutzer 1.
		@param User1_ID 
		User defined list element #1
	  */
	@Override
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_User1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get Nutzer 1.
		@return User defined list element #1
	  */
	@Override
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	/** Set Nutzer 2.
		@param User2_ID 
		User defined list element #2
	  */
	@Override
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_User2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get Nutzer 2.
		@return User defined list element #2
	  */
	@Override
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Nutzer-Element 1.
		@param UserElement1_ID 
		User defined accounting Element
	  */
	@Override
	public void setUserElement1_ID (int UserElement1_ID)
	{
		if (UserElement1_ID < 1) 
			set_Value (COLUMNNAME_UserElement1_ID, null);
		else 
			set_Value (COLUMNNAME_UserElement1_ID, Integer.valueOf(UserElement1_ID));
	}

	/** Get Nutzer-Element 1.
		@return User defined accounting Element
	  */
	@Override
	public int getUserElement1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserElement1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Nutzer-Element 2.
		@param UserElement2_ID 
		User defined accounting Element
	  */
	@Override
	public void setUserElement2_ID (int UserElement2_ID)
	{
		if (UserElement2_ID < 1) 
			set_Value (COLUMNNAME_UserElement2_ID, null);
		else 
			set_Value (COLUMNNAME_UserElement2_ID, Integer.valueOf(UserElement2_ID));
	}

	/** Get Nutzer-Element 2.
		@return User defined accounting Element
	  */
	@Override
	public int getUserElement2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserElement2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public void setUserElementString1 (final @Nullable java.lang.String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public java.lang.String getUserElementString1()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final @Nullable java.lang.String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public java.lang.String getUserElementString2()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable java.lang.String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public java.lang.String getUserElementString3()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable java.lang.String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public java.lang.String getUserElementString4()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable java.lang.String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public java.lang.String getUserElementString5()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable java.lang.String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public java.lang.String getUserElementString6()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable java.lang.String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public java.lang.String getUserElementString7()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}

	@Override
	public void setUserElementDate1 (final @Nullable java.sql.Timestamp UserElementDate1)
	{
		set_Value (COLUMNNAME_UserElementDate1, UserElementDate1);
	}

	@Override
	public java.sql.Timestamp getUserElementDate1()
	{
		return get_ValueAsTimestamp(COLUMNNAME_UserElementDate1);
	}

	@Override
	public void setUserElementDate2 (final @Nullable java.sql.Timestamp UserElementDate2)
	{
		set_Value (COLUMNNAME_UserElementDate2, UserElementDate2);
	}

	@Override
	public java.sql.Timestamp getUserElementDate2()
	{
		return get_ValueAsTimestamp(COLUMNNAME_UserElementDate2);
	}
}