package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;
import org.compiere.model.GridTabVO;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import groovy.transform.Immutable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Immutable
@SuppressWarnings("serial")
public final class DetailId implements Serializable
{
	/**
	 * @param tabNo
	 * @return {@link DetailId} or null if given TabNo is the main tab
	 */
	public static DetailId fromTabNoOrNull(final int tabNo)
	{
		if (tabNo == GridTabVO.MAIN_TabNo)
		{
			return null;
		}

		final DetailId detailId = cachedValues.get(tabNo);
		if (detailId != null)
		{
			return detailId;
		}

		return new DetailId(tabNo);
	}

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

		final int tabNo = Integer.parseInt(json);
		if (tabNo == GridTabVO.MAIN_TabNo)
		{
			return null;
		}

		return fromTabNoOrNull(tabNo);
	}
	
	public static final String toJson(final DetailId detailId)
	{
		return detailId == null ? null : String.valueOf(detailId.tabNo);
	}
	
	public static final Set<String> toJson(final Collection<DetailId> detailIds)
	{
		if(detailIds == null || detailIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		
		return detailIds.stream().map(DetailId::toJson).collect(GuavaCollectors.toImmutableSet());
	}

	public static int getTabNo(final DetailId detailId)
	{
		return detailId == null ? GridTabVO.MAIN_TabNo : detailId.getTabNo();

	}

	private static final Map<Integer, DetailId> cachedValues = ImmutableMap.<Integer, DetailId> builder()
			.put(1, new DetailId(1))
			.put(2, new DetailId(2))
			.put(3, new DetailId(3))
			.put(4, new DetailId(4))
			.put(5, new DetailId(5))
			.put(6, new DetailId(6))
			.build();

	private final int tabNo;

	private transient String _tableAlias = null; // lazy

	private DetailId(final int tabNo)
	{
		super();
		this.tabNo = tabNo;
	}

	@Override
	public String toString()
	{
		return toJson(this);
	}

	@Override
	public int hashCode()
	{
		return tabNo;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj instanceof DetailId)
		{
			final DetailId other = (DetailId)obj;
			return tabNo == other.tabNo;
		}

		return false;
	}

	private int getTabNo()
	{
		return tabNo;
	}

	public String getTableAlias()
	{
		if (_tableAlias == null)
		{
			_tableAlias = "d" + tabNo;
		}
		return _tableAlias;
	}
}
