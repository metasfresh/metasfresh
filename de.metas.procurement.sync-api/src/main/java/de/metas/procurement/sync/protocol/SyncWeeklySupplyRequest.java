package de.metas.procurement.sync.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

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

@XmlRootElement(name = "SyncWeekSupplyRequest")
public class SyncWeeklySupplyRequest
{
	private List<SyncWeeklySupply> weeklySupplies = new ArrayList<>();

	@Override
	public String toString()
	{
		return "SyncWeekSupplyRequest [weeklySupplies=" + weeklySupplies + "]";
	}

	public List<SyncWeeklySupply> getWeeklySupplies()
	{
		return weeklySupplies;
	}

	public void setWeeklySupply(List<SyncWeeklySupply> weeklySupplies)
	{
		this.weeklySupplies = weeklySupplies;
	}
}
