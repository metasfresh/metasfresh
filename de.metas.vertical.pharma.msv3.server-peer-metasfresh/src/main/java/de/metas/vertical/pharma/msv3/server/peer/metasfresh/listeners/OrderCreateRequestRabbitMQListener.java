package de.metas.vertical.pharma.msv3.server.peer.metasfresh.listeners;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItem;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;

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
	private OLCandRepository olCandRepo;

	@Value("${msv3server.adClientId:1000000}") // FIXME: hardcoded
	private int adClientId;
	@Value("${msv3server.adOrgId:1000000}") // FIXME: hardcoded
	private int adOrgId;
	@Value("${msv3server.adUserId:100}") // FIXME: hardcoded
	private int adUserId;

	@FunctionalInterface
	private static interface OLCandSupplier
	{
		OLCand getByExternalId(Id id);
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUENAME_CreateOrderRequestEvents)
	public OrderCreateResponse onRequest(final OrderCreateRequest request)
	{
		try (final IAutoCloseable ctxRestorer = Env.switchContext(createContext()))
		{
			final List<OLCand> olCands = olCandRepo.create(toOLCandCreateRequestsList(request));
			final Map<String, OLCand> olCandsByExternalId = Maps.uniqueIndex(olCands, OLCand::getExternalId);

			return toOrderCreateResponse(request, externalId -> olCandsByExternalId.get(externalId.getValueAsString()));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed processing request: {}", request, ex);
			return OrderCreateResponse.error(ex.getLocalizedMessage());
		}
	}

	private Properties createContext()
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClientId);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, adOrgId);
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, adUserId);
		return ctx;
	}

	private List<OLCandCreateRequest> toOLCandCreateRequestsList(final OrderCreateRequest request)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		final String poReference = request.getOrderId().getValueAsString();
		final LocalDate dateRequired = LocalDate.now().plusDays(1); // TODO

		final List<OLCandCreateRequest> olCandRequests = new ArrayList<>();
		for (final OrderCreateRequestPackage orderPackage : request.getOrderPackages())
		{
			for (final OrderCreateRequestPackageItem item : orderPackage.getItems())
			{
				final int productId = productDAO.retrieveProductIdByValue(item.getPzn().getValueAsString());
				final int uomId = productBL.getStockingUOM(productId).getC_UOM_ID();
				final int huPIItemProductId = -1; // TODO fetch it from item.getPackingMaterialId()
				olCandRequests.add(OLCandCreateRequest.builder()
						.externalId(item.getId().getValueAsString())
						//
						.bpartner(toOLCandBPartnerInfo(request.getBpartnerId()))
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

	private OrderCreateResponse toOrderCreateResponse(final OrderCreateRequest request, final OLCandSupplier olCandSupplier)
	{
		return OrderCreateResponse.ok(OrderResponse.builder()
				.bpartnerId(request.getBpartnerId())
				.orderId(request.getOrderId())
				.supportId(request.getSupportId())
				.nightOperation(false)
				.orderPackages(request.getOrderPackages().stream()
						.map(orderPackageRequest -> toOrderResponsePackage(orderPackageRequest, olCandSupplier))
						.collect(ImmutableList.toImmutableList()))
				.build());
	}

	private OrderResponsePackage toOrderResponsePackage(final OrderCreateRequestPackage orderPackageRequest, final OLCandSupplier olCandSupplier)
	{
		return OrderResponsePackage.builder()
				.id(orderPackageRequest.getId())
				.orderType(orderPackageRequest.getOrderType())
				.orderIdentification(orderPackageRequest.getOrderIdentification())
				.supportId(orderPackageRequest.getSupportId())
				.packingMaterialId(orderPackageRequest.getPackingMaterialId())
				.items(orderPackageRequest.getItems().stream()
						.map(itemRequest -> toOrderResponsePackageItem(itemRequest, olCandSupplier))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderResponsePackageItem toOrderResponsePackageItem(final OrderCreateRequestPackageItem itemRequest, final OLCandSupplier olCandSupplier)
	{
		final Id id = itemRequest.getId();

		final OLCand olCand = olCandSupplier.getByExternalId(id);
		if (olCand == null)
		{
			throw new RuntimeException("No OLCand found for ID: " + id);
		}

		return OrderResponsePackageItem.builder()
				.id(id)
				.pzn(itemRequest.getPzn())
				.qty(Quantity.of(olCand.getQty()))
				.deliverySpecifications(itemRequest.getDeliverySpecifications())
				.olCandId(olCand.getId())
				.build();
	}
}
