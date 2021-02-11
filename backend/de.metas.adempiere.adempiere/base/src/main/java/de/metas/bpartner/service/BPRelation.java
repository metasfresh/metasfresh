package de.metas.bpartner.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_BP_Relation;

import javax.annotation.Nullable;
import java.util.Arrays;

import static de.metas.common.util.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class BPRelation
{
	@Nullable
	BPRelationId bpRelationId;

	@NonNull BPartnerId bpartnerId;

	@Nullable
	BPartnerLocationId bpLocationId;

	@NonNull BPartnerId targetBPartnerId;

	@NonNull
	BPartnerLocationId targetBPLocationId;

	@NonNull String name;

	@Nullable
	String description;

	@Nullable
	ExternalId externalId;

	@Nullable
	BPRelationRole role;

	@Nullable
	Boolean billTo;

	@Nullable
	Boolean fetchedFrom;

	@Nullable
	Boolean handoverLocation;

	@Nullable
	Boolean payFrom;

	@Nullable
	Boolean remitTo;

	@Nullable
	Boolean shipTo;

	@Nullable
	Boolean active;

	@Builder(toBuilder = true)
	public BPRelation(
			@Nullable final BPRelationId bpRelationId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpLocationId,
			@NonNull final BPartnerId targetBPartnerId,
			@NonNull final BPartnerLocationId targetBPLocationId,
			@NonNull final String name,
			@Nullable final String description,
			@Nullable final ExternalId externalId,
			@Nullable final BPRelationRole role,
			@Nullable final Boolean fetchedFrom,
			@Nullable final Boolean shipTo,
			@Nullable final Boolean billTo,
			@Nullable final Boolean payFrom,
			@Nullable final Boolean handoverLocation,
			@Nullable final Boolean remitTo,
			@Nullable final Boolean active)
	{
		this.bpRelationId = bpRelationId;
		this.bpartnerId = bpartnerId;
		this.bpLocationId = bpLocationId;
		this.targetBPartnerId = targetBPartnerId;
		this.targetBPLocationId = targetBPLocationId;
		this.name = name;
		this.description = description;
		this.externalId = externalId;
		this.role = role;
		this.fetchedFrom = fetchedFrom;
		this.shipTo = shipTo;
		this.billTo = billTo;
		this.payFrom = payFrom;
		this.handoverLocation = handoverLocation;
		this.remitTo = remitTo;
		this.active = coalesce(active, true);
	}

	public enum BPRelationRole implements ReferenceListAwareEnum
	{
		MainProducer(X_C_BP_Relation.ROLE_MainProducer),
		Hospital(X_C_BP_Relation.ROLE_Hostpital),
		PhysicianDoctor(X_C_BP_Relation.ROLE_PhysicianDoctor),
		GeneralPractitioner(X_C_BP_Relation.ROLE_GeneralPractitioner),
		HealthInsurance(X_C_BP_Relation.ROLE_HealthInsurance),
		NursingHome(X_C_BP_Relation.ROLE_NursingHome),
		Caregiver(X_C_BP_Relation.ROLE_Caregiver),
		PreferredPharmacy(X_C_BP_Relation.ROLE_PreferredPharmacy),
		NursingService(X_C_BP_Relation.ROLE_NursingService);

		@Getter
		private final String code;

		BPRelationRole(@NonNull final String code)
		{
			this.code = code;
		}

		@Nullable
		public static BPRelationRole ofNullableCode(final String code)
		{
			return code != null ? ofCode(code) : null;
		}

		public static BPRelationRole ofCode(@NonNull final String code)
		{
			final BPRelationRole type = typesByCode.get(code);
			if (type == null)
			{
				throw new AdempiereException("No " + BPRelationRole.class + " found for code: " + code);
			}
			return type;
		}

		@Nullable
		public static String toCodeOrNull(final BPRelationRole type)
		{
			return type != null ? type.getCode() : null;
		}

		private static final ImmutableMap<String, BPRelationRole> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), BPRelationRole::getCode);
	}
}
