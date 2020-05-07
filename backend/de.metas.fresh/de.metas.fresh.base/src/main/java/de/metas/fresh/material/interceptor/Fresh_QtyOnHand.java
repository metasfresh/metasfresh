package de.metas.fresh.material.interceptor;

import java.util.ArrayList;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;

import org.adempiere.ad.modelvalidator.InterceptorUtil;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;

import de.metas.fresh.freshQtyOnHand.api.IFreshQtyOnHandDAO;
import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import lombok.NonNull;

@Interceptor(I_Fresh_QtyOnHand.class)
public class Fresh_QtyOnHand
{
	public static final Fresh_QtyOnHand INSTANCE = new Fresh_QtyOnHand();

	private Fresh_QtyOnHand()
	{
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE
	}, ifColumnsChanged = {
			I_Fresh_QtyOnHand.COLUMNNAME_Processed,
			I_Fresh_QtyOnHand.COLUMNNAME_IsActive
	})
	public void createAndFireStockCountEvents(
			@NonNull final I_Fresh_QtyOnHand qtyOnHand,
			@NonNull final ModelChangeType timing)
	{
		final boolean createDeletedEvent = timing.isDelete() || InterceptorUtil.isJustDeactivatedOrUnProcessed(qtyOnHand);

		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);

		List<AbstractStockEstimateEvent> events = new ArrayList<>();

		final List<I_Fresh_QtyOnHand_Line> lines = Services.get(IFreshQtyOnHandDAO.class).retrieveLines(qtyOnHand);
		for (final I_Fresh_QtyOnHand_Line line : lines)
		{
			final AbstractStockEstimateEvent event;

			final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(line);
			if (createDeletedEvent)
			{
				event = createDeletedEvent(line, productDescriptor);
			}
			else
			{
				event = createCreatedEvent( line, productDescriptor);
			}
			events.add(event);
		}

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		events.forEach(event -> materialEventService.postEventAfterNextCommit(event));
	}

	private AbstractStockEstimateEvent createCreatedEvent(
			@NonNull final I_Fresh_QtyOnHand_Line line,
			@NonNull final ProductDescriptor productDescriptor)
	{
		final I_Fresh_QtyOnHand qtyOnHand = line.getFresh_QtyOnHand();

		final AbstractStockEstimateEvent
		event = StockEstimateCreatedEvent.builder()
				.date(qtyOnHand.getDateDoc())
				.eventDescriptor(EventDescriptor.createNew(line))
				.plantId(line.getPP_Plant_ID())
				.productDescriptor(productDescriptor)
				.quantity(line.getQtyCount())
				.build();
		return event;
	}

	private AbstractStockEstimateEvent createDeletedEvent(
			@NonNull final I_Fresh_QtyOnHand_Line line,
			@NonNull final ProductDescriptor productDescriptor)
	{
		final I_Fresh_QtyOnHand qtyOnHand = line.getFresh_QtyOnHand();

		final AbstractStockEstimateEvent event = StockEstimateDeletedEvent.builder()
				.date(qtyOnHand.getDateDoc())
				.eventDescriptor(EventDescriptor.createNew(line))
				.plantId(line.getPP_Plant_ID())
				.productDescriptor(productDescriptor)
				.quantity(line.getQtyCount())
				.build();
		return event;
	}
}
