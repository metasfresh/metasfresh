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
package de.metas.material.planning.interceptor;

import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_S_Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AD_WorkflowInterceptorTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new AD_Workflow(), null);
	}

	private I_S_Resource createResource()
	{
		final I_S_Resource resource = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
		resource.setName("R");
		resource.setValue("R");
		InterfaceWrapperHelper.save(resource);
		return resource;
	}

	private I_AD_Workflow createWorkflow(final String name)
	{
		final I_AD_Workflow wf = InterfaceWrapperHelper.newInstance(I_AD_Workflow.class);
		wf.setName(name);
		wf.setValue(name);
		wf.setWorkflowType("M");
		wf.setDurationUnit("h");
		wf.setQtyBatchSize(java.math.BigDecimal.ONE);
		InterfaceWrapperHelper.save(wf);
		return wf;
	}

	private I_AD_WF_Node createNode(
			final I_AD_Workflow wf,
			final I_S_Resource resource,
			final String name,
			final boolean active)
	{
		final I_AD_WF_Node node = InterfaceWrapperHelper.newInstance(I_AD_WF_Node.class);
		node.setAD_Workflow_ID(wf.getAD_Workflow_ID());
		node.setName(name);
		node.setValue(name);
		if (resource != null)
		{
			node.setS_Resource_ID(resource.getS_Resource_ID());
		}
		node.setIsActive(active);
		InterfaceWrapperHelper.save(node);
		return node;
	}

	@Test
	public void setFirstNode_to_inactive_blocked()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow("WF1");
		final I_AD_WF_Node inactiveNode = createNode(wf, resource, "inactive", false);

		wf.setAD_WF_Node_ID(inactiveNode.getAD_WF_Node_ID());

		assertThatThrownBy(() -> InterfaceWrapperHelper.save(wf))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("PPRouting_FirstNodeInvalid")
				.hasMessageContaining("inactive");
	}

	@Test
	public void setFirstNode_to_otherWorkflow_blocked()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf1 = createWorkflow("WF1");
		final I_AD_Workflow wf2 = createWorkflow("WF2");
		final I_AD_WF_Node nodeOfWf2 = createNode(wf2, resource, "n2", true);

		wf1.setAD_WF_Node_ID(nodeOfWf2.getAD_WF_Node_ID());

		assertThatThrownBy(() -> InterfaceWrapperHelper.save(wf1))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("PPRouting_FirstNodeInvalid")
				.hasMessageContaining("different workflow");
	}

	@Test
	public void setFirstNode_to_validNode_allowed()
	{
		// Happy path: target node is active, in the same workflow, and has S_Resource_ID.
		// Guards must NOT fire here — without this test, an accidental inversion of the
		// wfNodeId<=0 early-return or any of the three failure-condition checks would
		// silently block ALL first-node assignments and only surface in manual UAT.
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow("WF1");
		final I_AD_WF_Node validNode = createNode(wf, resource, "valid", true);

		wf.setAD_WF_Node_ID(validNode.getAD_WF_Node_ID());

		assertThatCode(() -> InterfaceWrapperHelper.save(wf)).doesNotThrowAnyException();
	}

	@Test
	public void setFirstNode_to_nullResource_blocked()
	{
		final I_AD_Workflow wf = createWorkflow("WF1");
		final I_AD_WF_Node nodeNoResource = createNode(wf, null, "noRes", true);

		wf.setAD_WF_Node_ID(nodeNoResource.getAD_WF_Node_ID());

		assertThatThrownBy(() -> InterfaceWrapperHelper.save(wf))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("PPRouting_FirstNodeInvalid")
				.hasMessageContaining("S_Resource_ID");
	}
}
