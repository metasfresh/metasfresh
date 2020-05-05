package de.metas.handlingunits.attribute.strategy;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.compiere.model.I_M_Attribute;

/**
 * Implements the aggregation of attribute values which are propagated from children to parent (i.e. up the HU hierachy).
 *
 */
public interface IAttributeAggregationStrategy extends IAttributeStrategy
{
	/**
	 * @param attribute the "child" attribute whose value shall be aggregated to the parent.
	 * @param valueOld the old "parent" value into which the given <code>valueDelta</code> shall be aggregated.
	 * @param valueDelta the value (typically coming from the given <code>attribute</code>) the shall be aggregated to the given <code>valueOld</code>.
	 * @return value the new value after <code>valueDelta</code> has been aggregated/integrated into <code>valueOld</code>.
	 */
	Object aggregate(I_M_Attribute attribute, Object valueOld, Object valueDelta);
}
