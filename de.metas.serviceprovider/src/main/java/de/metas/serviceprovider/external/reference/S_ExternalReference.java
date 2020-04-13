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

package de.metas.serviceprovider.external.reference;

import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.serviceprovider.milestone.MilestoneRepository;
import de.metas.serviceprovider.model.I_S_ExternalReference;
import de.metas.serviceprovider.timebooking.TimeBooking;
import de.metas.serviceprovider.timebooking.TimeBookingId;
import de.metas.serviceprovider.timebooking.TimeBookingRepository;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Interceptor(I_S_ExternalReference.class)
@Component
public class S_ExternalReference
{
	private final IUserDAO userDAO;
	private final IssueRepository issueRepository;
	private final TimeBookingRepository timeBookingRepository;
	private final MilestoneRepository milestoneRepository;

	public S_ExternalReference(final IUserDAO userDAO, final IssueRepository issueRepository, final TimeBookingRepository timeBookingRepository, final MilestoneRepository milestoneRepository)
	{
		this.userDAO = userDAO;
		this.issueRepository = issueRepository;
		this.timeBookingRepository = timeBookingRepository;
		this.milestoneRepository = milestoneRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW })
	public void beforeSave(final I_S_ExternalReference record)
	{
		final ExternalReferenceType externalReferenceType = ExternalReferenceType.ofCode(record.getType());

		switch (externalReferenceType)
		{
			case USER_ID:
				validateUserId(UserId.ofRepoId(record.getRecord_ID()));
				break;
			case TIME_BOOKING_ID:
				validateTimeBooking(TimeBookingId.ofRepoId(record.getRecord_ID()));
				break;
			case ISSUE_ID:
				validateIssueId(IssueId.ofRepoId(record.getRecord_ID()));
				break;
			case MILESTONE_ID:
				validateMilestone(MilestoneId.ofRepoId(record.getRecord_ID()));
				break;
			default:
				throw new AdempiereException("There is no validation in place for ExternalReferenceType: " + externalReferenceType.getCode());
		}
	}

	private void validateUserId(@NonNull final UserId userId)
	{
		try
		{
			userDAO.getById(userId);
		}
		catch (final AdempiereException ex)
		{
			throw new AdempiereException("No user found for the given recordId! ", ex)
					.appendParametersToMessage()
					.setParameter("recordId", userId.getRepoId());
		}
	}

	private void validateIssueId(@NonNull final IssueId issueId)
	{
		final Optional<IssueEntity> issueEntity = issueRepository.getByIdOptional(issueId, false);

		if (!issueEntity.isPresent())
		{
			throw new AdempiereException("No issue found for ID: 'value' .")
					.appendParametersToMessage()
					.setParameter("value", issueId.getRepoId());
		}
	}

	private void validateTimeBooking(@NonNull final TimeBookingId timeBookingId)
	{
		final Optional<TimeBooking> timeBooking = timeBookingRepository.getByIdOptional(timeBookingId);

		if (!timeBooking.isPresent())
		{
			throw new AdempiereException("No TimeBooking found for ID: 'value' .")
					.appendParametersToMessage()
					.setParameter("value", timeBookingId.getRepoId());
		}
	}

	private void validateMilestone(@NonNull final MilestoneId milestoneId)
	{

		if (!milestoneRepository.exists(milestoneId))
		{
			throw new AdempiereException("No Milestone found for ID: 'value' .")
					.appendParametersToMessage()
					.setParameter("value", milestoneId.getRepoId());
		}
	}
}
