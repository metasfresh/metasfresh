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

package de.metas.vertical.healthcare.alberta.bpartner.caregiver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.vertical.healthcare.alberta.model.X_C_BPartner_AlbertaCareGiver;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

@AllArgsConstructor
public enum CaregiverType implements ReferenceListAwareEnum
{
	Unknown(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Unknown),
	Ehemann(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Ehemann),
	Ehefrau(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Ehefrau),
	LebensgefaehrteIn(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_LebensgefaehrteIn),
	Sohn(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Sohn),
	Tochter(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Tochter),
	Enkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Enkel),
	Enkelin(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Enkelin),
	Urenkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Urenkel),
	Urenkelin(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Urenkelin),
	Mutter(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Mutter),
	Vater(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Vater),
	Onkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Onkel),
	Tante(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Tante),
	Nichte(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Nichte),
	Neffe(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Neffe),
	Grossonkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Grossonkel),
	Grosstante(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Grosstante),
	Grossnichte(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Grossnichte),
	Grossneffe(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Grossneffe),
	Schwester(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwester),
	Bruder(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Bruder),
	Cousin(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Cousin),
	Cousine(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Cousine),
	Schwager(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwager),
	Schwaegerin(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwaegerin),
	Schwagersbruder(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwagersbruder),
	Schwagersschwester(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwagersschwester),
	Schwiegermutter(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegermutter),
	Schwiegervater(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegervater),
	Schwiegeronkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegeronkel),
	Schwiegertante(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegertante),
	Schwiegerkind(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegerkind),
	Schwiegersohn(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegersohn),
	Schwiegertochter(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegertochter),
	Schwiegerenkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegerenkel),
	Schwiegerenkelin(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Schwiegerenkelin),
	Stiefmutter(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Stiefmutter),
	Stiefvater(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Stiefvater),
	Stiefschwester(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Stiefschwester),
	Stiefbruder(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Stiefbruder),
	Stieftochter(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Stieftochter),
	Stiefsohn(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Stiefsohn),
	Stiefenkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Stiefenkel),
	Stiefurenkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Stiefurenkel),
	Pflegesohn(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Pflegesohn),
	Pflegetochter(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Pflegetochter),
	Pflegemutter(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Pflegemutter),
	Pflegevater(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Pflegevater),
	Halbschwester(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Halbschwester),
	Halbbruder(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Halbbruder),
	Halbonkel(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Halbonkel),
	Halbtante(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Halbtante),
	Halbcousin(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Halbcousin),
	Halbcousine(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Halbcousine),
	Nachbarin(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Nachbarin),
	Nachbar(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Nachbar),
	Betreuungsbuero(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_Betreuungsbuero),
	FreundIn(X_C_BPartner_AlbertaCareGiver.TYPE_CONTACT_FreundIn),
	;

	private final String code;

	@Override
	public String getCode()
	{
		return code;
	}

	@Nullable
	public static CaregiverType ofCodeNullable(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}

		return ofCode(code);
	}

	@JsonCreator
	public static CaregiverType ofCode(@NonNull final String code)
	{
		final CaregiverType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + CaregiverType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, CaregiverType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), CaregiverType::getCode);
}
