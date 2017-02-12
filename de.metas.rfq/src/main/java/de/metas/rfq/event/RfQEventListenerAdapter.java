package de.metas.rfq.event;

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

public class RfQEventListenerAdapter implements IRfQEventListener
{
	protected RfQEventListenerAdapter()
	{
		super();
	}

	@Override
	public void onAfterComplete(final I_C_RfQ rfq)
	{
		// nothing
	}

	@Override
	public void onAfterClose(final I_C_RfQ rfq)
	{
		// nothing
	}

	@Override
	public void onBeforeComplete(final I_C_RfQ rfq)
	{
		// nothing
	}

	@Override
	public void onBeforeClose(final I_C_RfQ rfq)
	{
		// nothing
	}

	@Override
	public void onDraftCreated(final I_C_RfQResponse rfqResponse)
	{
		// nothing
	}

	@Override
	public void onBeforeComplete(final I_C_RfQResponse rfqResponse)
	{
		// nothing
	}

	@Override
	public void onAfterComplete(final I_C_RfQResponse rfqResponse)
	{
		// nothing
	}

	@Override
	public void onBeforeClose(final I_C_RfQResponse rfqResponse)
	{
		// nothing
	}

	@Override
	public void onAfterClose(final I_C_RfQResponse rfqResponse)
	{
		// nothing
	}

	@Override
	public void onBeforeUnClose(final I_C_RfQ rfq)
	{
		// nothing
	}

	@Override
	public void onAfterUnClose(final I_C_RfQ rfq)
	{
		// nothing
	}

	@Override
	public void onBeforeUnClose(final I_C_RfQResponse rfqResponse)
	{
		// nothing
	}

	@Override
	public void onAfterUnClose(final I_C_RfQResponse rfqResponse)
	{
		// nothing
	}
}
