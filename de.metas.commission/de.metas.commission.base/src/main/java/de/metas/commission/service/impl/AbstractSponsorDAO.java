package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.commission.exception.SponsorConfigException;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_Sponsor_SalesRep;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.CommissionConstants;
import de.metas.commission.util.Messages;

public abstract class AbstractSponsorDAO implements ISponsorDAO
{
	protected final transient CLogger logger = CLogger.getCLogger(getClass());

	@Cached
	@Override
	public I_C_Sponsor retrieveForBPartner(
			final @CacheCtx Properties ctx,
			final org.compiere.model.I_C_BPartner customer,
			final boolean throwEx,
			final String trxName)
	{
		final String whereClause = I_C_Sponsor.COLUMNNAME_C_BPartner_ID + "=?";
		final Object[] parameters = { customer.getC_BPartner_ID() };

		final I_C_Sponsor result = new Query(ctx, I_C_Sponsor.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.firstOnly(I_C_Sponsor.class);

		if (result == null && throwEx)
		{
			throw new SponsorConfigException(
					Messages.SPONSOR_MISSING_FOR_BPARTNER_2P,
					new Object[] { customer.getValue(), customer.getName() });
		}
		return result;
	}

	@Override
	public I_C_Sponsor retrieveForBPartner(
			final org.compiere.model.I_C_BPartner customer,
			final boolean throwEx)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(customer);
		final String trxName = InterfaceWrapperHelper.getTrxName(customer);

		return retrieveForBPartner(
				ctx,
				InterfaceWrapperHelper.create(customer, I_C_BPartner.class),
				throwEx,
				trxName);
	}

	@Override
	public I_C_BPartner getCustomer(final I_C_Sponsor sponsor, final Timestamp date)
	{
		return InterfaceWrapperHelper.create(sponsor.getC_BPartner(), I_C_BPartner.class);
	}

	public Collection<I_C_Sponsor> retrieveAll(final Properties ctx, final String trxName)
	{
		return new Query(ctx, I_C_Sponsor.Table_Name, "", trxName)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list(I_C_Sponsor.class);
	}

	public List<I_C_Sponsor> retrieveForSalesRepBetween(
			final org.compiere.model.I_C_BPartner salesRep,
			final Timestamp dateFrom,
			final Timestamp dateTo)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(salesRep);
		final String trxName = InterfaceWrapperHelper.getTrxName(salesRep);

		final List<I_C_Sponsor> result = new ArrayList<I_C_Sponsor>();

