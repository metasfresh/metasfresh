package de.metas.handlingunits.tree.node.hu.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.attribute.api.IHUAttributeSet;
import de.metas.handlingunits.attribute.api.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageAware;
import de.metas.handlingunits.attribute.storage.impl.HUTreeNodeAttributeStorage;
import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.impl.RemoveHUCMAction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public final class HUTreeNode extends AbstractHUTreeNode implements IAttributeStorageAware
{
	private static final IHUTreeNodeCMActionProvider nodeActionProvider = new IHUTreeNodeCMActionProvider()
	{

		@Override
		public List<ICMAction> retrieveCMActions(HUEditorModel model, IHUTreeNode node)
		{
			if (node instanceof HUTreeNode)
			{
				final ICMAction removeAction = RemoveHUCMAction.instance;
				return Arrays.asList(removeAction);
			}
			else
			{
				return Collections.emptyList();
			}
		}
	};

	private final I_M_HU hu;
	private final I_M_HU_PI_Version huPIVersion;
	private final IAttributeStorage storage;

	private String displayName;
	private HUItemHUTreeNode parentItemHU;

	public HUTreeNode(final I_M_HU hu)
	{
		this(hu, hu.getM_HU_PI_Version());
	}

	private HUTreeNode(final I_M_HU hu, final I_M_HU_PI_Version huPIVersion)
	{
		super();

		Check.assumeNotNull(hu, "hu not null");
		this.hu = hu;

		Check.assumeNotNull(huPIVersion, "huPIVersion not null");
		this.huPIVersion = huPIVersion;

		storage = new HUTreeNodeAttributeStorage(this);

		this.displayName = huPIVersion.getM_HU_PI().getName();

		addHUTreeNodeCMActionProvider(nodeActionProvider);
	}

	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	@Override
	public void setDisplayName(final String displayName)
	{
		this.displayName = displayName;
	}

	public I_M_HU_PI_Version getM_HU_PI_Version()
	{
		return huPIVersion;
	}

	public I_M_HU_PI getM_HU_PI()
	{
		return huPIVersion.getM_HU_PI();
	}

	@Override
	public IAttributeStorage getAttributeStorage()
	{
		return storage;
	}

	public I_M_HU getM_HU()
	{
		return hu;
	}

	@Override
	public BigDecimal getQty()
	{
		return null;
	}

	@Override
	public void setParent(final IHUTreeNode parent)
	{
		if (parent == null)
		{
			parentItemHU = null;
		}
		else if (parent instanceof RootHUTreeNode)
		{
			parentItemHU = null;
		}
		else if (parent instanceof HUItemHUTreeNode)
		{
			parentItemHU = (HUItemHUTreeNode)parent;
		}
		else
		{
			throw new AdempiereException("Unsupported parent: " + parent);
		}

		super.setParent(parent);
	}

	public HUItemHUTreeNode getParentItemHU()
	{
		return parentItemHU;
	}

	@Override
	public void setReadonly(final boolean readonly)
	{
		super.setReadonly(readonly);

		if (_attributeSetModel != null)
		{
			_attributeSetModel.setReadonly(readonly);
		}
	}

	private HUAttributeSetModel _attributeSetModel = null;

	public HUAttributeSetModel getHUAttributeSetModel()
	{
		if (_attributeSetModel == null)
		{
			final IHUAttributeSet attributeSet = Services.get(IHUAttributesBL.class).getAttributeSet(getAttributeStorage());
			_attributeSetModel = new HUAttributeSetModel(attributeSet);
			_attributeSetModel.setReadonly(isReadonly());
		}
		return _attributeSetModel;
	}
}
