package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXPORT_PROVIDER;
import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXTERNAL_REFERENCE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.compiere.util.Util.coalesce;

import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

import javax.xml.bind.JAXBElement;

import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.ordercandidate.rest.JsonAttachment;
import de.metas.ordercandidate.rest.JsonBPartner;
import de.metas.ordercandidate.rest.JsonBPartnerContact;
import de.metas.ordercandidate.rest.JsonBPartnerInfo;
import de.metas.ordercandidate.rest.JsonBPartnerLocation;
import de.metas.ordercandidate.rest.JsonBPartnerLocation.JsonBPartnerLocationBuilder;
import de.metas.ordercandidate.rest.JsonDocTypeInfo;
import de.metas.ordercandidate.rest.JsonOLCand;
import de.metas.ordercandidate.rest.JsonOLCandCreateBulkRequest;
import de.metas.ordercandidate.rest.JsonOLCandCreateBulkResponse;
import de.metas.ordercandidate.rest.JsonOLCandCreateRequest;
import de.metas.ordercandidate.rest.JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder;
import de.metas.ordercandidate.rest.JsonProductInfo;
import de.metas.ordercandidate.rest.JsonProductInfo.Type;
import de.metas.ordercandidate.rest.OrderCandidatesRestEndpoint;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BodyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.CompanyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.GarantType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InsuranceAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InvoiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PatientAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PayantType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PayloadType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PersonType;
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
	private final OrderCandidatesRestEndpoint orderCandidatesRestEndpoint;

	private XmlToOLCandsService(@NonNull final OrderCandidatesRestEndpoint orderCandidatesRestEndpoint)
	{
		this.orderCandidatesRestEndpoint = orderCandidatesRestEndpoint;
	}

	public JsonAttachment createOLCands(@NonNull final MultipartFile xmlInvoiceFile)
	{
		final RequestType xmlInvoice = unmarshal(xmlInvoiceFile);

		final JsonOLCandCreateBulkRequest jsonOLCandCreateBulkRequest = createJsonOLCandCreateBulkRequest(xmlInvoice);

		final JsonOLCandCreateBulkResponse orderCandidates = orderCandidatesRestEndpoint.createOrderLineCandidates(jsonOLCandCreateBulkRequest);

		final String poReference = CollectionUtils.extractSingleElement(orderCandidates.getResult(), JsonOLCand::getPoReference);
		final JsonAttachment result = attachXmlToOLCandidates(xmlInvoiceFile, poReference);

		return result;
	}

	private static RequestType unmarshal(@NonNull final MultipartFile file)
	{
		final InputStream xmlInput;
		try
		{
			xmlInput = file.getInputStream();
		}
		catch (IOException e)
		{
			throw new XmlInvoiceInputStreamException();
		}

		try
		{
			final JAXBElement<RequestType> request = JaxbUtil.unmarshalToJaxbElement(xmlInput, RequestType.class);
			return request.getValue();
		}
		catch (RuntimeException e)
		{
			throw new XmlInvoiceUnmarshalException();
		}
	}

	private JsonAttachment attachXmlToOLCandidates(
			@NonNull final MultipartFile xmlInvoiceFile,
			@NonNull final String externalReference)
	{
		try
		{
			final ImmutableList<String> tags = ImmutableList.of(
					ATTATCHMENT_TAGNAME_EXPORT_PROVIDER/*name*/, ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID/*value*/,
					ATTATCHMENT_TAGNAME_EXTERNAL_REFERENCE/*name*/, externalReference/*value*/);

			return orderCandidatesRestEndpoint.attachFile(
					RestApiConstants.INPUT_SOURCE_INTERAL_NAME,
					externalReference,
					tags,
					xmlInvoiceFile);
		}
		catch (IOException e)
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

	private JsonOLCandCreateBulkRequest createJsonOLCandCreateBulkRequest(@NonNull final RequestType xmlInvoice)
	{
		final JsonOLCandCreateRequestBuilder requestBuilder = JsonOLCandCreateRequest
				.builder()
				.dataSourceInternalName(RestApiConstants.INPUT_SOURCE_INTERAL_NAME)
				.dataDestInternalName(InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);

		final List<JsonOLCandCreateRequestBuilder> requestBuilders = insertPayloadIntoBuilders(requestBuilder, xmlInvoice.getPayload());

		final ImmutableList<JsonOLCandCreateRequest> requests = requestBuilders
				.stream()
				.map(JsonOLCandCreateRequestBuilder::build)
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
		insertInoviceIntoBuilder(requestBuilder, payload.getInvoice());

		final ImmutableList<JsonOLCandCreateRequestBuilder> builders = insertBodyIntoBuilders(requestBuilder, payload.getBody());
		return builders;
	}

	private void insertInoviceIntoBuilder(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final InvoiceType invoice)
	{
		final String poReference = invoice.getRequestId();
		if (poReference.startsWith("KV_"))
		{
			requestBuilder.poReference(poReference.substring(3));
		}
		else
		{
			requestBuilder.poReference(poReference);
		}

		final LocalDate dateInvoiced = TimeUtil.asLocalDate(invoice.getRequestDate());
		requestBuilder.dateInvoiced(dateInvoiced);
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertBodyIntoBuilders(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final BodyType body)
	{
		boolean tiersGarantIsSet = body.getTiersGarant() != null;
		boolean tiersPayantIsSet = body.getTiersPayant() != null;
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
		insuranceBuilder.invoiceDocType(createJsonDocTypeInfo(tiersGarant.getInsurance()));
		insuranceBuilder.bpartner(createJsonBPartnerInfo(tiersGarant.getInsurance()));

		// despite having patient mater data in the XML, there is no point creating the master data in metasfresh;
		// we can't invoice patients with the given XML
		// final JsonOLCandCreateRequestBuilder patientBuilder = copyBuilder(requestBuilder);
		// patientBuilder.invoiceDocType(createJsonDocTypeInfo(tiersGarant.getPatient()));
		// patientBuilder.bpartner(createJsonBPartnerInfo(tiersGarant.getPatient()));

		// todo: what about "Gemeinde"?

		return ImmutableList.of(insuranceBuilder/* , patientBuilder */);
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertTiersPayantIntoBuilders(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final PayantType tiersPayant)
	{
		final JsonOLCandCreateRequestBuilder insuranceBuilder = copyBuilder(requestBuilder);
		insuranceBuilder.invoiceDocType(createJsonDocTypeInfo(tiersPayant.getInsurance()));
		insuranceBuilder.bpartner(createJsonBPartnerInfo(tiersPayant.getInsurance()));

		// despite having patient mater data in the XML, there is no point creating the master data in metasfresh;
		// we can't invoice patients with the given XML
		// final JsonOLCandCreateRequestBuilder patientBuilder = copyBuilder(requestBuilder);
		// patientBuilder.invoiceDocType(createJsonDocTypeInfo(tiersPayant.getPatient()));
		// patientBuilder.bpartner(createJsonBPartnerInfo(tiersPayant.getPatient()));

		// todo: what about "Gemeinde"?

		return ImmutableList.of(insuranceBuilder /* , patientBuilder */);
	}

	private JsonDocTypeInfo createJsonDocTypeInfo(@NonNull final InsuranceAddressType insurance)
	{
		return JsonDocTypeInfo.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_ARInvoice)
				.docSubType("KV")
				.build();
	}

	private JsonDocTypeInfo createJsonDocTypeInfo(@NonNull final PatientAddressType patient)
	{
		return JsonDocTypeInfo.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_ARInvoice)
				.docSubType("EA")
				.build();
	}

	private JsonBPartnerInfo createJsonBPartnerInfo(@NonNull final InsuranceAddressType insurance)
	{
		final CompanyType company = insurance.getCompany();
		final String insuranceBPartnerCode = createBPartnerCode(insurance);

		final JsonBPartnerLocation location = createJsonBPartnerLocation(insuranceBPartnerCode, company.getPostal());

		final JsonBPartner bPartner = JsonBPartner
				.builder()
				.name(company.getCompanyname())
				.code(insuranceBPartnerCode)
				.build();

		// final JsonBPartnerContact contact = createJsonBPartnerContact(insurance.getPerson());

		final JsonBPartnerInfo bPartnerInfo = JsonBPartnerInfo
				.builder()
				// .contact(contact)
				.bpartner(bPartner)
				.location(location)
				.build();

		return bPartnerInfo;
	}

	private String createBPartnerCode(@NonNull final InsuranceAddressType insurance)
	{
		return "EAN-" + insurance.getEanParty();
	}

	private JsonBPartnerInfo createJsonBPartnerInfo(@NonNull final PatientAddressType patient)
	{
		final PersonType person = patient.getPerson();

		final String personName = createNameString(person);
		final String partientBPartnerCode = createBPartnerCode(patient);

		final JsonBPartner bPartner = JsonBPartner
				.builder()
				.name(personName)
				.code(partientBPartnerCode)
				.build();

		final JsonBPartnerLocation location = createJsonBPartnerLocation(partientBPartnerCode, person.getPostal());

		// final JsonBPartnerLocation location = JsonBPartnerLocation
		// .builder()
		// .externalId()
		// .build();

		// final JsonBPartnerContact contact = createJsonBPartnerContact(patient.getPerson());

		final JsonBPartnerInfo bPartnerInfo = JsonBPartnerInfo
				.builder()
				// .contact(contact)
				.bpartner(bPartner)
				.location(location)
				.build();

		return bPartnerInfo;
	}

	private String createBPartnerCode(@NonNull final PatientAddressType patient)
	{
		return "SSN-" + patient.getSsn();
	}

	private JsonBPartnerLocation createJsonBPartnerLocation(
			@NonNull final String bPartnerCode,
			@NonNull final PostalAddressType postal)
	{
		final JsonBPartnerLocationBuilder builder = JsonBPartnerLocation.builder();

		final String street = StringUtils.trim(postal.getStreet());
		final String pobox = StringUtils.trim(postal.getPobox());
		final String city = StringUtils.trim(postal.getCity());

		final ZipType zip = postal.getZip();
		final String statecode = zip != null ? StringUtils.trim(zip.getStatecode()) : null;
		final String countrycode = zip != null ? StringUtils.trim(zip.getCountrycode()) : null;

		if (!Check.isEmpty(street))
		{
			builder.address1(street)
					.address2(pobox);
		}
		else
		{
			builder.address1(pobox);
		}
		final JsonBPartnerLocation location = builder
				.externalId(bPartnerCode + "_singleAddress") // TODO
				.city(city)
				.postal(zip.getValue())
				.state(statecode)
				.postal(statecode)
				.countryCode(coalesce(countrycode, "CH")) // TODO
				.build();
		return location;
	}

	private JsonBPartnerContact createJsonBPartnerContact(
			@NonNull final String bPartnerCode,
			@NonNull final PersonType person)
	{
		final String personName = createNameString(person);

		return JsonBPartnerContact
				.builder()
				.name(personName)
				.externalId(bPartnerCode + "_singlePerson") // TODO
				.build();
	}

	private String createNameString(final PersonType person)
	{
		final String name = new StringJoiner(" ")
				.add(person.getGivenname())
				.add(person.getFamilyname())
				.toString();
		return name;
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
		ImmutableList.Builder<JsonOLCandCreateRequestBuilder> result = ImmutableList.builder();

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
			if (record instanceof RecordTarmedType)
			{
				// TODO
			}
			else if (record instanceof RecordDRGType)
			{
				// TODO
			}
			else if (record instanceof RecordLabType)
			{
				// TODO
			}
			else if (record instanceof RecordMigelType)
			{
				// TODO
			}
			else if (record instanceof RecordParamedType)
			{
				// TODO
			}
			else if (record instanceof RecordDrugType)
			{
				// TODO
			}
			else if (record instanceof RecordOtherType)
			{
				final JsonOLCandCreateRequestBuilder serviceRecordBuilder = copyBuilder(invoiceRecipientBuilder);
				final RecordOtherType recordOtherType = (RecordOtherType)record;

				final String externalId = createExternalId(invoiceRecipientBuilder, recordOtherType);

				final JsonProductInfo product = createProduct(recordOtherType);

				final BigDecimal price = createPrice(recordOtherType);

				serviceRecordBuilder
						.externalId(externalId)
						.product(product)
						.price(price)
						.currencyCode("CHF") // TODO
						.discount(ZERO)
						.qty(recordOtherType.getQuantity());

				result.add(serviceRecordBuilder);
			}
		}
		return result.build();
	}

	private String createExternalId(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final RecordOtherType recordOtherType)
	{
		final JsonOLCandCreateRequest request = requestBuilder.build();

		return request.getBpartner().getBpartner().getCode()
				+ "_"
				+ request.getPoReference()
				+ "_"
				+ recordOtherType.getRecordId();
	}

	private JsonProductInfo createProduct(@NonNull final RecordOtherType recordOtherType)
	{
		final JsonProductInfo product = JsonProductInfo.builder()
				.code(recordOtherType.getCode())
				.name(recordOtherType.getName())
				.type(Type.SERVICE)
				.uomCode("HUR")
				.build();
		return product;
	}

	private BigDecimal createPrice(@NonNull final RecordOtherType recordOtherType)
	{
		final BigDecimal unit = coalesce(recordOtherType.getUnit(), ONE); // tax point (TP) of the applied service
		final BigDecimal unitFactor = coalesce(recordOtherType.getUnitFactor(), ONE); // tax point value (TPV) of the applied service
		final BigDecimal externalFactor = coalesce(recordOtherType.getExternalFactor(), ONE);

		final BigDecimal price = unit.multiply(unitFactor).multiply(externalFactor);
		return price;
	}
}
