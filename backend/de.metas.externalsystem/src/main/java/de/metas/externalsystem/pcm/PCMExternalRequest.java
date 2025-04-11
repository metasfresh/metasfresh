/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.externalsystem.pcm;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

/**
 * Keep in sync with {@code AD_Reference_ID=541858}
 */
@AllArgsConstructor
@Getter
public enum PCMExternalRequest implements ReferenceListAwareEnum
{
	START_PRODUCT_SYNC_LOCAL_FILE("startProductSyncLocalFile"),
	START_BPARTNER_SYNC_LOCAL_FILE("startBPartnerSyncLocalFile"),
	START_WAREHOUSE_SYNC_LOCAL_FILE("startWarehouseSyncLocalFile"),
	START_PURCHASE_ORDER_SYNC_LOCAL_FILE("startPurchaseOrderSyncLocalFile"),
	STOP_PRODUCT_SYNC_LOCAL_FILE("stopProductSyncLocalFile"),
	STOP_BPARTNER_SYNC_LOCAL_FILE("stopBPartnerSyncLocalFile"),
	STOP_WAREHOUSE_SYNC_LOCAL_FILE("stopWarehouseSyncLocalFile"),
	STOP_PURCHASE_ORDER_SYNC_LOCAL_FILE("stopPurchaseOrderSyncLocalFile");

	private final String code;

	public static PCMExternalRequest ofCode(@NonNull final String code)
	{
		final PCMExternalRequest type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PCMExternalRequest.class + " found for code: " + code);
		}
		return type;
	}

	public boolean isStartService()
	{
		return this == START_PRODUCT_SYNC_LOCAL_FILE
				|| this == START_BPARTNER_SYNC_LOCAL_FILE
				|| this == START_WAREHOUSE_SYNC_LOCAL_FILE
				|| this == START_PURCHASE_ORDER_SYNC_LOCAL_FILE;
	}

	private static final ImmutableMap<String, PCMExternalRequest> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PCMExternalRequest::getCode);
}
