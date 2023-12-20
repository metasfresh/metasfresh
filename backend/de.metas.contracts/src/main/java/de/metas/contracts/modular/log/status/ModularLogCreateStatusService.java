/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.log.status;

import de.metas.async.QueueWorkPackageId;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModularLogCreateStatusService
{
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	@NonNull
	private final ModularLogCreateStatusRepository statusRepository;

	public void setStatusEnqueued(@NonNull final QueueWorkPackageId workPackageId, @NonNull final TableRecordReference recordRef)
	{
		statusRepository.save(ModularLogCreateStatus.builder()
				.workPackageId(workPackageId)
				.recordReference(recordRef)
				.status(ProcessingStatus.ENQUEUED)
				.build());
	}

	public void setStatusSuccessfullyProcessed(@NonNull final QueueWorkPackageId workPackageId, @NonNull final TableRecordReference recordRef)
	{
		statusRepository.save(ModularLogCreateStatus.builder()
				.workPackageId(workPackageId)
				.recordReference(recordRef)
				.status(ProcessingStatus.SUCCESSFULLY_PROCESSED)
				.build());
	}

	public void setStatusErrored(
			@NonNull final QueueWorkPackageId workPackageId,
			@NonNull final TableRecordReference recordRef,
			@NonNull final Throwable error)
	{
		final AdIssueId adIssueId = errorManager.createIssue(error);

		statusRepository.save(ModularLogCreateStatus.builder()
				.workPackageId(workPackageId)
				.recordReference(recordRef)
				.status(ProcessingStatus.ERRORED)
				.issueId(adIssueId)
				.build());
	}
}
