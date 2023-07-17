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
	String METASFRESH_EXTERNAL_SYSTEM_NAME = "metasfresh";
	
	String PARSER_PROPERTY = "ParserProperty";
	String MASS_UPLOAD_STATISTICS_COLLECTOR_PROPERTY = "MassUpsertStatisticsCollector";
	String IS_CONTINUE_PARSING_PROPERTY = "IsContinueParsing";
	
	String FEEDBACK_RESOURCE_URL_HEADER = "FeedbackResourceURL";
	String FILE_NAME_HEADER = "CamelFileName";

	String MASS_JSON_REQUEST_PROCESSING_LOCATION_DEFAULT = "./src/main/resources/mass-process";
	String MASS_JSON_REQUEST_PROCESSING_LOCATION = "metasfresh.mass.json.request.directory.path";
}
