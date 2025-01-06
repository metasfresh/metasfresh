package de.metas.order.impl;

import com.google.common.collect.ImmutableList;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.order.IMatchPODAO;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.MMatchPO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MatchPODAO implements IMatchPODAO
{
	@Override
	public List<I_M_MatchPO> getByOrderLineAndInvoiceLine(
			@NonNull final OrderLineId orderLineId,
			@NonNull final InvoiceAndLineId invoiceAndLineId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_MatchPO.class)
				.addEqualsFilter(I_M_MatchPO.COLUMN_C_OrderLine_ID, orderLineId)
				.addEqualsFilter(I_M_MatchPO.COLUMN_C_InvoiceLine_ID, invoiceAndLineId)
				.create()
				.listImmutable(I_M_MatchPO.class);
	}

	@Override
	public List<I_M_MatchPO> getByReceiptId(@NonNull final InOutId inOutId)
	{
		final String sql = "SELECT * FROM M_MatchPO m"
				+ " INNER JOIN M_InOutLine l ON (m.M_InOutLine_ID=l.M_InOutLine_ID) "
				+ "WHERE l.M_InOut_ID=?";
		final List<Object> sqlParams = Collections.singletonList(inOutId);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<I_M_MatchPO> result = new ArrayList<>();
			while (rs.next())
			{
				result.add(new MMatchPO(Env.getCtx(), rs, ITrx.TRXNAME_ThreadInherited));
			}

			return result;
		}
		catch (final Exception e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}	// getInOut

	@Override
	public List<I_M_MatchPO> getByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		final String sql = "SELECT * FROM M_MatchPO mi"
				+ " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID) "
				+ "WHERE il.C_Invoice_ID=?";
		final List<Object> sqlParams = Collections.singletonList(invoiceId);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<I_M_MatchPO> result = new ArrayList<>();
			while (rs.next())
			{
				result.add(new MMatchPO(Env.getCtx(), rs, ITrx.TRXNAME_ThreadInherited));
			}
			return result;
		}
		catch (final Exception e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public List<I_M_MatchPO> getByOrderLineId(final OrderLineId orderLineId)
	{
		if (orderLineId == null)
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_MatchPO.class)
				.addEqualsFilter(I_M_MatchPO.COLUMN_C_OrderLine_ID, orderLineId)
				.orderBy(I_M_MatchPO.COLUMN_C_OrderLine_ID)
				.create()
				.listImmutable(I_M_MatchPO.class);
	}

}
