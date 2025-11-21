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

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

/**
 * Associates one attachment with one arbitrary {@link TableRecordReference}. 
 */
@Value
@Builder
public class AttachmentReference
{
	@With
	@Nullable
	AttachmentReferenceId id;
	
	@NonNull
	AttachmentEntryId attachmentEntryId;
	
	@NonNull
	TableRecordReference recordRef;

	@Nullable
	String attachmentNameOverride;
}
