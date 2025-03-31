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

package de.metas.contracts.modular.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractComputingMethodHandlerRegistry;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService;
import de.metas.contracts.modular.interim.invoice.service.impl.InterimFlatrateTermService;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsRepository;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutLineQuery;
import de.metas.inout.InOutQuery;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.VOIDED;

@Interceptor(I_C_Flatrate_Term.class)
@Component
@RequiredArgsConstructor
public class C_Flatrate_Term
{
	@NonNull private final BPartnerInterimContractService bPartnerInterimContractService;
	@NonNull private final ModularContractService modularContractService;
	@NonNull private final ModularContractSettingsRepository modularContractSettingsRepository;
	@NonNull private final ModularContractPriceService modularContractPriceService;
	@NonNull private final InterimFlatrateTermService interimFlatrateTermService;
	@NonNull private final ModularContractComputingMethodHandlerRegistry computingMethodHandlerRegistry;

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IInOutBL inoutBL = Services.get(IInOutBL.class);

	@NonNull private final static String SYS_CONFIG_INTERIM_CONTRACT_AUTO_CREATE = "de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete";

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createInterimContractIfNeeded(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		if (!sysConfigBL.getBooleanValue(SYS_CONFIG_INTERIM_CONTRACT_AUTO_CREATE, true))
		{
			return;
		}

		if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isModularContractType()
				|| !bPartnerInterimContractService.isBpartnerInterimInvoice(flatrateTermRecord))
		{
			return;
		}

		final ModularContractSettings settings = modularContractSettingsRepository.getByFlatrateTermId(FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID()));
		if (!settings.getSoTrx().isPurchase())
		{
			return;
		}

		if (!settings.contains(ComputingMethodType.INTERIM_CONTRACT))
		{
			return;
		}

		interimFlatrateTermService.create(flatrateTermRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onInterimContractComplete(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isInterimContractType())
		{
			return;
		}

		modularContractService.scheduleLogCreation(DocStatusChangedEvent.builder()
				.tableRecordReference(TableRecordReference.of(flatrateTermRecord))
				.modelAction(COMPLETED)
				.userInChargeId(Env.getLoggedUserId())
				.build());

		createMissingInterimReceiptLogs(flatrateTermRecord);
	}

	private void createMissingInterimReceiptLogs(@NonNull final I_C_Flatrate_Term interimContract)
	{
		inoutBL.streamLines(
						InOutLineQuery.builder()
								.headerQuery(InOutQuery.builder()
										.docStatus(DocStatus.Completed)
										.movementDateFrom(interimContract.getStartDate().toInstant())
										.movementDateTo(Check.assumeNotNull(interimContract.getEndDate(), "End Date shouldn't be null").toInstant())
										.build())
								.flatrateTermId(interimContract.getModular_Flatrate_Term_ID())
								.build())
				.forEach(inoutLine -> modularContractService.scheduleLogCreation(DocStatusChangedEvent.builder()
						.tableRecordReference(TableRecordReference.of(inoutLine))
						.modelAction(COMPLETED)
						.userInChargeId(Env.getLoggedUserId())
						.logEntryContractTypes(ImmutableSet.of(LogEntryContractType.INTERIM))
						.build())
				);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onModularContractComplete(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isModularContractType())
		{
			return;
		}

		modularContractService.scheduleLogCreation(DocStatusChangedEvent.builder()
				.tableRecordReference(TableRecordReference.of(flatrateTermRecord))
				.modelAction(COMPLETED)
				.userInChargeId(Env.getLoggedUserId())
				.build()
		);

	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void createInterimContractSpecificPrices(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		final TypeConditions typeConditions = TypeConditions.ofCode(flatrateTermRecord.getType_Conditions());
		if (!typeConditions.isInterimContractType())
		{
			return;
		}
		modularContractPriceService.createInterimContractSpecificPricesFor(flatrateTermRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void createModularContractSpecificPrices(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		final TypeConditions typeConditions = TypeConditions.ofCode(flatrateTermRecord.getType_Conditions());
		if (!typeConditions.isModularContractType())
		{
			return;
		}

		modularContractPriceService.createModularContractSpecificPricesFor(flatrateTermRecord, computingMethodHandlerRegistry);
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_C_Flatrate_Term.COLUMNNAME_ContractStatus)
	public void onModularOrInterimContractVoid(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isModularOrInterim())
		{
			return;
		}

		Check.assumeNotNull(flatrateTermRecord.getContractStatus(), "Contract status should be set");
		if (FlatrateTermStatus.ofCode(flatrateTermRecord.getContractStatus()).isVoided())
		{
			modularContractService.scheduleLogCreation(DocStatusChangedEvent.builder()
					.tableRecordReference(TableRecordReference.of(flatrateTermRecord))
					.modelAction(VOIDED)
					.userInChargeId(Env.getLoggedUserId())
					.build());
		}
	}
}
