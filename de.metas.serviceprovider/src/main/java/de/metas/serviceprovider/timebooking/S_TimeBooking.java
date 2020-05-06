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

package de.metas.serviceprovider.timebooking;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.issue.IssueEffortService;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.model.I_S_TimeBooking;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.HmmUtils;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Interceptor(I_S_TimeBooking.class)
@Callout(I_S_TimeBooking.class)
@Component
public class S_TimeBooking
{
	private final String INCORRECT_FORMAT_MSG_KEY = "de.metas.serviceprovider.incorrectHmmFormat";

	private final ExternalReferenceRepository externalReferenceRepository;
	private final IMsgBL msgBL;
	private final IssueEffortService issueEffortService;

	public S_TimeBooking(final ExternalReferenceRepository externalReferenceRepository, final IMsgBL msgBL, final IssueEffortService issueEffortService)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.msgBL = msgBL;
		this.issueEffortService = issueEffortService;
	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_S_TimeBooking record)
	{
		externalReferenceRepository.deleteByRecordIdAndType(record.getS_TimeBooking_ID(), ExternalReferenceType.TIME_BOOKING_ID);

		final IssueId issueId = IssueId.ofRepoId(record.getS_Issue_ID());
		final Effort effort = Effort.of(record.getBookedSeconds().longValue());

		updateIssueEfforts(effort.negate(), issueId);
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE}, ifColumnsChanged = I_S_TimeBooking.COLUMNNAME_HoursAndMinutes)
	public void onHoursAndMinutesUpdate(@NonNull final I_S_TimeBooking record)
	{
		record.setBookedSeconds(BigDecimal.valueOf(HmmUtils.hmmToSeconds(record.getHoursAndMinutes())));

		final I_S_TimeBooking oldRecord = InterfaceWrapperHelper.createOld(record, I_S_TimeBooking.class);

		final long deltaBookedSeconds = record.getBookedSeconds().subtract(oldRecord.getBookedSeconds()).longValue();

		final Effort effort = Effort.of(deltaBookedSeconds);
		final IssueId issueId = IssueId.ofRepoId(record.getS_Issue_ID());

		updateIssueEfforts(effort, issueId);
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_CHANGE}, ifColumnsChanged = {I_S_TimeBooking.COLUMNNAME_S_Issue_ID})
	public void onIssueChange(@NonNull final I_S_TimeBooking record)
	{
		final I_S_TimeBooking oldRecord = InterfaceWrapperHelper.createOld(record, I_S_TimeBooking.class);

		if (record.getS_Issue_ID() != oldRecord.getS_Issue_ID())
		{
			final IssueId oldIssueId = IssueId.ofRepoId(oldRecord.getS_Issue_ID());
			final Effort effort = Effort.of(record.getBookedSeconds().longValue());

			updateIssueEfforts(effort.negate(), oldIssueId);

			updateIssueEfforts(effort, IssueId.ofRepoId(record.getS_Issue_ID()));
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_S_TimeBooking.COLUMNNAME_IsActive)
	public void reactivateLinkedExternalReferences(@NonNull final I_S_TimeBooking record)
	{
		if (record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getS_TimeBooking_ID(), ExternalReferenceType.TIME_BOOKING_ID, record.isActive());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_S_TimeBooking.COLUMNNAME_IsActive)
	public void inactivateLinkedExternalReferences(@NonNull final I_S_TimeBooking record)
	{
		if (!record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getS_TimeBooking_ID(), ExternalReferenceType.TIME_BOOKING_ID, record.isActive());
		}
	}

	@CalloutMethod(columnNames = { I_S_TimeBooking.COLUMNNAME_HoursAndMinutes })
	public void validateHmmInput(@NonNull final I_S_TimeBooking record)
	{
		final String hoursAndMinutes = record.getHoursAndMinutes();

		if (Check.isBlank(hoursAndMinutes))
		{
			return;
		}

		if (!HmmUtils.matches(hoursAndMinutes))
		{

			throw new AdempiereException(msgBL.getTranslatableMsgText(AdMessageKey.of(INCORRECT_FORMAT_MSG_KEY)))
					.markAsUserValidationError();
		}
	}

	private void updateIssueEfforts(@NonNull final Effort effortToAdd, final IssueId issueId)
	{
		issueEffortService.addIssueEffort(issueId, effortToAdd);

		issueEffortService.addAggregatedEffort(issueId, effortToAdd);
	}
}
