/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

@Value
public class MobileUIPickingUserProfile
{
	public static final MobileUIPickingUserProfile DEFAULT = builder()
			.name("default")
			.availablePickingFilters(ImmutableList.of(
					PickingFilter.of(PickingJobFilterOption.CUSTOMER, 10),
					PickingFilter.of(PickingJobFilterOption.DELIVERY_DATE, 20)))
			.pickingJobConfigs(ImmutableList.of(
					PickingJobUIConfig.builder()
							.seqNo(10)
							.field(PickingJobField.DOCUMENT_NO)
							.isShowInDetailed(true)
							.isShowInSummary(true)
							.build(),
					PickingJobUIConfig.builder()
							.seqNo(20)
							.field(PickingJobField.CUSTOMER)
							.isShowInDetailed(true)
							.isShowInSummary(true)
							.build()))
			.build();

	@NonNull String name;
	@NonNull ImmutableSet<BPartnerId> onlyBPartnerIds;
	boolean isAllowPickingAnyHU;
	@NonNull CreateShipmentPolicy createShipmentPolicy;
	@NonNull ImmutableList<PickingFilter> availablePickingFilters;
	@Getter(AccessLevel.PACKAGE) @NonNull ImmutableList<PickingJobUIConfig> pickingJobConfigs;

	@NonNull ImmutableList<PickingJobField> launcherFieldsInOrder;
	@NonNull ImmutableList<PickingJobField> detailFieldsInOrder;

	@Builder(toBuilder = true)
	private MobileUIPickingUserProfile(
			@NonNull String name,
			@Nullable ImmutableSet<BPartnerId> onlyBPartnerIds,
			boolean isAllowPickingAnyHU,
			@Nullable CreateShipmentPolicy createShipmentPolicy,
			@Nullable ImmutableList<PickingFilter> availablePickingFilters,
			@Nullable ImmutableList<PickingJobUIConfig> pickingJobConfigs)
	{
		this.name = name;
		this.onlyBPartnerIds = onlyBPartnerIds != null ? onlyBPartnerIds : ImmutableSet.of();
		this.isAllowPickingAnyHU = isAllowPickingAnyHU;
		this.createShipmentPolicy = createShipmentPolicy != null ? createShipmentPolicy : CreateShipmentPolicy.DO_NOT_CREATE;
		this.availablePickingFilters = availablePickingFilters != null ? availablePickingFilters : ImmutableList.of();
		this.pickingJobConfigs = pickingJobConfigs != null ? pickingJobConfigs : ImmutableList.of();

		this.launcherFieldsInOrder = this.pickingJobConfigs.stream()
				.filter(PickingJobUIConfig::isShowInSummary)
				.sorted(Comparator.comparing(PickingJobUIConfig::getSeqNo))
				.map(PickingJobUIConfig::getField)
				.collect(ImmutableList.toImmutableList());

		this.detailFieldsInOrder = this.pickingJobConfigs.stream()
				.filter(PickingJobUIConfig::isShowInDetailed)
				.sorted(Comparator.comparing(PickingJobUIConfig::getSeqNo))
				.map(PickingJobUIConfig::getField)
				.collect(ImmutableList.toImmutableList());

	}

	public boolean isFilterByCustomerEnabled()
	{
		return hasFilterOption(PickingJobFilterOption.CUSTOMER);
	}

	public boolean isFilterByHandoverAddressEnabled()
	{
		return hasFilterOption(PickingJobFilterOption.HANDOVER_LOCATION);
	}

	public boolean isFilterByDeliveryDateEnabled()
	{
		return hasFilterOption(PickingJobFilterOption.DELIVERY_DATE);
	}

	public boolean isAnyFilterEnabled()
	{
		return !availablePickingFilters.isEmpty();
	}

	public boolean hasFilterOption(@NonNull final PickingJobFilterOption option)
	{
		return getFilterByOptionCode(option.getCode()).isPresent();
	}

	@NonNull
	public Comparator<String> getFilterDisplayOrderComparator()
	{
		return (filterOption1, filterOption2) -> {
			final int option1SeqNo = getFilterByOptionCode(filterOption1).map(PickingFilter::getSeqNo).orElse(-1);
			final int option2SeqNo = getFilterByOptionCode(filterOption2).map(PickingFilter::getSeqNo).orElse(-1);

			return Integer.compare(option1SeqNo, option2SeqNo);
		};
	}

	@NonNull
	private Optional<PickingFilter> getFilterByOptionCode(@NonNull final String optionCode)
	{
		return availablePickingFilters.stream()
				.filter(pickingFilter -> pickingFilter.getOption().getCode().equals(optionCode))
				.findFirst();
	}

	public boolean isLauncherField(@NonNull final PickingJobField field)
	{
		return launcherFieldsInOrder.contains(field);
	}
}
