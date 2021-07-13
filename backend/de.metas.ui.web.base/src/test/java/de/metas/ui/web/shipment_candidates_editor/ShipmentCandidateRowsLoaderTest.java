package de.metas.ui.web.shipment_candidates_editor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.Builder;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class ShipmentCandidateRowsLoaderTest
{
	private IShipmentScheduleBL shipmentScheduleBL;
	private IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	}

	@Builder(builderMethodName = "shipmentSchedule", builderClassName = "$ShipmentScheduleBuilder")
	private ShipmentScheduleId createShipmentScheduleId(
			final String qtyOrderedCU,
			final String qtyItemCapacity,
			final String qtyToDeliverCU)
	{
		final I_C_UOM uomEach = BusinessTestHelper.createUomEach();
		final I_M_Product product = BusinessTestHelper.createProduct("product", uomEach);

		I_C_BPartner customer = BusinessTestHelper.createBPartner("customer");

		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setM_Product_ID(product.getM_Product_ID());
		shipmentSchedule.setC_BPartner_ID(customer.getC_BPartner_ID());
		shipmentSchedule.setM_Warehouse_ID(1111);

		shipmentSchedule.setQtyOrdered_Calculated(qtyOrderedCU != null ? new BigDecimal(qtyOrderedCU) : null);
		shipmentSchedule.setQtyItemCapacity(qtyItemCapacity != null ? new BigDecimal(qtyItemCapacity) : null);

		shipmentSchedule.setQtyToDeliver(qtyToDeliverCU != null ? new BigDecimal(qtyToDeliverCU) : null);

		saveRecord(shipmentSchedule);

		return ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
	}

	private ShipmentCandidateRowsLoader newShipmentCandidateRowsLoader(final ShipmentScheduleId... shipmentScheduleIds)
	{
		return ShipmentCandidateRowsLoader.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				.shipmentScheduleEffectiveBL(shipmentScheduleEffectiveBL)
				.salesOrdersLookup(MockedLookupDataSource.withNamePrefix("salesOrder"))
				.customersLookup(MockedLookupDataSource.withNamePrefix("customer "))
				.warehousesLookup(MockedLookupDataSource.withNamePrefix("warehouse"))
				.productsLookup(MockedLookupDataSource.withNamePrefix("product"))
				.asiLookup(MockedLookupDataSource.withNamePrefix("ASI"))
				.catchUOMsLookup(MockedLookupDataSource.withNamePrefix("catchUOM"))
				.shipmentScheduleIds(ImmutableSet.copyOf(shipmentScheduleIds))
				.build();
	}

	@Nested
	public class extractPackingInfo
	{
		@Test
		public void withQtyItemCapacitySet()
		{
			final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
			sched.setQtyItemCapacity(new BigDecimal("3"));
			sched.setPackDescription("pack description");
			assertThat(ShipmentCandidateRowsLoader.extractPackingInfo(sched))
					.isEqualTo(PackingInfo.builder()
							.qtyCUsPerTU(new BigDecimal("3"))
							.description("pack description")
							.build());
		}

		@Test
		public void withoutQtyItemCapacitySet()
		{
			final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
			assertThat(ShipmentCandidateRowsLoader.extractPackingInfo(sched)).isSameAs(PackingInfo.NONE);
		}

		@Test
		public void withZeroQtyItemCapacitySet()
		{
			final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
			sched.setQtyItemCapacity(new BigDecimal("0"));
			assertThat(ShipmentCandidateRowsLoader.extractPackingInfo(sched)).isSameAs(PackingInfo.NONE);
		}
	}

	@Nested
	public class qtyOrdered
	{
		@Test
		public void with_1CUs_2CUsPerTU()
		{
			final ShipmentCandidateRowsLoader loader = newShipmentCandidateRowsLoader(
					shipmentSchedule().qtyOrderedCU("1").qtyItemCapacity("2").build() //
			);

			final List<ShipmentCandidateRow> rows = ImmutableList.copyOf(loader.load().getAllRows());
			assertThat(rows).hasSize(1);
			final ShipmentCandidateRow row = rows.get(0);
			assertThat(row.getQtyOrdered()).isEqualTo("1");
		}

		@Test
		public void with_3CUs_2CUsPerTU()
		{
			final ShipmentCandidateRowsLoader loader = newShipmentCandidateRowsLoader(
					shipmentSchedule().qtyOrderedCU("3").qtyItemCapacity("2").build() //
			);

			final List<ShipmentCandidateRow> rows = ImmutableList.copyOf(loader.load().getAllRows());
			assertThat(rows).hasSize(1);
			final ShipmentCandidateRow row = rows.get(0);
			assertThat(row.getQtyOrdered()).isEqualTo("2");
		}

		@Test
		public void with_3CUs_NoPackingInfo()
		{
			final ShipmentCandidateRowsLoader loader = newShipmentCandidateRowsLoader(
					shipmentSchedule().qtyOrderedCU("3").qtyItemCapacity(null).build() //
			);

			final List<ShipmentCandidateRow> rows = ImmutableList.copyOf(loader.load().getAllRows());
			assertThat(rows).hasSize(1);
			final ShipmentCandidateRow row = rows.get(0);
			assertThat(row.getQtyOrdered()).isEqualTo("3");
		}
	}

	@Nested
	public class qtyToDeliver
	{
		@Test
		public void with_1CUs_2CUsPerTU()
		{
			final ShipmentCandidateRowsLoader loader = newShipmentCandidateRowsLoader(
					shipmentSchedule().qtyOrderedCU("9999").qtyToDeliverCU("1").qtyItemCapacity("2").build() //
			);

			final List<ShipmentCandidateRow> rows = ImmutableList.copyOf(loader.load().getAllRows());
			assertThat(rows).hasSize(1);
			final ShipmentCandidateRow row = rows.get(0);
			assertThat(row.getQtyToDeliverUserEntered()).isEqualTo("1");
		}

		@Test
		public void with_3CUs_2CUsPerTU()
		{
			final ShipmentCandidateRowsLoader loader = newShipmentCandidateRowsLoader(
					shipmentSchedule().qtyOrderedCU("9999").qtyToDeliverCU("3").qtyItemCapacity("2").build() //
			);

			final List<ShipmentCandidateRow> rows = ImmutableList.copyOf(loader.load().getAllRows());
			assertThat(rows).hasSize(1);
			final ShipmentCandidateRow row = rows.get(0);
			assertThat(row.getQtyToDeliverUserEntered()).isEqualTo("2");
		}

		@Test
		public void with_3CUs_NoPackingInfo()
		{
			final ShipmentCandidateRowsLoader loader = newShipmentCandidateRowsLoader(
					shipmentSchedule().qtyOrderedCU("9999").qtyToDeliverCU("3").qtyItemCapacity(null).build() //
			);

			final List<ShipmentCandidateRow> rows = ImmutableList.copyOf(loader.load().getAllRows());
			assertThat(rows).hasSize(1);
			final ShipmentCandidateRow row = rows.get(0);
			assertThat(row.getQtyToDeliverUserEntered()).isEqualTo("3");
		}
	}
}
