package de.metas.purchasecandidate;

import com.google.common.annotations.VisibleForTesting;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

/**
 * Purchase candidates with the same reference belong to the same document line (e.g. sales order line).
 */
@Value
public class DemandGroupReference
{
	@VisibleForTesting
	public static final String REFERENCE_NOT_YET_SET = "reference-not-yet-set";

	public static final DemandGroupReference EMPTY = new DemandGroupReference(REFERENCE_NOT_YET_SET);

	public static DemandGroupReference ofReference(@NonNull final String demandReference)
	{
		return new DemandGroupReference(demandReference);
	}

	String demandReference;

	private DemandGroupReference(@NonNull final String demandReference)
	{
		this.demandReference = Check.assumeNotEmpty(demandReference, "Given demandReference may not be empty");
	}

	public boolean isNew()
	{
		return REFERENCE_NOT_YET_SET.equals(demandReference);
	}
}
