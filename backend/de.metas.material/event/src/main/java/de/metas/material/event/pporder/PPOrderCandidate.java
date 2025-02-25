/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.List;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class PPOrderCandidate
{
	int ppOrderCandidateId;
	boolean simulated;

	/**
	 * USed if this candidate is about a sub-BOM
	 */
	@With
	int parentPPOrderCandidateId;
	
	@With
	PPOrderData ppOrderData;
	List<PPOrderLineCandidate> lines;
	
	@JsonCreator
	@Builder(toBuilder = true)
	public PPOrderCandidate(
			@JsonProperty("ppOrderCandidateId") final int ppOrderCandidateId,
			@JsonProperty("simulated") final boolean simulated,
			@JsonProperty("parentPPOrderCandidateId") final int parentPPOrderCandidateId,
			@JsonProperty("ppOrderData") @NonNull final PPOrderData ppOrderData,
			@JsonProperty("lines") @Nullable final List<PPOrderLineCandidate> lines)
	{
		this.ppOrderCandidateId = ppOrderCandidateId;
		this.simulated = simulated;
		this.parentPPOrderCandidateId = parentPPOrderCandidateId;
		this.ppOrderData = ppOrderData;
		this.lines = lines;
	}
}