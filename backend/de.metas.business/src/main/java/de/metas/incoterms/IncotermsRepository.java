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

import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Incoterms;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.load;

@Repository
public class IncotermsRepository
{
	private final CCache<IncotermsId, Incoterms> cache = CCache.<IncotermsId, Incoterms>builder()
			.tableName(I_C_Incoterms.Table_Name)
			.maximumSize(50)
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
		return queryBL.createQueryBuilder(I_C_Incoterms.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Incoterms.COLUMNNAME_IsDefault, true)
				.addInArrayFilter(I_C_Incoterms.COLUMNNAME_AD_Org_ID, orgId.getRepoId(), OrgId.ANY)
				.orderByDescending(I_C_Incoterms.COLUMNNAME_AD_Org_ID)
				.create()
				.firstOnlyOptional(I_C_Incoterms.class)
				.map(this::ofRecord)
				.orElse(null);
	}

	@NotNull
	public Incoterms getById(@NotNull final IncotermsId id)
	{
		return cache.getOrLoadNonNull(id, this::retrieveById);
	}

	@NotNull
	private Incoterms retrieveById(@NotNull final IncotermsId id)
	{
		return ofRecord(load(id, I_C_Incoterms.class));
	}

	private Incoterms ofRecord(@NotNull final I_C_Incoterms record)
	{
		return Incoterms.builder()
				.id(IncotermsId.ofRepoId(record.getC_Incoterms_ID()))
				.name(record.getName())
				.value(record.getValue())
				.isDefault(record.isDefault())
				.defaultLocation(record.getDefaultLocation())
				.build();
	}
}
