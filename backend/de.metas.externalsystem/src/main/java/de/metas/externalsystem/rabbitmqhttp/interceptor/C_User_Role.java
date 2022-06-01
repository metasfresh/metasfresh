/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.rabbitmqhttp.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.user.role.UserRoleId;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.externalsystem.rabbitmqhttp.ExportBPartnerToRabbitMQService;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_User_Role;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Interceptor(I_C_User_Role.class)
@Component
public class C_User_Role
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	private final ExportBPartnerToRabbitMQService exportToRabbitMQService;
	private final UserRoleRepository userRoleRepository;

	public C_User_Role(@NonNull final ExportBPartnerToRabbitMQService exportToRabbitMQService, final UserRoleRepository userRoleRepository)
	{
		this.exportToRabbitMQService = exportToRabbitMQService;
		this.userRoleRepository = userRoleRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_User_Role.COLUMNNAME_Name)
	public void triggerSyncBPartnerWithExternalSystem(@NonNull final I_C_User_Role userRole)
	{
		final Set<BPartnerId> bpartnerIds = userRoleRepository.getAssignedUsers(UserRoleId.ofRepoId(userRole.getC_User_Role_ID()))
				.stream()
				.map(userDAO::getBPartnerIdByUserId)
				.collect(Collectors.toSet());

		if (bpartnerIds.isEmpty())
		{
			return;
		}

		trxManager.runAfterCommit(() -> bpartnerIds.forEach(exportToRabbitMQService::enqueueBPartnerSync));
	}
}
