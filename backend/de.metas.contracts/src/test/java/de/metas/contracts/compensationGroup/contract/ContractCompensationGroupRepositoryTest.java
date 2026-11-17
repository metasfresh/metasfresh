package de.metas.contracts.compensationGroup.contract;

import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.project.ProjectId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
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

class ContractCompensationGroupRepositoryTest
{
	private static final int ORDER_ID = 1;
	private static final int GROUP_ID = 10;
	private static final int OTHER_GROUP_ID = 11;

	private ContractCompensationGroupRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new ContractCompensationGroupRepository();
	}

	private static GroupId groupId(final int orderCompensationGroupId)
	{
		return OrderGroupRepository.createGroupId(OrderId.ofRepoId(ORDER_ID), orderCompensationGroupId);
	}

	private static I_C_OrderLine line(final int orderCompensationGroupId, final boolean compensationLine, @Nullable final Integer projectRepoId)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(ORDER_ID);
		line.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		line.setIsGroupCompensationLine(compensationLine);
		if (projectRepoId != null)
		{
			line.setC_Project_ID(projectRepoId);
		}
		saveRecord(line);
		return line;
	}

	@Nullable
	private static ProjectId positionOf(final I_C_OrderLine line)
	{
		return ProjectId.ofRepoIdOrNull(load(line.getC_OrderLine_ID(), I_C_OrderLine.class).getC_Project_ID());
	}

	@Test
	void retrieveCompensationLines_returnsOnlyCompensationLinesOfTheGroup()
	{
		final I_C_OrderLine compensationLine = line(GROUP_ID, true, null);
		line(GROUP_ID, false, null);
		line(OTHER_GROUP_ID, true, null);

		assertThat(repository.retrieveCompensationLines(groupId(GROUP_ID)))
				.extracting(I_C_OrderLine::getC_OrderLine_ID)
				.containsExactly(compensationLine.getC_OrderLine_ID());
	}

	@Test
	void deleteCompensationLines_deletesOnlyCompensationLinesOfTheGroup()
	{
		line(GROUP_ID, true, null);
		final I_C_OrderLine regularLine = line(GROUP_ID, false, null);
		final I_C_OrderLine otherGroupCompensationLine = line(OTHER_GROUP_ID, true, null);

		repository.deleteCompensationLines(groupId(GROUP_ID));

		assertThat(repository.retrieveCompensationLines(groupId(GROUP_ID))).isEmpty();
		assertThat(load(regularLine.getC_OrderLine_ID(), I_C_OrderLine.class)).isNotNull();
		assertThat(repository.retrieveCompensationLines(groupId(OTHER_GROUP_ID)))
				.extracting(I_C_OrderLine::getC_OrderLine_ID)
				.containsExactly(otherGroupCompensationLine.getC_OrderLine_ID());
	}

	@Test
	void setProjectOnCompensationLines_setsThePositionOnLinesWithNoOrOtherPositionAndKeepsTheOthers()
	{
		final I_C_OrderLine withoutPosition = line(GROUP_ID, true, null);
		final I_C_OrderLine withOtherPosition = line(GROUP_ID, true, 7);
		final I_C_OrderLine withSamePosition = line(GROUP_ID, true, 5);
		final I_C_OrderLine regularLine = line(GROUP_ID, false, null);

		repository.setProjectOnCompensationLines(groupId(GROUP_ID), ProjectId.ofRepoId(5));

		assertThat(positionOf(withoutPosition)).isEqualTo(ProjectId.ofRepoId(5));
		assertThat(positionOf(withOtherPosition)).isEqualTo(ProjectId.ofRepoId(5));
		assertThat(positionOf(withSamePosition)).isEqualTo(ProjectId.ofRepoId(5));
		assertThat(positionOf(regularLine)).isNull();
	}

	@Test
	void setProjectOnCompensationLines_withoutPosition_clearsThePositionOfTheLines()
	{
		final I_C_OrderLine withPosition = line(GROUP_ID, true, 7);

		repository.setProjectOnCompensationLines(groupId(GROUP_ID), null);

		assertThat(positionOf(withPosition)).isNull();
	}

	@Test
	void hasPosition_comparesTheLinesPositionIncludingNoPosition()
	{
		final I_C_OrderLine withPosition = line(GROUP_ID, true, 5);
		final I_C_OrderLine withoutPosition = line(GROUP_ID, true, null);

		assertThat(ContractCompensationGroupRepository.hasPosition(withPosition, ProjectId.ofRepoId(5))).isTrue();
		assertThat(ContractCompensationGroupRepository.hasPosition(withPosition, ProjectId.ofRepoId(6))).isFalse();
		assertThat(ContractCompensationGroupRepository.hasPosition(withPosition, null)).isFalse();
		assertThat(ContractCompensationGroupRepository.hasPosition(withoutPosition, null)).isTrue();
		assertThat(ContractCompensationGroupRepository.hasPosition(withoutPosition, ProjectId.ofRepoId(5))).isFalse();
	}
}
