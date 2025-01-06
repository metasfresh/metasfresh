package de.metas.ui.web.window.datatypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.OptionalInt;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@EqualsAndHashCode
@SuppressWarnings({ "OptionalAssignedToNull", "OptionalUsedAsFieldOrParameterType" })
public final class WindowId
{
	@JsonCreator
	public static WindowId fromJson(@NonNull final String json)
	{
		return new WindowId(json);
	}

	@Nullable
	public static WindowId fromNullableJson(@Nullable final String json)
	{
		return json != null ? fromJson(json) : null;
	}

	public static WindowId of(final int windowIdInt)
	{
		return new WindowId(windowIdInt);
	}

	public static WindowId of(@NonNull final AdWindowId adWindowId)
	{
		return new WindowId(adWindowId.getRepoId());
	}

	public static WindowId of(final DocumentId documentTypeId)
	{
		if (documentTypeId.isInt())
		{
			return new WindowId(documentTypeId.toInt());
		}
		else
		{
			return new WindowId(documentTypeId.toJson());
		}
	}

	@Nullable
	public static WindowId ofNullable(@Nullable final AdWindowId adWindowId)
	{
		return adWindowId != null ? new WindowId(adWindowId.getRepoId()) : null;
	}

	private final String value;
	private transient OptionalInt valueInt = null; // lazy

	private WindowId(final String value)
	{
		Check.assumeNotEmpty(value, "value is not empty");
		this.value = value;
	}

	private WindowId(final int valueInt)
	{
		Check.assumeGreaterThanZero(valueInt, "valueInt");
		this.valueInt = OptionalInt.of(valueInt);
		value = String.valueOf(valueInt);
	}

	@Override
	@Deprecated
	public String toString()
	{
		return toJson();
	}

	@JsonValue
	public String toJson()
	{
		return value;
	}

	public int toInt()
	{
		return toOptionalInt()
				.orElseThrow(() -> new AdempiereException("WindowId cannot be converted to int: " + this));
	}

	public int toIntOr(final int fallbackValue)
	{
		return toOptionalInt()
				.orElse(fallbackValue);
	}

	private OptionalInt toOptionalInt()
	{
		OptionalInt valueInt = this.valueInt;
		if (valueInt == null)
		{
			valueInt = this.valueInt = parseOptionalInt();
		}
		return valueInt;
	}

	private OptionalInt parseOptionalInt()
	{
		try
		{
			return OptionalInt.of(Integer.parseInt(value));
		}
		catch (final Exception ex)
		{
			return OptionalInt.empty();
		}
	}

	@Nullable
	public AdWindowId toAdWindowIdOrNull()
	{
		return AdWindowId.ofRepoIdOrNull(toIntOr(-1));
	}

	public AdWindowId toAdWindowId()
	{
		return AdWindowId.ofRepoId(toInt());
	}

	public boolean isInt()
	{
		return toOptionalInt().isPresent();
	}

	public DocumentId toDocumentId()
	{
		return DocumentId.of(value);
	}

	public static boolean equals(@Nullable final WindowId id1, @Nullable final WindowId id2)
	{
		return Objects.equals(id1, id2);
	}
}
