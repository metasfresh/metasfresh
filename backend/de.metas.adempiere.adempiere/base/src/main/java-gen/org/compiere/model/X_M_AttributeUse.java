// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_AttributeUse
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_AttributeUse extends org.compiere.model.PO implements I_M_AttributeUse, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -56901902L;

    /** Standard Constructor */
    public X_M_AttributeUse (final Properties ctx, final int M_AttributeUse_ID, @Nullable final String trxName)
    {
      super (ctx, M_AttributeUse_ID, trxName);
    }

    /** Load Constructor */
    public X_M_AttributeUse (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * MandatoryOnManufacturing AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MANDATORYONMANUFACTURING_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MANDATORYONMANUFACTURING_Yes = "Y";
	/** No = N */
	public static final String MANDATORYONMANUFACTURING_No = "N";
	@Override
	public void setMandatoryOnManufacturing (final @Nullable java.lang.String MandatoryOnManufacturing)
	{
		set_Value (COLUMNNAME_MandatoryOnManufacturing, MandatoryOnManufacturing);
	}

	@Override
	public java.lang.String getMandatoryOnManufacturing() 
	{
		return get_ValueAsString(COLUMNNAME_MandatoryOnManufacturing);
	}

	/** 
	 * MandatoryOnPicking AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MANDATORYONPICKING_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MANDATORYONPICKING_Yes = "Y";
	/** No = N */
	public static final String MANDATORYONPICKING_No = "N";
	@Override
	public void setMandatoryOnPicking (final @Nullable java.lang.String MandatoryOnPicking)
	{
		set_Value (COLUMNNAME_MandatoryOnPicking, MandatoryOnPicking);
	}

	@Override
	public java.lang.String getMandatoryOnPicking() 
	{
		return get_ValueAsString(COLUMNNAME_MandatoryOnPicking);
	}

	/** 
	 * MandatoryOnReceipt AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MANDATORYONRECEIPT_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MANDATORYONRECEIPT_Yes = "Y";
	/** No = N */
	public static final String MANDATORYONRECEIPT_No = "N";
	@Override
	public void setMandatoryOnReceipt (final @Nullable java.lang.String MandatoryOnReceipt)
	{
		set_Value (COLUMNNAME_MandatoryOnReceipt, MandatoryOnReceipt);
	}

	@Override
	public java.lang.String getMandatoryOnReceipt() 
	{
		return get_ValueAsString(COLUMNNAME_MandatoryOnReceipt);
	}

	/** 
	 * MandatoryOnShipment AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MANDATORYONSHIPMENT_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MANDATORYONSHIPMENT_Yes = "Y";
	/** No = N */
	public static final String MANDATORYONSHIPMENT_No = "N";
	@Override
	public void setMandatoryOnShipment (final @Nullable java.lang.String MandatoryOnShipment)
	{
		set_Value (COLUMNNAME_MandatoryOnShipment, MandatoryOnShipment);
	}

	@Override
	public java.lang.String getMandatoryOnShipment() 
	{
		return get_ValueAsString(COLUMNNAME_MandatoryOnShipment);
	}

	@Override
	public void setM_Attribute_ID (final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, M_Attribute_ID);
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
	}

	@Override
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(final org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	@Override
	public void setM_AttributeSet_ID (final int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_ID, M_AttributeSet_ID);
	}

	@Override
	public int getM_AttributeSet_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSet_ID);
	}

	@Override
	public void setM_AttributeUse_ID (final int M_AttributeUse_ID)
	{
		if (M_AttributeUse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeUse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeUse_ID, M_AttributeUse_ID);
	}

	@Override
	public int getM_AttributeUse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeUse_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}