package de.metas.document.engine.impl;

import de.metas.monitoring.adapter.PerformanceMonitoringService;
import org.adempiere.ad.table.api.IADTableDAO;
import de.metas.document.engine.IDocument;
import de.metas.monitoring.adapter.PerformanceMonitoringService.SpanMetadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.Type;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business
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

@UtilityClass
public class DocactionAPMHelper
{
	public SpanMetadata createMetadataFor(@NonNull final IDocument document, @NonNull final String docAction)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(document.get_Table_ID());

		return SpanMetadata
				.builder()
				.name("DocAction - " + docAction + " " + tableName)
				.type(Type.DOC_ACTION.getCode())
				.subType(docAction)
				.label("tableName", tableName)
				.label(PerformanceMonitoringService.LABEL_RECORD_ID, Integer.toString(document.get_ID()))
				.build();
	}
}
