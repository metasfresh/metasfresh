package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.repository.BPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
@Transactional
public class SyncBPartnerImportService extends AbstractSyncImportService
{
	@Autowired
	@Lazy
	private BPartnerRepository bpartnersRepo;
	@Autowired
	@Lazy
	private SyncContractListImportService contractsListImportService;
	@Autowired
	@Lazy
	private SyncUserImportService usersImportService;
	@Autowired
	@Lazy
	private SyncRfqImportService rfqImportService;

	public BPartner importBPartner(final SyncBPartner syncBpartner)
	{
		//
		// Import the BPartner only
		final BPartner bpartner = importBPartnerNoCascade(syncBpartner);

		//
		// Users
		usersImportService.importUsers(bpartner, syncBpartner.getUsers());

		//
		// Contracts
		if (syncBpartner.isSyncContracts())
		{
			try
			{
				contractsListImportService.importContracts(bpartner, syncBpartner.getContracts());
			}
			catch (Exception ex)
			{
				logger.warn("Failed importing contracts for {}. Skipped", bpartner, ex);
			}
		}

		//
		// RfQs
		try
		{
			final List<SyncRfQ> rfqs = syncBpartner.getRfqs();
			rfqImportService.importRfQs(bpartner, rfqs);
		}
		catch (Exception ex)
		{
			logger.warn("Failed importing contracts for {}. Skipped", bpartner, ex);
		}

		return bpartner;
	}

	private BPartner importBPartnerNoCascade(final SyncBPartner syncBpartner)
	{
		//
		// BPartner
		BPartner bpartner = bpartnersRepo.findByUuid(syncBpartner.getUuid());

		//
		// Handle delete request
		if (syncBpartner.isDeleted())
		{
			if (bpartner == null)
			{
				// already deleted
			}
			else
			{
				deleteBPartner(bpartner);
			}
			return bpartner;
		}

		if (bpartner == null)
		{
			bpartner = new BPartner();
			bpartner.setUuid(syncBpartner.getUuid());
		}

		bpartner.setDeleted(false);
		bpartner.setName(syncBpartner.getName());
		bpartnersRepo.save(bpartner);
		logger.debug("Imported: {} -> {}", syncBpartner, bpartner);

		return bpartner;
	}

	private void deleteBPartner(final BPartner bpartner)
	{
		if (bpartner.isDeleted())
		{
			return;
		}
		bpartner.setDeleted(true);
		bpartnersRepo.save(bpartner);

		logger.debug("Deleted: {}", bpartner);
	}
}
