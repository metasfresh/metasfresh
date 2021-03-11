// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_AlbertaTherapy
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_AlbertaTherapy extends org.compiere.model.PO implements I_M_Product_AlbertaTherapy, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 97348869L;

    /** Standard Constructor */
    public X_M_Product_AlbertaTherapy (final Properties ctx, final int M_Product_AlbertaTherapy_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_AlbertaTherapy_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_AlbertaTherapy (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Product_AlbertaTherapy_ID (final int M_Product_AlbertaTherapy_ID)
	{
		if (M_Product_AlbertaTherapy_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_AlbertaTherapy_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_AlbertaTherapy_ID, M_Product_AlbertaTherapy_ID);
	}

	@Override
	public int getM_Product_AlbertaTherapy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_AlbertaTherapy_ID);
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
	 * Therapy AD_Reference_ID=540709
	 * Reference name: MD_Candidate_BusinessCase
	 */
	public static final int THERAPY_AD_Reference_ID=540709;
	/** DISTRIBUTION = DISTRIBUTION */
	public static final String THERAPY_DISTRIBUTION = "DISTRIBUTION";
	/** PRODUCTION = PRODUCTION */
	public static final String THERAPY_PRODUCTION = "PRODUCTION";
	/** RECEIPT = RECEIPT */
	public static final String THERAPY_RECEIPT = "RECEIPT";
	/** SHIPMENT = SHIPMENT */
	public static final String THERAPY_SHIPMENT = "SHIPMENT";
	/** FORECAST = FORECAST */
	public static final String THERAPY_FORECAST = "FORECAST";
	/** PURCHASE = PURCHASE */
	public static final String THERAPY_PURCHASE = "PURCHASE";
	/** INVENTORY = INVENTORY */
	public static final String THERAPY_INVENTORY = "INVENTORY";
	@Override
	public void setTherapy (final java.lang.String Therapy)
	{
		set_Value (COLUMNNAME_Therapy, Therapy);
	}

	@Override
	public java.lang.String getTherapy() 
	{
		return get_ValueAsString(COLUMNNAME_Therapy);
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