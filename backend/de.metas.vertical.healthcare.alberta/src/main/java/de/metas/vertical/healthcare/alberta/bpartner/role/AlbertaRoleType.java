/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.bpartner.role;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.vertical.healthcare.alberta.model.X_C_BPartner_AlbertaRole;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

@AllArgsConstructor
public enum AlbertaRoleType implements ReferenceListAwareEnum
{
	Caregiver(X_C_BPartner_AlbertaRole.ALBERTAROLE_Caregiver),
	CareTaker(X_C_BPartner_AlbertaRole.ALBERTAROLE_Caretaker),
	GeneralPractitioner(X_C_BPartner_AlbertaRole.ALBERTAROLE_GeneralPractitioner),
	HealthInsurance(X_C_BPartner_AlbertaRole.ALBERTAROLE_HealthInsurance),
	Hospital(X_C_BPartner_AlbertaRole.ALBERTAROLE_Hostpital),
	MainProducer(X_C_BPartner_AlbertaRole.ALBERTAROLE_MainProducer),
	NursingHome(X_C_BPartner_AlbertaRole.ALBERTAROLE_NursingHome),
	NursingService(X_C_BPartner_AlbertaRole.ALBERTAROLE_NursingService),
	Payer(X_C_BPartner_AlbertaRole.ALBERTAROLE_Payer),
	PhysicianDoctor(X_C_BPartner_AlbertaRole.ALBERTAROLE_Doctor),
	Pharmacy(X_C_BPartner_AlbertaRole.ALBERTAROLE_Pharmacy),
	PreferredPharmacy(X_C_BPartner_AlbertaRole.ALBERTAROLE_PreferredPharmacy),
	Patient(X_C_BPartner_AlbertaRole.ALBERTAROLE_Pacient);

	private final String code;

	@Override
	public String getCode()
	{
		return code;
	}

	@Nullable
	public static AlbertaRoleType ofCodeNullable(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}

		return ofCode(code);
	}

	@JsonCreator
	public static AlbertaRoleType ofCode(@NonNull final String code)
	{
		final AlbertaRoleType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + AlbertaRoleType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, AlbertaRoleType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), AlbertaRoleType::getCode);
}
