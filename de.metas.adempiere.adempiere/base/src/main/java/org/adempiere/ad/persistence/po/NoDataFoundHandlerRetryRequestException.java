package org.adempiere.ad.persistence.po;

import org.adempiere.exceptions.AdempiereException;

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
 * Can be thrown to by a caller of {@link NoDataFoundHandlers#invokeHandlers(String, Object[], org.adempiere.model.IContextAware)},<br>
 * if the {@code invokeHandlers()} method returned @{code true}, but the code in change of the retry is somehwere else in the call stack.
 * 
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class NoDataFoundHandlerRetryRequestException extends AdempiereException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 273568070034884347L;

}
