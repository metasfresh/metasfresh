package de.metas.ui.web.cache;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ADLanguageList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ETagResponseEntityBuilder<T extends ETagAware, R>
{
	public static final <T extends ETagAware> ETagResponseEntityBuilder<T, T> ofETagAware(final WebRequest request, final T etagAware)
	{
		return new ETagResponseEntityBuilder<>(request, etagAware, () -> etagAware);
	}

	private final WebRequest request;
	private final T etagAware;
	private final Supplier<R> result;
	private Supplier<JSONOptions> jsonOptions = () -> null;
	private int cacheMaxAgeSec = 10;
	private boolean includeLanguageInETag = false;

	private ETagResponseEntityBuilder(@NonNull final WebRequest request, @NonNull final T etagAware, @NonNull final Supplier<R> result)
	{
		this.request = request;
		this.etagAware = etagAware;
		this.result = result;
	}

	public ETagResponseEntityBuilder<T, R> cacheMaxAge(final int cacheMaxAgeSec)
	{
		this.cacheMaxAgeSec = cacheMaxAgeSec >= 0 ? cacheMaxAgeSec : 0;
		return this;
	}

	public ETagResponseEntityBuilder<T, R> includeLanguageInETag()
	{
		includeLanguageInETag(true);
		return this;
	}

	private ETagResponseEntityBuilder<T, R> includeLanguageInETag(final boolean includeLanguageInETag)
	{
		this.includeLanguageInETag = includeLanguageInETag;
		return this;
	}

	public <R2> ETagResponseEntityBuilder<T, R2> map(@NonNull final Function<R, R2> resultMapper)
	{
		final Supplier<R> result = this.result;
		final Supplier<R2> newResult = () -> resultMapper.apply(result.get());
		return new ETagResponseEntityBuilder<>(request, etagAware, newResult)
				.includeLanguageInETag(includeLanguageInETag)
				.cacheMaxAge(this.cacheMaxAgeSec);
	}

	public ETagResponseEntityBuilder<T, R> jsonOptions(@NonNull final Supplier<JSONOptions> jsonOptions)
	{
		this.jsonOptions = ExtendedMemorizingSupplier.of(jsonOptions);
		return this;
	}

	private JSONOptions getJSONOptions()
	{
		final JSONOptions jsonOptions = this.jsonOptions.get();
		if (jsonOptions == null)
		{
			throw new IllegalStateException("jsonOptions not configured");
		}
		return jsonOptions;
	}

	private ETag getETag()
	{
		ETag etag = etagAware.getETag();
		if (includeLanguageInETag)
		{
			final String adLanguage = getJSONOptions().getAD_Language();
			etag = etag.overridingAttributes(ImmutableMap.of("lang", adLanguage));
		}

		return etag;
	}

	public <JSONType> ResponseEntity<JSONType> toJson(final BiFunction<R, JSONOptions, JSONType> toJsonMapper)
	{
		return toResponseEntity((responseBuilder, result) -> responseBuilder.body(toJsonMapper.apply(result, getJSONOptions())));
	}

	public <BodyType> ResponseEntity<BodyType> toResponseEntity(final BiFunction<ResponseEntity.BodyBuilder, R, ResponseEntity<BodyType>> toJsonMapper)
	{
		// Check ETag
		final String etag = getETag().toETagString();
		if (request.checkNotModified(etag))
		{
			// Response: 304 Not Modified
			return newResponse(HttpStatus.NOT_MODIFIED, etag).build();
		}

		// Get the result and convert it to JSON
		final R result = this.result.get();

		final ResponseEntity.BodyBuilder newResponse = newResponse(HttpStatus.OK, etag);
		return toJsonMapper.apply(newResponse, result);
	}

	private final ResponseEntity.BodyBuilder newResponse(final HttpStatus status, final String etag)
	{

		ResponseEntity.BodyBuilder response = ResponseEntity.status(status)
				.eTag(etag)
				.cacheControl(CacheControl.maxAge(cacheMaxAgeSec, TimeUnit.SECONDS));

		final String adLanguage = getJSONOptions().getAD_Language();
		if (adLanguage != null && !adLanguage.isEmpty())
		{
			final String contentLanguage = ADLanguageList.toHttpLanguageTag(adLanguage);
			response.header(HttpHeaders.CONTENT_LANGUAGE, contentLanguage);
			response.header(HttpHeaders.VARY, HttpHeaders.ACCEPT_LANGUAGE); // advice browser to include ACCEPT_LANGUAGE in their caching key
		}

		return response;
	}
}
