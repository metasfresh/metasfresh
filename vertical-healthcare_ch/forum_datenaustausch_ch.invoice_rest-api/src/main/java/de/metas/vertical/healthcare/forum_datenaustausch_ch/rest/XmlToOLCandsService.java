package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXPORT_PROVIDER;
import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXTERNAL_REFERENCE;
import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.assumeNotNull;
import static de.metas.util.lang.CoalesceUtil.coalesce;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;

import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner.JsonRequestBPartnerBuilder;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.bpartner.request.JsonRequestLocation.JsonRequestLocationBuilder;
import de.metas.rest_api.common.JsonDocTypeInfo;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.SyncAdvise;
import de.metas.rest_api.ordercandidates.OrderCandidatesRestEndpoint;
import de.metas.rest_api.ordercandidates.request.JSONPaymentRule;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateBulkRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest.OrderDocType;
import de.metas.rest_api.ordercandidates.request.JsonOrganization;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo.Type;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact.JsonRequestBPartnerLocationAndContactBuilder;
import de.metas.rest_api.ordercandidates.response.JsonAttachment;
import de.metas.rest_api.ordercandidates.response.JsonOLCand;
import de.metas.rest_api.ordercandidates.response.JsonOLCandCreateBulkResponse;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.exceptions.InvalidXMLException;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BillerAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BodyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.CompanyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InsuranceAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InvoiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.OnlineAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PayloadType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PostalAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordDRGType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordDrugType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordLabType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordMigelType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordOtherType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordParamedType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordTarmedType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ServicesType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ZipType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.JaxbUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_rest-api
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
@Conditional(RestApiStartupCondition.class)
public class XmlToOLCandsService
{
	private static final String CURRENCY_CODE = "CHF";

	private static final String UOM_CODE = "MJ";  // minute; HUR would be hour

	private final OrderCandidatesRestEndpoint orderCandidatesRestEndpoint;

	public XmlToOLCandsService(@NonNull final OrderCandidatesRestEndpoint orderCandidatesRestEndpoint)
	{
		this.orderCandidatesRestEndpoint = orderCandidatesRestEndpoint;
	}

	public JsonAttachment createOLCands(@NonNull final CreateOLCandsRequest createOLCandsRequest)
	{
		final MultipartFile xmlInvoiceFile = createOLCandsRequest.getXmlInvoiceFile();

		final RequestType xmlInvoice = unmarshal(xmlInvoiceFile);

		final JsonOLCandCreateBulkRequest jsonOLCandCreateBulkRequest = createJsonOLCandCreateBulkRequest(
				xmlInvoice,
				createOLCandsRequest.getBillerSyncAdvise(),
				createOLCandsRequest.getDebitorSyncAdvise(),
				createOLCandsRequest.getProductSyncAdvise());

		final ResponseEntity<JsonOLCandCreateBulkResponse> orderCandidates = orderCandidatesRestEndpoint.createOrderLineCandidates(jsonOLCandCreateBulkRequest);

		final String externalHeaderId = CollectionUtils.extractSingleElement(
				orderCandidates.getBody().getResult(),
				JsonOLCand::getExternalHeaderId);

		final ResponseEntity<JsonAttachment> result = attachXmlToOLCandidates(xmlInvoiceFile, externalHeaderId);
		return result.getBody();
	}

	@Value
	@Builder
	public static class CreateOLCandsRequest
	{
		MultipartFile xmlInvoiceFile;

		SyncAdvise billerSyncAdvise;
		SyncAdvise debitorSyncAdvise;
		SyncAdvise productSyncAdvise;
	}

	private static RequestType unmarshal(@NonNull final MultipartFile file)
	{
		final InputStream xmlInput;
		try
		{
			xmlInput = file.getInputStream();
		}
		catch (final IOException e)
		{
			throw new XmlInvoiceInputStreamException(e);
		}

		try
		{
			final JAXBElement<RequestType> request = JaxbUtil.unmarshalToJaxbElement(xmlInput, RequestType.class);
			return request.getValue();
		}
		catch (final RuntimeException e)
		{
			throw new XmlInvoiceUnmarshalException(e);
		}
	}

