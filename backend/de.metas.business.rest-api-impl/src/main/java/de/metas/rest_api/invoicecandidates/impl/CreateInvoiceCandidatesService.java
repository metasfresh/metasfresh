package de.metas.rest_api.invoicecandidates.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.BPartnerInfo.BPartnerInfoBuilder;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.externallyreferenced.ExternallyReferencedCandidate;
import de.metas.invoicecandidate.externallyreferenced.ExternallyReferencedCandidateRepository;
import de.metas.invoicecandidate.externallyreferenced.InvoiceCandidateLookupKey;
import de.metas.invoice.detail.InvoiceDetailItem;
import de.metas.invoicecandidate.externallyreferenced.ManualCandidateService;
import de.metas.invoicecandidate.externallyreferenced.NewManualInvoiceCandidate;
import de.metas.invoicecandidate.externallyreferenced.NewManualInvoiceCandidate.NewManualInvoiceCandidateBuilder;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.InvoiceRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgIdNotFoundException;
import de.metas.organization.OrgQuery;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IProductDAO.ProductQuery;
import de.metas.product.IProductDAO.ProductQuery.ProductQueryBuilder;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.BPartnerCompositeRestUtils;
import de.metas.rest_api.common.JsonDocTypeInfo;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.JsonInvoiceRule;
import de.metas.rest_api.common.JsonPrice;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.exception.InvalidEntityException;
import de.metas.rest_api.exception.MissingPropertyException;
import de.metas.rest_api.exception.MissingResourceException;
import de.metas.rest_api.invoicecandidates.request.JsonCreateInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.request.JsonCreateInvoiceCandidatesRequestItem;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse.JsonCreateInvoiceCandidatesResponseBuilder;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidatesResponseItem;
import de.metas.rest_api.utils.BPartnerCompositeLookupKey;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.DocTypeService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.collections4.CollectionUtils;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;
import static java.math.BigDecimal.ZERO;

