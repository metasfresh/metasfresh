/******************************************************************************
 * Copyright (C) 2009 Metas                                                   *
 * Copyright (C) 2009 Carlos Ruiz                                             *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package de.metas.commission.form;

/*
 * #%L
 * de.metas.commission.client
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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Vector;

import org.adempiere.model.I_AD_RelationType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MRelation;
import org.adempiere.model.MRelationType;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.grid.CreateFrom;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.commission.interfaces.I_C_BPartner_Location;
import de.metas.commission.model.I_C_Invoice_VAT_Corr_Candidates_v1;
import de.metas.commission.service.IBPartnerDAO;
import de.metas.commission.util.CommissionConstants;
import de.metas.document.IDocumentPA;
import de.metas.tax.api.ITaxBL;

/**
 * 
 * @author ts, used a work of Carlos Ruiz as reference
 * 
 */
public class CreateCorrections extends CreateFrom
{

	final I_C_Invoice invoice;

	final String MSG_CORRECTION_MINUS_2P = "AdvCom_CreateCorrections_Minus_Il";
	final String MSG_CORRECTION_PLUS_2P = "AdvCom_CreateCorrections_Plus_Il";

	/**
	 * Protected Constructor
	 * 
	 * @param mTab MTab
	 */
	public CreateCorrections(final I_C_Invoice invoice)
	{
		super(null);
		this.invoice = invoice;
		log.info(invoice.toString());
	} // VCreateFromInvoice

	/**
	 * Dynamic Init
	 * 
	 * @return true if initialized
	 */
	@Override
	public boolean dynInit() throws Exception
	{
		log.info("");

		final Properties ctx = Env.getCtx();

		final IDocumentPA docPA = Services.get(IDocumentPA.class);

		final I_C_DocType dt = docPA.retrieve(ctx, Env.getAD_Org_ID(ctx), Constants.DOCBASETYPE_AEInvoice, CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CORRECTION, true, null);

		setTitle(dt.getName() + " .. " + Msg.translate(ctx, "CreateFrom"));

		return true;
	} // dynInit

	/**************************************************************************
	 * Construct SQL Where Clause and define parameters (setParameters needs to set parameters) Includes first AND
	 * 
	 * @return sql where clause
	 */
	public String getSQLWhere(
			final int bPartnerId,
			final Timestamp DateFrom,
			final Timestamp DateTo)
	{
		final StringBuilder sql = new StringBuilder("WHERE ");

		sql.append("C_Bpartner_ID = ?");

		if (DateFrom != null || DateTo != null)
		{
			final Timestamp from = DateFrom;
			final Timestamp to = DateTo;

			if (from == null && to != null)
			{
				sql.append(" AND TRUNC(DateInvoiced) <= ?");
			}
			else if (from != null && to == null)
			{
				sql.append(" AND TRUNC(DateInvoiced) >= ?");
			}
			else if (from != null && to != null)
			{
				sql.append(" AND TRUNC(DateInvoiced) BETWEEN ? AND ?");
			}
		}
		//
		log.debug(sql.toString());

		final String orgWhere = Env.getUserRolePermissions().getOrgWhere(false);

		sql.append(" AND (");
		sql.append(orgWhere);
		sql.append(")");
		return sql.toString();
	} // getSQLWhere

	/**
	 * Set Parameters for Query. (as defined in getSQLWhere)
	 * 
	 * @param pstmt statement
	 * @param forCount for counting records
	 * @throws SQLException
	 */
	void setParameters(
			final PreparedStatement pstmt,
			boolean forCount,
			final int bPartnerId,
			final Timestamp DateFrom,
			final Timestamp DateTo) throws SQLException
	{
		int index = 1;

		pstmt.setInt(index++, bPartnerId);

		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = DateFrom;
			Timestamp to = DateTo;
			log.debug("Date From=" + from + ", To=" + to);

			if (from == null && to != null)
			{
				pstmt.setTimestamp(index++, to);
			}
			else if (from != null && to == null)
			{
				pstmt.setTimestamp(index++, from);
			}
			else if (from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}

	} // setParameters

	// /**
	// * Get SQL WHERE parameter
	// *
	// * @param f
	// * field
	// * @return Upper case text with % at the end
	// */
	// private String getSQLText(String text)
	// {
	// String s = text.toUpperCase();
	// if (!s.endsWith("%"))
	// s += "%";
	// log.debug("String=" + s);
	// return s;
	// } // getSQLText

