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
		assertThat(TransportDirection.ofCode(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming)).isEqualTo(TransportDirection.Incoming);
		assertThat(TransportDirection.ofCode(X_M_ShipperTransportation.TRANSPORTDIRECTION_Outgoing)).isEqualTo(TransportDirection.Outgoing);
		assertThat(TransportDirection.ofCode(X_M_ShipperTransportation.TRANSPORTDIRECTION_Dropship)).isEqualTo(TransportDirection.Dropship);
	}

	/**
	 * One enum now serves both tables, so the two generated constant sets must stay identical. They are
	 * generated from the same {@code AD_Reference_Value_ID=541689}; this pins that, so a future migration
	 * that changes only one of them fails here instead of silently splitting the domain in two.
	 */
	@Test
	void bothGeneratedModelsCarryTheSameCodes()
	{
		assertThat(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming).isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming);
		assertThat(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing).isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Outgoing);
		assertThat(X_M_Delivery_Planning.TRANSPORTDIRECTION_Dropship).isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Dropship);
	}

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

	@Test
	void isDropship()
	{
		assertThat(TransportDirection.Incoming.isDropship()).isFalse();
		assertThat(TransportDirection.Outgoing.isDropship()).isFalse();
		assertThat(TransportDirection.Dropship.isDropship()).isTrue();
	}
}
