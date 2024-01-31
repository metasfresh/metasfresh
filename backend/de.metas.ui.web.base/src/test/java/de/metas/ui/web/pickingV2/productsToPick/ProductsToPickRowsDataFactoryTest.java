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

package de.metas.ui.web.pickingV2.productsToPick;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.picking.PickingCandidateApprovalStatus;
import de.metas.handlingunits.picking.PickingCandidatePickStatus;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableList;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowType;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsData;
import de.metas.ui.web.pickingV2.productsToPick.rows.factory.ProductsToPickRowsDataFactory;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import lombok.Builder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class ProductsToPickRowsDataFactoryTest
{
	private PickingV2TestHelper testHelper;

	//
	// Master data
	private BPartnerLocationId customerAndLocationId;
	private ProductId productId;
	private I_C_UOM uomKg;
	private LocatorId locatorId;
	private static final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(3);

	@BeforeEach
	public void beforeEachTest()
	{
		AdempiereTestHelper.get().init();

		testHelper = new PickingV2TestHelper();

		customerAndLocationId = BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(2), 3);
		productId = testHelper.createProduct("product");
		{
			final I_M_Product product = load(productId, I_M_Product.class);
			product.setC_UOM_ID(testHelper.uomKg.getC_UOM_ID());
			save((product));
		}
		uomKg = testHelper.uomKg;

		final WarehouseId warehouseId = testHelper.createWarehouse();
		locatorId = testHelper.createLocator(warehouseId);
	}

	@Builder(builderMethodName = "preparePackageableRow", builderClassName = "PackageableRowBuilder")
	private PackageableRow createPackageableRow(
			final int qtyToDeliver,
			final int qtyPickedNotDelivered,
			@Nullable final ShipmentAllocationBestBeforePolicy bestBeforePolicy)
	{
		return PackageableRow.builder()
				.orderId(OrderId.ofRepoId(1))
				.orderDocumentNo("1234")
				.customer(IntegerLookupValue.of(customerAndLocationId.getBpartnerId().getRepoId(), "customer"))
				.timeZone(ZoneId.of("Pacific/Guadalcanal"))
				.packageables(PackageableList.of(
								Packageable.builder()
										.orgId(OrgId.ofRepoId(1))
										.shipmentScheduleId(shipmentScheduleId)
										//
										.qtyOrdered(Quantity.of(1000000, uomKg))
										.qtyToDeliver(Quantity.of(qtyToDeliver, uomKg))
										.qtyDelivered(Quantity.of(0, uomKg))
										.qtyPickedNotDelivered(Quantity.of(qtyPickedNotDelivered, uomKg))
										.qtyPickedPlanned(Quantity.of(0, uomKg))
										.qtyPickedAndDelivered(Quantity.of(0, uomKg))
										//
										.customerId(customerAndLocationId.getBpartnerId())
										.customerLocationId(customerAndLocationId)
										//
										.handoverLocationId(customerAndLocationId)
										//
										.warehouseId(locatorId.getWarehouseId())
										//
										.bestBeforePolicy(Optional.ofNullable(bestBeforePolicy))
										//
										.productId(productId)
										.asiId(AttributeSetInstanceId.NONE)
										//
										.build()
						)
				)
				.build();
	}

	@Nested
	public class testRowsOrder_BestBeforePolicy
	{
		@Test
		public void expiringFirst()
		{
			final HuId huId1 = testHelper.prepareExistingHU()
					.locatorId(locatorId)
					.bestBeforeDate(LocalDate.of(2019, Month.SEPTEMBER, 1))
					.productId(productId)
					.qty(Quantity.of(10, uomKg))
					.build();

			final HuId huId2 = testHelper.prepareExistingHU()
					.locatorId(locatorId)
					.bestBeforeDate(LocalDate.of(2019, Month.SEPTEMBER, 2))
					.productId(productId)
					.qty(Quantity.of(11, uomKg))
					.build();

			final HuId huId3 = testHelper.prepareExistingHU()
					.locatorId(locatorId)
					.bestBeforeDate(null)
					.productId(productId)
					.qty(Quantity.of(12, uomKg))
					.build();

			final PackageableRow packageableRow = preparePackageableRow()
					.qtyToDeliver(100)
					.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.Expiring_First)
					.build();

			final ProductsToPickRowsDataFactory productsToPickRowsDataFactory = testHelper.createProductsToPickRowsDataFactory();
			final ProductsToPickRowsData rowsData = productsToPickRowsDataFactory.create(packageableRow);

			final ImmutableList<ProductsToPickRow> rows = ImmutableList.copyOf(rowsData.getTopLevelRows());
			rows.forEach(row -> System.out.println("hu=" + row.getPickFromHUId() + ", exp=" + row.getExpiringDate() + ", qty=" + row.getQtyEffective() + " -- " + row));
			assertThat(rows).hasSize(4);

			final ProductsToPickRow row1 = rows.get(0);
			assertThat(row1.getType()).isEqualTo(ProductsToPickRowType.PICK_FROM_HU);
			assertThat(row1.getPickFromHUId()).isEqualTo(huId1);
			assertThat(row1.getQtyEffective()).isEqualTo(Quantity.of(10, uomKg));
			assertThat(row1.getExpiringDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 1));

			final ProductsToPickRow row2 = rows.get(1);
			assertThat(row2.getType()).isEqualTo(ProductsToPickRowType.PICK_FROM_HU);
			assertThat(row2.getPickFromHUId()).isEqualTo(huId2);
			assertThat(row2.getQtyEffective()).isEqualTo(Quantity.of(11, uomKg));
			assertThat(row2.getExpiringDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 2));

			final ProductsToPickRow row3 = rows.get(2);
			assertThat(row3.getType()).isEqualTo(ProductsToPickRowType.PICK_FROM_HU);
			assertThat(row3.getPickFromHUId()).isEqualTo(huId3);
			assertThat(row3.getQtyEffective()).isEqualTo(Quantity.of(12, uomKg));
			assertThat(row3.getExpiringDate()).isNull();

			final ProductsToPickRow row4 = rows.get(3);
			assertThat(row4.getType()).isEqualTo(ProductsToPickRowType.UNALLOCABLE);
			assertThat(row4.getPickFromHUId()).isNull();
			assertThat(row4.getQtyEffective()).isEqualTo(Quantity.of(100 - 10 - 11 - 12, uomKg));
			assertThat(row4.getExpiringDate()).isNull();
		}

		@Test
		public void expiringLast()
		{
			final HuId huId1 = testHelper.prepareExistingHU()
					.locatorId(locatorId)
					.bestBeforeDate(LocalDate.of(2019, Month.SEPTEMBER, 1))
					.productId(productId)
					.qty(Quantity.of(10, uomKg))
					.build();

			final HuId huId2 = testHelper.prepareExistingHU()
					.locatorId(locatorId)
					.bestBeforeDate(LocalDate.of(2019, Month.SEPTEMBER, 2))
					.productId(productId)
					.qty(Quantity.of(11, uomKg))
					.build();

			final HuId huId3 = testHelper.prepareExistingHU()
					.locatorId(locatorId)
					.bestBeforeDate(null)
					.productId(productId)
					.qty(Quantity.of(12, uomKg))
					.build();

			final PackageableRow packageableRow = preparePackageableRow()
					.qtyToDeliver(100)
					.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.Newest_First)
					.build();

			final ProductsToPickRowsDataFactory productsToPickRowsDataFactory = testHelper.createProductsToPickRowsDataFactory();
			final ProductsToPickRowsData rowsData = productsToPickRowsDataFactory.create(packageableRow);

			final ImmutableList<ProductsToPickRow> rows = ImmutableList.copyOf(rowsData.getTopLevelRows());
			rows.forEach(row -> System.out.println("hu=" + row.getPickFromHUId() + ", exp=" + row.getExpiringDate() + ", qty=" + row.getQtyEffective() + " -- " + row));
			assertThat(rows).hasSize(4);

			final ProductsToPickRow row1 = rows.get(0);
			assertThat(row1.getType()).isEqualTo(ProductsToPickRowType.PICK_FROM_HU);
			assertThat(row1.getPickFromHUId()).isEqualTo(huId2);
			assertThat(row1.getQtyEffective()).isEqualTo(Quantity.of(11, uomKg));
			assertThat(row1.getExpiringDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 2));

			final ProductsToPickRow row2 = rows.get(1);
			assertThat(row2.getType()).isEqualTo(ProductsToPickRowType.PICK_FROM_HU);
			assertThat(row2.getPickFromHUId()).isEqualTo(huId1);
			assertThat(row2.getQtyEffective()).isEqualTo(Quantity.of(10, uomKg));
			assertThat(row2.getExpiringDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 1));

			final ProductsToPickRow row3 = rows.get(2);
			assertThat(row3.getType()).isEqualTo(ProductsToPickRowType.PICK_FROM_HU);
			assertThat(row3.getPickFromHUId()).isEqualTo(huId3);
			assertThat(row3.getQtyEffective()).isEqualTo(Quantity.of(12, uomKg));
			assertThat(row3.getExpiringDate()).isNull();

			final ProductsToPickRow row4 = rows.get(3);
			assertThat(row4.getType()).isEqualTo(ProductsToPickRowType.UNALLOCABLE);
			assertThat(row4.getPickFromHUId()).isNull();
			assertThat(row4.getQtyEffective()).isEqualTo(Quantity.of(100 - 10 - 11 - 12, uomKg));
			assertThat(row4.getExpiringDate()).isNull();
		}
	}

	@Test
	public void test_AlreadyExistingPickingCandidate()
	{
		final HuId huId1 = testHelper.prepareExistingHU()
				.locatorId(locatorId)
				.productId(productId)
				.qty(Quantity.of(999, uomKg))
				.build();

		final I_M_Picking_Candidate pickingCandidateRecord = newInstance(I_M_Picking_Candidate.class);
		pickingCandidateRecord.setM_ShipmentSchedule_ID(shipmentScheduleId.getRepoId());
		pickingCandidateRecord.setStatus(PickingCandidateStatus.Draft.getCode());
		pickingCandidateRecord.setPickStatus(PickingCandidatePickStatus.PICKED.getCode());
		pickingCandidateRecord.setApprovalStatus(PickingCandidateApprovalStatus.TO_BE_APPROVED.getCode());
		pickingCandidateRecord.setQtyPicked(new BigDecimal("5"));
		pickingCandidateRecord.setC_UOM_ID(uomKg.getC_UOM_ID());
		pickingCandidateRecord.setPickFrom_HU_ID(huId1.getRepoId());
		saveRecord(pickingCandidateRecord);

		final PackageableRow packageableRow = preparePackageableRow()
				.qtyToDeliver(5)
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.Expiring_First)
				.build();

		final ProductsToPickRowsDataFactory productsToPickRowsDataFactory = testHelper.createProductsToPickRowsDataFactory();
		final Collection<ProductsToPickRow> rows = productsToPickRowsDataFactory.create(packageableRow).getTopLevelRows();

		assertThat(rows).hasSize(1);

		final ProductsToPickRow row = rows.iterator().next();
		assertThat(row.getQtyEffective()).isEqualTo(Quantity.of(5, uomKg));
		assertThat(row.getType()).isEqualTo(ProductsToPickRowType.PICK_FROM_HU);
		assertThat(row.getPickFromHUId()).isEqualTo(huId1);
	}
}
