/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class HUStatusBL implements IHUStatusBL
{
	private final static List<String> HU_STATUSES_THAT_COUNT_FOR_QTY_ON_HAND = ImmutableList.of(
			X_M_HU.HUSTATUS_Active,
			X_M_HU.HUSTATUS_Picked,
			X_M_HU.HUSTATUS_Issued);

	private final static List<String> HUSTATUSES_MoveToEmptiesWarehouse = ImmutableList.of(
			X_M_HU.HUSTATUS_Destroyed,
			X_M_HU.HUSTATUS_Active);

	private final static Multimap<String, String> ALLOWED_STATUS_TRANSITIONS = ImmutableMultimap.<String, String> builder()

			.put(X_M_HU.HUSTATUS_Planning, X_M_HU.HUSTATUS_Active)

			// e.g. if a purchase order is reactivated and there are already planning HUs that were supposed to be used on the material receipt
			.put(X_M_HU.HUSTATUS_Planning, X_M_HU.HUSTATUS_Destroyed)

			// e.g. if you put an already picked HU onto a new pallet, the the pallet's status needs to go directly from planned to picked
			.put(X_M_HU.HUSTATUS_Planning, X_M_HU.HUSTATUS_Picked)

			.put(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Picked)
			.put(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Issued)
			.put(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Destroyed)
			// active => shipped state-transition is used in vendor returns
			.put(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Shipped)

			.put(X_M_HU.HUSTATUS_Picked, X_M_HU.HUSTATUS_Active)
			.put(X_M_HU.HUSTATUS_Picked, X_M_HU.HUSTATUS_Shipped)
			// picked => destroyed is used if you distribute one already-picked HU onto other HUs, and the source-HU is then destroyed
			.put(X_M_HU.HUSTATUS_Picked, X_M_HU.HUSTATUS_Destroyed)

			.put(X_M_HU.HUSTATUS_Issued, X_M_HU.HUSTATUS_Active)
			.put(X_M_HU.HUSTATUS_Issued, X_M_HU.HUSTATUS_Destroyed)

			.put(X_M_HU.HUSTATUS_Destroyed, X_M_HU.HUSTATUS_Active)
			.put(X_M_HU.HUSTATUS_Destroyed, X_M_HU.HUSTATUS_Issued)

			// shipped => active is used e.g. when reverse-correcting a vendor return https://github.com/metasfresh/metasfresh/issues/2755
			.put(X_M_HU.HUSTATUS_Shipped, X_M_HU.HUSTATUS_Active)

			// shipped => picked is used if a shipment with picked HUs is reversed
			.put(X_M_HU.HUSTATUS_Shipped, X_M_HU.HUSTATUS_Picked)

			.build();

	private final static List<String> ALLOWED_STATUSES_FOR_LOCATOR_CHANGE = ImmutableList.of(
			X_M_HU.HUSTATUS_Planning,
			X_M_HU.HUSTATUS_Picked, // a HU can be commissioned/picked anywhere, and it still needs to be moved around afterwards
			X_M_HU.HUSTATUS_Shipped, // when restoring a snapshot HU for a customer return, the locator is set on a HU with status E..
			X_M_HU.HUSTATUS_Active);

	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);


	@Override
	public boolean isQtyOnHand(final String huStatus)
	{
		return HU_STATUSES_THAT_COUNT_FOR_QTY_ON_HAND.contains(huStatus);
	}

	@Override
	public List<String> getQtyOnHandStatuses()
	{
		return HU_STATUSES_THAT_COUNT_FOR_QTY_ON_HAND;
	}

	@Override
	public boolean isMovePackagingToEmptiesWarehouse(final String huStatus)
	{
		return HUSTATUSES_MoveToEmptiesWarehouse.contains(huStatus);
	}

	@Override
	public void assertStatusChangeIsAllowed(final I_M_HU huRecord, final String oldHuStatus, final String newHuStatus)
	{
		if (isStatusTransitionAllowed(oldHuStatus, newHuStatus))
		{
			return;
		}

		// no need to add an user friendly AD_Message. If this happens, we have a bug to fix.
		throw new HUException(StringUtils.formatMessage("Illegal M_HU.HUStatus change from {} to {}; hu={}", oldHuStatus, newHuStatus, huRecord));
	}

	@VisibleForTesting
	boolean isStatusTransitionAllowed(final String oldHuStatus, final String newHuStatus)
	{
		if (oldHuStatus == null)
		{
			return true;
		}
		if (newHuStatus == null)
		{
			return false; // note that M_HU.HuStatus is mandatory anyways
		}

		final Collection<String> allowedTargetStates = ALLOWED_STATUS_TRANSITIONS.get(oldHuStatus);
		Check.errorIf(allowedTargetStates == null, "Unexpected/Illegal value of oldHuStatus={}", oldHuStatus);

		return allowedTargetStates.contains(newHuStatus);
	}

	@Override
	public void assertLocatorChangeIsAllowed(
			@NonNull final I_M_HU huRecord,
			@NonNull final String huStatus)
	{
		if (ALLOWED_STATUSES_FOR_LOCATOR_CHANGE.contains(huStatus))
		{
			return;
		}
		throw new HUException(StringUtils.formatMessage("A HU's locator cannot be changed if the M_HU.HUStatus is {}; hu={}", huStatus, huRecord));
	}

	@Override
	public boolean isPhysicalHU(@Nullable final I_M_HU huRecord)
	{
		if (huRecord == null)
		{
			return false;
		}

		final String huStatus = huRecord.getHUStatus();
		if (Check.isBlank(huStatus))
		{
			return false; // can be the case with a new/unsaved HU
		}

		if (X_M_HU.HUSTATUS_Destroyed.equals(huStatus))
		{
			return false;
		}

		if (X_M_HU.HUSTATUS_Planning.equals(huStatus))
		{
			return false;
		}

		if (X_M_HU.HUSTATUS_Shipped.equals(huStatus))
		{
			return false;
		}

		// we consider the rest of the statuses to be physical
		// (active, picked and issued)
		return true;
	}

	@Override
	public boolean isStatusPlanned(@Nullable final I_M_HU huRecord)
	{
		if (huRecord == null)
		{
			return false;
		}
		return X_M_HU.HUSTATUS_Planning.equals(huRecord.getHUStatus());
	}

	@Override
	public boolean isStatusActiveOrIssued(@Nullable final I_M_HU huRecord)
	{
		if (huRecord == null)
		{
			return false;
		}

		final String huStatus = huRecord.getHUStatus();
		return X_M_HU.HUSTATUS_Active.equals(huStatus)
				|| X_M_HU.HUSTATUS_Issued.equals(huStatus);
	}

	@Override
	public boolean isStatusActive(@Nullable final I_M_HU huRecord)
	{
		if (huRecord == null)
		{
			return false;
		}
		return X_M_HU.HUSTATUS_Active.equals(huRecord.getHUStatus());
	}

	@Override
	public boolean isStatusIssued(@Nullable final I_M_HU huRecord)
	{
		if (huRecord == null)
		{
			return false;
		}
		return X_M_HU.HUSTATUS_Issued.equals(huRecord.getHUStatus());
	}

	@Override
	public boolean isStatusIssued(@NonNull final HuId huId){
		return isStatusIssued(handlingUnitsDAO.getById(huId));
	}

	@Override
	public boolean isStatusDestroyed(@Nullable final I_M_HU huRecord)
	{
		if (huRecord == null)
		{
			return false;
		}
		return X_M_HU.HUSTATUS_Destroyed.equals(huRecord.getHUStatus());
	}

	@Override
	public boolean isStatusShipped(@Nullable final I_M_HU huRecord)
	{
		if (huRecord == null)
		{
			return false;
		}
		return X_M_HU.HUSTATUS_Shipped.equals(huRecord.getHUStatus());
	}

	@Override
	public void setHUStatus(final IHUContext huContext,
			@NonNull final I_M_HU hu,
			@NonNull final String huStatus)
	{
		final boolean forceFetchPackingMaterial = false; // rely on HU Status configuration for detection when fetching packing material
		setHUStatus(huContext, hu, huStatus, forceFetchPackingMaterial);
	}

	@Override
	public void setHUStatus(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU hu,
			@NonNull final String huStatus,
			final boolean forceFetchPackingMaterial)
	{
		// keep this so we can compare it with the new one and make sure the moving to/from empties is done only when needed
		final String initialHUStatus = hu.getHUStatus();

		if (Objects.equals(huStatus, initialHUStatus))
		{
			// do nothing
			return;
		}

		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		final boolean isExchangeGebindelagerWhenEmpty = huStatusBL.isMovePackagingToEmptiesWarehouse(huStatus);

		//
		// 08157: If forced packing material fetching is enabled, then make sure to pull packing material from Gebinde warehouse (i.e when bringing a blank LU)
		if (forceFetchPackingMaterial)
		{
			if (isPhysicalHU(hu))
			{
				// collect the "destroyed" HUs in case they were already physical (active)
				huContext
						.getHUPackingMaterialsCollector()
						.releasePackingMaterialForHURecursively(hu, null);
			}
			else
			{
				// remove the HUs from the destroying collector (decrement qty) just in case of new HU
				huContext
						.getHUPackingMaterialsCollector()
						.requirePackingMaterialForHURecursively(hu);
			}
		}
		else if (!isExchangeGebindelagerWhenEmpty)
		{
			// do nothing
		}
		else
		{
			// remove the HUs from the collector (decrement qty) just in case of new HU (no initial status)
			if (initialHUStatus == null)
			{
				// TODO i can't see why we make this invocation. it results in a material movement from empties warehouse.
				// when to we need that?
				// huContext
				// .getHUPackingMaterialsCollector()
				// .removeHURecursively(hu);
			}
			// only collect the destroyed HUs in case they were already physical (active)
			else if (isPhysicalHU(hu))
			{
				huContext
						.getHUPackingMaterialsCollector()
						.releasePackingMaterialForHURecursively(hu, null);
			}
			else
			{
				// do nothing

				// TODO: evaluate the logic from here and the logic of the method at all
				// This could be the case when the HUStatus is changed from Planning to Active.
				// Theoretically we shall "fetch" the packing materials from gebinde lager in this case,
				// but by coincidence we don't want to do this because mainly this case happens when we are receving new HUs
				// from Wareneingang POS (and generate the material receipt)
				// And there we don't want to do this because those packing materials are fetched from Vendor and not from our lager.
			}
		}

		hu.setHUStatus(huStatus);

		// Do not save the HU because, at this point, we don't know what's to be done with it in future
	}

	@Override
	public void setHUStatusActive(final Collection<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return;
		}

		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		huTrxBL.process(huContext -> {
			for (final I_M_HU hu : hus)
			{
				final boolean isPhysicalHU = isPhysicalHU(hu);
				if (isPhysicalHU)
				{
					// in case of a physical HU, we don't need to activate and collect it for the empties movements, because that was already done.
					// concrete case: in both empfang and verteilung the boxes were coming from gebindelager to our current warehouse
					// ... but when you get to verteilung the boxes are already there
					return;
				}

				setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);
				InterfaceWrapperHelper.save(hu, ITrx.TRXNAME_ThreadInherited);

				//
				// Ask the API to get the packing materials needed to the HU which we just activate it
				// TODO: i think we can remove this part because it's done automatically ?! (NOTE: this one was copied from swing UI, de.metas.handlingunits.client.terminal.pporder.receipt.view.HUPPOrderReceiptHUEditorPanel.onDialogOkBeforeSave(ITerminalDialog))
				huContext.getHUPackingMaterialsCollector().requirePackingMaterialForHURecursively(hu);
			}
		});
	}
}
