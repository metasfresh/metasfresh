package org.adempiere.db;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.ISingletonService;
import org.compiere.model.PO;

/**
 * 
 * @author ts
 * @deprecated use {@link IQueryBL}
 */
@Deprecated
public interface IDatabaseBL extends ISingletonService {

	/**
	 * A generic way of retrieving a list of {@link PO} instances from database.
	 * 
	 * @param <T>
	 *            the type of the list items (e.g. I_C_Order).
	 * @param sql
	 *            the query to execute as {@link PreparedStatement}. Note: The
	 *            ResultSet is passed to the PO constructor, so the query needs
	 *            to match the PO type <T>.
	 * @param params
	 *            Array of prepared statement parameters. May be empty, but not
	 *            null.
	 * @param clazz
	 *            the real po type (e.g. MOrder or X_C_Order).
	 * @param trxName
	 * @return
	 */
	<T extends PO> List<T> retrieveList(String sql, Object[] params, Class<T> clazz, String trxName);

	/**
	 * Similar to {@link #retrieveList(String, Object[], Class, String)}, but
	 * returns a map of the {@link PO}'s with their ids as keys.
	 * 
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param clazz
	 * @param trxName
	 * @return
	 */
	<T extends PO> Map<Integer, T> retrieveMap(String sql, Object[] params, Class<T> clazz, String trxName);
}
