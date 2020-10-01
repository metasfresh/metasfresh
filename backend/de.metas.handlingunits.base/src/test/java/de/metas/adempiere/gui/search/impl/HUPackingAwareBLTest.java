package de.metas.adempiere.gui.search.impl;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.quantity.QuantityTU;
import de.metas.uom.IUOMDAO;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

class HUPackingAwareBLTest
{

	private HUPackingAwareBL huPackingAwareBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		huPackingAwareBL = new HUPackingAwareBL();
	}

	@Test
	void calculateQtyTU()
	{
		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("Coli", IUOMDAO.X12DE355_COLI);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		orderLineRecord.setQtyEntered(TEN);

		saveRecord(orderLineRecord);

		final OrderLineHUPackingAware orderLineHUPackingAware = new OrderLineHUPackingAware(orderLineRecord);

		// invoke the method under test
		final QuantityTU result = huPackingAwareBL.calculateQtyTU(orderLineHUPackingAware);

		assertThat(result).isEqualTo(QuantityTU.ofInt(10));
	}

}
