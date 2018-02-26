package de.metas.contracts.compensationGroup;

import java.util.List;
import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.contracts.model.I_C_CompensationGroup_SchemaLine;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.Group.GroupBuilder;
import de.metas.order.compensationGroup.GroupMatcherFactory;
import de.metas.order.compensationGroup.OrderGroupRepositoryAdvisor;
import lombok.ToString;

/*
 * #%L
 * de.metas.contracts
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

@Component
public class ContractsCompensationGroupAdvisor implements OrderGroupRepositoryAdvisor, GroupMatcherFactory
{
	@Override
	public void customizeFromOrder(final GroupBuilder groupBuilder, final I_C_Order order, final List<org.compiere.model.I_C_OrderLine> groupOrderLines)
	{
		groupBuilder.flatrateConditionsId(extractFlatrateTermConditionsId(groupOrderLines));
	}

	private static int extractFlatrateTermConditionsId(final List<org.compiere.model.I_C_OrderLine> groupOrderLines)
	{
		final ImmutableSet<Integer> flatrateConditionsIds = groupOrderLines.stream()
				.map(groupOrderLine -> InterfaceWrapperHelper.create(groupOrderLine, I_C_OrderLine.class))
				.map(I_C_OrderLine::getC_Flatrate_Conditions_ID)
				.filter(flatrateTermConditionsId -> flatrateTermConditionsId > 0)
				.collect(ImmutableSet.toImmutableSet());
		if (flatrateConditionsIds.isEmpty())
		{
			return -1;
		}
		else if (flatrateConditionsIds.size() == 1)
		{
			return flatrateConditionsIds.iterator().next();
		}
		else
		{
			throw new AdempiereException("More than one flatrate conditions found: " + flatrateConditionsIds);
		}
	}

	@Override
	public String getAppliesToLineType()
	{
		return I_C_CompensationGroup_SchemaLine.TYPE_Flatrate;
	}

	@Override
	public Predicate<Group> createPredicate(
			final de.metas.order.model.I_C_CompensationGroup_SchemaLine schemaLine,
			final List<de.metas.order.model.I_C_CompensationGroup_SchemaLine> allSchemaLines)
	{
		final I_C_CompensationGroup_SchemaLine contractsSchemaLine = InterfaceWrapperHelper.create(schemaLine, I_C_CompensationGroup_SchemaLine.class);
		final int flatrateConditionsId = contractsSchemaLine.getC_Flatrate_Conditions_ID();
		return new FlatrateConditionsGroupMatcher(flatrateConditionsId);
	}

	@ToString
	private static final class FlatrateConditionsGroupMatcher implements Predicate<Group>
	{
		private final int flatrateConditionsId;

		public FlatrateConditionsGroupMatcher(int flatrateConditionsId)
		{
			Check.assume(flatrateConditionsId > 0, "flatrateConditionsId > 0");
			this.flatrateConditionsId = flatrateConditionsId;
		}

		@Override
		public boolean test(final Group group)
		{
			return flatrateConditionsId == group.getFlatrateConditionsId();
		}
	}
}
