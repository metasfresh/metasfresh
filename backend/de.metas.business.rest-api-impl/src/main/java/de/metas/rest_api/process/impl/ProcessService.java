/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.process.impl;

import com.google.common.collect.ImmutableList;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessBasicInfo;
import de.metas.process.ProcessType;
import de.metas.security.PermissionService;
import de.metas.security.PermissionServiceFactories;
import de.metas.security.PermissionServiceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProcessService
{
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final PermissionServiceFactory permissionServiceFactory = PermissionServiceFactories.currentContext();

	@NonNull
	public ImmutableList<ProcessBasicInfo> getProcessesByType(@NonNull final Set<ProcessType> processTypes)
	{
		final PermissionService permissionService = permissionServiceFactory.createPermissionService();

		return adProcessDAO.getProcessesByType(processTypes)
				.stream()
				.filter(process -> permissionService.canRunProcess(process.getProcessId()))
				.collect(ImmutableList.toImmutableList());
	}
}