	private ResponseEntity<JsonAttachment> attachXmlToOLCandidates(
			@NonNull final MultipartFile xmlInvoiceFile,
			@NonNull final String externalReference)
	{
		try
		{
			final ImmutableList<String> tags = ImmutableList.of(
					ATTATCHMENT_TAGNAME_EXPORT_PROVIDER/* name */, ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID/* value */,
					ATTATCHMENT_TAGNAME_EXTERNAL_REFERENCE/* name */, externalReference/* value */);

			return orderCandidatesRestEndpoint.attachFile(
					RestApiConstants.INPUT_SOURCE_INTERAL_NAME,
					externalReference,
					tags,
					xmlInvoiceFile);
		}
		catch (final IOException e)
		{
			throw new XmlInvoiceAttachException(e);
		}
	}

	public static class XmlInvoiceInputStreamException extends RuntimeException
	{
		private static final long serialVersionUID = 8216181888558013882L;

		public XmlInvoiceInputStreamException(@NonNull final Throwable cause)
		{
			super("An error occurred while trying access the XML invoice input stream", cause);
		}
	}

	public static class XmlInvoiceUnmarshalException extends RuntimeException
	{
		private static final long serialVersionUID = 8216181888558013882L;

		public XmlInvoiceUnmarshalException(@NonNull final Throwable cause)
		{
			super("An error occurred while trying to unmarshal the invoice XML data", cause);
		}
	}

	public static class XmlInvalidRequestIdException extends RuntimeException
	{
		private static final long serialVersionUID = -4688552956794873772L;

		@Getter
		private final String invalidRequestId;

		public XmlInvalidRequestIdException(@Nullable final String invalidRequestId)
		{
			super("Invalid invoice request_id=" + invalidRequestId + "; it has to start with KV_ or KT_");
			this.invalidRequestId = invalidRequestId;
		}
	}

	public static class XmlInvoiceAttachException extends RuntimeException
	{
		private static final long serialVersionUID = 2013021164753485741L;

		public XmlInvoiceAttachException(@NonNull final Throwable cause)
		{
			super("An error occurred while trying attach the XML data to the order line candidates", cause);
		}
	}

	@VisibleForTesting
	JsonOLCandCreateBulkRequest createJsonOLCandCreateBulkRequest(
			@NonNull final RequestType xmlInvoice,
			@NonNull final SyncAdvise billerSyncAdvise,
			@NonNull final SyncAdvise debitorSyncAdvise,
			@NonNull final SyncAdvise productsSyncAdvise)
	{
		final JsonOLCandCreateRequestBuilder requestBuilder = JsonOLCandCreateRequest
				.builder()
				.dataSource("int-" + RestApiConstants.INPUT_SOURCE_INTERAL_NAME)
				.dataDest("int-" + InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME)
				.shipper("val-DPD - Classic")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.Paypal)
				.orderDocType(OrderDocType.SalesOrder);

		final String invoiceRecipientEAN = extractRecipientEAN(xmlInvoice);
		final HighLevelContext context = HighLevelContext.builder()
				.billerSyncAdvise(billerSyncAdvise)
				.debitorSyncAdvise(debitorSyncAdvise)
				.productsSyncAdvise(productsSyncAdvise)
				.invoiceRecipientEAN(invoiceRecipientEAN)
				.build();

		final List<JsonOLCandCreateRequest> requests = insertPayloadIntoBuilders(
				requestBuilder,
				xmlInvoice.getPayload(),
				context);

