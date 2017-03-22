package org.adempiere.ad.table.exception;

import org.adempiere.ad.table.process.AD_Table_CreatePK;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.StringUtils;
import org.compiere.model.POInfo;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Indicate that a particular table has move or less than one key columns. This is often a problem.
 *
 * Note: if this exception occurs, one might be able to run {@link AD_Table_CreatePK} to fix it.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class NoSingleKeyColumnException extends AdempiereException
{
	private static final long serialVersionUID = 2334933752321535129L;

	/**
	 *
	 * @param poInfo the info isntance for the affected table. Used to create the exception's message.
	 */
	public NoSingleKeyColumnException(final POInfo poInfo)
	{
		super(StringUtils.formatMessage("Table={} (AD_Table_ID={}) has these {} key columns: {}; it shall have exactly one; Consider running the process {} to fix it.",
				poInfo.getTableName(), poInfo.getAD_Table_ID(), poInfo.getKeyColumnNames().size(), poInfo.getKeyColumnNames(), AD_Table_CreatePK.class.getName()));
	}
}
