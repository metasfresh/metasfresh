/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.postfinance.customerregistration.util.XMLUtil;
import de.metas.postfinance.document.export.PostFinanceExportException;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceResponse;
import de.metas.postfinance.jaxb.ArrayOfInvoice;
import de.metas.postfinance.jaxb.ArrayOfProcessedInvoice;
import de.metas.postfinance.jaxb.ArrayOfProtocolReport;
import de.metas.postfinance.jaxb.B2BService;
import de.metas.postfinance.jaxb.B2BService_Service;
import de.metas.postfinance.jaxb.DownloadFile;
import de.metas.postfinance.jaxb.ObjectFactory;
import de.metas.postfinance.jaxb.ProtocolReport;
import de.metas.postfinance.orgconfig.PostFinanceOrgConfig;
import de.metas.postfinance.orgconfig.PostFinanceOrgConfigRepository;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

import static de.metas.postfinance.PostFinanceConstants.CUSTOMER_REGISTRATION_MESSAGE;

@Service
@RequiredArgsConstructor
public class B2BServiceWrapper
{
	@NonNull
	private final PostFinanceOrgConfigRepository postFinanceOrgConfigRepository;
	public static final ObjectFactory B2B_SERVICE_OBJECT_FACTORY = new ObjectFactory();
	private static final String B2B_SERVICE_WSDL_URL_TEST = "https://ebill-ki.postfinance.ch/B2BService/B2BService.svc?wsdl";

	private static final String B2B_SERVICE_WSDL_URL_PROD = "https://ebill.postfinance.ch/B2BService/B2BService.svc?wsdl";

	@NonNull
	public List<DownloadFile> getCustomerRegistrationMessageFiles(@NonNull final OrgId orgId)
	{
		final PostFinanceOrgConfig postFinanceOrgConfig = postFinanceOrgConfigRepository.getByOrgIdOrError(orgId);
		final String billerId = postFinanceOrgConfig.getBillerId();
		final boolean isArchiveData = postFinanceOrgConfig.isArchiveData();

		final B2BService port = getB2BService(postFinanceOrgConfig.isTestserver()).getUserNamePassword();
		final ArrayOfProtocolReport arrayOfProtocolReport = port.getRegistrationProtocolList(billerId, isArchiveData);

		return arrayOfProtocolReport.getProtocolReport()
				.stream()
				.filter(report -> report.getFileType().getValue().equals(CUSTOMER_REGISTRATION_MESSAGE))
				.map(ProtocolReport::getCreateDate)
				.distinct()
				.map(createDate -> port.getRegistrationProtocol(billerId, createDate, isArchiveData)
						.getDownloadFile())
				.flatMap(List::stream)
				.filter(downloadFile -> XMLUtil.isXML(downloadFile.getFilename().getValue()))
				.collect(ImmutableList.toImmutableList());
	}

	public ArrayOfProcessedInvoice uploadFilesReport(@NonNull final String billerId, @NonNull final List<PostFinanceYbInvoiceResponse> invoices)
	{
		if(invoices.isEmpty())
		{
			return new ArrayOfProcessedInvoice();
		}

		final OrgId orgId = CollectionUtils.extractSingleElement(invoices, PostFinanceYbInvoiceResponse::getOrgId);

		final PostFinanceOrgConfig postFinanceOrgConfig = postFinanceOrgConfigRepository.getByOrgIdOrError(orgId);
		final B2BService port = getB2BService(postFinanceOrgConfig.isTestserver()).getUserNamePassword();
		final ArrayOfInvoice arrayOfInvoice = B2B_SERVICE_OBJECT_FACTORY.createArrayOfInvoice();
		invoices.forEach(invoice -> arrayOfInvoice.getInvoice().add(invoice.getInvoice()));

		return port.uploadFilesReport(
				arrayOfInvoice,
				billerId);
	}

	public List<DownloadFile> getProcessProtocol(@NonNull final OrgId orgId)
	{
		final PostFinanceOrgConfig postFinanceOrgConfig = postFinanceOrgConfigRepository.getByOrgIdOrError(orgId);
		final String billerId = postFinanceOrgConfig.getBillerId();
		// note: if true, use already downloaded data. False = never downloaded data only
		final boolean isArchiveData = postFinanceOrgConfig.isArchiveData();

		final B2BService port = getB2BService(postFinanceOrgConfig.isTestserver()).getUserNamePassword();

		final ArrayOfProtocolReport arrayOfProtocolReport = port.getProcessProtocolList(billerId, isArchiveData);

		final Stream<ProtocolReport> protocolReportStream = arrayOfProtocolReport.getProtocolReport()
				.stream();

		return protocolReportStream
				.map(ProtocolReport::getCreateDate)
				.map(createDate -> port.getProcessProtocol(billerId, createDate, isArchiveData)
						.getDownloadFile())
				.flatMap(List::stream)
				.filter(downloadFile -> XMLUtil.isXML(downloadFile.getFilename().getValue()))
				.collect(ImmutableList.toImmutableList());

	}

	private B2BService_Service getB2BService(final boolean isTestServer)
	{
		final String wsdlURL = isTestServer ? B2B_SERVICE_WSDL_URL_TEST : B2B_SERVICE_WSDL_URL_PROD;

		try
		{
			return new B2BService_Service(new URL(wsdlURL));
		}
		catch (final MalformedURLException e)
		{
			throw new PostFinanceExportException("MalformedURLException on static variable, this shouldn't happen!", e);
		}

	}
}
