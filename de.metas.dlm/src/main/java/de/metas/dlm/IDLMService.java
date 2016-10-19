package de.metas.dlm;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Column;

import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;

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

public interface IDLMService extends ISingletonService
{
	/**
	 * Call the DB function <code>dlm.add_table_to_dlm()</code> with the given <code>table</code>'s name
	 * and create new {@link I_AD_Column}s for the columns declared by {@link IDLMAware} if necessary.
	 *
	 * @param table
	 */
	void addTableToDLM(final I_AD_Table table);

	/**
	 * Call the DB function <code>dlm.remove_table_from_dlm()</code> with the given <code>table</code>'s name
	 * and remove the {@link I_AD_Column}s for the columns declared by {@link IDLMAware}.
	 * <p>
	 * As of now, don't drop the physical columns from the table.
	 *
	 * @param table
	 */
	void removeTableFromDLM(final I_AD_Table table);
}
