// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_ASI_Data
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_ASI_Data extends org.compiere.model.PO implements I_M_Product_ASI_Data, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 55375959L;

    /** Standard Constructor */
    public X_M_Product_ASI_Data (final Properties ctx, final int M_Product_ASI_Data_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_ASI_Data_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_ASI_Data (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setCustomerLabelName (final @Nullable java.lang.String CustomerLabelName)
	{
		set_Value (COLUMNNAME_CustomerLabelName, CustomerLabelName);
	}

	@Override
	public java.lang.String getCustomerLabelName() 
	{
		return get_ValueAsString(COLUMNNAME_CustomerLabelName);
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
	public void setDescriptionURL (final @Nullable java.lang.String DescriptionURL)
	{
		set_Value (COLUMNNAME_DescriptionURL, DescriptionURL);
	}

	@Override
	public java.lang.String getDescriptionURL() 
	{
		return get_ValueAsString(COLUMNNAME_DescriptionURL);
	}

	@Override
	public void setEAN13_ProductCode (final @Nullable java.lang.String EAN13_ProductCode)
	{
		set_Value (COLUMNNAME_EAN13_ProductCode, EAN13_ProductCode);
	}

	@Override
	public java.lang.String getEAN13_ProductCode() 
	{
		return get_ValueAsString(COLUMNNAME_EAN13_ProductCode);
	}

	@Override
	public void setEAN_CU (final @Nullable java.lang.String EAN_CU)
	{
		set_Value (COLUMNNAME_EAN_CU, EAN_CU);
	}

	@Override
	public java.lang.String getEAN_CU() 
	{
		return get_ValueAsString(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setGTIN (final @Nullable java.lang.String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN);
	}

	@Override
	public void setIngredients (final @Nullable java.lang.String Ingredients)
	{
		set_Value (COLUMNNAME_Ingredients, Ingredients);
	}

	@Override
	public java.lang.String getIngredients() 
	{
		return get_ValueAsString(COLUMNNAME_Ingredients);
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
	public void setM_Product_ASI_Data_ID (final int M_Product_ASI_Data_ID)
	{
		if (M_Product_ASI_Data_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ASI_Data_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ASI_Data_ID, M_Product_ASI_Data_ID);
	}

	@Override
	public int getM_Product_ASI_Data_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ASI_Data_ID);
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
	public void setProductCategory (final @Nullable java.lang.String ProductCategory)
	{
		set_Value (COLUMNNAME_ProductCategory, ProductCategory);
	}

	@Override
	public java.lang.String getProductCategory() 
	{
		return get_ValueAsString(COLUMNNAME_ProductCategory);
	}

	@Override
	public void setProductDescription (final @Nullable java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setProductNo (final @Nullable java.lang.String ProductNo)
	{
		set_Value (COLUMNNAME_ProductNo, ProductNo);
	}

	@Override
	public java.lang.String getProductNo() 
	{
		return get_ValueAsString(COLUMNNAME_ProductNo);
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
	public void setUPC (final @Nullable java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return get_ValueAsString(COLUMNNAME_UPC);
	}
}