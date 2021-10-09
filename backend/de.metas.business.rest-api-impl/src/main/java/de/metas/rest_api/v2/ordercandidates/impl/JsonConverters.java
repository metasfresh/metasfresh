package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.common.ordercandidates.v2.request.JsonApplySalesRepFrom;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonOrderLineGroup;
import de.metas.common.ordercandidates.v2.response.JsonOLCand;
import de.metas.common.ordercandidates.v2.response.JsonOLCandCreateBulkResponse;
import de.metas.common.ordercandidates.v2.response.JsonResponseBPartnerLocationAndContact;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonDocTypeInfo;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineGroup;
import de.metas.order.impl.DocTypeService;
import de.metas.ordercandidate.api.AssignSalesRepRule;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandCreateRequest.OLCandCreateRequestBuilder;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductBL;
import de.metas.rest_api.utils.CurrencyService;
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
import java.util.Optional;

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
	public static final String DEFAULT_DATA_SOURCE_DEST_INTERNAL_NAME = "int-DEST.de.metas.ordercandidate";

	private final CurrencyService currencyService;
	private final DocTypeService docTypeService;
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

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

		final OrgId orgId = masterdataProvider.getOrgId(request.getOrgCode());

		final String jsonProductIdentifier = request.getProductIdentifier();
		final ExternalIdentifier productIdentifier = ExternalIdentifier.of(jsonProductIdentifier);
		final ProductMasterDataProvider.ProductInfo productInfo = masterdataProvider.getProductInfo(productIdentifier, orgId);

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

		final JsonOrderLineGroup jsonOrderLineGroup = request.getOrderLineGroup();
		final OrderLineGroup orderLineGroup = jsonOrderLineGroup == null
				? null
				: OrderLineGroup.builder()
				.groupKey(jsonOrderLineGroup.getGroupKey())
				.isGroupMainItem(jsonOrderLineGroup.isGroupMainItem())
				.discount(Percent.ofNullable(jsonOrderLineGroup.getDiscount()))
				.build();

		if (orderLineGroup != null && orderLineGroup.isGroupMainItem() && productBL.isStocked(productInfo.getProductId()))
		{
			throw new AdempiereException("The stocked product identified by: " + jsonProductIdentifier + " cannot be used as compensation group main item.");
		}

		final String docBaseType = Optional.ofNullable(request.getInvoiceDocType())
				.map(JsonDocTypeInfo::getDocBaseType)
				.orElse(null);

		final String subType = Optional.ofNullable(request.getInvoiceDocType())
				.map(JsonDocTypeInfo::getDocSubType)
				.orElse(null);

		final BPartnerInfo bPartnerInfo = masterdataProvider.getBPartnerInfoNotNull(request.getBpartner(), orgId);

		final AssignSalesRepRule assignSalesRepRule = getAssignSalesRepRule(request.getApplySalesRepFrom());

		final BPartnerId salesRepInternalId = masterdataProvider.getSalesRepBPartnerId(bPartnerInfo.getBpartnerId());

		return OLCandCreateRequest.builder()
				//
				.orgId(orgId)
				//
				.dataSourceId(dataSourceId)
				.dataDestId(dataDestId)
				.externalLineId(request.getExternalLineId())
				.externalHeaderId(request.getExternalHeaderId())
				//
				.bpartner(bPartnerInfo)
				.billBPartner(masterdataProvider.getBPartnerInfo(request.getBillBPartner(), orgId).orElse(null))
				.dropShipBPartner(masterdataProvider.getBPartnerInfo(request.getDropShipBPartner(), orgId).orElse(null))
				.handOverBPartner(masterdataProvider.getBPartnerInfo(request.getHandOverBPartner(), orgId).orElse(null))
				//
				.poReference(request.getPoReference())
				//
				.dateOrdered(request.getDateOrdered())
				.dateRequired(request.getDateRequired())
				.dateCandidate(request.getDateCandidate())
				//
				.docTypeInvoiceId(docTypeService.getInvoiceDocTypeId(docBaseType, subType, orgId))
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

				.orderLineGroup(orderLineGroup)

				.description(request.getDescription())
				.line(request.getLine())
				.isManualPrice(request.getIsManualPrice())
				.isImportedWithIssues(request.getIsImportedWithIssues())
				.importWarningMessage(request.getImportWarningMessage())
				.deliveryRule(request.getDeliveryRule())
				.deliveryViaRule(request.getDeliveryViaRule())
				.qtyShipped(request.getQtyShipped())
				//
				.assignSalesRepRule(assignSalesRepRule)
				.salesRepInternalId(salesRepInternalId)
				;
	}

	private InputDataSourceId retrieveDataDestId(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final OrgId orgId,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final String dataDestIdentifier = CoalesceUtil.coalesce(request.getDataDest(), DEFAULT_DATA_SOURCE_DEST_INTERNAL_NAME);
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

	@Nullable
	private JsonResponseBPartnerLocationAndContact toJson(
			@Nullable final String orgCode,
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

		final OrderLineGroup orderLineGroup = olCand.getOrderLineGroup();
		final JsonOrderLineGroup jsonOrderLineGroup = orderLineGroup == null
				? null
				: JsonOrderLineGroup.builder()
				.groupKey(orderLineGroup.getGroupKey())
				.isGroupMainItem(orderLineGroup.isGroupMainItem())
				.build();

		return JsonOLCand.builder()
				.id(olCand.getId())
				.poReference(olCand.getPOReference())
				.externalLineId(olCand.getExternalLineId())
				.externalHeaderId(olCand.getExternalHeaderId())
				//
				.orgCode(orgCode)
				//
				.bpartner(toJson(orgCode, olCand.getBPartnerInfo(), masterdataProvider))
				.billBPartner(toJson(orgCode, olCand.getBillBPartnerInfo(), masterdataProvider))
				.dropShipBPartner(toJson(orgCode, olCand.getDropShipBPartnerInfo().orElse(null), masterdataProvider))
				.handOverBPartner(toJson(orgCode, olCand.getHandOverBPartnerInfo().orElse(null), masterdataProvider))
				//
				.dateCandidate(TimeUtil.asLocalDate(olCand.unbox().getDateCandidate(), SystemTime.zoneId()))
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
				.jsonOrderLineGroup(jsonOrderLineGroup)

				.description(olCand.unbox().getDescription())
				.line(olCand.getLine())
				.build();
	}

	@NonNull
	private static AssignSalesRepRule getAssignSalesRepRule(@NonNull final JsonApplySalesRepFrom jsonApplySalesRepFrom)
	{
		switch (jsonApplySalesRepFrom)
		{
			case Candidate:
				return AssignSalesRepRule.Candidate;
			case BPartner:
				return AssignSalesRepRule.BPartner;
			case CandidateFirst:
				return AssignSalesRepRule.CandidateFirst;
			default:
				throw new AdempiereException("Unsupported JsonApplySalesRepFrom " + jsonApplySalesRepFrom);
		}
	}
}
