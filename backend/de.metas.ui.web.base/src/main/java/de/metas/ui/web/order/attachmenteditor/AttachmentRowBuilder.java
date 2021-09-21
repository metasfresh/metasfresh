/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.order.attachmenteditor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.attachments.AttachmentEntry;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

@Value
@Builder
public class AttachmentRowBuilder
{
	@NonNull
	Map<String, PriorityRowBuilder> tableName2PriorityRowBuilder;

	@NonNull
	OrderId purchaseOrderId;

	@NonNull
	Function<AttachmentEntry, OrderAttachmentRow> purchaseOrderRowBuilder;

	@NonNull
	Set<TableRecordReference> targetRecordReferences;

	public List<OrderAttachmentRow> buildRowsFor(@NonNull final AttachmentEntry attachmentEntry)
	{
		final TableRecordReference purchaseOrderRecordRef = TableRecordReference.of(I_C_Order.Table_Name, purchaseOrderId.getRepoId());

		final Set<TableRecordReference> matchingRecordReferences = attachmentEntry.getLinkedRecords()
				.stream()
				.filter(tableRecordReference -> !tableRecordReference.equals(purchaseOrderRecordRef))
				.filter(targetRecordReferences::contains)
				.collect(ImmutableSet.toImmutableSet());

		final Priority highestPriorityFound = matchingRecordReferences
				.stream()
				.map(TableRecordReference::getTableName)
				.map(tableName2PriorityRowBuilder::get)
				.filter(Objects::nonNull)
				.map(PriorityRowBuilder::getPriority)
				.max(Priority::compareTo)
				.orElse(Priority.NO_PRIORITY);

		final ImmutableList.Builder<OrderAttachmentRow> attachmentRows = ImmutableList.builder();

		if (highestPriorityFound.equals(Priority.NO_PRIORITY))
		{
			return attachmentRows
					.add(purchaseOrderRowBuilder.apply(attachmentEntry))
					.build();
		}

		for (final TableRecordReference linkedRecord : matchingRecordReferences)
		{
			Optional.ofNullable(tableName2PriorityRowBuilder.get(linkedRecord.getTableName()))
					.filter(priorityRowBuilder -> priorityRowBuilder.getPriority().equals(highestPriorityFound))
					.map(PriorityRowBuilder::getBuildRowFunction)
					.map(buildFunction -> buildFunction.apply(linkedRecord, attachmentEntry))
					.ifPresent(attachmentRows::add);
		}

		return attachmentRows.build();
	}

	@Value
	public static class Priority implements Comparable<Priority>
	{
		public static final Priority NO_PRIORITY = Priority.of(-1);

		public static Priority of(final int priority)
		{
			return new Priority(priority);
		}

		int priority;

		@Override
		public int compareTo(@NonNull final Priority o)
		{
			return Integer.compare(this.priority, o.priority);
		}
	}

	@Value
	@Getter
	public static class PriorityRowBuilder
	{
		Priority priority;
		BiFunction<TableRecordReference, AttachmentEntry, OrderAttachmentRow> buildRowFunction;

		public static PriorityRowBuilder of(final Priority priority, @NonNull final BiFunction<TableRecordReference, AttachmentEntry, OrderAttachmentRow> buildRowFunction)
		{
			return new PriorityRowBuilder(priority, buildRowFunction);
		}
	}
}
