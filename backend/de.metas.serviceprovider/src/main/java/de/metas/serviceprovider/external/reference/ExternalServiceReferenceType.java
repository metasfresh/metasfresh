/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.external.reference;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.model.I_S_Milestone;
import de.metas.serviceprovider.model.I_S_TimeBooking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_IssueID;
import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_MilestoneId;
import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_TimeBookingID;

@AllArgsConstructor
@Getter
public enum ExternalServiceReferenceType implements IExternalReferenceType
{
	ISSUE_ID(TYPE_IssueID, I_S_Issue.Table_Name),
	TIME_BOOKING_ID(TYPE_TimeBookingID, I_S_TimeBooking.Table_Name),
	MILESTONE_ID(TYPE_MilestoneId, I_S_Milestone.Table_Name);

	private final String code;
	private final String tableName;

	public static ExternalServiceReferenceType cast(final IExternalReferenceType externalReferenceType)
	{
		return (ExternalServiceReferenceType)externalReferenceType;
	}

	private static final ImmutableMap<String, ExternalServiceReferenceType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ExternalServiceReferenceType::getCode);

	public static ExternalServiceReferenceType ofCode(@NonNull final String code)
	{
		final ExternalServiceReferenceType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ExternalServiceReferenceType.class + " found for code: " + code);
		}
		return type;
	}

}
