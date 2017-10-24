package de.metas.handlingunits.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HandlingUnitsBLTests
{
	/**
	 * Verifies that {@link HandlingUnitsBL#isAggregateHU(I_M_HU)} returns {@code false} for a null param. This is a trivial test, but we rely on that behavior of the isAggregateHU() method.
	 */
	@Test
	public void testIsAggregateHUwithNull()
	{
		assertThat(new HandlingUnitsBL().isAggregateHU(null), is(false));
	}
}
