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
import de.metas.bpartner.postfinance.PostFinanceOrgConfig;
import de.metas.bpartner.postfinance.PostFinanceOrgConfigRepository;
import de.metas.organization.OrgId;
import de.metas.postfinance.customerregistration.util.XMLUtil;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceResponse;
import de.metas.postfinance.jaxb.ArrayOfInvoice;
import de.metas.postfinance.jaxb.ArrayOfProcessedInvoice;
import de.metas.postfinance.jaxb.ArrayOfProtocolReport;
import de.metas.postfinance.jaxb.B2BService;
import de.metas.postfinance.jaxb.B2BService_Service;
import de.metas.postfinance.jaxb.DownloadFile;
import de.metas.postfinance.jaxb.ObjectFactory;
import de.metas.postfinance.jaxb.ProtocolReport;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

	@NonNull
	public List<DownloadFile> getCustomerRegistrationMessageFiles(@NonNull final OrgId orgId)
	{
		final PostFinanceOrgConfig postFinanceOrgConfig = postFinanceOrgConfigRepository.getByOrgId(orgId);
		final String billerId = postFinanceOrgConfig.getBillerId();
		final boolean isArchiveData = postFinanceOrgConfig.isArchiveData();

		final B2BService port = new B2BService_Service().getUserNamePassword();
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
		final B2BService port = new B2BService_Service().getUserNamePassword();
		final ArrayOfInvoice arrayOfInvoice = B2B_SERVICE_OBJECT_FACTORY.createArrayOfInvoice();
		invoices.forEach(invoice -> arrayOfInvoice.getInvoice().add(invoice.getInvoice()));

		return port.uploadFilesReport(
				arrayOfInvoice,
				billerId);
	}

	public List<DownloadFile> getProcessProtocol(@NonNull final OrgId orgId)
	{
		final PostFinanceOrgConfig postFinanceOrgConfig = postFinanceOrgConfigRepository.getByOrgId(orgId);
		final String billerId = postFinanceOrgConfig.getBillerId();
		// note: if true, use already downloaded data. False = never downloaded data only
		final boolean isArchiveData = postFinanceOrgConfig.isArchiveData();

		final B2BService port = new B2BService_Service().getUserNamePassword();

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
}
