/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.movement.api;

import static de.metas.business.BusinessTestHelper.createLocator;
import static de.metas.business.BusinessTestHelper.createWarehouse;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.impl.HUMovementBuilder;
import de.metas.interfaces.I_M_Movement;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

public class HUMovementBuilderTests
{
	private LUTUProducerDestinationTestSupport testsupport;
	private I_AD_Org org;

	@BeforeEach
	public void init()
	{
		testsupport = new LUTUProducerDestinationTestSupport();

		// we need this to make sure that movementLine.getAD_Org() does not fail with the created M_MovementLines.
		org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		InterfaceWrapperHelper.save(org);

		Env.setContext(testsupport.helper.ctx, Env.CTXNAME_AD_Org_ID, org.getAD_Org_ID());

		// final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(1);
		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(acctSchemaId);
	}

	@Test
	public void testNonAggregateHU()
	{
		// one IFCO can hold 40kg tomatoes; we load 35kg. This should result in a not-aggregate HU
		final BigDecimal loadCuQty = new BigDecimal("35");
		performTest(
				loadCuQty,
				hu -> {
					// guard: HU is not aggregated
					final Node huXML = HUXmlConverter.toXml(hu);
					assertThat(huXML, hasXPath("string(HU-LU_Palet/Item/@ItemType)", is("HU")));
					assertThat(huXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO)", is("1")));
				},
				BigDecimal.ONE);
	}

	@Test
	public void testggregateHU()
	{
		// one IFCO can hold 40kg tomatoes;
		final BigDecimal loadCuQty = new BigDecimal("120");
		performTest(
				loadCuQty,
				hu -> {
					// guard: HU is aggregated
					final Node huXML = HUXmlConverter.toXml(hu);
					assertThat(huXML, hasXPath("string(HU-LU_Palet/Item/@ItemType)", is("HA")));
				},
				new BigDecimal("3"));
	}

	private void performTest(final BigDecimal loadCuQty,
			Consumer<I_M_HU> huGuard,
			final BigDecimal expectedTULineQty)
	{

		final I_M_Warehouse warehouseFrom = createWarehouse("testWarehouseFrom");
		final I_M_Locator locatorFrom = createLocator("testLocatorFrom", warehouseFrom);

		final I_M_Warehouse warehouseTo = createWarehouse("testWarehouseTo");

		assertThat(warehouseFrom.getAD_Org_ID(), is(org.getAD_Org_ID()));
		assertThat(locatorFrom.getAD_Org_ID(), is(org.getAD_Org_ID()));
		assertThat(warehouseTo.getAD_Org_ID(), is(org.getAD_Org_ID()));

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(LocatorId.ofRecord(locatorFrom));
		lutuProducer.setLUPI(testsupport.piLU);
		lutuProducer.setLUItemPI(testsupport.piLU_Item_IFCO);
		lutuProducer.setTUPI(testsupport.piTU_IFCO);

		testsupport.helper.load(lutuProducer, testsupport.helper.pTomatoProductId, loadCuQty, testsupport.helper.uomKg);
		final List<I_M_HU> hus = lutuProducer.getCreatedHUs();

		assertThat(hus.size(), is(1));

		final I_M_HU hu = hus.get(0);
		assertThat(hu.getAD_Org_ID(), is(org.getAD_Org_ID()));

		huGuard.accept(hu);

		final I_M_Movement movement = new HUMovementBuilder()
				.setContextInitial(testsupport.helper.getContextProvider())
				.setLocatorFrom(locatorFrom) // needs to match the HU's locator
				.setWarehouseTo(warehouseTo)
				.setMovementDate(SystemTime.asTimestamp())
				.addHU(hu)
				.createMovement();

		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movement);
		assertThat(movementLines.size(), is(3));
		assertThat(movementLines.stream()
				.map(l -> InterfaceWrapperHelper.create(l, de.metas.handlingunits.model.I_M_MovementLine.class))
				.anyMatch(l -> l.getM_Product_ID() == testsupport.helper.pTomato.getM_Product_ID()
						&& l.getMovementQty().compareTo(loadCuQty) == 0
						&& !l.isPackagingMaterial()),
				is(true));

		assertThat(movementLines.stream()
				.map(l -> InterfaceWrapperHelper.create(l, de.metas.handlingunits.model.I_M_MovementLine.class))
				.anyMatch(l -> l.getM_Product_ID() == testsupport.helper.pIFCO.getM_Product_ID()
						&& l.getMovementQty().compareTo(expectedTULineQty) == 0
						&& l.isPackagingMaterial()),
				is(true));

		assertThat(movementLines.stream()
				.map(l -> InterfaceWrapperHelper.create(l, de.metas.handlingunits.model.I_M_MovementLine.class))
				.anyMatch(l -> l.getM_Product_ID() == testsupport.helper.pPalet.getM_Product_ID()
						&& l.getMovementQty().compareTo(BigDecimal.ONE) == 0
						&& l.isPackagingMaterial()),
				is(true));
	}
}
