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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for R_RequestType
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_R_RequestType extends PO implements I_R_RequestType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_R_RequestType (Properties ctx, int R_RequestType_ID, String trxName)
    {
      super (ctx, R_RequestType_ID, trxName);
      /** if (R_RequestType_ID == 0)
        {
			setConfidentialType (null);
// C
			setDueDateTolerance (0);
// 7
			setIsAutoChangeRequest (false);
			setIsConfidentialInfo (false);
// N
			setIsDefault (false);
// N
			setIsEMailWhenDue (false);
			setIsEMailWhenOverdue (false);
			setIsIndexed (false);
			setIsSelfService (true);
// Y
			setName (null);
			setR_RequestType_ID (0);
			setR_StatusCategory_ID (0);
        } */
    }

    /** Load Constructor */
    public X_R_RequestType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_R_RequestType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Auto Due Date Days.
		@param AutoDueDateDays 
		Automatic Due Date Days
	  */
	public void setAutoDueDateDays (int AutoDueDateDays)
	{
		set_Value (COLUMNNAME_AutoDueDateDays, Integer.valueOf(AutoDueDateDays));
	}

	/** Get Auto Due Date Days.
		@return Automatic Due Date Days
	  */
	public int getAutoDueDateDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AutoDueDateDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ConfidentialType AD_Reference_ID=340 */
	public static final int CONFIDENTIALTYPE_AD_Reference_ID=340;
	/** Public Information = A */
	public static final String CONFIDENTIALTYPE_PublicInformation = "A";
	/** Partner Confidential = C */
	public static final String CONFIDENTIALTYPE_PartnerConfidential = "C";
	/** Internal = I */
	public static final String CONFIDENTIALTYPE_Internal = "I";
	/** Private Information = P */
	public static final String CONFIDENTIALTYPE_PrivateInformation = "P";
	/** Set Confidentiality.
		@param ConfidentialType 
		Type of Confidentiality
	  */
	public void setConfidentialType (String ConfidentialType)
	{

		set_Value (COLUMNNAME_ConfidentialType, ConfidentialType);
	}

	/** Get Confidentiality.
		@return Type of Confidentiality
	  */
	public String getConfidentialType () 
	{
		return (String)get_Value(COLUMNNAME_ConfidentialType);
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

	/** Set Due Date Tolerance.
		@param DueDateTolerance 
		Tolerance in days between the Date Next Action and the date the request is regarded as overdue
	  */
	public void setDueDateTolerance (int DueDateTolerance)
	{
		set_Value (COLUMNNAME_DueDateTolerance, Integer.valueOf(DueDateTolerance));
	}

	/** Get Due Date Tolerance.
		@return Tolerance in days between the Date Next Action and the date the request is regarded as overdue
	  */
	public int getDueDateTolerance () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DueDateTolerance);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Create Change Request.
		@param IsAutoChangeRequest 
		Automatically create BOM (Engineering) Change Request
	  */
	public void setIsAutoChangeRequest (boolean IsAutoChangeRequest)
	{
		set_Value (COLUMNNAME_IsAutoChangeRequest, Boolean.valueOf(IsAutoChangeRequest));
	}

	/** Get Create Change Request.
		@return Automatically create BOM (Engineering) Change Request
	  */
	public boolean isAutoChangeRequest () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoChangeRequest);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Confidential Info.
		@param IsConfidentialInfo 
		Can enter confidential information
	  */
	public void setIsConfidentialInfo (boolean IsConfidentialInfo)
	{
		set_Value (COLUMNNAME_IsConfidentialInfo, Boolean.valueOf(IsConfidentialInfo));
	}

	/** Get Confidential Info.
		@return Can enter confidential information
	  */
	public boolean isConfidentialInfo () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfidentialInfo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set EMail when Due.
		@param IsEMailWhenDue 
		Send EMail when Request becomes due
	  */
	public void setIsEMailWhenDue (boolean IsEMailWhenDue)
	{
		set_Value (COLUMNNAME_IsEMailWhenDue, Boolean.valueOf(IsEMailWhenDue));
	}

	/** Get EMail when Due.
		@return Send EMail when Request becomes due
	  */
	public boolean isEMailWhenDue () 
	{
		Object oo = get_Value(COLUMNNAME_IsEMailWhenDue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set EMail when Overdue.
		@param IsEMailWhenOverdue 
		Send EMail when Request becomes overdue
	  */
	public void setIsEMailWhenOverdue (boolean IsEMailWhenOverdue)
	{
		set_Value (COLUMNNAME_IsEMailWhenOverdue, Boolean.valueOf(IsEMailWhenOverdue));
	}

	/** Get EMail when Overdue.
		@return Send EMail when Request becomes overdue
	  */
	public boolean isEMailWhenOverdue () 
	{
		Object oo = get_Value(COLUMNNAME_IsEMailWhenOverdue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Indexed.
		@param IsIndexed 
		Index the document for the internal search engine
	  */
	public void setIsIndexed (boolean IsIndexed)
	{
		set_Value (COLUMNNAME_IsIndexed, Boolean.valueOf(IsIndexed));
	}

	/** Get Indexed.
		@return Index the document for the internal search engine
	  */
	public boolean isIndexed () 
	{
		Object oo = get_Value(COLUMNNAME_IsIndexed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoiced.
		@param IsInvoiced 
		Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced)
	{
		set_Value (COLUMNNAME_IsInvoiced, Boolean.valueOf(IsInvoiced));
	}

	/** Get Invoiced.
		@return Is this invoiced?
	  */
	public boolean isInvoiced () 
	{
		Object oo = get_Value(COLUMNNAME_IsInvoiced);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Self-Service.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Self-Service.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
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

	/** Set Request Type.
		@param R_RequestType_ID 
		Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public void setR_RequestType_ID (int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_RequestType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
	}

	/** Get Request Type.
		@return Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public int getR_RequestType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_StatusCategory getR_StatusCategory() throws RuntimeException
    {
		return (I_R_StatusCategory)MTable.get(getCtx(), I_R_StatusCategory.Table_Name)
			.getPO(getR_StatusCategory_ID(), get_TrxName());	}

	/** Set Status Category.
		@param R_StatusCategory_ID 
		Request Status Category
	  */
	public void setR_StatusCategory_ID (int R_StatusCategory_ID)
	{
		if (R_StatusCategory_ID < 1) 
			set_Value (COLUMNNAME_R_StatusCategory_ID, null);
		else 
			set_Value (COLUMNNAME_R_StatusCategory_ID, Integer.valueOf(R_StatusCategory_ID));
	}

	/** Get Status Category.
		@return Request Status Category
	  */
	public int getR_StatusCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_StatusCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}