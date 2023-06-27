package de.metas.material.cockpit.stock.process;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.HUStockService;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-material-cockpit
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

/**
 * Reset the {@link I_MD_Stock} table.
 * May be run in parallel to "normal" production stock changes.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class MD_Stock_Update_From_M_HUs extends JavaProcess
{
	private final HUStockService huStockService = SpringContextHolder.instance.getBean(HUStockService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		huStockService.createAndHandleDataUpdateRequests(getProcessInfo().getPinstanceId());
		addLog("Created and handled DataUpdateRequests for all MD_Stock_From_HUs_V records");

		return MSG_OK;
	}
}
