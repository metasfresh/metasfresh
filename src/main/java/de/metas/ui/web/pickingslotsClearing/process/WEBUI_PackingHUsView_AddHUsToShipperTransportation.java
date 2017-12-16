package de.metas.ui.web.pickingslotsClearing.process;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.IMsgBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;

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

public class WEBUI_PackingHUsView_AddHUsToShipperTransportation extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final transient IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, mandatory = true)
	private int shipperTransportationId;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean eligibleHUsFound = streamEligibleHURows()
				.findAny()
				.isPresent();
		if (!eligibleHUsFound)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_M_HU> hus = retrieveEligibleHUs();
		huShipperTransportationBL.addHUsToShipperTransportation(shipperTransportationId, hus);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		getView().invalidateAll();
	}

	private List<I_M_HU> retrieveEligibleHUs()
	{
		final Set<Integer> huIds = streamEligibleHURows()
				.map(HUEditorRow::getM_HU_ID)
				.collect(ImmutableSet.toImmutableSet());
		Check.assumeNotEmpty(huIds, "huIds is not empty"); // shall not happen

		final List<I_M_HU> hus = queryBL
				.createQueryBuilder(I_M_HU.class)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_HU.class);
		Check.assumeNotEmpty(hus, "hus is not empty"); // shall not happen

		return hus;

	}

	private Stream<HUEditorRow> streamEligibleHURows()
	{
		return streamSelectedRows(HUEditorRowFilter.select(Select.ONLY_TOPLEVEL));
	}

}
