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

package de.metas.vertical.healthcare.alberta.bpartner.patient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.vertical.healthcare.alberta.model.X_C_BPartner_AlbertaPatient;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

public enum DeactivationReasonType implements ReferenceListAwareEnum
{
	Unknown(X_C_BPartner_AlbertaPatient.DEACTIVATIONREASON_Unbekannt),
	PatientDeceased(X_C_BPartner_AlbertaPatient.DEACTIVATIONREASON_PatientVerstorben),
	AllTherapiesEnded(X_C_BPartner_AlbertaPatient.DEACTIVATIONREASON_TherapieendeAlleTherapien),
	ChangeOfServiceProvider(X_C_BPartner_AlbertaPatient.DEACTIVATIONREASON_Leistungserbringerwechsel),
	Oher(X_C_BPartner_AlbertaPatient.DEACTIVATIONREASON_Sonstiges),
	;

	private final String code;

	private DeactivationReasonType(final String code)
	{
		this.code = code;
	}

	@Override
	public String getCode()
	{
		return code;
	}

	@JsonCreator
	public static DeactivationReasonType ofCode(@NonNull final String code)
	{
		DeactivationReasonType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + DeactivationReasonType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, DeactivationReasonType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), DeactivationReasonType::getCode);
}
