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

package de.metas.project.workorder.data;

import com.google.common.collect.ImmutableList;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectObjectUnderTestId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_ObjectUnderTest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class WorkOrderProjectObjectUnderTestRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public List<WOProjectObjectUnderTest> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_ObjectUnderTest.class)
				.addEqualsFilter(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_C_Project_ID, projectId.getRepoId())
				.create()
				.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public WOProjectObjectUnderTest save(@NonNull final WOProjectObjectUnderTest objectUnderTestData)
	{
		final WOProjectObjectUnderTestId existingRecordId;
		if (objectUnderTestData.getId() != null)
		{
			existingRecordId = objectUnderTestData.getIdNonNull();
		}
		else if (objectUnderTestData.getExternalId() != null)
		{
			existingRecordId = getByProjectId(objectUnderTestData.getProjectIdNonNull())
					.stream()
					.filter(s -> Objects.equals(s.getExternalId(), objectUnderTestData.getExternalId()))
					.findAny()
					.map(WOProjectObjectUnderTest::getId)
					.orElse(null);
		}
		else
		{
			existingRecordId = null;
		}

		final I_C_Project_WO_ObjectUnderTest record = InterfaceWrapperHelper.loadOrNew(existingRecordId, I_C_Project_WO_ObjectUnderTest.class);

		if (objectUnderTestData.getExternalId() != null)
		{
			record.setExternalId(objectUnderTestData.getExternalId().getValue());
		}

		record.setNumberOfObjectsUnderTest(objectUnderTestData.getNumberOfObjectsUnderTest());
		record.setC_Project_ID(objectUnderTestData.getProjectIdNonNull().getRepoId());
		record.setWODeliveryNote(objectUnderTestData.getWoDeliveryNote());
		record.setWOManufacturer(objectUnderTestData.getWoManufacturer());
		record.setWOObjectType(objectUnderTestData.getWoObjectType());
		record.setWOObjectName(objectUnderTestData.getWoObjectName());
		record.setWOObjectWhereabouts(objectUnderTestData.getWoObjectWhereabouts());

		InterfaceWrapperHelper.save(record);

		return ofRecord(record);
	}

	@NonNull
	private WOProjectObjectUnderTest ofRecord(@NonNull final I_C_Project_WO_ObjectUnderTest record)
	{
		final ProjectId projectId = ProjectId.ofRepoId(record.getC_Project_ID());
		final WOProjectObjectUnderTestId id = WOProjectObjectUnderTestId.ofRepoId(projectId, record.getC_Project_WO_ObjectUnderTest_ID());

		return WOProjectObjectUnderTest.builder()
				.id(id)
				.numberOfObjectsUnderTest(record.getNumberOfObjectsUnderTest())
				.projectId(projectId)
				.externalId(ExternalId.of(record.getExternalId()))
				.woDeliveryNote(record.getWODeliveryNote())
				.woManufacturer(record.getWOManufacturer())
				.woObjectType(record.getWOObjectType())
				.woObjectName(record.getWOObjectName())
				.woObjectWhereabouts(record.getWOObjectWhereabouts())
				.build();
	}
}
