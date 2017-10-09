package de.metas.material.dispo.service.event.handler;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ShipmentScheduleEvent;
import lombok.NonNull;
import mockit.Mocked;

/*
 * #%L
 * metasfresh-material-dispo-service
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

public class ShipmentScheduleEventHandlerTests
{
	private static final int orderLineId = 86;

	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	public static final Date t0 = SystemTime.asDate();

	private static final Date t1 = TimeUtil.addMinutes(t0, 10);

	private static final int toWarehouseId = 30;

	private static final int productId = 40;

	private I_AD_Org org;

	@Mocked
	private MaterialEventService materialEventService;

	private ShipmentScheduleEventHandler shipmentScheduleEventHandler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		org = newInstance(I_AD_Org.class);
		save(org);

		final CandidateRepository candidateRepository = new CandidateRepository();

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(candidateRepository, new StockCandidateService(candidateRepository), materialEventService);

		shipmentScheduleEventHandler = new ShipmentScheduleEventHandler(candidateChangeHandler);
	}

	@Test
	public void testShipmentScheduleEvent()
	{
		final ShipmentScheduleEvent event = createShipmentScheduleTestEvent(org);
		shipmentScheduleEventHandler.handleShipmentScheduleEvent(event);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		assertThat(DispoTestUtils.filter(Type.DEMAND)).hasSize(1);
		assertThat(DispoTestUtils.filter(Type.STOCK)).hasSize(1);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);

		assertThat(demandRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo() - 1); // the demand record shall be displayed first
		assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

		assertThat(demandRecord.getQty()).isEqualByComparingTo("10");
		assertThat(stockRecord.getQty()).isEqualByComparingTo("-10"); // the stock is unbalanced, because there is no existing stock and no supply
	}

	public static ShipmentScheduleEvent createShipmentScheduleTestEvent(@NonNull final I_AD_Org org)
	{
		final ShipmentScheduleEvent event = ShipmentScheduleEvent.builder()
				.eventDescr(new EventDescr(org.getAD_Client_ID(), org.getAD_Org_ID()))
				.materialDescr(MaterialDescriptor.builder()
						.date(t1)
						.productId(productId)
						.quantity(BigDecimal.TEN)
						.warehouseId(toWarehouseId)
						.build())
				.reference(TableRecordReference.of("someTable", 4))
				.orderLineId(orderLineId)
				.build();
		return event;
	}
}
