package de.metas.contracts.process;

import java.util.Iterator;

import org.compiere.SpringContextHolder;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.bpartner.ContractPartnerRepository;
import de.metas.contracts.impl.CustomerRetentionRepository;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class C_Customer_Retention_CreateUpdate extends JavaProcess
{

	final CustomerRetentionRepository customerRetentionRepo = SpringContextHolder.instance.getBean(CustomerRetentionRepository.class);

	final ContractPartnerRepository contractPartnerRepo = SpringContextHolder.instance.getBean(ContractPartnerRepository.class);

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<BPartnerId> customers = contractPartnerRepo.retrieveAllPartnersWithContracts();

		while(customers.hasNext())
		{
			customerRetentionRepo.createUpdateCustomerRetention(customers.next());
		}

		return MSG_OK;
	}

}
