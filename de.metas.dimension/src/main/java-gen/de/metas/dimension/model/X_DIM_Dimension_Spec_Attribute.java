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
 * Generated Model for DIM_Dimension_Spec_Attribute
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DIM_Dimension_Spec_Attribute extends org.compiere.model.PO implements I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1637687623L;

	/** Standard Constructor */
	public X_DIM_Dimension_Spec_Attribute(final Properties ctx, final int DIM_Dimension_Spec_Attribute_ID, final String trxName)
	{
		super(ctx, DIM_Dimension_Spec_Attribute_ID, trxName);
		/**
		 * if (DIM_Dimension_Spec_Attribute_ID == 0)
		 * {
		 * setDIM_Dimension_Spec_Attribute_ID (0);
		 * setDIM_Dimension_Spec_ID (0);
		 * setIsIncludeAllAttributeValues (true);
		 * // Y
		 * setIsValueAggregate (false);
		 * // N
		 * setM_Attribute_ID (0);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DIM_Dimension_Spec_Attribute(final Properties ctx, final ResultSet rs, final String trxName)
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

	@Override
	public de.metas.dimension.model.I_DIM_Dimension_Spec getDIM_Dimension_Spec() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DIM_Dimension_Spec_ID, de.metas.dimension.model.I_DIM_Dimension_Spec.class);
	}

	@Override
	public void setDIM_Dimension_Spec(final de.metas.dimension.model.I_DIM_Dimension_Spec DIM_Dimension_Spec)
	{
		set_ValueFromPO(COLUMNNAME_DIM_Dimension_Spec_ID, de.metas.dimension.model.I_DIM_Dimension_Spec.class, DIM_Dimension_Spec);
	}

	/**
	 * Set Dimensionsspezifikation.
	 * 
	 * @param DIM_Dimension_Spec_ID Dimensionsspezifikation
	 */
	@Override
	public void setDIM_Dimension_Spec_ID(final int DIM_Dimension_Spec_ID)
	{
		if (DIM_Dimension_Spec_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DIM_Dimension_Spec_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DIM_Dimension_Spec_ID, Integer.valueOf(DIM_Dimension_Spec_ID));
		}
	}

	/**
	 * Get Dimensionsspezifikation.
	 * 
	 * @return Dimensionsspezifikation
	 */
	@Override
	public int getDIM_Dimension_Spec_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set Alle Attributwerte.
	 * 
	 * @param IsIncludeAllAttributeValues Alle Attributwerte
	 */
	@Override
	public void setIsIncludeAllAttributeValues(final boolean IsIncludeAllAttributeValues)
	{
		set_Value(COLUMNNAME_IsIncludeAllAttributeValues, Boolean.valueOf(IsIncludeAllAttributeValues));
	}

	/**
	 * Get Alle Attributwerte.
	 * 
	 * @return Alle Attributwerte
	 */
	@Override
	public boolean isIncludeAllAttributeValues()
	{
		final Object oo = get_Value(COLUMNNAME_IsIncludeAllAttributeValues);
		if (oo != null)
		{
			if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Merkmalswert-Zusammenfassung.
	 * 
	 * @param IsValueAggregate Merkmalswert-Zusammenfassung
	 */
	@Override
	public void setIsValueAggregate(final boolean IsValueAggregate)
	{
		set_Value(COLUMNNAME_IsValueAggregate, Boolean.valueOf(IsValueAggregate));
	}

	/**
	 * Get Merkmalswert-Zusammenfassung.
	 * 
	 * @return Merkmalswert-Zusammenfassung
	 */
	@Override
	public boolean isValueAggregate()
	{
		final Object oo = get_Value(COLUMNNAME_IsValueAggregate);
		if (oo != null)
		{
			if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setM_Attribute(final org.compiere.model.I_M_Attribute M_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
	}

	/**
	 * Set Merkmal.
	 * 
	 * @param M_Attribute_ID
	 *            Produkt-Merkmal
	 */
	@Override
	public void setM_Attribute_ID(final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1)
		{
			set_Value(COLUMNNAME_M_Attribute_ID, null);
		}
		else
		{
			set_Value(COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
		}
	}

	/**
	 * Get Merkmal.
	 * 
	 * @return Produkt-Merkmal
	 */
	@Override
	public int getM_Attribute_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set Gruppenname.
	 * 
	 * @param ValueAggregateName Gruppenname
	 */
	@Override
	public void setValueAggregateName(final java.lang.String ValueAggregateName)
	{
		set_Value(COLUMNNAME_ValueAggregateName, ValueAggregateName);
	}

	/**
	 * Get Gruppenname.
	 * 
	 * @return Gruppenname
	 */
	@Override
	public java.lang.String getValueAggregateName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValueAggregateName);
	}
}