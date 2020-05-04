package de.metas.rfq.event.impl;

import de.metas.rfq.event.IRfQEventDispacher;
import de.metas.rfq.event.IRfQEventListener;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.rfq
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

public class RfQEventDispacher implements IRfQEventDispacher
{
	private final CompositeRfQEventListener listeners = new CompositeRfQEventListener();

	@Override
	public void registerListener(final IRfQEventListener listener)
	{
		listeners.addListener(listener);
	}

	@Override
	public void fireBeforeComplete(final I_C_RfQ rfq)
	{
		listeners.onBeforeComplete(rfq);
	}

	@Override
	public void fireAfterComplete(final I_C_RfQ rfq)
	{
		listeners.onAfterComplete(rfq);
	}

	@Override
	public void fireBeforeClose(final I_C_RfQ rfq)
	{
		listeners.onBeforeClose(rfq);
	}

	@Override
	public void fireAfterClose(final I_C_RfQ rfq)
	{
		listeners.onAfterClose(rfq);
	}

	@Override
	public void fireDraftCreated(final I_C_RfQResponse rfqResponse)
	{
		listeners.onDraftCreated(rfqResponse);
	}

	@Override
	public void fireBeforeComplete(final I_C_RfQResponse rfqResponse)
	{
		listeners.onBeforeComplete(rfqResponse);
	}

	@Override
	public void fireAfterComplete(final I_C_RfQResponse rfqResponse)
	{
		listeners.onAfterComplete(rfqResponse);
	}

	@Override
	public void fireBeforeClose(final I_C_RfQResponse rfqResponse)
	{
		listeners.onBeforeClose(rfqResponse);
	}

	@Override
	public void fireAfterClose(final I_C_RfQResponse rfqResponse)
	{
		listeners.onAfterClose(rfqResponse);
	}

	@Override
	public void fireBeforeUnClose(I_C_RfQ rfq)
	{
		listeners.onBeforeUnClose(rfq);
	}

	@Override
	public void fireAfterUnClose(I_C_RfQ rfq)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireBeforeUnClose(I_C_RfQResponse rfqResponse)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireAfterUnClose(I_C_RfQResponse rfqResponse)
	{
		// TODO Auto-generated method stub
		
	}
}
