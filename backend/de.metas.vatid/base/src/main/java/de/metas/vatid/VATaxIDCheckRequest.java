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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.process.PInstanceId;
import de.metas.tax.api.VATIdentifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import org.adempiere.ad.session.AdSessionId;

/**
 * Everything needed to append a {@code VATaxID_CheckLog} row at {@link VATaxIDStatus#RequestSent}.
 *
 * <p>Exactly one of {@link #getPinstanceId()} / {@link #getAdSessionId()} identifies what caused the check
 * (a process run vs. a user session), or both are {@code null} for a check that has neither.
 */
@Value
@Builder
public class VATaxIDCheckRequest
{
	@NonNull BPartnerId bpartnerId;

	@Nullable BPartnerLocationId bpartnerLocationId;

	@NonNull VATIdentifier vataxID;

	@Nullable PInstanceId pinstanceId;

	@Nullable AdSessionId adSessionId;
}
