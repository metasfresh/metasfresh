package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Contract status, contract date and master start date of {@code CompensationGroup}-type contract terms.
 *
 * @see ContractCompensationGroupTermStatusRule
 */
@Service
@RequiredArgsConstructor
public class ContractCompensationGroupTermService
{
	private static final ImmutableSet<FlatrateTermStatus> STATUSES_CHANGED_DAILY = ImmutableSet.of(FlatrateTermStatus.Waiting, FlatrateTermStatus.Running);

	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final ContractCompensationGroupTermRepository termRepository;

	public static ContractCompensationGroupTermService newInstanceForUnitTesting()
	{
		return new ContractCompensationGroupTermService(new ContractCompensationGroupTermRepository());
	}

	/**
	 * Fills the values the given term is about to be completed with, each only if it is still empty:
	 * <ul>
	 *     <li>{@code ContractStatus} per {@link ContractCompensationGroupTermStatusRule#computeStatusOnComplete}</li>
	 *     <li>{@code DateContracted}: the day the term was created</li>
	 *     <li>{@code MasterStartDate}: the predecessor's master start date if the term is the follow-up of an extended term
	 *     and that one has a master start date, else the term's own start date</li>
	 * </ul>
	 * Does not save the term.
	 */
	public void setDefaultsBeforeComplete(@NonNull final I_C_Flatrate_Term term)
	{
		final LocalDate today = SystemTime.asLocalDate();
		ContractCompensationGroupTermStatusRule.computeStatusOnComplete(FlatrateTermStatus.ofNullableCode(term.getContractStatus()), TimeUtil.asLocalDate(term.getStartDate()), today)
				.ifPresent(status -> term.setContractStatus(status.getCode()));

		if (term.getDateContracted() == null)
		{
			term.setDateContracted(TimeUtil.truncToDay(term.getCreated()));
		}

		if (term.getMasterStartDate() == null)
		{
			final Timestamp predecessorMasterStartDate = getPredecessorMasterStartDate(term);
			term.setMasterStartDate(predecessorMasterStartDate != null ? predecessorMasterStartDate : term.getStartDate());
		}
	}

	@Nullable
	private Timestamp getPredecessorMasterStartDate(@NonNull final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Term predecessor = flatrateDAO.retrieveAncestorFlatrateTerm(term);
		return predecessor != null ? predecessor.getMasterStartDate() : null;
	}

	/**
	 * Applies {@link ContractCompensationGroupTermStatusRule#computeStatusUpdate} for today to every completed
	 * compensation-group term of the context client that is "not yet started" or "running".
	 *
	 * @return the number of terms whose contract status was changed
	 */
	public int updateContractStatusOfCompletedTerms()
	{
		final LocalDate today = SystemTime.asLocalDate();

		int updatedCount = 0;
		for (final I_C_Flatrate_Term term : termRepository.getCompletedTermsWithContractStatus(STATUSES_CHANGED_DAILY))
		{
			final Optional<FlatrateTermStatus> newStatus = ContractCompensationGroupTermStatusRule.computeStatusUpdate(
					FlatrateTermStatus.ofNullableCode(term.getContractStatus()),
					TimeUtil.asLocalDate(term.getStartDate()),
					TimeUtil.asLocalDate(term.getEndDate()),
					term.getC_FlatrateTerm_Next_ID() > 0,
					today);
			if (newStatus.isPresent())
			{
				Loggables.addLog("C_Flatrate_Term_ID={}: ContractStatus {} -> {}", term.getC_Flatrate_Term_ID(), term.getContractStatus(), newStatus.get().getCode());
				termRepository.saveContractStatus(term, newStatus.get());
				updatedCount++;
			}
		}
		return updatedCount;
	}
}
