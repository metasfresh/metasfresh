/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the collector to {@code ScheduledPackageableList.getSingleValue}'s semantics — nulls ignored,
 * two different values collapse to null. The STARTED half of the launcher list goes through
 * {@code getSingleValue}; if this half disagreed, a work item would show one external system before
 * it is started and another after, which is exactly the display/filter mismatch the feature exists
 * to avoid.
 */
@ExtendWith(AdempiereTestWatcher.class)
class SingleExternalSystemCollectorTest
{
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);
	private static final ExternalSystemId SHOPWARE = ExternalSystemId.ofRepoId(540007);
	private static final ExternalSystemId WOO = ExternalSystemId.ofRepoId(540003);

	private final AtomicInteger nextShipmentScheduleId = new AtomicInteger(1);

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void noItems_isNull()
	{
		assertThat(new SingleExternalSystemCollector().getSingleExternalSystemIdOrNull()).isNull();
	}

	@Test
	void oneSystemThroughout_isThatSystem()
	{
		assertThat(collect(SHOPWARE, SHOPWARE)).isEqualTo(SHOPWARE);
	}

	@Test
	void twoDifferentSystems_isNull()
	{
		assertThat(collect(SHOPWARE, WOO)).isNull();
	}

	/** Nulls are ignored, not treated as a third value — matching getSingleValue. */
	@Test
	void oneSystemAndOneWithout_isThatSystem()
	{
		assertThat(collect(SHOPWARE, null)).isEqualTo(SHOPWARE);
		assertThat(collect(null, SHOPWARE)).isEqualTo(SHOPWARE);
	}

	@Test
	void noneHasOne_isNull()
	{
		assertThat(collect(null, null)).isNull();
	}

	/** Once diverged, a later repeat of the first value must not resurrect it. */
	@Test
	void divergenceIsSticky()
	{
		assertThat(collect(SHOPWARE, WOO, SHOPWARE)).isNull();
	}

	@Nullable
	private ExternalSystemId collect(@Nullable final ExternalSystemId... externalSystemIds)
	{
		final SingleExternalSystemCollector collector = new SingleExternalSystemCollector();
		for (final ExternalSystemId externalSystemId : externalSystemIds)
		{
			collector.collect(scheduledPackageable(externalSystemId));
		}
		return collector.getSingleExternalSystemIdOrNull();
	}

	private ScheduledPackageable scheduledPackageable(@Nullable final ExternalSystemId externalSystemId)
	{
		final I_C_UOM uomRecord = newInstanceOutOfTrx(I_C_UOM.class);
		uomRecord.setUOMSymbol("PCE");
		final Quantity zero = Quantity.zero(uomRecord);

		return ScheduledPackageable.ofPackageable(Packageable.builder()
				.orgId(ORG_ID)
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(nextShipmentScheduleId.getAndIncrement()))
				.qtyOrdered(zero)
				.qtyToDeliver(zero)
				.qtyDelivered(zero)
				.qtyPickedAndDelivered(zero)
				.qtyPickedNotDelivered(zero)
				.qtyPickedPlanned(zero)
				.customerId(BPartnerId.ofRepoId(1))
				.customerLocationId(BPartnerLocationId.ofRepoId(1, 1))
				.handoverLocationId(BPartnerLocationId.ofRepoId(1, 1))
				.warehouseId(WarehouseId.ofRepoId(1))
				.bestBeforePolicy(Optional.of(ShipmentAllocationBestBeforePolicy.Expiring_First))
				.productId(ProductId.ofRepoId(1))
				.productValueAndName(ProductValueAndName.of("P1", TranslatableStrings.constant("Product 1")))
				.asiId(AttributeSetInstanceId.NONE)
				.externalSystemId(externalSystemId)
				.build());
	}
}
