// Generated Model - DO NOT CHANGE
package de.metas.materialtracking.ch.lagerkonf.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_QualityInsp_LagerKonf_AdditionalFee
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_QualityInsp_LagerKonf_AdditionalFee extends org.compiere.model.PO implements I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -468170247L;

    /** Standard Constructor */
    public X_M_QualityInsp_LagerKonf_AdditionalFee (final Properties ctx, final int M_QualityInsp_LagerKonf_AdditionalFee_ID, @Nullable final String trxName)
    {
      super (ctx, M_QualityInsp_LagerKonf_AdditionalFee_ID, trxName);
    }

    /** Load Constructor */
    public X_M_QualityInsp_LagerKonf_AdditionalFee (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAdditional_Fee_Amt_Per_UOM (final BigDecimal Additional_Fee_Amt_Per_UOM)
	{
		set_Value (COLUMNNAME_Additional_Fee_Amt_Per_UOM, Additional_Fee_Amt_Per_UOM);
	}

	@Override
	public BigDecimal getAdditional_Fee_Amt_Per_UOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Additional_Fee_Amt_Per_UOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ApplyFeeTo AD_Reference_ID=541889
	 * Reference name: QualityInsp_AdditionalFee_ApplyFeeTo
	 */
	public static final int APPLYFEETO_AD_Reference_ID=541889;
	/** Produced Total Without By Products = ProducedTotalWithoutByProducts */
	public static final String APPLYFEETO_ProducedTotalWithoutByProducts = "ProducedTotalWithoutByProducts";
	/** Raw = Raw */
	public static final String APPLYFEETO_Raw = "Raw";
	@Override
	public void setApplyFeeTo (final String ApplyFeeTo)
	{
		set_Value (COLUMNNAME_ApplyFeeTo, ApplyFeeTo);
	}

	@Override
	public String getApplyFeeTo() 
	{
		return get_ValueAsString(COLUMNNAME_ApplyFeeTo);
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
	public void setM_QualityInsp_LagerKonf_AdditionalFee_ID (final int M_QualityInsp_LagerKonf_AdditionalFee_ID)
	{
		if (M_QualityInsp_LagerKonf_AdditionalFee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID, M_QualityInsp_LagerKonf_AdditionalFee_ID);
	}

	@Override
	public int getM_QualityInsp_LagerKonf_AdditionalFee_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID);
	}

	@Override
	public I_M_QualityInsp_LagerKonf_Version getM_QualityInsp_LagerKonf_Version()
	{
		return get_ValueAsPO(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, I_M_QualityInsp_LagerKonf_Version.class);
	}

	@Override
	public void setM_QualityInsp_LagerKonf_Version(final I_M_QualityInsp_LagerKonf_Version M_QualityInsp_LagerKonf_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, I_M_QualityInsp_LagerKonf_Version.class, M_QualityInsp_LagerKonf_Version);
	}

	@Override
	public void setM_QualityInsp_LagerKonf_Version_ID (final int M_QualityInsp_LagerKonf_Version_ID)
	{
		if (M_QualityInsp_LagerKonf_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, M_QualityInsp_LagerKonf_Version_ID);
	}

	@Override
	public int getM_QualityInsp_LagerKonf_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID);
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