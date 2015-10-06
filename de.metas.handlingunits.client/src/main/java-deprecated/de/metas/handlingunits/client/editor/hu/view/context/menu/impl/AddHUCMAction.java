package de.metas.handlingunits.client.editor.hu.view.context.menu.impl;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.node.action.AddHUAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMActionGroup;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.RootHUTreeNode;

public class AddHUCMAction implements ICMAction
{
	private static final ICMActionGroup group = new CMActionGroup("Add HU");
	
	private final String id;
	private final I_M_HU_PI huPI;

	public AddHUCMAction(final I_M_HU_PI huPI)
	{
		super();

		this.huPI = huPI;

		this.id = getClass().getName() + "#M_HU_PI_ID=" + huPI.getM_HU_PI_ID();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return "Add " + huPI.getName();
	}
	
	@Override
	public ICMActionGroup getCMActionGroup()
	{
		return group;
	}

	@Override
	public boolean isAvailable(final HUEditorModel model, final IHUTreeNode node)
	{
		if (node.isReadonly())
		{
			return false;
		}

		if (node instanceof RootHUTreeNode)
		{
			return true;
		}

		if (!(node instanceof HUItemHUTreeNode))
		{
			return false;
		}

		return true;
	}

	@Override
	public void execute(final HUEditorModel model, final IHUTreeNode node)
	{
		final IAction action = new AddHUAction(model, node, huPI);
		model.getHistory().execute(action);
	}
}
