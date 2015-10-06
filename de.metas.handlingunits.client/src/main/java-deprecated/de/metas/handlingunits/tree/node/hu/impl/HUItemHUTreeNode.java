package de.metas.handlingunits.tree.node.hu.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageAware;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.impl.AddHUCMAction;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;

/**
 * HU Item of type Included Handling Unit (HU)
 * 
 * @author tsa
 * 
 */
public class HUItemHUTreeNode extends AbstractHUTreeNode implements IAttributeStorageAware
{
	private static final transient IHUTreeNodeCMActionProvider nodeActionProvider = new IHUTreeNodeCMActionProvider()
	{
		@Override
		public List<ICMAction> retrieveCMActions(HUEditorModel model, IHUTreeNode node)
		{
			if (node instanceof HUItemHUTreeNode)
			{
				final HUItemHUTreeNode selectedHUItem = (HUItemHUTreeNode)node;

				final I_M_HU_PI_Item itemPI = selectedHUItem.getM_HU_PI_Item();
				final ICMAction addHUAction = new AddHUCMAction(itemPI.getIncluded_HU_PI());
				return Collections.singletonList(addHUAction);
			}
			else
			{
				return Collections.emptyList();
			}
		}
	};

	private final I_M_HU_Item huItem;
	private final I_M_HU_PI_Item huPIItem;

	public HUItemHUTreeNode(final I_M_HU_Item huItem)
	{
		this(huItem, huItem.getM_HU_PI_Item());
	}

	private HUItemHUTreeNode(final I_M_HU_Item huItem, final I_M_HU_PI_Item huPIItem)
	{
		super();

		Check.assumeNotNull(huItem, "huItem not null");
		this.huItem = huItem;

		Check.assumeNotNull(huPIItem, "huPIItem not null");
		this.huPIItem = huPIItem;

		final String itemType = huPIItem.getItemType();
		if (!X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
		{
			throw new AdempiereException("Invalid item type: " + itemType + ". Expected: " + X_M_HU_PI_Item.ITEMTYPE_HandlingUnit);
		}

		addHUTreeNodeCMActionProvider(nodeActionProvider);
	}

	@Override
	public String getDisplayName()
	{
		final I_M_HU_PI_Item itemPI = getM_HU_PI_Item();
		final String itemType = itemPI.getItemType();

		final StringBuilder displayName = new StringBuilder().append("Type=" + itemType);

		final I_M_HU_PI includedHuPI = itemPI.getIncluded_HU_PI();
		displayName.append(", " + includedHuPI.getName());

		return displayName.toString();
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
		Check.assumeInstanceOfOrNull(parent, HUTreeNode.class, "parent");
		super.setParent(parent);
	}

	@Override
	public ItemProductPMHUTreeNodeProduct getProduct()
	{
		return null;
	}

	@Override
	public void setProduct(final IHUTreeNodeProduct product)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getQty()
	{
		return BigDecimal.valueOf(getChildren().size());
	}

	@Override
	public IAttributeStorage getAttributeStorage()
	{
		final HUTreeNode parent = getParent();
		if (parent == null)
		{
			return NullAttributeStorage.instance;
		}
		return parent.getAttributeStorage();
	}
}
