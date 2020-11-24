/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.process.config;

import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.process.WEBUI_CloneLine;
import de.metas.util.Services;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CloneLineProcessConfig
{
	private final IADProcessDAO adProcessesRepo = Services.get(IADProcessDAO.class);

	@PostConstruct
	public void registerProcess()
	{
		adProcessesRepo.registerTableProcess(RelatedProcessDescriptor.builder()
				.processId(adProcessesRepo.retrieveProcessIdByClass(WEBUI_CloneLine.class))
				.anyTable()
				.anyWindow()
				.displayPlace(RelatedProcessDescriptor.DisplayPlace.SingleDocumentActionsMenu)
				.build());
	}
}
