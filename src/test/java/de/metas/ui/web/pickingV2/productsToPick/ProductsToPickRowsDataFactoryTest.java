package de.metas.ui.web.pickingV2.productsToPick;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsData;
import de.metas.ui.web.pickingV2.productsToPick.rows.factory.ProductsToPickRowsDataFactory;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

public class ProductsToPickRowsDataFactoryTest
{
	private PickingV2TestHelper testHelper;

	//
	// Master data
	private BPartnerLocationId customerAndLocationId;
	private ProductId productId;
	private I_C_UOM uomKg;
	private LocatorId locatorId;

	@BeforeEach
	public void beforeEachTest()
	{
		AdempiereTestHelper.get().init();

		testHelper = new PickingV2TestHelper();

		customerAndLocationId = BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(2), 3);
		productId = testHelper.createProduct("product");
		uomKg = testHelper.uomKg;
		final WarehouseId warehouseId = testHelper.createWarehouse();
		locatorId = testHelper.createLocator(warehouseId);
	}

	private BPartnerBL createAndRegisterBPartnerBL()
	{
		final UserRepository userRepository = new UserRepository();
		final BPartnerBL bpartnersService = new BPartnerBL(userRepository);
		Services.registerService(IBPartnerBL.class, bpartnersService);
		return bpartnersService;
	}

	private ProductsToPickRowsDataFactory createProductsToPickRowsDataFactory()
	{
		final IBPartnerBL bpartnersService = createAndRegisterBPartnerBL();

		final HUReservationRepository huReservationRepository = new HUReservationRepository();
		final HUReservationService huReservationService = new HUReservationService(huReservationRepository);

		final PickingCandidateRepository pickingCandidateRepository = new PickingCandidateRepository();

		final HUTraceRepository huTraceRepository = new HUTraceRepository();
		final HuId2SourceHUsService sourceHUsRepository = new HuId2SourceHUsService(huTraceRepository);

		final PickingCandidateService pickingCandidateService = new PickingCandidateService(pickingCandidateRepository, sourceHUsRepository);

		return ProductsToPickRowsDataFactory.builder()
				.bpartnersService(bpartnersService)
				.huReservationService(huReservationService)
				.pickingCandidateService(pickingCandidateService)
				.locatorLookup(testHelper::locatorLookupById)
				.build();
	}

	@Test
	public void testRowsOrder_BestBeforePolicy_Expiring_First()
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
				.qtyOrdered(Quantity.of(100, uomKg))
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.Expiring_First)
				.build();

		final ProductsToPickRowsDataFactory productsToPickRowsDataFactory = createProductsToPickRowsDataFactory();
		final ProductsToPickRowsData rowsData = productsToPickRowsDataFactory.create(packageableRow);

		final ImmutableList<ProductsToPickRow> rows = ImmutableList.copyOf(rowsData.getTopLevelRows());
		rows.forEach(row -> System.out.println("hu=" + row.getHuId() + ", exp=" + row.getExpiringDate() + ", qty=" + row.getQtyEffective() + " -- " + row));
		assertThat(rows).hasSize(4);

		final ProductsToPickRow row1 = rows.get(0);
		assertThat(row1.getHuId()).isEqualTo(huId1);
		assertThat(row1.getQtyEffective()).isEqualTo(Quantity.of(10, uomKg));
		assertThat(row1.getExpiringDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 1));

		final ProductsToPickRow row2 = rows.get(1);
		assertThat(row2.getHuId()).isEqualTo(huId2);
		assertThat(row2.getQtyEffective()).isEqualTo(Quantity.of(11, uomKg));
		assertThat(row2.getExpiringDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 2));

		final ProductsToPickRow row3 = rows.get(2);
		assertThat(row3.getHuId()).isEqualTo(huId3);
		assertThat(row3.getQtyEffective()).isEqualTo(Quantity.of(12, uomKg));
		assertThat(row3.getExpiringDate()).isNull();

		final ProductsToPickRow row4 = rows.get(3);
		assertThat(row4.getHuId()).isNull();
		assertThat(row4.getQtyEffective()).isEqualTo(Quantity.of(100 - 10 - 11 - 12, uomKg));
		assertThat(row4.getExpiringDate()).isNull();
	}

	@Test
	public void testRowsOrder_BestBeforePolicy_Expiring_Last()
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
				.qtyOrdered(Quantity.of(100, uomKg))
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.Newest_First)
				.build();

		final ProductsToPickRowsDataFactory productsToPickRowsDataFactory = createProductsToPickRowsDataFactory();
		final ProductsToPickRowsData rowsData = productsToPickRowsDataFactory.create(packageableRow);

		final ImmutableList<ProductsToPickRow> rows = ImmutableList.copyOf(rowsData.getTopLevelRows());
		rows.forEach(row -> System.out.println("hu=" + row.getHuId() + ", exp=" + row.getExpiringDate() + ", qty=" + row.getQtyEffective() + " -- " + row));
		assertThat(rows).hasSize(4);

		final ProductsToPickRow row1 = rows.get(0);
		assertThat(row1.getHuId()).isEqualTo(huId2);
		assertThat(row1.getQtyEffective()).isEqualTo(Quantity.of(11, uomKg));
		assertThat(row1.getExpiringDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 2));

		final ProductsToPickRow row2 = rows.get(1);
		assertThat(row2.getHuId()).isEqualTo(huId1);
		assertThat(row2.getQtyEffective()).isEqualTo(Quantity.of(10, uomKg));
		assertThat(row2.getExpiringDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 1));

		final ProductsToPickRow row3 = rows.get(2);
		assertThat(row3.getHuId()).isEqualTo(huId3);
		assertThat(row3.getQtyEffective()).isEqualTo(Quantity.of(12, uomKg));
		assertThat(row3.getExpiringDate()).isNull();

		final ProductsToPickRow row4 = rows.get(3);
		assertThat(row4.getHuId()).isNull();
		assertThat(row4.getQtyEffective()).isEqualTo(Quantity.of(100 - 10 - 11 - 12, uomKg));
		assertThat(row4.getExpiringDate()).isNull();
	}

	@Builder(builderMethodName = "preparePackageableRow", builderClassName = "PackageableRowBuilder")
	private PackageableRow createPackageableRow(
			@NonNull final Quantity qtyOrdered,
			@Nullable final ShipmentAllocationBestBeforePolicy bestBeforePolicy)
	{
		return PackageableRow.builder()
				.orderId(OrderId.ofRepoId(1))
				.orderDocumentNo("1234")
				.customer(IntegerLookupValue.of(customerAndLocationId.getBpartnerId().getRepoId(), "customer"))
				.packageable(Packageable.builder()
						.shipmentScheduleId(ShipmentScheduleId.ofRepoId(3))
						//
						.qtyOrdered(qtyOrdered)
						.qtyToDeliver(qtyOrdered)
						.qtyDelivered(Quantity.of(0, uomKg))
						.qtyPickedNotDelivered(Quantity.of(0, uomKg))
						.qtyPickedPlanned(Quantity.of(0, uomKg))
						.qtyPickedAndDelivered(Quantity.of(0, uomKg))
						//
						.customerId(customerAndLocationId.getBpartnerId())
						.customerLocationId(customerAndLocationId)
						//
						.warehouseId(locatorId.getWarehouseId())
						//
						.bestBeforePolicy(Optional.ofNullable(bestBeforePolicy))
						//
						.productId(productId)
						.asiId(AttributeSetInstanceId.NONE)
						//
						.build())
				.build();
	}
}
