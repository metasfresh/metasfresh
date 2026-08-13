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
 * What {@link VATaxIDCheckService} needs to read from the record whose VAT-ID status columns a check
 * maintains — the {@code C_BPartner} header or the {@code C_BPartner_Location}, whichever the request
 * names: the organisation whose {@code VATaxID_Config} applies, and the status stored right now.
 *
 * <p>Deliberately these two fields and no more: the stored status is needed to tell a changed status from
 * an unchanged one and to answer a check that is configured off, and the organisation is needed to resolve
 * the configuration — nothing else about the parent record concerns the check.
 *
 * <p>Read by {@link VATaxIDParentStatusRepository#getParentStatus(VATaxIDCheckRequest)}; the matching
 * write takes a {@link VATaxIDLastCheck} instead, because a completed check contributes three columns
 * ({@code VATaxIDStatus}, {@code VATaxIDCheckedAt}, {@code VATaxID_CheckLog_ID}) and never the
 * organisation.
 */
@Value
@Builder
public class VATaxIDParentStatus
{
	@NonNull OrgId orgId;

	@NonNull VATaxIDStatus status;
}
