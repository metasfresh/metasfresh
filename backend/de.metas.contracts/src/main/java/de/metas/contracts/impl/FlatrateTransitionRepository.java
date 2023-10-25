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

package de.metas.contracts.impl;

import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.ICalendarDAO;
import de.metas.contracts.FlatrateTransitionId;
import de.metas.contracts.TermDurationUnit;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class FlatrateTransitionRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);

	@NonNull
	public FlatrateTransition getById(@NonNull final FlatrateTransitionId flatrateTransitionId)
	{
		final I_C_Flatrate_Transition flatrateTransitionRecord = InterfaceWrapperHelper.load(flatrateTransitionId, I_C_Flatrate_Transition.class);

		if (flatrateTransitionRecord == null)
		{
			throw new AdempiereException("No record found for " + flatrateTransitionId);
		}

		return fromRecord(flatrateTransitionRecord);
	}

	@NonNull
	public FlatrateTransitionId getOrCreate(
			@NonNull final FlatrateTransitionQuery flatrateTransitionQuery,
			@NonNull final String namePrefix)
	{
		return Optional.ofNullable(getBy(flatrateTransitionQuery))
				.orElseGet(() -> create(flatrateTransitionQuery, namePrefix));
	}

	@NonNull
	private FlatrateTransitionId create(
			@NonNull final FlatrateTransitionQuery flatrateTransitionQuery,
			@NonNull final String namePrefix)
	{
		final CalendarId calendarId = flatrateTransitionQuery.getCalendarId();

		final I_C_Flatrate_Transition transitionRecord = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class);

		transitionRecord.setName(namePrefix + " " + calendarDAO.getName(calendarId));
		transitionRecord.setC_Calendar_Contract_ID(calendarId.getRepoId());
		transitionRecord.setEndsWithCalendarYear(flatrateTransitionQuery.getEndsWithCalendarYear());
		transitionRecord.setTermDuration(flatrateTransitionQuery.getTermDuration());
		transitionRecord.setTermDurationUnit(flatrateTransitionQuery.getTermDurationUnit().getCode());
		transitionRecord.setTermOfNotice(flatrateTransitionQuery.getTermOfNotice());
		transitionRecord.setTermOfNoticeUnit(flatrateTransitionQuery.getTermOfNoticeUnit().getCode());
		transitionRecord.setIsNotifyUserInCharge(flatrateTransitionQuery.getNotifyUserInCharge());
		transitionRecord.setDocStatus(DocStatus.Completed.getCode());

		saveRecord(transitionRecord);

		return FlatrateTransitionId.ofRepoId(transitionRecord.getC_Flatrate_Transition_ID());
	}

	@Nullable
	private FlatrateTransitionId getBy(@NonNull final FlatrateTransitionQuery flatrateTransitionQuery)
	{
		final I_C_Flatrate_Transition transitionRecord = queryBL.createQueryBuilder(I_C_Flatrate_Transition.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Transition.COLUMNNAME_C_Calendar_Contract_ID, flatrateTransitionQuery.getCalendarId())
				.addEqualsFilter(I_C_Flatrate_Transition.COLUMNNAME_EndsWithCalendarYear, flatrateTransitionQuery.getEndsWithCalendarYear())
				.addEqualsFilter(I_C_Flatrate_Transition.COLUMNNAME_TermDuration, flatrateTransitionQuery.getTermDuration())
				.addEqualsFilter(I_C_Flatrate_Transition.COLUMNNAME_TermDurationUnit, flatrateTransitionQuery.getTermDurationUnit().getCode())
				.addEqualsFilter(I_C_Flatrate_Transition.COLUMNNAME_TermOfNotice, flatrateTransitionQuery.getTermOfNotice())
				.addEqualsFilter(I_C_Flatrate_Transition.COLUMNNAME_TermOfNoticeUnit, flatrateTransitionQuery.getTermOfNoticeUnit().getCode())
				.addEqualsFilter(I_C_Flatrate_Transition.COLUMNNAME_IsNotifyUserInCharge, flatrateTransitionQuery.getNotifyUserInCharge())
				.create()
				.firstOnly(I_C_Flatrate_Transition.class);

		return Optional.ofNullable(transitionRecord)
				.map(I_C_Flatrate_Transition::getC_Flatrate_Transition_ID)
				.map(FlatrateTransitionId::ofRepoId)
				.orElse(null);
	}

	@NonNull
	private static FlatrateTransition fromRecord(@NonNull final I_C_Flatrate_Transition record)
	{
		return FlatrateTransition.builder()
				.id(FlatrateTransitionId.ofRepoId(record.getC_Flatrate_Transition_ID()))
				.calendarId(CalendarId.ofRepoId(record.getC_Calendar_Contract_ID()))
				.endsWithCalendarYear(record.isEndsWithCalendarYear())
				.termDuration(record.getTermDuration())
				.termDurationUnit(TermDurationUnit.ofCode(record.getTermDurationUnit()))
				.termOfNotice(record.getTermOfNotice())
				.termOfNoticeUnit(TermDurationUnit.ofCode(record.getTermOfNoticeUnit()))
				.notifyUserInCharge(record.isNotifyUserInCharge())
				.build();
	}
}