	protected Vector<Vector<Object>> getPackageData(final Timestamp DateFrom, final Timestamp DateTo)
	{
		final int bPartnerId = invoice.getC_BPartner_ID();

		final Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append("FROM " + I_C_Invoice_VAT_Corr_Candidates_v1.Table_Name + " i ");

		sql.append(getSQLWhere(bPartnerId, DateFrom, DateTo));
		sql.append(" ORDER BY i.DocumentNo, i.Line");

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			setParameters(pstmt, false, bPartnerId, DateFrom, DateTo);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final Vector<Object> line = new Vector<Object>(6);

				line.add(Boolean.FALSE); // 0-Selection

				final KeyNamePair ppBp = new KeyNamePair(rs.getInt("C_Bpartner_ID"), rs.getString("C_BPartner_Name"));
				line.add(ppBp); // 1-sales rep

				line.add(rs.getTimestamp("DateInvoiced")); // 2-DateInvoiced

				final KeyNamePair ppInv = new KeyNamePair(rs.getInt("C_Invoice_ID"), rs.getString("DocumentNo"));
				line.add(ppInv); // 3-commission calculation to correct

				final KeyNamePair ppIl = new KeyNamePair(rs.getInt("C_InvoiceLine_ID"), rs.getBigDecimal("Line").toString());
				line.add(ppIl); // 4-invoice line

				line.add(rs.getBigDecimal("LineNetAmt")); // 5-NetLineAmt

				final KeyNamePair ppTc = new KeyNamePair(rs.getInt("C_TaxCategory_ID"), rs.getString("C_TaxCategory_Name"));
				line.add(ppTc); // 6-TaxCategory

				line.add(rs.getBigDecimal("Rate")); // 7-Rate

				line.add(rs.getBoolean("IsSmallBusinessAtDate")); // 8-IsSmallBusinessAtDate

				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return data;
	}

	@Override
	public void info()
	{

	}

	protected void configureMiniTable(final IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false); // 0-Selection
		miniTable.setColumnClass(1, String.class, true); // 1-sales rep
		miniTable.setColumnClass(2, Timestamp.class, true); // 2-DateInvoiced
		miniTable.setColumnClass(3, String.class, true); // 3-commission calculation to correct
		miniTable.setColumnClass(4, String.class, true); // 4-invoice line
		miniTable.setColumnClass(5, BigDecimal.class, true); // 5-LineNetAmt
		miniTable.setColumnClass(6, String.class, true); // 6-TaxCategory
		miniTable.setColumnClass(7, BigDecimal.class, true); // 7-Rate
		miniTable.setColumnClass(8, Boolean.class, true); // 8-IsSmallBusinessAtDate

