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

import de.metas.externalsystem.endpoint.TransportType;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_ExternalSystem_Endpoint.class)
@Component
@RequiredArgsConstructor
public class ExternalSystem_Endpoint
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_ExternalSystem_Endpoint.COLUMNNAME_AuthType)
	public void resetCredentials(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		endpoint.setAuthToken(null);
		endpoint.setLoginUsername(null);
		endpoint.setPassword(null);
		endpoint.setClientId(null);
		endpoint.setClientSecret(null);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_ExternalSystem_Endpoint.COLUMNNAME_TransportType)
	public void resetTransportSpecificFields(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		final String newTransportType = endpoint.getTransportType();
		if (TransportType.HTTP.getCode().equals(newTransportType))
		{
			// Switching to HTTP — clear SFTP-specific fields
			endpoint.setSftpHost(null);
			endpoint.setSftpPort(0);
			endpoint.setSftpUsername(null);
			endpoint.setSftpAuthType(null);
			endpoint.setSshPrivateKey(null);
			endpoint.setSftpRemotePath(null);
			endpoint.setSftpFilenamePattern(null);
		}
		else if (TransportType.SFTP.getCode().equals(newTransportType))
		{
			// Switching to SFTP — clear HTTP-specific fields
			endpoint.setOutboundHttpEP(null);
			endpoint.setOutboundHttpMethod(null);
			endpoint.setContentType(null);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType)
	public void resetSftpCredentials(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		final String newSftpAuthType = endpoint.getSftpAuthType();
		if (de.metas.externalsystem.endpoint.SftpAuthType.PASSWORD.getCode().equals(newSftpAuthType))
		{
			// Switching to password auth — clear SSH key
			endpoint.setSshPrivateKey(null);
		}
		else if (de.metas.externalsystem.endpoint.SftpAuthType.SSH_KEY.getCode().equals(newSftpAuthType))
		{
			// Switching to SSH key auth — clear password
			endpoint.setPassword(null);
		}
	}
}
