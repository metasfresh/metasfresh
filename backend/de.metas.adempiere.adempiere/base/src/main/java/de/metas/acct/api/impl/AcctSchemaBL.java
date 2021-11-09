/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.acct.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

@Service
public class AcctSchemaBL implements IAcctSchemaBL
{
	private final Logger logger = LogManager.getLogger(AcctSchemaBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public void updateDebitorCreditorIds(@NonNull final AcctSchema acctSchema, @Nullable final OrgId orgId)
	{
		if (!acctSchema.isAutoSetDebtoridAndCreditorid())
		{
			return;
		}
		final IQueryBuilder<I_C_BPartner> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_CreditorId, null)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_DebtorId, null)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_OrgBP_ID, null);
		final Collection<OrgId> orgIdsToUse = getOrgIdsToUse(acctSchema, orgId);
		if (!orgIdsToUse.contains(OrgId.ANY))
		{
			queryBuilder.addInArrayFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, orgIdsToUse);
		}
		final Iterator<I_C_BPartner> bPartnerIterator = queryBuilder.create()
				.iterate(I_C_BPartner.class);
		while (bPartnerIterator.hasNext())
		{
			final I_C_BPartner bpartner = bPartnerIterator.next();
			updateDebitorCreditorIdsAndSave(acctSchema, bpartner);
		}
	}

	private Collection<OrgId> getOrgIdsToUse(@NonNull final AcctSchema acctSchema, @Nullable final OrgId orgId)
	{
		if (orgId != null)
		{
			return Collections.singleton(orgId);
		}
		final ImmutableSet<OrgId> postOnlyForOrgIds = acctSchema.getPostOnlyForOrgIds();
		return postOnlyForOrgIds.isEmpty() ? Collections.singleton(acctSchema.getOrgId()) : postOnlyForOrgIds;
	}

	private void updateDebitorCreditorIdsAndSave(@NonNull final AcctSchema acctSchema, @NonNull final I_C_BPartner bpartner)
	{
		Services.get(ITrxManager.class).runInNewTrx(localTrxName -> {
			try
			{
				updateDebitorCreditorIds(acctSchema, bpartner);
				InterfaceWrapperHelper.save(bpartner);
			}
			catch (final RuntimeException runException)
			{
				final ILoggable loggable = Loggables.withLogger(logger, Level.WARN);
				loggable.addLog("AcctSchemaBL.updateDebitorCreditorIdsAndSave, for bpartnerId {} - caught {} with message={}",
						bpartner.getC_BPartner_ID(),
						runException.getClass(),
						runException.getMessage(),
						runException);
			}
		});
	}

	@Override
	public void updateDebitorCreditorIds(@NonNull final AcctSchema acctSchema, @NonNull final I_C_BPartner bpartner)
	{
		if (acctSchema.isAutoSetDebtoridAndCreditorid())
		{
			String value = bpartner.getValue();
			//as per c_bpartner_datev_no_generate.sql, we should be updating only values with length between 5 and 7
			if (Check.isNotBlank(value))
			{
				if (value.startsWith("<") && value.endsWith(">"))
				{
					value = value.substring(1, value.length() - 1);
				}
				if (value.length() >= 5 && value.length() <= 7 && StringUtils.isNumber(value))
				{
					final String valueAsString = Strings.padStart(value, 7, '0').substring(0, 7);
					bpartner.setCreditorId(Integer.parseInt(acctSchema.getCreditorIdPrefix() + valueAsString));
					bpartner.setDebtorId(Integer.parseInt(acctSchema.getDebtorIdPrefix() + valueAsString));
				}
				else
				{
					Loggables.withLogger(logger, Level.DEBUG).addLog("value {} for bpartnerId {} must be a number with 5 to 7 digits", value, bpartner.getC_BPartner_ID());
				}
			}
		}
	}
}
