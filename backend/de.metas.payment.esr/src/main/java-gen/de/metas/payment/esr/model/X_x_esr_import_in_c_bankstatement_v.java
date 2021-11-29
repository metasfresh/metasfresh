// Generated Model - DO NOT CHANGE
package de.metas.payment.esr.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for x_esr_import_in_c_bankstatement_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_x_esr_import_in_c_bankstatement_v extends org.compiere.model.PO implements I_x_esr_import_in_c_bankstatement_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 767880106L;

    /** Standard Constructor */
    public X_x_esr_import_in_c_bankstatement_v (final Properties ctx, final int x_esr_import_in_c_bankstatement_v_ID, @Nullable final String trxName)
    {
      super (ctx, x_esr_import_in_c_bankstatement_v_ID, trxName);
    }

    /** Load Constructor */
    public X_x_esr_import_in_c_bankstatement_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BankStatement_ID (final int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, C_BankStatement_ID);
	}

	@Override
	public int getC_BankStatement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatement_ID);
	}

	@Override
	public void setDateDoc (final @Nullable java.sql.Timestamp DateDoc)
	{
		set_ValueNoCheck (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public de.metas.payment.esr.model.I_ESR_Import getESR_Import()
	{
		return get_ValueAsPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class);
	}

	@Override
	public void setESR_Import(final de.metas.payment.esr.model.I_ESR_Import ESR_Import)
	{
		set_ValueFromPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class, ESR_Import);
	}

	@Override
	public void setESR_Import_ID (final int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, ESR_Import_ID);
	}

	@Override
	public int getESR_Import_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_Import_ID);
	}
}