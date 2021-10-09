/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.kpi.descriptor;

import de.metas.ui.web.base.model.X_WEBUI_KPI;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum KPIDatasourceType implements ReferenceListAwareEnum
{
	ELASTICSEARCH(X_WEBUI_KPI.KPI_DATASOURCE_TYPE_Elasticsearch),
	SQL(X_WEBUI_KPI.KPI_DATASOURCE_TYPE_SQL);

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<KPIDatasourceType> index = ReferenceListAwareEnums.index(values());

	public static KPIDatasourceType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static KPIDatasourceType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
}
