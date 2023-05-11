/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.material.cockpit.stock.HUStockService;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import org.compiere.SpringContextHolder;

public class MD_Stock_Update_From_M_HUs_StepDef
{
	private final IADPInstanceDAO instanceDAO = Services.get(IADPInstanceDAO.class);
	private final HUStockService huStockService = SpringContextHolder.instance.getBean(HUStockService.class);

	@And("MD_Stock_Update_From_M_HUs process is called")
	public void call_MD_Stock_Update_From_M_HUs_process()
	{
		final PInstanceId pInstanceId = instanceDAO.createSelectionId();

		huStockService.createAndHandleDataUpdateRequests(pInstanceId);
	}
}
