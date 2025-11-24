/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.incoterms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Incoterms;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncotermsRepository
{
	private final CCache<Integer, IncotermsMap> cache = CCache.<Integer, IncotermsMap>builder()
			.tableName(I_C_Incoterms.Table_Name)
			.maximumSize(1)
			.build();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public static IncotermsRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new IncotermsRepository();
	}

	@Nullable
	public Incoterms getDefaultIncoterms(final @NotNull OrgId orgId)
	{
		return getIncotermsMap().getDefaultByOrgId(orgId);
	}

	@NotNull
	public Incoterms getById(@NotNull final IncotermsId id)
	{
		return getIncotermsMap().getById(id);
	}

	@NonNull
	private IncotermsMap getIncotermsMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveIncotermsMap);
	}

	@NotNull
	private IncotermsMap retrieveIncotermsMap()
	{
		final ImmutableList<Incoterms> incoterms = queryBL.createQueryBuilder(I_C_Incoterms.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(IncotermsRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());

		return new IncotermsRepository.IncotermsMap(incoterms);
	}

	private static Incoterms ofRecord(@NotNull final I_C_Incoterms record)
	{
		return Incoterms.builder()
				.id(IncotermsId.ofRepoId(record.getC_Incoterms_ID()))
				.name(record.getName())
				.value(record.getValue())
				.isDefault(record.isDefault())
				.defaultLocation(record.getDefaultLocation())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.build();
	}

	private static final class IncotermsMap
	{
		private final ImmutableMap<IncotermsId, Incoterms> byId;
		private final ImmutableMap<OrgId, Incoterms> defaultByOrgId;

		IncotermsMap(final List<Incoterms> list)
		{
			this.byId = Maps.uniqueIndex(list, Incoterms::getId);
			this.defaultByOrgId = Maps.uniqueIndex(
					list.stream().filter(Incoterms::isDefault).collect(ImmutableList.toImmutableList()),
					Incoterms::getOrgId);
		}

		@NonNull
		public Incoterms getById(@NonNull final IncotermsId id)
		{
			final Incoterms incoterms = byId.get(id);
			if (incoterms == null)
			{
				throw new AdempiereException("Incoterms not found by ID: " + id);
			}
			return incoterms;
		}

		@Nullable
		public Incoterms getDefaultByOrgId(@NonNull final OrgId orgId)
		{
			return CoalesceUtil.coalesce(defaultByOrgId.get(orgId), defaultByOrgId.get(OrgId.ANY));
		}
	}
}
