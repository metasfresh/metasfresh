/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.kpi.descriptor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class KPIField
{
	@NonNull String fieldName;
	boolean groupBy;
	@NonNull ITranslatableString caption;
	@NonNull ITranslatableString offsetCaption;
	@NonNull ITranslatableString description;
	@Nullable ITranslatableString unit;
	@NonNull KPIFieldValueType valueType;
	@Nullable Integer numberPrecision;
	@Nullable String color;

	@Builder
	private KPIField(
			@NonNull final String fieldName,
			final boolean groupBy,
			@NonNull final ITranslatableString caption,
			@Nullable final ITranslatableString offsetCaption,
			@Nullable final ITranslatableString description,
			@Nullable final ITranslatableString unit,
			@NonNull final KPIFieldValueType valueType,
			@Nullable final Integer numberPrecision,
			@Nullable final String color)
	{
		this.fieldName = fieldName;
		this.groupBy = groupBy;
		this.caption = caption;
		this.offsetCaption = offsetCaption != null ? offsetCaption : TranslatableStrings.empty();
		this.description = description != null ? description : TranslatableStrings.empty();
		this.unit = !TranslatableStrings.isBlank(unit) ? unit : null;
		this.valueType = valueType;
		this.numberPrecision = numberPrecision;
		this.color = color;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldName", fieldName)
				.add("groupBy", groupBy)
				.add("valueType", valueType)
				.toString();
	}

	public String getOffsetFieldName()
	{
		return fieldName + "_offset";
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getOffsetCaption(final String adLanguage)
	{
		return offsetCaption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public Optional<String> getUnit(final String adLanguage)
	{
		return unit != null
				? StringUtils.trimBlankToOptional(unit.translate(adLanguage))
				: Optional.empty();
	}
}
