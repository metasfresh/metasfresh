/*
 * #%L
 * de.metas.business
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

package de.metas.pricing.pricelist;

import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class PriceListRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public PriceListVersion createPriceListVersion(@NonNull final CreatePriceListVersionRequest request)
	{
		final I_M_PriceList_Version record = buildCreatePriceListVersion(request);
		saveRecord(record);

		return toPriceListVersion(record);
	}

	@NonNull
	public PriceListVersion updatePriceListVersion(@NonNull final PriceListVersion request)
	{
		final I_M_PriceList_Version record = buildPriceListVersion(request);
		saveRecord(record);

		return toPriceListVersion(record);
	}

	@NonNull
	private I_M_PriceList_Version buildCreatePriceListVersion(@NonNull final CreatePriceListVersionRequest request)
	{
		final I_M_PriceList_Version record = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);

		record.setAD_Org_ID(request.getOrgId().getRepoId());

		record.setM_PriceList_ID(request.getPriceListId().getRepoId());

		record.setValidFrom(TimeUtil.asTimestamp(request.getValidFrom()));
		record.setIsActive(request.getIsActive());

		if (request.getDescription() != null)
		{
			record.setDescription(request.getDescription());
		}

		return record;
	}

	@NonNull
	private I_M_PriceList_Version buildPriceListVersion(@NonNull final PriceListVersion request)
	{
		final I_M_PriceList_Version existingRecord = queryBL
				.createQueryBuilder(I_M_PriceList_Version.class)
				.filter(item -> item.getM_PriceList_Version_ID() == request.getPriceListVersionId().getRepoId())
				.create()
				.first();

		existingRecord.setAD_Org_ID(request.getOrgId().getRepoId());

		existingRecord.setM_PriceList_Version_ID(request.getPriceListVersionId().getRepoId());
		existingRecord.setM_PriceList_ID(request.getPriceListId().getRepoId());

		existingRecord.setValidFrom(TimeUtil.asTimestamp(request.getValidFrom()));
		existingRecord.setIsActive(request.getIsActive());

		if (request.getDescription() != null)
		{
			existingRecord.setDescription(request.getDescription());
		}

		return existingRecord;
	}

	@NonNull
	private PriceListVersion toPriceListVersion(@NonNull final I_M_PriceList_Version record)
	{
		return PriceListVersion.builder()
				.priceListVersionId(PriceListVersionId.ofRepoId(record.getM_PriceList_Version_ID()))
				.priceListId(PriceListId.ofRepoId(record.getM_PriceList_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.description(record.getDescription())
				.validFrom(TimeUtil.asInstant(record.getValidFrom()))
				.isActive(record.isActive())
				.build();
	}
}
