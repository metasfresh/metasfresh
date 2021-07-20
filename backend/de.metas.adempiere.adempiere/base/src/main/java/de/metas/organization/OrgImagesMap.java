/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.organization;

import com.google.common.collect.ImmutableMap;
import de.metas.image.AdImageId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.I_AD_OrgInfo;

import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class OrgImagesMap
{
	private final ImmutableMap<String, AdImageId> imageIdsByColumnName;

	private OrgImagesMap(@NonNull final Map<String, AdImageId> imageIdsByColumnName)
	{
		this.imageIdsByColumnName = ImmutableMap.copyOf(imageIdsByColumnName);
	}

	public static OrgImagesMap ofImageIdsByColumnName(@NonNull final Map<String, AdImageId> imageIdsByColumnName)
	{
		return new OrgImagesMap(imageIdsByColumnName);
	}

	public Optional<AdImageId> getLogoId()
	{
		return getImageId(I_AD_OrgInfo.COLUMNNAME_Logo_ID);
	}

	public Optional<AdImageId> getImageId(@NonNull final String columnName)
	{
		return Optional.ofNullable(imageIdsByColumnName.get(columnName));
	}
}
