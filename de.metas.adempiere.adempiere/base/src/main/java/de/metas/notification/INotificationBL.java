package de.metas.notification;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_User;

import de.metas.notification.spi.INotificationCtxProvider;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public interface INotificationBL extends ISingletonService
{
	void notifyUser(I_AD_User recipient,
			String adMessage,
			String messageText,
			ITableRecordReference referencedrecord);

	/**
	 * This method will be used when a new <{@code INotificationCtxProvider} implementation is registered.
	 * 
	 * @param ctxProvider
	 */
	void addCtxProvider(INotificationCtxProvider ctxProvider);

	/**
	 * Sets the default {@link INotificationCtxProvider} to be used if none of the registered ones match.
	 * 
	 * @param defaultCtxProvider
	 */
	void setDefaultCtxProvider(INotificationCtxProvider defaultCtxProvider);
}
