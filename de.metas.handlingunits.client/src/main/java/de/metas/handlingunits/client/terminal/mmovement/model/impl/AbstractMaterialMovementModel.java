package de.metas.handlingunits.client.terminal.mmovement.model.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.mmovement.model.IMaterialMovementModel;
import de.metas.handlingunits.model.I_M_HU;

public abstract class AbstractMaterialMovementModel implements IMaterialMovementModel
{
	protected final WeakPropertyChangeSupport pcs;

	private final ITerminalContext terminalContext;

	protected final IHUPOSLayoutConstants layoutConstantsBL = Services.get(IHUPOSLayoutConstants.class);
	private final Properties layoutConstants;

	private boolean disposed = false;

	public AbstractMaterialMovementModel(final ITerminalContext terminalContext)
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;
		pcs = terminalContext.createPropertyChangeSupport(this);

		layoutConstants = layoutConstantsBL.getConstants(terminalContext);

		terminalContext.addToDisposableComponents(this);
	}

	/**
	 * Nothing to do at this level; {@link #pcs} was created using {@link ITerminalContext#createPropertyChangeSupport(Object)}, so clearing it will also be done by that context
	 */
	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}


	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(propertyName, listener);
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public final Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	@Override
	public Properties getLayoutConstants()
	{
		return layoutConstants;
	}

	protected final <V> void firePropertyChanged(final String propertyName, final V valueOld, final V valueNew)
	{
		pcs.firePropertyChange(propertyName, valueOld, valueNew);
	}

	/**
	 * @param huKey
	 * @return children of the given huKey, but with their names refreshed
	 */
	protected final List<IHUKey> getRefreshChildren(final IHUKey huKey)
	{
		final List<IHUKey> children = huKey.getChildren();
		for (final IHUKey child : children)
		{
			refreshChild(child);
		}
		return children;
	}

	private final void refreshChild(final IHUKey groupingHUKey)
	{
		Check.assumeNotNull(groupingHUKey, "groupingHUKey not null");
		if (!groupingHUKey.isGrouping())
		{
			groupingHUKey.updateName();
			return;
		}

		final Iterator<IHUKey> childrenIt = groupingHUKey.getChildren().iterator();
		while (childrenIt.hasNext())
		{
			final HUKey huKey = HUKey.castIfPossible(childrenIt.next());
			if (huKey == null)
			{
				continue;
			}
			final I_M_HU hu = huKey.getM_HU();

			final boolean destroyed = Services.get(IHandlingUnitsBL.class).isDestroyedRefreshFirst(hu);
			if (destroyed)
			{
				groupingHUKey.removeChild(huKey);
			}
		}
	}
}
