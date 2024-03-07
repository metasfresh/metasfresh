/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.customerregistration.process;

import de.metas.postfinance.customerregistration.CustomerRegistrationMessageService;
import de.metas.postfinance.customerregistration.repository.CustomerRegistrationMessage;
import de.metas.postfinance.customerregistration.repository.CustomerRegistrationMessageQuery;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.SpringContextHolder;
import de.metas.postfinance.model.I_PostFinance_Customer_Registration_Message;

import java.util.stream.Stream;

public class ProcessPostFinanceCustomerRegistrationMessage extends JavaProcess implements IProcessPrecondition
{
	private final CustomerRegistrationMessageService customerRegistrationMessageService = SpringContextHolder.instance.getBean(CustomerRegistrationMessageService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		customerRegistrationMessageService.processCustomerRegistrationMessages(getSelectedRecords());

		return MSG_OK;
	}

	@NonNull
	private Stream<CustomerRegistrationMessage> getSelectedRecords()
	{
		final IQueryBuilder<I_PostFinance_Customer_Registration_Message> queryBuilder =
				retrieveActiveSelectedRecordsQueryBuilder(I_PostFinance_Customer_Registration_Message.class);

		return customerRegistrationMessageService
				.streamByQuery(CustomerRegistrationMessageQuery.builder()
									   .queryBuilder(queryBuilder)
									   .isBPartnerIdSet(true)
									   .processed(false)
									   .build());
	}
}
