/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.bpartner.department;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Department;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

@Repository
public class BPartnerDepartmentRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<BPartnerId, List<BPartnerDepartment>> bPartnerDepartmentCache = CCache.newLRUCache(I_C_BPartner_Department.Table_Name + "#by#C_BPartner_ID", 200, 60);

	@Nullable
	public BPartnerDepartment getById(@NonNull final BPartnerDepartmentId id)
	{
		return getByBPartnerId(id.getBpartnerId())
				.stream()
				.filter(dpt -> id.equals(dpt.getId()))
				.findFirst().orElse(null);
	}

	@NonNull
	public BPartnerDepartment getByIdNotNull(@NonNull final BPartnerDepartmentId id)
	{
		return getByBPartnerId(id.getBpartnerId())
				.stream()
				.filter(dpt -> id.equals(dpt.getId()))
				.findFirst().orElseThrow(() -> new AdempiereException("Missing BPartnerDepartment for id=" + id));
	}

	@NonNull
	public List<BPartnerDepartment> getByBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return bPartnerDepartmentCache.getOrLoad(bPartnerId, this::getByBPartnerId0);
	}

	private List<BPartnerDepartment> getByBPartnerId0(@NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_Department.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Department.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.orderBy(I_C_BPartner_Department.COLUMNNAME_Value)
				.create()
				.stream()
				.map(this::fromPO)
				.collect(ImmutableList.toImmutableList());
	}

	private BPartnerDepartment fromPO(@NonNull final I_C_BPartner_Department po)
	{
		final BPartnerDepartmentId id = BPartnerDepartmentId.ofRepoId(po.getC_BPartner_ID(), po.getC_BPartner_Department_ID());
		return new BPartnerDepartment(id, po.getValue(), po.getName(), po.getDescription());
	}
}
