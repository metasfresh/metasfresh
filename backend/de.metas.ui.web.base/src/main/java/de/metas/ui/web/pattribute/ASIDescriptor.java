package de.metas.ui.web.pattribute;

import de.metas.ui.web.pattribute.ASIDescriptorFactory.ASIAttributeFieldBinding;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Value
public class ASIDescriptor
{
	@NonNull AttributeSetId attributeSetId;
	@NonNull DocumentEntityDescriptor entityDescriptor;
	@NonNull ASILayout layout;

	@Nullable DocumentPath contextDocumentPath;
	@Nullable String contextTableName;
	@Nullable String contextColumnName;

	@Builder
	private ASIDescriptor(
			@NonNull final AttributeSetId attributeSetId,
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			@NonNull final ASILayout layout,
			@Nullable final DocumentPath contextDocumentPath,
			@Nullable final String contextTableName,
			@Nullable final String contextColumnName)
	{
		this.attributeSetId = attributeSetId;
		this.entityDescriptor = entityDescriptor;
		this.layout = layout;

		this.contextDocumentPath = contextDocumentPath;
		this.contextTableName = contextTableName;
		this.contextColumnName = contextColumnName;
	}

	public int getContextRecordId()
	{
		return contextDocumentPath != null ? contextDocumentPath.getSingleRecordId() : -1;
	}

	public boolean isAttributeField(String fieldName)
	{
		final DocumentFieldDescriptor field = entityDescriptor.getFieldOrNull(fieldName);
		if (field == null)
		{
			return false;
		}

		final DocumentFieldDataBindingDescriptor fieldBinding = field.getDataBinding().orElse(null);
		return fieldBinding instanceof ASIAttributeFieldBinding;
	}

	@NonNull
	public AttributeId getAttributeId(String fieldName)
	{
		return getFieldDataBinding(fieldName).getAttributeId();
	}

	@NonNull
	public AttributeCode getAttributeCode(String fieldName)
	{
		return getFieldDataBinding(fieldName).getAttributeCode();
	}

	@NonNull
	private ASIAttributeFieldBinding getFieldDataBinding(final String fieldName)
	{
		return entityDescriptor.getField(fieldName).getDataBindingNotNull(ASIAttributeFieldBinding.class);
	}
}
