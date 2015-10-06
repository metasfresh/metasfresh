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
package de.metas.fresh.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Fresh_QtyOnHand
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_Fresh_QtyOnHand extends org.compiere.model.PO implements I_Fresh_QtyOnHand, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -507990966L;

    /** Standard Constructor */
    public X_Fresh_QtyOnHand (Properties ctx, int Fresh_QtyOnHand_ID, String trxName)
    {
      super (ctx, Fresh_QtyOnHand_ID, trxName);
      /** if (Fresh_QtyOnHand_ID == 0)
        {
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
// N
			setFresh_QtyOnHand_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Fresh_QtyOnHand (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	@Override
	public void setDateDoc (java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	@Override
	public java.sql.Timestamp getDateDoc () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zählbestand Einkauf (fresh).
		@param Fresh_QtyOnHand_ID Zählbestand Einkauf (fresh)	  */
	@Override
	public void setFresh_QtyOnHand_ID (int Fresh_QtyOnHand_ID)
	{
		if (Fresh_QtyOnHand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_ID, Integer.valueOf(Fresh_QtyOnHand_ID));
	}

	/** Get Zählbestand Einkauf (fresh).
		@return Zählbestand Einkauf (fresh)	  */
	@Override
	public int getFresh_QtyOnHand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Fresh_QtyOnHand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}