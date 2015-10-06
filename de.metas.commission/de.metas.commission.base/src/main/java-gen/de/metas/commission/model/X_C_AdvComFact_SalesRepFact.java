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
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for C_AdvComFact_SalesRepFact
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComFact_SalesRepFact extends PO implements I_C_AdvComFact_SalesRepFact, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComFact_SalesRepFact (Properties ctx, int C_AdvComFact_SalesRepFact_ID, String trxName)
    {
      super (ctx, C_AdvComFact_SalesRepFact_ID, trxName);
      /** if (C_AdvComFact_SalesRepFact_ID == 0)
        {
			setC_AdvComFact_SalesRepFact_ID (0);
			setC_AdvCommissionFact_ID (0);
			setC_AdvComSalesRepFact_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_AdvComFact_SalesRepFact (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvComFact_SalesRepFact[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_AdvComFact_SalesRepFact_ID.
		@param C_AdvComFact_SalesRepFact_ID C_AdvComFact_SalesRepFact_ID	  */
	public void setC_AdvComFact_SalesRepFact_ID (int C_AdvComFact_SalesRepFact_ID)
	{
		if (C_AdvComFact_SalesRepFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComFact_SalesRepFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComFact_SalesRepFact_ID, Integer.valueOf(C_AdvComFact_SalesRepFact_ID));
	}

	/** Get C_AdvComFact_SalesRepFact_ID.
		@return C_AdvComFact_SalesRepFact_ID	  */
	public int getC_AdvComFact_SalesRepFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComFact_SalesRepFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionFact getC_AdvCommissionFact() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionFact)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionFact.Table_Name)
			.getPO(getC_AdvCommissionFact_ID(), get_TrxName());	}

	/** Set Vorgangsdatensatz.
		@param C_AdvCommissionFact_ID Vorgangsdatensatz	  */
	public void setC_AdvCommissionFact_ID (int C_AdvCommissionFact_ID)
	{
		if (C_AdvCommissionFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFact_ID, Integer.valueOf(C_AdvCommissionFact_ID));
	}

	/** Get Vorgangsdatensatz.
		@return Vorgangsdatensatz	  */
	public int getC_AdvCommissionFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvComSalesRepFact getC_AdvComSalesRepFact() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComSalesRepFact)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComSalesRepFact.Table_Name)
			.getPO(getC_AdvComSalesRepFact_ID(), get_TrxName());	}

	/** Set Sponsor-Provisionsdatensatz.
		@param C_AdvComSalesRepFact_ID Sponsor-Provisionsdatensatz	  */
	public void setC_AdvComSalesRepFact_ID (int C_AdvComSalesRepFact_ID)
	{
		if (C_AdvComSalesRepFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSalesRepFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSalesRepFact_ID, Integer.valueOf(C_AdvComSalesRepFact_ID));
	}

	/** Get Sponsor-Provisionsdatensatz.
		@return Sponsor-Provisionsdatensatz	  */
	public int getC_AdvComSalesRepFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSalesRepFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}