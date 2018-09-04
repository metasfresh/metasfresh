package de.metas.inbound.mail;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.inbound.mail.config.InboundEMailConfig;
import lombok.ToString;

/*
 * #%L
 * de.metas.inbound.mail
 * %%
 * Copyright (C) 2018 metas GmbH
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
final class CompositeInboundEMailListener implements InboundEMailListener
{
	public static CompositeInboundEMailListener of(final List<InboundEMailListener> listeners)
	{
		return new CompositeInboundEMailListener(listeners);
	}

	private final ImmutableList<InboundEMailListener> listeners;

	private CompositeInboundEMailListener(final List<InboundEMailListener> listeners)
	{
		this.listeners = ImmutableList.copyOf(listeners);
	}

	@Override
	public InboundEMail onInboundEMailReceived(final InboundEMailConfig config, final InboundEMail email)
	{
		InboundEMail currentEmail = email;

		for (final InboundEMailListener listener : listeners)
		{
			currentEmail = listener.onInboundEMailReceived(config, currentEmail);
		}

		return currentEmail;
	}
}
