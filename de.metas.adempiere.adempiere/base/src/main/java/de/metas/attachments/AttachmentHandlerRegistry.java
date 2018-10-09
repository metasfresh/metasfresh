package de.metas.attachments;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class AttachmentHandlerRegistry
{
	private final List<AttachmentHandler> handlers;

	public AttachmentHandlerRegistry(@NonNull final Optional<List<AttachmentHandler>> handlers)
	{
		this.handlers = handlers.orElse(ImmutableList.of());
	}

	/**
	 * For the given attachments and linked records, find further records that shall <i>also</i> be linked to those attachments.
	 *
	 * @param newlyLinkedRecords may be a collection of model objects or {@link ITableRecordReference}s
	 */
	public ExpandResult expand(@NonNull final Collection<? extends Object> newlyLinkedRecords)
	{
		final List<TableRecordReference> tableRecordReferences = TableRecordReference.ofCollection(newlyLinkedRecords);

		final ImmutableSet.Builder<ITableRecordReference> result = ImmutableSet.builder();

		for (final AttachmentHandler handler : handlers)
		{
			final ExpandResult singleHandlerResult = handler.expand(tableRecordReferences);
			result.addAll(singleHandlerResult.getAdditionalReferences());
		}
		return new ExpandResult(result.build());
	}

	@Value
	@Builder
	public static class ExpandResult
	{
		public static final ExpandResult EMPTY = new ExpandResult(ImmutableSet.of());

		ImmutableSet<? extends ITableRecordReference> additionalReferences;
	}
}
