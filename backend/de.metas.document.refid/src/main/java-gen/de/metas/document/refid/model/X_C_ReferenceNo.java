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
package de.metas.document.refid.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_ReferenceNo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_ReferenceNo extends org.compiere.model.PO implements I_C_ReferenceNo, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 162203464L;

    /** Standard Constructor */
    public X_C_ReferenceNo (Properties ctx, int C_ReferenceNo_ID, String trxName)
    {
      super (ctx, C_ReferenceNo_ID, trxName);
      /** if (C_ReferenceNo_ID == 0)
        {
			setC_ReferenceNo_ID (0);
			setC_ReferenceNo_Type_ID (0);
			setReferenceNo (null);
        } */
    }

    /** Load Constructor */
    public X_C_ReferenceNo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_ReferenceNo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Reference No.
		@param C_ReferenceNo_ID Reference No	  */
	@Override
	public void setC_ReferenceNo_ID (int C_ReferenceNo_ID)
	{
		if (C_ReferenceNo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ReferenceNo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ReferenceNo_ID, Integer.valueOf(C_ReferenceNo_ID));
	}

	/** Get Reference No.
		@return Reference No	  */
	@Override
	public int getC_ReferenceNo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ReferenceNo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.document.refid.model.I_C_ReferenceNo_Type getC_ReferenceNo_Type() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ReferenceNo_Type_ID, de.metas.document.refid.model.I_C_ReferenceNo_Type.class);
	}

	@Override
	public void setC_ReferenceNo_Type(de.metas.document.refid.model.I_C_ReferenceNo_Type C_ReferenceNo_Type)
	{
		set_ValueFromPO(COLUMNNAME_C_ReferenceNo_Type_ID, de.metas.document.refid.model.I_C_ReferenceNo_Type.class, C_ReferenceNo_Type);
	}

	/** Set Reference No Type.
		@param C_ReferenceNo_Type_ID Reference No Type	  */
	@Override
	public void setC_ReferenceNo_Type_ID (int C_ReferenceNo_Type_ID)
	{
		if (C_ReferenceNo_Type_ID < 1) 
			set_Value (COLUMNNAME_C_ReferenceNo_Type_ID, null);
		else 
			set_Value (COLUMNNAME_C_ReferenceNo_Type_ID, Integer.valueOf(C_ReferenceNo_Type_ID));
	}

	/** Get Reference No Type.
		@return Reference No Type	  */
	@Override
	public int getC_ReferenceNo_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ReferenceNo_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manuell.
		@param IsManual 
		Dies ist ein manueller Vorgang
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return Dies ist ein manueller Vorgang
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Referenznummer.
		@param ReferenceNo 
		Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	  */
	@Override
	public void setReferenceNo (java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Referenznummer.
		@return Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	  */
	@Override
	public java.lang.String getReferenceNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReferenceNo);
	}
}