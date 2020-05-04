/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.issue;

import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.timebooking.Effort;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_Issue.class)
@Component
public class S_Issue
{
	private final ExternalReferenceRepository externalReferenceRepository;
	private final IssueEffortService issueEffortService;

	public S_Issue(final ExternalReferenceRepository externalReferenceRepository, final IssueEffortService issueEffortService)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.issueEffortService = issueEffortService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_S_Issue record)
	{
		externalReferenceRepository.deleteByRecordIdAndType(record.getS_Issue_ID(), ExternalReferenceType.ISSUE_ID);
	}

	@ModelChange(timings = {ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW}, ifColumnsChanged = I_S_Issue.COLUMNNAME_S_Parent_Issue_ID)
	public void afterParentChanged(@NonNull final I_S_Issue record)
	{
		final I_S_Issue oldRecord = InterfaceWrapperHelper.createOld(record, I_S_Issue.class);

		if (record.getS_Parent_Issue_ID() != oldRecord.getS_Parent_Issue_ID())
		{
			final Effort effort = Effort.ofNullable(record.getAggregatedEffort());

			final IssueId currentParent = IssueId.ofRepoIdOrNull(record.getS_Parent_Issue_ID());

			if (currentParent != null)
			{
				issueEffortService.addAggregatedEffort(currentParent, effort);
			}

			final IssueId oldParent = IssueId.ofRepoIdOrNull(oldRecord.getS_Parent_Issue_ID());

			if ( oldParent != null)
			{
				issueEffortService.addAggregatedEffort(oldParent, effort.negate());
			}
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_S_Issue.COLUMNNAME_IsActive)
	public void reactivateLinkedExternalReferences(@NonNull final I_S_Issue record)
	{
		if (record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getS_Issue_ID(), ExternalReferenceType.ISSUE_ID, record.isActive());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_S_Issue.COLUMNNAME_IsActive)
	public void inactivateLinkedExternalReferences(@NonNull final I_S_Issue record)
	{
		if (!record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getS_Issue_ID(), ExternalReferenceType.ISSUE_ID, record.isActive());
		}
	}

}
