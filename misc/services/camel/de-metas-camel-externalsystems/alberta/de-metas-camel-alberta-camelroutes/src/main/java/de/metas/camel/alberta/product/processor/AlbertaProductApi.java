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

package de.metas.camel.alberta.product.processor;

import de.metas.camel.alberta.patient.AlbertaConnectionDetails;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.Article;
import lombok.NonNull;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class AlbertaProductApi
{
	@NonNull
	DefaultApi defaultApi;

	@NonNull
	AlbertaConnectionDetails connectionDetails;

	@NonNull
	public static AlbertaProductApi of(@NonNull final AlbertaConnectionDetails connectionDetails)
	{
		final ApiClient apiClient = new ApiClient().setBasePath(connectionDetails.getBasePath());
		final DefaultApi defaultApi = new DefaultApi(apiClient);

		return new AlbertaProductApi(defaultApi, connectionDetails);
	}

	public void upsertProduct(@NonNull final Article article)
	{
		try
		{
			defaultApi.addArticle(connectionDetails.getApiKey(), connectionDetails.getTenant(), article);
		}
		catch (final ApiException exception)
		{
			if (exception.getCode() == HttpStatus.METHOD_NOT_ALLOWED.value())
			{
				updateProduct(article);
			}
			else
			{
				throw new RuntimeException(exception);
			}
		}
	}

	private void updateProduct(@NonNull final Article article)
	{
		try
		{
			defaultApi.updateArticle(connectionDetails.getApiKey(), connectionDetails.getTenant(), article.getCustomerNumber(), article);
		}
		catch (final ApiException e)
		{
			throw new RuntimeException(e);
		}
	}
}
