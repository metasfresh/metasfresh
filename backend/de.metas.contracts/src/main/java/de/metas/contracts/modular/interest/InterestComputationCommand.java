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
import de.metas.contracts.model.I_ModCntr_Interest;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.interest.log.CreateModularLogInterestRequest;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.interest.run.CreateInterestRunRequest;
import de.metas.contracts.modular.interest.run.InterestRunId;
import de.metas.contracts.modular.interest.run.InterestRunRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
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
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class InterestComputationCommand
{
	/**
	 * we consider a fiscal year as having 12 months of 30 days
	 */
	public static final int TOTAL_DAYS_OF_FISCAL_YEAR = 360;
	@NonNull private final ModularLogInterestRepository interestRepository;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final MoneyService moneyService;
	@NonNull private final ModularContractService modularContractService;
	@NonNull private final InterestRunRepository interestRunRepository;
	@NonNull private final InterestComputationNotificationsProducer notificationsProducer;
	@NonNull private final ICurrencyBL currencyBL;
	@NonNull private final IOrgDAO orgDAO;
	@NonNull private final IQueryBL queryBL;

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

		final InterestComputationRequest interestComputationRequest = getInterestDistributionRequest(request, runId);

		computeAndDistributeInterest(interestComputationRequest);

		computeAndDistributeBonus(interestComputationRequest.toBuilder()
										  .bonusComputationDetails(request.getBonusComputationDetails())
										  .build());

		final boolean anyRecordsCreatedForCurrentRun = queryBL.createQueryBuilder(I_ModCntr_Interest.class)
				.addEqualsFilter(I_ModCntr_Interest.COLUMNNAME_ModCntr_Interest_Run_ID, runId)
				.addNotEqualsFilter(I_ModCntr_Interest.COLUMNNAME_FinalInterest, 0)
				.create()
				.anyMatch();

		if (anyRecordsCreatedForCurrentRun)
		{
			notificationsProducer.notifyGenerated(runId, request.getInvoicingGroupId(), request.getUserId());
		}
	}

	private void computeAndDistributeInterest(@NonNull final InterestComputationRequest request)
	{
		Check.assume(request.getComputingMethodType() == ComputingMethodType.AddValueOnInterim,
					 "Interest can only be distributed for" + ComputingMethodType.AddValueOnInterim);

		distributeInterest(request, createInterestRecords(request));
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
		final ModularContractSettings settings = modularContractService.getModularSettingsForContract(contractId);

		final ModularContractLogEntriesList shippingNotificationLogs = streamShippingNotificationLogEntries(request, contractId);
		// TODO: interim invoice and shipping notification logs are no longer relevant for the Added Value on Interim
		BigDecimal totalInterestScore = BigDecimal.ZERO;
		final boolean interimInvoiceExists = interimInvoiceLogExists(contractId, request);

		if (!interimInvoiceExists)
		{
			for(final ModularContractLogEntry shippingNotificationLog: shippingNotificationLogs)
			{
				final @NonNull AllocationItem currentShippingNotification = initAllocationItem(request.getInterestToDistribute().getCurrencyId(),
																							   shippingNotificationLog);
				saveSubtractValueInterestRecord(request, settings.getInterestPercent(), currentShippingNotification);

			}
		}

		final ModularContractLogEntriesList interimContractLogs = streamInterimContractLogEntries(contractId, request);

		for(final ModularContractLogEntry interimContractLog : interimContractLogs)
		{
			final InterimContractAllocations currentInterimContractAllocations = initInterimContractAllocations(request,
																												settings.getAdditionalInterestDays(),
																												interimContractLog);

			// while (currentInterimContractAllocations.openAmountSignum() > 0)
			// {
			// 	// while (!currentInterimContractAllocations.canAllocate(currentShippingNotification) && shippingNotificationIterator.hasNext())
			// 	// {
			// 	// 	final boolean wasCreatedBeforeInvoice = currentShippingNotification != null
			// 	// 			&& currentInterimContractAllocations.isInvoiceCreatedAfter(currentShippingNotification);
			// 	//
			// 	// 	// if (wasCreatedBeforeInvoice)
			// 	// 	// {
			// 	// 	// 	saveSubtractValueInterestRecord(request, settings.getBonusInterestRate(), currentShippingNotification);
			// 	// 	// }
			// 	//
			// 	// 	// currentShippingNotification = initAllocationItem(request.getInterestToDistribute().getCurrencyId(),
			// 	// 	// 												 shippingNotificationIterator.next());
			// 	// }
			//
			// 	// if (currentInterimContractAllocations.canAllocate(currentShippingNotification))
			// 	// {
			// 	// 	currentShippingNotification = currentInterimContractAllocations.allocate(currentShippingNotification);
			// 	// }
			//
			// 	// if (!shippingNotificationIterator.hasNext())
			// 	// {
			// 	// 	break;
			// 	// }
			// }

			saveAddedValueInterestRecords(currentInterimContractAllocations, request);
			totalInterestScore = totalInterestScore.add(currentInterimContractAllocations.getAllocatedInterestScore());
			splitOpenAmount(currentInterimContractAllocations);
		}

		// if (!interimInvoiceExists)
		// {
		// 	saveSubtractedValueInterestRecords(request,
		// 									   settings.getBonusInterestRate(),
		// 									   shippingNotificationIterator,
		// 									   currentShippingNotification);
		// }

		return InitialInterestAllocationResult.of(totalInterestScore);
	}

	// private void saveSubtractedValueInterestRecords(
	// 		@NonNull final InterestComputationRequest request,
	// 		@NonNull final Percent bonusInterestRate,
	// 		@NonNull final Iterator<ModularContractLogEntry> shippingNotificationIterator,
	// 		@Nullable final InterimContractAllocations.AllocationItem currentShippingNotification)
	// {
	// 	if (currentShippingNotification != null && currentShippingNotification.openAmountSignum() > 0)
	// 	{
	// 		saveSubtractValueInterestRecord(request, bonusInterestRate, currentShippingNotification);
	// 	}
	//
	// 	while (shippingNotificationIterator.hasNext())
	// 	{
	// 		saveSubtractValueInterestRecord(request, bonusInterestRate, initAllocationItem(request.getInterestToDistribute().getCurrencyId(),
	// 																					   shippingNotificationIterator.next()));
	// 	}
	// }

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
																	   .build());
	}

	private void saveAddedValueInterestRecords(@NonNull final InterimContractAllocations currentInterimContractAllocations,
			final @NonNull InterestComputationRequest request)
	{
		final ModularContractLogEntry interimContractEntry = currentInterimContractAllocations.getInterimContractEntry();
		Check.assumeNotNull(interimContractEntry, "An interim contract log should exist.");

		final CreateModularLogInterestRequest modularLogInterestRequest = CreateModularLogInterestRequest.builder()
				.interimContractLogId(interimContractEntry.getId())
				.allocatedAmt(currentInterimContractAllocations.getOpenAmount())
				.interestDays((long)0)
				.interestRunId(request.getInterestRunId())
				.build();
		interestRepository.create(modularLogInterestRequest);
	}

	private void saveSubtractValueInterestRecord(
			@NonNull final InterestComputationRequest request,
			@NonNull final Percent interestPercent,
			@NonNull final AllocationItem shippingNotification)
	{
		if (request.getBonusComputationDetails() == null)
		{
			final CreateModularLogInterestRequest createInterestRequest = CreateModularLogInterestRequest.builder()
					.interestRunId(request.getInterestRunId())
					.shippingNotificationLogId(shippingNotification.getShippingNotificationEntry().getId())
					.allocatedAmt(shippingNotification.getOpenAmount())
					.interestDays((long)1)
					.finalInterest(Money.zero(request.getInterestToDistribute().getCurrencyId()))
					.build();

			interestRepository.create(createInterestRequest);
			return;
		}

		final long interestDays = request.getBonusComputationDetails().getBonusInterestDays();

		final BigDecimal bonusAmountAsBD = interestPercent.computePercentageOf(shippingNotification.getOpenAmount().toBigDecimal(),
																				 request.getInterestCurrencyPrecision().toInt())
				.multiply(BigDecimal.valueOf(interestDays))
				.divide(BigDecimal.valueOf(TOTAL_DAYS_OF_FISCAL_YEAR), RoundingMode.HALF_UP)
				.negate();

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
	private ModularContractLogEntriesList streamShippingNotificationLogEntries(
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

		 return modularContractLogService.getModularContractLogEntries(query);
	}

	@NonNull
	private ModularContractLogEntriesList streamInterimContractLogEntries(
			@NonNull final FlatrateTermId modularContractId,
			@NonNull final InterestComputationRequest request)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.computingMethodType(request.getComputingMethodType())
				.flatrateTermId(modularContractId)
				.invoicingGroupId(request.getInvoicingGroupId())
				.billable(true)
				.processed(false)
				.onlyIfAmountIsSet(true)
				.lockOwner(request.getLockOwner())
				.logEntryDocumentType(LogEntryDocumentType.CONTRACT_PREFINANCING)
				.orderBy(ModularContractLogQuery.OrderBy.TRANSACTION_DATE_ASC)
				.build();

		return modularContractLogService.getModularContractLogEntries(query);
	}

	private boolean interimInvoiceLogExists(
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

		return modularContractLogService.anyMatch(query);
	}

	private void distributeInterest(
			@NonNull final InterestComputationRequest request,
			@NonNull final InitialInterestAllocationResult result)
	{
		if (result.getTotalInterestScore().signum() == 0)
		{ // nothing to distribute
			return;
		}

		final FlatrateTermId onlyForContractId = null; // we want all contracts involved
		streamShippingNotificationLogEntries(request, onlyForContractId)
				.forEach(logEntry -> distributeInterest(request, result, logEntry));
	}

	private void distributeInterest(
			@NonNull final InterestComputationRequest request,
			@NonNull final InitialInterestAllocationResult result,
			@NonNull final ModularContractLogEntry shippingNotification)
	{
		interestRepository.getForShippingNotificationLogId(request.getInterestRunId(), shippingNotification.getId())
				.stream()
				.map(interestLog -> {
					final BigDecimal interestScore = interestLog.getInterestScoreEnsuringCurrency(request.getInterestToDistribute().getCurrencyId());
					final BigDecimal finalInterest = request.getInterestToDistribute().toBigDecimal()
							.multiply(interestScore)
							.divide(result.getTotalInterestScore(), request.getInterestCurrencyPrecision().toInt(), RoundingMode.HALF_UP);

					return interestLog.withFinalInterest(Money.of(finalInterest, request.getInterestToDistribute().getCurrencyId()));
				})
				.forEach(interestRepository::save);
	}

	@NonNull
	private InterimContractAllocations initInterimContractAllocations(
			@NonNull final InterestComputationRequest request,
			final int additionalInterestDays,
			@NonNull final ModularContractLogEntry interimContract)
	{
		Check.assume(LogEntryDocumentType.CONTRACT_PREFINANCING == interimContract.getDocumentType(),
					 "Expecting DocumentType = " + LogEntryDocumentType.CONTRACT_PREFINANCING +
							 " but got " + interimContract.getDocumentType() + "! LogId=" + interimContract.getId());
		Check.assumeNotNull(interimContract.getAmount(), "Interim contracts with no amount should've been skipped already! LogId="
				+ interimContract.getId());

		final Money contractAmtToAllocate = interimContract.getAmount().abs();

		final CurrencyConversionContext context = getCurrencyConversionContext(interimContract);

		final CurrencyId targetCurrencyId = request.getInterestToDistribute().getCurrencyId();
		final Money openAmountInTargetCurr = moneyService.convertMoneyToCurrency(contractAmtToAllocate,
																				 targetCurrencyId,
																				 context);

		return InterimContractAllocations.builder()
				.interestRunId(request.getInterestRunId())
				.additionalInterestDays(additionalInterestDays)
				.orgDAO(orgDAO)
				.interimContractEntry(interimContract)
				.openAmount(openAmountInTargetCurr)
				.build();
	}

	@NonNull
	private AllocationItem initAllocationItem(
			@NonNull final CurrencyId targetCurrencyId,
			@NonNull final ModularContractLogEntry shippingNotification)
	{
		Check.assume(LogEntryDocumentType.SHIPPING_NOTIFICATION == shippingNotification.getDocumentType(),
					 "Expecting DocumentType = " + LogEntryDocumentType.SHIPPING_NOTIFICATION +
							 " but got " + shippingNotification.getDocumentType() + "! LogId=" + shippingNotification.getId());

		final Money shippingNotificationAmount = Optional
				.ofNullable(shippingNotification.getAmount())
				.map(Money::abs)
				.orElse(Money.zero(targetCurrencyId));

		final CurrencyConversionContext context = getCurrencyConversionContext(shippingNotification);
		final Money shippingNotificationAmountInTargetCurr = moneyService.convertMoneyToCurrency(shippingNotificationAmount,
																								 targetCurrencyId,
																								 context);

		return AllocationItem.builder()
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

	private void computeAndDistributeBonus(@NonNull final InterestComputationRequest request)
	{
		Check.assume(request.getComputingMethodType() == ComputingMethodType.SubtractValueOnInterim,
					 "Bonus can only be distributed for" + ComputingMethodType.SubtractValueOnInterim);
		createInterestRecords(request);
	}

	private void splitOpenAmount(@NonNull final InterimContractAllocations interimContractAllocations)
	{
		// todo: do this for contract ?
		final ModularContractLogEntry invoice = interimContractAllocations.getInterimContractEntry();
		if (invoice.getAmount() == null
				|| interimContractAllocations.openAmountSignum() <= 0
				|| interimContractAllocations.getAllocatedShippingNotifications().isEmpty())
		{
			return;
		}

		final CurrencyConversionContext context = getCurrencyConversionContext(invoice);
		final Money openAmountInInvoiceCurrency = moneyService.convertMoneyToCurrency(interimContractAllocations.getOpenAmount(),
																					  invoice.getAmount().getCurrencyId(),
																					  context);

		final Percent openAmountPercent = openAmountInInvoiceCurrency.percentageOf(invoice.getAmount().abs());
		final Quantity openAmountQty = Optional.ofNullable(invoice.getQuantity())
				.map(qty -> {
					final BigDecimal openQty = openAmountPercent.computePercentageOf(qty.toBigDecimal(), qty.getUOM().getStdPrecision());
					return Quantity.of(openQty, qty.getUOM());
				}).orElse(null);

		final Money openAmountInInvoiceCurrencyWithSignApplied = openAmountInInvoiceCurrency.negateIf(invoice.getAmount().isNegative());

		final LogEntryCreateRequest createLogForOpenAmt = LogEntryCreateRequest.ofEntry(invoice)
				.toBuilder()
				.amount(openAmountInInvoiceCurrencyWithSignApplied)
				.quantity(openAmountQty)
				.build();

		modularContractLogService.create(createLogForOpenAmt);

		final ModularContractLogEntry logEntry = invoice.toBuilder()
				.amount(invoice.getAmount().subtract(openAmountInInvoiceCurrencyWithSignApplied))
				.quantity(Optional.ofNullable(invoice.getQuantity()).map(qty -> qty.subtract(openAmountQty)).orElse(null))
				.build();

		modularContractLogService.updateModularLog(logEntry);
	}

	@NonNull
	private CurrencyConversionContext getCurrencyConversionContext(@NonNull final ModularContractLogEntry logEntry)
	{
		final Instant conversionDate = logEntry.getTransactionDate().toInstant(orgDAO::getTimeZone);
		return currencyBL.createCurrencyConversionContext(conversionDate,
														  logEntry.getClientAndOrgId().getClientId(),
														  logEntry.getClientAndOrgId().getOrgId());
	}

	@NonNull
	public InterestComputationRequest getInterestDistributionRequest(
			@NonNull final InterestBonusComputationRequest request,
			@NonNull final InterestRunId runId)
	{
		return InterestComputationRequest.builder()
				.interestCurrencyPrecision(currencyBL.getStdPrecision(request.getInterestToDistribute().getCurrencyId()))
				.interestRunId(runId)
				.interestToDistribute(request.getInterestToDistribute())
				.invoicingGroupId(request.getInvoicingGroupId())
				.lockOwner(request.getLockOwner())
				.build();
	}

	@Value(staticConstructor = "of")
	private static class InitialInterestAllocationResult
	{
		@NonNull
		BigDecimal totalInterestScore;
	}
}
