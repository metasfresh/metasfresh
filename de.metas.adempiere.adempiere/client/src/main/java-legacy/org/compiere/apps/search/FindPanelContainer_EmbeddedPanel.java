package org.compiere.apps.search;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;

import org.adempiere.util.Check;

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
 * {@link FindPanelContainer} implementation which only contains the actual {@link FindPanel}.
 * 
 * Calling {@link #setExpanded(boolean)} will just show/hide the FindPanel.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class FindPanelContainer_Embedded extends FindPanelContainer
{
	private PropertyChangeSupport _pcs;

	private static final String PROPERTY_Expanded = FindPanelContainer.class.getName() + ".Expanded";
	private boolean expanded = false;

	public FindPanelContainer_Embedded(final FindPanelBuilder builder)
	{
		super(builder);
	}
	
	@Override
	protected void init(final FindPanelBuilder builder)
	{
		findPanel.setVisible(expanded);
		findPanel.setEnabled(expanded);
	}

	@Override
	public final JComponent getComponent()
	{
		return findPanel;
	}

	@Override
	public boolean isExpanded()
	{
		return expanded;
	}

	@Override
	public void setExpanded(final boolean expanded)
	{
		if (this.expanded == expanded)
		{
			return;
		}

		this.expanded = expanded;

		findPanel.setVisible(expanded);
		findPanel.setEnabled(expanded);

		_pcs.firePropertyChange(PROPERTY_Expanded, !expanded, expanded);
	}

	@Override
	public boolean isFocusable()
	{
		return findPanel.isFocusable();
	}

	@Override
	public void requestFocus()
	{
		findPanel.requestFocus();
	}

	@Override
	public boolean requestFocusInWindow()
	{
		return findPanel.requestFocusInWindow();
	}
	
	private final synchronized PropertyChangeSupport getPropertyChangeSupport()
	{
		if (_pcs == null)
		{
			_pcs = new PropertyChangeSupport(this);
		}
		return _pcs;
	}

	@Override
	public void runOnExpandedStateChange(final Runnable runnable)
	{
		Check.assumeNotNull(runnable, "runnable not null");
		getPropertyChangeSupport().addPropertyChangeListener(PROPERTY_Expanded, new PropertyChangeListener()
		{

			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				runnable.run();
			}
		});
	}
}
