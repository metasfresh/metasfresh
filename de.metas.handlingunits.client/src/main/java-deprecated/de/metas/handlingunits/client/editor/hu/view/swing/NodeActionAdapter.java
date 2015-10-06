package de.metas.handlingunits.client.editor.hu.view.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

/**
 * Adapts {@link INodeAction} to {@link javax.swing.Action}.
 * 
 * @author tsa
 * 
 */
public final class NodeActionAdapter extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1548162572640839920L;

	private NodeActionAdapter(final ICMAction nodeAction, final HUEditorModel model)
	{
		super(nodeAction.getName());

		Check.assumeNotNull(nodeAction, "nodeAction not null");
		Check.assumeNotNull(model, "model not null");

		this.nodeAction = nodeAction;
		this.model = model;
	}

	public static void addTo(final JMenu menu, final ICMAction action, final HUEditorModel model)
	{
		final NodeActionAdapter actionAdapter = new NodeActionAdapter(action, model);
		final JMenuItem menuItem = menu.add(actionAdapter);
		menuItem.setName(action.getName());
		actionAdapter.component = menuItem;
		menu.getPopupMenu().addPopupMenuListener(actionAdapter.popupMenuListener);

	}

	public static void addTo(final JPopupMenu popupMenu, final ICMAction action, final HUEditorModel model)
	{
		final NodeActionAdapter actionAdapter = new NodeActionAdapter(action, model);
		final JMenuItem menuItem = popupMenu.add(actionAdapter);
		menuItem.setName(action.getName());
		actionAdapter.component = menuItem;
		popupMenu.addPopupMenuListener(actionAdapter.popupMenuListener);
	}

	private final ICMAction nodeAction;
	private final HUEditorModel model;
	private JMenuItem component;

	private final PopupMenuListener popupMenuListener = new PopupMenuListener()
	{
		@Override
		public void popupMenuWillBecomeVisible(final PopupMenuEvent e)
		{
			final boolean enabledNew = checkEnabled();

			if (component != null)
			{
				component.setVisible(enabledNew);
			}
		}

		@Override
		public void popupMenuWillBecomeInvisible(final PopupMenuEvent e)
		{
			// nothing
		}

		@Override
		public void popupMenuCanceled(final PopupMenuEvent e)
		{
			// nothing
		}
	};

	/**
	 * @return true if enabled, false if disabled
	 */
	public boolean checkEnabled()
	{
		final boolean enabledNew = nodeAction.isAvailable(model, model.getSelectedNode());
		setEnabled(enabledNew);
		return enabledNew;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			final IHUTreeNode node = model.getSelectedNode();
			nodeAction.execute(model, node);
		}
		catch (Exception ex)
		{
			final int windowNo = Env.getWindowNo(component);
			Services.get(IClientUI.class).warn(windowNo, ex);
		}
	}
}
