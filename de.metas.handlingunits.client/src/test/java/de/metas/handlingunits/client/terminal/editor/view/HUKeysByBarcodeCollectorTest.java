package de.metas.handlingunits.client.terminal.editor.view;

/*
 * #%L
 * de.metas.handlingunits.client
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

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU;

public class HUKeysByBarcodeCollectorTest
{
	private Properties ctx;
	private PlainContextAware contextProvider;

	@Before
	public void initialize()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();
		contextProvider = new PlainContextAware(ctx, ITrx.TRXNAME_None);
	}

	private I_M_HU createHU(final String barcode)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, contextProvider);
		// NOTE: matcher is matching the HU's Barcode by M_HU.Value
		hu.setValue(barcode);
		InterfaceWrapperHelper.save(hu);
		return hu;
	}

	private void assertBarcodeMatches(final boolean matchedExpected, final HUKeysByBarcodeCollector barcodeMatcher, final I_M_HU hu)
	{
		final boolean matchedActual = barcodeMatcher.match(hu);
		final String message = "Invalid matching result"
				+ "\nMatcher=" + barcodeMatcher
				+ "\nHU=" + hu
				+ "\n";
		Assert.assertEquals(message, matchedExpected, matchedActual);
	}

	@Test(expected = AdempiereException.class)
	public void init_Error_NullBarcode()
	{
		// shall throw exception because null barcode is not allowed
		new HUKeysByBarcodeCollector(ctx, null);
	}

	@Test(expected = AdempiereException.class)
	public void init_Error_EmptyBarcode()
	{
		// shall throw exception because empty barcode is not allowed
		new HUKeysByBarcodeCollector(ctx, "   ");
	}

	@Test
	public void matcher_Matched()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU("12345");

		assertBarcodeMatches(true, barcodeMatcher, hu);
	}

	@Test
	public void matcher_Matched_BarcodeWithWhitespaces()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345     ");
		final I_M_HU hu = createHU("12345");

		assertBarcodeMatches(true, barcodeMatcher, hu);
	}

	@Test
	public void matcher_Matched_NotTopLevelHU()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU("12345");

		// Make our HU to be a not top level HU
		// i.e. setting some dummy value on M_HU_Item_Parent_ID
		hu.setM_HU_Item_Parent_ID(1000000);
		InterfaceWrapperHelper.save(hu);

		assertBarcodeMatches(true, barcodeMatcher, hu);
	}

	@Test
	public void matcher_NotMatched_InactiveHU()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU("12345");

		// Inactivate our HU

		// i.e. setting some dummy value on M_HU_Item_Parent_ID
		hu.setIsActive(false);
		InterfaceWrapperHelper.save(hu);

		assertBarcodeMatches(false, barcodeMatcher, hu);
	}

	@Test
	public void matcher_NotMatched()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU("11111");

		assertBarcodeMatches(false, barcodeMatcher, hu);
	}

	@Test
	public void matcher_NotMatched_NullHUBarcode()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU(null);

		assertBarcodeMatches(false, barcodeMatcher, hu);
	}
}
