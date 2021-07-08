package org.eevolution.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.X_PP_Order_Node;

import java.util.Arrays;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

public enum PPOrderRoutingActivityStatus
{
	NOT_STARTED(X_PP_Order_Node.DOCSTATUS_Drafted), //
	IN_PROGRESS(X_PP_Order_Node.DOCSTATUS_InProgress), //
	COMPLETED(X_PP_Order_Node.DOCSTATUS_Completed), //
	CLOSED(X_PP_Order_Node.DOCSTATUS_Closed), //
	VOIDED(X_PP_Order_Node.DOCSTATUS_Voided), //
	;

	@Getter
	private final String docStatus;

	PPOrderRoutingActivityStatus(final String docStatus)
	{
		this.docStatus = docStatus;
	}

	public static PPOrderRoutingActivityStatus ofDocStatus(@NonNull final String docStatus)
	{
		final PPOrderRoutingActivityStatus type = typesByDocStatus.get(docStatus);
		if (type == null)
		{
			throw new AdempiereException("No " + PPOrderRoutingActivityStatus.class + " found for DocStatus: " + docStatus);
		}
		return type;
	}

	public void assertCanChangeTo(@NonNull final PPOrderRoutingActivityStatus nextStatus)
	{
		if (!canChangeTo(nextStatus))
		{
			throw new AdempiereException("Invalid transition from " + this + " to " + nextStatus);
		}
	}

	private boolean canChangeTo(@NonNull final PPOrderRoutingActivityStatus nextStatus)
	{
		return allowedTransitions.get(this).contains(nextStatus);
	}

	public boolean canReport()
	{
		return this == NOT_STARTED || this == IN_PROGRESS;
	}

	private static final ImmutableMap<String, PPOrderRoutingActivityStatus> typesByDocStatus = Maps.uniqueIndex(Arrays.asList(values()), PPOrderRoutingActivityStatus::getDocStatus);

	private static final ImmutableSetMultimap<PPOrderRoutingActivityStatus, PPOrderRoutingActivityStatus> allowedTransitions = ImmutableSetMultimap.<PPOrderRoutingActivityStatus, PPOrderRoutingActivityStatus> builder()
			.put(NOT_STARTED, IN_PROGRESS)
			.put(NOT_STARTED, COMPLETED)
			.put(NOT_STARTED, VOIDED)
			.put(IN_PROGRESS, NOT_STARTED)
			.put(IN_PROGRESS, COMPLETED)
			.put(IN_PROGRESS, VOIDED)
			// .put(COMPLETED, NOT_STARTED)
			.put(COMPLETED, CLOSED)
			// .put(COMPLETED, VOIDED)
			.put(CLOSED, IN_PROGRESS)
			.build();
}
