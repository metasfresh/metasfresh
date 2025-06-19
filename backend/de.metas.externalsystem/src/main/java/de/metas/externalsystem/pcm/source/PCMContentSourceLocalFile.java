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

package de.metas.externalsystem.pcm.source;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.pcm.PCMExternalRequest;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Optional;

@Value
@Builder
public class PCMContentSourceLocalFile
{
	private static final AdMessageKey MSG_DUPLICATE_FILE_LOOKUP_DETAILS = AdMessageKey.of("ExternalSystemConfigPCMDuplicateFileLookupDetails");

	@NonNull
	String rootLocation;

	@NonNull
	String processedDirectory;

	@NonNull
	String erroredDirectory;

	@NonNull
	Duration pollingFrequency;

	// bpartner
	@Nullable
	String fileNamePatternBPartner;

	// product
	@Nullable
	String fileNamePatternProduct;

	// warehouse
	@Nullable
	String fileNamePatternWarehouse;

	// purchase order
	@Nullable
	String fileNamePatternPurchaseOrder;

	@NonNull
	public BooleanWithReason isStartServicePossible(
			@NonNull final PCMExternalRequest pcmExternalRequest,
			@NonNull final ExternalSystemParentConfigId parentId,
			@NonNull final IMsgBL msgBL)
	{
		if (!pcmExternalRequest.isStartService())
		{
			return BooleanWithReason.TRUE;
		}

		final ImmutableMap<PCMExternalRequest, String> request2LookupInfo = getLookupInfoByExternalRequest();

		final String targetLookupInfo = Optional.ofNullable(request2LookupInfo.get(pcmExternalRequest))
				.orElseThrow(() -> new AdempiereException("Unexpected pcmExternalRequest=" + pcmExternalRequest.getCode()));

		final boolean isFileLookupInfoDuplicated = CollectionUtils.hasDuplicatesForValue(request2LookupInfo.values(), targetLookupInfo);

		if (isFileLookupInfoDuplicated)
		{
			final ITranslatableString duplicateFileLookupInfoErrorMsg = msgBL.getTranslatableMsgText(MSG_DUPLICATE_FILE_LOOKUP_DETAILS,
																									 pcmExternalRequest.getCode(),
																									 parentId.getRepoId());

			return BooleanWithReason.falseBecause(duplicateFileLookupInfoErrorMsg);
		}

		return BooleanWithReason.TRUE;
	}

	@NonNull
	private ImmutableMap<PCMExternalRequest, String> getLookupInfoByExternalRequest()
	{
		return ImmutableMap.of(
				PCMExternalRequest.START_PRODUCT_SYNC_LOCAL_FILE, Strings.nullToEmpty(fileNamePatternProduct),
				PCMExternalRequest.START_BPARTNER_SYNC_LOCAL_FILE, Strings.nullToEmpty(fileNamePatternBPartner),
				PCMExternalRequest.START_WAREHOUSE_SYNC_LOCAL_FILE, Strings.nullToEmpty(fileNamePatternWarehouse),
				PCMExternalRequest.START_PURCHASE_ORDER_SYNC_LOCAL_FILE, Strings.nullToEmpty(fileNamePatternPurchaseOrder)
		);
	}
}
