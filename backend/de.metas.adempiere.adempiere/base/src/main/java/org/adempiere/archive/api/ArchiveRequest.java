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
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import de.metas.report.DocumentReportFlavor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Properties;

@Value
@Builder
public class ArchiveRequest
{
	@Nullable
	DocumentReportFlavor flavor;

	byte[] data;

	/**
	 * if true, the document will be archived anyway (even if auto-archive is not activated)
	 */
	boolean force;

	/**
	 * save I_AD_Archive record
	 */
	boolean save;

	@NonNull
	@Builder.Default
	Properties ctx = Env.getCtx();

	String trxName;

	// ported from ArchiveInfo
	boolean isReport;
	@Nullable
	TableRecordReference recordRef;
	@Nullable
	AdProcessId processId;
	@Nullable
	PInstanceId pinstanceId;
	@Nullable
	String archiveName;
	@Nullable
	BPartnerId bpartnerId;

	@Nullable
	Language language;
}
