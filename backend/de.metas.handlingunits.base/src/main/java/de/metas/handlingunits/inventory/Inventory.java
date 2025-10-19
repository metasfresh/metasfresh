package de.metas.handlingunits.inventory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.reducers.Reducers;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Inventory
{
	@NonNull InventoryId id;
	@NonNull OrgId orgId;
	@NonNull DocBaseAndSubType docBaseAndSubType;
	@NonNull InventoryType inventoryType;
	@NonNull ZonedDateTime movementDate;
	@Nullable WarehouseId warehouseId;
	@Nullable String description;
	@Nullable ActivityId activityId;
	@NonNull DocStatus docStatus;
	@NonNull String documentNo;
	@Nullable UserId responsibleId;

	@NonNull ImmutableList<InventoryLine> lines;

	@Builder(toBuilder = true)
	private Inventory(
			@NonNull final InventoryId id,
			@NonNull final OrgId orgId,
			@NonNull final DocBaseAndSubType docBaseAndSubType,
			@NonNull final ZonedDateTime movementDate,
			@Nullable final WarehouseId warehouseId,
			@Nullable final String description,
			@Nullable final ActivityId activityId,
			@NonNull final DocStatus docStatus,
			@NonNull final String documentNo,
			@Nullable final UserId responsibleId,
			@NonNull final List<InventoryLine> lines)
	{
		this.id = id;
		this.orgId = orgId;
		this.docBaseAndSubType = docBaseAndSubType;
		this.inventoryType = extractInventoryType(lines).orElse(InventoryType.PHYSICAL);
		this.movementDate = movementDate;
		this.warehouseId = warehouseId;
		this.description = description;
		this.activityId = activityId;
		this.docStatus = docStatus;
		this.documentNo = documentNo;
		this.responsibleId = responsibleId;

		this.lines = ImmutableList.copyOf(lines);
	}

	private static Optional<InventoryType> extractInventoryType(
			@NonNull final List<InventoryLine> lines)
	{
		return lines.stream()
				.map(InventoryLine::getInventoryType)
				.reduce(Reducers.singleValue(values -> new AdempiereException("Mixing Physical inventories with Internal Use inventories is not allowed: " + lines)));
	}

	public boolean isInternalUseInventory()
	{
		return inventoryType.isInternalUse();
	}

	public InventoryLine getLineById(@NonNull final InventoryLineId inventoryLineId)
	{
		return lines.stream()
				.filter(line -> inventoryLineId.equals(line.getId()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for " + inventoryLineId + " in " + this));
	}

	public ImmutableList<InventoryLineHU> getLineHUs()
	{
		return streamLineHUs().collect(ImmutableList.toImmutableList());
	}

	private Stream<InventoryLineHU> streamLineHUs()
	{
		return lines.stream().flatMap(line -> line.getInventoryLineHUs().stream());
	}

	public ImmutableSet<HuId> getHuIds()
	{
		return InventoryLineHU.extractHuIds(streamLineHUs());
	}

	public Inventory assigningTo(@NonNull final UserId newResponsibleId)
	{
		return assigningTo(newResponsibleId, false);
	}

	public Inventory reassigningTo(@NonNull final UserId newResponsibleId)
	{
		return assigningTo(newResponsibleId, true);
	}

	private Inventory assigningTo(@NonNull final UserId newResponsibleId, boolean allowReassignment)
	{
		// no responsible change
		if (UserId.equals(responsibleId, newResponsibleId))
		{
			return this;
		}

		if (!newResponsibleId.isRegularUser())
		{
			throw new AdempiereException("Only regular users can be assigned to an inventory");
		}

		if (!allowReassignment && responsibleId != null)
		{
			throw new AdempiereException("Inventory is already assigned");
		}

		return toBuilder().responsibleId(newResponsibleId).build();
	}

	public Inventory unassign()
	{
		return responsibleId == null ? this : toBuilder().responsibleId(null).build();
	}

	public void assertHasAccess(@NonNull final UserId calledId)
	{
		if (!UserId.equals(responsibleId, calledId))
		{
			throw new AdempiereException("No access");
		}
	}

	public Stream<InventoryLine> streamLines(@Nullable final InventoryLineId onlyLineId)
	{
		return onlyLineId != null
				? Stream.of(getLineById(onlyLineId))
				: lines.stream();
	}

	public Set<LocatorId> getLocatorIdsEligibleForCounting(@Nullable final InventoryLineId onlyLineId)
	{
		return streamLines(onlyLineId)
				.filter(InventoryLine::isEligibleForCounting)
				.map(InventoryLine::getLocatorId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Inventory updatingLineById(@NonNull final InventoryLineId lineId, @NonNull UnaryOperator<InventoryLine> updater)
	{
		final ImmutableList<InventoryLine> newLines = CollectionUtils.map(
				this.lines,
				line -> InventoryLineId.equals(line.getId(), lineId) ? updater.apply(line) : line
		);

		return this.lines == newLines
				? this
				: toBuilder().lines(newLines).build();
	}

}
