// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_Order_BOM
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_BOM extends org.compiere.model.PO implements I_PP_Order_BOM, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1223071028L;

    /** Standard Constructor */
    public X_PP_Order_BOM (final Properties ctx, final int PP_Order_BOM_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_BOM_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_BOM (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * BOMType AD_Reference_ID=347
	 * Reference name: BOMType
	 */
	public static final int BOMTYPE_AD_Reference_ID=347;
	/** Current Active = A */
	public static final String BOMTYPE_CurrentActive = "A";
	/** Make-To-Order = O */
	public static final String BOMTYPE_Make_To_Order = "O";
	/** Previous = P */
	public static final String BOMTYPE_Previous = "P";
	/** Previous, Spare = S */
	public static final String BOMTYPE_PreviousSpare = "S";
	/** Future = F */
	public static final String BOMTYPE_Future = "F";
	/** Verwaltung = M */
	public static final String BOMTYPE_Verwaltung = "M";
	/** Repair = R */
	public static final String BOMTYPE_Repair = "R";
	/** Product Configure = C */
	public static final String BOMTYPE_ProductConfigure = "C";
	/** Make-To-Kit = K */
	public static final String BOMTYPE_Make_To_Kit = "K";
	@Override
	public void setBOMType (final @Nullable java.lang.String BOMType)
	{
		set_Value (COLUMNNAME_BOMType, BOMType);
	}

	@Override
	public java.lang.String getBOMType() 
	{
		return get_ValueAsString(COLUMNNAME_BOMType);
	}

	/** 
	 * BOMUse AD_Reference_ID=348
	 * Reference name: BOMUse
	 */
	public static final int BOMUSE_AD_Reference_ID=348;
	/** Master = A */
	public static final String BOMUSE_Master = "A";
	/** Engineering = E */
	public static final String BOMUSE_Engineering = "E";
	/** Manufacturing = M */
	public static final String BOMUSE_Manufacturing = "M";
	/** Planning = P */
	public static final String BOMUSE_Planning = "P";
	/** Quality = Q */
	public static final String BOMUSE_Quality = "Q";
	/** Phantom = H */
	public static final String BOMUSE_Phantom = "H";
	@Override
	public void setBOMUse (final @Nullable java.lang.String BOMUse)
	{
		set_Value (COLUMNNAME_BOMUse, BOMUse);
	}

	@Override
	public java.lang.String getBOMUse() 
	{
		return get_ValueAsString(COLUMNNAME_BOMUse);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setCopyFrom (final @Nullable java.lang.String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	@Override
	public java.lang.String getCopyFrom() 
	{
		return get_ValueAsString(COLUMNNAME_CopyFrom);
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
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
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
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice()
	{
		return get_ValueAsPO(COLUMNNAME_M_ChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class);
	}

	@Override
	public void setM_ChangeNotice(final org.compiere.model.I_M_ChangeNotice M_ChangeNotice)
	{
		set_ValueFromPO(COLUMNNAME_M_ChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class, M_ChangeNotice);
	}

	@Override
	public void setM_ChangeNotice_ID (final int M_ChangeNotice_ID)
	{
		if (M_ChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, M_ChangeNotice_ID);
	}

	@Override
	public int getM_ChangeNotice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ChangeNotice_ID);
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
	public void setPP_Order_BOM_ID (final int PP_Order_BOM_ID)
	{
		if (PP_Order_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOM_ID, PP_Order_BOM_ID);
	}

	@Override
	public int getPP_Order_BOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_BOM_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
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
	public void setRevision (final @Nullable java.lang.String Revision)
	{
		set_Value (COLUMNNAME_Revision, Revision);
	}

	@Override
	public java.lang.String getRevision() 
	{
		return get_ValueAsString(COLUMNNAME_Revision);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getSerialNo_Sequence()
	{
		return get_ValueAsPO(COLUMNNAME_SerialNo_Sequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setSerialNo_Sequence(final org.compiere.model.I_AD_Sequence SerialNo_Sequence)
	{
		set_ValueFromPO(COLUMNNAME_SerialNo_Sequence_ID, org.compiere.model.I_AD_Sequence.class, SerialNo_Sequence);
	}

	@Override
	public void setSerialNo_Sequence_ID (final int SerialNo_Sequence_ID)
	{
		if (SerialNo_Sequence_ID < 1) 
			set_Value (COLUMNNAME_SerialNo_Sequence_ID, null);
		else 
			set_Value (COLUMNNAME_SerialNo_Sequence_ID, SerialNo_Sequence_ID);
	}

	@Override
	public int getSerialNo_Sequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SerialNo_Sequence_ID);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}