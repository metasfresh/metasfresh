package de.metas.vertical.pharma.vendor.gateway.msv3;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.order.LocalPurchaseOrderForRemoteOrderCreated;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem;
import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfigId;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfigRepository;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.Version;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.MSV3PurchaseOrderClient;
import lombok.NonNull;

/*
 * #%L
 * de.metas.vendor.gateway.msv3
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

@Service
public class MSV3VendorGatewayService implements VendorGatewayService
{
	private final MSV3ClientConfigRepository configRepo;
	private final ImmutableMap<String, MSV3ClientFactory> clientFactoriesByVersion;

	public MSV3VendorGatewayService(
			@NonNull final MSV3ClientConfigRepository configRepo,
			@NonNull final List<MSV3ClientFactory> clientFactories)
	{
		this.configRepo = configRepo;
		clientFactoriesByVersion = Maps.uniqueIndex(clientFactories, MSV3ClientFactory::getVersionId);
	}

	private MSV3ClientFactory getClientFactory(final Version version)
	{
		final MSV3ClientFactory clientFactory = clientFactoriesByVersion.get(version.getId());
		if (clientFactory == null)
		{
			throw new AdempiereException("No MSV3 client factory found for version " + version + "."
					+ " Available factories are: " + clientFactoriesByVersion);
		}
		return clientFactory;
	}

	@Override
	public String testConnection(final int configRepoId)
	{
		final MSV3ClientConfigId configId = MSV3ClientConfigId.ofRepoId(configRepoId);
		final MSV3ClientConfig config = configRepo.getById(configId);
		return getClientFactory(config.getVersion())
				.newTestConnectionClient(config)
				.testConnection();
	}

	@Override
	public boolean isProvidedForVendor(final int vendorRepoId)
	{
		final BPartnerId vendorId = BPartnerId.ofRepoId(vendorRepoId);
		return configRepo.hasConfigForVendor(vendorId);
	}

	@Override
	public AvailabilityResponse retrieveAvailability(@NonNull final AvailabilityRequest request)
	{
		final BPartnerId vendorId = BPartnerId.ofRepoId(request.getVendorId());
		final MSV3ClientConfig config = configRepo.getByVendorId(vendorId);
		final MSV3AvailiabilityClient client = getClientFactory(config.getVersion())
				.newAvailabilityClient(config);

		return client.retrieveAvailability(request);
	}

	@Override
	public RemotePurchaseOrderCreated placePurchaseOrder(@NonNull final PurchaseOrderRequest request)
	{
		final BPartnerId vendorId = BPartnerId.ofRepoId(request.getVendorId());
		final MSV3ClientConfig config = configRepo.getByVendorId(vendorId);
		final MSV3PurchaseOrderClient client = getClientFactory(config.getVersion())
				.newPurchaseOrderClient(config);

		return client.prepare(request).placeOrder();
	}

	@Override
	public void associateLocalWithRemotePurchaseOrderId(
			@NonNull final LocalPurchaseOrderForRemoteOrderCreated localPurchaseOrderForRemoteOrderCreated)
	{
		final RemotePurchaseOrderCreatedItem item = localPurchaseOrderForRemoteOrderCreated.getRemotePurchaseOrderCreatedItem();

		final I_MSV3_BestellungAnteil anteilRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MSV3_BestellungAnteil.class)
				.addEqualsFilter(I_MSV3_BestellungAnteil.COLUMN_MSV3_BestellungAnteil_ID, item.getInternalItemId())
				.create()
				.firstOnlyNotNull(I_MSV3_BestellungAnteil.class);

		anteilRecord.setC_OrderLinePO_ID(localPurchaseOrderForRemoteOrderCreated.getPurchaseOrderLineId());
		save(anteilRecord);

		final I_MSV3_BestellungAntwortAuftrag bestellungAntwortAuftrag = anteilRecord
				.getMSV3_BestellungAntwortPosition()
				.getMSV3_BestellungAntwortAuftrag();
		bestellungAntwortAuftrag.setC_OrderPO_ID(localPurchaseOrderForRemoteOrderCreated.getPurchaseOrderId());
		save(bestellungAntwortAuftrag);
	}
}
