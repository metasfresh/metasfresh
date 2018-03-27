package de.metas.inoutcandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;

import de.metas.inoutcandidate.spi.impl.IQtyAndQuality;

/**
 * Contains business logic that goes with both shipment schedules and receipt schedules.
 * 
 * @author ts
 * 
 */
public interface IInOutCandidateBL extends ISingletonService
{
	/**
	 * @param storeReceipts if <code>true</code>, then the result will contain the actual receipts that were created. This might cause memory problems. If false, the result will just contain the
	 *            number of generated receipts.
	 * @return generated result
	 */
	InOutGenerateResult createEmptyInOutGenerateResult(boolean storeReceipts);
	
	IQtyAndQuality getQtyAndQuality(final org.compiere.model.I_M_InOutLine inoutLine);

}
