package de.metas.procurement.sync.protocol;

import java.util.HashMap;
import java.util.Map;

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

public class SyncProduct extends AbstractSyncModel
{
	private String name;
	private String packingInfo;
	private boolean shared = false;

	private Map<String, String> namesTrl = new HashMap<>();

	@Override
	public String toString()
	{
		return "SyncProduct [name=" + name + ", packingInfo=" + packingInfo + ", shared=" + shared + ", namesTrl=" + namesTrl + "]";
	}
	
	public SyncProduct copy()
	{
		final SyncProduct to = new SyncProduct();
		copyTo(to);
		to.setName(getName());
		to.setPackingInfo(getPackingInfo());
		to.setShared(isShared());
		to.setNamesTrl(new HashMap<>(getNamesTrl()));
		return to;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPackingInfo()
	{
		return packingInfo;
	}

	public void setPackingInfo(String packingInfo)
	{
		this.packingInfo = packingInfo;
	}

	public boolean isShared()
	{
		return shared;
	}

	public void setShared(boolean shared)
	{
		this.shared = shared;
	}

	/**
	 * @param namesTrl adLanguage to translated product name map
	 */
	public void setNamesTrl(Map<String, String> namesTrl)
	{
		this.namesTrl = namesTrl;
	}

	/**
	 * @return namesTrl adLanguage to translated product name map
	 */
	public Map<String, String> getNamesTrl()
	{
		return namesTrl;
	}
}
