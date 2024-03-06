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
import de.metas.postfinance.generated.ArrayOfProtocolReport;
import de.metas.postfinance.generated.B2BService;
import de.metas.postfinance.generated.B2BService_Service;
import de.metas.postfinance.generated.DownloadFile;
import de.metas.postfinance.generated.ProtocolReport;
import de.metas.postfinance.util.XMLUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.metas.postfinance.PostFinanceConstants.CUSTOMER_REGISTRATION_MESSAGE;

@Service
public class B2BServiceWrapper
{
	private final PostFinanceOrgConfigRepository postFinanceOrgConfigRepository;

	public B2BServiceWrapper(@NonNull final PostFinanceOrgConfigRepository postFinanceOrgConfigRepository)
	{
		this.postFinanceOrgConfigRepository = postFinanceOrgConfigRepository;
	}

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
}
