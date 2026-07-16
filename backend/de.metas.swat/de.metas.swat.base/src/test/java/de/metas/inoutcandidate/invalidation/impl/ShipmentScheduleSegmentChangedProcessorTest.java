package de.metas.inoutcandidate.invalidation.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * RED test proving that {@link ShipmentScheduleSegmentChangedProcessor} does NOT dedupe accumulated segments.
 * <p>
 * The processor accumulates segments into a plain {@code ArrayList} and, on trx commit (AFTER_COMMIT listener),
 * flushes the whole list to {@link ShipmentScheduleInvalidateBL#flagSegmentForRecompute(Collection)}.
 * Feeding the same value N times should collapse to a single distinct segment, but the ArrayList keeps all N.
 */
class ShipmentScheduleSegmentChangedProcessorTest
{
	private static final int N = 1000;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Adding the SAME segment value N times must be deduped down to a single distinct segment.
	 * <p>
	 * FAILS on current ArrayList-based accumulator (retains all {@value #N}).
	 */
	@Test
	void sameSegmentAddedManyTimes_isDedupedToOne()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);

		final ImmutableShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder()
				.productId(1)
				.bpartnerId(2)
				.locatorId(3)
				.build();

		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			final ShipmentScheduleSegmentChangedProcessor processor =
					ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor)
					.as("processor must be created inside a thread-inherited trx")
					.isNotNull();

			for (int i = 0; i < N; i++)
			{
				processor.addSegment(segment);
			}
		});

		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator).flagSegmentForRecompute(captor.capture());

		assertThat(captor.getValue())
				.as("adding the same segment %d times must be deduped to a single distinct segment", N)
				.hasSize(1);
	}

	/**
	 * N DISTINCT segment values must all be retained (proves dedupe, not truncation).
	 * <p>
	 * PASSES on current code.
	 */
	@Test
	void distinctSegments_areAllRetained()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);

		final List<IShipmentScheduleSegment> distinctSegments = new ArrayList<>();
		for (int i = 0; i < N; i++)
		{
			distinctSegments.add(ImmutableShipmentScheduleSegment.builder()
					.productId(i)
					.bpartnerId(2)
					.locatorId(3)
					.build());
		}

		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			final ShipmentScheduleSegmentChangedProcessor processor =
					ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor)
					.as("processor must be created inside a thread-inherited trx")
					.isNotNull();

			processor.addSegments(distinctSegments);
		});

		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator).flagSegmentForRecompute(captor.capture());

		assertThat(captor.getValue())
				.as("all %d distinct segments must be retained", N)
				.hasSize(N);
	}
}
