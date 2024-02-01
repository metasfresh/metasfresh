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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Comparator;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
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
	@NonNull @Builder.Default ImmutableSet<BPartnerId> onlyBPartnerIds = ImmutableSet.of();
	boolean isAllowPickingAnyHU;
	@NonNull @Builder.Default CreateShipmentPolicy createShipmentPolicy = CreateShipmentPolicy.DO_NOT_CREATE;
	@NonNull @Builder.Default ImmutableList<PickingFilter> availablePickingFilters = ImmutableList.of();
	@NonNull @Builder.Default ImmutableList<PickingJobUIConfig> pickingJobConfigs = ImmutableList.of();

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
}
