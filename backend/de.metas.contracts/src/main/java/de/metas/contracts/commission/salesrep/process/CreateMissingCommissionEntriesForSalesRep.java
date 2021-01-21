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
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.commission.commissioninstance.interceptor.C_Invoice_CandidateFacadeService;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;
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

public class CreateMissingCommissionEntriesForSalesRep extends JavaProcess
{
	private static final Logger logger = LogManager.getLogger(CreateMissingCommissionEntriesForSalesRep.class);

	private final ChangeLogEntryRepository changeLogEntryRepository = SpringContextHolder.instance.getBean(ChangeLogEntryRepository.class);
	private final C_Invoice_CandidateFacadeService invoiceCandFacadeService = SpringContextHolder.instance.getBean(C_Invoice_CandidateFacadeService.class);

	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private static final String FROM_DATE = "2020-01-01";
	private static final String TO_DATE = "2020-12-31";

	@Override
	protected String doIt() throws Exception
	{
		try
		{
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			final ZonedDateTime from = LocalDate.parse(FROM_DATE, formatter).atStartOfDay(ZoneId.of("UTC-8"));
			final ZonedDateTime to = LocalDate.parse(TO_DATE, formatter).atStartOfDay(ZoneId.of("UTC-8"));

			final InstantInterval targetInterval = InstantInterval.of(from.toInstant(), to.toInstant());

			recalculateCommission(targetInterval);
		}
		catch (final Exception e)
		{
			Loggables.withLogger(logger, Level.ERROR).addLog("*** ERROR: {}", e.getMessage(), e);
		}

		return MSG_OK;
	}

	private void recalculateCommission(@NonNull final InstantInterval targetInterval)
	{
		final SalesRepRelatedBPTableInfo salesRepRelatedBPTableInfo = getSalesRepRelatedBPTableInfo();

		final ImmutableSet<BPartnerId> preliminaryQualifiedBPIds = retrieveAllBPsWithASalesRep(targetInterval, salesRepRelatedBPTableInfo);

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("*** DEBUG: number of preliminary qualified bPartnerIds for recalculation={}", preliminaryQualifiedBPIds.size());

		long count = 0;
		long countError=0;
		for(final BPartnerId bPartnerId:preliminaryQualifiedBPIds)
		{
			try
			{
				count++;
				processBPId(bPartnerId, salesRepRelatedBPTableInfo, targetInterval);
			}
			catch (final Exception e)
			{
				Loggables.withLogger(logger, Level.ERROR).addLog("*** ERROR while trying to calculate commission for bpartnerId: {}, message: {}"
						, bPartnerId, e.getMessage(), e);
				countError++;
			}
		};
		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("*** DEBUG: iterated {} preliminary qualified bPartnerIds; countError={}", count, countError);
	}

	private void recalculateForBPartnerId(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final ImmutableList<ChangeLogEntry> salesRepChangeLogEntries,
			@NonNull final ImmutableList<ChangeLogEntry> bPartnerSalesRepChangeLogEntries,
			@NonNull final InstantInterval targetTimeframe)
	{
		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("*** DEBUG: start recalculating for bpartnerId: {}! ", bPartnerId);

		final List<RecalculateCommissionCriteria> recalculateCommissionCriteriaList =
				extractCommissionCriteriaList(bPartnerId, salesRepChangeLogEntries, bPartnerSalesRepChangeLogEntries, targetTimeframe);

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("*** DEBUG: recalculateCommissionCriteriaList {}! ", recalculateCommissionCriteriaList);

		final I_C_BPartner bPartner = bPartnerDAO.getById(bPartnerId);
		final BPartnerId initialSalesRepBPartnerId = BPartnerId.ofRepoIdOrNull(bPartner.getC_BPartner_SalesRep_ID());

		recalculateCommissionCriteriaList.forEach(criteria -> {
			try
			{
				trxManager.runInNewTrx(() -> recalculateCommissionForCriteria(criteria));
			}
			catch (final RuntimeException e)
			{
				Loggables.withLogger(logger, Level.ERROR)
						.addLog("*** ERROR: recalculateCommissionForCriteria failed for Criteria :{}, errorMessage: {}! ", criteria, e.getLocalizedMessage());
			}
		});

		//preserve the initial state of the BPartner.BPartnerSalesRepId
		bPartnerBL.setBPartnerSalesRepId(bPartnerId, initialSalesRepBPartnerId);
	}

