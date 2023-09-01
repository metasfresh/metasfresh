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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BPartnerInterimContractService
{
	private static final AdMessageKey MSG_InterimContractExists = AdMessageKey.of("de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService.InterimContractExists");
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final BPartnerInterimContractRepo bPartnerInterimContractRepo;
	private final ModularContractSettingsDAO modularContractSettingsDAO;

	public BPartnerInterimContractService(
			@NonNull final BPartnerInterimContractRepo bPartnerInterimContractRepo,
			@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.bPartnerInterimContractRepo = bPartnerInterimContractRepo;
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@NonNull
	public BPartnerInterimContract getById(@NonNull final BPartnerInterimContractId bPartnerInterimContractId)
	{
		return bPartnerInterimContractRepo.getById(bPartnerInterimContractId);
	}

	public void upsert(@NonNull final BPartnerInterimContractUpsertRequest request)
	{
		final List<BPartnerInterimContract> existingBPartnerInterimContract = bPartnerInterimContractRepo.getByRequest(request)
				.collect(ImmutableList.toImmutableList());

		if (existingBPartnerInterimContract.isEmpty())
		{
			final BPartnerInterimContractUpsertRequest bPartnerInterimContractUpsertRequest = getbPartnerInterimContractUpsertRequest(request);
			bPartnerInterimContractRepo.create(bPartnerInterimContractUpsertRequest);
			return;
		}

		final ModularFlatrateTermQuery interimContractsQuery = ModularFlatrateTermQuery.builder()
				.bPartnerId(request.getBPartnerId())
				.calendarId(request.getYearAndCalendarId().calendarId())
				.yearId(request.getYearAndCalendarId().yearId())
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(TypeConditions.INTERIM_INVOICE)
				.build();

		final boolean hasOnGoingInterimContracts = flatrateBL.streamModularFlatrateTermsByQuery(interimContractsQuery)
				.findAny()
				.isPresent();

		if (hasOnGoingInterimContracts)
		{
			throw new AdempiereException(MSG_InterimContractExists);
		}

		existingBPartnerInterimContract.stream()
				.map(BPartnerInterimContract::toBuilder)
				.map(builder -> builder.isInterimContract(request.getIsInterimContract()).build())
				.forEach(bPartnerInterimContractRepo::update);
	}

	private BPartnerInterimContractUpsertRequest getbPartnerInterimContractUpsertRequest(final @NonNull BPartnerInterimContractUpsertRequest request)
	{
		return BPartnerInterimContractUpsertRequest.builder()
				.yearAndCalendarId(request.getYearAndCalendarId())
				.isInterimContract(request.getIsInterimContract())
				.bPartnerId(request.getBPartnerId())
				.build();
	}

	public boolean isBpartnerInterimInvoice(final I_C_Flatrate_Term modularFlatrateTermRecord)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoId(modularFlatrateTermRecord.getC_Flatrate_Conditions_ID());
		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateConditionsId(conditionsId);

		return bPartnerInterimContractRepo.getByRequest(BPartnerInterimContractUpsertRequest.builder()
																.bPartnerId(BPartnerId.ofRepoId(modularFlatrateTermRecord.getBill_BPartner_ID()))
																.yearAndCalendarId(modularContractSettings.getYearAndCalendarId())
																.isInterimContract(true) //isn't used as EqualsFilter
																.build()).anyMatch(BPartnerInterimContract::getIsInterimContract);
	}
}
