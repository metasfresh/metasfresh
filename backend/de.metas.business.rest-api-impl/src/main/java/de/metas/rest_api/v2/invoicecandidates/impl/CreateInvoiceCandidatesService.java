/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.invoicecandidates.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.BPartnerInfo.BPartnerInfoBuilder;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v2.JsonDocTypeInfo;
import de.metas.common.rest_api.v2.JsonInvoiceRule;
import de.metas.common.rest_api.v2.JsonPrice;
import de.metas.document.DocBaseAndSubType;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.detail.InvoiceDetailItem;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.NewInvoiceCandidate;
import de.metas.invoicecandidate.NewInvoiceCandidate.NewInvoiceCandidateBuilder;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.externallyreferenced.InvoiceCandidate;
import de.metas.invoicecandidate.externallyreferenced.InvoiceCandidateLookupKey;
import de.metas.invoicecandidate.externallyreferenced.InvoiceCandidateRepository;
import de.metas.invoicecandidate.externallyreferenced.ManualCandidateService;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgIdNotFoundException;
import de.metas.organization.OrgQuery;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse.JsonCreateInvoiceCandidatesResponseBuilder;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidatesResponseItem;
import de.metas.rest_api.invoicecandidates.v2.request.JsonCreateInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.v2.request.JsonCreateInvoiceCandidatesRequestItem;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.DocTypeService;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.BPartnerCompositeRestUtils;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.bpartner.relation.BPRelationsService;
import de.metas.rest_api.v2.product.ExternalIdentifierResolver;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.web.exception.InvalidEntityException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
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

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.Check.isNotBlank;
import static java.math.BigDecimal.ZERO;

