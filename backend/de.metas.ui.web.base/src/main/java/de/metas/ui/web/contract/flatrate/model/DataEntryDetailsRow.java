/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.contract.flatrate.model;

import com.google.common.collect.ImmutableMap;
import de.metas.contracts.model.I_C_Flatrate_DataEntry_Detail;
import de.metas.ui.web.order.products_proposal.model.ProductASIDescription;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Set;

@ToString(exclude = { "values" })
public class DataEntryDetailsRow implements IViewRow
{
	public static final String FIELD_Department = I_C_Flatrate_DataEntry_Detail.COLUMNNAME_C_BPartner_Department_ID;
	@ViewColumn(seqNo = 10, fieldName = FIELD_Department, captionKey = FIELD_Department, widgetType = DocumentFieldWidgetType.Lookup, editor = ViewEditorRenderMode.NEVER)
	@Getter
	private final LookupValue department;

	public static final String FIELD_ASI = I_C_Flatrate_DataEntry_Detail.COLUMNNAME_M_AttributeSetInstance_ID;
	@ViewColumn(seqNo = 20, fieldName = FIELD_ASI, captionKey = FIELD_ASI, widgetType = DocumentFieldWidgetType.ProductAttributes, editor = ViewEditorRenderMode.NEVER)
	@Getter
	private final ProductASIDescription asi;

	/** I don't know how to make the field read-only if the entry is processed */
	public static final String FIELD_Qty = I_C_Flatrate_DataEntry_Detail.COLUMNNAME_Qty_Reported;
	@ViewColumn(seqNo = 30, fieldName = FIELD_Qty, captionKey = FIELD_Qty, widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final BigDecimal qty;

	public static final String FIELD_UOM = I_C_Flatrate_DataEntry_Detail.COLUMNNAME_C_UOM_ID;
	@ViewColumn(seqNo = 60, fieldName = FIELD_UOM, captionKey = FIELD_UOM, widgetType = DocumentFieldWidgetType.Lookup, editor = ViewEditorRenderMode.NEVER)
	private final LookupValue uom;

	@Getter
	private final DocumentId id;

	private final boolean processed;

	private final ViewRowFieldNameAndJsonValuesHolder<DataEntryDetailsRow> values;

	private static final ImmutableMap<String, ViewEditorRenderMode> EDITOR_RENDER_MODES = ImmutableMap.<String, ViewEditorRenderMode> builder()
			.put(FIELD_Qty, ViewEditorRenderMode.ALWAYS)			
			.build();


	@Builder(toBuilder = true)
	public DataEntryDetailsRow(
			final boolean processed,
			@NonNull final ProductASIDescription asi,
			@Nullable final LookupValue department,
			@NonNull final DocumentId id,
			@Nullable final BigDecimal qty,
			@NonNull final LookupValue uom)
	{
		this.processed = processed;
		this.asi = asi;
		this.department = department;
		this.id = id;
		this.qty = qty;
		this.uom = uom;

		this.values = ViewRowFieldNameAndJsonValuesHolder.builder(DataEntryDetailsRow.class)
				.viewEditorRenderModeByFieldName(EDITOR_RENDER_MODES)
				.build();
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
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
}
