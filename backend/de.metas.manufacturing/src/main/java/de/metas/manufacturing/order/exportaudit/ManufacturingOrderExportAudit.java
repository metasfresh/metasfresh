package de.metas.manufacturing.order.exportaudit;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
@ToString
public class ManufacturingOrderExportAudit
{
	@Getter
	private final ExportTransactionId transactionId;

	private final HashMap<PPOrderId, ManufacturingOrderExportAuditItem> items;

	@Builder
	private ManufacturingOrderExportAudit(
			@NonNull final ExportTransactionId transactionId,
			@NonNull @Singular List<ManufacturingOrderExportAuditItem> items)
	{
		this.transactionId = transactionId;
		this.items = items.stream()
				.collect(GuavaCollectors.toHashMapByKeyFailOnDuplicates(ManufacturingOrderExportAuditItem::getOrderId));
	}

	public ImmutableList<ManufacturingOrderExportAuditItem> getItems()
	{
		return ImmutableList.copyOf(items.values());
	}

	public ImmutableMap<PPOrderId, APIExportStatus> getExportStatusesByOrderId()
	{
		return items.values()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						item -> item.getOrderId(),
						item -> item.getExportStatus()));
	}

	public ManufacturingOrderExportAuditItem computeItemIfAbsent(
			@NonNull final PPOrderId orderId,
			@NonNull final Function<PPOrderId, ManufacturingOrderExportAuditItem> mappingFunction)
	{
		return items.computeIfAbsent(orderId, mappingFunction);
	}

	@Nullable
	public ManufacturingOrderExportAuditItem getByOrderId(@NonNull final PPOrderId orderId)
	{
		return items.get(orderId);
	}
}
