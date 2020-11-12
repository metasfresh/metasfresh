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

package de.metas.report;

import de.metas.i18n.Language;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import javax.annotation.Nullable;

@Value
@Builder
public class DocumentReportRequest
{
	@Nullable
	AdProcessId callingFromProcessId;

	@NonNull TableRecordReference documentRef;

	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull UserId userId;
	@NonNull RoleId roleId;
	@Builder.Default
	int windowNo = Env.WINDOW_MAIN;
	@Builder.Default
	int tabNo = 0;

	boolean printPreview;
	@Builder.Default
	int printCopies = 1;

	Language reportLanguage;
}
