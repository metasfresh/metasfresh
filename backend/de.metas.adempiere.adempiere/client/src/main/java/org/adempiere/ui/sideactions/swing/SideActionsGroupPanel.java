package org.adempiere.ui.sideactions.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Component;
import java.awt.Container;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;

import org.adempiere.ui.sideactions.model.ISideAction;
import org.adempiere.ui.sideactions.model.ISideAction.SideActionType;
import org.adempiere.ui.sideactions.model.ISideActionsGroupModel;
import org.jdesktop.swingx.JXTaskPane;

/**
 * Renders an {@link ISideActionsGroupModel} as {@link JXTaskPane}.
 *
 * To be used in {@link SideActionsGroupsListPanel}.
 *
 * @author tsa
 *
 */
class SideActionsGroupPanel extends JXTaskPane
{

	/**
	 *
	 */
	private static final long serialVersionUID = -3109115624961497796L;

	public static final String PROPERTY_Visible = SideActionsGroupPanel.class.getName() + ".Visible";

	private ISideActionsGroupModel model;
	private Container _actionsPanel;

	private final ListDataListener modelListener = new ListDataListener()
	{
		@Override
		public void intervalAdded(final ListDataEvent e)
		{
			final Container actionsPanel = getActionsPanel();
			final ListModel<ISideAction> actionsList = model.getActions();

			for (int i = e.getIndex0(); i <= e.getIndex1(); i++)
			{
				final ISideAction action = actionsList.getElementAt(i);
				final Component actionComp = createActionComponent(action);
				actionsPanel.add(actionComp, i);
			}

			refreshUI();
		}

		@Override
		public void intervalRemoved(final ListDataEvent e)
		{
			final Container actionsPanel = getActionsPanel();
			final int index0 = e.getIndex0();
			for (int i = index0; i <= e.getIndex1(); i++)
			{
				actionsPanel.remove(index0);
			}

			refreshUI();
		}

		@Override
		public void contentsChanged(final ListDataEvent e)
		{
			final Container actionsPanel = getActionsPanel();
			final ListModel<ISideAction> actionsList = model.getActions();

			for (int i = e.getIndex0(); i <= e.getIndex1(); i++)
			{
				final ISideAction action = actionsList.getElementAt(i);
				final Component actionComp = actionsPanel.getComponent(i);
				updateActionComponent(actionComp, action);
			}

			refreshUI();
		}
	};

	public SideActionsGroupPanel()
	{
		super();
		setAnimated(false);

		refreshUI();
	}

	public void setModel(final ISideActionsGroupModel model)
	{
		if (this.model == model)
		{
			return;
		}

		//
		// Un-bind listeners from old model
		if (this.model != null)
		{
			this.model.getActions().removeListDataListener(modelListener);
		}

		//
		// Set new model and refresh
		this.model = model;
		renderAll();

		//
		// Bind listeners to new model
		if (this.model != null)
		{
			setTitle(this.model.getTitle());
			setCollapsed(this.model.isDefaultCollapsed());
			this.model.getActions().addListDataListener(modelListener);
		}
	}

	public ISideActionsGroupModel getModel()
	{
		return model;
	}

	private Container getActionsPanel()
	{
		if (_actionsPanel == null)
		{
			final Container contentPane = getContentPane();
			contentPane.setLayout(new MigLayout("fill, flowy"));
			_actionsPanel = contentPane;
		}
		return _actionsPanel;
	}

	private void renderAll()
	{
		final Container actionsPanel = getActionsPanel();

		actionsPanel.removeAll();

		if (model != null)
		{
			final ListModel<ISideAction> actionsList = model.getActions();
			for (int i = 0; i < actionsList.getSize(); i++)
			{
				final ISideAction action = actionsList.getElementAt(i);
				final Component actionComp = createActionComponent(action);
				actionsPanel.add(actionComp);
			}
		}

		refreshUI();
	}

	private Component createActionComponent(final ISideAction action)
	{
		final SideActionType type = action.getType();
		if (type == SideActionType.Toggle)
		{
			return new CheckableSideActionComponent(action);
		}
		else if (type == SideActionType.ExecutableAction)
		{
			return new HyperlinkSideActionComponent(action);
		}
		else if (type == SideActionType.Label)
		{
			return new LabelSideActionComponent(action);
		}
		else
		{
			throw new IllegalArgumentException("Unknown action type: " + type);
		}
	}

	protected void updateActionComponent(final Component actionComp, final ISideAction action)
	{
		// TODO Auto-generated method stub
	}

	private final boolean hasActions()
	{
		return model != null && model.getActions().getSize() > 0;
	}

	private final void refreshUI()
	{
		// Auto-hide if no actions
		setVisible(hasActions());

		final Container actionsPanel = getActionsPanel();
		actionsPanel.revalidate();
	}

	@Override
	public void setVisible(final boolean visible)
	{
		final boolean visibleOld = isVisible();
		super.setVisible(visible);

		firePropertyChange(PROPERTY_Visible, visibleOld, isVisible());
	}
}
