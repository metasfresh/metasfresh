package de.metas.pricing.process;

import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.JavaProcess;
import de.metas.user.UserId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class M_PricelistVersion_MutateCustomerPrices extends JavaProcess
{
	final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(getRecord_ID());

		priceListsRepo.mutateCustomerPrices(priceListVersionId, UserId.ofRepoId(getAD_User_ID()));

		return MSG_OK;
	}

}
