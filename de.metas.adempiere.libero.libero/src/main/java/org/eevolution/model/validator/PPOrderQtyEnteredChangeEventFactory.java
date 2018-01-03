package org.eevolution.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.createOld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.compiere.Adempiere;
import org.eevolution.model.I_PP_Order;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderQtyChangedEvent;
import de.metas.material.event.pporder.PPOrderQtyChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderQtyChangedEvent.ChangedPPOrderLineDescriptor.ChangedPPOrderLineDescriptorBuilder;
import de.metas.material.event.pporder.PPOrderQtyChangedEvent.DeletedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderQtyChangedEvent.PPOrderQtyChangedEventBuilder;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PPOrderQtyEnteredChangeEventFactory
{
	public static PPOrderQtyEnteredChangeEventFactory newWithPPOrderBeforeChange(@NonNull final I_PP_Order ppOrderRecord)
	{
		return new PPOrderQtyEnteredChangeEventFactory(ppOrderRecord);
	}

	private final PPOrderQtyChangedEventBuilder eventBuilder;
	private final PPOrderPojoConverter ppOrderConverter;
	private final I_PP_Order ppOrderRecord;

	private final Map<Integer, ChangedPPOrderLineDescriptorBuilder> productBomLineId2ChangeBuilder = new HashMap<>();

	private PPOrderQtyEnteredChangeEventFactory(@NonNull final I_PP_Order ppOrderRecord)
	{
		this.ppOrderConverter = Adempiere.getBean(PPOrderPojoConverter.class);

		this.eventBuilder = createAndInitEventBuilderWithOldValues(ppOrderRecord);
		this.ppOrderRecord = ppOrderRecord;

		final PPOrder ppOrderPojo = ppOrderConverter.asPPOrderPojo(ppOrderRecord);
		this.eventBuilder.newQuantity(ppOrderPojo.getQuantity());

		final Map<Boolean, List<PPOrderLine>> linesWithAndWithoutProductBomLineId = //
				collectLinesWithAndWithoutProductBomLineId(ppOrderPojo);

		final List<PPOrderLine> linesWithoutProductBomLine = linesWithAndWithoutProductBomLineId.get(false);
		linesWithoutProductBomLine
				.forEach(line -> {
					eventBuilder.deletedPPOrderLine(DeletedPPOrderLineDescriptor.ofPPOrderLine(line));
				});

		final List<PPOrderLine> linesWithProductBomLine = linesWithAndWithoutProductBomLineId.get(true);
		linesWithProductBomLine
				.forEach(line -> {

					final ChangedPPOrderLineDescriptorBuilder builder = ChangedPPOrderLineDescriptor.builder()
							.issueOrReceiveDate(line.getIssueOrReceiveDate())
							.productDescriptor(line.getProductDescriptor())
							.oldPPOrderLineId(line.getPpOrderLineId())
							.oldQuantity(line.getQtyRequired());
					productBomLineId2ChangeBuilder.put(line.getProductBomLineId(), builder);
				});
	}

	private PPOrderQtyChangedEventBuilder createAndInitEventBuilderWithOldValues(@NonNull final I_PP_Order ppOrderRecord)
	{
		final I_PP_Order oldPPOrderRecord = createOld(ppOrderRecord, I_PP_Order.class);
		final PPOrder oldPPOrderPojo = this.ppOrderConverter.asPPOrderPojo(oldPPOrderRecord);

		final PPOrderQtyChangedEventBuilder eventBuilder = PPOrderQtyChangedEvent
				.builder()
				.eventDescriptor(EventDescriptor.createNew(ppOrderRecord))
				.datePromised(oldPPOrderPojo.getDatePromised())
				.productDescriptor(oldPPOrderPojo.getProductDescriptor())
				.ppOrderId(oldPPOrderPojo.getPpOrderId())
				.oldQuantity(oldPPOrderPojo.getQuantity());

		return eventBuilder;
	}

	private Map<Boolean, List<PPOrderLine>> collectLinesWithAndWithoutProductBomLineId(@NonNull final PPOrder oldPPOrder)
	{
		final Map<Boolean, List<PPOrderLine>> collect = oldPPOrder
				.getLines().stream()
				.collect(Collectors.partitioningBy(line -> line.getProductBomLineId() > 0));
		return collect;
	}

	public PPOrderQtyChangedEvent inspectPPOrderAfterChange()
	{
		final PPOrder updatedPPOrder = ppOrderConverter.asPPOrderPojo(ppOrderRecord);

		for (final PPOrderLine updatedPPOrderLine : updatedPPOrder.getLines())
		{
			final int productBomLineId = updatedPPOrderLine.getProductBomLineId();
			if (!productBomLineId2ChangeBuilder.containsKey(productBomLineId))
			{
				eventBuilder.newPPOrderLine(updatedPPOrderLine);
			}
			else
			{
				productBomLineId2ChangeBuilder.get(productBomLineId)
						.newPPOrderLineId(updatedPPOrderLine.getPpOrderLineId())
						.newQuantity(updatedPPOrderLine.getQtyRequired());
			}
		}

		final Map<Boolean, List<ChangedPPOrderLineDescriptor>> collection = collectMapEntriesWithAndWithoutNewPPorderLineId();

		final List<ChangedPPOrderLineDescriptor> updatedPPOrderLines = collection.get(true);
		eventBuilder.ppOrderLineChanges(updatedPPOrderLines);

		final List<ChangedPPOrderLineDescriptor> deletedPPOrderLines = collection.get(false);
		deletedPPOrderLines.forEach(descriptor -> {

			final DeletedPPOrderLineDescriptor deletedDescriptor = DeletedPPOrderLineDescriptor.builder()
					.issueOrReceiveDate(descriptor.getIssueOrReceiveDate())
					.ppOrderLineId(descriptor.getOldPPOrderLineId())
					.productDescriptor(descriptor.getProductDescriptor())
					.quantity(descriptor.getOldQuantity())
					.build();
			eventBuilder.deletedPPOrderLine(deletedDescriptor);
		});

		final PPOrderQtyChangedEvent event = eventBuilder.build();
		return event;
	}

	private Map<Boolean, List<ChangedPPOrderLineDescriptor>> collectMapEntriesWithAndWithoutNewPPorderLineId()
	{
		final Map<Boolean, List<ChangedPPOrderLineDescriptor>> collect = productBomLineId2ChangeBuilder.values().stream()
				.map(ChangedPPOrderLineDescriptorBuilder::build)
				.collect(Collectors.partitioningBy(descriptor -> descriptor.getNewPPOrderLineId() > 0));
		return collect;
	}
}
