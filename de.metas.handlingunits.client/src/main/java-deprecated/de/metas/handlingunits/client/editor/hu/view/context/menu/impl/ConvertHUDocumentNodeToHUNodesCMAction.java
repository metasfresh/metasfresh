package de.metas.handlingunits.client.editor.hu.view.context.menu.impl;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.api.IHandlingUnitsBL;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.node.action.ConvertHUDocumentNodeToHUNodesAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMActionGroup;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.RootHUTreeNode;

public class ConvertHUDocumentNodeToHUNodesCMAction implements ICMAction
{
	private static final ICMActionGroup group = new CMActionGroup("Take out", ICMActionGroup.SEQNO_Top);

	// private I_M_HU hu;
	private final String huDisplayName;
	private final String id;

	private final HUDocumentTreeNode documentTreeNode;

	public ConvertHUDocumentNodeToHUNodesCMAction(final I_M_HU hu, final HUDocumentTreeNode documentTreeNode)
	{
		super();

		Check.assumeNotNull(documentTreeNode, "documentTreeNode not null");
		this.documentTreeNode = documentTreeNode;

		Check.assumeNotNull(hu, "hu not null");
		// this.hu = hu;
		this.huDisplayName = Services.get(IHandlingUnitsBL.class).getDisplayName(hu);

		this.id = getClass().getName() + "#M_HU_ID=" + hu.getM_HU_ID();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return group.getName() + " " + huDisplayName;
	}

	@Override
	public ICMActionGroup getCMActionGroup()
	{
		return group;
	}

	@Override
	public boolean isAvailable(HUEditorModel model, IHUTreeNode node)
	{
		if (!(node instanceof RootHUTreeNode))
		{
			return false;
		}

		// NOTE: we are not checking for "hu.getM_HU_Item_Parent_ID() <= 0".
		// It was already made when HUDocument was created
		// if (hu.getM_HU_Item_Parent_ID() <= 0)
		// {
		// return false;
		// }

		if (!hasRemainingQtys())
		{
			return false;
		}

		return true;
	}

	private boolean hasRemainingQtys()
	{
		for (final IHUDocumentTreeNode child : documentTreeNode.getChildren())
		{
			if (child instanceof HUDocumentLineTreeNode)
			{
				final HUDocumentLineTreeNode documentLineNode = (HUDocumentLineTreeNode)child;
				if (documentLineNode.getQtyRemaining().signum() > 0)
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void execute(HUEditorModel model, IHUTreeNode node)
	{
		final RootHUTreeNode rootNode = (RootHUTreeNode)node;
		final ConvertHUDocumentNodeToHUNodesAction action = new ConvertHUDocumentNodeToHUNodesAction(model, rootNode, documentTreeNode);
		model.getHistory().execute(action);
	}
}
