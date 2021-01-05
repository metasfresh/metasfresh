package de.metas.procurement.webui;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Contracts;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.IContractsService;
import de.metas.procurement.webui.service.ISendService;
import de.metas.procurement.webui.service.impl.SendService;
import de.metas.procurement.webui.ui.model.ProductQtyReportRepository;
import de.metas.procurement.webui.ui.model.RfqHeaderContainer;

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
	public static final Builder builder()
	{
		return new Builder();
	}

	private final User user;
	private final String bpartner_uuid;

	private final Contracts contracts;
	private final ProductQtyReportRepository productQtyReportRepository;
	private RfqHeaderContainer _activeRfqsContainer; // lazy

	private final ISendService sendService = new SendService();

	private MFSession(final Builder builder)
	{
		super();
		Application.autowire(this);

		user = builder.getUser();
		bpartner_uuid = user.getBpartner().getUuid();

		final BPartner bpartner = user.getBpartner();
		contracts = builder.getContractsRepository().getContracts(bpartner);

		productQtyReportRepository = new ProductQtyReportRepository(user, contracts);
	}

	/**
	 * @return current logged user; never returns null
	 */
	public User getUser()
	{
		return user;
	}

	public String getBpartner_uuid()
	{
		return bpartner_uuid;
	}

	public Contracts getContracts()
	{
		return contracts;
	}

	public ProductQtyReportRepository getProductQtyReportRepository()
	{
		return productQtyReportRepository;
	}

	public RfqHeaderContainer getActiveRfqs()
	{
		if (_activeRfqsContainer == null)
		{
			synchronized (this)
			{
				if (_activeRfqsContainer == null)
				{
					final RfqHeaderContainer activeRfqsContainer = new RfqHeaderContainer(user);
					activeRfqsContainer.loadAll();
					_activeRfqsContainer = activeRfqsContainer;
				}
			}
		}
		return _activeRfqsContainer;
	}

	public ISendService getSendService()
	{
		return sendService;
	}

	public static final class Builder
	{
		private User user;
		private IContractsService contractsRepository;

		private Builder()
		{
			super();
		}

		public MFSession build()
		{
			return new MFSession(this);
		}

		public Builder setUser(final User user)
		{
			this.user = user;
			return this;
		}

		private User getUser()
		{
			Preconditions.checkNotNull(user, "user is null");
			return user;
		}

		public Builder setContractsRepository(final IContractsService contractsRepository)
		{
			this.contractsRepository = contractsRepository;
			return this;
		}

		public IContractsService getContractsRepository()
		{
			Preconditions.checkNotNull(contractsRepository, "contractsRepository is null");
			return contractsRepository;
		}

	}
}
