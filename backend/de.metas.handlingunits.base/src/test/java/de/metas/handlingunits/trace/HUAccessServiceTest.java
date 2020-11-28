package de.metas.handlingunits.trace;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU;
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

public class HUAccessServiceTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void retrieveTopLevelHuId_when_shipped_then_stillNotMinusOne()
	{
		final I_M_HU shippedHu = newInstance(I_M_HU.class);
		shippedHu.setHUStatus(X_M_HU.HUSTATUS_Shipped);
		save(shippedHu);

		final int topLevelHuId = new HUAccessService().retrieveTopLevelHuId(shippedHu);
		assertThat(topLevelHuId).isEqualTo(shippedHu.getM_HU_ID());
	}

}
