package de.metas.handlingunits.client.editor.attr.helper;

import org.adempiere.util.Check;

import de.metas.handlingunits.attribute.api.IAttributeValue;
import de.metas.handlingunits.attribute.api.IHUAttributeSet;
import de.metas.handlingunits.attribute.api.impl.HUAttributeSet;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;

public final class StorageHelper
{
	private StorageHelper()
	{
		super();
	}

	/**
	 * Propagate an empty value, then set the value back for the node's storage, so that the storage remains unchanged.
	 * 
	 * @param node
	 */
	public static void propagateEmptyValues(final IHUTreeNode node)
	{
		final IAttributeStorage storage = StorageHelper.getNodeStorage(node);
		final IHUAttributeSet attributeSet = new HUAttributeSet(storage);

		for (final IAttributeValue value : storage.getAttributeValues())
		{
			final Object oldValue = value.getValue();
			// propagate empty value
			attributeSet.setValue(value.getM_Attribute(), value.getEmptyValue(), true);
			// set old value back to the attribute with NO propagation
			attributeSet.setValue(value.getM_Attribute(), oldValue, false);
		}
	}

	/**
	 * Artificially propagate node storage values.
	 * 
	 * @param node
	 */
	public static void propagateNodeValues(final IHUTreeNode node)
	{
		final IAttributeStorage storage = StorageHelper.getNodeStorage(node);
		final IHUAttributeSet attributeSet = new HUAttributeSet(storage);

		for (final IAttributeValue value : storage.getAttributeValues())
		{
			attributeSet.setValue(value.getM_Attribute(), value.getValue(), true);
		}
	}

	private static IAttributeStorage getNodeStorage(final IHUTreeNode node)
	{
		Check.assumeInstanceOf(node, HUTreeNode.class, "node");
		final HUTreeNode huNode = (HUTreeNode)node;
		return huNode.getAttributeStorage();
	}
}
