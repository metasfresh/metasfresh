// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_ElementValue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_ElementValue extends org.compiere.model.PO implements I_I_ElementValue, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1988684738L;

    /** Standard Constructor */
    public X_I_ElementValue (final Properties ctx, final int I_ElementValue_ID, @Nullable final String trxName)
    {
      super (ctx, I_ElementValue_ID, trxName);
    }

    /** Load Constructor */
    public X_I_ElementValue (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AccountSign AD_Reference_ID=118
	 * Reference name: C_ElementValue Account Sign
	 */
	public static final int ACCOUNTSIGN_AD_Reference_ID=118;
	/** Natural = N */
	public static final String ACCOUNTSIGN_Natural = "N";
	/** Debit = D */
	public static final String ACCOUNTSIGN_Debit = "D";
	/** Credit = C */
	public static final String ACCOUNTSIGN_Credit = "C";
	@Override
	public void setAccountSign (final @Nullable java.lang.String AccountSign)
	{
		set_Value (COLUMNNAME_AccountSign, AccountSign);
	}

	@Override
	public java.lang.String getAccountSign() 
	{
		return get_ValueAsString(COLUMNNAME_AccountSign);
	}

	/** 
	 * AccountType AD_Reference_ID=117
	 * Reference name: C_ElementValue AccountType
	 */
	public static final int ACCOUNTTYPE_AD_Reference_ID=117;
	/** Asset = A */
	public static final String ACCOUNTTYPE_Asset = "A";
	/** Liability = L */
	public static final String ACCOUNTTYPE_Liability = "L";
	/** Revenue = R */
	public static final String ACCOUNTTYPE_Revenue = "R";
	/** Expense = E */
	public static final String ACCOUNTTYPE_Expense = "E";
	/** OwnerSEquity = O */
	public static final String ACCOUNTTYPE_OwnerSEquity = "O";
	/** Memo = M */
	public static final String ACCOUNTTYPE_Memo = "M";
	@Override
	public void setAccountType (final @Nullable java.lang.String AccountType)
	{
		set_Value (COLUMNNAME_AccountType, AccountType);
	}

	@Override
	public java.lang.String getAccountType() 
	{
		return get_ValueAsString(COLUMNNAME_AccountType);
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(final org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (final int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, C_DataImport_Run_ID);
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
	}

	@Override
	public void setC_Element_ID (final int C_Element_ID)
	{
		if (C_Element_ID < 1) 
			set_Value (COLUMNNAME_C_Element_ID, null);
		else 
			set_Value (COLUMNNAME_C_Element_ID, C_Element_ID);
	}

	@Override
	public int getC_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Element_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getC_ElementValue()
	{
		return get_ValueAsPO(COLUMNNAME_C_ElementValue_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ElementValue(final org.compiere.model.I_C_ElementValue C_ElementValue)
	{
		set_ValueFromPO(COLUMNNAME_C_ElementValue_ID, org.compiere.model.I_C_ElementValue.class, C_ElementValue);
	}

	@Override
	public void setC_ElementValue_ID (final int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, C_ElementValue_ID);
	}

	@Override
	public int getC_ElementValue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ElementValue_ID);
	}

	@Override
	public void setDefault_Account (final @Nullable java.lang.String Default_Account)
	{
		set_Value (COLUMNNAME_Default_Account, Default_Account);
	}

	@Override
	public java.lang.String getDefault_Account() 
	{
		return get_ValueAsString(COLUMNNAME_Default_Account);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setElementName (final @Nullable java.lang.String ElementName)
	{
		set_Value (COLUMNNAME_ElementName, ElementName);
	}

	@Override
	public java.lang.String getElementName() 
	{
		return get_ValueAsString(COLUMNNAME_ElementName);
	}

	@Override
	public void setI_ElementValue_ID (final int I_ElementValue_ID)
	{
		if (I_ElementValue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_ElementValue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_ElementValue_ID, I_ElementValue_ID);
	}

	@Override
	public int getI_ElementValue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_ElementValue_ID);
	}

	@Override
	public void setI_ErrorMsg (final @Nullable java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_I_ErrorMsg);
	}

	@Override
	public void setI_IsImported (final boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public boolean isI_IsImported() 
	{
		return get_ValueAsBoolean(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (final @Nullable java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return get_ValueAsString(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (final int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, I_LineNo);
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	@Override
	public void setIsDocControlled (final boolean IsDocControlled)
	{
		set_Value (COLUMNNAME_IsDocControlled, IsDocControlled);
	}

	@Override
	public boolean isDocControlled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDocControlled);
	}

	@Override
	public void setIsSummary (final boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getParentElementValue()
	{
		return get_ValueAsPO(COLUMNNAME_ParentElementValue_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setParentElementValue(final org.compiere.model.I_C_ElementValue ParentElementValue)
	{
		set_ValueFromPO(COLUMNNAME_ParentElementValue_ID, org.compiere.model.I_C_ElementValue.class, ParentElementValue);
	}

	@Override
	public void setParentElementValue_ID (final int ParentElementValue_ID)
	{
		if (ParentElementValue_ID < 1) 
			set_Value (COLUMNNAME_ParentElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_ParentElementValue_ID, ParentElementValue_ID);
	}

	@Override
	public int getParentElementValue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ParentElementValue_ID);
	}

	@Override
	public void setParentValue (final @Nullable java.lang.String ParentValue)
	{
		set_Value (COLUMNNAME_ParentValue, ParentValue);
	}

	@Override
	public java.lang.String getParentValue() 
	{
		return get_ValueAsString(COLUMNNAME_ParentValue);
	}

	@Override
	public void setPostActual (final boolean PostActual)
	{
		set_Value (COLUMNNAME_PostActual, PostActual);
	}

	@Override
	public boolean isPostActual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PostActual);
	}

	@Override
	public void setPostBudget (final boolean PostBudget)
	{
		set_Value (COLUMNNAME_PostBudget, PostBudget);
	}

	@Override
	public boolean isPostBudget() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PostBudget);
	}

	@Override
	public void setPostEncumbrance (final boolean PostEncumbrance)
	{
		set_Value (COLUMNNAME_PostEncumbrance, PostEncumbrance);
	}

	@Override
	public boolean isPostEncumbrance() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PostEncumbrance);
	}

	@Override
	public void setPostStatistical (final boolean PostStatistical)
	{
		set_Value (COLUMNNAME_PostStatistical, PostStatistical);
	}

	@Override
	public boolean isPostStatistical() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PostStatistical);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}