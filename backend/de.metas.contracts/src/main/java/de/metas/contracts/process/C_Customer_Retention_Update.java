package de.metas.contracts.process;

import org.compiere.SpringContextHolder;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.impl.CustomerRetentionRepository;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

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

public class C_Customer_Retention_Update extends JavaProcess
{

	private final CustomerRetentionRepository customerRetentionRepo = SpringContextHolder.instance.getBean(CustomerRetentionRepository.class);

	final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<BPartnerId> bPartnerIdsWithCustomerRetention = customerRetentionRepo.retrieveBPartnerIdsWithCustomerRetention();

		for (final BPartnerId bpartnerId : bPartnerIdsWithCustomerRetention)
		{
			customerRetentionRepo.updateCustomerRetention(bpartnerId);
		}

		return MSG_OK;
	}

}
