package org.adempiere.ad.service;

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

<<<<<<< HEAD
import java.util.Properties;

import org.compiere.model.I_AD_Sequence;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.ISingletonService;
=======
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_AD_Sequence;

import java.util.Properties;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/**
 * Service to access the actual {@link I_AD_Sequence} table. To get actual sequence numbers, use {@link IDocumentNoBuilderFactory}.
 *
 */
public interface ISequenceDAO extends ISingletonService
{
	I_AD_Sequence retrieveTableSequenceOrNull(final Properties ctx, final String tableName, final String trxName);

	I_AD_Sequence retrieveTableSequenceOrNull(final Properties ctx, final String tableName);

	ITableSequenceChecker createTableSequenceChecker(Properties ctx);

	/**
	 * Rename the sequence name when a given table name was changed
	 */
	void renameTableSequence(Properties ctx, String tableNameOld, String tableNameNew);
<<<<<<< HEAD
=======

	@NonNull
	I_AD_Sequence retrieveSequenceByName(@NonNull String sequenceName);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
