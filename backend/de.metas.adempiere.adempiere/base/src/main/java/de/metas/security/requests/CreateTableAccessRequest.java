package de.metas.security.requests;

import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Creates one {@code AD_Table_Access} row. Every flag is a plain {@code boolean} defaulting (via the
 * builder) to its {@code AD_Table_Access} column's own non-restricting value, so a request that overrides
 * nothing produces a row equivalent to no row at all; flip a flag to restrict that aspect.
 */
@Value
@Builder
public class CreateTableAccessRequest
{
	@NonNull RoleId roleId;

	// Role table-access is org-independent (AD_Table_Access is AccessLevel System+Client), so this
	// defaults to OrgId.ANY (the "*" org) and callers need not pass it; still @NonNull to reject an explicit null.
	@Builder.Default @NonNull OrgId orgId = OrgId.ANY;

	@NonNull AdTableId adTableId;

	// Defaults mirror the (NOT NULL) AD_Table_Access column defaults, verified against the live schema —
	// compiler-checked here rather than described in a "see the AD_Column" prose comment.
	@Builder.Default boolean readOnly = false;

	@Builder.Default boolean canReport = true;

	@Builder.Default boolean canExport = true;

	@Builder.Default boolean canCreateNewRecords = true;
}
