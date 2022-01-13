/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.acct.model.validator;

import ch.qos.logback.classic.Level;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.common.util.EmptyUtil;
import de.metas.document.sequence.SequenceUtil;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner.class)
@Component
public class C_BPartner
{
	private final static transient Logger logger = LogManager.getLogger(C_BPartner.class);

	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_BPartner.COLUMNNAME_Value, I_C_BPartner.COLUMNNAME_AD_Org_ID })
	public void beforeSave(final I_C_BPartner bpartner)
	{
		final ClientId clientId = ClientId.ofRepoId(bpartner.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoIdOrAny(bpartner.getAD_Org_ID());

		if(EmptyUtil.isBlank(bpartner.getValue()))
		{
			// we need a value for the debitor and creditor IDs; 
			// if we don't set it here, then org.compiere.model.PO#saveNew would set it anyways
			final String value = SequenceUtil.createValueFor(bpartner);
			bpartner.setValue(value);
			logger.debug("On-the-fly created C_BPartner.Value={}", value);
		}
		
		final AcctSchema as = acctSchemaDAO.getByClientAndOrgOrNull(clientId, orgId);
		if (as == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					"Found no AcctSchema for AD_Client_ID={} and AD_Org_ID={}; -> can't update debitorId and creditorId of C_BPartner_ID={}",
					bpartner.getAD_Client_ID(), bpartner.getAD_Org_ID(), bpartner.getC_BPartner_ID());
			return;
		}
		acctSchemaBL.updateDebitorCreditorIds(as, bpartner);
	}
}
