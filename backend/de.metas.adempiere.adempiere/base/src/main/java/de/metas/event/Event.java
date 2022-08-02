package de.metas.event;

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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.common.util.CoalesceUtil;
import de.metas.event.log.EventLogEntryCollector;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Event that can be sent/received on {@link IEventBus}.
 *
 * @author tsa
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class Event
{
	private static final String PROP_Body = "body";

	public static Builder builder()
	{
		return new Builder();
	}

	private static final String PROPERTY_Record = "record";
	public static final String PROPERTY_SuggestedWindowId = "suggestedWindowId";

	// put this first, because this is imho the most interesting part of the event's json representation, at least when shown in the event log
	@JsonProperty("properties")
	@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	ImmutableMap<String, Object> properties;

	@JsonProperty("uuid")
	UUID uuid;

	@JsonProperty("when")
	Instant when;

	@JsonProperty("summary")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String summary;

	@JsonProperty("detailPlain")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String detailPlain;

	@JsonProperty("detailADMessage")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String detailADMessage;

	@JsonProperty("senderId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String senderId;

	@JsonProperty("recipientUserIds")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	ImmutableSet<Integer> recipientUserIds;

	private enum LoggingStatus
	{
		SHALL_NOT_BE_LOGGED,

		/**
		 * With this status, the system shall store the event before it is posted to the event bus. See {@link Event#withStatusWasLogged()}.
		 */
		SHALL_BE_LOGGED,

		/**
		 * Indicate that the event itself was logged; with this status, the event handlers will invoke {@link EventLogEntryCollector#createThreadLocalForEvent(Event)} so that event handling business logic can log event-related info.
		 */
		WAS_LOGGED
	}

	@JsonProperty("loggingStatus")
	@Getter(value = AccessLevel.NONE)
	LoggingStatus loggingStatus;

	private Event(final Builder builder)
	{
		uuid = CoalesceUtil.coalesceSuppliers(() -> builder.uuid, UUID::randomUUID);
		when = CoalesceUtil.coalesceSuppliers(() -> builder.when, Instant::now);

		summary = builder.summary;
		detailPlain = builder.getDetailPlain();
		detailADMessage = builder.getDetailADMessage();
		senderId = builder.senderId;
		recipientUserIds = ImmutableSet.copyOf(builder.recipientUserIds);
		properties = deepCopy(builder.getProperties());
		loggingStatus = builder.loggingStatus;
	}

	@JsonCreator
	public Event(
			@JsonProperty("uuid") @NonNull final UUID uuid,
			@JsonProperty("when") @NonNull final Instant when,
			@JsonProperty("summary") final String summary,
			@JsonProperty("detailPlain") final String detailPlain,
			@JsonProperty("detailADMessage") final String detailADMessage,
			@JsonProperty("senderId") final String senderId,
			@JsonProperty("recipientUserIds") final Set<Integer> recipientUserIds,
			@JsonProperty("properties") final Map<String, Object> properties,
			@JsonProperty("loggingStatus") final LoggingStatus loggingStatus)
	{
		this.uuid = uuid;
		this.when = when;

		this.summary = summary;
		this.detailPlain = detailPlain;
		this.detailADMessage = detailADMessage;
		this.senderId = senderId;
		this.recipientUserIds = recipientUserIds != null ? ImmutableSet.copyOf(recipientUserIds) : ImmutableSet.of();
		this.properties = deepCopy(properties);
		this.loggingStatus = loggingStatus;
	}

	private static ImmutableMap<String, Object> deepCopy(final Map<String, Object> properties)
	{
		if (properties == null || properties.isEmpty())
		{
			return ImmutableMap.of();
		}

		return properties.entrySet()
				.stream()
				.filter(e -> e.getValue() != null) // skip nulls
				.collect(GuavaCollectors.toImmutableMap());
	}

	public boolean isLocalEvent()
	{
		return Objects.equals(EventBusConfig.getSenderId(), senderId);
	}

	public boolean hasRecipient(final int userId)
	{
		if (isAllRecipients())
		{
			return true;
		}
		return recipientUserIds.contains(userId);
	}

	/**
	 * @return true if this event is for all users.<br>
	 * If no recipients were specified, consider that this event is for anybody
	 */
	public boolean isAllRecipients()
	{
		return recipientUserIds.isEmpty();
	}

	public int getSingleRecipientUserId()
	{
		if (recipientUserIds.size() != 1)
		{
			throw new AdempiereException("Event does not have a single recipient: " + this);
		}
		return recipientUserIds.asList().get(0);
	}

	public int getSuggestedWindowId()
	{
		return getPropertyAsInt(PROPERTY_SuggestedWindowId, 0);
	}

	/**
	 * @return the property with the given name or {@code null}
	 */
	public <T> T getProperty(final String name)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)properties.get(name);
		return value;
	}

	@Nullable
	public String getPropertyAsString(final String name)
	{
		final Object value = getProperty(name);
		return value != null ? value.toString() : null;
	}

	public boolean getPropertyAsBoolean(final String name)
	{
		final Object value = getProperty(name);
		return DisplayType.toBoolean(value);
	}

	public int getPropertyAsInt(final String name, final int defaultValue)
	{
		final Object value = getProperty(name);
		return NumberUtils.asInt(value, defaultValue);
	}

	@Nullable
	public Date getPropertyAsDate(final String name)
	{
		final Supplier<Date> defaultValue = null;
		return getPropertyAsDate(name, defaultValue);
	}

	@Nullable
	public Date getPropertyAsDate(final String name, final Supplier<Date> defaultValue)
	{
		final Object value = getProperty(name);
		if (value == null)
		{
			return defaultValue != null ? defaultValue.get() : null;
		}
		else if (value instanceof Date)
		{
			return (Date)value;
		}
		else
		{
			throw new AdempiereException("Cannot convert " + value + " to " + Date.class);
		}
	}

	/**
	 * @return record or null
	 * @see #getProperty(String)
	 * @see Builder#setRecord(ITableRecordReference)
	 */
	public ITableRecordReference getRecord()
	{
		return getProperty(PROPERTY_Record);
	}

	public Event withStatusWasLogged()
	{
		final Builder builder = toBuilder();
		builder.loggingStatus = LoggingStatus.WAS_LOGGED;
		return builder.build();
	}

	public boolean isShallBeLogged()
	{
		return LoggingStatus.SHALL_BE_LOGGED.equals(loggingStatus);
	}

	public boolean isWasLogged()
	{
		return LoggingStatus.WAS_LOGGED.equals(loggingStatus);
	}

	public String getBody()
	{
		return getPropertyAsString(PROP_Body);
	}

	public Builder toBuilder()
	{
		final Builder builder = new Builder();
		builder.detailADMessage = detailADMessage;
		builder.detailPlain = detailPlain;
		builder.properties.putAll(properties);
		builder.recipientUserIds.addAll(recipientUserIds);
		builder.senderId = senderId;
		builder.summary = summary;
		builder.uuid = uuid;
		builder.when = when;
		builder.loggingStatus = loggingStatus;

		return builder;
	}

	// --------------------------------------------------------------------------------------------------------------------------------
	//
	//
	public static final class Builder
	{
		private UUID uuid;
		private Instant when;
		private String summary;
		private String detailPlain;
		private String detailADMessage;
		private String senderId = EventBusConfig.getSenderId();
		private final Set<Integer> recipientUserIds = new HashSet<>();
		private final Map<String, Object> properties = Maps.newLinkedHashMap();
		private LoggingStatus loggingStatus = LoggingStatus.SHALL_NOT_BE_LOGGED;

		private Builder()
		{
		}

		public Event build()
		{
			return new Event(this);
		}

		public Builder setUUID(@NonNull final UUID uuid)
		{
			this.uuid = uuid;
			return this;
		}

		public Builder setWhen(@NonNull final Instant when)
		{
			this.when = when;
			return this;
		}

		public Builder setSummary(final String summary)
		{
			this.summary = summary;
			return this;
		}

		public Builder setDetailPlain(final String detailPlain)
		{
			this.detailPlain = detailPlain;
			return this;
		}

		public Builder withBody(final String body)
		{
			properties.put(PROP_Body, body);
			return this;
		}

		private String getDetailPlain()
		{
			return detailPlain;
		}

		/**
		 * Sets the detail AD_Message.
		 * <p>
		 * NOTE: the message will not be translated, but it will be stored as it is and is expected to be translated when it's used (using the language of the recipient). If any parameters were
		 * provided, they will be added to event properties, using following convention:
		 * <ul>
		 * <li>first parameter will be put with name "0"
		 * <li>second parameter will be put with name "1"
		 * <li>etc
		 * </ul>
		 * Because the message parameters are stored as Event properties, please make sure the parameter type is supported (see <code>putProperty</code> methods).
		 *
		 * @param params AD_Message parameters
		 */
		public Builder setDetailADMessage(final String adMessage, final Object... params)
		{
			if (params != null && params.length > 0)
			{
				for (int i = 0; i < params.length; i++)
				{
					final String parameterName = String.valueOf(i);
					final Object parameterValue = params[i];
					putPropertyFromObject(parameterName, parameterValue);
				}
			}

			detailADMessage = adMessage;
			return this;
		}

		public Builder setDetailADMessage(final String adMessage, final List<Object> params)
		{
			if (params != null && !params.isEmpty())
			{
				final int paramsCount = params.size();
				for (int i = 0; i < paramsCount; i++)
				{
					final String parameterName = String.valueOf(i);
					final Object parameterValue = params.get(i);
					putPropertyFromObject(parameterName, parameterValue);
				}
			}

			detailADMessage = adMessage;
			return this;
		}

		public Builder setDetailADMessage(final String adMessage, final Map<String, Object> params)
		{
			if (params != null && !params.isEmpty())
			{
				params.forEach(this::putPropertyFromObject);
			}

			detailADMessage = adMessage;
			return this;
		}

		private String getDetailADMessage()
		{
			return detailADMessage;
		}

		public Builder setSenderId(final String senderId)
		{
			Check.assumeNotEmpty(senderId, "senderId not empty");
			this.senderId = senderId;
			return this;
		}

		public Builder addRecipient_User_ID(final int recipientUserId)
		{
			if (recipientUserId < 0)
			{
				return this;
			}

			recipientUserIds.add(recipientUserId);
			return this;
		}

		public Builder addRecipient_User_IDs(final Iterable<Integer> recipientUserIds)
		{
			if (recipientUserIds == null)
			{
				return this;
			}

			for (final Integer userId : recipientUserIds)
			{
				if (userId == null)
				{
					continue;
				}
				addRecipient_User_ID(userId);
			}

			return this;
		}

		private Map<String, Object> getProperties()
		{
			return properties;
		}

		public Builder putProperty(final String name, final int value)
		{
			properties.put(name, value);
			return this;
		}

		public Builder putProperty(final String name, final long value)
		{
			properties.put(name, value);
			return this;
		}

		public Builder putProperty(final String name, final boolean value)
		{
			properties.put(name, value);
			return this;
		}

		public Builder putPropertyIfTrue(final String name, final boolean value)
		{
			if (value)
			{
				properties.put(name, value);
			}
			return this;
		}

		public Builder putProperty(final String name, final String value)
		{
			properties.put(name, value);
			return this;
		}

		public Builder putProperty(final String name, final Date value)
		{
			properties.put(name, value);
			return this;
		}

		public Builder putProperty(final String name, final BigDecimal value)
		{
			properties.put(name, value);
			return this;
		}

		public Builder putProperty(final String name, final ITableRecordReference value)
		{
			properties.put(name, value);
			return this;
		}

		public Builder putProperty(final String name, final List<?> value)
		{
			properties.put(name, value);
			return this;
		}

		/**
		 * @see #putProperty(String, ITableRecordReference)
		 * @see Event#PROPERTY_Record
		 */
		public Builder setRecord(final ITableRecordReference record)
		{
			putProperty(Event.PROPERTY_Record, record);
			return this;
		}

		public Builder putPropertyFromObject(final String name, final Object value)
		{
			if (value == null)
			{
				properties.remove(name);
				return this;
			}
			else if (value instanceof Integer)
			{
				return putProperty(name, (Integer)value);
			}
			else if (value instanceof Long)
			{
				return putProperty(name, (Long)value);
			}
			else if (value instanceof Double)
			{
				final Double doubleValue = (Double)value;
				final int intValue = doubleValue.intValue();
				if (doubleValue.doubleValue() == intValue)
				{
					return putProperty(name, intValue);
				}
				else
				{
					return putProperty(name, BigDecimal.valueOf(doubleValue));
				}
			}
			else if (value instanceof String)
			{
				return putProperty(name, (String)value);
			}
			else if (value instanceof Date)
			{
				return putProperty(name, (Date)value);
			}
			else if (value instanceof Boolean)
			{
				return putProperty(name, (Boolean)value);
			}
			else if (value instanceof ITableRecordReference)
			{
				return putProperty(name, (ITableRecordReference)value);
			}
			else if (value instanceof BigDecimal)
			{
				return putProperty(name, (BigDecimal)value);
			}
			else if (value instanceof List)
			{
				return putProperty(name, (List<?>)value);
			}
			else
			{
				throw new AdempiereException("Unknown value type " + name + " = " + value + " (type " + value.getClass() + ")");
			}
		}

		public Builder setSuggestedWindowId(final int suggestedWindowId)
		{
			putProperty(PROPERTY_SuggestedWindowId, suggestedWindowId);
			return this;
		}

		public Builder wasLogged()
		{
			this.loggingStatus = LoggingStatus.WAS_LOGGED;
			return this;
		}

		public Builder shallBeLogged()
		{
			this.loggingStatus = LoggingStatus.SHALL_BE_LOGGED;
			return this;
		}
	}
}
