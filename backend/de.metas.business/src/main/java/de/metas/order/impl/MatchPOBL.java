package de.metas.order.impl;

import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.acct.api.IPostingService;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.order.IMatchPOBL;
import de.metas.order.IMatchPODAO;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

public class MatchPOBL implements IMatchPOBL
{
	private final IMatchPODAO matchPODAO = Services.get(IMatchPODAO.class);

	@Override
	public I_M_MatchPO create(
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

		final List<I_M_MatchPO> existingMatchPOs = Services.get(IMatchPODAO.class).getByOrderLineId(orderLineId);
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
				retValue = createMatchPO(receiptLine, dateTrx, qty);
				if (iLine != null)
				{
					retValue.setC_InvoiceLine(iLine);
				}
			}
			else if (iLine != null)
			{
				retValue = createMatchPO(iLine, dateTrx, qty);
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
	}

	private I_M_MatchPO createMatchPO(final I_M_InOutLine sLine, final Timestamp dateTrx, final BigDecimal qty)
	{
		final I_M_MatchPO matchPO = newInstance(I_M_MatchPO.class);
		matchPO.setAD_Org_ID(sLine.getAD_Org_ID());
		matchPO.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
		matchPO.setC_OrderLine_ID(sLine.getC_OrderLine_ID());
		if (dateTrx != null)
		{
			matchPO.setDateTrx(dateTrx);
		}
		matchPO.setM_Product_ID(sLine.getM_Product_ID());
		matchPO.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
		matchPO.setQty(qty);
		matchPO.setProcessed(true);        // auto

		return matchPO;
	}

	private I_M_MatchPO createMatchPO(final I_C_InvoiceLine iLine, final Timestamp dateTrx, final BigDecimal qty)
	{
		final I_M_MatchPO matchPO = newInstance(I_M_MatchPO.class);
		matchPO.setAD_Org_ID(iLine.getAD_Org_ID());
		matchPO.setC_InvoiceLine(iLine);
		if (iLine.getC_OrderLine_ID() != 0)
		{
			matchPO.setC_OrderLine_ID(iLine.getC_OrderLine_ID());
		}
		if (dateTrx != null)
		{
			matchPO.setDateTrx(dateTrx);
		}
		matchPO.setM_Product_ID(iLine.getM_Product_ID());
		matchPO.setM_AttributeSetInstance_ID(iLine.getM_AttributeSetInstance_ID());
		matchPO.setQty(qty);
		matchPO.setProcessed(true);        // auto

		return matchPO;
	}    // MMatchPO

	private void enqueToPost(@NonNull final Set<Integer> matchPOIds)
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

	@Override
	public void unlink(@NonNull final OrderLineId orderLineId, @NonNull final InvoiceAndLineId invoiceAndLineId)
	{
		for (final I_M_MatchPO matchPO : matchPODAO.getByOrderLineAndInvoiceLine(orderLineId, invoiceAndLineId))
		{
			if (matchPO.getM_InOutLine_ID() <= 0)
			{
				matchPO.setProcessed(false);
				InterfaceWrapperHelper.delete(matchPO);
			}
			else
			{
				matchPO.setC_InvoiceLine_ID(-1);
				InterfaceWrapperHelper.save(matchPO);
			}
		}
	}

	@Override
	public void unlink(@NonNull final InOutId inoutId)
	{
		for (final I_M_MatchPO matchPO : matchPODAO.getByReceiptId(inoutId))
		{
			if (matchPO.getC_InvoiceLine_ID() <= 0)
			{
				matchPO.setProcessed(false);
				InterfaceWrapperHelper.delete(matchPO);
			}
			else
			{
				matchPO.setM_InOutLine_ID(-1);
				InterfaceWrapperHelper.save(matchPO);
			}
		}
	}

	@Override
	public void unlink(@NonNull final InvoiceId invoiceId)
	{
		for (final I_M_MatchPO matchPO : matchPODAO.getByInvoiceId(invoiceId))
		{
			if (matchPO.getM_InOutLine_ID() <= 0)
			{
				matchPO.setProcessed(false);
				InterfaceWrapperHelper.delete(matchPO);
			}
			else
			{
				matchPO.setC_InvoiceLine_ID(-1);
				InterfaceWrapperHelper.save(matchPO);
			}
		}
	}
}
