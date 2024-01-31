/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.picking.workflow;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.RenderedAddressProvider;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.IOrgDAO;
import de.metas.picking.config.MobileUIPickingUserProfile;
import de.metas.picking.config.PickingJobField;
import de.metas.picking.config.PickingJobUIConfig;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisplayValueProvider
{
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final IDocumentLocationBL documentLocationBL;

	@NonNull
	public ITranslatableString computeSummaryCaption(
			@NonNull final MobileUIPickingUserProfile profile,
			@NonNull final PickingJobUIDescriptor pickingJob)
	{
		return computeSummaryCaption(profile, pickingJob, newAddressProvider());
	}

	@NonNull
	public RenderedAddressProvider newAddressProvider()
	{
		return RenderedAddressProvider.of(documentLocationBL);
	}

	@NonNull
	public ITranslatableString computeSummaryCaption(
			@NonNull final MobileUIPickingUserProfile profile,
			@NonNull final PickingJobUIDescriptor pickingJob,
			@NonNull final RenderedAddressProvider addressProvider)
	{
		final String workflowCaption = profile.getPickingJobConfigs()
				.stream()
				.filter(PickingJobUIConfig::isShowInSummary)
				.sorted(Comparator.comparing(PickingJobUIConfig::getSeqNo))
				.map(PickingJobUIConfig::getField)
				.map(uiFiled -> getDisplayValue(uiFiled, pickingJob, addressProvider))
				.map(caption -> caption.translate(Env.getAD_Language()))
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" | "));

		return TranslatableStrings.anyLanguage(workflowCaption);
	}

	@NonNull
	public PickingJobUIDescriptor toUIDescriptor(@NonNull final PickingJobCandidate pickingJob)
	{
		return PickingJobUIDescriptor.builder()
				.deliveryLocationId(pickingJob.getDeliveryBPLocationId())
				.salesOrderDocumentNo(pickingJob.getSalesOrderDocumentNo())
				.customerName(pickingJob.getCustomerName())
				.deliveryDate(pickingJob.getPreparationDate().toZonedDateTime(orgDAO::getTimeZone))
				.handoverLocationId(pickingJob.getHandoverLocationId())
				.build();
	}

	@NonNull
	public static PickingJobUIDescriptor toUIDescriptor(@NonNull final PickingJobReference pickingJobReference)
	{
		return PickingJobUIDescriptor.builder()
				.deliveryLocationId(pickingJobReference.getDeliveryLocationId())
				.salesOrderDocumentNo(pickingJobReference.getSalesOrderDocumentNo())
				.customerName(pickingJobReference.getCustomerName())
				.deliveryDate(pickingJobReference.getDeliveryDate())
				.handoverLocationId(pickingJobReference.getHandoverLocationId())
				.build();
	}

	@NonNull
	public static PickingJobUIDescriptor toUIDescriptor(@NonNull final PickingJob pickingJob)
	{
		return PickingJobUIDescriptor.builder()
				.deliveryLocationId(pickingJob.getDeliveryBPLocationId())
				.salesOrderDocumentNo(pickingJob.getSalesOrderDocumentNo())
				.customerName(pickingJob.getCustomerName())
				.deliveryDate(pickingJob.getDeliveryDate())
				.handoverLocationId(pickingJob.getHandoverLocationId())
				.build();
	}

	@NonNull
	public ITranslatableString getDisplayValue(
			@NonNull final PickingJobField uiField,
			@NonNull final PickingJobUIDescriptor pickingJob,
			@NonNull final RenderedAddressProvider addressProvider)
	{
		switch (uiField)
		{
			case CUSTOMER:
				return TranslatableStrings.anyLanguage(pickingJob.getCustomerName());
			case DATE_READY:
				return TranslatableStrings.dateAndTime(pickingJob.getDeliveryDate());
			case DOCUMENT_NO:
				return TranslatableStrings.anyLanguage(pickingJob.getSalesOrderDocumentNo());
			case RUESTPLATZ_NR:
				return TranslatableStrings.anyLanguage(getRuestplatz(pickingJob));
			case DELIVERY_ADDRESS:
				return TranslatableStrings.anyLanguage(getDeliveryAddress(pickingJob, addressProvider));
			case HANDOVER_LOCATION:
				return TranslatableStrings.anyLanguage(getHandoverAddress(pickingJob, addressProvider));
			default:
				throw new AdempiereException("Unknown filed=" + uiField);
		}
	}

	@Nullable
	private String getRuestplatz(@NonNull final PickingJobUIDescriptor pickingJob)
	{
		return partnerDAO.getBPartnerLocationByIdEvenInactive(pickingJob.getDeliveryLocationId()).getSetup_Place_No();
	}

	@NonNull
	private String getHandoverAddress(
			@NonNull final PickingJobUIDescriptor pickingJob,
			@NonNull final RenderedAddressProvider addressProvider)
	{
		return addressProvider.getAddress(pickingJob.getHandoverLocationIdWithFallback());
	}

	@NonNull
	private String getDeliveryAddress(
			@NonNull final PickingJobUIDescriptor pickingJob,
			@NonNull final RenderedAddressProvider addressProvider)
	{
		return addressProvider.getAddress(pickingJob.getDeliveryLocationId());
	}

	@Value
	@Builder
	public static class PickingJobUIDescriptor
	{
		@NonNull String salesOrderDocumentNo;
		@NonNull String customerName;
		@NonNull ZonedDateTime deliveryDate;
		@NonNull BPartnerLocationId deliveryLocationId;
		@Nullable BPartnerLocationId handoverLocationId;

		@NonNull
		public BPartnerLocationId getHandoverLocationIdWithFallback()
		{
			return handoverLocationId != null ? handoverLocationId : deliveryLocationId;
		}
	}
}
