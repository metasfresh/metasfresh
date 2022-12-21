// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GL_Distribution
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GL_Distribution extends org.compiere.model.PO implements I_GL_Distribution, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -382080513L;

    /** Standard Constructor */
    public X_GL_Distribution (final Properties ctx, final int GL_Distribution_ID, @Nullable final String trxName)
    {
      super (ctx, GL_Distribution_ID, trxName);
    }

    /** Load Constructor */
    public X_GL_Distribution (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_ElementValue getAccount()
	{
		return get_ValueAsPO(COLUMNNAME_Account_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setAccount(final org.compiere.model.I_C_ElementValue Account)
	{
		set_ValueFromPO(COLUMNNAME_Account_ID, org.compiere.model.I_C_ElementValue.class, Account);
	}

	@Override
	public void setAccount_ID (final int Account_ID)
	{
		if (Account_ID < 1) 
			set_Value (COLUMNNAME_Account_ID, null);
		else 
			set_Value (COLUMNNAME_Account_ID, Account_ID);
	}

	@Override
	public int getAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Account_ID);
	}

	@Override
	public void setAD_OrgTrx_ID (final int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, AD_OrgTrx_ID);
	}

	@Override
	public int getAD_OrgTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTrx_ID);
	}

	@Override
	public void setAnyAcct (final boolean AnyAcct)
	{
		set_Value (COLUMNNAME_AnyAcct, AnyAcct);
	}

	@Override
	public boolean isAnyAcct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyAcct);
	}

	@Override
	public void setAnyActivity (final boolean AnyActivity)
	{
		set_Value (COLUMNNAME_AnyActivity, AnyActivity);
	}

	@Override
	public boolean isAnyActivity() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyActivity);
	}

	@Override
	public void setAnyBPartner (final boolean AnyBPartner)
	{
		set_Value (COLUMNNAME_AnyBPartner, AnyBPartner);
	}

	@Override
	public boolean isAnyBPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyBPartner);
	}

	@Override
	public void setAnyCampaign (final boolean AnyCampaign)
	{
		set_Value (COLUMNNAME_AnyCampaign, AnyCampaign);
	}

	@Override
	public boolean isAnyCampaign() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyCampaign);
	}

	@Override
	public void setAnyLocFrom (final boolean AnyLocFrom)
	{
		set_Value (COLUMNNAME_AnyLocFrom, AnyLocFrom);
	}

	@Override
	public boolean isAnyLocFrom() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyLocFrom);
	}

	@Override
	public void setAnyLocTo (final boolean AnyLocTo)
	{
		set_Value (COLUMNNAME_AnyLocTo, AnyLocTo);
	}

	@Override
	public boolean isAnyLocTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyLocTo);
	}

	@Override
	public void setAnyOrder (final boolean AnyOrder)
	{
		set_Value (COLUMNNAME_AnyOrder, AnyOrder);
	}

	@Override
	public boolean isAnyOrder() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyOrder);
	}

	@Override
	public void setAnyOrg (final boolean AnyOrg)
	{
		set_Value (COLUMNNAME_AnyOrg, AnyOrg);
	}

	@Override
	public boolean isAnyOrg() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyOrg);
	}

	@Override
	public void setAnyOrgTrx (final boolean AnyOrgTrx)
	{
		set_Value (COLUMNNAME_AnyOrgTrx, AnyOrgTrx);
	}

	@Override
	public boolean isAnyOrgTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyOrgTrx);
	}

	@Override
	public void setAnyProduct (final boolean AnyProduct)
	{
		set_Value (COLUMNNAME_AnyProduct, AnyProduct);
	}

	@Override
	public boolean isAnyProduct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyProduct);
	}

	@Override
	public void setAnyProject (final boolean AnyProject)
	{
		set_Value (COLUMNNAME_AnyProject, AnyProject);
	}

	@Override
	public boolean isAnyProject() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyProject);
	}

	@Override
	public void setAnySalesRegion (final boolean AnySalesRegion)
	{
		set_Value (COLUMNNAME_AnySalesRegion, AnySalesRegion);
	}

	@Override
	public boolean isAnySalesRegion() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnySalesRegion);
	}

	@Override
	public void setAnySectionCode (final boolean AnySectionCode)
	{
		set_Value (COLUMNNAME_AnySectionCode, AnySectionCode);
	}

	@Override
	public boolean isAnySectionCode() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnySectionCode);
	}

	@Override
	public void setAnyUser1 (final boolean AnyUser1)
	{
		set_Value (COLUMNNAME_AnyUser1, AnyUser1);
	}

	@Override
	public boolean isAnyUser1() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyUser1);
	}

	@Override
	public void setAnyUser2 (final boolean AnyUser2)
	{
		set_Value (COLUMNNAME_AnyUser2, AnyUser2);
	}

	@Override
	public boolean isAnyUser2() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AnyUser2);
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(final org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
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
	public org.compiere.model.I_C_Campaign getC_Campaign()
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(final org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	@Override
	public void setC_Campaign_ID (final int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, C_Campaign_ID);
	}

	@Override
	public int getC_Campaign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Campaign_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocFrom()
	{
		return get_ValueAsPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocFrom(final org.compiere.model.I_C_Location C_LocFrom)
	{
		set_ValueFromPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class, C_LocFrom);
	}

	@Override
	public void setC_LocFrom_ID (final int C_LocFrom_ID)
	{
		if (C_LocFrom_ID < 1) 
			set_Value (COLUMNNAME_C_LocFrom_ID, null);
		else 
			set_Value (COLUMNNAME_C_LocFrom_ID, C_LocFrom_ID);
	}

	@Override
	public int getC_LocFrom_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_LocFrom_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocTo()
	{
		return get_ValueAsPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocTo(final org.compiere.model.I_C_Location C_LocTo)
	{
		set_ValueFromPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class, C_LocTo);
	}

	@Override
	public void setC_LocTo_ID (final int C_LocTo_ID)
	{
		if (C_LocTo_ID < 1) 
			set_Value (COLUMNNAME_C_LocTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_LocTo_ID, C_LocTo_ID);
	}

	@Override
	public int getC_LocTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_LocTo_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public org.compiere.model.I_C_SalesRegion getC_SalesRegion()
	{
		return get_ValueAsPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class);
	}

	@Override
	public void setC_SalesRegion(final org.compiere.model.I_C_SalesRegion C_SalesRegion)
	{
		set_ValueFromPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class, C_SalesRegion);
	}

	@Override
	public void setC_SalesRegion_ID (final int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, C_SalesRegion_ID);
	}

	@Override
	public int getC_SalesRegion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SalesRegion_ID);
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
	public void setGL_Distribution_ID (final int GL_Distribution_ID)
	{
		if (GL_Distribution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_Distribution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Distribution_ID, GL_Distribution_ID);
	}

	@Override
	public int getGL_Distribution_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_Distribution_ID);
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIsCreateReversal (final boolean IsCreateReversal)
	{
		set_Value (COLUMNNAME_IsCreateReversal, IsCreateReversal);
	}

	@Override
	public boolean isCreateReversal() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateReversal);
	}

	@Override
	public void setIsValid (final boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, IsValid);
	}

	@Override
	public boolean isValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValid);
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
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setOrg_ID (final int Org_ID)
	{
		if (Org_ID < 1) 
			set_Value (COLUMNNAME_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Org_ID, Org_ID);
	}

	@Override
	public int getOrg_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Org_ID);
	}

	@Override
	public void setPercentTotal (final BigDecimal PercentTotal)
	{
		set_Value (COLUMNNAME_PercentTotal, PercentTotal);
	}

	@Override
	public BigDecimal getPercentTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PercentTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * PostingType AD_Reference_ID=125
	 * Reference name: _Posting Type
	 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Actual Year End = Y */
	public static final String POSTINGTYPE_ActualYearEnd = "Y";
	@Override
	public void setPostingType (final @Nullable java.lang.String PostingType)
	{
		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	@Override
	public java.lang.String getPostingType() 
	{
		return get_ValueAsString(COLUMNNAME_PostingType);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1()
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(final org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	@Override
	public void setUser1_ID (final int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, User1_ID);
	}

	@Override
	public int getUser1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User1_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2()
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(final org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	@Override
	public void setUser2_ID (final int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, User2_ID);
	}

	@Override
	public int getUser2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User2_ID);
	}
}