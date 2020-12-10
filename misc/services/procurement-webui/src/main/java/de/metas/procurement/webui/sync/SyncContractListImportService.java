package de.metas.procurement.webui.sync;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import de.metas.common.procurement.sync.protocol.dto.SyncContract;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Contract;
import de.metas.procurement.webui.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
public class SyncContractListImportService extends AbstractSyncImportService
{
	@Autowired
	@Lazy
	private ContractRepository contractsRepo;

	@Autowired
	@Lazy
	private SyncContractImportService contractsImportService;

	public void importContracts(final BPartner bpartner, final List<SyncContract> syncContracts)
	{
		final Map<String, Contract> contracts = mapByUuid(contractsRepo.findByBpartnerAndDeletedFalse(bpartner));
		final List<Entry<SyncContract, Contract>> contractsToImport = new ArrayList<>();
		for (final SyncContract syncContract : syncContracts)
		{
			// If delete request, skip importing the contract.
			// As a result, if there is a contract for this sync-contract, it will be deleted below.
			if (syncContract.isDeleted())
			{
				continue;
			}

			final Contract contract = contracts.remove(syncContract.getUuid());
			contractsToImport.add(Maps.immutableEntry(syncContract, contract));
		}
		//
		// Delete contracts
		for (final Contract contract : contracts.values())
		{
			try
			{
				contractsImportService.deleteContract(contract);
			}
			catch (Exception ex)
			{
				logger.error("Failed deleting contract {}. Ignored.", contract, ex);
			}
		}
		//
		// Import contracts
		for (final Entry<SyncContract, Contract> e : contractsToImport)
		{
			final SyncContract syncContract = e.getKey();
			final Contract contract = e.getValue();
			try
			{
				contractsImportService.importContract(bpartner, syncContract, contract);
			}
			catch (Exception ex)
			{
				logger.error("Failed importing contract {} for {}. Skipped.", contract, bpartner, ex);
			}
		}
	}
}
