/*
 * #%L
 * de.metas.vatid
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.vatid;

import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * What {@link VATaxIDCheckService} reads from the record whose status columns a check maintains — the
 * {@code C_BPartner} header or the {@code C_BPartner_Location}: the organisation whose
 * {@code VATaxID_Config} applies, and the currently stored status.
 *
 * <p>These two and no more. The matching write takes a {@link VATaxIDLastCheck} instead, because a
 * completed check contributes three columns and never the organisation.
 */
@Value
@Builder
public class VATaxIDParentStatus
{
	@NonNull OrgId orgId;

	@NonNull VATaxIDStatus status;
}
