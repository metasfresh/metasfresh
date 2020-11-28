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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for A_Asset_Reval_Entry
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_A_Asset_Reval_Entry extends PO implements I_A_Asset_Reval_Entry, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_A_Asset_Reval_Entry (Properties ctx, int A_Asset_Reval_Entry_ID, String trxName)
    {
      super (ctx, A_Asset_Reval_Entry_ID, trxName);
      /** if (A_Asset_Reval_Entry_ID == 0)
        {
			setA_Asset_Reval_Entry_ID (0);
			setA_Effective_Date (new Timestamp( System.currentTimeMillis() ));
			setA_Reval_Cal_Method (null);
			setA_Reval_Effective_Date (null);
			setA_Reval_Multiplier (null);
			setA_Rev_Code (null);
			setC_Currency_ID (0);
			setDescription (null);
			setDocumentNo (null);
			setPostingType (null);
			setProcessed (false);
			setProcessing (false);
        } */
    }

    /** Load Constructor */
    public X_A_Asset_Reval_Entry (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_A_Asset_Reval_Entry[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set A_Asset_Reval_Entry_ID.
		@param A_Asset_Reval_Entry_ID A_Asset_Reval_Entry_ID	  */
	public void setA_Asset_Reval_Entry_ID (int A_Asset_Reval_Entry_ID)
	{
		if (A_Asset_Reval_Entry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Reval_Entry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Reval_Entry_ID, Integer.valueOf(A_Asset_Reval_Entry_ID));
	}

	/** Get A_Asset_Reval_Entry_ID.
		@return A_Asset_Reval_Entry_ID	  */
	public int getA_Asset_Reval_Entry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Reval_Entry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_Asset_Reval_Entry_ID()));
    }

	/** Set A_Effective_Date.
		@param A_Effective_Date A_Effective_Date	  */
	public void setA_Effective_Date (Timestamp A_Effective_Date)
	{
		set_Value (COLUMNNAME_A_Effective_Date, A_Effective_Date);
	}

	/** Get A_Effective_Date.
		@return A_Effective_Date	  */
	public Timestamp getA_Effective_Date () 
	{
		return (Timestamp)get_Value(COLUMNNAME_A_Effective_Date);
	}

	/** A_Reval_Cal_Method AD_Reference_ID=53259 */
	public static final int A_REVAL_CAL_METHOD_AD_Reference_ID=53259;
	/** Default = DFT */
	public static final String A_REVAL_CAL_METHOD_Default = "DFT";
	/** Inception to date = IDF */
	public static final String A_REVAL_CAL_METHOD_InceptionToDate = "IDF";
	/** Year Balances = YBF */
	public static final String A_REVAL_CAL_METHOD_YearBalances = "YBF";
	/** Set Revaluation Calculation Method.
		@param A_Reval_Cal_Method Revaluation Calculation Method	  */
	public void setA_Reval_Cal_Method (String A_Reval_Cal_Method)
	{

		set_Value (COLUMNNAME_A_Reval_Cal_Method, A_Reval_Cal_Method);
	}

	/** Get Revaluation Calculation Method.
		@return Revaluation Calculation Method	  */
	public String getA_Reval_Cal_Method () 
	{
		return (String)get_Value(COLUMNNAME_A_Reval_Cal_Method);
	}

	/** A_Reval_Effective_Date AD_Reference_ID=53261 */
	public static final int A_REVAL_EFFECTIVE_DATE_AD_Reference_ID=53261;
	/** Date Aquired = DA */
	public static final String A_REVAL_EFFECTIVE_DATE_DateAquired = "DA";
	/** Revaluation Date = RD */
	public static final String A_REVAL_EFFECTIVE_DATE_RevaluationDate = "RD";
	/** Date Depreciation Started = SD */
	public static final String A_REVAL_EFFECTIVE_DATE_DateDepreciationStarted = "SD";
	/** Set A_Reval_Effective_Date.
		@param A_Reval_Effective_Date A_Reval_Effective_Date	  */
	public void setA_Reval_Effective_Date (String A_Reval_Effective_Date)
	{

		set_Value (COLUMNNAME_A_Reval_Effective_Date, A_Reval_Effective_Date);
	}

	/** Get A_Reval_Effective_Date.
		@return A_Reval_Effective_Date	  */
	public String getA_Reval_Effective_Date () 
	{
		return (String)get_Value(COLUMNNAME_A_Reval_Effective_Date);
	}

	/** A_Reval_Multiplier AD_Reference_ID=53260 */
	public static final int A_REVAL_MULTIPLIER_AD_Reference_ID=53260;
	/** Factor = FAC */
	public static final String A_REVAL_MULTIPLIER_Factor = "FAC";
	/** Index = IND */
	public static final String A_REVAL_MULTIPLIER_Index = "IND";
	/** Set A_Reval_Multiplier.
		@param A_Reval_Multiplier A_Reval_Multiplier	  */
	public void setA_Reval_Multiplier (String A_Reval_Multiplier)
	{

		set_Value (COLUMNNAME_A_Reval_Multiplier, A_Reval_Multiplier);
	}

	/** Get A_Reval_Multiplier.
		@return A_Reval_Multiplier	  */
	public String getA_Reval_Multiplier () 
	{
		return (String)get_Value(COLUMNNAME_A_Reval_Multiplier);
	}

	/** A_Rev_Code AD_Reference_ID=53262 */
	public static final int A_REV_CODE_AD_Reference_ID=53262;
	/** Revaluation Code #1 = R01 */
	public static final String A_REV_CODE_RevaluationCode1 = "R01";
	/** Revaluation Code #2 = R02 */
	public static final String A_REV_CODE_RevaluationCode2 = "R02";
	/** Revaluation Code #3 = R03 */
	public static final String A_REV_CODE_RevaluationCode3 = "R03";
	/** Set A_Rev_Code.
		@param A_Rev_Code A_Rev_Code	  */
	public void setA_Rev_Code (String A_Rev_Code)
	{

		set_Value (COLUMNNAME_A_Rev_Code, A_Rev_Code);
	}

	/** Get A_Rev_Code.
		@return A_Rev_Code	  */
	public String getA_Rev_Code () 
	{
		return (String)get_Value(COLUMNNAME_A_Rev_Code);
	}

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException
    {
		return (I_C_AcctSchema)MTable.get(getCtx(), I_C_AcctSchema.Table_Name)
			.getPO(getC_AcctSchema_ID(), get_TrxName());	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Period getC_Period() throws RuntimeException
    {
		return (I_C_Period)MTable.get(getCtx(), I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	public I_GL_Category getGL_Category() throws RuntimeException
    {
		return (I_GL_Category)MTable.get(getCtx(), I_GL_Category.Table_Name)
			.getPO(getGL_Category_ID(), get_TrxName());	}

	/** Set GL Category.
		@param GL_Category_ID 
		General Ledger Category
	  */
	public void setGL_Category_ID (int GL_Category_ID)
	{
		if (GL_Category_ID < 1) 
			set_Value (COLUMNNAME_GL_Category_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Category_ID, Integer.valueOf(GL_Category_ID));
	}

	/** Get GL Category.
		@return General Ledger Category
	  */
	public int getGL_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PostingType AD_Reference_ID=125 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Set PostingType.
		@param PostingType 
		The type of posted amount for the transaction
	  */
	public void setPostingType (String PostingType)
	{

		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	/** Get PostingType.
		@return The type of posted amount for the transaction
	  */
	public String getPostingType () 
	{
		return (String)get_Value(COLUMNNAME_PostingType);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
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
}