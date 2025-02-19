package de.metas.material.event.ddorder;

import de.metas.document.engine.DocStatus;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_DD_Order;

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
@Builder
@Jacksonized
public class DDOrderDocStatusChangedEvent implements MaterialEvent
{
	public static final String TYPE = "DDOrderDocStatusChangedEvent";

	@NonNull EventDescriptor eventDescriptor;
	int ddOrderId;
	@NonNull DocStatus newDocStatus;

	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.of(I_DD_Order.Table_Name, ddOrderId);
	}

	@Override
	public String getEventName() {return TYPE;}

}
