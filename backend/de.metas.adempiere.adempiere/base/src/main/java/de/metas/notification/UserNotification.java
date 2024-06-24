package de.metas.notification;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Getter
@ToString(exclude = { "adLanguage2message" })
@EqualsAndHashCode
public class UserNotification
{
	@JsonProperty("id")
	private final int id;
	@JsonProperty("timestamp")
	private final Instant timestamp;
	@JsonProperty("important")
	private final boolean important;
	@JsonProperty("recipientUserId")
	private final int recipientUserId;
	@JsonProperty("detailPlain")
	private final String detailPlain;
	@JsonProperty("detailADMessage")
	private final String detailADMessage;
	@JsonProperty("detailADMessageParams")
	private final List<Object> detailADMessageParams;

	//
	// Target action
	@JsonProperty("targetType")
	private final UserNotificationTargetType targetType;
	@JsonProperty("targetRecord")
	private final TableRecordReference targetRecord;
	@JsonProperty("targetWindowId")
	private final AdWindowId targetWindowId;
	@JsonProperty("targetViewId")
	private final String targetViewId;

	@JsonProperty("read")
	@Getter(AccessLevel.NONE)
	private boolean read;

	@JsonIgnore
	@Getter(AccessLevel.NONE)
	private final transient Map<String, String> adLanguage2message = new HashMap<>();

	@Builder
	@JsonCreator
	private UserNotification(
			@JsonProperty("id") final int id,
			@JsonProperty("timestamp") @NonNull final Instant timestamp,
			@JsonProperty("important") final boolean important,
			@JsonProperty("read") final boolean read,
			@JsonProperty("recipientUserId") @NonNull final Integer recipientUserId,
			//
			@JsonProperty("detailPlain") final String detailPlain,
			@JsonProperty("detailADMessage") final String detailADMessage,
			@JsonProperty("detailADMessageParams") @Singular final List<Object> detailADMessageParams,
			//
			// Target action
			@JsonProperty("targetType") @NonNull final UserNotificationTargetType targetType,
			@JsonProperty("targetRecord") final TableRecordReference targetRecord,
			@JsonProperty("targetWindowId") final AdWindowId targetWindowId,
			@JsonProperty("targetViewId") final String targetViewId)
	{
		Check.assumeGreaterThanZero(id, "id");

		this.id = id;
		this.timestamp = timestamp;
		this.important = important;
		this.read = read;
		this.recipientUserId = recipientUserId;

		this.detailPlain = detailPlain;
		this.detailADMessage = detailADMessage;
		this.detailADMessageParams = detailADMessageParams != null ? Collections.unmodifiableList(new ArrayList<>(detailADMessageParams)) : ImmutableList.of();

		this.targetType = targetType;
		if (targetType == UserNotificationTargetType.Window)
		{
			Check.assumeNotNull(targetRecord, "Parameter targetRecord is not null");
			this.targetRecord = targetRecord;
			this.targetWindowId = targetWindowId;
			this.targetViewId = null;
		}
		else if (targetType == UserNotificationTargetType.View)
		{
			Check.assumeNotEmpty(targetViewId, "targetViewId is not empty");
			Check.assumeNotNull(targetWindowId, "targetWindowId");
			this.targetRecord = null;
			this.targetWindowId = targetWindowId;
			this.targetViewId = targetViewId;
		}
		else
		{
			this.targetRecord = null;
			this.targetWindowId = null;
			this.targetViewId = targetViewId;
		}
	}

	public String getIdAsString()
	{
		return String.valueOf(id);
	}

	public String getMessage(final String adLanguage)
	{
		return adLanguage2message.computeIfAbsent(adLanguage, this::buildMessage);
	}

	private String buildMessage(final String adLanguage)
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

	public synchronized boolean isRead()
	{
		return read;
	}

	public boolean isNotRead()
	{
		return !isRead();
	}

	public synchronized boolean setRead(final boolean read)
	{
		final boolean readOld = this.read;
		this.read = read;
		return readOld;
	}

	@Nullable
	public String getTargetWindowIdAsString()
	{
		return targetWindowId != null ? String.valueOf(targetWindowId.getRepoId()) : null;
	}

	public String getTargetDocumentId()
	{
		return targetRecord != null ? String.valueOf(targetRecord.getRecord_ID()) : null;
	}
}
