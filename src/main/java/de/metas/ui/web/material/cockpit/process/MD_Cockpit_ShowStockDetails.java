package de.metas.ui.web.material.cockpit.process;

import java.util.List;

import org.adempiere.mm.attributes.AttributeId;
import org.compiere.model.I_M_Attribute;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.handlingunits.model.I_M_HU_Stock_Detail_V;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilter.Builder;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitUtil;
import de.metas.ui.web.material.cockpit.MaterialCockpitView;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;

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

public class MD_Cockpit_ShowStockDetails extends MaterialCockpitViewBasedProcess
{
	@Autowired
	private transient IViewsRepository viewsRepo;
	// = Adempiere.getBean(IViewsRepository.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No MaterialCockpitrows are selected");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final String viewId = createView().getViewId();
		//getResult().setWebuiViewId(viewId);
		getResult().setWebuiIncludedViewIdToOpen(viewId);

		return MSG_OK;
	}

	private ViewId createView()
	{
		final MaterialCockpitView materialCockpitView = getView();

		final List<MaterialCockpitRow> rows = getSelectedRowIds()
				.stream()
				.map(materialCockpitView::getById)
				.collect(ImmutableList.toImmutableList());

		final CreateViewRequest.Builder viewRequestBuilder = CreateViewRequest
				.builder(MaterialCockpitUtil.WINDOW_MaterialCockpit_StockDetailView)
				.setParentViewId(getView().getViewId());

		for (final MaterialCockpitRow row : rows)
		{
			Builder builder = newBuilder(row);

			boolean attributeFilterAdded = false;

			final DimensionSpecGroup dimensionGroup = row.getDimensionGroupOrNull();
			if (dimensionGroup == null)
			{
				final DimensionSpec dimensionSpec = MaterialCockpitUtil.retrieveDimensionSpec();
				for (final I_M_Attribute attributeRecord : dimensionSpec.retrieveAttributes())
				{
					final int attributeRepoId = attributeRecord.getM_Attribute_ID();
					final DocumentFilterParam attributeParameter = DocumentFilterParam
							.builder()
							.setFieldName(I_M_HU_Stock_Detail_V.COLUMNNAME_M_Attribute_ID)
							.setValue(attributeRepoId)
							.build();
					builder.addParameter(attributeParameter);

					final DocumentFilterParam attributeValueParameter = DocumentFilterParam
							.builder()
							.setFieldName(I_M_HU_Stock_Detail_V.COLUMNNAME_AttributeValue)
							.setValue(MaterialCockpitUtil.DONT_FILTER)
							.build();
					builder.addParameter(attributeValueParameter);

					final DocumentFilter filter = builder.setFilterId(row.getId().toString() + "_" + attributeRepoId).build();

					viewRequestBuilder.addStickyFilters(filter);
					attributeFilterAdded = true;
					builder = newBuilder(row);
				}
			}
			else
			{
				for (final AttributeId attributeId : dimensionGroup.getAttributeIds())
				{
					final int attributeRepoId = attributeId.getRepoId();
					final DocumentFilterParam attributeParameter = DocumentFilterParam
							.builder()
							.setFieldName(I_M_HU_Stock_Detail_V.COLUMNNAME_M_Attribute_ID)
							.setValue(attributeRepoId)
							.build();
					builder.addParameter(attributeParameter);

					final DocumentFilterParam attributeValueParameter = DocumentFilterParam
							.builder()
							.setFieldName(I_M_HU_Stock_Detail_V.COLUMNNAME_AttributeValue)
							.setValue(MaterialCockpitUtil.NON_EMPTY)
							.build();
					builder.addParameter(attributeValueParameter);

					final DocumentFilter filter = builder.setFilterId(row.getId().toString() + "_" + attributeRepoId).build();

					viewRequestBuilder.addStickyFilters(filter);
					attributeFilterAdded = true;
					builder = newBuilder(row);
				}
			}
			if (!attributeFilterAdded)
			{
				final DocumentFilter filter = builder.setFilterId(row.getId().toString()).build();
				viewRequestBuilder.addStickyFilters(filter);
			}
		}

		final IView view = viewsRepo.createView(viewRequestBuilder.build());
		return view.getViewId();
	}

	private Builder newBuilder(final MaterialCockpitRow row)
	{
		final Builder builder = DocumentFilter.builder();

		final DocumentFilterParam productParameter = DocumentFilterParam
				.builder()
				.setFieldName(I_M_HU_Stock_Detail_V.COLUMNNAME_M_Product_ID)
				.setValue(row.getProductId())
				.build();
		builder.addParameter(productParameter);
		return builder;
	}

}
