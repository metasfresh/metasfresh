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
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for C_VAT_SmallBusiness
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_VAT_SmallBusiness extends PO implements I_C_VAT_SmallBusiness, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_VAT_SmallBusiness (Properties ctx, int C_VAT_SmallBusiness_ID, String trxName)
    {
      super (ctx, C_VAT_SmallBusiness_ID, trxName);
      /** if (C_VAT_SmallBusiness_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_VAT_SmallBusiness_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_VAT_SmallBusiness (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_VAT_SmallBusiness[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
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
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kleinunternehmer-Steuerbefreiung .
		@param C_VAT_SmallBusiness_ID Kleinunternehmer-Steuerbefreiung 	  */
	public void setC_VAT_SmallBusiness_ID (int C_VAT_SmallBusiness_ID)
	{
		if (C_VAT_SmallBusiness_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_VAT_SmallBusiness_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_VAT_SmallBusiness_ID, Integer.valueOf(C_VAT_SmallBusiness_ID));
	}

	/** Get Kleinunternehmer-Steuerbefreiung .
		@return Kleinunternehmer-Steuerbefreiung 	  */
	public int getC_VAT_SmallBusiness_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_VAT_SmallBusiness_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information für ein Dokument
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information für ein Dokument
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Gültig bis inklusiv (letzter Tag)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Gültig bis inklusiv (letzter Tag)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Umsatzsteuer-ID.
		@param VATaxID Umsatzsteuer-ID	  */
	public void setVATaxID (String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	/** Get Umsatzsteuer-ID.
		@return Umsatzsteuer-ID	  */
	public String getVATaxID () 
	{
		return (String)get_Value(COLUMNNAME_VATaxID);
	}
}