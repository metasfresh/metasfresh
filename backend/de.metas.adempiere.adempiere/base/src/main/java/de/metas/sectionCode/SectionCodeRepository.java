/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.sectionCode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_SectionCode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SectionCodeRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, SectionCodesMap> cache = CCache.<Integer, SectionCodesMap>builder()
			.tableName(I_M_SectionCode.Table_Name)
			.build();

	@NonNull
	public SectionCode getById(@NonNull final SectionCodeId sectionCodeId)
	{
		return getMap().getById(sectionCodeId);
	}

	@NonNull
	public Optional<SectionCodeId> getSectionCodeIdByValue(@NonNull final OrgId orgId, @NonNull final String value)
	{
		return getMap().getByOrgIdAndValue(orgId, value).map(SectionCode::getSectionCodeId);
	}

	private SectionCodesMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private SectionCodesMap retrieveMap()
	{
		final List<SectionCode> list = queryBL.createQueryBuilder(I_M_SectionCode.class)
				//.addOnlyActiveRecordsFilter() // all!
				.stream()
				.map(SectionCodeRepository::ofRecord)
				.collect(Collectors.toList());

		return new SectionCodesMap(list);
	}

	@NonNull
	private static SectionCode ofRecord(@NonNull final I_M_SectionCode record)
	{
		return SectionCode.builder()
				.sectionCodeId(SectionCodeId.ofRepoId(record.getM_SectionCode_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.value(record.getValue())
				.name(record.getName())
				.isActive(record.isActive())
				.build();
	}

	//
	//
	//

	private static class SectionCodesMap
	{
		private final ImmutableList<SectionCode> list;
		private final ImmutableMap<SectionCodeId, SectionCode> byId;

		public SectionCodesMap(@NonNull final List<SectionCode> list)
		{
			this.list = ImmutableList.copyOf(list);
			this.byId = Maps.uniqueIndex(list, SectionCode::getSectionCodeId);
		}

		public SectionCode getById(@NonNull final SectionCodeId sectionCodeId)
		{
			final SectionCode sectionCode = byId.get(sectionCodeId);
			if (sectionCode == null)
			{
				throw new AdempiereException("No section code found for " + sectionCodeId);
			}
			return sectionCode;
		}

		public Optional<SectionCode> getByOrgIdAndValue(@NonNull final OrgId orgId, @NonNull final String value)
		{
			final List<SectionCode> result = list.stream()
					.filter(sectionCode -> sectionCode.isActive()
							&& OrgId.equals(sectionCode.getOrgId(), orgId)
							&& Objects.equals(sectionCode.getValue(), value))
					.collect(Collectors.toList());

			return CollectionUtils.emptyOrSingleElement(result);
		}

	}
}
