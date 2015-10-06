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
package de.metas.commission.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_AdvCommissionCondition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AdvCommissionCondition extends org.compiere.model.PO implements I_C_AdvCommissionCondition, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 539248773L;

    /** Standard Constructor */
    public X_C_AdvCommissionCondition (Properties ctx, int C_AdvCommissionCondition_ID, String trxName)
    {
      super (ctx, C_AdvCommissionCondition_ID, trxName);
      /** if (C_AdvCommissionCondition_ID == 0)
        {
			setC_AdvCommissionCondition_ID (0);
			setC_AdvComSystem_ID (0);
			setC_Calendar_ID (0);
// 1000000
			setC_Currency_ID (0);
			setC_Doctype_Payroll_ID (0);
// 1000065
			setHR_Payroll_ID (0);
			setIsDefault (false);
// N
			setIsDefaultForOrphandedSponsors (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionCondition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionCondition[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Provisionsvertrag.
		@param C_AdvCommissionCondition_ID Provisionsvertrag	  */
	@Override
	public void setC_AdvCommissionCondition_ID (int C_AdvCommissionCondition_ID)
	{
		if (C_AdvCommissionCondition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionCondition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionCondition_ID, Integer.valueOf(C_AdvCommissionCondition_ID));
	}

	/** Get Provisionsvertrag.
		@return Provisionsvertrag	  */
	@Override
	public int getC_AdvCommissionCondition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionCondition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AdvComSystem_ID, de.metas.commission.model.I_C_AdvComSystem.class);
	}

	@Override
	public void setC_AdvComSystem(de.metas.commission.model.I_C_AdvComSystem C_AdvComSystem)
	{
		set_ValueFromPO(COLUMNNAME_C_AdvComSystem_ID, de.metas.commission.model.I_C_AdvComSystem.class, C_AdvComSystem);
	}

	/** Set Vergütungsplan.
		@param C_AdvComSystem_ID Vergütungsplan	  */
	@Override
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID)
	{
		if (C_AdvComSystem_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
	}

	/** Get Vergütungsplan.
		@return Vergütungsplan	  */
	@Override
	public int getC_AdvComSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	/** Set Kalender.
		@param C_Calendar_ID 
		Bezeichnung des Buchfð¨²µngs-Kalenders
	  */
	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	/** Get Kalender.
		@return Bezeichnung des Buchfð¨²µngs-Kalenders
	  */
	@Override
	public int getC_Calendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_Doctype_Payroll() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Doctype_Payroll_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_Doctype_Payroll(org.compiere.model.I_C_DocType C_Doctype_Payroll)
	{
		set_ValueFromPO(COLUMNNAME_C_Doctype_Payroll_ID, org.compiere.model.I_C_DocType.class, C_Doctype_Payroll);
	}

	/** Set Lohnbuch-Belegart.
		@param C_Doctype_Payroll_ID Lohnbuch-Belegart	  */
	@Override
	public void setC_Doctype_Payroll_ID (int C_Doctype_Payroll_ID)
	{
		if (C_Doctype_Payroll_ID < 1) 
			set_Value (COLUMNNAME_C_Doctype_Payroll_ID, null);
		else 
			set_Value (COLUMNNAME_C_Doctype_Payroll_ID, Integer.valueOf(C_Doctype_Payroll_ID));
	}

	/** Get Lohnbuch-Belegart.
		@return Lohnbuch-Belegart	  */
	@Override
	public int getC_Doctype_Payroll_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Doctype_Payroll_ID);
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

	@Override
	public org.eevolution.model.I_HR_Payroll getHR_Payroll() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HR_Payroll_ID, org.eevolution.model.I_HR_Payroll.class);
	}

	@Override
	public void setHR_Payroll(org.eevolution.model.I_HR_Payroll HR_Payroll)
	{
		set_ValueFromPO(COLUMNNAME_HR_Payroll_ID, org.eevolution.model.I_HR_Payroll.class, HR_Payroll);
	}

	/** Set Payroll.
		@param HR_Payroll_ID Payroll	  */
	@Override
	public void setHR_Payroll_ID (int HR_Payroll_ID)
	{
		if (HR_Payroll_ID < 1) 
			set_Value (COLUMNNAME_HR_Payroll_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Payroll_ID, Integer.valueOf(HR_Payroll_ID));
	}

	/** Get Payroll.
		@return Payroll	  */
	@Override
	public int getHR_Payroll_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Payroll_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Interner Name.
		@param InternalName 
		Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
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

	/** Set für Sponsoren ohne VP.
		@param IsDefaultForOrphandedSponsors für Sponsoren ohne VP	  */
	@Override
	public void setIsDefaultForOrphandedSponsors (boolean IsDefaultForOrphandedSponsors)
	{
		set_Value (COLUMNNAME_IsDefaultForOrphandedSponsors, Boolean.valueOf(IsDefaultForOrphandedSponsors));
	}

	/** Get für Sponsoren ohne VP.
		@return für Sponsoren ohne VP	  */
	@Override
	public boolean isDefaultForOrphandedSponsors () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefaultForOrphandedSponsors);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class);
	}

	@Override
	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class, M_Product_Category);
	}

	/** Set Produkt-Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt-Kategorie.
		@return Kategorie eines Produktes
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}