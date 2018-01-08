package de.metas.handlingunits.material.interceptor;

import java.util.List;

import org.compiere.model.I_M_Transaction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.material.event.MaterialEvent;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public abstract class M_Transaction_EventCreator
{

	public final List<MaterialEvent> createEventsForTransaction(
			@NonNull final I_M_Transaction transaction, final boolean deleted)
	{

		final Builder<MaterialEvent> result = ImmutableList.builder();

		if (transaction.getM_InOutLine_ID() > 0)
		{
			result.addAll(createEventsForInOutLine(transaction, deleted));
		}
		else if (transaction.getPP_Cost_Collector_ID() > 0)
		{
			result.addAll(createEventsForCostCollector(transaction, deleted));
		}
		else if (transaction.getM_MovementLine_ID() > 0)
		{
			result.addAll(createEventsForMovementLine(transaction, deleted));
		}

		return result.build();
	}

	public abstract List<MaterialEvent> createEventsForInOutLine(I_M_Transaction transaction, boolean deleted);

	public abstract List<MaterialEvent> createEventsForCostCollector(I_M_Transaction transaction, boolean deleted);

	public abstract List<MaterialEvent> createEventsForMovementLine(I_M_Transaction transaction, boolean deleted);
}
