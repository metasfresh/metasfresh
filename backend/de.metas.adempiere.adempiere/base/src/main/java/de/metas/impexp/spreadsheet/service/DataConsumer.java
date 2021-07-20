/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.impexp.spreadsheet.service;

import java.util.List;

/** Implementors of this interface can receive data from {@link SpreadsheetExporterService} and maybe in future others. */
public interface DataConsumer<T>
{
	void putHeader(List<String> header);

	void putResult(T result);

	/** Might return {@code true} also if {@link #putResult(Object)} was called, but the result to consume turned out to be empty. */
	boolean isNoDataAddedYet();
}
