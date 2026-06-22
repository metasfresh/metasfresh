package de.metas.material.cockpit.stock;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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

/**
 * Guards against the me03 #30569 escalation: on intercheese PROD the (now-deactivated)
 * {@code MD_Stock_Update_From_M_HUs} reset loop drove {@code MD_Stock.QtyOnHand} to non-physical
 * 50–1267-digit values. This sanity guard is the backstop that prevents a stock correction from
 * persisting such a value, regardless of which amplifier produced it.
 */
class StockQtySanityGuardTest
{
	@Test
	void plausible_realWorldStockQuantities_areAccepted()
	{
		// the largest HU-derived truth seen in the incident data was ~28430; allow a vast margin
		assertThat(StockQtySanityGuard.isPlausibleQtyOnHand(BigDecimal.ZERO)).isTrue();
		assertThat(StockQtySanityGuard.isPlausibleQtyOnHand(new BigDecimal("28430"))).isTrue();
		assertThat(StockQtySanityGuard.isPlausibleQtyOnHand(new BigDecimal("-28430"))).isTrue();
		assertThat(StockQtySanityGuard.isPlausibleQtyOnHand(new BigDecimal("1000000000"))).isTrue(); // 1e9
	}

	@Test
	void nonPhysical_escalatedQuantities_areRejected()
	{
		// the exact 2026-06-22 failure mode: QtyOnHand escalated far beyond any physical stock
		assertThat(StockQtySanityGuard.isPlausibleQtyOnHand(new BigDecimal("1E40"))).isFalse();
		assertThat(StockQtySanityGuard.isPlausibleQtyOnHand(new BigDecimal("-1E40"))).isFalse();

		// a 1267-digit integer like the worst correction in PInstance 14047016
		final BigDecimal escalated = new BigDecimal("7905921439873659415476636832212631141029078893757275382149400337485652411120886665843404585035232329381195376075211625359289455509333683310327999370610632729835731551505839897952362125889161400420421792328883907564851401408088928249340993686396299550779771566188978082697707520");
		assertThat(StockQtySanityGuard.isPlausibleQtyOnHand(escalated)).isFalse();
	}
}
