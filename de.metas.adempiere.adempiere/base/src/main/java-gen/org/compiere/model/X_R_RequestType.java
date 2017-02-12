/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for R_RequestType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_R_RequestType extends org.compiere.model.PO implements I_R_RequestType, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 955384659L;

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
			setIsUseForPartnerRequestWindow (false);
// N
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


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Auto Due Date Days.
		@param AutoDueDateDays 
		Automatic Due Date Days
	  */
	@Override
	public void setAutoDueDateDays (int AutoDueDateDays)
	{
		set_Value (COLUMNNAME_AutoDueDateDays, Integer.valueOf(AutoDueDateDays));
	}

	/** Get Auto Due Date Days.
		@return Automatic Due Date Days
	  */
	@Override
	public int getAutoDueDateDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AutoDueDateDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * ConfidentialType AD_Reference_ID=340
	 * Reference name: R_Request Confidential
	 */
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
	@Override
	public void setConfidentialType (java.lang.String ConfidentialType)
	{

		set_Value (COLUMNNAME_ConfidentialType, ConfidentialType);
	}

	/** Get Confidentiality.
		@return Type of Confidentiality
	  */
	@Override
	public java.lang.String getConfidentialType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConfidentialType);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Due Date Tolerance.
		@param DueDateTolerance 
		Tolerance in days between the Date Next Action and the date the request is regarded as overdue
	  */
	@Override
	public void setDueDateTolerance (int DueDateTolerance)
	{
		set_Value (COLUMNNAME_DueDateTolerance, Integer.valueOf(DueDateTolerance));
	}

	/** Get Due Date Tolerance.
		@return Tolerance in days between the Date Next Action and the date the request is regarded as overdue
	  */
	@Override
	public int getDueDateTolerance () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DueDateTolerance);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Interner Name.
		@param InternalName 
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Create Change Request.
		@param IsAutoChangeRequest 
		Automatically create BOM (Engineering) Change Request
	  */
	@Override
	public void setIsAutoChangeRequest (boolean IsAutoChangeRequest)
	{
		set_Value (COLUMNNAME_IsAutoChangeRequest, Boolean.valueOf(IsAutoChangeRequest));
	}

	/** Get Create Change Request.
		@return Automatically create BOM (Engineering) Change Request
	  */
	@Override
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
	@Override
	public void setIsConfidentialInfo (boolean IsConfidentialInfo)
	{
		set_Value (COLUMNNAME_IsConfidentialInfo, Boolean.valueOf(IsConfidentialInfo));
	}

	/** Get Confidential Info.
		@return Can enter confidential information
	  */
	@Override
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

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
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
	@Override
	public void setIsEMailWhenDue (boolean IsEMailWhenDue)
	{
		set_Value (COLUMNNAME_IsEMailWhenDue, Boolean.valueOf(IsEMailWhenDue));
	}

	/** Get EMail when Due.
		@return Send EMail when Request becomes due
	  */
	@Override
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
	@Override
	public void setIsEMailWhenOverdue (boolean IsEMailWhenOverdue)
	{
		set_Value (COLUMNNAME_IsEMailWhenOverdue, Boolean.valueOf(IsEMailWhenOverdue));
	}

	/** Get EMail when Overdue.
		@return Send EMail when Request becomes overdue
	  */
	@Override
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
	@Override
	public void setIsIndexed (boolean IsIndexed)
	{
		set_Value (COLUMNNAME_IsIndexed, Boolean.valueOf(IsIndexed));
	}

	/** Get Indexed.
		@return Index the document for the internal search engine
	  */
	@Override
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
	@Override
	public void setIsInvoiced (boolean IsInvoiced)
	{
		set_Value (COLUMNNAME_IsInvoiced, Boolean.valueOf(IsInvoiced));
	}

	/** Get Invoiced.
		@return Is this invoiced?
	  */
	@Override
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
	@Override
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Self-Service.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
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

	/** Set IsUseForPartnerRequestWindow.
		@param IsUseForPartnerRequestWindow 
		Flag that tells if the R_Request entries of this type will be displayed or not in the business partner request window (Vorgang)
	  */
	@Override
	public void setIsUseForPartnerRequestWindow (boolean IsUseForPartnerRequestWindow)
	{
		set_Value (COLUMNNAME_IsUseForPartnerRequestWindow, Boolean.valueOf(IsUseForPartnerRequestWindow));
	}

	/** Get IsUseForPartnerRequestWindow.
		@return Flag that tells if the R_Request entries of this type will be displayed or not in the business partner request window (Vorgang)
	  */
	@Override
	public boolean isUseForPartnerRequestWindow () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseForPartnerRequestWindow);
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
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Request Type.
		@param R_RequestType_ID 
		Type of request (e.g. Inquiry, Complaint, ..)
	  */
	@Override
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
	@Override
	public int getR_RequestType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_StatusCategory getR_StatusCategory() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_StatusCategory_ID, org.compiere.model.I_R_StatusCategory.class);
	}

	@Override
	public void setR_StatusCategory(org.compiere.model.I_R_StatusCategory R_StatusCategory)
	{
		set_ValueFromPO(COLUMNNAME_R_StatusCategory_ID, org.compiere.model.I_R_StatusCategory.class, R_StatusCategory);
	}

	/** Set Status Category.
		@param R_StatusCategory_ID 
		Request Status Category
	  */
	@Override
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
	@Override
	public int getR_StatusCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_StatusCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}