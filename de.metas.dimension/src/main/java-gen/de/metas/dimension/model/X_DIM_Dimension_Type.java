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
 * Generated Model for DIM_Dimension_Type
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DIM_Dimension_Type extends org.compiere.model.PO implements I_DIM_Dimension_Type, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1937063498L;

	/** Standard Constructor */
	public X_DIM_Dimension_Type(final Properties ctx, final int DIM_Dimension_Type_ID, final String trxName)
	{
		super(ctx, DIM_Dimension_Type_ID, trxName);
		/**
		 * if (DIM_Dimension_Type_ID == 0)
		 * {
		 * setDIM_Dimension_Type_ID (0);
		 * setInternalName (null);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DIM_Dimension_Type(final Properties ctx, final ResultSet rs, final String trxName)
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
	 * Set Beschreibung.
	 * 
	 * @param Description Beschreibung
	 */
	@Override
	public void setDescription(final java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
	}

	/**
	 * Get Beschreibung.
	 * 
	 * @return Beschreibung
	 */
	@Override
	public java.lang.String getDescription()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/**
	 * Set Dimensionstyp.
	 * 
	 * @param DIM_Dimension_Type_ID Dimensionstyp
	 */
	@Override
	public void setDIM_Dimension_Type_ID(final int DIM_Dimension_Type_ID)
	{
		if (DIM_Dimension_Type_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DIM_Dimension_Type_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DIM_Dimension_Type_ID, Integer.valueOf(DIM_Dimension_Type_ID));
		}
	}

	/**
	 * Get Dimensionstyp.
	 * 
	 * @return Dimensionstyp
	 */
	@Override
	public int getDIM_Dimension_Type_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Type_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set Interner Name.
	 * 
	 * @param InternalName Interner Name
	 */
	@Override
	public void setInternalName(final java.lang.String InternalName)
	{
		set_ValueNoCheck(COLUMNNAME_InternalName, InternalName);
	}

	/**
	 * Get Interner Name.
	 * 
	 * @return Interner Name
	 */
	@Override
	public java.lang.String getInternalName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/**
	 * Set Name.
	 * 
	 * @param Name
	 *            Alphanumeric identifier of the entity
	 */
	@Override
	public void setName(final java.lang.String Name)
	{
		set_Value(COLUMNNAME_Name, Name);
	}

	/**
	 * Get Name.
	 * 
	 * @return Alphanumeric identifier of the entity
	 */
	@Override
	public java.lang.String getName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}