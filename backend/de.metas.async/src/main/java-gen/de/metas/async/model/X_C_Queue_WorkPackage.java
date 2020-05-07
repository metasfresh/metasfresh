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
package de.metas.async.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Queue_WorkPackage
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Queue_WorkPackage extends org.compiere.model.PO implements I_C_Queue_WorkPackage, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1741997145L;

    /** Standard Constructor */
    public X_C_Queue_WorkPackage (Properties ctx, int C_Queue_WorkPackage_ID, String trxName)
    {
      super (ctx, C_Queue_WorkPackage_ID, trxName);
      /** if (C_Queue_WorkPackage_ID == 0)
        {
			setC_Queue_Block_ID (0);
			setC_Queue_WorkPackage_ID (0);
			setIsError (false);
// N
			setIsErrorAcknowledged (false);
// N
			setIsReadyForProcessing (false);
// N
			setPriority (null);
// 5
			setProcessed (false);
// N
			setSkipTimeoutMillis (0);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_Queue_WorkPackage (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance_Creator() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_Creator_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance_Creator(org.compiere.model.I_AD_PInstance AD_PInstance_Creator)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_Creator_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance_Creator);
	}

	/** Set Erstellt durch Prozess-Instanz.
		@param AD_PInstance_Creator_ID Erstellt durch Prozess-Instanz	  */
	@Override
	public void setAD_PInstance_Creator_ID (int AD_PInstance_Creator_ID)
	{
		throw new IllegalArgumentException ("AD_PInstance_Creator_ID is virtual column");	}

	/** Get Erstellt durch Prozess-Instanz.
		@return Erstellt durch Prozess-Instanz	  */
	@Override
	public int getAD_PInstance_Creator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_Creator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	/** Set Prozess-Instanz.
		@param AD_PInstance_ID 
		Instanz eines Prozesses
	  */
	@Override
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Prozess-Instanz.
		@return Instanz eines Prozesses
	  */
	@Override
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID 
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User_InCharge() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_InCharge_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_InCharge(org.compiere.model.I_AD_User AD_User_InCharge)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_InCharge_ID, org.compiere.model.I_AD_User.class, AD_User_InCharge);
	}

	/** Set Betreuer.
		@param AD_User_InCharge_ID 
		Person, die bei einem fachlichen Problem vom System informiert wird.
	  */
	@Override
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, Integer.valueOf(AD_User_InCharge_ID));
	}

	/** Get Betreuer.
		@return Person, die bei einem fachlichen Problem vom System informiert wird.
	  */
	@Override
	public int getAD_User_InCharge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_InCharge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Batch Enqueued Count.
		@param BatchEnqueuedCount Batch Enqueued Count	  */
	@Override
	public void setBatchEnqueuedCount (int BatchEnqueuedCount)
	{
		set_Value (COLUMNNAME_BatchEnqueuedCount, Integer.valueOf(BatchEnqueuedCount));
	}

	/** Get Batch Enqueued Count.
		@return Batch Enqueued Count	  */
	@Override
	public int getBatchEnqueuedCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BatchEnqueuedCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Async_Batch getC_Async_Batch() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class);
	}

	@Override
	public void setC_Async_Batch(de.metas.async.model.I_C_Async_Batch C_Async_Batch)
	{
		set_ValueFromPO(COLUMNNAME_C_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class, C_Async_Batch);
	}

	/** Set Async Batch.
		@param C_Async_Batch_ID Async Batch	  */
	@Override
	public void setC_Async_Batch_ID (int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, Integer.valueOf(C_Async_Batch_ID));
	}

	/** Get Async Batch.
		@return Async Batch	  */
	@Override
	public int getC_Async_Batch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Async_Batch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Queue_Block getC_Queue_Block() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_Block_ID, de.metas.async.model.I_C_Queue_Block.class);
	}

	@Override
	public void setC_Queue_Block(de.metas.async.model.I_C_Queue_Block C_Queue_Block)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_Block_ID, de.metas.async.model.I_C_Queue_Block.class, C_Queue_Block);
	}

	/** Set Queue Block.
		@param C_Queue_Block_ID Queue Block	  */
	@Override
	public void setC_Queue_Block_ID (int C_Queue_Block_ID)
	{
		if (C_Queue_Block_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Block_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Block_ID, Integer.valueOf(C_Queue_Block_ID));
	}

	/** Get Queue Block.
		@return Queue Block	  */
	@Override
	public int getC_Queue_Block_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_Block_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class);
	}

	@Override
	public void setC_Queue_PackageProcessor(de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class, C_Queue_PackageProcessor);
	}

	/** Set WorkPackage Processor.
		@param C_Queue_PackageProcessor_ID WorkPackage Processor	  */
	@Override
	public void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID)
	{
		throw new IllegalArgumentException ("C_Queue_PackageProcessor_ID is virtual column");	}

	/** Get WorkPackage Processor.
		@return WorkPackage Processor	  */
	@Override
	public int getC_Queue_PackageProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_PackageProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WorkPackage Queue.
		@param C_Queue_WorkPackage_ID WorkPackage Queue	  */
	@Override
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, Integer.valueOf(C_Queue_WorkPackage_ID));
	}

	/** Get WorkPackage Queue.
		@return WorkPackage Queue	  */
	@Override
	public int getC_Queue_WorkPackage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_WorkPackage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public java.lang.String getErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
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

	/** Set Fehler zur Kentnis genommen.
		@param IsErrorAcknowledged Fehler zur Kentnis genommen	  */
	@Override
	public void setIsErrorAcknowledged (boolean IsErrorAcknowledged)
	{
		set_Value (COLUMNNAME_IsErrorAcknowledged, Boolean.valueOf(IsErrorAcknowledged));
	}

	/** Get Fehler zur Kentnis genommen.
		@return Fehler zur Kentnis genommen	  */
	@Override
	public boolean isErrorAcknowledged () 
	{
		Object oo = get_Value(COLUMNNAME_IsErrorAcknowledged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bereit zur Verarbeitung.
		@param IsReadyForProcessing 
		Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.
	  */
	@Override
	public void setIsReadyForProcessing (boolean IsReadyForProcessing)
	{
		set_Value (COLUMNNAME_IsReadyForProcessing, Boolean.valueOf(IsReadyForProcessing));
	}

	/** Get Bereit zur Verarbeitung.
		@return Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.
	  */
	@Override
	public boolean isReadyForProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadyForProcessing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Duration (ms).
		@param LastDurationMillis Last Duration (ms)	  */
	@Override
	public void setLastDurationMillis (int LastDurationMillis)
	{
		set_Value (COLUMNNAME_LastDurationMillis, Integer.valueOf(LastDurationMillis));
	}

	/** Get Last Duration (ms).
		@return Last Duration (ms)	  */
	@Override
	public int getLastDurationMillis () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LastDurationMillis);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Last End Time.
		@param LastEndTime Last End Time	  */
	@Override
	public void setLastEndTime (java.sql.Timestamp LastEndTime)
	{
		set_Value (COLUMNNAME_LastEndTime, LastEndTime);
	}

	/** Get Last End Time.
		@return Last End Time	  */
	@Override
	public java.sql.Timestamp getLastEndTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LastEndTime);
	}

	/** Set Last StartTime.
		@param LastStartTime Last StartTime	  */
	@Override
	public void setLastStartTime (java.sql.Timestamp LastStartTime)
	{
		set_Value (COLUMNNAME_LastStartTime, LastStartTime);
	}

	/** Get Last StartTime.
		@return Last StartTime	  */
	@Override
	public java.sql.Timestamp getLastStartTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LastStartTime);
	}

	/** Set Gesperrt.
		@param Locked Gesperrt	  */
	@Override
	public void setLocked (boolean Locked)
	{
		throw new IllegalArgumentException ("Locked is virtual column");	}

	/** Get Gesperrt.
		@return Gesperrt	  */
	@Override
	public boolean isLocked () 
	{
		Object oo = get_Value(COLUMNNAME_Locked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * Priority AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITY_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITY_High = "3";
	/** Medium = 5 */
	public static final String PRIORITY_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITY_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITY_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITY_Minor = "9";
	/** Set Priority.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public void setPriority (java.lang.String Priority)
	{

		set_Value (COLUMNNAME_Priority, Priority);
	}

	/** Get Priority.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public java.lang.String getPriority () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Priority);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Skipped Count.
		@param Skipped_Count Skipped Count	  */
	@Override
	public void setSkipped_Count (int Skipped_Count)
	{
		set_Value (COLUMNNAME_Skipped_Count, Integer.valueOf(Skipped_Count));
	}

	/** Get Skipped Count.
		@return Skipped Count	  */
	@Override
	public int getSkipped_Count () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Skipped_Count);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Skipped First Time.
		@param Skipped_First_Time Skipped First Time	  */
	@Override
	public void setSkipped_First_Time (java.sql.Timestamp Skipped_First_Time)
	{
		set_Value (COLUMNNAME_Skipped_First_Time, Skipped_First_Time);
	}

	/** Get Skipped First Time.
		@return Skipped First Time	  */
	@Override
	public java.sql.Timestamp getSkipped_First_Time () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Skipped_First_Time);
	}

	/** Set Skipped Last Reason.
		@param Skipped_Last_Reason Skipped Last Reason	  */
	@Override
	public void setSkipped_Last_Reason (java.lang.String Skipped_Last_Reason)
	{
		set_Value (COLUMNNAME_Skipped_Last_Reason, Skipped_Last_Reason);
	}

	/** Get Skipped Last Reason.
		@return Skipped Last Reason	  */
	@Override
	public java.lang.String getSkipped_Last_Reason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Skipped_Last_Reason);
	}

	/** Set Zuletzt Übersprungen um.
		@param SkippedAt Zuletzt Übersprungen um	  */
	@Override
	public void setSkippedAt (java.sql.Timestamp SkippedAt)
	{
		set_Value (COLUMNNAME_SkippedAt, SkippedAt);
	}

	/** Get Zuletzt Übersprungen um.
		@return Zuletzt Übersprungen um	  */
	@Override
	public java.sql.Timestamp getSkippedAt () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_SkippedAt);
	}

	/** Set Skip Timeout (millis).
		@param SkipTimeoutMillis Skip Timeout (millis)	  */
	@Override
	public void setSkipTimeoutMillis (int SkipTimeoutMillis)
	{
		set_Value (COLUMNNAME_SkipTimeoutMillis, Integer.valueOf(SkipTimeoutMillis));
	}

	/** Get Skip Timeout (millis).
		@return Skip Timeout (millis)	  */
	@Override
	public int getSkipTimeoutMillis () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SkipTimeoutMillis);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}