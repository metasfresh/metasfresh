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
package de.metas.inout.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_Material_Balance_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Material_Balance_Config extends org.compiere.model.PO implements I_M_Material_Balance_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1498143467L;

    /** Standard Constructor */
    public X_M_Material_Balance_Config (Properties ctx, int M_Material_Balance_Config_ID, String trxName)
    {
      super (ctx, M_Material_Balance_Config_ID, trxName);
      /** if (M_Material_Balance_Config_ID == 0)
        {
			setC_Calendar_ID (0);
			setIsAutoResetEnabled (false);
// N
			setM_Material_Balance_Config_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Material_Balance_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AutoResetInterval.
		@param AutoResetInterval AutoResetInterval	  */
	@Override
	public void setAutoResetInterval (java.math.BigDecimal AutoResetInterval)
	{
		set_Value (COLUMNNAME_AutoResetInterval, AutoResetInterval);
	}

	/** Get AutoResetInterval.
		@return AutoResetInterval	  */
	@Override
	public java.math.BigDecimal getAutoResetInterval () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AutoResetInterval);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
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
	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	/** Set Geschäftspartnergruppe.
		@param C_BP_Group_ID 
		Geschäftspartnergruppe
	  */
	@Override
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Geschäftspartnergruppe.
		@return Geschäftspartnergruppe
	  */
	@Override
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
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
		Bezeichnung des Buchführungs-Kalenders
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
		@return Bezeichnung des Buchführungs-Kalenders
	  */
	@Override
	public int getC_Calendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IsAutoResetEnabled.
		@param IsAutoResetEnabled IsAutoResetEnabled	  */
	@Override
	public void setIsAutoResetEnabled (boolean IsAutoResetEnabled)
	{
		set_Value (COLUMNNAME_IsAutoResetEnabled, Boolean.valueOf(IsAutoResetEnabled));
	}

	/** Get IsAutoResetEnabled.
		@return IsAutoResetEnabled	  */
	@Override
	public boolean isAutoResetEnabled () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoResetEnabled);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * IsCustomer AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISCUSTOMER_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISCUSTOMER_Yes = "Y";
	/** No = N */
	public static final String ISCUSTOMER_No = "N";
	/** Set Kunde.
		@param IsCustomer 
		Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	  */
	@Override
	public void setIsCustomer (java.lang.String IsCustomer)
	{

		set_Value (COLUMNNAME_IsCustomer, IsCustomer);
	}

	/** Get Kunde.
		@return Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	  */
	@Override
	public java.lang.String getIsCustomer () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsCustomer);
	}

	/** 
	 * IsForFlatrate AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISFORFLATRATE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISFORFLATRATE_Yes = "Y";
	/** No = N */
	public static final String ISFORFLATRATE_No = "N";
	/** Set IsForFlatrate.
		@param IsForFlatrate IsForFlatrate	  */
	@Override
	public void setIsForFlatrate (java.lang.String IsForFlatrate)
	{

		set_Value (COLUMNNAME_IsForFlatrate, IsForFlatrate);
	}

	/** Get IsForFlatrate.
		@return IsForFlatrate	  */
	@Override
	public java.lang.String getIsForFlatrate () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsForFlatrate);
	}

	/** 
	 * IsVendor AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISVENDOR_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISVENDOR_Yes = "Y";
	/** No = N */
	public static final String ISVENDOR_No = "N";
	/** Set Lieferant.
		@param IsVendor 
		Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	  */
	@Override
	public void setIsVendor (java.lang.String IsVendor)
	{

		set_Value (COLUMNNAME_IsVendor, IsVendor);
	}

	/** Get Lieferant.
		@return Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	  */
	@Override
	public java.lang.String getIsVendor () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsVendor);
	}

	/** Set M_Material_Balance_Config.
		@param M_Material_Balance_Config_ID M_Material_Balance_Config	  */
	@Override
	public void setM_Material_Balance_Config_ID (int M_Material_Balance_Config_ID)
	{
		if (M_Material_Balance_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Balance_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Balance_Config_ID, Integer.valueOf(M_Material_Balance_Config_ID));
	}

	/** Get M_Material_Balance_Config.
		@return M_Material_Balance_Config	  */
	@Override
	public int getM_Material_Balance_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Balance_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ResetBufferInterval.
		@param ResetBufferInterval ResetBufferInterval	  */
	@Override
	public void setResetBufferInterval (java.math.BigDecimal ResetBufferInterval)
	{
		set_Value (COLUMNNAME_ResetBufferInterval, ResetBufferInterval);
	}

	/** Get ResetBufferInterval.
		@return ResetBufferInterval	  */
	@Override
	public java.math.BigDecimal getResetBufferInterval () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ResetBufferInterval);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}