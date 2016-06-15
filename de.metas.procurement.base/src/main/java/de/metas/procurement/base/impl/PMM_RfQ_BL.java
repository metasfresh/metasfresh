package de.metas.procurement.base.impl;

import org.adempiere.util.Services;

import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PMM_RfQ_BL implements IPMM_RfQ_BL
{
	@Override
	public boolean isClosed(final I_C_RfQResponse rfqResponse)
	{
		return Services.get(IRfqBL.class).isClosed(rfqResponse);
	}
}
