/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.issue.interceptor;

import de.metas.quantity.Quantity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.timebooking.Effort;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class HandleParentChangedRequest
{
	@Nullable
	Instant latestActivity;

	@Nullable
	IssueId oldParentId;

	@Nullable
	IssueId currentParentId;

	@Nullable
	Effort oldAggregatedEffort;

	@Nullable
	Effort currentAggregatedEffort;

	@Nullable
	Quantity oldInvoicableEffort;

	@Nullable
	Quantity currentInvoicableEffort;
	
}
