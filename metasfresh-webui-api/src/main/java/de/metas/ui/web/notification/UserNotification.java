package de.metas.ui.web.notification;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.DisplayType;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

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
	private final Map<String, Object> detailADMessageParams;
	private final Map<String, String> adLanguage2message = new HashMap<>();

	//
	// Mutable: read flag
	private final AtomicBoolean read;

	//
	// Target
	public static enum TargetType
	{
		None("none"), Window("window");
		
		final private String jsonValue;

		TargetType(final String jsonValue)
		{
			this.jsonValue = jsonValue;
		}
		
		@JsonValue
		public String getJsonValue()
		{
			return jsonValue;
		}
		
		public static TargetType forJsonValue(final String jsonValue)
		{
			return Stream.of(values())
					.filter(value -> Objects.equal(jsonValue, value.getJsonValue()))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Invalid jsonValue: " + jsonValue));
		}
	};

	private final TargetType targetType;
	//
	// Target: Window/Document
	private final int target_adWindowId;
	private final String target_tableName;
	private final int target_recordId;

	private UserNotification(final Event event)
	{
		super();

		id = event.getId();
		timestamp = System.currentTimeMillis(); // TODO: introduce Event.getTimestamp()
		important = DisplayType.toBoolean(event.getProperty(EVENT_PARAM_Important), false);

		detailPlain = event.getDetailPlain();
		detailADMessage = event.getDetailADMessage();
		detailADMessageParams = ImmutableMap.copyOf(event.getProperties());

		read = new AtomicBoolean(false);

		//
		// Target: window (from document record)
		final ITableRecordReference targetRecord = event.getRecord();
		if (targetRecord != null)
		{
			targetType = TargetType.Window;
			final RecordZoomWindowFinder recordWindowFinder = RecordZoomWindowFinder.newInstance(targetRecord);
			target_adWindowId = recordWindowFinder.findAD_Window_ID();
			target_tableName = recordWindowFinder.getTableName();
			target_recordId = recordWindowFinder.getRecord_ID();
		}
		//
		// Target: none
		else
		{
			targetType = TargetType.None;
			target_adWindowId = -1;
			target_tableName = null;
			target_recordId = -1;
		}
	}

	private UserNotification(final UserNotification from)
	{
		super();

		id = from.id;
		timestamp = from.timestamp;
		important = from.important;

		detailPlain = from.detailPlain;
		detailADMessage = from.detailADMessage;
		detailADMessageParams = from.detailADMessageParams;
		adLanguage2message.putAll(from.adLanguage2message);

		read = new AtomicBoolean(from.read.get());

		targetType = from.targetType;
		target_adWindowId = from.target_adWindowId;
		target_tableName = from.target_tableName;
		target_recordId = from.target_recordId;
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
				//
				.add("targetType", targetType)
				.add("target_AD_Window_ID", target_adWindowId)
				.add("target_TableName", target_tableName)
				.add("target_RecordId", target_recordId)
				//
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
		return adLanguage2message.computeIfAbsent(adLanguage, this::buildMessage);
	}

	private final String buildMessage(final String adLanguage)
	{
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

			final String detailTrlParsed = UserNotificationDetailMessageFormat.newInstance()
					.setLeftBrace("{").setRightBrace("}")
					.setThrowExceptionIfKeyWasNotFound(false)
					.setArguments(detailADMessageParams)
					.format(detailTrl);

			if (!Check.isEmpty(detailTrlParsed, true))
			{
				if (detailBuf.length() > 0)
				{
					detailBuf.append("\n");
				}
				detailBuf.append(detailTrlParsed);
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

	public TargetType getTargetType()
	{
		return targetType;
	}

	public String getTargetDocumentType()
	{
		if (target_adWindowId <= 0)
		{
			return null;
		}
		return String.valueOf(target_adWindowId);
	}

	public String getTargetDocumentId()
	{
		if (target_recordId < 0)
		{
			return null;
		}
		return String.valueOf(target_recordId);
	}
}
