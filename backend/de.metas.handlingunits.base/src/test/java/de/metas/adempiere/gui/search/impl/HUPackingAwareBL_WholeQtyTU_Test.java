package de.metas.adempiere.gui.search.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * A TU quantity is always a whole number: a fractional TU quantity is refused with a user-validation error
 * instead of being silently truncated (e.g. 1.5 TU used to become 1 TU).
 */
class HUPackingAwareBL_WholeQtyTU_Test
{
	/**
	 * 6 CU per TU
	 */
	private static final BigDecimal CAPACITY_6 = new BigDecimal("6");

	private HUPackingAwareBL huPackingAwareBL;
	private I_C_UOM uom;
	private ProductId productId;
	private I_M_HU_PI_Item_Product pip;

	@BeforeEach
	void beforeEach()
	{
		final HUTestHelper helper = HUTestHelper.newInstanceOutOfTrx();
		huPackingAwareBL = new HUPackingAwareBL();

		uom = helper.uomEach;
		productId = ProductId.ofRepoId(BusinessTestHelper.createProduct("Product", uom).getM_Product_ID());

		final I_M_HU_PI piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piItem = helper.createHU_PI_Item_Material(piTU);
		pip = helper.assignProduct(piItem, productId, CAPACITY_6, uom);
	}

	private PlainHUPackingAware newPackingAware()
	{
		final PlainHUPackingAware packingAware = new PlainHUPackingAware();
		packingAware.setM_Product_ID(productId.getRepoId());
		packingAware.setC_UOM_ID(uom.getC_UOM_ID());
		packingAware.setM_HU_PI_Item_Product_ID(pip.getM_HU_PI_Item_Product_ID());
		return packingAware;
	}

	@Test
	void quickInput_wholeQtyTU_computesQtyCU()
	{
		final PlainHUPackingAware packingAware = newPackingAware();

		huPackingAwareBL.computeAndSetQtysForNewHuPackingAware(packingAware, new BigDecimal("2"));

		assertThat(packingAware.getQtyTU()).isEqualByComparingTo("2");
		assertThat(packingAware.getQty()).isEqualByComparingTo("12");
	}

	@Test
	void quickInput_wholeQtyTU_withTrailingZeros_computesQtyCU()
	{
		final PlainHUPackingAware packingAware = newPackingAware();

		huPackingAwareBL.computeAndSetQtysForNewHuPackingAware(packingAware, new BigDecimal("2.000"));

		assertThat(packingAware.getQtyTU()).isEqualByComparingTo("2");
		assertThat(packingAware.getQty()).isEqualByComparingTo("12");
	}

	@Test
	void quickInput_fractionalQtyTU_isRefused()
	{
		final PlainHUPackingAware packingAware = newPackingAware();

		assertThatThrownBy(() -> huPackingAwareBL.computeAndSetQtysForNewHuPackingAware(packingAware, new BigDecimal("1.5")))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue())
				.hasMessageContaining(HUPackingAwareBL.MSG_QtyTU_MustBeWholeNumber.toAD_Message());
	}

	@Test
	void setQtyCUFromQtyTU_wholeQtyTU_computesQtyCU()
	{
		final PlainHUPackingAware packingAware = newPackingAware();

		huPackingAwareBL.setQtyCUFromQtyTU(packingAware, new BigDecimal("2"));

		assertThat(packingAware.getQty()).isEqualByComparingTo("12");
	}

	@Test
	void setQtyCUFromQtyTU_fractionalQtyTU_isRefused()
	{
		final PlainHUPackingAware packingAware = newPackingAware();

		assertThatThrownBy(() -> huPackingAwareBL.setQtyCUFromQtyTU(packingAware, new BigDecimal("1.5")))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue())
				.hasMessageContaining(HUPackingAwareBL.MSG_QtyTU_MustBeWholeNumber.toAD_Message());
	}
}
