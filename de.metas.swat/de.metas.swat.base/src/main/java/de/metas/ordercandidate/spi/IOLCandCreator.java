package de.metas.ordercandidate.spi;

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


import org.compiere.model.PO;

import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.model.I_C_OLCand;

/**
 * Implementation of this class is responsible with creating the {@link I_C_OLCand} from a given source.
 * 
 * NOTE: please don't call the methods of this class directly. If you want to explicitly create an {@link I_C_OLCand} with a particular creator then use
 * {@link IOLCandBL#invokeOLCandCreator(PO, IOLCandCreator)}.
 * 
 * @author tsa
 * 
 */
public interface IOLCandCreator
{
	String getSourceTable();

	I_C_OLCand createFrom(PO sourcePo);
}
