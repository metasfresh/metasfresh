/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2026 metas GmbH
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
package de.metas.material.planning.pporder.impl;

import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_S_Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PPRoutingRepositoryTest
{
	private IPPRoutingRepository ppRoutingRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		ppRoutingRepository = Services.get(IPPRoutingRepository.class);
	}

	private I_S_Resource createResource()
	{
		final I_S_Resource resource = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
		resource.setName("Resource");
		resource.setValue("R1");
		InterfaceWrapperHelper.save(resource);
		return resource;
	}

	private I_AD_Workflow createWorkflow()
	{
		final I_AD_Workflow wf = InterfaceWrapperHelper.newInstance(I_AD_Workflow.class);
		wf.setName("WF1");
		wf.setValue("WF1");
		wf.setWorkflowType("M"); // Manufacturing
		wf.setDurationUnit("h");
		wf.setQtyBatchSize(java.math.BigDecimal.ONE);
		InterfaceWrapperHelper.save(wf);
		return wf;
	}

	private I_AD_WF_Node createNode(final I_AD_Workflow wf, final I_S_Resource resource, final String name)
	{
		final I_AD_WF_Node node = InterfaceWrapperHelper.newInstance(I_AD_WF_Node.class);
		node.setAD_Workflow_ID(wf.getAD_Workflow_ID());
		node.setName(name);
		node.setValue(name);
		node.setS_Resource_ID(resource.getS_Resource_ID());
		InterfaceWrapperHelper.save(node);
		return node;
	}

	@Test
	public void firstActivityId_orphan_throws_clear_error()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();

		// Create 3 real nodes for the workflow.
		final I_AD_WF_Node node1 = createNode(wf, resource, "N1");
		createNode(wf, resource, "N2");
		createNode(wf, resource, "N3");

		// Note: AD_WF_Node interceptor's AFTER_NEW auto-sets the workflow's start node to the first
		// node it sees. We override that to point at a non-existing AD_WF_Node_ID to simulate the
		// orphan/deactivated-first-node scenario.
		final int orphanWfNodeId = node1.getAD_WF_Node_ID() + 99999;
		wf.setAD_WF_Node_ID(orphanWfNodeId);
		InterfaceWrapperHelper.save(wf);

		final PPRoutingId routingId = PPRoutingId.ofRepoId(wf.getAD_Workflow_ID());

		assertThatThrownBy(() -> ppRoutingRepository.getById(routingId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("PPRouting_FirstNodeInvalid");
	}

	@Test
	public void firstActivityId_deactivated_throws_clear_error()
	{
		// Reproduces the actual ic114 me03 30094 scenario: AD_WF_Node still exists in the DB but has
		// IsActive='N', so retrieveNodes filters it out of the loaded activity set, and the
		// firstActivityId pointer becomes orphan from the loader's perspective.
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();

		final I_AD_WF_Node node1 = createNode(wf, resource, "N1");
		createNode(wf, resource, "N2");
		createNode(wf, resource, "N3");

		// Keep node1 as the workflow's first node, then soft-delete it. PPRoutingRepositoryTest does
		// not register the AD_WF_Node interceptor that would normally block this, so the bad-data
		// shape is reproducible here for the loader's own defence.
		wf.setAD_WF_Node_ID(node1.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(wf);
		node1.setIsActive(false);
		InterfaceWrapperHelper.save(node1);

		final PPRoutingId routingId = PPRoutingId.ofRepoId(wf.getAD_Workflow_ID());

		assertThatThrownBy(() -> ppRoutingRepository.getById(routingId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("PPRouting_FirstNodeInvalid");
	}

	@Test
	public void firstActivityId_valid_loads_successfully()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();

		final I_AD_WF_Node node1 = createNode(wf, resource, "N1");
		createNode(wf, resource, "N2");

		wf.setAD_WF_Node_ID(node1.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(wf);

		final PPRoutingId routingId = PPRoutingId.ofRepoId(wf.getAD_Workflow_ID());

		assertThat(ppRoutingRepository.getById(routingId)).isNotNull();
	}
}
