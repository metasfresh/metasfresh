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

/** Generated Model for Impex_ConnectorParam
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_Impex_ConnectorParam extends PO implements I_Impex_ConnectorParam, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110413L;

    /** Standard Constructor */
    public X_Impex_ConnectorParam (Properties ctx, int Impex_ConnectorParam_ID, String trxName)
    {
      super (ctx, Impex_ConnectorParam_ID, trxName);
      /** if (Impex_ConnectorParam_ID == 0)
        {
			setAD_Reference_ID (0);
			setImpEx_Connector_ID (0);
			setImpex_ConnectorParam_ID (0);
			setName (null);
			setParamName (null);
        } */
    }

    /** Load Constructor */
    public X_Impex_ConnectorParam (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Impex_ConnectorParam[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

	/** Set Referenz.
		@param AD_Reference_ID 
		Systemreferenz und Validierung
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Referenz.
		@return Systemreferenz und Validierung
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
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

	public de.metas.impex.model.I_ImpEx_Connector getImpEx_Connector() throws RuntimeException
    {
		return (de.metas.impex.model.I_ImpEx_Connector)MTable.get(getCtx(), de.metas.impex.model.I_ImpEx_Connector.Table_Name)
			.getPO(getImpEx_Connector_ID(), get_TrxName());	}

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

	/** Set Konnektor-Parameter.
		@param Impex_ConnectorParam_ID Konnektor-Parameter	  */
	public void setImpex_ConnectorParam_ID (int Impex_ConnectorParam_ID)
	{
		if (Impex_ConnectorParam_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Impex_ConnectorParam_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Impex_ConnectorParam_ID, Integer.valueOf(Impex_ConnectorParam_ID));
	}

	/** Get Konnektor-Parameter.
		@return Konnektor-Parameter	  */
	public int getImpex_ConnectorParam_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Impex_ConnectorParam_ID);
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

	/** Set Parameter-Name.
		@param ParamName Parameter-Name	  */
	public void setParamName (String ParamName)
	{
		set_Value (COLUMNNAME_ParamName, ParamName);
	}

	/** Get Parameter-Name.
		@return Parameter-Name	  */
	public String getParamName () 
	{
		return (String)get_Value(COLUMNNAME_ParamName);
	}

	/** Set Parameterwert.
		@param ParamValue Parameterwert	  */
	public void setParamValue (String ParamValue)
	{
		set_Value (COLUMNNAME_ParamValue, ParamValue);
	}

	/** Get Parameterwert.
		@return Parameterwert	  */
	public String getParamValue () 
	{
		return (String)get_Value(COLUMNNAME_ParamValue);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Eintraege; die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Eintraege; die kleinste Zahl kommt zuerst
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
