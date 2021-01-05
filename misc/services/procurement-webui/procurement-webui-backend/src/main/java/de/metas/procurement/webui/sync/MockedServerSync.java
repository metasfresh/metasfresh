package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest;
import de.metas.procurement.webui.util.DummyDataProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

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

public class MockedServerSync
{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required=true)
	@Lazy
	private DummyDataProducer dummyDataProducer;

	public List<SyncBPartner> getAllBPartners()
	{
		return dummyDataProducer.getSyncBPartnersRequest().getBpartners();
	}

	public List<SyncProduct> getAllProducts()
	{
		return dummyDataProducer.getSyncProductsRequest().getProducts();
	}

	public void reportProductSupplies(PutProductSuppliesRequest request)
	{
		logger.info("Got {}", request);
	}

	public void reportWeekSupply(PutWeeklySupplyRequest request)
	{
		logger.info("Got {}", request);
	}

	public String getInfoMessage()
	{
		return "";
	}

	public void reportRfQChanges(PutRfQChangeRequest request)
	{
		logger.info("Got {}", request);
	}
}
