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
import java.util.Properties;

import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_DocType;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

public interface IDocTypeDAO extends ISingletonService
{
	I_C_DocType getById(int docTypeId);
	
	/**
	 * @return C_DocType_ID or -1 if not found
	 */
	int getDocTypeIdOrNull(final DocTypeQuery query);

	/**
	 * @return C_DocType_ID
	 * @throws DocTypeNotFoundException if no document type was found
	 */
	@Deprecated
	int getDocTypeId(Properties ctx, String docBaseType, int adClientId, int adOrgId, String trxName);

	int getDocTypeId(DocTypeQuery query);

	/**
	 *
	 * @param ctx
	 * @param docBaseType
	 * @param docSubType doc sub type or {@link #DOCSUBTYPE_Any}.
	 * @param adClientId
	 * @param adOrgId
	 * @param trxName
	 * @return C_DocType_ID
	 * @throws DocTypeNotFoundException if no document type was found
	 */
	@Deprecated
	int getDocTypeId(Properties ctx, String docBaseType, String docSubType, int adClientId, int adOrgId, String trxName);

	@Deprecated
	I_C_DocType getDocType(String docBaseType, String docSubType, int adClientId, int adOrgId);

	I_C_DocType getDocTypeOrNull(DocTypeQuery query);

	/**
	 * Retrieve all the doc types of a certain base type as a list
	 *
	 * @param query
	 * @return a list of docTypes never <code>null</code>. Those with <code>IsDefault</code> and with <code>AD_Org_ID > 0</code> will be first in the list.
	 */
	List<I_C_DocType> retrieveDocTypesByBaseType(DocTypeQuery query);

	/**
	 * Retrieve the Counter_DocBaseType that fits the given DocBaseType.
	 * 
	 * @param ctx
	 * @param docBaseType
	 * @return
	 */
	String retrieveDocBaseTypeCounter(Properties ctx, String docBaseType);

	I_C_DocType createDocType(DocTypeCreateRequest request);

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
}
