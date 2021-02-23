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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import de.metas.common.util.time.SystemTime;
import org.adempiere.service.ISysConfigBL;

import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.util.Services;

/**
 * Track {@link IHUKey} changes.
 *
 * Mainly it is not checking if the {@link IHUKey}s were changed but it is only measuring the time from when the {@link IHUKey}s were displayed to user and the time when user wanted to move on (i.e.
 * cancel them).
 *
 * After how many seconds we consider as changed it's configured by sysconfig {@link #SYSCONFIG_ConsiderChangedAfterSec}.
 *
 * @author tsa
 *
 */
/* package */class HUKeyChangesTracker
{
	// Services
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_ConsiderChangedAfterSec = "de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel.ConsiderChangedAfterSec";
	private static final int DEFAULT_ConsiderChangedAfterSec = 60; // 1min

	private long setTimestampMillis = 0;

	public HUKeyChangesTracker()
	{
		super();
	}

	private final long getConsiderChangedAfterMillis()
	{
		final int considerChangedAfterSec = sysConfigBL.getIntValue(SYSCONFIG_ConsiderChangedAfterSec, DEFAULT_ConsiderChangedAfterSec);
		final long considerChangedAfterMillis = considerChangedAfterSec * 1000;
		return considerChangedAfterMillis;
	}

	/**
	 * Sets our reference {@link IHUKey} root. When changes are computed this root key will be used as reference
	 *
	 * @param key
	 */
	public void set(final IHUKey key)
	{
		setTimestampMillis = de.metas.common.util.time.SystemTime.millis();
	}

	/**
	 * Checks if there were any changes between initial/reference {@link IHUKey} (see {@link #set(IHUKey)}) and given {@link IHUKey}.
	 *
	 * @param key
	 * @return true if there are any changes.
	 */
	public boolean hasChanges(final IHUKey key)
	{
		final long currentMillis = SystemTime.millis();
		final long considerChangedAfterMillis = getConsiderChangedAfterMillis();
		if (currentMillis >= setTimestampMillis + considerChangedAfterMillis)
		{
			return true;
		}

		return false;
	}
}
