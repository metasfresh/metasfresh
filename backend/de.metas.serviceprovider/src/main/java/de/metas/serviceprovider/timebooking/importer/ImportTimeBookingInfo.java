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

package de.metas.serviceprovider.timebooking.importer;

import de.metas.externalreference.ExternalId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.timebooking.TimeBookingId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class ImportTimeBookingInfo
{
	@Nullable
	TimeBookingId timeBookingId;

	@NonNull
	ExternalId<ExternalSystem> externalTimeBookingId;

	@NonNull
	UserId performingUserId;

	@NonNull
	IssueId issueId;

	long bookedSeconds;

	@NonNull
	Instant bookedDate;
}
