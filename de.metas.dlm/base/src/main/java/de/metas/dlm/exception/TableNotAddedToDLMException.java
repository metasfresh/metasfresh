package de.metas.dlm.exception;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.StringUtils;
import org.compiere.model.I_AD_Table;

import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest;

/*
 * #%L
 * metasfresh-dlm
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
 * Can be thrown if a table is not (yet) ready for DLM. See {@link CreatePartitionRequest.OnNotDLMTable#FAIL}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class TableNotAddedToDLMException extends AdempiereException
{
	private static final long serialVersionUID = -8314830541276513678L;

	/**
	 *
	 * @param table the table in question. May not be <code>null</code>.
	 */
	public TableNotAddedToDLMException(final I_AD_Table table)
	{
		super(StringUtils.formatMessage("Table {} (AD_Table_ID={}) has not yet been added to DLM.", table.getTableName(), table.getAD_Table_ID()));
	}
}
