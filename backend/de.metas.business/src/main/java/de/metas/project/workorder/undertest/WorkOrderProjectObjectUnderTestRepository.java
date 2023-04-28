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
import com.google.common.collect.Maps;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
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
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class WorkOrderProjectObjectUnderTestRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);
	private static final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

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
		final Set<Integer> objectUnderTestIds = objectUnderTestList.stream()
				.map(WOProjectObjectUnderTest::getObjectUnderTestId)
				.map(WOProjectObjectUnderTestId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_C_Project_WO_ObjectUnderTest> objectUnderTestRecords = InterfaceWrapperHelper.loadByIds(objectUnderTestIds, I_C_Project_WO_ObjectUnderTest.class);

		final Map<Integer, I_C_Project_WO_ObjectUnderTest> objectUnderTestId2Record = Maps.uniqueIndex(objectUnderTestRecords,
																									   I_C_Project_WO_ObjectUnderTest::getC_Project_WO_ObjectUnderTest_ID);

		final ImmutableList.Builder<WOProjectObjectUnderTest> savedObjectsUnderTest = ImmutableList.builder();

		for (final WOProjectObjectUnderTest objectUnderTest : objectUnderTestList)
		{
			final I_C_Project_WO_ObjectUnderTest record = objectUnderTestId2Record.get(objectUnderTest.getObjectUnderTestId().getRepoId());

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

			if (objectUnderTest.getProductId() != null)
			{
				record.setM_Product_ID(objectUnderTest.getProductId().getRepoId());
			}
			if (objectUnderTest.getObjectDeliveredDate() != null)
			{
				record.setObjectDeliveredDate(TimeUtil.asTimestamp(objectUnderTest.getObjectDeliveredDate()));
			}
			if (objectUnderTest.getOrderLineProvisionId() != null)
			{
				record.setC_OrderLine_Provision_ID(OrderLineId.toRepoId(objectUnderTest.getOrderLineProvisionId()));
			}

			saveRecord(record);

			savedObjectsUnderTest.add(ofRecord(record));
		}

		return savedObjectsUnderTest.build();
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

	@Nullable
	public WOProjectObjectUnderTest getByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return Optional.ofNullable(queryBL.createQueryBuilder(I_C_Project_WO_ObjectUnderTest.class)
										   .addEqualsFilter(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_C_OrderLine_Provision_ID, orderLineId.getRepoId())
										   .create()
										   .firstOptional(I_C_Project_WO_ObjectUnderTest.class))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(WorkOrderProjectObjectUnderTestRepository::ofRecord)
				.orElse(null);
	}

	public void updateObjectDeliveredDate(@NonNull final WOProjectObjectUnderTest woProjectObjectUnderTest, @NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Project_WO_ObjectUnderTest record = InterfaceWrapperHelper.loadByIds(ImmutableSet.of(woProjectObjectUnderTest.getObjectUnderTestId().getRepoId()), I_C_Project_WO_ObjectUnderTest.class).get(0);
		record.setObjectDeliveredDate(orderLine.getDateDelivered());
		InterfaceWrapperHelper.saveRecord(record);
	}

	@NonNull
	private static WOProjectObjectUnderTest ofRecord(@NonNull final I_C_Project_WO_ObjectUnderTest record)
	{
		final ProjectId projectId = ProjectId.ofRepoId(record.getC_Project_ID());
		final WOProjectObjectUnderTestId woProjectObjectUnderTestId = WOProjectObjectUnderTestId.ofRepoId(projectId, record.getC_Project_WO_ObjectUnderTest_ID());

		final LocalDate objectDeliveredLocalDate = Optional.ofNullable(record.getObjectDeliveredDate())
				.map(timestamp -> TimeUtil.asLocalDate(timestamp, orgDAO.getTimeZone(Env.getOrgId())))
				.orElse(null);

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
				.objectDeliveredDate(objectDeliveredLocalDate)
				.orderLineProvisionId(OrderLineId.ofRepoIdOrNull(record.getC_OrderLine_Provision_ID()))
				.build();
	}
}
