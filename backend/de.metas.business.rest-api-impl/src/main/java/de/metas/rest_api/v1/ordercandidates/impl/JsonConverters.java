package de.metas.rest_api.v1.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v1.response.JsonOLCand;
import de.metas.common.ordercandidates.v1.response.JsonOLCandCreateBulkResponse;
import de.metas.common.ordercandidates.v1.response.JsonResponseBPartnerLocationAndContact;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.money.CurrencyId;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandCreateRequest.OLCandCreateRequestBuilder;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.DocTypeService;
import de.metas.rest_api.v1.ordercandidates.impl.ProductMasterDataProvider.ProductInfo;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.List;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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
public class JsonConverters
{
	private final CurrencyService currencyService;
	private final DocTypeService docTypeService;
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public JsonConverters(
			@NonNull final CurrencyService currencyService,
			@NonNull final DocTypeService docTypeService)
	{
		this.currencyService = currencyService;
		this.docTypeService = docTypeService;

	}

	public final OLCandCreateRequestBuilder fromJson(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		// POReference shall be mandatory, else we would have problems later,
		// when we will aggregate the invoice candidates by POReference
		if (Check.isEmpty(request.getPoReference(), true))
		{
			throw new AdempiereException("@FillMandatory@ @POReference@: " + request);
		}

		final OrgId orgId = masterdataProvider.getCreateOrgIdInTrx(request.getOrg());

		final ProductInfo productInfo = masterdataProvider.getCreateProductInfo(request.getProduct(), orgId);

		final PricingSystemId pricingSystemId = masterdataProvider.getPricingSystemIdByValue(request.getPricingSystemCode());

		final CurrencyId currencyId = currencyService.getCurrencyId(request.getCurrencyCode());

		final WarehouseId warehouseDestId = !Check.isEmpty(request.getWarehouseDestCode())
				? masterdataProvider.getWarehouseIdByValue(request.getWarehouseDestCode())
				: null;

		final WarehouseId warehouseId = !Check.isEmpty(request.getWarehouseCode())
				? masterdataProvider.getWarehouseIdByValue(request.getWarehouseCode())
				: null;

		final InputDataSourceId dataSourceId = retrieveDataSourceId(request, orgId, masterdataProvider);

		final InputDataSourceId dataDestId = retrieveDataDestId(request, orgId, masterdataProvider);
		final I_AD_InputDataSource dataDestRecord = inputDataSourceDAO.getById(dataDestId);
		final String dataDestInternalName = dataDestRecord.getInternalName();
		if (!"DEST.de.metas.invoicecandidate".equals(dataDestInternalName)) // TODO extract constant
		{
			Check.assumeNotNull(request.getDateRequired(),
					"dateRequired may not be null, unless dataDestInternalName={}; this={}",
					"DEST.de.metas.invoicecandidate", this);
		}

		final ShipperId shipperId = masterdataProvider.getShipperId(request);

		final BPartnerId salesRepId = masterdataProvider.getSalesRepId(request, orgId);

		final PaymentRule paymentRule = masterdataProvider.getPaymentRule(request);

		final PaymentTermId paymentTermId = masterdataProvider.getPaymentTermId(request, orgId);

		final UomId uomId;
		if (!Check.isBlank(request.getUomCode()))
		{
			uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(request.getUomCode()));
		}
		else
		{
			uomId = productInfo.getUomId();
		}

		return OLCandCreateRequest.builder()
				//
				.orgId(orgId)
				//
				.dataSourceId(dataSourceId)
				.dataDestId(dataDestId)
				.externalLineId(request.getExternalLineId())
				.externalHeaderId(request.getExternalHeaderId())
				//
				.bpartner(masterdataProvider.getCreateBPartnerInfoInTrx(request.getBpartner(), true/* billTo */, orgId))
				.billBPartner(masterdataProvider.getCreateBPartnerInfoInTrx(request.getBillBPartner(), true/* billTo */, orgId))
				.dropShipBPartner(masterdataProvider.getCreateBPartnerInfoInTrx(request.getDropShipBPartner(), false/* billTo */, orgId))
				.handOverBPartner(masterdataProvider.getCreateBPartnerInfoInTrx(request.getHandOverBPartner(), false/* billTo */, orgId))
				//
				.poReference(request.getPoReference())
				//
				.dateOrdered(request.getDateOrdered())
				.dateRequired(request.getDateRequired())
				//
				.docTypeInvoiceId(docTypeService.getInvoiceDocTypeId(request.getInvoiceDocType(), orgId))
				.docTypeOrderId(docTypeService.getOrderDocTypeId(request.getOrderDocType(), orgId))
				.presetDateInvoiced(request.getPresetDateInvoiced())
				//
				.presetDateShipped(request.getPresetDateShipped())
				//
				.flatrateConditionsId(request.getFlatrateConditionsId())
				//
				.productId(productInfo.getProductId())
				.productDescription(request.getProductDescription())
				.qty(request.getQty())
				.uomId(uomId)
				.huPIItemProductId(JsonMetasfreshId.toValueInt(request.getPackingMaterialId()))
				//
				.pricingSystemId(pricingSystemId)
				.price(request.getPrice())
				.currencyId(currencyId)
				.discount(Percent.ofNullable(request.getDiscount()))
				//
				.warehouseDestId(warehouseDestId)
				.warehouseId(warehouseId)

