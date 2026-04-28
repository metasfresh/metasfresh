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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.department.BPartnerDepartment;
import de.metas.bpartner.department.BPartnerDepartmentId;
import de.metas.bpartner.department.BPartnerDepartmentRepo;
import de.metas.cache.CCache;
import de.metas.calendar.Period;
import de.metas.calendar.PeriodId;
import de.metas.calendar.PeriodRepo;
import de.metas.common.util.Check;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRepo;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_DataEntry_Detail;
import de.metas.document.engine.DocStatus;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class FlatrateDataEntryRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	
	private final BPartnerDepartmentRepo bPartnerDepartmentRepo;
	private final FlatrateTermRepo flatrateTermRepo;
	private final PeriodRepo periodRepo;

	private final CCache<FlatrateDataEntryId, FlatrateDataEntry> id2entry = CCache.newLRUCache(I_C_Flatrate_DataEntry.Table_Name + "#by#C_Flatrate_DataEntry_ID", 100, 60);

	public FlatrateDataEntryRepo(
			@NonNull final BPartnerDepartmentRepo bPartnerDepartmentRepo,
			@NonNull final FlatrateTermRepo flatrateTermRepo,
			@NonNull final PeriodRepo periodRepo)
	{
		this.bPartnerDepartmentRepo = bPartnerDepartmentRepo;
		this.flatrateTermRepo = flatrateTermRepo;
		this.periodRepo = periodRepo;
	}

	@NonNull
	public FlatrateDataEntry getById(@NonNull final FlatrateDataEntryId id)
	{
		return id2entry.getOrLoad(id, this::getById0);
	}

	private FlatrateDataEntry getById0(@NonNull final FlatrateDataEntryId id)
	{
		final I_C_Flatrate_DataEntry flatrateDataEntry = load(id, I_C_Flatrate_DataEntry.class);

		final List<I_C_Flatrate_DataEntry_Detail> detailRecords = queryBL.createQueryBuilder(I_C_Flatrate_DataEntry_Detail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_DataEntry_Detail.COLUMNNAME_C_Flatrate_DataEntry_ID, id)
				.orderBy(I_C_Flatrate_DataEntry_Detail.COLUMNNAME_SeqNo)
				.create()
				.list();

		return fromPO(flatrateDataEntry, detailRecords);
	}

	private FlatrateDataEntry fromPO(
			@NonNull final I_C_Flatrate_DataEntry dataEntryRecord,
			@NonNull final List<I_C_Flatrate_DataEntry_Detail> detailRecords)
	{
		final FlatrateTerm flatrateTerm = flatrateTermRepo.getById(FlatrateTermId.ofRepoId(dataEntryRecord.getC_Flatrate_Term_ID()));
		final BPartnerId bPartnerId = flatrateTerm.getShipOrBillPartnerId();
		
		final FlatrateDataEntryId flatrateDataEntryId = FlatrateDataEntryId.ofRepoId(
				FlatrateTermId.ofRepoId(dataEntryRecord.getC_Flatrate_Term_ID()),
				dataEntryRecord.getC_Flatrate_DataEntry_ID());
		
		final FlatrateDataEntry.FlatrateDataEntryBuilder result = FlatrateDataEntry.builder();

		final DocStatus docStatus = DocStatus.ofCode(dataEntryRecord.getDocStatus());
		result.processed(docStatus.isCompletedOrClosed());
		
		Check.errorIf(dataEntryRecord.getC_UOM_ID() <= 0, "C_Flatrate_DataEntry_ID={} needs to have a C_UOM_ID", dataEntryRecord.getC_Flatrate_DataEntry_ID());
		result.uomId(UomId.ofRepoId(dataEntryRecord.getC_UOM_ID()));

		result.id(flatrateDataEntryId);

		final PeriodId periodId = periodRepo.getPeriodId(dataEntryRecord.getC_Period_ID());
		final Period period = periodRepo.getById(periodId);
		result.period(period);

		for (final I_C_Flatrate_DataEntry_Detail detailRecord : detailRecords)
		{
			final BPartnerDepartmentId bPartnerDepartmentId = BPartnerDepartmentId.ofRepoIdOrNone(bPartnerId, detailRecord.getC_BPartner_Department_ID());
			final BPartnerDepartment bPartnerDepartment;

			if (bPartnerDepartmentId.isNone())
			{
				bPartnerDepartment = BPartnerDepartment.none(bPartnerId);
			}
			else
			{
				bPartnerDepartment = bPartnerDepartmentRepo.getByIdNotNull(bPartnerDepartmentId);
			}

			final FlatrateDataEntryDetail entryDetail = FlatrateDataEntryDetail.builder()
					.id(FlatrateDataEntryDetailId.ofRepoId(flatrateDataEntryId, detailRecord.getC_Flatrate_DataEntry_Detail_ID()))
					.bPartnerDepartment(bPartnerDepartment)
					.asiId(AttributeSetInstanceId.ofRepoIdOrNone(detailRecord.getM_AttributeSetInstance_ID()))
					.quantity(Quantitys.of(detailRecord.getQty_Reported(), UomId.ofRepoId(detailRecord.getC_UOM_ID())))
					.build();
			result.detail(entryDetail);
		}

		return result.build();
	}

	public FlatrateDataEntry save(@NonNull final FlatrateDataEntry entry)
	{
		final I_C_Flatrate_DataEntry entryRecord = load(entry.getId(), I_C_Flatrate_DataEntry.class);
		entryRecord.setC_UOM_ID(entry.getUomId().getRepoId());
		entryRecord.setC_Flatrate_Term_ID(entry.getId().getFlatrateTermId().getRepoId());

		final FlatrateDataEntry.FlatrateDataEntryBuilder result = entry.toBuilder().clearDetails();

		int maxSeqNo = 0;
		for (final FlatrateDataEntryDetail detail : entry.getDetails())
		{
			if (detail.getSeqNo() > maxSeqNo)
			{
				maxSeqNo = detail.getSeqNo();
			}
		}
		maxSeqNo += 10;

		Quantity qtySum = Quantitys.zero(entry.getUomId());

		for (final FlatrateDataEntryDetail detail : entry.getDetails())
		{
			final I_C_Flatrate_DataEntry_Detail detailRecord = loadOrNew(detail.getId(), I_C_Flatrate_DataEntry_Detail.class);
			detailRecord.setC_Flatrate_DataEntry_ID(entry.getId().getRepoId());
			detailRecord.setC_BPartner_Department_ID(BPartnerDepartmentId.toRepoId(detail.getBPartnerDepartment().getId()));
			detailRecord.setM_AttributeSetInstance_ID(AttributeSetInstanceId.toRepoId(detail.getAsiId()));

			final Quantity quantity = detail.getQuantity();
			detailRecord.setQty_Reported(Quantitys.toBigDecimalOrNull(quantity));
			if (quantity != null)
			{
				detailRecord.setC_UOM_ID(quantity.getUomId().getRepoId());
				qtySum = qtySum.add(quantity);
			}
			else
			{
				detailRecord.setC_UOM_ID(entryRecord.getC_UOM_ID());
			}
			if (detailRecord.getSeqNo() <= 0)
			{
				detailRecord.setSeqNo(maxSeqNo);
				maxSeqNo += 10;
			}

			saveRecord(detailRecord);

			final FlatrateDataEntryDetailId detailId = FlatrateDataEntryDetailId.ofRepoId(entry.getId(), detailRecord.getC_Flatrate_DataEntry_Detail_ID());
			result.detail(detail.withId(detailId));
		}

		entryRecord.setQty_Reported(qtySum.toBigDecimal());
		saveRecord(entryRecord);

		return result.build();
	}
}