@Service
public class CreateInvoiceCandidatesService
{
	private final DocTypeService docTypeService;
	private final CurrencyService currencyService;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);
	private final InvoiceCandidateRepository invoiceCandidateRepository;
	private final ManualCandidateService manualCandidateService;
	private final ExternalIdentifierResolver externalIdentifierResolver;
	private final BPRelationsService bpRelationsService;
	private final JsonRetrieverService jsonRetrieverService;

	public CreateInvoiceCandidatesService(
			@NonNull final DocTypeService docTypeService,
			@NonNull final CurrencyService currencyService,
			@NonNull final ManualCandidateService manualCandidateService,
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final ExternalIdentifierResolver externalIdentifierResolver,
			@NonNull final BPRelationsService bpRelationsService,
			@NonNull final JsonServiceFactory jsonServiceFactory)
	{
		this.currencyService = currencyService;
		this.docTypeService = docTypeService;
		this.manualCandidateService = manualCandidateService;
		this.externalIdentifierResolver = externalIdentifierResolver;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.bpRelationsService = bpRelationsService;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
	}

	public JsonCreateInvoiceCandidatesResponse createInvoiceCandidates(@NonNull final JsonCreateInvoiceCandidatesRequest request)
	{
		final ImmutableMap<InvoiceCandidateLookupKey, JsonCreateInvoiceCandidatesRequestItem> lookupKey2Item = Maps.uniqueIndex(request.getItems(), this::createInvoiceCandidateLookupKey);

		// candidates
		final ImmutableList<InvoiceCandidate> //
				exitingCandidates = invoiceCandidateRepository.getAllBy(lookupKey2Item.keySet());
		failIfNotEmpty(exitingCandidates);

		final ImmutableList.Builder<NewInvoiceCandidate> candidatesToSave = ImmutableList.builder();
		for (final Entry<InvoiceCandidateLookupKey, JsonCreateInvoiceCandidatesRequestItem> keyWithItem : lookupKey2Item.entrySet())
		{
			final JsonCreateInvoiceCandidatesRequestItem item = keyWithItem.getValue();
			final NewInvoiceCandidate candidate = createCandidate(item);

			candidatesToSave.add(candidate);
		}

		final JsonCreateInvoiceCandidatesResponseBuilder result = JsonCreateInvoiceCandidatesResponse.builder();
		final ImmutableSet.Builder<InvoiceCandidateId> invoiceCandidateIds = ImmutableSet.builder();
		for (final NewInvoiceCandidate candidateToSave : candidatesToSave.build())
		{
			final JsonExternalId headerId = JsonExternalIds.ofOrNull(candidateToSave.getExternalHeaderId());
			final JsonExternalId lineId = JsonExternalIds.ofOrNull(candidateToSave.getExternalLineId());

			final InvoiceCandidateId candidateId = invoiceCandidateRepository.save(manualCandidateService.createInvoiceCandidate(candidateToSave));
			invoiceCandidateIds.add(candidateId);
			final JsonInvoiceCandidatesResponseItem responseItem = JsonInvoiceCandidatesResponseItem.builder()
					.externalHeaderId(headerId)
					.externalLineId(lineId)
					.metasfreshId(MetasfreshId.of(candidateId))
					.build();
			result.responseItem(responseItem);
		}

		return result.build();
	}

	private void failIfNotEmpty(@NonNull final ImmutableList<InvoiceCandidate> exitingCandidates)
	{
		if (!exitingCandidates.isEmpty())
		{
			throw new InvalidEntityException(TranslatableStrings.constant("InvoiceCandidate(s) already exist"))
					.appendParametersToMessage();
		}
	}

	private NewInvoiceCandidate createCandidate(@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final NewInvoiceCandidateBuilder candidate = NewInvoiceCandidate.builder();

		final OrgId orgId = syncOrgIdToCandidate(candidate, item);
		final ProductId productId = syncProductToCandidate(candidate, orgId, item);

		syncBPartnerToCandidate(candidate, orgId, item);

		syncTargetDocTypeToCandidate(candidate, orgId, item.getInvoiceDocType());

		syncDiscountOverrideToCandidate(candidate, item.getDiscountOverride());

		syncPriceEnteredOverrideToCandidate(candidate, productId, item);

		// poReference
		if (isNotBlank(item.getPoReference()))
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
		final UomId uomId = lookupUomId(
				X12DE355.ofNullableCode(item.getUomCode()),
				productId,
				item);
		candidate.invoicingUomId(uomId);

		// qtyOrdered
		if (item.getQtyOrdered() != null)
		{

			final StockQtyAndUOMQty qtyOrdered = StockQtyAndUOMQtys.createConvert(
					Quantitys.of(item.getQtyOrdered(), uomId),
					productId,
					uomId);
			candidate.qtyOrdered(qtyOrdered);
		}
		else
		{
			throw new MissingPropertyException("qtyOrdered", item);
		}

		// qtyDelivered
		final BigDecimal qtyDeliveredEff = coalesceNotNull(item.getQtyDelivered(), ZERO);
		final StockQtyAndUOMQty qtyDelivered = StockQtyAndUOMQtys.createConvert(
				Quantitys.of(qtyDeliveredEff, uomId),
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
					.map(jsonDetail -> InvoiceDetailItem.builder()
							.seqNo(jsonDetail.getSeqNo())
							.label(jsonDetail.getLabel())
							.description(jsonDetail.getDescription())
							.date(jsonDetail.getDate())
							.price(jsonDetail.getPrice())
							.note(jsonDetail.getNote())
							.orgId(orgId)
							.build())
					.collect(Collectors.toList());

			candidate.invoiceDetailItems(invoiceDetailItems);
		}

		candidate.isManual(true);
		candidate.handlerId(invoiceCandidateHandlerDAO.retrieveIdForClassOneOnly(ManualCandidateHandler.class));

		return candidate
				.externalHeaderId(JsonExternalIds.toExternalId(item.getExternalHeaderId()))
				.externalLineId(JsonExternalIds.toExternalId(item.getExternalLineId()))
				.build();
	}

	@Nullable
	private ProductId syncProductToCandidate(
			@NonNull final NewInvoiceCandidateBuilder candidate,
			@NonNull final OrgId orgId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final ExternalIdentifier productIdentifier = ExternalIdentifier.ofOrNull(item.getProductIdentifier());
		if (productIdentifier == null)
		{
			return null;
		}

		final ProductId productId = externalIdentifierResolver.resolveProductExternalIdentifier(productIdentifier, orgId).orElseThrow(() -> MissingResourceException.builder()
				.resourceName("product")
				.resourceIdentifier(productIdentifier.toString())
				.parentResource(item)
				.build());

		candidate.productId(productId);
		return productId;

	}

	private OrgId syncOrgIdToCandidate(
			@NonNull final NewInvoiceCandidateBuilder candidate,
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
				throw MissingResourceException.builder()
						.resourceName("organisation")
						.resourceIdentifier(item.getOrgCode())
						.parentResource(item)
						.cause(e)
						.build();
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
			@NonNull final NewInvoiceCandidateBuilder candidate,
			@NonNull final OrgId orgId,
			@Nullable final JsonDocTypeInfo docType)
	{
		if (docType == null)
		{
			return;
		}
		final DocBaseAndSubType docBaseAndSubType = DocBaseAndSubType.of(docType.getDocBaseType(), docType.getDocSubType());

		candidate.invoiceDocTypeId(docTypeService.getInvoiceDocTypeId(docBaseAndSubType, orgId));
	}

	private void syncBPartnerToCandidate(
			@NonNull final NewInvoiceCandidateBuilder candidate,
			@NonNull final OrgId orgId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final BPartnerInfoBuilder bpartnerInfo = BPartnerInfo.builder();

		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.ofOrNull(item.getBillPartnerIdentifier());
		if (bpartnerIdentifier == null)
		{
			throw new MissingPropertyException("billPartnerIdentifier", item);
		}
		final BPartnerComposite bpartnerComposite = jsonRetrieverService.getBPartnerComposite(orgId, bpartnerIdentifier).orElseThrow(() -> MissingResourceException.builder()
				.resourceName("billPartner")
				.resourceIdentifier(bpartnerIdentifier.toString())
				.parentResource(item)
				.build());

		bpartnerInfo.bpartnerId(bpartnerComposite.getBpartner().getId());

		final ExternalIdentifier billLocationIdentifier = ExternalIdentifier.ofOrNull(item.getBillLocationIdentifier());
		if (billLocationIdentifier == null)
		{
			final BPartnerLocation location = bpartnerComposite
					.extractBillToLocation()
					.orElseThrow(() -> MissingResourceException.builder()
							.resourceName("billLocation")
							.parentResource(item)
							.build());
			bpartnerInfo.bpartnerLocationId(location.getId());
		}
		else
		{
			final BPartnerLocationId billLocationId = bpRelationsService.getBpartnerLocation(bpartnerIdentifier, billLocationIdentifier, bpartnerComposite)
					.map(BPartnerLocation::getId)
					.orElseThrow(() -> MissingResourceException.builder()
							.resourceName("billLocation")
							.resourceIdentifier(billLocationIdentifier.toString())
							.parentResource(item)
							.build());
			bpartnerInfo.bpartnerLocationId(billLocationId);
		}

		final ExternalIdentifier billContactIdentifier = ExternalIdentifier.ofOrNull(item.getBillContactIdentifier());
		if (billContactIdentifier == null)
		{
			bpartnerInfo.contactId(null); // that's OK, because the contact is not mandatory in C_Invoice_Candidate
		}
		else
		{
			// extract the composite's location that has the given billContactIdentifier
			final BPartnerContactId billContactId = bpRelationsService.getBpartnerContact(billContactIdentifier, bpartnerComposite)
					.map(BPartnerContact::getId)
					.orElseThrow(() -> MissingResourceException.builder()
							.resourceName("billContact")
							.resourceIdentifier(billContactIdentifier.toString())
							.parentResource(item)
							.build());

			bpartnerInfo.contactId(billContactId);
		}

		final BPartnerInfo build = bpartnerInfo.build();
		candidate.billPartnerInfo(build);

		final PaymentTermQuery paymentTermQuery = PaymentTermQuery.forPartner(build.getBpartnerId(), SOTrx.ofBoolean(item.getSoTrx().isSales()));
		final PaymentTermId paymentTermId = paymentTermRepository.retrievePaymentTermIdNotNull(paymentTermQuery);
		candidate.paymentTermId(paymentTermId);
	}

	private void syncDiscountOverrideToCandidate(
			@NonNull final NewInvoiceCandidateBuilder candidate,
			@Nullable final BigDecimal discountOverride)
	{
		candidate.discountOverride(Percent.ofNullable(discountOverride));
	}

	private void syncInvoiceRuleOverrideToCandidate(
			@NonNull final NewInvoiceCandidateBuilder candidate,
			@Nullable final JsonInvoiceRule invoiceRuleOverride)
	{
		candidate.invoiceRuleOverride(BPartnerCompositeRestUtils.getInvoiceRule(invoiceRuleOverride));
	}

	private void syncPriceEnteredOverrideToCandidate(
			@NonNull final NewInvoiceCandidateBuilder candidate,
			@NonNull final ProductId productId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		final JsonPrice priceEnteredOverride = item.getPriceEnteredOverride();

		final ProductPrice price = createProductPriceOrNull(priceEnteredOverride, productId, item);
		candidate.priceEnteredOverride(price);

	}

	@Nullable
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

		final UomId priceUomId = lookupUomId(
				X12DE355.ofNullableCode(jsonPrice.getPriceUomCode()),
				productId,
				item);

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
			@Nullable final X12DE355 uomCode,
			@NonNull final ProductId productId,
			@NonNull final JsonCreateInvoiceCandidatesRequestItem item)
	{
		if (uomCode == null)
		{
			return productBL.getStockUOMId(productId);
		}

		final UomId priceUomId;
		try
		{
			priceUomId = uomDAO.getUomIdByX12DE355(uomCode);
		}
		catch (final AdempiereException e)
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
