package de.metas.ui.web.pporder.process;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;

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

public class WEBUI_PP_Order_Issue
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	private static final String PARAM_M_HU_ID = I_M_HU.COLUMNNAME_M_HU_ID;
	@Param(parameterName = PARAM_M_HU_ID, mandatory = true)
	private int p_M_HU_ID;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedDocumentIds().size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderLineRow row = getSingleSelectedRow();
		if (!row.isIssue())
		{
			return ProcessPreconditionsResolution.reject("cannot issue to selected line");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_HU_ID, dependsOn = {}, numericKey = true)
	public LookupValuesList getAvailableHUsToIssue()
	{
		final PPOrderLineRow row = getSingleSelectedRow();

		return handlingUnitsDAO
				.createHUQueryBuilder()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				//
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.addOnlyWithProductId(row.getM_Product_ID())
				.setOnlyTopLevelHUs()
				//
				.list().stream()
				.map(hu -> IntegerLookupValue.of(hu.getM_HU_ID(), handlingUnitsBL.getDisplayName(hu)))
				.collect(LookupValuesList.collect());
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final I_M_HU hu = InterfaceWrapperHelper.load(p_M_HU_ID, I_M_HU.class);
		final int ppOrderId = getView().getPP_Order_ID();

		huPPOrderBL
				.createIssueProducer()
				.setTargetOrderBOMLinesByPPOrderId(ppOrderId)
				.createIssues(hu);

		getView().invalidateAll();

		return MSG_OK;
	}
}
