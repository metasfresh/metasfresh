package de.metas.ui.web.handlingunits.trace;

import org.springframework.stereotype.Component;

import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.window.datatypes.WindowId;
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
public class HUTraceSqlConverterDecorator implements SqlDocumentFilterConverterDecorator
{
	private final HUTraceRepository huTraceRepository;

	public HUTraceSqlConverterDecorator(final HUTraceRepository huTRaceRepository)
	{
		this.huTraceRepository = huTRaceRepository;
	}

	@Override
	public WindowId getWindowId()
	{
		return WEBUI_HU_Constants.WEBUI_HU_Trace_Window_ID;
	}

	public SqlDocumentFilterConverter decorate(@NonNull final SqlDocumentFilterConverter converter)
	{
		return HUTraceResultExtender.createForRepositoryAndconverter(huTraceRepository, converter);
	}
}
