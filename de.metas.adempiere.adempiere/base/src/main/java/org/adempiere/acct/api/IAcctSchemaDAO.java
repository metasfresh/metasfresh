package org.adempiere.acct.api;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.exception.AccountingException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.util.Env;

public interface IAcctSchemaDAO extends ISingletonService
{
	/**
	 * Retrieves the accounting schema for the given context's <code>AD_Client_ID</code> and <code>AD_Org_ID</code>.<br>
	 * It returns the C_AcctSchema with the given AD_Client_ID and AD_OrgOnly_ID. Inactive records are ignored. If no C_AcctSchema record with the given AD_OrgOnly_ID exists, then it falls back and
	 * returns the record which is set in <code>AD_ClientInfo</code>.
	 * <p>
	 * IMPORTANT: Make sure that the C_AcctSchema referenced in AD_ClientInfo has AD_OrgOnly_ID=NULL (as of now, MAcctSchema.beforeSave() takes care of this).
	 * 
	 * Unit testing note: note preparing a plain implementation. Use jmockit to unit test.
	 * 
	 * @param ctx
	 * @return
	 * @throws AccountingException if no accounting schema is found.
	 */
	I_C_AcctSchema retrieveAcctSchema(Properties ctx);
	
	I_C_AcctSchema retrieveAcctSchemaById(final int acctSchemaId);

	/**
	 * Similar to {@link #retrieveAcctSchema(Properties)}, but uses the given client and org ID rather than the ones of the given <code>ctx</code>.
	 * 
	 * @param ctx
	 * @param ad_Client_ID
	 * @param ad_Org_ID
	 * @return
	 */
	I_C_AcctSchema retrieveAcctSchema(Properties ctx, int ad_Client_ID, int ad_Org_ID);

	/**
	 * Retrieves all accounting schemas for given AD_Client_ID.
	 * 
	 * if given AD_Client_ID is ZERO, all accountign schemas from all clients will be fetched.
	 * 
	 * @param ctx
	 * @param adClientId AD_Client_ID
	 * @return client accounting schemas
	 */
	List<I_C_AcctSchema> retrieveClientAcctSchemas(Properties ctx, int adClientId);
	
	default List<I_C_AcctSchema> retrieveClientAcctSchemas(final int adClientId)
	{
		return retrieveClientAcctSchemas(Env.getCtx(), adClientId);
	}


	/**
	 * Retrieve {@link I_C_AcctSchema_Element}s, ordered by SeqNo.
	 * 
	 * @param aSchema
	 * @return
	 */
	List<I_C_AcctSchema_Element> retrieveSchemaElements(I_C_AcctSchema aSchema);

	/**
	 * Retrieve {@link I_C_AcctSchema_Element}s which have {@link I_C_AcctSchema_Element#isDisplayInEditor()}, ordered by SeqNo.
	 * 
	 * @param aSchema
	 * @return
	 */
	List<I_C_AcctSchema_Element> retrieveSchemaElementsDisplayedInEditor(I_C_AcctSchema as);

	I_C_AcctSchema_Element retrieveFirstAcctSchemaElementOrNull(I_C_AcctSchema as, String elementType);

	I_C_AcctSchema_GL retrieveAcctSchemaGL(Properties ctx, int acctSchemaId);
}
