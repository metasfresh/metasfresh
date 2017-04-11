package de.metas.manufacturing.dispo.event;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.adempiere.model.I_M_Product;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.logging.LogManager;
/*
 * #%L
 * metasfresh-manufacturing-dispo
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
import de.metas.manufacturing.dispo.Candidate;
import de.metas.manufacturing.dispo.Candidate.SupplyType;
import de.metas.manufacturing.dispo.Candidate.Type;
import de.metas.manufacturing.dispo.CandidateChangeHandler;
import de.metas.manufacturing.event.ManufactoringEventBus;
import de.metas.quantity.Quantity;

public class ReceiptScheduleEventListener implements IEventListener
{
	private static final Logger logger = LogManager.getLogger(ReceiptScheduleEventListener.class);
	
	@Autowired
	private CandidateChangeHandler candidateChangeHandler;

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final BigDecimal qtyOrdered = event.getProperty("QtyOrdered");
		logger.info("qtyOrdered={}", qtyOrdered);

		final Date datePromised = event.getProperty("DatePromised");
		logger.info("datePromised={}", datePromised);

		if (qtyOrdered == null && datePromised == null)
		{
			return; // nothing to be done, this event doesn't interest us
		}
		
		// create or update a supply record
		final int timing = event.getProperty(ManufactoringEventBus.MODEL_INTERCEPTOR_TIMING);
		if (timing == ModelValidator.TYPE_AFTER_NEW)
		{
			// qtyOrdered and preparationDate are not null

			final int productId = event.getProperty(I_M_Product.COLUMNNAME_M_Product_ID);
			final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);

			final int warehouseId = event.getProperty(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID);
			final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(Env.getCtx(), warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);

			// the event shall contain everything we need
			final Candidate candidate = Candidate.builder()
					.referencedRecord(event.getRecord())
					.type(Type.SUPPLY)
					.supplyType(SupplyType.RECEIPT)
					.product(product)
					.warehouse(warehouse)
					.quantity(new Quantity(qtyOrdered, product.getC_UOM()))
					.date(datePromised)
					.build();
			candidateChangeHandler.onSupplyCandidateNew(candidate);
		}
		else if (timing == ModelValidator.TYPE_AFTER_CHANGE)
		{
			// use the event's reference to load the candidate we need to change
			// use that candidate's product's UOM
			candidateChangeHandler.onSupplyCandidateChange(event.getRecord(), qtyOrdered, datePromised);
		}
		else if (timing == ModelValidator.TYPE_AFTER_DELETE)
		{
			candidateChangeHandler.onSupplyCandidateDelete(event.getRecord());
		}
	}

}
