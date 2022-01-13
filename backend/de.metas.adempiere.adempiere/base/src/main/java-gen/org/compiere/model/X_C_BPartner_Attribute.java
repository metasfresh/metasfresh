// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Attribute extends org.compiere.model.PO implements I_C_BPartner_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 14649709L;

    /** Standard Constructor */
    public X_C_BPartner_Attribute (final Properties ctx, final int C_BPartner_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * Attribute AD_Reference_ID=540749
	 * Reference name: C_BPartner_Attribute
	 */
	public static final int ATTRIBUTE_AD_Reference_ID=540749;
	/** Verband D = K_Verband_D */
	public static final String ATTRIBUTE_VerbandD = "K_Verband_D";
	/** Firma = K_Firma */
	public static final String ATTRIBUTE_Firma = "K_Firma";
	/** Bildungsinstitut = K_Bildungsinstitut */
	public static final String ATTRIBUTE_Bildungsinstitut = "K_Bildungsinstitut";
	/** Schule = K_Schule */
	public static final String ATTRIBUTE_Schule = "K_Schule";
	/** Oberstufe = K_Oberstufe */
	public static final String ATTRIBUTE_Oberstufe = "K_Oberstufe";
	/** Gymnasium = K_Gymnasium */
	public static final String ATTRIBUTE_Gymnasium = "K_Gymnasium";
	/** BIZ_Berufsberatung  = K_BIZ_Berufsberatung */
	public static final String ATTRIBUTE_BIZ_Berufsberatung = "K_BIZ_Berufsberatung";
	/** Partner = K_Partner */
	public static final String ATTRIBUTE_Partner = "K_Partner";
	/** Lieferant = K_Lieferant */
	public static final String ATTRIBUTE_Lieferant = "K_Lieferant";
	/** Behörden = K_Behörden */
	public static final String ATTRIBUTE_Behoerden = "K_Behörden";
	/** Sponsor = K_Sponsor */
	public static final String ATTRIBUTE_Sponsor = "K_Sponsor";
	/** Massenmedien = K_Massenmedien */
	public static final String ATTRIBUTE_Massenmedien = "K_Massenmedien";
	@Override
	public void setAttribute (final java.lang.String Attribute)
	{
		set_Value (COLUMNNAME_Attribute, Attribute);
	}

	@Override
	public java.lang.String getAttribute() 
	{
		return get_ValueAsString(COLUMNNAME_Attribute);
	}

	@Override
	public void setC_BPartner_Attribute_ID (final int C_BPartner_Attribute_ID)
	{
		if (C_BPartner_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Attribute_ID, C_BPartner_Attribute_ID);
	}

	@Override
	public int getC_BPartner_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Attribute_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}
}