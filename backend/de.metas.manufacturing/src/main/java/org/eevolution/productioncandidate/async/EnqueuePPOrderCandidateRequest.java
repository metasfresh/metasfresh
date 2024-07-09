/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.async;

import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Properties;

@Value
@Builder
public class EnqueuePPOrderCandidateRequest
{
	@NonNull
	PInstanceId adPInstanceId;

	@NonNull
	Properties ctx;

	@Nullable
	Boolean isCompleteDocOverride;

	boolean autoProcessCandidatesAfterProduction;
	
	boolean autoCloseCandidatesAfterProduction;

	@NonNull
	public static EnqueuePPOrderCandidateRequest of(@NonNull final PInstanceId pInstanceId, @NonNull final Properties ctx)
	{
		return EnqueuePPOrderCandidateRequest.builder()
				.adPInstanceId(pInstanceId)
				.ctx(ctx)
				.build();
	}
}
