package de.metas.procurement.webui.service.impl;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Contract;
import de.metas.procurement.webui.model.Contracts;
import de.metas.procurement.webui.repository.ContractRepository;
import de.metas.procurement.webui.service.IContractsService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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
public class ContractsService implements IContractsService
{
	private final ContractRepository contractRepository;

	public ContractsService(@NonNull final ContractRepository contractRepository)
	{
		this.contractRepository = contractRepository;
	}

	@Override
	public Contracts getContracts(@NonNull final BPartner bpartner)
	{
		final List<Contract> contracts = contractRepository.findByBpartnerAndDeletedFalse(bpartner);
		return new Contracts(bpartner, contracts);
	}
}
