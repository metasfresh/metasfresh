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

package de.metas.order;

import de.metas.project.ProjectId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

/**
 * Represents a functional interface for performing necessary propagation of a project ID from a collection of purchase order line IDs.
 * <p>
 * Its main usage is to ensure a project that is set to a purchase order line is propagated to any corresponding records.
 */
@FunctionalInterface
public interface PurchaseOrderProjectListener
{
	void onCreated(@NonNull ProjectCreatedEvent event);

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	class ProjectCreatedEvent
	{
		@NonNull ProjectId projectId;
		@NonNull Set<OrderAndLineId> purchaseOrderLineIds;
		@NonNull UserId byUserId;
	}
}
