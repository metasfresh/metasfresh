/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.config.mobileui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.ad_reference.ReferenceId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_AD_Reference_ID;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_Customer;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_DateReady;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_DeliveryAddress;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_DocumentNo;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_HandoverLocation;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_ProductName;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_ProductNo;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_QtyToDeliver;
import static de.metas.picking.model.X_PickingProfile_PickingJobConfig.PICKINGJOBFIELD_RuestplatzNr;

@AllArgsConstructor
@Getter
public enum PickingJobFieldType implements ReferenceListAwareEnum
{
	DOCUMENT_NO(PICKINGJOBFIELD_DocumentNo),
	CUSTOMER(PICKINGJOBFIELD_Customer),
	DELIVERY_ADDRESS(PICKINGJOBFIELD_DeliveryAddress),
	DATE_READY(PICKINGJOBFIELD_DateReady),
	HANDOVER_LOCATION(PICKINGJOBFIELD_HandoverLocation),
	RUESTPLATZ_NR(PICKINGJOBFIELD_RuestplatzNr),
	PRODUCT_NO(PICKINGJOBFIELD_ProductNo),
	PRODUCT_NAME(PICKINGJOBFIELD_ProductName),
	QTY_TO_DELIVER(PICKINGJOBFIELD_QtyToDeliver),
	;

	private static final ValuesIndex<PickingJobFieldType> index = ReferenceListAwareEnums.index(values());

	private static final ReferenceId PICKING_JOB_FIELD_REFERENCE_ID = ReferenceId.ofRepoId(PICKINGJOBFIELD_AD_Reference_ID);
	@NonNull private final String code;

	@NonNull
	public static PickingJobFieldType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@NonNull
	@JsonCreator
	public static PickingJobFieldType ofCodeOrName(@NonNull final String code) {return index.ofCodeOrName(code);}

	@JsonValue
	public String toJson() {return getCode();}

	public ITranslatableString getCaption() {return TranslatableStrings.adRefList(PickingJobFieldType.PICKING_JOB_FIELD_REFERENCE_ID, code);}

	public static boolean equals(@Nullable PickingJobFieldType type1, @Nullable PickingJobFieldType type2) {return Objects.equals(type1, type2);}
}
