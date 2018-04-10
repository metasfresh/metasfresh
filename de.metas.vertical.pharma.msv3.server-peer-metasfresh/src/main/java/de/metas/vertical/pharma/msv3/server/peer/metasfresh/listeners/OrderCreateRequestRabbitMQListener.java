package de.metas.vertical.pharma.msv3.server.peer.metasfresh.listeners;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.util.web.security.UserAuthTokenService;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncRequest;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncRequestPackage;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncRequestPackageItem;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncResponse;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncResponseItem;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3PeerAuthToken;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
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

@Component
@Profile(Profiles.PROFILE_App)
public class OrderCreateRequestRabbitMQListener
{
	public static final String DATA_SOURCE_INTERNAL_NAME = "SOURCE.MSV3-server";

	private static final Logger logger = LogManager.getLogger(OrderCreateRequestRabbitMQListener.class);

	@Autowired
	private UserAuthTokenService authService;
	@Autowired
	private OLCandRepository olCandRepo;
	@Autowired
	private MSV3ServerPeerService serverPeerService;

	@FunctionalInterface
	private static interface OLCandSupplier
	{
		OLCand getByExternalId(Id id);
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUENAME_SyncOrderRequestEvents)
	public void onMessage(@Payload final MSV3OrderSyncRequest request, @Header(MSV3PeerAuthToken.NAME) final MSV3PeerAuthToken authToken)
	{
		try
		{
			final MSV3OrderSyncResponse response = authService.call(authToken::getValueAsString, () -> process(request));
			serverPeerService.publishSyncOrderResponse(response);
		}
		catch (final Exception ex)
		{
			logger.error("Failed processing: {}", request, ex);
		}
	}

	private MSV3OrderSyncResponse process(final MSV3OrderSyncRequest request)
	{
		try
		{
			final List<OLCand> olCands = olCandRepo.create(toOLCandCreateRequestsList(request));

			return MSV3OrderSyncResponse.ok(request.getOrderId(), request.getBpartner())
					.items(olCands.stream()
							.map(olCand -> MSV3OrderSyncResponseItem.builder()
									.olCandId(olCand.getId())
									.orderPackageItemId(Id.of(olCand.getExternalId()))
									.build())
							.collect(ImmutableList.toImmutableList()))
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed processing request: {}. Sending response.", request, ex);
			return MSV3OrderSyncResponse.error(request.getOrderId(), request.getBpartner(), ex);
		}
	}

	private List<OLCandCreateRequest> toOLCandCreateRequestsList(final MSV3OrderSyncRequest request)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final IProductBL productBL = Services.get(IProductBL.class);

//		final OrderResponse order = request.getOrder();
		final String poReference = request.getOrderId().getValueAsString();
		final LocalDate dateRequired = LocalDate.now().plusDays(1); // TODO

		final List<OLCandCreateRequest> olCandRequests = new ArrayList<>();
		for (final MSV3OrderSyncRequestPackage orderPackage : request.getOrderPackages())
		{
			for (final MSV3OrderSyncRequestPackageItem item : orderPackage.getItems())
			{
				final int productId = productDAO.retrieveProductIdByValue(item.getPzn().getValueAsString());
				final int uomId = productBL.getStockingUOM(productId).getC_UOM_ID();
				final int huPIItemProductId = -1; // TODO fetch it from item.getPackingMaterialId()
				olCandRequests.add(OLCandCreateRequest.builder()
						.externalId(item.getId().getValueAsString())
						//
						.bpartner(toOLCandBPartnerInfo(request.getBpartner()))
						.poReference(poReference)
						//
						.dateRequired(dateRequired)
						//
						.productId(productId)
						.qty(item.getQty().getValueAsBigDecimal())
						.uomId(uomId)
						.huPIItemProductId(huPIItemProductId)
						.adInputDataSourceInternalName(DATA_SOURCE_INTERNAL_NAME)
						.build());
			}
		}

		return olCandRequests;
	}

	private static OLCandBPartnerInfo toOLCandBPartnerInfo(final BPartnerId bpartnerId)
	{
		if (bpartnerId == null)
		{
			return null;
		}

		return OLCandBPartnerInfo.builder()
				.bpartnerId(bpartnerId.getBpartnerId())
				.bpartnerLocationId(bpartnerId.getBpartnerLocationId())
				.build();
	}
}
