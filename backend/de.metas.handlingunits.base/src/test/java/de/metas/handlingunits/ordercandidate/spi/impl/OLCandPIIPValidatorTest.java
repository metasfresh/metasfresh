/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.ordercandidate.spi.impl;

import de.metas.handlingunits.model.I_C_OLCand;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.uom.X12DE355;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;


public class OLCandPIIPValidatorTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Verifies that the validator does not change the Qty if a olCand has no PIIP and doesn't throw an exception etc.
	 */
	@Test
	public void testNoPIIPQtyOnChanged()
	{
		final I_C_OLCand olCand = InterfaceWrapperHelper.newInstance(I_C_OLCand.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		olCand.setQtyEntered(BigDecimal.TEN);
		InterfaceWrapperHelper.save(olCand);

		// invoke the method under test
		new OLCandPIIPPriceValidator().validate(olCand);

		assertThat(olCand.getQtyEntered()).isEqualByComparingTo(BigDecimal.TEN);
	}

	@Test
	public void testNOPIIsOrderInTuUomWhenMatched()
	{
		final I_C_UOM eachUOM = createUOM(X12DE355.EACH);

		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		saveRecord(piItem);

		final I_M_HU_PI_Item_Product huPiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		huPiItemProduct.setIsOrderInTuUomWhenMatched(false);
		huPiItemProduct.setM_HU_PI_Item(piItem);

		saveRecord(huPiItemProduct);

		final I_C_OLCand olCand = InterfaceWrapperHelper.newInstance(I_C_OLCand.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		olCand.setQtyEntered(BigDecimal.TEN);
		olCand.setC_UOM_Internal_ID(eachUOM.getC_UOM_ID());
		olCand.setM_HU_PI_Item_Product_ID(huPiItemProduct.getM_HU_PI_Item_Product_ID());
		saveRecord(olCand);

		// invoke the method under test
		new OLCandPIIPPriceValidator().validate(olCand);

		assertThat(olCand.getC_UOM_Internal_ID()).isEqualTo(eachUOM.getC_UOM_ID());
	}

	@Test
	public void testPIIsOrderInTuUomWhenMatched()
	{
		final I_C_UOM coliUOM = createUOM(X12DE355.COLI);
		final I_C_UOM eachUOM = createUOM(X12DE355.EACH);

		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		saveRecord(piItem);

		final I_M_HU_PI_Item_Product huPiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		huPiItemProduct.setIsOrderInTuUomWhenMatched(true);
		huPiItemProduct.setM_HU_PI_Item(piItem);

		saveRecord(huPiItemProduct);

		final I_C_OLCand olCand = InterfaceWrapperHelper.newInstance(I_C_OLCand.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		olCand.setC_UOM_Internal_ID(eachUOM.getC_UOM_ID());
		olCand.setM_HU_PI_Item_Product_ID(huPiItemProduct.getM_HU_PI_Item_Product_ID());
		saveRecord(olCand);

		// invoke the method under test
		new OLCandPIIPPriceValidator().validate(olCand);

		assertThat(olCand.getC_UOM_Internal_ID()).isEqualTo(coliUOM.getC_UOM_ID());
	}

	private I_C_UOM createUOM(final X12DE355 coli)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		uom.setX12DE355(coli.getCode());
		saveRecord(uom);

		return uom;
	}

}
