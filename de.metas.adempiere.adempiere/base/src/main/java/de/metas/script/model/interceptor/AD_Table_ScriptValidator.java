package de.metas.script.model.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table_ScriptValidator;
import org.compiere.model.ModelValidator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_AD_Table_ScriptValidator.class)
public class AD_Table_ScriptValidator
{
	public static final transient AD_Table_ScriptValidator instance = new AD_Table_ScriptValidator();

	private AD_Table_ScriptValidator()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_AD_Table_ScriptValidator validator)
	{
		if (validator.getSeqNo() == 0)
		{
			final Integer lastSeqNo = Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Table_ScriptValidator.class, validator)
					.addEqualsFilter(I_AD_Table_ScriptValidator.COLUMN_AD_Table_ID, validator.getAD_Table_ID())
					.addEqualsFilter(I_AD_Table_ScriptValidator.COLUMN_EventModelValidator, validator.getEventModelValidator())
					.create()
					.aggregate(I_AD_Table_ScriptValidator.COLUMN_SeqNo, IQuery.AGGREGATE_MAX, Integer.class);

			final int seqNo = (lastSeqNo == null ? 0 : lastSeqNo) + 10;
			validator.setSeqNo(seqNo);
		}
	}

}
