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
import de.metas.handlingunits.picking.job.model.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Comparator;

@Value
public class MobileUIPickingUserProfile
{
	public static final MobileUIPickingUserProfile DEFAULT = builder()
			.name("default")
			.filters(PickingFiltersList.ofList(ImmutableList.of(
					PickingFilter.of(PickingJobFacetGroup.CUSTOMER, 10),
					PickingFilter.of(PickingJobFacetGroup.DELIVERY_DATE, 20)))
			)
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
							.build())
			)
			.build();

	@NonNull String name;
	@NonNull ImmutableSet<BPartnerId> onlyBPartnerIds;
	boolean isAllowPickingAnyHU;
	@NonNull CreateShipmentPolicy createShipmentPolicy;
	@Getter(AccessLevel.NONE) @NonNull PickingFiltersList filters;
	@Getter(AccessLevel.PACKAGE) @NonNull ImmutableList<PickingJobUIConfig> pickingJobConfigs;

	@NonNull ImmutableList<PickingJobField> launcherFieldsInOrder;
	@NonNull ImmutableList<PickingJobField> detailFieldsInOrder;

	@Builder(toBuilder = true)
	private MobileUIPickingUserProfile(
			@NonNull String name,
			@Nullable ImmutableSet<BPartnerId> onlyBPartnerIds,
			boolean isAllowPickingAnyHU,
			@Nullable CreateShipmentPolicy createShipmentPolicy,
			@Nullable PickingFiltersList filters,
			@Nullable ImmutableList<PickingJobUIConfig> pickingJobConfigs)
	{
		this.name = name;
		this.onlyBPartnerIds = onlyBPartnerIds != null ? onlyBPartnerIds : ImmutableSet.of();
		this.isAllowPickingAnyHU = isAllowPickingAnyHU;
		this.createShipmentPolicy = createShipmentPolicy != null ? createShipmentPolicy : CreateShipmentPolicy.DO_NOT_CREATE;
		this.filters = filters != null ? filters : PickingFiltersList.EMPTY;
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

	public ImmutableList<PickingJobFacetGroup> getFilterGroupsInOrder() {return filters.getGroupsInOrder();}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isAnyFilterEnabled() {return !filters.isEmpty();}

	public boolean isLauncherField(@NonNull final PickingJobField field)
	{
		return launcherFieldsInOrder.contains(field);
	}
}