/*
 * #%L
 * de.metas.business.rest-api-impl
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
public class CreateInvoiceCandidatesService
{
	private final BPartnerQueryService bPartnerQueryService;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;
	private final DocTypeService docTypeService;
	private final CurrencyService currencyService;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final ExternallyReferencedCandidateRepository externallyReferencedCandidateRepository;
	private final ManualCandidateService manualCandidateService;

	public CreateInvoiceCandidatesService(
			@NonNull final BPartnerQueryService bPartnerQueryService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final DocTypeService docTypeService,
			@NonNull final CurrencyService currencyService,
			@NonNull final ManualCandidateService manualCandidateService,
			@NonNull final ExternallyReferencedCandidateRepository externallyReferencedCandidateRepository)
	{
		this.bPartnerQueryService = bPartnerQueryService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.currencyService = currencyService;
		this.docTypeService = docTypeService;
		this.manualCandidateService = manualCandidateService;
		this.externallyReferencedCandidateRepository = externallyReferencedCandidateRepository;
	}

	public JsonCreateInvoiceCandidatesResponse createInvoiceCandidates(@NonNull final JsonCreateInvoiceCandidatesRequest request)
	{
		final ImmutableMap<InvoiceCandidateLookupKey, JsonCreateInvoiceCandidatesRequestItem> lookupKey2Item = Maps.uniqueIndex(request.getItems(), this::createInvoiceCandidateLookupKey);

		// candidates
		final ImmutableList<ExternallyReferencedCandidate> //
		exitingCandidates = externallyReferencedCandidateRepository.getAllBy(lookupKey2Item.keySet());
		failIfNotEmpty(exitingCandidates);

		final ImmutableList.Builder<NewManualInvoiceCandidate> candidatesToSave = ImmutableList.builder();
		for (final Entry<InvoiceCandidateLookupKey, JsonCreateInvoiceCandidatesRequestItem> keyWithItem : lookupKey2Item.entrySet())
		{
			final JsonCreateInvoiceCandidatesRequestItem item = keyWithItem.getValue();
			final NewManualInvoiceCandidate candidate = createCandidate(item);

			candidatesToSave.add(candidate);
		}

		final JsonCreateInvoiceCandidatesResponseBuilder result = JsonCreateInvoiceCandidatesResponse.builder();
		for (final NewManualInvoiceCandidate candidateToSave : candidatesToSave.build())
		{
			final JsonExternalId headerId = JsonExternalIds.ofOrNull(candidateToSave.getExternalHeaderId());
			final JsonExternalId lineId = JsonExternalIds.ofOrNull(candidateToSave.getExternalLineId());

			final InvoiceCandidateId candidateId = externallyReferencedCandidateRepository.save(manualCandidateService.createInvoiceCandidate(candidateToSave));

			final JsonInvoiceCandidatesResponseItem responseItem = JsonInvoiceCandidatesResponseItem.builder()
					.externalHeaderId(headerId)
					.externalLineId(lineId)
					.metasfreshId(MetasfreshId.of(candidateId))
					.build();
			result.responseItem(responseItem);
		}

		return result.build();
	}

	private void failIfNotEmpty(@NonNull final ImmutableList<ExternallyReferencedCandidate> exitingCandidates)
	{
		if (!exitingCandidates.isEmpty())
		{
			throw new InvalidEntityException(TranslatableStrings.constant("InvoiceCandidate(s) already exist"))
					.appendParametersToMessage();
		}
	}

	private NewManualInvoiceCandidate createCandidate(@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final NewManualInvoiceCandidateBuilder candidate = NewManualInvoiceCandidate.builder();

		final OrgId orgId = syncOrgIdToCandidate(candidate, item);
		final ProductId productId = syncProductToCandidate(candidate, orgId, item);

		syncBPartnerToCandidate(candidate, orgId, item);

		syncTargetDocTypeToCandidate(candidate, orgId, item.getInvoiceDocType());

		syncDiscountOverrideToCandidate(candidate, item.getDiscountOverride());

		syncPriceEnteredOverrideToCandidate(candidate, productId, item);

		// poReference
		if (!isEmpty(item.getPoReference(), true))
		{
			candidate.poReference(item.getPoReference().trim());
		}

		// dateOrdered
		if (item.getDateOrdered() != null)
		{
			candidate.dateOrdered(item.getDateOrdered());
		}
		else
		{
			candidate.dateOrdered(LocalDate.now());
		}

		// uomId
		final UomId uomId = lookupUomId(item.getUomCode(), productId, item);
		candidate.invoicingUomId(uomId);

		// qtyOrdered
		if (item.getQtyOrdered() != null)
		{

			final StockQtyAndUOMQty qtyOrdered = StockQtyAndUOMQtys.createConvert(
					Quantitys.create(item.getQtyOrdered(), uomId),
					productId,
					uomId);
			candidate.qtyOrdered(qtyOrdered);
		}
		else
		{
			throw new MissingPropertyException("qtyOrdered", item);
		}

		// qtyDelivered
		final BigDecimal qtyDeliveredEff = coalesce(item.getQtyDelivered(), ZERO);
		final StockQtyAndUOMQty qtyDelivered = StockQtyAndUOMQtys.createConvert(
				Quantitys.create(qtyDeliveredEff, uomId),
				productId,
				uomId);
		candidate.qtyDelivered(qtyDelivered);

		// soTrx
		if (item.getSoTrx() != null)
		{
			final SOTrx soTrx = SOTrx.ofBoolean(item.getSoTrx().isSales());
			candidate.soTrx(soTrx);
		}
		else
		{
			throw new MissingPropertyException("soTrx", item);
		}

		syncInvoiceRuleOverrideToCandidate(candidate, item.getInvoiceRuleOverride());

		// presetDateInvoiced
		if (item.getPresetDateInvoiced() != null)
		{
			candidate.presetDateInvoiced(item.getPresetDateInvoiced());
		}

		// lineDescription
		if (!isEmpty(item.getLineDescription(), true))
		{
			candidate.lineDescription(item.getLineDescription().trim());
		}

		// invoice detail items
		if (CollectionUtils.isNotEmpty(item.getInvoiceDetailItems()))
		{
			final List<InvoiceDetailItem> invoiceDetailItems = item.getInvoiceDetailItems()
					.stream()
					.map(jsonDetail -> new InvoiceDetailItem(
							jsonDetail.getSeqNo(),
							jsonDetail.getLabel(),
							jsonDetail.getDescription(),
							jsonDetail.getDate()))
					.collect(Collectors.toList());

			candidate.invoiceDetailItems(invoiceDetailItems);
		}

		return candidate
				.externalHeaderId(JsonExternalIds.toExternalId(item.getExternalHeaderId()))
				.externalLineId(JsonExternalIds.toExternalId(item.getExternalLineId()))
				.build();
	}

	private ProductId syncProductToCandidate(
			@NonNull final NewManualInvoiceCandidateBuilder candidate,
			@NonNull final OrgId orgId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final IdentifierString productIdentifier = IdentifierString.ofOrNull(item.getProductIdentifier());
		if (productIdentifier == null)
		{
			throw new MissingPropertyException("productIdentifier", item);
		}

		final ProductQueryBuilder productQuery = ProductQuery.builder()
				.orgId(orgId)
				.includeAnyOrg(true)
				.outOfTrx(true);
		final ProductId productId;
		switch (productIdentifier.getType())
		{
			case EXTERNAL_ID:
				productId = productDAO.retrieveProductIdBy(productQuery.externalId(productIdentifier.asExternalId()).build());
				break;
			case METASFRESH_ID:
				productId = ProductId.ofRepoId(productIdentifier.asMetasfreshId().getValue());
				break;
			case VALUE:
				productId = productDAO.retrieveProductIdBy(productQuery.value(productIdentifier.asValue()).build());
				break;
			default:
				throw new AdempiereException("Unexpected type=" + productIdentifier.getType());
		}
		if (productId == null)
		{
			throw MissingResourceException.builder().resourceName("product").resourceIdentifier(productIdentifier.toJson()).parentResource(item).build();
		}

		candidate.productId(productId);
		return productId;

	}

	private OrgId syncOrgIdToCandidate(
			@NonNull final NewManualInvoiceCandidateBuilder candidate,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final OrgId orgId;
		if (Check.isNotBlank(item.getOrgCode()))
		{
			final OrgQuery query = OrgQuery.builder()
					.orgValue(item.getOrgCode())
					.failIfNotExists(true)
					.build();
			try
			{
				orgId = orgDAO
						.retrieveOrgIdBy(query)
						.get();
			}
			catch (final OrgIdNotFoundException e)
			{
				throw MissingResourceException.builder().resourceName("organisation").resourceIdentifier(item.getOrgCode()).parentResource(item).cause(e).build();
			}
		}
		else
		{
			orgId = Env.getOrgId(Env.getCtx());
			if (!orgId.isRegular())
			{
				throw new InvalidEntityException(TranslatableStrings.constant("Request entity has no orgCode property, the candidate in question doesn't exist yet and the invoking user is not assigned to an organization"))
						.appendParametersToMessage()
						.setParameter("item", item);
			}
		}
		candidate.orgId(orgId);
		return orgId;
	}

	private void syncTargetDocTypeToCandidate(
			@NonNull final NewManualInvoiceCandidateBuilder candidate,
			@NonNull final OrgId orgId,
			@Nullable final JsonDocTypeInfo docType)
	{
		if (docType == null)
		{
			return;
		}

		candidate.invoiceDocTypeId(docTypeService.getInvoiceDocTypeId(docType, orgId));
	}

	private void syncBPartnerToCandidate(
			@NonNull final NewManualInvoiceCandidateBuilder candidate,
			@NonNull final OrgId orgId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final boolean noBPartnerIdentifier = isEmpty(item.getBillPartnerIdentifier(), true);

		final BPartnerInfoBuilder bpartnerInfo = BPartnerInfo.builder();

		if (noBPartnerIdentifier)
		{
			throw new MissingPropertyException("billPartnerIdentifier", item);
		}

		final IdentifierString bpartnerIdentifier = IdentifierString.of(item.getBillPartnerIdentifier());
		final BPartnerCompositeLookupKey bpartnerIdLookupKey = BPartnerCompositeLookupKey.ofIdentifierString(bpartnerIdentifier);
		final BPartnerQuery query = bPartnerQueryService.createQueryFailIfNotExists(bpartnerIdLookupKey, orgId);
		final BPartnerComposite bpartnerComposite;
		try
		{
			bpartnerComposite = bpartnerCompositeRepository.getSingleByQuery(query).get();
		}
		catch (final AdempiereException e)
		{
			throw MissingResourceException.builder()
					.resourceName("billPartner")
					.resourceIdentifier(bpartnerIdentifier.toJson())
					.parentResource(item)
					.cause(e)
					.build();
		}

		bpartnerInfo.bpartnerId(bpartnerComposite.getBpartner().getId());

		final IdentifierString billLocationIdentifier = IdentifierString.ofOrNull(item.getBillLocationIdentifier());
		if (billLocationIdentifier == null)
		{
			final BPartnerLocation location = bpartnerComposite
					.extractBillToLocation()
					.orElseThrow(() -> MissingResourceException.builder().resourceName("billLocation").parentResource(item).build());
			bpartnerInfo.bpartnerLocationId(location.getId());
		}
		else
		{
			final BPartnerLocation location = bpartnerComposite
					.extractLocation(BPartnerCompositeRestUtils.createLocationFilterFor(billLocationIdentifier))
					.orElseThrow(() -> MissingResourceException.builder().resourceName("billLocation").resourceIdentifier(billLocationIdentifier.toJson()).parentResource(item).build());
			bpartnerInfo.bpartnerLocationId(location.getId());
		}

		final IdentifierString billContactIdentifier = IdentifierString.ofOrNull(item.getBillContactIdentifier());
		if (billContactIdentifier == null)
		{
			bpartnerInfo.contactId(null); // that's OK, because the contact is not mandatory in C_Invoice_Candidate
		}
		else
		{
			// extract the composite's location that has the given billContactIdentifier
			final BPartnerContact contact = bpartnerComposite
					.extractContact(BPartnerCompositeRestUtils.createContactFilterFor(billContactIdentifier))
					.orElseThrow(() -> MissingResourceException.builder().resourceName("billContact").resourceIdentifier(billContactIdentifier.toJson()).parentResource(item).build());

			bpartnerInfo.contactId(contact.getId());
		}

		candidate.billPartnerInfo(bpartnerInfo.build());
	}

	private void syncDiscountOverrideToCandidate(
			@NonNull final NewManualInvoiceCandidateBuilder candidate,
			@Nullable final BigDecimal discountOverride)
	{
		candidate.discountOverride(Percent.ofNullable(discountOverride));
	}

	private void syncInvoiceRuleOverrideToCandidate(
			@NonNull final NewManualInvoiceCandidateBuilder candidate,
			@Nullable final JsonInvoiceRule invoiceRuleOverride)
	{
		candidate.invoiceRuleOverride(createInvoiceRule(invoiceRuleOverride));
	}

	private InvoiceRule createInvoiceRule(@Nullable final JsonInvoiceRule jsonInvoiceRule)
	{
		if (jsonInvoiceRule == null)
		{
			return null;
		}
		final InvoiceRule invoiceRule;
		switch (jsonInvoiceRule)
		{
			case AfterDelivery:
				invoiceRule = InvoiceRule.AfterDelivery;
				break;
			case CustomerScheduleAfterDelivery:
				invoiceRule = InvoiceRule.CustomerScheduleAfterDelivery;
				break;
			case Immediate:
				invoiceRule = InvoiceRule.Immediate;
				break;
			default:
				throw new AdempiereException("Unsupported JsonInvliceRule " + jsonInvoiceRule);
		}
		return invoiceRule;
	}

	private void syncPriceEnteredOverrideToCandidate(
			@NonNull final NewManualInvoiceCandidateBuilder candidate,
			@NonNull final ProductId productId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final JsonPrice priceEnteredOverride = item.getPriceEnteredOverride();

		final ProductPrice price = createProductPriceOrNull(priceEnteredOverride, productId, item);
		candidate.priceEnteredOverride(price);
		return;

	}

	private ProductPrice createProductPriceOrNull(
			@Nullable final JsonPrice jsonPrice,
			@NonNull final ProductId productId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		if (jsonPrice == null)
		{
			return null;
		}

		final CurrencyId currencyId = lookupCurrencyId(jsonPrice);

		final UomId priceUomId = lookupUomId(jsonPrice.getPriceUomCode(), productId, item);

		final ProductPrice price = ProductPrice.builder()
				.money(Money.of(jsonPrice.getValue(), currencyId))
				.productId(productId)
				.uomId(priceUomId)
				.build();
		return price;
	}

	private CurrencyId lookupCurrencyId(@NonNull final JsonPrice jsonPrice)
	{
		final CurrencyId result = currencyService.getCurrencyId(jsonPrice.getCurrencyCode());
		if (result == null)
		{
			throw MissingResourceException.builder().resourceName("currency").resourceIdentifier(jsonPrice.getPriceUomCode()).parentResource(jsonPrice).build();
		}
		return result;
	}

	private UomId lookupUomId(
			@Nullable final String uomCode,
			@NonNull final ProductId productId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		if (isEmpty(uomCode, true))
		{
			return productBL.getStockUOMId(productId);
		}

		final UomId priceUomId;
		try
		{
			priceUomId = uomDAO.getUomIdByX12DE355(uomCode);
		}
		catch (AdempiereException e)
		{
			throw MissingResourceException.builder().resourceName("uom").resourceIdentifier("priceUomCode").parentResource(item).cause(e).build();
		}
		return priceUomId;
	}

	private InvoiceCandidateLookupKey createInvoiceCandidateLookupKey(@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		try
		{
			return InvoiceCandidateLookupKey.builder()
					.externalHeaderId(JsonExternalIds.toExternalIdOrNull(item.getExternalHeaderId()))
					.externalLineId(JsonExternalIds.toExternalIdOrNull(item.getExternalLineId()))
					.build();
		}
		catch (final AdempiereException e)
		{
			throw InvalidEntityException.wrapIfNeeded(e);
		}
	}

}
