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
package org.adempiere.archive.api;

import de.metas.bpartner.BPartnerId;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.report.PrintCopies;
import de.metas.util.Check;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Data
public class ArchiveInfo
{
	private boolean withDialog = false;
	private PrintCopies copies = PrintCopies.ONE;
	private boolean isDocumentCopy = false;
	private String printerName = null;
	//
	private final String name;
	private String description;
	private String help;
	private AdProcessId processId;
	private TableRecordReference recordRef;
	private BPartnerId bpartnerId;

	//FRESH-349: AD_PInstance is also needed
	private PInstanceId pInstanceId;

	public ArchiveInfo(@NonNull final ProcessInfo processInfo)
	{
		this.name = normalizeName(processInfo.getTitle());
		this.recordRef = processInfo.getRecordRefOrNull();
		this.processId = processInfo.getAdProcessId();
		this.pInstanceId = processInfo.getPinstanceId();
	}

	public ArchiveInfo(final String name, final TableRecordReference recordRef)
	{
		this.name = normalizeName(name);
		this.recordRef = recordRef;
	}

	private ArchiveInfo(@NonNull final ArchiveInfo printInfo)
	{
		this.withDialog = printInfo.withDialog;
		this.copies = printInfo.copies;
		this.isDocumentCopy = printInfo.isDocumentCopy;
		this.printerName = printInfo.printerName;
		//
		this.name = normalizeName(printInfo.name);
		this.description = printInfo.description;
		this.help = printInfo.help;
		this.processId = printInfo.processId;
		this.recordRef = printInfo.recordRef;
		this.bpartnerId = printInfo.bpartnerId;
		this.pInstanceId = printInfo.pInstanceId;
	}

	private static String normalizeName(@Nullable final String name)
	{
		return Check.isNotBlank(name) ? name : "Unknown";
	}

	public ArchiveInfo copy()
	{
		return new ArchiveInfo(this);
	}

	public boolean isReport()
	{
		return processId != null || bpartnerId == null;
	}
}
