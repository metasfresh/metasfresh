package de.metas.handlingunits.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.hutransaction.impl.HUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * These aren't really "unit" tests, but they all start by invoking stuff from {@link HUTrxBL}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUTrxBLTests
{
	private static final BigDecimal IFCOS_PER_PALET = new BigDecimal("5");
	private static final BigDecimal TOMATOS_PER_IFCO = new BigDecimal("40");
	private HUTestHelper helper;

	private I_M_HU_PI huDefIFCO;
	private I_M_HU_PI_Item huDefPalet_IFCO;
	private I_M_HU_PI huDefPalet;

	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();

		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, helper.pTomato, TOMATOS_PER_IFCO, helper.uomEach);
			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, helper.pmIFCO);
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			huDefPalet_IFCO = helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, IFCOS_PER_PALET);
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, helper.pmPalet);
		}
	}

	/**
	 * Verifies that the per-TU HU-transactions are aggregated into one trx because all the TUs are represented in one aggregate HU.
	 */
	@Test
	public void testTransferIncomingToHUs()
	{
		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				helper.pTomato, // product
				new BigDecimal("86") // qty
		);

		final List<I_M_HU> huPalets = helper.createHUsFromSimplePI(incomingTrxDoc, huDefPalet);

		assertThat(huPalets.size(), is(1));

		final Mutable<I_M_HU> aggregateVHU = new Mutable<>();
		final Mutable<I_M_HU> realIFCO = new Mutable<>();

		// one IFCO can hold 40 tomatoes, one palet can hold 5 IFCOS
		// so we expect an aggregate HU that represents 2 IFCOS and one partially filled "real" IFCO
		//@formatter:off
		final HUsExpectation compressedHUExpectation = new HUsExpectation()
			.newHUExpectation()
				.huPI(huDefPalet)

				.newHUItemExpectation() // the "real" item that shall hold the real IFCO with the remaining 6
					.itemType(X_M_HU_Item.ITEMTYPE_HandlingUnit)
					.huPIItem(huDefPalet_IFCO)
					.newIncludedHUExpectation()
						.capture(realIFCO)
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.newIncludedVirtualHU()
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.qty("6").uom(helper.uomEach).product(helper.pTomato)
									.endExpectation()
								.endExpectation()
							.endExpectation()
						.endExpectation() // end of the IFCO HU's material item
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.endExpectation() // end of the IFCO HU's packing material item
					.endExpectation() // end of the "IFCO" HU
				.endExpectation() // end of the "real" item that shall hold the real IFCO with the remaining 6

				.newHUItemExpectation() // the virtual item that shall hold the "bag" VHU
					.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
					.huPIItem(huDefPalet_IFCO)
					.qty("2")
					.newIncludedHUExpectation()
					.capture(aggregateVHU)
						.huPI(helper.huDefVirtual)
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.newItemStorageExpectation()
								.qty("80").uom(helper.uomEach).product(helper.pTomato)
							.endExpectation() // itemStorageExcpectation
						.endExpectation() // material item
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
							.packingMaterial(helper.pmIFCO)
						.endExpectation() // packing-material item
					.endExpectation() // included "bag" VHU
				.endExpectation()  // end of the virtual item that shall hold the "bag" VHU

				.newHUItemExpectation() // the packing material item for this LU
					.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
					.packingMaterial(helper.pmPalet)
				.endExpectation() // HUItemExpectation()
			.endExpectation() // huExpectation
		;
		//@formatter:on

		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml("result", huPalets)));

		compressedHUExpectation.assertExpected(huPalets);

		final IHUTrxDAO huTrxDAO = Services.get(IHUTrxDAO.class);

		final List<I_M_HU_Trx_Line> trxLinesForAgrregateVHU = huTrxDAO.retrieveReferencingTrxLinesForHU(aggregateVHU.getValue());
		assertThat(trxLinesForAgrregateVHU.size(), is(1)); // we expect that there is just one trx line and not 3
		assertThat(trxLinesForAgrregateVHU.get(0).getQty(), comparesEqualTo(new BigDecimal("80")));
		assertThat(TableRecordReference.ofReferenced(trxLinesForAgrregateVHU.get(0)), is(TableRecordReference.of(incomingTrxDoc)));

		final List<I_M_HU_Trx_Line> trxLinesForRealIFCO = huTrxDAO.retrieveReferencingTrxLinesForHU(realIFCO.getValue());
		assertThat(trxLinesForRealIFCO.size(), is(1)); //
		assertThat(trxLinesForRealIFCO.get(0).getQty(), comparesEqualTo(new BigDecimal("6")));
		assertThat(TableRecordReference.ofReferenced(trxLinesForRealIFCO.get(0)), is(TableRecordReference.of(incomingTrxDoc)));
	}
}
