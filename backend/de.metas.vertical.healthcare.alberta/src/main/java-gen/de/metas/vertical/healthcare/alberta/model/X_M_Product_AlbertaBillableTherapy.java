// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_AlbertaBillableTherapy
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_AlbertaBillableTherapy extends org.compiere.model.PO implements I_M_Product_AlbertaBillableTherapy, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -2108783737L;

    /** Standard Constructor */
    public X_M_Product_AlbertaBillableTherapy (final Properties ctx, final int M_Product_AlbertaBillableTherapy_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_AlbertaBillableTherapy_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_AlbertaBillableTherapy (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Product_AlbertaBillableTherapy_ID (final int M_Product_AlbertaBillableTherapy_ID)
	{
		if (M_Product_AlbertaBillableTherapy_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_AlbertaBillableTherapy_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_AlbertaBillableTherapy_ID, M_Product_AlbertaBillableTherapy_ID);
	}

	@Override
	public int getM_Product_AlbertaBillableTherapy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_AlbertaBillableTherapy_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	/** 
	 * Therapy AD_Reference_ID=541282
	 * Reference name: Therapy
	 */
	public static final int THERAPY_AD_Reference_ID=541282;
	/** Unknown = 0 */
	public static final String THERAPY_Unknown = "0";
	/** ParenteralNutrition = 1 */
	public static final String THERAPY_ParenteralNutrition = "1";
	/** EnteralNutrition = 2 */
	public static final String THERAPY_EnteralNutrition = "2";
	/** Stoma = 3 */
	public static final String THERAPY_Stoma = "3";
	/** Tracheostoma = 4 */
	public static final String THERAPY_Tracheostoma = "4";
	/** Inkontinenz ableitend = 5 */
	public static final String THERAPY_InkontinenzAbleitend = "5";
	/** Wundversorgung = 6 */
	public static final String THERAPY_Wundversorgung = "6";
	/** IV-Therapien = 7 */
	public static final String THERAPY_IV_Therapien = "7";
	/** Beatmung = 8 */
	public static final String THERAPY_Beatmung = "8";
	/** Sonstiges = 9 */
	public static final String THERAPY_Sonstiges = "9";
	/** OSA = 10 */
	public static final String THERAPY_OSA = "10";
	/** Hustenhilfen = 11 */
	public static final String THERAPY_Hustenhilfen = "11";
	/** Absaugung = 12 */
	public static final String THERAPY_Absaugung = "12";
	/** Patientenüberwachung = 13 */
	public static final String THERAPY_Patientenueberwachung = "13";
	/** Sauerstoff = 14 */
	public static final String THERAPY_Sauerstoff = "14";
	/** Inhalations- und Atemtherapie = 15 */
	public static final String THERAPY_Inhalations_UndAtemtherapie = "15";
	/** Lagerungshilfsmittel = 16 */
	public static final String THERAPY_Lagerungshilfsmittel = "16";
	/** Schmerztherapie = 17 */
	public static final String THERAPY_Schmerztherapie = "17";
	@Override
	public void setTherapy (final String Therapy)
	{
		set_Value (COLUMNNAME_Therapy, Therapy);
	}

	@Override
	public String getTherapy()
	{
		return get_ValueAsString(COLUMNNAME_Therapy);
	}

	@Override
	public void setValue (final @Nullable String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}