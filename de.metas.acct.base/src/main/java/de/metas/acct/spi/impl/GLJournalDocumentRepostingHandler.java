package de.metas.acct.spi.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IGLJournalDAO;
import org.adempiere.util.Services;

import de.metas.acct.spi.IDocumentRepostingHandler;

/*
 * #%L
 * de.metas.acct.base
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
 * Document reposting handler for GL_Journal
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class GLJournalDocumentRepostingHandler implements IDocumentRepostingHandler
{

	@Override
	public List<?> retrievePostedWithoutFactAcct(Properties ctx, Timestamp startTime)
	{
		return Services.get(IGLJournalDAO.class).retrievePostedWithoutFactAcct(ctx, startTime);
	}

}
