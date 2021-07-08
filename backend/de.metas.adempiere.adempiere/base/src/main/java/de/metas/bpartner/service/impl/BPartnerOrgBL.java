package de.metas.bpartner.service.impl;

import static de.metas.util.Check.assume;

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

import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.bpartner.service.OrgHasNoBPartnerLinkException;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

public class BPartnerOrgBL implements IBPartnerOrgBL
{
	@Override
	public I_C_BPartner retrieveLinkedBPartner(final I_AD_Org org)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(org);
		final String trxName = InterfaceWrapperHelper.getTrxName(org);
		final int adOrgId = org.getAD_Org_ID();
		return retrieveLinkedBPartner(ctx, adOrgId, trxName);
	}

	@Override
	public I_C_BPartner retrieveLinkedBPartner(final int adOrgId)
	{
		return retrieveLinkedBPartner(Env.getCtx(), adOrgId, ITrx.TRXNAME_None);
	}

	@Override
	public Optional<BPartnerId> retrieveLinkedBPartnerId(@NonNull final OrgId orgId)
	{
		assume(orgId.isRegular(), "Given orgId={} needs to be a regular >0 (i.e. not 'any') Org-ID", orgId);

		final I_C_BPartner resultRecord = retrieveLinkedBPartner(Env.getCtx(), orgId.getRepoId(), ITrx.TRXNAME_None);
		if (resultRecord == null)
		{
			return Optional.empty();
		}
		return Optional.of(BPartnerId.ofRepoId(resultRecord.getC_BPartner_ID()));
	}

	@Cached(cacheName = I_C_BPartner.Table_Name + "#By#AD_OrgBP_ID")
	/* package */ I_C_BPartner retrieveLinkedBPartner(@CacheCtx final Properties ctx, final int adOrgId, @CacheTrx final String trxName)
	{
		final IQueryBuilder<I_C_BPartner> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class, ctx, trxName);

		final ICompositeQueryFilter<I_C_BPartner> filters = queryBuilder.getCompositeFilter();
		filters.addOnlyActiveRecordsFilter();
		filters.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_OrgBP_ID, adOrgId);

		return queryBuilder.create()
				.firstOnly(I_C_BPartner.class);
	}

	@Override
	public I_C_Location retrieveOrgLocation(@NonNull final OrgId orgId)
	{
		final BPartnerLocationId orgBPLocationId = retrieveOrgBPLocationId(orgId);
		if (orgBPLocationId == null)
		{
			return null;
		}

		final I_C_BPartner_Location bpLocation = Services.get(IBPartnerDAO.class).getBPartnerLocationByIdEvenInactive(orgBPLocationId);
		if (bpLocation != null) // 03378 : Temporary. Will be removed when OrgBP_Location is mandatory.
		{
			return bpLocation.getC_Location();
		}
		return null;
	}

	@Override
	public BPartnerLocationId retrieveOrgBPLocationId(final OrgId orgId)
	{
		final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
		return orgsRepo.getOrgInfoById(orgId).getOrgBPartnerLocationId();
	}

	@Override
	public Optional<UserId> retrieveUserInChargeOrNull(@NonNull final OrgId orgId)
	{
		final org.compiere.model.I_AD_User user = retrieveUserInChargeOrNull(Env.getCtx(), orgId.getRepoId(), ITrx.TRXNAME_None);
		if (user != null)
		{
			return Optional.of(UserId.ofRepoId(user.getAD_User_ID()));
		}
		return Optional.empty();
	}

	@Override
	@Deprecated
	public org.compiere.model.I_AD_User retrieveUserInChargeOrNull(final Properties ctx, final int orgId, final String trxName)
	{
		final IBPartnerDAO bPartnerPA = Services.get(IBPartnerDAO.class);
		org.compiere.model.I_AD_User defaultContact;
		try
		{
			final I_C_BPartner orgBPartner = bPartnerPA.retrieveOrgBPartner(ctx, orgId, I_C_BPartner.class, trxName);
			defaultContact = bPartnerPA.retrieveDefaultContactOrNull(orgBPartner, I_AD_User.class);
		}
		catch (OrgHasNoBPartnerLinkException e)
		{
			defaultContact = null;
		}
		return defaultContact;
	}
}
