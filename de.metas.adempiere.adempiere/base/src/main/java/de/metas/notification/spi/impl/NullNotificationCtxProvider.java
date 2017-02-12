package de.metas.notification.spi.impl;

import org.adempiere.util.lang.ITableRecordReference;

import com.google.common.base.Optional;

import de.metas.notification.spi.INotificationCtxProvider;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link INotificationCtxProvider} implementation which always return {@link Optional#absent()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class NullNotificationCtxProvider implements INotificationCtxProvider
{
	public static final transient NullNotificationCtxProvider instance = new NullNotificationCtxProvider();

	private NullNotificationCtxProvider()
	{
		super();
	}

	/**
	 * @return always {@link Optional#absent()}
	 */
	@Override
	public Optional<String> getTextMessageIfApplies(final ITableRecordReference referencedRecord)
	{
		return Optional.absent();
	}
}
