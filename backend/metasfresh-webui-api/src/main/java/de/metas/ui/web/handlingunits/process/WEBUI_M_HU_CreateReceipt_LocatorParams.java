package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.api.impl.WarehouseDAO;
import org.adempiere.warehouse.model.I_M_Warehouse;
import org.compiere.Adempiere;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.quarantine.HULotNumberQuarantineService;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters.CreateReceiptsParametersBuilder;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_CreateReceipt_LocatorParams
		extends WEBUI_M_HU_CreateReceipt_Base
		implements IProcessPrecondition, IProcessParametersCallout, IProcessDefaultParametersProvider
{

	private final transient HULotNumberQuarantineService lotNumberQuarantineService = Adempiere.getBean(HULotNumberQuarantineService.class);

	private final transient IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final transient IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final static String WAREHOUSE_PARAM_NAME = I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_Dest_ID;
	@Param(mandatory = false, parameterName = WAREHOUSE_PARAM_NAME)
	private int warehouseRepoId;

	private final static String LOCATOR_PARAM_NAME = "M_Locator_Dest_ID";
	@Param(mandatory = false, parameterName = LOCATOR_PARAM_NAME)
	private int locatorRepoId;

	@ProcessParamLookupValuesProvider(parameterName = WAREHOUSE_PARAM_NAME, numericKey = true, lookupSource = LookupSource.lookup)
	public LookupValuesList getAvailableWarehouses()
	{
		final boolean existQuarantineHUs = existQuarantineHUs();

		return Services.get(IWarehouseDAO.class).getAllWarehouses()
				.stream()
				.map(warehouse -> create(warehouse, I_M_Warehouse.class))
				.filter(warehouse -> !existQuarantineHUs || warehouse.isQuarantineWarehouse())
				.map(warehouse -> IntegerLookupValue.of(warehouse.getM_Warehouse_ID(), warehouse.getName()))
				.collect(LookupValuesList.collect());

	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (WAREHOUSE_PARAM_NAME.equals(parameter.getColumnName()))
		{

			if (existQuarantineHUs())
			{
				final I_M_Warehouse quarantineWarehouse = Services.get(IWarehouseDAO.class).retrieveQuarantineWarehouseOrNull();

				if (quarantineWarehouse == null)
				{
					throw new AdempiereException("@" + WarehouseDAO.MSG_M_Warehouse_NoQuarantineWarehouse + "@");
				}

				return quarantineWarehouse.getM_Warehouse_ID();
			}

			final int singleWarehouseId = CollectionUtils.extractSingleElementOrDefault(
					getM_ReceiptSchedules(),
					I_M_ReceiptSchedule::getM_Warehouse_Dest_ID,
					-1);
			if (singleWarehouseId > 0)
			{
				return singleWarehouseId;
			}
		}

		else if (LOCATOR_PARAM_NAME.equals(parameter.getColumnName()))
		{
			if (warehouseIdOrNull() != null)
			{
				final LocatorId defaultLocator = warehouseBL.getDefaultLocatorId(warehouseId());
				if (defaultLocator != null)
				{
					return defaultLocator.getRepoId();
				}
			}
		}
		return null;
	}

	private boolean existQuarantineHUs()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		return retrieveHUsToReceive()
				.stream()
				.map(handlingUnitsDAO::getById)
				.anyMatch(lotNumberQuarantineService::isQuarantineHU);
	}

	@Override
	public void onParameterChanged(String parameterName)
	{
		if (!WAREHOUSE_PARAM_NAME.equals(parameterName))
		{
			return;
		}
		if (warehouseIdOrNull() == null)
		{
			locatorRepoId = 0;
			return;
		}

		locatorRepoId = warehouseBL.getDefaultLocatorId(warehouseId()).getRepoId();
	}

	@ProcessParamLookupValuesProvider(parameterName = LOCATOR_PARAM_NAME, dependsOn = WAREHOUSE_PARAM_NAME, numericKey = true)
	public LookupValuesList getLocators()
	{
		if (warehouseRepoId <= 0)
		{
			return LookupValuesList.EMPTY;
		}

		return warehouseDAO
				.getLocators(warehouseId())
				.stream()
				.map(locator -> IntegerLookupValue.of(locator.getM_Locator_ID(), locator.getValue()))
				.collect(LookupValuesList.collect());
	}

	@Override
	protected void customizeParametersBuilder(@NonNull final CreateReceiptsParametersBuilder parametersBuilder)
	{
		final LocatorId locatorId = LocatorId.ofRepoIdOrNull(warehouseIdOrNull(), locatorRepoId);
		parametersBuilder.destinationLocatorIdOrNull(locatorId);
	}

	private WarehouseId warehouseId()
	{
		return WarehouseId.ofRepoId(warehouseRepoId);
	}

	private WarehouseId warehouseIdOrNull()
	{
		return WarehouseId.ofRepoIdOrNull(warehouseRepoId);
	}
}
