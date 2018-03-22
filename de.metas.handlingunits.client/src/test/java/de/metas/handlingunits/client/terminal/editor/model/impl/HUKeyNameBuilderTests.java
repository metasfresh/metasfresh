package de.metas.handlingunits.client.terminal.editor.model.impl;

import static de.metas.business.BusinessTestHelper.createProduct;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

/*
 * #%L
 * de.metas.handlingunits.client
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
 * Verifies the behavior of {@link HUKeyNameBuilder}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUKeyNameBuilderTests
{
	private HUTestHelper helper;
	private I_M_HU_PI huDefIFCO;
	private I_M_Product pTomatoKg;

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	/**
	 * PI for a plalet. Can hold 10 {@link #huDefIFCO}.
	 */
	private I_M_HU_PI huDefPalet;

	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();

		pTomatoKg = createProduct(HUTestHelper.NAME_Tomato_Product, helper.uomKg);

		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomatoKg, BigDecimal.TEN, helper.uomKg);
			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, helper.pmIFCO);
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, BigDecimal.TEN);
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, helper.pmPalet);
		}
	}

	@Test
	public void testLUWithHUAggregate()
	{
		final IPair<ITerminalContext, ITerminalContextReferences> contextAndRefs = TerminalContextFactory.get().createContextAndRefs();
		final ITerminalContext terminalCtx = contextAndRefs.getLeft();
		final IHUKeyFactory keyFactory = new HUKeyFactory();
		keyFactory.setTerminalContext(terminalCtx);

		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				pTomatoKg, // product with UOM
				new BigDecimal("86") // qty
		);

		final I_M_HU lu = helper.createHUsFromSimplePI(incomingTrxDoc, huDefPalet).get(0);

		final IAttributeStorageFactory attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(lu);
		attributeStorage.setValue(helper.attr_WeightGross, new BigDecimal("102.3248"));
		attributeStorage.saveChangesIfNeeded();

		final HUKey luKey = new HUKey(keyFactory, lu, null);
		assertThat(luKey.isAggregateHU(), is(false));
		assertThat(luKey.isReadonly(), is(false)); // e.g. we want to be able to split from this aggregated HU.

		final HUKeyNameBuilder huKeyNameBuilder = new HUKeyNameBuilder(luKey);
		final String luDisplayName = huKeyNameBuilder.build();

		final String expecteLUDisplayName = "<center>"
				+ lu.getM_HU_ID() + " - 9 TU<br>" // M_HU.Value and the aggregate item's Qty
				+ "(Palet)<br>" // PI's name
				+ "Tomato<br>- 86 x Kg -<br>" // product name name CU Qty & UOM
				+ "68,325 Kg</center>" // net weight; tare: 1x25+9x1=34;
		;
		assertThat(luDisplayName, is(expecteLUDisplayName));
		assertThat(luKey.getName(), is(expecteLUDisplayName));
	}

	@Test
	public void testHUAggregate()
	{
		final IPair<ITerminalContext, ITerminalContextReferences> contextAndRefs = TerminalContextFactory.get().createContextAndRefs();
		final ITerminalContext terminalCtx = contextAndRefs.getLeft();
		final IHUKeyFactory keyFactory = new HUKeyFactory();
		keyFactory.setTerminalContext(terminalCtx);

		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				pTomatoKg, // product with UOM
				new BigDecimal("86") // qty
		);

		final I_M_HU lu = helper.createHUsFromSimplePI(incomingTrxDoc, huDefPalet).get(0);

		final List<I_M_HU> includedHUs = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(lu);
		assertThat(includedHUs.size(), is(2)); // there shall be one aggregate HU containing the 80xCU and one "real" IFCO containing the remaining 6xCU

		final I_M_HU aggregateHu = includedHUs.get(1); // 2nd one because of the was retrieveIncludedHUs() orders them
		assertThat(handlingUnitsBL.isAggregateHU(aggregateHu), is(true)); // guard

		final HUKey aggregateHuKey = new HUKey(keyFactory, aggregateHu, null);
		assertThat(aggregateHuKey.isAggregateHU(), is(true));
		assertThat(aggregateHuKey.isReadonly(), is(false)); // e.g. we want to be able to split from this aggregated HU.

		final HUKeyNameBuilder aggregateHuKeyNameBuilder = new HUKeyNameBuilder(aggregateHuKey);
		final String aggregateHuDisplayName = aggregateHuKeyNameBuilder.build();

		final String expecteAggregateHUDisplayName = "<center>"
				+ aggregateHu.getM_HU_ID() + " - 8 TU<br>" // M_HU.Value and the aggregate item's Qty
				+ "(IFCO)<br>" // PI's name
				+ "Tomato<br>- 80 x Kg -</center>";
		assertThat(aggregateHuDisplayName, is(expecteAggregateHUDisplayName));
	}


	/**
	 * Creates an LU with an just empty aggregate HU <i>stub</i> and verifies that this "stub" is ignored HU-key wise.
	 */
	@Test
	public void testHUAggregateStub()
	{
		final IPair<ITerminalContext, ITerminalContextReferences> contextAndRefs = TerminalContextFactory.get().createContextAndRefs();
		final ITerminalContext terminalCtx = contextAndRefs.getLeft();
		final IHUKeyFactory keyFactory = new HUKeyFactory();
		keyFactory.setTerminalContext(terminalCtx);

		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				pTomatoKg, // product with UOM
				new BigDecimal("6") // qty
		);

		final I_M_HU lu = helper.createHUsFromSimplePI(incomingTrxDoc, huDefPalet).get(0);

		final HUKey luKey = new HUKey(keyFactory, lu, null);
		assertThat(luKey.isAggregateHU(), is(false));

		final List<IHUKey> children = luKey.getChildren(); // create the children without problems and..
		assertThat(children.size(), is(1)); // ignore the stub
		assertThat(HUKey.cast(children.get(0)).isAggregateHU(), is(false));
	}
}
