package de.metas.handlingunits.client.terminal.editor.model.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

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
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
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

public class HUKeyNameBuilderTests
{
	private HUTestHelper helper;
	private I_M_HU_PI huDefIFCO;
	private I_M_Product pTomatoKg;

	/**
	 * PI for a plalet. Can hold 10 {@link #huDefIFCO}.
	 */
	private I_M_HU_PI huDefPalet;

	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();

		pTomatoKg = helper.createProduct(HUTestHelper.NAME_Tomato_Product, helper.uomKg);

		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomatoKg, BigDecimal.TEN, helper.uomKg);
			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, helper.pmIFCO);
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, BigDecimal.TEN);
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, helper.pmPalet);
		}
	}

	@Test
	public void testWithHUAggregate()
	{
		final IPair<ITerminalContext, ITerminalContextReferences> contextAndRefs = TerminalContextFactory.get().createContextAndRefs();
		final ITerminalContext terminalCtx = contextAndRefs.getLeft();
		final IHUKeyFactory keyFactory = new HUKeyFactory();
		keyFactory.setTerminalContext(terminalCtx);

		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				pTomatoKg, // product
				new BigDecimal("86") // qty
		);

		final I_M_HU hu = helper.createHUsFromSimplePI(incomingTrxDoc, huDefPalet).get(0);

		final IAttributeStorageFactory attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);
		attributeStorage.setValue(helper.attr_WeightGross, new BigDecimal("102.3248"));
		attributeStorage.saveChangesIfNeeded();

		final HUKey huKey = new HUKey(keyFactory, hu, null);
		final HUKeyNameBuilder huKeyNameBuilder = new HUKeyNameBuilder(huKey);
		final String huDisplayName = huKeyNameBuilder.build();
		//
		final String expecteHUDisplayName = "<center>"
				+ hu.getM_HU_ID() + " - 9 TU<br>" // M_HU.Value and the aggregate item's Qty
				+ "(Palet)<br>" // PI's name
				+ "Tomato<br>- 86 x Kg -<br>" // product name name CU Qty & UOM
				+ "68,325 Kg</center>" // net weight; tare: 1x25+9x1=34;
		;
		assertThat(huDisplayName, is(expecteHUDisplayName));
	}
}
