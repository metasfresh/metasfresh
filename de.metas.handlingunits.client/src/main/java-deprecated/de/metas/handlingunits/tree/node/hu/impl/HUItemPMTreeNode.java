package de.metas.handlingunits.tree.node.hu.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;

/**
 * HU Item of type Packing Material (PM)
 * 
 * @author tsa
 * 
 */
public class HUItemPMTreeNode extends AbstractHUTreeNode
{
	private final I_M_HU_Item huItem;
	private final I_M_HU_PI_Item huPIItem;
	private final ItemProductPMHUTreeNodeProduct huPackingMaterial;

	public HUItemPMTreeNode(final I_M_HU_Item huItem)
	{
		this(huItem, huItem.getM_HU_PI_Item());
	}

	private HUItemPMTreeNode(final I_M_HU_Item huItem, final I_M_HU_PI_Item huPIItem)
	{
		super();

		Check.assumeNotNull(huItem, "huItem not null");
		this.huItem = huItem;

		Check.assumeNotNull(huPIItem, "huPIItem not null");
		this.huPIItem = huPIItem;

		final String itemType = huPIItem.getItemType();
		if (!X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(itemType))
		{
			throw new AdempiereException("Invalid item type: " + itemType + ". Expected: " + X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
		}

		final I_M_HU_PackingMaterial huPackingMaterialDef = huPIItem.getM_HU_PackingMaterial();
		huPackingMaterial = new ItemProductPMHUTreeNodeProduct(huPackingMaterialDef);

		setProduct(huPackingMaterial);
	}

	@Override
	public String getDisplayName()
	{
		final I_M_HU_PI_Item itemPI = getM_HU_PI_Item();
		final String itemType = itemPI.getItemType();

		final StringBuilder displayName = new StringBuilder().append("Type=" + itemType);
		return displayName.toString();
	}

	@Override
	public List<IHUTreeNodeProduct> getAvailableProducts()
	{
		return Collections.singletonList((IHUTreeNodeProduct)huPackingMaterial);
	}

	public I_M_HU_PI_Item getM_HU_PI_Item()
	{
		return huPIItem;
	}

	public I_M_HU_Item getM_HU_Item()
	{
		return huItem;
	}

	@Override
	public HUTreeNode getParent()
	{
		return (HUTreeNode)super.getParent();
	}

	@Override
	public void setParent(final IHUTreeNode parent)
	{
		Check.assumeInstanceOfOrNull(parent, AbstractHUTreeNode.class, "parent");
		super.setParent(parent);
	}

	@Override
	public ItemProductPMHUTreeNodeProduct getProduct()
	{
		return (ItemProductPMHUTreeNodeProduct)super.getProduct();
	}

	@Override
	public void setProduct(final IHUTreeNodeProduct product)
	{
		Check.assumeInstanceOfOrNull(product, ItemProductPMHUTreeNodeProduct.class, "product");
		super.setProduct(product);
	}

	@Override
	public BigDecimal getQty()
	{
		return null;
	}
}
