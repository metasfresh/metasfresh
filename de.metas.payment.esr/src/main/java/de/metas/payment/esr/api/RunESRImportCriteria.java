/*
 * #%L
 * de.metas.payment.esr
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

package de.metas.payment.esr.api;

import de.metas.attachments.AttachmentEntryId;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class RunESRImportCriteria
{
	@NonNull
	I_ESR_Import esrImport;

	@NonNull
	AttachmentEntryId attachmentEntryId;

	@NonNull
	String asyncBatchName;

	@NonNull
	String asyncBatchDesc;

	@NonNull
	ILoggable loggableClass;

	@Nullable
	PInstanceId pInstanceId;
}
