package de.metas.distribution.ddorder.interceptor;

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

import de.metas.adempiere.gui.search.impl.HUPackingAwareBL;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A TU quantity is always a whole number: a fractional {@code DD_OrderLine.QtyEnteredTU} is refused with the
 * translated user-validation error, the same way as on order / invoice lines.
 */
class DD_OrderLine_WholeQtyTU_Test
{
	/**
	 * 6 CU per TU
	 */
	private static final BigDecimal CAPACITY_6 = new BigDecimal("6");

	private DD_OrderLine interceptor;
	private I_C_UOM uom;
	private ProductId productId;
	private I_M_HU_PI_Item_Product pip;

	@BeforeEach
	void beforeEach()
	{
		final HUTestHelper helper = HUTestHelper.newInstanceOutOfTrx();
		interceptor = new DD_OrderLine(Mockito.mock(DDOrderMoveScheduleService.class));

		uom = helper.uomEach;
		productId = ProductId.ofRepoId(BusinessTestHelper.createProduct("Product", uom).getM_Product_ID());

		final I_M_HU_PI piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piItem = helper.createHU_PI_Item_Material(piTU);
		pip = helper.assignProduct(piItem, productId, CAPACITY_6, uom);
	}

	private I_DD_OrderLine newDDOrderLine(final String qtyEnteredTU)
	{
		final I_DD_OrderLine ddOrderLine = newInstance(I_DD_OrderLine.class);
		ddOrderLine.setM_Product_ID(productId.getRepoId());
		ddOrderLine.setC_UOM_ID(uom.getC_UOM_ID());
		ddOrderLine.setM_HU_PI_Item_Product_ID(pip.getM_HU_PI_Item_Product_ID());
		ddOrderLine.setQtyEntered(new BigDecimal("12"));
		ddOrderLine.setQtyEnteredTU(new BigDecimal(qtyEnteredTU));
		return ddOrderLine;
	}

	private static void assertRefusedAsFractionalQtyTU(final ThrowingCallable call)
	{
		assertThatThrownBy(call)
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue())
				.hasMessageContaining(HUPackingAwareBL.MSG_QtyTU_MustBeWholeNumber.toAD_Message());
	}

	@Test
	void qtyTU_callout_wholeQtyTU_computesQtyCU()
	{
		final I_DD_OrderLine ddOrderLine = newDDOrderLine("3");

		interceptor.onQtyTU_Set_Callout(ddOrderLine);

		assertThat(ddOrderLine.getQtyEntered()).isEqualByComparingTo("18");
	}

	@Test
	void qtyTU_callout_fractionalQtyTU_isRefused()
	{
		final I_DD_OrderLine ddOrderLine = newDDOrderLine("1.5");

		assertRefusedAsFractionalQtyTU(() -> interceptor.onQtyTU_Set_Callout(ddOrderLine));
	}

	@Test
	void qtyTU_interceptor_fractionalQtyTU_isRefused()
	{
		final I_DD_OrderLine ddOrderLine = newDDOrderLine("2");
		saveRecord(ddOrderLine);

		ddOrderLine.setQtyEnteredTU(new BigDecimal("1.5")); // QtyEnteredTU alone changes => it drives QtyEntered

		assertRefusedAsFractionalQtyTU(() -> interceptor.onQtyTU_Set_Intercept(ddOrderLine));
	}

	@Test
	void packingInstructionChanged_fractionalQtyTU_isRefused()
	{
		final I_DD_OrderLine ddOrderLine = newDDOrderLine("1.5");

		assertRefusedAsFractionalQtyTU(() -> interceptor.onM_HU_PI_Item_Product_Set(ddOrderLine));
	}
}
