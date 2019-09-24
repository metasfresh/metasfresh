package de.metas.contracts.process;

import java.sql.Timestamp;

import org.compiere.Adempiere;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.impl.CustomerRetentionRepository;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import de.metas.util.time.SystemTime;

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

	private final CustomerRetentionRepository customerRetentionRepo = Adempiere.getBean(CustomerRetentionRepository.class);

	final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<BPartnerId> bPartnerIdsWithCustomerRetention = customerRetentionRepo.retrieveBPartnerIdsWithCustomerRetention();

		for (final BPartnerId bpartnerId : bPartnerIdsWithCustomerRetention)
		{
			final I_C_Flatrate_Term latestFlatrateTermForBPartnerId = contractsDAO.retrieveLatestFlatrateTermForBPartnerId(bpartnerId);

			if (Check.isEmpty(latestFlatrateTermForBPartnerId))
			{
				continue;
			}

			final Timestamp endDate = CoalesceUtil.coalesce(latestFlatrateTermForBPartnerId.getMasterEndDate(), latestFlatrateTermForBPartnerId.getEndDate());

			if (customerRetentionRepo.dateExceedsThreshold(endDate, SystemTime.asTimestamp()))
			{
				customerRetentionRepo.setNonSubscriptionCustomer(bpartnerId);
			}
		}

		return MSG_OK;
	}

}