		return JsonOLCandCreateBulkRequest
				.builder()
				.requests(requests)
				.build();
	}

	/** Contains context-infos from high-up in the call-hierarchy */
	@Value
	@Builder
	private static class HighLevelContext
	{
		@NonNull
		String invoiceRecipientEAN;

		@NonNull
		SyncAdvise billerSyncAdvise;
		@NonNull
		SyncAdvise debitorSyncAdvise;
		@NonNull
		SyncAdvise productsSyncAdvise;
	}

	private String extractRecipientEAN(@NonNull final RequestType xmlInvoice)
	{
		final String invoiceRecipientEAN = xmlInvoice
				.getProcessing()
				.getTransport()
				.getTo();
		return invoiceRecipientEAN;
	}

	/**
	 * @param requestBuilder serves as a prototype for the list of requestBuilders that are returned by this function
	 */
	private List<JsonOLCandCreateRequest> insertPayloadIntoBuilders(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final PayloadType payload,
			@NonNull final HighLevelContext context)
	{
		final IPair<String, String> docSubTypeAndPOReference = createPOReference(payload);

		requestBuilder.poReference(docSubTypeAndPOReference.getRight());

		requestBuilder.invoiceDocType(createJsonDocTypeInfo(docSubTypeAndPOReference.getLeft()));

		insertInoviceIntoBuilder(
				requestBuilder,
				payload.getInvoice());

		final ImmutableList<JsonOLCandCreateRequestBuilder> builders = insertBodyIntoBuilders(
				requestBuilder,
				payload.getBody(),
				context);

		final ImmutableList<JsonOLCandCreateRequest> requests = builders
				.stream()
				.map(JsonOLCandCreateRequestBuilder::build)
				.collect(ImmutableList.toImmutableList());
		return requests;
	}

	/**
	 * @return a pair consisting of invoice {@code DocSubType} and {@code POreference}.
	 *         TODO the hardcoded way of getting the invoice's {@code DocSubType} from the XML is not cool, but I want to keep it for now. Ideas of how to solve this in future are centered around linking the doc type to the invoice recipient
	 */
	private IPair<String, String> createPOReference(@NonNull final PayloadType payload)
	{
		final InvoiceType invoice = payload.getInvoice();

		final String requestIdToUse = Check.assumeNotEmpty(
				invoice.getRequestId(),
				InvalidXMLException.class, "payload/invoice/requestId may not be empty");

		final IPair<String, String> result;
		if (requestIdToUse.startsWith("KV_"))
		{
			result = ImmutablePair.of("KV", requestIdToUse.substring("KV_".length()));
		}
		else if (requestIdToUse.startsWith("KT_"))
		{
			result = ImmutablePair.of("KT", requestIdToUse.substring("KT_".length()));
		}
		else
		{
			throw new XmlInvalidRequestIdException(requestIdToUse);
		}
		return result;
	}

	private void insertInoviceIntoBuilder(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final InvoiceType invoice)
	{
		final LocalDate dateInvoiced = TimeUtil.asLocalDate(invoice.getRequestDate());
		requestBuilder.dateRequired(dateInvoiced); // this will be dateOrdered and dateDelivered
		requestBuilder.presetDateInvoiced(dateInvoiced);
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertBodyIntoBuilders(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final BodyType body,
			@NonNull final HighLevelContext context)
	{
		final JsonOLCandCreateRequestBuilder //
		invoiceRecipientBuilder = insertInsuranceAndBillerIntoBuilder(
				requestBuilder,
				getInsuranceAndBiller(body),
				context);

		invoiceRecipientBuilder.externalHeaderId(createExternalHeaderId(invoiceRecipientBuilder));

		final ImmutableList<JsonOLCandCreateRequestBuilder> allBuilders = insertServicesIntoBuilder(
				invoiceRecipientBuilder,
				body.getServices(),
				context);

		return allBuilders;
	}

	private InsuranceAndBiller getInsuranceAndBiller(@NonNull final BodyType body)
	{
		final boolean tiersGarantIsSet = body.getTiersGarant() != null;
		final boolean tiersPayantIsSet = body.getTiersPayant() != null;

		Check.errorUnless(tiersGarantIsSet ^ tiersPayantIsSet,
				"One of TiersGarant or TiersPayant needs to be provided but not both; tiersGarantIsSet={}; tiersPayantIsSet={} ",
				tiersGarantIsSet, tiersPayantIsSet);

		if (tiersGarantIsSet)
		{
			return new InsuranceAndBiller(
					body.getTiersGarant().getInsurance(),
					body.getTiersGarant().getBiller());
		}

		// tiersPayantIsSet
		return new InsuranceAndBiller(
				body.getTiersPayant().getInsurance(),
				body.getTiersPayant().getBiller());
	}

	private JsonOLCandCreateRequestBuilder insertInsuranceAndBillerIntoBuilder(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final InsuranceAndBiller insuranceAndBiller,
			@NonNull final HighLevelContext context)
	{
		final JsonRequestBPartnerLocationAndContact recipientBPartnerInfo = createJsonBPartnerInfo(
				insuranceAndBiller.getInsurance(),
				context);

		final JsonOrganization billerOrgInfo = createBillerOrg(
				insuranceAndBiller.getBiller(),
				context);

		final JsonOLCandCreateRequestBuilder insuranceBuilder = copyBuilder(requestBuilder)
				.bpartner(recipientBPartnerInfo)
				.org(billerOrgInfo);

		return insuranceBuilder;
	}

	@Value
	private static class InsuranceAndBiller
	{
		@NonNull
		final InsuranceAddressType insurance;

		@NonNull
		final BillerAddressType biller;
	}

	private JsonOrganization createBillerOrg(
			@NonNull final BillerAddressType biller,
			@NonNull final HighLevelContext context)
	{
		final Name name = createName(biller);

		final JsonOrganization org = JsonOrganization
				.builder()
				.syncAdvise(context.getBillerSyncAdvise())
				.code(createBPartnerExternalId(biller).getValue())
				.name(name.getSingleStringName())
				.bpartner(createJsonBPartnerInfo(biller, context))
				.build();
		return org;
	}

	private JsonDocTypeInfo createJsonDocTypeInfo(@NonNull final String docSubType)
	{
		return JsonDocTypeInfo.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_ARInvoice)
				.docSubType(docSubType)
				.build();
	}

	private JsonRequestBPartnerLocationAndContact createJsonBPartnerInfo(
			@NonNull final InsuranceAddressType insurance,
			@NonNull final HighLevelContext context)
	{
		final CompanyType company = insurance.getCompany();
		final JsonExternalId insuranceExternalId = createBPartnerExternalId(insurance);
		final JsonExternalId recipientExternalId = createBPartnerExternalId(context.getInvoiceRecipientEAN());

		final JsonRequestBPartnerBuilder bPartner = JsonRequestBPartner
				.builder()
				.externalId(recipientExternalId);

		final JsonRequestBPartnerLocationAndContactBuilder bPartnerInfo = JsonRequestBPartnerLocationAndContact.builder();

		if (recipientExternalId.equals(insuranceExternalId))
		{
			final JsonRequestLocation location = createJsonBPartnerLocation(
					insuranceExternalId,
					insurance.getEanParty(),
					company.getPostal());
			bPartner.name(company.getCompanyname());

			return bPartnerInfo
					.syncAdvise(context.getDebitorSyncAdvise())
					.bpartner(bPartner.build())
					.location(location)
					.build();
		}

		// build a "sparse" bPartner that is only suitable for lookup, because we don't have the masterdata needed to insert or update
		final JsonRequestLocation location = JsonRequestLocation.builder()
				.gln(context.getInvoiceRecipientEAN())
				.externalId(createLocationExternalId(recipientExternalId))
				.build();

		return bPartnerInfo
				.syncAdvise(SyncAdvise.READ_ONLY)
				.bpartner(bPartner.build())
				.location(location)
				.build();

	}

	private JsonRequestBPartnerLocationAndContact createJsonBPartnerInfo(
			@NonNull final BillerAddressType biller,
			@NonNull final HighLevelContext context)
	{
		final Name name = createName(biller);

		final CompanyType company = biller.getCompany();
		final JsonExternalId billerBPartnerExternalId = createBPartnerExternalId(biller);

		final JsonRequestBPartnerBuilder bPartnerBuilder = JsonRequestBPartner
				.builder()
				.name(name.getSingleStringName())
				.externalId(billerBPartnerExternalId);

		final JsonRequestLocation location;
		final String email;
		if (company != null)
		{
			bPartnerBuilder.companyName(company.getCompanyname());

			email = extracFirsttEmailOrNull(company.getOnline());

			location = createJsonBPartnerLocation(
					billerBPartnerExternalId,
					biller.getEanParty(),
					company.getPostal());
		}
		else
		{ // biller.getPerson() != null
			email = extracFirsttEmailOrNull(biller.getPerson().getOnline());

			location = createJsonBPartnerLocation(
					billerBPartnerExternalId,
					biller.getEanParty(),
					biller.getPerson().getPostal());
		}

		final JsonRequestContact contact = JsonRequestContact
				.builder()
				.externalId(JsonExternalId.of(billerBPartnerExternalId + "_singlePerson"))
				.firstName(name.getFirstName())
				.lastName(coalesce(name.getLastName(), name.getSingleStringName()))
				.name(name.getSingleStringName())
				.email(email)
				.build();

		final JsonRequestBPartnerLocationAndContact bPartnerInfo = JsonRequestBPartnerLocationAndContact.builder()
				.syncAdvise(context.getBillerSyncAdvise())
				.bpartner(bPartnerBuilder.build())
				.contact(contact)
				.location(location)
				.build();

		return bPartnerInfo;
	}

	private Name createName(@NonNull final BillerAddressType biller)
	{
		final Name.NameBuilder name = Name.builder();

		final String orgName;
		if (biller.getCompany() != null)
		{
			orgName = biller.getCompany().getCompanyname();
		}
		else
		{
			orgName = biller.getPerson().getGivenname() + " " + biller.getPerson().getFamilyname();

			name.firstName(biller.getPerson().getGivenname());
			name.lastName(biller.getPerson().getFamilyname());
		}
		name.singleStringName(orgName);

		return name.build();
	}

	@Value
	@Builder
	private static class Name
	{
		String firstName;
		String lastName;

		@NonNull
		String singleStringName;
	}

	private String extracFirsttEmailOrNull(@Nullable final OnlineAddressType online)
	{
		if (online == null || online.getEmail().isEmpty())
		{
			return null;
		}
		return online.getEmail().get(0);
	}

	private JsonExternalId createBPartnerExternalId(@NonNull final String eanParty)
	{
		return JsonExternalId.of("EAN-" + eanParty);
	}

	private JsonExternalId createBPartnerExternalId(@NonNull final InsuranceAddressType insurance)
	{
		return JsonExternalId.of("EAN-" + insurance.getEanParty());
	}

	private JsonExternalId createBPartnerExternalId(@NonNull final BillerAddressType biller)
	{
		return JsonExternalId.of("EAN-" + biller.getEanParty());
	}

	private JsonRequestLocation createJsonBPartnerLocation(
			@NonNull final JsonExternalId bPartnerExternalId,
			@NonNull final String gln,
			@NonNull final PostalAddressType postal)
	{
		final String street = StringUtils.trim(postal.getStreet());
		final String pobox = StringUtils.trim(postal.getPobox());
		final String city = StringUtils.trim(postal.getCity());

		final ZipType zip = postal.getZip();
		final String statecode = zip != null ? StringUtils.trim(zip.getStatecode()) : null;
		final String countrycode = zip != null ? StringUtils.trim(zip.getCountrycode()) : null;

		final JsonRequestLocationBuilder builder = JsonRequestLocation.builder();

		if (!Check.isEmpty(street, true))
		{
			builder.address1(street)
					.address2(pobox);
		}
		else
		{
			builder.address1(pobox);
		}

		if (!Check.isEmpty(gln, true))
		{
			builder.gln(gln);
		}

		final JsonRequestLocation location = builder
				.externalId(createLocationExternalId(bPartnerExternalId)) // TODO
				.city(city)
				.postal(zip.getValue())
				.region(statecode)
				.postal(statecode)
				.countryCode(coalesce(countrycode, "CH")) // TODO
				.build();
		return location;
	}

	private JsonExternalId createLocationExternalId(@NonNull final JsonExternalId bPartnerExternalId)
	{
		return JsonExternalId.of(bPartnerExternalId.getValue() + "_singleAddress");
	}

	JsonOLCandCreateRequestBuilder copyBuilder(@NonNull final JsonOLCandCreateRequestBuilder builder)
	{
		return builder
				.build()
				.toBuilder();
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertServicesIntoBuilder(
			@NonNull final JsonOLCandCreateRequestBuilder invoiceRecipientBuilder,
			@NonNull final ServicesType services,
			@NonNull final HighLevelContext context)
	{
		final ImmutableList.Builder<JsonOLCandCreateRequestBuilder> result = ImmutableList.builder();

		final List<Object> records = services.getRecordTarmedOrRecordDrgOrRecordLab();
		for (final Object record : records)
		{
			result.addAll(insertServiceRecordIntoBuilder(invoiceRecipientBuilder, record, context));
		}

		return result.build();
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertServiceRecordIntoBuilder(
			@NonNull final JsonOLCandCreateRequestBuilder invoiceRecipientBuilder,
			@NonNull final Object record,
			@NonNull final HighLevelContext context)
	{
		final ImmutableList.Builder<JsonOLCandCreateRequestBuilder> result = ImmutableList.builder();

		final String externalLineId;
		final JsonProductInfo product;
		final BigDecimal price;
		final BigDecimal quantity;
		if (record instanceof RecordTarmedType)
		{
			final RecordTarmedType recordTarmedType = (RecordTarmedType)record;

			externalLineId = createExternalId(invoiceRecipientBuilder, recordTarmedType.getRecordId());
			product = createProduct(recordTarmedType.getCode(), recordTarmedType.getName(), context);
			price = recordTarmedType.getAmount();
			quantity = ONE;
		}
		else if (record instanceof RecordDRGType)
		{
			final RecordDRGType recordDRGType = (RecordDRGType)record;

			externalLineId = createExternalId(invoiceRecipientBuilder, recordDRGType.getRecordId());
			product = createProduct(recordDRGType.getCode(), recordDRGType.getName(), context);
			price = createPrice(recordDRGType.getUnit(), recordDRGType.getUnitFactor(), recordDRGType.getExternalFactor());
			quantity = recordDRGType.getQuantity();
		}
		else if (record instanceof RecordLabType)
		{
			final RecordLabType recordLabType = (RecordLabType)record;

			externalLineId = createExternalId(invoiceRecipientBuilder, recordLabType.getRecordId());
			product = createProduct(recordLabType.getCode(), recordLabType.getName(), context);
			price = createPrice(recordLabType.getUnit(), recordLabType.getUnitFactor(), recordLabType.getExternalFactor());
			quantity = recordLabType.getQuantity();
		}
		else if (record instanceof RecordMigelType)
		{
			final RecordMigelType recordMigelType = (RecordMigelType)record;

			externalLineId = createExternalId(invoiceRecipientBuilder, recordMigelType.getRecordId());
			product = createProduct(recordMigelType.getCode(), recordMigelType.getName(), context);
			price = createPrice(recordMigelType.getUnit(), recordMigelType.getUnitFactor(), recordMigelType.getExternalFactor());
			quantity = recordMigelType.getQuantity();
		}
		else if (record instanceof RecordParamedType)
		{
			final RecordParamedType recordParamedOtherType = (RecordParamedType)record;

			externalLineId = createExternalId(invoiceRecipientBuilder, recordParamedOtherType.getRecordId());
			product = createProduct(recordParamedOtherType.getCode(), recordParamedOtherType.getName(), context);
			price = createPrice(recordParamedOtherType.getUnit(), recordParamedOtherType.getUnitFactor(), recordParamedOtherType.getExternalFactor());
			quantity = recordParamedOtherType.getQuantity();
		}
		else if (record instanceof RecordDrugType)
		{
			final RecordDrugType recordDrugType = (RecordDrugType)record;

			externalLineId = createExternalId(invoiceRecipientBuilder, recordDrugType.getRecordId());
			product = createProduct(recordDrugType.getCode(), recordDrugType.getName(), context);
			price = createPrice(recordDrugType.getUnit(), recordDrugType.getUnitFactor(), recordDrugType.getExternalFactor());
			quantity = recordDrugType.getQuantity();
		}
		else if (record instanceof RecordOtherType)
		{
			final RecordOtherType recordOtherType = (RecordOtherType)record;

			externalLineId = createExternalId(invoiceRecipientBuilder, recordOtherType.getRecordId());
			product = createProduct(recordOtherType.getCode(), recordOtherType.getName(), context);
			price = createPrice(recordOtherType);
			quantity = recordOtherType.getQuantity();

		}
		else
		{
			Check.fail("Unexpected record type={}", record);
			return null;
		}
		final JsonOLCandCreateRequestBuilder serviceRecordBuilder = copyBuilder(invoiceRecipientBuilder);
		serviceRecordBuilder
				.externalLineId(externalLineId)
				.product(product)
				.price(price)
				.currencyCode(CURRENCY_CODE)
				// the UOM shall be taken from the product-masterdata, because we don't really know it from the XML file
				// .uomCode(UOM_CODE)
				.discount(ZERO)
				.qty(quantity);

		result.add(serviceRecordBuilder);

		return result.build();
	}

	private String createExternalHeaderId(@NonNull final JsonOLCandCreateRequestBuilder requestBuilder)
	{
		final JsonOLCandCreateRequest request = requestBuilder.build();

		final String poReference = assumeNotEmpty(request.getPoReference(), "request.poReference may not be empty; request={}", request);
		final JsonExternalId billerExternalId = assumeNotNull(request.getOrg().getBpartner().getBpartner().getExternalId(), "request.org.bpartner.bpartner.externalId may not be null; request={}", request);
		final JsonExternalId billRecipientExternalId = assumeNotNull(request.getBpartner().getBpartner().getExternalId(), "request.bpartner.bpartner.externalId may not be null; request={}", request);

		return ""
				+ poReference // probably the "diversest" property
				+ "_"
				+ billerExternalId.getValue() // biller, might be the same for different bill receivers
				+ "_"
				+ billRecipientExternalId.getValue(); // bill receiver, might be the same for different billers
	}

	private String createExternalId(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			final int recordId)
	{
		final JsonOLCandCreateRequest request = requestBuilder.build();

		final String externalHeaderId = assumeNotEmpty(request.getExternalHeaderId(), "request.externalHeaderId may not be empty; request={}", request);
		return ""
				+ externalHeaderId
				+ "_"
				+ recordId;
	}

	private JsonProductInfo createProduct(
			@NonNull final String productCode,
			@NonNull final String productName,
			@NonNull final HighLevelContext context)
	{
		final JsonProductInfo product = JsonProductInfo.builder()
				.syncAdvise(context.getProductsSyncAdvise())
				.code(productCode)
				.name(productName)
				.type(Type.SERVICE)
				.uomCode(UOM_CODE)
				.build();
		return product;
	}

	private BigDecimal createPrice(@NonNull final RecordOtherType recordOtherType)
	{
		return createPrice(
				recordOtherType.getUnit(),
				recordOtherType.getUnitFactor(),
				recordOtherType.getExternalFactor());
	}

	private BigDecimal createPrice(
			@Nullable final BigDecimal unit,
			@Nullable final BigDecimal unitFactor,
			@Nullable final BigDecimal externalFactor)
	{
		final BigDecimal unitToUse = coalesce(unit, ONE); // tax point (TP) of the applied service
		final BigDecimal unitFactorToUse = coalesce(unitFactor, ONE); // tax point value (TPV) of the applied service
		final BigDecimal externalFactorToUse = coalesce(externalFactor, ONE);

		final BigDecimal price = unitToUse.multiply(unitFactorToUse).multiply(externalFactorToUse);
		return price;
	}
}
