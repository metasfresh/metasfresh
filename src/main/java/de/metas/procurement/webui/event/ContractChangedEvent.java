package de.metas.procurement.webui.event;

import com.google.gwt.thirdparty.guava.common.base.Objects;

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

public class ContractChangedEvent implements IApplicationEvent
{
	public static final ContractChangedEvent of(final String bpartner_uuid, final long contract_id)
	{
		return new ContractChangedEvent(bpartner_uuid, contract_id);
	}

	private final String bpartner_uuid;
	private final long contract_id;

	private ContractChangedEvent(final String bpartner_uuid, final long contract_id)
	{
		super();
		this.bpartner_uuid = bpartner_uuid;
		this.contract_id = contract_id;
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this)
				.add("bpartner_uuid", bpartner_uuid)
				.add("contract_id", contract_id)
				.toString();
	}

	@Override
	public String getBpartner_uuid()
	{
		return bpartner_uuid;
	}

	public long getRfq_id()
	{
		return contract_id;
	}
}
