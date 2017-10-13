package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;

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

public class WEBUI_M_HU_Pick extends HUEditorProcessTemplate implements IProcessPrecondition
{
	@Autowired
	private PickingCandidateService pickingCandidateService;

	private static final String PARAM_M_PickingSlot_ID = I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID;
	@Param(parameterName = PARAM_M_PickingSlot_ID, mandatory = true)
	private int pickingSlotId;

	private static final String PARAM_M_ShipmentSchedule_ID = I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;
	@Param(parameterName = PARAM_M_ShipmentSchedule_ID, mandatory = true)
	private int shipmentScheduleId;

	private LookupDataSource _shipmentScheduleDataSource;
	private LookupDataSource _pickingSlotDataSource;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final List<HUEditorRow> rows = getSelectedRows(HUEditorRowFilter.select(Select.ONLY_TOPLEVEL));
		if (rows.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_M_HU_Messages.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		if (rows.size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		
		final HUEditorRow row = rows.get(0);
		if (!row.isHUStatusActive())
		{
			return ProcessPreconditionsResolution.reject("select an active HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_ShipmentSchedule_ID, numericKey = true, lookupSource = LookupSource.lookup)
	private LookupValuesList getShipmentScheduleValues(final LookupDataSourceContext context)
	{
		final LookupDataSource shipmentScheduleDataSource = getShipmentScheduleDataSource();
		final LookupValuesList result = shipmentScheduleDataSource.findEntities(context, context.getFilter(), context.getOffset(0), context.getLimit(10));
		return result;
	}

	private LookupDataSource getShipmentScheduleDataSource()
	{
		if (_shipmentScheduleDataSource == null)
		{
			final LookupDescriptor lookupDescriptor = SqlLookupDescriptor.builder()
					.setColumnName(PARAM_M_ShipmentSchedule_ID)
					.setDisplayType(DisplayType.Search)
					.addValidationRule(createShipmentSchedulesValidationRule())
					.buildForDefaultScope();
			_shipmentScheduleDataSource = LookupDataSourceFactory.instance.createLookupDataSource(lookupDescriptor);
		}

		return _shipmentScheduleDataSource;
	}

	private IValidationRule createShipmentSchedulesValidationRule()
	{
		final I_M_HU hu = getRecord(I_M_HU.class);
		final List<Integer> productIds = Services.get(IHUContextFactory.class)
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(hu)
				.getProductStorages()
				.stream()
				.map(IHUProductStorage::getM_Product_ID)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		if (productIds.isEmpty())
		{
			throw new AdempiereException("Empty HUs are not allowed");
		}
		if (productIds.size() > 1)
		{
			throw new AdempiereException("Multi product HUs are not allowed");
		}

		final StringBuilder sqlWhereClause = new StringBuilder();
		sqlWhereClause.append(I_M_ShipmentSchedule.COLUMNNAME_Processed).append("=").append(DB.TO_BOOLEAN(false));

		final int productId = productIds.get(0);
		sqlWhereClause.append(" AND ").append(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID).append("=").append(productId);

		// TODO: filter by selected HU's BPartner, Location etc

		return Services.get(IValidationRuleFactory.class).createSQLValidationRule(sqlWhereClause.toString());
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_PickingSlot_ID, numericKey = true, lookupSource = LookupSource.lookup)
	private LookupValuesList getPickingSlotValues(final LookupDataSourceContext context)
	{
		final LookupDataSource pickingSlotDataSource = getPickingSlotDataSource();
		final LookupValuesList result = pickingSlotDataSource.findEntities(context, context.getFilter(), context.getOffset(0), context.getLimit(10));
		return result;
	}

	private LookupDataSource getPickingSlotDataSource()
	{
		if (_pickingSlotDataSource == null)
		{
			final LookupDescriptor lookupDescriptor = SqlLookupDescriptor.builder()
					.setColumnName(PARAM_M_PickingSlot_ID)
					.setDisplayType(DisplayType.Search)
					.buildForDefaultScope();

			// TODO: filter by selected HU's BPartner, Location etc

			_pickingSlotDataSource = LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);
		}

		return _pickingSlotDataSource;
	}

	@Override
	protected String doIt() throws Exception
	{
		final Set<Integer> huIdsToPick = getSelectedHUIds(Select.ONLY_TOPLEVEL);
		if (huIdsToPick.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		huIdsToPick.forEach(this::pickHU);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		invalidateView();
	}

	private void pickHU(final int huId)
	{
		pickingCandidateService.addHUToPickingSlot(huId, pickingSlotId, shipmentScheduleId);
		// TODO: do we need a material movement???
		pickingCandidateService.setCandidatesProcessed(ImmutableList.of(huId), pickingSlotId, OptionalInt.of(shipmentScheduleId));
	}

}
