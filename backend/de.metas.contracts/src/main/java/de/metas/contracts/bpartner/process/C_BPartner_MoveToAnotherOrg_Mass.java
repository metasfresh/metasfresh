/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.bpartner.service.OrgChangeRequest;
import de.metas.contracts.bpartner.service.OrgChangeService;
import de.metas.logging.LogManager;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.organization.OrgId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_CompensationGroup_Schema_Category;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Iterator;

public class C_BPartner_MoveToAnotherOrg_Mass extends JavaProcess implements
		IProcessDefaultParametersProvider
{
	public static final String PARAM_AD_ORG_TARGET_ID = "AD_Org_Target_ID";
	public static final String PARAM_GroupCategory_ID = I_C_CompensationGroup_Schema_Category.COLUMNNAME_C_CompensationGroup_Schema_Category_ID;
	public static final String PARAM_DATE_ORG_CHANGE = "Date_OrgChange";
	public static final String PARAM_IsCloseInvoiceCandidate = "IsCloseInvoiceCandidate";
	public static final String PARAM_WhereClause = "WhereClause";

	private final static Logger logger = LogManager.getLogger(C_BPartner_MoveToAnotherOrg_Mass.class);
	
	final OrgChangeService service = SpringContextHolder.instance.getBean(OrgChangeService.class);
	final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Param(parameterName = PARAM_AD_ORG_TARGET_ID, mandatory = true)
	protected OrgId p_orgTargetId;
	@Param(parameterName = PARAM_GroupCategory_ID)
	protected GroupCategoryId p_groupCategoryId;
	@Param(parameterName = PARAM_DATE_ORG_CHANGE, mandatory = true)
	protected Instant p_startDate;
	@Param(parameterName = PARAM_IsCloseInvoiceCandidate, mandatory = true)
	protected boolean isCloseInvoiceCandidate;
	@Param(parameterName = PARAM_WhereClause, mandatory = true)
	protected String p_WhereClause;

	private int countOK = 0;
	private int countError = 0;

	@RunOutOfTrx
	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_C_BPartner> partnerIterator = retrievePartnersToMove(p_WhereClause);
		
		trxManager.runInNewTrx(() -> {
			while (partnerIterator.hasNext())
			{
				final I_C_BPartner partner = partnerIterator.next();
				movePartnerToNewOrg(partner);
			}
		});
		
		return "@Processed@ (OK=#" + this.countOK + ", Error=#" + this.countError + ")";
	}

	private void movePartnerToNewOrg(@NonNull final I_C_BPartner partner)
	{
		try
		{
			final OrgChangeRequest orgChangeRequest = OrgChangeRequest.builder()
					.bpartnerId(BPartnerId.ofRepoId(partner.getC_BPartner_ID()))
					.startDate(p_startDate)
					.groupCategoryId(p_groupCategoryId)
					.orgFromId(OrgId.ofRepoId(partner.getAD_Org_ID()))
					.orgToId(p_orgTargetId)
					.isCloseInvoiceCandidate(isCloseInvoiceCandidate)
					.build();

			service.moveToNewOrg(orgChangeRequest);
			countOK++;
		}
		catch (final AdempiereException rte)
		{
			logger.warn("Error moving partner " + partner.getName() + " to AD_Org_ID " + p_orgTargetId, rte);
			addLog("Error moving partner {} to AD_Org_ID {}: {}", partner.getName(), p_orgTargetId, rte.getLocalizedMessage());
			countError++;
		}
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_DATE_ORG_CHANGE.equals(parameter.getColumnName()))
		{
			return SystemTime.asLocalDate().plusDays(1);
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	private Iterator<I_C_BPartner> retrievePartnersToMove(@NonNull final String whereClause)
	{
		final IQuery<I_C_BPartner> query = new TypedSqlQuery<>(getCtx(), I_C_BPartner.class, whereClause, ITrx.TRXNAME_None)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1);

		return bpartnerDAO.retrievePartnersByQuery(query);
	}
}
