/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;

/**
 * Test class for {@link IShipmentScheduleBL#updateASIFromProjectId(I_M_ShipmentSchedule)}.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ShipmentScheduleBL_UpdateASIFromProjectIdTest
{
	private static final String PROJECT_VALUE = "PROJECT-001";
	private static final String PROJECT_VALUE_2 = "PROJECT-002";
	private static final AttributeCode OTHER_ATTR_CODE = AttributeCode.ofString("TestSize");
	private static final String OTHER_ATTR_VALUE = "SIZE-21";

	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private IShipmentScheduleBL shipmentScheduleBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ProjectRepository.newInstanceForUnitTesting());
		shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		createProjectValueAttr(true);
	}

	private void createProjectValueAttr(final boolean storageRelevant)
	{
		final I_M_Attribute attribute = newInstance(I_M_Attribute.class);
		attribute.setValue(AttributeConstants.ATTR_Project.getCode());
		attribute.setAttributeValueType(ATTRIBUTEVALUETYPE_StringMax40);
		attribute.setIsStorageRelevant(storageRelevant);
		saveRecord(attribute);
	}

	private void createOtherAttr()
	{
		final I_M_Attribute attribute = newInstance(I_M_Attribute.class);
		attribute.setValue(OTHER_ATTR_CODE.getCode());
		attribute.setAttributeValueType(ATTRIBUTEVALUETYPE_StringMax40);
		attribute.setIsStorageRelevant(true);
		saveRecord(attribute);
	}

	private I_M_Product createProduct(final String productValue)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(productValue);
		product.setName(productValue);
		saveRecord(product);
		return product;
	}

	private I_C_Project createProject(final String projectValue)
	{
		final I_C_Project project = newInstance(I_C_Project.class);
		project.setValue(projectValue);
		project.setName(projectValue);
		saveRecord(project);
		return project;
	}

	private I_M_AttributeSetInstance createASI()
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asi);
		return asi;
	}

	private I_M_ShipmentSchedule createShipmentSchedule(
			final I_M_Product product,
			@Nullable final I_M_AttributeSetInstance asi,
			@Nullable final ProjectId projectId,
			final boolean processed)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Product_ID(product.getM_Product_ID());
		if (asi != null)
		{
			schedule.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		}
		schedule.setC_Project_ID(ProjectId.toRepoId(projectId));
		schedule.setProcessed(processed);
		return schedule;
	}

	@Nullable
	private String getProjectValueFromASI(final AttributeSetInstanceId asiId)
	{
		if (asiId.isNone())
		{
			return null;
		}
		return attributeSetInstanceBL.getImmutableAttributeSetById(asiId)
				.getValueAsStringIfExists(AttributeConstants.ATTR_Project)
				.orElse(null);
	}

	@Test
	void nonProcessed_AsiExists_setsAsiAttribute()
	{
		// Given
		final I_M_Product product = createProduct("Product-1");
		final I_C_Project project = createProject(PROJECT_VALUE);
		final I_M_AttributeSetInstance asi = createASI();
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(product, asi, ProjectId.ofRepoId(project.getC_Project_ID()), false);

		// When
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// Then
		assertThat(getProjectValueFromASI(AttributeSetInstanceId.ofRepoId(schedule.getM_AttributeSetInstance_ID())))
				.isEqualTo(PROJECT_VALUE);
	}

	@Test
	void nonProcessed_NoAsi_createsAsiAndSetsAttribute()
	{
		// Given
		final I_M_Product product = createProduct("Product-2");
		final I_C_Project project = createProject(PROJECT_VALUE);
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(product, null, ProjectId.ofRepoId(project.getC_Project_ID()), false);
		assertThat(schedule.getM_AttributeSetInstance_ID()).isLessThanOrEqualTo(0);

		// When
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// Then
		final AttributeSetInstanceId newAsiId = AttributeSetInstanceId.ofRepoIdOrNone(schedule.getM_AttributeSetInstance_ID());
		assertThat(newAsiId.isNone()).isFalse();
		assertThat(getProjectValueFromASI(newAsiId)).isEqualTo(PROJECT_VALUE);
	}

	@Test
	void processed_doesNotChangeAsi()
	{
		// Given
		final I_M_Product product = createProduct("Product-3");
		final I_C_Project project = createProject(PROJECT_VALUE);
		final I_M_AttributeSetInstance asi = createASI();
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(product, asi, ProjectId.ofRepoId(project.getC_Project_ID()), true);
		final int initialAsiId = schedule.getM_AttributeSetInstance_ID();

		// When
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// Then
		assertThat(schedule.getM_AttributeSetInstance_ID()).isEqualTo(initialAsiId);
		assertThat(getProjectValueFromASI(AttributeSetInstanceId.ofRepoId(initialAsiId))).isNull();
	}

	@Test
	void projectNull_AsiHasProject_clearsAsiAttribute()
	{
		// Given: seed an ASI with the project attribute already set
		final I_M_Product product = createProduct("Product-4");
		final I_C_Project project = createProject(PROJECT_VALUE);
		final I_M_AttributeSetInstance asi = createASI();
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(product, asi, ProjectId.ofRepoId(project.getC_Project_ID()), false);
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// sanity: the project value did land on the schedule's (cloned) ASI before we clear it
		final AttributeSetInstanceId asiIdWithProject = AttributeSetInstanceId.ofRepoId(schedule.getM_AttributeSetInstance_ID());
		assertThat(getProjectValueFromASI(asiIdWithProject)).isEqualTo(PROJECT_VALUE);

		// When: clear the project on the schedule and fire again
		schedule.setC_Project_ID(0);
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// Then: the M_AttributeInstance row is still present on the (again cloned) ASI, with a null value
		final AttributeSetInstanceId updatedAsiId = AttributeSetInstanceId.ofRepoId(schedule.getM_AttributeSetInstance_ID());
		final boolean hasProjectValueInstance = attributeSetInstanceBL.getImmutableAttributeSetById(updatedAsiId)
				.hasAttribute(AttributeConstants.ATTR_Project);
		assertThat(hasProjectValueInstance).as("ProjectValue instance row should still exist after clearing").isTrue();
		assertThat(getProjectValueFromASI(updatedAsiId)).isNull();
	}

	@Test
	void projectNull_NoAsi_doesNotCreateAsi()
	{
		// Given
		final I_M_Product product = createProduct("Product-5");
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(product, null, null, false);

		// When
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// Then
		assertThat(AttributeSetInstanceId.ofRepoIdOrNone(schedule.getM_AttributeSetInstance_ID()).isNone()).isTrue();
	}

	@Test
	void projectChanged_updatesAsiAttribute()
	{
		// Given
		final I_M_Product product = createProduct("Product-6");
		final I_C_Project project1 = createProject(PROJECT_VALUE);
		final I_C_Project project2 = createProject(PROJECT_VALUE_2);
		final I_M_AttributeSetInstance asi = createASI();
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(product, asi, ProjectId.ofRepoId(project1.getC_Project_ID()), false);
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// When: switch to project 2
		schedule.setC_Project_ID(project2.getC_Project_ID());
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// Then
		assertThat(getProjectValueFromASI(AttributeSetInstanceId.ofRepoId(schedule.getM_AttributeSetInstance_ID())))
				.isEqualTo(PROJECT_VALUE_2);
	}

	@Test
	void attributeNotStorageRelevant_doesNotTouchAsi()
	{
		// Given: rebuild the attribute as non-storage-relevant
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ProjectRepository.newInstanceForUnitTesting());
		shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		createProjectValueAttr(false);

		final I_M_Product product = createProduct("Product-7");
		final I_C_Project project = createProject(PROJECT_VALUE);
		final I_M_AttributeSetInstance asi = createASI();
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(product, asi, ProjectId.ofRepoId(project.getC_Project_ID()), false);
		final int initialAsiId = schedule.getM_AttributeSetInstance_ID();

		// When
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// Then: ASI untouched
		assertThat(schedule.getM_AttributeSetInstance_ID()).isEqualTo(initialAsiId);
		assertThat(getProjectValueFromASI(AttributeSetInstanceId.ofRepoId(initialAsiId))).isNull();
	}

	@Test
	void projectNeverSet_asiHasOtherAttributeButNoProjectValue_doesNotCloneOrInjectProjectAttribute()
	{
		// Given: an ASI carrying a real, non-Project attribute value, but no ProjectValue instance,
		// and a schedule with no project at all
		final I_M_Product product = createProduct("Product-8");
		createOtherAttr();
		final I_M_AttributeSetInstance asi = createASI();
		final AttributeSetInstanceId seededAsiId = attributeSetInstanceBL.setAttributeInstanceValue(
				AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID()),
				OTHER_ATTR_CODE,
				OTHER_ATTR_VALUE);

		final I_M_ShipmentSchedule schedule = createShipmentSchedule(product, null, null, false);
		schedule.setM_AttributeSetInstance_ID(seededAsiId.getRepoId());

		// sanity: the seeded ASI does not yet carry a ProjectValue instance
		assertThat(attributeSetInstanceBL.getImmutableAttributeSetById(seededAsiId).hasAttribute(AttributeConstants.ATTR_Project))
				.as("Sanity: seeded ASI must not already carry a ProjectValue instance")
				.isFalse();

		// When
		shipmentScheduleBL.updateASIFromProjectId(schedule);

		// Then: no clone happened - the guard's early return was taken
		assertThat(schedule.getM_AttributeSetInstance_ID())
				.as("ASI must not be cloned when there is no project and no existing ProjectValue instance")
				.isEqualTo(seededAsiId.getRepoId());

		// And: still no ProjectValue instance was materialized on the ASI
		assertThat(attributeSetInstanceBL.getImmutableAttributeSetById(AttributeSetInstanceId.ofRepoId(schedule.getM_AttributeSetInstance_ID())).hasAttribute(AttributeConstants.ATTR_Project))
				.as("No ProjectValue instance should be materialized when the schedule has no project")
				.isFalse();
	}
}
