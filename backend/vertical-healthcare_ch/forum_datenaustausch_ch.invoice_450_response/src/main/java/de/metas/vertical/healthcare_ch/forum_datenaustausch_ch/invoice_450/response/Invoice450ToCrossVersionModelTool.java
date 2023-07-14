/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_450.response
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.response;

import com.google.common.collect.ImmutableList;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice.XmlInvoiceBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlPayload;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlPayload.XmlPayloadBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse.XmlResponseBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.XMLParty;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.XMLPatientAddress;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.XMLPersonType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.XmlBody.XmlBodyBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.XMLCompany;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.XmlRejected;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.XmlRejected.XmlRejectedBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.contact.XMLContact;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.contact.XMLEmployee;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.contact.XMLOnline;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.contact.XMLTelecom;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.rejected.XmlError;
import lombok.NonNull;

import java.math.BigInteger;
import java.util.List;

public class Invoice450ToCrossVersionModelTool
{
	public static final Invoice450ToCrossVersionModelTool INSTANCE = new Invoice450ToCrossVersionModelTool();

	private Invoice450ToCrossVersionModelTool()
	{
	}

	XmlResponse toCrossVersionModel(@NonNull final ResponseType invoice440Response)
	{
		final XmlResponseBuilder builder = XmlResponse.builder();

		updateBuilderForResponse(invoice440Response, builder);

		return builder.build();
	}

	private void updateBuilderForResponse(
			@NonNull final ResponseType invoice440Response,
			@NonNull final XmlResponseBuilder xResponse)
	{
		xResponse.payload(createXmlPayload(invoice440Response.getPayload()));
	}

	private XmlPayload createXmlPayload(@NonNull final PayloadType payload)
	{
		final XmlPayloadBuilder payloadBuilder = XmlPayload.builder();

		payloadBuilder.invoice(createXmlInvoice(payload.getInvoice()));
		payloadBuilder.responseTimestamp(payload.getResponseTimestamp());
		payloadBuilder.body(createXmlBody(payload.getBody()));

		return payloadBuilder.build();
	}

	private XmlInvoice createXmlInvoice(@NonNull final InvoiceType invoiceType)
	{
		final XmlInvoiceBuilder xInvoice = XmlInvoice.builder();

		xInvoice.requestTimestamp(BigInteger.valueOf(invoiceType.getRequestTimestamp()));
		xInvoice.requestDate(invoiceType.getRequestDate());
		xInvoice.requestId(invoiceType.getRequestId());

		return xInvoice.build();
	}

	private XmlBody createXmlBody(@NonNull final BodyType body)
	{
		final XmlBodyBuilder bodyBuilder = XmlBody.builder();

		if (body.getRejected() != null)
		{
			bodyBuilder.rejected(createXmlRejected(body.getRejected()));
		}

		if (body.getPatient() != null)
		{
			bodyBuilder.patient(createXmlPatientAddress(body.getPatient()));
		}

		if (body.getBiller() != null)
		{
			bodyBuilder.biller(createXmlParty(body.getBiller()));
		}

		if (body.getContact() != null)
		{
			bodyBuilder.contact(createXmlContact(body.getContact()));
		}

		return bodyBuilder.build();
	}

	private XMLContact createXmlContact(@NonNull final ContactAddressType contact)
	{
		final XMLContact.XMLContactBuilder contactBuilder = XMLContact.builder();

		if (contact.getCompany() != null)
		{
			contactBuilder.company(createXMLCompany(contact.getCompany()));
		}

		if (contact.getEmployee() != null)
		{
			contactBuilder.employee(createXmlEmployee(contact.getEmployee()));
		}

		return contactBuilder.build();
	}

	private XMLEmployee createXmlEmployee(@NonNull final EmployeeType employee)
	{
		final XMLEmployee.XMLEmployeeBuilder employeeBuilder = XMLEmployee.builder();

		employeeBuilder.familyName(employee.familyname);

		if (employee.getGivenname() != null)
		{
			employeeBuilder.givenName(employee.getGivenname());
		}

		if (employee.getTelecom() != null)
		{
			employeeBuilder.telecom(createXmlTelecom(employee.getTelecom()));
		}

		if (employee.getOnline() != null)
		{
			employeeBuilder.online(createXmlOnline(employee.getOnline()));
		}

		return employeeBuilder.build();
	}

	private XMLOnline createXmlOnline(@NonNull final OnlineAddressType online)
	{
		return XMLOnline.builder()
				.email(ImmutableList.copyOf(online.getEmail()))
				.build();
	}

	private XMLTelecom createXmlTelecom(@NonNull final TelecomAddressType telecom)
	{
		return XMLTelecom.builder()
				.phone(ImmutableList.copyOf(telecom.getPhone()))
				.build();
	}

	private XMLCompany createXMLCompany(@NonNull final CompanyType company)
	{
		return XMLCompany.builder()
				.companyName(company.getCompanyname())
				.build();
	}

	private XMLParty createXmlParty(@NonNull final PartyType eanParty)
	{
		final XMLParty.XMLPartyBuilder partyBuilder = XMLParty.builder();

		if (eanParty.getEanParty() != null)
		{
			partyBuilder.eanParty(eanParty.getEanParty());
		}

		return partyBuilder.build();
	}

	private XMLPatientAddress createXmlPatientAddress(@NonNull final PatientAddressType patient)
	{
		final XMLPatientAddress.XMLPatientAddressBuilder patientAddressBuilder = XMLPatientAddress.builder();
		if (patient.getPerson() != null)
		{
			patientAddressBuilder.person(createXmlPersonType(patient.getPerson()));
		}
		return patientAddressBuilder.build();
	}

	private XMLPersonType createXmlPersonType(@NonNull final PersonType person)
	{
		return XMLPersonType.builder()
				.familyName(person.getFamilyname())
				.givenName(person.getGivenname())
				.build();
	}

	private XmlRejected createXmlRejected(@NonNull final RejectedType rejected)
	{
		final XmlRejectedBuilder rejectedBuilder = XmlRejected.builder();

		rejectedBuilder.explanation(rejected.getExplanation())
				.statusIn(rejected.getStatusIn())
				.statusOut(rejected.getStatusOut());

		if (rejected.getError() != null && !rejected.getError().isEmpty())
		{
			rejectedBuilder.errors(createXmlErrors(rejected.getError()));
		}

		return rejectedBuilder.build();
	}

	private List<XmlError> createXmlErrors(@NonNull final List<ErrorType> error)
	{
		final ImmutableList.Builder<XmlError> errorsBuilder = ImmutableList.builder();

		for (final ErrorType errorType : error)
		{
			final XmlError xmlError = XmlError
					.builder()
					.code(errorType.getCode())
					.errorValue(errorType.getErrorValue())
					.recordId(errorType.getRecordId())
					.text(errorType.getText())
					.validValue(errorType.getValidValue())
					.build();
			errorsBuilder.add(xmlError);
		}
		return errorsBuilder.build();
	}
}
