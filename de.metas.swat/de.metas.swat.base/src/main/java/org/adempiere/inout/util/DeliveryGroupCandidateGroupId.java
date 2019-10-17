package org.adempiere.inout.util;

import org.adempiere.util.lang.impl.TableRecordReference;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
public final class DeliveryGroupCandidateGroupId
{
	public static DeliveryGroupCandidateGroupId of(@NonNull final TableRecordReference recordRef)
	{
		return new DeliveryGroupCandidateGroupId(recordRef.getTableName() + "=" + recordRef.getRecord_ID());
	}

	private final String id;

	private DeliveryGroupCandidateGroupId(@NonNull final String id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return id;
	}
}
