// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_QuickInput_Attributes
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_QuickInput_Attributes extends org.compiere.model.PO implements I_C_BPartner_QuickInput_Attributes, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 794457220L;

    /** Standard Constructor */
    public X_C_BPartner_QuickInput_Attributes (final Properties ctx, final int C_BPartner_QuickInput_Attributes_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_QuickInput_Attributes_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_QuickInput_Attributes (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * attributes AD_Reference_ID=540749
	 * Reference name: C_BPartner_Attribute
	 */
	public static final int ATTRIBUTES_AD_Reference_ID=540749;
	/** Verband D = K_Verband_D */
	public static final String ATTRIBUTES_VerbandD = "K_Verband_D";
	/** Firma = K_Firma */
	public static final String ATTRIBUTES_Firma = "K_Firma";
	/** Bildungsinstitut = K_Bildungsinstitut */
	public static final String ATTRIBUTES_Bildungsinstitut = "K_Bildungsinstitut";
	/** Schule = K_Schule */
	public static final String ATTRIBUTES_Schule = "K_Schule";
	/** Oberstufe = K_Oberstufe */
	public static final String ATTRIBUTES_Oberstufe = "K_Oberstufe";
	/** Gymnasium = K_Gymnasium */
	public static final String ATTRIBUTES_Gymnasium = "K_Gymnasium";
	/** BIZ_Berufsberatung  = K_BIZ_Berufsberatung */
	public static final String ATTRIBUTES_BIZ_Berufsberatung = "K_BIZ_Berufsberatung";
	/** Partner = K_Partner */
	public static final String ATTRIBUTES_Partner = "K_Partner";
	/** Lieferant = K_Lieferant */
	public static final String ATTRIBUTES_Lieferant = "K_Lieferant";
	/** Behörden = K_Behörden */
	public static final String ATTRIBUTES_Behoerden = "K_Behörden";
	/** Sponsor = K_Sponsor */
	public static final String ATTRIBUTES_Sponsor = "K_Sponsor";
	/** Massenmedien = K_Massenmedien */
	public static final String ATTRIBUTES_Massenmedien = "K_Massenmedien";
	@Override
	public void setattributes (final java.lang.String attributes)
	{
		set_Value (COLUMNNAME_attributes, attributes);
	}

	@Override
	public java.lang.String getattributes() 
	{
		return get_ValueAsString(COLUMNNAME_attributes);
	}

	@Override
	public void setC_BPartner_QuickInput_Attributes_ID (final int C_BPartner_QuickInput_Attributes_ID)
	{
		if (C_BPartner_QuickInput_Attributes_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_Attributes_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_Attributes_ID, C_BPartner_QuickInput_Attributes_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_Attributes_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_Attributes_ID);
	}

	@Override
	public org.compiere.model.I_C_BPartner_QuickInput getC_BPartner_QuickInput()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_QuickInput_ID, org.compiere.model.I_C_BPartner_QuickInput.class);
	}

	@Override
	public void setC_BPartner_QuickInput(final org.compiere.model.I_C_BPartner_QuickInput C_BPartner_QuickInput)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_QuickInput_ID, org.compiere.model.I_C_BPartner_QuickInput.class, C_BPartner_QuickInput);
	}

	@Override
	public void setC_BPartner_QuickInput_ID (final int C_BPartner_QuickInput_ID)
	{
		if (C_BPartner_QuickInput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_ID, C_BPartner_QuickInput_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_ID);
	}
}