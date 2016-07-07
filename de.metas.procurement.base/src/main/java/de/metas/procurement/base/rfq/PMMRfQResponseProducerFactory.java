package de.metas.procurement.base.rfq;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import com.google.common.base.Supplier;

import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.procurement.base.rfq.model.I_C_RfQLine;
import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.IRfQResponseProducer;
import de.metas.rfq.IRfQResponseProducerFactory;
import de.metas.rfq.impl.DefaultRfQResponseProducer;
import de.metas.rfq.model.I_C_RfQ;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Generates {@link I_C_RfQResponse}s from procurement {@link I_C_RfQ}s.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public final class PMMRfQResponseProducerFactory implements IRfQResponseProducerFactory
{
	public static final transient PMMRfQResponseProducerFactory instance = new PMMRfQResponseProducerFactory();

	private PMMRfQResponseProducerFactory()
	{
		super();
	}

	@Override
	public IRfQResponseProducer newRfQResponsesProducerFor(final I_C_RfQ rfq)
	{
		if (!Services.get(IPMM_RfQ_BL.class).isProcurement(rfq))
		{
			return null;
		}

		return new PMMRfQResponseProducer();
	}

	private static class PMMRfQResponseProducer extends DefaultRfQResponseProducer
	{
		@Override
		protected I_C_RfQResponseLine createRfqResponseLine(final Supplier<I_C_RfQResponse> responseSupplier, final de.metas.rfq.model.I_C_RfQLine rfqLine)
		{
			final de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine = super.createRfqResponseLine(responseSupplier, rfqLine);
			if (rfqResponseLine == null)
			{
				return null;
			}
			final I_C_RfQResponseLine pmmRfqResponseLine = InterfaceWrapperHelper.create(rfqResponseLine, I_C_RfQResponseLine.class);

			//
			// From RfQ Line
			final I_C_RfQLine pmmRfqLine = InterfaceWrapperHelper.create(rfqLine, I_C_RfQLine.class);
			pmmRfqResponseLine.setPMM_Product(pmmRfqLine.getPMM_Product());

			return pmmRfqResponseLine;
		}
	}

}
