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
package org.adempiere.process.rpl.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EXP_ReplicationTrxLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EXP_ReplicationTrxLine extends org.compiere.model.PO implements I_EXP_ReplicationTrxLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -659009756L;

    /** Standard Constructor */
    public X_EXP_ReplicationTrxLine (Properties ctx, int EXP_ReplicationTrxLine_ID, String trxName)
    {
      super (ctx, EXP_ReplicationTrxLine_ID, trxName);
      /** if (EXP_ReplicationTrxLine_ID == 0)
        {
			setAD_Table_ID (0);
			setEXP_ReplicationTrxLine_ID (0);
			setRecord_ID (0);
			setReplicationTrxStatus (null);
        } */
    }

    /** Load Constructor */
    public X_EXP_ReplicationTrxLine (Properties ctx, ResultSet rs, String trxName)
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

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_EXP_ReplicationTrxLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.adempiere.process.rpl.model.I_EXP_ReplicationTrx getEXP_ReplicationTrx() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_EXP_ReplicationTrx_ID, org.adempiere.process.rpl.model.I_EXP_ReplicationTrx.class);
	}

	@Override
	public void setEXP_ReplicationTrx(org.adempiere.process.rpl.model.I_EXP_ReplicationTrx EXP_ReplicationTrx)
	{
		set_ValueFromPO(COLUMNNAME_EXP_ReplicationTrx_ID, org.adempiere.process.rpl.model.I_EXP_ReplicationTrx.class, EXP_ReplicationTrx);
	}

	/** Set Replikationstransaktion.
		@param EXP_ReplicationTrx_ID Replikationstransaktion	  */
	@Override
	public void setEXP_ReplicationTrx_ID (int EXP_ReplicationTrx_ID)
	{
		if (EXP_ReplicationTrx_ID < 1) 
			set_Value (COLUMNNAME_EXP_ReplicationTrx_ID, null);
		else 
			set_Value (COLUMNNAME_EXP_ReplicationTrx_ID, Integer.valueOf(EXP_ReplicationTrx_ID));
	}

	/** Get Replikationstransaktion.
		@return Replikationstransaktion	  */
	@Override
	public int getEXP_ReplicationTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_ReplicationTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Replikationstransaktionszeile.
		@param EXP_ReplicationTrxLine_ID Replikationstransaktionszeile	  */
	@Override
	public void setEXP_ReplicationTrxLine_ID (int EXP_ReplicationTrxLine_ID)
	{
		if (EXP_ReplicationTrxLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_ReplicationTrxLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EXP_ReplicationTrxLine_ID, Integer.valueOf(EXP_ReplicationTrxLine_ID));
	}

	/** Get Replikationstransaktionszeile.
		@return Replikationstransaktionszeile	  */
	@Override
	public int getEXP_ReplicationTrxLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_ReplicationTrxLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * ReplicationTrxStatus AD_Reference_ID=540491
	 * Reference name: EXP_ReplicationTrxStatus
	 */
	public static final int REPLICATIONTRXSTATUS_AD_Reference_ID=540491;
	/** Vollständig = Completed */
	public static final String REPLICATIONTRXSTATUS_Vollstaendig = "Completed";
	/** Nicht vollständig importiert = ImportedWithIssues */
	public static final String REPLICATIONTRXSTATUS_NichtVollstaendigImportiert = "ImportedWithIssues";
	/** Fehler = Failed */
	public static final String REPLICATIONTRXSTATUS_Fehler = "Failed";
	/** Set Replikationsstatus.
		@param ReplicationTrxStatus Replikationsstatus	  */
	@Override
	public void setReplicationTrxStatus (java.lang.String ReplicationTrxStatus)
	{

		set_Value (COLUMNNAME_ReplicationTrxStatus, ReplicationTrxStatus);
	}

	/** Get Replikationsstatus.
		@return Replikationsstatus	  */
	@Override
	public java.lang.String getReplicationTrxStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReplicationTrxStatus);
	}
}