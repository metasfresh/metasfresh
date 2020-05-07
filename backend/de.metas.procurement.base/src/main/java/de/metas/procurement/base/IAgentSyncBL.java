package de.metas.procurement.base;

import java.util.List;

import org.adempiere.util.ISingletonService;

import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.procurement.sync.protocol.SyncConfirmation;
import de.metas.procurement.sync.protocol.SyncProductsRequest;

/*
 * #%L
 * de.metas.procurement.base
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
/**
 * Note that we don't have an implementation in metasfresh. Instead, we use the framework, to get a jax-rs client proxy which we then register in {@link org.adempiere.util.Services}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IAgentSyncBL extends IAgentSync, ISingletonService
{
	@Override
	void syncBPartners(final SyncBPartnersRequest request);

	@Override
	void syncProducts(final SyncProductsRequest request);

	@Override
	void confirm(List<SyncConfirmation> syncConfirmations);
}
