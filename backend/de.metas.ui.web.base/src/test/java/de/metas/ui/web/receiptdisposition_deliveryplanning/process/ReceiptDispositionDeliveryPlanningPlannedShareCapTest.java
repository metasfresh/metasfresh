/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import de.metas.handlingunits.receiptschedule.ReceiptScheduleLUTUConfigurations;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The receive quick action must offer the SELECTED PLANNING's quantity, not the whole order line's.
 * <p>
 * Reported from the window: a row planned for 50, on a receipt schedule of 100 because a second planning had
 * been added by the split process, offered "100".
 * {@code ReceiptScheduleLUTUConfigurations.adjustToDefaults} derives QtyTU from
 * {@code getQtyToMoveTU(receiptSchedule)}, and that one configuration is what the caption prints, what the
 * operator's config form is pre-filled with, and what the allocation is sized from - so the user was shown 100
 * and, on the operator-stated path, would have booked the sibling's share too.
 */
class ReceiptDispositionDeliveryPlanningPlannedShareCapTest
{
	private static final UomId UOM_ID = UomId.ofRepoId(540001);

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// Quantitys.of resolves the UomId through UOMDAO, so the record has to exist - a bare id fails with
		// "No cached object found for clazz=I_C_UOM".
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setC_UOM_ID(UOM_ID.getRepoId());
		uom.setStdPrecision(0);
		InterfaceWrapperHelper.saveRecord(uom);

		// A no-LU configuration keeps each assertion on the single quantity under test, QtyTU.
		final ILUTUConfigurationFactory lutuConfigurationFactory = Mockito.mock(ILUTUConfigurationFactory.class);
		Mockito.doReturn(true).when(lutuConfigurationFactory).isNoLU(Mockito.any());
		Services.registerService(ILUTUConfigurationFactory.class, lutuConfigurationFactory);
	}

	/** 10 TUs x 10 CUs = the whole 100-unit order line, which is what adjustToDefaults produces. */
	private static I_M_HU_LUTU_Configuration wholeOrderLineConfiguration()
	{
		final I_M_HU_LUTU_Configuration lutuConfig = InterfaceWrapperHelper.newInstance(I_M_HU_LUTU_Configuration.class);
		lutuConfig.setQtyTU(new BigDecimal("10"));
		lutuConfig.setQtyCUsPerTU(new BigDecimal("10"));
		lutuConfig.setIsInfiniteQtyCU(false);
		InterfaceWrapperHelper.saveRecord(lutuConfig);
		return lutuConfig;
	}

	private static Quantity share(final String qty)
	{
		return Quantitys.of(new BigDecimal(qty), UOM_ID);
	}

	@Test
	void plannedRow_isCappedToItsOwnShare_notTheWholeOrderLine()
	{
		final I_M_HU_LUTU_Configuration lutuConfig = wholeOrderLineConfiguration();

		ReceiptScheduleLUTUConfigurations.capToPlannedShare(lutuConfig, share("50"));

		assertThat(lutuConfig.getQtyTU())
				.as("a row planned for 50 of a 100 order line must offer 5 TUs of 10, not the schedule's 10")
				.isEqualByComparingTo("5");
	}

	@Test
	void unplannedRow_keepsTheScheduleDerivedQuantity()
	{
		final I_M_HU_LUTU_Configuration lutuConfig = wholeOrderLineConfiguration();

		// null share = an UNPLANNED row: there the schedule-derived configuration is the correct one.
		ReceiptScheduleLUTUConfigurations.capToPlannedShare(lutuConfig, null);

		assertThat(lutuConfig.getQtyTU()).isEqualByComparingTo("10");
	}

	@Test
	void shareLargerThanThePacking_isNotTheBindingLimit()
	{
		final I_M_HU_LUTU_Configuration lutuConfig = wholeOrderLineConfiguration();

		ReceiptScheduleLUTUConfigurations.capToPlannedShare(lutuConfig, share("500"));

		assertThat(lutuConfig.getQtyTU())
				.as("the packing already fits inside the share, so it must be left alone")
				.isEqualByComparingTo("10");
	}

	@Test
	void aShareSmallerThanOneTU_shrinksThatTUsContentsInsteadOfItsCount()
	{
		final I_M_HU_LUTU_Configuration lutuConfig = wholeOrderLineConfiguration();

		ReceiptScheduleLUTUConfigurations.capToPlannedShare(lutuConfig, share("5"));

		assertThat(lutuConfig.getQtyTU())
				.as("five CUs of a ten-per-TU packing is still ONE TU - the count cannot express the share")
				.isEqualByComparingTo("1");
		assertThat(lutuConfig.getQtyCUsPerTU())
				.as("so the share has to come out of what that TU HOLDS, or the producer fills it to ten")
				.isEqualByComparingTo("5");
		assertThat(lutuConfig.isInfiniteQtyCU())
				.as("a capped CU count is meaningless while the CU quantity is still infinite")
				.isFalse();
	}

	@Test
	void roundingUpTheTUCountLeavesTheCONFIGURATIONHoldingMoreThanTheShare()
	{
		// The reason the caller must clamp the ALLOCATION separately and cannot trust the configuration's own
		// capacity. 15 CUs at ten per TU rounds up to two TUs, and QtyCUsPerTU stays ten - so the configuration
		// can hold 20 while only 15 is owed to this planning. Allocating the capacity would draw five units
		// belonging to the sibling planning on the same receipt schedule.
		final I_M_HU_LUTU_Configuration lutuConfig = wholeOrderLineConfiguration();

		ReceiptScheduleLUTUConfigurations.capToPlannedShare(lutuConfig, share("15"));

		assertThat(lutuConfig.getQtyTU()).isEqualByComparingTo("2");
		assertThat(lutuConfig.getQtyCUsPerTU())
				.as("left at the packing's own figure - this is what makes capacity exceed the share")
				.isEqualByComparingTo("10");
		assertThat(lutuConfig.getQtyTU().multiply(lutuConfig.getQtyCUsPerTU()))
				.as("capacity 20 against a share of 15: the gap the allocation clamp exists to close")
				.isEqualByComparingTo("20");
	}

	@Test
	void aPartialTUStillHasToBeReceived_soRoundingIsUp()
	{
		final I_M_HU_LUTU_Configuration lutuConfig = wholeOrderLineConfiguration();

		ReceiptScheduleLUTUConfigurations.capToPlannedShare(lutuConfig, share("55"));

		assertThat(lutuConfig.getQtyTU())
				.as("55 CUs at 10 per TU needs 6 TUs, not 5 - a partial TU still has to be received")
				.isEqualByComparingTo("6");
	}
}
