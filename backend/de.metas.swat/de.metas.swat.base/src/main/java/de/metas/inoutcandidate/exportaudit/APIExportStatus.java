/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.inoutcandidate.exportaudit;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum APIExportStatus implements ReferenceListAwareEnum
{
	DontExport(X_M_ShipmentSchedule.EXPORTSTATUS_DONT_EXPORT),
	Pending(X_M_ShipmentSchedule.EXPORTSTATUS_PENDING),
	Exported(X_M_ShipmentSchedule.EXPORTSTATUS_EXPORTED),
	ExportError(X_M_ShipmentSchedule.EXPORTSTATUS_EXPORT_ERROR),
	ExportedAndForwarded(X_M_ShipmentSchedule.EXPORTSTATUS_EXPORTED_AND_FORWARDED),
	ExportedAndError(X_M_ShipmentSchedule.EXPORTSTATUS_EXPORTED_FORWARD_ERROR);

	public static final ImmutableSet<APIExportStatus> EXPORTED_STATES = ImmutableSet.of(Exported, ExportedAndError, ExportedAndForwarded);

	@Getter
	private final String code;

	APIExportStatus(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static APIExportStatus ofNullableCode(final String code)
	{
		return ofNullableCode(code, null);
	}

	@Nullable
	public static APIExportStatus ofNullableCode(@Nullable final String code, @Nullable final APIExportStatus fallbackValue)
	{
		return code != null ? ofCode(code) : fallbackValue;
	}

	public static APIExportStatus ofCode(@NonNull final String code)
	{
		final APIExportStatus type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + APIExportStatus.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static String toCodeOrNull(final APIExportStatus type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, APIExportStatus> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), APIExportStatus::getCode);
}
