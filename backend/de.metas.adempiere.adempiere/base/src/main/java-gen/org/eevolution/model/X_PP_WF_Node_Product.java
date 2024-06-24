// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_WF_Node_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_WF_Node_Product extends org.compiere.model.PO implements I_PP_WF_Node_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2139628678L;

    /** Standard Constructor */
    public X_PP_WF_Node_Product (final Properties ctx, final int PP_WF_Node_Product_ID, @Nullable final String trxName)
    {
      super (ctx, PP_WF_Node_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_WF_Node_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_WF_Node_ID (final int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, AD_WF_Node_ID);
	}

	@Override
	public int getAD_WF_Node_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Node_ID);
	}

	@Override
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
	}

	/** 
	 * ConfigurationLevel AD_Reference_ID=53222
	 * Reference name: AD_SysConfig ConfigurationLevel
	 */
	public static final int CONFIGURATIONLEVEL_AD_Reference_ID=53222;
	/** System = S */
	public static final String CONFIGURATIONLEVEL_System = "S";
	/** Mandant = C */
	public static final String CONFIGURATIONLEVEL_Mandant = "C";
	/** Organisation = O */
	public static final String CONFIGURATIONLEVEL_Organisation = "O";
	@Override
	public void setConfigurationLevel (final @Nullable java.lang.String ConfigurationLevel)
	{
		set_Value (COLUMNNAME_ConfigurationLevel, ConfigurationLevel);
	}

	@Override
	public java.lang.String getConfigurationLevel() 
	{
		return get_ValueAsString(COLUMNNAME_ConfigurationLevel);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setIsSubcontracting (final boolean IsSubcontracting)
	{
		set_Value (COLUMNNAME_IsSubcontracting, IsSubcontracting);
	}

	@Override
	public boolean isSubcontracting() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubcontracting);
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
	public void setPP_WF_Node_Product_ID (final int PP_WF_Node_Product_ID)
	{
		if (PP_WF_Node_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_WF_Node_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_WF_Node_Product_ID, PP_WF_Node_Product_ID);
	}

	@Override
	public int getPP_WF_Node_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_WF_Node_Product_ID);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
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

	@Override
	public void setSpecification (final @Nullable java.lang.String Specification)
	{
		set_Value (COLUMNNAME_Specification, Specification);
	}

	@Override
	public java.lang.String getSpecification() 
	{
		return get_ValueAsString(COLUMNNAME_Specification);
	}

	@Override
	public void setYield (final @Nullable BigDecimal Yield)
	{
		set_Value (COLUMNNAME_Yield, Yield);
	}

	@Override
	public BigDecimal getYield() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Yield);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}