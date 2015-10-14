package de.metas.handlingunits.tree.node.hu.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.api.IHUPIItemProductDAO;
import de.metas.handlingunits.api.IHandlingUnitsBL;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode.IQtyRequest;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;

/**
 * HU Item of type Material (MI)
 * 
 */
public final class HUItemMITreeNode extends AbstractHUTreeNode
{
	private final I_M_HU_Item huItem;
	private final I_M_HU_PI_Item huPIItem;
	private final IHUItemStorage storage;

	private HUDocumentLineTreeNode documentLineTreeNode;
	private HUDocumentLineTreeNode documentLineTreeNodeInitial;
	private IQtyRequest qtyRequest;
	private IQtyRequest qtyRequestInitial;

	public HUItemMITreeNode(final I_M_HU_Item huItem)
	{
		this(huItem, huItem.getM_HU_PI_Item());
	}

	private HUItemMITreeNode(final I_M_HU_Item huItem, final I_M_HU_PI_Item huPIItem)
	{
		super();

		Check.assumeNotNull(huItem, "huItem not null");
		this.huItem = huItem;

		Check.assumeNotNull(huPIItem, "huPIItem not null");
		this.huPIItem = huPIItem;

		final String itemType = huPIItem.getItemType();
		if (!X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemType))
		{
			throw new AdempiereException("Invalid item type: " + itemType + ". Expected: " + X_M_HU_PI_Item.ITEMTYPE_Material);
		}

		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
		this.storage = storageFactory.getStorage(huItem);
	}

	@Override
	public String getDisplayName()
	{
		final I_M_HU_PI_Item itemPI = getM_HU_PI_Item();
		final String itemType = itemPI.getItemType();

		final StringBuilder displayName = new StringBuilder().append("Type=" + itemType);
		if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
		{
			final I_M_HU_PI includedHuPI = itemPI.getIncluded_HU_PI();
			displayName.append(", " + includedHuPI.getName());
		}
		return displayName.toString();
	}

	@Override
	public List<IHUTreeNodeProduct> getAvailableProducts()
	{
		return getAvailableProducts(documentLineTreeNode);
	}

	public List<IHUTreeNodeProduct> getAvailableProducts(final IHUDocumentTreeNode allocationLineTreeNode)
	{
		if (allocationLineTreeNode == null)
		{
			return Collections.emptyList();
		}

		final List<I_M_HU_PI_Item_Product> itemProductsDef = Services.get(IHUPIItemProductDAO.class).retrievePIMaterialItemProducts(getM_HU_PI_Item());
		final List<IHUTreeNodeProduct> nodeProducts = new ArrayList<IHUTreeNodeProduct>(itemProductsDef.size());
		for (final I_M_HU_PI_Item_Product itemProductDef : itemProductsDef)
		{
			if (!allocationLineTreeNode.getProduct().equals(itemProductDef.getM_Product()))
			{
				continue;
			}

			final ItemProductHUTreeNodeProduct nodeProduct = new ItemProductHUTreeNodeProduct(itemProductDef);
			nodeProducts.add(nodeProduct);
		}
		return nodeProducts;
	}

	public I_M_HU_PI_Item getM_HU_PI_Item()
	{
		return huPIItem;
	}

	public I_M_HU_Item getM_HU_Item()
	{
		return huItem;
	}

	public HUDocumentLineTreeNode getHUDocumentLineTreeNode()
	{
		return documentLineTreeNode;
	}

	public IHUDocumentLine getHUDocumentLine()
	{
		if (documentLineTreeNode == null)
		{
			return null;
		}
		return documentLineTreeNode.getHUDocumentLine();
	}

	public void setHUDocumentTreeNode(final IHUDocumentTreeNode documentLineTreeNode)
	{
		if (documentLineTreeNode == null)
		{
			this.documentLineTreeNode = null;
		}
		else
		{
			Check.assumeInstanceOf(documentLineTreeNode, HUDocumentLineTreeNode.class, "documentLineTreeNode");
			this.documentLineTreeNode = (HUDocumentLineTreeNode)documentLineTreeNode;
		}
	}

	public void setHUDocumentTreeNodeInitial(final IHUDocumentTreeNode documentTreeNodeInitial)
	{
		if (documentTreeNodeInitial == null)
		{
			this.documentLineTreeNodeInitial = null;
		}
		else
		{
			Check.assumeInstanceOf(documentTreeNodeInitial, HUDocumentLineTreeNode.class, "documentTreeNodeInitial");
			this.documentLineTreeNodeInitial = (HUDocumentLineTreeNode)documentTreeNodeInitial;
		}
	}

	public HUDocumentLineTreeNode getHUDocumentTreeNodeInitial()
	{
		return documentLineTreeNodeInitial;
	}

	public void setQtyRequest(final IQtyRequest qtyRequest)
	{
		this.qtyRequest = qtyRequest;
	}

	public IQtyRequest getQtyRequest()
	{
		return qtyRequest;
	}

	@Override
	public BigDecimal getQty()
	{
		if (qtyRequest == null)
		{
			return BigDecimal.ZERO;
		}
		return qtyRequest.getQty();
	}

	public BigDecimal getQtyInitial()
	{
		if (qtyRequestInitial == null)
		{
			return BigDecimal.ZERO;
		}
		return qtyRequestInitial.getQty();
	}

	public void setQtyRequestInitial(final IQtyRequest qtyRequestInitial)
	{
		this.qtyRequestInitial = qtyRequestInitial;
	}

	public I_C_UOM getC_UOM()
	{
		final ItemProductHUTreeNodeProduct product = getProduct();
		Check.assumeNotNull(product, "product not null");
		return product.getC_UOM();
	}

	@Override
	public HUTreeNode getParent()
	{
		return (HUTreeNode)super.getParent();
	}

	@Override
	public void setParent(final IHUTreeNode parent)
	{
		Check.assumeInstanceOfOrNull(parent, AbstractHUTreeNode .class, "parent");
		super.setParent(parent);
	}

	@Override
	public ItemProductHUTreeNodeProduct getProduct()
	{
		return (ItemProductHUTreeNodeProduct)super.getProduct();
	}

	@Override
	public void setProduct(final IHUTreeNodeProduct product)
	{
		Check.assumeInstanceOfOrNull(product, ItemProductHUTreeNodeProduct.class, "product");
		super.setProduct(product);
	}

	/**
	 * Checks if given qty can be loaded to this HU Item
	 * 
	 * @param qtyRequested
	 * @return qty that can be actually loaded to this HU item
	 */
	public BigDecimal getQtyAvailableToLoad(final BigDecimal qtyRequested)
	{
		// TODO task 05272 Packtischdialog allocated qty smaller than HU PI Item Product capacity (104437025296)
		return qtyRequested;
	}

	/**
	 * Sets Qty Locked.
	 * 
	 * Locked Qty is quantity which was allocated to this node but is from other allocation source line(s) than the ones that we have it now in our scope
	 * 
	 * @param qtyLocked
	 */
	public void setQtyLocked(BigDecimal qtyLocked)
	{
		// TODO task 05272 Packtischdialog allocated qty smaller than HU PI Item Product capacity (104437025296)

	}

	public IHUItemStorage getStorage()
	{
		return storage;
	}
}
