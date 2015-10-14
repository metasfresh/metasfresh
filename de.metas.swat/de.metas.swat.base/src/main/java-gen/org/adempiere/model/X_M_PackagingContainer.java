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
package org.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_PackagingContainer
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#${env.BUILD_NUMBER} - $Id$ */
public class X_M_PackagingContainer extends PO implements I_M_PackagingContainer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20091027L;

    /** Standard Constructor */
    public X_M_PackagingContainer (Properties ctx, int M_PackagingContainer_ID, String trxName)
    {
      super (ctx, M_PackagingContainer_ID, trxName);
      /** if (M_PackagingContainer_ID == 0)
        {
			setC_UOM_Length_ID (0);
			setC_UOM_Weight_ID (0);
			setHeight (Env.ZERO);
			setLength (Env.ZERO);
			setMaxWeight (Env.ZERO);
			setM_PackagingContainer_ID (0);
			setM_Product_ID (0);
			setName (null);
			setWidth (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_PackagingContainer (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_M_PackagingContainer[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_UOM getC_UOM_Length() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_Length_ID(), get_TrxName());	}

	/** Set Masseinheit fuer Laenge.
		@param C_UOM_Length_ID 
		Standardmasseinheit fuer Laenge
	  */
	public void setC_UOM_Length_ID (int C_UOM_Length_ID)
	{
		if (C_UOM_Length_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Length_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Length_ID, Integer.valueOf(C_UOM_Length_ID));
	}

	/** Get Masseinheit fuer Laenge.
		@return Standardmasseinheit fuer Laenge
	  */
	public int getC_UOM_Length_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Length_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_UOM getC_UOM_Weight() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_Weight_ID(), get_TrxName());	}

	/** Set Masseinheit fuer Gewicht.
		@param C_UOM_Weight_ID 
		Standardmasseinheit fuer Gewicht
	  */
	public void setC_UOM_Weight_ID (int C_UOM_Weight_ID)
	{
		if (C_UOM_Weight_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Weight_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Weight_ID, Integer.valueOf(C_UOM_Weight_ID));
	}

	/** Get Masseinheit fuer Gewicht.
		@return Standardmasseinheit fuer Gewicht
	  */
	public int getC_UOM_Weight_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Weight_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Hoehe.
		@param Height Hoehe	  */
	public void setHeight (BigDecimal Height)
	{
		set_Value (COLUMNNAME_Height, Height);
	}

	/** Get Hoehe.
		@return Hoehe	  */
	public BigDecimal getHeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Height);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Laenge.
		@param Length Laenge	  */
	public void setLength (BigDecimal Length)
	{
		set_Value (COLUMNNAME_Length, Length);
	}

	/** Get Laenge.
		@return Laenge	  */
	public BigDecimal getLength () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Length);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max. Volumen.
		@param MaxVolume Max. Volumen	  */
	public void setMaxVolume (BigDecimal MaxVolume)
	{
		set_Value (COLUMNNAME_MaxVolume, MaxVolume);
	}

	/** Get Max. Volumen.
		@return Max. Volumen	  */
	public BigDecimal getMaxVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxVolume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max. Gewicht.
		@param MaxWeight Max. Gewicht	  */
	public void setMaxWeight (BigDecimal MaxWeight)
	{
		set_Value (COLUMNNAME_MaxWeight, MaxWeight);
	}

	/** Get Max. Gewicht.
		@return Max. Gewicht	  */
	public BigDecimal getMaxWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxWeight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Verpackung.
		@param M_PackagingContainer_ID Verpackung	  */
	public void setM_PackagingContainer_ID (int M_PackagingContainer_ID)
	{
		if (M_PackagingContainer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PackagingContainer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PackagingContainer_ID, Integer.valueOf(M_PackagingContainer_ID));
	}

	/** Get Verpackung.
		@return Verpackung	  */
	public int getM_PackagingContainer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackagingContainer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
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

	/** Set Suchschluessel.
		@param Value 
		Suchschluessel fuer den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschluessel.
		@return Suchschluessel fuer den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Breite.
		@param Width Breite	  */
	public void setWidth (BigDecimal Width)
	{
		set_Value (COLUMNNAME_Width, Width);
	}

	/** Get Breite.
		@return Breite	  */
	public BigDecimal getWidth () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Width);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}
