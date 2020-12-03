/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.jgoodies.common.base.Objects;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.organization.OrgId;
import de.metas.rest_api.bpartner.impl.BpartnerRestController;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestBPartnerUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestBPartnerUpsertItem;
import de.metas.rest_api.bpartner.request.JsonRequestComposite;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsertItem;
import de.metas.rest_api.common.JsonDocTypeInfo;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.SyncAdvise;
import de.metas.rest_api.exception.MissingPropertyException;
import de.metas.rest_api.ordercandidates.OrderCandidatesRestEndpoint;
import de.metas.rest_api.ordercandidates.request.BPartnerLookupAdvise;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateBulkRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder;
import de.metas.rest_api.ordercandidates.request.JsonOrganization;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact.JsonRequestBPartnerLocationAndContactBuilder;
import de.metas.rest_api.ordercandidates.response.JsonAttachment;
import de.metas.rest_api.ordercandidates.response.JsonOLCand;
import de.metas.rest_api.ordercandidates.response.JsonOLCandCreateBulkResponse;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ExternalId;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.RestApiConstants;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.RestApiStartupCondition;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.InvalidXMLException;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.XmlInvalidRequestIdException;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.XmlInvoiceAttachException;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.XmlInvoiceInputStreamException;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.XmlInvoiceUnmarshalException;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.HealthCareInvoiceDocSubType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.HealthcareCHHelper;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ImportConfig;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ImportConfigRepository;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.Invoice440RequestConversionService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BillerAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BodyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.CompanyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.GuarantorAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InvoiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.OnlineAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PatientAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PayloadType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PersonType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PostalAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ServicesType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ZipType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.JaxbUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER;
import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXTERNAL_REFERENCE;
import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.assumeNotNull;

@Service
@Conditional(RestApiStartupCondition.class)
public class XmlToOLCandsService
{
	private final OrderCandidatesRestEndpoint orderCandidatesRestEndpoint;
	private final BpartnerRestController bpartnerRestController;
	private final ImportConfigRepository importConfigRepository;

	public XmlToOLCandsService(
			@NonNull final OrderCandidatesRestEndpoint orderCandidatesRestEndpoint,
			@NonNull final BpartnerRestController bpartnerRestController,
			@NonNull final ImportConfigRepository importConfigRepository)
	{
		this.orderCandidatesRestEndpoint = orderCandidatesRestEndpoint;
		this.bpartnerRestController = bpartnerRestController;
		this.importConfigRepository = importConfigRepository;
	}

	public JsonAttachment createOLCands(@NonNull final CreateOLCandsRequest createOLCandsRequest)
	{
		final MultipartFile xmlInvoiceFile = createOLCandsRequest.getXmlInvoiceFile();

		final RequestType xmlInvoice = unmarshal(xmlInvoiceFile);

		final JsonOLCandCreateBulkRequest jsonOLCandCreateBulkRequest = createJsonOLCandCreateBulkRequest(
				xmlInvoice,
				createOLCandsRequest.getTargetDocType(),
				createOLCandsRequest.getBillerSyncAdvise(),
				createOLCandsRequest.getDebitorSyncAdvise(),
				createOLCandsRequest.getProductSyncAdvise());

		final ResponseEntity<JsonOLCandCreateBulkResponse> orderCandidates = orderCandidatesRestEndpoint.createOrderLineCandidates(jsonOLCandCreateBulkRequest);

		final String externalHeaderId = CollectionUtils.extractSingleElement(
				orderCandidates.getBody().getResult(),
				JsonOLCand::getExternalHeaderId);

		final ResponseEntity<JsonAttachment> result = attachXmlToOLCandidates(
				xmlInvoiceFile,
				externalHeaderId,
				Invoice440RequestConversionService.INVOICE_440_REQUEST_XSD); // store the XSD name so we don't need to extract it from the binary attachment data
		return result.getBody();
	}

	@Value
	@Builder
	public static class CreateOLCandsRequest
	{
		@NonNull
		HealthCareInvoiceDocSubType targetDocType;

		@NonNull
		MultipartFile xmlInvoiceFile;

		@NonNull
		SyncAdvise billerSyncAdvise;

		@NonNull
		SyncAdvise debitorSyncAdvise;

