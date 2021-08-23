package de.metas.interfaces;

/**
 * {@link org.compiere.model.I_C_OrderLine} extension with Swat columns.
 */
public interface I_C_OrderLine extends org.compiere.model.I_C_OrderLine
{
	// @formatter:off
	boolean isDiverse();
	void setIsDiverse(boolean isDiverse);
	// @formatter:on

	// @formatter:off
	I_C_OrderLine getSinglePriceTag();
	int getSinglePriceTag_ID();
	void setSinglePriceTag_ID(int SinglePriceTag_ID);
	// @formatter:on

	void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	int getM_HU_PI_Item_Product_ID();
}
