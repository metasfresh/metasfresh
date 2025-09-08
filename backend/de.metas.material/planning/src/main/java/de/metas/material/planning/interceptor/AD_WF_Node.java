/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.planning.interceptor;

import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRoutingActivityId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_WF_Node.class)
@Component
public class AD_WF_Node
{
	final IPPRoutingRepository ppRoutingRepo = Services.get(IPPRoutingRepository.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void setStartNodeInWorkflow(final I_AD_WF_Node node)
	{
		final PPRoutingId routingId = PPRoutingId.ofRepoIdOrNull(node.getAD_Workflow_ID());

		final PPRoutingActivityId ppRoutingActivityId = PPRoutingActivityId.ofRepoId(routingId, node.getAD_WF_Node_ID());

		if (ppRoutingRepo.nodesAlreadyExistInWorkflow(ppRoutingActivityId))
		{
			// nothing to do
			return;
		}

		// If this is the first node for the workflow, also set it as a starting node in the workflow itself
		// Issue https://github.com/metasfresh/metasfresh/issues/11292

		ppRoutingRepo.setFirstNodeToWorkflow(ppRoutingActivityId);
	}
}
