package de.metas.bpartner.product.stats;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.event.SimpleObjectSerializer;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.util.time.SystemTime;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ReceiptEventsTest
{
	@Test
	public void test_InOutChangedEvent()
	{
		testSerializeDeserialize(InOutChangedEvent.builder()
				.bpartnerId(BPartnerId.ofRepoId(123))
				.movementDate(SystemTime.asInstant())
				.soTrx(SOTrx.SALES)
				.reversal(true)
				.productIds(ImmutableSet.of(ProductId.ofRepoId(1), ProductId.ofRepoId(2)))
				.build());
	}

	private void testSerializeDeserialize(final Object event)
	{
		final SimpleObjectSerializer serializer = SimpleObjectSerializer.get();

		final String json = serializer.serialize(event);
		// System.out.println("JSON\n" + json);

		final Object eventDeserialized = serializer.deserialize(json, event.getClass());

		assertThat(eventDeserialized).isEqualTo(event);
	}
}
