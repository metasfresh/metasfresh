package de.metas.handlingunits.client.editor.hu.view.column.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.util.Util;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.node.action.SetHUAllocationSourceAction;
import de.metas.handlingunits.client.editor.view.column.impl.AbstractTreeTableColumn;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;

public class MHUDocumentLineColumn extends AbstractTreeTableColumn<IHUTreeNode>
{
	private final HUEditorModel model;

	public MHUDocumentLineColumn(final String columnHeader, final HUEditorModel model)
	{
		super(columnHeader);

		this.model = model;
	}

	@Override
	public boolean isEditable(final IHUTreeNode node)
	{
		if (!(node instanceof HUItemMITreeNode))
		{
			return false;
		}

		if (node.isReadonly())
		{
			return false;
		}

		// has allocation?
		if (model.getHUDocumentsModel().isEmpty())
		{
			return false;
		}

		return true;
	}

	@Override
	public HUDocumentLineTreeNode getValue(final IHUTreeNode node)
	{
		// item wrapper contains allocation tree node info
		if (!(node instanceof HUItemMITreeNode))
		{
			return null;
		}
		final HUItemMITreeNode itemNode = (HUItemMITreeNode)node;

		return itemNode.getHUDocumentLineTreeNode();
	}

	@Override
	public void setValue(final IHUTreeNode node, final Object value)
	{
		Check.assume(node instanceof HUItemMITreeNode, "node instanceof HUItemTreeNode", node);
		final HUItemMITreeNode itemNode = (HUItemMITreeNode)node;

		final IHUDocumentTreeNode documentNode = (IHUDocumentTreeNode)value;
		final IHUDocumentTreeNode documentNodeOld = itemNode.getHUDocumentLineTreeNode();
		if (Util.equals(documentNodeOld, documentNode))
		{
			// nothing changed
			return;
		}

		final IAction action = new SetHUAllocationSourceAction(model, itemNode, documentNode);
		model.getHistory().execute(action);
	}

	@Override
	public boolean isCombobox()
	{
		return true;
	}

	@Override
	public List<Object> getAvailableValuesList(final IHUTreeNode node)
	{
		final List<HUDocumentLineTreeNode> documentLineNodes = model.getHUDocumentsModel().getHUDocumentLineTreeNodes();
		final List<Object> result = new ArrayList<Object>(documentLineNodes.size());
		for (final HUDocumentLineTreeNode documentLineNode : documentLineNodes)
		{
			if (documentLineNode.isReadonly())
			{
				continue;
			}

			result.add(documentLineNode);
		}
		return result;
	}

	@Override
	public String getDisplayName(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		final IHUDocumentTreeNode nodeValue = (IHUDocumentTreeNode)value;
		return nodeValue.getDisplayName();
	}

	@Override
	public Class<?> getColumnType()
	{
		return String.class;
	}
}
