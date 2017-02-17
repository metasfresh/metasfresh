package org.adempiere.ad.persistence.po;

import org.adempiere.model.IContextAware;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Implementors are called by {@link org.compiere.model.PO} if the PO can't load a record for a certain table name using certain keys.
 * To make it happen, register your implementor using {@link NoDataFoundHandlers#addHandler(INoDataFoundHandler)}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface INoDataFoundHandler
{
	/**
	 * 
	 * @param tableName the name of the table (or view!) the PO tried to load from.
	 * @param ids the primary keys the PO tries to load with. In <i>most</i> cases, these IDs are integers.
	 * @param ctx
	 * 
	 * @return {@code true} if something was done to fix the situation. The PO will retry if at least one of the registered handlers returned {@code true}.<br>
	 * 		Note: it might be OK to have a handler that never returns {@code true}, but e.g. does logging etc
	 * 
	 */
	boolean invoke(String tableName, Object[] ids, IContextAware ctx);
}
