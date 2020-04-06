package de.metas.security.permissions.bpartner_hierarchy;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.bpartner.BPartnerId;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocument;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class BPartnerDependentDocumentEvent
{
	public static BPartnerDependentDocumentEvent newRecord(final BPartnerDependentDocument doc)
	{
		return prepareFrom(doc)
				.eventType(EventType.NEW_RECORD)
				.build();
	}

	public static BPartnerDependentDocumentEvent bpartnerChanged(final BPartnerDependentDocument doc)
	{
		return prepareFrom(doc)
				.eventType(EventType.BPARTNER_CHANGED)
				.build();
	}

	private static BPartnerDependentDocumentEventBuilder prepareFrom(@NonNull final BPartnerDependentDocument doc)
	{
		return builder()
				.documentRef(doc.getDocumentRef())
				.newBPartnerId(doc.getNewBPartnerId())
				.oldBPartnerId(doc.getOldBPartnerId())
				.updatedBy(doc.getUpdatedBy());
	}

	@JsonProperty("documentRef")
	TableRecordReference documentRef;

	public enum EventType
	{
		NEW_RECORD, BPARTNER_CHANGED
	}

	@JsonProperty("eventType")
	EventType eventType;

	@JsonProperty("newBPartnerId")
	BPartnerId newBPartnerId;

	@JsonProperty("oldBPartnerId")
	BPartnerId oldBPartnerId;

	@JsonProperty("updatedBy")
	UserId updatedBy;

	@JsonCreator
	@Builder
	private BPartnerDependentDocumentEvent(
			@JsonProperty("documentRef") @NonNull final TableRecordReference documentRef,
			@JsonProperty("eventType") @NonNull final EventType eventType,
			@JsonProperty("newBPartnerId") @Nullable final BPartnerId newBPartnerId,
			@JsonProperty("oldBPartnerId") @Nullable final BPartnerId oldBPartnerId,
			@JsonProperty("updatedBy") @NonNull final UserId updatedBy)
	{
		this.documentRef = documentRef;
		this.eventType = eventType;
		this.newBPartnerId = newBPartnerId;
		this.oldBPartnerId = oldBPartnerId;
		this.updatedBy = updatedBy;
	}
}
