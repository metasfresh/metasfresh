/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.sap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

/**
 * Keep in sync with {@code AD_Reference_ID=541661}
 */
@AllArgsConstructor
public enum SAPExternalRequest implements ReferenceListAwareEnum
{
	START_PRODUCT_SYNC("startProductsSync"),
	STOP_PRODUCT_SYNC("stopProductsSync"),
	START_BPARTNER_SYNC("startBPartnerSync"),
	STOP_BPARTNER_SYNC("stopBPartnerSync"),
	START_CREDIT_LIMITS_SYNC("startCreditLimitsSync"),
	STOP_CREDIT_LIMITS_SYNC("stopCreditLimitsSync");

	@Getter
	private final String code;

	private static final ImmutableMap<String, SAPExternalRequest> externalRequestByCode = Maps.uniqueIndex(Arrays.asList(values()), SAPExternalRequest::getCode);

	@NonNull
	public static SAPExternalRequest ofCode(@NonNull final String code)
	{
		final SAPExternalRequest type = externalRequestByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + SAPExternalRequest.class + " found for code: " + code);
		}
		return type;
	}

	public boolean isStartService()
	{
		return this == START_PRODUCT_SYNC
				|| this == START_CREDIT_LIMITS_SYNC
				|| this == START_BPARTNER_SYNC;
	}
}
