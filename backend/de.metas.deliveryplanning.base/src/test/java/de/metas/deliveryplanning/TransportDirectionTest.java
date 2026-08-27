/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning;

import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransportDirectionTest
{
	@Test
	void codes_matchTheReferenceListValues()
	{
		assertThat(TransportDirection.Incoming.getCode()).isEqualTo("Incoming");
		assertThat(TransportDirection.Outgoing.getCode()).isEqualTo("Outgoing");
		assertThat(TransportDirection.Dropship.getCode()).isEqualTo("Dropship");
	}

	@Test
	void ofCode_resolvesEveryReferenceListValue()
	{
		assertThat(TransportDirection.ofCode(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming)).isEqualTo(TransportDirection.Incoming);
		assertThat(TransportDirection.ofCode(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing)).isEqualTo(TransportDirection.Outgoing);
		assertThat(TransportDirection.ofCode(X_M_Delivery_Planning.TRANSPORTDIRECTION_Dropship)).isEqualTo(TransportDirection.Dropship);
	}

	@Test
	void hasReceipt()
	{
		assertThat(TransportDirection.Incoming.hasReceipt()).isTrue();
		assertThat(TransportDirection.Outgoing.hasReceipt()).isFalse();
		assertThat(TransportDirection.Dropship.hasReceipt()).isTrue();
	}

	@Test
	void hasShipment()
	{
		assertThat(TransportDirection.Incoming.hasShipment()).isFalse();
		assertThat(TransportDirection.Outgoing.hasShipment()).isTrue();
		assertThat(TransportDirection.Dropship.hasShipment()).isTrue();
	}

	@Test
	void isDropship()
	{
		assertThat(TransportDirection.Incoming.isDropship()).isFalse();
		assertThat(TransportDirection.Outgoing.isDropship()).isFalse();
		assertThat(TransportDirection.Dropship.isDropship()).isTrue();
	}
}
