/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.cockpit.view.ddorderdetail;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.material.cockpit.model.X_MD_Cockpit_DDOrder_Detail;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;
import java.util.Optional;

public enum DDOrderDetailType
{
	DEMAND(X_MD_Cockpit_DDOrder_Detail.DDORDERDETAILTYPE_GeplAbgang),
	SUPPLY(X_MD_Cockpit_DDOrder_Detail.DDORDERDETAILTYPE_GeplZugang);

	@Getter
	private final String code;

	DDOrderDetailType(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	public static DDOrderDetailType ofCode(@NonNull final String code)
	{
		return Optional.ofNullable(typesByCode.get(code))
				.orElseThrow(() -> new AdempiereException("Unsupported detailType!"));
	}

	private static final ImmutableMap<String, DDOrderDetailType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), DDOrderDetailType::getCode);
}
