package de.metas.ui.web.handlingunits.trace;

import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUTraceResultExtender implements SqlDocumentFilterConverter
{
	private static final String WHERE_IN_T_SELECTION = "(M_HU_Trace_ID IN (select T_Selection_ID from T_Selection where AD_PInstance_ID=%s))";

	public static HUTraceResultExtender createForRepositoryAndconverter(
			@NonNull final HUTraceRepository huTraceRepository,
			@NonNull final SqlDocumentFilterConverter converter)
	{
		return new HUTraceResultExtender(huTraceRepository, converter);
	}

	private final HUTraceRepository huTraceRepository;
	private final SqlDocumentFilterConverter converter;

	private HUTraceResultExtender(
			@NonNull final HUTraceRepository huTraceRepository,
			@NonNull final SqlDocumentFilterConverter converter)
	{
		this.huTraceRepository = huTraceRepository;
		this.converter = converter;
	}

	@Override
	public String getSql(
			@NonNull final SqlParamsCollector sqlParamsOut,
			@NonNull final DocumentFilter filter,
			@NonNull final SqlOptions sqlOpts)
	{
		if (filter.getParameters() == null || filter.getParameters().isEmpty())
		{
			return converter.getSql(sqlParamsOut, filter, sqlOpts); // do whatever the system usually does
		}
		final HUTraceEventQuery huTraceQuery = HuTraceQueryCreator.createTraceQueryFromDocumentFilter(filter);
		final int selectionId = huTraceRepository.queryToSelection(huTraceQuery);

		final String sqlPlaceHolder = sqlParamsOut.placeholder(selectionId);
		return String.format(WHERE_IN_T_SELECTION, sqlPlaceHolder);
	}
}
