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
package de.metas.commission.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_AdvCommissionFactCand
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AdvCommissionFactCand extends org.compiere.model.PO implements I_C_AdvCommissionFactCand, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1675803658L;

    /** Standard Constructor */
    public X_C_AdvCommissionFactCand (Properties ctx, int C_AdvCommissionFactCand_ID, String trxName)
    {
      super (ctx, C_AdvCommissionFactCand_ID, trxName);
      /** if (C_AdvCommissionFactCand_ID == 0)
        {
			setAD_Table_ID (0);
			setAlsoHandleTypesWithProcessNow (false);
// N
			setC_AdvCommissionFactCand_ID (0);
			setC_AdvCommissionRelevantPO_ID (0);
			setIsError (false);
// N
			setIsImmediateProcessingDone (false);
// N
			setIsSubsequentProcessingDone (false);
// N
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionFactCand (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionFactCand[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Issue getAD_Issue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	/** Set Inklusive Provisionsarten mit "Sofort verarbeiten".
		@param AlsoHandleTypesWithProcessNow Inklusive Provisionsarten mit "Sofort verarbeiten"	  */
	@Override
	public void setAlsoHandleTypesWithProcessNow (boolean AlsoHandleTypesWithProcessNow)
	{
		set_ValueNoCheck (COLUMNNAME_AlsoHandleTypesWithProcessNow, Boolean.valueOf(AlsoHandleTypesWithProcessNow));
	}

	/** Get Inklusive Provisionsarten mit "Sofort verarbeiten".
		@return Inklusive Provisionsarten mit "Sofort verarbeiten"	  */
	@Override
	public boolean isAlsoHandleTypesWithProcessNow () 
	{
		Object oo = get_Value(COLUMNNAME_AlsoHandleTypesWithProcessNow);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public de.metas.commission.model.I_C_AdvCommissionFactCand getC_AdvComFactCand_Cause() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AdvComFactCand_Cause_ID, de.metas.commission.model.I_C_AdvCommissionFactCand.class);
	}

	@Override
	public void setC_AdvComFactCand_Cause(de.metas.commission.model.I_C_AdvCommissionFactCand C_AdvComFactCand_Cause)
	{
		set_ValueFromPO(COLUMNNAME_C_AdvComFactCand_Cause_ID, de.metas.commission.model.I_C_AdvCommissionFactCand.class, C_AdvComFactCand_Cause);
	}

	/** Set Urspr. Warteschlangen-Eintrag.
		@param C_AdvComFactCand_Cause_ID Urspr. Warteschlangen-Eintrag	  */
	@Override
	public void setC_AdvComFactCand_Cause_ID (int C_AdvComFactCand_Cause_ID)
	{
		if (C_AdvComFactCand_Cause_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComFactCand_Cause_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComFactCand_Cause_ID, Integer.valueOf(C_AdvComFactCand_Cause_ID));
	}

	/** Get Urspr. Warteschlangen-Eintrag.
		@return Urspr. Warteschlangen-Eintrag	  */
	@Override
	public int getC_AdvComFactCand_Cause_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComFactCand_Cause_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsdaten-Wartschlange.
		@param C_AdvCommissionFactCand_ID Provisionsdaten-Wartschlange	  */
	@Override
	public void setC_AdvCommissionFactCand_ID (int C_AdvCommissionFactCand_ID)
	{
		if (C_AdvCommissionFactCand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFactCand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFactCand_ID, Integer.valueOf(C_AdvCommissionFactCand_ID));
	}

	/** Get Provisionsdaten-Wartschlange.
		@return Provisionsdaten-Wartschlange	  */
	@Override
	public int getC_AdvCommissionFactCand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionFactCand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.commission.model.I_C_AdvCommissionRelevantPO getC_AdvCommissionRelevantPO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AdvCommissionRelevantPO_ID, de.metas.commission.model.I_C_AdvCommissionRelevantPO.class);
	}

	@Override
	public void setC_AdvCommissionRelevantPO(de.metas.commission.model.I_C_AdvCommissionRelevantPO C_AdvCommissionRelevantPO)
	{
		set_ValueFromPO(COLUMNNAME_C_AdvCommissionRelevantPO_ID, de.metas.commission.model.I_C_AdvCommissionRelevantPO.class, C_AdvCommissionRelevantPO);
	}

	/** Set Def. relevanter Datensatz.
		@param C_AdvCommissionRelevantPO_ID Def. relevanter Datensatz	  */
	@Override
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID)
	{
		if (C_AdvCommissionRelevantPO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionRelevantPO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionRelevantPO_ID, Integer.valueOf(C_AdvCommissionRelevantPO_ID));
	}

	/** Get Def. relevanter Datensatz.
		@return Def. relevanter Datensatz	  */
	@Override
	public int getC_AdvCommissionRelevantPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionRelevantPO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	@Override
	public void setDateAcct (java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	@Override
	public java.sql.Timestamp getDateAcct () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Info.
		@param Info 
		Information
	  */
	@Override
	public void setInfo (boolean Info)
	{
		set_ValueNoCheck (COLUMNNAME_Info, Boolean.valueOf(Info));
	}

	/** Get Info.
		@return Information
	  */
	@Override
	public boolean isInfo () 
	{
		Object oo = get_Value(COLUMNNAME_Info);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sofort-Verarbeitung durchgeführt.
		@param IsImmediateProcessingDone Sofort-Verarbeitung durchgeführt	  */
	@Override
	public void setIsImmediateProcessingDone (boolean IsImmediateProcessingDone)
	{
		set_Value (COLUMNNAME_IsImmediateProcessingDone, Boolean.valueOf(IsImmediateProcessingDone));
	}

	/** Get Sofort-Verarbeitung durchgeführt.
		@return Sofort-Verarbeitung durchgeführt	  */
	@Override
	public boolean isImmediateProcessingDone () 
	{
		Object oo = get_Value(COLUMNNAME_IsImmediateProcessingDone);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Nachgelagerte Verarbeitung durchgeführt.
		@param IsSubsequentProcessingDone Nachgelagerte Verarbeitung durchgeführt	  */
	@Override
	public void setIsSubsequentProcessingDone (boolean IsSubsequentProcessingDone)
	{
		set_Value (COLUMNNAME_IsSubsequentProcessingDone, Boolean.valueOf(IsSubsequentProcessingDone));
	}

	/** Get Nachgelagerte Verarbeitung durchgeführt.
		@return Nachgelagerte Verarbeitung durchgeführt	  */
	@Override
	public boolean isSubsequentProcessingDone () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubsequentProcessingDone);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
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

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_ValueNoCheck (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaktion.
		@param TrxName 
		Name of the transaction
	  */
	@Override
	public void setTrxName (java.lang.String TrxName)
	{
		set_Value (COLUMNNAME_TrxName, TrxName);
	}

	/** Get Transaktion.
		@return Name of the transaction
	  */
	@Override
	public java.lang.String getTrxName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TrxName);
	}
}