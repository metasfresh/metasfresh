/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.material.dispo.service.event.handler.receiptschedule;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Collection;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.newMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class ReceiptsScheduleDeletedHandlerTest
{
	private ReceiptsScheduleDeletedHandler receiptsScheduleDeletedHandler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateQtyDetailsRepository candidateQtyDetailsRepository = new CandidateQtyDetailsRepository();
		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo, candidateRepositoryRetrieval, candidateQtyDetailsRepository);
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepositoryRetrieval, candidateRepositoryWriteService);
		final Collection<CandidateHandler> candidateChangeHandlers = ImmutableList.of(new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService));
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(candidateChangeHandlers);

		receiptsScheduleDeletedHandler = new ReceiptsScheduleDeletedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
	}

	@Test
	public void handleEvent_isDropShipWarehouse_shortCircuits()
	{
		final ReceiptScheduleDeletedEvent event = ReceiptScheduleDeletedEvent
				.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.materialDescriptor(newMaterialDescriptor().withDate(NOW))
				.receiptScheduleId(ReceiptsScheduleCreatedHandlerTest.RECEIPT_SCHEDULE_ID)
				.reservedQuantity(new BigDecimal("10"))
				.isIgnoreInMaterialDispo(true)
				.build();

		receiptsScheduleDeletedHandler.handleEvent(event);

		// dropship-warehouse receipts bypass material-disposition entirely — no candidates of any type are created.
		assertThat(DispoTestUtils.retrieveAllRecords()).isEmpty();
	}
}
