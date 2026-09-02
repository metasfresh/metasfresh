/*
 * #%L
 * de.metas.business
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

package de.metas.shipping;

import de.metas.lang.SOTrx;
import de.metas.shipping.model.X_M_ShipperTransportation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransportDirectionTest
{
	@Test
	void ofSOTrx_mapsTheTwoValuedDocumentNature()
	{
		assertThat(TransportDirection.ofSOTrx(SOTrx.SALES)).isEqualTo(TransportDirection.Outgoing);
		assertThat(TransportDirection.ofSOTrx(SOTrx.PURCHASE)).isEqualTo(TransportDirection.Incoming);
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

	/**
	 * The contract that makes {@code isOutgoing()} worth having next to {@link TransportDirection#hasShipment()}:
	 * it is STRICTLY Outgoing, so a Dropship - which does have a shipment - is false here.
	 */
	@Test
	void isOutgoing()
	{
		assertThat(TransportDirection.Outgoing.isOutgoing()).isTrue();
		assertThat(TransportDirection.Dropship.isOutgoing())
				.as("a dropship has a shipment but is NOT Outgoing - that is the whole difference to hasShipment()")
				.isFalse();
		assertThat(TransportDirection.Incoming.isOutgoing()).isFalse();
	}

	/**
	 * {@code M_Delivery_Planning.TransportDirection} and {@code M_ShipperTransportation.TransportDirection} are both
	 * NOT NULL, so an unset direction reaches java as the empty string rather than as null - blank has to resolve the
	 * same way null does, or every caller that reads an unset direction throws.
	 */
	@Test
	void ofNullableCode_resolvesBlankTheSameWayAsNull()
	{
		assertThat(TransportDirection.ofNullableCode(null)).isNull();
		assertThat(TransportDirection.ofNullableCode("")).isNull();
		assertThat(TransportDirection.ofNullableCode(" ")).isNull();
		assertThat(TransportDirection.ofNullableCode(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming))
				.isEqualTo(TransportDirection.Incoming);

		assertThat(TransportDirection.ofNullableCode(null, TransportDirection.Outgoing)).isEqualTo(TransportDirection.Outgoing);
		assertThat(TransportDirection.ofNullableCode("", TransportDirection.Outgoing)).isEqualTo(TransportDirection.Outgoing);
		assertThat(TransportDirection.ofNullableCode(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming, TransportDirection.Outgoing))
				.isEqualTo(TransportDirection.Incoming);
	}

	@Test
	void isDropship()
	{
		assertThat(TransportDirection.Incoming.isDropship()).isFalse();
		assertThat(TransportDirection.Outgoing.isDropship()).isFalse();
		assertThat(TransportDirection.Dropship.isDropship()).isTrue();
	}

	/**
	 * The direction fact behind {@link TransportDirection#hasReceipt()}: true for {@link TransportDirection#Incoming}
	 * and {@link TransportDirection#Dropship}.
	 */
	@Test
	void isInbound()
	{
		assertThat(TransportDirection.Incoming.isInbound()).isTrue();
		assertThat(TransportDirection.Outgoing.isInbound()).isFalse();
		assertThat(TransportDirection.Dropship.isInbound()).isTrue();
	}

	/**
	 * The direction fact behind {@link TransportDirection#hasShipment()}: true for {@link TransportDirection#Outgoing}
	 * and {@link TransportDirection#Dropship}.
	 */
	@Test
	void isOutbound()
	{
		assertThat(TransportDirection.Incoming.isOutbound()).isFalse();
		assertThat(TransportDirection.Outgoing.isOutbound()).isTrue();
		assertThat(TransportDirection.Dropship.isOutbound()).isTrue();
	}
}
