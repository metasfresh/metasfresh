/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.mapping;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_M_Shipper_Mapping_Config;

@RequiredArgsConstructor
public enum AttributeType implements ReferenceListAwareEnum
{
	// Keep in sync with de.metas.common.delivery.v1.json.DeliveryMappingConstants
	SENDER_ATTENTION(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTETYPE_SenderAttention),
	RECEIVER_ATTENTION(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTETYPE_ReceiverAttention),
	// CustNo mapping targets (address CustNo of sender / receiver). Value uses DeliveryMappingConstants, not a
	// generated X_ constant, to avoid a model regeneration — the code is defined once in DeliveryMappingConstants and
	// seeded as an AD_Ref_List value by migration 5819100.
	SENDER_CUSTNO(DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_CUSTNO),
	RECEIVER_CUSTNO(DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_CUSTNO),
	REFERENCE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTETYPE_Reference),
	LINE_REFERENCE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTETYPE_LineReference),
	LINE_DETAIL_GROUP(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTETYPE_LineDetailGroup),
	DETAIL_GROUP(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTETYPE_DetailGroup);


	private static final ReferenceListAwareEnums.ValuesIndex<AttributeType> index = ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@NonNull
	public static AttributeType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@JsonValue
	public String toJson() {return getCode();}
}
