// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GL_DistributionLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GL_DistributionLine extends org.compiere.model.PO implements I_GL_DistributionLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -891351302L;

    /** Standard Constructor */
    public X_GL_DistributionLine (final Properties ctx, final int GL_DistributionLine_ID, @Nullable final String trxName)
    {
      super (ctx, GL_DistributionLine_ID, trxName);
    }

    /** Load Constructor */
    public X_GL_DistributionLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_GL_Distribution getGL_Distribution()
	{
		return get_ValueAsPO(COLUMNNAME_GL_Distribution_ID, org.compiere.model.I_GL_Distribution.class);
	}

	@Override
	public void setGL_Distribution(final org.compiere.model.I_GL_Distribution GL_Distribution)
	{
		set_ValueFromPO(COLUMNNAME_GL_Distribution_ID, org.compiere.model.I_GL_Distribution.class, GL_Distribution);
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
	public void setGL_DistributionLine_ID (final int GL_DistributionLine_ID)
	{
		if (GL_DistributionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_DistributionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_DistributionLine_ID, GL_DistributionLine_ID);
	}

	@Override
	public int getGL_DistributionLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_DistributionLine_ID);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
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
	public void setOverwriteAcct (final boolean OverwriteAcct)
	{
		set_Value (COLUMNNAME_OverwriteAcct, OverwriteAcct);
	}

	@Override
	public boolean isOverwriteAcct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteAcct);
	}

	@Override
	public void setOverwriteActivity (final boolean OverwriteActivity)
	{
		set_Value (COLUMNNAME_OverwriteActivity, OverwriteActivity);
	}

	@Override
	public boolean isOverwriteActivity() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteActivity);
	}

	@Override
	public void setOverwriteBPartner (final boolean OverwriteBPartner)
	{
		set_Value (COLUMNNAME_OverwriteBPartner, OverwriteBPartner);
	}

	@Override
	public boolean isOverwriteBPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteBPartner);
	}

	@Override
	public void setOverwriteCampaign (final boolean OverwriteCampaign)
	{
		set_Value (COLUMNNAME_OverwriteCampaign, OverwriteCampaign);
	}

	@Override
	public boolean isOverwriteCampaign() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteCampaign);
	}

	@Override
	public void setOverwriteLocFrom (final boolean OverwriteLocFrom)
	{
		set_Value (COLUMNNAME_OverwriteLocFrom, OverwriteLocFrom);
	}

	@Override
	public boolean isOverwriteLocFrom() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteLocFrom);
	}

	@Override
	public void setOverwriteLocTo (final boolean OverwriteLocTo)
	{
		set_Value (COLUMNNAME_OverwriteLocTo, OverwriteLocTo);
	}

	@Override
	public boolean isOverwriteLocTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteLocTo);
	}

	@Override
	public void setOverwriteOrder (final boolean OverwriteOrder)
	{
		set_Value (COLUMNNAME_OverwriteOrder, OverwriteOrder);
	}

	@Override
	public boolean isOverwriteOrder() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteOrder);
	}

	@Override
	public void setOverwriteOrg (final boolean OverwriteOrg)
	{
		set_Value (COLUMNNAME_OverwriteOrg, OverwriteOrg);
	}

	@Override
	public boolean isOverwriteOrg() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteOrg);
	}

	@Override
	public void setOverwriteOrgTrx (final boolean OverwriteOrgTrx)
	{
		set_Value (COLUMNNAME_OverwriteOrgTrx, OverwriteOrgTrx);
	}

	@Override
	public boolean isOverwriteOrgTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteOrgTrx);
	}

	@Override
	public void setOverwriteProduct (final boolean OverwriteProduct)
	{
		set_Value (COLUMNNAME_OverwriteProduct, OverwriteProduct);
	}

	@Override
	public boolean isOverwriteProduct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteProduct);
	}

	@Override
	public void setOverwriteProject (final boolean OverwriteProject)
	{
		set_Value (COLUMNNAME_OverwriteProject, OverwriteProject);
	}

	@Override
	public boolean isOverwriteProject() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteProject);
	}

	@Override
	public void setOverwriteSalesRegion (final boolean OverwriteSalesRegion)
	{
		set_Value (COLUMNNAME_OverwriteSalesRegion, OverwriteSalesRegion);
	}

	@Override
	public boolean isOverwriteSalesRegion() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteSalesRegion);
	}

	@Override
	public void setOverwriteSectionCode (final boolean OverwriteSectionCode)
	{
		set_Value (COLUMNNAME_OverwriteSectionCode, OverwriteSectionCode);
	}

	@Override
	public boolean isOverwriteSectionCode() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteSectionCode);
	}

	@Override
	public void setOverwriteUser1 (final boolean OverwriteUser1)
	{
		set_Value (COLUMNNAME_OverwriteUser1, OverwriteUser1);
	}

	@Override
	public boolean isOverwriteUser1() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteUser1);
	}

	@Override
	public void setOverwriteUser2 (final boolean OverwriteUser2)
	{
		set_Value (COLUMNNAME_OverwriteUser2, OverwriteUser2);
	}

	@Override
	public boolean isOverwriteUser2() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwriteUser2);
	}

	@Override
	public void setPercent (final BigDecimal Percent)
	{
		set_Value (COLUMNNAME_Percent, Percent);
	}

	@Override
	public BigDecimal getPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Percent);
		return bd != null ? bd : BigDecimal.ZERO;
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