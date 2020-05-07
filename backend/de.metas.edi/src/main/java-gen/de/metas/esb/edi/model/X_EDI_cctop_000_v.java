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
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_000_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_000_v extends org.compiere.model.PO implements I_EDI_cctop_000_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 736774063L;

    /** Standard Constructor */
    public X_EDI_cctop_000_v (Properties ctx, int EDI_cctop_000_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_000_v_ID, trxName);
      /** if (EDI_cctop_000_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_000_v (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI_cctop_000_v.
		@param EDI_cctop_000_v_ID EDI_cctop_000_v	  */
	@Override
	public void setEDI_cctop_000_v_ID (int EDI_cctop_000_v_ID)
	{
		if (EDI_cctop_000_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, Integer.valueOf(EDI_cctop_000_v_ID));
	}

	/** Get EDI_cctop_000_v.
		@return EDI_cctop_000_v	  */
	@Override
	public int getEDI_cctop_000_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_000_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI-ID des Dateiempfängers.
		@param EdiRecipientGLN 
		EDI-ID des Dateiempfängers
	  */
	@Override
	public void setEdiRecipientGLN (java.lang.String EdiRecipientGLN)
	{
		set_Value (COLUMNNAME_EdiRecipientGLN, EdiRecipientGLN);
	}

	/** Get EDI-ID des Dateiempfängers.
		@return EDI-ID des Dateiempfängers
	  */
	@Override
	public java.lang.String getEdiRecipientGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EdiRecipientGLN);
	}
}