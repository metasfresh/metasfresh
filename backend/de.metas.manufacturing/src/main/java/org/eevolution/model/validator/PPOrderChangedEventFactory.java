package org.eevolution.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.createOld;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import org.eevolution.model.I_PP_Order;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor.ChangedPPOrderLineDescriptorBuilder;
import de.metas.material.event.pporder.PPOrderChangedEvent.DeletedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderChangedEvent.PPOrderChangedEventBuilder;
import de.metas.material.event.pporder.PPOrderLine;
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

public class PPOrderChangedEventFactory
{
	public static PPOrderChangedEventFactory newWithPPOrderBeforeChange(
			@NonNull final PPOrderPojoConverter ppOrderConverter,
			@NonNull final I_PP_Order ppOrderRecord)
	{
		return new PPOrderChangedEventFactory(ppOrderConverter, ppOrderRecord);
	}

	private final PPOrderPojoConverter ppOrderConverter;
	private final I_PP_Order ppOrderRecord;

	private final PPOrderChangedEventBuilder eventBuilder;
	private final Map<Integer, ChangedPPOrderLineDescriptorBuilder> productBomLineId2ChangeBuilder = new HashMap<>();

	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private PPOrderChangedEventFactory(
			@NonNull final PPOrderPojoConverter ppOrderConverter,
			@NonNull final I_PP_Order ppOrderRecord)
	{
		this.ppOrderConverter = ppOrderConverter;
		this.ppOrderRecord = ppOrderRecord;

		this.eventBuilder = createAndInitEventBuilderWithOldValues(ppOrderRecord, ppOrderConverter);

		final PPOrder ppOrder = ppOrderConverter.toPPOrder(ppOrderRecord);
		final Map<Boolean, List<PPOrderLine>> linesWithAndWithoutProductBomLineId = collectLinesWithAndWithoutProductBomLineId(ppOrder);

		final List<PPOrderLine> linesWithoutProductBomLine = linesWithAndWithoutProductBomLineId.get(false);
		linesWithoutProductBomLine.forEach(line -> {
			eventBuilder.deletedPPOrderLine(DeletedPPOrderLineDescriptor.ofPPOrderLine(line));
		});

		final List<PPOrderLine> linesWithProductBomLine = linesWithAndWithoutProductBomLineId.get(true);
		linesWithProductBomLine.forEach(line -> {
			final ChangedPPOrderLineDescriptorBuilder builder = ChangedPPOrderLineDescriptor.builder()
					.issueOrReceiveDate(line.getIssueOrReceiveDate())
					.productDescriptor(line.getProductDescriptor())
					.oldPPOrderLineId(line.getPpOrderLineId())
					.oldQtyRequired(line.getQtyRequired())
					.oldQtyDelivered(line.getQtyDelivered());

			productBomLineId2ChangeBuilder.put(line.getProductBomLineId(), builder);
		});
	}

	private static PPOrderChangedEventBuilder createAndInitEventBuilderWithOldValues(
			@NonNull final I_PP_Order ppOrderRecord,
			@NonNull final PPOrderPojoConverter ppOrderConverter)
	{
		final I_PP_Order oldPPOrderRecord = createOld(ppOrderRecord, I_PP_Order.class);
		final PPOrder oldPPOrder = ppOrderConverter.toPPOrder(oldPPOrderRecord);

		return PPOrderChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ppOrderRecord.getAD_Client_ID(), ppOrderRecord.getAD_Org_ID()))
				.oldDatePromised(oldPPOrder.getDatePromised())
				.oldDocStatus(oldPPOrder.getDocStatus())
				.oldQtyRequired(oldPPOrder.getQtyRequired())
				.oldQtyDelivered(oldPPOrder.getQtyDelivered());
	}

	private Map<Boolean, List<PPOrderLine>> collectLinesWithAndWithoutProductBomLineId(@NonNull final PPOrder ppOrder)
	{
		return ppOrder.getLines()
				.stream()
				.collect(Collectors.partitioningBy(line -> line.getProductBomLineId() > 0));
	}

	public PPOrderChangedEvent inspectPPOrderAfterChange()
	{
		final PPOrder updatedPPOrder = ppOrderConverter.toPPOrder(ppOrderRecord);
		eventBuilder.ppOrderAfterChanges(updatedPPOrder);

		eventBuilder.newDatePromised(updatedPPOrder.getDatePromised());
		eventBuilder.newDocStatus(updatedPPOrder.getDocStatus());

		eventBuilder.newQtyRequired(updatedPPOrder.getQtyRequired());
		eventBuilder.newQtyDelivered(updatedPPOrder.getQtyDelivered());

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
						.newQtyRequired(updatedPPOrderLine.getQtyRequired())
						.newQtyDelivered(updatedPPOrderLine.getQtyDelivered());
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
					.qtyRequired(descriptor.getOldQtyRequired())
					.qtyDelivered(descriptor.getOldQtyDelivered())
					.build();
			eventBuilder.deletedPPOrderLine(deletedDescriptor);
		});

		final PPOrderChangedEvent event = eventBuilder.build();
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
