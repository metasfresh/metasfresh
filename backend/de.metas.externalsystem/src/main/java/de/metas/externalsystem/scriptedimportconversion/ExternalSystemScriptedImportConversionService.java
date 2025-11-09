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

package de.metas.externalsystem.scriptedimportconversion;

import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN;

@Service
@RequiredArgsConstructor
public class ExternalSystemScriptedImportConversionService
{
	@NonNull
	private final UserAuthTokenRepository userAuthTokenRepository;

	@NonNull
	public Map<String, String> getParameters(@NonNull final ExternalSystemScriptedImportConversionConfig config)
	{
		final Map<String, String> parameters = new HashMap<>();

		final UserAuthToken token = userAuthTokenRepository.retrieveByUserId(config.getUserImportId(), RoleId.WEBUI);

		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, config.getEndpointName());
		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER, config.getScriptIdentifier());
		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN, token.getAuthToken());

		return parameters;
	}
}
