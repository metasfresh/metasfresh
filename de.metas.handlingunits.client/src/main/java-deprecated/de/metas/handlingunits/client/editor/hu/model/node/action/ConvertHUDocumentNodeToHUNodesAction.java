package de.metas.handlingunits.client.editor.hu.model.node.action;

import java.math.BigDecimal;
import java.util.UUID;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.command.model.action.impl.ReversedAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.model.context.menu.impl.SingletonHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMActionGroup;
import de.metas.handlingunits.client.editor.hu.view.context.menu.impl.Action2CMActionWrapper;
import de.metas.handlingunits.client.editor.hu.view.context.menu.impl.CMActionGroup;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.tree.factory.IHUTreeNodeFactory;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode.IQtyRequest;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.RootHUTreeNode;

/**
 * Take out one HU from document node
 * 
 * @author tsa
 * 
 */
public class ConvertHUDocumentNodeToHUNodesAction implements IAction
{
	private final HUEditorModel model;
	private final IHUTreeNode parentNode;
	private final HUDocumentTreeNode documentTreeNode;

	private final String actionName;
	private final IHUTreeNodeCMActionProvider innerHUTreeNodeActionProvider;

	private HUTreeNode innerHUTreeNode;

	public ConvertHUDocumentNodeToHUNodesAction(final HUEditorModel model,
			final RootHUTreeNode node,
			final HUDocumentTreeNode documentTreeNode)
	{
		super();
		this.model = model;
		this.parentNode = node;
		this.documentTreeNode = documentTreeNode;

		this.actionName = "Take out HU";

		//
		// Create put back action to inner HU Tree Node
		{
			final IAction putBackAction = new ReversedAction(this);
			final ICMAction putBackCMAction = new Action2CMActionWrapper(putBackAction,
					getClass().getName() + "#" + UUID.randomUUID(), // ID
					new CMActionGroup(actionName, ICMActionGroup.SEQNO_Middle) // actionGroup
			);
			innerHUTreeNodeActionProvider = new SingletonHUTreeNodeCMActionProvider(putBackCMAction);
		}
	}

	@Override
	public String getActionName()
	{
		return "Take out HU";
	}

	private boolean doItExecuted = false;

	@Override
	public void doIt()
	{
		Check.assume(!doItExecuted, "doIt not executed");
		Check.assumeNull(innerHUTreeNode, "innerHUTreeNode is null");

		innerHUTreeNode = createInnerHUToTreeNode();

		// add node to parent
		model.addNode(innerHUTreeNode, parentNode);

		doItExecuted = true;
	}

	@Override
	public void undoIt()
	{
		Check.assume(doItExecuted, "doIt executed");
		Check.assumeNotNull(innerHUTreeNode, "innerHUTreeNode is not null");

		// Remove from parent
		final RemoveHUAction removeHU = new RemoveHUAction(model, innerHUTreeNode);
		removeHU.doIt();

		innerHUTreeNode = null;

		doItExecuted = false;
	}

	public HUTreeNode createInnerHUToTreeNode()
	{
		final IHUTreeNodeFactory nodeFactory = model.getHUTreeNodeFactory();

		final IHUDocument document = documentTreeNode.getHUDocument();
		final I_M_HU hu = document.getInnerHandlingUnit();

		final HUTreeNode huNode = (HUTreeNode)nodeFactory.createNode(hu);
		huNode.setDisplayName(documentTreeNode.getDisplayName());
		huNode.setReadonly(true);

		for (final IHUDocumentTreeNode child : documentTreeNode.getChildren())
		{
			final HUDocumentLineTreeNode documentLineNode = (HUDocumentLineTreeNode)child;
			final IHUTreeNode huItemNode = convertInnerHUToTreeNode(documentLineNode);
			nodeFactory.addChild(huNode, huItemNode);
		}

		huNode.addHUTreeNodeCMActionProvider(innerHUTreeNodeActionProvider);
		return huNode;
	}

	public IHUTreeNode convertInnerHUToTreeNode(final HUDocumentLineTreeNode documentLineNode)
	{
		final IHUTreeNodeFactory nodeFactory = model.getHUTreeNodeFactory();

		final IHUDocumentLine documentLine = documentLineNode.getHUDocumentLine();
		final I_M_HU_Item huItem = documentLine.getInnerHUItem();

		final HUItemMITreeNode huItemNode = (HUItemMITreeNode)nodeFactory.createNode(huItem);

		//
		// Allocate Document Line Node to HU Item Node
		{
			final BigDecimal qty = documentLineNode.getQtyRemaining();
			final IQtyRequest qtyRequest = documentLineNode.requestQty(qty);
			// FIXME: if qtyRequest != qty throw exception and deallocate everything
			huItemNode.setHUDocumentTreeNodeInitial(documentLineNode);
			huItemNode.setHUDocumentTreeNode(documentLineNode);
			huItemNode.setQtyRequestInitial(qtyRequest);
			huItemNode.setQtyRequest(qtyRequest);
		}

		model.getHUDocumentsModel().fireNodeChanged(documentLineNode);

		return huItemNode;
	}

}
