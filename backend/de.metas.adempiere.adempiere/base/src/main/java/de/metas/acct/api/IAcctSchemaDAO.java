package de.metas.acct.api;

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

import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_AcctSchema_GL;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

public interface IAcctSchemaDAO extends ISingletonService
{

	AcctSchema getById(final AcctSchemaId acctSchemaId);

	/**
	 * Retrieves the accounting schema for the given context's <code>AD_Client_ID</code> and <code>AD_Org_ID</code>.<br>
	 * It returns the C_AcctSchema with the given AD_Client_ID and AD_OrgOnly_ID. Inactive records are ignored. If no C_AcctSchema record with the given AD_OrgOnly_ID exists, then it falls back and
	 * returns the record which is set in <code>AD_ClientInfo</code>.
	 * <p>
	 * IMPORTANT: Make sure that the C_AcctSchema referenced in AD_ClientInfo has AD_OrgOnly_ID=NULL (as of now, MAcctSchema.beforeSave() takes care of this).
	 * <p>
	 * Unit testing note: note preparing a plain implementation. Use jmockit to unit test.
	 */
	AcctSchema getByClientAndOrg(Properties ctx);

	/**
	 * Similar to {@link #getByClientAndOrg(Properties)}, but uses the given client and org ID rather than the ones of the given <code>ctx</code>.
	 */
	@NonNull
	AcctSchema getByClientAndOrg(ClientId clientId, OrgId orgId);

	@Nullable
	AcctSchema getByClientAndOrgOrNull(ClientId clientId, OrgId orgId);

	AcctSchemaId getAcctSchemaIdByClientAndOrg(ClientId clientId, OrgId orgId);

	/**
	 * Retrieves all accounting schemas for given AD_Client_ID.
	 * <p>
	 * if given AD_Client_ID is ZERO, all accounting schemas from all clients will be fetched.
	 *
	 * @param adClientId AD_Client_ID
	 * @return client accounting schemas
	 */
	List<AcctSchema> getAllByClient(ClientId adClientId);

	AcctSchemaId getPrimaryAcctSchemaId(ClientId clientId);

	@Deprecated
	I_C_AcctSchema getRecordById(AcctSchemaId acctSchemaId);

	I_C_AcctSchema_GL retrieveAcctSchemaGLRecordOrNull(AcctSchemaId acctSchemaId);

	List<AcctSchema> getByChartOfAccountsId(@NonNull ChartOfAccountsId chartOfAccountsId);

	I_C_AcctSchema_Default retrieveAcctSchemaDefaultsRecordOrNull(AcctSchemaId acctSchemaId);

	void changeAcctSchemaAutomaticPeriodId(AcctSchemaId acctSchemaId, int periodId);
}
