// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Quality_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Quality_Attribute extends org.compiere.model.PO implements I_M_Quality_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2034788831L;

    /** Standard Constructor */
    public X_M_Quality_Attribute (final Properties ctx, final int M_Quality_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, M_Quality_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Quality_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Quality_Attribute_ID (final int M_Quality_Attribute_ID)
	{
		if (M_Quality_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Quality_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Quality_Attribute_ID, M_Quality_Attribute_ID);
	}

	@Override
	public int getM_Quality_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Quality_Attribute_ID);
	}

	@Override
	public org.compiere.model.I_M_Quality getM_Quality()
	{
		return get_ValueAsPO(COLUMNNAME_M_Quality_ID, org.compiere.model.I_M_Quality.class);
	}

	@Override
	public void setM_Quality(final org.compiere.model.I_M_Quality M_Quality)
	{
		set_ValueFromPO(COLUMNNAME_M_Quality_ID, org.compiere.model.I_M_Quality.class, M_Quality);
	}

	@Override
	public void setM_Quality_ID (final int M_Quality_ID)
	{
		if (M_Quality_ID < 1) 
			set_Value (COLUMNNAME_M_Quality_ID, null);
		else 
			set_Value (COLUMNNAME_M_Quality_ID, M_Quality_ID);
	}

	@Override
	public int getM_Quality_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Quality_ID);
	}

	/** 
	 * QualityAttribute AD_Reference_ID=541509
	 * Reference name: M_Quality_Attribute
	 */
	public static final int QUALITYATTRIBUTE_AD_Reference_ID=541509;
	/** Allergene (ALBA) = Allergene_ALBA */
	public static final String QUALITYATTRIBUTE_AllergeneALBA = "Allergene_ALBA";
	/** Allergene (EU) = Allergene_EU */
	public static final String QUALITYATTRIBUTE_AllergeneEU = "Allergene_EU";
	/** Bearbeitet Bio = BearbeitetBio */
	public static final String QUALITYATTRIBUTE_BearbeitetBio = "BearbeitetBio";
	/** Gekühlt = Gekuhlt */
	public static final String QUALITYATTRIBUTE_Gekuehlt = "Gekuhlt";
	/** Gesiebt = Gesiebt */
	public static final String QUALITYATTRIBUTE_Gesiebt = "Gesiebt";
	/** Halal = Halal */
	public static final String QUALITYATTRIBUTE_Halal = "Halal";
	/** Keimreduziert = Keimreduziert */
	public static final String QUALITYATTRIBUTE_Keimreduziert = "Keimreduziert";
	/** Kosher = Kosher */
	public static final String QUALITYATTRIBUTE_Kosher = "Kosher";
	/** Metalldetektiert = Metalldetektiert */
	public static final String QUALITYATTRIBUTE_Metalldetektiert = "Metalldetektiert";
	/** Nicht Keimreduziert = NichtKeimreduziert */
	public static final String QUALITYATTRIBUTE_NichtKeimreduziert = "NichtKeimreduziert";
	/** Non Pork-Free = NonPorkFree */
	public static final String QUALITYATTRIBUTE_NonPork_Free = "NonPorkFree";
	/** OIP = OIP */
	public static final String QUALITYATTRIBUTE_OIP = "OIP";
	/** Pork-Free = PorkFree */
	public static final String QUALITYATTRIBUTE_Pork_Free = "PorkFree";
	/** V-Label = VLabel */
	public static final String QUALITYATTRIBUTE_V_Label = "VLabel";
	/** VLOG = VLOG */
	public static final String QUALITYATTRIBUTE_VLOG = "VLOG";
	/** EU-Landwirtschaft = EU-Agriculture */
	public static final String QUALITYATTRIBUTE_EU_Landwirtschaft = "EU-Agriculture";
	/** Nicht-EU-Landwirtschaft = Non-EU-Agriculture */
	public static final String QUALITYATTRIBUTE_Nicht_EU_Landwirtschaft = "Non-EU-Agriculture";
	/** EU-/Nicht-EU-Landwirtschaft = EU/Non-EU-Agriculture */
	public static final String QUALITYATTRIBUTE_EU_Nicht_EU_Landwirtschaft = "EU/Non-EU-Agriculture";
	@Override
	public void setQualityAttribute (final @Nullable java.lang.String QualityAttribute)
	{
		set_Value (COLUMNNAME_QualityAttribute, QualityAttribute);
	}

	@Override
	public java.lang.String getQualityAttribute() 
	{
		return get_ValueAsString(COLUMNNAME_QualityAttribute);
	}
}