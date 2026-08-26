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

class DeliveryPlanningTypeTest
{
	@Test
	void codes_matchTheReferenceListValues()
	{
		assertThat(DeliveryPlanningType.Incoming.getCode()).isEqualTo("Incoming");
		assertThat(DeliveryPlanningType.Outgoing.getCode()).isEqualTo("Outgoing");
		assertThat(DeliveryPlanningType.Dropship.getCode()).isEqualTo("Dropship");
	}

	@Test
	void ofCode_resolvesEveryReferenceListValue()
	{
		assertThat(DeliveryPlanningType.ofCode(X_M_Delivery_Planning.M_DELIVERY_PLANNING_TYPE_Incoming)).isEqualTo(DeliveryPlanningType.Incoming);
		assertThat(DeliveryPlanningType.ofCode(X_M_Delivery_Planning.M_DELIVERY_PLANNING_TYPE_Outgoing)).isEqualTo(DeliveryPlanningType.Outgoing);
		assertThat(DeliveryPlanningType.ofCode(X_M_Delivery_Planning.M_DELIVERY_PLANNING_TYPE_Dropship)).isEqualTo(DeliveryPlanningType.Dropship);
	}

	@Test
	void hasReceipt()
	{
		assertThat(DeliveryPlanningType.Incoming.hasReceipt()).isTrue();
		assertThat(DeliveryPlanningType.Outgoing.hasReceipt()).isFalse();
		assertThat(DeliveryPlanningType.Dropship.hasReceipt()).isTrue();
	}

	@Test
	void hasShipment()
	{
		assertThat(DeliveryPlanningType.Incoming.hasShipment()).isFalse();
		assertThat(DeliveryPlanningType.Outgoing.hasShipment()).isTrue();
		assertThat(DeliveryPlanningType.Dropship.hasShipment()).isTrue();
	}

	@Test
	void isDropship()
	{
		assertThat(DeliveryPlanningType.Incoming.isDropship()).isFalse();
		assertThat(DeliveryPlanningType.Outgoing.isDropship()).isFalse();
		assertThat(DeliveryPlanningType.Dropship.isDropship()).isTrue();
	}
}
