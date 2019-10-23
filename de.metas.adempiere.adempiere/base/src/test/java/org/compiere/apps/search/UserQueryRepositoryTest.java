package org.compiere.apps.search;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_UserQuery;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import lombok.Builder;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class UserQueryRepositoryTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static UserQueryRepository createRepo(final String... columnNames)
	{
		return UserQueryRepository.builder()
				.setSearchFields(Stream.of(columnNames)
						.map(PlainUserQueryField::ofColumnName)
						.collect(ImmutableList.toImmutableList()))
				.setAD_Tab_ID(111)
				.setAD_Table_ID(123)
				.setAD_User_ID_Any()
				.build();
	}

	@Builder(builderMethodName = "newUserQueryRecord", builderClassName = "UserQueryRecordBuilder")
	private I_AD_UserQuery createUserQueryRecord(
			final String code,
			final boolean manadatoryParams,
			final boolean showAllParams)
	{
		final I_AD_UserQuery record = newInstance(I_AD_UserQuery.class);
		record.setCode(code);
		record.setIsManadatoryParams(manadatoryParams);
		record.setIsShowAllParams(showAllParams);
		// save is not needed
		return record;
	}

	/** Standard case, with ShowAllParams and MandatoryParams unset */
	@Test
	public void test_createUserQuery_InternalParameter()
	{
		final UserQueryRepository repo = createRepo("MyColumn");

		final I_AD_UserQuery record = newUserQueryRecord()
				.code("AND<^>MyColumn<^>=<^>Y<^>")
				.showAllParams(false)
				.manadatoryParams(false)
				.build();
		final IUserQuery userQuery = repo.createUserQuery(record);
		System.out.println("User query: " + userQuery);

		final IUserQueryRestriction restriction = userQuery.getRestrictions().get(0);
		assertThat(restriction.isInternalParameter()).isTrue();
		assertThat(restriction.isMandatory()).isFalse();
	}

	@Test
	public void test_createUserQuery_InternalParameter_FlaggedAsMandatory()
	{
		final UserQueryRepository repo = createRepo("MyColumn");

		final I_AD_UserQuery record = newUserQueryRecord()
				.code("AND<^>MyColumn<^>=<^>Y<^>")
				.showAllParams(false)
				.manadatoryParams(true)
				.build();
		final IUserQuery userQuery = repo.createUserQuery(record);
		System.out.println("User query: " + userQuery);

		final IUserQueryRestriction restriction = userQuery.getRestrictions().get(0);
		assertThat(restriction.isInternalParameter()).isTrue();
		assertThat(restriction.isMandatory()).isFalse(); // internal params shall never be mandatory
	}

	@Test
	public void test_createUserQuery_StandardParameter()
	{
		final UserQueryRepository repo = createRepo("MyColumn");

		final I_AD_UserQuery record = newUserQueryRecord()
				.code("AND<^>MyColumn<^>=<^><^>") // remark: the parameter value is NOT set
				.showAllParams(false)
				.manadatoryParams(false)
				.build();
		final IUserQuery userQuery = repo.createUserQuery(record);
		System.out.println("User query: " + userQuery);

		final IUserQueryRestriction restriction = userQuery.getRestrictions().get(0);
		assertThat(restriction.isInternalParameter()).isFalse();
		assertThat(restriction.isMandatory()).isFalse();
	}

	@Test
	public void test_createUserQuery_ShowAllParams()
	{
		final UserQueryRepository repo = createRepo("MyColumn");

		final I_AD_UserQuery record = newUserQueryRecord()
				.code("AND<^>MyColumn<^>=<^>Y<^>")
				.showAllParams(true)
				.manadatoryParams(false)
				.build();
		final IUserQuery userQuery = repo.createUserQuery(record);
		System.out.println("User query: " + userQuery);

		final IUserQueryRestriction restriction = userQuery.getRestrictions().get(0);
		assertThat(restriction.isInternalParameter()).isFalse();
		assertThat(restriction.isMandatory()).isFalse();
	}

	@Test
	public void test_createUserQuery_ShowAllParams_MandatoryParam()
	{
		final UserQueryRepository repo = createRepo("MyColumn");

		final I_AD_UserQuery record = newUserQueryRecord()
				.code("AND<^>MyColumn<^>=<^>Y<^>")
				.showAllParams(true)
				.manadatoryParams(true)
				.build();
		final IUserQuery userQuery = repo.createUserQuery(record);
		System.out.println("User query: " + userQuery);

		final IUserQueryRestriction restriction = userQuery.getRestrictions().get(0);
		assertThat(restriction.isInternalParameter()).isFalse();
		assertThat(restriction.isMandatory()).isTrue();
	}

}
