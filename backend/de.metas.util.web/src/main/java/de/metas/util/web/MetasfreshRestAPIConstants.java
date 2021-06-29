package de.metas.util.web;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class MetasfreshRestAPIConstants
{
	/**
	 * Rest endpoints matching this constant are required to provide a secret authorization token.
	 */
	public static final String URL_PATTERN_API = "/api/*";

	public static final String URL_PATTERN_API_V2 = "/api/v2/*";
	
	/**
	 * @deprecated please invoke endpoints with either {@link #ENDPOINT_API_V1} or {@link #ENDPOINT_API_V2}.
	 */
	public static final String ENDPOINT_API_DEPRECATED = "/api";

	public static final String ENDPOINT_API_V1 = "/api/v1";

	/**
	 * going to be renamed to {@code v2} soon, after some initial breaking changes were made
	 */
	public static final String ENDPOINT_API_V2 = "/api/v2";
}
