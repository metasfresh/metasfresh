package de.metas.commission.custom.type;

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


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvComSystem_Type;
import de.metas.commission.service.IComRelevantPoBL;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.util.CommissionTools;
import de.metas.document.engine.IDocument;

/**
 * Common base class for all commission types.
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public abstract class BaseCommission implements ICommissionType
{

	private static final Logger logger = LogManager.getLogger(BaseCommission.class);

	protected final static ICommissionFactBL commissionFactBL = Services.get(ICommissionFactBL.class);

	public static final BigDecimal MIN_TURNOVER_FOR_COMMISSION = new BigDecimal(Integer.MIN_VALUE);

	private I_C_AdvComSystem_Type comSystemType;

	/**
	 * 
	 * @param cand
	 * @param poLine
	 * @param status one of {@link X_C_AdvComSalesRepFact#STATUS_Prognose} or {@link X_C_AdvComSalesRepFact#STATUS_Prov_Relevant}
	 * @param adPInstanceId If new records are created, the given ID is recorded in their {@link I_C_AdvCommissionFact#COLUMNNAME_AD_PInstance_ID} column for documentation. If there is no
	 *            AD_PInstance_ID id available, use {@link ICommissionFactBL#NO_AD_PINSTANCE_ID}.
	 * 
	 */
	abstract void createInstanceAndFact(MCAdvCommissionFactCand cand, PO poLine, final String status, final int adPInstanceId);

	@Override
	public final void evaluateCandidate(final MCAdvCommissionFactCand candidate, final String status, final int adPInstanceId)
	{
		//
		// evaluate the candidates and create facts
		final PO po = Services.get(ICommissionFactCandBL.class).retrievePO(candidate);

		if (po instanceof MOrder)
		{
			handleOrder(candidate, status, adPInstanceId);
		}
		else if (po instanceof MInvoice)
		{
			handleInvoice(candidate, status, adPInstanceId);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(po, I_C_AdvComSalesRepFact.class))
		{
			createInstanceAndFact(candidate, po, status, adPInstanceId);
		}
		else if (po instanceof MAllocationHdr)
		{
			final ICommissionFactBL commissionFactBL = Services.get(ICommissionFactBL.class);

			final MAllocationHdr allocHdr = (MAllocationHdr)po;
			for (final MAllocationLine allocLine : allocHdr.getLines(true))
			{
				commissionFactBL.recordAllocationLine(this, candidate, allocLine, adPInstanceId);
			}
		}
		else
		{
			BaseCommission.logger.warn("Not handling " + po + " (candidate=" + candidate + ")");
		}
	}

	void handleInvoice(final MCAdvCommissionFactCand cand, final String status, final int adPInstanceId)
	{
		final MInvoice invoice = (MInvoice)Services.get(ICommissionFactCandBL.class).retrievePO(cand);

		if (CommissionTools.isEmployeeInvoice(invoice))
		{
			return;
		}

		final IComRelevantPoBL relevantPoBL = Services.get(IComRelevantPoBL.class);
		for (final MInvoiceLine il : invoice.getLines())
		{
			if (relevantPoBL.isRelevantInvoiceLine(invoice, il, this))
			{
				createInstanceAndFact(cand, il, status, adPInstanceId);
			}
		}
	}

	void handleOrder(final MCAdvCommissionFactCand cand, final String status, final int adPInstanceId)
	{
		final MOrder order = (MOrder)Services.get(ICommissionFactCandBL.class).retrievePO(cand);
		;
		final boolean completeSO = isCompleteSO(order);

		if (!completeSO)
		{
			return;
		}
		for (final MOrderLine ol : order.getLines())
		{
			createInstanceAndFact(cand, ol, status, adPInstanceId);
		}
	}

	private boolean isCompleteSO(final MOrder order)
	{
		final String docStatus = order.getDocStatus();

		final boolean completeSO = order.isSOTrx()
				&& (IDocument.STATUS_Completed.equals(docStatus)
						|| IDocument.STATUS_WaitingPayment.equals(docStatus)
						|| IDocument.STATUS_Closed.equals(docStatus)
						|| IDocument.STATUS_Voided.equals(docStatus));

		if (!completeSO)
		{
			BaseCommission.logger.info(order + " has Status=" + docStatus + ". Nothing to do.");
		}
		return completeSO;
	}

	@Override
	public BigDecimal getCommissionPointsSum(
			final IAdvComInstance inst,
			final String status,
			final Timestamp date,
			final Object po)
	{
		final IFieldAccessBL fieldAccessBL = Services.get(IFieldAccessBL.class);

		final BigDecimal qty = fieldAccessBL.getQty(po);
		final BigDecimal grossCommissionPoints = fieldAccessBL.getCommissionPoints(po, true).multiply(qty);

		if (X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Brutto.equals(comSystemType.getUseGrossOrNetPoints()))
		{
			return grossCommissionPoints;
		}


		Check.assume(X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Netto.equals(comSystemType.getUseGrossOrNetPoints()), "C_AdvComSystem_Type='NET' (not {})", comSystemType.getUseGrossOrNetPoints());

		final BigDecimal discount = fieldAccessBL.getDiscount(po, true);
		return Services.get(IOrderLineBL.class).subtractDiscount(grossCommissionPoints, discount, 2);
	}

	@Override
	public void setComSystemType(final I_C_AdvComSystem_Type comSystemType)
	{
		this.comSystemType = comSystemType;
	}

	@Override
	public I_C_AdvComSystem_Type getComSystemType()
	{
		return comSystemType;
	}
}
