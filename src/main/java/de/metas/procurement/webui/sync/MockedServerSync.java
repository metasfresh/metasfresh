package de.metas.procurement.webui.sync;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import de.metas.procurement.sync.IServerSync;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyRequest;
import de.metas.procurement.webui.sync.annotation.NoCxfServerBind;
import de.metas.procurement.webui.util.DummyDataProducer;

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

@NoCxfServerBind
public class MockedServerSync implements IServerSync
{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required=true)
	@Lazy
	private DummyDataProducer dummyDataProducer;

	@Override
	public List<SyncBPartner> getAllBPartners()
	{
		return dummyDataProducer.getSyncBPartnersRequest().getBpartners();
	}

	@Override
	public List<SyncProduct> getAllProducts()
	{
		return dummyDataProducer.getSyncProductsRequest().getProducts();
	}

	@Override
	public void reportProductSupplies(SyncProductSuppliesRequest request)
	{
		logger.info("Got {}", request);
	}

	@Override
	public void reportWeekSupply(SyncWeeklySupplyRequest request)
	{
		logger.info("Got {}", request);
	}

	@Override
	public String getInfoMessage()
	{
		return "";
	}
}