		for (final I_C_Sponsor_SalesRep ssr : retrieveSalesRepLinks(ctx, salesRep.getC_BPartner_ID(), dateFrom, dateTo, trxName))
		{
			final I_C_Sponsor sponsor = ssr.getC_Sponsor();
			result.add(sponsor);
		}
		return result;
	}

	@Override
	public List<I_C_Sponsor> retrieveForSalesRepUntil(final I_C_BPartner salesRep, final Timestamp dateTo)
	{
		final Timestamp dateFrom = null;
		return retrieveForSalesRep(salesRep, dateFrom, dateTo, Mode.UNTIL);
	}

	@Override
	public List<I_C_Sponsor> retrieveForSalesRepAt(final org.compiere.model.I_C_BPartner salesRep, final Timestamp ts)
	{
		return retrieveForSalesRep(salesRep, null, ts, Mode.AT);
	}

	private enum Mode
	{
		AT, UNTIL
	};

	private List<I_C_Sponsor> retrieveForSalesRep(
			final org.compiere.model.I_C_BPartner salesRep,
			final Timestamp dateFrom, final Timestamp dateTo,
			final Mode mode)
	{
		assert salesRep != null;

		final List<I_C_Sponsor> result = new ArrayList<I_C_Sponsor>();

		for (final I_C_Sponsor_SalesRep ssr : retrieveForBPartner(salesRep))
		{
			// as the result of retrieveForBPartner is order by validFrom we
			// could also do a break
			final boolean addSponsor;
			if (mode.equals(Mode.AT))
			{
				addSponsor = ssr.getValidFrom().getTime() <= dateTo.getTime()
						&& ssr.getValidTo().getTime() >= dateTo.getTime();
			}
			else if (mode.equals(Mode.UNTIL))
			{
				addSponsor = ssr.getValidFrom().getTime() <= dateTo.getTime();
			}
			else
			{
				throw new IllegalArgumentException(mode.toString());
			}

			if (addSponsor)
			{
				result.add(ssr.getC_Sponsor());
			}
		}
		return result;
	}

	@Override
	public I_C_Sponsor createNewForCustomer(final org.compiere.model.I_C_BPartner bPartnerPO)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartnerPO, true); // useClientOrgFromModel=true
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartnerPO);
		final I_C_Sponsor newSponsor = InterfaceWrapperHelper.newInstance(I_C_Sponsor.class, bPartnerPO);

		// newSponsor.setClientOrg(bPartnerPO); // not needed, context already contains the right client/org
		newSponsor.setAD_Org_ID(bPartnerPO.getAD_Org_ID());
		newSponsor.setSponsorNo(bPartnerPO.getValue());
		newSponsor.setC_BPartner_ID(bPartnerPO.getC_BPartner_ID());
		InterfaceWrapperHelper.save(newSponsor);
		logger.info("Created " + newSponsor + " for " + bPartnerPO);

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(bPartnerPO, I_C_BPartner.class);
		final int bPartnerParentSponsorId = bPartner.getC_Sponsor_Parent_ID();

		if (bPartnerParentSponsorId > 0)
		{
			final I_C_Sponsor parentSponsor = InterfaceWrapperHelper.create(ctx, bPartnerParentSponsorId, I_C_Sponsor.class, trxName);

			// metas-ts: 02171
			for (final I_C_Sponsor_SalesRep parentSponsorSSR : retrieveParentLinksSSRs(parentSponsor))
			{
				final I_C_Sponsor_SalesRep ssr = InterfaceWrapperHelper.create(ctx, I_C_Sponsor_SalesRep.class, trxName);

				ssr.setC_AdvComSystem_ID(parentSponsorSSR.getC_AdvComSystem_ID());
				ssr.setSponsorSalesRepType(X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_Hierarchie);
				ssr.setC_Sponsor_ID(newSponsor.getC_Sponsor_ID());
				ssr.setC_Sponsor_Parent_ID(bPartnerParentSponsorId);
				ssr.setValidFrom(parentSponsorSSR.getValidFrom());
				ssr.setValidTo(parentSponsorSSR.getValidTo());
				InterfaceWrapperHelper.save(ssr);

				logger.info("Created and saved " + ssr + " for " + bPartnerPO);
			}
		}
		else if (bPartnerPO.isCustomer() || bPartnerPO.isSalesRep())
		{
			logger.warning(bPartnerPO + " has no " + I_C_BPartner.COLUMNNAME_C_Sponsor_Parent_ID);
		}
		return newSponsor;
	}

	public boolean isParentOf(final I_C_Sponsor parentSponsor, final I_C_Sponsor sponsor, final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(parentSponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(parentSponsor);

		final List<List<I_C_Sponsor_SalesRep>> paths = new ArrayList<List<I_C_Sponsor_SalesRep>>();
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final List<I_C_Sponsor_SalesRep> parentSSRs = retrieveParentLinks(ctx, sponsor.getC_Sponsor_ID(), date, date, trxName);

		for (final I_C_Sponsor_SalesRep parentLinkSSR : parentSSRs)
		{
			final List<I_C_Sponsor_SalesRep> currentPath = new ArrayList<I_C_Sponsor_SalesRep>();
			currentPath.add(parentLinkSSR);

			sponsorBL.findPathsToSponsor(parentLinkSSR, parentSponsor.getC_Sponsor_ID(), date, date, paths, currentPath);
		}
		return !paths.isEmpty();
	}

	@Override
	public Collection<I_C_Sponsor> retrieveChildren(final I_C_Sponsor sponsor, final Timestamp ts)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final Collection<I_C_Sponsor> result = new ArrayList<I_C_Sponsor>();

		for (final I_C_Sponsor_SalesRep ssr : retrieveChildrenOfAt(ctx, sponsor.getC_Sponsor_ID(), ts, trxName))
		{
			result.add(ssr.getC_Sponsor());
		}
		return result;
	}

	@Override
	public Collection<I_C_Sponsor> retrieveChildrenBetween(
			final I_C_Sponsor sponsor,
			final Timestamp dateFrom,
			final Timestamp dateTo,
			final int comSystemId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final Collection<I_C_Sponsor> result = new ArrayList<I_C_Sponsor>();

		for (final I_C_Sponsor_SalesRep ssr : retrieveChildrenOf(ctx, sponsor.getC_Sponsor_ID(), dateFrom, dateTo, comSystemId, trxName))
		{
			result.add(ssr.getC_Sponsor());
		}
		return result;
	}

	//
	// MCSponsorSalesRep --------------------------------------------------------------
	//

	public static final String WHERE_PARENT_SPONSOR =
			I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND "
					+ I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType + "='" + X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_Hierarchie + "'";

	public static final String WHERE_SALESREP =
			I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND "
					+ I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType + "='" + X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP + "'";

	// @Override
	public String toString(final I_C_Sponsor_SalesRep sponsorSalesRep)
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("MCSponsorSalesRep[Id=");
		sb.append(sponsorSalesRep.getC_Sponsor_SalesRep_ID());
		sb.append("; SponsorSalesRepType=");
		sb.append(sponsorSalesRep.getSponsorSalesRepType());
		sb.append("; C_Sponsor_ID=");
		sb.append(sponsorSalesRep.getC_Sponsor_ID());
		sb.append("; C_BPartner_ID=");
		sb.append(sponsorSalesRep.getC_BPartner_ID());
		sb.append(",C_Sponsor_Parent_ID=");
		sb.append(sponsorSalesRep.getC_Sponsor_Parent_ID());
		sb.append("; valid from ");
		sb.append(sponsorSalesRep.getValidFrom());
		sb.append(" until ");
		sb.append(sponsorSalesRep.getValidTo());
		sb.append("]");

		return sb.toString();
	}

	@Override
	public List<I_C_Sponsor_SalesRep> retrieveForBPartner(final org.compiere.model.I_C_BPartner bPartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		final String whereClause = I_C_Sponsor_SalesRep.COLUMNNAME_C_BPartner_ID + "=?";

		final Object[] parameters = { bPartner.getC_BPartner_ID() };

		final String orderBy = I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom;

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_C_Sponsor_SalesRep.class);
	}

	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSalesRepSSRs(final I_C_Sponsor sponsor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		return retrieveSalesRepSSRs(ctx, sponsor, trxName);
	}

	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSalesRepSSRs(final Properties ctx, final I_C_Sponsor sponsor, final String trxName)
	{
		return retrieveSSRs(ctx, AbstractSponsorDAO.WHERE_SALESREP, sponsor.getC_Sponsor_ID(), trxName);
	}

	// @Cached
	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSSRs(
			final @CacheCtx Properties ctx,
			final String whereClause,
			final int sponsorId,
			final String trxName)
	{
		Check.assume(sponsorId > 0, "sponsorId > 0");

		final Object[] parameters = { sponsorId };

		final String orderBy = I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + ", " + I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_SalesRep_ID;

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_C_Sponsor_SalesRep.class);
	}

	@Override
	public List<I_C_Sponsor_SalesRep> retrieveParentLinksSSRs(final I_C_Sponsor sponsor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);
		return retrieveParentLinksSSRs(ctx, sponsor, trxName);
	}

	@Override
	public List<I_C_Sponsor_SalesRep> retrieveParentLinksSSRs(final Properties ctx, final I_C_Sponsor sponsor, final String trxName)
	{
		return retrieveSSRs(ctx, AbstractSponsorDAO.WHERE_PARENT_SPONSOR, sponsor.getC_Sponsor_ID(), trxName);
	}

	@Override
	public void validate(final I_C_Sponsor_SalesRep sponsorSalesRep)
	{
		if (sponsorSalesRep.getC_Sponsor_ID() <= 0)
		{
			throw new FillMandatoryException(I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID);
		}

		logger.fine("Checking validFrom and validTo");

		if (sponsorSalesRep.getValidFrom() == null)
		{
			throw new FillMandatoryException(I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom);
		}
		if (sponsorSalesRep.getValidTo() == null)
		{
			throw new FillMandatoryException(I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo);
		}

		if (sponsorSalesRep.getValidFrom().after(sponsorSalesRep.getValidTo()))
		{
			// logger.saveError(Messages.SPONSOR_SALESREP_VALIDFROM_TOO_LATE, get_DisplayValue(sponsorSalesRep, I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom, true));
			// return false;
			throw new AdempiereException(Messages.SPONSOR_SALESREP_VALIDFROM_TOO_LATE + ": " + get_DisplayValue(sponsorSalesRep, I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom, true));
		}
		else if (sponsorSalesRep.getValidFrom().before(CommissionConstants.VALID_RANGE_MIN))
		{
			// logger.saveError(Messages.SPONSOR_SALESREP_VALIDFROM_TOO_EARLY, get_DisplayValue(sponsorSalesRep, I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom, true));
			// return false;
			throw new AdempiereException(Messages.SPONSOR_SALESREP_VALIDFROM_TOO_EARLY + ": " + get_DisplayValue(sponsorSalesRep, I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom, true));
		}
		else if (sponsorSalesRep.getValidTo().after(CommissionConstants.VALID_RANGE_MAX))
		{
			// logger.saveError(Messages.SPONSOR_SALESREP_VALIDTO_TOO_LATE, get_DisplayValue(sponsorSalesRep, I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo, true));
			// return false;
			throw new AdempiereException(Messages.SPONSOR_SALESREP_VALIDTO_TOO_LATE + ": " + get_DisplayValue(sponsorSalesRep, I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo, true));
		}
	}

	private String get_DisplayValue(final Object model, final String columnName, final boolean currentValue)
	{
		// TODO move it to InterfaceWrapperHelper/POWrapper
		final PO po = InterfaceWrapperHelper.getPO(model);
		return po.get_DisplayValue(columnName, currentValue);
	}

	@Override
	public Collection<I_C_Sponsor_SalesRep> retrieveChildren(final I_C_Sponsor_SalesRep sponsorSalesRep, final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsorSalesRep);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsorSalesRep);
		final int sponsorId = sponsorSalesRep.getC_Sponsor_ID();
		return retrieveChildrenOfAt(ctx, sponsorId, date, trxName);
	}

	public Collection<I_C_Sponsor_SalesRep> retrieveChildrenOfAt(
			final Properties ctx,
			final int sponsorId,
			final Timestamp date,
			final String trxName)
	{

		final String whereClause = //
		I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=? AND " //
				+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + ">=? AND "
				+ I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID + "=?";

		final Object[] parameters = { date, date, sponsorId };

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.list(I_C_Sponsor_SalesRep.class);
	}

	public static Collection<I_C_Sponsor_SalesRep> retrieveChildrenOf(
			final Properties ctx,
			final int sponsorId,
			final Timestamp dateFrom,
			final Timestamp dateTo,
			final int comSystemId,
			final String trxName)
	{

		final StringBuilder wc = new StringBuilder();
		// TODO: tsa: is this correct? AND / OR ... no brackets
		wc.append(
				I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID + "=? AND (" //
						+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + ">=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=?"
						+ " OR "
						+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + ">=?" + ")");

		final List<Object> params = new ArrayList<Object>();
		params.add(sponsorId);
		params.add(dateFrom);
		params.add(dateTo);
		params.add(dateFrom);
		params.add(dateFrom);

		if (comSystemId > 0)
		{
			wc.append(" AND " + I_C_Sponsor_SalesRep.COLUMNNAME_C_AdvComSystem_ID + "=? ");
			params.add(comSystemId);
		}

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_SalesRep_ID)
				.list(I_C_Sponsor_SalesRep.class);
	}

	public Collection<I_C_Sponsor_SalesRep> retrieveChildren(final I_C_Sponsor_SalesRep sponsorSalesRep)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsorSalesRep);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsorSalesRep);

		// select all SSRs whose parent sponsor is this ssr's sponsor at any time
		final String whereClause = I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID + "=?";

		final Object[] parameters = { sponsorSalesRep.getC_Sponsor_ID() };

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list(I_C_Sponsor_SalesRep.class);
	}

	@Cached
	@Override
	public List<I_C_Sponsor_SalesRep> retrieveChildrenLinks(
			final @CacheCtx Properties ctx,
			final int sponsorParentID,
			final Timestamp validFrom,
			final Timestamp validTo,
			final String trxName)
	{
		// TODO: tsa: is that correct? no brackets?
		final String whereClause = I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID + "=? AND (" //
				+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + ">=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=?"
				+ " OR "
				+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + ">=?" + ")";

		final Object[] parameters = { sponsorParentID,
				validFrom, validTo, validFrom, validFrom };

		final String orderBy = I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom;

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_C_Sponsor_SalesRep.class);
	}

	// @Cached
	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSalesRepLinksForSponsor(
			final/* @CacheIgnore */Properties ctx,
			final int sponsorId,
			final Timestamp validFrom,
			final Timestamp validTo,
			final String trxName)
	{
		// TODO: tsa: is that correct?
		final String whereClause =
				I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND (" //
						+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + ">=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=?"
						+ " OR "
						+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + ">=?" + ")";

		final Object[] parameters = { sponsorId,
				validFrom, validTo, validFrom, validFrom };

		final String orderBy = I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom;

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_C_Sponsor_SalesRep.class);
	}

	@Cached
	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSalesRepLinks(
			final @CacheCtx Properties ctx,
			final int bPartnerId,
			final Timestamp validFrom,
			final Timestamp validTo,
			final String trxName)
	{

		// TODO: tsa is that correct?
		final String whereClause = I_C_Sponsor_SalesRep.COLUMNNAME_C_BPartner_ID //
				+ "=? AND (" //
				+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + ">=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom
				+ "<=?" + " OR " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=? AND "
				+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + ">=?" + ")";

		final Object[] parameters = { bPartnerId, //
				validFrom, validTo, validFrom, validFrom };

		final String orderBy = I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom;

		return new Query(ctx, I_C_Sponsor_SalesRep.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_C_Sponsor_SalesRep.class);
	}

	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSalesRepsForConditionSet(final Properties ctx, final Set<Integer> conditionIDs, final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Sponsor_SalesRep.class, ctx, trxName)
				.filter(new InArrayQueryFilter<I_C_Sponsor_SalesRep>(I_C_Sponsor_SalesRep.COLUMNNAME_C_AdvCommissionCondition_ID, conditionIDs))
				.create()
				.list(I_C_Sponsor_SalesRep.class);
	}

	@Override
	public List<I_C_Sponsor_SalesRep> retrieveSponsorSalesRepsForCondition(final Properties ctx, final I_C_AdvCommissionCondition condition, final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Sponsor_SalesRep.class, ctx, trxName)
				.filter(new InArrayQueryFilter<I_C_Sponsor_SalesRep>(I_C_Sponsor_SalesRep.COLUMNNAME_C_AdvCommissionCondition_ID, condition.getC_AdvCommissionCondition_ID()))
				.create()
				.list(I_C_Sponsor_SalesRep.class);
	}

	@Override
	public final List<I_C_Sponsor_SalesRep> retrieveParentLinks(final Properties ctx, final int sponsorID, final Timestamp validFrom, final Timestamp validTo, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		
		final IQueryFilter<I_C_Sponsor_SalesRep> sponsorFilter = queryBL.createCompositeQueryFilter(I_C_Sponsor_SalesRep.class)
				.addFilter(ActiveRecordQueryFilter.getInstance(I_C_Sponsor_SalesRep.class))
				.addEqualsFilter(I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID, sponsorID)
				.addEqualsFilter(I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType, X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_Hierarchie);

		final IQueryFilter<I_C_Sponsor_SalesRep> validFromFilter = queryBL.createCompositeQueryFilter(I_C_Sponsor_SalesRep.class)
				.addCompareFilter(I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom, Operator.GREATER_OR_EQUAL, validFrom)
				.addCompareFilter(I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, validTo);

		final IQueryFilter<I_C_Sponsor_SalesRep> validToFilter = queryBL.createCompositeQueryFilter(I_C_Sponsor_SalesRep.class)
				.addCompareFilter(I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, validFrom)
				.addCompareFilter(I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo, Operator.GREATER_OR_EQUAL, validFrom);

		final IQueryFilter<I_C_Sponsor_SalesRep> validFilter = queryBL.createCompositeQueryFilter(I_C_Sponsor_SalesRep.class)
				.addFilter(validFromFilter)
				.setJoinOr()
				.addFilter(validToFilter);

		final IQueryFilter<I_C_Sponsor_SalesRep> filter = queryBL.createCompositeQueryFilter(I_C_Sponsor_SalesRep.class)
				.addFilter(sponsorFilter)
				.setJoinAnd()
				.addFilter(validFilter);

		// TODO: fully implement the whereClause below:
		// final String whereClause =
		// I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND " //
		// + I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID + ">0 AND (" //
		// + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + ">=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=?" +
		// " OR "
		// + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + "<=? AND " + I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + ">=?" + ")";

		final IQueryBuilder<I_C_Sponsor_SalesRep> queryBuilder = queryBL.createQueryBuilder(I_C_Sponsor_SalesRep.class, ctx, trxName)
				.filter(filter);
		queryBuilder.orderBy().addColumn(I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom);

		return queryBuilder
				.create()
				.list(I_C_Sponsor_SalesRep.class);
	}
}
