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
import de.metas.material.event.pporder.PPOrderQtyEnteredChangedEvent;
import de.metas.material.event.pporder.PPOrderQtyEnteredChangedEvent.PPOrderLineChangeDescriptor;
import de.metas.material.event.pporder.PPOrderQtyEnteredChangedEvent.PPOrderLineChangeDescriptor.PPOrderLineChangeDescriptorBuilder;
import de.metas.material.event.pporder.PPOrderQtyEnteredChangedEvent.PPOrderQtyEnteredChangedEventBuilder;
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

	private final PPOrderQtyEnteredChangedEventBuilder eventBuilder;
	private final PPOrderPojoConverter ppOrderConverter;
	private final I_PP_Order ppOrderRecord;

	final Map<Integer, PPOrderLineChangeDescriptorBuilder> map = new HashMap<>();

	private PPOrderQtyEnteredChangeEventFactory(@NonNull final I_PP_Order ppOrderRecord)
	{
		this.eventBuilder = createAndInitEventBuilder(ppOrderRecord);
		this.ppOrderConverter = Adempiere.getBean(PPOrderPojoConverter.class);
		this.ppOrderRecord = ppOrderRecord;

		final PPOrder oldPPOrder = ppOrderConverter.asPPOrderPojo(ppOrderRecord);
		final Map<Boolean, List<PPOrderLine>> linesWithAndWithoutProductBomLineId = //
				collectLinesWithAndWithoutProductBomLineId(oldPPOrder);

		final List<PPOrderLine> linesWithoutProductBomLine = linesWithAndWithoutProductBomLineId.get(false);
		linesWithoutProductBomLine
				.forEach(line -> {

					eventBuilder.deletedPPOrderLineID(line.getPpOrderLineId());
				});

		final List<PPOrderLine> linesWithProductBomLine = linesWithAndWithoutProductBomLineId.get(true);
		linesWithProductBomLine
				.forEach(line -> {

					final PPOrderLineChangeDescriptorBuilder builder = PPOrderLineChangeDescriptor.builder()
							.oldPPOrderLineId(line.getPpOrderLineId())
							.oldQuantity(line.getQtyRequired());
					map.put(line.getProductBomLineId(), builder);
				});
	}

	private PPOrderQtyEnteredChangedEventBuilder createAndInitEventBuilder(@NonNull final I_PP_Order ppOrderRecord)
	{
		final I_PP_Order oldPPOrderRecord = createOld(ppOrderRecord, I_PP_Order.class);

		final PPOrderQtyEnteredChangedEventBuilder eventBuilder = PPOrderQtyEnteredChangedEvent
				.builder()
				.eventDescriptor(EventDescriptor.createNew(ppOrderRecord))
				.ppOrderId(ppOrderRecord.getPP_Order_ID())
				.oldQuantity(oldPPOrderRecord.getQtyEntered())
				.newQuantity(ppOrderRecord.getQtyEntered());
		return eventBuilder;
	}

	private Map<Boolean, List<PPOrderLine>> collectLinesWithAndWithoutProductBomLineId(@NonNull final PPOrder oldPPOrder)
	{
		final Map<Boolean, List<PPOrderLine>> collect = oldPPOrder
				.getLines().stream()
				.collect(Collectors.partitioningBy(line -> line.getProductBomLineId() > 0));
		return collect;
	}

	public PPOrderQtyEnteredChangedEvent inspectPPOrderAfterChange()
	{
		final PPOrder updatedPPOrder = ppOrderConverter.asPPOrderPojo(ppOrderRecord);

		for (final PPOrderLine updatedPPOrderLine : updatedPPOrder.getLines())
		{
			final int productBomLineId = updatedPPOrderLine.getProductBomLineId();
			if (!map.containsKey(productBomLineId))
			{
				eventBuilder.newPPOrderLine(updatedPPOrderLine);
			}
			else
			{
				map.get(productBomLineId)
						.newPPOrderLineId(updatedPPOrderLine.getPpOrderLineId())
						.newQuantity(updatedPPOrderLine.getQtyRequired());
			}
		}

		final Map<Boolean, List<PPOrderLineChangeDescriptor>> collect = collectMapEntriesWithAndWithoutNewPPorderLineId();

		final List<PPOrderLineChangeDescriptor> updatedPPOrderLines = collect.get(true);
		eventBuilder.ppOrderLineChanges(updatedPPOrderLines);

		final List<PPOrderLineChangeDescriptor> deletedPPOrderLines = collect.get(false);
		deletedPPOrderLines.forEach(descriptor -> eventBuilder.deletedPPOrderLineID(descriptor.getOldPPOrderLineId()));

		final PPOrderQtyEnteredChangedEvent event = eventBuilder.build();
		return event;
	}

	private Map<Boolean, List<PPOrderLineChangeDescriptor>> collectMapEntriesWithAndWithoutNewPPorderLineId()
	{
		final Map<Boolean, List<PPOrderLineChangeDescriptor>> collect = map.values().stream()
				.map(PPOrderLineChangeDescriptorBuilder::build)
				.collect(Collectors.partitioningBy(descriptor -> descriptor.getNewPPOrderLineId() > 0));
		return collect;
	}
}
