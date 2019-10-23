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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for M_FreightCostShipper
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_FreightCostShipper extends PO implements I_M_FreightCostShipper, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20091209L;

    /** Standard Constructor */
    public X_M_FreightCostShipper (Properties ctx, int M_FreightCostShipper_ID, String trxName)
    {
      super (ctx, M_FreightCostShipper_ID, trxName);
      /** if (M_FreightCostShipper_ID == 0)
        {
			setM_FreightCost_ID (0);
			setM_FreightCostShipper_ID (0);
			setM_Shipper_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_M_FreightCostShipper (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_FreightCostShipper[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_M_FreightCost getM_FreightCost() throws RuntimeException
    {
		return (I_M_FreightCost)MTable.get(getCtx(), I_M_FreightCost.Table_Name)
			.getPO(getM_FreightCost_ID(), get_TrxName());	}

	/** Set Frachtkostenpauschale.
		@param M_FreightCost_ID Frachtkostenpauschale	  */
	public void setM_FreightCost_ID (int M_FreightCost_ID)
	{
		if (M_FreightCost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_FreightCost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_FreightCost_ID, Integer.valueOf(M_FreightCost_ID));
	}

	/** Get Frachtkostenpauschale.
		@return Frachtkostenpauschale	  */
	public int getM_FreightCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	public I_M_Shipper getM_Shipper() throws RuntimeException
    {
		return (I_M_Shipper)MTable.get(getCtx(), I_M_Shipper.Table_Name)
			.getPO(getM_Shipper_ID(), get_TrxName());	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Methode oder Art der Warenlieferung
	  */
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gueltig ab.
		@param ValidFrom 
		Gueltig ab inklusiv (erster Tag)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gueltig ab.
		@return Gueltig ab inklusiv (erster Tag)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}
