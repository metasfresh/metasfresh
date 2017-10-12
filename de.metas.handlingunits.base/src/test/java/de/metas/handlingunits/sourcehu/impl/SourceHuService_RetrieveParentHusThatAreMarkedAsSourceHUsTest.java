package de.metas.handlingunits.sourcehu.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.sourcehu.SourceHUsService;

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

public class SourceHuService_RetrieveParentHusThatAreMarkedAsSourceHUsTest
{
	private SourceHUsService sourceHuService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		sourceHuService = new SourceHUsService();
	}

	@Test
	public void testHuAndNoSourceHuRecord()
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		save(hu);

		final List<I_M_HU> result = sourceHuService.retrieveParentHusThatAreSourceHUs(ImmutableList.of(hu));
		assertThat(result).isEmpty();
	}

	/**
	 * We have one HU that is flagged with a {@link I_M_Source_HU} record.<br>
	 * Verify that this HU is returned.
	 */
	@Test
	public void testHuWithSourceHuRecord()
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		save(hu);

		final I_M_Source_HU sourceHU = newInstance(I_M_Source_HU.class);
		sourceHU.setM_HU(hu);
		save(sourceHU);

		final List<I_M_HU> result = sourceHuService.retrieveParentHusThatAreSourceHUs(ImmutableList.of(hu));
		assertThat(result).containsExactly(hu);
	}

	/**
	 * We have a LU - TU -VHU and the TU is flagged with a {@link I_M_Source_HU} record.<br>
	 * Verify that only the TU is returned.
	 */
	@Test
	public void testVhuWithTuAndLuAndSourceHuRecordForTu()
	{
		final I_M_HU lu = newInstance(I_M_HU.class);
		lu.setHUStatus(X_M_HU.HUSTATUS_Active);
		save(lu);
		POJOWrapper.setInstanceName(lu, "lu");

		final I_M_HU_Item luItem = newInstance(I_M_HU_Item.class);
		luItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		luItem.setM_HU(lu);
		save(luItem);

		final I_M_HU tu = newInstance(I_M_HU.class);
		tu.setHUStatus(X_M_HU.HUSTATUS_Active);
		tu.setM_HU_Item_Parent(luItem);
		save(tu);
		POJOWrapper.setInstanceName(tu, "tu");

		final I_M_HU_Item tuItem = newInstance(I_M_HU_Item.class);
		tuItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		tuItem.setM_HU(tu);
		save(tuItem);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_HU_Item_Parent(tuItem);
		save(vhu);
		POJOWrapper.setInstanceName(vhu, "vhu");

		final I_M_Source_HU sourceHU = newInstance(I_M_Source_HU.class);
		sourceHU.setM_HU(tu);
		save(sourceHU);

		final List<I_M_HU> result = sourceHuService.retrieveParentHusThatAreSourceHUs(ImmutableList.of(vhu));
		assertThat(result).containsExactly(tu);
	}
}
