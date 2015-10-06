package de.metas.handlingunits.tree.node.hu.impl;

import org.adempiere.util.Check;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;

public class ItemProductPMHUTreeNodeProduct implements IHUTreeNodeProduct
{
	private final I_M_HU_PackingMaterial huPM;

	public ItemProductPMHUTreeNodeProduct(final I_M_HU_PackingMaterial huPM)
	{
		super();

		Check.assumeNotNull(huPM, "huPM not null");
		this.huPM = huPM;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return huPM.getM_Product();
	}

	public I_M_HU_PackingMaterial getM_HU_PackingMaterial()
	{
		return huPM;
	}

}
