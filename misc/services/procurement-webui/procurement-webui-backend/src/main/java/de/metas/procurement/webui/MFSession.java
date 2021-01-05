package de.metas.procurement.webui;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Contracts;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.IContractsService;
import lombok.Getter;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class MFSession
{
	@Getter
	private final User user;
	@Getter
	private final String bpartner_uuid;
	@Getter
	private final Contracts contracts;
	// private final ProductQtyReportRepository productQtyReportRepository;
	// private RfqHeaderContainer _activeRfqsContainer; // lazy
	//
	// private final ISendService sendService = new SendService();

	@lombok.Builder
	private MFSession(
			@NonNull final IContractsService contractsRepository,
			@NonNull final User user)
	{
		this.user = user;
		bpartner_uuid = user.getBpartner().getUuid();

		final BPartner bpartner = user.getBpartner();
		contracts = contractsRepository.getContracts(bpartner);

		// productQtyReportRepository = new ProductQtyReportRepository(user, contracts);
	}

	// public ProductQtyReportRepository getProductQtyReportRepository()
	// {
	// 	return productQtyReportRepository;
	// }
	//
	// public RfqHeaderContainer getActiveRfqs()
	// {
	// 	if (_activeRfqsContainer == null)
	// 	{
	// 		synchronized (this)
	// 		{
	// 			if (_activeRfqsContainer == null)
	// 			{
	// 				final RfqHeaderContainer activeRfqsContainer = new RfqHeaderContainer(user);
	// 				activeRfqsContainer.loadAll();
	// 				_activeRfqsContainer = activeRfqsContainer;
	// 			}
	// 		}
	// 	}
	// 	return _activeRfqsContainer;
	// }
	//
	// public ISendService getSendService()
	// {
	// 	return sendService;
	// }
}
