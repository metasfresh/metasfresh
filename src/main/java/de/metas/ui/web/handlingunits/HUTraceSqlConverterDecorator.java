package de.metas.ui.web.handlingunits;

import org.springframework.stereotype.Component;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecoratorProvider;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.view.WindowSpecificSqlDocumentFilterConverterDecoratorProvider;
import de.metas.ui.web.window.datatypes.WindowId;
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

/**
 * Does nothing yet, but shall add to the "normal" result all connected HU-trace records
 * 
 * @author metas-dev <dev@metasfresh.com>
 * 
 */
@Component
public class HUTraceSqlConverterDecorator implements WindowSpecificSqlDocumentFilterConverterDecoratorProvider
{
	private static final HUTraceResultExtenderProvider INSTANCE = new HUTraceResultExtenderProvider();

	@Override
	public WindowId getWindowId()
	{
		return WEBUI_HU_Constants.WEBUI_HU_Trace_Window_ID;
	}

	@Override
	public SqlDocumentFilterConverterDecoratorProvider getConverter()
	{
		return INSTANCE;
	}

	public static class HUTraceResultExtenderProvider extends SqlDocumentFilterConverterDecoratorProvider
	{
		public SqlDocumentFilterConverter provideDecoratorFor(@NonNull final SqlDocumentFilterConverter converter)
		{
			return new HUTraceResultExtender(converter);
		}
	}

	public static class HUTraceResultExtender implements SqlDocumentFilterConverter
	{
		private final SqlDocumentFilterConverter converter;

		private HUTraceResultExtender(SqlDocumentFilterConverter converter)
		{
			this.converter = converter;
		}

		@Override
		public String getSql(SqlParamsCollector sqlParamsOut, DocumentFilter filter, SqlOptions sqlOpts)
		{
			return converter.getSql(sqlParamsOut, filter, sqlOpts);
		}
	}

}
