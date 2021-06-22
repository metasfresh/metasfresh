// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Fiscal_Representation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Fiscal_Representation extends org.compiere.model.PO implements I_C_Fiscal_Representation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -233760649L;

    /** Standard Constructor */
    public X_C_Fiscal_Representation (final Properties ctx, final int C_Fiscal_Representation_ID, @Nullable final String trxName)
    {
      super (ctx, C_Fiscal_Representation_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Fiscal_Representation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_BPartner_Representative_ID (final int C_BPartner_Representative_ID)
	{
		if (C_BPartner_Representative_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Representative_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Representative_ID, C_BPartner_Representative_ID);
	}

	@Override
	public int getC_BPartner_Representative_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Representative_ID);
	}

	@Override
	public void setC_Fiscal_Representation_ID (final int C_Fiscal_Representation_ID)
	{
		if (C_Fiscal_Representation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Fiscal_Representation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Fiscal_Representation_ID, C_Fiscal_Representation_ID);
	}

	@Override
	public int getC_Fiscal_Representation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Fiscal_Representation_ID);
	}

	@Override
	public void setTaxID (final @Nullable java.lang.String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	@Override
	public java.lang.String getTaxID() 
	{
		return get_ValueAsString(COLUMNNAME_TaxID);
	}

	@Override
	public org.compiere.model.I_C_Country getTo_Country()
	{
		return get_ValueAsPO(COLUMNNAME_To_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setTo_Country(final org.compiere.model.I_C_Country To_Country)
	{
		set_ValueFromPO(COLUMNNAME_To_Country_ID, org.compiere.model.I_C_Country.class, To_Country);
	}

	@Override
	public void setTo_Country_ID (final int To_Country_ID)
	{
		if (To_Country_ID < 1) 
			set_Value (COLUMNNAME_To_Country_ID, null);
		else 
			set_Value (COLUMNNAME_To_Country_ID, To_Country_ID);
	}

	@Override
	public int getTo_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_To_Country_ID);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}

	@Override
	public void setVATaxID (final java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	@Override
	public java.lang.String getVATaxID() 
	{
		return get_ValueAsString(COLUMNNAME_VATaxID);
	}
}