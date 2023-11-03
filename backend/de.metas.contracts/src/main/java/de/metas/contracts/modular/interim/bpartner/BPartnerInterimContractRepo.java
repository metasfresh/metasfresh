/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.interim.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.model.I_C_BPartner_InterimContract;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class BPartnerInterimContractRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public BPartnerInterimContract getById(@NonNull final BPartnerInterimContractId bPartnerInterimContractId)
	{
		final I_C_BPartner_InterimContract record = InterfaceWrapperHelper.load(bPartnerInterimContractId.getRepoId(), I_C_BPartner_InterimContract.class);
		return of(record);
	}

	@NonNull
	public Stream<BPartnerInterimContract> getByRequest(@NonNull final BPartnerInterimContractUpsertRequest request)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_InterimContract.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_InterimContract.COLUMNNAME_C_BPartner_ID, request.getBPartnerId())
				.addEqualsFilter(I_C_BPartner_InterimContract.COLUMNNAME_C_Harvesting_Calendar_ID, request.getYearAndCalendarId().calendarId())
				.addEqualsFilter(I_C_BPartner_InterimContract.COLUMNNAME_Harvesting_Year_ID, request.getYearAndCalendarId().yearId())
				.create()
				.iterateAndStream()
				.map(BPartnerInterimContractRepo::of);
	}

	@NonNull
	public BPartnerInterimContract create(@NonNull final BPartnerInterimContractUpsertRequest request)
	{
		final I_C_BPartner_InterimContract record = InterfaceWrapperHelper.newInstance(I_C_BPartner_InterimContract.class);

		record.setC_BPartner_ID(BPartnerId.toRepoId(request.getBPartnerId()));
		record.setC_Harvesting_Calendar_ID(CalendarId.toRepoId(request.getYearAndCalendarId().calendarId()));
		record.setHarvesting_Year_ID(YearId.toRepoId(request.getYearAndCalendarId().yearId()));
		record.setIsInterimContract(request.getIsInterimContract());

		saveRecord(record);
		return of(record);
	}

	@NonNull
	public BPartnerInterimContract update(@NonNull final BPartnerInterimContract bPartnerInterimContract)
	{
		final I_C_BPartner_InterimContract existingRecord = InterfaceWrapperHelper.load(bPartnerInterimContract.getId(), I_C_BPartner_InterimContract.class);

		existingRecord.setIsInterimContract(bPartnerInterimContract.getIsInterimContract());

		saveRecord(existingRecord);
		return of(existingRecord);
	}

	@NonNull
	private static BPartnerInterimContract of(@NonNull final I_C_BPartner_InterimContract record)
	{
		return BPartnerInterimContract.builder()
				.id(BPartnerInterimContractId.ofRepoId(record.getC_BPartner_InterimContract_ID()))
				.bPartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.isInterimContract(record.isInterimContract())
				.yearAndCalendarId(YearAndCalendarId.ofRepoId(YearId.ofRepoId(record.getHarvesting_Year_ID()), CalendarId.ofRepoId(record.getC_Harvesting_Calendar_ID())))
				.build();
	}
}
