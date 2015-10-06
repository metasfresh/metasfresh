package org.adempiere.event.jms;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.adempiere.event.Event;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonEventSerializer implements IEventSerializer
{
	public static final transient JsonEventSerializer instance = new JsonEventSerializer();
	
	private static final String SENDER_ID_Unknown = "UnknownSender";

	private final JsonParser parser = new JsonParser();
	private final Gson gson;

	private JsonEventSerializer()
	{
		super();

		gson = new GsonBuilder()
				.registerTypeAdapter(Event.class, EventTypeAdapter.instance)
				.registerTypeAdapter(TypedKeyValue.class, TypedKeyValueTypeAdapter.instance)
				.registerTypeAdapter(TableRecordReference.class, TableRecordReferenceTypeAdapter.instance)
				.registerTypeAdapter(Date.class, DateTypeAdapter.instance)
				.registerTypeAdapter(Timestamp.class, TimestampTypeAdapter.instance)
				.create();

	}

	/**
	 * Serialize given value to JSON string.
	 *
	 * @param value
	 * @return JSON string
	 */
	@Override
	public String toString(final Event value)
	{
		if (value == null)
		{
			return null;
		}
		return gson.toJson(value);
	}

	/**
	 * Deserialize given JSON string
	 *
	 * @param eventStr JSON string
	 * @return object
	 */
	@Override
	public Event fromString(final String eventStr)
	{
		if (eventStr == null || eventStr.isEmpty())
		{
			return null;
		}

		final JsonElement jsonElement = parser.parse(eventStr);
		final Event event = gson.fromJson(jsonElement, Event.class);
		return event;
	}

	public static final class TypedKeyValue
	{
		public static final ImmutableSet<TypedKeyValue> asTypedKeyValuesSet(final Map<String, Object> map)
		{
			if (map == null || map.isEmpty())
			{
				return ImmutableSet.of();
			}
			final ImmutableSet.Builder<TypedKeyValue> list = ImmutableSet.builder();
			for (final Map.Entry<String, Object> e : map.entrySet())
			{
				final Object value = e.getValue();

				// skip null values
				if (value == null)
				{
					continue;
				}

				final TypedKeyValue keyValue = new TypedKeyValue(e.getKey(), value);
				list.add(keyValue);
			}
			return list.build();
		}

		public static final transient ImmutableBiMap<Class<?>, String> class2type = ImmutableBiMap.<Class<?>, String> builder()
				.put(Integer.class, "i")
				.put(Long.class, "l")
				.put(String.class, "s")
				.put(java.util.Date.class, "d")
				.put(Timestamp.class, "ts")
				.put(BigDecimal.class, "n")
				.put(Boolean.class, "b")
				.put(TableRecordReference.class, "rec")
				.build();
		public static final transient ImmutableBiMap<String, Class<?>> type2class = class2type.inverse();

		private final String name;
		private final String type;
		private final Object value;

		/** Deserialization constructor */
		public <T> TypedKeyValue(final String name, final String type, final T value)
		{
			super();
			this.name = name;
			this.type = type;
			this.value = value;
		}

		/** Serialization constructor */
		public TypedKeyValue(final String name, final Object value)
		{
			super();
			this.name = name;

			final Class<?> typeClass = value.getClass();
			final String type = class2type.get(typeClass);
			if (type == null)
			{
				throw new IllegalArgumentException("Unknown type for " + typeClass);
			}
			this.type = type;

			Check.assumeNotNull(value, "value not null");
			this.value = value;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public String getName()
		{
			return name;
		}

		public Object getValue()
		{
			return value;
		}

		public String getType()
		{
			return type;
		}

		public Class<?> getTypeClass()
		{
			return type2class.get(type);
		}
	}

	private static class TypedKeyValueTypeAdapter implements JsonDeserializer<TypedKeyValue>, JsonSerializer<TypedKeyValue>
	{
		public static final transient TypedKeyValueTypeAdapter instance = new TypedKeyValueTypeAdapter();

		@Override
		public TypedKeyValue deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject jo = json.getAsJsonObject();

			final String name = jo.get("n").getAsString();

			final String valueType = jo.get("t").getAsString();
			final Class<?> valueClass = TypedKeyValue.type2class.get(valueType);

			final JsonElement valueElement = jo.get("v");
			final Object value = context.deserialize(valueElement, valueClass);
			return new TypedKeyValue(name, valueType, value);
		}

		@Override
		public JsonElement serialize(final TypedKeyValue src, final Type typeOfSrc, final JsonSerializationContext context)
		{
			final JsonObject jo = new JsonObject();
			jo.addProperty("n", src.getName());
			jo.addProperty("t", src.getType());

			final JsonElement valueJsonElement = context.serialize(src.getValue(), src.getTypeClass());
			jo.add("v", valueJsonElement);

			return jo;
		}
	}

	public static final class DateTypeAdapter implements JsonDeserializer<Date>, JsonSerializer<Date>
	{
		public static final transient DateTypeAdapter instance = new DateTypeAdapter();

		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context)
		{
			return new JsonPrimitive(src.getTime());
		}

		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			final long timeMillis = json.getAsLong();
			return new Date(timeMillis);
		}
	}

	public static final class TimestampTypeAdapter implements JsonDeserializer<Timestamp>, JsonSerializer<Timestamp>
	{
		public static final transient TimestampTypeAdapter instance = new TimestampTypeAdapter();

		@Override
		public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context)
		{
			return new JsonPrimitive(src.getTime());
		}

		@Override
		public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			final long timeMillis = json.getAsLong();
			return new Timestamp(timeMillis);
		}
	}

	private static class TableRecordReferenceTypeAdapter implements JsonDeserializer<TableRecordReference>, JsonSerializer<TableRecordReference>
	{
		public static final transient TableRecordReferenceTypeAdapter instance = new TableRecordReferenceTypeAdapter();

		@Override
		public JsonElement serialize(final TableRecordReference src, final Type typeOfSrc, final JsonSerializationContext context)
		{
			final JsonObject jo = new JsonObject();
			jo.addProperty("t", src.getTableName());
			jo.addProperty("id", src.getRecord_ID());
			return jo;
		}

		@Override
		public TableRecordReference deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject jo = json.getAsJsonObject();

			final String tableName = jo.get("t").getAsString();
			final int recordId = jo.get("id").getAsInt();
			return new TableRecordReference(tableName, recordId);
		}

	}

	private static class ImmutableSetTypeAdapter<T> implements JsonDeserializer<ImmutableSet<T>>, JsonSerializer<ImmutableSet<T>>
	{
		@Override
		public ImmutableSet<T> deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException
		{
			@SuppressWarnings("unchecked")
			final TypeToken<ImmutableSet<T>> immutableSetToken = (TypeToken<ImmutableSet<T>>)TypeToken.of(type);
			final TypeToken<? super ImmutableSet<T>> setToken = immutableSetToken.getSupertype(Set.class);
			final Set<T> set = context.deserialize(json, setToken.getType());
			return ImmutableSet.copyOf(set);
		}

		@Override
		public JsonElement serialize(final ImmutableSet<T> src, final Type typeOfSrc, final JsonSerializationContext context)
		{
			// just delegate
			return context.serialize(src, typeOfSrc);
		}
	}

	private static final class EventTypeAdapter implements JsonSerializer<Event>, JsonDeserializer<Event>
	{
		public static final transient EventTypeAdapter instance = new EventTypeAdapter();

		private final ImmutableSetTypeAdapter<Integer> recipientUserIdsTypeAdapter = new ImmutableSetTypeAdapter<>();
		private final ImmutableSetTypeAdapter<TypedKeyValue> propertiesTypeAdapter = new ImmutableSetTypeAdapter<>();
		private static final Type TYPE_ImmutableSet_of_TypedKeyValue = new TypeToken<ImmutableSet<TypedKeyValue>>()
		{
			private static final long serialVersionUID = 1L;
		}.getType();

		@Override
		public JsonElement serialize(final Event event, final Type typeOfSrc, final JsonSerializationContext context)
		{
			final JsonObject jo = new JsonObject();
			jo.addProperty("id", event.getId());
			jo.addProperty("summary", event.getSummary());
			jo.addProperty("detailPlain", event.getDetailPlain());
			jo.addProperty("detailADMessage", event.getDetailADMessage());
			jo.addProperty("senderId", event.getSenderId());

			// recipientUserIds
			{
				final ImmutableSet<Integer> recipientUserIds = ImmutableSet.copyOf(event.getRecipientUserIds());
				final JsonElement recipientUserIdsElement = recipientUserIdsTypeAdapter.serialize(recipientUserIds, ImmutableSet.class, context);
				jo.add("recipientUserIds", recipientUserIdsElement);
			}

			{
				final ImmutableSet<TypedKeyValue> properties = TypedKeyValue.asTypedKeyValuesSet(event.getProperties());
				final JsonElement propertiesElement = propertiesTypeAdapter.serialize(properties, ImmutableSet.class, context);
				jo.add("properties", propertiesElement);
			}

			return jo;
		}

		@Override
		public Event deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject jo = json.getAsJsonObject();
			final Event.Builder eventBuilder = Event.builder()
					.setSummary(getAsString(jo, "summary"))
					.setDetailPlain(getAsString(jo, "detailPlain"))
					.setDetailADMessage(getAsString(jo, "detailADMessage"))
					//
			;

			//
			// Event ID
			final String eventId = getAsString(jo, "id");
			if (!Check.isEmpty(eventId, false))
			{
				eventBuilder.setId(eventId);
			}
			
			//
			// Sender ID
			String senderId = getAsString(jo, "senderId");
			if (senderId == null)
			{
				senderId = SENDER_ID_Unknown;
			}
			eventBuilder.setSenderId(senderId);

			//
			// Recipients
			eventBuilder.addRecipient_User_IDs(getAsIntSet(jo, "recipientUserIds"));
			{
				final ImmutableSet<Integer> recipientUserIds = recipientUserIdsTypeAdapter.deserialize(jo.get("recipientUserIds"), ImmutableSet.class, context);
				for (final Number recipientUserId : recipientUserIds)
				{
					eventBuilder.addRecipient_User_ID(recipientUserId.intValue());
				}
			}

			//
			// Properties
			{
				final ImmutableSet<TypedKeyValue> properties = propertiesTypeAdapter.deserialize(jo.get("properties"), TYPE_ImmutableSet_of_TypedKeyValue
						, context);
				for (final TypedKeyValue keyValue : properties)
				{
					eventBuilder.putPropertyFromObject(keyValue.getName(), keyValue.getValue());
				}
			}

			return eventBuilder.build();
		}

		private static final String getAsString(final JsonObject jo, final String propertyName)
		{
			if (jo == null)
			{
				return null;
			}
			final JsonElement jsonElement = jo.get(propertyName);
			if (jsonElement == null)
			{
				return null;
			}

			final String value = jsonElement.getAsString();
			return value;
		}

		private static final Set<Integer> getAsIntSet(final JsonObject jo, final String propertyName)
		{
			if (jo == null)
			{
				return ImmutableSet.of();
			}
			final JsonElement jsonElement = jo.get(propertyName);
			if (jsonElement == null)
			{
				return ImmutableSet.of();
			}

			final JsonArray jsonArray = jsonElement.getAsJsonArray();
			if (jsonArray == null || jsonArray.size() <= 0)
			{
				return ImmutableSet.of();
			}

			final Set<Integer> set = new LinkedHashSet<>(jsonArray.size()); // preserve same order
			for (final Iterator<JsonElement> it = jsonArray.iterator(); it.hasNext();)
			{
				final int valueInt = it.next().getAsInt();
				set.add(valueInt);
			}

			return set;
		}

	}
}
