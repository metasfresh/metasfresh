package de.metas.ui.web.process.adprocess;

import org.adempiere.ad.expression.api.LogicExpressionResult;

import de.metas.ui.web.devices.DeviceDescriptorsList;
import de.metas.ui.web.process.IProcessInstanceParameter;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.DocumentValidStatus;
import de.metas.ui.web.window.model.IDocumentFieldView;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public final class DocumentFieldAsProcessInstanceParameter implements IProcessInstanceParameter
{
	public static DocumentFieldAsProcessInstanceParameter of(final IDocumentFieldView documentField)
	{
		return new DocumentFieldAsProcessInstanceParameter(documentField);
	}

	private final IDocumentFieldView documentField;

	private DocumentFieldAsProcessInstanceParameter(@NonNull final IDocumentFieldView documentField)
	{
		this.documentField = documentField;
	}

	@Override
	public String getParameterName()
	{
		return documentField.getFieldName();
	}

	@Override
	public DocumentFieldWidgetType getWidgetType()
	{
		return documentField.getWidgetType();
	}

	@Override
	public Object getValueAsJsonObject(final JSONOptions jsonOpts)
	{
		return documentField.getValueAsJsonObject(jsonOpts);
	}

	@Override
	public LogicExpressionResult getReadonly()
	{
		return documentField.getReadonly();
	}

	@Override
	public LogicExpressionResult getMandatory()
	{
		return documentField.getMandatory();
	}

	@Override
	public LogicExpressionResult getDisplayed()
	{
		return documentField.getDisplayed();
	}

	@Override
	public DocumentValidStatus getValidStatus()
	{
		return documentField.getValidStatus();
	}

	@Override
	public DeviceDescriptorsList getDevices()
	{
		return documentField.getDescriptor()
				.getDeviceDescriptorsProvider()
				.getDeviceDescriptors();
	}
}
