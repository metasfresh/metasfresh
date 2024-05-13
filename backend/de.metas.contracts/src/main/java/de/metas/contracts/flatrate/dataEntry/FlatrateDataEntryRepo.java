/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.flatrate.dataEntry;

import de.metas.bpartner.BPartnerDepartmentId;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_DataEntry_Detail;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlatrateDataEntryRepo
{
	@NonNull
	public FlatrateDataEntry getById(@NonNull final FlatrateDataEntryId id)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_C_Flatrate_DataEntry flatrateDataEntry = InterfaceWrapperHelper.load(id, I_C_Flatrate_DataEntry.class);

		final List<I_C_Flatrate_DataEntry_Detail> detailRecords = queryBL.createQueryBuilder(I_C_Flatrate_DataEntry_Detail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_DataEntry_Detail.COLUMNNAME_C_Flatrate_DataEntry_ID, id)
				.orderBy(I_C_Flatrate_DataEntry_Detail.COLUMNNAME_SeqNo)
				.create()
				.list();

		return fromPO(flatrateDataEntry, detailRecords);
	}

	private FlatrateDataEntry fromPO(
			@NonNull final I_C_Flatrate_DataEntry flatrateDataEntry,
			@NonNull final List<I_C_Flatrate_DataEntry_Detail> detailRecords)
	{

		final BPartnerId billBPartnerId = BPartnerId.ofRepoId(flatrateDataEntry.getC_Flatrate_Term().getBill_BPartner_ID());

		final FlatrateDataEntryId flatrateDataEntryId = FlatrateDataEntryId.ofRepoId(flatrateDataEntry.getC_Flatrate_DataEntry_ID());

		final FlatrateDataEntry.FlatrateDataEntryBuilder result = FlatrateDataEntry.builder();
		result.uomId(UomId.ofRepoId(flatrateDataEntry.getC_UOM_ID()));
		result.id(flatrateDataEntryId);

		for (final I_C_Flatrate_DataEntry_Detail detailRecord : detailRecords)
		{
			final FlatrateDataEntryDetail entryDetail = FlatrateDataEntryDetail.builder()
					.id(FlatrateDataEntryDetailId.ofRepoId(flatrateDataEntryId, detailRecord.getC_Flatrate_DataEntry_Detail_ID()))
					.bPartnerDepartmentId(BPartnerDepartmentId.ofRepoIdOrNull(billBPartnerId, detailRecord.getC_BPartner_Department_ID()))
					.asiId(AttributeSetInstanceId.ofRepoIdOrNone(detailRecord.getM_AttributeSetInstance_ID()))
					.quantity(Quantitys.create(detailRecord.getQty_Reported(), UomId.ofRepoId(detailRecord.getC_UOM_ID())))
					.build();
			result.detail(entryDetail);
		}

		return result.build();
	}
}
