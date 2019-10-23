package org.adempiere.plaf;

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

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.UIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helper class to serialize {@link UIResource}s to/from {@link String}.
 * 
 * @author tsa
 *
 */
class UIDefaultsSerializer
{
	private static final transient Logger logger = LogManager.getLogger(UIDefaultsSerializer.class);

	private final JsonParser parser = new JsonParser();
	private final Gson gson;

	public UIDefaultsSerializer()
	{
		super();

		gson = new GsonBuilder()
				.registerTypeAdapter(Color.class, new ColorJsonSerializer())
				.registerTypeAdapter(ColorUIResource.class, new ColorJsonSerializer())
				.registerTypeAdapter(Font.class, new FontJsonSerializer())
				.registerTypeAdapter(FontUIResource.class, new FontJsonSerializer())
				.registerTypeAdapter(VEditorDialogButtonAlign.class, new VEditorDialogButtonAlignJsonSerializer())
				.create();

	}

	/**
	 * Serialize given value to JSON string.
	 * 
	 * @param value
	 * @return JSON string
	 */
	public String toString(final Object value)
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
	 * @param valueStr JSON string
	 * @return object
	 */
	public Object fromString(String valueStr)
	{
		if (valueStr == null || valueStr.isEmpty())
		{
			return null;
		}

		final JsonElement jsonElement = parser.parse(valueStr);
		final Class<?> type = getType(jsonElement);
		final Object value = gson.fromJson(jsonElement, type);
		return value;
	}

	private Class<?> getType(JsonElement json)
	{
		if(json.isJsonPrimitive())
		{
			final JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
			if (jsonPrimitive.isNumber())
			{
				final Number number = jsonPrimitive.getAsNumber();
				if(number instanceof BigDecimal)
				{
					return BigDecimal.class;
				}
				return Integer.class;
			}
			else if (jsonPrimitive.isBoolean())
			{
				return Boolean.class;
			}
			else if (jsonPrimitive.isString())
			{
				return String.class;
			}
			else
			{
				throw new IllegalArgumentException("JSON primitive not supported: " + jsonPrimitive);
			}
		}
		final String classname = json.getAsJsonObject().get(UIResourceJsonSerializer.PROPERTY_Classname).getAsString();
		try
		{
			return Thread.currentThread()
					.getContextClassLoader()
					.loadClass(classname);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	static abstract class UIResourceJsonSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T>
	{
		public static final String PROPERTY_Classname = "className";
	}

	public static class ColorJsonSerializer extends UIResourceJsonSerializer<Color>
	{
		@Override
		public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context)
		{
			JsonObject jo = new JsonObject();
			jo.addProperty(PROPERTY_Classname, src.getClass().getName());
			jo.addProperty("r", src.getRed());
			jo.addProperty("g", src.getGreen());
			jo.addProperty("b", src.getBlue());
			jo.addProperty("a", src.getAlpha());
			return jo;
		}

		@Override
		public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject jo = json.getAsJsonObject();
			final int r = jo.get("r").getAsInt();
			final int g = jo.get("g").getAsInt();
			final int b = jo.get("b").getAsInt();
			final int a = jo.get("a").getAsInt();
			return new ColorUIResource(new Color(r, g, b, a));
		}
	}

	public static class FontJsonSerializer extends UIResourceJsonSerializer<Font>
	{
		@Override
		public JsonElement serialize(Font src, Type typeOfSrc, JsonSerializationContext context)
		{
			JsonObject jo = new JsonObject();
			jo.addProperty(PROPERTY_Classname, src.getClass().getName());
			jo.addProperty("name", src.getName());
			jo.addProperty("style", src.getStyle());
			jo.addProperty("size", src.getSize());
			return jo;
		}

		@Override
		public Font deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject jo = json.getAsJsonObject();
			String name = jo.get("name").getAsString();
			int style = jo.get("style").getAsInt();
			int size = jo.get("size").getAsInt();
			return new FontUIResource(name, style, size);
		}
	}

	public static class VEditorDialogButtonAlignJsonSerializer extends UIResourceJsonSerializer<VEditorDialogButtonAlign>
	{

		@Override
		public JsonElement serialize(VEditorDialogButtonAlign src, Type typeOfSrc, JsonSerializationContext context)
		{
			final JsonObject jo = new JsonObject();
			jo.addProperty(PROPERTY_Classname, src.getClass().getName());
			jo.addProperty("value", src.toString());
			return jo;
		}

		@Override
		public VEditorDialogButtonAlign deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject jo = json.getAsJsonObject();
			final String value = jo.get("value").getAsString();

			try
			{
				return VEditorDialogButtonAlign.valueOf(value);
			}
			catch (Exception e)
			{
				logger.warn("Failed parsing value {}. Using default", value);
			}

			return VEditorDialogButtonAlign.DEFAULT_Value;
		}

	}
}
