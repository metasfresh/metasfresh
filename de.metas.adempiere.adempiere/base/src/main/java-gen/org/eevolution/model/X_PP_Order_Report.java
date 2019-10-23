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
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PP_Order_Report
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order_Report extends org.compiere.model.PO implements I_PP_Order_Report, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -655285460L;

    /** Standard Constructor */
    public X_PP_Order_Report (Properties ctx, int PP_Order_Report_ID, String trxName)
    {
      super (ctx, PP_Order_Report_ID, trxName);
      /** if (PP_Order_Report_ID == 0)
        {
			setC_UOM_ID (0);
			setLineType (null);
			setPP_Order_ID (0);
			setPP_Order_Report_ID (0);
			setQty (Env.ZERO);
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_PP_Order_Report (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_PP_Order_Report[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * ComponentType AD_Reference_ID=53225
	 * Reference name: PP_ComponentType
	 */
	public static final int COMPONENTTYPE_AD_Reference_ID=53225;
	/** By-Product = BY */
	public static final String COMPONENTTYPE_By_Product = "BY";
	/** Component = CO */
	public static final String COMPONENTTYPE_Component = "CO";
	/** Phantom = PH */
	public static final String COMPONENTTYPE_Phantom = "PH";
	/** Packing = PK */
	public static final String COMPONENTTYPE_Packing = "PK";
	/** Planning = PL */
	public static final String COMPONENTTYPE_Planning = "PL";
	/** Tools = TL */
	public static final String COMPONENTTYPE_Tools = "TL";
	/** Option = OP */
	public static final String COMPONENTTYPE_Option = "OP";
	/** Variant = VA */
	public static final String COMPONENTTYPE_Variant = "VA";
	/** Co-Product = CP */
	public static final String COMPONENTTYPE_Co_Product = "CP";
	/** Scrap = SC */
	public static final String COMPONENTTYPE_Scrap = "SC";
	/** Product = PR */
	public static final String COMPONENTTYPE_Product = "PR";
	/** Set Component Type.
		@param ComponentType 
		Component Type for a Bill of Material or Formula
	  */
	@Override
	public void setComponentType (java.lang.String ComponentType)
	{

		set_Value (COLUMNNAME_ComponentType, ComponentType);
	}

	/** Get Component Type.
		@return Component Type for a Bill of Material or Formula
	  */
	@Override
	public java.lang.String getComponentType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ComponentType);
	}

	/** 
	 * LineType AD_Reference_ID=540505
	 * Reference name: PP_Order_Report_LineType
	 */
	public static final int LINETYPE_AD_Reference_ID=540505;
	/** ProductionMaterial = PM */
	public static final String LINETYPE_ProductionMaterial = "PM";
	/** Sum = SU */
	public static final String LINETYPE_Sum = "SU";
	/** Set Zeilenart.
		@param LineType Zeilenart	  */
	@Override
	public void setLineType (java.lang.String LineType)
	{

		set_Value (COLUMNNAME_LineType, LineType);
	}

	/** Get Zeilenart.
		@return Zeilenart	  */
	@Override
	public java.lang.String getLineType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LineType);
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
	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	/** Set Produktionsauftrag.
		@param PP_Order_ID Produktionsauftrag	  */
	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Produktionsauftrag.
		@return Produktionsauftrag	  */
	@Override
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Summary the results of manufacturing.
		@param PP_Order_Report_ID Summary the results of manufacturing	  */
	@Override
	public void setPP_Order_Report_ID (int PP_Order_Report_ID)
	{
		if (PP_Order_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Report_ID, Integer.valueOf(PP_Order_Report_ID));
	}

	/** Get Summary the results of manufacturing.
		@return Summary the results of manufacturing	  */
	@Override
	public int getPP_Order_Report_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Report_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * ProductionMaterialType AD_Reference_ID=540506
	 * Reference name: PP_Order_Report_ProductionMaterialType
	 */
	public static final int PRODUCTIONMATERIALTYPE_AD_Reference_ID=540506;
	/** RawMaterial = R */
	public static final String PRODUCTIONMATERIALTYPE_RawMaterial = "R";
	/** ProducedMaterial = P */
	public static final String PRODUCTIONMATERIALTYPE_ProducedMaterial = "P";
	/** ProducedMaterial = S */
	public static final String PRODUCTIONMATERIALTYPE_Scrap = "S";
	/** Set Production Material Type.
		@param ProductionMaterialType Production Material Type	  */
	@Override
	public void setProductionMaterialType (java.lang.String ProductionMaterialType)
	{

		set_Value (COLUMNNAME_ProductionMaterialType, ProductionMaterialType);
	}

	/** Get Production Material Type.
		@return Production Material Type	  */
	@Override
	public java.lang.String getProductionMaterialType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductionMaterialType);
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set VariantGroup.
		@param VariantGroup VariantGroup	  */
	@Override
	public void setVariantGroup (java.lang.String VariantGroup)
	{
		set_Value (COLUMNNAME_VariantGroup, VariantGroup);
	}

	/** Get VariantGroup.
		@return VariantGroup	  */
	@Override
	public java.lang.String getVariantGroup () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VariantGroup);
	}
}