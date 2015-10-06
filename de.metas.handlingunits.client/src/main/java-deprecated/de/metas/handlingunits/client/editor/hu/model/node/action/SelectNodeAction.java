package de.metas.handlingunits.client.editor.hu.model.node.action;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.command.model.action.impl.CompositeAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public class SelectNodeAction implements IAction
{
	private final HUEditorModel model;
	private final IHUTreeNode node;
	private final boolean selected;
	private final boolean selectedOld;
	private final CompositeAction selectChildrenActions;

	private boolean allowDo = true;

	public SelectNodeAction(final HUEditorModel model,
			final IHUTreeNode node,
			final boolean selected)
	{
		super();

		Check.assumeNotNull(model, "model not null");
		this.model = model;

		Check.assumeNotNull(node, "node not null");
		this.node = node;

		this.selected = selected;
		this.selectedOld = node.isSelected();

		this.selectChildrenActions = new CompositeAction();
		for (final IHUTreeNode child : node.getChildren())
		{
			final SelectNodeAction action = new SelectNodeAction(model, child, selected);
			selectChildrenActions.addAction(action);
		}
	}

	@Override
	public String getActionName()
	{
		return selected ? "Select" : "Deselect";
	}

	@Override
	public void doIt()
	{
		Check.assume(allowDo, "doIt shall not be executed before");
		allowDo = false;

		node.setSelected(selected);
		selectChildrenActions.doIt();

		fireChangeEvents();
	}

	@Override
	public void undoIt()
	{
		Check.assume(!allowDo, "doIt shall be executed before");
		allowDo = true;

		selectChildrenActions.undoIt();
		node.setSelected(selectedOld);

		fireChangeEvents();
	}

	private void fireChangeEvents()
	{
		// notify tree that model was changed
		model.fireNodeChanged(node);
	}
}
