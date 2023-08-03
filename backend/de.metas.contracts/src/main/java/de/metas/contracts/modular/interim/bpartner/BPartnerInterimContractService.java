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
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermRequest;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BPartnerInterimContractService
{
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

	public void upsert(@NonNull final BPartnerInterimContractUpsertRequest request)
	{
		final List<BPartnerInterimContract> existingBPartnerInterimContract = bPartnerInterimContractRepo.getByRequest(request)
				.collect(ImmutableList.toImmutableList());

		if (existingBPartnerInterimContract.isEmpty())
		{
			final ModularFlatrateTermRequest modularContractsLookup = ModularFlatrateTermRequest.builder()
					.bPartnerId(request.getBPartnerId())
					.calendarId(request.getYearAndCalendarId().calendarId())
					.yearId(request.getYearAndCalendarId().yearId())
					.typeConditions(TypeConditions.MODULAR_CONTRACT)
					.build();

			flatrateBL.streamModularFlatrateTermsByQuery(modularContractsLookup)
					.map(modularContract -> {
						final BPartnerId contractBPartnerId = BPartnerId.ofRepoId(modularContract.getBill_BPartner_ID());

						final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(FlatrateTermId.ofRepoId(modularContract.getC_Flatrate_Term_ID()));
						Check.assumeNotNull(modularContractSettings != null, "Modular contract settings should not be null at this stage");

						return BPartnerInterimContractUpsertRequest.builder()
								.yearAndCalendarId(modularContractSettings.getYearAndCalendarId())
								.isInterimContract(request.getIsInterimContract())
								.bPartnerId(contractBPartnerId)
								.build();
					})
					.collect(ImmutableSet.toImmutableSet())
					.forEach(bPartnerInterimContractRepo::create);

			return;
		}

		final ModularFlatrateTermRequest interimContractsLookup = ModularFlatrateTermRequest.builder()
				.bPartnerId(request.getBPartnerId())
				.calendarId(request.getYearAndCalendarId().calendarId())
				.yearId(request.getYearAndCalendarId().yearId())
				.typeConditions(TypeConditions.INTERIM_INVOICE)
				.build();

		final boolean hasOnGoingInterimContracts = flatrateBL.streamModularFlatrateTermsByQuery(interimContractsLookup)
				.collect(ImmutableList.toImmutableList())
				.size() > 0;

		if (hasOnGoingInterimContracts)
		{
			throw new AdempiereException("The partner already has an interim contract created for this harvesting year!");
		}

		existingBPartnerInterimContract.stream()
				.map(BPartnerInterimContract::toBuilder)
				.map(builder -> builder.isInterimContract(request.getIsInterimContract()).build())
				.forEach(bPartnerInterimContractRepo::update);
	}
}
