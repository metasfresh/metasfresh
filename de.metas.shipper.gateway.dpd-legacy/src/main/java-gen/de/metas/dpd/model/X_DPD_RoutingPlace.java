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
package de.metas.dpd.model;

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
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for DPD_RoutingPlace
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_RoutingPlace extends PO implements I_DPD_RoutingPlace, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_RoutingPlace (Properties ctx, int DPD_RoutingPlace_ID, String trxName)
    {
      super (ctx, DPD_RoutingPlace_ID, trxName);
      /** if (DPD_RoutingPlace_ID == 0)
        {
			setDPD_Route_ID (0);
			setDPD_RoutingPlace_ID (0);
			setPlace (null);
        } */
    }

    /** Load Constructor */
    public X_DPD_RoutingPlace (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_DPD_RoutingPlace[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.dpd.model.I_DPD_Route getDPD_Route() throws RuntimeException
    {
		return (de.metas.dpd.model.I_DPD_Route)MTable.get(getCtx(), de.metas.dpd.model.I_DPD_Route.Table_Name)
			.getPO(getDPD_Route_ID(), get_TrxName());	}

	/** Set DPD_Route.
		@param DPD_Route_ID DPD_Route	  */
	public void setDPD_Route_ID (int DPD_Route_ID)
	{
		if (DPD_Route_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_Route_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_Route_ID, Integer.valueOf(DPD_Route_ID));
	}

	/** Get DPD_Route.
		@return DPD_Route	  */
	public int getDPD_Route_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_Route_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DPD_RoutingPlace.
		@param DPD_RoutingPlace_ID DPD_RoutingPlace	  */
	public void setDPD_RoutingPlace_ID (int DPD_RoutingPlace_ID)
	{
		if (DPD_RoutingPlace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_RoutingPlace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_RoutingPlace_ID, Integer.valueOf(DPD_RoutingPlace_ID));
	}

	/** Get DPD_RoutingPlace.
		@return DPD_RoutingPlace	  */
	public int getDPD_RoutingPlace_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_RoutingPlace_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Place.
		@param Place Place	  */
	public void setPlace (String Place)
	{
		set_Value (COLUMNNAME_Place, Place);
	}

	/** Get Place.
		@return Place	  */
	public String getPlace () 
	{
		return (String)get_Value(COLUMNNAME_Place);
	}
}
