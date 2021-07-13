package de.metas.material.dispo.service.event.handler.receiptschedule;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.*;

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

	private DimensionService dimensionService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());
		dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepositoryRetrieval, candidateRepositoryWriteService);
		final Collection<CandidateHandler> candidateChangeHandlers = ImmutableList.of(new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService));
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(candidateChangeHandlers);

		receiptsScheduleCreatedHandler = new ReceiptsScheduleCreatedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
		receiptsScheduleUpdatedHandler = new ReceiptsScheduleUpdatedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
	}

	/** The update's timestamp is at the same date as the original creation (not sure how realisitic that is) */
	@Test
	public void handleEvent_ReceiptScheduleUpdatedEvent_update_NOW()
	{
		ReceiptsScheduleCreatedHandlerTest.handleEvent_ReceiptScheduleCreatedEvent_performTest(receiptsScheduleCreatedHandler);
		assertThat(DispoTestUtils.retrieveAllRecords()) // guard
				.extracting("DateProjected", "Qty", "MD_Candidate_Type")
				.containsExactly(
						tuple(TimeUtil.asTimestamp(NOW), TEN, CandidateType.SUPPLY.getCode()),
						tuple(TimeUtil.asTimestamp(NOW), TEN, CandidateType.STOCK.getCode()));

		final Instant updateTimestamp = NOW;
		createAndHandleUpdate(updateTimestamp);

		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY))
				.extracting("DateProjected", "Qty")
				.containsExactly(tuple(TimeUtil.asTimestamp(NOW), new BigDecimal("11")));
	}

	/** The update's timestamp is after the original creation. Verify that the actual candidate's the projected data did note change */
	@Test
	public void handleEvent_ReceiptScheduleUpdatedEvent_update_AFTER_NOW()
	{
		ReceiptsScheduleCreatedHandlerTest.handleEvent_ReceiptScheduleCreatedEvent_performTest(receiptsScheduleCreatedHandler);
		assertThat(DispoTestUtils.retrieveAllRecords()) // guard
				.extracting("DateProjected", "Qty", "MD_Candidate_Type")
				.containsExactly(
						tuple(TimeUtil.asTimestamp(NOW), TEN, CandidateType.SUPPLY.getCode()),
						tuple(TimeUtil.asTimestamp(NOW), TEN, CandidateType.STOCK.getCode()));

		final Instant updateTimestamp = AFTER_NOW;
		createAndHandleUpdate(updateTimestamp);

		assertThat(DispoTestUtils.retrieveAllRecords())
				.extracting("DateProjected", "Qty", "MD_Candidate_Type")
				.containsExactly(
						tuple(TimeUtil.asTimestamp(NOW), new BigDecimal("11"), CandidateType.SUPPLY.getCode()),
						tuple(TimeUtil.asTimestamp(NOW), new BigDecimal("11"), CandidateType.STOCK.getCode()));
	}

	private void createAndHandleUpdate(final Instant updateTimestamp)
	{
		final MaterialDescriptor eventMaterialDescriptor = createMaterialDescriptor()
				.withDate(updateTimestamp)
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
	}
}
