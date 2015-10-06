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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for PA_ReportColumn
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_PA_ReportColumn extends PO implements I_PA_ReportColumn, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_PA_ReportColumn (Properties ctx, int PA_ReportColumn_ID, String trxName)
    {
      super (ctx, PA_ReportColumn_ID, trxName);
      /** if (PA_ReportColumn_ID == 0)
        {
			setColumnType (null);
// R
			setIsIncludeNullsActivity (false);
// N
			setIsIncludeNullsBPartner (false);
// N
			setIsIncludeNullsCampaign (false);
// N
			setIsIncludeNullsElementValue (false);
// N
			setIsIncludeNullsLocation (false);
// N
			setIsIncludeNullsOrg (false);
// N
			setIsIncludeNullsOrgTrx (false);
// N
			setIsIncludeNullsProduct (false);
// N
			setIsIncludeNullsProject (false);
// N
			setIsIncludeNullsSalesRegion (false);
// N
			setIsIncludeNullsUserElement1 (false);
// N
			setIsIncludeNullsUserElement2 (false);
// N
			setIsPrinted (true);
// Y
			setName (null);
			setPA_ReportColumn_ID (0);
			setPA_ReportColumnSet_ID (0);
			setPostingType (null);
// A
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM PA_ReportColumn WHERE PA_ReportColumnSet_ID=@PA_ReportColumnSet_ID@
        } */
    }

    /** Load Constructor */
    public X_PA_ReportColumn (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_PA_ReportColumn[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Activity getC_Activity() throws RuntimeException
    {
		return (I_C_Activity)MTable.get(getCtx(), I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CalculationType AD_Reference_ID=236 */
	public static final int CALCULATIONTYPE_AD_Reference_ID=236;
	/** Add (Op1+Op2) = A */
	public static final String CALCULATIONTYPE_AddOp1PlusOp2 = "A";
	/** Subtract (Op1-Op2) = S */
	public static final String CALCULATIONTYPE_SubtractOp1_Op2 = "S";
	/** Percentage (Op1 of Op2) = P */
	public static final String CALCULATIONTYPE_PercentageOp1OfOp2 = "P";
	/** Add Range (Op1 to Op2) = R */
	public static final String CALCULATIONTYPE_AddRangeOp1ToOp2 = "R";
	/** Set Calculation.
		@param CalculationType Calculation	  */
	public void setCalculationType (String CalculationType)
	{

		set_Value (COLUMNNAME_CalculationType, CalculationType);
	}

	/** Get Calculation.
		@return Calculation	  */
	public String getCalculationType () 
	{
		return (String)get_Value(COLUMNNAME_CalculationType);
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Campaign getC_Campaign() throws RuntimeException
    {
		return (I_C_Campaign)MTable.get(getCtx(), I_C_Campaign.Table_Name)
			.getPO(getC_Campaign_ID(), get_TrxName());	}

	/** Set Campaign.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Campaign.
		@return Marketing Campaign
	  */
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (I_C_ElementValue)MTable.get(getCtx(), I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ColumnType AD_Reference_ID=237 */
	public static final int COLUMNTYPE_AD_Reference_ID=237;
	/** Relative Period = R */
	public static final String COLUMNTYPE_RelativePeriod = "R";
	/** Calculation = C */
	public static final String COLUMNTYPE_Calculation = "C";
	/** Segment Value = S */
	public static final String COLUMNTYPE_SegmentValue = "S";
	/** Set Column Type.
		@param ColumnType Column Type	  */
	public void setColumnType (String ColumnType)
	{

		set_Value (COLUMNNAME_ColumnType, ColumnType);
	}

	/** Get Column Type.
		@return Column Type	  */
	public String getColumnType () 
	{
		return (String)get_Value(COLUMNNAME_ColumnType);
	}

	public I_C_Project getC_Project() throws RuntimeException
    {
		return (I_C_Project)MTable.get(getCtx(), I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_SalesRegion getC_SalesRegion() throws RuntimeException
    {
		return (I_C_SalesRegion)MTable.get(getCtx(), I_C_SalesRegion.Table_Name)
			.getPO(getC_SalesRegion_ID(), get_TrxName());	}

	/** Set Sales Region.
		@param C_SalesRegion_ID 
		Sales coverage region
	  */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
	}

	/** Get Sales Region.
		@return Sales coverage region
	  */
	public int getC_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SalesRegion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CurrencyType AD_Reference_ID=238 */
	public static final int CURRENCYTYPE_AD_Reference_ID=238;
	/** Source Currency = S */
	public static final String CURRENCYTYPE_SourceCurrency = "S";
	/** Accounting Currency = A */
	public static final String CURRENCYTYPE_AccountingCurrency = "A";
	/** Set Currency Type.
		@param CurrencyType Currency Type	  */
	public void setCurrencyType (String CurrencyType)
	{

		set_Value (COLUMNNAME_CurrencyType, CurrencyType);
	}

	/** Get Currency Type.
		@return Currency Type	  */
	public String getCurrencyType () 
	{
		return (String)get_Value(COLUMNNAME_CurrencyType);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** ElementType AD_Reference_ID=53280 */
	public static final int ELEMENTTYPE_AD_Reference_ID=53280;
	/** Account = AC */
	public static final String ELEMENTTYPE_Account = "AC";
	/** Activity = AY */
	public static final String ELEMENTTYPE_Activity = "AY";
	/** BPartner = BP */
	public static final String ELEMENTTYPE_BPartner = "BP";
	/** Location From = LF */
	public static final String ELEMENTTYPE_LocationFrom = "LF";
	/** Location To = LT */
	public static final String ELEMENTTYPE_LocationTo = "LT";
	/** Campaign = MC */
	public static final String ELEMENTTYPE_Campaign = "MC";
	/** Organization = OO */
	public static final String ELEMENTTYPE_Organization = "OO";
	/** Org Trx = OT */
	public static final String ELEMENTTYPE_OrgTrx = "OT";
	/** Project = PJ */
	public static final String ELEMENTTYPE_Project = "PJ";
	/** Product = PR */
	public static final String ELEMENTTYPE_Product = "PR";
	/** Sub Account = SA */
	public static final String ELEMENTTYPE_SubAccount = "SA";
	/** Sales Region = SR */
	public static final String ELEMENTTYPE_SalesRegion = "SR";
	/** User List 1 = U1 */
	public static final String ELEMENTTYPE_UserList1 = "U1";
	/** User List 2 = U2 */
	public static final String ELEMENTTYPE_UserList2 = "U2";
	/** User Element 1 = X1 */
	public static final String ELEMENTTYPE_UserElement1 = "X1";
	/** User Element 2 = X2 */
	public static final String ELEMENTTYPE_UserElement2 = "X2";
	/** Combination = CO */
	public static final String ELEMENTTYPE_Combination = "CO";
	/** Set Type.
		@param ElementType 
		Element Type (account or user defined)
	  */
	public void setElementType (String ElementType)
	{

		set_Value (COLUMNNAME_ElementType, ElementType);
	}

	/** Get Type.
		@return Element Type (account or user defined)
	  */
	public String getElementType () 
	{
		return (String)get_Value(COLUMNNAME_ElementType);
	}

	/** Factor AD_Reference_ID=53285 */
	public static final int FACTOR_AD_Reference_ID=53285;
	/** Thousand = k */
	public static final String FACTOR_Thousand = "k";
	/** Million = M */
	public static final String FACTOR_Million = "M";
	/** Set Factor.
		@param Factor 
		Scaling factor.
	  */
	public void setFactor (String Factor)
	{

		set_Value (COLUMNNAME_Factor, Factor);
	}

	/** Get Factor.
		@return Scaling factor.
	  */
	public String getFactor () 
	{
		return (String)get_Value(COLUMNNAME_Factor);
	}

	/** Set Format Pattern.
		@param FormatPattern 
		The pattern used to format a number or date.
	  */
	public void setFormatPattern (String FormatPattern)
	{
		set_Value (COLUMNNAME_FormatPattern, FormatPattern);
	}

	/** Get Format Pattern.
		@return The pattern used to format a number or date.
	  */
	public String getFormatPattern () 
	{
		return (String)get_Value(COLUMNNAME_FormatPattern);
	}

	public I_GL_Budget getGL_Budget() throws RuntimeException
    {
		return (I_GL_Budget)MTable.get(getCtx(), I_GL_Budget.Table_Name)
			.getPO(getGL_Budget_ID(), get_TrxName());	}

	/** Set Budget.
		@param GL_Budget_ID 
		General Ledger Budget
	  */
	public void setGL_Budget_ID (int GL_Budget_ID)
	{
		if (GL_Budget_ID < 1) 
			set_Value (COLUMNNAME_GL_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Budget_ID, Integer.valueOf(GL_Budget_ID));
	}

	/** Get Budget.
		@return General Ledger Budget
	  */
	public int getGL_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Adhoc Conversion.
		@param IsAdhocConversion 
		Perform conversion for all amounts to currency
	  */
	public void setIsAdhocConversion (boolean IsAdhocConversion)
	{
		set_Value (COLUMNNAME_IsAdhocConversion, Boolean.valueOf(IsAdhocConversion));
	}

	/** Get Adhoc Conversion.
		@return Perform conversion for all amounts to currency
	  */
	public boolean isAdhocConversion () 
	{
		Object oo = get_Value(COLUMNNAME_IsAdhocConversion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Activity.
		@param IsIncludeNullsActivity 
		Include nulls in the selection of the activity
	  */
	public void setIsIncludeNullsActivity (boolean IsIncludeNullsActivity)
	{
		set_Value (COLUMNNAME_IsIncludeNullsActivity, Boolean.valueOf(IsIncludeNullsActivity));
	}

	/** Get Include Nulls in Activity.
		@return Include nulls in the selection of the activity
	  */
	public boolean isIncludeNullsActivity () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsActivity);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in BPartner.
		@param IsIncludeNullsBPartner 
		Include nulls in the selection of the business partner
	  */
	public void setIsIncludeNullsBPartner (boolean IsIncludeNullsBPartner)
	{
		set_Value (COLUMNNAME_IsIncludeNullsBPartner, Boolean.valueOf(IsIncludeNullsBPartner));
	}

	/** Get Include Nulls in BPartner.
		@return Include nulls in the selection of the business partner
	  */
	public boolean isIncludeNullsBPartner () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsBPartner);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Campaign.
		@param IsIncludeNullsCampaign 
		Include nulls in the selection of the campaign
	  */
	public void setIsIncludeNullsCampaign (boolean IsIncludeNullsCampaign)
	{
		set_Value (COLUMNNAME_IsIncludeNullsCampaign, Boolean.valueOf(IsIncludeNullsCampaign));
	}

	/** Get Include Nulls in Campaign.
		@return Include nulls in the selection of the campaign
	  */
	public boolean isIncludeNullsCampaign () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsCampaign);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Account.
		@param IsIncludeNullsElementValue 
		Include nulls in the selection of the account
	  */
	public void setIsIncludeNullsElementValue (boolean IsIncludeNullsElementValue)
	{
		set_Value (COLUMNNAME_IsIncludeNullsElementValue, Boolean.valueOf(IsIncludeNullsElementValue));
	}

	/** Get Include Nulls in Account.
		@return Include nulls in the selection of the account
	  */
	public boolean isIncludeNullsElementValue () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsElementValue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Location.
		@param IsIncludeNullsLocation 
		Include nulls in the selection of the location
	  */
	public void setIsIncludeNullsLocation (boolean IsIncludeNullsLocation)
	{
		set_Value (COLUMNNAME_IsIncludeNullsLocation, Boolean.valueOf(IsIncludeNullsLocation));
	}

	/** Get Include Nulls in Location.
		@return Include nulls in the selection of the location
	  */
	public boolean isIncludeNullsLocation () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsLocation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Org.
		@param IsIncludeNullsOrg 
		Include nulls in the selection of the organization
	  */
	public void setIsIncludeNullsOrg (boolean IsIncludeNullsOrg)
	{
		set_Value (COLUMNNAME_IsIncludeNullsOrg, Boolean.valueOf(IsIncludeNullsOrg));
	}

	/** Get Include Nulls in Org.
		@return Include nulls in the selection of the organization
	  */
	public boolean isIncludeNullsOrg () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsOrg);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Org Trx.
		@param IsIncludeNullsOrgTrx 
		Include nulls in the selection of the organization transaction
	  */
	public void setIsIncludeNullsOrgTrx (boolean IsIncludeNullsOrgTrx)
	{
		set_Value (COLUMNNAME_IsIncludeNullsOrgTrx, Boolean.valueOf(IsIncludeNullsOrgTrx));
	}

	/** Get Include Nulls in Org Trx.
		@return Include nulls in the selection of the organization transaction
	  */
	public boolean isIncludeNullsOrgTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsOrgTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Product.
		@param IsIncludeNullsProduct 
		Include nulls in the selection of the product
	  */
	public void setIsIncludeNullsProduct (boolean IsIncludeNullsProduct)
	{
		set_Value (COLUMNNAME_IsIncludeNullsProduct, Boolean.valueOf(IsIncludeNullsProduct));
	}

	/** Get Include Nulls in Product.
		@return Include nulls in the selection of the product
	  */
	public boolean isIncludeNullsProduct () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Project.
		@param IsIncludeNullsProject 
		Include nulls in the selection of the project
	  */
	public void setIsIncludeNullsProject (boolean IsIncludeNullsProject)
	{
		set_Value (COLUMNNAME_IsIncludeNullsProject, Boolean.valueOf(IsIncludeNullsProject));
	}

	/** Get Include Nulls in Project.
		@return Include nulls in the selection of the project
	  */
	public boolean isIncludeNullsProject () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsProject);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in Sales Region.
		@param IsIncludeNullsSalesRegion 
		Include nulls in the selection of the sales region
	  */
	public void setIsIncludeNullsSalesRegion (boolean IsIncludeNullsSalesRegion)
	{
		set_Value (COLUMNNAME_IsIncludeNullsSalesRegion, Boolean.valueOf(IsIncludeNullsSalesRegion));
	}

	/** Get Include Nulls in Sales Region.
		@return Include nulls in the selection of the sales region
	  */
	public boolean isIncludeNullsSalesRegion () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsSalesRegion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in User Element 1.
		@param IsIncludeNullsUserElement1 
		Include nulls in the selection of the user element 1
	  */
	public void setIsIncludeNullsUserElement1 (boolean IsIncludeNullsUserElement1)
	{
		set_Value (COLUMNNAME_IsIncludeNullsUserElement1, Boolean.valueOf(IsIncludeNullsUserElement1));
	}

	/** Get Include Nulls in User Element 1.
		@return Include nulls in the selection of the user element 1
	  */
	public boolean isIncludeNullsUserElement1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsUserElement1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Include Nulls in User Element 2.
		@param IsIncludeNullsUserElement2 
		Include nulls in the selection of the user element 2
	  */
	public void setIsIncludeNullsUserElement2 (boolean IsIncludeNullsUserElement2)
	{
		set_Value (COLUMNNAME_IsIncludeNullsUserElement2, Boolean.valueOf(IsIncludeNullsUserElement2));
	}

	/** Get Include Nulls in User Element 2.
		@return Include nulls in the selection of the user element 2
	  */
	public boolean isIncludeNullsUserElement2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeNullsUserElement2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	public I_PA_ReportColumn getOper_1() throws RuntimeException
    {
		return (I_PA_ReportColumn)MTable.get(getCtx(), I_PA_ReportColumn.Table_Name)
			.getPO(getOper_1_ID(), get_TrxName());	}

	/** Set Operand 1.
		@param Oper_1_ID 
		First operand for calculation
	  */
	public void setOper_1_ID (int Oper_1_ID)
	{
		if (Oper_1_ID < 1) 
			set_Value (COLUMNNAME_Oper_1_ID, null);
		else 
			set_Value (COLUMNNAME_Oper_1_ID, Integer.valueOf(Oper_1_ID));
	}

	/** Get Operand 1.
		@return First operand for calculation
	  */
	public int getOper_1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Oper_1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_PA_ReportColumn getOper_2() throws RuntimeException
    {
		return (I_PA_ReportColumn)MTable.get(getCtx(), I_PA_ReportColumn.Table_Name)
			.getPO(getOper_2_ID(), get_TrxName());	}

	/** Set Operand 2.
		@param Oper_2_ID 
		Second operand for calculation
	  */
	public void setOper_2_ID (int Oper_2_ID)
	{
		if (Oper_2_ID < 1) 
			set_Value (COLUMNNAME_Oper_2_ID, null);
		else 
			set_Value (COLUMNNAME_Oper_2_ID, Integer.valueOf(Oper_2_ID));
	}

	/** Get Operand 2.
		@return Second operand for calculation
	  */
	public int getOper_2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Oper_2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Organization.
		@param Org_ID 
		Organizational entity within client
	  */
	public void setOrg_ID (int Org_ID)
	{
		if (Org_ID < 1) 
			set_Value (COLUMNNAME_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Org_ID, Integer.valueOf(Org_ID));
	}

	/** Get Organization.
		@return Organizational entity within client
	  */
	public int getOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PAAmountType AD_Reference_ID=53328 */
	public static final int PAAMOUNTTYPE_AD_Reference_ID=53328;
	/** Balance (expected sign) = B */
	public static final String PAAMOUNTTYPE_BalanceExpectedSign = "B";
	/** Credit Only = C */
	public static final String PAAMOUNTTYPE_CreditOnly = "C";
	/** Debit Only = D */
	public static final String PAAMOUNTTYPE_DebitOnly = "D";
	/** Quantity (expected sign) = Q */
	public static final String PAAMOUNTTYPE_QuantityExpectedSign = "Q";
	/** Balance (accounted sign) = S */
	public static final String PAAMOUNTTYPE_BalanceAccountedSign = "S";
	/** Quantity (accounted sign) = R */
	public static final String PAAMOUNTTYPE_QuantityAccountedSign = "R";
	/** Set Amount Type.
		@param PAAmountType 
		PA Amount Type for reporting
	  */
	public void setPAAmountType (String PAAmountType)
	{

		set_Value (COLUMNNAME_PAAmountType, PAAmountType);
	}

	/** Get Amount Type.
		@return PA Amount Type for reporting
	  */
	public String getPAAmountType () 
	{
		return (String)get_Value(COLUMNNAME_PAAmountType);
	}

	/** PAPeriodType AD_Reference_ID=53327 */
	public static final int PAPERIODTYPE_AD_Reference_ID=53327;
	/** Total = T */
	public static final String PAPERIODTYPE_Total = "T";
	/** Year = Y */
	public static final String PAPERIODTYPE_Year = "Y";
	/** Period = P */
	public static final String PAPERIODTYPE_Period = "P";
	/** Natural = N */
	public static final String PAPERIODTYPE_Natural = "N";
	/** Set Period Type.
		@param PAPeriodType 
		PA Period Type
	  */
	public void setPAPeriodType (String PAPeriodType)
	{

		set_Value (COLUMNNAME_PAPeriodType, PAPeriodType);
	}

	/** Get Period Type.
		@return PA Period Type
	  */
	public String getPAPeriodType () 
	{
		return (String)get_Value(COLUMNNAME_PAPeriodType);
	}

	/** Set Report Column.
		@param PA_ReportColumn_ID 
		Column in Report
	  */
	public void setPA_ReportColumn_ID (int PA_ReportColumn_ID)
	{
		if (PA_ReportColumn_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_ReportColumn_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_ReportColumn_ID, Integer.valueOf(PA_ReportColumn_ID));
	}

	/** Get Report Column.
		@return Column in Report
	  */
	public int getPA_ReportColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_ReportColumn_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_PA_ReportColumnSet getPA_ReportColumnSet() throws RuntimeException
    {
		return (I_PA_ReportColumnSet)MTable.get(getCtx(), I_PA_ReportColumnSet.Table_Name)
			.getPO(getPA_ReportColumnSet_ID(), get_TrxName());	}

	/** Set Report Column Set.
		@param PA_ReportColumnSet_ID 
		Collection of Columns for Report
	  */
	public void setPA_ReportColumnSet_ID (int PA_ReportColumnSet_ID)
	{
		if (PA_ReportColumnSet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_ReportColumnSet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_ReportColumnSet_ID, Integer.valueOf(PA_ReportColumnSet_ID));
	}

	/** Get Report Column Set.
		@return Collection of Columns for Report
	  */
	public int getPA_ReportColumnSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_ReportColumnSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PostingType AD_Reference_ID=125 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Set PostingType.
		@param PostingType 
		The type of posted amount for the transaction
	  */
	public void setPostingType (String PostingType)
	{

		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	/** Get PostingType.
		@return The type of posted amount for the transaction
	  */
	public String getPostingType () 
	{
		return (String)get_Value(COLUMNNAME_PostingType);
	}

	/** Set Relative Period.
		@param RelativePeriod 
		Period offset (0 is current)
	  */
	public void setRelativePeriod (BigDecimal RelativePeriod)
	{
		set_Value (COLUMNNAME_RelativePeriod, RelativePeriod);
	}

	/** Get Relative Period.
		@return Period offset (0 is current)
	  */
	public BigDecimal getRelativePeriod () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RelativePeriod);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User Element 1.
		@param UserElement1_ID 
		User defined accounting Element
	  */
	public void setUserElement1_ID (int UserElement1_ID)
	{
		if (UserElement1_ID < 1) 
			set_Value (COLUMNNAME_UserElement1_ID, null);
		else 
			set_Value (COLUMNNAME_UserElement1_ID, Integer.valueOf(UserElement1_ID));
	}

	/** Get User Element 1.
		@return User defined accounting Element
	  */
	public int getUserElement1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserElement1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User Element 2.
		@param UserElement2_ID 
		User defined accounting Element
	  */
	public void setUserElement2_ID (int UserElement2_ID)
	{
		if (UserElement2_ID < 1) 
			set_Value (COLUMNNAME_UserElement2_ID, null);
		else 
			set_Value (COLUMNNAME_UserElement2_ID, Integer.valueOf(UserElement2_ID));
	}

	/** Get User Element 2.
		@return User defined accounting Element
	  */
	public int getUserElement2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserElement2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}