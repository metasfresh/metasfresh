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

import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueService;
import de.metas.serviceprovider.issue.interceptor.AddIssueProgressRequest;
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
	private final IssueService issueService;

	public S_TimeBooking(final ExternalReferenceRepository externalReferenceRepository, final IMsgBL msgBL, final IssueService issueService)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.msgBL = msgBL;
		this.issueService = issueService;
	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_S_TimeBooking record)
	{
		externalReferenceRepository.deleteByRecordIdAndType(record.getS_TimeBooking_ID(), ExternalServiceReferenceType.TIME_BOOKING_ID);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void recomputeIssueProgressAfterDelete(@NonNull final I_S_TimeBooking record)
	{
		final IssueId issueId = IssueId.ofRepoId(record.getS_Issue_ID());
		final Effort removedEffort = Effort.ofSeconds(record.getBookedSeconds().longValue());

		final AddIssueProgressRequest addIssueProgressRequest = AddIssueProgressRequest
				.builder()
				.issueId(issueId)
				.bookedEffort(removedEffort.negate())
				.build();

		issueService.addIssueProgress(addIssueProgressRequest);
	}

	@ModelChange(timings = {ModelValidator.TYPE_AFTER_CHANGE}, ifColumnsChanged = {I_S_TimeBooking.COLUMNNAME_S_Issue_ID})
	public void recomputeIssueProgress(@NonNull final I_S_TimeBooking record)
	{
		final I_S_TimeBooking oldRecord = InterfaceWrapperHelper.createOld(record, I_S_TimeBooking.class);

		if (record.getS_Issue_ID() != oldRecord.getS_Issue_ID())
		{
			final Effort bookedEffort = Effort.ofSeconds(record.getBookedSeconds().longValue());

			//1. add the progress for the new issue
			final AddIssueProgressRequest addIssueProgressRequest = AddIssueProgressRequest
					.builder()
					.issueId(IssueId.ofRepoId(record.getS_Issue_ID()))
					.bookedEffort(bookedEffort)
					.build();

			issueService.addIssueProgress(addIssueProgressRequest);

			//2. recompute the progress for the old one
			final AddIssueProgressRequest recomputeProgressReq = AddIssueProgressRequest
					.builder()
					.issueId(IssueId.ofRepoId(oldRecord.getS_Issue_ID()))
					.bookedEffort(bookedEffort.negate())
					.build();

			issueService.addIssueProgress(recomputeProgressReq);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_S_TimeBooking.COLUMNNAME_AD_Org_ID)
	public void updateLinkedExternalReferencesOrgId(@NonNull final I_S_TimeBooking record)
	{
			externalReferenceRepository.updateOrgIdByRecordIdAndType(record.getS_TimeBooking_ID(), ExternalServiceReferenceType.TIME_BOOKING_ID, OrgId.ofRepoId(record.getAD_Org_ID()));
	}

	@ModelChange(timings = {ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE},
			     ifColumnsChanged = {I_S_TimeBooking.COLUMNNAME_BookedDate,I_S_TimeBooking.COLUMNNAME_HoursAndMinutes})
	public void addIssueProgress(@NonNull final I_S_TimeBooking record)
	{
		record.setBookedSeconds(BigDecimal.valueOf(HmmUtils.hmmToSeconds(record.getHoursAndMinutes())));

		final I_S_TimeBooking oldRecord = InterfaceWrapperHelper.createOld(record, I_S_TimeBooking.class);

		final long deltaBookedSeconds = record.getBookedSeconds().subtract(oldRecord.getBookedSeconds()).longValue();

		final AddIssueProgressRequest addIssueProgressRequest = AddIssueProgressRequest
				.builder()
				.issueId(IssueId.ofRepoId(record.getS_Issue_ID()))
				.bookedEffort(Effort.ofSeconds(deltaBookedSeconds))
				.build();

		issueService.addIssueProgress(addIssueProgressRequest);
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
}
