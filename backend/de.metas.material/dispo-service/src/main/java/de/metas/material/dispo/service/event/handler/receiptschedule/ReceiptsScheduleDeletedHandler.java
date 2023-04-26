package de.metas.material.dispo.service.event.handler.receiptschedule;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.PurchaseDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

/*
 * #%L
 * metasfresh-material-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class ReceiptsScheduleDeletedHandler
		implements MaterialEventHandler<ReceiptScheduleDeletedEvent>
{
	private static final Logger logger = LogManager.getLogger(ReceiptsScheduleDeletedHandler.class);

	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public ReceiptsScheduleDeletedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@Override
	public Collection<Class<? extends ReceiptScheduleDeletedEvent>> getHandledEventType()
	{
		return ImmutableList.of(ReceiptScheduleDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ReceiptScheduleDeletedEvent event)
	{
		final CandidatesQuery query = ReceiptsScheduleHandlerUtil.queryByReceiptScheduleId(event);
		final Candidate candidateToDelete = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(query);
		if (candidateToDelete == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No deletable candidate found; query={}", query);
			return;
		}

		final BigDecimal actualQty = candidateToDelete.computeActualQty();
		final boolean candidateHasTransactions = actualQty.signum() > 0;
		if (candidateHasTransactions)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("candidateId={} for the deleted receiptScheduleId={} already has actual trasactions",
					candidateToDelete.getId(), event.getReceiptScheduleId());
			candidateChangeHandler.onCandidateNewOrChange(candidateToDelete.withQuantity(actualQty));
		}
		else
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("candidateId={} for the deleted receiptScheduleId={} can be deleted",
					candidateToDelete.getId(), event.getReceiptScheduleId());
			candidateChangeHandler.onCandidateDelete(candidateToDelete);
		}
	}

	protected CandidatesQuery createCandidatesQuery(@NonNull final ReceiptScheduleDeletedEvent deletedEvent)
	{

		final PurchaseDetailsQuery purchaseDetailsQuery = PurchaseDetailsQuery.builder()
				.receiptScheduleRepoId(deletedEvent.getReceiptScheduleId())
				.build();

		return CandidatesQuery.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PURCHASE)
				.purchaseDetailsQuery(purchaseDetailsQuery)
				.build();
	}
}
