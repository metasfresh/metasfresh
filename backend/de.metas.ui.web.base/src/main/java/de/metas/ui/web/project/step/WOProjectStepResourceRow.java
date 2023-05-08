/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.project.step;

import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Step;

import javax.annotation.Nullable;
import java.util.Set;

public class WOProjectStepResourceRow implements IViewRow
{
	@ViewColumn(seqNo = 10,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_C_Project_WO_Step.COLUMNNAME_Name,
			fieldName = I_C_Project_WO_Step.COLUMNNAME_Name,
			editor = ViewEditorRenderMode.NEVER)
	private final String name;

	@ViewColumn(seqNo = 20,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID,
			fieldName = I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID,
			editor = ViewEditorRenderMode.NEVER,
			displayed = ViewColumn.ViewColumnLayout.Displayed.FALSE)
	private final WOProjectStepId stepId;

	@ViewColumn(seqNo = 30,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_C_Project_WO_Step.COLUMNNAME_C_Project_ID,
			fieldName = I_C_Project_WO_Step.COLUMNNAME_C_Project_ID,
			editor = ViewEditorRenderMode.NEVER)
	private final ProjectId projectId;

	@ViewColumn(seqNo = 40,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID,
			fieldName = I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID,
			editor = ViewEditorRenderMode.NEVER)
	@Getter
	private final WOProjectResourceId resourceId;

	@ViewColumn(seqNo = 50,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_C_Project_WO_Resource.COLUMNNAME_Duration,
			fieldName = I_C_Project_WO_Resource.COLUMNNAME_Duration,
			editor = ViewEditorRenderMode.NEVER)
	private final Long reservedHours;

	@ViewColumn(seqNo = 60,
			widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_C_Project_WO_Resource.COLUMNNAME_ResolvedHours,
			fieldName = I_C_Project_WO_Resource.COLUMNNAME_ResolvedHours,
			editor = ViewEditorRenderMode.NEVER)
	private final Long resolvedHours;

	private final DocumentId rowId;

	private final ViewRowFieldNameAndJsonValuesHolder<WOProjectStepResourceRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(WOProjectStepResourceRow.class);

	@Builder
	private WOProjectStepResourceRow(
			@NonNull final String name,
			@NonNull final WOProjectStepId stepId,
			@NonNull final ProjectId projectId,
			@NonNull final WOProjectResourceId resourceId,
			@NonNull final DocumentId rowId,
			@Nullable final Long reservedHours,
			@Nullable final Long resolvedHours)
	{
		this.name = name;
		this.stepId = stepId;
		this.projectId = projectId;
		this.resourceId = resourceId;
		this.reservedHours = reservedHours;
		this.resolvedHours = resolvedHours;
		this.rowId = rowId;
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
}
