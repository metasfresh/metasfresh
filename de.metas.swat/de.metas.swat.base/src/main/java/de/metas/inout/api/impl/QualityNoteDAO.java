package de.metas.inout.api.impl;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;

import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_QualityNote;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class QualityNoteDAO implements IQualityNoteDAO
{

	@Override
	public I_M_QualityNote retrieveQualityNoteForName(final Properties ctx, final String name)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_QualityNote.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_QualityNote.COLUMN_Name, name)
				.create()
				.firstOnly(I_M_QualityNote.class);
	}

}
