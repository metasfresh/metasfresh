package de.metas.handlingunits.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.handlingunits.model.X_M_HU;

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

public class HUStatusBLTest
{
	@Test
	public void testIsStatusTransitionAllowed()
	{
		assertThat(new HUStatusBL().isStatusTransitionAllowed(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Destroyed)).isTrue();
		assertThat(new HUStatusBL().isStatusTransitionAllowed(X_M_HU.HUSTATUS_Picked, X_M_HU.HUSTATUS_Issued)).isFalse();
		assertThat(new HUStatusBL().isStatusTransitionAllowed(X_M_HU.HUSTATUS_Active, "unexpectedstatus")).isFalse();
	}
}
