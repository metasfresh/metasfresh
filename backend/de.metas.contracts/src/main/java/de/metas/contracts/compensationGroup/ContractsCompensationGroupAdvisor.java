package de.metas.contracts.compensationGroup;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.Group.GroupBuilder;
import de.metas.order.compensationGroup.GroupMatcher;
import de.metas.order.compensationGroup.GroupMatcherFactory;
import de.metas.order.compensationGroup.GroupTemplateCompensationLineType;
import de.metas.order.compensationGroup.OrderGroupRepositoryAdvisor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

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
		groupBuilder.contractConditionsId(extractContractConditionsId(groupOrderLines));
	}

	@Nullable
	private static ConditionsId extractContractConditionsId(final List<org.compiere.model.I_C_OrderLine> groupOrderLines)
	{
		final ImmutableSet<ConditionsId> contractConditionsIds = groupOrderLines.stream()
				.map(ContractsCompensationGroupAdvisor::extractContractConditionsId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (contractConditionsIds.isEmpty())
		{
			return null;
		}
		else if (contractConditionsIds.size() == 1)
		{
			return contractConditionsIds.iterator().next();
		}
		else
		{
			throw new AdempiereException("More than one contract conditions found: " + contractConditionsIds);
		}
	}

	@Nullable
	private static ConditionsId extractContractConditionsId(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_OrderLine contractsOrderLine = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);
		return ConditionsId.ofRepoIdOrNull(contractsOrderLine.getC_Flatrate_Conditions_ID());
	}

	@Override
	public GroupTemplateCompensationLineType getAppliesToLineType()
	{
		return GroupTemplateCompensationLineType.CONTRACT;
	}

	@Override
	public GroupMatcher createPredicate(
			final de.metas.order.model.I_C_CompensationGroup_SchemaLine schemaLine,
			final List<de.metas.order.model.I_C_CompensationGroup_SchemaLine> allSchemaLines)
	{
		final ConditionsId contractConditionsId = ConditionsId.ofRepoIdOrNull(schemaLine.getC_Flatrate_Conditions_ID());
		if (contractConditionsId == null)
		{
			throw new AdempiereException("Schema line does not have contract conditions set: " + schemaLine);
		}
		return new ContractConditionsGroupMatcher(contractConditionsId);
	}

	@ToString
	@AllArgsConstructor
	private static final class ContractConditionsGroupMatcher implements GroupMatcher
	{
		private final ConditionsId contractConditionsId;

		@Override
		public boolean isMatching(@NonNull final Group group)
		{
			return Objects.equals(contractConditionsId, group.getContractConditionsId());

			// if i just this instead:
			// 
			// return ConditionsId.equals(contractConditionsId, group.getContractConditionsId());
			//
			// then i get this build error in intellij
			// /home/tobi/work-metas_2/metasfresh/backend/de.metas.contracts/src/main/java/de/metas/contracts/compensationGroup/ContractsCompensationGroupAdvisor.java:111:32
			// java: no suitable method found for equals(de.metas.contracts.ConditionsId,de.metas.contracts.ConditionsId)
			// method java.lang.Object.equals(java.lang.Object) is not applicable
			// 	(actual and formal argument lists differ in length)
			// method de.metas.contracts.ConditionsId.equals(java.lang.Object) is not applicable
			// 	(actual and formal argument lists differ in length)
			
		}
	}
}
