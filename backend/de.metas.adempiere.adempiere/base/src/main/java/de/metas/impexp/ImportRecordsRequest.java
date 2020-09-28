package de.metas.impexp;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;

import de.metas.impexp.processing.IImportProcess;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

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
@Builder
public class ImportRecordsRequest
{
	private static final String PARAM_ImportTableName = "ImportTableName";
	@NonNull
	String importTableName;

	@NonNull
	PInstanceId selectionId;

	private static final String PARAM_NotifyUserId = "NotifyUserId";

	@Nullable
	UserId notifyUserId;

	boolean completeDocuments;

	@NonNull
	@Default
	Params additionalParameters = Params.EMPTY;

	public Params toParams()
	{
		final Map<String, Object> map = new HashMap<>();

		for (String parameterName : additionalParameters.getParameterNames())
		{
			map.put(parameterName, additionalParameters.getParameterAsObject(parameterName));
		}

		map.put(PARAM_ImportTableName, importTableName);
		map.put(IImportProcess.PARAM_Selection_ID, selectionId);
		map.put(PARAM_NotifyUserId, notifyUserId);
		map.put(IImportProcess.PARAM_IsDocComplete, completeDocuments);

		return Params.ofMap(map);
	}

	public static ImportRecordsRequest ofParams(@NonNull final IParams params)
	{
		final String importTableName = params.getParameterAsString(PARAM_ImportTableName);
		if (Check.isBlank(importTableName))
		{
			throw new AdempiereException("Param `" + PARAM_ImportTableName + "` not found in " + params);
		}

		final PInstanceId selectionId = params.getParameterAsId(IImportProcess.PARAM_Selection_ID, PInstanceId.class);
		if (selectionId == null)
		{
			throw new AdempiereException("Param `" + IImportProcess.PARAM_Selection_ID + "` not found in " + params);
		}

		return builder()
				.importTableName(importTableName)
				.selectionId(selectionId)
				.notifyUserId(params.getParameterAsId(PARAM_NotifyUserId, UserId.class))
				.completeDocuments(params.getParameterAsBool(IImportProcess.PARAM_IsDocComplete))
				.additionalParameters(Params.copyOf(params))
				.build();
	}
}
