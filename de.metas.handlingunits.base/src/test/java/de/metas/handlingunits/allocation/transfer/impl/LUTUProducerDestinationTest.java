package de.metas.handlingunits.allocation.transfer.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class LUTUProducerDestinationTest
{

	private HUTestHelper helper;

	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();
	}

	@Test
	public void setLUPI_checks_HU_Type()
	{
		final I_M_HU_PI huDefPaletWithWrongType = helper.createHUDefinition(
				HUTestHelper.NAME_Palet_Product,
				X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		final LUTUProducerDestination lutuProducerDestination = new LUTUProducerDestination();
		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(() -> lutuProducerDestination.setLUPI(huDefPaletWithWrongType))
				.withMessageContaining("The M_HU_PI_Version of the given parameter pi needs to have type=" + X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
	}

	@Test
	public void setTUPI_checks_HU_Type()
	{
		final I_M_HU_PI huDefPaletWithWrongType = helper.createHUDefinition(
				HUTestHelper.NAME_IFCO_Product,
				X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		final LUTUProducerDestination lutuProducerDestination = new LUTUProducerDestination();
		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(() -> lutuProducerDestination.setTUPI(huDefPaletWithWrongType))
				.withMessageContaining("The M_HU_PI_Version of the given parameter pi needs to have type=" + X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
	}

}
