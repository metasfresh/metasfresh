package de.metas.process;

import com.google.common.collect.ImmutableSet;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Process;

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
 */
@AllArgsConstructor
public enum ProcessType implements ReferenceListAwareEnum
{
	Java(X_AD_Process.TYPE_Java),
	SQL(X_AD_Process.TYPE_SQL),
	POSTGREST(X_AD_Process.TYPE_PostgREST),
	JasperReportsJSON(X_AD_Process.TYPE_JasperReportsJSON),
	JasperReportsSQL(X_AD_Process.TYPE_JasperReportsSQL),
	Excel(X_AD_Process.TYPE_Excel),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ProcessType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	public static ProcessType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public boolean isJasper()
	{
		return this == JasperReportsJSON || this == JasperReportsSQL;
	}

	public boolean isJasperJSON()
	{
		return this == JasperReportsJSON;
	}

	public static ImmutableSet<ProcessType> getTypesRunnableFromAppRestController()
	{
		return ImmutableSet.of(SQL, Excel, POSTGREST);
	}

}
