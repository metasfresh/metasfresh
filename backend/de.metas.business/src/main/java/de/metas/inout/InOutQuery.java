/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.inout;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class InOutQuery
{
	@Nullable Instant movementDateFrom;
	@Nullable Instant movementDateTo;
	@Nullable DocStatus docStatus;
	@Nullable @Singular ImmutableSet<DocStatus> excludeDocStatuses;

	@NonNull @Singular ImmutableSet<OrderId> orderIds;

	@Builder.Default QueryLimit limit = QueryLimit.ONE_HUNDRED;
}
