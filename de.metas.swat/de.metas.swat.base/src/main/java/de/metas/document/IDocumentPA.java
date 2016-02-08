package de.metas.document;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_DocType;

public interface IDocumentPA extends ISingletonService
{
	/**
	 * 
	 * @param docTypeId
	 * @param trxName
	 * @return something like {@link X_C_DocType#DOCBASETYPE_MaterialDelivery}
	 */
	String retrieveDocBaseType(int docTypeId, String trxName);

	int retriveDocTypeId(Properties ctx, int adOrgId, String docBaseType);
	/**
	 * Returns a C_DocType_ID for a given DocSubType
	 *   
	 * @param ctx
	 * @param docBaseType
	 * @param docSubType
	 * @return
	 */
	int retriveDocTypeIdForDocSubtype(Properties ctx, String docBaseType, String docSubType);

	/**
	 * Returns a C_DocType that has the given attributes or <code>null</code> (if throwEx is false).
	 * 
	 * @param ctx
	 * @param adOrgId
	 *            Optional. If not given (='0') and a C_DocType with AD_Org_ID=0 exists, that C_DocType is returned. If
	 *            a value is given, a C_DocType with AD_Org_ID=0 may still be returned, if no other C_DocType with the
	 *            given AD_Org_ID exists.
	 * @param docBaseType
	 * @param docSubType
	 * @param throwEx
	 *            if true and there is no C_DocType for the given parameters, the method throw an ADempiereException.
	 *            Otherwise it returns <code>null</code>.
	 * @param trxName
	 * @return
	 */
	I_C_DocType retrieve(Properties ctx, int adOrgId, String docBaseType, String docSubType, boolean throwEx, String trxName);

	/**
	 * 
	 * @param ctx
	 * @param adOrgId
	 * @param docBaseType
	 * @param docSubType
	 * @param trxName
	 * @return
	 * @deprecated Backward compatibility. Please use {@link #retrieve(Properties, int, String, String, boolean, String)} (throwEx=true).
	 */
	@Deprecated
	I_C_DocType retrieve(Properties ctx, int adOrgId, String docBaseType, String docSubType, String trxName);

	/**
	 * Updates the BPartnerAddress and BillToAddress columns.
	 * values of all {@link I_C_Order} entries that use the given id and don'T use a custom address.
	 * 
	 * @param bPArtnerLocationId
	 * @param trxName
	 * 
	 * @return the number of updated documents
	 */
	int updateDocumentAddresses(int bPartnerLocationId, String trxName);

	/**
	 * Creates a new C_DocType with the given parameters. Note that the new doctype is not saved.
	 * 
	 * @param ctx
	 * @param entityType
	 * @param Name
	 * @param PrintName
	 * @param DocBaseType
	 * @param DocSubType
	 * @param C_DocTypeShipment_ID
	 * @param C_DocTypeInvoice_ID
	 * @param StartNo
	 * @param GL_Category_ID
	 * @param trxName
	 * @return
	 */
	I_C_DocType createDocType(
			Properties ctx, String entityType, String Name, String PrintName, String DocBaseType, String DocSubType,
			int C_DocTypeShipment_ID, int C_DocTypeInvoice_ID, int StartNo, int GL_Category_ID, String trxName);
}