				.shipperId(shipperId)

				.paymentRule(paymentRule)

				.salesRepId(salesRepId)

				.paymentTermId(paymentTermId)
		//
		;
	}

	private InputDataSourceId retrieveDataDestId(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final OrgId orgId,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final String dataDestIdentifier = request.getDataDest();
		if (Check.isEmpty(dataDestIdentifier))
		{
			throw new MissingPropertyException("dataDest", request);
		}
		final InputDataSourceId dataDestId = masterdataProvider.getDataSourceId(dataDestIdentifier, orgId);
		if (dataDestId == null)
		{
			throw MissingResourceException.builder()
					.resourceName("dataDest")
					.resourceIdentifier(dataDestIdentifier)
					.parentResource(request).build();
		}
		return dataDestId;
	}

	private InputDataSourceId retrieveDataSourceId(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final OrgId orgId,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final String dataSourceIdentifier = request.getDataSource();
		if (Check.isEmpty(dataSourceIdentifier))
		{
			throw new MissingPropertyException("dataSource", request);
		}
		final InputDataSourceId dataSourceId = masterdataProvider.getDataSourceId(dataSourceIdentifier, orgId);
		if (dataSourceId == null)
		{
			throw MissingResourceException.builder()
					.resourceName("dataSource")
					.resourceIdentifier(dataSourceIdentifier)
					.parentResource(request).build();
		}
		return dataSourceId;
	}

	private final JsonResponseBPartnerLocationAndContact toJson(
			@Nullable String orgCode,
			@Nullable final BPartnerInfo bpartnerInfo,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		if (bpartnerInfo == null)
		{
			return null;
		}

		final BPartnerId bpartnerId = bpartnerInfo.getBpartnerId();
		final BPartnerLocationId bpartnerLocationId = bpartnerInfo.getBpartnerLocationId();
		final BPartnerContactId contactId = bpartnerInfo.getContactId();

		return JsonResponseBPartnerLocationAndContact.builder()
				.bpartner(masterdataProvider.getJsonBPartnerById(orgCode, bpartnerId))
				.location(masterdataProvider.getJsonBPartnerLocationById(orgCode, bpartnerLocationId))
				.contact(masterdataProvider.getJsonBPartnerContactById(orgCode, contactId))
				.build();
	}

	public JsonOLCandCreateBulkResponse toJson(
			@NonNull final List<OLCand> olCands,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		return JsonOLCandCreateBulkResponse.ok(olCands.stream()
				.map(olCand -> toJson(olCand, masterdataProvider))
				.collect(ImmutableList.toImmutableList()));
	}

	private JsonOLCand toJson(
			@NonNull final OLCand olCand,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = OrgId.ofRepoId(olCand.getAD_Org_ID());
		final ZoneId orgTimeZone = masterdataProvider.getOrgTimeZone(orgId);
		final String orgCode = orgDAO.retrieveOrgValue(orgId);

		return JsonOLCand.builder()
				.id(olCand.getId())
				.poReference(olCand.getPOReference())
				.externalLineId(olCand.getExternalLineId())
				.externalHeaderId(olCand.getExternalHeaderId())
				//
				.org(masterdataProvider.getJsonOrganizationById(orgId))
				//
				.bpartner(toJson(orgCode, olCand.getBPartnerInfo(), masterdataProvider))
				.billBPartner(toJson(orgCode, olCand.getBillBPartnerInfo(), masterdataProvider))
				.dropShipBPartner(toJson(orgCode, olCand.getDropShipBPartnerInfo().orElse(null), masterdataProvider))
				.handOverBPartner(toJson(orgCode, olCand.getHandOverBPartnerInfo().orElse(null), masterdataProvider))
				//
				.dateOrdered(olCand.getDateDoc())
				.datePromised(TimeUtil.asLocalDate(olCand.getDatePromised(), orgTimeZone))
				.flatrateConditionsId(olCand.getFlatrateConditionsId())
				//
				.productId(olCand.getM_Product_ID())
				.productDescription(olCand.getProductDescription())
				.qty(olCand.getQty().toBigDecimal())
				.uomId(olCand.getQty().getUomId().getRepoId())
				.qtyItemCapacity(olCand.getQtyItemCapacity())
				.huPIItemProductId(olCand.getHUPIProductItemId())
				//
				.pricingSystemId(PricingSystemId.toRepoId(olCand.getPricingSystemId()))
				.price(olCand.getPriceActual())
				.discount(olCand.getDiscount())
				//
				.warehouseDestId(WarehouseId.toRepoId(olCand.getWarehouseDestId()))
				//
				.build();
	}
}
