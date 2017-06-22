package de.metas.ui.web.picking;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
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

@Component
public class PickingSlotViewRepository
{
	private final LookupDataSource warehouseLookup;
	private final LookupDataSource bpartnerLookup;
	private final LookupDataSource bpartnerLocationLookup;

	public PickingSlotViewRepository()
	{
		warehouseLookup = LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setColumnName(I_M_PickingSlot.COLUMNNAME_M_Warehouse_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField));
		bpartnerLookup = LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setColumnName(I_M_PickingSlot.COLUMNNAME_C_BPartner_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField));
		bpartnerLocationLookup = LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setColumnName(I_M_PickingSlot.COLUMNNAME_C_BPartner_Location_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField));
	}

	public List<PickingSlotRow> retrieveRowsByIds(final Collection<DocumentId> rowIds)
	{
		final Set<Integer> pickingSlotIds = rowIds.stream().map(DocumentId::toInt).collect(ImmutableSet.toImmutableSet());
		return Services.get(IPickingSlotDAO.class).retrievePickingSlotsByIds(pickingSlotIds)
				.stream()
				.map(pickingSlotPO -> createPickingSlotRow(pickingSlotPO))
				.collect(ImmutableList.toImmutableList());
	}
	
	public Set<Integer> retrieveAllRowIds()
	{
		return Services.get(IPickingSlotDAO.class).retrievePickingSlots(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.stream()
				.map(I_M_PickingSlot::getM_PickingSlot_ID)
				.collect(ImmutableSet.toImmutableSet());
	}


	private PickingSlotRow createPickingSlotRow(final I_M_PickingSlot pickingSlotPO)
	{
		final DocumentId rowId = DocumentId.of(pickingSlotPO.getM_PickingSlot_ID());
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(PickingConstants.WINDOWID_PickingSlotView, rowId);
		return PickingSlotRow.builder()
				.documentPath(documentPath)
				.id(rowId)
				.type(DefaultRowType.Row)
				.processed(false)
				//
				.name(pickingSlotPO.getPickingSlot())
				.warehouse(warehouseLookup.findById(pickingSlotPO.getM_Warehouse_ID()))
				.bpartner(bpartnerLookup.findById(pickingSlotPO.getC_BPartner_ID()))
				.bpartnerLocation(bpartnerLocationLookup.findById(pickingSlotPO.getC_BPartner_Location_ID()))
				//
				.build();
	}

}
