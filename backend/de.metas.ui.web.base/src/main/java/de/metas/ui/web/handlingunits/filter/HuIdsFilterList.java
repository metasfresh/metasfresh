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

package de.metas.ui.web.handlingunits.filter;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public final class HuIdsFilterList
{
	public static final HuIdsFilterList ALL = new HuIdsFilterList(true, null);
	public static final HuIdsFilterList NONE = new HuIdsFilterList(false, ImmutableSet.of());

	public static HuIdsFilterList of(@NonNull final Collection<HuId> huIds)
	{
		return !huIds.isEmpty()
				? new HuIdsFilterList(false, ImmutableSet.copyOf(huIds))
				: NONE;
	}

	public static HuIdsFilterList of(@NonNull final HuId... huIds)
	{
		return huIds != null && huIds.length > 0
				? new HuIdsFilterList(false, ImmutableSet.copyOf(huIds))
				: NONE;
	}

	private final boolean all;
	private final ImmutableSet<HuId> huIds;

	private HuIdsFilterList(final boolean all, @Nullable final ImmutableSet<HuId> huIds)
	{
		this.all = all;
		if (this.all)
		{
			this.huIds = null;
		}
		else
		{
			this.huIds = Check.assumeNotNull(huIds, "huIds");
		}
	}

	public boolean isAll()
	{
		return all;
	}

	private void assertNotAll()
	{
		if (all)
		{
			throw new AdempiereException("method not supported for ALL");
		}
	}

	public boolean isNone()
	{
		return !all && huIds.isEmpty();
	}

	public Integer estimateSize()
	{
		return all ? null : huIds.size();
	}

	public Stream<HuId> stream()
	{
		assertNotAll();
		return huIds.stream();
	}

	public ImmutableSet<HuId> toSet()
	{
		assertNotAll();
		return huIds;
	}
}
