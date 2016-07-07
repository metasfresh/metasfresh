package de.metas.procurement.webui.event;

import com.google.gwt.thirdparty.guava.common.base.Objects;

import de.metas.procurement.webui.model.Rfq;

/*
 * #%L
 * metasfresh-procurement-webui
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

public class RfqChangedEvent implements IApplicationEvent
{
	public static final RfqChangedEvent of(final Rfq rfq)
	{
		return new RfqChangedEvent(rfq);
	}

	private final String bpartner_uuid;
	private final long rfq_id;
	private final String rfq_uuid;
	private final boolean closed;

	private RfqChangedEvent(final Rfq rfq)
	{
		super();
		this.bpartner_uuid = rfq.getBpartner().getUuid();
		this.rfq_id = rfq.getId();
		this.rfq_uuid = rfq.getUuid();
		this.closed = rfq.isClosed();
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this)
				.add("bpartner_uuid", bpartner_uuid)
				.add("rfq_id", rfq_id)
				.add("closed", closed)
				.toString();
	}

	@Override
	public String getBpartner_uuid()
	{
		return bpartner_uuid;
	}

	public long getRfq_id()
	{
		return rfq_id;
	}
	
	public String getRfq_uuid()
	{
		return rfq_uuid;
	}
	
	public boolean isClosed()
	{
		return closed;
	}
}
