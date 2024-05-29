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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner_Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BPartnerDepartmentRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<BPartnerDepartmentId> getIdsForBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_Department.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Department.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.orderBy(I_C_BPartner_Department.COLUMNNAME_Value)
				.create()
				.list()
				.stream()
				.map(record -> BPartnerDepartmentId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Department_ID()))
				.collect(ImmutableList.toImmutableList());
	}
}
