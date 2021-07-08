package de.metas.ui.web.pattribute;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.util.ASIEditingInfo;
import org.adempiere.mm.attributes.util.ASIEditingInfo.WindowType;
import org.compiere.model.I_M_Attribute;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
@Builder
public final class WebuiASIEditingInfo
{
	public static WebuiASIEditingInfoBuilder builder(@NonNull ASIEditingInfo info)
	{
		return new WebuiASIEditingInfoBuilder()
				.contextWindowType(info.getWindowType())
				// .contextDocumentPath(contextDocumentPath)
				//
				.attributeSetId(info.getAttributeSetId())
				.attributeSetName(info.getM_AttributeSet_Name())
				.attributeSetDescription(info.getM_AttributeSet_Description())
				//
				.attributeSetInstanceId(info.getAttributeSetInstanceId() != null ? info.getAttributeSetInstanceId() : AttributeSetInstanceId.NONE)
				.productId(info.getProductId())
				.soTrx(info.getSOTrx())
				//
				.callerTableName(info.getCallerTableName())
				.callerAdColumnId(info.getCallerColumnId())
				//
				.attributes(info.getAvailableAttributes());
	}

	public static WebuiASIEditingInfo readonlyASI(final AttributeSetInstanceId attributeSetInstanceId)
	{
		final ASIEditingInfo info = ASIEditingInfo.builder()
				.type(WindowType.StrictASIAttributes)
				.soTrx(SOTrx.SALES)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		return builder(info).build();
	}

	public static WebuiASIEditingInfo processParameterASI(
			final AttributeSetInstanceId attributeSetInstanceId,
			final DocumentPath documentPath)
	{
		final ASIEditingInfo info = ASIEditingInfo.builder()
				.type(WindowType.ProcessParameter)
				.soTrx(SOTrx.SALES)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		return builder(info)
				.contextDocumentPath(documentPath)
				.build();
	}

	@NonNull
	WindowType contextWindowType;
	DocumentPath contextDocumentPath;

	@NonNull
	AttributeSetId attributeSetId;
	String attributeSetName;
	String attributeSetDescription;

	@NonNull
	AttributeSetInstanceId attributeSetInstanceId;
	ProductId productId;
	@NonNull
	SOTrx soTrx;
	String callerTableName;
	int callerAdColumnId;

	@NonNull
	@Singular
	ImmutableList<I_M_Attribute> attributes;

	public ImmutableSet<AttributeId> getAttributeIds()
	{
		return attributes.stream()
				.map(a -> AttributeId.ofRepoId(a.getM_Attribute_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}
}
