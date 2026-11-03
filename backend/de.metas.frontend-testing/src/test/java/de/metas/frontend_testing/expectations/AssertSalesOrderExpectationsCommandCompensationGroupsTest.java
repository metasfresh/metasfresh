package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.expectations.request.JsonOrderCompensationGroupExpectation;
import de.metas.order.OrderId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.frontend-testing
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

/**
 * The {@code Backend.expect({salesOrders: {<id>: {compensationGroups: [...]}}})} check: the order's compensation groups,
 * matched in id order, carry the expected contract term and schema.
 */
class AssertSalesOrderExpectationsCommandCompensationGroupsTest
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(100);

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static I_C_Order_CompensationGroup group(final int flatrateTermId, final int schemaId)
	{
		final I_C_Order_CompensationGroup group = InterfaceWrapperHelper.newInstance(I_C_Order_CompensationGroup.class);
		group.setC_Order_ID(ORDER_ID.getRepoId());
		group.setName("group");
		group.setC_Flatrate_Term_ID(flatrateTermId);
		group.setC_CompensationGroup_Schema_ID(schemaId);
		InterfaceWrapperHelper.saveRecord(group);
		return group;
	}

	private static JsonOrderCompensationGroupExpectation expected(final Integer flatrateTermId, final Integer schemaId)
	{
		return JsonOrderCompensationGroupExpectation.builder()
				.flatrateTermId(flatrateTermId)
				.compensationGroupSchemaId(schemaId)
				.build();
	}

	private static void assertGroups(final List<I_C_Order_CompensationGroup> actual, final JsonOrderCompensationGroupExpectation... expected)
	{
		AssertSalesOrderExpectationsCommand.assertCompensationGroups(actual, ORDER_ID, ImmutableList.copyOf(expected));
	}

	@Test
	void theContractsGroup_matches()
	{
		final List<I_C_Order_CompensationGroup> actual = ImmutableList.of(group(10, 20));

		assertThatCode(() -> assertGroups(actual, expected(10, 20))).doesNotThrowAnyException();
	}

	@Test
	void aNullExpectedIdIsNotAsserted()
	{
		final List<I_C_Order_CompensationGroup> actual = ImmutableList.of(group(10, 20));

		assertThatCode(() -> assertGroups(actual, expected(10, null))).doesNotThrowAnyException();
	}

	@Test
	void anotherTerm_fails()
	{
		final List<I_C_Order_CompensationGroup> actual = ImmutableList.of(group(11, 20));

		assertThatThrownBy(() -> assertGroups(actual, expected(10, 20)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_Flatrate_Term_ID");
	}

	@Test
	void anotherSchema_fails()
	{
		final List<I_C_Order_CompensationGroup> actual = ImmutableList.of(group(10, 21));

		assertThatThrownBy(() -> assertGroups(actual, expected(10, 20)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_Schema_ID");
	}

	@Test
	void anExtraGroup_fails()
	{
		final List<I_C_Order_CompensationGroup> actual = ImmutableList.of(group(10, 20), group(10, 20));

		assertThatThrownBy(() -> assertGroups(actual, expected(10, 20)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("compensation groups");
	}
}
