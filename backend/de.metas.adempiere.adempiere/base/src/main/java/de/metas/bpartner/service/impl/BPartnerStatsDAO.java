package de.metas.bpartner.service.impl;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.sectionCode.SectionCodeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class BPartnerStatsDAO implements IBPartnerStatsDAO
{
	private IQueryBL queryBL = Services.get(IQueryBL.class);
	private IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);

	@Override
	public BPartnerStats getCreateBPartnerStats(@NonNull final I_C_BPartner partner)
	{

		I_C_BPartner_Stats statsRecord = queryBL
				.createQueryBuilder(I_C_BPartner_Stats.class) // using current trx, because we will save in current trx too
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.create()
				.firstOnly(I_C_BPartner_Stats.class);

		if (statsRecord == null)
		{
			statsRecord = createBPartnerStats(partner);
		}

		return BPartnerStats.builder()
				.repoId(statsRecord.getC_BPartner_Stats_ID())
				.bpartnerId(BPartnerId.ofRepoId(partner.getC_BPartner_ID()))
				.actualLifeTimeValue(statsRecord.getActualLifeTimeValue())
				.openItems(statsRecord.getOpenItems())
				.soCreditStatus(CreditStatus.ofNullableCode(statsRecord.getSOCreditStatus()))
				.soCreditUsed(statsRecord.getSO_CreditUsed())
				.deliveryCreditUsed(statsRecord.getDelivery_CreditUsed())
				.deliveryCreditStatus(CreditStatus.ofNullableCode(statsRecord.getDelivery_CreditStatus()))
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(statsRecord.getM_SectionCode_ID()))
				.build();
	}

	/**
	 * Create the C_BPartner_Stats entry for the given bpartner.
	 */
	private I_C_BPartner_Stats createBPartnerStats(final I_C_BPartner partner)
	{
		final BPGroupId bpGroupId = BPGroupId.ofRepoId(partner.getC_BP_Group_ID());

		final I_C_BP_Group bpGroup = bpGroupDAO.getByIdInInheritedTrx(bpGroupId);

		final I_C_BPartner_Stats stat = newInstance(I_C_BPartner_Stats.class);
		final String status = bpGroup.getSOCreditStatus();
		stat.setC_BPartner_ID(partner.getC_BPartner_ID());
		stat.setM_SectionCode(partner.getM_SectionCode());
		stat.setSOCreditStatus(status);
		stat.setSO_CreditUsed(BigDecimal.ZERO);
		stat.setDelivery_CreditStatus(status);
		stat.setDelivery_CreditUsed(BigDecimal.ZERO);
		stat.setActualLifeTimeValue(BigDecimal.ZERO);
		stat.setOpenItems(BigDecimal.ZERO);
		stat.setAD_Org_ID(partner.getAD_Org_ID());

		saveRecord(stat);

		return stat;
	}

	@Override
	public BigDecimal retrieveOpenItems(@NonNull final BPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = loadDataRecord(bpStats);

		final String trxName = ITrx.TRXNAME_ThreadInherited;

		BigDecimal openItems = null;

		final Object[] sqlParams = new Object[] { stats.getC_BPartner_ID() };
		final String sql = "SELECT "
				+ " currencyBase(OpenAmt,C_Currency_ID,DateInvoiced,AD_Client_ID,AD_Org_ID)"
				+ " FROM de_metas_endcustomer_fresh_reports.OpenItems_Report(now()::date, 'N', ?)";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final BigDecimal openAmt = rs.getBigDecimal(1);
				return openAmt != null ? openAmt : BigDecimal.ZERO;
			}
			else
			{
				return BigDecimal.ZERO;
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public BigDecimal retrieveSOCreditUsed(@NonNull final BPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = loadDataRecord(bpStats);
		final String trxName = ITrx.TRXNAME_None;

		final Object[] sqlParams = new Object[] { stats.getC_BPartner_ID() };
		final String sql = "SELECT OpenOrderAmt, OpenInvoiceAmt, UnallocatedPaymentAmt "
				+ "FROM C_BPartner_OpenAmounts_v  "
				+ "WHERE C_BPartner_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final BigDecimal openOrderAmt = rs.getBigDecimal(1);
				final BigDecimal openInvoiceAmt = rs.getBigDecimal(2);
				final BigDecimal unallocatedPaymentAmt = rs.getBigDecimal(3);
				final BigDecimal SO_CreditUsed = openInvoiceAmt.add(unallocatedPaymentAmt).add(openOrderAmt);
				return SO_CreditUsed;
			}
			else
			{
				return BigDecimal.ZERO;
			}

		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public void setSOCreditStatus(@NonNull final BPartnerStats bpStats, final CreditStatus soCreditStatus)
	{
		final I_C_BPartner_Stats stats = loadDataRecord(bpStats);

		stats.setSOCreditStatus(CreditStatus.toCodeOrNull(soCreditStatus));

		saveRecord(stats);

	}

	@Override
	public void setDeliveryCreditStatus(@NonNull final BPartnerStats bpStats, final CreditStatus deliveryCreditStatus)
	{
		final I_C_BPartner_Stats stats = loadDataRecord(bpStats);

		stats.setDelivery_CreditStatus(CreditStatus.toCodeOrNull(deliveryCreditStatus));

		saveRecord(stats);

	}

	@Override
	public I_C_BPartner_Stats loadDataRecord(@NonNull final BPartnerStats bpStats)
	{
		return load(bpStats.getRepoId(), I_C_BPartner_Stats.class);
	}

	@Override
	@Nullable
	public BigDecimal computeActualLifeTimeValue(@NonNull final BPartnerId partnerId)
	{
		BigDecimal actualLifeTimeValue = null;
		final Object[] sqlParams = new Object[] { partnerId.getRepoId() };

		// Legacy sql

		// AZ Goodwill -> BF2041226 : only count completed/closed docs.
		final String sql = "SELECT "
				+ "COALESCE ((SELECT SUM(currencyBase(i.GrandTotal,i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsSOTrx='Y' AND i.DocStatus IN ('CO','CL')),0) "
				+ "FROM C_BPartner bp " + "WHERE C_BPartner_ID=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);

			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				actualLifeTimeValue = rs.getBigDecimal(1);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return actualLifeTimeValue;
	}

}
