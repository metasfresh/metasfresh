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

/** Generated Model for C_Tax
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Tax extends org.compiere.model.PO implements I_C_Tax, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1970467670L;

    /** Standard Constructor */
    public X_C_Tax (Properties ctx, int C_Tax_ID, String trxName)
    {
      super (ctx, C_Tax_ID, trxName);
      /** if (C_Tax_ID == 0)
        {
			setC_Tax_ID (0);
			setC_TaxCategory_ID (0);
			setIsDefault (false);
			setIsDocumentLevel (false);
			setIsSalesTax (false);
// N
			setIsSummary (false);
			setIsTaxExempt (false);
			setIsWholeTax (false);
// N
			setName (null);
			setRate (Env.ZERO);
			setRequiresTaxCertificate (false);
			setSOPOType (null);
// B
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_Tax (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Tax[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Rule getAD_Rule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Rule_ID, org.compiere.model.I_AD_Rule.class);
	}

	@Override
	public void setAD_Rule(org.compiere.model.I_AD_Rule AD_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Rule_ID, org.compiere.model.I_AD_Rule.class, AD_Rule);
	}

	/** Set Regel.
		@param AD_Rule_ID Regel	  */
	@Override
	public void setAD_Rule_ID (int AD_Rule_ID)
	{
		if (AD_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Rule_ID, Integer.valueOf(AD_Rule_ID));
	}

	/** Get Regel.
		@return Regel	  */
	@Override
	public int getAD_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID 
		Country 
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Country 
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	@Override
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	@Override
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Steuer.
		@param C_Tax_ID 
		Tax identifier
	  */
	@Override
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Steuer.
		@return Tax identifier
	  */
	@Override
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class);
	}

	@Override
	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class, C_TaxCategory);
	}

	/** Set Steuerkategorie.
		@param C_TaxCategory_ID 
		Tax Category
	  */
	@Override
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Steuerkategorie.
		@return Tax Category
	  */
	@Override
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Steuer kopieren.
		@param DuplicateTax 
		Starte das kopieren des Steuersatzes
	  */
	@Override
	public void setDuplicateTax (java.lang.String DuplicateTax)
	{
		set_Value (COLUMNNAME_DuplicateTax, DuplicateTax);
	}

	/** Get Steuer kopieren.
		@return Starte das kopieren des Steuersatzes
	  */
	@Override
	public java.lang.String getDuplicateTax () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DuplicateTax);
	}

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Dokumentbasiert.
		@param IsDocumentLevel 
		Steuer wird dokumentbasiert berechnet (abweichend w?re zeilenweise)
	  */
	@Override
	public void setIsDocumentLevel (boolean IsDocumentLevel)
	{
		set_Value (COLUMNNAME_IsDocumentLevel, Boolean.valueOf(IsDocumentLevel));
	}

	/** Get Dokumentbasiert.
		@return Steuer wird dokumentbasiert berechnet (abweichend w?re zeilenweise)
	  */
	@Override
	public boolean isDocumentLevel () 
	{
		Object oo = get_Value(COLUMNNAME_IsDocumentLevel);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set VK Steuer.
		@param IsSalesTax 
		Dies ist eine VK Steuer
	  */
	@Override
	public void setIsSalesTax (boolean IsSalesTax)
	{
		set_Value (COLUMNNAME_IsSalesTax, Boolean.valueOf(IsSalesTax));
	}

	/** Get VK Steuer.
		@return Dies ist eine VK Steuer
	  */
	@Override
	public boolean isSalesTax () 
	{
		Object oo = get_Value(COLUMNNAME_IsSalesTax);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zusammenfassungseintrag.
		@param IsSummary 
		This is a summary entity
	  */
	@Override
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Zusammenfassungseintrag.
		@return This is a summary entity
	  */
	@Override
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set steuerbefreit.
		@param IsTaxExempt 
		Steuersatz steuerbefreit
	  */
	@Override
	public void setIsTaxExempt (boolean IsTaxExempt)
	{
		set_Value (COLUMNNAME_IsTaxExempt, Boolean.valueOf(IsTaxExempt));
	}

	/** Get steuerbefreit.
		@return Steuersatz steuerbefreit
	  */
	@Override
	public boolean isTaxExempt () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxExempt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Nach EU.
		@param IsToEULocation 
		Das Zielland befindet sich in der EU
	  */
	@Override
	public void setIsToEULocation (boolean IsToEULocation)
	{
		set_Value (COLUMNNAME_IsToEULocation, Boolean.valueOf(IsToEULocation));
	}

	/** Get Nach EU.
		@return Das Zielland befindet sich in der EU
	  */
	@Override
	public boolean isToEULocation () 
	{
		Object oo = get_Value(COLUMNNAME_IsToEULocation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Whole Tax.
		@param IsWholeTax 
		If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount
	  */
	@Override
	public void setIsWholeTax (boolean IsWholeTax)
	{
		set_Value (COLUMNNAME_IsWholeTax, Boolean.valueOf(IsWholeTax));
	}

	/** Get Whole Tax.
		@return If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount
	  */
	@Override
	public boolean isWholeTax () 
	{
		Object oo = get_Value(COLUMNNAME_IsWholeTax);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public org.compiere.model.I_C_Tax getParent_Tax() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Parent_Tax_ID, org.compiere.model.I_C_Tax.class);
	}

	@Override
	public void setParent_Tax(org.compiere.model.I_C_Tax Parent_Tax)
	{
		set_ValueFromPO(COLUMNNAME_Parent_Tax_ID, org.compiere.model.I_C_Tax.class, Parent_Tax);
	}

	/** Set Uebergeordnete Steuer.
		@param Parent_Tax_ID 
		Setzt sich die Steuer aus mehreren Steuersaetzen zusammen, wird dies mit uebergeordneten Steuersaetzen definiert.
	  */
	@Override
	public void setParent_Tax_ID (int Parent_Tax_ID)
	{
		if (Parent_Tax_ID < 1) 
			set_Value (COLUMNNAME_Parent_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Tax_ID, Integer.valueOf(Parent_Tax_ID));
	}

	/** Get Uebergeordnete Steuer.
		@return Setzt sich die Steuer aus mehreren Steuersaetzen zusammen, wird dies mit uebergeordneten Steuersaetzen definiert.
	  */
	@Override
	public int getParent_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Satz.
		@param Rate 
		Rate or Tax or Exchange
	  */
	@Override
	public void setRate (java.math.BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	/** Get Satz.
		@return Rate or Tax or Exchange
	  */
	@Override
	public java.math.BigDecimal getRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set erfordert Steuer-ID.
		@param RequiresTaxCertificate 
		Dieser Steuersatz erfordert eine Steuer-ID beim Geschaeftspartner,.
	  */
	@Override
	public void setRequiresTaxCertificate (boolean RequiresTaxCertificate)
	{
		set_Value (COLUMNNAME_RequiresTaxCertificate, Boolean.valueOf(RequiresTaxCertificate));
	}

	/** Get erfordert Steuer-ID.
		@return Dieser Steuersatz erfordert eine Steuer-ID beim Geschaeftspartner,.
	  */
	@Override
	public boolean isRequiresTaxCertificate () 
	{
		Object oo = get_Value(COLUMNNAME_RequiresTaxCertificate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * SOPOType AD_Reference_ID=287
	 * Reference name: C_Tax SPPOType
	 */
	public static final int SOPOTYPE_AD_Reference_ID=287;
	/** Both = B */
	public static final String SOPOTYPE_Both = "B";
	/** Sales Tax = S */
	public static final String SOPOTYPE_SalesTax = "S";
	/** Purchase Tax = P */
	public static final String SOPOTYPE_PurchaseTax = "P";
	/** Set VK/ EK Typ.
		@param SOPOType 
		Steuer fuer Einkauf und/ oder Verkauf Transaktionen.
	  */
	@Override
	public void setSOPOType (java.lang.String SOPOType)
	{

		set_Value (COLUMNNAME_SOPOType, SOPOType);
	}

	/** Get VK/ EK Typ.
		@return Steuer fuer Einkauf und/ oder Verkauf Transaktionen.
	  */
	@Override
	public java.lang.String getSOPOType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SOPOType);
	}

	/** Set Steuer-Indikator.
		@param TaxIndicator 
		Short form for Tax to be printed on documents
	  */
	@Override
	public void setTaxIndicator (java.lang.String TaxIndicator)
	{
		set_Value (COLUMNNAME_TaxIndicator, TaxIndicator);
	}

	/** Get Steuer-Indikator.
		@return Short form for Tax to be printed on documents
	  */
	@Override
	public java.lang.String getTaxIndicator () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaxIndicator);
	}

	@Override
	public org.compiere.model.I_C_Country getTo_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_To_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setTo_Country(org.compiere.model.I_C_Country To_Country)
	{
		set_ValueFromPO(COLUMNNAME_To_Country_ID, org.compiere.model.I_C_Country.class, To_Country);
	}

	/** Set An.
		@param To_Country_ID 
		Receiving Country
	  */
	@Override
	public void setTo_Country_ID (int To_Country_ID)
	{
		if (To_Country_ID < 1) 
			set_Value (COLUMNNAME_To_Country_ID, null);
		else 
			set_Value (COLUMNNAME_To_Country_ID, Integer.valueOf(To_Country_ID));
	}

	/** Get An.
		@return Receiving Country
	  */
	@Override
	public int getTo_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_To_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Region getTo_Region() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_To_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setTo_Region(org.compiere.model.I_C_Region To_Region)
	{
		set_ValueFromPO(COLUMNNAME_To_Region_ID, org.compiere.model.I_C_Region.class, To_Region);
	}

	/** Set An.
		@param To_Region_ID 
		Receiving Region
	  */
	@Override
	public void setTo_Region_ID (int To_Region_ID)
	{
		if (To_Region_ID < 1) 
			set_Value (COLUMNNAME_To_Region_ID, null);
		else 
			set_Value (COLUMNNAME_To_Region_ID, Integer.valueOf(To_Region_ID));
	}

	/** Get An.
		@return Receiving Region
	  */
	@Override
	public int getTo_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_To_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gueltig ab.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gueltig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}