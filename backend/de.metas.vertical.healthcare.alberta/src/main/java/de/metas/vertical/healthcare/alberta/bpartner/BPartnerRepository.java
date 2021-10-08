/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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

package de.metas.vertical.healthcare.alberta.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BP_Relation;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class BPartnerRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Optional<BPartnerId> getLastUpdatedPreferedPharmacyByPartnerId(@Nullable final BPartnerId bpartnerId)
	{
		if (bpartnerId != null)
		{
			final IQuery<I_C_BP_Relation> query = queryBL.createQueryBuilder(I_C_BP_Relation.class)
					.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, bpartnerId)
					.addEqualsFilter(I_C_BP_Relation.COLUMN_Role, "PP")
					.addOnlyActiveRecordsFilter()
					.create();

			final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_C_BP_Relation.class)
					.addColumnDescending(I_C_BP_Relation.COLUMNNAME_Updated)
					.createQueryOrderBy();
			query.setOrderBy(orderBy);

			return query
					.firstOptional(I_C_BP_Relation.class)
					.map(bpRelation -> BPartnerId.ofRepoId(bpRelation.getC_BPartnerRelation_ID()));
		}
		return Optional.empty();

	}

}
