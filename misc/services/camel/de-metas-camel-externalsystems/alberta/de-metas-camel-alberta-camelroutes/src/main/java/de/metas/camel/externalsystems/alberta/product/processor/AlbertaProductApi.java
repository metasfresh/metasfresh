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

package de.metas.camel.externalsystems.alberta.product.processor;

import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.Article;
import io.swagger.client.model.ArticleMapping;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AlbertaProductApi
{
	@NonNull
	private final DefaultApi defaultApi;

	@NonNull
	private final AlbertaConnectionDetails connectionDetails;

	@NonNull
	public static AlbertaProductApi of(@NonNull final AlbertaConnectionDetails connectionDetails)
	{
		final ApiClient apiClient = new ApiClient().setBasePath(connectionDetails.getBasePath());
		final DefaultApi defaultApi = new DefaultApi(apiClient);

		return new AlbertaProductApi(defaultApi, connectionDetails);
	}

	@NonNull
	public ArticleMapping updateProductFallbackAdd(@NonNull final Article article)
	{
		try
		{
			return defaultApi.updateArticle(connectionDetails.getApiKey(), article.getCustomerNumber(), article);
		}
		catch (final ApiException exception)
		{
			if (exception.getCode() == HttpStatus.BAD_REQUEST.value())
			{
				return addArticle(article);
			}
			else
			{
				throw new RuntimeException(exception);
			}
		}
	}

	@NonNull
	public ArticleMapping addProductFallbackUpdate(@NonNull final Article article)
	{
		try
		{
			return defaultApi.addArticle(connectionDetails.getApiKey(), article);
		}
		catch (final ApiException exception)
		{
			if (exception.getCode() == HttpStatus.METHOD_NOT_ALLOWED.value())
			{
				return updateArticle(article);
			}
			else
			{
				throw new RuntimeException(exception);
			}
		}
	}

	private ArticleMapping updateArticle(@NonNull final Article article)
	{
		try
		{
			return defaultApi.updateArticle(connectionDetails.getApiKey(), article.getCustomerNumber(), article);
		}
		catch (final ApiException e)
		{
			throw new RuntimeException(e);
		}
	}

	private ArticleMapping addArticle(@NonNull final Article article)
	{
		try
		{
			return defaultApi.addArticle(connectionDetails.getApiKey(), article);
		}
		catch (final ApiException e)
		{
			throw new RuntimeException(e);
		}
	}
}
