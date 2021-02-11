/*
 * #%L
 * de-metas-camel-edi
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

package de.metas.edi.esb.remadvimport.ecosio;

public interface EcosioRemadvConstants
{
	String REMADV_XML_TO_JSON_PROCESSOR = "remadv-xml-to-json-processor-id";
	String ECOSIO_REMADV_XML_TO_JSON_ROUTE = "remadv-xml-to-json-route-id";

	String ECOSIO_REMADV_SFTP_URL = "{{edi.ecosio.remadv.source}}";
	String ECOSIO_AUTH_TOKEN = "{{edi.ecosio.remadv-import.auth}}";
	String CREATE_REMADV_MF_URL = "{{metasfresh.api.baseurl}}/payment/remittanceAdvice";

	String NUMBER_OF_ITEMS = "NumberOfItems";
	String AUTHORIZATION = "Authorization";

	String DOC_PREFIX = "doc-";
	String GLN_PREFIX = "gln-";
	String DOCUMENT_ZONE_ID = "Europe/Vienna";
}
