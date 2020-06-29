package de.metas.uom.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM_Conversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.uom.UOMConversionRate;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class UOMConversionDAOTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Nested
	public class toUOMConversionOrNull
	{
		@Test
		public void check_DivideRate_is_ignored()
		{
			final I_C_UOM_Conversion record = newInstance(I_C_UOM_Conversion.class);
			record.setC_UOM_ID(1);
			record.setC_UOM_To_ID(2);
			record.setMultiplyRate(new BigDecimal("2"));
			record.setDivideRate(new BigDecimal("3"));

			final UOMConversionRate rate = UOMConversionDAO.toUOMConversionOrNull(record);
			assertThat(rate.getFromToMultiplier()).isEqualTo("2");
			assertThat(rate.invert().getFromToMultiplier()).isEqualTo("0.5");
		}
	}
}
