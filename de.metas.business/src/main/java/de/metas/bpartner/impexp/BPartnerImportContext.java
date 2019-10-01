package de.metas.bpartner.impexp;

import java.util.Objects;

import org.compiere.model.I_I_BPartner;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.impexp.BPartnersCache.BPartner;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
final class BPartnerImportContext
{
	@Getter
	private final BPartnersCache bpartnersCache;
	@Getter
	private final boolean insertOnly;

	@Getter
	@Setter
	private I_I_BPartner currentImportRecord;

	@Builder
	private BPartnerImportContext(
			@NonNull final BPartnersCache bpartnersCache,
			final boolean insertOnly)
	{
		this.bpartnersCache = bpartnersCache;
		this.insertOnly = insertOnly;
	}

	public boolean isSameBPartner(final I_I_BPartner importRecord)
	{
		final I_I_BPartner currentImportRecord = getCurrentImportRecord();

		return currentImportRecord != null
				&& Objects.equals(importRecord.getBPValue(), currentImportRecord.getBPValue())
				&& Objects.equals(importRecord.getGlobalId(), currentImportRecord.getGlobalId());
	}

	public boolean isCurrentBPartnerIdSet()
	{
		return getCurrentBPartnerIdOrNull() != null;
	}

	public BPartner getCurrentBPartner()
	{
		final BPartnerId bpartnerId = getCurrentBPartnerIdOrNull();
		return getBpartnersCache().getBPartnerById(bpartnerId);
	}

	public BPartnerId getCurrentBPartnerIdOrNull()
	{
		final I_I_BPartner currentImportRecord = getCurrentImportRecord();
		return currentImportRecord != null
				? BPartnerId.ofRepoIdOrNull(currentImportRecord.getC_BPartner_ID())
				: null;
	}

	public void setCurrentBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		final I_I_BPartner currentImportRecord = getCurrentImportRecord();
		Check.assumeNotNull(currentImportRecord, "Parameter currentImportRecord is not null");

		currentImportRecord.setC_BPartner_ID(bpartnerId.getRepoId());
	}

	public BPartnerLocationId getCurrentBPartnerLocationIdOrNull()
	{
		final I_I_BPartner currentImportRecord = getCurrentImportRecord();
		return currentImportRecord != null
				? BPartnerLocationId.ofRepoIdOrNull(currentImportRecord.getC_BPartner_ID(), currentImportRecord.getC_BPartner_Location_ID())
				: null;
	}

	public void setCurrentBPartnerLocationId(@NonNull final BPartnerLocationId bpLocationId)
	{
		final I_I_BPartner currentImportRecord = getCurrentImportRecord();
		Check.assumeNotNull(currentImportRecord, "Parameter currentImportRecord is not null");

		currentImportRecord.setC_BPartner_Location_ID(bpLocationId.getRepoId());
	}

	public BPartnerContactId getCurrentBPartnerContactIdOrNull()
	{
		final I_I_BPartner currentImportRecord = getCurrentImportRecord();
		return currentImportRecord != null
				? BPartnerContactId.ofRepoIdOrNull(currentImportRecord.getC_BPartner_ID(), currentImportRecord.getAD_User_ID())
				: null;
	}

	public void setCurrentBPartnerContactId(@NonNull final BPartnerContactId bpContactId)
	{
		final I_I_BPartner currentImportRecord = getCurrentImportRecord();
		Check.assumeNotNull(currentImportRecord, "Parameter currentImportRecord is not null");

		currentImportRecord.setAD_User_ID(bpContactId.getRepoId());
	}

}
