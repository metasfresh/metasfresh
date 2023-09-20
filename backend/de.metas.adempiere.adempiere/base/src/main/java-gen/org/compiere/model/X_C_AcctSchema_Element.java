// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_AcctSchema_Element
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_AcctSchema_Element extends org.compiere.model.PO implements I_C_AcctSchema_Element, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 260948484L;

    /** Standard Constructor */
    public X_C_AcctSchema_Element (final Properties ctx, final int C_AcctSchema_Element_ID, @Nullable final String trxName)
    {
      super (ctx, C_AcctSchema_Element_ID, trxName);
    }

    /** Load Constructor */
    public X_C_AcctSchema_Element (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public void setC_AcctSchema_Element_ID (final int C_AcctSchema_Element_ID)
	{
		if (C_AcctSchema_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_Element_ID, C_AcctSchema_Element_ID);
	}

	@Override
	public int getC_AcctSchema_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_Element_ID);
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
	public void setC_Element_ID (final int C_Element_ID)
	{
		if (C_Element_ID < 1) 
			set_Value (COLUMNNAME_C_Element_ID, null);
		else 
			set_Value (COLUMNNAME_C_Element_ID, C_Element_ID);
	}

	@Override
	public int getC_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Element_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getC_ElementValue()
	{
		return get_ValueAsPO(COLUMNNAME_C_ElementValue_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ElementValue(final org.compiere.model.I_C_ElementValue C_ElementValue)
	{
		set_ValueFromPO(COLUMNNAME_C_ElementValue_ID, org.compiere.model.I_C_ElementValue.class, C_ElementValue);
	}

	@Override
	public void setC_ElementValue_ID (final int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, C_ElementValue_ID);
	}

	@Override
	public int getC_ElementValue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ElementValue_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_Location()
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(final org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	@Override
	public void setC_Location_ID (final int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, C_Location_ID);
	}

	@Override
	public int getC_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Location_ID);
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

	/** 
	 * ElementType AD_Reference_ID=181
	 * Reference name: C_AcctSchema ElementType
	 */
	public static final int ELEMENTTYPE_AD_Reference_ID=181;
	/** Organization = OO */
	public static final String ELEMENTTYPE_Organization = "OO";
	/** Account = AC */
	public static final String ELEMENTTYPE_Account = "AC";
	/** Product = PR */
	public static final String ELEMENTTYPE_Product = "PR";
	/** BPartner = BP */
	public static final String ELEMENTTYPE_BPartner = "BP";
	/** OrgTrx = OT */
	public static final String ELEMENTTYPE_OrgTrx = "OT";
	/** LocationFrom = LF */
	public static final String ELEMENTTYPE_LocationFrom = "LF";
	/** LocationTo = LT */
	public static final String ELEMENTTYPE_LocationTo = "LT";
	/** SalesRegion = SR */
	public static final String ELEMENTTYPE_SalesRegion = "SR";
	/** Project = PJ */
	public static final String ELEMENTTYPE_Project = "PJ";
	/** Campaign = MC */
	public static final String ELEMENTTYPE_Campaign = "MC";
	/** UserList1 = U1 */
	public static final String ELEMENTTYPE_UserList1 = "U1";
	/** UserList2 = U2 */
	public static final String ELEMENTTYPE_UserList2 = "U2";
	/** Activity = AY */
	public static final String ELEMENTTYPE_Activity = "AY";
	/** SubAccount = SA */
	public static final String ELEMENTTYPE_SubAccount = "SA";
	/** UserElement1 = X1 */
	public static final String ELEMENTTYPE_UserElement1 = "X1";
	/** UserElement2 = X2 */
	public static final String ELEMENTTYPE_UserElement2 = "X2";
	/** UserElementString1 = S1 */
	public static final String ELEMENTTYPE_UserElementString1 = "S1";
	/** UserElementString2 = S2 */
	public static final String ELEMENTTYPE_UserElementString2 = "S2";
	/** UserElementString3 = S3 */
	public static final String ELEMENTTYPE_UserElementString3 = "S3";
	/** UserElementString4 = S4 */
	public static final String ELEMENTTYPE_UserElementString4 = "S4";
	/** UserElementString5 = S5 */
	public static final String ELEMENTTYPE_UserElementString5 = "S5";
	/** UserElementString6 = S6 */
	public static final String ELEMENTTYPE_UserElementString6 = "S6";
	/** UserElementString7 = S7 */
	public static final String ELEMENTTYPE_UserElementString7 = "S7";
	@Override
	public void setElementType (final java.lang.String ElementType)
	{
		set_Value (COLUMNNAME_ElementType, ElementType);
	}

	@Override
	public java.lang.String getElementType() 
	{
		return get_ValueAsString(COLUMNNAME_ElementType);
	}

	@Override
	public void setIsBalanced (final boolean IsBalanced)
	{
		set_Value (COLUMNNAME_IsBalanced, IsBalanced);
	}

	@Override
	public boolean isBalanced() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBalanced);
	}

	@Override
	public void setIsDisplayInEditor (final boolean IsDisplayInEditor)
	{
		set_Value (COLUMNNAME_IsDisplayInEditor, IsDisplayInEditor);
	}

	@Override
	public boolean isDisplayInEditor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayInEditor);
	}

	@Override
	public void setIsMandatory (final boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	@Override
	public boolean isMandatory() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMandatory);
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
	public void setUserElementString1 (final @Nullable java.lang.String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public java.lang.String getUserElementString1() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final @Nullable java.lang.String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public java.lang.String getUserElementString2() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable java.lang.String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public java.lang.String getUserElementString3() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable java.lang.String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public java.lang.String getUserElementString4() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable java.lang.String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public java.lang.String getUserElementString5() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable java.lang.String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public java.lang.String getUserElementString6() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable java.lang.String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public java.lang.String getUserElementString7() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}
}