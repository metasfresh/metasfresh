package de.metas.impexp;

import de.metas.impexp.processing.IImportProcess;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Request for Import Phase 2,
 * i.e. the actual import, where we are iterating records one by one, group them and actually import them.
 */
@Value
public class ImportRecordsRequest
{
	private static final String PARAM_ImportTableName = "ImportTableName";
	@NonNull String importTableName;

	private static final String PARAM_AD_Client_ID = "AD_Client_ID";
	@NonNull ClientId clientId;

	private static final String PARAM_Selection_ID = IImportProcess.PARAM_Selection_ID;
	@Nullable PInstanceId selectionId;

	private static final String PARAM_Limit = "Limit";
	@NonNull QueryLimit limit;

	private static final String PARAM_NotifyUserId = "NotifyUserId";
	@Nullable UserId notifyUserId;

	private static final String PARAM_IsDocComplete = IImportProcess.PARAM_IsDocComplete;
	boolean completeDocuments;

	private static final String PARAM_IsSubsequentRequest = IImportProcess.PARAM_IsSubsequentRequest;
	boolean isSubsequentRequest;

	@NonNull Params additionalParameters;

	@Builder
	private ImportRecordsRequest(
			@NonNull final String importTableName,
			@Nullable ClientId clientId,
			@Nullable final PInstanceId selectionId,
			@Nullable QueryLimit limit,
			@Nullable final UserId notifyUserId,
			final boolean completeDocuments,
			final boolean isSubsequentRequest,
			@Nullable Params additionalParameters)
	{
		this.importTableName = importTableName;
		this.clientId = clientId != null ? clientId : ClientId.METASFRESH;
		this.selectionId = selectionId;
		this.limit = limit != null ? limit : QueryLimit.NO_LIMIT;
		this.notifyUserId = notifyUserId;
		this.completeDocuments = completeDocuments;
		this.isSubsequentRequest = isSubsequentRequest;
		this.additionalParameters = Params.builder()
				.putAll(additionalParameters != null ? additionalParameters : Params.EMPTY)
				.value(PARAM_ImportTableName, this.importTableName)
				.value(PARAM_AD_Client_ID, this.clientId)
				.value(PARAM_Selection_ID, this.selectionId)
				.value(PARAM_Limit, this.limit.toIntOrZero())
				.value(PARAM_NotifyUserId, this.notifyUserId)
				.value(PARAM_IsDocComplete, this.completeDocuments)
				.value(PARAM_IsSubsequentRequest, this.isSubsequentRequest)
				.build();
	}

	public Params toParams() {return additionalParameters;}

	public static ImportRecordsRequest ofParams(@NonNull final IParams params)
	{
		final String importTableName = StringUtils.trimBlankToNull(params.getParameterAsString(PARAM_ImportTableName));
		if (importTableName == null)
		{
			throw new AdempiereException("Param `" + PARAM_ImportTableName + "` not found in " + params);
		}

		return builder()
				.importTableName(importTableName)
				.clientId(params.getParameterAsId(PARAM_AD_Client_ID, ClientId.class, ClientId.METASFRESH))
				.selectionId(params.getParameterAsId(PARAM_Selection_ID, PInstanceId.class))
				.limit(QueryLimit.ofNullableOrNoLimit(params.getParameterAsInt(PARAM_Limit, 0)))
				.notifyUserId(params.getParameterAsId(PARAM_NotifyUserId, UserId.class))
				.completeDocuments(params.getParameterAsBool(PARAM_IsDocComplete))
				.isSubsequentRequest(params.getParameterAsBool(PARAM_IsSubsequentRequest))
				.additionalParameters(Params.copyOf(params))
				.build();
	}
}
