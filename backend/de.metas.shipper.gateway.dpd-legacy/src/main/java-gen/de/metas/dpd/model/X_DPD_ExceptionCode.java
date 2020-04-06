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
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for DPD_ExceptionCode
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_ExceptionCode extends PO implements I_DPD_ExceptionCode, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_ExceptionCode (Properties ctx, int DPD_ExceptionCode_ID, String trxName)
    {
      super (ctx, DPD_ExceptionCode_ID, trxName);
      /** if (DPD_ExceptionCode_ID == 0)
        {
			setCodeNo (null);
			setDPD_ExceptionCode_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DPD_ExceptionCode (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_DPD_ExceptionCode[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Abkuerzung.
		@param Abbreviation Abkuerzung	  */
	public void setAbbreviation (String Abbreviation)
	{
		set_Value (COLUMNNAME_Abbreviation, Abbreviation);
	}

	/** Get Abkuerzung.
		@return Abkuerzung	  */
	public String getAbbreviation () 
	{
		return (String)get_Value(COLUMNNAME_Abbreviation);
	}

	/** Set CodeNo.
		@param CodeNo CodeNo	  */
	public void setCodeNo (String CodeNo)
	{
		set_Value (COLUMNNAME_CodeNo, CodeNo);
	}

	/** Get CodeNo.
		@return CodeNo	  */
	public String getCodeNo () 
	{
		return (String)get_Value(COLUMNNAME_CodeNo);
	}

	/** Set Beschreibung.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set DPD Differenz-Code.
		@param DPD_ExceptionCode_ID DPD Differenz-Code	  */
	public void setDPD_ExceptionCode_ID (int DPD_ExceptionCode_ID)
	{
		if (DPD_ExceptionCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_ExceptionCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_ExceptionCode_ID, Integer.valueOf(DPD_ExceptionCode_ID));
	}

	/** Get DPD Differenz-Code.
		@return DPD Differenz-Code	  */
	public int getDPD_ExceptionCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_ExceptionCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
