/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.impexp.process;

import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.impexp.DataImportRequest;
import de.metas.impexp.DataImportResult;
import de.metas.impexp.DataImportService;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import javax.annotation.Nullable;

@Builder
public class AttachmentImportCommand
{
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final transient DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);

	@NonNull
	private final AttachmentEntryId attachmentEntryId;
	@NonNull
	private final DataImportConfigId dataImportConfigId;
	@Default
	private final ClientId clientId = Env.getClientId();
	@Default
	private final OrgId orgId = Env.getOrgId();
	@Default
	private final UserId userId = Env.getLoggedUserId();

	@Default
	private final IParams additionalParameters = IParams.NULL;

	@Nullable
	private final Params overrideColumnValues;

	public DataImportResult execute()
	{
		final AttachmentEntryDataResource data = attachmentEntryService.retrieveDataResource(attachmentEntryId);

		return dataImportService.importDataFromResource(DataImportRequest.builder()
																.data(data)
																.dataImportConfigId(dataImportConfigId)
																.clientId(clientId)
																.orgId(orgId)
																.userId(userId)
																.additionalParameters(additionalParameters)
																.overrideColumnValues(overrideColumnValues)
																.build());
	}
}
