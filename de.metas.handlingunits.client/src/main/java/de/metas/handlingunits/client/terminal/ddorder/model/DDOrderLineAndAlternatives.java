package de.metas.handlingunits.client.terminal.ddorder.model;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_DD_OrderLine_Alternative;

import de.metas.handlingunits.model.I_DD_OrderLine;

/**
 * Optimized "carrier" object which contains both the {@link I_DD_OrderLine} and it's {@link I_DD_OrderLine_Alternative}s; used in aggregation
 *
 * @author al
 */
public final class DDOrderLineAndAlternatives
{
	private final I_DD_OrderLine ddOrderLine;
	private final List<I_DD_OrderLine_Alternative> alternatives;

	public DDOrderLineAndAlternatives(final org.eevolution.model.I_DD_OrderLine ddOrderLine, final List<I_DD_OrderLine_Alternative> alternatives)
	{
		super();

		this.ddOrderLine = InterfaceWrapperHelper.create(ddOrderLine, I_DD_OrderLine.class);
		this.alternatives = alternatives;
	}

	public I_DD_OrderLine getDD_OrderLine()
	{
		return ddOrderLine;
	}

	public List<I_DD_OrderLine_Alternative> getDD_OrderLine_Alternatives()
	{
		return alternatives;
	}
}
