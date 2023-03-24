/*
 * #%L
 * de.metas.swat.base
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

package de.metas.product.quality.attribute;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.common.product.v2.JsonQualityAttribute;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_M_Quality_Attribute;

import javax.annotation.Nullable;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum QualityAttributeLabel implements ReferenceListAwareEnum
{
	BIO(X_M_Quality_Attribute.QUALITYATTRIBUTE_BearbeitetBio, JsonQualityAttribute.BIO),
	HALAL(X_M_Quality_Attribute.QUALITYATTRIBUTE_Halal, JsonQualityAttribute.HALAL),
	EU_AGRICULTURE(X_M_Quality_Attribute.QUALITYATTRIBUTE_EU_Landwirtschaft, JsonQualityAttribute.EU_AGRICULTURE),
	NON_EU_AGRICULTURE(X_M_Quality_Attribute.QUALITYATTRIBUTE_Nicht_EU_Landwirtschaft, JsonQualityAttribute.NON_EU_AGRICULTURE),
	EU_NON_EU_AGRICULTURE(X_M_Quality_Attribute.QUALITYATTRIBUTE_EU_Nicht_EU_Landwirtschaft, JsonQualityAttribute.EU_NON_EU_AGRICULTURE),
	ALLERGENE_ALBA(X_M_Quality_Attribute.QUALITYATTRIBUTE_AllergeneALBA, null),
	ALLERGENE_EU(X_M_Quality_Attribute.QUALITYATTRIBUTE_AllergeneEU, null),
	REFRIGERATED(X_M_Quality_Attribute.QUALITYATTRIBUTE_Gekuehlt, null),
	SIFTED(X_M_Quality_Attribute.QUALITYATTRIBUTE_Gesiebt, null),
	GERM_REDUCED(X_M_Quality_Attribute.QUALITYATTRIBUTE_Keimreduziert, null),
	KOSHER(X_M_Quality_Attribute.QUALITYATTRIBUTE_Kosher, null),
	METAL_DETECTED(X_M_Quality_Attribute.QUALITYATTRIBUTE_Metalldetektiert, null),
	NOT_GERM_REDUCED(X_M_Quality_Attribute.QUALITYATTRIBUTE_NichtKeimreduziert, null),
	NON_PORK_FREE(X_M_Quality_Attribute.QUALITYATTRIBUTE_NonPork_Free, null),
	OIP(X_M_Quality_Attribute.QUALITYATTRIBUTE_OIP, null),
	PORK_FREE(X_M_Quality_Attribute.QUALITYATTRIBUTE_Pork_Free, null),
	V_LABEL(X_M_Quality_Attribute.QUALITYATTRIBUTE_V_Label, null),
	VLOG(X_M_Quality_Attribute.QUALITYATTRIBUTE_VLOG, null);

	@NonNull
	private final String code;

	@Nullable
	private final JsonQualityAttribute jsonAttribute;

	@NonNull
	public static QualityAttributeLabel ofCode(@NonNull final String code)
	{
		final QualityAttributeLabel type = labelsByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + QualityAttributeLabel.class + " found for code: " + code);
		}
		return type;
	}

	@NonNull
	public static QualityAttributeLabel ofJson(@NonNull final JsonQualityAttribute jsonQualityAttribute)
	{
		final QualityAttributeLabel type = labelsByJson.get(jsonQualityAttribute);

		if (type == null)
		{
			throw new AdempiereException("No " + QualityAttributeLabel.class + " found for JsonQualityAttributeLabel: " + jsonQualityAttribute);
		}
		return type;
	}

	private static final ImmutableMap<String, QualityAttributeLabel> labelsByCode = Maps.uniqueIndex(Arrays.asList(values()), QualityAttributeLabel::getCode);
	private static final ImmutableMap<JsonQualityAttribute, QualityAttributeLabel> labelsByJson = Maps.uniqueIndex(Arrays.stream(values())
																														   .filter(attribute -> attribute.getJsonAttribute() != null)
																														   .iterator(),
																												   QualityAttributeLabel::getJsonAttribute);
}
