/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.material.cockpit;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.cockpit.QtyDemandSupplyRepository;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.material.cockpit.filters.MaterialCockpitFilters;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_DocumentDetail_Display;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_PricingConditions;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_ShowStockDetails;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.standard.DefaultDocumentDescriptorFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@ViewFactory(windowId = MaterialCockpitUtil.WINDOWID_MaterialCockpitView_String, //
		viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class MaterialCockpitViewFactory implements IViewFactory
{
	public static final String SYSCFG_Layout = "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout";
	/**
	 * Please keep its prefix in sync with {@link MaterialCockpitRow#SYSCFG_PREFIX}
	 */
	public static final String SYSCFG_DisplayIncludedRows = "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.DisplayIncludedRows";

	private final MaterialCockpitRowsLoader materialCockpitRowsLoader;
	private final MaterialCockpitFilters materialCockpitFilters;
	private final MaterialCockpitRowFactory materialCockpitRowFactory;
	private final QtyDemandSupplyRepository qtyDemandSupplyRepository;

	private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public MaterialCockpitViewFactory(
			@NonNull final MaterialCockpitRowsLoader materialCockpitRowsLoader,
			@NonNull final MaterialCockpitFilters materialCockpitFilters,
			@NonNull final DefaultDocumentDescriptorFactory defaultDocumentDescriptorFactory,
			@NonNull final MaterialCockpitRowFactory materialCockpitRowFactory,
			@NonNull final QtyDemandSupplyRepository qtyDemandSupplyRepository)
	{
		this.materialCockpitRowsLoader = materialCockpitRowsLoader;
		this.materialCockpitFilters = materialCockpitFilters;
		this.materialCockpitRowFactory = materialCockpitRowFactory;
		this.qtyDemandSupplyRepository = qtyDemandSupplyRepository;

		defaultDocumentDescriptorFactory.addUnsupportedWindowId(MaterialCockpitUtil.WINDOWID_MaterialCockpitView);
	}

	@Override
	public MaterialCockpitView createView(@NonNull final CreateViewRequest request)
	{
		assertWindowIdOfRequestIsCorrect(request);

		final DocumentFilterDescriptorsProvider filterDescriptors = materialCockpitFilters.getFilterDescriptors();
		final DocumentFilterList requestFilters = materialCockpitFilters.extractDocumentFilters(request);
		final DocumentFilterList filtersToUse = request.isUseAutoFilters() ? materialCockpitFilters.createAutoFilters() : requestFilters;

		final MaterialCockpitRowsData rowsData = createRowsData(filtersToUse, materialCockpitRowsLoader);

		return MaterialCockpitView.builder()
				.viewId(request.getViewId())
				.description(TranslatableStrings.empty())
				.filters(filtersToUse)
				.filterDescriptors(filterDescriptors)
				.rowsData(rowsData)
				.relatedProcessDescriptor(createProcessDescriptor(MD_Cockpit_DocumentDetail_Display.class))
				.relatedProcessDescriptor(createProcessDescriptor(MD_Cockpit_PricingConditions.class))
				.relatedProcessDescriptor(createProcessDescriptor(MD_Cockpit_ShowStockDetails.class))
				.relatedProcessDescriptor(createProcessDescriptor(MD_Cockpit_SetProcurementStatus.class))
				.build();
	}

	private void assertWindowIdOfRequestIsCorrect(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		final WindowId windowId = viewId.getWindowId();

		Check.errorUnless(MaterialCockpitUtil.WINDOWID_MaterialCockpitView.equals(windowId),
				"The parameter request needs to have WindowId={}, but has {} instead; request={};",
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView, windowId, request);
	}

	private MaterialCockpitRowsData createRowsData(
			@NonNull final DocumentFilterList filters,
			@NonNull final MaterialCockpitRowsLoader materialCockpitRowsLoader)
	{
		final LocalDate date = materialCockpitFilters.getFilterByDate(filters);
		final MaterialCockpitDetailsRowAggregation detailsRowAggregation = retrieveDetailsRowAggregation();
		if (date == null)
		{
			return new MaterialCockpitRowsData(detailsRowAggregation, materialCockpitRowFactory, ImmutableList.of(), qtyDemandSupplyRepository);
		}

		final List<MaterialCockpitRow> rows = materialCockpitRowsLoader.getMaterialCockpitRows(filters, date, detailsRowAggregation);

		return new MaterialCockpitRowsData(detailsRowAggregation, materialCockpitRowFactory, rows, qtyDemandSupplyRepository);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		Check.errorUnless(MaterialCockpitUtil.WINDOWID_MaterialCockpitView.equals(windowId),
				"The parameter windowId needs to be {}, but is {} instead; viewDataType={}; ",
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView, windowId, viewDataType);

		final String commaSeparatedFieldNames = sysConfigBL.getValue(SYSCFG_Layout, (String)null);
		final boolean displayIncludedRows = sysConfigBL.getBooleanValue(SYSCFG_DisplayIncludedRows, true);

		final ViewLayout.Builder viewlayOutBuilder = ViewLayout.builder()
				.setWindowId(windowId)
				.setHasTreeSupport(displayIncludedRows)
				.setTreeCollapsible(true)
				.setTreeExpandedDepth(ViewLayout.TreeExpandedDepth_AllCollapsed)
				.setAllowOpeningRowDetails(false)
				.addElementsFromViewRowClass(MaterialCockpitRow.class, viewDataType, commaSeparatedFieldNames)
				.setFilters(materialCockpitFilters.getFilterDescriptors().getAll());

		return viewlayOutBuilder.build();
	}

	private RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final AdProcessId processId = processDAO.retrieveProcessIdByClass(processClass);
		if (processId == null)
		{
			throw new AdempiereException("No processId found for " + processClass);
		}

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.anyTable().anyWindow()
				.displayPlace(RelatedProcessDescriptor.DisplayPlace.ViewQuickActions)
				.build();
	}

	private MaterialCockpitDetailsRowAggregation retrieveDetailsRowAggregation()
	{
		final String sysConfigValue = sysConfigBL.getValue(
				MaterialCockpitUtil.SYSCONFIG_DETAILS_ROW_AGGREGATION,
				MaterialCockpitDetailsRowAggregation.NONE.getValue(),
				Env.getAD_Client_ID(),
				Env.getAD_Org_ID(Env.getCtx()));

		return MaterialCockpitDetailsRowAggregation.getValueOrDefault(sysConfigValue);
	}
}
