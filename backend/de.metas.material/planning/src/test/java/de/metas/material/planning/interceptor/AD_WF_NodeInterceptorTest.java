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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AD_WF_NodeInterceptorTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new AD_WF_Node(), null);
	}

	private I_S_Resource createResource()
	{
		final I_S_Resource resource = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
		resource.setName("R1");
		resource.setValue("R1");
		InterfaceWrapperHelper.save(resource);
		return resource;
	}

	private I_AD_Workflow createWorkflow()
	{
		final I_AD_Workflow wf = InterfaceWrapperHelper.newInstance(I_AD_Workflow.class);
		wf.setName("WF1");
		wf.setValue("WF1");
		wf.setWorkflowType("M");
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

	// =========================================================
	// Rule 1: deactivate-first-node blocked
	// =========================================================

	@Test
	public void deactivate_firstNode_blocked()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();
		final I_AD_WF_Node firstNode = createNode(wf, resource, "first");
		// AFTER_NEW interceptor auto-sets first node — make sure of it.
		wf.setAD_WF_Node_ID(firstNode.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(wf);

		firstNode.setIsActive(false);
		assertThatThrownBy(() -> InterfaceWrapperHelper.save(firstNode))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("PPRouting_CannotDeactivateFirstNode");
	}

	@Test
	public void deactivate_nonFirstNode_allowed()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();
		final I_AD_WF_Node firstNode = createNode(wf, resource, "first");
		final I_AD_WF_Node secondNode = createNode(wf, resource, "second");
		wf.setAD_WF_Node_ID(firstNode.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(wf);

		secondNode.setIsActive(false);
		InterfaceWrapperHelper.save(secondNode);

		assertThat(InterfaceWrapperHelper.load(secondNode.getAD_WF_Node_ID(), I_AD_WF_Node.class).isActive()).isFalse();
	}

	// =========================================================
	// Rule 2: delete-first-node blocked
	// =========================================================

	@Test
	public void delete_firstNode_blocked()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();
		final I_AD_WF_Node firstNode = createNode(wf, resource, "first");
		wf.setAD_WF_Node_ID(firstNode.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(wf);

		assertThatThrownBy(() -> InterfaceWrapperHelper.delete(firstNode))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("PPRouting_CannotDeleteFirstNode");
	}

	@Test
	public void delete_nonFirstNode_allowed()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();
		final I_AD_WF_Node firstNode = createNode(wf, resource, "first");
		final I_AD_WF_Node secondNode = createNode(wf, resource, "second");
		wf.setAD_WF_Node_ID(firstNode.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(wf);

		InterfaceWrapperHelper.delete(secondNode);
	}

	// =========================================================
	// Rule 3: remove-resource-from-first-node blocked
	// =========================================================

	@Test
	public void removeResource_firstNode_blocked()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();
		final I_AD_WF_Node firstNode = createNode(wf, resource, "first");
		wf.setAD_WF_Node_ID(firstNode.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(wf);

		firstNode.setS_Resource_ID(0);
		assertThatThrownBy(() -> InterfaceWrapperHelper.save(firstNode))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("PPRouting_CannotRemoveResourceFromFirstNode");
	}

	@Test
	public void removeResource_nonFirstNode_allowed()
	{
		final I_S_Resource resource = createResource();
		final I_AD_Workflow wf = createWorkflow();
		final I_AD_WF_Node firstNode = createNode(wf, resource, "first");
		final I_AD_WF_Node secondNode = createNode(wf, resource, "second");
		wf.setAD_WF_Node_ID(firstNode.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(wf);

		secondNode.setS_Resource_ID(0);
		InterfaceWrapperHelper.save(secondNode);
	}
}
