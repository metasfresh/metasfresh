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

public interface IDocTypeDAO extends ISingletonService
{
	String DOCSUBTYPE_Any = null;

	/**
	 *
	 * @param ctx
	 * @param docBaseType
	 * @param adClientId
	 * @param adOrgId
	 * @param trxName
	 * @return C_DocType_ID or -1 if not found
	 */
	int getDocTypeIdOrNull(Properties ctx, String docBaseType, int adClientId, int adOrgId, String trxName);

	/**
	 *
	 * @param ctx
	 * @param docBaseType
	 * @param adClientId
	 * @param adOrgId
	 * @param trxName
	 * @return C_DocType_ID
	 * @throws DocTypeNotFoundException if no document type was found
	 */
	int getDocTypeId(Properties ctx, String docBaseType, int adClientId, int adOrgId, String trxName);

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
	int getDocTypeId(Properties ctx, String docBaseType, String docSubType, int adClientId, int adOrgId, String trxName);

	I_C_DocType getDocTypeOrNull(Properties ctx, String docBaseType, int adClientId, int adOrgId, String trxName);

	I_C_DocType getDocTypeOrNull(Properties ctx, String docBaseType, String docSubType, int adClientId, int adOrgId, String trxName);

	/**
	 * Retrieve all the doc types of a certain base type as a list
	 *
	 * @param ctx
	 * @param docBaseType
	 * @param adClientId only DocTypes with the given <code>AD_Client_ID</code> will be returned
	 * @param adOrgId only DocTypes with the given <code>AD_Org_ID</code> or <code>AD_Org_ID==0</code> will be returned
	 * @param trxName
	 * @return a list of docTypes never <code>null</code>. Those with <code>IsDefault</code> and with <code>AD_Org_ID > 0</code> will be first in the list.
	 */
	List<I_C_DocType> retrieveDocTypesByBaseType(Properties ctx, String docBaseType, int adClientId, int adOrgId, String trxName);

	/**
	 * Retrieve the Counter_DocBaseType that fits the given DocBaseType.
	 * 
	 * @param ctx
	 * @param docBaseType
	 * @return
	 */
	String retrieveDocBaseTypeCounter(Properties ctx, String docBaseType);
}
