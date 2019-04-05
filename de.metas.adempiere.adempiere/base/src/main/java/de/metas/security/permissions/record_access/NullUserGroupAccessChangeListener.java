package de.metas.security.permissions.record_access;

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

final class NullUserGroupAccessChangeListener implements UserGroupAccessChangeListener
{
	public static final transient NullUserGroupAccessChangeListener instance = new NullUserGroupAccessChangeListener();

	private NullUserGroupAccessChangeListener()
	{
	}

	@Override
	public void setUserGroupRecordAccessService(final UserGroupRecordAccessService service)
	{
		// nothing
	}

	@Override
	public void onAccessGranted(final UserGroupRecordAccess request)
	{
		// nothing
	}

	@Override
	public void onAccessRevoked(final UserGroupRecordAccess request)
	{
		// nothing
	}

}
