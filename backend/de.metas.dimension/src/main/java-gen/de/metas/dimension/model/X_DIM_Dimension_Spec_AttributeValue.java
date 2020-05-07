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
package de.metas.dimension.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for DIM_Dimension_Spec_AttributeValue
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DIM_Dimension_Spec_AttributeValue extends org.compiere.model.PO implements I_DIM_Dimension_Spec_AttributeValue, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 834989721L;

	/** Standard Constructor */
	public X_DIM_Dimension_Spec_AttributeValue(final Properties ctx, final int DIM_Dimension_Spec_AttributeValue_ID, final String trxName)
	{
		super(ctx, DIM_Dimension_Spec_AttributeValue_ID, trxName);
		/**
		 * if (DIM_Dimension_Spec_AttributeValue_ID == 0)
		 * {
		 * setDIM_Dimension_Spec_Attribute_ID (0);
		 * setDIM_Dimension_Spec_AttributeValue_ID (0);
		 * setM_AttributeValue_ID (0);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DIM_Dimension_Spec_AttributeValue(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		final org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo(ctx, Table_Name, get_TrxName());
		return poi;
	}

	@Override
	public de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute getDIM_Dimension_Spec_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute.class);
	}

	@Override
	public void setDIM_Dimension_Spec_Attribute(final de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute DIM_Dimension_Spec_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute.class, DIM_Dimension_Spec_Attribute);
	}

	/**
	 * Set Dimensionsspezifikation (Merkmal).
	 * 
	 * @param DIM_Dimension_Spec_Attribute_ID Dimensionsspezifikation (Merkmal)
	 */
	@Override
	public void setDIM_Dimension_Spec_Attribute_ID(final int DIM_Dimension_Spec_Attribute_ID)
	{
		if (DIM_Dimension_Spec_Attribute_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, Integer.valueOf(DIM_Dimension_Spec_Attribute_ID));
		}
	}

	/**
	 * Get Dimensionsspezifikation (Merkmal).
	 * 
	 * @return Dimensionsspezifikation (Merkmal)
	 */
	@Override
	public int getDIM_Dimension_Spec_Attribute_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set Dimensionsattributwert.
	 * 
	 * @param DIM_Dimension_Spec_AttributeValue_ID Dimensionsattributwert
	 */
	@Override
	public void setDIM_Dimension_Spec_AttributeValue_ID(final int DIM_Dimension_Spec_AttributeValue_ID)
	{
		if (DIM_Dimension_Spec_AttributeValue_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DIM_Dimension_Spec_AttributeValue_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DIM_Dimension_Spec_AttributeValue_ID, Integer.valueOf(DIM_Dimension_Spec_AttributeValue_ID));
		}
	}

	/**
	 * Get Dimensionsattributwert.
	 * 
	 * @return Dimensionsattributwert
	 */
	@Override
	public int getDIM_Dimension_Spec_AttributeValue_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_AttributeValue_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_AttributeValue getM_AttributeValue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeValue_ID, org.compiere.model.I_M_AttributeValue.class);
	}

	@Override
	public void setM_AttributeValue(final org.compiere.model.I_M_AttributeValue M_AttributeValue)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeValue_ID, org.compiere.model.I_M_AttributeValue.class, M_AttributeValue);
	}

	/**
	 * Set Merkmals-Wert.
	 * 
	 * @param M_AttributeValue_ID
	 *            Product Attribute Value
	 */
	@Override
	public void setM_AttributeValue_ID(final int M_AttributeValue_ID)
	{
		if (M_AttributeValue_ID < 1)
		{
			set_Value(COLUMNNAME_M_AttributeValue_ID, null);
		}
		else
		{
			set_Value(COLUMNNAME_M_AttributeValue_ID, Integer.valueOf(M_AttributeValue_ID));
		}
	}

	/**
	 * Get Merkmals-Wert.
	 * 
	 * @return Product Attribute Value
	 */
	@Override
	public int getM_AttributeValue_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeValue_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}
}