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
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for DPD_FileInfo
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_FileInfo extends PO implements I_DPD_FileInfo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_FileInfo (Properties ctx, int DPD_FileInfo_ID, String trxName)
    {
      super (ctx, DPD_FileInfo_ID, trxName);
      /** if (DPD_FileInfo_ID == 0)
        {
			setDPD_FileInfo_ID (0);
			setExpiration (new Timestamp( System.currentTimeMillis() ));
			setFields (null);
			setFileName (null);
			setHash (null);
			setKey (null);
			setReference (null);
			setVersion (null);
        } */
    }

    /** Load Constructor */
    public X_DPD_FileInfo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_DPD_FileInfo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DPD_FileInfo.
		@param DPD_FileInfo_ID DPD_FileInfo	  */
	public void setDPD_FileInfo_ID (int DPD_FileInfo_ID)
	{
		if (DPD_FileInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_FileInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_FileInfo_ID, Integer.valueOf(DPD_FileInfo_ID));
	}

	/** Get DPD_FileInfo.
		@return DPD_FileInfo	  */
	public int getDPD_FileInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_FileInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Expiration.
		@param Expiration Expiration	  */
	public void setExpiration (Timestamp Expiration)
	{
		set_ValueNoCheck (COLUMNNAME_Expiration, Expiration);
	}

	/** Get Expiration.
		@return Expiration	  */
	public Timestamp getExpiration () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Expiration);
	}

	/** Set Fields.
		@param Fields Fields	  */
	public void setFields (String Fields)
	{
		set_ValueNoCheck (COLUMNNAME_Fields, Fields);
	}

	/** Get Fields.
		@return Fields	  */
	public String getFields () 
	{
		return (String)get_Value(COLUMNNAME_Fields);
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_ValueNoCheck (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set Hash.
		@param Hash Hash	  */
	public void setHash (String Hash)
	{
		set_ValueNoCheck (COLUMNNAME_Hash, Hash);
	}

	/** Get Hash.
		@return Hash	  */
	public String getHash () 
	{
		return (String)get_Value(COLUMNNAME_Hash);
	}

	/** Set Key.
		@param Key Key	  */
	public void setKey (String Key)
	{
		set_ValueNoCheck (COLUMNNAME_Key, Key);
	}

	/** Get Key.
		@return Key	  */
	public String getKey () 
	{
		return (String)get_Value(COLUMNNAME_Key);
	}

	/** Set Referenz.
		@param Reference 
		Bezug fuer diesen Eintrag
	  */
	public void setReference (String Reference)
	{
		set_ValueNoCheck (COLUMNNAME_Reference, Reference);
	}

	/** Get Referenz.
		@return Bezug fuer diesen Eintrag
	  */
	public String getReference () 
	{
		return (String)get_Value(COLUMNNAME_Reference);
	}

	/** Set Preislistenversion.
		@param Version 
		Version of the table definition
	  */
	public void setVersion (String Version)
	{
		set_ValueNoCheck (COLUMNNAME_Version, Version);
	}

	/** Get Preislistenversion.
		@return Version of the table definition
	  */
	public String getVersion () 
	{
		return (String)get_Value(COLUMNNAME_Version);
	}
}
