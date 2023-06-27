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
@Getter
public enum SAPExternalRequest implements ReferenceListAwareEnum
{
	START_PRODUCT_SYNC_SFTP("startProductSyncSFTP"),
	START_BPARTNER_SYNC_SFTP("startBPartnerSyncSFTP"),
	START_CREDIT_LIMIT_SYNC_SFTP("startCreditLimitSyncSFTP"),
	START_CONVERSION_RATE_SYNC_SFTP("startConversionRateSyncSFTP"),
	START_PRODUCT_SYNC_LOCAL_FILE("startProductSyncLocalFile"),
	START_BPARTNER_SYNC_LOCAL_FILE("startBPartnerSyncLocalFile"),
	START_CREDIT_LIMIT_SYNC_LOCAL_FILE("startCreditLimitSyncLocalFile"),
	START_CONVERSION_RATE_SYNC_LOCAL_FILE("startConversionRateSyncLocalFile"),
	STOP_PRODUCT_SYNC_SFTP("stopProductSyncSFTP"),
	STOP_BPARTNER_SYNC_SFTP("stopBPartnerSyncSFTP"),
	STOP_CREDIT_LIMIT_SYNC_SFTP("stopCreditLimitSyncSFTP"),
	STOP_CONVERSION_RATE_SYNC_SFTP("stopConversionRateSyncSFTP"),
	STOP_PRODUCT_SYNC_LOCAL_FILE("stopProductSyncLocalFile"),
	STOP_BPARTNER_SYNC_LOCAL_FILE("stopBPartnerSyncLocalFile"),
	STOP_CREDIT_LIMIT_SYNC_LOCAL_FILE("stopCreditLimitSyncLocalFile"),
	STOP_CONVERSION_RATE_SYNC_LOCAL_FILE("stopConversionRateSyncLocalFile")
	;

	private final String code;

	public static SAPExternalRequest ofCode(@NonNull final String code)
	{
		final SAPExternalRequest type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + SAPExternalRequest.class + " found for code: " + code);
		}
		return type;
	}

	public boolean isStartService()
	{
		return this == START_PRODUCT_SYNC_SFTP
				|| this == START_CREDIT_LIMIT_SYNC_SFTP
				|| this == START_BPARTNER_SYNC_SFTP
				|| this == START_CONVERSION_RATE_SYNC_SFTP
				|| this == START_PRODUCT_SYNC_LOCAL_FILE
				|| this == START_CREDIT_LIMIT_SYNC_LOCAL_FILE
				|| this == START_BPARTNER_SYNC_LOCAL_FILE
				|| this == START_CONVERSION_RATE_SYNC_LOCAL_FILE;
	}

	public boolean isSFTPSpecific()
	{
		return this == START_PRODUCT_SYNC_SFTP
				|| this == START_BPARTNER_SYNC_SFTP
				|| this == START_CREDIT_LIMIT_SYNC_SFTP
				|| this == START_CONVERSION_RATE_SYNC_SFTP
				|| this == STOP_PRODUCT_SYNC_SFTP
				|| this == STOP_BPARTNER_SYNC_SFTP
				|| this == STOP_CREDIT_LIMIT_SYNC_SFTP
				|| this == STOP_CONVERSION_RATE_SYNC_SFTP;
	}

	public boolean isLocalFileSpecific()
	{
		return this == START_PRODUCT_SYNC_LOCAL_FILE
				|| this == START_BPARTNER_SYNC_LOCAL_FILE
				|| this == START_CREDIT_LIMIT_SYNC_LOCAL_FILE
				|| this == START_CONVERSION_RATE_SYNC_LOCAL_FILE
				|| this == STOP_PRODUCT_SYNC_LOCAL_FILE
				|| this == STOP_BPARTNER_SYNC_LOCAL_FILE
				|| this == STOP_CREDIT_LIMIT_SYNC_LOCAL_FILE
				|| this == STOP_CONVERSION_RATE_SYNC_LOCAL_FILE;
	}

	private static final ImmutableMap<String, SAPExternalRequest> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), SAPExternalRequest::getCode);
}
