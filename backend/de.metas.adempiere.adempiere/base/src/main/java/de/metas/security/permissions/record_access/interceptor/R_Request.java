/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.security.permissions.record_access.interceptor;

import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.PermissionIssuer;
import de.metas.security.permissions.record_access.RecordAccessConfigService;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccessGrantRequest;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_R_Request;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

@Interceptor(I_R_Request.class)
@Component
public class R_Request
{
	private final RecordAccessConfigService recordAccessConfigService;
	private final RecordAccessService recordAccessService;

	public R_Request(
			@NonNull final RecordAccessConfigService recordAccessConfigService,
			@NonNull final RecordAccessService recordAccessService)
	{
		this.recordAccessConfigService = recordAccessConfigService;
		this.recordAccessService = recordAccessService;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void afterSave(@NonNull final I_R_Request requestRecord)
	{
		if (recordAccessConfigService.isFeatureEnabled(RecordAccessFeature.BPARTNER_HIERARCHY))
		{
			recordAccessService.grantAccess(
					RecordAccessGrantRequest.builder()
							.recordRef(TableRecordReference.of(I_R_Request.Table_Name, requestRecord.getR_Request_ID()))
							.principal(Principal.userId(UserId.ofRepoId(requestRecord.getCreatedBy())))
							.permission(Access.READ)
							.permission(Access.WRITE)
							.issuer(PermissionIssuer.AUTO_BP_HIERARCHY)
							.requestedBy(Env.getLoggedUserIdIfExists().orElse(UserId.SYSTEM))
							.description("grant permissions to creator")
							.build());
		}
	}
}
