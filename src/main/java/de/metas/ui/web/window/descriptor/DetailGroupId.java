package de.metas.ui.web.window.descriptor;

import static de.metas.util.Check.assumeGreaterOrEqualToZero;
import static de.metas.util.Check.isEmpty;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;

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

@Immutable
@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class DetailGroupId
{
	public static final DetailGroupId ANONYMOUS = fromTabGroupId(0);

	public static DetailGroupId fromTabGroupId(final int tabGroupId)
	{
		return new DetailGroupId(tabGroupId);
	}

	@JsonCreator
	public static final DetailGroupId fromJson(@Nullable final String json)
	{
		if (json == null)
		{
			return null;
		}

		if (isEmpty(json, true))
		{
			return null;
		}

		final int tabGroupId = Integer.parseInt(json.trim());
		return fromTabGroupId(tabGroupId);
	}

	public static final String toJson(final DetailGroupId detailGroupId)
	{
		return detailGroupId == null ? null : String.valueOf(detailGroupId.detailGroupId);
	}

	public static final Set<String> toJson(final Collection<DetailGroupId> detailIds)
	{
		if (detailIds == null || detailIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return detailIds.stream().map(detailId -> detailId.toJson()).collect(GuavaCollectors.toImmutableSet());
	}

	private final int detailGroupId;

	private transient String _tableAlias = null; // lazy

	private DetailGroupId(final int detailGroupId)
	{
		// note that the anonymous group has the ID "zero"
		this.detailGroupId = assumeGreaterOrEqualToZero(detailGroupId, "detailGroupId");
	}

	@Override
	public String toString()
	{
		return toJson(this);
	}

	@JsonValue
	public String toJson()
	{
		return toJson(this);
	}

	public String getTableAlias()
	{
		if (_tableAlias == null)
		{
			_tableAlias = "dg" + detailGroupId;
		}
		return _tableAlias;
	}
}
