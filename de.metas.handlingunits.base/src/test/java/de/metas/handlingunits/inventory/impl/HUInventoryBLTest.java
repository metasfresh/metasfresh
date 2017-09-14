package de.metas.handlingunits.inventory.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Status;
import de.metas.handlingunits.model.validator.M_HU;

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

public class HUInventoryBLTest
{
	private LUTUProducerDestinationTestSupport data;

	private I_M_Locator locator;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();

		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory);
		docType.setDocSubType(X_C_DocType.DOCSUBTYPE_MaterialDisposal);
		save(docType);

		final I_M_Warehouse wh = newInstance(I_M_Warehouse.class);
		save(wh);

		locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse(wh);
		save(locator);
	}

	@Test
	public void testMoveToGarbage()
	{
		final I_M_HU createdHU = createLUWithTUs();
		final ImmutableList<I_M_HU> hus = ImmutableList.of(createdHU);

		new HUInventoryBL().moveToGarbage(
				hus,
				SystemTime.asTimestamp());
		assertHusDestroyed(hus);
	}

	@Test
	public void testSplitThenMoveToGarbage()
	{
		final I_M_HU lu = createLUWithTUs();
		final List<I_M_HU> tus = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(lu);
		assertThat(tus).hasSize(1);

		final List<I_M_HU> newTUs = HUTransformService
				.get(data.helper.getHUContext())
				.tuToNewTUs(tus.get(0), BigDecimal.valueOf(3), false);
		assertThat(newTUs).hasSize(3);

		new HUInventoryBL().moveToGarbage(newTUs, SystemTime.asTimestamp());
		assertHusDestroyed(newTUs);
	}

	private void assertHusDestroyed(final List<I_M_HU> newTUs)
	{
		newTUs.forEach(
				tu -> {
					refresh(tu);
					assertThat(tu.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Destroyed);
				});
	}

	private I_M_HU createLUWithTUs()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setM_Locator(locator);

		// one IFCO can hold 40kg tomatoes
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("200"), data.helper.uomKg);

		// guard: we did not need lutuConfig.
		assertThat(lutuProducer.getM_HU_LUTU_Configuration()).isNull();
		assertThat(lutuProducer.getCreatedHUsCount()).isEqualTo(1);

		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();
		assertThat(createdLUs).hasSize(1);

		final I_M_HU createdLU = createdLUs.get(0);
		final IMutableHUContext huContext = data.helper.createMutableHUContextOutOfTransaction();
		Services.get(IHandlingUnitsBL.class).setHUStatus(huContext, createdLU, X_M_HU_Status.HUSTATUS_Active);
		assertThat(createdLU.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		new M_HU().updateChildren(createdLU);
		save(createdLU);

		return createdLUs.get(0);
	}

}
