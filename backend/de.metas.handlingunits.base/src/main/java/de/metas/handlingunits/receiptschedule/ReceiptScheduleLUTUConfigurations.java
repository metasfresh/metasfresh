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

package de.metas.handlingunits.receiptschedule;

import javax.annotation.Nullable;
import de.metas.quantity.Quantity;
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
 * The receipt schedule's LU/TU packing configuration as the "receive HUs" actions read and default it - the ONE
 * definition, so both windows receive into exactly the same packing.
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


	/**
	 * Caps a DERIVED configuration at the share a single delivery planning may receive.
	 * <p>
	 * Lives here, beside {@link #adjustToDefaults}, because that method derives its LU/TU quantities from the
	 * SCHEDULE while a split copies {@code M_ReceiptSchedule_ID} onto every planning it creates - so any caller
	 * that receives ONE planning off a shared schedule has to cap, or the first row books the whole order line.
	 * Both the per-row receive processes and the batch receive need it, which is why it is not on either.
	 *
	 * @param plannedShare {@code null} for an UNPLANNED row, which is left alone: there the
	 *                     schedule-derived configuration is the correct one.
	 */
	public static void capToPlannedShare(
			@NonNull final I_M_HU_LUTU_Configuration lutuConfig,
			@Nullable final Quantity plannedShare)
	{
		if (plannedShare == null)
		{
			return;
		}

		final BigDecimal qtyCUsPerTU = lutuConfig.getQtyCUsPerTU();
		if (lutuConfig.isInfiniteQtyCU() || qtyCUsPerTU == null || qtyCUsPerTU.signum() <= 0)
		{
			// Nothing to divide by: leave the configuration as derived rather than guess a TU count.
			return;
		}

		// UP, not HALF_UP: a partial TU still has to be received, so 55 CUs at 10 per TU needs 6 TUs.
		// A share SMALLER than one TU cannot be expressed by a TU count - five CUs of a ten-per-TU packing is
		// still one TU, and capping the count alone would leave the producer free to fill that TU to ten. The
		// TU stays the configured one, so the receipt still carries the row's packing instruction; only its
		// content shrinks to the share.
		if (plannedShare.toBigDecimal().compareTo(qtyCUsPerTU) < 0)
		{
			lutuConfig.setIsInfiniteQtyTU(false);
			lutuConfig.setQtyTU(BigDecimal.ONE);
			lutuConfig.setIsInfiniteQtyCU(false);
			lutuConfig.setQtyCUsPerTU(plannedShare.toBigDecimal());
			return;
		}

		final BigDecimal cappedQtyTU = plannedShare.toBigDecimal().divide(qtyCUsPerTU, 0, RoundingMode.UP);
		if (cappedQtyTU.signum() <= 0 || cappedQtyTU.compareTo(lutuConfig.getQtyTU()) >= 0)
		{
			// The share is not the binding limit - the packing already fits inside it.
			return;
		}

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		lutuConfig.setIsInfiniteQtyTU(false);
		lutuConfig.setQtyTU(cappedQtyTU);
		if (!lutuConfigurationFactory.isNoLU(lutuConfig))
		{
			lutuConfig.setIsInfiniteQtyLU(false);
			lutuConfig.setQtyLU(BigDecimal.valueOf(
					lutuConfigurationFactory.calculateQtyLUForTotalQtyTUs(lutuConfig, cappedQtyTU)));
		}
	}
}
