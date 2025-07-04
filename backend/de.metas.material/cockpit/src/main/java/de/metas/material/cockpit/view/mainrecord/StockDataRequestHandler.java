/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.cockpit.view.mainrecord;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import static de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler.retrieveOrCreateDataRecord;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Service
public class StockDataRequestHandler
{
	public void handleStockUpdateRequest(@NonNull final UpdateStockDataRequest updateStockDataRequest)
	{
		synchronized (StockDataRequestHandler.class)
		{
			final I_MD_Cockpit dataRecord = retrieveOrCreateDataRecord(updateStockDataRequest.getIdentifier());

			dataRecord.setMDCandidateQtyStock_AtDate(updateStockDataRequest.getQtyStockCurrent());
			saveRecord(dataRecord);
		}
	}

}
