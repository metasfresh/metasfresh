package de.metas.inout.api;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.warehouse.LocatorId;
import org.apache.ecs.xhtml.code;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.interfaces.I_M_Movement;
import de.metas.util.ISingletonService;

public interface IInOutMovementBL extends ISingletonService
{
	/**
	 * Reverse all movements which are linked to given shipment/receipt.
	 *
	 * This is the counter-part of {@link #generateMovementFromReceiptLines(List, I_M_Locator)}.
	 */
	void reverseMovements(I_M_InOut inout);

	/**
	 * /**
	 * Generate material movement(s) from {@link I_M_InOut#getM_Warehouse()} either the given {@code locatorToId} (if not null)
	 * or to the default locators of the {@code receiptLines}s' {@link I_M_ReceiptSchedule}.
	 * <p>
	 * Receipt lines whose destination warehouse would be same as the receipt's warehouse and lines without any destination warehouse will be skipped.
	 *
	 * @param locatorToId may be {@link code null}.
	 */
	List<I_M_Movement> generateMovementFromReceiptLines(List<I_M_InOutLine> receiptLines, LocatorId locatorToId);
}
