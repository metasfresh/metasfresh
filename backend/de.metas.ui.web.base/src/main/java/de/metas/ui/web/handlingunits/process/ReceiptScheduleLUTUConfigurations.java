/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The receipt schedule's LU/TU packing configuration, as the "receive HUs" actions read and default it - the ONE
 * definition, so the receipt-logistics window's actions receive into exactly the packing the receipt-schedule
 * window's do.
 * <p>
 * Extracted unchanged from {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_Base} and
 * {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults}, whose package-private / private members made both
 * steps unreachable from another package.
 */
@UtilityClass
public class ReceiptScheduleLUTUConfigurations
{
	/** The schedule's current (stored or freshly derived) configuration, guarded against being overwritten. */
	public static I_M_HU_LUTU_Configuration getCurrent(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfig = Services.get(IHUReceiptScheduleBL.class)
				.createLUTUConfigurationManager(receiptSchedule)
				.getCreateLUTUConfiguration();

		// Make sure nobody is overriding the existing configuration
		if (lutuConfig.getM_HU_LUTU_Configuration_ID() > 0)
		{
			InterfaceWrapperHelper.setSaveDeleteDisabled(lutuConfig, true);
		}

		return lutuConfig;
	}

	/**
	 * A NEW, unsaved copy of {@code template}, adjusted to the one-click defaults ({@link #adjustToDefaults}) -
	 * what "receive HUs using defaults" receives into.
	 * <p>
	 * Takes the template rather than deriving it: {@link #getCurrent} can CREATE the schedule's configuration
	 * record, so calling it a second time for the same receive is not a free re-read.
	 */
	public static I_M_HU_LUTU_Configuration newDefaultCopy(
			@NonNull final I_M_HU_LUTU_Configuration template,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfigurationNew = InterfaceWrapperHelper.copy()
				.setFrom(template)
				.copyToNew(I_M_HU_LUTU_Configuration.class);

		adjustToDefaults(lutuConfigurationNew, receiptSchedule);

		// NOTE: don't save it
		return lutuConfigurationNew;
	}

	public static void adjustToDefaults(
			@NonNull final I_M_HU_LUTU_Configuration lutuConfig,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

		if (lutuConfigurationFactory.isNoLU(lutuConfig))
		{
			//
			// Adjust TU
			lutuConfig.setIsInfiniteQtyTU(false);
			lutuConfig.setQtyTU(BigDecimal.ONE);
		}
		else
		{
			//
			// Adjust LU
			lutuConfig.setIsInfiniteQtyLU(false);
			lutuConfig.setQtyLU(BigDecimal.ONE);

			//
			// Adjust TU
			// * if the standard QtyTU is less than how much is available to be received => enforce the available Qty
			// * else always take the standard QtyTU
			// see https://github.com/metasfresh/metasfresh-webui/issues/228
			{
				final BigDecimal qtyToMoveTU = huReceiptScheduleBL.getQtyToMoveTU(receiptSchedule);

				if (qtyToMoveTU.signum() > 0 && qtyToMoveTU.compareTo(lutuConfig.getQtyTU()) < 0)
				{
					lutuConfig.setQtyTU(qtyToMoveTU);
				}
			}

			// Adjust CU if TU can hold an infinite qty, but the material receipt is of course finite, so we need to adjust the LUTU Configuration.
			// Otherwise, receiving using the default configuration will not work.
			final BigDecimal qtyTU = lutuConfig.getQtyTU();
			if (lutuConfig.isInfiniteQtyCU() && qtyTU.signum() > 0)
			{
				lutuConfig.setIsInfiniteQtyCU(false);

				final BigDecimal qtyToMoveCU = receiptSchedule.getQtyToMove().divide(qtyTU, RoundingMode.UP);

				lutuConfig.setQtyCUsPerTU(qtyToMoveCU);
			}
		}
	}
}
