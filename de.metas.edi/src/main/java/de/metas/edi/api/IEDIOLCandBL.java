package de.metas.edi.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_C_OLCand;

public interface IEDIOLCandBL extends ISingletonService
{
	/**
	 * Decides for the given olCand whether the HU item capacity from our own master data (<code>false</code>) shall be date, or the one that was imported into the given <code>olCand</code> (
	 * <code>true</code>).
	 *
	 * @param olCand
	 * @return <code>true</code> as long as we have a capacity in our master data
	 */
	boolean isManualQtyItemCapacity(I_C_OLCand olCand);

	/**
	 * @param olCand
	 * @return true if the input data source is EDI
	 */
	boolean isEDIInput(de.metas.ordercandidate.model.I_C_OLCand olCand);
}
