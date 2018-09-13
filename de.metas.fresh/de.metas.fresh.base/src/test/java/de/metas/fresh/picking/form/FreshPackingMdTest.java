package de.metas.fresh.picking.form;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.api.impl.MockedPackagingDAO;
import de.metas.picking.legacy.form.TableRow;
import de.metas.picking.legacy.form.TableRowKey;
import de.metas.product.ProductId;

public class FreshPackingMdTest
{
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(30);
	private final LocalDateTime date_2014_01_10 = LocalDateTime.of(2014, Month.JANUARY, 10, 0, 0);
	private final LocalDateTime date_2014_01_11 = LocalDateTime.of(2014, Month.JANUARY, 11, 0, 0);
	private final LocalDateTime date_2014_01_12 = LocalDateTime.of(2014, Month.JANUARY, 12, 0, 0);
	private final LocalDateTime date_2014_01_13 = LocalDateTime.of(2014, Month.JANUARY, 13, 0, 0);
	private final LocalDateTime date_2014_01_14 = LocalDateTime.of(2014, Month.JANUARY, 14, 0, 0);
	private final LocalDateTime date_2014_01_15 = LocalDateTime.of(2014, Month.JANUARY, 15, 0, 0);

	private MockedPackagingDAO packagingDAO;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.packagingDAO = new MockedPackagingDAO();
		Services.registerService(IPackagingDAO.class, packagingDAO);
	}

	@Test
	public void testTableRowAggregation_StandardCase() throws Exception
	{
		final List<Packageable> packageables = Arrays.asList(
				createPackageable(1, date_2014_01_12, date_2014_01_13),
				createPackageable(2, date_2014_01_10, date_2014_01_15),
				createPackageable(3, date_2014_01_11, date_2014_01_14));
		final int expectedQtyToDeliver = 1 + 2 + 3;
		final LocalDateTime expectedDeliveryDate = date_2014_01_10;
		final LocalDateTime expectedPreparationDate = date_2014_01_15;

		testTableRowAggregation(packageables, expectedQtyToDeliver, expectedDeliveryDate, expectedPreparationDate);
	}

	@Test
	public void testTableRowAggregation_Null_PreparationDate() throws Exception
	{
		final List<Packageable> packageables = Arrays.asList(
				createPackageable(1, date_2014_01_12, date_2014_01_13),
				createPackageable(2, date_2014_01_10, null),
				createPackageable(3, date_2014_01_11, date_2014_01_14));
		final int expectedQtyToDeliver = 1 + 2 + 3;
		final LocalDateTime expectedDeliveryDate = date_2014_01_10;
		final LocalDateTime expectedPreparationDate = null;

		testTableRowAggregation(packageables, expectedQtyToDeliver, expectedDeliveryDate, expectedPreparationDate);
	}

	@Test
	public void testTableRowAggregation_Null_Same_DeliveryDate_Take_Min_PreparationDate() throws Exception
	{
		final List<Packageable> packageables = Arrays.asList(
				createPackageable(1, date_2014_01_10, date_2014_01_15),
				createPackageable(2, date_2014_01_11, null),
				createPackageable(3, date_2014_01_10, date_2014_01_14));
		final int expectedQtyToDeliver = 1 + 2 + 3;
		final LocalDateTime expectedDeliveryDate = date_2014_01_10;
		final LocalDateTime expectedPreparationDate = date_2014_01_14;

		testTableRowAggregation(packageables, expectedQtyToDeliver, expectedDeliveryDate, expectedPreparationDate);
	}

	private void testTableRowAggregation(final List<Packageable> packageables,
			int expectedQtyToDeliver,
			LocalDateTime expectedDeliveryDate,
			LocalDateTime expectedPreparationDate)
			throws Exception
	{
		final FreshPackingMd model = createPackingModel();

		packagingDAO.setPackableLines(packageables);
		model.reload();
		packagingDAO.setPackableLines(null); // reset to not influence other tests

		//
		// Check TableRowKey: we assume everything was aggregated on one key
		// if this will change in future, we need to adjust the test and make it aggregate all Packageables to one key for this test
		final List<TableRowKey> tableRowKeys = model.getTableRowKeys();
		Assert.assertNotNull("tableRowKeys not null", tableRowKeys);
		Assert.assertEquals("Invalid TableRowKeys size", 1, tableRowKeys.size());
		final TableRowKey tableRowKey = tableRowKeys.get(0);

		//
		// Check Aggregated TableRow existence
		final TableRow tableRowAgg = model.getAggregatedTableRowOrNull(tableRowKey);
		Assert.assertNotNull("Aggregated TableRow shall exist for " + tableRowKey, tableRowAgg);

		//
		// Check Aggregated TableRow values
		Assert.assertThat("Invalid aggregated QtyToDeliver",
				tableRowAgg.getQtyToDeliver(),
				Matchers.comparesEqualTo(BigDecimal.valueOf(expectedQtyToDeliver)));

		Assert.assertEquals("Invalid aggregated DeliveryDate", expectedDeliveryDate, tableRowAgg.getDeliveryDate());
		Assert.assertEquals("Invalid aggregated PreparationDate", expectedPreparationDate, tableRowAgg.getPreparationDate());
	}

	private FreshPackingMd createPackingModel()
	{
		final ITerminalContext terminalContext = TerminalContextFactory.get().createContextAndRefs().getLeft();
		terminalContext.setWindowNo(Env.WINDOW_None);

		final FreshPackingMd model = new FreshPackingMd(terminalContext);
		model.setWarehouseId(WAREHOUSE_ID);
		return model;
	}

	private Packageable createPackageable(int qtyToDeliver, LocalDateTime deliveryDate, LocalDateTime preparationDate)
	{
		final I_M_ShipmentSchedule shipmentScheduleRecord = newInstance(I_M_ShipmentSchedule.class);
		save(shipmentScheduleRecord);

		return Packageable.builder()
				.qtyToDeliver(BigDecimal.valueOf(qtyToDeliver))
				.deliveryDate(deliveryDate)
				.preparationDate(preparationDate)
				.displayed(true)
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentScheduleRecord.getM_ShipmentSchedule_ID()))
				.productId(ProductId.ofRepoId(20))
				.warehouseId(WAREHOUSE_ID)
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(0))
				.build();
	}
}
