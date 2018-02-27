package de.metas.handlingunits.impl;

import java.util.Collection;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.StringUtils;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.X_M_HU;
import lombok.NonNull;

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

			.put(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Picked)
			.put(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Issued)
			.put(X_M_HU.HUSTATUS_Active, X_M_HU.HUSTATUS_Destroyed)
			// active => shipped transition is used in vendor returns
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

			.build();

	private final static List<String> ALLOWED_STATUSES_FOR_LOCATOR_CHANGE = ImmutableList.of(
			X_M_HU.HUSTATUS_Planning,
			X_M_HU.HUSTATUS_Picked, // a HU can be commissioned/picked anywhere, and it still needs to be moved around afterwards
			X_M_HU.HUSTATUS_Shipped, // when restoring a snapshot HU for a customer return, the locator is set on a HU with status E..
			X_M_HU.HUSTATUS_Active);

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
	public void assertStatusChangeIsAllowed(final String oldHuStatus, final String newHuStatus)
	{
		if (isStatusTransitionAllowed(oldHuStatus, newHuStatus))
		{
			return;
		}

		// no need to add an user friendly AD_Message. If this happens, we have a bug to fix.
		throw new HUException(StringUtils.formatMessage("Illegal M_HU.HUStatus change from {} to {}", oldHuStatus, newHuStatus));
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
	public void assertLocatorChangeIsAllowed(@NonNull final String huStatus)
	{
		if (ALLOWED_STATUSES_FOR_LOCATOR_CHANGE.contains(huStatus))
		{
			return;
		}
		throw new HUException(StringUtils.formatMessage("A HU's locator cannot be changed if the M_HU.HUStatus is {}", huStatus));
	}
}
