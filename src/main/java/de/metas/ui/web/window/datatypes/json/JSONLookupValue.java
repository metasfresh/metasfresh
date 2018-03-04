package de.metas.ui.web.window.datatypes.json;

import java.util.Map;

import org.compiere.util.Env;
import org.compiere.util.NamePair;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ApiModel(value = "lookup-value", description = "pair of { field : value}")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@EqualsAndHashCode
public final class JSONLookupValue
{
	public static final JSONLookupValue of(final String key, final String caption)
	{
		final Map<String, Object> attributes = null;
		return new JSONLookupValue(key, caption, attributes);
	}

	public static final JSONLookupValue of(final int key, final String caption)
	{
		final String keyStr = String.valueOf(key);
		return of(keyStr, caption);
	}

	public static final JSONLookupValue ofLookupValue(final LookupValue lookupValue)
	{
		final String id = lookupValue.getIdAsString();

		final ITranslatableString displayNameTrl = lookupValue.getDisplayNameTrl();
		final String adLanguage = Env.getAD_Language(Env.getCtx()); // FIXME add it as parameter!
		final String displayName = displayNameTrl.translate(adLanguage);

		return new JSONLookupValue(id, displayName, lookupValue.getAttributes());
	}

	public static final JSONLookupValue ofNamePair(final NamePair namePair)
	{
		return of(namePair.getID(), namePair.getName());
	}

	public static final IntegerLookupValue integerLookupValueFromJsonMap(final Map<String, Object> map)
	{
		final Object keyObj = map.get(PROPERTY_Key);
		if (keyObj == null)
		{
			return null;
		}
		final String keyStr = keyObj.toString().trim();
		if (keyStr.isEmpty())
		{
			return null;
		}
		final int keyInt = Integer.parseInt(keyStr);

		final Object captionObj = map.get(PROPERTY_Caption);
		final String caption = captionObj != null ? captionObj.toString() : "";
		final ITranslatableString displayName = ImmutableTranslatableString.anyLanguage(caption);

		@SuppressWarnings("unchecked")
		final Map<String, Object> attributes = (Map<String, Object>)map.get(PROPERTY_Attributes);
		if (attributes == null || attributes.isEmpty())
		{
			return IntegerLookupValue.of(keyInt, displayName);
		}

		return IntegerLookupValue.builder()
				.id(keyInt)
				.displayName(displayName)
				.attributes(attributes)
				.build();
	}

	public static final StringLookupValue stringLookupValueFromJsonMap(final Map<String, Object> map)
	{
		final Object keyObj = map.get(PROPERTY_Key);
		final String key = keyObj != null ? keyObj.toString() : null;

		final Object captionObj = map.get(PROPERTY_Caption);
		final String caption = captionObj != null ? captionObj.toString() : "";
		final ITranslatableString displayName = ImmutableTranslatableString.anyLanguage(caption);

		@SuppressWarnings("unchecked")
		final Map<String, Object> attributes = (Map<String, Object>)map.get(PROPERTY_Attributes);
		if (attributes == null || attributes.isEmpty())
		{
			return StringLookupValue.of(key, displayName);
		}

		return StringLookupValue.builder()
				.id(key)
				.displayName(displayName)
				.attributes(attributes)
				.build();
	}

	public static JSONLookupValue unknown(final int id)
	{
		return of(id, "<" + id + ">");
	}

	public static final JSONLookupValue concat(final JSONLookupValue lookupValue1, final JSONLookupValue lookupValue2)
	{
		if (lookupValue1 == null)
		{
			return lookupValue2;
		}
		if (lookupValue2 == null)
		{
			return lookupValue1;
		}

		final String key = Joiner.on("_").skipNulls().join(lookupValue1.getKey(), lookupValue2.getKey());
		final String caption = Joiner.on(" ").skipNulls().join(lookupValue1.getCaption(), lookupValue2.getCaption());
		return JSONLookupValue.of(key, caption);

	}

	// IMPORTANT: when changing this property name, pls also check/change de.metas.handlingunits.attribute.impl.AbstractAttributeValue.extractKey(Map<String, String>, I_M_Attribute)
	private static final String PROPERTY_Key = "key";
	@JsonProperty(PROPERTY_Key)
	private final String key;
	@JsonIgnore
	private transient Integer keyAsInt = null; // lazy

	private static final String PROPERTY_Caption = "caption";
	@JsonProperty(PROPERTY_Caption)
	private final String caption;

	private static final String PROPERTY_Attributes = "attributes";
	@JsonProperty(PROPERTY_Attributes)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Map<String, Object> attributes;

	@JsonCreator
	private JSONLookupValue(
			@JsonProperty(PROPERTY_Key) @NonNull final String key,
			@JsonProperty(PROPERTY_Caption) @NonNull final String caption,
			@JsonProperty(PROPERTY_Attributes) final Map<String, Object> attributes)
	{
		this.key = key;
		this.caption = caption;
		this.attributes = attributes != null && !attributes.isEmpty() ? ImmutableMap.copyOf(attributes) : ImmutableMap.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("key", key)
				.add("caption", caption)
				.add("attributes", attributes)
				.toString();
	}

	public String getKey()
	{
		return key;
	}

	public int getKeyAsInt()
	{
		if (keyAsInt == null)
		{
			keyAsInt = Integer.parseInt(getKey());
		}

		return keyAsInt;
	}

	public String getCaption()
	{
		return caption;
	}

	public Map<String, Object> getAttributes()
	{
		return attributes;
	}
}
