/*
 * #%L
 * metasfresh-material-dispo-service
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

package de.metas.material.dispo.service.candidatechange.handler;

import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.function.Function;

@UtilityClass
public class CandidateHandlerUtil
{
	@NonNull
	public static Function<CandidateId, CandidateRepositoryWriteService.DeleteResult> getDeleteFunction(
			@Nullable final CandidateBusinessCase candidateBusinessCase,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryWriteService)
	{
		if (candidateBusinessCase == CandidateBusinessCase.STOCK_CHANGE)
		{
			return candidateRepositoryWriteService::deleteCandidateById;
		}

		return candidateRepositoryWriteService::deleteCandidateAndDetailsById;
	}
}
