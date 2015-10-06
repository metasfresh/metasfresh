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


import de.metas.handlingunits.attribute.storage.IAttributeStorage;

/**
 * Implementations of this interface are responsible for splitting a parent attribute's value so that it can be distributed among the child attributes.
 *
 * @author tsa
 */
public interface IAttributeSplitterStrategy extends IAttributeStrategy
{
	/**
	 * @param attribute
	 * @param value value to be split
	 * @return split {@link IAttributeSplitResult}
	 */
	IAttributeSplitResult split(IAttributeSplitRequest request);

	/**
	 * Method called after {@link #split(IAttributeSplitRequest)} in case an attribute storage has not accepted our {@link IAttributeSplitResult#getSplitValue()}.
	 *
	 * @param result result returned by previous {@link #split(IAttributeSplitRequest)} invocation
	 * @param valueSet value that was actually set/accepted by {@link IAttributeStorage}.
	 * @return remaining value adjusted by this unexpected refusal
	 */
	Object recalculateRemainingValue(IAttributeSplitResult result, Object valueSet);
}
