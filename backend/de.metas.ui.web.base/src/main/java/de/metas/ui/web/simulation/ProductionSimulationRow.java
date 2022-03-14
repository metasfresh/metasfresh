/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.simulation;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public class ProductionSimulationRow implements IViewRow
{
	@ViewColumn(seqNo = 10,
			widgetType = DocumentFieldWidgetType.Lookup,
			widgetSize = WidgetSize.Small,
			captionKey = I_MD_Candidate.COLUMNNAME_M_Product_ID,
			fieldName = I_MD_Candidate.COLUMNNAME_M_Product_ID,
			editor = ViewEditorRenderMode.NEVER)
	private final LookupValue product;

	@ViewColumn(seqNo = 20,
			widgetType = DocumentFieldWidgetType.Lookup,
			widgetSize = WidgetSize.Small,
			captionKey = I_MD_Candidate.COLUMNNAME_M_AttributeSetInstance_ID,
			fieldName = I_MD_Candidate.COLUMNNAME_M_AttributeSetInstance_ID,
			editor = ViewEditorRenderMode.NEVER)
	private final LookupValue attributeSetInstance;

	@ViewColumn(seqNo = 30,
			widgetType = DocumentFieldWidgetType.Lookup,
			widgetSize = WidgetSize.Small,
			captionKey = I_MD_Candidate.COLUMNNAME_M_Warehouse_ID,
			fieldName = I_MD_Candidate.COLUMNNAME_M_Warehouse_ID,
			editor = ViewEditorRenderMode.NEVER)
	private final LookupValue warehouse;

	@ViewColumn(seqNo = 40,
			widgetType = DocumentFieldWidgetType.Quantity,
			widgetSize = WidgetSize.Small,
			captionKey = I_MD_Candidate.COLUMNNAME_Qty,
			fieldName = I_MD_Candidate.COLUMNNAME_Qty,
			editor = ViewEditorRenderMode.NEVER)
	private final BigDecimal qty;

	@ViewColumn(seqNo = 50,
			widgetType = DocumentFieldWidgetType.Quantity,
			widgetSize = WidgetSize.Small,
			captionKey = "SimulatedStock",
			fieldName = "SimulatedStock",
			editor = ViewEditorRenderMode.NEVER)
	private final BigDecimal simulatedStock;

	@ViewColumn(seqNo = 60,
			widgetType = DocumentFieldWidgetType.ZonedDateTime,
			widgetSize = WidgetSize.Small,
			captionKey = I_MD_Candidate.COLUMNNAME_DateProjected,
			fieldName = I_MD_Candidate.COLUMNNAME_DateProjected,
			editor = ViewEditorRenderMode.NEVER)
	private final ZonedDateTime dateProjected;

	@ViewColumn(seqNo = 70,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_MD_Candidate.COLUMNNAME_MD_Candidate_Type,
			fieldName = I_MD_Candidate.COLUMNNAME_MD_Candidate_Type,
			editor = ViewEditorRenderMode.NEVER)
	private final String type;

	@ViewColumn(seqNo = 80,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_MD_Candidate.COLUMNNAME_MD_Candidate_BusinessCase,
			fieldName = I_MD_Candidate.COLUMNNAME_MD_Candidate_BusinessCase,
			editor = ViewEditorRenderMode.NEVER)
	private final String businessCase;

	@ViewColumn(seqNo = 80,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = "IsSimulatedMaterial",
			fieldName = "IsSimulatedMaterial",
			editor = ViewEditorRenderMode.NEVER)
	private final Boolean isSimulated;

	@ViewColumn(seqNo = 90,
			widgetType = DocumentFieldWidgetType.Color,
			widgetSize = WidgetSize.Small,
			captionKey = "Status",
			fieldName = "Status",
			editor = ViewEditorRenderMode.NEVER)
	@Nullable
	private final ColorValue lineStatusColor;

	private final DocumentId rowId;

	@NonNull
	private final List<ProductionSimulationRow> includedRows;

	private final ViewRowFieldNameAndJsonValuesHolder<ProductionSimulationRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(ProductionSimulationRow.class);

	@Builder
	private ProductionSimulationRow(
			@NonNull final LookupValue product,
			@Nullable final LookupValue attributeSetInstance,
			@Nullable final LookupValue warehouse,
			@NonNull final BigDecimal qty,
			@NonNull final BigDecimal simulatedStock,
			@NonNull final ZonedDateTime dateProjected,
			@NonNull final String type,
			@Nullable final String businessCase,
			@NonNull final DocumentId rowId,
			@NonNull final Boolean isSimulated,
			@Nullable final List<ProductionSimulationRow> includedRows,
			@Nullable final ColorValue lineStatusColor)
	{
		this.product = product;
		this.attributeSetInstance = attributeSetInstance;
		this.warehouse = warehouse;
		this.qty = qty;
		this.simulatedStock = simulatedStock;
		this.dateProjected = dateProjected;
		this.type = type;
		this.businessCase = businessCase;
		this.rowId = rowId;
		this.isSimulated = isSimulated;
		this.includedRows = CoalesceUtil.coalesceNotNull(includedRows, ImmutableList.of());
		this.lineStatusColor = lineStatusColor;
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Nullable
	@Override
	public DocumentPath getDocumentPath()
	{
		return null;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	@Override
	@NonNull
	public List<ProductionSimulationRow> getIncludedRows()
	{
		return includedRows;
	}
}