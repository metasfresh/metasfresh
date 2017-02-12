package org.compiere.apps.search;

import java.awt.Dimension;

import javax.swing.JComponent;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.FindPanelUI;

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
 * Container for {@link FindPanel} to be used for embedding it in standard windows.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class FindPanelContainer
{
	protected final FindPanel findPanel;
	
	FindPanelContainer(final FindPanelBuilder builder)
	{
		super();
		
		findPanel = builder.buildFindPanel();
		final int findPanelHeight = AdempierePLAF.getInt(FindPanelUI.KEY_StandardWindow_Height, FindPanelUI.DEFAULT_StandardWindow_Height);
		findPanel.setPreferredSize(new Dimension(500, findPanelHeight));

		init(builder);
		
		runOnExpandedStateChange(new Runnable()
		{
			@Override
			public void run()
			{
				onCollapsedStateChanged();
			}
		});

		setExpanded(!builder.isSearchPanelCollapsed());
	}
	
	protected abstract void init(final FindPanelBuilder builder);
	
	/** @return swing component */
	public abstract JComponent getComponent();

	public abstract boolean isExpanded();

	public abstract void setExpanded(final boolean expanded);

	public abstract void runOnExpandedStateChange(final Runnable runnable);

	public abstract boolean isFocusable();

	public abstract void requestFocus();

	public abstract boolean requestFocusInWindow();
	
	private void onCollapsedStateChanged()
	{
		requestFocus();
	}

}
