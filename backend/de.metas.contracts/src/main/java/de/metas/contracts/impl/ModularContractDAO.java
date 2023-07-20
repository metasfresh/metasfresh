/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IModularContractDAO;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;

import java.util.ArrayList;
import java.util.List;

public class ModularContractDAO implements IModularContractDAO
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);
	final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	public List<I_ModCntr_Module> getModulaContractsForOrderLine(
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final I_C_BPartner bPartner)
	{
		final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		final List<I_ModCntr_Settings> modCntrSettings =  queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Product_ID, orderLine.getM_Product_ID())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Year_ID, order.getHarvesting_Year_ID())
				.create()
				.list(I_ModCntr_Settings.class);

		final List<I_ModCntr_Module> modCntrModules = new ArrayList<>();
		modCntrSettings.forEach(modCntrSetting -> {
			if (!flatrateBL.getFlatrateTermsByModularContractSettings(
					ModularContractSettingsId.ofRepoId(modCntrSetting.getModCntr_Settings_ID()),
					BPartnerId.ofRepoId(bPartner.getC_BPartner_ID()))
					.isEmpty())
			{
				modCntrModules.addAll(getModularContractsBySettings(modCntrSetting));
			}
		});
		return modCntrModules;
	}

	private List<I_ModCntr_Module> getModularContractsBySettings(@NonNull final I_ModCntr_Settings modCntrSettings)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addEqualsFilter(I_ModCntr_Module.COLUMNNAME_ModCntr_Settings_ID, modCntrSettings.getModCntr_Settings_ID())
				.create()
				.list(I_ModCntr_Module.class);
	}
}
