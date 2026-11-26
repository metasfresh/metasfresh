/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.pickingV2.packageable;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse_Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class PackageableRowsRepositoryTest
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(1000342);
	private static final UserId USER_A = UserId.ofRepoId(100);
	private static final UserId USER_B = UserId.ofRepoId(101);

	private I_C_UOM uom;
	private IPackagingDAO packagingDAO;
	private PackageableRowsRepository rowsRepo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("PCE");
		saveRecord(uom);

		packagingDAO = Mockito.mock(IPackagingDAO.class);
		Services.registerService(IPackagingDAO.class, packagingDAO);

		final LookupDataSource lookup = Mockito.mock(LookupDataSource.class);
		Mockito.when(lookup.findById(any())).thenAnswer(invocation -> {
			final Object id = invocation.getArgument(0);
			return id != null ? IntegerLookupValue.of(((RepoIdAware)id).getRepoId(), "name-" + id) : null;
		});
		final LookupDataSourceFactory lookupDataSourceFactory = Mockito.mock(LookupDataSourceFactory.class);
		Mockito.when(lookupDataSourceFactory.searchInTableLookup(any())).thenReturn(lookup);

		rowsRepo = new PackageableRowsRepository(Mockito.mock(MoneyService.class), lookupDataSourceFactory);
	}

	private Packageable packageable(final int shipmentScheduleId, @Nullable final UserId lockedBy)
	{
		return packageable(shipmentScheduleId, lockedBy, null);
	}

	private Packageable packageable(final int shipmentScheduleId, @Nullable final UserId lockedBy, @Nullable final WarehouseTypeId warehouseTypeId)
	{
		final Quantity qty = Quantity.of(10, uom);
		final Quantity zero = Quantity.zero(uom);
		final BPartnerLocationId customerLocationId = BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(2), 3);
		return Packageable.builder()
				.orgId(OrgId.ANY)
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentScheduleId))
				.qtyOrdered(qty)
				.qtyToDeliver(qty)
				.qtyDelivered(zero)
				.qtyPickedAndDelivered(zero)
				.qtyPickedNotDelivered(zero)
				.qtyPickedPlanned(zero)
				.customerId(customerLocationId.getBpartnerId())
				.customerLocationId(customerLocationId)
				.handoverLocationId(customerLocationId)
				.warehouseId(WarehouseId.ofRepoId(4))
				.warehouseTypeId(warehouseTypeId)
				.productId(ProductId.ofRepoId(5))
				.productValueAndName(ProductValueAndName.of("P1", TranslatableStrings.anyLanguage("Product 1")))
				.asiId(AttributeSetInstanceId.NONE)
				.salesOrderId(ORDER_ID)
				.salesOrderDocumentNo("0314")
				.preparationDate(InstantAndOrgId.ofInstant(Instant.parse("2026-11-23T08:00:00Z"), OrgId.ANY))
				.lockedBy(lockedBy)
				.build();
	}

	private Map<DocumentId, PackageableRow> retrieveRowsById(final Packageable... packageables)
	{
		Mockito.when(packagingDAO.stream(any())).thenAnswer(invocation -> ImmutableList.copyOf(packageables).stream());

		return rowsRepo.newPackageableRowsData()
				.stickyFilters(DocumentFilterList.EMPTY)
				.filters(DocumentFilterList.EMPTY)
				.build()
				.getDocumentId2TopLevelRows();
	}

	private static PackageableRow singleRow(final Map<DocumentId, PackageableRow> rowsById)
	{
		final List<PackageableRow> rows = ImmutableList.copyOf(rowsById.values());
		assertThat(rows).hasSize(1);
		final PackageableRow row = rows.get(0);
		assertThat(row.getId()).isEqualTo(DocumentId.of(ORDER_ID.getRepoId()));
		return row;
	}

	@Test
	void orderWithPartlyLockedShipmentSchedules_yieldsOneRowLockedByTheSingleLocker()
	{
		final PackageableRow row = singleRow(retrieveRowsById(
				packageable(1, USER_A),
				packageable(2, null)));

		assertThat(row.getShipmentScheduleIds()).containsExactlyInAnyOrder(ShipmentScheduleId.ofRepoId(1), ShipmentScheduleId.ofRepoId(2));
		assertThat(row.isLocked()).isTrue();
		assertThat(row.isLockedBy(USER_A)).isTrue();
		assertThat(row.isLockedBy(USER_B)).isFalse();
	}

	@Test
	void orderWithShipmentSchedulesLockedByTwoUsers_yieldsOneLockedRowNotLockedByEitherUserAlone()
	{
		final PackageableRow row = singleRow(retrieveRowsById(
				packageable(1, USER_A),
				packageable(2, USER_B)));

		assertThat(row.getShipmentScheduleIds()).containsExactlyInAnyOrder(ShipmentScheduleId.ofRepoId(1), ShipmentScheduleId.ofRepoId(2));
		assertThat(row.isLocked()).isTrue();
		assertThat(row.isNotLocked()).isFalse();
		assertThat(row.isLockedBy(USER_A)).isFalse();
		assertThat(row.isLockedBy(USER_B)).isFalse();
	}

	private static WarehouseTypeId createWarehouseType(final String name)
	{
		final I_M_Warehouse_Type record = newInstance(I_M_Warehouse_Type.class);
		record.setName(name);
		saveRecord(record);
		return WarehouseTypeId.ofRepoId(record.getM_Warehouse_Type_ID());
	}

	@Test
	void orderWithTwoWarehouseTypes_yieldsOneRowPerWarehouseType()
	{
		final WarehouseTypeId warehouseType1 = createWarehouseType("WT1");
		final WarehouseTypeId warehouseType2 = createWarehouseType("WT2");

		final Map<DocumentId, PackageableRow> rowsById = retrieveRowsById(
				packageable(1, USER_A, warehouseType1),
				packageable(2, null, warehouseType2));

		assertThat(rowsById.keySet()).containsExactlyInAnyOrder(
				PackageableRowId.of(ORDER_ID, warehouseType1).getDocumentId(),
				PackageableRowId.of(ORDER_ID, warehouseType2).getDocumentId());
		assertThat(rowsById.get(PackageableRowId.of(ORDER_ID, warehouseType1).getDocumentId()).isLockedBy(USER_A)).isTrue();
		assertThat(rowsById.get(PackageableRowId.of(ORDER_ID, warehouseType2).getDocumentId()).isNotLocked()).isTrue();
	}

	@Test
	void orderWithUnlockedShipmentSchedules_yieldsOneUnlockedRow()
	{
		final PackageableRow row = singleRow(retrieveRowsById(
				packageable(1, null),
				packageable(2, null)));

		assertThat(row.isNotLocked()).isTrue();
		assertThat(row.isLockedBy(USER_A)).isFalse();
	}
}
