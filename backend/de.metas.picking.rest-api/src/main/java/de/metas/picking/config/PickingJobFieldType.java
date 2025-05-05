/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.config;

import com.google.common.collect.ImmutableMap;
import de.metas.ad_reference.ReferenceId;
import de.metas.common.util.Check;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
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
	;

	private static final ReferenceId PICKING_JOB_FIELD_REFERENCE_ID = ReferenceId.ofRepoId(PICKINGJOBFIELD_AD_Reference_ID);

	private final String code;

	@NonNull
	public static PickingJobFieldType ofCode(@NonNull final String code)
	{
		return Check.assumeNotNull(typesByCode.get(code), "No Type found for code=" + code);
	}

	private static final ImmutableMap<String, PickingJobFieldType> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	public ITranslatableString getCaption()
	{
		return TranslatableStrings.adRefList(PickingJobFieldType.PICKING_JOB_FIELD_REFERENCE_ID, code);
	}

	public static boolean equals(@Nullable PickingJobFieldType type1, @Nullable PickingJobFieldType type2)
	{
		return Objects.equals(type1, type2);
	}
}
