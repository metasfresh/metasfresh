package de.metas.handlingunits.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.api.IDDOrderMovementBuilder;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategy;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.handlingunits.inventory.draftlinescreator.LeastRecentTransactionStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.request.service.async.spi.impl.C_Request_CreateFromDDOrder_Async;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Interceptor(I_DD_Order.class)
public class DD_Order
{

	private final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REVERSEACCRUAL, ModelValidator.TIMING_BEFORE_REVERSECORRECT, ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_CLOSE })
	public void clearHUsScheduledToMoveList(final I_DD_Order ddOrder)
	{
		Services.get(IHUDDOrderDAO.class).clearHUsScheduledToMoveList(ddOrder);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void DD_Order_Quarantine_Request(final I_DD_Order ddOrder)
	{
		
		final List<Integer> ddOrderLineToQuarantineIds = retrieveLineToQuarantineWarehouseIds(ddOrder);
		
		C_Request_CreateFromDDOrder_Async.createWorkpackage(ddOrderLineToQuarantineIds);

	}

	
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void DD_Order_createMovementsIfNeeded(final I_DD_Order ddOrder) {

		final List<I_DD_OrderLine> ddOrderLines = ddOrderDAO.retrieveLines(ddOrder);

		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(ddOrder.getAD_Org_ID()));
		final LocalDate movementDate = TimeUtil.asLocalDate(ddOrder.getPickDate(), timeZone);
		
		 final HUsForInventoryStrategy strategy = LeastRecentTransactionStrategy.builder()
		 .movementDate(movementDate)
		 .build();

			final Iterator<HuForInventoryLine> huLines = strategy.streamHus().iterator();

			final List<I_M_HU> hus = new ArrayList<I_M_HU>();

			while (huLines.hasNext()) {
				final HuForInventoryLine hu = huLines.next();
				final HuId huID = hu.getHuId();

				hus.add(handlingUnitsDAO.getById(huID));
			}
		 
		 processDDOrderLines(ddOrderLines, hus);
		 
	}
	

	public void processDDOrderLines(final Collection<I_DD_OrderLine> ddOrderLines, final List<I_M_HU> hus)
	{
		final IHUDDOrderBL huDDOrderBL = Services.get(IHUDDOrderBL.class);
		huDDOrderBL.createMovements()
				.setDDOrderLines(ddOrderLines)
				.allocateHUs(hus)
				.processWithinOwnTrx();
	}

	
	private List<Integer> retrieveLineToQuarantineWarehouseIds(final I_DD_Order ddOrder)
	{
		return ddOrderDAO.retrieveLines(ddOrder)
				.stream()
				.filter(ddOrderLine -> isQuarantineWarehouseLine(ddOrderLine))
				.map(I_DD_OrderLine::getDD_OrderLine_ID)
				.collect(ImmutableList.toImmutableList())
				;
	}

	private boolean isQuarantineWarehouseLine(final I_DD_OrderLine ddOrderLine)
	{
		final I_M_Warehouse warehouse = create(ddOrderLine.getM_LocatorTo().getM_Warehouse(), I_M_Warehouse.class);

		return warehouse.isQuarantineWarehouse();

	}

}
