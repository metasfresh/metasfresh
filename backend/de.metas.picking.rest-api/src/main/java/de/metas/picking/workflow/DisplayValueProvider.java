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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.PickingJobField;
import de.metas.handlingunits.picking.config.mobileui.PickingJobFieldType;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceList;
import de.metas.handlingunits.picking.job.model.RenderedAddressProvider;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.AddressDisplaySequence;
import de.metas.organization.IOrgDAO;
import de.metas.quantity.Quantity;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Location;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class DisplayValueProvider
{
	@NonNull private final IBPartnerDAO partnerDAO;
	@NonNull private final IOrgDAO orgDAO;
	@NonNull private final RenderedAddressProvider renderedAddressProvider;

	@NonNull private final MobileUIPickingUserProfile profile;

	@NonNull private final HashMap<BPartnerLocationId, Optional<String>> ruestplatzCache = new HashMap<>();

	@Builder
	private DisplayValueProvider(
			@NonNull final IBPartnerDAO partnerDAO,
			@NonNull final IOrgDAO orgDAO,
			@NonNull final IDocumentLocationBL documentLocationBL,
			//
			@NonNull final MobileUIPickingUserProfile profile)
	{
		this.partnerDAO = partnerDAO;
		this.orgDAO = orgDAO;
		this.renderedAddressProvider = RenderedAddressProvider.newInstance(documentLocationBL);

		this.profile = profile;
	}

	public void cacheWarmUpForPickingJobReferences(final PickingJobReferenceList pickingJobReferences)
	{
		if (pickingJobReferences.isEmpty())
		{
			return;
		}

		final ImmutableList<Context> contexts = pickingJobReferences.stream().map(DisplayValueProvider::toContext).collect(ImmutableList.toImmutableList());
		cacheWarmUpForContexts(contexts);
	}

	public void cacheWarmUpForPickingJobCandidates(final ImmutableList<PickingJobCandidate> pickingJobCandidates)
	{
		if (pickingJobCandidates.isEmpty())
		{
			return;
		}

		final ImmutableList<Context> contexts = pickingJobCandidates.stream().map(this::toContext).collect(ImmutableList.toImmutableList());
		cacheWarmUpForContexts(contexts);
	}

	private void cacheWarmUpForContexts(final ImmutableList<Context> contexts)
	{
		if (contexts.isEmpty())
		{
			return;
		}

		if (profile.isLauncherField(PickingJobFieldType.RUESTPLATZ_NR))
		{
			loadRuestplatz(contexts);
		}
	}

	private void loadRuestplatz(@NonNull final ImmutableList<Context> contexts)
	{
		final ImmutableSet<BPartnerLocationId> deliveryLocationIds = extractDeliveryLocationIds(contexts);

		final ImmutableMap<BPartnerLocationId, I_C_BPartner_Location> locationsById = Maps.uniqueIndex(
				partnerDAO.retrieveBPartnerLocationsByIds(deliveryLocationIds),
				location -> BPartnerLocationId.ofRepoId(location.getC_BPartner_ID(), location.getC_BPartner_Location_ID())
		);

		deliveryLocationIds.forEach(locationId -> ruestplatzCache.put(locationId, extractRuestplatz(locationsById.get(locationId))));
	}

	private static ImmutableSet<BPartnerLocationId> extractDeliveryLocationIds(final @NonNull ImmutableList<Context> contexts)
	{
		return contexts.stream().map(Context::getDeliveryLocationId).filter(Objects::isNull).collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public WorkflowLauncherCaption computeLauncherCaption(@NonNull final PickingJob pickingJob)
	{
		return computeLauncherCaption(toContext(pickingJob));
	}

	@NonNull
	public WorkflowLauncherCaption computeLauncherCaption(@NonNull final PickingJobCandidate pickingJobCandidate)
	{
		return computeLauncherCaption(toContext(pickingJobCandidate));
	}

	@NonNull
	public WorkflowLauncherCaption computeLauncherCaption(@NonNull final PickingJobReference pickingJobReference)
	{
		return computeLauncherCaption(toContext(pickingJobReference));
	}

	@NonNull
	private WorkflowLauncherCaption computeLauncherCaption(@NonNull final Context context)
	{
		final ImmutableList.Builder<String> fieldsInOrder = ImmutableList.builder();
		@NonNull ImmutableMap.Builder<String, ITranslatableString> fieldValues = ImmutableMap.builder();
		@NonNull ImmutableMap.Builder<String, Comparable<?>> comparableKeys = ImmutableMap.builder();

		for (final PickingJobField field : profile.getLauncherFieldsInOrder())
		{
			final ITranslatableString value = getDisplayValue(field, context);
			final Comparable<?> comparableKey = getComparableKey(field, context);
			final String fieldType = field.getField().getCode();

			fieldsInOrder.add(fieldType);
			fieldValues.put(fieldType, value);
			if (comparableKey != null)
			{
				comparableKeys.put(fieldType, comparableKey);
			}
		}

		return WorkflowLauncherCaption.builder()
				.fieldsInOrder(fieldsInOrder.build())
				.fieldValues(fieldValues.build())
				.comparingKeys(comparableKeys.build())
				.build();
	}

	@NonNull
	private Context toContext(@NonNull final PickingJobCandidate pickingJobCandidate)
	{
		return Context.builder()
				.deliveryLocationId(pickingJobCandidate.getDeliveryBPLocationId())
				.salesOrderDocumentNo(pickingJobCandidate.getSalesOrderDocumentNo())
				.customerName(pickingJobCandidate.getCustomerName())
				.preparationDate(pickingJobCandidate.getPreparationDate() != null ? pickingJobCandidate.getPreparationDate().toZonedDateTime(orgDAO::getTimeZone) : null)
				.handoverLocationId(pickingJobCandidate.getHandoverLocationId())
				.productName(pickingJobCandidate.getProductName())
				.qtyToDeliver(pickingJobCandidate.getQtyToDeliver())
				.build();
	}

	@NonNull
	private static Context toContext(@NonNull final PickingJobReference pickingJobReference)
	{
		return Context.builder()
				.deliveryLocationId(pickingJobReference.getDeliveryBPLocationId())
				.salesOrderDocumentNo(pickingJobReference.getSalesOrderDocumentNo())
				.customerName(pickingJobReference.getCustomerName())
				.preparationDate(pickingJobReference.getPreparationDate())
				.handoverLocationId(pickingJobReference.getHandoverLocationId())
				.productName(pickingJobReference.getProductName())
				.qtyToDeliver(pickingJobReference.getQtyToDeliver())
				.build();
	}

	@NonNull
	private static Context toContext(@NonNull final PickingJob pickingJob)
	{
		return Context.builder()
				.deliveryLocationId(pickingJob.getDeliveryBPLocationId())
				.salesOrderDocumentNo(pickingJob.getSalesOrderDocumentNo())
				.customerName(pickingJob.getCustomerName())
				.preparationDate(pickingJob.getPreparationDate())
				.handoverLocationId(pickingJob.getHandoverLocationId())
				.productName(pickingJob.getSingleProductNameOrEmpty())
				.qtyToDeliver(pickingJob.getSingleQtyToPickOrNull())
				.build();
	}

	@NonNull
	public ITranslatableString getDisplayValue(@NonNull final PickingJobField field, @NonNull final PickingJob pickingJob)
	{
		return getDisplayValue(field, toContext(pickingJob));
	}

	@NonNull
	private ITranslatableString getDisplayValue(@NonNull final PickingJobField field, @NonNull final Context context)
	{
		switch (field.getField())
		{
			case CUSTOMER:
			{
				return TranslatableStrings.anyLanguage(context.getCustomerName());
			}
			case DATE_READY:
			{
				return context.getPreparationDate() != null
						? TranslatableStrings.dateAndTime(context.getPreparationDate())
						: TranslatableStrings.empty();
			}
			case DOCUMENT_NO:
			{
				return TranslatableStrings.anyLanguage(context.getSalesOrderDocumentNo());
			}
			case RUESTPLATZ_NR:
			{
				return TranslatableStrings.anyLanguage(getRuestplatz(context).orElse(null));
			}
			case DELIVERY_ADDRESS:
			{
				final AddressDisplaySequence displaySequence = getAddressDisplaySequence(field);
				return TranslatableStrings.anyLanguage(getDeliveryAddress(context, displaySequence));
			}
			case HANDOVER_LOCATION:
			{
				final AddressDisplaySequence displaySequence = getAddressDisplaySequence(field);
				return TranslatableStrings.anyLanguage(getHandoverAddress(context, displaySequence));
			}
			case PRODUCT:
			{
				final ITranslatableString productName = context.getProductName();
				return productName != null ? productName : TranslatableStrings.empty();
			}
			case QTY_TO_DELIVER:
			{
				final Quantity qtyToDeliver = context.getQtyToDeliver();
				return qtyToDeliver != null
						? TranslatableStrings.quantity(qtyToDeliver.toBigDecimal(), qtyToDeliver.getUOMSymbol())
						: TranslatableStrings.empty();
			}
			default:
			{
				throw new AdempiereException("Unknown field: " + field.getField());
			}
		}
	}

	@Nullable
	private Comparable<?> getComparableKey(@NonNull final PickingJobField field, @NonNull final Context pickingJob)
	{
		//noinspection SwitchStatementWithTooFewBranches
		switch (field.getField())
		{
			case RUESTPLATZ_NR:
			{
				return getRuestplatz(pickingJob)
						.map(value -> NumberUtils.asInteger(value, null)) // we assume Ruestplantz is number so we want to sort it as numbers
						.orElse(null);
			}
			default:
			{
				return null;
			}
		}
	}

	@Nullable
	private static AddressDisplaySequence getAddressDisplaySequence(final PickingJobField field)
	{
		final String pattern = StringUtils.trimBlankToNull(field.getPattern());
		if (pattern == null)
		{
			return null;
		}

		return AddressDisplaySequence.ofNullable(pattern);
	}

	private Optional<String> getRuestplatz(@NonNull final Context pickingJob)
	{
		final BPartnerLocationId deliveryLocationId = pickingJob.getDeliveryLocationId();
		if (deliveryLocationId == null)
		{
			return Optional.empty();
		}

		return ruestplatzCache.computeIfAbsent(deliveryLocationId, this::retrieveRuestplatz);
	}

	private Optional<String> retrieveRuestplatz(final BPartnerLocationId deliveryLocationId)
	{
		return extractRuestplatz(partnerDAO.getBPartnerLocationByIdEvenInactive(deliveryLocationId));
	}

	private static Optional<String> extractRuestplatz(@Nullable final I_C_BPartner_Location location)
	{
		return Optional.ofNullable(location)
				.map(I_C_BPartner_Location::getSetup_Place_No)
				.map(StringUtils::trimBlankToNull);
	}

	@Nullable
	private String getHandoverAddress(@NonNull final Context pickingJob, @Nullable AddressDisplaySequence displaySequence)
	{
		final BPartnerLocationId bpLocationId = pickingJob.getHandoverLocationIdWithFallback();
		return bpLocationId != null
				? renderedAddressProvider.getAddress(bpLocationId, displaySequence)
				: null;
	}

	@Nullable
	private String getDeliveryAddress(@NonNull final Context context, @Nullable AddressDisplaySequence displaySequence)
	{
		final BPartnerLocationId bpLocationId = context.getDeliveryLocationId();
		return bpLocationId != null
				? renderedAddressProvider.getAddress(bpLocationId, displaySequence)
				: null;
	}

	@Value
	@Builder
	private static class Context
	{
		@Nullable String salesOrderDocumentNo;
		@Nullable String customerName;
		@Nullable ZonedDateTime preparationDate;
		@Nullable BPartnerLocationId deliveryLocationId;
		@Nullable BPartnerLocationId handoverLocationId;
		@Nullable ITranslatableString productName;
		@Nullable Quantity qtyToDeliver;

		@Nullable
		public BPartnerLocationId getHandoverLocationIdWithFallback()
		{
			return handoverLocationId != null ? handoverLocationId : deliveryLocationId;
		}
	}
}
