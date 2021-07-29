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

import ch.qos.logback.classic.Level;
import de.metas.RestUtils;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonAttributeInstance;
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.common.rest_api.v2.JsonPurchaseCandidate;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateCreateItem;
import de.metas.common.rest_api.v2.JsonVendor;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.IPurchaseCandidateBL;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidateSource;
import de.metas.quantity.Quantity;
import de.metas.rest_api.utils.RestApiUtilsV2;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.warehouse.WarehouseService;
import de.metas.util.Check;
import de.metas.util.Loggables;
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
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CreatePurchaseCandidatesService
{
	private final static transient Logger logger = LogManager.getLogger(CreatePurchaseCandidatesService.class);

	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IPurchaseCandidateBL purchaseCandidateBL = Services.get(IPurchaseCandidateBL.class);

	private final PurchaseCandidateRepository purchaseCandidateRepo;
	private final JsonRetrieverService jsonRetrieverService;
	private final CurrencyRepository currencyRepository;
	private final ExternalReferenceRestControllerService externalReferenceService;
	private final WarehouseService warehouseService;

	public CreatePurchaseCandidatesService(
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepo,
			@NonNull final JsonServiceFactory jsonServiceFactory,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final ExternalReferenceRestControllerService externalReferenceService,
			@NonNull final WarehouseService warehouseService)
	{
		this.purchaseCandidateRepo = purchaseCandidateRepo;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
		this.currencyRepository = currencyRepository;
		this.externalReferenceService = externalReferenceService;
		this.warehouseService = warehouseService;
	}

	public Optional<JsonPurchaseCandidate> createCandidate(@RequestBody final JsonPurchaseCandidateCreateItem request)
	{
		final Optional<PurchaseCandidateId> alreadyCreatedCandId = retrieveAlreadyCreatedCandId(request);
		if (alreadyCreatedCandId.isPresent())
		{
			Loggables.withLogger(logger, Level.INFO).addLog("C_PurchaseCandidate_ID={} with ExternalHeaderId={} and ExternalLineId={} already exists; -> ignore request",
															alreadyCreatedCandId.get().getRepoId(), request.getExternalHeaderId(), request.getExternalLineId());
			return Optional.empty();
		}

		final PurchaseCandidate purchaseCandidate = toPurchaseCandidate(request);

		final PurchaseCandidateId save = purchaseCandidateRepo.save(purchaseCandidate);
		return Optional.of(JsonPurchaseCandidate.builder()
								   .metasfreshId(JsonMetasfreshId.of(save.getRepoId()))
								   .externalHeaderId(JsonExternalId.of(purchaseCandidate.getExternalHeaderId().getValue()))
								   .externalLineId(JsonExternalId.of(purchaseCandidate.getExternalLineId().getValue()))
								   .externalPurchaseOrderUrl(purchaseCandidate.getExternalPurchaseOrderUrl())
								   .processed(false)
								   .build());
	}

	public PurchaseCandidate toPurchaseCandidate(final JsonPurchaseCandidateCreateItem request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());
		final ProductId productId = getProductByIdentifier(orgId, request.getProductIdentifier());
		final Quantity quantity = RestApiUtilsV2.getQuantity(request);
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

		final AttributeSetInstanceId attributeSetInstanceId = getAttributeSetInstanceId(request.getAttributeSetInstance());

		final BigDecimal enteredPrice = manualPrice ? request.getPrice().getValue() : BigDecimal.ZERO;
		final Percent discountPercent = Percent.of(request.isManualDiscount() ? request.getDiscount() : BigDecimal.ZERO);

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.orgId(orgId)
				.externalHeaderId(ExternalId.of(request.getExternalHeaderId()))
				.externalLineId(ExternalId.of(request.getExternalLineId()))
				.externalPurchaseOrderUrl(request.getExternalPurchaseOrderUrl())
				.productId(productId)
				.warehouseId(warehouseService.getWarehouseByIdentifier(orgId, request.getWarehouseIdentifier()))
				.purchaseDatePromised(datePromised)
				.purchaseDateOrdered(request.getPurchaseDateOrdered())
				.vendorId(vendorId)
				.vendorProductNo(productDAO.retrieveProductValueByProductId(productId))
				.qtyToPurchase(quantity)
				.groupReference(DemandGroupReference.EMPTY)
				.price(enteredPrice)
				.discount(discountPercent)
				.isManualDiscount(request.isManualDiscount())
				.isManualPrice(manualPrice)
				.prepared(request.isPrepared())
				.attributeSetInstanceId(attributeSetInstanceId)
				.source(PurchaseCandidateSource.Api)
				.build();
		purchaseCandidateBL.updateCandidatePricingDiscount(purchaseCandidate);

		if (manualPrice && request.getPrice().getCurrencyCode() != null)
		{
			purchaseCandidate.setCurrencyId(currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getPrice().getCurrencyCode())));
		}
		return purchaseCandidate;
	}

	private Optional<PurchaseCandidateId> retrieveAlreadyCreatedCandId(@NonNull final JsonPurchaseCandidateCreateItem purchaseCandRequest)
	{
		if (Check.isBlank(purchaseCandRequest.getExternalHeaderId())
				|| Check.isBlank(purchaseCandRequest.getExternalLineId()))
		{
			return Optional.empty();
		}

		return purchaseCandidateRepo.getByExternalHeaderAndLineId(purchaseCandRequest.getExternalHeaderId(),
																  purchaseCandRequest.getExternalLineId());

	}

	private AttributeSetInstanceId getAttributeSetInstanceId(final @Nullable JsonAttributeSetInstance attributeSetInstance)
	{
		if (attributeSetInstance == null || Check.isEmpty(attributeSetInstance.getAttributeInstances()))
		{
			return AttributeSetInstanceId.NONE;
		}
		final ImmutableAttributeSet.Builder attributeSetBuilder = ImmutableAttributeSet.builder();
		for (final JsonAttributeInstance attributeValue : attributeSetInstance.getAttributeInstances())
		{
			attributeSetBuilder.attributeValue(
					AttributeCode.ofString(attributeValue.getAttributeCode()),
					CoalesceUtil.coalesce(attributeValue.getValueStr(), attributeValue.getValueDate(), attributeValue.getValueNumber()));
		}
		return AttributeSetInstanceId.ofRepoId(attributeSetInstanceBL.createASIFromAttributeSet(attributeSetBuilder.build()).getM_AttributeSetInstance_ID());
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
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);

		final BPartnerId bPartnerId;
		try
		{
			bPartnerId = jsonRetrieverService.resolveBPartnerExternalIdentifier(bpartnerIdentifier, orgId)
					.orElseThrow(() -> MissingResourceException.builder()
							.resourceName("bpartnerIdentifier")
							.resourceIdentifier(bpartnerIdentifier.getRawValue())
							.parentResource(vendor)
							.build());
		}
		catch (final AdempiereException e)
		{
			throw MissingResourceException.builder()
					.resourceName("vendor.bpartnerIdentifier")
					.resourceIdentifier(bpartnerIdentifier.getRawValue())
					.cause(e)
					.build();
		}

		return bPartnerId;
	}

	@NonNull
	private ProductId getProductByIdentifier(final OrgId orgId,
			@NonNull final String productIdentifier)
	{
		final ExternalIdentifier productExternalIdentifier = ExternalIdentifier.of(productIdentifier);

		switch (productExternalIdentifier.getType())
		{
			case METASFRESH_ID:
				return ProductId.ofRepoId(productExternalIdentifier.asMetasfreshId().getValue());
			case EXTERNAL_REFERENCE:
				return externalReferenceService.resolveExternalReference(orgId, productExternalIdentifier, ProductExternalReferenceType.PRODUCT)
						.map(metasfreshId -> ProductId.ofRepoId(metasfreshId.getValue()))
						.orElseThrow(() -> MissingResourceException.builder()
								.resourceName("productIdentifier")
								.resourceIdentifier(productExternalIdentifier.getRawValue())
								.build());
			case VALUE:
				final IProductDAO.ProductQuery query = IProductDAO.ProductQuery.builder()
						.orgId(orgId)
						.value(productExternalIdentifier.asValue()).build();
				final ProductId productId = productDAO.retrieveProductIdBy(query);
				if (productId == null)
				{
					throw MissingResourceException.builder()
							.resourceName("productIdentifier")
							.resourceIdentifier(productExternalIdentifier.getRawValue())
							.build();
				}
				return productId;
			default:
				throw new InvalidIdentifierException(productExternalIdentifier.getRawValue());
		}
	}
}
