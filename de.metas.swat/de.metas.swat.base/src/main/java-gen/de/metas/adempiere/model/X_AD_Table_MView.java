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
package de.metas.adempiere.model;

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
import org.compiere.model.*;

/** Generated Model for AD_Table_MView
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#286 - $Id$ */
public class X_AD_Table_MView extends PO implements I_AD_Table_MView, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110705L;

    /** Standard Constructor */
    public X_AD_Table_MView (Properties ctx, int AD_Table_MView_ID, String trxName)
    {
      super (ctx, AD_Table_MView_ID, trxName);
      /** if (AD_Table_MView_ID == 0)
        {
			setIsStaled (false);
			setIsValid (true);
// Y
        } */
    }

    /** Load Constructor */
    public X_AD_Table_MView (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_AD_Table_MView[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set DB-Tabelle.
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

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Staled.
		@param IsStaled Staled	  */
	public void setIsStaled (boolean IsStaled)
	{
		set_Value (COLUMNNAME_IsStaled, Boolean.valueOf(IsStaled));
	}

	/** Get Staled.
		@return Staled	  */
	public boolean isStaled () 
	{
		Object oo = get_Value(COLUMNNAME_IsStaled);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gueltig.
		@param IsValid 
		Element ist gueltig
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Gueltig.
		@return Element ist gueltig
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Refresh Date.
		@param LastRefreshDate Last Refresh Date	  */
	public void setLastRefreshDate (Timestamp LastRefreshDate)
	{
		set_Value (COLUMNNAME_LastRefreshDate, LastRefreshDate);
	}

	/** Get Last Refresh Date.
		@return Last Refresh Date	  */
	public Timestamp getLastRefreshDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastRefreshDate);
	}

	/** Set Staled Since.
		@param StaledSinceDate Staled Since	  */
	public void setStaledSinceDate (Timestamp StaledSinceDate)
	{
		set_Value (COLUMNNAME_StaledSinceDate, StaledSinceDate);
	}

	/** Get Staled Since.
		@return Staled Since	  */
	public Timestamp getStaledSinceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StaledSinceDate);
	}
}
