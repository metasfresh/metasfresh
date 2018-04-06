package org.adempiere.acct.api.impl;

import java.util.Properties;

import org.compiere.model.I_C_AcctSchema;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class PlainAcctSchemaDAO extends AcctSchemaDAO
{
	/**
	 * @return {@code null}. If you need to test code that relies in this method returning not-null,
	 *         the current practice is to register an anonymous subclass of {@link AcctSchemaDAO}.
	 */
	@Override
	public I_C_AcctSchema retrieveAcctSchema(final Properties ctx, final int ad_Client_ID, final int ad_Org_ID)
	{
		return null;
	}
}
