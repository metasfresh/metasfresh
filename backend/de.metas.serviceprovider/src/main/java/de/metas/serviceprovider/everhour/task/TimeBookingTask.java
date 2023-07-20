/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.everhour.task;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value(staticConstructor = "of")
public class TimeBookingTask
{
	@NonNull
	TimeBookingTaskId id;

	@NonNull
	String url;

	public boolean isSourceUnknown()
	{
		return id.getSource().isUnknown();
	}

	@Value
	public static class TimeBookingTaskId
	{
		@NonNull
		String rawId;

		@NonNull
		Source source;

		@NonNull
		String extractedId;

		public boolean isGithubTask()
		{
			return source.isGithub();
		}

		@NonNull
		public static TimeBookingTaskId of(@NonNull final String rawId)
		{
			final Source source = Source.getSourceForId(rawId).orElse(Source.UNKNOWN);
			final String extractedId = source.getId(rawId)
					.orElseThrow(() -> new AdempiereException("No Id could be extracted for source!")
							.appendParametersToMessage()
							.setParameter("Source", source)
							.setParameter("rawId", rawId));

			return new TimeBookingTaskId(rawId, source, extractedId);
		}
	}
}
