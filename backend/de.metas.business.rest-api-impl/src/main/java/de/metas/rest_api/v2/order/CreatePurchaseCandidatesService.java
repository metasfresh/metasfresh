/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.order;

import de.metas.RestUtils;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonExternalId;
import de.metas.common.rest_api.v2.JsonRequestAttributeInstance;
import de.metas.common.rest_api.v2.JsonRequestAttributeSetInstance;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidateSource;
import de.metas.purchasecandidate.purchaseordercreation.PurchaseOrderPriceCalculator;
import de.metas.purchasecandidate.purchaseordercreation.PurchaseOrderPricingInfo;
import de.metas.quantity.Quantity;
import de.metas.rest_api.order.JsonPurchaseCandidate;
import de.metas.rest_api.order.JsonPurchaseCandidateCreateItem;
import de.metas.rest_api.order.JsonVendor;
import de.metas.rest_api.utils.BPartnerCompositeLookupKey;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.RestApiUtils;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
public class CreatePurchaseCandidatesService
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	private final PurchaseCandidateRepository purchaseCandidateRepo;
	private final BPartnerQueryService bPartnerQueryService;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;
	private final CurrencyRepository currencyRepository;

	public CreatePurchaseCandidatesService(
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepo,
			@NonNull final BPartnerQueryService bPartnerQueryService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final CurrencyRepository currencyRepository)
	{
		this.purchaseCandidateRepo = purchaseCandidateRepo;
		this.bPartnerQueryService = bPartnerQueryService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.currencyRepository = currencyRepository;
	}

	public JsonPurchaseCandidate createCandidate(@RequestBody final JsonPurchaseCandidateCreateItem request)
	{
		final PurchaseCandidate purchaseCandidate = toPurchaseCandidate(request);

		final PurchaseCandidateId save = purchaseCandidateRepo.save(purchaseCandidate);
		return JsonPurchaseCandidate.builder()
				.metasfreshId(JsonMetasfreshId.of(save.getRepoId()))
				.externalHeaderId(JsonExternalId.of(purchaseCandidate.getExternalHeaderId().getValue()))
				.externalLineId(JsonExternalId.of(purchaseCandidate.getExternalLineId().getValue()))
				.processed(false)
				.build();
	}

	public PurchaseCandidate toPurchaseCandidate(final JsonPurchaseCandidateCreateItem request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());
		final ProductId productId = getProductByIdentifier(orgId, request.getProductIdentifier());
		final Quantity quantity = RestApiUtils.getQuantity(request);
		final BPartnerId vendorId = getBPartnerId(orgId, request.getVendor());
		final ZonedDateTime datePromised = getOrDefaultDatePromised(request.getPurchaseDatePromised(), orgId);
		if (request.isManualDiscount() && request.getDiscount() == null)
		{
			throw new MissingPropertyException("discount", request);
		}
		final boolean manualPrice = request.isManualPrice();
		if (manualPrice && request.getPrice() == null)
		{
			throw new MissingPropertyException("price", request);
		}

		final PurchaseOrderPricingInfo pricingInfo = PurchaseOrderPricingInfo.builder()
				.productId(productId)
				.orgId(orgId)
				.quantity(quantity)
				.bpartnerId(vendorId)
				.datePromised(datePromised)
				.countryId(bpartnerDAO.getDefaultShipToLocationCountryIdOrNull(vendorId))
				.build();
		final IPricingResult priceAndDiscount = getPriceAndDiscount(pricingInfo);

		final AttributeSetInstanceId attributeSetInstanceId = getAttributeSetInstanceId(request.getAttributeSetInstance());

		final BigDecimal enteredPrice = manualPrice ? request.getPrice().getValue() : priceAndDiscount.getPriceStd();
		final Percent discountPercent = request.isManualDiscount() ? Percent.of(request.getDiscount()) : priceAndDiscount.getDiscount();

		final BigDecimal priceActual = discountPercent.subtractFromBase(enteredPrice, priceAndDiscount.getPrecision().toInt());
		CurrencyId currencyId = priceAndDiscount.getCurrencyId();
		if (manualPrice && request.getPrice().getCurrencyCode() != null)
		{

			currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getPrice().getCurrencyCode()));
		}

		return PurchaseCandidate.builder()
				.orgId(orgId)
				.externalHeaderId(ExternalId.of(request.getExternalHeaderId()))
				.externalLineId(ExternalId.of(request.getExternalLineId()))
				.productId(productId)
				.warehouseId(getWarehouseByIdentifier(orgId, request.getWarehouseIdentifier()))
				.purchaseDatePromised(datePromised)
				.purchaseDateOrdered(request.getPurchaseDateOrdered())
				.vendorId(vendorId)
				.vendorProductNo(productDAO.retrieveProductValueByProductId(productId))
				.qtyToPurchase(quantity)
				.groupReference(DemandGroupReference.EMPTY)
				.price(enteredPrice)
				.priceInternal(priceAndDiscount.getPriceStd())
				.priceEnteredEff(enteredPrice)
				.discount(discountPercent)
				.discountInternal(priceAndDiscount.getDiscount())
				.discountEff(discountPercent)
				.priceActual(priceActual)
				.isTaxIncluded(priceAndDiscount.isTaxIncluded())
				.isManualDiscount(request.isManualDiscount())
				.isManualPrice(manualPrice)
				.taxCategoryId(priceAndDiscount.getTaxCategoryId())
				.attributeSetInstanceId(attributeSetInstanceId)
				.source(PurchaseCandidateSource.Api)
				.currencyId(currencyId)
				.build();
	}

	private AttributeSetInstanceId getAttributeSetInstanceId(final @Nullable JsonRequestAttributeSetInstance attributeSetInstance)
	{
		if (attributeSetInstance == null || Check.isEmpty(attributeSetInstance.getAttributeInstances()))
		{
			return AttributeSetInstanceId.NONE;
		}
		final ImmutableAttributeSet.Builder attributeSetBuilder = ImmutableAttributeSet.builder();
		for (final JsonRequestAttributeInstance attributeValue : attributeSetInstance.getAttributeInstances())
		{
			attributeSetBuilder.attributeValue(
					AttributeCode.ofString(attributeValue.getAttributeCode()),
					CoalesceUtil.coalesce(attributeValue.getValueStr(), attributeValue.getValueDate(), attributeValue.getValueNumber()));
		}
		return AttributeSetInstanceId.ofRepoId(attributeSetInstanceBL.createASIFromAttributeSet(attributeSetBuilder.build()).getM_AttributeSetInstance_ID());
	}

	private IPricingResult getPriceAndDiscount(final PurchaseOrderPricingInfo pricingInfo)
	{
		return PurchaseOrderPriceCalculator.builder()
				.pricingBL(pricingBL)
				.pricingInfo(pricingInfo)
				.build()
				.calculatePrice();
	}

	private ZonedDateTime getOrDefaultDatePromised(final @Nullable ZonedDateTime purchaseDatePromised, final OrgId orgId)
	{
		return purchaseDatePromised != null ?
				purchaseDatePromised :
				SystemTime.asZonedDateTime(orgDAO.getTimeZone(orgId));
	}

	@NonNull
	private BPartnerId getBPartnerId(final OrgId orgId,
			@NonNull final JsonVendor vendor)
	{
		final String bpartnerIdentifierStr = vendor.getBpartnerIdentifier();
		if (Check.isBlank(bpartnerIdentifierStr))
		{
			throw new MissingPropertyException("vendor.bpartnerIdentifier", vendor);
		}
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		final BPartnerCompositeLookupKey bpartnerIdLookupKey = BPartnerCompositeLookupKey.ofIdentifierString(bpartnerIdentifier);
		final BPartnerQuery query = bPartnerQueryService.createQueryFailIfNotExists(bpartnerIdLookupKey, orgId);
		final BPartnerComposite bPartnerComposite;
		try
		{
			bPartnerComposite = bpartnerCompositeRepository.getSingleByQuery(query).orElseGet(() -> {
				throw MissingResourceException.builder()
						.resourceName("bpartnerIdentifier")
						.resourceIdentifier(bpartnerIdentifier.getRawIdentifierString())
						.parentResource(vendor)
						.build();
			});
		}
		catch (final AdempiereException e)
		{
			throw MissingResourceException.builder()
					.resourceName("vendor.bpartnerIdentifier")
					.resourceIdentifier(bpartnerIdentifier.toJson())
					.cause(e)
					.build();
		}
		final BPartner bpartner = bPartnerComposite.getBpartner();
		return bpartner.getId();

	}

	@NonNull
	private ProductId getProductByIdentifier(final OrgId orgId,
			@NonNull final String productIdentifier)
	{
		final IdentifierString productString = IdentifierString.of(productIdentifier);
		final ProductId result;
		if (productString.getType().equals(IdentifierString.Type.METASFRESH_ID))
		{
			result = ProductId.ofRepoId(productString.asMetasfreshId().getValue());
		}
		else
		{
			final IProductDAO.ProductQuery.ProductQueryBuilder builder = IProductDAO.ProductQuery.builder()
					.orgId(orgId);

			if (productString.getType().equals(IdentifierString.Type.EXTERNAL_ID))
			{
				builder.externalId(productString.asExternalId());
			}
			else if (productString.getType().equals(IdentifierString.Type.VALUE))
			{
				builder.value(productString.asValue());
			}
			else
			{
				throw new InvalidIdentifierException(productIdentifier);
			}
			result = productDAO.retrieveProductIdBy(builder.build());
		}
		if (result == null)
		{
			throw new InvalidIdentifierException(productIdentifier);
		}

		return result;
	}

	@NonNull
	private WarehouseId getWarehouseByIdentifier(final OrgId orgId,
			@NonNull final String warehouseIdentifier)
	{
		final IdentifierString warehouseString = IdentifierString.of(warehouseIdentifier);
		final IWarehouseDAO.WarehouseQuery.WarehouseQueryBuilder builder = IWarehouseDAO.WarehouseQuery.builder().orgId(orgId);
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

		final WarehouseId result;
		if (warehouseString.getType().equals(IdentifierString.Type.METASFRESH_ID))
		{
			result = WarehouseId.ofRepoId(warehouseString.asMetasfreshId().getValue());
		}
		else
		{
			if (warehouseString.getType().equals(IdentifierString.Type.EXTERNAL_ID))
			{
				builder.externalId(warehouseString.asExternalId());
			}
			else if (warehouseString.getType().equals(IdentifierString.Type.VALUE))
			{
				builder.value(warehouseString.asValue());
			}
			else
			{
				throw new InvalidIdentifierException(warehouseIdentifier);
			}
			result = warehouseDAO.retrieveWarehouseIdBy(builder.build());
		}
		if (result == null)
		{
			throw new InvalidIdentifierException(warehouseIdentifier);
		}

		return result;
	}

}
