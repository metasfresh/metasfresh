package de.metas.handlingunits.tree.node.hu.impl;

import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;

public class ItemProductHUTreeNodeProduct implements IHUTreeNodeProduct
{
	private final I_M_HU_PI_Item_Product itemProduct;

	public ItemProductHUTreeNodeProduct(final I_M_HU_PI_Item_Product itemProduct)
	{
		super();
		Check.assumeNotNull(itemProduct, "itemProduct not null");
		this.itemProduct = itemProduct;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return itemProduct.getM_Product();
	}

	public I_C_UOM getC_UOM()
	{
		return itemProduct.getC_UOM();
	}

}
