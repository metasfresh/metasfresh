// Generated Model - DO NOT CHANGE
package de.metas.acct.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for SAP_GLJournalLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SAP_GLJournalLine extends org.compiere.model.PO implements I_SAP_GLJournalLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2011207627L;

    /** Standard Constructor */
    public X_SAP_GLJournalLine (final Properties ctx, final int SAP_GLJournalLine_ID, @Nullable final String trxName)
    {
      super (ctx, SAP_GLJournalLine_ID, trxName);
    }

    /** Load Constructor */
    public X_SAP_GLJournalLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAmount (final BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtAcct (final BigDecimal AmtAcct)
	{
		set_Value (COLUMNNAME_AmtAcct, AmtAcct);
	}

	@Override
	public BigDecimal getAmtAcct() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcct);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getC_ValidCombination()
	{
		return get_ValueAsPO(COLUMNNAME_C_ValidCombination_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setC_ValidCombination(final org.compiere.model.I_C_ValidCombination C_ValidCombination)
	{
		set_ValueFromPO(COLUMNNAME_C_ValidCombination_ID, org.compiere.model.I_C_ValidCombination.class, C_ValidCombination);
	}

	@Override
	public void setC_ValidCombination_ID (final int C_ValidCombination_ID)
	{
		if (C_ValidCombination_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombination_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombination_ID, C_ValidCombination_ID);
	}

	@Override
	public int getC_ValidCombination_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ValidCombination_ID);
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
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	@Override
	public de.metas.acct.model.I_SAP_GLJournalLine getParent()
	{
		return get_ValueAsPO(COLUMNNAME_Parent_ID, de.metas.acct.model.I_SAP_GLJournalLine.class);
	}

	@Override
	public void setParent(final de.metas.acct.model.I_SAP_GLJournalLine Parent)
	{
		set_ValueFromPO(COLUMNNAME_Parent_ID, de.metas.acct.model.I_SAP_GLJournalLine.class, Parent);
	}

	@Override
	public void setParent_ID (final int Parent_ID)
	{
		if (Parent_ID < 1) 
			set_Value (COLUMNNAME_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_ID, Parent_ID);
	}

	@Override
	public int getParent_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_ID);
	}

	/** 
	 * PostingSign AD_Reference_ID=541699
	 * Reference name: PostingSign
	 */
	public static final int POSTINGSIGN_AD_Reference_ID=541699;
	/** DR = D */
	public static final String POSTINGSIGN_DR = "D";
	/** CR = C */
	public static final String POSTINGSIGN_CR = "C";
	@Override
	public void setPostingSign (final java.lang.String PostingSign)
	{
		set_Value (COLUMNNAME_PostingSign, PostingSign);
	}

	@Override
	public java.lang.String getPostingSign() 
	{
		return get_ValueAsString(COLUMNNAME_PostingSign);
	}

	@Override
	public de.metas.acct.model.I_SAP_GLJournal getSAP_GLJournal()
	{
		return get_ValueAsPO(COLUMNNAME_SAP_GLJournal_ID, de.metas.acct.model.I_SAP_GLJournal.class);
	}

	@Override
	public void setSAP_GLJournal(final de.metas.acct.model.I_SAP_GLJournal SAP_GLJournal)
	{
		set_ValueFromPO(COLUMNNAME_SAP_GLJournal_ID, de.metas.acct.model.I_SAP_GLJournal.class, SAP_GLJournal);
	}

	@Override
	public void setSAP_GLJournal_ID (final int SAP_GLJournal_ID)
	{
		if (SAP_GLJournal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournal_ID, SAP_GLJournal_ID);
	}

	@Override
	public int getSAP_GLJournal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SAP_GLJournal_ID);
	}

	@Override
	public void setSAP_GLJournalLine_ID (final int SAP_GLJournalLine_ID)
	{
		if (SAP_GLJournalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournalLine_ID, SAP_GLJournalLine_ID);
	}

	@Override
	public int getSAP_GLJournalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SAP_GLJournalLine_ID);
	}
}