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
package de.metas.adempiere.model;

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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_CountryArea_Assign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_CountryArea_Assign extends org.compiere.model.PO implements I_C_CountryArea_Assign, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 745866137L;

    /** Standard Constructor */
    public X_C_CountryArea_Assign (Properties ctx, int C_CountryArea_Assign_ID, String trxName)
    {
      super (ctx, C_CountryArea_Assign_ID, trxName);
      /** if (C_CountryArea_Assign_ID == 0)
        {
			setC_Country_ID (0);
			setC_CountryArea_Assign_ID (0);
			setC_CountryArea_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_CountryArea_Assign (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_CountryArea_Assign[")
        .append(get_ID()).append("]");
      return sb.toString();
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
		Land
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
		@return Land
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Country area assign.
		@param C_CountryArea_Assign_ID Country area assign	  */
	@Override
	public void setC_CountryArea_Assign_ID (int C_CountryArea_Assign_ID)
	{
		if (C_CountryArea_Assign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_Assign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_Assign_ID, Integer.valueOf(C_CountryArea_Assign_ID));
	}

	/** Get Country area assign.
		@return Country area assign	  */
	@Override
	public int getC_CountryArea_Assign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CountryArea_Assign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_C_CountryArea getC_CountryArea() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_CountryArea_ID, I_C_CountryArea.class);
	}

	@Override
	public void setC_CountryArea(I_C_CountryArea C_CountryArea)
	{
		set_ValueFromPO(COLUMNNAME_C_CountryArea_ID, I_C_CountryArea.class, C_CountryArea);
	}

	/** Set Country Area.
		@param C_CountryArea_ID Country Area	  */
	@Override
	public void setC_CountryArea_ID (int C_CountryArea_ID)
	{
		if (C_CountryArea_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_ID, Integer.valueOf(C_CountryArea_ID));
	}

	/** Get Country Area.
		@return Country Area	  */
	@Override
	public int getC_CountryArea_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CountryArea_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GĂĽltig ab.
		@param ValidFrom 
		GĂĽltig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get GĂĽltig ab.
		@return GĂĽltig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set GĂĽltig bis.
		@param ValidTo 
		GĂĽltig bis inklusiv (letzter Tag)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get GĂĽltig bis.
		@return GĂĽltig bis inklusiv (letzter Tag)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}
