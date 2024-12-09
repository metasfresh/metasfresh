package de.metas.acct.gldistribution;

<<<<<<< HEAD
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_GL_Distribution;
import org.compiere.model.I_GL_DistributionLine;

import de.metas.acct.api.AccountDimension;
import de.metas.util.ISingletonService;
=======
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.PostingType;
import de.metas.document.DocTypeId;
import de.metas.util.ISingletonService;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.I_GL_DistributionLine;

import java.util.List;
import java.util.Properties;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IGLDistributionDAO extends ISingletonService
{
	/**
	 * Retrieves those {@link I_GL_Distribution}s which are matching the given criteria.
<<<<<<< HEAD
	 * 
	 * @param ctx
	 * @param dimension
	 * @param PostingType
	 * @param C_DocType_ID
	 * @return
	 */
	List<I_GL_Distribution> retrieve(Properties ctx, AccountDimension dimension, String PostingType, int C_DocType_ID);
=======
	 *
	 */
	List<I_GL_Distribution> retrieve(Properties ctx, AccountDimension dimension, PostingType PostingType, DocTypeId C_DocType_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Retrieves {@link I_GL_DistributionLine}s of given {@link I_GL_Distribution}.
	 * 
<<<<<<< HEAD
	 * @param glDistribution
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return active {@link I_GL_DistributionLine}s.
	 */
	List<I_GL_DistributionLine> retrieveLines(I_GL_Distribution glDistribution);

	/**
<<<<<<< HEAD
	 * @param glDistribution
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return last {@link I_GL_DistributionLine#getLine()} of given {@link I_GL_Distribution}; if there are no lines, this method returns zero.
	 */
	int retrieveLastLineNo(I_GL_Distribution glDistribution);
}
