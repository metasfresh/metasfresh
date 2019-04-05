package de.metas.security.permissions.record_access.listeners;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.security.permissions.record_access.UserGroupRecordAccess;
import de.metas.security.permissions.record_access.UserGroupRecordAccessService;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
public final class CompositeUserGroupAccessChangeListener implements UserGroupAccessChangeListener
{
	public static final UserGroupAccessChangeListener of(final List<UserGroupAccessChangeListener> listeners)
	{
		return new CompositeUserGroupAccessChangeListener(listeners);
	}

	private final ImmutableList<UserGroupAccessChangeListener> listeners;

	private CompositeUserGroupAccessChangeListener(@NonNull final List<UserGroupAccessChangeListener> listeners)
	{
		this.listeners = ImmutableList.copyOf(listeners);
	}

	@Override
	public void setUserGroupRecordAccessService(final UserGroupRecordAccessService service)
	{
		for (final UserGroupAccessChangeListener listener : listeners)
		{
			listener.setUserGroupRecordAccessService(service);
		}
	}

	@Override
	public void onAccessGranted(final UserGroupRecordAccess request)
	{
		for (final UserGroupAccessChangeListener listener : listeners)
		{
			listener.onAccessGranted(request);
		}
	}

	@Override
	public void onAccessRevoked(final UserGroupRecordAccess request)
	{
		for (final UserGroupAccessChangeListener listener : listeners)
		{
			listener.onAccessRevoked(request);
		}
	}

}
