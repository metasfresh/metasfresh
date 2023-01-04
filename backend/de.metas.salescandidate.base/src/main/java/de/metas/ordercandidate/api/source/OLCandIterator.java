/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ordercandidate.api.source;

import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandOrderDefaults;
import de.metas.ordercandidate.model.I_C_OLCand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Iterator;

@Value
public class OLCandIterator implements Iterator<OLCand>
{
	@NonNull
	@Getter(AccessLevel.NONE)
	Iterator<I_C_OLCand> olCandIterator;

	@NonNull
	@Getter(AccessLevel.NONE)
	IOLCandBL olCandBL;

	@NonNull
	@Getter(AccessLevel.NONE)
	OLCandOrderDefaults orderDefaults;

	public OLCandIterator(
			final @NonNull Iterator<I_C_OLCand> olCandIterator,
			final @NonNull IOLCandBL olCandBL,
			final @NonNull OLCandOrderDefaults orderDefaults)
	{
		this.olCandIterator = olCandIterator;
		this.olCandBL = olCandBL;
		this.orderDefaults = orderDefaults;
	}

	@Override
	public boolean hasNext()
	{
		return olCandIterator.hasNext();
	}

	@Override
	public OLCand next()
	{
		return mapToOLCand(olCandIterator.next());
	}

	@NonNull
	private OLCand mapToOLCand(@NonNull final I_C_OLCand olCandRecord)
	{
		return olCandBL.toOLCand(olCandRecord, orderDefaults);
	}
}
