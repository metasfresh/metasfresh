package de.metas.ui.web.order.products_proposal.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.GetOrdersQuery;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
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

@Service
public class OrderProductProposalsService
{
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IHUPIItemProductBL packingMaterialsService = Services.get(IHUPIItemProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	private final IBPartnerBL bpartnersService = Services.get(IBPartnerBL.class);

	private final CurrencyRepository currencyRepo;
	private final MoneyService moneyService;

	public OrderProductProposalsService(
			@NonNull final CurrencyRepository currencyRepo,
			@NonNull final MoneyService moneyService)
	{
		this.currencyRepo = currencyRepo;
		this.moneyService = moneyService;
	}

	@NonNull
	public ProductPrice getQuotationPrice(
			@NonNull final Order quotation,
			@NonNull final ProductId productId,
			@NonNull final CurrencyCode currencyCode)
	{
		final OrderLine orderLine = quotation.getFirstMatchingOrderLine(productId);

		final Money quotationPriceEntered = Money.of(orderLine.getPriceEntered(), orderLine.getCurrencyId());

		final Money priceEnteredInPriceListCurrency = moneyService
				.convertMoneyToCurrency(quotationPriceEntered, currencyCode, getCurrencyConversionContext(quotation.getClientAndOrgId()));

		return ProductPrice.builder()
				.productId(productId)
				.money(priceEnteredInPriceListCurrency)
				.uomId(Optional.ofNullable(orderLine.getPriceUomId()).orElse(orderLine.getUomId()))
				.build();
	}

	@NonNull
	public Order getOrderById(@NonNull final OrderId orderId)
	{
		final I_C_Order orderRecord = ordersRepo.getById(orderId);

		return toOrder(orderRecord);
	}

	@NonNull
	public List<Order> getOrdersByQuery(
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Set<ProductId> productIdSet)
	{
		final DocTypeId quotationDocTypeId = getQuotationDocTypeId(clientAndOrgId);

		return ordersRepo.getOrdersByQuery(buildOrderQuery(bPartnerId, productIdSet, quotationDocTypeId))
				.stream()
				.map(this::toOrder)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Optional<Order> getLastQuotation(
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final BPartnerId bPartnerId,
			@NonNull final ProductId productId)
	{
		final DocTypeId quotationDocTypeId = getQuotationDocTypeId(clientAndOrgId);

		return retrieveFirstByQuery(buildOrderQuery(bPartnerId, ImmutableSet.of(productId), quotationDocTypeId));
	}

	@NonNull
	public DocTypeId getQuotationDocTypeId(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(DocBaseType.SalesOrder)
				.docSubType(X_C_DocType.DOCSUBTYPE_Proposal)
				.adClientId(clientAndOrgId.getClientId().getRepoId())
				.adOrgId(clientAndOrgId.getOrgId().getRepoId())
				.build();

		return docTypeDAO.getDocTypeId(docTypeQuery);
	}

	@NonNull
	private Order toOrder(@NonNull final I_C_Order orderRecord)
	{
		final OrderId orderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());

		final OrgId orgId = OrgId.ofRepoIdOrAny(orderRecord.getAD_Org_ID());
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final ZonedDateTime datePromised = TimeUtil.asZonedDateTime(orderRecord.getDatePromised(), zoneId);
		final ZonedDateTime dateOrdered = TimeUtil.asZonedDateTime(orderRecord.getDateOrdered(), zoneId);
		final PriceListId priceListId = PriceListId.ofRepoId(orderRecord.getM_PriceList_ID());
		final I_M_PriceList priceList = priceListsRepo.getById(priceListId);
		final PriceListVersionId priceListVersionId = priceListsRepo.retrievePriceListVersionId(priceListId, datePromised);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());
		final String bpartnerName = bpartnersService.getBPartnerName(bpartnerId);

		return Order.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(orderRecord.getAD_Client_ID(), orderRecord.getAD_Org_ID()))
				.orderId(orderId)
				.soTrx(SOTrx.ofBoolean(orderRecord.isSOTrx()))
				.datePromised(datePromised)
				.dateOrdered(dateOrdered)
				.bpartnerId(bpartnerId)
				.bpartnerName(bpartnerName)
				.pricingSystemId(priceListsRepo.getPricingSystemId(priceListId))
				.priceListId(priceListId)
				.priceListVersionId(priceListVersionId)
				.countryId(CountryId.ofRepoIdOrNull(priceList.getC_Country_ID()))
				.currency(currencyRepo.getById(priceList.getC_Currency_ID()))
				.incoTermsId(orderRecord.getC_Incoterms_ID())
				.refOrderId(OrderId.ofRepoIdOrNull(orderRecord.getRef_Order_ID()))
				.lines(ordersRepo.retrieveOrderLines(orderId, I_C_OrderLine.class)
							   .stream()
							   .map(this::toOrderLine)
							   .collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderLine toOrderLine(final I_C_OrderLine record)
	{
		final HUPIItemProductId packingMaterialId = HUPIItemProductId.ofRepoIdOrNone(record.getM_HU_PI_Item_Product_ID());

		return OrderLine.builder()
				.orderLineId(OrderLineId.ofRepoId(record.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.packingMaterialId(packingMaterialId)
				.packingMaterialWithInfiniteCapacity(packingMaterialsService.isInfiniteCapacity(packingMaterialId))
				.priceActual(record.getPriceActual())
				.priceEntered(record.getPriceEntered())
				.qtyEnteredCU(record.getQtyEntered())
				.qtyEnteredTU(record.getQtyEnteredTU().intValue())
				.currencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()))
				.uomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.priceUomId(UomId.ofRepoIdOrNull(record.getPrice_UOM_ID()))
				.description(record.getDescription())
				.build();
	}

	@NonNull
	private Optional<Order> retrieveFirstByQuery(@NonNull final GetOrdersQuery query)
	{
		return ordersRepo.retrieveFirstByQuery(query)
				.map(this::toOrder);
	}

	@NonNull
	private CurrencyConversionContext getCurrencyConversionContext(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final Instant conversionDate = Instant.now();
		return currencyConversionBL.createCurrencyConversionContext(conversionDate,
																	clientAndOrgId.getClientId(),
																	clientAndOrgId.getOrgId());
	}

	@NonNull
	private static GetOrdersQuery buildOrderQuery(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Set<ProductId> productIds,
			@NonNull final DocTypeId quotationDocTypeId)
	{
		return GetOrdersQuery.builder()
				.docTypeTargetId(quotationDocTypeId)
				.bPartnerId(bPartnerId)
				.productIds(productIds)
				.docStatus(DocStatus.Completed)
				.descSortByDateOrdered(true)
				.build();
	}
}
