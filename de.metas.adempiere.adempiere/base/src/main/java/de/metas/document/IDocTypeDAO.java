package de.metas.document;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.exceptions.DocTypeNotFoundException;
import org.compiere.model.I_C_DocType;

import de.metas.document.engine.IDocumentBL;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

public interface IDocTypeDAO extends ISingletonService
{
	I_C_DocType getById(int docTypeId);

	I_C_DocType getById(DocTypeId docTypeId);

	/**
	 * @return C_DocType_ID or <code>null</code> if not found
	 */
	DocTypeId getDocTypeIdOrNull(final DocTypeQuery query);

	DocTypeId getDocTypeId(DocTypeQuery query) throws DocTypeNotFoundException;

	Optional<I_C_DocType> retrieveDocType(DocTypeQuery docTypeQuery);

	/**
	 * Returns {@code true} if the given {@code documentModel}'s {@link IDocumentBL#COLUMNNAME_C_DocType_ID} value
	 * is one of the ID that are matched by the given {@code docTypeQuery}.
	 */
	boolean queryMatchesDocTypeId(DocTypeQuery docTypeQuery, int docTypeId);

	/**
	 * Retrieve all the doc types of a certain base type as a list
	 *
	 * @param query
	 * @return a list of docTypes never <code>null</code>. Those with <code>IsDefault</code> and with <code>AD_Org_ID > 0</code> will be first in the list.
	 */
	List<I_C_DocType> retrieveDocTypesByBaseType(DocTypeQuery query);

	/**
	 * Retrieve the Counter_DocBaseType that fits the given DocBaseType.
	 */
	Optional<String> getDocBaseTypeCounter(String docBaseType);

	DocTypeId createDocType(DocTypeCreateRequest request);

	@Value
	@Builder
	public static final class DocTypeCreateRequest
	{
		@NonNull
		final Properties ctx;
		@Default
		final int adOrgId = -1;
		final String entityType;
		@NonNull
		final String name;
		final String printName;
		@NonNull
		final String docBaseType;
		final String docSubType;
		final Boolean isSOTrx;
		final int docTypeShipmentId;
		final int docTypeInvoiceId;
		final int glCategoryId;

		final int docNoSequenceId;
		final int newDocNoSequenceStartNo;

		final int documentCopies;
	}

	DocBaseAndSubType getDocBaseAndSubTypeById(DocTypeId docTypeId);
}
