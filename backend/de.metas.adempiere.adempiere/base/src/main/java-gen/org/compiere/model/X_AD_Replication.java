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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_Replication
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_AD_Replication extends PO implements I_AD_Replication, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100614L;

    /** Standard Constructor */
    public X_AD_Replication (Properties ctx, int AD_Replication_ID, String trxName)
    {
      super (ctx, AD_Replication_ID, trxName);
      /** if (AD_Replication_ID == 0)
        {
			setAD_Replication_ID (0);
			setAD_ReplicationStrategy_ID (0);
			setHostAddress (null);
			setHostPort (0);
// 80
			setIsRMIoverHTTP (true);
// Y
			setName (null);
			setRemote_Client_ID (0);
			setRemote_Org_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Replication (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_AD_Replication[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Replication.
		@param AD_Replication_ID 
		Data Replication Target
	  */
	public void setAD_Replication_ID (int AD_Replication_ID)
	{
		if (AD_Replication_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Replication_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Replication_ID, Integer.valueOf(AD_Replication_ID));
	}

	/** Get Replication.
		@return Data Replication Target
	  */
	public int getAD_Replication_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Replication_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_ReplicationStrategy getAD_ReplicationStrategy() throws RuntimeException
    {
		return (I_AD_ReplicationStrategy)MTable.get(getCtx(), I_AD_ReplicationStrategy.Table_Name)
			.getPO(getAD_ReplicationStrategy_ID(), get_TrxName());	}

	/** Set Replication Strategy.
		@param AD_ReplicationStrategy_ID 
		Data Replication Strategy
	  */
	public void setAD_ReplicationStrategy_ID (int AD_ReplicationStrategy_ID)
	{
		if (AD_ReplicationStrategy_ID < 1) 
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, Integer.valueOf(AD_ReplicationStrategy_ID));
	}

	/** Get Replication Strategy.
		@return Data Replication Strategy
	  */
	public int getAD_ReplicationStrategy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ReplicationStrategy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date last run.
		@param DateLastRun 
		Date the process was last run.
	  */
	public void setDateLastRun (Timestamp DateLastRun)
	{
		set_ValueNoCheck (COLUMNNAME_DateLastRun, DateLastRun);
	}

	/** Get Date last run.
		@return Date the process was last run.
	  */
	public Timestamp getDateLastRun () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastRun);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Host Address.
		@param HostAddress 
		Host Address URL or DNS
	  */
	public void setHostAddress (String HostAddress)
	{
		set_Value (COLUMNNAME_HostAddress, HostAddress);
	}

	/** Get Host Address.
		@return Host Address URL or DNS
	  */
	public String getHostAddress () 
	{
		return (String)get_Value(COLUMNNAME_HostAddress);
	}

	/** Set Host port.
		@param HostPort 
		Host Communication Port
	  */
	public void setHostPort (int HostPort)
	{
		set_Value (COLUMNNAME_HostPort, Integer.valueOf(HostPort));
	}

	/** Get Host port.
		@return Host Communication Port
	  */
	public int getHostPort () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HostPort);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ID Range End.
		@param IDRangeEnd 
		End if the ID Range used
	  */
	public void setIDRangeEnd (BigDecimal IDRangeEnd)
	{
		set_Value (COLUMNNAME_IDRangeEnd, IDRangeEnd);
	}

	/** Get ID Range End.
		@return End if the ID Range used
	  */
	public BigDecimal getIDRangeEnd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IDRangeEnd);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ID Range Start.
		@param IDRangeStart 
		Start of the ID Range used
	  */
	public void setIDRangeStart (BigDecimal IDRangeStart)
	{
		set_Value (COLUMNNAME_IDRangeStart, IDRangeStart);
	}

	/** Get ID Range Start.
		@return Start of the ID Range used
	  */
	public BigDecimal getIDRangeStart () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IDRangeStart);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tunnel via HTTP.
		@param IsRMIoverHTTP 
		Connect to Server via HTTP Tunnel
	  */
	public void setIsRMIoverHTTP (boolean IsRMIoverHTTP)
	{
		set_Value (COLUMNNAME_IsRMIoverHTTP, Boolean.valueOf(IsRMIoverHTTP));
	}

	/** Get Tunnel via HTTP.
		@return Connect to Server via HTTP Tunnel
	  */
	public boolean isRMIoverHTTP () 
	{
		Object oo = get_Value(COLUMNNAME_IsRMIoverHTTP);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Prefix.
		@param Prefix 
		Prefix before the sequence number
	  */
	public void setPrefix (String Prefix)
	{
		set_Value (COLUMNNAME_Prefix, Prefix);
	}

	/** Get Prefix.
		@return Prefix before the sequence number
	  */
	public String getPrefix () 
	{
		return (String)get_Value(COLUMNNAME_Prefix);
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Remote Client.
		@param Remote_Client_ID 
		Remote Client to be used to replicate / synchronize data with.
	  */
	public void setRemote_Client_ID (int Remote_Client_ID)
	{
		if (Remote_Client_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Remote_Client_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Remote_Client_ID, Integer.valueOf(Remote_Client_ID));
	}

	/** Get Remote Client.
		@return Remote Client to be used to replicate / synchronize data with.
	  */
	public int getRemote_Client_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Remote_Client_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Remote Organization.
		@param Remote_Org_ID 
		Remote Organization to be used to replicate / synchronize data with.
	  */
	public void setRemote_Org_ID (int Remote_Org_ID)
	{
		if (Remote_Org_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Remote_Org_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Remote_Org_ID, Integer.valueOf(Remote_Org_ID));
	}

	/** Get Remote Organization.
		@return Remote Organization to be used to replicate / synchronize data with.
	  */
	public int getRemote_Org_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Remote_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Suffix.
		@param Suffix 
		Suffix after the number
	  */
	public void setSuffix (String Suffix)
	{
		set_Value (COLUMNNAME_Suffix, Suffix);
	}

	/** Get Suffix.
		@return Suffix after the number
	  */
	public String getSuffix () 
	{
		return (String)get_Value(COLUMNNAME_Suffix);
	}
}