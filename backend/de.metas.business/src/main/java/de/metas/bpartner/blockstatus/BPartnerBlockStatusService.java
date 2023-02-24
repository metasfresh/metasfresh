/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.blockstatus;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_BlockStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BPartnerBlockStatusService
{
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull
	private final BPartnerBlockStatusRepository bPartnerBlockStatusRepository;

	public BPartnerBlockStatusService(@NonNull final BPartnerBlockStatusRepository bPartnerBlockStatusRepository)
	{
		this.bPartnerBlockStatusRepository = bPartnerBlockStatusRepository;
	}

	@NonNull
	public BPartnerBlockStatus createBPartnerBlockStatus(@NonNull final CreateBPartnerBlockStatusRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			final BPartnerBlockStatus status = bPartnerBlockStatusRepository.create(request);

			updateBPartnerRecord(status);

			return status;
		});
	}

	public boolean isBPartnerBlocked(@NonNull final BPartnerId bPartnerId)
	{
		return retrieveBlockedByBPartnerId(bPartnerId)
				.map(BPartnerBlockStatus::isBlocked)
				.orElse(false);
	}

	@VisibleForTesting
	@NonNull
	public Optional<BPartnerBlockStatus> retrieveBlockedByBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return bPartnerBlockStatusRepository.retrieveBlockedByBPartnerId(bPartnerId);
	}

	@NonNull
	public IQuery<I_C_BPartner_BlockStatus> getBlockedBPartnerQuery()
	{
		return bPartnerBlockStatusRepository.getBlockedBPartnerQuery();
	}

	private void updateBPartnerRecord(@NonNull final BPartnerBlockStatus status)
	{
		final I_C_BPartner bPartner = bPartnerDAO.getById(status.getBPartnerId());

		if (bPartner.isActive() && status.getStatus().isInactivateBPartner())
		{
			bPartner.setIsActive(false);
			bPartnerDAO.save(bPartner);
		}
		else if (!bPartner.isActive() && status.getStatus().isActivateBPartner())
		{
			bPartner.setIsActive(true);
			bPartnerDAO.save(bPartner);
		}
	}
}
