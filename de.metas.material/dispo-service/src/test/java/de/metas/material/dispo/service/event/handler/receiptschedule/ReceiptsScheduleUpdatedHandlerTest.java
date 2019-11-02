package de.metas.material.dispo.service.event.handler.receiptschedule;

import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2018 metas GmbH
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
@ExtendWith(AdempiereTestWatcher.class)
public class ReceiptsScheduleUpdatedHandlerTest
{
	private ReceiptsScheduleCreatedHandler receiptsScheduleCreatedHandler;
	private ReceiptsScheduleUpdatedHandler receiptsScheduleUpdatedHandler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService();
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepositoryRetrieval, candidateRepositoryWriteService);
		final Collection<CandidateHandler> candidateChangeHandlers = ImmutableList.of(new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService));
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(candidateChangeHandlers);

		receiptsScheduleCreatedHandler = new ReceiptsScheduleCreatedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
		receiptsScheduleUpdatedHandler = new ReceiptsScheduleUpdatedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
	}

	@Test
	public void handleEvent_ReceiptScheduleUpdatedEvent()
	{
		ReceiptsScheduleCreatedHandlerTest.handleEvent_ReceiptScheduleCreatedEvent_performTest(receiptsScheduleCreatedHandler);
		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY))
				.hasSize(1)
				.element(0)
				.returns(TimeUtil.asTimestamp(NOW), I_MD_Candidate::getDateProjected);

		final MaterialDescriptor eventMaterialDescriptor = createMaterialDescriptor()
				.withDate(BEFORE_NOW)
				.withQuantity(new BigDecimal("11"));

		final ReceiptScheduleUpdatedEvent receiptScheduleUpdatedEvent = ReceiptScheduleUpdatedEvent
				.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.materialDescriptor(eventMaterialDescriptor)
				.receiptScheduleId(ReceiptsScheduleCreatedHandlerTest.RECEIPT_SCHEDULE_ID)
				.reservedQuantity(new BigDecimal("11"))
				.reservedQuantityDelta(ONE)
				.orderedQuantityDelta(ONE)
				.build()
				.validate();

		receiptsScheduleUpdatedHandler.handleEvent(receiptScheduleUpdatedEvent);

		final List<I_MD_Candidate> supplyCandidates = DispoTestUtils.filter(CandidateType.SUPPLY);
		assertThat(supplyCandidates).hasSize(1);
		final I_MD_Candidate supplyCandidate = supplyCandidates.get(0);
		assertThat(supplyCandidate.getDateProjected()).isEqualTo(TimeUtil.asTimestamp(BEFORE_NOW));
		assertThat(supplyCandidate.getQty()).isEqualByComparingTo("11");
	}
}
