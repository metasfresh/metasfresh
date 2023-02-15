package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.quarantine.HULotNumberQuarantineService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import org.compiere.SpringContextHolder;

import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_M_HU_MoveToAnotherWarehouse_ExclQuarantined extends WEBUI_M_HU_MoveToAnotherWarehouse_Template
{
	public static final AdMessageKey MSG_WEBUI_HUs_IN_Quarantine = AdMessageKey.of("WEBUI_HUs_IN_Quarantine");

	private final transient HULotNumberQuarantineService lotNumberQuarantineService = SpringContextHolder.instance.getBean(HULotNumberQuarantineService.class);

	@ProcessParamLookupValuesProvider(parameterName = I_M_Locator.COLUMNNAME_M_Warehouse_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	@Override
	public LookupValuesList getAvailableWarehouses(final LookupDataSourceContext evalCtx)
	{
		return super.getAvailableWarehouses(evalCtx);
	}

	@ProcessParamLookupValuesProvider(parameterName = I_M_Locator.COLUMNNAME_M_Locator_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	@Override
	protected LookupValuesList getAvailableLocators(final LookupDataSourceContext evalCtx)
	{
		return super.getAvailableLocators(evalCtx);
	}

	@Override
	public ProcessPreconditionsResolution checkHUsEligible(final List<I_M_HU> hus)
	{
		return isQuarantineHUs(hus)
				? ProcessPreconditionsResolution.reject(TranslatableStrings.adMessage(MSG_WEBUI_HUs_IN_Quarantine))
				: ProcessPreconditionsResolution.accept();
	}

	private boolean isQuarantineHUs(final List<I_M_HU> hus)
	{
		return hus.stream().anyMatch(lotNumberQuarantineService::isQuarantineHU);
	}
}
