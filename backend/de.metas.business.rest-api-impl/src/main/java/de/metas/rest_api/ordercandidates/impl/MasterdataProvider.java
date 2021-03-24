package de.metas.rest_api.ordercandidates.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.api.impl.InputDataSourceQuery;
import de.metas.impex.api.impl.InputDataSourceQuery.InputDataSourceQueryBuilder;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.organization.OrgQuery;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.payment.paymentterm.impl.PaymentTermQuery.PaymentTermQueryBuilder;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.rest_api.bpartner.impl.BpartnerRestController;
import de.metas.common.bpartner.response.JsonResponseBPartner;
import de.metas.common.bpartner.response.JsonResponseContact;
import de.metas.common.bpartner.response.JsonResponseLocation;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.rest_api.ordercandidates.impl.ProductMasterDataProvider.ProductInfo;
import de.metas.rest_api.ordercandidates.request.JSONPaymentRule;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.request.JsonOrganization;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.security.permissions2.PermissionService;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

final class MasterdataProvider
{
	private static final Logger logger = LogManager.getLogger(MasterdataProvider.class);

	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);

	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);

	private final PermissionService permissionService;
	private final BPartnerEndpointAdapter bpartnerEndpointAdapter;
	private final ProductMasterDataProvider productMasterDataProvider;
	private final ProductPriceMasterDataProvider productPricesMasterDataProvider;

	private final Map<String, OrgId> orgIdsByCode = new HashMap<>();

	@Builder
	private MasterdataProvider(
			@NonNull final PermissionService permissionService,
			@NonNull final BpartnerRestController bpartnerRestController)
	{
		this.permissionService = permissionService;
		this.bpartnerEndpointAdapter = new BPartnerEndpointAdapter(bpartnerRestController);
		this.productMasterDataProvider = new ProductMasterDataProvider(permissionService);
		this.productPricesMasterDataProvider = new ProductPriceMasterDataProvider();
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

	public OrgId getCreateOrgIdInTrx(@Nullable final JsonOrganization json)
	{
		if (json == null)
		{
			return permissionService.getDefaultOrgId();
		}

		return orgIdsByCode.compute(json.getCode(), (code, existingOrgId) -> createOrUpdateOrgIdInTrx(json, existingOrgId));
	}

	@VisibleForTesting
	OrgId createOrUpdateOrgIdInTrx(
			@NonNull final JsonOrganization json,
			@Nullable final OrgId existingOrgId)
	{
		final I_AD_Org orgRecord = trxManager.callInNewTrx(() -> createOrUpdateOrgIdInTrx0(json, existingOrgId));
		final OrgId orgId = OrgId.ofRepoId(orgRecord.getAD_Org_ID());

		if (json.getBpartner() != null)
		{
			final BPartnerInfo bpartnerInfo = bpartnerEndpointAdapter.getCreateBPartnerInfoInTrx(json.getBpartner(), true/* billTo */, orgRecord.getValue());
			logger.debug("Created or updated org-C_BPartner with C_BPartner_ID={}", bpartnerInfo.getBpartnerId().getRepoId());

			trxManager.runInNewTrx(() -> {
				final I_C_BPartner bpartnerRecord = Services.get(IBPartnerDAO.class).getById(bpartnerInfo.getBpartnerId());
				bpartnerRecord.setAD_OrgBP_ID(orgRecord.getAD_Org_ID());
				saveRecord(bpartnerRecord);

				orgDAO.createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
						.orgId(orgId)
						.orgBPartnerLocationId(Optional.of(bpartnerInfo.getBpartnerLocationId()))
						.build());
				logger.debug("Linked org-C_BPartner with C_BPartner_ID={} to AD_Org with AD_Org_ID={}", bpartnerRecord.getC_BPartner_ID(), orgRecord.getAD_Org_ID());
			});
		}
		return orgId;
	}

	private I_AD_Org createOrUpdateOrgIdInTrx0(@NonNull final JsonOrganization json, @Nullable OrgId existingOrgId)
	{
		final SyncAdvise orgSyncAdvise = json.getSyncAdvise();

		if (existingOrgId == null)
		{
			final String code = json.getCode();
			if (Check.isEmpty(code, true))
			{
				throw new MissingPropertyException("JsonOrganization.code", json);
			}

			final OrgQuery query = OrgQuery.builder()
					.orgValue(code)
					.failIfNotExists(orgSyncAdvise.isFailIfNotExists())
					.build();

			existingOrgId = orgDAO
					.retrieveOrgIdBy(query)
					.orElse(null);
			logger.debug("Tried to retrieve existingOrgId using AD_Org_ID.Value={}; result: {}", code, existingOrgId);
		}

		final I_AD_Org orgRecord;
		if (existingOrgId != null)
		{
			logger.debug("Load existing AD_Org record with AD_Org_ID={}", existingOrgId.getRepoId());
			orgRecord = orgDAO.getById(existingOrgId);
		}
		else
		{
			logger.debug("Create new AD_Org record");
			orgRecord = newInstance(I_AD_Org.class);
		}

		if (!orgSyncAdvise.isLoadReadOnly())
		{
			permissionService.assertCanCreateOrUpdate(orgRecord);
			updateOrgRecord(orgRecord, json);
			orgDAO.save(orgRecord);
		}

		return orgRecord;
	}

	private void updateOrgRecord(@NonNull final I_AD_Org orgRecord, @NonNull final JsonOrganization json)
	{
		orgRecord.setValue(json.getCode());
		orgRecord.setName(json.getName());
	}

	public JsonOrganization getJsonOrganizationById(final OrgId orgId)
	{
		final I_AD_Org orgRecord = orgDAO.getById(orgId);
		if (orgRecord == null)
		{
			return null;
		}

		return JsonOrganization.builder()
				.code(orgRecord.getValue())
				.name(orgRecord.getName())
				.build();
	}

	public ZoneId getOrgTimeZone(final OrgId orgId)
	{
		return orgDAO.getTimeZone(orgId);
	}

	public BPartnerInfo getCreateBPartnerInfoInTrx(
			@Nullable final JsonRequestBPartnerLocationAndContact jsonBPartnerInfo,
			final boolean billTo,
			@Nullable final OrgId orgId)
	{
		final String orgCode = orgId != null ? orgDAO.retrieveOrgValue(orgId) : null;
		return bpartnerEndpointAdapter.getCreateBPartnerInfoInTrx(jsonBPartnerInfo, billTo, orgCode);
	}

	public JsonResponseBPartner getJsonBPartnerById(@Nullable String orgCode, @NonNull final BPartnerId bpartnerId)
	{
		return bpartnerEndpointAdapter.getJsonBPartnerById(orgCode, bpartnerId);
	}

	public JsonResponseLocation getJsonBPartnerLocationById(@Nullable String orgCode, @NonNull final BPartnerLocationId bpartnerLocationId)
	{
		return bpartnerEndpointAdapter.getJsonBPartnerLocationById(orgCode, bpartnerLocationId);
	}

	public JsonResponseContact getJsonBPartnerContactById(@Nullable String orgCode, @Nullable final BPartnerContactId bpartnerContactId)
	{
		if (bpartnerContactId == null)
		{
			return null;
		}
		return bpartnerEndpointAdapter.getJsonBPartnerContactById(orgCode, bpartnerContactId);
	}

	public ProductInfo getCreateProductInfo(
			@NonNull final JsonProductInfo jsonProductInfo,
			@NonNull final OrgId orgId)
	{
		return productMasterDataProvider.getCreateProductInfo(jsonProductInfo, orgId);
	}

	public void createProductPrice(@NonNull final ProductPriceCreateRequest request)
	{
		productPricesMasterDataProvider.createProductPrice(request);
	}

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

	public ShipperId getShipperId(final JsonOLCandCreateRequest request)
	{
		final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

		final String shipper = request.getShipper();

		if (shipper == null)
		{
			return null;
		}

		final IdentifierString shipperIdentifier = IdentifierString.of(shipper);

		final ShipperId shipperId;

		switch (shipperIdentifier.getType())
		{

			case METASFRESH_ID:
				shipperId = ShipperId.ofRepoIdOrNull(shipperIdentifier.asMetasfreshId().getValue());
				break;
			case VALUE:
				shipperId = shipperDAO.getShipperIdByValue(shipperIdentifier.asValue(), getCreateOrgIdInTrx(request.getOrg())).orElse(null);
				break;

			default:
				throw new InvalidIdentifierException(shipperIdentifier);
		}

		if (shipperId == null)
		{
			throw MissingResourceException.builder().resourceName("shipper").resourceIdentifier(shipperIdentifier.toJson()).parentResource(request).build();
		}

		return shipperId;

	}

	/**
	 * @param orgId the method filters for the given orgId or {@link OrgId#ANY}.
	 * @return the sales bpartnerId for the given {@code request}'s {@code salesPartnerCode} and orgId.
	 */
	public BPartnerId getSalesRepId(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final OrgId orgId)
	{
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		final String salesRepValue = request.getSalesPartnerCode();

		if (Check.isEmpty(salesRepValue))
		{
			return null;
		}

		final Optional<BPartnerId> bPartnerIdBySalesPartnerCode = bPartnerDAO.getBPartnerIdBySalesPartnerCode(salesRepValue, ImmutableSet.of(OrgId.ANY, orgId));

		return bPartnerIdBySalesPartnerCode.orElseThrow(() -> MissingResourceException.builder()
				.resourceName("salesPartnerCode")
				.resourceIdentifier(salesRepValue)
				.parentResource(request).build());
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
}
