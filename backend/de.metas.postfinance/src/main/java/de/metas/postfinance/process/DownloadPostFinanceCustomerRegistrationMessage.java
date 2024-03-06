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

package de.metas.postfinance.process;

import de.metas.postfinance.B2BServiceWrapper;
import de.metas.postfinance.CustomerRegistrationMessageService;
import de.metas.postfinance.generated.DownloadFile;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

import java.util.List;

public class DownloadPostFinanceCustomerRegistrationMessage extends JavaProcess
{
	private final B2BServiceWrapper b2BServiceWrapper = SpringContextHolder.instance.getBean(B2BServiceWrapper.class);
	private final CustomerRegistrationMessageService customerRegistrationMessageService = SpringContextHolder.instance.getBean(CustomerRegistrationMessageService.class);

	@Override
	protected String doIt()
	{
		final List<DownloadFile> customerRegistrationMessageFiles = b2BServiceWrapper.getCustomerRegistrationMessageFiles(getOrgId());
		customerRegistrationMessageService.processCustomerRegistrationMessages(customerRegistrationMessageFiles);

		return MSG_OK;
	}
}
