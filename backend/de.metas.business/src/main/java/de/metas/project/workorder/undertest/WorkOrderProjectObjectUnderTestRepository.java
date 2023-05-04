/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.workorder.undertest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Project_WO_ObjectUnderTest;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class WorkOrderProjectObjectUnderTestRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public List<WOProjectObjectUnderTest> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_ObjectUnderTest.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_C_Project_ID, projectId.getRepoId())
				.create()
				.stream()
				.map(WorkOrderProjectObjectUnderTestRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<WOProjectObjectUnderTest> updateAll(@NonNull final Collection<WOProjectObjectUnderTest> objectUnderTestList)
	{
		final ImmutableList.Builder<WOProjectObjectUnderTest> savedObjectsUnderTest = ImmutableList.builder();

		for (final WOProjectObjectUnderTest objectUnderTest : objectUnderTestList)
		{
			savedObjectsUnderTest.add(update(objectUnderTest));
		}

		return savedObjectsUnderTest.build();
	}

	@NonNull
	public WOProjectObjectUnderTest update(@NonNull final WOProjectObjectUnderTest objectUnderTest)
	{
		final I_C_Project_WO_ObjectUnderTest record = InterfaceWrapperHelper.load(objectUnderTest.getObjectUnderTestId(), I_C_Project_WO_ObjectUnderTest.class);

		if (record == null)
		{
			throw new AdempiereException("Missing C_Project_WO_ObjectUnderTest for repoId:" + objectUnderTest.getObjectUnderTestId());
		}

		if (objectUnderTest.getExternalId() != null)
		{
			record.setExternalId(objectUnderTest.getExternalId().getValue());
		}

		record.setNumberOfObjectsUnderTest(objectUnderTest.getNumberOfObjectsUnderTest());
		record.setC_Project_ID(objectUnderTest.getProjectId().getRepoId());
		record.setWODeliveryNote(objectUnderTest.getWoDeliveryNote());
		record.setWOManufacturer(objectUnderTest.getWoManufacturer());
		record.setWOObjectType(objectUnderTest.getWoObjectType());
		record.setWOObjectName(objectUnderTest.getWoObjectName());
		record.setWOObjectWhereabouts(objectUnderTest.getWoObjectWhereabouts());
		record.setM_Product_ID(ProductId.toRepoId(objectUnderTest.getProductId()));
		record.setC_OrderLine_Provision_ID(OrderLineId.toRepoId(objectUnderTest.getOrderLineProvisionId()));
		record.setObjectDeliveredDate(TimeUtil.asTimestamp(objectUnderTest.getObjectDeliveredDate()));

		saveRecord(record);

		return ofRecord(record);
	}

	@NonNull
	public WOProjectObjectUnderTest create(@NonNull final CreateWOProjectObjectUnderTestRequest createWOProjectObjectUnderTestRequest)
	{
		final I_C_Project_WO_ObjectUnderTest record = InterfaceWrapperHelper.newInstance(I_C_Project_WO_ObjectUnderTest.class);

		record.setAD_Org_ID(createWOProjectObjectUnderTestRequest.getOrgId().getRepoId());
		record.setC_Project_ID(createWOProjectObjectUnderTestRequest.getProjectId().getRepoId());
		record.setExternalId(createWOProjectObjectUnderTestRequest.getExternalId().getValue());

		record.setNumberOfObjectsUnderTest(createWOProjectObjectUnderTestRequest.getNumberOfObjectsUnderTest());

		record.setWODeliveryNote(createWOProjectObjectUnderTestRequest.getWoDeliveryNote());
		record.setWOManufacturer(createWOProjectObjectUnderTestRequest.getWoManufacturer());
		record.setWOObjectType(createWOProjectObjectUnderTestRequest.getWoObjectType());
		record.setWOObjectName(createWOProjectObjectUnderTestRequest.getWoObjectName());
		record.setWOObjectWhereabouts(createWOProjectObjectUnderTestRequest.getWoObjectWhereabouts());

		saveRecord(record);

		return ofRecord(record);
	}

	@NonNull
	public List<WOProjectObjectUnderTest> getByQuery(@NonNull final WOObjectUnderTestQuery query)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_ObjectUnderTest.class)
				.addEqualsFilter(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_AD_Org_ID, query.getOrgId())
				.addInArrayFilter(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_ExternalId, query.getExternalIds())
				.create()
				.stream()
				.map(WorkOrderProjectObjectUnderTestRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableList<WOProjectObjectUnderTest> getByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_ObjectUnderTest.class)
				.addEqualsFilter(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_C_OrderLine_Provision_ID, orderLineId.getRepoId())
				.create()
				.list(I_C_Project_WO_ObjectUnderTest.class)
				.stream()
				.map(WorkOrderProjectObjectUnderTestRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static WOProjectObjectUnderTest ofRecord(@NonNull final I_C_Project_WO_ObjectUnderTest record)
	{
		final ProjectId projectId = ProjectId.ofRepoId(record.getC_Project_ID());
		final WOProjectObjectUnderTestId woProjectObjectUnderTestId = WOProjectObjectUnderTestId.ofRepoId(projectId, record.getC_Project_WO_ObjectUnderTest_ID());

		return WOProjectObjectUnderTest.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.objectUnderTestId(woProjectObjectUnderTestId)
				.projectId(projectId)
				.numberOfObjectsUnderTest(record.getNumberOfObjectsUnderTest())
				.externalId(ExternalId.ofOrNull(record.getExternalId()))
				.woDeliveryNote(record.getWODeliveryNote())
				.woManufacturer(record.getWOManufacturer())
				.woObjectType(record.getWOObjectType())
				.woObjectName(record.getWOObjectName())
				.woObjectWhereabouts(record.getWOObjectWhereabouts())
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.objectDeliveredDate(TimeUtil.asInstant(record.getObjectDeliveredDate()))
				.orderLineProvisionId(OrderLineId.ofRepoIdOrNull(record.getC_OrderLine_Provision_ID()))
				.build();
	}
}
