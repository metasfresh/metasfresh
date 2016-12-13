package de.metas.process;

import org.compiere.model.GridField;
import org.compiere.model.Null;

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

/**
 * Let your process implement this interface if you want to provide some default values for process parameters. Those values will be set when the UI parameters panel will be presented to user.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@FunctionalInterface
public interface IProcessDefaultParametersProvider
{
	Object DEFAULT_VALUE_NOTAVAILABLE = null;

	/**
	 * Gets process parameter's default value.
	 * <p>
	 * Note: we could consider to pass a dedicated object to the method, not the full {@link GridField}. See {@link ISvrProcessPrecondition#isPreconditionApplicable(org.adempiere.ad.process.ISvrProcessPrecondition.PreconditionsContext)}.
	 *
	 * @param parameter
	 * @return
	 * 		<ul>
	 *         <li>not null value
	 *         <li>{@link Null#NULL} to specify that we provide a null value
	 *         <li>{@link #DEFAULT_VALUE_NOTAVAILABLE} to advice the caller that we don't have a default value for given parameter and the caller can search forward in other places.
	 *         <li>Important: if the field is a lookup field (table, search etc), then the calles expects an integer <b>ID</b>, not the actual model.
	 *         </ul>
	 */
	Object getParameterDefaultValue(final GridField parameter);
}
