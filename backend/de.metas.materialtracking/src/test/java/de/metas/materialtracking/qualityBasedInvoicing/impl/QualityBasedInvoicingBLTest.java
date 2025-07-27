package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.hamcrest.Matchers.comparesEqualTo;


import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingBL;
import de.metas.util.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QualityBasedInvoicingBLTest
{
	/** service under test */
	private QualityBasedInvoicingBL qualityBasedInvoicingBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.qualityBasedInvoicingBL = (QualityBasedInvoicingBL)Services.get(IQualityBasedInvoicingBL.class);
	}

	@Test
	public void testCreateProjectedQty()
	{
		final I_C_UOM uom = createUOM(2);

		Assertions.assertThat(
				qualityBasedInvoicingBL.calculateProjectedQty(new BigDecimal("100"), // fullRaw
						new BigDecimal("10"),  // sampleRaw
						new BigDecimal("8"),// sampleProduced
						uom
						)).isEqualByComparingTo(new BigDecimal("80"));

		// doesn't make sense, but shall not fail
		Assertions.assertThat(
				qualityBasedInvoicingBL.calculateProjectedQty(new BigDecimal("0"), // fullRaw
						new BigDecimal("10"),  // sampleRaw
						new BigDecimal("8"), // sampleProduced
						uom
						)).isEqualByComparingTo(new BigDecimal("0"));
	}

	private I_C_UOM createUOM(final int precision)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.create(Env.getCtx(), I_C_UOM.class, ITrx.TRXNAME_None);
		uom.setStdPrecision(precision);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}
}
