package de.metas.ui.web.notification;

import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.DisplayType;

import com.google.common.base.MoreObjects;

import de.metas.event.Event;

/*
 * #%L
 * metasfresh-webui-api
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

public class UserNotification
{
	/* package */static final UserNotification of(final Event event)
	{
		return new UserNotification(event);
	}
	
	public static final String EVENT_PARAM_Important = "important";

	private final String id;
	private final long timestamp;
	private final boolean important;

	private final String detailPlain;
	private final String detailADMessage;

	private final AtomicBoolean read;

	private UserNotification(final Event event)
	{
		super();

		id = event.getId();
		timestamp = System.currentTimeMillis(); // TODO: introduce Event.getTimestamp()
		important = DisplayType.toBoolean(event.getProperty(EVENT_PARAM_Important), false);

		detailPlain = event.getDetailPlain();
		detailADMessage = event.getDetailADMessage();

		read = new AtomicBoolean(false);
	}

	private UserNotification(final UserNotification from)
	{
		super();

		id = from.id;
		timestamp = from.timestamp;
		important = from.important;

		detailPlain = from.detailPlain;
		detailADMessage = from.detailADMessage;

		read = new AtomicBoolean(from.read.get());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("detailPlain", detailPlain)
				.add("detailADMessage", detailADMessage)
				.add("timestamp", timestamp)
				.add("important", important)
				.add("read", read)
				.toString();
	}

	public UserNotification copy()
	{
		return new UserNotification(this);
	}

	public String getId()
	{
		return id;
	}

	public String getMessage(final String adLanguage)
	{
		// TODO: implement; see org.adempiere.ui.notifications.SwingEventNotifierFrame.toNotificationItem(Event)

		//
		// Build detail message
		final StringBuilder detailBuf = new StringBuilder();

		// Add plain detail if any
		if (!Check.isEmpty(detailPlain, true))
		{
			detailBuf.append(detailPlain.trim());
		}

		// Translate, parse and add detail (AD_Message).
		if (!Check.isEmpty(detailADMessage, true))
		{
			final String detailTrl = Services.get(IMsgBL.class)
					.getTranslatableMsgText(detailADMessage)
					.translate(adLanguage);
			if (!Check.isEmpty(detailTrl, true))
			{
				if (detailBuf.length() > 0)
				{
					detailBuf.append("\n");
				}
				detailBuf.append(detailTrl);
			}
		}

		return detailBuf.toString();
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public boolean isImportant()
	{
		return important;
	}

	public boolean isRead()
	{
		return read.get();
	}

	boolean setRead(final boolean read)
	{
		return this.read.getAndSet(read);
	}
}
