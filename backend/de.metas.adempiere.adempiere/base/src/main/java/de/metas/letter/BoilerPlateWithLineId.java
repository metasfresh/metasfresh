/**
 *
 */
package de.metas.letter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.logging.LogManager;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

@Value
public class BoilerPlateWithLineId implements RepoIdAware
{
	@NonNull BoilerPlateId lineId;
	@NonNull BoilerPlateId headerId;
	private static final Logger logger = LogManager.getLogger(BoilerPlateWithLineId.class);

	@JsonCreator
	@Nullable
	public static BoilerPlateWithLineId ofRepoIdsOrNull(final int headerId, final int lineId)
	{
		if (headerId <= 0)
		{
			return null;
		}
		if (lineId <= 0)
		{
			logger.warn("ofRepoIdsOrNull: Possible development error: lineId={} while headerId={}. Returning null", lineId, headerId);
			return null;
		}
		return ofRepoIds(headerId, lineId);
	}

	public static BoilerPlateWithLineId ofRepoIds(final int orderRepoId, final int orderLineRepoId)
	{
		return new BoilerPlateWithLineId(BoilerPlateId.ofRepoId(orderRepoId), BoilerPlateId.ofRepoId(orderLineRepoId));
	}

	private BoilerPlateWithLineId(@NonNull final BoilerPlateId headerId, @NonNull final BoilerPlateId lineId)
	{
		this.lineId = lineId;
		this.headerId = headerId;
	}

	public static int toRepoId(final BoilerPlateWithLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@JsonValue
	public int getRepoId()
	{
		return lineId.getRepoId();
	}
}
