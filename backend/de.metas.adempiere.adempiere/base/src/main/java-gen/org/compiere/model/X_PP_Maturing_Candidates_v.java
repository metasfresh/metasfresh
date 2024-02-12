// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Maturing_Candidates_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Maturing_Candidates_v extends org.compiere.model.PO implements I_PP_Maturing_Candidates_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 273821769L;

    /** Standard Constructor */
    public X_PP_Maturing_Candidates_v (final Properties ctx, final int PP_Maturing_Candidates_v_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Maturing_Candidates_v_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Maturing_Candidates_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDateStartSchedule (final @Nullable java.sql.Timestamp DateStartSchedule)
	{
		set_ValueNoCheck (COLUMNNAME_DateStartSchedule, DateStartSchedule);
	}

	@Override
	public java.sql.Timestamp getDateStartSchedule() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStartSchedule);
	}

	/** 
	 * HUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int HUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String HUSTATUS_Planning = "P";
	/** Active = A */
	public static final String HUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String HUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String HUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String HUSTATUS_Shipped = "E";
	/** Issued = I */
	public static final String HUSTATUS_Issued = "I";
	@Override
	public void setHUStatus (final @Nullable java.lang.String HUStatus)
	{
		set_ValueNoCheck (COLUMNNAME_HUStatus, HUStatus);
	}

	@Override
	public java.lang.String getHUStatus() 
	{
		return get_ValueAsString(COLUMNNAME_HUStatus);
	}

	@Override
	public void setIssue_M_Product_ID (final int Issue_M_Product_ID)
	{
		if (Issue_M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Issue_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Issue_M_Product_ID, Issue_M_Product_ID);
	}

	@Override
	public int getIssue_M_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Issue_M_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public org.compiere.model.I_M_Maturing_Configuration getM_Maturing_Configuration()
	{
		return get_ValueAsPO(COLUMNNAME_M_Maturing_Configuration_ID, org.compiere.model.I_M_Maturing_Configuration.class);
	}

	@Override
	public void setM_Maturing_Configuration(final org.compiere.model.I_M_Maturing_Configuration M_Maturing_Configuration)
	{
		set_ValueFromPO(COLUMNNAME_M_Maturing_Configuration_ID, org.compiere.model.I_M_Maturing_Configuration.class, M_Maturing_Configuration);
	}

	@Override
	public void setM_Maturing_Configuration_ID (final int M_Maturing_Configuration_ID)
	{
		if (M_Maturing_Configuration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_ID, M_Maturing_Configuration_ID);
	}

	@Override
	public int getM_Maturing_Configuration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Maturing_Configuration_ID);
	}

	@Override
	public org.compiere.model.I_M_Maturing_Configuration_Line getM_Maturing_Configuration_Line()
	{
		return get_ValueAsPO(COLUMNNAME_M_Maturing_Configuration_Line_ID, org.compiere.model.I_M_Maturing_Configuration_Line.class);
	}

	@Override
	public void setM_Maturing_Configuration_Line(final org.compiere.model.I_M_Maturing_Configuration_Line M_Maturing_Configuration_Line)
	{
		set_ValueFromPO(COLUMNNAME_M_Maturing_Configuration_Line_ID, org.compiere.model.I_M_Maturing_Configuration_Line.class, M_Maturing_Configuration_Line);
	}

	@Override
	public void setM_Maturing_Configuration_Line_ID (final int M_Maturing_Configuration_Line_ID)
	{
		if (M_Maturing_Configuration_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_Line_ID, M_Maturing_Configuration_Line_ID);
	}

	@Override
	public int getM_Maturing_Configuration_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Maturing_Configuration_Line_ID);
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

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setPP_Maturing_Candidates_v_ID (final int PP_Maturing_Candidates_v_ID)
	{
		if (PP_Maturing_Candidates_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Maturing_Candidates_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Maturing_Candidates_v_ID, PP_Maturing_Candidates_v_ID);
	}

	@Override
	public int getPP_Maturing_Candidates_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Maturing_Candidates_v_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Candidate getPP_Order_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class);
	}

	@Override
	public void setPP_Order_Candidate(final org.eevolution.model.I_PP_Order_Candidate PP_Order_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class, PP_Order_Candidate);
	}

	@Override
	public void setPP_Order_Candidate_ID (final int PP_Order_Candidate_ID)
	{
		if (PP_Order_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Candidate_ID, PP_Order_Candidate_ID);
	}

	@Override
	public int getPP_Order_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Candidate_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOMVersions getPP_Product_BOMVersions()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOMVersions_ID, org.eevolution.model.I_PP_Product_BOMVersions.class);
	}

	@Override
	public void setPP_Product_BOMVersions(final org.eevolution.model.I_PP_Product_BOMVersions PP_Product_BOMVersions)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOMVersions_ID, org.eevolution.model.I_PP_Product_BOMVersions.class, PP_Product_BOMVersions);
	}

	@Override
	public void setPP_Product_BOMVersions_ID (final int PP_Product_BOMVersions_ID)
	{
		if (PP_Product_BOMVersions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMVersions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMVersions_ID, PP_Product_BOMVersions_ID);
	}

	@Override
	public int getPP_Product_BOMVersions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOMVersions_ID);
	}

	@Override
	public void setPP_Product_Planning_ID (final int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, PP_Product_Planning_ID);
	}

	@Override
	public int getPP_Product_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_Planning_ID);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}