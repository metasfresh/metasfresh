package de.metas.process.ui;

import javax.swing.SwingUtilities;

import com.google.common.base.MoreObjects;

import de.metas.process.ASyncProcess;
import de.metas.process.ProcessInfo;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2016 metas GmbH
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

/*package*/final class SwingASyncProcess implements ASyncProcess
{
	public static final ASyncProcess of(final ASyncProcess delegate)
	{
		if (delegate instanceof SwingASyncProcess)
		{
			return delegate;
		}
		return new SwingASyncProcess(delegate);
	}

	private final ASyncProcess delegate;

	private SwingASyncProcess(final ASyncProcess delegate)
	{
		super();
		this.delegate = delegate;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(delegate).toString();
	}

	@Override
	public void lockUI(final ProcessInfo pi)
	{
		invokeInEDT(() -> delegate.lockUI(pi));
	}

	@Override
	public void unlockUI(final ProcessInfo pi)
	{
		invokeInEDT(() -> delegate.unlockUI(pi));
	}

	private final void invokeInEDT(final Runnable runnable)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			runnable.run();
		}
		else
		{
			SwingUtilities.invokeLater(runnable);
		}
	}

}
