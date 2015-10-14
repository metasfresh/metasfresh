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

/** Generated Model for AD_User_SaveCustomInfo
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_User_SaveCustomInfo extends PO implements I_AD_User_SaveCustomInfo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110418L;

    /** Standard Constructor */
    public X_AD_User_SaveCustomInfo (Properties ctx, int AD_User_SaveCustomInfo_ID, String trxName)
    {
      super (ctx, AD_User_SaveCustomInfo_ID, trxName);
      /** if (AD_User_SaveCustomInfo_ID == 0)
        {
			setAD_User_SaveCustomInfo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_User_SaveCustomInfo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_User_SaveCustomInfo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set User Save Custom Info.
		@param AD_User_SaveCustomInfo_ID User Save Custom Info	  */
	public void setAD_User_SaveCustomInfo_ID (int AD_User_SaveCustomInfo_ID)
	{
		if (AD_User_SaveCustomInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_SaveCustomInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_SaveCustomInfo_ID, Integer.valueOf(AD_User_SaveCustomInfo_ID));
	}

	/** Get User Save Custom Info.
		@return User Save Custom Info	  */
	public int getAD_User_SaveCustomInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_SaveCustomInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
    {
		return (org.compiere.model.I_C_Country)MTable.get(getCtx(), org.compiere.model.I_C_Country.Table_Name)
			.getPO(getC_Country_ID(), get_TrxName());	}

	/** Set Land.
		@param C_Country_ID 
		Land
	  */
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
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

	/** Set Capture Sequence.
		@param CaptureSequence Capture Sequence	  */
	public void setCaptureSequence (String CaptureSequence)
	{
		set_Value (COLUMNNAME_CaptureSequence, CaptureSequence);
	}

	/** Get Capture Sequence.
		@return Capture Sequence	  */
	public String getCaptureSequence () 
	{
		return (String)get_Value(COLUMNNAME_CaptureSequence);
	}
}
