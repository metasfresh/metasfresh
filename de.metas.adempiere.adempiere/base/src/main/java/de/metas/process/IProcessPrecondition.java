package de.metas.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Let your process implement this interface if you want to:
 * <ul>
 * <li>control when this process will be displayed in related processes toolbar (e.g. Gear in Swing)
 * <li>override displayed process caption
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IProcessPrecondition
{
	/**
	 * Determines if a process should be displayed in current context.
	 * <p>
	 * <b>IMPORTANT:</b> this method will not be invoked on the same instance that shall later execute <code>prepare()</code> {@link JavaProcess#doIt(String, String, Object[])},
	 * so it does not make any sense to set any values to be used later.
	 *
	 * @param context
	 * @return precondition resolution
	 */
	ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context);
}
