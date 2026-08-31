/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.model.validator;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.slot.IHUPickingSlotBL;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Package;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Completing a delivery instruction evicts the HUs of its shipping packages from their picking slots - only its
 * ACTIVE packages: an {@code IsActive='N'} package's HU may be queued in a picking slot for something else.
 */
class ShipperTransportationPickingSlotEvictionTest
{
	private IHUPickingSlotBL huPickingSlotBL;
	private IHUPackageDAO huPackageDAO;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		huPickingSlotBL = Mockito.mock(IHUPickingSlotBL.class);
		huPackageDAO = Mockito.mock(IHUPackageDAO.class);
		Services.registerService(IHUPickingSlotBL.class, huPickingSlotBL);
		Services.registerService(IHUPackageDAO.class, huPackageDAO);
	}

	private I_M_HU hu()
	{
		final I_M_HU record = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	/** A package on the given instruction, carrying its own {@code M_Package}, whose HU is {@code hu}. */
	private void shippingPackage(
			final int shipperTransportationId,
			final boolean active,
			final I_M_HU hu)
	{
		final I_M_Package mpackage = InterfaceWrapperHelper.newInstance(I_M_Package.class);
		InterfaceWrapperHelper.save(mpackage);

		final I_M_ShippingPackage record = InterfaceWrapperHelper.newInstance(I_M_ShippingPackage.class);
		record.setM_ShipperTransportation_ID(shipperTransportationId);
		record.setM_Package_ID(mpackage.getM_Package_ID());
		record.setIsActive(active);
		InterfaceWrapperHelper.save(record);

		Mockito.when(huPackageDAO.retrieveHUs(Mockito.argThat(
						p -> p != null && p.getM_Package_ID() == mpackage.getM_Package_ID())))
				.thenReturn(ImmutableList.of(hu));
	}

	@Test
	@DisplayName("completing an instruction evicts only its ACTIVE packages' HUs, never a retired package's")
	void retiredPackagesHUIsNotEvictedFromItsPickingSlot()
	{
		final I_M_ShipperTransportation instruction = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		InterfaceWrapperHelper.save(instruction);

		final I_M_HU loadedHU = hu();
		final I_M_HU retiredPlanningsHU = hu();
		shippingPackage(instruction.getM_ShipperTransportation_ID(), true, loadedHU);
		shippingPackage(instruction.getM_ShipperTransportation_ID(), false, retiredPlanningsHU);

		new M_ShipperTransportation().removeHUsFromPickingSlot(instruction);

		final ArgumentCaptor<I_M_HU> evicted = ArgumentCaptor.forClass(I_M_HU.class);
		Mockito.verify(huPickingSlotBL, Mockito.atLeastOnce()).removeFromPickingSlotQueueRecursivelly(evicted.capture());

		assertThat(evicted.getAllValues())
				.as("the retired package belongs to a planning that was taken off this instruction; its HU was "
						+ "never on this truck and may be queued in an active picking slot for something else")
				.extracting(I_M_HU::getM_HU_ID)
				.containsExactly(loadedHU.getM_HU_ID());
	}
}
