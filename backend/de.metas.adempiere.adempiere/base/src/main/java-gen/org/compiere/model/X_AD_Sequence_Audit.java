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
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Sequence_Audit
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Sequence_Audit extends PO implements I_AD_Sequence_Audit, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Sequence_Audit (Properties ctx, int AD_Sequence_Audit_ID, String trxName)
    {
      super (ctx, AD_Sequence_Audit_ID, trxName);
      /** if (AD_Sequence_Audit_ID == 0)
        {
			setAD_Sequence_ID (0);
			setAD_Table_ID (0);
			setDocumentNo (null);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Sequence_Audit (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_AD_Sequence_Audit[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Sequence getAD_Sequence() throws RuntimeException
    {
		return (I_AD_Sequence)MTable.get(getCtx(), I_AD_Sequence.Table_Name)
			.getPO(getAD_Sequence_ID(), get_TrxName());	}

	/** Set Sequence.
		@param AD_Sequence_ID 
		Document Sequence
	  */
	public void setAD_Sequence_ID (int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Sequence_ID, Integer.valueOf(AD_Sequence_ID));
	}

	/** Get Sequence.
		@return Document Sequence
	  */
	public int getAD_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}