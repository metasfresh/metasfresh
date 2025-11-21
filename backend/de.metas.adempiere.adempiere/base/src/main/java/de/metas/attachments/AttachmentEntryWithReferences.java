/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.attachments;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
@Builder
public class AttachmentEntryWithReferences
{
	@NonNull AttachmentEntry entry;

	@NonNull ImmutableList<AttachmentReference> references;

	public boolean hasTableReference(@NonNull final TableRecordReference tableRecordReference)
	{
		return getReferenceFor(tableRecordReference) != null;
	}

	@Nullable
	private AttachmentReference getReferenceFor(@NonNull final TableRecordReference tableRecordReference)
	{
		for (final AttachmentReference reference : references)
		{
			if (reference.getRecordRef().equals(tableRecordReference))
			{
				return reference;
			}
		}
		return null;
	}

	public ImmutableList<TableRecordReference> getTableReferences()
	{
		return references.stream()
				.map(AttachmentReference::getRecordRef)
				.collect(ImmutableList.toImmutableList());
	}

	public String getFilename(@NonNull final TableRecordReference recordReference)
	{
		final AttachmentReference attachmentReference = getReferenceFor(recordReference);
		Check.assumeNotNull(attachmentReference, "attachmentReference not null for recordReference={} in {}", recordReference, this);
		
		return CoalesceUtil.coalesceNotNull(attachmentReference.getAttachmentNameOverride(), entry.getFilename());
	}
}
