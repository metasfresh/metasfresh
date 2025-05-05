package de.metas.rest_api.v2.ordercandidates.impl;

import de.metas.RestUtils;
import de.metas.auction.AuctionId;
import de.metas.auction.AuctionService;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.ordercandidates.v2.request.JsonSalesPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JSONPaymentRule;
import de.metas.externalreference.ExternalBusinessKey;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalreference.shipper.ShipperExternalReferenceType;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.api.impl.InputDataSourceQuery;
import de.metas.impex.api.impl.InputDataSourceQuery.InputDataSourceQueryBuilder;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.payment.paymentterm.impl.PaymentTermQuery.PaymentTermQueryBuilder;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.ordercandidates.impl.ProductMasterDataProvider.ProductInfo;
import de.metas.sectionCode.SectionCodeId;
import de.metas.sectionCode.SectionCodeService;
import de.metas.security.permissions2.PermissionService;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
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

public final class MasterdataProvider
{
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);

	private final PermissionService permissionService;
	private final BPartnerEndpointAdapter bpartnerEndpointAdapter;
	private final ProductMasterDataProvider productMasterDataProvider;
	private final JsonRetrieverService jsonRetrieverService;
	private final ExternalReferenceRestControllerService externalReferenceService;
	private final SectionCodeService sectionCodeService;
	private final AuctionService auctionService;

	private final Map<String, OrgId> orgIdsByCode = new HashMap<>();

	@Builder
	private MasterdataProvider(
			@NonNull final PermissionService permissionService,
			@NonNull final BpartnerRestController bpartnerRestController,
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final JsonRetrieverService jsonRetrieverService,
			@NonNull final SectionCodeService sectionCodeService,
			@NonNull final AuctionService auctionService)
	{
		this.permissionService = permissionService;
		this.bpartnerEndpointAdapter = new BPartnerEndpointAdapter(bpartnerRestController);
		this.jsonRetrieverService = jsonRetrieverService;
		this.externalReferenceService = externalReferenceRestControllerService;
		this.productMasterDataProvider = new ProductMasterDataProvider(externalReferenceRestControllerService);
		this.sectionCodeService = sectionCodeService;
		this.auctionService = auctionService;
	}

	public void assertCanCreateNewOLCand(final OrgId orgId)
	{
		permissionService.assertCanCreateOrUpdateRecord(orgId, I_C_OLCand.class);
	}

	public PricingSystemId getPricingSystemIdByValue(@Nullable final String pricingSystemCode)
	{
		if (Check.isEmpty(pricingSystemCode, true))
		{
			return null;
		}

		return priceListsRepo.getPricingSystemIdByValue(pricingSystemCode);
	}

	public WarehouseId getWarehouseIdByValue(@NonNull final String warehouseCode)
	{
		return warehousesRepo.getWarehouseIdByValue(warehouseCode);
	}

	@NonNull
	public OrgId getOrgId(@Nullable final String orgCode)
	{
		if (orgCode == null)
		{
			return permissionService.getDefaultOrgId();
		}

		return orgIdsByCode.computeIfAbsent(orgCode, (code) -> RestUtils.retrieveOrgIdOrDefault(orgCode));
	}

	public ZoneId getOrgTimeZone(final OrgId orgId)
	{
		return orgDAO.getTimeZone(orgId);
	}

	@NonNull
	public BPartnerInfo getBPartnerInfoNotNull(
			@NonNull final JsonRequestBPartnerLocationAndContact jsonBPartnerInfo,
			@Nullable final OrgId orgId)
	{
		return getBPartnerInfo(jsonBPartnerInfo, orgId)
				.orElseThrow(() -> new AdempiereException("No BPartner was found for the given identifier!")
						.appendParametersToMessage()
						.setParameter("JsonBPartnerLocationContact", jsonBPartnerInfo));
	}

	public Optional<BPartnerInfo> getBPartnerInfo(
			@Nullable final JsonRequestBPartnerLocationAndContact jsonBPartnerInfo,
			@Nullable final OrgId orgId)
	{
		final String orgCode = orgId != null ? orgDAO.retrieveOrgValue(orgId) : null;
		return bpartnerEndpointAdapter.getBPartnerInfo(jsonBPartnerInfo, orgCode);
	}

	@NonNull
	public JsonResponseBPartner getJsonBPartnerById(@Nullable final String orgCode, @NonNull final BPartnerId bpartnerId)
	{
		return bpartnerEndpointAdapter.getJsonBPartnerById(orgCode, bpartnerId);
	}

	@NonNull
	public JsonResponseLocation getJsonBPartnerLocationById(@Nullable final String orgCode, @NonNull final BPartnerLocationId bpartnerLocationId)
	{
		return bpartnerEndpointAdapter.getJsonBPartnerLocationById(orgCode, bpartnerLocationId);
	}

	@Nullable
	public JsonResponseContact getJsonBPartnerContactById(@Nullable final String orgCode, @Nullable final BPartnerContactId bpartnerContactId)
	{
		if (bpartnerContactId == null)
		{
			return null;
		}
		return bpartnerEndpointAdapter.getJsonBPartnerContactById(orgCode, bpartnerContactId);
	}

	@NonNull
	public JsonResponseBPartner getBPartnerInfoByExternalIdentifier(@NonNull final String bPartnerExternalIdentifier, @Nullable final String orgCode)
	{
		return bpartnerEndpointAdapter.getJsonBPartnerByExternalIdentifier(orgCode, bPartnerExternalIdentifier);
	}

	public ProductInfo getProductInfo(
			@NonNull final ExternalIdentifier productIdentifier,
			@NonNull final OrgId orgId)
	{
		return productMasterDataProvider.getProductInfo(productIdentifier, orgId);
	}

	@Nullable
	public InputDataSourceId getDataSourceId(@Nullable final String dataSourceIdentifier, @NonNull final OrgId orgId)
	{
		if (Check.isEmpty(dataSourceIdentifier, true))
		{
			return null;
		}

		final IInputDataSourceDAO dataSourceDAO = Services.get(IInputDataSourceDAO.class);
		final IdentifierString dataSource = IdentifierString.of(dataSourceIdentifier);

		final InputDataSourceQueryBuilder queryBuilder = InputDataSourceQuery.builder();

		queryBuilder.orgId(orgId);

		switch (dataSource.getType())
		{
			case INTERNALNAME:
				queryBuilder.internalName(dataSource.asInternalName());
				break;
			case EXTERNAL_ID:
				queryBuilder.externalId(dataSource.asExternalId());
				break;
			case METASFRESH_ID:
				queryBuilder.inputDataSourceId(InputDataSourceId.ofRepoIdOrNull(dataSource.asMetasfreshId().getValue()));
				break;
			case VALUE:
				queryBuilder.value(dataSource.asValue());
				break;

			default:
				throw new InvalidIdentifierException(dataSource);
		}

		final Optional<InputDataSourceId> dataSourceId = dataSourceDAO.retrieveInputDataSourceIdBy(queryBuilder.build());
		return dataSourceId.orElse(null);
	}

	@Nullable
	public ShipperId getShipperId(final JsonOLCandCreateRequest request)
	{
		final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

		final String shipper = request.getShipper();

		if (shipper == null)
		{
			return null;
		}

		final OrgId orgId = getOrgId(request.getOrgCode());
		final ExternalIdentifier shipperExternalIdentifier = ExternalIdentifier.of(shipper);
		final ShipperId shipperId;

		switch (shipperExternalIdentifier.getType())
		{
			case METASFRESH_ID:
				shipperId = ShipperId.ofRepoId(shipperExternalIdentifier.asMetasfreshId().getValue());
				break;
			case VALUE:
				shipperId = shipperDAO.getShipperIdByValue(shipperExternalIdentifier.asValue(), orgId).orElse(null);
				break;
			case EXTERNAL_REFERENCE:
				shipperId = externalReferenceService.getJsonMetasfreshIdFromExternalReference(orgId, shipperExternalIdentifier, ShipperExternalReferenceType.SHIPPER)
						.map(JsonMetasfreshId::getValue)
						.map(ShipperId::ofRepoId)
						.orElse(null);
				break;
			default:
				throw new InvalidIdentifierException(shipperExternalIdentifier.getRawValue());
		}

		if (shipperId == null)
		{
			throw MissingResourceException.builder()
					.resourceName("shipper")
					.resourceIdentifier(shipperExternalIdentifier.getRawValue())
					.parentResource(request)
					.build();
		}

		return shipperId;
	}

	/**
	 * @param orgId the method filters for the given orgId or {@link OrgId#ANY}.
	 * @return the sales bpartnerId for the given {@code request}'s {@code salesPartnerCode} and orgId.
	 */
	@Nullable
	public BPartnerId getSalesRepId(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final OrgId orgId)
	{
		final JsonSalesPartner jsonSalesPartner = request.getSalesPartner();

		if (jsonSalesPartner == null)
		{
			return null;
		}

		final Optional<BPartnerId> bPartnerIdBySalesPartnerCode;

		if (jsonSalesPartner.getSalesPartnerCode() != null)
		{
			final ExternalBusinessKey bPartnerExternalBusinessKey = ExternalBusinessKey.of(jsonSalesPartner.getSalesPartnerCode());
			bPartnerIdBySalesPartnerCode = resolveBPartnerExternalBusinessKey(orgId, bPartnerExternalBusinessKey);
		}
		else if (jsonSalesPartner.getSalesPartnerIdentifier() != null)
		{
			final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(jsonSalesPartner.getSalesPartnerIdentifier());
			bPartnerIdBySalesPartnerCode = jsonRetrieverService.resolveBPartnerExternalIdentifier(externalIdentifier, orgId);
		}
		else
		{
			throw new AdempiereException("JsonSalesPartner has all attributes empty!");
		}

		return bPartnerIdBySalesPartnerCode
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("salesPartnerCode")
						.resourceIdentifier(jsonSalesPartner.toString())
						.parentResource(request).build());

	}

	@NonNull
	private Optional<BPartnerId> resolveBPartnerExternalBusinessKey(@NonNull final OrgId orgId, @NonNull final ExternalBusinessKey externalBusinessKey)
	{
		if (ExternalBusinessKey.Type.VALUE.equals(externalBusinessKey.getType()))
		{
			return bPartnerDAO.retrieveBPartnerIdBy(BPartnerQuery.builder()
															.bpartnerValue(externalBusinessKey.asValue())
															.onlyOrgId(orgId)
															.build());
		}
		else if (ExternalBusinessKey.Type.EXTERNAL_REFERENCE.equals(externalBusinessKey.getType()))
		{
			return externalReferenceService
					.getJsonMetasfreshIdFromExternalBusinessKey(orgId, externalBusinessKey, BPartnerExternalReferenceType.BPARTNER_VALUE)
					.map(jsonMetasfreshId -> BPartnerId.ofRepoId(jsonMetasfreshId.getValue()));
		}
		else
		{
			throw new InvalidIdentifierException("Given ExternalBusinessKeyType is not supported!")
					.appendParametersToMessage()
					.setParameter("ExternalBusinessKeyType", externalBusinessKey.getType())
					.setParameter("RawValue", externalBusinessKey.getRawValue());
		}
	}

	public PaymentRule getPaymentRule(final JsonOLCandCreateRequest request)
	{
		final JSONPaymentRule jsonPaymentRule = request.getPaymentRule();

		if (jsonPaymentRule == null)
		{
			return null;
		}

		if (JSONPaymentRule.Paypal.equals(jsonPaymentRule))
		{
			return PaymentRule.PayPal;
		}

		if (JSONPaymentRule.OnCredit.equals(jsonPaymentRule))
		{
			return PaymentRule.OnCredit;
		}

		if (JSONPaymentRule.DirectDebit.equals(jsonPaymentRule))
		{
			return PaymentRule.DirectDebit;
		}

		throw MissingResourceException.builder()
				.resourceName("paymentRule")
				.resourceIdentifier(jsonPaymentRule.getCode())
				.parentResource(request)
				.build();
	}

	@Nullable
	public PaymentTermId getPaymentTermId(@NonNull final JsonOLCandCreateRequest request, @NonNull final OrgId orgId)
	{

		final String paymentTermCode = request.getPaymentTerm();

		if (Check.isEmpty(paymentTermCode))
		{
			return null;
		}

		final IdentifierString paymentTerm = IdentifierString.of(paymentTermCode);

		final PaymentTermQueryBuilder queryBuilder = PaymentTermQuery.builder();

		queryBuilder.orgId(orgId);

		switch (paymentTerm.getType())
		{

			case EXTERNAL_ID:
				queryBuilder.externalId(paymentTerm.asExternalId());
				break;

			case VALUE:
				queryBuilder.value(paymentTerm.asValue());
				break;

			default:
				throw new InvalidIdentifierException(paymentTerm);
		}

		final Optional<PaymentTermId> paymentTermId = paymentTermRepo.retrievePaymentTermId(queryBuilder.build());

		return paymentTermId.orElseThrow(() -> MissingResourceException.builder()
				.resourceName("PaymentTerm")
				.resourceIdentifier(paymentTermCode)
				.parentResource(request).build());

	}

	@Nullable
	public BPartnerId getSalesRepBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		final I_C_BPartner bPartner = bPartnerDAO.getById(bPartnerId);
		return BPartnerId.ofRepoIdOrNull(bPartner.getC_BPartner_SalesRep_ID());
	}

	@Nullable
	public SectionCodeId getSectionCodeId(@NonNull final OrgId orgId, @Nullable final String sectionCode)
	{
		if (Check.isBlank(sectionCode))
		{
			return null;
		}

		return sectionCodeService.getSectionCodeIdByValue(orgId, sectionCode);
	}


	@Nullable
	public AuctionId getAuctionId(@NonNull final JsonOLCandCreateRequest request)
	{
		if (Check.isBlank(request.getAuction()))
		{
			return null;
		}

		return auctionService.getIdByName(request.getAuction());
	}
}
