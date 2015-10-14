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
package de.metas.impex.model;

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
import org.compiere.util.KeyNamePair;

/** Generated Model for ImpEx_Connector
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_ImpEx_Connector extends PO implements I_ImpEx_Connector, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110413L;

    /** Standard Constructor */
    public X_ImpEx_Connector (Properties ctx, int ImpEx_Connector_ID, String trxName)
    {
      super (ctx, ImpEx_Connector_ID, trxName);
      /** if (ImpEx_Connector_ID == 0)
        {
			setImpEx_Connector_ID (0);
			setImpEx_ConnectorType_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_ImpEx_Connector (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_ImpEx_Connector[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Konnektor.
		@param ImpEx_Connector_ID Konnektor	  */
	public void setImpEx_Connector_ID (int ImpEx_Connector_ID)
	{
		if (ImpEx_Connector_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ImpEx_Connector_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ImpEx_Connector_ID, Integer.valueOf(ImpEx_Connector_ID));
	}

	/** Get Konnektor.
		@return Konnektor	  */
	public int getImpEx_Connector_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ImpEx_Connector_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.impex.model.I_ImpEx_ConnectorType getImpEx_ConnectorType() throws RuntimeException
    {
		return (de.metas.impex.model.I_ImpEx_ConnectorType)MTable.get(getCtx(), de.metas.impex.model.I_ImpEx_ConnectorType.Table_Name)
			.getPO(getImpEx_ConnectorType_ID(), get_TrxName());	}

	/** Set Konnektor-Typ.
		@param ImpEx_ConnectorType_ID Konnektor-Typ	  */
	public void setImpEx_ConnectorType_ID (int ImpEx_ConnectorType_ID)
	{
		if (ImpEx_ConnectorType_ID < 1) 
			set_Value (COLUMNNAME_ImpEx_ConnectorType_ID, null);
		else 
			set_Value (COLUMNNAME_ImpEx_ConnectorType_ID, Integer.valueOf(ImpEx_ConnectorType_ID));
	}

	/** Get Konnektor-Typ.
		@return Konnektor-Typ	  */
	public int getImpEx_ConnectorType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ImpEx_ConnectorType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}
