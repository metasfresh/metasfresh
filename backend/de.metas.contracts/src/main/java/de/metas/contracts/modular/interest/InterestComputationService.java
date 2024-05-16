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

package de.metas.contracts.modular.interest;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.interest.log.CreateModularLogInterestRequest;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.interest.run.CreateInterestRunRequest;
import de.metas.contracts.modular.interest.run.InterestRunId;
import de.metas.contracts.modular.interest.run.InterestRunRepository;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InterestComputationService
{
	@NonNull private final ModularLogInterestRepository interestRepository;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final MoneyService moneyService;
	@NonNull private final ModularContractService modularContractService;
	@NonNull private final InterestRunRepository interestRunRepository;

	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public void distributeInterestAndBonus(@NonNull final InterestBonusComputationRequest request)
	{
		final InterestRunId runId = createInterestRunRecord(request);
		final PInstanceId currentLogSelection = modularContractLogService.getSelection(request.getLockOwner());
		if (currentLogSelection == null)
		{
			Loggables.addLog("Nothing to do, there are no logs selected!");
			return;
		}

		deletePreviousProgress(currentLogSelection);

		final InterestComputationRequest interestComputationRequest = request.getInterestDistributionRequest(runId);

		computeAndDistributeInterest(interestComputationRequest);

		computeAndDistributeBonusRecords(interestComputationRequest.toBuilder()
												 .bonusComputationDetails(request.getBonusComputationDetails())
												 .build());
	}

	private void computeAndDistributeInterest(@NonNull final InterestComputationRequest request)
	{
		Check.assume(request.getComputingMethodType() == ComputingMethodType.AddValueOnInterim,
					 "Interest can only be distributed for" + ComputingMethodType.AddValueOnInterim);

		final InitialInterestAllocationResult result = createInterestRecords(request);
		final BigDecimal interestDistributionPercent = request.getInterestToDistribute().toBigDecimal()
				.divide(result.getTotalInterestScore(), result.getTotalInterestScore().scale(), RoundingMode.HALF_UP);
		distributeInterest(request, interestDistributionPercent);
	}

	private void deletePreviousProgress(@NonNull final PInstanceId logSelectionId)
	{
		final ModularLogInterestRepository.LogInterestQuery query = ModularLogInterestRepository.LogInterestQuery.builder()
				.modularLogSelection(logSelectionId)
				.build();

		interestRepository.deleteByQuery(query);
	}

	@NonNull
	private InitialInterestAllocationResult createInterestRecords(@NonNull final InterestComputationRequest request)
	{
		final BigDecimal totalInterestScore = getModularContractIds(request)
				.stream()
				.map(contractId -> createInterestRecords(request, contractId))
				.map(InitialInterestAllocationResult::getTotalInterestScore)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return InitialInterestAllocationResult.of(totalInterestScore);
	}

	@NonNull
	private InitialInterestAllocationResult createInterestRecords(
			@NonNull final InterestComputationRequest request,
			@NonNull final FlatrateTermId contractId)
	{
		final Iterator<ModularContractLogEntry> shippingNotificationIterator = streamShippingNotificationLogEntries(request, contractId).iterator();
		final Iterator<ModularContractLogEntry> interimInvoiceIterator = streamInterimInvoiceLogEntries(contractId, request).iterator();
		final ModularContractSettings settings = modularContractService.getModularSettingsForContract(contractId);

		BigDecimal totalInterestScore = BigDecimal.ZERO;

		InvoiceAllocations.AllocationItem currentShippingNotification = null;
		while (interimInvoiceIterator.hasNext())
		{
			final InvoiceAllocations currentInvoiceAllocations = initInvoiceAllocations(request,
																						settings.getAdditionalInterestDays(),
																						interimInvoiceIterator.next());

			while (currentInvoiceAllocations.openAmountSignum() > 0 && shippingNotificationIterator.hasNext())
			{
				while (shippingNotificationIterator.hasNext() && !currentInvoiceAllocations.canAllocate(currentShippingNotification))
				{
					final boolean wasCreatedBeforeInvoice = currentShippingNotification != null
							&& currentInvoiceAllocations.isInvoiceCreatedAfter(currentShippingNotification);

					if (wasCreatedBeforeInvoice)
					{
						saveSubtractValueInterestRecords(request, settings.getBonusInterestRate(), currentShippingNotification);
					}

					currentShippingNotification = initAllocationItem(request.getInterestToDistribute().getCurrencyId(),
																	 shippingNotificationIterator.next());
				}

				if (currentInvoiceAllocations.canAllocate(currentShippingNotification))
				{
					currentShippingNotification = currentInvoiceAllocations.allocate(currentShippingNotification);
				}
			}

			saveAddedValueInterestRecords(currentInvoiceAllocations);
			totalInterestScore = totalInterestScore.add(currentInvoiceAllocations.getAllocatedInterestScore());
		}

		saveSubtractedValueInterestRecords(request,
										   settings.getBonusInterestRate(),
										   shippingNotificationIterator,
										   currentShippingNotification);

		return InitialInterestAllocationResult.of(totalInterestScore);
	}

	private void saveSubtractedValueInterestRecords(
			@NonNull final InterestComputationRequest request,
			@NonNull final BigDecimal bonusInterestRate,
			@NonNull final Iterator<ModularContractLogEntry> shippingNotificationIterator,
			@Nullable final InvoiceAllocations.AllocationItem currentShippingNotification)
	{
		if (request.getBonusComputationDetails() == null)
		{
			return;
		}

		if (currentShippingNotification != null && currentShippingNotification.openAmountSignum() > 0)
		{
			saveSubtractValueInterestRecords(request, bonusInterestRate, currentShippingNotification);
		}

		while (shippingNotificationIterator.hasNext())
		{
			saveSubtractValueInterestRecords(request, bonusInterestRate, initAllocationItem(request.getInterestToDistribute().getCurrencyId(),
																							shippingNotificationIterator.next()));
		}
	}

	@NonNull
	private ImmutableSet<FlatrateTermId> getModularContractIds(
			@NonNull final InterestComputationRequest request)
	{
		return modularContractLogService.getModularContractIds(ModularContractLogQuery.builder()
																	   .computingMethodType(request.getComputingMethodType())
																	   .invoicingGroupId(request.getInvoicingGroupId())
																	   .lockOwner(request.getLockOwner())
																	   .processed(false)
																	   .billable(true)
																	   .onlyIfAmountIsSet(true)
																	   .logEntryDocumentType(LogEntryDocumentType.INTERIM_INVOICE)
																	   .build());
	}

	private void saveAddedValueInterestRecords(@NonNull final InvoiceAllocations currentInvoiceAllocations)
	{
		currentInvoiceAllocations.getAllocatedShippingNotifications().forEach(interestRepository::create);
	}

	private void saveSubtractValueInterestRecords(
			@NonNull final InterestComputationRequest request,
			@NonNull final BigDecimal bonusInterestRate,
			@NonNull final InvoiceAllocations.AllocationItem shippingNotification)
	{
		if (request.getBonusComputationDetails() == null)
		{
			return;
		}

		final int interestDays = request.getBonusComputationDetails().getBonusInterestDays();

		final BigDecimal bonusAmountAsBD = shippingNotification.getOpenAmount()
				.toBigDecimal()
				.multiply(bonusInterestRate)
				.multiply(NumberUtils.asBigDecimal(interestDays))
				.divide(NumberUtils.asBigDecimal(360), RoundingMode.HALF_UP);

		final CreateModularLogInterestRequest createInterestRequest = CreateModularLogInterestRequest.builder()
				.interestRunId(request.getInterestRunId())
				.shippingNotificationLogId(shippingNotification.getShippingNotificationEntry().getId())
				.allocatedAmt(shippingNotification.getOpenAmount())
				.interestDays(request.getBonusComputationDetails().getBonusInterestDays())
				.finalInterest(Money.of(bonusAmountAsBD, shippingNotification.getOpenAmount().getCurrencyId()))
				.build();

		interestRepository.create(createInterestRequest);
	}

	@NonNull
	private Stream<ModularContractLogEntry> streamShippingNotificationLogEntries(
			@NonNull final InterestComputationRequest request,
			@Nullable final FlatrateTermId onlyForContractId)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.computingMethodType(request.getComputingMethodType())
				.flatrateTermId(onlyForContractId)
				.invoicingGroupId(request.getInvoicingGroupId())
				.billable(true)
				.processed(false)
				.lockOwner(request.getLockOwner())
				.logEntryDocumentType(LogEntryDocumentType.SHIPPING_NOTIFICATION)
				.orderBy(ModularContractLogQuery.OrderBy.TRANSACTION_DATE_ASC)
				.build();

		return modularContractLogService.streamModularContractLogEntries(query);
	}

	@NonNull
	private Stream<ModularContractLogEntry> streamInterimInvoiceLogEntries(
			@NonNull final FlatrateTermId contractId,
			@NonNull final InterestComputationRequest request)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.computingMethodType(request.getComputingMethodType())
				.flatrateTermId(contractId)
				.invoicingGroupId(request.getInvoicingGroupId())
				.billable(true)
				.processed(false)
				.onlyIfAmountIsSet(true)
				.lockOwner(request.getLockOwner())
				.logEntryDocumentType(LogEntryDocumentType.INTERIM_INVOICE)
				.orderBy(ModularContractLogQuery.OrderBy.TRANSACTION_DATE_ASC)
				.build();

		return modularContractLogService.streamModularContractLogEntries(query);
	}

	private void distributeInterest(
			@NonNull final InterestComputationRequest request,
			@NonNull final BigDecimal interestDistributionPercent)
	{
		final FlatrateTermId onlyForContractId = null; // we want all contracts involved
		streamShippingNotificationLogEntries(request, onlyForContractId)
				.forEach(logEntry -> distributeInterest(request, interestDistributionPercent, logEntry));
	}

	private void distributeInterest(
			@NonNull final InterestComputationRequest request,
			@NonNull final BigDecimal interestDistributionPercent,
			@NonNull final ModularContractLogEntry shippingNotification)
	{
		interestRepository.getForShippingNotificationLogId(request.getInterestRunId(), shippingNotification.getId())
				.stream()
				.map(interestLog -> interestLog.withComputedFinalInterest(interestDistributionPercent, request.getInterestToDistribute().getCurrencyId()))
				.forEach(interestRepository::save);
	}

	@NonNull
	private InvoiceAllocations initInvoiceAllocations(
			@NonNull final InterestComputationRequest request,
			final int additionalInterestDays,
			@NonNull final ModularContractLogEntry invoice)
	{
		Check.assume(LogEntryDocumentType.INTERIM_INVOICE == invoice.getDocumentType(),
					 "Expecting DocumentType = " + LogEntryDocumentType.INTERIM_INVOICE +
							 " but got " + invoice.getDocumentType() + "! LogId=" + invoice.getId());
		Check.assumeNotNull(invoice.getAmount(), "Invoices with no amount should've been skipped already! LogId="
				+ invoice.getId());

		final Instant conversionDate = invoice.getTransactionDate().toInstant(orgDAO::getTimeZone);
		final CurrencyConversionContext context = currencyBL.createCurrencyConversionContext(conversionDate,
																							 invoice.getClientAndOrgId().getClientId(),
																							 invoice.getClientAndOrgId().getOrgId());

		final CurrencyId targetCurrencyId = request.getInterestToDistribute().getCurrencyId();
		final Money openAmountInTargetCurr = moneyService.convertMoneyToCurrency(invoice.getAmount(),
																				 targetCurrencyId,
																				 context);

		return InvoiceAllocations.builder()
				.interestRunId(request.getInterestRunId())
				.additionalInterestDays(additionalInterestDays)
				.orgDAO(orgDAO)
				.invoiceEntry(invoice)
				.openAmount(openAmountInTargetCurr)
				.build();
	}

	@NonNull
	private InvoiceAllocations.AllocationItem initAllocationItem(
			@NonNull final CurrencyId targetCurrencyId,
			@NonNull final ModularContractLogEntry shippingNotification)
	{
		Check.assume(LogEntryDocumentType.SHIPPING_NOTIFICATION == shippingNotification.getDocumentType(),
					 "Expecting DocumentType = " + LogEntryDocumentType.SHIPPING_NOTIFICATION +
							 " but got " + shippingNotification.getDocumentType() + "! LogId=" + shippingNotification.getId());

		final Money shippingNotificationAmount = Optional
				.ofNullable(shippingNotification.getAmount())
				.orElse(Money.zero(targetCurrencyId));

		final Instant conversionDate = shippingNotification.getTransactionDate().toInstant(orgDAO::getTimeZone);
		final CurrencyConversionContext context = currencyBL.createCurrencyConversionContext(conversionDate,
																							 shippingNotification.getClientAndOrgId().getClientId(),
																							 shippingNotification.getClientAndOrgId().getOrgId());

		final Money shippingNotificationAmountInTargetCurr = moneyService.convertMoneyToCurrency(shippingNotificationAmount,
																								 targetCurrencyId,
																								 context);

		return InvoiceAllocations.AllocationItem.builder()
				.shippingNotificationEntry(shippingNotification)
				.openAmount(shippingNotificationAmountInTargetCurr)
				.build();
	}

	@NonNull
	private InterestRunId createInterestRunRecord(@NonNull final InterestBonusComputationRequest request)
	{
		final CreateInterestRunRequest interestRunRequest = CreateInterestRunRequest.builder()
				.interestToDistribute(request.getInterestToDistribute())
				.billingDate(request.getBillingDate())
				.interimDate(request.getInterimDate())
				.invoicingGroupId(request.getInvoicingGroupId())
				.build();

		return interestRunRepository.create(interestRunRequest);
	}

	private void computeAndDistributeBonusRecords(@NonNull final InterestComputationRequest request)
	{
		Check.assume(request.getComputingMethodType() == ComputingMethodType.SubtractValueOnInterim,
					 "Bonus can only be distributed for" + ComputingMethodType.SubtractValueOnInterim);
		createInterestRecords(request);
	}

	@Value(staticConstructor = "of")
	private static class InitialInterestAllocationResult
	{
		@NonNull
		BigDecimal totalInterestScore;
	}
}
