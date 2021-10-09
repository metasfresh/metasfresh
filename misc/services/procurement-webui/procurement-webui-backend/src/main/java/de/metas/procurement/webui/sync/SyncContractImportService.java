package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.SyncContract;
import de.metas.common.procurement.sync.protocol.dto.SyncContractLine;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Contract;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.repository.ContractLineRepository;
import de.metas.procurement.webui.repository.ContractRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
class SyncContractImportService extends AbstractSyncImportService
{
	private final ContractRepository contractsRepo;
	private final ContractLineRepository contractLinesRepo;
	private final SyncProductImportService productsImportService;
	// @Autowired private MFEventBus applicationEventBus;

	public SyncContractImportService(
			@NonNull final ContractRepository contractsRepo,
			@NonNull final ContractLineRepository contractLinesRepo,
			@NonNull final SyncProductImportService productsImportService)
	{
		this.contractsRepo = contractsRepo;
		this.contractLinesRepo = contractLinesRepo;
		this.productsImportService = productsImportService;
	}

	public void importContract(final BPartner bpartner, final SyncContract syncContract, Contract contract)
	{
		contract = importContractNoCascade(bpartner, syncContract, contract);

		//
		// Contract Line
		final List<ContractLine> contractLinesToSave = new ArrayList<>();
		final Map<String, ContractLine> contractLines = mapByUuid(contract.getContractLines());
		for (final SyncContractLine syncContractLine : syncContract.getContractLines())
		{
			final ContractLine contractLine = importContractLineNoSave(contract, syncContractLine, contractLines);
			if (contractLine == null)
			{
				continue;
			}

			contractLinesToSave.add(contractLine);
		}
		//
		// Delete remaining lines
		for (final ContractLine contractLine : contractLines.values())
		{
			deleteContractLine(contractLine);
		}
		//
		// Save created/updated lines
		contractLinesRepo.saveAll(contractLinesToSave);

		// applicationEventBus.post(ContractChangedEvent.of(contract.getBpartner().getUuid(), contract.getId()));

	}

	private Contract importContractNoCascade(final BPartner bpartner, final SyncContract syncContract, Contract contract)
	{
		final String uuid = syncContract.getUuid();
		if (contract != null && !Objects.equals(uuid, contract.getUuid()))
		{
			contract = null;
		}
		if (contract == null)
		{
			contract = new Contract();
			contract.setUuid(uuid);
			contract.setBpartner(bpartner);
		}

		contract.setDeleted(false);
		contract.setDateFrom(syncContract.getDateFrom());
		contract.setDateTo(syncContract.getDateTo());
		contract.setRfq_uuid(syncContract.getRfq_uuid());
		contractsRepo.save(contract);
		logger.debug("Imported: {} -> {}", syncContract, contract);

		return contract;
	}

	@Nullable
	private ContractLine importContractLineNoSave(final Contract contract, final SyncContractLine syncContractLine, final Map<String, ContractLine> contractLines)
	{
		// If delete request, skip importing the contract line.
		// As a result, if there is a contract line for this sync-contract line, it will be deleted below.
		if (syncContractLine.isDeleted())
		{
			return null;
		}

		ContractLine contractLine = contractLines.remove(syncContractLine.getUuid());

		//
		// Product
		final SyncProduct syncProductNoDelete = assertNotDeleteRequest_WarnAndFix(syncContractLine.getProduct(), "importing contract lines");
		Product product = contractLine == null ? null : contractLine.getProduct();
		product = productsImportService.importProduct(syncProductNoDelete, product)
				.orElseThrow(() -> new RuntimeException("Deleted product cannot be used in " + syncContractLine));

		//
		// Contract Line
		if (contractLine == null)
		{
			contractLine = new ContractLine();
			contractLine.setUuid(syncContractLine.getUuid());
			contractLine.setContract(contract);
		}

		contractLine.setDeleted(false);
		contractLine.setProduct(product);
		logger.debug("Imported: {} -> {}", syncContractLine, contractLine);

		return contractLine;
	}

	public void deleteContract(final Contract contract)
	{
		contractsRepo.delete(contract);
		contractsRepo.flush();
		logger.debug("Deleted: {}", contract);
	}

	private void deleteContractLine(final ContractLine contractLine)
	{
		contractLinesRepo.delete(contractLine);
		contractLinesRepo.flush();
		logger.debug("Deleted: {}", contractLine);
	}
}
