/**
 * 
 */
package de.metas.document.documentNo;

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


import org.adempiere.util.ISingletonService;

import de.metas.document.documentNo.impl.IPreliminaryDocumentNoBuilder;

/**
 * @author cg
 *
 */
public interface IDocumentNoBuilderFactory extends ISingletonService
{
	IPreliminaryDocumentNoBuilder createPreliminaryDocumentNoBuilder();
	
	/**
	 * Convenient method to create and prepare the builder for a given TableName.
	 *
	 * @param TableName
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @return builder
	 */
	IDocumentNoBuilder forTableName(final String TableName, final int AD_Client_ID, final int AD_Org_ID);
	

	/**
	 * Convenient method to create and prepare the builder for a given DocType.
	 *
	 * @param C_DocType_ID
	 * @param useDefiniteSequence
	 * @return builder
	 */
	IDocumentNoBuilder forDocType(final int C_DocType_ID, final boolean useDefiniteSequence);
	
	IDocumentNoBuilder createDocumentNoBuilder();
}
