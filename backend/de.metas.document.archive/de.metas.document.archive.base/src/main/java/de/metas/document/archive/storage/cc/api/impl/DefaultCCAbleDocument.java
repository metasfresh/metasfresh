package de.metas.document.archive.storage.cc.api.impl;

import lombok.NonNull;
import lombok.Value;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Env;

import de.metas.document.archive.storage.cc.api.ICCAbleDocument;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class DefaultCCAbleDocument implements ICCAbleDocument
{
	public static DefaultCCAbleDocument of(@NonNull final ITableRecordReference tableRecordReference)
	{
		StringBuilder sb = new StringBuilder();
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableRecordReference.getTableName());
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		if (Check.isEmpty(keyColumnName, true))
		{
			msgBL.translate(Env.getCtx(), tableRecordReference.getTableName());
			sb.append(tableRecordReference.getTableName());
		}
		else
		{
			Services.get(IMsgBL.class).translate(Env.getCtx(), keyColumnName);
		}

		return new DefaultCCAbleDocument(sb.append(".pdf").toString());
	}

	@NonNull
	String fileName;

}
