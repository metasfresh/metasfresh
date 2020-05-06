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


import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.adempiere.ui.sideactions.model.ISideActionsGroupModel;
import org.adempiere.ui.sideactions.model.ISideActionsGroupsListModel;
import org.compiere.swing.CPanel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.ScrollableSizeHint;

/**
 * Renders an {@link ISideActionsGroupsListModel} as {@link JXTaskPaneContainer}.
 * 
 * @author tsa
 *
 */
public class SideActionsGroupsListPanel extends CPanel
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1208213387721766864L;

	// services
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final JXTaskPaneContainer contentPanel;
	private ISideActionsGroupsListModel model;

	/** Groups list listener */
	private final ListDataListener groupsListModelListener = new ListDataListener()
	{
		@Override
		public void intervalAdded(final ListDataEvent e)
		{
			final ListModel<ISideActionsGroupModel> groups = model.getGroups();

			for (int i = e.getIndex0(); i <= e.getIndex1(); i++)
			{
				final ISideActionsGroupModel group = groups.getElementAt(i);
				final SideActionsGroupPanel groupComp = createGroupComponent(group);
				contentPanel.add(groupComp, i);
			}

			refreshUI();
		}

		@Override
		public void intervalRemoved(final ListDataEvent e)
		{
			final int index0 = e.getIndex0();
			for (int i = index0; i <= e.getIndex1(); i++)
			{
				final SideActionsGroupPanel groupComp = (SideActionsGroupPanel)contentPanel.getComponent(index0);
				contentPanel.remove(index0);

				destroyGroupComponent(groupComp);
			}

			refreshUI();
		}

		@Override
		public void contentsChanged(final ListDataEvent e)
		{
			logger.warn("Changing the content is not synchronized: " + e);
		}
	};

	private final PropertyChangeListener groupPanelChangedListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			refreshUI();
		}
	};

	public SideActionsGroupsListPanel()
	{
		super();

		contentPanel = new JXTaskPaneContainer();
		contentPanel.setScrollableWidthHint(ScrollableSizeHint.FIT);
		contentPanel.setOpaque(false);

		final JScrollPane contentPaneScroll = new JScrollPane();
		contentPaneScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPaneScroll.getViewport().add(contentPanel);
		contentPaneScroll.setOpaque(false);

		setLayout(new BorderLayout());
		this.add(contentPaneScroll, BorderLayout.CENTER);
		this.setOpaque(false);

		refreshUI();
	}

	public void setModel(final ISideActionsGroupsListModel model)
	{
		if (this.model == model)
		{
			return;
		}

		if (this.model != null)
		{
			model.getGroups().removeListDataListener(groupsListModelListener);
		}

		this.model = model;
		renderAll();

		if (this.model != null)
		{
			model.getGroups().addListDataListener(groupsListModelListener);
		}
	}

	private void renderAll()
	{
		final ListModel<ISideActionsGroupModel> groups = model.getGroups();

		for (int i = 0; i < groups.getSize(); i++)
		{
			final ISideActionsGroupModel group = groups.getElementAt(i);
			final SideActionsGroupPanel groupComp = createGroupComponent(group);
			contentPanel.add(groupComp);
		}

		refreshUI();
	}

	private final SideActionsGroupPanel createGroupComponent(final ISideActionsGroupModel group)
	{
		final SideActionsGroupPanel groupComp = new SideActionsGroupPanel();
		groupComp.setModel(group);
		groupComp.addPropertyChangeListener(SideActionsGroupPanel.PROPERTY_Visible, groupPanelChangedListener);
		return groupComp;
	}

	private void destroyGroupComponent(final SideActionsGroupPanel groupComp)
	{
		groupComp.removePropertyChangeListener(SideActionsGroupPanel.PROPERTY_Visible, groupPanelChangedListener);
	}

	protected void refreshUI()
	{
		autoHideIfNeeded();
		contentPanel.revalidate();
	}

	/**
	 * Auto-hide if no groups or groups are not visible
	 */
	private final void autoHideIfNeeded()
	{
		boolean haveVisibleGroups = false;
		for (Component groupComp : contentPanel.getComponents())
		{
			if (groupComp.isVisible())
			{
				haveVisibleGroups = true;
				break;
			}
		}
		setVisible(haveVisibleGroups);
	}

}
