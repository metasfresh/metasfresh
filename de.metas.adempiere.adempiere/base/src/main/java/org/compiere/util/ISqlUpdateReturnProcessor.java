package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.ResultSet;
import java.sql.SQLException;

import org.compiere.model.PO;

/**
 * Used in conjunction with sql <code>RETURNING</code> dml statements.
 * And implementor can be passed to {@link DB#executeUpdate(String, Object[], org.compiere.util.DB.OnFail, String, int, ISqlUpdateReturnProcessor)}.<br>
 * The implementors job is to process the returned values.
 * <p>
 * Hint: {@link PO} contains an inner class that implements this interface.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ISqlUpdateReturnProcessor
{
	void process(ResultSet rs) throws SQLException;
}
