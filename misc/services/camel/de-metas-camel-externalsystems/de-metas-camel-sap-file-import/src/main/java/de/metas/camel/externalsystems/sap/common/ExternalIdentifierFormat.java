/*
 * #%L
 * de-metas-camel-sap
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

package de.metas.camel.externalsystems.sap.common;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import static de.metas.camel.externalsystems.sap.SAPConstants.SAP_SYSTEM_NAME;

@UtilityClass
public class ExternalIdentifierFormat
{
	private final static String EXTERNAL_ID_PREFIX = "ext";

	@NonNull
	public static String formatExternalId(@NonNull final String externalId)
	{
		return EXTERNAL_ID_PREFIX + "-" + SAP_SYSTEM_NAME + "-" + externalId;
	}
}
