package de.metas.procurement.sync;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.metas.procurement.sync.protocol.SyncProductSupply;

/*
 * #%L
 * de.metas.procurement.sync-api
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
 * Event fired by metasfresh server to indicate that a given RfQ was closed.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@XmlRootElement(name = "SyncRfQCloseEvent")
public class SyncRfQCloseEvent
{
	private String rfq_uuid;
	private boolean winnerKnown;
	private boolean winner;

	private List<SyncProductSupply> plannedSupplies = new ArrayList<>();

	@Override
	public String toString()
	{
		return "SyncRfQCloseEvent ["
				+ "rfq_uuid=" + rfq_uuid
				+ ", winnerKnown=" + winnerKnown
				+ ", winner=" + winner
				+ ", plannedSupplies=" + plannedSupplies
				+ "]";
	}

	public String getRfq_uuid()
	{
		return rfq_uuid;
	}

	public void setRfq_uuid(String rfq_uuid)
	{
		this.rfq_uuid = rfq_uuid;
	}
	
	public void setWinnerKnown(boolean winnerKnown)
	{
		this.winnerKnown = winnerKnown;
	}
	
	public boolean isWinnerKnown()
	{
		return winnerKnown;
	}

	public boolean isWinner()
	{
		return winner;
	}

	public void setWinner(boolean winner)
	{
		this.winner = winner;
	}

	public List<SyncProductSupply> getPlannedSupplies()
	{
		return plannedSupplies;
	}

	public void setPlannedSupplies(List<SyncProductSupply> plannedSupplies)
	{
		this.plannedSupplies = plannedSupplies;
	}

}
