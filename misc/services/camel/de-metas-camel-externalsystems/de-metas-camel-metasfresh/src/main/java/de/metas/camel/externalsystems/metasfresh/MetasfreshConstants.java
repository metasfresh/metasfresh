/*
 * #%L
 * de-metas-camel-metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.metasfresh;

public interface MetasfreshConstants
{
	String PARSER_PROPERTY = "ParserProperty";
	String IS_CONTINUE_PARSING_PROPERTY = "IsContinueParsing";
	String RESPONSE_ITEMS_NO_PROPERTY = "responseItemsNo";
	String MASS_PROCESSING_TARGET_ROUTE = "itemType";

	String AUTHORIZATION = "Authorization";
	String RESPONSE_URL_HEADER = "ResponseURL";
	String FILE_NAME_HEADER = "CamelFileName";

	String HEADER_AUTH_TOKEN = "X-Auth-Token";
}
