package de.metas.material.cockpit.view.eventhandler;

import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

import static de.metas.material.event.EventTestHelper.newMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/*
 * #%L
 * metasfresh-material-cockpit
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

public class AbstractStockEstimateHandlerTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * A {@link StockEstimateCreatedEvent} may carry a {@code null} qtyStockEstimateSeqNo (it is
	 * {@code @Nullable}, and the cucumber scenarios in materialDispo_stockEstimateEvent.feature /
	 * forecast_corner_cases.feature post exactly such events). The created-vs-deleted ternary in
	 * {@code createDataUpdateRequestForEvent} must not auto-unbox that value to {@code int} — else it
	 * throws a {@link NullPointerException} on the material-event async thread (logged to AD_Issue).
	 * The downstream {@link MainDataRequestHandler} already null-tolerates the seqNo, so null must
	 * flow through unchanged.
	 */
	@Test
	public void handleEvent_createdEventWithNullSeqNo_doesNotThrow_andPassesNullThrough()
	{
		final AtomicReference<UpdateMainDataRequest> captured = new AtomicReference<>();
		final MainDataRequestHandler capturingHandler = new MainDataRequestHandler()
		{
			@Override
			public void handleDataUpdateRequest(final UpdateMainDataRequest dataUpdateRequest)
			{
				captured.set(dataUpdateRequest);
			}
		};
		final AbstractStockEstimateHandler handler = new AbstractStockEstimateHandler(capturingHandler);

		final StockEstimateCreatedEvent event = StockEstimateCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(0, 0)) // org 0 (ANY) -> getTimeZone() uses SystemTime, no DB record needed
				.materialDescriptor(newMaterialDescriptor())
				.date(Instant.parse("2021-06-23T00:00:00Z"))
				.eventDate(Instant.parse("2021-06-23T00:00:00Z"))
				.qtyStockEstimateSeqNo(null) // the NPE trigger
				.build();

		assertThatCode(() -> handler.handleEvent(event)).doesNotThrowAnyException();
		assertThat(captured.get()).isNotNull();
		assertThat(captured.get().getQtyStockEstimateSeqNo()).isNull();
	}

	/**
	 * The deleted-event branch of the same ternary must keep yielding seqNo 0 (it already did; this
	 * pins that contract so the NPE fix cannot regress it).
	 */
	@Test
	public void handleEvent_deletedEvent_passesSeqNoZero()
	{
		final AtomicReference<UpdateMainDataRequest> captured = new AtomicReference<>();
		final MainDataRequestHandler capturingHandler = new MainDataRequestHandler()
		{
			@Override
			public void handleDataUpdateRequest(final UpdateMainDataRequest dataUpdateRequest)
			{
				captured.set(dataUpdateRequest);
			}
		};
		final AbstractStockEstimateHandler handler = new AbstractStockEstimateHandler(capturingHandler);

		final StockEstimateDeletedEvent event = StockEstimateDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(0, 0))
				.materialDescriptor(newMaterialDescriptor())
				.date(Instant.parse("2021-06-23T00:00:00Z"))
				.eventDate(Instant.parse("2021-06-23T00:00:00Z"))
				.build();

		assertThatCode(() -> handler.handleEvent(event)).doesNotThrowAnyException();
		assertThat(captured.get()).isNotNull();
		assertThat(captured.get().getQtyStockEstimateSeqNo()).isZero();
	}
}
