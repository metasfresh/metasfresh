// Generated Model - DO NOT CHANGE
package de.metas.order.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CompensationGroup_CalibrationRule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CompensationGroup_CalibrationRule extends org.compiere.model.PO implements I_C_CompensationGroup_CalibrationRule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 590196429L;

    /** Standard Constructor */
    public X_C_CompensationGroup_CalibrationRule (final Properties ctx, final int C_CompensationGroup_CalibrationRule_ID, @Nullable final String trxName)
    {
      super (ctx, C_CompensationGroup_CalibrationRule_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CompensationGroup_CalibrationRule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BP_Group_ID (final int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, C_BP_Group_ID);
	}

	@Override
	public int getC_BP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_ID);
	}

	@Override
	public void setC_CompensationGroup_CalibrationRule_ID (final int C_CompensationGroup_CalibrationRule_ID)
	{
		if (C_CompensationGroup_CalibrationRule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_CalibrationRule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_CalibrationRule_ID, C_CompensationGroup_CalibrationRule_ID);
	}

	@Override
	public int getC_CompensationGroup_CalibrationRule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_CalibrationRule_ID);
	}

	@Override
	public void setC_CompensationGroup_Schema_ID (final int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, C_CompensationGroup_Schema_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_ID);
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
	public void setGroupCompensationCalibrationFactor (final BigDecimal GroupCompensationCalibrationFactor)
	{
		set_Value (COLUMNNAME_GroupCompensationCalibrationFactor, GroupCompensationCalibrationFactor);
	}

	@Override
	public BigDecimal getGroupCompensationCalibrationFactor() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GroupCompensationCalibrationFactor);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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