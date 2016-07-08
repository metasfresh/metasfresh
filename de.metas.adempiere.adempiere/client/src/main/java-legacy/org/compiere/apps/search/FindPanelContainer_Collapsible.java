package org.compiere.apps.search;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import org.adempiere.plaf.AdempiereTaskPaneUI;
import org.adempiere.plaf.IUISubClassIDAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXTaskPane;

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

/**
 * {@link FindPanelContainer} implementation as a collapsible pane.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class FindPanelContainer_Collapsible extends FindPanelContainer
{
	private CollapsiblePanel _findPanelCollapsible;

	FindPanelContainer_Collapsible(final FindPanelBuilder builder)
	{
		super(builder);
	}
	
	@Override
	protected void init(final FindPanelBuilder builder)
	{
		final CollapsiblePanel findPanelCollapsible = getCollapsiblePanel();
		findPanelCollapsible.setTitle(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Find"));
		findPanelCollapsible.add(findPanel);
	}
	
	private synchronized final CollapsiblePanel getCollapsiblePanel()
	{
		if (_findPanelCollapsible == null)
		{
			_findPanelCollapsible = new CollapsiblePanel();
		}
		return _findPanelCollapsible;
	}

	@Override
	public JComponent getComponent()
	{
		final CollapsiblePanel findPanelCollapsible = getCollapsiblePanel();
		return findPanelCollapsible;
	}

	@Override
	public boolean isExpanded()
	{
		final CollapsiblePanel findPanelCollapsible = getCollapsiblePanel();
		return !findPanelCollapsible.isCollapsed();
	}

	@Override
	public void setExpanded(final boolean expanded)
	{
		final CollapsiblePanel findPanelCollapsible = getCollapsiblePanel();
		findPanelCollapsible.setCollapsed(!expanded);
	}

	@Override
	public final void requestFocus()
	{
		final CollapsiblePanel findPanelCollapsible = getCollapsiblePanel();
		if (findPanelCollapsible.isCollapsed())
		{
			return;
		}

		findPanel.requestFocus();
	}

	@Override
	public final boolean requestFocusInWindow()
	{
		final CollapsiblePanel findPanelCollapsible = getCollapsiblePanel();
		if (findPanelCollapsible.isCollapsed())
		{
			return false;
		}

		return findPanel.requestFocusInWindow();
	}

	/**
	 * @return true if it's expanded and the underlying {@link FindPanel} allows focus.
	 */
	@Override
	public boolean isFocusable()
	{
		if (!isExpanded())
		{
			return false;
		}

		return findPanel.isFocusable();
	}

	/**
	 * Adds a runnable to be executed when the this panel is collapsed or expanded.
	 *
	 * @param runnable
	 */
	@Override
	public void runOnExpandedStateChange(final Runnable runnable)
	{
		Check.assumeNotNull(runnable, "runnable not null");
		final CollapsiblePanel findPanelCollapsible = getCollapsiblePanel();
		findPanelCollapsible.addPropertyChangeListener("collapsed", new PropertyChangeListener()
		{

			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				runnable.run();
			}
		});
	}

	private static final class CollapsiblePanel extends JXTaskPane implements IUISubClassIDAware
	{
		private static final long serialVersionUID = 1L;

		public CollapsiblePanel()
		{
			super();
		}

		@Override
		public String getUISubClassID()
		{
			return AdempiereTaskPaneUI.UISUBCLASSID_VPanel_FindPanel;
		}
	}
}
