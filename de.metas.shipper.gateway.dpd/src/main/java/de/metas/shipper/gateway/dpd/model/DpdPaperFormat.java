/*
 * #%L
 * de.metas.shipper.gateway.dpd
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.shipper.gateway.dpd.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.shipper.gateway.spi.model.PackageLabelType;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

public enum DpdPaperFormat implements PackageLabelType, ReferenceListAwareEnum
{
	PAPER_FORMAT_A6("A6"), // todo should not be hardcoded: add to shipper configuration
	PAPER_FORMAT_A5("A5"), // todo should not be hardcoded: add to shipper configuration
	PAPER_FORMAT_A4("A4"); // todo should not be hardcoded: add to shipper configuration

	@Getter
	private final String code;

	DpdPaperFormat(final String code)
	{
		this.code = code;
	}

	public static DpdPaperFormat ofCode(@NonNull final String code)
	{
		final DpdPaperFormat type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + DpdPaperFormat.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, DpdPaperFormat> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), DpdPaperFormat::getCode);

}
