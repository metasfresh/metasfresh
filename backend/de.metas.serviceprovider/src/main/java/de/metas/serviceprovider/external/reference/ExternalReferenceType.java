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

import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.model.I_S_Milestone;
import de.metas.serviceprovider.model.I_S_TimeBooking;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;

import java.util.stream.Stream;

import static de.metas.serviceprovider.model.X_S_ExternalReference.TYPE_IssueID;
import static de.metas.serviceprovider.model.X_S_ExternalReference.TYPE_MilestoneId;
import static de.metas.serviceprovider.model.X_S_ExternalReference.TYPE_TimeBookingID;
import static de.metas.serviceprovider.model.X_S_ExternalReference.TYPE_UserID;

@AllArgsConstructor
@Getter
public enum ExternalReferenceType implements ReferenceListAwareEnum
{
	ISSUE_ID(TYPE_IssueID, I_S_Issue.Table_Name),
	USER_ID(TYPE_UserID, I_AD_User.Table_Name),
	TIME_BOOKING_ID(TYPE_TimeBookingID, I_S_TimeBooking.Table_Name),
	MILESTONE_ID(TYPE_MilestoneId, I_S_Milestone.Table_Name);

	private final String code;
	private final String tableName;

	@NonNull
	public static ExternalReferenceType ofCode(@NonNull final String code)
	{
		return Stream.of(values())
				.filter( type -> type.getCode().equals(code))
				.findFirst()
				.orElseThrow(() ->
						new AdempiereException("Unknown ExternalReferenceType: 'type'.")
						.appendParametersToMessage()
						.setParameter("type", code));
	}
}
