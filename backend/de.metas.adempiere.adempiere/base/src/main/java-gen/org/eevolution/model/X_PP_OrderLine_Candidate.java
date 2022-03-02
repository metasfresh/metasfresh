/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_OrderLine_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_OrderLine_Candidate extends org.compiere.model.PO implements I_PP_OrderLine_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1752868252L;

    /** Standard Constructor */
    public X_PP_OrderLine_Candidate (final Properties ctx, final int PP_OrderLine_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, PP_OrderLine_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_OrderLine_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
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
	@Override
	public void setComponentType (final @Nullable java.lang.String ComponentType)
	{
		set_Value (COLUMNNAME_ComponentType, ComponentType);
	}

	@Override
	public java.lang.String getComponentType() 
	{
		return get_ValueAsString(COLUMNNAME_ComponentType);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Candidate getPP_Order_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class);
	}

	@Override
	public void setPP_Order_Candidate(final org.eevolution.model.I_PP_Order_Candidate PP_Order_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class, PP_Order_Candidate);
	}

	@Override
	public void setPP_Order_Candidate_ID (final int PP_Order_Candidate_ID)
	{
		if (PP_Order_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Candidate_ID, PP_Order_Candidate_ID);
	}

	@Override
	public int getPP_Order_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Candidate_ID);
	}

	@Override
	public void setPP_OrderLine_Candidate_ID (final int PP_OrderLine_Candidate_ID)
	{
		if (PP_OrderLine_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_OrderLine_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_OrderLine_Candidate_ID, PP_OrderLine_Candidate_ID);
	}

	@Override
	public int getPP_OrderLine_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_OrderLine_Candidate_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class);
	}

	@Override
	public void setPP_Product_BOMLine(final org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class, PP_Product_BOMLine);
	}

	@Override
	public void setPP_Product_BOMLine_ID (final int PP_Product_BOMLine_ID)
	{
		if (PP_Product_BOMLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMLine_ID, PP_Product_BOMLine_ID);
	}

	@Override
	public int getPP_Product_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOMLine_ID);
	}

	@Override
	public void setQtyEntered (final @Nullable BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}