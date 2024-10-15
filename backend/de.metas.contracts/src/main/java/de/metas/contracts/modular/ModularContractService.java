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

package de.metas.contracts.modular;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.ModularContractSettingsId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ContractSpecificScalePriceRequest;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsService;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.contracts.modular.workpackage.ProcessModularLogsEnqueuer;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductPrice;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Invoice;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.metas.contracts.modular.ComputingMethodType.AVERAGE_CONTRACT_SPECIFIC_PRICE_METHODS;

@Service
@RequiredArgsConstructor
public class ModularContractService
{
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@NonNull private final ModularContractComputingMethodHandlerRegistry modularContractHandlers;
	@NonNull private final ProcessModularLogsEnqueuer processLogsEnqueuer;
	@NonNull private final ComputingMethodService computingMethodService;
	@NonNull private final ModularContractPriceRepository modularContractPriceRepository;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final ModularContractSettingsService modularContractSettingsService;

	public static ModularContractService newInstanceForJUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ModularContractService(
			ModularContractComputingMethodHandlerRegistry.newInstanceForJUnitTesting(),
			ProcessModularLogsEnqueuer.newInstanceForJUnitTesting(),
			ComputingMethodService.newInstanceForJUnitTesting(),
			new ModularContractPriceRepository(),
			ModularContractLogService.newInstanceForJUnitTesting(),
			ModularContractSettingsService.newInstanceForJUnitTesting()
		);
	}

	public void scheduleLogCreation(@NonNull final DocStatusChangedEvent event)
	{
		boolean isRequestValidated = false;

		for (final LogEntryContractType logEntryContractType : event.getLogEntryContractTypes())
		{
			for (final IComputingMethodHandler handler : modularContractHandlers.getApplicableHandlersFor(event.getTableRecordReference(), logEntryContractType))
			{
				final ComputingMethodType computingMethodType = handler.getComputingMethodType();


				final List<FlatrateTermId> contractIds = handler.streamContractIds(event.getTableRecordReference()).toList();
				Check.assume(contractIds.size() <= 1, "Maximum 1 Contract should be found");
				if(!contractIds.isEmpty())
				{
					//If in future Iterations it's still max 1 we should replace it with Optional or Nullable
					final FlatrateTermId contractId = contractIds.get(0);
					if (!isApplicableContract(event.getTableRecordReference(), handler, contractId))
					{
						continue;
					}

					if (!isRequestValidated)
					{
						computingMethodService.validateAction(event.getTableRecordReference(), event.getModelAction());
						isRequestValidated = true;
					}

					processLogsEnqueuer.enqueueAfterCommit(ProcessModularLogsEnqueuer.EnqueueRequest.builder()
							.recordReference(event.getTableRecordReference())
							.action(event.getModelAction())
							.userInChargeId(event.getUserInChargeId())
							.logEntryContractType(logEntryContractType)
							.computingMethodType(computingMethodType)
							.flatrateTermId(contractId)
							.build());
				}
			}
		}
	}

	private boolean isApplicableContract(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final IComputingMethodHandler handler,
			@NonNull final FlatrateTermId contractId)
	{
		if (!isModularOrInterimContract(contractId))
		{
			return false;
		}

		final ModularContractSettings settings = modularContractSettingsService.getByFlatrateTermIdOrNull(contractId);
		if (settings == null || !settings.isMatching(handler.getComputingMethodType()))
		{
			return false;
		}

		return handler.isApplicableForSettings(tableRecordReference, settings);
	}

	private boolean isModularOrInterimContract(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(flatrateTermId);
		final TypeConditions typeConditions = TypeConditions.ofCode(flatrateTermRecord.getType_Conditions());
		return typeConditions.isModularOrInterim();
	}

	public PricingSystemId getPricingSystemId(@NonNull final FlatrateTermId flatrateTermId)
	{
		final ModularContractSettings modularContractSettings = modularContractSettingsService.getByFlatrateTermId(flatrateTermId);

		return modularContractSettings.getPricingSystemId();
	}

	@NonNull
	public TaxCategoryId getContractSpecificTaxCategoryId(@NonNull final ContractSpecificPriceRequest contractSpecificPriceRequest)
	{
		return modularContractPriceRepository.retrieveOptionalContractSpecificTaxCategory(contractSpecificPriceRequest)
				// don't have a contract specific price (e.g: Receipt), default to the contract's tax category.
				.orElseGet(() -> TaxCategoryId.ofRepoId(flatrateBL.getById(contractSpecificPriceRequest.getFlatrateTermId()).getC_TaxCategory_ID()));
	}

	@NonNull
	public ProductPrice getContractSpecificPrice(@NonNull final ContractSpecificPriceRequest contractSpecificPriceRequest)
	{
		return modularContractPriceRepository.retrievePriceForProductAndContract(contractSpecificPriceRequest).getProductPrice();
	}

	@Nullable
	public ProductPrice getContractSpecificScalePrice(@NonNull final ContractSpecificScalePriceRequest contractSpecificScalePriceRequest)
	{
		final ModCntrSpecificPrice modCntrSpecificPrice = modularContractPriceRepository.retrieveScalePriceForProductAndContract(contractSpecificScalePriceRequest);

		if (modCntrSpecificPrice == null)
		{
			return null;
		}

		return ProductPrice.builder()
				.productId(modCntrSpecificPrice.productId())
				.money(modCntrSpecificPrice.amount())
				.uomId(modCntrSpecificPrice.uomId())
				.build();
	}

	@NonNull
	public ImmutableMap<FlatrateTermId, ModularContractSettings> getSettingsByContractIds(@NonNull final ImmutableSet<FlatrateTermId> contractIds)
	{
		return modularContractSettingsService.getOrLoadBy(contractIds);
	}

	@NonNull
	public ModularContractSettings getModularSettingsForContract(@NonNull final FlatrateTermId contractId)
	{
		final ModularContractSettings settings = getSettingsByContractIds(ImmutableSet.of(contractId))
				.get(contractId);

		if (settings == null)
		{
			throw new AdempiereException("No ModularContractSettings found for contractId=" + contractId);
		}

		return settings;
	}

	@NonNull
	public ModuleConfig getByModuleId(@NonNull final ModularContractModuleId modularContractModuleId)
	{
		return modularContractSettingsService.getByModuleId(modularContractModuleId);
	}

	public void afterInvoiceReverse(@NonNull final I_C_Invoice invoiceRecord, @NonNull final ModularContractLogHandlerRegistry logHandlerRegistry)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
		modularContractLogService.unprocessModularContractLogs(invoiceId, DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID()));

		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId.ofRepoIdOrNull(invoiceRecord.getModCntr_Settings_ID());
		if(modularContractSettingsId == null)
		{
			return;
		}
		final FlatrateTermId flatrateTermId = Check.assumePresent(flatrateBL.getIdByInvoiceId(invoiceId), "FlatrateTermId should be present");

		modularContractSettingsService.getById(modularContractSettingsId).getModuleConfigs().stream()
				.filter(config -> config.isMatchingAnyOf(AVERAGE_CONTRACT_SPECIFIC_PRICE_METHODS))
				.forEach(config -> modularContractLogService.updateAverageContractSpecificPrice(config, flatrateTermId, logHandlerRegistry));
	}

}
