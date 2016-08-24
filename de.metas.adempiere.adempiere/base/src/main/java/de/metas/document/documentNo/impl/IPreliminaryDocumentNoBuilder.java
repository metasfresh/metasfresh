package de.metas.document.documentNo.impl;

import org.compiere.model.I_C_DocType;

import de.metas.document.DocumentNoBuilderException;
import de.metas.document.documentNo.IDocumentNoBuilder;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Builder used to fetch preliminary document no informations.
 * 
 * Compared with {@link IDocumentNoBuilder} this builder is fetching current document sequence number, <b>without</b> incrementing it.
 * 
 * It's main purpose is to be used in callouts.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPreliminaryDocumentNoBuilder
{
	IDocumentNoInfo buildOrNull() throws DocumentNoBuilderException;

	IPreliminaryDocumentNoBuilder setNewDocType(I_C_DocType newDocType);

	IPreliminaryDocumentNoBuilder setOldDocType_ID(int oldDocType_ID);

	IPreliminaryDocumentNoBuilder setOldDocumentNo(String oldDocumentNo);

	IPreliminaryDocumentNoBuilder setDocumentModel(Object documentModel);
}
