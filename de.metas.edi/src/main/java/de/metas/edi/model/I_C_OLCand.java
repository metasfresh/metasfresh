package de.metas.edi.model;

import de.metas.pricing.attributebased.IProductPriceAware;

public interface I_C_OLCand extends de.metas.handlingunits.model.I_C_OLCand, IProductPriceAware
{
	//@formatter:off
	// NOTE: mainly used for Excel imports (see task 08839)
    public static final String COLUMNNAME_M_ProductPrice_ID = "M_ProductPrice_ID";
	public void setM_ProductPrice_ID (int M_ProductPrice_ID);
	@Override
	public int getM_ProductPrice_ID();
	public org.compiere.model.I_M_ProductPrice getM_ProductPrice() throws RuntimeException;
	public void setM_ProductPrice(org.compiere.model.I_M_ProductPrice M_ProductPrice);
	//@formatter:on

	//@formatter:off
	// NOTE: mainly used for Excel imports (see task 08839)
	//public static final String COLUMNNAME_IsExplicitProductPriceAttribute = "IsExplicitProductPriceAttribute";
	@Override
	public boolean isExplicitProductPriceAttribute();
	public void setIsExplicitProductPriceAttribute(final boolean IsExplicitProductPriceAttribute);
	//@formatter:on
}
