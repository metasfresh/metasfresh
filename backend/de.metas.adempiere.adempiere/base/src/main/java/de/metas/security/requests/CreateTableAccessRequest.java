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
 * Creates one {@code AD_Table_Access} row. Each flag is nullable here: {@code null} means the row says
 * nothing about that aspect, so the column keeps its own (non-restricting) default.
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

	// The four access flags mirror their AD_Table_Access column defaults (NOT NULL; verified against the
	// live schema). A row left at these defaults is non-restricting — equivalent to no row; flip one to
	// restrict that aspect. Mirroring the DB defaults here keeps them compiler-checked in the builder
	// instead of a prose "see the AD_Column" comment.
	@Builder.Default boolean readOnly = false;            // IsReadOnly            default 'N'

	@Builder.Default boolean canReport = true;            // IsCanReport           default 'Y'

	@Builder.Default boolean canExport = true;            // IsCanExport           default 'Y'

	@Builder.Default boolean canCreateNewRecords = true;  // IsCanCreateNewRecords default 'Y'
}