		// Table UI
		miniTable.autoSize();
	}

	/**
	 * Save Statement - Insert Data
	 * 
	 * @return true if saved
	 */
	@Override
	public boolean save(final IMiniTable miniTable, final String trxName)
	{
		// fixed values

		final Properties ctx = Env.getCtx();
		final MInvoice thisInvoice = new MInvoice(ctx, invoice.getC_Invoice_ID(), trxName);

		log.info(thisInvoice.toString());

		final IBPartnerDAO bPartnerBl = Services.get(IBPartnerDAO.class);

		final I_C_BPartner_Location payrollLocPO = bPartnerBl.retrieveCommissionToLocation(ctx, invoice.getC_BPartner_ID(), trxName);

		final I_C_BPartner_Location payrollLoc = InterfaceWrapperHelper.create(payrollLocPO, I_C_BPartner_Location.class);

		final int countryID = payrollLocPO.getC_Location().getC_Country_ID();

		final I_AD_RelationType relType = MRelationType.retrieveForInternalName(ctx, "com_calcline2corrline", trxName);

		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			final boolean isSelected = ((Boolean)miniTable.getValueAt(i, 0));
			if (!isSelected)
			{
				continue;
			}
			final KeyNamePair ppIl = (KeyNamePair)miniTable.getValueAt(i, 4); // 4-Invoice line
			final int invoiceLineId = ppIl.getKey();
			final String lineNo = ppIl.getName();

			final KeyNamePair ppTc = (KeyNamePair)miniTable.getValueAt(i, 6); // 6-TaxCategory
			final int taxCategoryId = ppTc.getKey();

			final KeyNamePair ppInv = (KeyNamePair)miniTable.getValueAt(i, 3); // 3-commission calculation to correct
			final String documentNo = ppInv.getName();

			final boolean isSmallBusiness = ((Boolean)miniTable.getValueAt(i, 8)); // 8-IsSmallBusinessAtDate

			final MInvoiceLine lineToCorrect = new MInvoiceLine(ctx, invoiceLineId, trxName);
			log.debug("Correcting " + lineToCorrect);

			final int plusTaxId;
			if (isSmallBusiness)
			{
				// "Kleinunternehmer-Regel"
				plusTaxId = Services.get(ITaxBL.class).getExemptTax(ctx, lineToCorrect.getAD_Org_ID());
				assert plusTaxId > 0;
			}
			else
			{
				final ITaxBL taxBL = Services.get(ITaxBL.class);
				plusTaxId = taxBL.retrieveTaxIdForCategory(
						ctx,
						countryID, thisInvoice.getAD_Org_ID(), payrollLoc, thisInvoice.getDateInvoiced(),
						taxCategoryId, false,
						trxName, false);
			}

			if (plusTaxId == lineToCorrect.getC_Tax_ID())
			{
				continue;
			}

			final MInvoiceLine minusLine = new MInvoiceLine(thisInvoice);
			minusLine.setC_Tax_ID(lineToCorrect.getC_Tax_ID());
			minusLine.setC_TaxCategory(lineToCorrect.getC_TaxCategory());
			minusLine.setQtyInvoiced(BigDecimal.ONE);
			minusLine.setQtyEntered(BigDecimal.ONE);
			minusLine.setPriceActual(lineToCorrect.getPriceActual().negate());

			final String minusDescription = Msg.getMsg(ctx, MSG_CORRECTION_MINUS_2P, new Object[] { documentNo, lineNo }) + " " + lineToCorrect.getDescription();
			minusLine.setDescription(minusDescription);
			minusLine.saveEx();

			final MInvoiceLine plusLine = new MInvoiceLine(thisInvoice);
			plusLine.setC_Tax_ID(plusTaxId);
			plusLine.setQtyInvoiced(BigDecimal.ONE);
			plusLine.setQtyEntered(BigDecimal.ONE);
			plusLine.setPriceActual(lineToCorrect.getPriceActual());

			final String plusDescription = Msg.getMsg(ctx, MSG_CORRECTION_PLUS_2P, new Object[] { documentNo, lineNo }) + " " + lineToCorrect.getDescription();
			plusLine.setDescription(plusDescription);
			plusLine.saveEx();

			MRelation.add(ctx, relType, lineToCorrect.get_ID(), minusLine.get_ID(), trxName);
			MRelation.add(ctx, relType, lineToCorrect.get_ID(), plusLine.get_ID(), trxName);

		} // for all rows

		thisInvoice.renumberLines(5);

		return true;
	} // save

	protected Vector<String> getOISColumnNames()
	{
		final Properties ctx = Env.getCtx();
		final IDocumentPA docPA = Services.get(IDocumentPA.class);

		final I_C_DocType dt = docPA.retrieve(ctx, Env.getAD_Org_ID(ctx), Constants.DOCBASETYPE_AEInvoice, CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CALC, true, null);

		// Header Info
		final Vector<String> columnNames = new Vector<String>(9);

		columnNames.add(Msg.getMsg(Env.getCtx(), "Select")); // 0-Selection
		columnNames.add(Msg.translate(Env.getCtx(), "C_BPartner_ID")); // 1-sales rep
		columnNames.add(Msg.translate(Env.getCtx(), "DateInvoiced")); // 2-DateInvoiced
		columnNames.add(dt.getName()); // 3-commission calculation to correct
		columnNames.add(Msg.translate(Env.getCtx(), "Line")); // 4-invoice line
		columnNames.add(Msg.translate(Env.getCtx(), "LineNetAmt")); // 5-LineNetAmt
		columnNames.add(Msg.translate(Env.getCtx(), "C_TaxCategory_ID")); // 6-TaxCategory
		columnNames.add(Msg.translate(Env.getCtx(), "TaxRate")); // 7-Rate
		columnNames.add(Msg.translate(Env.getCtx(), "IsSmallBusinessAtDate")); // 8-IsSmallBusinessAtDate

		return columnNames;
	}
}
