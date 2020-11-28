/*
 * #%L
 * de.metas.swat.base
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

package de.metas.adempiere.service;

import de.metas.document.DocTypeId;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
public class PrinterRoutingsQuery
{
	@Builder.Default
	ClientId clientId = ClientId.METASFRESH;

	@Builder.Default
	OrgId orgId = OrgId.ANY;

	@Nullable
	RoleId roleId;

	@Nullable
	UserId userId;

	@Nullable
	DocTypeId docTypeId;

	@Nullable
	AdProcessId processId;

	@Nullable
	AdTableId tableId;

	@Nullable
	String printerType;
}
