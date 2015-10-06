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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_C_Country;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for M_FreightCostDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_FreightCostDetail extends PO implements I_M_FreightCostDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20091209L;

    /** Standard Constructor */
    public X_M_FreightCostDetail (Properties ctx, int M_FreightCostDetail_ID, String trxName)
    {
      super (ctx, M_FreightCostDetail_ID, trxName);
      /** if (M_FreightCostDetail_ID == 0)
        {
			setFreightAmt (Env.ZERO);
			setM_FreightCostDetail_ID (0);
			setM_FreightCostShipper_ID (0);
			setShipmentValueAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_FreightCostDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_FreightCostDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Country getC_Country() throws RuntimeException
    {
		return (I_C_Country)MTable.get(getCtx(), I_C_Country.Table_Name)
			.getPO(getC_Country_ID(), get_TrxName());	}

	/** Set Land.
		@param C_Country_ID 
		Land
	  */
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
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Frachtbetrag.
		@param FreightAmt 
		Frachtbetrag
	  */
	public void setFreightAmt (BigDecimal FreightAmt)
	{
		set_Value (COLUMNNAME_FreightAmt, FreightAmt);
	}

	/** Get Frachtbetrag.
		@return Frachtbetrag
	  */
	public BigDecimal getFreightAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FreightAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Frachtkostendetail.
		@param M_FreightCostDetail_ID Frachtkostendetail	  */
	public void setM_FreightCostDetail_ID (int M_FreightCostDetail_ID)
	{
		if (M_FreightCostDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_FreightCostDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_FreightCostDetail_ID, Integer.valueOf(M_FreightCostDetail_ID));
	}

	/** Get Frachtkostendetail.
		@return Frachtkostendetail	  */
	public int getM_FreightCostDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCostDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_FreightCostShipper getM_FreightCostShipper() throws RuntimeException
    {
		return (I_M_FreightCostShipper)MTable.get(getCtx(), I_M_FreightCostShipper.Table_Name)
			.getPO(getM_FreightCostShipper_ID(), get_TrxName());	}

	/** Set Lieferweg-Versandkosten.
		@param M_FreightCostShipper_ID Lieferweg-Versandkosten	  */
	public void setM_FreightCostShipper_ID (int M_FreightCostShipper_ID)
	{
		if (M_FreightCostShipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_FreightCostShipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_FreightCostShipper_ID, Integer.valueOf(M_FreightCostShipper_ID));
	}

	/** Get Lieferweg-Versandkosten.
		@return Lieferweg-Versandkosten	  */
	public int getM_FreightCostShipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCostShipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferwert.
		@param ShipmentValueAmt Lieferwert	  */
	public void setShipmentValueAmt (BigDecimal ShipmentValueAmt)
	{
		set_Value (COLUMNNAME_ShipmentValueAmt, ShipmentValueAmt);
	}

	/** Get Lieferwert.
		@return Lieferwert	  */
	public BigDecimal getShipmentValueAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShipmentValueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}