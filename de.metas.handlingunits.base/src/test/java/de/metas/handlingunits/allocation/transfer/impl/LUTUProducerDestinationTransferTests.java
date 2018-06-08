package de.metas.handlingunits.allocation.transfer.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import org.adempiere.util.Services;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
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
 * This test verifies the loader's behavior if quantities are transferred between HUs. It uses {@link HUTestHelper#transferMaterialToNewHUs(List, LUTUProducerDestination, BigDecimal, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM)}.
 * The reason it doesn't use the {@link HUSplitBuilder} has more moving parts (like running the BL in yet another IHUContextProcessor and destroying empty HUs) which are not relevant for these tests.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LUTUProducerDestinationTransferTests
{
	private LUTUProducerDestinationTestSupport data;

	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	/**
	 * Here we verify the behavior if two "real" IFCOS of 20kg each are transferred to one IFCO that sits on a palet and can cold 40kg.
	 *
	 * The current behavior is that the system creates a LU (palet) with one TU (IFCO) with two VHU of 20kg each.
	 * It might be OK to have a different behavior (with an aggregated target), but this is how it is now.
	 */
	@Test
	public void testCreateTwoIFCOsThenTransferToLU()
	{

		final LUTUProducerDestination lutuProducer1 = mkLUTUProducerForSingleIFCO();
		data.helper.load(lutuProducer1, data.helper.pTomato, new BigDecimal("20"), data.helper.uomKg);

		final LUTUProducerDestination lutuProducer2 = mkLUTUProducerForSingleIFCO();
		data.helper.load(lutuProducer2, data.helper.pTomato, new BigDecimal("20"), data.helper.uomKg);

		assertThat(lutuProducer1.getCreatedHUsCount(), is(1));
		final Node sourceXML1 = HUXmlConverter.toXml(lutuProducer1.getCreatedHUs().get(0));
		// System.out.println(HUXmlConverter.toString(sourceXML1));
		verifySingleIFCO(sourceXML1, "20.000");

		assertThat(lutuProducer2.getCreatedHUsCount(), is(1));
		final Node sourceXML2 = HUXmlConverter.toXml(lutuProducer2.getCreatedHUs().get(0));
		verifySingleIFCO(sourceXML2, "20.000");

		HUAssert.assertAllStoragesAreValid();

		// TODO transfer them onto a new palet and IFCO
		// verify if there will be an aggregate HU on the target LU or if instead on the target LU there will be two "real" IFCOs

		final LUTUProducerDestination transferLutuProducer = mkLUTUProducerDestination();

		// no palets allowed; the bags shall be on the top level
		transferLutuProducer.setTUPI(data.piTU_IFCO);
		transferLutuProducer.setLUPI(data.piLU);
		transferLutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		transferLutuProducer.setMaxLUs(10);
		// lutuProducer.setMaxTUsForRemainingQty(4); // allow four max; we will expect and verify 3
		transferLutuProducer.setCreateTUsForRemainingQty(true);

		data.helper.transferMaterialToNewHUs(ImmutableList.of(lutuProducer1.getCreatedHUs().get(0), lutuProducer2.getCreatedHUs().get(0)),
				transferLutuProducer,
				new BigDecimal("40"),
				data.helper.pTomato,
				data.helper.uomKg);

		//
		// Verify the transfer source
		final Node sourceXML1AfterTransfer = HUXmlConverter.toXml(lutuProducer1.getCreatedHUs().get(0));
		verifySingleIFCO(sourceXML1AfterTransfer, "0.000");

		final Node sourceXML2AfterTransfer = HUXmlConverter.toXml(lutuProducer2.getCreatedHUs().get(0));
		verifySingleIFCO(sourceXML2AfterTransfer, "0.000");

		//
		// Verify the transfer destination
		assertThat(transferLutuProducer.getCreatedHUsCount(), is(1));
		final Node transferXML = HUXmlConverter.toXml(transferLutuProducer.getCreatedHUs().get(0));
		// System.out.println(HUXmlConverter.toString(transferXML));

		assertThat(transferXML, hasXPath("count(/HU-LU_Palet)", Matchers.equalTo("1")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM'])", is("1")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
		verifyPaletHasEmptyAggregateVHU(transferXML);

		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("1")));
		assertThat(transferXML, not(hasXPath("HU-LU_Palet/Item[@ItemType='HU']/Storage"))); // HU items don't have a storage
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO)", is("1")));

		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI)", is("2")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("2")));
		assertThat(transferXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("2")));
	}

	private LUTUProducerDestination mkLUTUProducerForSingleIFCO()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setNoLU();

		return lutuProducer;
	}

	/**
	 * Verifies a standalone IFCO that was created using the producer from {@link #mkLUTUProducerForSingleIFCO()}.
	 *
	 * @param sourceXML
	 */
	private void verifySingleIFCO(final Node sourceXML, final String storageQty)
	{
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO)", Matchers.equalTo("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM'])", is("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='" + storageQty + "' and @C_UOM_Name='Kg'])", is("1")));

		assertThat(sourceXML, not(hasXPath("HU-TU_IFCO/Item[@ItemType='HU']")));
		assertThat(sourceXML, not(hasXPath("HU-TU_IFCO/Item[@ItemType='HA']")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM'])", is("1")));

		// the "real" partially filled IFCO
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='" + storageQty + "' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='" + storageQty + "' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(sourceXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='" + storageQty + "' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Test
	public void testCreatePaletWithIFCOThenTransferToTopLevelTU()
	{
		final List<I_M_HU> huPalets;

		//
		// load 23 kg of tomatoes into huPalets
		{
			final LUTUProducerDestination lutuProducer = mkLUTUProducerDestination();

			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("23"), data.helper.uomKg);
			huPalets = lutuProducer.getCreatedHUs();

			assertThat(huPalets.size(), is(1));
			final I_M_HU huPalet = huPalets.get(0);

			final IAttributeStorageFactory attrStorageFactory = data.helper.getHUContext().getHUAttributeStorageFactory();
			final IAttributeStorage huPalet1_Attrs = attrStorageFactory.getAttributeStorage(huPalet);
			huPalet1_Attrs.setValue(data.helper.attr_CountryMadeIn, HUTestHelper.COUNTRYMADEIN_DE);

			//
			// Validate the palet HU
			{
				final Node huPaletXML = HUXmlConverter.toXml(huPalet);
				// System.out.println(HUXmlConverter.toString(huPaletXML));

				// loaded 23kg tomatoes into a LU with TUs that can hold 40kg each => expecting one LU with one TU
				assertThat(huPaletXML, hasXPath("count(/HU-LU_Palet)", Matchers.equalTo("1")));
				assertThat(huPaletXML, hasXPath("/HU-LU_Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='23.000' and @C_UOM_Name='Kg']"));

				verifyPaletHasEmptyAggregateVHU(huPaletXML);

				// the "real" partially filled IFCO
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='23.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='23.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='23.000' and @C_UOM_Name='Kg'])", is("1")));

				HUAssert.assertAllStoragesAreValid();
			}
		}

		//
		// Transfer 17 items from huPalets to new Bags
		final LUTUProducerDestination lutuProducer = mkLUTUProducerDestination();

		// no palets allowed; the bags shall be on the top level
		lutuProducer.setTUPI(data.piTU_Bag);
		lutuProducer.setNoLU();
		lutuProducer.setMaxTUsForRemainingQty(4); // allow four max; we will expect and verify 3

		data.helper.transferMaterialToNewHUs(huPalets,
				lutuProducer,
				new BigDecimal("17"),
				data.helper.pTomato,
				data.helper.uomKg);

		final List<I_M_HU> huBags = lutuProducer.getCreatedHUs();

		// Validate the "source" Palets after moving to 17 to Bags
		{
			final I_M_HU huPalet = huPalets.get(0);
			final Node huPaletXML = HUXmlConverter.toXml(huPalet);
			// System.out.println("" + HUXmlConverter.toString(huPaletsXML));

			// we had 23, so 23-17=6 should be left
			assertThat(huPaletXML, hasXPath("count(/HU-LU_Palet)", Matchers.equalTo("1")));
			assertThat(huPaletXML, hasXPath("/HU-LU_Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg']"));

			verifyPaletHasEmptyAggregateVHU(huPaletXML);

			// the "real" partially filled IFCO
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg'])", is("1")));

			HUAssert.assertAllStoragesAreValid();
		}
		//
		// Validate Bags after transferring 17 items to it
		{
			final Node huBagsXML = HUXmlConverter.toXml("Bags", huBags);
			// System.out.println(HUXmlConverter.toString(huBagsXML));

			useCase2VerifyBagInvariants(huBagsXML);

			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[1]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[2]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg']"));

			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[1]/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[2]/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[3]/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg']"));
		}

		//
		// Transfer another 1 item from Palets to existing Bags
		data.helper.transferMaterialToExistingHUs(huPalets, huBags, data.helper.pTomato, BigDecimal.ONE, data.helper.uomKg);

		//
		// Validate Blisters after transferring another 1 item to it (so we got 18 in total)
		{
			final Node huBagsXML = HUXmlConverter.toXml("Bags", huBags);
			System.out.println(HUXmlConverter.toString(huBagsXML));

			useCase2VerifyBagInvariants(huBagsXML);

			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[1]/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[1]/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg'])", is("1")));

			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[2]/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[2]/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg'])", is("1")));

			// for the last bag, since there were two different transfers, we expect two VHUs with one item each
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[3]/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/HU-VirtualPI)", is("2")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("2")));

			HUAssert.assertAllStoragesAreValid();
		}

		//
		// Shipment: ship 17 items from bags
		final I_M_Transaction outgoingTrx = data.helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_CustomerShipment,
				data.helper.pTomato,
				new BigDecimal("-17"));
		data.helper.transferHUsToOutgoing(outgoingTrx, huBags);

		//
		// Validate bags after taking 17 items out of them, so there is 1 left
		{
			final Node huBagsXML = HUXmlConverter.toXml("Bags", huBags);
			// System.out.println(HUXmlConverter.toString(huBagsXML));

			useCase2VerifyBagInvariants(huBagsXML);

			// FIXME the first two bags shall have a Bag packing material item with qty=0 because there aren't any bags anymore.
			// Note: as far as this test is concerned, it would also be OK to not have the PM items altogether

			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[1]/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));
			// assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[1]/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Bag' and @Qty='0'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[1]/Item[@ItemType='MI'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[1]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[1]/Item[@ItemType='MI']/HU-VirtualPI)", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[1]/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[1]/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[2]/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));
			// assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[2]/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Bag' and @Qty='0'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[2]/Item[@ItemType='MI'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[2]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[2]/Item[@ItemType='MI']/HU-VirtualPI)", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[2]/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[2]/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
			// assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Bag' and @Qty='1'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/HU-VirtualPI)", is("2")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/HU-VirtualPI[1]/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/HU-VirtualPI[1]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/HU-VirtualPI[2]/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/HU-VirtualPI[2]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

			HUAssert.assertAllStoragesAreValid();
		}

		// TraceUtils.dump(huPalets);
		// TraceUtils.dump(huBags);
		// TraceUtils.dumpTransactions();
	}

	/**
	 * Verifies that the given {@code huPaletXML} has an aggregate VHU that is not really used in this case. It has no storage, and its HA item has a quantity of zero
	 *
	 * @param huPaletXML
	 */
	private void verifyPaletHasEmptyAggregateVHU(final Node huPaletXML)
	{
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(huPaletXML, not(hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
	}

	/**
	 * Verify some things that shall stay the same about the "Bag" TUs after different operations done within {@link #useCase2()}.
	 *
	 * @param huBagsXML
	 */
	private void useCase2VerifyBagInvariants(final Node huBagsXML)
	{
		assertThat(huBagsXML, Matchers.hasXPath("count(/Bags/HU-TU_Bag)", is("3"))); // one bag can hold 8 kg tomatoes, to there should be 3 bags for our 17 kg tomatoes
		IntStream.of(1, 2, 3).forEach(n -> {

			final String failMsg = "assert failure in bag# " + n;

			// actually, the aggregation feature is not used because there are no lower-level HUs such as IFCOs that would need to be represented within the aggregate.
			// therefore, no item with type HA
			assertThat(failMsg, huBagsXML, not(hasXPath("/Bags/HU-TU_Bag[" + n + "]/Item[@ItemType='HA']")));

			// the bag's material item has one tomato-kg storage
			assertThat(failMsg, huBagsXML, hasXPath("count(/Bags/HU-TU_Bag[" + n + "]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg'])", is("1")));

			// the item's VHU also has at least one storage, at least one "material" item and no items of other types
			assertThat(failMsg, huBagsXML, hasXPath("/Bags/HU-TU_Bag[" + n + "]/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']"));
			assertThat(failMsg, huBagsXML, hasXPath("/Bags/HU-TU_Bag[" + n + "]/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']"));
			assertThat(failMsg, huBagsXML, not(hasXPath("/Bags/HU-TU_Bag[" + n + "]/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='HA']")));
			assertThat(failMsg, huBagsXML, not(hasXPath("/Bags/HU-TU_Bag[" + n + "]/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='HU']")));
			assertThat(failMsg, huBagsXML, not(hasXPath("/Bags/HU-TU_Bag[" + n + "]/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='PM']")));
		});
	}

	private LUTUProducerDestination mkLUTUProducerDestination()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);

		return lutuProducer;
	}

	/**
	 *
	 * <li>Create one IFCO with 35kg tomatoes. That IFCO has a capacity of 40kg.
	 * <li>Repackage/Move the 35kg onto a palet with once again one IFCO that has the same capacity
	 *
	 */
	@Test
	public void testSplitTUontoLUwithOneTU()
	{
		final List<I_M_HU> createdIFCOs;
		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
			lutuProducer.setTUPI(data.piTU_IFCO);
			lutuProducer.setNoLU();

			// one ifco can hold 40kg tomatoes
			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("35"), data.helper.uomKg);

			createdIFCOs = lutuProducer.getCreatedHUs();
			// guards
			assertThat(createdIFCOs.size(), is(1));
			final Node createdIFCOsXML = HUXmlConverter.toXml("IFCOs", createdIFCOs);
			// System.out.println(HUXmlConverter.toString(createdIFCOsXML));
			assertThat(createdIFCOsXML, hasXPath("/IFCOs/HU-TU_IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg']"));
		}

		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

			// no palets allowed; the bags shall be on the top level
			lutuProducer.setTUPI(data.piTU_IFCO);
			lutuProducer.setLUPI(data.piLU);
			lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
			lutuProducer.setMaxLUs(99);
			lutuProducer.setCreateTUsForRemainingQty(false);

			data.helper.transferMaterialToNewHUs(createdIFCOs,
					lutuProducer,
					new BigDecimal("35"),
					data.helper.pTomato,
					data.helper.uomKg);
			final List<I_M_HU> splitLUs = lutuProducer.getCreatedHUs();

					assertThat(splitLUs.size(), is(1));

			// Validate splitLUsXML
			final Node splitLuXML = HUXmlConverter.toXml(splitLUs.get(0));
			// System.out.println(HUXmlConverter.toString(splitLUsXML));

			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet)", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='PM'])", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));

			// the LU contains an aggregated HU, but that's only an empty stub
			assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
			assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
			assertThat(splitLuXML, not(hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage")));
			assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));

			// LU LU also contains the partitally filled "real" IFCO
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO)", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI)", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLuXML, hasXPath("count(/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));

			HUAssert.assertAllStoragesAreValid();
		}

	}

	/**
	 * <li>Create one IFCO with 35kg tomatoes. That IFCO has a capacity of 40kg.
	 * <li>Repackage/Move the 35kg onto a palet with two IFCOs, but each of those two IFCOs have a capacity of 20kg each.
	 *
	 */
	@Test
	public void testSplitTUontoLUwithTwoTUs()
	{
		final List<I_M_HU> createdIFCOs;
		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
			lutuProducer.setTUPI(data.piTU_IFCO);
			lutuProducer.setNoLU();

			// one IFCO can hold 40kg tomatoes
			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("35"), data.helper.uomKg);

			createdIFCOs = lutuProducer.getCreatedHUs();
			// guards
			assertThat(createdIFCOs.size(), is(1));
			final Node createdIFCOsXML = HUXmlConverter.toXml("IFCOs", createdIFCOs);
			// System.out.println(HUXmlConverter.toString(createdIFCOsXML));
			assertThat(createdIFCOsXML, hasXPath("/IFCOs/HU-TU_IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg']"));
		}

		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

			lutuProducer.setTUPI(data.piTU_IFCO);
			lutuProducer.setLUPI(data.piLU);
			lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
			lutuProducer.setMaxLUs(99);
			lutuProducer.setMaxTUsPerLU(2);
			lutuProducer.addCUPerTU(data.helper.pTomato, new BigDecimal("20"), data.helper.uomKg); // only allow 20kg, so that the 35kg don't fit into one IFCO
			lutuProducer.setCreateTUsForRemainingQty(false);

			data.helper.transferMaterialToNewHUs(createdIFCOs,
					lutuProducer,
					new BigDecimal("35"),
					data.helper.pTomato,
					data.helper.uomKg);
			final List<I_M_HU> splitLUs = lutuProducer.getCreatedHUs();

			//
			// now do some splitting
			// final List<I_M_HU> splitLUs = new HUSplitBuilder(data.helper.ctx)
			// .setHUToSplit(createdIFCOs.get(0))
			// .setCUQty(new BigDecimal("35"))
			// // LU
			// .setLU_M_HU_PI_Item(data.piLU_Item_IFCO)
			// .setMaxLUToAllocate(new BigDecimal("1"))
			// // TU
			// .setTU_M_HU_PI_Item(data.piTU_Item_IFCO)
			// .setTUPerLU(new BigDecimal("2"))
			// // CU
			// .setCUProduct(data.helper.pTomato)
			// .setCUPerTU(new BigDecimal("20"))
			// .setCUUOM(uomKg)
			// //
			// .split();

			// Validate splitLUsXML
			final Node splitLUsXML = HUXmlConverter.toXml("SplitLUs", splitLUs);
			// System.out.println(HUXmlConverter.toString(splitLUsXML));

			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet)", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));

			// the LU contains one aggregated HU for the 1 fully filled IFCO (20kg)..
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA' and @Qty='1'])", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));

			// ..and also the LU one "normal" TU for the 1 partially filled IFCO (15kg)..
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU'])", is("1")));
			assertThat(splitLUsXML, not(hasXPath("/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU']/Storage"))); // a HU item does not have an item storage
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO)", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='15.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='15.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI)", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='15.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='15.000' and @C_UOM_Name='Kg'])", is("1")));

			HUAssert.assertAllStoragesAreValid();
		}
	}

	@Test
	public void testSplitFromLU()
	{
		final List<I_M_HU> luPalets;

		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
			lutuProducer.setLUPI(data.piLU);
			lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
			lutuProducer.setTUPI(data.piTU_IFCO);

			// one IFCO can hold 40kg tomatoes
			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("120"), data.helper.uomKg);

			// guard: we did not need lutuConfig.
			assertThat(lutuProducer.getM_HU_LUTU_Configuration(), nullValue());
			final int lutu_Configuration_ID = 0;

			assertThat(lutuProducer.getCreatedHUsCount(), is(1));

			//
			// verify the palet's initial state
			luPalets = lutuProducer.getCreatedHUs();
			{
				final Node huPaletXML = HUXmlConverter.toXml(luPalets.get(0));
				// System.out.println(HUXmlConverter.toString(huPaletXML));

				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet[@M_HU_LUTU_Configuration_ID='" + lutu_Configuration_ID + "'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='120.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));

				// the aggregate HU that represents three IFCO with 120kg tomatoes
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='3'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI[@M_HU_LUTU_Configuration_ID='" + lutu_Configuration_ID + "'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='120.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='120.000' and @C_UOM_Name='Kg'])", is("1")));
			}
		}

		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

			lutuProducer.setTUPI(data.piTU_IFCO);
			lutuProducer.setLUPI(data.piLU);
			lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
			lutuProducer.setMaxLUs(99);
			lutuProducer.setMaxTUsPerLU(2);
			lutuProducer.setCreateTUsForRemainingQty(false);

			data.helper.transferMaterialToNewHUs(luPalets,
					lutuProducer,
					new BigDecimal("60"),
					data.helper.pTomato,
					data.helper.uomKg);
			final List<I_M_HU> splitLUs = lutuProducer.getCreatedHUs();

			//
			// verify the "split target"
			{
				assertThat(splitLUs.size(), is(1));
				final Node splitLuXML = HUXmlConverter.toXml(splitLUs.get(0));
				// System.out.println(HUXmlConverter.toString(splitLuXML));

				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='60.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));

				// the aggregate HU that represents one IFCO with 40kg tomatoes
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='1'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));

				// the "real" partially filled IFCO
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
				assertThat(splitLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
			}

			//
			// verify the "split source"

			{
				final Node huPaletXML = HUXmlConverter.toXml(luPalets.get(0));
				System.out.println(HUXmlConverter.toString(huPaletXML));

				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='60.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));

				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item)", is("3"))); // three items (one HU, one HA, one PM)

				// the aggregate VHU which now represents one full IFCO
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='1'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));

				// the "real" partially filled IFCO
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO)", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
				assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
			}
		}
	}

	/**
	 * Creates a palet with 110kg tomatoes. the palet has one aggregate VHU that represents two IFCOs with 40kg each and one "real" IFCO for the remaining 30kg.<br>
	 * Then the test transfers 25kg to a new IFCO.<br>
	 * It then verifies that those 25kg were taken from the "real" IFCO, and the aggregated VHU was left intact.
	 */
	@Test
	public void testTransferFromLUWithPartialTU()
	{

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);

		// one IFCO can hold 40kg tomatoes
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("110"), data.helper.uomKg);

		// guard: we did not need lutuConfig.
		assertThat(lutuProducer.getM_HU_LUTU_Configuration(), nullValue());
		assertThat(lutuProducer.getCreatedHUsCount(), is(1));

		// verify the palet's initial state
		final I_M_HU luPalet = lutuProducer.getCreatedHUs().get(0);
		{
			final Node luPaletXML = HUXmlConverter.toXml(luPalet);
			testTransferFromLUWithPartialTUVerifyInitialLU(luPaletXML);
		}

		final LUTUProducerDestination transferLutuProducer = new LUTUProducerDestination();
		transferLutuProducer.setTUPI(data.piTU_IFCO);
		transferLutuProducer.setNoLU();

		data.helper.transferMaterialToNewHUs(ImmutableList.of(luPalet),
				transferLutuProducer,
				new BigDecimal("25"),
				data.helper.pTomato,
				data.helper.uomKg);
		final List<I_M_HU> splitTUs = transferLutuProducer.getCreatedHUs();
		assertThat(splitTUs.size(), is(1));

		//
		// verify the source after the transfer
		{
			final Node huPaletXML = HUXmlConverter.toXml(luPalet);

			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet)", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='85.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));

			// the aggregate HU that *still* represents two IFCOs with 80kg tomatoes
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='80.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='80.000' and @C_UOM_Name='Kg'])", is("1")));

			// the "real" partially filled IFCO
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO)", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='5.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='5.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='5.000' and @C_UOM_Name='Kg'])", is("1")));
		}

		//
		// verify the destination
		verifySingleIFCO(HUXmlConverter.toXml(splitTUs.get(0)), "25.000");
	}

	/**
	 * Similar to {@link #testTransferFromLUWithPartialTU()}, but in this case, the qty transfered from the source LU exceeds that qty contained in the source LU's partial IFCO.
	 * Therefore, the partial IFCO shall be empties <b>and</b> and the remaining amount shall be taken from the aggregate HU, which then also needs to be split.
	 */
	@Test
	public void testTransferFromLUWithPartialTU2()
	{

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);

		// one IFCO can hold 40kg tomatoes
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("110"), data.helper.uomKg);

		// guard: we did not need lutuConfig.
		assertThat(lutuProducer.getM_HU_LUTU_Configuration(), nullValue());
		assertThat(lutuProducer.getCreatedHUsCount(), is(1));

		// verify the palet's initial state
		final I_M_HU luPalet = lutuProducer.getCreatedHUs().get(0);
		{
			final Node luPaletXML = HUXmlConverter.toXml(luPalet);
			testTransferFromLUWithPartialTUVerifyInitialLU(luPaletXML);
		}

		final LUTUProducerDestination transferLutuProducer = new LUTUProducerDestination();
		transferLutuProducer.setTUPI(data.piTU_IFCO);
		transferLutuProducer.setNoLU();

		data.helper.transferMaterialToNewHUs(ImmutableList.of(luPalet),
				transferLutuProducer,
				new BigDecimal("35"),
				data.helper.pTomato,
				data.helper.uomKg);
		final List<I_M_HU> splitTUs = transferLutuProducer.getCreatedHUs();
		assertThat(splitTUs.size(), is(1));

		//
		// verify the source after the transfer
		{
			final Node huPaletXML = HUXmlConverter.toXml(luPalet);

			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet)", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='75.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));

			// the aggregate HU that *still* represents two IFCOs with 80kg tomatoes
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='1'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));

			// there are two IFCOs around, because we only transfered, but did not destroy empty HUs!
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO)", is("2")));

			// the first IFCO is the one from where we took 30 of the 35kg. It's empty now.
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

			// the the second IFCO is the partially filled one that contains the remaining 35kg
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO[2]/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO[2]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO[2]/Item[@ItemType='MI'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO[2]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO[2]/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
			assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO[2]/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		}

		//
		// verify the destination
		{
			final Node splitTUXML = HUXmlConverter.toXml(splitTUs.get(0));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO)", Matchers.equalTo("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM'])", is("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));

			assertThat(splitTUXML, not(hasXPath("HU-TU_IFCO/Item[@ItemType='HU']")));
			assertThat(splitTUXML, not(hasXPath("HU-TU_IFCO/Item[@ItemType='HA']")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM'])", is("1")));

			// the "real" partially filled IFCO
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI)", is("2")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("2")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='30.000' and @C_UOM_Name='Kg'])", is("1")));
			assertThat(splitTUXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='5.000' and @C_UOM_Name='Kg'])", is("1")));
		}
	}

	private void testTransferFromLUWithPartialTUVerifyInitialLU(final Node huPaletXML)
	{
		// System.out.println(HUXmlConverter.toString(huPaletXML));

		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet)", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='110.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));

		// the aggregate HU that represents two IFCOs with 80kg tomatoes
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='80.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='80.000' and @C_UOM_Name='Kg'])", is("1")));

		// the "real" partially filled IFCO.
		// note that we have two VHUs, one for the 30kg that came from the previous partial HU and one for the 5kg that came from the aggregate VHU.
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO)", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='30.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='30.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(huPaletXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='30.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Sets up the transfer so that there will be 85 unloaded, but on the target side there is just space for 5.
	 * Then, from the unloader there will be one {@link IHUTransactionCandidate} with 5 which will in consequence completely fill the target.
	 * Then the second {@link IHUTransactionCandidate} with with the remaining 80 that were also unloaded will come in.<br>
	 * This rest verifies that those 80 won't be loaded <b>and</b> that the destination HU's HA item's qty will be 1 (and not two because of the two incoming transactions).
	 *
	 */
	@Test
	public void testForCorrectItemQtyOnTwoTrxCandidates()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);

		// one IFCO can hold 40kg tomatoes, so we will have one "real" IFCO with 5kg
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("85"), data.helper.uomKg);

		// guard: we did not need lutuConfig.

		// verify the palet's initial state
		final I_M_HU luPalet = lutuProducer.getCreatedHUs().get(0);

		final LUTUProducerDestination transferLutuProducer = new LUTUProducerDestination();

		transferLutuProducer.setLUPI(data.piLU);
		transferLutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		transferLutuProducer.setTUPI(data.piTU_IFCO);
		transferLutuProducer.setMaxLUs(1);
		transferLutuProducer.setMaxTUsPerLU(1);
		transferLutuProducer.setCreateTUsForRemainingQty(false);
		transferLutuProducer.addCUPerTU(data.helper.pTomato, new BigDecimal("5"), data.helper.uomKg);

		data.helper.transferMaterialToNewHUs(ImmutableList.of(luPalet),
				transferLutuProducer,
				new BigDecimal("85"),
				data.helper.pTomato,
				data.helper.uomKg);
		final List<I_M_HU> splitLUs = transferLutuProducer.getCreatedHUs();

		assertThat(splitLUs.size(), is(1));

		final Node splitLUsXML = HUXmlConverter.toXml(splitLUs.get(0));
		// System.out.println(HUXmlConverter.toString(splitLUsXML));

		assertThat(splitLUsXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='1'])", is("1")));
		assertThat(splitLUsXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='1']/Storage[@M_Product_Value='Tomato' and @Qty='5.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1203
	 */
	@Test
	public void testForMissingRoundingIssue()
	{
		final I_M_HU_PI piTU_IFCO_with3kg = data.helper.createHUDefinition("TU_IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		final I_M_HU_PI_Item piTU_Item_IFCO_with3kg = data.helper.createHU_PI_Item_Material(piTU_IFCO_with3kg);
		data.helper.assignProduct(piTU_Item_IFCO_with3kg, data.helper.pTomato, new BigDecimal("3"), data.helper.uomKg);

		data.helper.createHU_PI_Item_PackingMaterial(piTU_IFCO_with3kg, data.helper.pmIFCO);

		final I_M_HU_PI_Item piLU_Item_IFCO_with3kg = data.helper.createHU_PI_Item_IncludedHU(data.piLU, piTU_IFCO_with3kg, new BigDecimal("20"));

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(piLU_Item_IFCO_with3kg);
		lutuProducer.setTUPI(piTU_IFCO_with3kg);

		// create one LU with and aggregate HU representing 8 x 3kg = 24kg
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("24"), data.helper.uomKg);

		// guards to make sure the HU-tree under test is what we expect
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();
		assertThat(createdLUs.size(), is(1));
		final I_M_HU createdLU = createdLUs.get(0);

		final List<I_M_HU> createdAggregateTUs = handlingUnitsDAO.retrieveIncludedHUs(createdLU);
		assertThat(createdAggregateTUs.size(), is(1));
		final I_M_HU createdAggregateTU = createdAggregateTUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(createdAggregateTU), is(true));
		assertThat(handlingUnitsDAO.retrieveParentItem(createdAggregateTU).getQty(), comparesEqualTo(new BigDecimal("8")));

		final LUTUProducerDestination transferLutuProducer = new LUTUProducerDestination();
		transferLutuProducer.setTUPI(data.piTU_IFCO);
		transferLutuProducer.setNoLU();

		data.helper.transferMaterialToNewHUs(ImmutableList.of(createdAggregateTU),
				transferLutuProducer,
				new BigDecimal("5"),
				data.helper.pTomato,
				data.helper.uomKg);

		final List<I_M_HU> createdTUsAfterSplit = transferLutuProducer.getCreatedHUs();
		assertThat(createdTUsAfterSplit.size(), is(1));
		final I_M_HU createdTUAfterSplit = createdTUsAfterSplit.get(0);

		final Node createdTUAfterSplitXML = HUXmlConverter.toXml(createdTUAfterSplit);
		assertThat(createdTUAfterSplitXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));

		final Node huPaletXML = HUXmlConverter.toXml(createdLU);
		assertThat(huPaletXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("19.000")));
		// verify that below the LU level we have a "clean" split without rounding issues.
		assertThat(huPaletXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("1.000")));
		assertThat(huPaletXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("18.000")));
	}
}
