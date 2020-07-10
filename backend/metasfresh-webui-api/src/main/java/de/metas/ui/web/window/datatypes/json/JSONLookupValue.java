package de.metas.ui.web.window.datatypes.json;

import java.util.Map;

import javax.annotation.Nullable;

import org.compiere.util.NamePair;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue.IntegerLookupValueBuilder;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue.StringLookupValueBuilder;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
	public static JSONLookupValue of(@NonNull final RepoIdAware key, final String caption)
	{
		return of(key.getRepoId(), caption);
	}

	public static JSONLookupValue of(final int key, final String caption)
	{
		final String keyStr = String.valueOf(key);
		final String description = null;
		final Map<String, Object> attributes = null;
		final Boolean active = null;
		final JSONLookupValueValidationInformation validationInformation = null;
		return new JSONLookupValue(keyStr, caption, description, attributes, active, validationInformation);
	}

	public static JSONLookupValue of(
			final String key,
			final String caption)
	{
		final String description = null;
		final Map<String, Object> attributes = null;
		final Boolean active = null;
		final JSONLookupValueValidationInformation validationInformation = null;
		return new JSONLookupValue(key, caption, description, attributes, active, validationInformation);
	}

	public static JSONLookupValue of(
			final String key,
			final String caption,
			@Nullable final String description)
	{
		final Map<String, Object> attributes = null;
		final Boolean active = null;
		return new JSONLookupValue(key, caption, description, attributes, active, null);
	}

	public static JSONLookupValue ofLookupValue(@NonNull final LookupValue lookupValue, @NonNull final String adLanguage)
	{
		final String id = lookupValue.getIdAsString();

		final ITranslatableString displayNameTrl = lookupValue.getDisplayNameTrl();
		final ITranslatableString descriptionTrl = lookupValue.getDescriptionTrl();

		// final String adLanguage = Env.getAD_Language(Env.getCtx());
		final String displayName = displayNameTrl.translate(adLanguage);
		final String description = descriptionTrl.translate(adLanguage);

		final JSONLookupValueValidationInformation validationInformation = JSONLookupValueValidationInformation.ofNullable(
				lookupValue.getValidationInformation(),
				adLanguage);

		// NOTE: for bandwidth optimization, we provide the flag only when it's false
		final Boolean active = !lookupValue.isActive() ? Boolean.FALSE : null;

		return new JSONLookupValue(id, displayName, description, lookupValue.getAttributes(), active, validationInformation);
	}

	public static JSONLookupValue ofNamePair(final NamePair namePair)
	{
		final Map<String, Object> attributes = null;
		final Boolean active = null;
		final JSONLookupValueValidationInformation validationInformation = null;
		return new JSONLookupValue(namePair.getID(), namePair.getName(), namePair.getDescription(), attributes, active, validationInformation);
	}

	public static IntegerLookupValue integerLookupValueFromJsonMap(@NonNull final Map<String, Object> map)
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

		final ITranslatableString displayName = extractCaption(map);
		final ITranslatableString description = extractDescription(map);
		final Boolean active = extractActive(map);

		final IntegerLookupValueBuilder builder = IntegerLookupValue.builder()
				.id(keyInt)
				.displayName(displayName)
				.description(description)
				.active(active);

		@SuppressWarnings("unchecked")
		final Map<String, Object> attributes = (Map<String, Object>)map.get(PROPERTY_Attributes);
		if (attributes != null && !attributes.isEmpty())
		{
			builder.attributes(attributes);
		}

		return builder.build();
	}

	public static StringLookupValue stringLookupValueFromJsonMap(@NonNull final Map<String, Object> map)
	{
		final Object keyObj = map.get(PROPERTY_Key);
		final String key = keyObj != null ? keyObj.toString() : null;

		final ITranslatableString displayName = extractCaption(map);
		final ITranslatableString description = extractDescription(map);
		final Boolean active = extractActive(map);

		final StringLookupValueBuilder builder = StringLookupValue.builder()
				.id(key)
				.displayName(displayName)
				.description(description)
				.active(active)
				.validationInformation(null); // TODO: Extract this from map for future usages

		@SuppressWarnings("unchecked")
		final Map<String, Object> attributes = (Map<String, Object>)map.get(PROPERTY_Attributes);
		if (attributes != null && !attributes.isEmpty())
		{
			builder.attributes(attributes);
		}

		return builder.build();
	}

	private static ITranslatableString extractCaption(@NonNull final Map<String, Object> map)
	{
		final Object captionObj = map.get(PROPERTY_Caption);
		final String caption = captionObj != null ? captionObj.toString() : "";
		final ITranslatableString displayName = TranslatableStrings.anyLanguage(caption);
		return displayName;
	}

	private static ITranslatableString extractDescription(@NonNull final Map<String, Object> map)
	{
		final Object descriptionObj = map.get(PROPERTY_Description);
		final String descriptionStr = descriptionObj != null ? descriptionObj.toString() : "";
		final ITranslatableString description = TranslatableStrings.anyLanguage(descriptionStr);
		return description;
	}

	private static Boolean extractActive(@NonNull final Map<String, Object> map)
	{
		return StringUtils.toBoolean(map.get(PROPERTY_Active), null);
	}

	public static JSONLookupValue unknown(final int id)
	{
		return of(id, "<" + id + ">");
	}

	public static JSONLookupValue unknown(@Nullable final RepoIdAware id)
	{
		final int idAsInt = id != null ? id.getRepoId() : -1;
		return unknown(idAsInt);
	}

	// IMPORTANT: when changing this property name, pls also check/change de.metas.handlingunits.attribute.impl.AbstractAttributeValue.extractKey(Map<String, String>, I_M_Attribute)
	static final String PROPERTY_Key = "key";
	@JsonProperty(PROPERTY_Key)
	@Getter
	private final String key;
	@JsonIgnore
	private transient Integer keyAsInt = null; // lazy

	private static final String PROPERTY_Caption = "caption";
	@JsonProperty(PROPERTY_Caption)
	@Getter
	private final String caption;

	private static final String PROPERTY_Description = "description";
	@JsonProperty(PROPERTY_Description)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Getter
	private final String description;

	private static final String PROPERTY_Attributes = "attributes";
	@JsonProperty(PROPERTY_Attributes)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Getter
	private final Map<String, Object> attributes;

	private static final String PROPERTY_Active = "active";
	@JsonProperty(PROPERTY_Active)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Boolean active;

	private static final String PROPERTY_ValidationInformation = "validationInformation";
	@JsonProperty(PROPERTY_ValidationInformation)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Getter
	private final JSONLookupValueValidationInformation validationInformation;

	@JsonCreator
	private JSONLookupValue(
			@JsonProperty(PROPERTY_Key) @NonNull final String key,
			@JsonProperty(PROPERTY_Caption) @NonNull final String caption,
			@JsonProperty(PROPERTY_Description) @Nullable final String description,
			@JsonProperty(PROPERTY_Attributes) final Map<String, Object> attributes,
			@JsonProperty(PROPERTY_Active) final Boolean active,
			@JsonProperty(PROPERTY_ValidationInformation) @Nullable final JSONLookupValueValidationInformation validationInformation)
	{
		this.key = key;
		this.caption = caption;
		this.description = description;
		this.attributes = attributes != null && !attributes.isEmpty() ? ImmutableMap.copyOf(attributes) : ImmutableMap.of();
		this.active = active;
		this.validationInformation = validationInformation;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("key", key)
				.add("caption", caption)
				.add("attributes", attributes)
				.add("active", active)
				.add("validationInformation", validationInformation)
				.toString();
	}

	public int getKeyAsInt()
	{
		Integer keyAsInt = this.keyAsInt;
		if (keyAsInt == null)
		{
			keyAsInt = this.keyAsInt = Integer.parseInt(getKey());
		}

		return keyAsInt;
	}

	public IntegerLookupValue toIntegerLookupValue()
	{
		return IntegerLookupValue.builder()
				.id(getKeyAsInt())
				.displayName(TranslatableStrings.constant(getCaption()))
				.attributes(getAttributes())
				.active(isActive())
				.build();
	}

	public StringLookupValue toStringLookupValue()
	{
		return StringLookupValue.builder()
				.id(getKey())
				.displayName(TranslatableStrings.constant(getCaption()))
				.attributes(getAttributes())
				.active(isActive())
				.validationInformation(null) // NOTE: converting back from JSON is not supported nor needed atm
				.build();
	}

	private boolean isActive()
	{
		return active == null || active.booleanValue();
	}

}
