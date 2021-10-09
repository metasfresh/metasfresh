/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.webui.service;

import com.google.common.collect.ImmutableList;
import de.metas.procurement.webui.model.User;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserConfirmationService
{

	private static final Logger logger = LoggerFactory.getLogger(UserConfirmationService.class);
	private final ImmutableList<UserConfirmationHandler> handlers;

	public UserConfirmationService(@NonNull final List<UserConfirmationHandler> handlers)
	{
		this.handlers = ImmutableList.copyOf(handlers);
		logger.info("Handlers: {}", this.handlers);
	}

	public long getCountUnconfirmed(@NonNull final User user)
	{
		return handlers.stream()
				.mapToLong(provider -> provider.getCountUnconfirmed(user))
				.map(count -> count >= 0 ? count : 0)
				.sum();
	}

	@Transactional
	public void confirmUserEntries(@NonNull final User user)
	{
		handlers.forEach(provider -> provider.confirmUserEntries(user));
	}
}
