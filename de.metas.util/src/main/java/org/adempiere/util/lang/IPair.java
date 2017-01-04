package org.adempiere.util.lang;

/*
 * #%L
 * de.metas.util
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

/**
 * A pair consisting of two elements.
 * <p>
 * Hint: use {@link ImmutablePair} to make your own instance.
 *
 * @author tsa
 *
 * @param <LT> left element type
 * @param <RT> right element type
 */
public interface IPair<LT, RT>
{
	/**
	 * Gets the left element from this pair.
	 *
	 * @return the left element, may be null
	 */
	public LT getLeft();

	/**
	 * Gets the right element from this pair.
	 *
	 * @return the right element, may be null
	 */
	public RT getRight();
}
