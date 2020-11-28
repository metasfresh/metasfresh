package org.compiere.model;

/*
 * #%L
 * de.metas.banking.base
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.banking.model.X_C_RecurrentPaymentLine;

@SuppressWarnings("serial")
public class MRecurrentPaymentLine extends X_C_RecurrentPaymentLine {

	/** Static Logger */
	private static final Logger logger = LogManager.getLogger(MRecurrentPaymentLine.class);

	/**
	 * Standard Constructor
	 * 
	 * @param ctx
	 *            context
	 * @param C_RecurrentPayment_ID
	 *            id
	 * @param trxName
	 *            transaction
	 */
	public MRecurrentPaymentLine(Properties ctx, int C_RecurrentPaymentLine_ID,
			String trxName) {
		super(ctx, C_RecurrentPaymentLine_ID, trxName);
	} // MRecurrentPayment

	/**
	 * Load Constructor
	 * 
	 * @param ctx
	 *            Current context
	 * @param rs
	 *            result set
	 * @param trxName
	 *            transaction
	 */
	public MRecurrentPaymentLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	} // MRecurrentPayment

	/**
	 * Returns a List containing all (active) due RecurrentPaymentLines. A line
	 * is due if it's frequency type and frequency is reached and at least one
	 * invoice is missing.
	 * 
	 * @return list with due recurrent payment lines.
	 */
	public static List<MRecurrentPaymentLine> getAllDueRecurrentPaymentLines(
			String trxName) {
		final Properties ctx = Env.getCtx();
		final ArrayList<MRecurrentPaymentLine> dueLines = new ArrayList<MRecurrentPaymentLine>();
		final ArrayList<MRecurrentPaymentLine> activeLines = new ArrayList<MRecurrentPaymentLine>();
		// get all active lines
		String sql = "SELECT C_RecurrentPaymentLine_ID FROM C_RecurrentPaymentLine "
				+ "WHERE isActive='Y' AND AD_Client_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, Env.getAD_Client_ID(ctx));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				activeLines.add(new MRecurrentPaymentLine(ctx, rs.getInt(1),
						trxName));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(ex.getLocalizedMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		for (MRecurrentPaymentLine line : activeLines) {
			long actualTime = System.currentTimeMillis();
			// check if line is due
			if (line.getDateFrom().getTime() < actualTime) {
				if (line.getDateTo() == null
						|| line.getDateTo().getTime() > actualTime) {
					// check already generated invoices (check history)
					// find newest invoice, add recurrent payment line
					// interval
					Timestamp lastDate = getLatestHistoryDateInvoicedForLine(line.get_ID(), trxName);
					if (lastDate != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTimeInMillis(lastDate.getTime());
						int frequency = line.getFrequency();
						if (MRecurrentPaymentLine.FREQUENCYTYPE_Month
								.equals(line.getFrequencyType())) {
							cal.add(Calendar.MONTH, frequency);
						} else if (MRecurrentPaymentLine.FREQUENCYTYPE_Day
								.equals(line.getFrequencyType())) {
							cal.add(Calendar.DAY_OF_MONTH, frequency);
						} else {
							logger.error("Unimplemented frequency type!");
						}

						if (cal.getTimeInMillis() < actualTime) {
							// if calculated date < actual date: add to due
							// payment list
							dueLines.add(line);
						}
					} else {
						// no history entries found: add to due payment list
						dueLines.add(line);
					}

				}
			}
		}
		return dueLines;
	}


	/**
	 * Return the last date invoiced from the Payment History of the given
	 * MRecurrentPaymentLine or null if no History exists.
	 * 
	 * @param recurrentPaymentLineId
	 * @param trxName
	 * @return
	 */
	public static Timestamp getLatestHistoryDateInvoicedForLine(
			int recurrentPaymentLineId, String trxName) {
		Timestamp lastDate = null;
		String sql = "SELECT max(i.DateInvoiced) FROM C_RecurrentPaymentHistory ph"
				+ " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=ph.C_Invoice_ID)"
				+ " WHERE ph.C_RecurrentPaymentLine_ID=? AND ph.AD_Client_ID=? "
				+ " AND i.DocStatus IN ('CO', 'CL')";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, recurrentPaymentLineId);
			pstmt.setInt(2, Env.getAD_Client_ID(Env.getCtx()));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				lastDate = rs.getTimestamp(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(ex.getLocalizedMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return lastDate;
	}
}
