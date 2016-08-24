package de.metas.procurement.sync.protocol;

import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.fresh.procurement.webui
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

public class SyncBPartner extends AbstractSyncModel
{
	private String name;
	private List<SyncUser> users = new ArrayList<>();

	private boolean syncContracts = false;
	private List<SyncContract> contracts = new ArrayList<>();
	
	private List<SyncRfQ> rfqs = new ArrayList<>();

	@Override
	public String toString()
	{
		return "SyncBPartner [name=" + name
				+ ", users=" + users
				+ ", syncContracts=" + syncContracts
				+ ", contracts=" + contracts
				+", rfqs="+rfqs
				+ "]";
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<SyncUser> getUsers()
	{
		return users;
	}

	/**
	 * WARNING: don't use it directly
	 *
	 * @param users
	 */
	public void setUsers(List<SyncUser> users)
	{
		this.users = users;
	}

	public void setSyncContracts(boolean syncContracts)
	{
		this.syncContracts = syncContracts;
	}

	public boolean isSyncContracts()
	{
		return syncContracts;
	}

	public List<SyncContract> getContracts()
	{
		return contracts;
	}

	/**
	 * WARNING: don't use it directly
	 *
	 * @param contracts
	 */
	public void setContracts(List<SyncContract> contracts)
	{
		this.contracts = contracts;
	}

	public List<SyncRfQ> getRfqs()
	{
		return rfqs;
	}

	public void setRfqs(List<SyncRfQ> rfqs)
	{
		this.rfqs = rfqs;
	}
}
