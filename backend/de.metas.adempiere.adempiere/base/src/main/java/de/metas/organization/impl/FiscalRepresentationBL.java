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

package de.metas.organization.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.location.CountryId;
import de.metas.organization.FiscalRepresentationId;
import de.metas.organization.IFiscalRepresentationBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Fiscal_Representation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class FiscalRepresentationBL implements IFiscalRepresentationBL
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CCache<OrgId, Boolean> orgsWithActiveFiscalRepresentationCache = CCache.<OrgId, Boolean>builder()
			.tableName(I_C_Fiscal_Representation.Table_Name)
			.build();
	private final CCache<Integer, Boolean> anyActiveFiscalRepresentationCache = CCache.<Integer, Boolean>builder()
			.tableName(I_C_Fiscal_Representation.Table_Name)
			.initialCapacity(1)
			.build();

	public void updateCountryId(final I_C_Fiscal_Representation fiscalRep)
	{
		if (fiscalRep.getC_BPartner_Location_ID() > 0)
		{
			final BPartnerLocationId bpLocation = BPartnerLocationId.ofRepoId(fiscalRep.getC_BPartner_Representative_ID(), fiscalRep.getC_BPartner_Location_ID());
			final CountryId countryId = bpartnerDAO.getCountryId(bpLocation);
			fiscalRep.setTo_Country_ID(countryId.getRepoId());
		}
	}

	public boolean isValidFromDate(final I_C_Fiscal_Representation fiscalRep)
	{
		return fiscalRep.getValidFrom().before(fiscalRep.getValidTo());
	}

	public boolean isValidToDate(final I_C_Fiscal_Representation fiscalRep)
	{
		return fiscalRep.getValidTo() == null || fiscalRep.getValidTo().after(fiscalRep.getValidFrom());
	}

	public void updateValidTo(final I_C_Fiscal_Representation fiscalRep)
	{
		fiscalRep.setValidTo(fiscalRep.getValidFrom());
	}

	public void updateValidFrom(final I_C_Fiscal_Representation fiscalRep)
	{
		fiscalRep.setValidFrom(fiscalRep.getValidTo());
	}

	@Override
	public boolean hasFiscalRepresentation(@NonNull final CountryId countryId, @NonNull final OrgId orgId, @NonNull final Timestamp date)
	{
		return getFiscalRepresentation(countryId, orgId, date).isPresent();
	}

	@Override
	public Optional<I_C_Fiscal_Representation> getFiscalRepresentation(@NonNull final CountryId countryId, @NonNull final OrgId orgId, @NonNull final Timestamp date)
	{
		final ICompositeQueryFilter<I_C_Fiscal_Representation> validToFilter = queryBL.createCompositeQueryFilter(I_C_Fiscal_Representation.class)
				.setJoinOr()
				.addCompareFilter(I_C_Fiscal_Representation.COLUMN_ValidTo, CompareQueryFilter.Operator.GREATER_OR_EQUAL, date)
				.addEqualsFilter(I_C_Fiscal_Representation.COLUMN_ValidTo, null);

		return queryBL.createQueryBuilder(I_C_Fiscal_Representation.class)
				.addEqualsFilter(I_C_Fiscal_Representation.COLUMNNAME_To_Country_ID, countryId)
				.addEqualsFilter(I_C_Fiscal_Representation.COLUMNNAME_AD_Org_ID, orgId)
				.addCompareFilter(I_C_Fiscal_Representation.COLUMN_ValidFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL, date)
				.filter(validToFilter)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_C_Fiscal_Representation.COLUMNNAME_ValidFrom)
				.orderByDescending(I_C_Fiscal_Representation.COLUMNNAME_C_Fiscal_Representation_ID)
				.create()
				.firstOptional(I_C_Fiscal_Representation.class);
	}

	@Override
	public boolean hasAnyActiveFiscalRepresentation(@NonNull final OrgId orgId)
	{
		return orgsWithActiveFiscalRepresentationCache.getOrLoad(orgId, () -> queryBL.createQueryBuilderOutOfTrx(I_C_Fiscal_Representation.class)
				.addEqualsFilter(I_C_Fiscal_Representation.COLUMNNAME_AD_Org_ID, orgId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch());
	}

	@Override
	public boolean hasAnyActiveFiscalRepresentationInAnyOrg()
	{
		return anyActiveFiscalRepresentationCache.getOrLoad(0, () -> queryBL.createQueryBuilderOutOfTrx(I_C_Fiscal_Representation.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch());
	}

	@Override
	@NonNull
	public I_C_Fiscal_Representation getById(@NonNull final FiscalRepresentationId fiscalRepresentationId)
	{
		return InterfaceWrapperHelper.loadOutOfTrx(fiscalRepresentationId, I_C_Fiscal_Representation.class);
	}
}
