/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.salesrep.process;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.commission.commissioninstance.interceptor.C_InvoiceFacadeService;
import de.metas.contracts.commission.commissioninstance.interceptor.C_Invoice_CandidateFacadeService;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import de.metas.util.time.InstantInterval;
import de.metas.util.time.IntervalUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.ChangeLogEntry;
import org.adempiere.ad.table.ChangeLogEntryQuery;
import org.adempiere.ad.table.ChangeLogEntryRepository;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class C_Commission_Share_CreateMissingForSalesRep extends JavaProcess
{
	private static final Logger logger = LogManager.getLogger(C_Commission_Share_CreateMissingForSalesRep.class);

	private final ChangeLogEntryRepository changeLogEntryRepository = SpringContextHolder.instance.getBean(ChangeLogEntryRepository.class);
	private final C_Invoice_CandidateFacadeService invoiceCandFacadeService = SpringContextHolder.instance.getBean(C_Invoice_CandidateFacadeService.class);
	private final C_InvoiceFacadeService invoiceFacadeService = SpringContextHolder.instance.getBean(C_InvoiceFacadeService.class);

	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	private static final String FROM_DATE = "2020-01-01";
	private static final String TO_DATE = "2020-12-31";

	/**
	 * Allows to run the full process with just one particular bpartner.
	 */
	@Param(parameterName = I_C_Commission_Share.COLUMNNAME_C_BPartner_SalesRep_ID)
	private BPartnerId bPartnerId;

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		try
		{
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			final ZonedDateTime from = LocalDate.parse(FROM_DATE, formatter).atStartOfDay(ZoneId.of("UTC-8"));
			final ZonedDateTime to = LocalDate.parse(TO_DATE, formatter).atStartOfDay(ZoneId.of("UTC-8"));

			final InstantInterval targetInterval = InstantInterval.of(from.toInstant(), to.toInstant());

			final SalesRepRelatedBPTableInfo salesRepRelatedBPTableInfo = getSalesRepRelatedBPTableInfo();

			if (bPartnerId == null)
			{
				recalculateCommission(salesRepRelatedBPTableInfo, targetInterval);
			}
			else
			{
				recalculateCommissionForSalesRep(bPartnerId, salesRepRelatedBPTableInfo, targetInterval);
			}
		}
		catch (final Exception e)
		{
			Loggables.withLogger(logger, Level.ERROR).addLog("*** ERROR: {}", e.getMessage(), e);
		}

		return MSG_OK;
	}

	private void recalculateCommission(
			@NonNull final SalesRepRelatedBPTableInfo salesRepRelatedBPTableInfo, @NonNull final InstantInterval targetInterval)
	{

		final ImmutableSet<BPartnerId> preliminaryQualifiedBPIds = retrieveAllBPsWithASalesRep(targetInterval, salesRepRelatedBPTableInfo);

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("*** DEBUG: number of preliminary qualified bPartnerIds for recalculation={}", preliminaryQualifiedBPIds.size());

		long count = 0;
		long countError = 0;
		for (final BPartnerId bPartnerId : preliminaryQualifiedBPIds)
		{
			count++;
			if (count % 1000 == 0)
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog("*** DEBUG {} of {} preliminaryQualifiedBPIds are done", count, preliminaryQualifiedBPIds.size());
			}

			final boolean success = recalculateCommissionForSalesRep(bPartnerId, salesRepRelatedBPTableInfo, targetInterval);
			if (!success)
			{
				countError++;
			}
		}
		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("*** DEBUG: iterated {} preliminary qualified bPartnerIds; countError={}", count, countError);
	}

	private void recalculateForBPartnerId(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final ImmutableList<ChangeLogEntry> salesRepChangeLogEntries,
			@NonNull final ImmutableList<ChangeLogEntry> bPartnerSalesRepChangeLogEntries,
			@NonNull final InstantInterval targetTimeframe)
	{
		final ILoggable loggableDebug = Loggables.withLogger(logger, Level.DEBUG);
		loggableDebug.addLog("*** DEBUG: start recalculating for sales-rep bpartnerId={}! ", bPartnerId);

		final List<RecalculateCommissionCriteria> recalculateCommissionCriteriaList =
				extractCommissionCriteriaList(bPartnerId, salesRepChangeLogEntries, bPartnerSalesRepChangeLogEntries, targetTimeframe);

		if (recalculateCommissionCriteriaList.isEmpty())
		{
			loggableDebug.addLog("*** DEBUG recalculating for for sales-rep bpartnerId: {} : empty criteria list! Skipping...", RepoIdAwares.toRepoId(bPartnerId));
			return;
		}
		else
		{
			loggableDebug.addLog("*** DEBUG: recalculating for for sales-rep bpartnerId: {}: recalculateCommissionCriteriaList: {}! ", RepoIdAwares.toRepoId(bPartnerId), recalculateCommissionCriteriaList);
		}

		final I_C_BPartner bPartner = bPartnerDAO.getById(bPartnerId);
		final BPartnerId initialSalesRepBPartnerId = BPartnerId.ofRepoIdOrNull(bPartner.getC_BPartner_SalesRep_ID());
		try
		{
			recalculateCommissionCriteriaList.forEach(criteria -> {
				try
				{
					trxManager.runInNewTrx(() -> recalculateCommissionForCriteria(criteria));
				}
				catch (final RuntimeException e)
				{
					final AdIssueId adIssueId = errorManager.createIssue(e);

					Loggables.withLogger(logger, Level.ERROR)
							.addLog("*** ERROR: recalculateCommissionForCriteria failed for Criteria :{}, errorMessage: {}! AD_Issue_ID={}",
									criteria, e.getLocalizedMessage(), RepoIdAwares.toRepoId(adIssueId));
				}
			});
		}
		finally
		{
			//preserve the initial state of the BPartner.BPartnerSalesRepId
			bPartnerBL.setBPartnerSalesRepIdOutOfTrx(bPartnerId, initialSalesRepBPartnerId);
		}
	}

	@NonNull
	private ImmutableList<RecalculateCommissionCriteria> extractCommissionCriteriaList(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final ImmutableList<ChangeLogEntry> userSalesRepChangeLogEntries,
			@NonNull final ImmutableList<ChangeLogEntry> bPartnerSalesRepChangeLogEntries,
			@NonNull final InstantInterval targetTimeframe)
	{
		final I_C_BPartner bPartner = bPartnerBL.getById(bPartnerId);

		// get SalesRep_IDs (not yet parsed to int) at different intervals
		final ImmutableMap<InstantInterval, String> userSalesRepIdByInterval;
		if (!userSalesRepChangeLogEntries.isEmpty())
		{ // there are SalesRep_ID change log entries with their respective time intervals
			userSalesRepIdByInterval = mapValueByInterval(userSalesRepChangeLogEntries, targetTimeframe);
		}
		else if (bPartner.getSalesRep_ID() > 0)
		{ // if no log entries were found, it means the present SalesRep_ID applies for the whole timeframe
			userSalesRepIdByInterval = ImmutableMap.of(targetTimeframe, String.valueOf(bPartner.getSalesRep_ID()));
		}
		else
		{ // no changelog and also no current sales rep set
			userSalesRepIdByInterval = ImmutableMap.of();
		}

		// get C_BPartner_SalesRep_IDs (not yet parsed to int) at different intervals
		final Map<InstantInterval, String> bPartnerSalesRepIdByInterval;
		if (!bPartnerSalesRepChangeLogEntries.isEmpty())
		{ // there are C_BPartner_SalesRep_ID change log entries with their respective time intervals
			bPartnerSalesRepIdByInterval = mapValueByInterval(bPartnerSalesRepChangeLogEntries, targetTimeframe);
		}
		// don't allow a C_BPartner_SalesRep_ID value that is set *now* to override the whole history of SalesRep_IDs
		// else if (bPartner.getC_BPartner_SalesRep_ID() > 0)
		// { // if no log entries were found, it means the present C_BPartner_SalesRep_ID applies for the whole timeframe
		// 	bPartnerSalesRepIdByInterval = ImmutableMap.of(targetTimeframe, String.valueOf(bPartner.getC_BPartner_SalesRep_ID()));
		// }
		else
		{ // no changelog
			bPartnerSalesRepIdByInterval = ImmutableMap.of();
		}

		final ImmutableList.Builder<RecalculateCommissionCriteria> commissionCriteriaListBuilder = ImmutableList.builder();

		userSalesRepIdByInterval.forEach((userSalesRepInterval, userSalesRepIdAsString) -> {
			{
				final Optional<BPartnerId> superSalesRepBPId = getSuperSalesRepBPId(userSalesRepIdAsString, bPartnerId);

				if (superSalesRepBPId.isPresent())
				{   // If the BP had both a SalesRep_ID and a C_BPartner_SalesRep_ID at the certain intervals then remove thome intervals.
					// I.e in the end, a "direct" C_BPartner_SalesRep_ID reference takes preference over an indirect via-SalesRep_ID-reference
					final ImmutableList<InstantInterval> intersectingIntervals = bPartnerSalesRepIdByInterval.keySet()
							.stream()
							.map(userSalesRepInterval::getIntersectionWith) // intersect userSalesRepInterval with the current bPartnerSalesRepInterval
							.filter(Optional::isPresent)
							.map(Optional::get)
							.collect(ImmutableList.toImmutableList());

					if (intersectingIntervals.isEmpty())
					{ // just add userSalesRepInterval as-is
						commissionCriteriaListBuilder.add(RecalculateCommissionCriteria.of(bPartnerId, userSalesRepInterval, superSalesRepBPId.get()));
					}
					else
					{ // trim and split userSalesRepInterval by cutting out the intersecting intervals, and add the result(s)
						Loggables.withLogger(logger, Level.DEBUG)
								.addLog("*** DEBUG: SalesRep_ID-interval={} is intersected by C_BPartner_SalesRep_ID-intervals={}; (those will be removed from the SalesRep_ID-interval)",
										userSalesRepInterval, intersectingIntervals);

						IntervalUtils.intervalDiff(ImmutableList.of(userSalesRepInterval), intersectingIntervals)
								.stream()
								.map(interval -> RecalculateCommissionCriteria.of(bPartnerId, interval, superSalesRepBPId.get()))
								.forEach(commissionCriteriaListBuilder::add);
					}
				}
			}
		});

		return commissionCriteriaListBuilder.build();
	}

	@NonNull
	private ImmutableMap<InstantInterval, String> mapValueByInterval(
			@NonNull final ImmutableList<ChangeLogEntry> changeLogEntries,
			@NonNull final InstantInterval targetInterval)
	{
		final ImmutableList<ChangeLogEntry> orderedChangeLogEntries = changeLogEntries.stream()
				.sorted(Comparator.comparing(ChangeLogEntry::getChangeTimestamp))
				.collect(ImmutableList.toImmutableList());

		Instant currentFrom = targetInterval.getFrom();
		final HashMap<InstantInterval, String> valueByInterval = new HashMap<>();
		final Iterator<ChangeLogEntry> salesRepLogIterator = orderedChangeLogEntries.iterator();

		while (salesRepLogIterator.hasNext())
		{
			final ChangeLogEntry logEntry = salesRepLogIterator.next();

			// we only care about the newValue if it is the last entry in the change log
			if (!salesRepLogIterator.hasNext())
			{
				if (logEntry.getOldValue() != null)
				{
					final InstantInterval oldValueInterval = InstantInterval.of(currentFrom, logEntry.getChangeTimestamp());

					valueByInterval.put(oldValueInterval, logEntry.getOldValue());
				}

				if (logEntry.getNewValue() != null)
				{
					final InstantInterval newValueInterval = InstantInterval.of(logEntry.getChangeTimestamp(), targetInterval.getTo());

					valueByInterval.put(newValueInterval, logEntry.getNewValue());
				}
			}
			else if (logEntry.getOldValue() != null)
			{
				final InstantInterval oldValueInterval = InstantInterval.of(currentFrom, logEntry.getChangeTimestamp());

				valueByInterval.put(oldValueInterval, logEntry.getOldValue());
			}

			currentFrom = logEntry.getChangeTimestamp();
		}

		return ImmutableMap.copyOf(valueByInterval);
	}

	/**
	 * @param superSalesRepUserIdValue parse this to an {@link UserId} and get the AD_User's C_BPartner_ID, if any
	 * @param salesRepId               only given as context info for log messages
	 */
	private Optional<BPartnerId> getSuperSalesRepBPId(@NonNull final String superSalesRepUserIdValue, @NonNull final BPartnerId salesRepId)
	{
		final int userId = NumberUtils.asInt(superSalesRepUserIdValue, -1);
		final UserId superSalesRepUserId = UserId.ofRepoIdOrNull(userId);

		if (superSalesRepUserId == null)
		{
			Loggables.withLogger(logger, Level.WARN)
					.addLog("*** WARN during calculation for bPartnerId: {}: salesRepUser value: {} cannot be converted to int! Skipping.. ", superSalesRepUserIdValue);
			return Optional.empty();
		}

		final I_AD_User superSalesRepUser = userDAO.getById(superSalesRepUserId);
		if (superSalesRepUser.getC_BPartner_ID() <= 0)
		{
			// this might be OK; we did not take care to assign a bpartner the user if there were no actial commission instance with base-points > 0
			logger.debug("*** WARN during calculation for bPartnerId={} salesRepUserId={} doesn't have a bPartner! Skipping.. ",
					RepoIdAwares.toRepoId(salesRepId), superSalesRepUser.getAD_User_ID());
			return Optional.empty();
		}
		else
		{
			final I_C_BPartner superSalesRepBPartner = bPartnerDAO.getById(superSalesRepUser.getC_BPartner_ID());

			if (!superSalesRepBPartner.isSalesRep())
			{
				Loggables.withLogger(logger, Level.WARN)
						.addLog("*** WARN during calculation for salesRepId={}: missing 'isSalesRep' flag on regional manager id={}! Skipping.. ",
								RepoIdAwares.toRepoId(salesRepId), superSalesRepBPartner.getC_BPartner_ID());

				return Optional.empty();
			}
			else
			{
				return Optional.of(BPartnerId.ofRepoId(superSalesRepBPartner.getC_BPartner_ID()));
			}
		}
	}

	public void recalculateCommissionForCriteria(@NonNull final RecalculateCommissionCriteria commissionCriteria)
	{
		bPartnerBL.setBPartnerSalesRepIdOutOfTrx(commissionCriteria.getSalesrepPartnerId(), commissionCriteria.getTopLevelSalesRepId());

		recalculateStartingFromInvoiceCand(commissionCriteria);

		recalculateStartingFromInvoice(commissionCriteria);
	}

	private boolean recalculateCommissionForSalesRep(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final SalesRepRelatedBPTableInfo tableInfo,
			@NonNull final InstantInterval targetInterval)
	{
		try
		{
			final ChangeLogEntryQuery changeLogEntryQuery = ChangeLogEntryQuery.builder()
					.adTableId(tableInfo.getBPartnerTableId())
					.adColumnIds(ImmutableSet.of(tableInfo.getSalesRepColumnId(), tableInfo.getBPartnerSalesRepColumnId()))
					.from(targetInterval.getFrom())
					.to(targetInterval.getTo())
					.recordId(bPartnerId.getRepoId())
					.build();

			final ImmutableList<ChangeLogEntry> changeLogEntries = changeLogEntryRepository.getLogEntriesFor(changeLogEntryQuery);

			//split the change logs by column
			final ImmutableList<ChangeLogEntry> salesRepLogEntries = changeLogEntries.stream()
					.filter(logEntry -> logEntry.getAdColumnId().getRepoId() == tableInfo.getSalesRepColumnId().getRepoId())
					.collect(ImmutableList.toImmutableList());

			final ImmutableList<ChangeLogEntry> bpSalesRepLogEntries = changeLogEntries.stream()
					.filter(logEntry -> logEntry.getAdColumnId().getRepoId() == tableInfo.getBPartnerSalesRepColumnId().getRepoId())
					.collect(ImmutableList.toImmutableList());

			recalculateForBPartnerId(bPartnerId, salesRepLogEntries, bpSalesRepLogEntries, targetInterval);
			return true;
		}
		catch (final Exception e)
		{
			Loggables.withLogger(logger, Level.ERROR).addLog("*** ERROR while trying to calculate commission for bpartnerId: {}, message: {}"
					, bPartnerId, e.getMessage(), e);
			return false;

		}
	}

	@NonNull
	private ImmutableSet<BPartnerId> retrieveAllBPsWithASalesRep(
			@NonNull final InstantInterval interval,
			@NonNull final SalesRepRelatedBPTableInfo salesRepRelatedBPTableInfo)
	{
		// load all related change logs
		final ChangeLogEntryQuery changeLogEntryQuery = ChangeLogEntryQuery.builder()
				.adColumnId(salesRepRelatedBPTableInfo.getSalesRepColumnId())
				.adTableId(salesRepRelatedBPTableInfo.getBPartnerTableId())
				.from(interval.getFrom())
				.to(interval.getTo())
				.build();

		final Set<BPartnerId> preliminaryQualifiedBPIdSet = changeLogEntryRepository.getLogEntriesFor(changeLogEntryQuery)
				.stream()
				.map(ChangeLogEntry::getRecordId)
				.map(BPartnerId::ofRepoId)
				.collect(Collectors.toSet());

		//check also the current situation
		final BPartnerQuery bPartnerQuery = BPartnerQuery.builder()
				.userSalesRepSet(true)
				.build();

		preliminaryQualifiedBPIdSet.addAll(bPartnerDAO.retrieveBPartnerIdsBy(bPartnerQuery));

		return ImmutableSet.copyOf(preliminaryQualifiedBPIdSet);
	}

	private SalesRepRelatedBPTableInfo getSalesRepRelatedBPTableInfo()
	{
		final AdTableId bPartnerTableId = adTableDAO.retrieveAdTableId(I_C_BPartner.Table_Name);
		final AdColumnId salesRepColumnId = adTableDAO.retrieveColumnId(bPartnerTableId, I_C_BPartner.COLUMNNAME_SalesRep_ID);
		final AdColumnId bPartnerSalesRepColumnId = adTableDAO.retrieveColumnId(bPartnerTableId, I_C_BPartner.COLUMNNAME_C_BPartner_SalesRep_ID);

		return SalesRepRelatedBPTableInfo.builder()
				.bPartnerTableId(bPartnerTableId)
				.salesRepColumnId(salesRepColumnId)
				.bPartnerSalesRepColumnId(bPartnerSalesRepColumnId)
				.build();
	}

	private void recalculateStartingFromInvoiceCand(@NonNull final RecalculateCommissionCriteria commissionCriteria)
	{
		final InvoiceCandidateQuery invoiceCandidateQuery = InvoiceCandidateQuery.builder()
				.dateOrderedInterval(commissionCriteria.getTargetInterval())
				.salesRepBPartnerId(commissionCriteria.getSalesrepPartnerId())
				.build();

		final InvoiceCandidateMultiQuery multiQuery = InvoiceCandidateMultiQuery.builder().query(invoiceCandidateQuery).build();

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.getByQuery(multiQuery);

		Loggables.withLogger(logger, Level.DEBUG).addLog("*** DEBUG: found {} ICs while recalculating commission for criteria: {} ",
				invoiceCandidates.size(), commissionCriteria);

		invoiceCandidates.forEach(invoiceCandidate -> {
			try
			{
				invoiceCandFacadeService.syncICToCommissionInstance(invoiceCandidate, false);
			}
			catch (final Exception e)
			{
				Loggables.withLogger(logger, Level.DEBUG)
						.addLog("recalculateCommissionForCriteria failed for IC_ID ={}: Error message {}",
								invoiceCandidate.getC_Invoice_Candidate_ID(), e.getLocalizedMessage());

				throw AdempiereException.wrapIfNeeded(e)
						.appendParametersToMessage()
						.setParameter("C_Invoice_Candidate_ID", invoiceCandidate.getC_Invoice_Candidate_ID());
			}
		});
	}

	private void recalculateStartingFromInvoice(@NonNull final RecalculateCommissionCriteria commissionCriteria)
	{
		final List<I_C_Invoice> invoices = invoiceDAO.retrieveBySalesrepPartnerId(commissionCriteria.getSalesrepPartnerId(), commissionCriteria.getTargetInterval());

		Loggables.withLogger(logger, Level.DEBUG).addLog("*** DEBUG: found {} Invoices while recalculating commission for criteria: {}",
				invoices.size(), commissionCriteria);

		invoices.forEach(invoice -> {
			try
			{
				invoiceFacadeService.syncInvoiceToCommissionInstance(invoice);
			}
			catch (final Exception e)
			{
				Loggables.withLogger(logger, Level.DEBUG)
						.addLog("recalculateCommissionForCriteria failed for C_Invoice_Id ={}: Error message {}",
								invoice.getC_Invoice_ID(), e.getLocalizedMessage());

				throw AdempiereException.wrapIfNeeded(e)
						.appendParametersToMessage()
						.setParameter("C_Invoice_Id", invoice.getC_Invoice_ID());
			}
		});
	}

	@Value
	@Builder
	private static class SalesRepRelatedBPTableInfo
	{
		@NonNull
		AdTableId bPartnerTableId;

		@NonNull
		AdColumnId salesRepColumnId;

		@NonNull
		AdColumnId bPartnerSalesRepColumnId;
	}
}
