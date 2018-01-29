package org.compiere.acct;

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


import org.adempiere.acct.api.IDocFactory;
import org.compiere.model.MAcctSchema;

/**
 * Helper class used to create a new instance of {@link Doc}.
 * 
 * @author tsa
 *
 */
public interface IDocBuilder
{
	/** @return {@link Doc} instance */ 
	Doc<?> build();

	MAcctSchema[] getAcctSchemas();

	IDocBuilder setAcctSchemas(MAcctSchema[] acctSchemas);

	Object getDocumentModel();

	IDocBuilder setDocumentModel(Object documentModel);

	IDocFactory getDocFactory();
}
