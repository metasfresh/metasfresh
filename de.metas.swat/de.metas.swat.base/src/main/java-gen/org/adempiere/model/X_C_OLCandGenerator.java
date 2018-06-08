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
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_OLCandGenerator
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_OLCandGenerator extends PO implements I_C_OLCandGenerator, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110412L;

    /** Standard Constructor */
    public X_C_OLCandGenerator (Properties ctx, int C_OLCandGenerator_ID, String trxName)
    {
      super (ctx, C_OLCandGenerator_ID, trxName);
      /** if (C_OLCandGenerator_ID == 0)
        {
			setC_OLCandGenerator_ID (0);
			setName (null);
			setOCGeneratorImpl (null);
        } */
    }

    /** Load Constructor */
    public X_C_OLCandGenerator (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_C_OLCandGenerator[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Auftragskand. Erzeugen.
		@param C_OLCandGenerator_ID Auftragskand. Erzeugen	  */
	public void setC_OLCandGenerator_ID (int C_OLCandGenerator_ID)
	{
		if (C_OLCandGenerator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandGenerator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandGenerator_ID, Integer.valueOf(C_OLCandGenerator_ID));
	}

	/** Get Auftragskand. Erzeugen.
		@return Auftragskand. Erzeugen	  */
	public int getC_OLCandGenerator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OLCandGenerator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Auftragskand.-Erzeuger.
		@param OCGeneratorImpl 
		Java-Class
	  */
	public void setOCGeneratorImpl (String OCGeneratorImpl)
	{
		set_Value (COLUMNNAME_OCGeneratorImpl, OCGeneratorImpl);
	}

	/** Get Auftragskand.-Erzeuger.
		@return Java-Class
	  */
	public String getOCGeneratorImpl () 
	{
		return (String)get_Value(COLUMNNAME_OCGeneratorImpl);
	}
}
