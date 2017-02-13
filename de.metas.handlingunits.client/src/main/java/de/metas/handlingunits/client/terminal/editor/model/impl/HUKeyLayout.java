package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModelAware;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyChildrenFilter;

/**
 * {@link KeyLayout} which displays {@link IHUKey}s.
 *
 * @author tsa
 *
 */
public class HUKeyLayout extends KeyLayout implements IKeyLayoutSelectionModelAware
{
	private IHUKey parentHUKey;
	private final PropertyChangeListener parentHUKey_ChildrenChangedListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			onParentHUKeyChildrenChanged();
		}
	};

	public HUKeyLayout(final ITerminalContext tc)
	{
		super(tc);

		setColumns(5);

		final IKeyLayoutSelectionModel selectionModel = getKeyLayoutSelectionModel();
		selectionModel.setAllowKeySelection(true);
	}

	@Override
	public String getId()
	{
		return getClass().getName();
	}

	@Override
	public boolean isText()
	{
		return false;
	}

	@Override
	public boolean isNumeric()
	{
		return false;
	}

	@Override
	protected List<ITerminalKey> createKeys()
	{
		// NOTE: keys are not created from here but from set methods
		return Collections.emptyList();
	}

	public void setParentHUKey(final IHUKey parentHUKey)
	{
		// Remove listeners from old Key
		final IHUKey parentHUKeyOld = parentHUKey;
		if (parentHUKeyOld != null)
		{
			parentHUKeyOld.removeListener(IHUKey.ACTION_ChildrenChanged, parentHUKey_ChildrenChangedListener);
			parentHUKeyOld.removeListener(IHUKey.ACTION_ChildrenFilterChanged, parentHUKey_ChildrenChangedListener);
		}

		// don't check if are the same, refresh always
		this.parentHUKey = parentHUKey;
		if (this.parentHUKey != null)
		{
			this.parentHUKey.addListener(IHUKey.ACTION_ChildrenChanged, parentHUKey_ChildrenChangedListener);
			this.parentHUKey.addListener(IHUKey.ACTION_ChildrenFilterChanged, parentHUKey_ChildrenChangedListener);
		}

		//
		// Load keys from parentHUKey children
		setKeysFromParentHUKey();

		//
		// Reset selection
		final IKeyLayoutSelectionModel selectionModel = getKeyLayoutSelectionModel();
		selectionModel.onKeySelected(null);
	}

	/**
	 * Set Keys as {@link #parentHUKey}'s children
	 */
	private final void setKeysFromParentHUKey()
	{
		final List<IHUKey> children;
		if (parentHUKey == null)
		{
			children = Collections.emptyList();
		}
		else
		{
			children = new ArrayList<>(parentHUKey.getChildren());

			//
			// Filter children if there is a filter set
			final IHUKeyChildrenFilter childrenFilter = parentHUKey.getChildrenFilter();
			if (childrenFilter != null)
			{
				for (final Iterator<IHUKey> it = children.iterator(); it.hasNext();)
				{
					final IHUKey child = it.next();
					if (!childrenFilter.accept(child))
					{
						it.remove();
						continue;
					}
				}
			}
		}

		setKeys(children);
	}

	public IHUKey getParentHUKey()
	{
		return parentHUKey;
	}

	public IHUKey getSelectedKey()
	{
		final IKeyLayoutSelectionModel selectionModel = getKeyLayoutSelectionModel();
		final IHUKey huKey = (IHUKey)selectionModel.getSelectedKeyOrNull();
		return huKey;
	}

	@Override
	public String toString()
	{
		return "HUKeyLayout [parentHUKey=" + parentHUKey
				+ ", parentHUKey_ChildrenChangedListener="
				+ parentHUKey_ChildrenChangedListener + "]";
	}

	private final void onParentHUKeyChildrenChanged()
	{
		setKeysFromParentHUKey();
	}
}
