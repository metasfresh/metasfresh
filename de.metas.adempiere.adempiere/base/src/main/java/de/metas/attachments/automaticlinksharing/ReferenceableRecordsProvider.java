package de.metas.attachments.automaticlinksharing;

import java.util.Collection;

import org.adempiere.util.lang.ITableRecordReference;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.automaticlinksharing.RecordToReferenceProviderService.ExpandResult;

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

public interface ReferenceableRecordsProvider
{
	/**
	 * For the given records that shall be linked to an attachment entry, retrieve additional records that shall <b>also</b> be linked.
	 * Don't worry about the possibility that the records returned by this method might be already linked to begin with.
	 *
	 * @param newlyLinkedRecordRefs records that are to be just linked to an attachment entry.
	 */
	public ExpandResult expand(
			AttachmentEntry attachmentEntry,
			Collection<? extends ITableRecordReference> newlyLinkedRecordRefs);
}
