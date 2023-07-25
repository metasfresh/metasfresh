/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.printingclient;

import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.common.externalsystem.printingclient.JsonExternalSystemPrintingClientRequest;
import de.metas.externalsystem.model.I_ExternalSystem_Config_PrintingClient;
import de.metas.externalsystem.model.X_ExternalSystem_Config_PrintingClient;
import de.metas.printing.IPrintingHandler;
import de.metas.printing.PrintingClientRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class ExternalSystemsPrintingHandler implements IPrintingHandler
{
	private final PrintingClientMFToExternalSystemMessageSenderService messageSenderService;
	private final IQueryBL queryBL;

	public ExternalSystemsPrintingHandler(
			@NonNull final PrintingClientMFToExternalSystemMessageSenderService messageSenderService,
			final IQueryBL queryBL)
	{
		this.messageSenderService = messageSenderService;
		this.queryBL = queryBL;
	}

	@Override
	public void notify(final PrintingClientRequest request)
	{
		messageSenderService.send(
				JsonExternalSystemPrintingClientRequest.builder()
						.printingQueueId(request.getPrintingQueueId())
						.build()
		);
	}

	@Override
	public String getTargetDirectory(final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_PrintingClient.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(X_ExternalSystem_Config_PrintingClient.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.first(X_ExternalSystem_Config_PrintingClient.COLUMNNAME_Target_Directory, String.class);
	}
}
