/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.postfinance;

import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_PostFinance_Org_Config;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostFinanceOrgConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public PostFinanceOrgConfig getByOrgId(@NonNull final OrgId orgId)
	{
		return getRecordByOrgId(orgId)
				.map(PostFinanceOrgConfigRepository::toPostFinanceOrgConfig)
				.orElseThrow(() -> new AdempiereException("No PostFinance config found for OrgId!")
						.appendParametersToMessage()
						.setParameter("OrgId", orgId));
	}

	@NonNull
	private Optional<I_PostFinance_Org_Config> getRecordByOrgId(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_PostFinance_Org_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PostFinance_Org_Config.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnlyOptional();
	}

	@NonNull
	private static PostFinanceOrgConfig toPostFinanceOrgConfig(@NonNull final I_PostFinance_Org_Config financeOrgConfig)
	{
		return PostFinanceOrgConfig.builder()
				.id(PostFinanceOrgConfigId.ofRepoId(financeOrgConfig.getPostFinance_Org_Config_ID()))
				.billerId(financeOrgConfig.getPostFinance_Sender_BillerId())
				.isArchiveData(financeOrgConfig.isArchiveData())
				.isUsePaperBill(financeOrgConfig.isUsePaperBill())
				.build();
	}
}
