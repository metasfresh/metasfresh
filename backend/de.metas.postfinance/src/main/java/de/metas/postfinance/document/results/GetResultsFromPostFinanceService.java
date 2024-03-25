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

package de.metas.postfinance.document.results;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentTags;
import de.metas.organization.OrgId;
import de.metas.postfinance.B2BServiceWrapper;
import de.metas.postfinance.customerregistration.util.XMLUtil;
import de.metas.postfinance.jaxb.ArrayOfProtocolReport;
import de.metas.postfinance.jaxb.DownloadFile;
import de.metas.postfinance.jaxb.ProtocolReport;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.drools.modelcompiler.dsl.pattern.D;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetResultsFromPostFinanceService
{
	@NonNull private final B2BServiceWrapper b2BServiceWrapper;

	public void handleResultsFromPostFinance(@NonNull final OrgId orgId)
	{
		final List<DownloadFile> processProtocol = b2BServiceWrapper.getProcessProtocol(orgId);
		processProtocol.forEach(file -> handleResultsFromPostFinance (file, orgId));
	}

	private void handleResultsFromPostFinance(@NonNull final DownloadFile downloadFile, @NonNull final OrgId orgId)
	{

		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = createAttachmentRequest(downloadFile);

		XMLUtil.getXmlProcessProtocol(downloadFile)
				.customerRegistrations()
				.stream()
				.map(customerRegistration -> toCustomerRegistrationMessageRequest(customerRegistration, attachmentEntryCreateRequest, orgId))
				.map(customerRegistrationMessageRepository::create)
				.forEach(this::processIfRequired);

	}

	@NonNull
	private AttachmentEntryCreateRequest createAttachmentRequest(@NonNull final DownloadFile downloadFile)
	{
		// TODO refactor to not have duplicate code
		final AttachmentTags attachmentTags = AttachmentTags.builder()
				.tag(AttachmentTags.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true))
				.build();

		return AttachmentEntryCreateRequest
				.builderFromByteArray(
						downloadFile.getFilename().getValue(),
						downloadFile.getData().getValue())
				.tags(attachmentTags)
				.build();
	}
}
