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
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.contracts.modular.log.LogEntryDocumentType.ALL_SHIPPING_NOTIFICATION_MODCNTR_LOG_DOCUMENTTYPES;

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

		final InterestComputationRequest.InterestComputationRequestBuilder interestDistributionRequestBuilder = getInterestDistributionRequestBuilder(request, runId);

		computeAndDistributeInterest(interestDistributionRequestBuilder.computingMethodType(ComputingMethodType.AddValueOnInterim).build());

		computeAndDistributeBonus(interestDistributionRequestBuilder.computingMethodType(ComputingMethodType.SubtractValueOnInterim).build());

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

	private void createBonusRecords(@NonNull final InterestComputationRequest request)
	{
		for (final FlatrateTermId contractId : getModularContractIds(request))
		{
			createBonusRecords(request, contractId);
		}
	}

	private void createBonusRecords(
			@NonNull final InterestComputationRequest request,
			@NonNull final FlatrateTermId contractId)
	{

		final Iterator<ModularContractLogEntry> shippingNotificationIterator = streamShippingNotificationLogEntries(request, contractId).iterator();
		final boolean interimInvoiceExists = interimInvoiceLogExists(contractId, request);
		ModularContractAllocations.AllocationItem currentShippingNotification;
		final ModularContractSettings settings = modularContractService.getModularSettingsForContract(contractId);
		if (interimInvoiceExists)
		{
			return;
		}
		while (shippingNotificationIterator.hasNext())
		{
			currentShippingNotification = initAllocationItem(request.getInterestToDistribute().getCurrencyId(),
															 shippingNotificationIterator.next(),
															 request.getBonusAndInterestTimeInterval());

			{
				final SaveSubtractedValueRequest saveSubtractedValueRequest = SaveSubtractedValueRequest.builder()
						.request(request)
						.bonusInterestRate(settings.getBonusInterestRate())
						.shippingNotification(currentShippingNotification)
						.build();
				saveSubtractValueInterestRecord(saveSubtractedValueRequest);
			}
		}

	}

	@NonNull
	private InitialInterestAllocationResult createInterestRecords(
			@NonNull final InterestComputationRequest request,
			@NonNull final FlatrateTermId contractId)
	{
		final Iterator<ModularContractLogEntry> shippingNotificationIterator = streamShippingNotificationLogEntries(request, contractId).iterator();
		final Iterator<ModularContractLogEntry> modularContractLogEntryIterator = streamModularContractLogEntries(contractId, request).iterator();
		final ModularContractSettings settings = modularContractService.getModularSettingsForContract(contractId);

		BigDecimal totalInterestScore = BigDecimal.ZERO;

		ModularContractAllocations.AllocationItem currentShippingNotification = null;
		while (modularContractLogEntryIterator.hasNext())
		{
			final ModularContractAllocations currentModularContractAllocations = initModularContractAllocations(request,
																												settings.getAdditionalInterestDays(),
																												modularContractLogEntryIterator.next());

			while (currentModularContractAllocations.openAmountSignum() > 0)
			{
				while (!currentModularContractAllocations.canAllocate(currentShippingNotification) && shippingNotificationIterator.hasNext())
				{
					currentShippingNotification = initAllocationItem(request.getInterestToDistribute().getCurrencyId(),
																	 shippingNotificationIterator.next(),
																	 request.getBonusAndInterestTimeInterval());
				}

				if (currentModularContractAllocations.canAllocate(currentShippingNotification))
				{
					currentShippingNotification = currentModularContractAllocations.allocate(currentShippingNotification);
				}

				if (!shippingNotificationIterator.hasNext())
				{
					break;
				}
			}

			saveAddedValueInterestRecords(currentModularContractAllocations);
			totalInterestScore = totalInterestScore.add(currentModularContractAllocations.getAllocatedInterestScore());
		}

		return InitialInterestAllocationResult.of(totalInterestScore);
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
																	   .build());
	}

	private void saveAddedValueInterestRecords(@NonNull final ModularContractAllocations currentModularContractAllocations)
	{
		currentModularContractAllocations.getAllocatedShippingNotifications().forEach(interestRepository::create);
	}

	private void saveSubtractValueInterestRecord(@NonNull final SaveSubtractedValueRequest saveSubtractedValueRequest)
	{
		final InterestComputationRequest interestComputationRequest = saveSubtractedValueRequest.getRequest();
		final ModularContractAllocations.AllocationItem shippingNotification = saveSubtractedValueRequest.getShippingNotification();
		final Percent bonusInterestRate = saveSubtractedValueRequest.getBonusInterestRate();

		final long interestDays = interestComputationRequest.getBonusAndInterestTimeInterval().getBonusInterestDays();

		final BigDecimal bonusAmountAsBD = bonusInterestRate.computePercentageOf(shippingNotification.getOpenAmount().toBigDecimal(),
																				 interestComputationRequest.getInterestCurrencyPrecision().toInt())
				.multiply(BigDecimal.valueOf(interestDays))
				.divide(BigDecimal.valueOf(TOTAL_DAYS_OF_FISCAL_YEAR), RoundingMode.HALF_UP)
				.negate();

		final CreateModularLogInterestRequest createInterestRequest = CreateModularLogInterestRequest.builder()
				.interestRunId(interestComputationRequest.getInterestRunId())
				.shippingNotificationLogId(shippingNotification.getShippingNotificationEntry().getId())
				.allocatedAmt(shippingNotification.getOpenAmount())
				.interestDays(interestDays)
				.finalInterest(Money.of(bonusAmountAsBD, shippingNotification.getOpenAmount().getCurrencyId()))
				.build();

		interestRepository.create(createInterestRequest);
	}

	@NonNull
	private Stream<ModularContractLogEntry> streamShippingNotificationLogEntries(
			@NonNull final InterestComputationRequest request,
			@Nullable final FlatrateTermId onlyForContractId)
	{
		final ModularContractLogQuery.ModularContractLogQueryBuilder queryBuilder = ModularContractLogQuery.builder()
				.computingMethodType(request.getComputingMethodType())
				.invoicingGroupId(request.getInvoicingGroupId())
				.billable(true)
				.processed(false)
				.lockOwner(request.getLockOwner())
				.logEntryDocumentTypes(ALL_SHIPPING_NOTIFICATION_MODCNTR_LOG_DOCUMENTTYPES)
				.orderBy(ModularContractLogQuery.OrderBy.TRANSACTION_DATE_ASC);

		if(onlyForContractId != null)
		{
			queryBuilder.flatrateTermId(onlyForContractId);
		}

		return modularContractLogService.streamModularContractLogEntries(queryBuilder.build());
	}

	@NonNull
	private Stream<ModularContractLogEntry> streamModularContractLogEntries(
			@Nullable final FlatrateTermId contractId,
			@NonNull final InterestComputationRequest request)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.computingMethodType(request.getComputingMethodType())
				.invoicingGroupId(request.getInvoicingGroupId())
				.billable(true)
				.processed(false)
				.flatrateTermId(contractId)
				.onlyIfAmountIsSet(true)
				.lockOwner(request.getLockOwner())
				.logEntryDocumentType(LogEntryDocumentType.PURCHASE_MODULAR_CONTRACT)
				.orderBy(ModularContractLogQuery.OrderBy.TRANSACTION_DATE_ASC)
				.build();

		return modularContractLogService.getModularContractLogEntries(query).stream();
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
	private ModularContractAllocations initModularContractAllocations(
			@NonNull final InterestComputationRequest request,
			final int additionalInterestDays,
			@NonNull final ModularContractLogEntry modularContractLogEntry)
	{
		Check.assume(LogEntryDocumentType.PURCHASE_MODULAR_CONTRACT == modularContractLogEntry.getDocumentType(),
					 "Expecting DocumentType = " + LogEntryDocumentType.PURCHASE_MODULAR_CONTRACT +
							 " but got " + modularContractLogEntry.getDocumentType() + "! LogId=" + modularContractLogEntry.getId());
		Check.assumeNotNull(modularContractLogEntry.getAmount(), "Contracts with no amount should've been skipped already! LogId="
				+ modularContractLogEntry.getId());

		final Money interimAmtToAllocate = modularContractLogEntry.getAmount().abs();

		final CurrencyConversionContext context = modularContractLogService.getCurrencyConversionContext(modularContractLogEntry);

		final CurrencyId targetCurrencyId = request.getInterestToDistribute().getCurrencyId();
		final Money openAmountInTargetCurr = moneyService.convertMoneyToCurrency(interimAmtToAllocate,
																				 targetCurrencyId,
																				 context);

		return ModularContractAllocations.builder()
				.interestRunId(request.getInterestRunId())
				.additionalInterestDays(additionalInterestDays)
				.orgDAO(orgDAO)
				.modularContractLogEntry(modularContractLogEntry)
				.openAmount(openAmountInTargetCurr)
				.build();
	}

	@NonNull
	private ModularContractAllocations.AllocationItem initAllocationItem(
			@NonNull final CurrencyId targetCurrencyId,
			@NonNull final ModularContractLogEntry shippingNotification,
			@NonNull final BonusAndInterestTimeInterval bonusAndInterestTimeInterval)
	{
		Check.assume(shippingNotification.getDocumentType().isAnyShippingNotificationType(),
					 "Expecting DocumentType in " + ALL_SHIPPING_NOTIFICATION_MODCNTR_LOG_DOCUMENTTYPES +
							 " but got " + shippingNotification.getDocumentType() + "! LogId=" + shippingNotification.getId());

		final Money shippingNotificationAmount = Optional
				.ofNullable(shippingNotification.getAmount())
				.map(Money::abs)
				.orElse(Money.zero(targetCurrencyId));

		final CurrencyConversionContext context = modularContractLogService.getCurrencyConversionContext(shippingNotification);
		final Money shippingNotificationAmountInTargetCurr = moneyService.convertMoneyToCurrency(shippingNotificationAmount,
																								 targetCurrencyId,
																								 context);

		return ModularContractAllocations.AllocationItem.builder()
				.shippingNotificationEntry(shippingNotification)
				.openAmount(shippingNotificationAmountInTargetCurr)
				.bonusAndInterestTimeInterval(bonusAndInterestTimeInterval)
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
		createBonusRecords(request);
	}

	private InterestComputationRequest.InterestComputationRequestBuilder getInterestDistributionRequestBuilder(
			@NonNull final InterestBonusComputationRequest request,
			@NonNull final InterestRunId runId)
	{
		return InterestComputationRequest.builder()
				.interestCurrencyPrecision(currencyBL.getStdPrecision(request.getInterestToDistribute().getCurrencyId()))
				.interestRunId(runId)
				.interestToDistribute(request.getInterestToDistribute())
				.invoicingGroupId(request.getInvoicingGroupId())
				.bonusAndInterestTimeInterval(request.getBonusAndInterestTimeInterval())
				.lockOwner(request.getLockOwner());

	}

	@Value(staticConstructor = "of")
	private static class InitialInterestAllocationResult
	{
		@NonNull
		BigDecimal totalInterestScore;
	}
}
