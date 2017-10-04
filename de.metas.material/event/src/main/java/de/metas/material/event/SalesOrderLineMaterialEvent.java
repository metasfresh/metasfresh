package de.metas.material.event;

import com.google.common.base.Preconditions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
public class SalesOrderLineMaterialEvent implements MaterialEvent
{
	public static final String TYPE = "SalesOrderLineMaterialEvent";

	@NonNull
	MaterialDemandDescr materialDemandDescr;

	@Builder // used by devs to make sure they know with parameter value does into which property
	public SalesOrderLineMaterialEvent(MaterialDemandDescr materialDemandDescr)
	{
		Preconditions.checkArgument(materialDemandDescr.getOrderLineId() > 0,
				"For this kind of event, the given materialDemandDescr needs to have OrderLineId>0, but it has OrderLineId=%s; materialDemandDescr=%s",
				materialDemandDescr.getOrderLineId(), materialDemandDescr);
		
		this.materialDemandDescr = materialDemandDescr;
	}

	@Override
	public EventDescr getEventDescr()
	{
		return materialDemandDescr.getEventDescr();
	}

	public int getOrderLineId()
	{
		return materialDemandDescr.getOrderLineId();
	}
}
