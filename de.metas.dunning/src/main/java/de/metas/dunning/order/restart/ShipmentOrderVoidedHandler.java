package de.metas.dunning.order.restart;

import org.springframework.stereotype.Component;

import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.order.restart.VoidOrderWithRelatedDocsHandler;
import de.metas.order.restart.VoidOrderWithRelatedDocsRequest;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class ShipmentOrderVoidedHandler implements VoidOrderWithRelatedDocsHandler
{
	@Override
	public void handleOrderVoided(@NonNull final VoidOrderWithRelatedDocsRequest orderWillBeVoidedRequest)
	{
		// we don't know what to do here
	}

	@Override
	public RecordsToHandleKey getRecordsToHandleKey()
	{
		return RecordsToHandleKey.of(I_C_DunningDoc.Table_Name);
	}
}
