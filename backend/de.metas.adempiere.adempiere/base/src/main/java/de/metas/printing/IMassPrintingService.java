/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.printing;

import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy.ExecuteReportResult;
import org.adempiere.archive.api.ArchiveInfo;

public interface IMassPrintingService
{
	/**
	 * A {@link ProcessInfo} parameter with this name can be used to specify the number of copies (1 means one printout) in case using {@link ArchiveInfo} is not feasible.
	 */
	String PARAM_PrintCopies = "PrintCopies";

	void print(ExecuteReportResult executeReportResult, ProcessInfo pi);
}
