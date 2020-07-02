package de.metas.handlingunits.attribute.weightable;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class PlainWeightableTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void copyOf()
	{
		final I_C_UOM uom = BusinessTestHelper.createUOM("uom");
		final PlainWeightable weightable = PlainWeightable.builder()
				.uom(uom)
				.weightGross(new BigDecimal("1"))
				.weightNet(new BigDecimal("2"))
				.weightTare(new BigDecimal("3"))
				.weightTareAdjust(new BigDecimal("4"))
				.build();

		assertThat(PlainWeightable.copyOf(weightable)).isEqualTo(weightable);
	}
}
