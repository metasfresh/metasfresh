// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_UserProfile_MFG
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_MFG extends org.compiere.model.PO implements I_MobileUI_UserProfile_MFG, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1252554458L;

    /** Standard Constructor */
    public X_MobileUI_UserProfile_MFG (final Properties ctx, final int MobileUI_UserProfile_MFG_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_UserProfile_MFG_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_UserProfile_MFG (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	/** 
	 * IsAllowFinishedGoodsReceiveToLU AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISALLOWFINISHEDGOODSRECEIVETOLU_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISALLOWFINISHEDGOODSRECEIVETOLU_Yes = "Y";
	/** No = N */
	public static final String ISALLOWFINISHEDGOODSRECEIVETOLU_No = "N";
	@Override
	public void setIsAllowFinishedGoodsReceiveToLU (final @Nullable java.lang.String IsAllowFinishedGoodsReceiveToLU)
	{
		set_Value (COLUMNNAME_IsAllowFinishedGoodsReceiveToLU, IsAllowFinishedGoodsReceiveToLU);
	}

	@Override
	public java.lang.String getIsAllowFinishedGoodsReceiveToLU() 
	{
		return get_ValueAsString(COLUMNNAME_IsAllowFinishedGoodsReceiveToLU);
	}

	/** 
	 * IsAllowFinishedGoodsReceiveToTU AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISALLOWFINISHEDGOODSRECEIVETOTU_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISALLOWFINISHEDGOODSRECEIVETOTU_Yes = "Y";
	/** No = N */
	public static final String ISALLOWFINISHEDGOODSRECEIVETOTU_No = "N";
	@Override
	public void setIsAllowFinishedGoodsReceiveToTU (final @Nullable java.lang.String IsAllowFinishedGoodsReceiveToTU)
	{
		set_Value (COLUMNNAME_IsAllowFinishedGoodsReceiveToTU, IsAllowFinishedGoodsReceiveToTU);
	}

	@Override
	public java.lang.String getIsAllowFinishedGoodsReceiveToTU() 
	{
		return get_ValueAsString(COLUMNNAME_IsAllowFinishedGoodsReceiveToTU);
	}

	/** 
	 * IsAllowIssuingAnyHU AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISALLOWISSUINGANYHU_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISALLOWISSUINGANYHU_Yes = "Y";
	/** No = N */
	public static final String ISALLOWISSUINGANYHU_No = "N";
	@Override
	public void setIsAllowIssuingAnyHU (final @Nullable java.lang.String IsAllowIssuingAnyHU)
	{
		set_Value (COLUMNNAME_IsAllowIssuingAnyHU, IsAllowIssuingAnyHU);
	}

	@Override
	public java.lang.String getIsAllowIssuingAnyHU() 
	{
		return get_ValueAsString(COLUMNNAME_IsAllowIssuingAnyHU);
	}

	/** 
	 * IsBestBeforeDateEditable AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISBESTBEFOREDATEEDITABLE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISBESTBEFOREDATEEDITABLE_Yes = "Y";
	/** No = N */
	public static final String ISBESTBEFOREDATEEDITABLE_No = "N";
	@Override
	public void setIsBestBeforeDateEditable (final @Nullable java.lang.String IsBestBeforeDateEditable)
	{
		set_Value (COLUMNNAME_IsBestBeforeDateEditable, IsBestBeforeDateEditable);
	}

	@Override
	public java.lang.String getIsBestBeforeDateEditable() 
	{
		return get_ValueAsString(COLUMNNAME_IsBestBeforeDateEditable);
	}

	/** 
	 * IsCaptureFinishedGoodsCatchWeightAtReceipt AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISCAPTUREFINISHEDGOODSCATCHWEIGHTATRECEIPT_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISCAPTUREFINISHEDGOODSCATCHWEIGHTATRECEIPT_Yes = "Y";
	/** No = N */
	public static final String ISCAPTUREFINISHEDGOODSCATCHWEIGHTATRECEIPT_No = "N";
	@Override
	public void setIsCaptureFinishedGoodsCatchWeightAtReceipt (final @Nullable java.lang.String IsCaptureFinishedGoodsCatchWeightAtReceipt)
	{
		set_Value (COLUMNNAME_IsCaptureFinishedGoodsCatchWeightAtReceipt, IsCaptureFinishedGoodsCatchWeightAtReceipt);
	}

	@Override
	public java.lang.String getIsCaptureFinishedGoodsCatchWeightAtReceipt() 
	{
		return get_ValueAsString(COLUMNNAME_IsCaptureFinishedGoodsCatchWeightAtReceipt);
	}

	/** 
	 * IsLotNumberEditable AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISLOTNUMBEREDITABLE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISLOTNUMBEREDITABLE_Yes = "Y";
	/** No = N */
	public static final String ISLOTNUMBEREDITABLE_No = "N";
	@Override
	public void setIsLotNumberEditable (final @Nullable java.lang.String IsLotNumberEditable)
	{
		set_Value (COLUMNNAME_IsLotNumberEditable, IsLotNumberEditable);
	}

	@Override
	public java.lang.String getIsLotNumberEditable() 
	{
		return get_ValueAsString(COLUMNNAME_IsLotNumberEditable);
	}

	/** 
	 * IsScanResourceRequired AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISSCANRESOURCEREQUIRED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISSCANRESOURCEREQUIRED_Yes = "Y";
	/** No = N */
	public static final String ISSCANRESOURCEREQUIRED_No = "N";
	@Override
	public void setIsScanResourceRequired (final @Nullable java.lang.String IsScanResourceRequired)
	{
		set_Value (COLUMNNAME_IsScanResourceRequired, IsScanResourceRequired);
	}

	@Override
	public java.lang.String getIsScanResourceRequired() 
	{
		return get_ValueAsString(COLUMNNAME_IsScanResourceRequired);
	}

	/** 
	 * IsSkipFinishedGoodsReceiveTargetStep AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISSKIPFINISHEDGOODSRECEIVETARGETSTEP_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISSKIPFINISHEDGOODSRECEIVETARGETSTEP_Yes = "Y";
	/** No = N */
	public static final String ISSKIPFINISHEDGOODSRECEIVETARGETSTEP_No = "N";
	@Override
	public void setIsSkipFinishedGoodsReceiveTargetStep (final @Nullable java.lang.String IsSkipFinishedGoodsReceiveTargetStep)
	{
		set_Value (COLUMNNAME_IsSkipFinishedGoodsReceiveTargetStep, IsSkipFinishedGoodsReceiveTargetStep);
	}

	@Override
	public java.lang.String getIsSkipFinishedGoodsReceiveTargetStep() 
	{
		return get_ValueAsString(COLUMNNAME_IsSkipFinishedGoodsReceiveTargetStep);
	}

	@Override
	public void setMobileUI_UserProfile_MFG_ID (final int MobileUI_UserProfile_MFG_ID)
	{
		if (MobileUI_UserProfile_MFG_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_MFG_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_MFG_ID, MobileUI_UserProfile_MFG_ID);
	}

	@Override
	public int getMobileUI_UserProfile_MFG_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_MFG_ID);
	}

	/** 
	 * ReceiveUnitType AD_Reference_ID=542051
	 * Reference name: ReceiveUnitType
	 */
	public static final int RECEIVEUNITTYPE_AD_Reference_ID=542051;
	/** CU = CU */
	public static final String RECEIVEUNITTYPE_CU = "CU";
	/** TU = TU */
	public static final String RECEIVEUNITTYPE_TU = "TU";
	@Override
	public void setReceiveUnitType (final @Nullable java.lang.String ReceiveUnitType)
	{
		set_Value (COLUMNNAME_ReceiveUnitType, ReceiveUnitType);
	}

	@Override
	public java.lang.String getReceiveUnitType() 
	{
		return get_ValueAsString(COLUMNNAME_ReceiveUnitType);
	}
}