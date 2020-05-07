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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/**
 * Exactly the same as {@link AutoCloseable} but {@link #close()} method is not declared to throw exceptions.
 *
 * The main benefit of this interface is that allows you to quickly create annonymous inner {@link AutoCloseable} instances,
 * which you can use in your try-with-resources block without having to declare an "catch" block.
 *
 * @author tsa
 *
 */
@FunctionalInterface
public interface IAutoCloseable extends AutoCloseable
{
	@Override
	public void close();
}