	@NonNull
	private ImmutableList<RecalculateCommissionCriteria> extractCommissionCriteriaList(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final ImmutableList<ChangeLogEntry> salesRepChangeLogEntries,
			@NonNull final ImmutableList<ChangeLogEntry> bPartnerSalesRepChangeLogEntries,
			@NonNull final InstantInterval targetTimeframe)
	{
		final I_C_BPartner bPartner = bPartnerBL.getById(bPartnerId);

		final ImmutableMap<InstantInterval, String> salesRepValueByInterval = !salesRepChangeLogEntries.isEmpty()
				? mapValueByInterval(salesRepChangeLogEntries, targetTimeframe)
				//if no log entries were found, it means the present situation applies for the whole target timeframe
				: (bPartner.getSalesRep_ID() > 0
					? ImmutableMap.of(targetTimeframe, String.valueOf(bPartner.getSalesRep_ID()))
					: ImmutableMap.of());

		final Map<InstantInterval, String> bPartnerSalesRepValueByInterval = !bPartnerSalesRepChangeLogEntries.isEmpty()
				? mapValueByInterval(bPartnerSalesRepChangeLogEntries, targetTimeframe)
				: (bPartner.getC_BPartner_SalesRep_ID() > 0
					? ImmutableMap.of(targetTimeframe, String.valueOf(bPartner.getC_BPartner_SalesRep_ID()))
					: ImmutableMap.of());

		final ImmutableList.Builder<RecalculateCommissionCriteria> commissionCriteriaListBuilder = ImmutableList.builder();

		salesRepValueByInterval.forEach((userSalesRepInterval, userSalesRepValue) -> {
			{
				final Optional<BPartnerId> superSalesRepBPId = getSuperSalesRepBPId(userSalesRepValue, bPartnerId);

				if (superSalesRepBPId.isPresent())
				{
					//remove those time intervals when the BP had also a bp_salesrep_id
					final ImmutableList<InstantInterval> overlappingIntervals = bPartnerSalesRepValueByInterval.keySet()
							.stream()
							.map(userSalesRepInterval::getIntersection)
							.filter(Optional::isPresent)
							.map(Optional::get)
							.collect(ImmutableList.toImmutableList());

					Loggables.withLogger(logger, Level.DEBUG)
							.addLog("*** DEBUG: userSalesRepInterval: {}, overlappingIntervals (to be removed): {} ",
									userSalesRepInterval, overlappingIntervals);

					IntervalUtils.intervalDiff(ImmutableList.of(userSalesRepInterval), overlappingIntervals)
							.stream()
							.map(interval -> RecalculateCommissionCriteria.of(bPartnerId, interval, superSalesRepBPId.get()))
							.forEach(commissionCriteriaListBuilder::add);
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
			else if (logEntry.getOldValue() != null )
			{
				final InstantInterval oldValueInterval = InstantInterval.of(currentFrom, logEntry.getChangeTimestamp());

				valueByInterval.put(oldValueInterval, logEntry.getOldValue());
			}

			currentFrom = logEntry.getChangeTimestamp();
		}

		return ImmutableMap.copyOf(valueByInterval);
	}

	private Optional<BPartnerId> getSuperSalesRepBPId(@NonNull final String superSalesRepUserIdValue, @NonNull final BPartnerId salesRepId)
	{
		final int userId = NumberUtils.asInt(superSalesRepUserIdValue, -1);
		final UserId superSalesRepUserId = UserId.ofRepoIdOrNull(userId);

		if (superSalesRepUserId == null)
		{
			Loggables.withLogger(logger, Level.WARN)
					.addLog("*** WARN during calculation for bPartnerId: {}:"
									+ " salesRepUser value: {} cannot be converted to int! Skipping.. ", superSalesRepUserIdValue);
			return Optional.empty();
		}

		final I_AD_User superSalesRepUser = userDAO.getById(superSalesRepUserId);

		if (superSalesRepUser.getC_BPartner_ID() <= 0)
		{
			Loggables.withLogger(logger, Level.WARN)
					.addLog("*** WARN during calculation for bPartnerId: {}:"
									+ " salesRepUser: {} doesn't have a bPartner! Skipping.. ", salesRepId, superSalesRepUser.getAD_User_ID());
			return Optional.empty();
		}
		else
		{
			final I_C_BPartner superSalesRepBPartner = bPartnerDAO.getById(superSalesRepUser.getC_BPartner_ID());

			if (!superSalesRepBPartner.isSalesRep())
			{
				Loggables.withLogger(logger, Level.WARN)
						.addLog("*** WARN during calculation for bPartnerId: {}: missing 'isSalesRep' flag on regional manager: {}! Skipping.. ",
								salesRepId, superSalesRepBPartner.getC_BPartner_ID());

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
		bPartnerBL.setBPartnerSalesRepId(commissionCriteria.getBPartnerId(), commissionCriteria.getTopLevelSalesRepId());

		final InvoiceCandidateQuery invoiceCandidateQuery = InvoiceCandidateQuery.builder()
				.dateToInvoiceInterval(commissionCriteria.getTargetInterval())
				.salesRepBPartnerId(commissionCriteria.getBPartnerId())
				.build();

		final InvoiceCandidateMultiQuery multiQuery = InvoiceCandidateMultiQuery.builder().query(invoiceCandidateQuery).build();

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.getByQuery(multiQuery);

		Loggables.withLogger(logger, Level.DEBUG).addLog("*** DEBUG: recalculating commission for criteria: {}, found {} ICs",
														 commissionCriteria, invoiceCandidates.size());
		invoiceCandidates.forEach( invoiceCandidate -> {
			try
			{
				trxManager.runInNewTrx(() -> invoiceCandFacadeService.syncICToCommissionInstance(invoiceCandidate, false));
			}
			catch (final Exception e)
			{
				Loggables.withLogger(logger, Level.DEBUG)
						.addLog("recalculateCommissionForCriteria failed for IC_ID ={}: Error message {}",
								invoiceCandidate.getC_Invoice_Candidate_ID(), e.getLocalizedMessage());
			}
		});
	}

	private void processBPId(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final SalesRepRelatedBPTableInfo tableInfo,
			@NonNull final InstantInterval targetInterval)
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
