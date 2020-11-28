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

/** Generated Model for C_ReferenceNo_Doc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_ReferenceNo_Doc extends org.compiere.model.PO implements I_C_ReferenceNo_Doc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -262231971L;

    /** Standard Constructor */
    public X_C_ReferenceNo_Doc (Properties ctx, int C_ReferenceNo_Doc_ID, String trxName)
    {
      super (ctx, C_ReferenceNo_Doc_ID, trxName);
      /** if (C_ReferenceNo_Doc_ID == 0)
        {
			setAD_Table_ID (0);
			setC_ReferenceNo_Doc_ID (0);
			setC_ReferenceNo_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_ReferenceNo_Doc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_ReferenceNo_Doc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenced Document.
		@param C_ReferenceNo_Doc_ID Referenced Document	  */
	@Override
	public void setC_ReferenceNo_Doc_ID (int C_ReferenceNo_Doc_ID)
	{
		if (C_ReferenceNo_Doc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ReferenceNo_Doc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ReferenceNo_Doc_ID, Integer.valueOf(C_ReferenceNo_Doc_ID));
	}

	/** Get Referenced Document.
		@return Referenced Document	  */
	@Override
	public int getC_ReferenceNo_Doc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ReferenceNo_Doc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.document.refid.model.I_C_ReferenceNo getC_ReferenceNo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ReferenceNo_ID, de.metas.document.refid.model.I_C_ReferenceNo.class);
	}

	@Override
	public void setC_ReferenceNo(de.metas.document.refid.model.I_C_ReferenceNo C_ReferenceNo)
	{
		set_ValueFromPO(COLUMNNAME_C_ReferenceNo_ID, de.metas.document.refid.model.I_C_ReferenceNo.class, C_ReferenceNo);
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

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}