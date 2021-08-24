/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.bpartner.patient;

import de.metas.bpartner.BPartnerId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class AlbertaPatient
{
	@Nullable
	BPartnerAlbertaPatientId bPartnerAlbertaPatientId;

	@NonNull
	BPartnerId bPartnerId;

	@Nullable
	BPartnerId hospitalId;

	@Nullable
	LocalDate dischargeDate;

	@Nullable
	BPartnerId payerId;

	@Nullable
	PayerType payerType;

	@Nullable
	String numberOfInsured;

	@Nullable
	LocalDate copaymentFrom;

	@Nullable
	LocalDate copaymentTo;

	boolean isTransferPatient;

	boolean isIVTherapy;

	@Nullable
	UserId fieldNurseId;

	@Nullable
	DeactivationReasonType deactivationReason;

	@Nullable
	LocalDate deactivationDate;

	@Nullable
	String deactivationComment;

	@Nullable
	Instant createdAt;

	@Nullable
	UserId createdById;

	@Nullable
	Instant updatedAt;

	@Nullable
	UserId updatedById;

	public BPartnerAlbertaPatientId getIdNotNull()
	{
		if (bPartnerAlbertaPatientId == null)
		{
			throw new AdempiereException("getIdNotNull should be called only for persisted records");
		}

		return bPartnerAlbertaPatientId;
	}
}
