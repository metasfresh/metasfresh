/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.base;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.marketing.base.model.ContactPerson;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class ContactPersonsEventBus
{
	private static final Logger logger = LogManager.getLogger(ContactPersonsEventBus.class);
	private final ImmutableList<ContactPersonListener> listeners;

	public ContactPersonsEventBus(@NonNull final Optional<List<ContactPersonListener>> listeners)
	{
		this.listeners = listeners.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		logger.info("Listeners: {}", this.listeners);
	}

	public void notifyChanged(@NonNull final Collection<ContactPerson> contacts)
	{
		if (contacts.isEmpty())
		{
			return;
		}

		listeners.forEach(listener -> listener.onContactsChanged(contacts));
	}
}
