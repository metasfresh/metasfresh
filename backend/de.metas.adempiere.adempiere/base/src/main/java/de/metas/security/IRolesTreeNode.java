package de.metas.security;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;

import de.metas.adempiere.model.I_AD_Role;

/**
 * A node in the {@link I_AD_Role}s hierarchy of included roles and substituted roles.
 * 
 * @author tsa
 *
 */
public interface IRolesTreeNode
{
	RoleId getRoleId();

	int getSeqNo();

	/**
	 * @return included roles and (maybe) substitute roles
	 */
	List<IRolesTreeNode> getChildren();

	/**
	 * Iterate role notes bottom up and aggregate some informations using then given aggregator.
	 * 
	 * @return final aggregation result
	 */
	<ValueType, AggregatedValueType> ValueType aggregateBottomUp(final BottomUpAggregator<ValueType, AggregatedValueType> aggregator);

	/**
	 * Aggregation strategy.
	 * 
	 * @author tsa
	 *
	 * @param <ValueType> result type
	 * @param <AggregatedValueType> intermediate aggregated value
	 */
	interface BottomUpAggregator<ValueType, AggregatedValueType>
	{
		/** @return value of the leaf node */
		ValueType leafValue(IRolesTreeNode node);

		/**
		 * Gets initial aggregation value.
		 * This method is called before iterating the child nodes.
		 * 
		 * @return initial value
		 */
		AggregatedValueType initialValue(IRolesTreeNode node);

		/**
		 * Aggregate given child value into aggregated value.
		 * 
		 * @param aggregatedValue current aggregated value
		 * @param childNode child node
		 * @param value child's calculated value (already aggregated).
		 */
		void aggregateValue(final AggregatedValueType aggregatedValue, final IRolesTreeNode childNode, final ValueType value);

		/**
		 * Converts the given aggregated value to actual value, ready to be returned.
		 * 
		 * @param aggregatedValue
		 */
		ValueType finalValue(AggregatedValueType aggregatedValue);
	}
}
