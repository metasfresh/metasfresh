/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.common;

import io.swagger.client.ApiClient;
import io.swagger.client.api.AttachmentApi;
import io.swagger.client.api.DocumentApi;
import io.swagger.client.api.UserApi;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AlbertaApiProvider
{
	@NonNull
	public AttachmentApi getAttachmentApi(@NonNull final ApiClient apiClient)
	{
		return new AttachmentApi(apiClient);
	}

	@NonNull
	public DocumentApi getDocumentApi(@NonNull final ApiClient apiClient)
	{
		return new DocumentApi(apiClient);
	}

	@NonNull
	public UserApi getUserApi(@NonNull final ApiClient apiClient)
	{
		return new UserApi(apiClient);
	}
}
