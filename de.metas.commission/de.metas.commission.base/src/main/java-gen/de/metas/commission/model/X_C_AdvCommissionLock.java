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

/** Generated Model for C_AdvCommissionLock
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvCommissionLock extends PO implements I_C_AdvCommissionLock, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvCommissionLock (Properties ctx, int C_AdvCommissionLock_ID, String trxName)
    {
      super (ctx, C_AdvCommissionLock_ID, trxName);
      /** if (C_AdvCommissionLock_ID == 0)
        {
			setC_AdvCommissionLock_ID (0);
			setC_BPartner_ID (0);
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setDateTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionLock (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionLock[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Provisionssperre.
		@param C_AdvCommissionLock_ID Provisionssperre	  */
	public void setC_AdvCommissionLock_ID (int C_AdvCommissionLock_ID)
	{
		if (C_AdvCommissionLock_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionLock_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionLock_ID, Integer.valueOf(C_AdvCommissionLock_ID));
	}

	/** Get Provisionssperre.
		@return Provisionssperre	  */
	public int getC_AdvCommissionLock_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionLock_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Geschå§´spartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschå§´spartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschå§´spartner.
		@return Bezeichnet einen Geschå§´spartner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datum von.
		@param DateFrom 
		Startdatum eines Abschnittes
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Datum von.
		@return Startdatum eines Abschnittes
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Datum bis.
		@param DateTo 
		Enddatum eines Abschnittes
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Datum bis.
		@return Enddatum eines Abschnittes
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}
}