		@NonNull
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
			@NonNull final String externalReference,
			@NonNull final String xsdName)
	{
		try
		{
			final ImmutableList<String> tags = ImmutableList.of(
					ATTACHMENT_TAGNAME_EXPORT_PROVIDER/* name */, ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID/* value */,
					ATTACHMENT_TAGNAME_EXTERNAL_REFERENCE/* name */, externalReference/* value */,
					ForumDatenaustauschChConstants.XSD_NAME, xsdName);

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

	@VisibleForTesting
	JsonOLCandCreateBulkRequest createJsonOLCandCreateBulkRequest(
			@NonNull final RequestType xmlInvoice,
			@NonNull final HealthCareInvoiceDocSubType targetDocType,
			@NonNull final SyncAdvise billerSyncAdvise,
			@NonNull final SyncAdvise debitorSyncAdvise,
			@NonNull final SyncAdvise productsSyncAdvise)
	{
		final JsonOLCandCreateRequestBuilder requestBuilder = JsonOLCandCreateRequest
				.builder()
				.dataSource("int-" + RestApiConstants.INPUT_SOURCE_INTERAL_NAME)
				.dataDest("int-" + InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);

		final String invoiceRecipientEAN = extractRecipientEAN(xmlInvoice);
		final HighLevelContext context = HighLevelContext.builder()
				.targetDocType(targetDocType)
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

	/**
	 * Contains context-infos from high-up in the call-hierarchy
	 */
	@Value
	@Builder
	public static class HighLevelContext
	{
		@NonNull
		HealthCareInvoiceDocSubType targetDocType;
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
		final String poReference = createPOReference(payload);

		requestBuilder.poReference(poReference);

		requestBuilder.invoiceDocType(createJsonDocTypeInfo(context.getTargetDocType()));

		insertInoviceIntoBuilder(
				requestBuilder,
				payload.getInvoice());

		final ImmutableList<JsonOLCandCreateRequestBuilder> builders = insertBodyIntoBuilders(
				requestBuilder,
				payload.getBody(),
				context);

		return builders
				.stream()
				.map(JsonOLCandCreateRequestBuilder::build)
				.collect(ImmutableList.toImmutableList());
	}

	private String createPOReference(@NonNull final PayloadType payload)
	{
		final InvoiceType invoice = payload.getInvoice();

		final String requestIdToUse = Check.assumeNotEmpty(
				invoice.getRequestId(),
				InvalidXMLException.class, "payload/invoice/requestId may not be empty");

		final String result;
		if (requestIdToUse.startsWith("KV_")) // "Krankenversicherung"
		{
			result = requestIdToUse.substring("KV_".length());
		}
		else if (requestIdToUse.startsWith("KT_")) // "Kanton"
		{
			result = requestIdToUse.substring("KT_".length());
		}
		else if (requestIdToUse.startsWith("EA_")) // "Eigenanteil"
		{
			result = requestIdToUse.substring("EA_".length());
		}
		else if (requestIdToUse.startsWith("GM_")) // "Gemeinde"
		{
			result = requestIdToUse.substring("GM_".length());
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
		final JsonOrganization billerOrgInfo = createBillerOrg(
				getBiller(body),
				context);
		requestBuilder.org(billerOrgInfo);

		switch (context.getTargetDocType())
		{
			case KV:
			case KT:
				// the bpartner already exists in metasfresh
				requestBuilder.bpartner(createReadOnlyInvoiceRecipient(context));
				break;
			case EA:
				// The bill-receiver (patient) might or might not yet exist.
				// We extract all the infos, so the sales order-candidate-API could create it on the fly
				addPatientInvoiceRecipient(
						requestBuilder,
						body,
						context);
				break;
			case GM:
				// The bill-receiver (municipality)  might or might not yet exist.
				// We need it to be at org=* which can't be done by sales order-candidate-API
				requestBuilder.bpartner(addMunicipalityInvoiceRecipient(body, context));
				break;
			default:
				throw new AdempiereException("Unsupported targetDocType=" + context.getTargetDocType());
		}

		requestBuilder
				.externalHeaderId(createExternalHeaderId(requestBuilder));

		return insertServicesIntoBuilder(
				requestBuilder.build(),
				body.getServices(),
				context);
	}

	private BillerAddressType getBiller(@NonNull final BodyType body)
	{
		final boolean tiersGarantIsSet = body.getTiersGarant() != null;
		final boolean tiersPayantIsSet = body.getTiersPayant() != null;

		Check.errorUnless(tiersGarantIsSet ^ tiersPayantIsSet,
				"One of TiersGarant or TiersPayant needs to be provided but not both; tiersGarantIsSet={}; tiersPayantIsSet={} ",
				tiersGarantIsSet, tiersPayantIsSet);

		if (tiersGarantIsSet)
		{
			return body.getTiersGarant().getBiller();
		}

		// tiersPayantIsSet
		return body.getTiersPayant().getBiller();
	}

	private PatientAddressType getPatient(@NonNull final BodyType body)
	{
		final boolean tiersGarantIsSet = body.getTiersGarant() != null;
		final boolean tiersPayantIsSet = body.getTiersPayant() != null;

		Check.errorUnless(tiersGarantIsSet ^ tiersPayantIsSet,
				"One of TiersGarant or TiersPayant needs to be provided but not both; tiersGarantIsSet={}; tiersPayantIsSet={} ",
				tiersGarantIsSet, tiersPayantIsSet);

		if (tiersGarantIsSet)
		{
			return body.getTiersGarant().getPatient();
		}
		// tiersPayantIsSet
		return body.getTiersPayant().getPatient();
	}

	private GuarantorAddressType getGuarantor(@NonNull final BodyType body)
	{
		final boolean tiersGarantIsSet = body.getTiersGarant() != null;
		final boolean tiersPayantIsSet = body.getTiersPayant() != null;

		Check.errorUnless(tiersGarantIsSet ^ tiersPayantIsSet,
				"One of TiersGarant or TiersPayant needs to be provided but not both; tiersGarantIsSet={}; tiersPayantIsSet={} ",
				tiersGarantIsSet, tiersPayantIsSet);

		if (tiersGarantIsSet)
		{
			return body.getTiersGarant().getGuarantor();
		}
		// tiersPayantIsSet
		return body.getTiersPayant().getGuarantor();
	}

	private void addPatientInvoiceRecipient(
			@NonNull final JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final BodyType body,
			@NonNull final HighLevelContext context)
	{
		final PatientAddressType patient = getPatient(body);
		final GuarantorAddressType guarantor = getGuarantor(body);
		final BillerAddressType biller = getBiller(body);

		final JsonExternalId patientExternalId = createPatientBPartnerExternalId(biller, patient);

		final JsonRequestBPartnerLocationAndContactBuilder bPartnerInfo = JsonRequestBPartnerLocationAndContact
				.builder()
				.syncAdvise(context.getDebitorSyncAdvise());

		final PersonType person = patient.getPerson();
		final String patientName = createFullPersonName(person);

		final BPartnerQuery bPartnerQuery = BPartnerQuery.builder()
				.externalId(ExternalId.of(patientExternalId.getValue()))
				.failIfNotExists(false)
				.build();
		final ImportConfig importConfig = importConfigRepository.getForQueryOrNull(bPartnerQuery);

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setGlobalId(patient.getSsn());
		bPartner.setExternalId(patientExternalId);
		bPartner.setName(patientName);
		bPartner.setGroup(importConfig.getPartientBPartnerGroupName());
		bPartner.setLanguage(importConfig.getLanguageCode());
		bPartnerInfo.bpartner(bPartner);

		final JsonRequestLocation patientLocation = createJsonBPartnerLocation(
				patientExternalId,
				null/* gln */,
				person.getPostal());

		final NameAndPostal nameAndPostal = extractGuarantorNameAndPostal(guarantor);

		final JsonRequestLocation guarantorLocation = createJsonBPartnerLocation(
				patientExternalId,
				null/* gln */,
				nameAndPostal.getPostal());
		if (Objects.equals(patientLocation, guarantorLocation))
		{
			patientLocation.setName(patientName);
			patientLocation.setShipTo(true);
			patientLocation.setShipToDefault(true);
			patientLocation.setBillTo(true);
			patientLocation.setBillToDefault(true);
		}
		else
		{
			// add an additional bill-location for the guarantor
			final JsonRequestBPartnerLocationAndContactBuilder billBartnerInfo = JsonRequestBPartnerLocationAndContact
					.builder()
					.syncAdvise(context.getDebitorSyncAdvise())
					.bpartner(bPartner);

			guarantorLocation.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
			guarantorLocation.setName(nameAndPostal.getName());
			guarantorLocation.setBpartnerName(nameAndPostal.getName());
			guarantorLocation.setExternalId(JsonExternalId.of(guarantorLocation.getExternalId().getValue() + "_GUARANTOR"));
			guarantorLocation.setShipTo(false);
			guarantorLocation.setShipToDefault(false);
			guarantorLocation.setBillTo(true);
			guarantorLocation.setBillToDefault(true);
			billBartnerInfo.location(guarantorLocation);
			requestBuilder.billBPartner(billBartnerInfo.build());

			patientLocation.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
			patientLocation.setName(patientName);
			patientLocation.setShipTo(true);
			patientLocation.setShipToDefault(true);
			patientLocation.setBillTo(false);
			patientLocation.setBillToDefault(false);
		}
		bPartnerInfo.location(patientLocation);
		requestBuilder.bpartner(bPartnerInfo.build());
	}

	private JsonRequestBPartnerLocationAndContact addMunicipalityInvoiceRecipient(
			@NonNull final BodyType body,
			@NonNull final HighLevelContext context)
	{
		final GuarantorAddressType guarantor = getGuarantor(body); // the guarantor is the municipality who gets the invoice

		final NameAndPostal guarantorNameAndPostal = extractGuarantorNameAndPostal(guarantor);

		final String zipCode = guarantorNameAndPostal.getPostal().getZip().getValue();

		final String bpartnerCode = "GM-" + zipCode;
		final JsonExternalId guarantorExternalId = JsonExternalId.of(bpartnerCode);
		final JsonExternalId locationExternalId = JsonExternalId.of(guarantorExternalId.getValue() + "_GUARANTOR");

		final BPartnerQuery bPartnerQuery = BPartnerQuery.builder()
				.bpartnerValue(bpartnerCode).onlyOrgId(OrgId.ANY)
				.failIfNotExists(false)
				.build();
		final ImportConfig importConfig = importConfigRepository.getForQueryOrNull(bPartnerQuery);

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setCode(bpartnerCode);
		bPartner.setExternalId(guarantorExternalId);
		bPartner.setCompanyName(guarantorNameAndPostal.getName());
		bPartner.setName(guarantorNameAndPostal.getName());
		bPartner.setGroup(importConfig.getMunicipalityBPartnerGroupName());
		bPartner.setLanguage(importConfig.getLanguageCode());

		final JsonRequestLocation guarantorLocation = createJsonBPartnerLocation(
				guarantorExternalId,
				null/* gln */,
				guarantorNameAndPostal.getPostal());

		guarantorLocation.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
		guarantorLocation.setName(guarantorNameAndPostal.getName());
		guarantorLocation.setBpartnerName(guarantorNameAndPostal.getName());
		guarantorLocation.setExternalId(locationExternalId);
		guarantorLocation.setShipTo(true);
		guarantorLocation.setShipToDefault(true);
		guarantorLocation.setBillTo(true);
		guarantorLocation.setBillToDefault(true);

		final JsonRequestBPartnerUpsert partnerUpsert = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(context.getDebitorSyncAdvise())
				.requestItem(JsonRequestBPartnerUpsertItem.builder()
						.bpartnerIdentifier("val-" + bpartnerCode) // the
						.bpartnerComposite(JsonRequestComposite.builder()
								.orgCode("0") // if a bpartner is created, then with Org=*
								.bpartner(bPartner)
								.locations(JsonRequestLocationUpsert.builder()
										.requestItem(JsonRequestLocationUpsertItem.builder()
												.locationIdentifier("ext-" + locationExternalId.getValue())
												.location(guarantorLocation)
												.build())
										.build())
								.build())
						.build())
				.build();
		bpartnerRestController.createOrUpdateBPartner(partnerUpsert);

		// now prepare the JSON to put into the order line candidate request
		final JsonRequestBPartner lookupOnlyBPartner = new JsonRequestBPartner();
		lookupOnlyBPartner.setExternalId(guarantorExternalId);
		final JsonRequestLocation lookupOnlyLocation = new JsonRequestLocation();
		lookupOnlyLocation.setExternalId(locationExternalId);

		return JsonRequestBPartnerLocationAndContact
				.builder()
				.syncAdvise(SyncAdvise.READ_ONLY) // there should be nothing to do since we just did it
				.bpartner(lookupOnlyBPartner)
				.location(lookupOnlyLocation)
				.build();
	}

	@NonNull
	private NameAndPostal extractGuarantorNameAndPostal(@NonNull final GuarantorAddressType guarantor)
	{
		final PostalAddressType guarantorPostal;
		final String guarantorName;
		if (guarantor.getCompany() != null)
		{
			guarantorPostal = guarantor.getCompany().getPostal();
			guarantorName = guarantor.getCompany().getCompanyname();
		}
		else if (guarantor.getPerson() != null)
		{
			guarantorPostal = guarantor.getPerson().getPostal();
			guarantorName = createFullPersonName(guarantor.getPerson());
		}
		else
		{
			throw new MissingPropertyException("guarantor/company or guarantor/person", guarantor);
		}

		final NameAndPostal nameAndPostal = new NameAndPostal(guarantorName, guarantorPostal);
		return nameAndPostal;
	}

	@Value
	private static class NameAndPostal
	{
		@NonNull
		String name;

		@NonNull
		PostalAddressType postal;
	}

	private String createFullPersonName(@NonNull final PersonType person)
	{
		final StringJoiner joiner = new StringJoiner(" ");
		if (Check.isNotBlank(person.getSalutation()))
		{
			joiner.add(person.getSalutation());
		}
		if (Check.isNotBlank(person.getTitle()))
		{
			joiner.add(person.getTitle());
		}
		if (Check.isNotBlank(person.getGivenname()))
		{
			joiner.add(person.getGivenname());
		}
		if (Check.isNotBlank(person.getFamilyname()))
		{
			joiner.add(person.getFamilyname());
		}
		return joiner.toString();
	}

	private JsonOrganization createBillerOrg(
			@NonNull final BillerAddressType biller,
			@NonNull final HighLevelContext context)
	{
		final PersonName name = createName(biller);

		return JsonOrganization
				.builder()
				.syncAdvise(context.getBillerSyncAdvise())
				.code(createBPartnerExternalId(biller).getValue())
				.name(name.getSingleStringName())
				.bpartner(createJsonBPartnerInfo(biller, context))
				.build();
	}

	private JsonDocTypeInfo createJsonDocTypeInfo(@NonNull final HealthCareInvoiceDocSubType docSubType)
	{
		return JsonDocTypeInfo.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_ARInvoice)
				.docSubType(docSubType.getCode())
				.build();
	}

	/**
	 * Build a "sparse" bPartner that is (only) suitable for lookup.
	 */
	private JsonRequestBPartnerLocationAndContact createReadOnlyInvoiceRecipient(@NonNull final HighLevelContext context)
	{
		final JsonExternalId recipientExternalId = createBPartnerExternalId(context.getInvoiceRecipientEAN());

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setExternalId(recipientExternalId);

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setGln(context.getInvoiceRecipientEAN());
		location.setExternalId(createLocationExternalId(recipientExternalId));

		return JsonRequestBPartnerLocationAndContact
				.builder()
				.bpartnerLookupAdvise(BPartnerLookupAdvise.GLN)
				.syncAdvise(SyncAdvise.READ_ONLY)
				.bpartner(bPartner)
				.location(location)
				.build();
	}

	private JsonRequestBPartnerLocationAndContact createJsonBPartnerInfo(
			@NonNull final BillerAddressType biller,
			@NonNull final HighLevelContext context)
	{
		final PersonName name = createName(biller);

		final CompanyType company = biller.getCompany();
		final JsonExternalId billerBPartnerExternalId = createBPartnerExternalId(biller);

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setName(name.getSingleStringName());
		bPartner.setExternalId(billerBPartnerExternalId);

		final JsonRequestLocation location;
		final String email;
		if (company != null)
		{
			bPartner.setCompanyName(company.getCompanyname());

			email = extracFirstEmailOrNull(company.getOnline());

			location = createJsonBPartnerLocation(
					billerBPartnerExternalId,
					biller.getEanParty(),
					company.getPostal());
		}
		else
		{ // biller.getPerson() != null
			email = extracFirstEmailOrNull(biller.getPerson().getOnline());

			location = createJsonBPartnerLocation(
					billerBPartnerExternalId,
					biller.getEanParty(),
					biller.getPerson().getPostal());
		}

		final JsonRequestContact jsonRequestContact = new JsonRequestContact();
		jsonRequestContact.setExternalId(JsonExternalId.of(billerBPartnerExternalId.getValue() + "_singlePerson"));
		jsonRequestContact.setFirstName(name.getFirstName());
		jsonRequestContact.setLastName(coalesce(name.getLastName(), name.getSingleStringName()));
		jsonRequestContact.setName(name.getSingleStringName());
		jsonRequestContact.setEmail(email);

		return JsonRequestBPartnerLocationAndContact.builder()
				.bpartnerLookupAdvise(BPartnerLookupAdvise.GLN)
				.syncAdvise(context.getBillerSyncAdvise())
				.bpartner(bPartner)
				.contact(jsonRequestContact)
				.location(location)
				.build();
	}

	private PersonName createName(@NonNull final BillerAddressType biller)
	{
		final PersonName.PersonNameBuilder name = PersonName.builder();

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
	private static class PersonName
	{
		String firstName;
		String lastName;

		@NonNull
		String singleStringName;
	}

	@Nullable
	private String extracFirstEmailOrNull(@Nullable final OnlineAddressType online)
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

	private JsonExternalId createPatientBPartnerExternalId(@NonNull final BillerAddressType biller, @NonNull final PatientAddressType patient)
	{
		final ExternalId externalId = HealthcareCHHelper.createBPartnerExternalIdForPatient(biller.getEanParty(), patient.getSsn());
		if (externalId == null)
		{
			throw new MissingPropertyException("SSN", patient);
		}
		return JsonExternalId.of(externalId.getValue());
	}

	@NonNull
	private JsonExternalId createBPartnerExternalId(@NonNull final BillerAddressType biller)
	{
		return JsonExternalId.of("EAN-" + biller.getEanParty());
	}

	private JsonRequestLocation createJsonBPartnerLocation(
			@NonNull final JsonExternalId bPartnerExternalId,
			@Nullable final String gln,
			@NonNull final PostalAddressType postal)
	{
		final String street = StringUtils.trim(postal.getStreet());
		final String pobox = StringUtils.trim(postal.getPobox());
		final String city = StringUtils.trim(postal.getCity());

		final ZipType zip = postal.getZip();
		final String statecode = zip != null ? StringUtils.trim(zip.getStatecode()) : null;
		final String countrycode = zip != null ? StringUtils.trim(zip.getCountrycode()) : null;

		final JsonRequestLocation location = new JsonRequestLocation();

		if (!Check.isEmpty(street, true))
		{
			location.setAddress1(street);
			location.setAddress2(pobox);
		}
		else
		{
			location.setAddress1(pobox);
		}

		if (!Check.isEmpty(gln, true))
		{
			location.setGln(gln);
		}

		location.setExternalId(createLocationExternalId(bPartnerExternalId));
		location.setCity(city);
		location.setPostal(zip.getValue());
		location.setRegion(statecode);
		location.setCountryCode(coalesce(countrycode, "CH"));

		location.setBillTo(true);
		location.setBillToDefault(true);
		location.setShipTo(true);
		location.setShipToDefault(true);

		return location;
	}

	private JsonExternalId createLocationExternalId(@NonNull final JsonExternalId bPartnerExternalId)
	{
		return JsonExternalId.of(bPartnerExternalId.getValue() + "_singleAddress");
	}

	private ImmutableList<JsonOLCandCreateRequestBuilder> insertServicesIntoBuilder(
			@NonNull final JsonOLCandCreateRequest invoiceRecipient,
			@NonNull final ServicesType services,
			@NonNull final HighLevelContext context)
	{
		final ImmutableList.Builder<JsonOLCandCreateRequestBuilder> result = ImmutableList.builder();

		final List<Object> records = services.getRecordTarmedOrRecordDrgOrRecordLab();
		for (final Object record : records)
		{
			result.addAll(XmlServiceRecordUtil.insertServiceRecordIntoBuilder(invoiceRecipient, record, context));
		}

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

}
