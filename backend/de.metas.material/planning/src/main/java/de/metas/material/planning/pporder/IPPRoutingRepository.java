package de.metas.material.planning.pporder;

import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * metasfresh-material-planning
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IPPRoutingRepository extends ISingletonService
{
	PPRouting getById(PPRoutingId routingId);

	@Nullable
	PPRoutingId getRoutingIdByProductId(ProductId productId);

	void changeRouting(PPRoutingChangeRequest changeRequest);

	boolean nodesAlreadyExistInWorkflow(@NonNull PPRoutingActivityId excludeActivityId);

	Optional<PPRoutingId> getDefaultRoutingIdByType(@NonNull PPRoutingType type);

	void setFirstNodeToWorkflow(@NonNull PPRoutingActivityId ppRoutingActivityId);

	SeqNo getActivityProductNextSeqNo(@NonNull PPRoutingActivityId activityId);
}
