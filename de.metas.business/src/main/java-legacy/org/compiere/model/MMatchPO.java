/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.acct.api.IPostingService;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostingService;
import de.metas.currency.ICurrencyBL;
import de.metas.invoice.IMatchInvDAO;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/**
 * Match PO Model.
 * = Created when processing Shipment or Order
 * - Updates Order (delivered, invoiced)
 * - Creates PPV acct
 *
 * @author Jorg Janke
 * @version $Id: MMatchPO.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Bayu Cahya, Sistematika
 *         <li>BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2314749 ] MatchPO not considering currency PriceMatchDifference
 *
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 2215840 ] MatchPO Bug Collection
 *         <li>BF [ 2858043 ] Correct Included Tax in Average Costing
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MMatchPO extends X_M_MatchPO
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7189366329684552916L;

	static List<I_M_MatchPO> getByOrderLineAndInvoiceLine(final int C_OrderLine_ID, final int C_InvoiceLine_ID)
	{
		if (C_OrderLine_ID <= 0 || C_InvoiceLine_ID <= 0)
		{
			return ImmutableList.of();
		}
		else
		{
			return Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_MatchPO.class)
					.addEqualsFilter(I_M_MatchPO.COLUMN_C_OrderLine_ID, C_OrderLine_ID)
					.addEqualsFilter(I_M_MatchPO.COLUMN_C_InvoiceLine_ID, C_InvoiceLine_ID)
					.create()
					.listImmutable(I_M_MatchPO.class);
		}
	}

	/**
	 * Get PO Matches of receipt
	 *
	 * @param ctx context
	 * @param M_InOut_ID receipt
	 * @param trxName transaction
	 * @return array of matches
	 */
	static MMatchPO[] getInOut(final Properties ctx,
			final int M_InOut_ID, final String trxName)
	{
		if (M_InOut_ID == 0)
		{
			return new MMatchPO[] {};
		}
		//
		final String sql = "SELECT * FROM M_MatchPO m"
				+ " INNER JOIN M_InOutLine l ON (m.M_InOutLine_ID=l.M_InOutLine_ID) "
				+ "WHERE l.M_InOut_ID=?";
		final ArrayList<MMatchPO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, M_InOut_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MMatchPO(ctx, rs, trxName));
			}
		}
		catch (final Exception e)
		{
			logger.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		final MMatchPO[] retValue = new MMatchPO[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getInOut

	/**
	 * Get PO Matches of Invoice
	 *
	 * @param ctx context
	 * @param C_Invoice_ID invoice
	 * @param trxName transaction
	 * @return array of matches
	 */
	public static MMatchPO[] getInvoice(final Properties ctx,
			final int C_Invoice_ID, final String trxName)
	{
		if (C_Invoice_ID == 0)
		{
			return new MMatchPO[] {};
		}
		//
		final String sql = "SELECT * FROM M_MatchPO mi"
				+ " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID) "
				+ "WHERE il.C_Invoice_ID=?";
		final ArrayList<MMatchPO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, C_Invoice_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MMatchPO(ctx, rs, trxName));
			}
		}
		catch (final Exception e)
		{
			logger.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		final MMatchPO[] retValue = new MMatchPO[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getInvoice

	static List<I_M_MatchPO> getOrderLine(final int orderLineId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_MatchPO.class)
				.addEqualsFilter(I_M_MatchPO.COLUMN_C_OrderLine_ID, orderLineId)
				.orderBy(I_M_MatchPO.COLUMN_M_MatchPO_ID)
				.create()
				.listImmutable(I_M_MatchPO.class);
	}

	/**
	 * Retrieves or creates a MMatchPO instance. An existing instance is retrieved if it references the same <code>C_OrderLine_ID</code> as either the given <code>iLine</code> or <code>sLine</code>.
	 * The iLine takes precendence.
	 *
	 * @param iLine invoice line optional; but not that one of this an <code>sLine</code> are required
	 * @param receiptLine receipt line optional; but not that one of this an <code>iLine</code> are required
	 * @param dateTrx date used only if a new MMatchPO is created
	 * @param qty qty filter parameter: an existing record that references the <code>C_OrderLine_ID</code> of the given <code>iLine</code> or <code>sLine</code> will be ignored, if it's qty differs
	 *            from this parameter value
	 * @return Match Record; the record is not saved!
	 */
	public static I_M_MatchPO create(
			final I_C_InvoiceLine iLine,
			final I_M_InOutLine receiptLine,
			final Timestamp dateTrx,
			final BigDecimal qty)
	{
		OrderLineId orderLineId = null;
		if (iLine != null)
		{
			orderLineId = OrderLineId.ofRepoIdOrNull(iLine.getC_OrderLine_ID());
		}
		if (receiptLine != null)
		{
			orderLineId = OrderLineId.ofRepoIdOrNull(receiptLine.getC_OrderLine_ID());
		}

		I_M_MatchPO retValue = null;

		final List<I_M_MatchPO> existingMatchPOs = getByOrderLineId(orderLineId);
		for (final I_M_MatchPO mpo : existingMatchPOs)
		{
			if (qty.compareTo(mpo.getQty()) == 0)
			{
				final int mpoASIId = mpo.getM_AttributeSetInstance_ID();
				if (iLine != null)
				{
					if (mpo.getC_InvoiceLine_ID() <= 0
							|| mpo.getC_InvoiceLine_ID() == iLine.getC_InvoiceLine_ID())
					{
						mpo.setC_InvoiceLine(iLine);
						if (iLine.getM_AttributeSetInstance_ID() > 0)
						{
							if (mpoASIId <= 0)
							{
								mpo.setM_AttributeSetInstance_ID(iLine.getM_AttributeSetInstance_ID());
							}
							else if (receiptLine != null // 07742: Try ASI matching only if given receipt line was not null
									&& mpoASIId != receiptLine.getM_AttributeSetInstance_ID())
							{
								continue;
							}
						}
					}
					else
					{
						continue;
					}
				}
				if (receiptLine != null)
				{
					final int receiptLineId = receiptLine.getM_InOutLine_ID();
					if (mpo.getM_InOutLine_ID() <= 0
							|| mpo.getM_InOutLine_ID() == receiptLineId)
					{
						mpo.setM_InOutLine_ID(receiptLineId);
						if (receiptLine.getM_AttributeSetInstance_ID() > 0)
						{
							if (mpoASIId == 0)
							{
								mpo.setM_AttributeSetInstance_ID(receiptLine.getM_AttributeSetInstance_ID());
							}
							else if (iLine != null // 07742: Try ASI matching only if given receipt line was not null
									&& mpoASIId != iLine.getM_AttributeSetInstance_ID())
							{
								continue;
							}
						}
					}
					else
					{
						continue;
					}
				}

				//
				// 07742: Set the invoice line's receipt line ID from the mpo if matching could be found
				if (mpo != null)
				{
					int receiptLineId = mpo.getM_InOutLine_ID(); // default is the MPO's receipt line
					if (receiptLine != null)
					{
						receiptLineId = receiptLine.getM_InOutLine_ID(); // if receipt line is given, use it's IOL
					}

					final I_C_InvoiceLine invoiceLine = mpo.getC_InvoiceLine();
					if (invoiceLine != null && receiptLineId > 0)
					{
						invoiceLine.setM_InOutLine_ID(receiptLineId);
						InterfaceWrapperHelper.save(invoiceLine, ITrx.TRXNAME_ThreadInherited); // save in the shipment line's transaction

						if (iLine != null)
						{
							InterfaceWrapperHelper.refresh(iLine); // if invoiceLine is also matched, refresh it so that the PO is on the same update level as the MatchPO's
						}
					}
				}

				retValue = mpo;
				break;
			}
		}

		//
		// Create New
		if (retValue == null)
		{
			if (receiptLine != null)
			{
				retValue = new MMatchPO(receiptLine, dateTrx, qty);
				if (iLine != null)
				{
					retValue.setC_InvoiceLine(iLine);
				}
			}
			else if (iLine != null)
			{
				retValue = new MMatchPO(iLine, dateTrx, qty);
			}
		}

		//
		if (retValue != null)
		{
			saveRecord(retValue);
		}

		//
		// Post eligible MatchPOs
		{
			final HashSet<Integer> matchPOIdsToPost = new HashSet<>();
			existingMatchPOs.stream()
					.filter(matchPO -> matchPO.getM_InOutLine_ID() > 0)
					.forEach(matchPO -> matchPOIdsToPost.add(matchPO.getM_MatchPO_ID()));
			if (retValue != null && retValue.getM_InOutLine_ID() > 0)
			{
				matchPOIdsToPost.add(retValue.getM_MatchPO_ID());
			}
			enqueToPost(matchPOIdsToPost);
		}

		return retValue;
	}	// create

	private static List<I_M_MatchPO> getByOrderLineId(final OrderLineId orderLineId)
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
				.list();
	}

	private static void enqueToPost(@NonNull final Set<Integer> matchPOIds)
	{
		if (matchPOIds.isEmpty())
		{
			return;
		}

		final IPostingService postingService = Services.get(IPostingService.class);
		final ClientId clientId = ClientId.ofRepoId(Env.getAD_Client_ID());

		matchPOIds.forEach(matchPOId -> postingService.newPostingRequest()
				.setClientId(clientId)
				.setDocumentRef(TableRecordReference.of(I_M_MatchPO.Table_Name, matchPOId)) // the document to be posted
				.setFailOnError(false) // don't fail because we don't want to fail the main document posting because one of it's depending documents are failing
				.setPostImmediate(PostImmediate.No) // no, just enqueue it
				.setForce(false) // don't force it
				.postIt());
	}

	private static final Logger logger = LogManager.getLogger(MMatchPO.class);

	public MMatchPO(final Properties ctx, final int M_MatchPO_ID, final String trxName)
	{
		super(ctx, M_MatchPO_ID, trxName);
		if (is_new())
		{
			// setC_OrderLine_ID (0);
			// setDateTrx (new Timestamp(System.currentTimeMillis()));
			// setM_InOutLine_ID (0);
			// setM_Product_ID (0);
			setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
			// setQty (BigDecimal.ZERO);
			setPosted(false);
			setProcessed(false);
			setProcessing(false);
		}
	}

	public MMatchPO(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	private MMatchPO(final I_M_InOutLine sLine, final Timestamp dateTrx, final BigDecimal qty)
	{
		this(Env.getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		setAD_Org_ID(sLine.getAD_Org_ID());
		setM_InOutLine_ID(sLine.getM_InOutLine_ID());
		setC_OrderLine_ID(sLine.getC_OrderLine_ID());
		if (dateTrx != null)
		{
			setDateTrx(dateTrx);
		}
		setM_Product_ID(sLine.getM_Product_ID());
		setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
		setQty(qty);
		setProcessed(true);		// auto
	}	// MMatchPO

	private MMatchPO(final I_C_InvoiceLine iLine, final Timestamp dateTrx, final BigDecimal qty)
	{
		this(Env.getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		setAD_Org_ID(iLine.getAD_Org_ID());
		setC_InvoiceLine(iLine);
		if (iLine.getC_OrderLine_ID() != 0)
		{
			setC_OrderLine_ID(iLine.getC_OrderLine_ID());
		}
		if (dateTrx != null)
		{
			setDateTrx(dateTrx);
		}
		setM_Product_ID(iLine.getM_Product_ID());
		setM_AttributeSetInstance_ID(iLine.getM_AttributeSetInstance_ID());
		setQty(qty);
		setProcessed(true);		// auto
	}	// MMatchPO

	/**
	 * Get PriceActual from Invoice and convert it to Order Currency
	 *
	 * @return Price Actual in Order Currency
	 */
	private BigDecimal getInvoicePriceActual()
	{
		final I_C_InvoiceLine iLine = getC_InvoiceLine();
		final I_C_Invoice invoice = iLine.getC_Invoice();
		final I_C_Order order = getC_OrderLine().getC_Order();

		BigDecimal priceActual = iLine.getPriceActual();
		final int invoiceCurrency_ID = invoice.getC_Currency_ID();
		final int orderCurrency_ID = order.getC_Currency_ID();
		if (invoiceCurrency_ID != orderCurrency_ID)
		{
			priceActual = Services.get(ICurrencyBL.class).convert(getCtx(), priceActual, invoiceCurrency_ID, orderCurrency_ID,
					invoice.getDateInvoiced(), invoice.getC_ConversionType_ID(),
					getAD_Client_ID(), getAD_Org_ID());
		}
		return priceActual;
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Set Trx Date
		if (getDateTrx() == null)
		{
			setDateTrx(SystemTime.asDayTimestamp());
		}

		// Set Acct Date
		if (getDateAcct() == null)
		{
			Timestamp ts = getNewerDateAcct();
			if (ts == null)
			{
				ts = getDateTrx();
			}
			setDateAcct(ts);
		}

		// Set ASI from Receipt
		final int mpoASIId = getM_AttributeSetInstance_ID();
		if (mpoASIId <= 0 && getM_InOutLine_ID() > 0)
		{
			final I_M_InOutLine iol = getM_InOutLine();
			setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
		}

		// Bayu, Sistematika
		// BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
		// If newRecord, set c_invoiceline_id while null
		if (newRecord && getC_InvoiceLine_ID() <= 0)
		{
			final List<I_M_MatchInv> matchInvs = Services.get(IMatchInvDAO.class).retrieveForInOutLine(getM_InOutLine());
			for (final I_M_MatchInv matchInv : matchInvs)
			{
				if (matchInv.getC_InvoiceLine_ID() > 0 && matchInv.getM_AttributeSetInstance_ID() == mpoASIId)
				{
					setC_InvoiceLine_ID(matchInv.getC_InvoiceLine_ID());
					break;
				}
			}
		}
		// end Bayu

		// Find OrderLine
		if (getC_OrderLine_ID() <= 0)
		{
			final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
			if (invoiceLine != null && invoiceLine.getC_OrderLine_ID() > 0)
			{
				setC_OrderLine_ID(invoiceLine.getC_OrderLine_ID());
			}

			if (getC_OrderLine_ID() <= 0)
			{
				final I_M_InOutLine inoutLine = getM_InOutLine();
				if (inoutLine != null && inoutLine.getC_OrderLine_ID() > 0)
				{
					setC_OrderLine_ID(inoutLine.getC_OrderLine_ID());
					if (invoiceLine != null)
					{
						invoiceLine.setC_OrderLine_ID(inoutLine.getC_OrderLine_ID());
						InterfaceWrapperHelper.save(invoiceLine);
					}
				}
			}
		}	// find order line

		// Price Match Approval
		if (getC_OrderLine_ID() > 0
				&& getC_InvoiceLine_ID() > 0
				&& (newRecord || is_ValueChanged(COLUMNNAME_C_OrderLine_ID) || is_ValueChanged(COLUMNNAME_C_InvoiceLine_ID)))
		{
			final BigDecimal poPrice = getC_OrderLine().getPriceActual();
			final BigDecimal invPrice = getInvoicePriceActual();
			BigDecimal difference = poPrice.subtract(invPrice);
			if (difference.signum() != 0)
			{
				difference = difference.multiply(getQty());
				setPriceMatchDifference(difference);
				// Approval
				final BPartnerId bpartnerId = BPartnerId.ofRepoId(getC_OrderLine().getC_BPartner_ID());
				final I_C_BP_Group bpGroup = Services.get(IBPGroupDAO.class).getByBPartnerId(bpartnerId);
				final BigDecimal matchTolerance = bpGroup.getPriceMatchTolerance();
				if (matchTolerance != null && matchTolerance.signum() != 0)
				{
					final BigDecimal poAmt = poPrice.multiply(getQty());
					BigDecimal maxTolerance = poAmt.multiply(matchTolerance);
					maxTolerance = maxTolerance.abs().divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
					difference = difference.abs();
					final boolean ok = difference.compareTo(maxTolerance) <= 0;
					logger.info("Difference=" + getPriceMatchDifference() + ", Max=" + maxTolerance + " => " + ok);
					setIsApproved(ok);
				}
			}
			else
			{
				setPriceMatchDifference(difference);
				setIsApproved(true);
			}
		}

		return true;
	}	// beforeSave

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success)
		{
			return success;
		}

		// Purchase Order Delivered/Invoiced
		// (Reserved in VMatch and MInOut.completeIt)
		if (getC_OrderLine_ID() > 0)
		{
			final I_C_OrderLine orderLine = getC_OrderLine();

			if (InterfaceWrapperHelper.isValueChanged(this, COLUMNNAME_M_InOutLine_ID) /* task 09084 => */ || newRecord)
			{
				if (getM_InOutLine_ID() > 0)
				{
					// a new delivery line was linked to the order line => add the qty
					orderLine.setQtyDelivered(orderLine.getQtyDelivered().add(getQty()));
					orderLine.setDateDelivered(getDateTrx());	// overwrite=last
				}
				else if (getM_InOutLine_ID() <= 0 && !newRecord)
				{
					// a previously linked delivery line was unlinked from an existing matchPO (and thus from the order line) => subtract the qty
					orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(getQty()));
				}
			}

			if (InterfaceWrapperHelper.isValueChanged(this, COLUMNNAME_C_InvoiceLine_ID) /* task 09084 => */ || newRecord)
			{
				if (getC_InvoiceLine_ID() > 0)
				{
					// a new invoice line was linked to the order line => add the qty
					orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().add(getQty()));
					orderLine.setDateInvoiced(getDateTrx());	// overwrite=last
				}
				else if (getC_InvoiceLine_ID() <= 0 && !newRecord)
				{
					// a previously linked invoice line was unlinked from an existing matchPO (and thus from the order line) => subtract the qty
					orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().subtract(getQty()));
				}
			}

			// Update Order ASI if full match
			if (orderLine.getM_AttributeSetInstance_ID() == 0
					&& getM_InOutLine_ID() > 0)
			{
				final I_M_InOutLine iol = getM_InOutLine();
				if (iol.getMovementQty().compareTo(orderLine.getQtyOrdered()) == 0)
				{
					orderLine.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
				}
			}
			InterfaceWrapperHelper.save(orderLine);
		}

		//
		return true;
	}	// afterSave

	/**
	 * Get the later Date Acct from invoice or shipment
	 *
	 * @return date or null
	 */
	private Timestamp getNewerDateAcct()
	{
		Timestamp invoiceDate = null;
		Timestamp shipDate = null;

		if (getC_InvoiceLine_ID() > 0)
		{
			final String sql = "SELECT i.DateAcct "
					+ "FROM C_InvoiceLine il"
					+ " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID) "
					+ "WHERE C_InvoiceLine_ID=?";
			invoiceDate = DB.getSQLValueTS(null, sql, getC_InvoiceLine_ID());
		}
		//
		if (getM_InOutLine_ID() > 0)
		{
			final String sql = "SELECT io.DateAcct "
					+ "FROM M_InOutLine iol"
					+ " INNER JOIN M_InOut io ON (io.M_InOut_ID=iol.M_InOut_ID) "
					+ "WHERE iol.M_InOutLine_ID=?";
			shipDate = DB.getSQLValueTS(null, sql, getM_InOutLine_ID());
		}
		//
		// Assuming that order date is always earlier
		if (invoiceDate == null)
		{
			return shipDate;
		}
		if (shipDate == null)
		{
			return invoiceDate;
		}
		if (invoiceDate.after(shipDate))
		{
			return invoiceDate;
		}
		return shipDate;
	}	// getNewerDateAcct

	@Override
	protected boolean beforeDelete()
	{
		if (isPosted())
		{
			MPeriod.testPeriodOpen(getCtx(), getDateTrx(), X_C_DocType.DOCBASETYPE_MatchPO, getAD_Org_ID());
			setPosted(false);
			Services.get(IFactAcctDAO.class).deleteForDocumentModel(this);
		}

		final ICostingService costDetailService = Adempiere.getBean(ICostingService.class);
		costDetailService.voidAndDeleteForDocument(CostingDocumentRef.ofMatchPOId(getM_MatchPO_ID()));

		return true;
	}

	@Override
	protected boolean afterDelete(final boolean success)
	{
		if (!success)
		{
			return success;
		}

		// Order Delivered/Invoiced
		// (Reserved in VMatch and MInOut.completeIt)
		final I_C_OrderLine orderLine = getC_OrderLine();
		if (orderLine != null)
		{
			if (getM_InOutLine_ID() > 0)
			{
				orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(getQty()));
			}
			if (getC_InvoiceLine_ID() > 0)
			{
				orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().subtract(getQty()));
			}
			InterfaceWrapperHelper.save(orderLine);
		}

		return true;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("MMatchPO[")
				.append(getM_MatchPO_ID())
				.append(",Qty=").append(getQty())
				.append(",C_OrderLine_ID=").append(getC_OrderLine_ID())
				.append(",M_InOutLine_ID=").append(getM_InOutLine_ID())
				.append(",C_InvoiceLine_ID=").append(getC_InvoiceLine_ID())
				.append("]")
				.toString();
	}
}	// MMatchPO
