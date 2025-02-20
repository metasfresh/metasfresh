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

package de.metas.material.event.stockcandidate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static de.metas.material.event.MaterialEventConstants.MD_CANDIDATE_TABLE_NAME;

@Value
@Builder
public class MaterialCandidateChangedEvent implements MaterialEvent
{
	public static final String TYPE = "MaterialCandidateChangedEvent";

	@NonNull
	EventDescriptor eventDescriptor;

	@NonNull
	MaterialDescriptor materialDescriptor;

	@NonNull
	BigDecimal qtyFulfilledDelta;

	int mdCandidateId;

	@JsonCreator
	@Builder
	public MaterialCandidateChangedEvent(
			@JsonProperty("eventDescriptor") final @NonNull EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") final @NonNull MaterialDescriptor materialDescriptor,
			@JsonProperty("qtyFulfilledDelta") final @NonNull BigDecimal qtyFulfilledDelta,
			@JsonProperty("mdCandidateId") final int mdCandidateId)
	{
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
		this.qtyFulfilledDelta = qtyFulfilledDelta;
		this.mdCandidateId = mdCandidateId;
	}

	@Nullable
	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.ofNullable(MD_CANDIDATE_TABLE_NAME, mdCandidateId);
	}

	@Override
	public String getEventName() {return TYPE;}
}
