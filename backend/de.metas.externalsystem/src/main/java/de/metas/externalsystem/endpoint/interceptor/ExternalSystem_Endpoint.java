/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.endpoint.interceptor;

import de.metas.externalsystem.endpoint.ExternalSystemEndpointService;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/** Fires {@link ExternalSystemEndpointService#resetFieldsHiddenByTheNewConfiguration(I_ExternalSystem_Endpoint)}, which is where the reset itself lives. */
@Interceptor(I_ExternalSystem_Endpoint.class)
@Component
@RequiredArgsConstructor
public class ExternalSystem_Endpoint
{
	@NonNull private final ExternalSystemEndpointService externalSystemEndpointService;

	/**
	 * One handler for all three governing columns on purpose: a save may change more than one of them at
	 * once, and a condition such as {@code Password}'s spans all three -- a handler keyed on a single column
	 * would decide that field's fate from part of the change only.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_ExternalSystem_Endpoint.COLUMNNAME_TransportType,
			I_ExternalSystem_Endpoint.COLUMNNAME_AuthType,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType })
	public void resetFieldsHiddenByTheNewConfiguration(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		externalSystemEndpointService.resetFieldsHiddenByTheNewConfiguration(endpoint);
	}
}
