package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXPORT_PROVIDER;
import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXTERNAL_REFERENCE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.compiere.util.Util.coalesce;

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
import de.metas.ordercandidate.rest.JsonAttachment;
import de.metas.ordercandidate.rest.JsonBPartner;
import de.metas.ordercandidate.rest.JsonBPartner.JsonBPartnerBuilder;
import de.metas.ordercandidate.rest.JsonBPartnerContact;
import de.metas.ordercandidate.rest.JsonBPartnerInfo;
import de.metas.ordercandidate.rest.JsonBPartnerInfo.JsonBPartnerInfoBuilder;
import de.metas.ordercandidate.rest.JsonBPartnerLocation;
import de.metas.ordercandidate.rest.JsonBPartnerLocation.JsonBPartnerLocationBuilder;
import de.metas.ordercandidate.rest.JsonDocTypeInfo;
import de.metas.ordercandidate.rest.JsonOLCand;
import de.metas.ordercandidate.rest.JsonOLCandCreateBulkRequest;
import de.metas.ordercandidate.rest.JsonOLCandCreateBulkResponse;
import de.metas.ordercandidate.rest.JsonOLCandCreateRequest;
import de.metas.ordercandidate.rest.JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder;
import de.metas.ordercandidate.rest.JsonOrganization;
import de.metas.ordercandidate.rest.JsonProductInfo;
import de.metas.ordercandidate.rest.JsonProductInfo.Type;
import de.metas.ordercandidate.rest.OrderCandidatesRestEndpoint;
import de.metas.ordercandidate.rest.SyncAdvise;
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
				.dataSourceInternalName(RestApiConstants.INPUT_SOURCE_INTERAL_NAME)
				.dataDestInternalName(InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);

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

		requestBuilder.externalHeaderId(createExternalHeaderId(payload));

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

	private String createExternalHeaderId(@NonNull final PayloadType payload)
	{
		final IPair<String, String> docSubTypeAndPOReference = createPOReference(payload);
		final String requestIdToUse = docSubTypeAndPOReference.getRight();

		final BillerAddressType biller;

		final BodyType body = payload.getBody();
		if (body.getTiersGarant() != null)
		{
			biller = body.getTiersGarant().getBiller();
		}
		else
		{
			biller = body.getTiersPayant().getBiller();
		}

		final String billerEAN = Check.assumeNotEmpty(
				biller.getEanParty(),
				InvalidXMLException.class, "payload/invoice/requestId may not be empty");
		return billerEAN + "_" + requestIdToUse;
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
		requestBuilder.dateInvoiced(dateInvoiced);
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertBodyIntoBuilders(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final BodyType body,
			@NonNull final HighLevelContext context)
	{
		final ImmutableList<JsonOLCandCreateRequestBuilder> //
		invoiceRecipientBuilders = insertInsuranceAndBillerIntoBuilder(
				requestBuilder,
				getInsuranceAndBiller(body),
				context);

		final ImmutableList<JsonOLCandCreateRequestBuilder> allBuilders = insertServicesIntoBuilders(
				invoiceRecipientBuilders,
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

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertInsuranceAndBillerIntoBuilder(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final InsuranceAndBiller insuranceAndBiller,
			@NonNull final HighLevelContext context)
	{
		final JsonBPartnerInfo recipientBPartnerInfo = createJsonBPartnerInfo(
				insuranceAndBiller.getInsurance(),
				context);

		final JsonOrganization billerOrgInfo = createBillerOrg(
				insuranceAndBiller.getBiller(),
				context);

		final JsonOLCandCreateRequestBuilder insuranceBuilder = copyBuilder(requestBuilder)
				.bpartner(recipientBPartnerInfo)
				.org(billerOrgInfo);

		return ImmutableList.of(insuranceBuilder);
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
		final JsonOrganization org = JsonOrganization
				.builder()
				.syncAdvise(context.getBillerSyncAdvise())
				.code(createBPartnerExternalId(biller))
				.name(createNameString(biller))
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

	private JsonBPartnerInfo createJsonBPartnerInfo(
			@NonNull final InsuranceAddressType insurance,
			@NonNull final HighLevelContext context)
	{
		final CompanyType company = insurance.getCompany();
		final String insuranceExternalId = createBPartnerExternalId(insurance);
		final String recipientExternalId = createBPartnerExternalId(context.getInvoiceRecipientEAN());

		final JsonBPartnerBuilder bPartner = JsonBPartner
				.builder()
				.externalId(recipientExternalId);

		final JsonBPartnerInfoBuilder bPartnerInfo = createJsonBPartnerInfoBuilder();

		if (recipientExternalId.equals(insuranceExternalId))
		{
			final JsonBPartnerLocation location = createJsonBPartnerLocation(
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
		final JsonBPartnerLocation location = JsonBPartnerLocation.builder()
				.gln(context.getInvoiceRecipientEAN())
				.externalId(createLocationExternalId(recipientExternalId))
				.build();

		return bPartnerInfo
				.syncAdvise(SyncAdvise.READ_ONLY)
				.bpartner(bPartner.build())
				.location(location)
				.build();

	}

	private JsonBPartnerInfo createJsonBPartnerInfo(
			@NonNull final BillerAddressType biller,
			@NonNull final HighLevelContext context)
	{
		final String name = createNameString(biller);

		final CompanyType company = biller.getCompany();
		final String billerBPartnerExternalId = createBPartnerExternalId(biller);

		final JsonBPartnerBuilder bPartnerBuilder = JsonBPartner
				.builder()
				.name(name)
				.externalId(billerBPartnerExternalId);

		final JsonBPartnerLocation location;
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

		final JsonBPartnerContact contact = JsonBPartnerContact
				.builder()
				.externalId(billerBPartnerExternalId + "_singlePerson")
				.name(name)
				.email(email)
				.build();

		final JsonBPartnerInfo bPartnerInfo = createJsonBPartnerInfoBuilder()
				.syncAdvise(context.getBillerSyncAdvise())
				.bpartner(bPartnerBuilder.build())
				.contact(contact)
				.location(location)
				.build();

		return bPartnerInfo;
	}

	private String createNameString(@NonNull final BillerAddressType biller)
	{
		final String orgName;
		if (biller.getCompany() != null)
		{
			orgName = biller.getCompany().getCompanyname();
		}
		else
		{
			orgName = biller.getPerson().getGivenname() + " " + biller.getPerson().getFamilyname();
		}
		return orgName;
	}

	private String extracFirsttEmailOrNull(@Nullable final OnlineAddressType online)
	{
		if (online == null || online.getEmail().isEmpty())
		{
			return null;
		}
		return online.getEmail().get(0);
	}

	private String createBPartnerExternalId(@NonNull final String eanParty)
	{
		return "EAN-" + eanParty;
	}

	private String createBPartnerExternalId(@NonNull final InsuranceAddressType insurance)
	{
		return "EAN-" + insurance.getEanParty();
	}

	private String createBPartnerExternalId(@NonNull final BillerAddressType biller)
	{
		return "EAN-" + biller.getEanParty();
	}

	private JsonBPartnerLocation createJsonBPartnerLocation(
			@NonNull final String bPartnerExternalId,
			@NonNull final String gln,
			@NonNull final PostalAddressType postal)
	{
		final String street = StringUtils.trim(postal.getStreet());
		final String pobox = StringUtils.trim(postal.getPobox());
		final String city = StringUtils.trim(postal.getCity());

		final ZipType zip = postal.getZip();
		final String statecode = zip != null ? StringUtils.trim(zip.getStatecode()) : null;
		final String countrycode = zip != null ? StringUtils.trim(zip.getCountrycode()) : null;

		final JsonBPartnerLocationBuilder builder = JsonBPartnerLocation.builder();

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

		final JsonBPartnerLocation location = builder
				.externalId(createLocationExternalId(bPartnerExternalId)) // TODO
				.city(city)
				.postal(zip.getValue())
				.state(statecode)
				.postal(statecode)
				.countryCode(coalesce(countrycode, "CH")) // TODO
				.build();
		return location;
	}

	private String createLocationExternalId(final String bPartnerExternalId)
	{
		return bPartnerExternalId + "_singleAddress";
	}

	private JsonBPartnerInfoBuilder createJsonBPartnerInfoBuilder()
	{
		return JsonBPartnerInfo.builder();
	}

	JsonOLCandCreateRequestBuilder copyBuilder(@NonNull final JsonOLCandCreateRequestBuilder builder)
	{
		return builder
				.build()
				.toBuilder();
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertServicesIntoBuilders(
			@NonNull final ImmutableList<JsonOLCandCreateRequestBuilder> invoiceRecipientBuilders,
			@NonNull final ServicesType services,
			@NonNull final HighLevelContext context)
	{
		final ImmutableList.Builder<JsonOLCandCreateRequestBuilder> result = ImmutableList.builder();

		final List<Object> records = services.getRecordTarmedOrRecordDrgOrRecordLab();
		for (final Object record : records)
		{
			result.addAll(insertServiceRecordIntoBuilders(invoiceRecipientBuilders, record, context));
		}

		return result.build();
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertServiceRecordIntoBuilders(
			@NonNull final ImmutableList<JsonOLCandCreateRequestBuilder> invoiceRecipientBuilders,
			@NonNull final Object record,
			@NonNull final HighLevelContext context)
	{
		final ImmutableList.Builder<JsonOLCandCreateRequestBuilder> result = ImmutableList.builder();

		for (final JsonOLCandCreateRequestBuilder invoiceRecipientBuilder : invoiceRecipientBuilders)
		{
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
		}
		return result.build();
	}

	private String createExternalId(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			final int recordId)
	{
		final JsonOLCandCreateRequest request = requestBuilder.build();

		return ""
				+ request.getPoReference() // probably the "diversest" property
				+ "_"
				+ request.getOrg().getBpartner().getBpartner().getExternalId() // biller
				+ "_"
				+ request.getBpartner().getBpartner().getExternalId() // might be that same for different billers
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
