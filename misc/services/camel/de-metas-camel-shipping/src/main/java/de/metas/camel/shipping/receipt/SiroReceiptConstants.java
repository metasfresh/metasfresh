/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.camel.shipping.receipt;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public interface SiroReceiptConstants
{
	String SIRO_RECEIPTS_FTP_PATH = "{{siro.ftp.retrieve.receipts.endpoint}}";
	String CREATE_RECEIPT_MF_URL = "{{metasfresh.api.baseurl}}/receipt";
	String RECEIPT_XML_TO_JSON_PROCESSOR = "receipt-xml-to-json-id";

	String LOCAL_STORAGE_URL = "{{siro.receipts.local.storage}}";

	Set<String> DATE_RECEIVED_PATTERNS = ImmutableSet.of("dd.MM.yyyy HH.mm.ss");
	Set<String> MOVEMENT_DATE_PATTERNS = ImmutableSet.of("dd.MM.yyyy");
	Set<String> EXPIRY_DATE_PATTERNS = ImmutableSet.of("yyyy/MM/dd", "yyyy.MM.dd", "dd.MM.yyyy");
}
