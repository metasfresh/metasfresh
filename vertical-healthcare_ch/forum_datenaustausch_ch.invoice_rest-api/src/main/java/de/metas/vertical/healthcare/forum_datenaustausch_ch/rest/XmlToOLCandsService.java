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

import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
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
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.GarantType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InsuranceAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InvoiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.OnlineAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PayantType;
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
				createOLCandsRequest.getOrgSyncAdvise(),
				createOLCandsRequest.getBPartnerSyncAdvise(),
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

		SyncAdvise orgSyncAdvise;
		SyncAdvise bPartnerSyncAdvise;
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
			throw new XmlInvoiceInputStreamException();
		}

		try
		{
			final JAXBElement<RequestType> request = JaxbUtil.unmarshalToJaxbElement(xmlInput, RequestType.class);
			return request.getValue();
		}
		catch (final RuntimeException e)
		{
			throw new XmlInvoiceUnmarshalException();
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
			throw new XmlInvoiceAttachException();
		}
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "An error occurred while trying access the XML invoice inout stream")
	public static class XmlInvoiceInputStreamException extends RuntimeException
	{
		private static final long serialVersionUID = 8216181888558013882L;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "An error occurred while trying to unmarshal the invoice XML data")
	public static class XmlInvoiceUnmarshalException extends RuntimeException
	{
		private static final long serialVersionUID = 8216181888558013882L;
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error occurred while trying attach the XML data to the order line candidates")
	public static class XmlInvoiceAttachException extends RuntimeException
	{
		private static final long serialVersionUID = 2013021164753485741L;
	}

	@VisibleForTesting
	JsonOLCandCreateBulkRequest createJsonOLCandCreateBulkRequest(
			@NonNull final RequestType xmlInvoice,
			@NonNull final SyncAdvise orgSyncAdvise,
			@NonNull final SyncAdvise bPartnersSyncAdvise,
			@NonNull final SyncAdvise productsSyncAdvise)
	{
		final JsonOLCandCreateRequestBuilder requestBuilder = JsonOLCandCreateRequest
				.builder()
				.dataSourceInternalName(RestApiConstants.INPUT_SOURCE_INTERAL_NAME)
				.dataDestInternalName(InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);

		final List<JsonOLCandCreateRequestBuilder> requestBuilders = insertPayloadIntoBuilders(
				requestBuilder,
				xmlInvoice.getPayload());

		final ImmutableList<JsonOLCandCreateRequest> requests = requestBuilders
				.stream()
				.map(JsonOLCandCreateRequestBuilder::build)
				.map(r -> r.withOrgSyncAdvise(orgSyncAdvise))
				.map(r -> r.withBPartnersSyncAdvise(bPartnersSyncAdvise))
				.map(r -> r.withProductsSyncAdvise(productsSyncAdvise))
				.collect(ImmutableList.toImmutableList());

		return JsonOLCandCreateBulkRequest
				.builder()
				.requests(requests)
				.build();
	}

	private List<JsonOLCandCreateRequestBuilder> insertPayloadIntoBuilders(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final PayloadType payload)
	{
		requestBuilder.poReference(createPOReference(payload));

		requestBuilder.externalHeaderId(createExternalHeaderId(payload));

		insertInoviceIntoBuilder(requestBuilder, payload.getInvoice());

		final ImmutableList<JsonOLCandCreateRequestBuilder> builders = insertBodyIntoBuilders(requestBuilder, payload.getBody());
		return builders;
	}

	private String createExternalHeaderId(@NonNull final PayloadType payload)
	{
		final String requestIdToUse = createPOReference(payload);

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

	private String createPOReference(final PayloadType payload)
	{
		final InvoiceType invoice = payload.getInvoice();

		String requestIdToUse = Check.assumeNotEmpty(
				invoice.getRequestId(),
				InvalidXMLException.class, "payload/invoice/requestId may not be empty");
		if (requestIdToUse.startsWith("KV_"))
		{
			requestIdToUse = requestIdToUse.substring(3);
		}
		return requestIdToUse;
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
			@NonNull final BodyType body)
	{
		final boolean tiersGarantIsSet = body.getTiersGarant() != null;
		final boolean tiersPayantIsSet = body.getTiersPayant() != null;

		Check.errorUnless(tiersGarantIsSet ^ tiersPayantIsSet,
				"One of TiersGarant or TiersPayant needs to be provided but not both; tiersGarantIsSet={}; tiersPayantIsSet={} ",
				tiersGarantIsSet, tiersPayantIsSet);

		final ImmutableList<JsonOLCandCreateRequestBuilder> invoiceRecipientBuilders;
		if (tiersGarantIsSet)
		{
			invoiceRecipientBuilders = insertTiersGarantIntoBuilders(requestBuilder, body.getTiersGarant());
		}
		else // tiersPayantIsSet
		{
			invoiceRecipientBuilders = insertTiersPayantIntoBuilders(requestBuilder, body.getTiersPayant());
		}

		final ImmutableList<JsonOLCandCreateRequestBuilder> allBuilders = insertServicesIntoBuilders(invoiceRecipientBuilders, body.getServices());

		return allBuilders;
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertTiersGarantIntoBuilders(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final GarantType tiersGarant)
	{
		final JsonOLCandCreateRequestBuilder insuranceBuilder = copyBuilder(requestBuilder);
		insuranceBuilder.invoiceDocType(createJsonDocTypeInfo());
		insuranceBuilder.bpartner(createJsonBPartnerInfo(tiersGarant.getInsurance()));

		insuranceBuilder.org(createBillerOrg(tiersGarant.getBiller()));

		// despite having patient mater data in the XML, there is no point creating the master data in metasfresh;
		// we can't invoice patients with the given XML
		// final JsonOLCandCreateRequestBuilder patientBuilder = copyBuilder(requestBuilder);
		// patientBuilder.invoiceDocType(createJsonDocTypeInfo(tiersGarant.getPatient()));
		// patientBuilder.bpartner(createJsonBPartnerInfo(tiersGarant.getPatient()));

		// todo: what about "Gemeinde"?

		return ImmutableList.of(insuranceBuilder/* , patientBuilder */);
	}

	private JsonOrganization createBillerOrg(@NonNull final BillerAddressType biller)
	{
		final JsonOrganization org = JsonOrganization
				.builder()
				.code(createBPartnerExternalId(biller))
				.name(createNameString(biller))
				.bpartner(createJsonBPartnerInfo(biller))
				.build();
		return org;
	}

	private String createNameString(final BillerAddressType biller)
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

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertTiersPayantIntoBuilders(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final PayantType tiersPayant)
	{
		final JsonOLCandCreateRequestBuilder insuranceBuilder = copyBuilder(requestBuilder);
		insuranceBuilder.invoiceDocType(createJsonDocTypeInfo());
		insuranceBuilder.bpartner(createJsonBPartnerInfo(tiersPayant.getInsurance()));

		insuranceBuilder.org(createBillerOrg(tiersPayant.getBiller()));

		// despite having patient mater data in the XML, there is no point creating the master data in metasfresh;
		// we can't invoice patients with the given XML

		return ImmutableList.of(insuranceBuilder /* , patientBuilder */);
	}

	private JsonDocTypeInfo createJsonDocTypeInfo()
	{
		return JsonDocTypeInfo.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_ARInvoice)
				.docSubType("KV")
				.build();
	}

	private JsonBPartnerInfo createJsonBPartnerInfo(@NonNull final InsuranceAddressType insurance)
	{
		final CompanyType company = insurance.getCompany();
		final String insuranceBPartnerExternalId = createBPartnerExternalId(insurance);

		final JsonBPartnerLocation location = createJsonBPartnerLocation(insuranceBPartnerExternalId, insurance.getEanParty(), company.getPostal());

		final JsonBPartner bPartner = JsonBPartner
				.builder()
				.name(company.getCompanyname())
				.externalId(insuranceBPartnerExternalId)
				.build();

		// final JsonBPartnerContact contact = createJsonBPartnerContact(insurance.getPerson());

		final JsonBPartnerInfo bPartnerInfo = createJsonBPartnerInfoBuilder()
				// .contact(contact)
				.bpartner(bPartner)
				.location(location)
				.build();

		return bPartnerInfo;
	}

	private JsonBPartnerInfo createJsonBPartnerInfo(@NonNull final BillerAddressType biller)
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
				.bpartner(bPartnerBuilder.build())
				.contact(contact)
				.location(location)
				.build();

		return bPartnerInfo;
	}

	private String extracFirsttEmailOrNull(@Nullable final OnlineAddressType online)
	{
		if (online == null || online.getEmail().isEmpty())
		{
			return null;
		}
		return online.getEmail().get(0);
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
		final JsonBPartnerLocationBuilder builder = JsonBPartnerLocation.builder();

		final String street = StringUtils.trim(postal.getStreet());
		final String pobox = StringUtils.trim(postal.getPobox());
		final String city = StringUtils.trim(postal.getCity());

		final ZipType zip = postal.getZip();
		final String statecode = zip != null ? StringUtils.trim(zip.getStatecode()) : null;
		final String countrycode = zip != null ? StringUtils.trim(zip.getCountrycode()) : null;

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
				.externalId(bPartnerExternalId + "_singleAddress") // TODO
				.city(city)
				.postal(zip.getValue())
				.state(statecode)
				.postal(statecode)
				.countryCode(coalesce(countrycode, "CH")) // TODO
				.build();
		return location;
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
			@NonNull final ServicesType services)
	{
		final ImmutableList.Builder<JsonOLCandCreateRequestBuilder> result = ImmutableList.builder();

		final List<Object> records = services.getRecordTarmedOrRecordDrgOrRecordLab();
		for (final Object record : records)
		{
			result.addAll(insertServiceRecordIntoBuilders(invoiceRecipientBuilders, record));
		}

		return result.build();
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertServiceRecordIntoBuilders(
			@NonNull final ImmutableList<JsonOLCandCreateRequestBuilder> invoiceRecipientBuilders,
			@NonNull final Object record)
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
				product = createProduct(recordTarmedType.getCode(), recordTarmedType.getName());
				price = recordTarmedType.getAmount();
				quantity = ONE;
			}
			else if (record instanceof RecordDRGType)
			{
				final RecordDRGType recordDRGType = (RecordDRGType)record;

				externalLineId = createExternalId(invoiceRecipientBuilder, recordDRGType.getRecordId());
				product = createProduct(recordDRGType.getCode(), recordDRGType.getName());
				price = createPrice(recordDRGType.getUnit(), recordDRGType.getUnitFactor(), recordDRGType.getExternalFactor());
				quantity = recordDRGType.getQuantity();
			}
			else if (record instanceof RecordLabType)
			{
				final RecordLabType recordLabType = (RecordLabType)record;

				externalLineId = createExternalId(invoiceRecipientBuilder, recordLabType.getRecordId());
				product = createProduct(recordLabType.getCode(), recordLabType.getName());
				price = createPrice(recordLabType.getUnit(), recordLabType.getUnitFactor(), recordLabType.getExternalFactor());
				quantity = recordLabType.getQuantity();
			}
			else if (record instanceof RecordMigelType)
			{
				final RecordMigelType recordMigelType = (RecordMigelType)record;

				externalLineId = createExternalId(invoiceRecipientBuilder, recordMigelType.getRecordId());
				product = createProduct(recordMigelType.getCode(), recordMigelType.getName());
				price = createPrice(recordMigelType.getUnit(), recordMigelType.getUnitFactor(), recordMigelType.getExternalFactor());
				quantity = recordMigelType.getQuantity();
			}
			else if (record instanceof RecordParamedType)
			{
				final RecordParamedType recordParamedOtherType = (RecordParamedType)record;

				externalLineId = createExternalId(invoiceRecipientBuilder, recordParamedOtherType.getRecordId());
				product = createProduct(recordParamedOtherType.getCode(), recordParamedOtherType.getName());
				price = createPrice(recordParamedOtherType.getUnit(), recordParamedOtherType.getUnitFactor(), recordParamedOtherType.getExternalFactor());
				quantity = recordParamedOtherType.getQuantity();
			}
			else if (record instanceof RecordDrugType)
			{
				final RecordDrugType recordDrugType = (RecordDrugType)record;

				externalLineId = createExternalId(invoiceRecipientBuilder, recordDrugType.getRecordId());
				product = createProduct(recordDrugType.getCode(), recordDrugType.getName());
				price = createPrice(recordDrugType.getUnit(), recordDrugType.getUnitFactor(), recordDrugType.getExternalFactor());
				quantity = recordDrugType.getQuantity();
			}
			else if (record instanceof RecordOtherType)
			{
				final RecordOtherType recordOtherType = (RecordOtherType)record;

				externalLineId = createExternalId(invoiceRecipientBuilder, recordOtherType.getRecordId());
				product = createProduct(recordOtherType.getCode(), recordOtherType.getName());
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
			@NonNull final String productName)
	{
		final JsonProductInfo product = JsonProductInfo.builder()
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
