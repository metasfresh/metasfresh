package de.metas.fresh.material.interceptor;

import de.metas.fresh.freshQtyOnHand.api.IFreshQtyOnHandDAO;
import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

@Interceptor(I_Fresh_QtyOnHand.class)
@Component
public class Fresh_QtyOnHand
{
	private final IFreshQtyOnHandDAO freshQtyOnHandDAO = Services.get(IFreshQtyOnHandDAO.class);

	private final ModelProductDescriptorExtractor productDescriptorFactory;
	private final PostMaterialEventService materialEventService;

	public Fresh_QtyOnHand(
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
			@NonNull final PostMaterialEventService materialEventService)
	{
		this.productDescriptorFactory = productDescriptorFactory;
		this.materialEventService = materialEventService;
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
		final boolean createDeletedEvent = timing.isDelete() || ModelChangeUtil.isJustDeactivatedOrUnProcessed(qtyOnHand);

		final List<AbstractStockEstimateEvent> events = new ArrayList<>();

		final List<I_Fresh_QtyOnHand_Line> lines = freshQtyOnHandDAO.retrieveLines(qtyOnHand);
		for (final I_Fresh_QtyOnHand_Line line : lines)
		{
			final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(line);

			final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
					.productDescriptor(productDescriptor)
					.warehouseId(WarehouseId.ofRepoId(line.getM_Warehouse_ID()))
					.quantity(line.getQtyCount())
					.date(TimeUtil.asInstant(line.getDateDoc()))
					.build();

			final AbstractStockEstimateEvent event;

			if (createDeletedEvent)
			{
				event = Fresh_QtyOnHand_Line.buildDeletedEvent(line, materialDescriptor);
			}
			else
			{
				event = Fresh_QtyOnHand_Line.buildCreatedEvent(line, materialDescriptor);
			}
			events.add(event);
		}

		events.forEach(materialEventService::postEventAfterNextCommit);
	}

}
