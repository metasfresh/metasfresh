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
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for EDI_Desadv_M_InOut
 *  @author Adempiere (generated)
 *  @version Release 3.5.4a - $Id$ */
public class X_EDI_Desadv_M_InOut extends PO implements I_EDI_Desadv_M_InOut, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260520L;

    /** Standard Constructor */
    public X_EDI_Desadv_M_InOut (Properties ctx, int EDI_Desadv_M_InOut_ID, String trxName)
    {
      super (ctx, EDI_Desadv_M_InOut_ID, trxName);
      /** if (EDI_Desadv_M_InOut_ID == 0)
        {
			setEDI_Desadv_ID (0);
			setM_InOut_ID (0);
			setIsActive (true);
// Y
        } */
    }

    /** Load Constructor */
    public X_EDI_Desadv_M_InOut (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_EDI_Desadv_M_InOut[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_EDI_Desadv getEDI_Desadv() throws RuntimeException
    {
		return (I_EDI_Desadv)MTable.get(getCtx(), I_EDI_Desadv.Table_Name)
			.getPO(getEDI_Desadv_ID(), get_TrxName());	}

	/** Set EDI Desadv.
		@param EDI_Desadv_ID EDI Desadv	  */
	public void setEDI_Desadv_ID (int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1)
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, Integer.valueOf(EDI_Desadv_ID));
	}

	/** Get EDI Desadv.
		@return EDI Desadv	  */
	public int getEDI_Desadv_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_Desadv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI Desadv InOut Link.
		@param EDI_Desadv_M_InOut_ID EDI Desadv InOut Link	  */
	public void setEDI_Desadv_M_InOut_ID (int EDI_Desadv_M_InOut_ID)
	{
		if (EDI_Desadv_M_InOut_ID < 1)
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_M_InOut_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_M_InOut_ID, Integer.valueOf(EDI_Desadv_M_InOut_ID));
	}

	/** Get EDI Desadv InOut Link.
		@return EDI Desadv InOut Link	  */
	public int getEDI_Desadv_M_InOut_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_Desadv_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Active.
		@param IsActive The record is active in the system	  */
	public void setIsActive (boolean IsActive)
	{
		set_Value (COLUMNNAME_IsActive, Boolean.valueOf(IsActive));
	}

	/** Get Active.
		@return The record is active in the system	  */
	public boolean isActive ()
	{
		Object oo = get_Value(COLUMNNAME_IsActive);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_InOut getM_InOut() throws RuntimeException
    {
		return (I_M_InOut)MTable.get(getCtx(), I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment.
		@param M_InOut_ID Shipment	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment.
		@return Shipment	  */
	public int getM_InOut_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
