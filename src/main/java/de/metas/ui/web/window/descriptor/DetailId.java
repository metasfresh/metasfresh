package de.metas.ui.web.window.descriptor;

import java.util.Collection;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import groovy.transform.Immutable;
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
public final class DetailId
{
	public static DetailId fromAD_Tab_ID(final int adTabId)
	{
		return new DetailId(adTabId);
	}

	@JsonCreator
	public static final DetailId fromJson(String json)
	{
		if (json == null)
		{
			return null;
		}

		json = json.trim();
		if (json.isEmpty())
		{
			return null;
		}

		final int adTabId = Integer.parseInt(json);
		return fromAD_Tab_ID(adTabId);
	}

	public static final String toJson(final DetailId detailId)
	{
		return detailId == null ? null : String.valueOf(detailId.adTabId);
	}

	public static final Set<String> toJson(final Collection<DetailId> detailIds)
	{
		if (detailIds == null || detailIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return detailIds.stream().map(detailId -> detailId.toJson()).collect(GuavaCollectors.toImmutableSet());
	}

	private final int adTabId;

	private transient String _tableAlias = null; // lazy

	private DetailId(final int adTabId)
	{
		Check.assume(adTabId > 0, "adTabId > 0");
		this.adTabId = adTabId;
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
			_tableAlias = "d" + adTabId;
		}
		return _tableAlias;
	}
}
