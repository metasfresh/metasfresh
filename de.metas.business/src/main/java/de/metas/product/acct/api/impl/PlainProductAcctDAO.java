package de.metas.product.acct.api.impl;

import org.adempiere.model.IContextAware;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.business
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

public class PlainProductAcctDAO extends ProductAcctDAO
{

	private static final Logger logger = LogManager.getLogger(PlainProductAcctDAO.class);

	/**
	 * @return always {@code null}.
	 */
	@Override
	public I_C_Activity retrieveActivityForAcct(final IContextAware contextProvider, final I_AD_Org org, final I_M_Product product)
	{
		logger.info("PlainProductAcctDAO.retrieveActivityForAcct always returns null");

		// skip this because:
		// 1. retrieving acct schema calls database
		// 2. we don't have an acct schema defined anyway
		return null;
	}
}
