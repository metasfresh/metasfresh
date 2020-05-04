/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.compiere.apps.search;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.ColumnDisplayTypeProvider;
import org.compiere.model.I_AD_UserQuery;
import org.compiere.model.MQuery;
import org.compiere.util.DisplayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

public class UserQueryRepositoryTest
{

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static UserQueryRepository createRepo(final String... columnNames)
	{
		// TODO If we need to add a third column to this provider, we should extract columnNames and columnDisplayType to own builder class, for easier test instantiation
		final ColumnDisplayTypeProvider columnDisplayTypeProvider = columnName -> {
			if ("MyColumn".equals(columnName))
			{
				return DisplayType.String;
			}
			return DisplayType.Quantity;
		};

		return UserQueryRepository.builder()
				.setSearchFields(Stream.of(columnNames)
						.map(PlainUserQueryField::ofColumnName)
						.collect(ImmutableList.toImmutableList()))
				.setAD_Tab_ID(111)
				.setAD_Table_ID(123)
				.setAD_User_ID_Any()
				.setColumnDisplayTypeProvider(columnDisplayTypeProvider)
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

	/**
	 * Standard case, with ShowAllParams and MandatoryParams unset
	 */
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

	@Nested
	class SegmentTests
	{
		@Test
		public void numberIsNotParsedAsString1()
		{
			final String segment = "AND<^>QtyOrdered<^>><^>0<^>";

			final UserQueryRepository repo = createRepo("QtyOrdered");
			final UserQueryRestriction actual = repo.parseUserQuerySegment(segment);

			final UserQueryRestriction expected = new UserQueryRestriction();
			expected.setJoin(IUserQueryRestriction.Join.AND);
			expected.setSearchField(repo.findSearchFieldByColumnName("QtyOrdered"));
			expected.setOperator(MQuery.Operator.GREATER);
			expected.setValue(BigDecimal.ZERO);

			Assertions.assertThat(actual.getValue()).isOfAnyClassIn(BigDecimal.class);
			Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected);
		}

		@Test
		public void numberIsNotParsedAsString2()
		{
			final String segment = "AND<^>QtyInvoiced<^>=<^>0<^>";

			final UserQueryRepository repo = createRepo("QtyInvoiced");
			final UserQueryRestriction actual = repo.parseUserQuerySegment(segment);

			final UserQueryRestriction expected = new UserQueryRestriction();
			expected.setJoin(IUserQueryRestriction.Join.AND);
			expected.setSearchField(repo.findSearchFieldByColumnName("QtyInvoiced"));
			expected.setOperator(MQuery.Operator.EQUAL);
			expected.setValue(BigDecimal.ZERO);

			Assertions.assertThat(actual.getValue()).isOfAnyClassIn(BigDecimal.class);
			Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected);
		}

		@Test
		public void numberIsNotParsedAsString3()
		{
			final String segment = "AND<^>QtyInvoiced<^>=<^>66<^>";

			final UserQueryRepository repo = createRepo("QtyInvoiced");
			final UserQueryRestriction actual = repo.parseUserQuerySegment(segment);

			final UserQueryRestriction expected = new UserQueryRestriction();
			expected.setJoin(IUserQueryRestriction.Join.AND);
			expected.setSearchField(repo.findSearchFieldByColumnName("QtyInvoiced"));
			expected.setOperator(MQuery.Operator.EQUAL);
			expected.setValue(BigDecimal.valueOf(66));

			Assertions.assertThat(actual.getValue()).isOfAnyClassIn(BigDecimal.class);
			Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected);
		}
	}
}
