package de.metas.ordercandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MRelationType;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_Currency;
import org.compiere.model.MNote;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRefTable;
import org.compiere.model.MReference;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.OrderCandidate_Constants;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.ordercandidate.model.I_C_OLCandGenerator;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.ordercandidate.model.X_C_OLCandAggAndOrder;
import de.metas.ordercandidate.spi.IOLCandCreator;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IProductPriceAttributeAware;
import de.metas.pricing.attributebased.ProductPriceAttributeAware;
import de.metas.product.IProductPA;
import de.metas.relation.grid.ModelRelationTarget;
import de.metas.workflow.api.IWFExecutionFactory;

public class OLCandBL implements IOLCandBL
{
	private static final Logger logger = LogManager.getLogger(OLCandBL.class);

	private static final String MSG_OL_CAND_PROCESSOR_ORDER_COMPLETION_FAILED_2P = "OLCandProcessor.Order_Completion_Failed";
	private static final String MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_DESC_1P = "OLCandProcessor.ProcessingError_Desc";

	private final List<IOLCandListener> olCandListeners = new ArrayList<IOLCandListener>();

	private final List<IOLCandGroupingProvider> groupingValuesProviders = new ArrayList<IOLCandGroupingProvider>();

	@Override
	public void process(
			final Properties ctx,
			final I_C_OLCandProcessor processor,
			final ILoggable process,
			final String trxName)
	{
		// Note: We could make life easier by constructing a ORDER and GROUP BY SQL statement,
		// but I'm afraid that grouping by time - granularity is not really portable. Also there might be other
		// granularity levels, that can't be put into an sql later on.

		//
		// 1. Get the ol-candidates to process (using relation)
		final MRelationType relationType = MRelationType.retrieveForInternalName(ctx, mkRelationTypeInternalName(processor), trxName);
		if (relationType == null)
		{
			final String msg = "No relation type has been configured for" + processor;
			logProcess(process, msg);
			throw new AdempiereException(msg);
		}

		//
		// FIXME retrieve (AND FILTER) the candidates directly, and not with M..., please
		final List<I_C_OLCand> allCandidates = relationType.retrieveDestinations(InterfaceWrapperHelper.getPO(processor), I_C_OLCand.class);
		final List<I_C_OLCand> orderCandidates = filterValidOrderCandidates(ctx, allCandidates, trxName);
		if (orderCandidates.isEmpty())
		{
			logProcess(process, "Found no Orders candidates; nothing to do");
			return;
		}

		final List<I_C_OLCand> candidates = filterProcessedAndError(orderCandidates);

		if (candidates.isEmpty())
		{
			logProcess(process, "Found no unprocessed candidates; nothing to do");
			return;
		}

		logProcess(process, "Processing " + candidates.size() + " order line candidates");

		//
		// 2. Order the candidates
		final List<I_C_OLCandAggAndOrder> aggAndOrderList = Services.get(IOLCandDAO.class).retrieveOLCandAggAndOrderForProcessor(ctx, processor, trxName);
		Collections.sort(candidates, mkComparator(processor, aggAndOrderList));

		//
		// 3. compute a grouping key for each candidate and group them according to their key
		final Map<Integer, ArrayKey> toProcess = new HashMap<Integer, ArrayKey>();
		final Map<ArrayKey, List<I_C_OLCand>> grouping = new HashMap<ArrayKey, List<I_C_OLCand>>();

		for (final I_C_OLCand candidate : candidates)
		{
			if (candidate.isProcessed())
			{
				continue; // ts: I don't see a need for this check, but let's keep it until we covered this by ait
			}

			//
			// Register grouping key
			final int olCandId = candidate.getC_OLCand_ID();
			final ArrayKey groupingKey = mkGroupingKey(processor, candidate, aggAndOrderList);
			toProcess.put(olCandId, groupingKey);

			List<I_C_OLCand> groupingVal = grouping.get(groupingKey);
			if (groupingVal == null)
			{
				groupingVal = new ArrayList<I_C_OLCand>();
				grouping.put(groupingKey, groupingVal);
			}
			groupingVal.add(candidate);
		}

		//
		// 4. create orders and order lines.

		// 'processedIds' contains the candidates that have already been processed
		final Set<Integer> processedIds = new HashSet<Integer>();

		MOrder order = null;
		MOrderLine orderLine = null;
		final List<MOrderLine> createdOrderLines = new ArrayList<MOrderLine>();

		// This variable is used to decide if the current candidate differs from the previous one in a way that requires
		// a new order.
		I_C_OLCand previousCand = null;

		for (final I_C_OLCand candidate : candidates)
		{
			final int olCandId = candidate.getC_OLCand_ID();
			if (processedIds.contains(olCandId) || candidate.isProcessed())
			{
				// 'candidate' has already been processed
				continue;
			}

			// get the group of the current unprocessed candidate
			final ArrayKey groupingKey = toProcess.get(olCandId);
			for (final I_C_OLCand candOfGroup : grouping.get(groupingKey))
			{
				if (order == null || isOrderSplit(ctx, candOfGroup, previousCand, aggAndOrderList, processor, trxName))
				{
					// complete 'order' if not null and if there have been some order lines created
					completeOrDeleteOrder(ctx, order, processor, candidates, createdOrderLines, process, trxName);

					// create a new order to work with from this point on
					order = mkNewOrder(ctx, processor, candOfGroup, trxName);
					createdOrderLines.clear();

				}

				// 02384
				try
				{
					orderLine = processOLCand(ctx, grouping, order, orderLine, candOfGroup, trxName);

					// 03472: establishing a "real" link with FK-constraints between order candidate and order line
					createOla(
							InterfaceWrapperHelper.create(candOfGroup, I_C_OLCand.class),
							InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class), processor, candOfGroup.getQty());

					createdOrderLines.add(orderLine);
				}
				catch (final AdempiereException e)
				{
					final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

					// storing the error-info out of trx, i hope this solves the bug that the info just wasn't there
					final int userInChargeId = processor.getAD_User_InCharge_ID();
					final MNote note = new MNote(ctx, IOLCandBL.MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_0P, userInChargeId, ITrx.TRXNAME_None);
					note.setRecord(adTableDAO.retrieveTableId(I_C_OLCand.Table_Name), candOfGroup.getC_OLCand_ID());

					final MUser user = MUser.get(ctx, userInChargeId);
					note.setClientOrg(user.getAD_Client_ID(), user.getAD_Org_ID());

					note.setTextMsg(e.getLocalizedMessage());
					note.saveEx();

					candOfGroup.setIsError(true);
					candOfGroup.setErrorMsg(e.getLocalizedMessage());
					candOfGroup.setAD_Note_ID(note.getAD_Note_ID());
					InterfaceWrapperHelper.save(candOfGroup, ITrx.TRXNAME_None);
				}
				// 02384 end

				Check.assume(candOfGroup == candidate || !processedIds.contains(candOfGroup.getC_OLCand_ID()),
						candOfGroup + " of grouping " + grouping + " is not processed twice");
				Check.assume(processedIds.add(candOfGroup.getC_OLCand_ID()),
						candOfGroup + " of grouping " + grouping + " is not processed twice");

				previousCand = candOfGroup;
			}
			orderLine = null;
		}

		completeOrDeleteOrder(ctx, order, processor, candidates, createdOrderLines, process, trxName);
		Check.assume(processedIds.size() == candidates.size(), "All candidates have been processed");
	}

	private void completeOrDeleteOrder(
			final Properties ctx,
			final MOrder order,
			final I_C_OLCandProcessor processor,
			final List<I_C_OLCand> candidates,
			final List<MOrderLine> createdOrderLines,
			final ILoggable process,
			final String trxName)
	{
		if (order == null)
		{
			return;
		}
		if (createdOrderLines.isEmpty())
		{
			order.deleteEx(false);
		}
		else
		{
			// 02384
			try
			{
				completeOrder(order, process);
			}
			catch (final AdempiereException e)
			{
				final StringBuilder sb = new StringBuilder();
				boolean first = true;
				int counter = 0;
				for (final I_C_OLCand candidate : candidates)
				{
					counter++;
					if (first)
					{
						first = false;
					}
					else
					{
						sb.append(", ");
					}
					sb.append(candidate.getC_OLCand_ID());
					if (!first && counter % 10 == 0)
					{
						sb.append("\n");
					}
				}
				final int userInChargeId = processor.getAD_User_InCharge_ID();
				final MNote note = new MNote(ctx, IOLCandBL.MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_0P, userInChargeId, trxName);

				final MUser user = MUser.get(ctx, userInChargeId);
				note.setClientOrg(user.getAD_Client_ID(), user.getAD_Org_ID());

				note.setReference(e.getLocalizedMessage());

				final String errorMsg = Services.get(IMsgBL.class).getMsg(
						user.getC_BPartner().getAD_Language(),
						OLCandBL.MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_DESC_1P,
						new Object[] { sb.toString() });
				note.setTextMsg(errorMsg);
				note.saveEx();

				for (final I_C_OLCand candidate : candidates)
				{
					candidate.setIsError(true);
					candidate.setErrorMsg(errorMsg);
					candidate.setAD_Note_ID(note.getAD_Note_ID());
					InterfaceWrapperHelper.save(candidate);
				}
			}
		}
	}

	private MOrderLine processOLCand(
			final Properties ctx,
			final Map<ArrayKey, List<I_C_OLCand>> grouping,
			final MOrder order,
			final MOrderLine currentOrderLine,
			final I_C_OLCand candToProcess,
			final String trxName)
	{
		final MOrderLine resultOrderLinePO;

		if (currentOrderLine == null || currentOrderLine.getC_Order_ID() != order.get_ID())
		{
			// 'orderLine' belongs to an "old" order - see isOrderSplit(). Create a new one.
			if (currentOrderLine != null)
			{
				currentOrderLine.saveEx();
			}

			resultOrderLinePO = new MOrderLine(order);

			if (candToProcess.getC_Charge_ID() > 0)
			{
				resultOrderLinePO.setC_Charge_ID(candToProcess.getC_Charge_ID());
			}
			else
			{
				final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

				final int productId = olCandEffectiveValuesBL.getM_Product_Effective_ID(candToProcess);
				if (productId <= 0)
				{
					throw new FillMandatoryException(I_C_OLCand.COLUMNNAME_M_Product_ID);
				}
				resultOrderLinePO.setM_Product_ID(productId, olCandEffectiveValuesBL.getC_UOM_Effective_ID(candToProcess));
			}

			// make sure that both records have their independent ASI to avoid unwanted side effects if the order line's ASI is altered.
			Services.get(IAttributeSetInstanceBL.class).cloneASI(resultOrderLinePO, candToProcess);
		}
		else
		{
			resultOrderLinePO = currentOrderLine;
		}

		final BigDecimal newQty = resultOrderLinePO.getQtyOrdered().add(candToProcess.getQty());
		resultOrderLinePO.setQty(newQty);

		final I_C_OrderLine resultOrderLine = InterfaceWrapperHelper.create(resultOrderLinePO, I_C_OrderLine.class);

		// set destination warehouse
		resultOrderLine.setM_Warehouse_Dest_ID(candToProcess.getM_Warehouse_Dest_ID());

		//
		// set pricing
		resultOrderLine.setIsManualPrice(candToProcess.isManualPrice());
		if (candToProcess.isManualPrice())
		{
			resultOrderLinePO.setPriceEntered(candToProcess.getPriceActual());
		}
		else
		{
			// leave it to the pricing engine
		}
		resultOrderLine.setIsManualDiscount(candToProcess.isManualDiscount());
		if (candToProcess.isManualDiscount())
		{
			resultOrderLinePO.setDiscount(candToProcess.getDiscount());
		}
		if (candToProcess.isManualPrice() || candToProcess.isManualDiscount())
		{
			Services.get(IOrderLineBL.class).calculatePriceActualIfNotIgnored(resultOrderLine, candToProcess.getC_Currency().getStdPrecision());
		}

		for (final IOLCandListener l : olCandListeners)
		{
			l.onOrderLineCreated(candToProcess, resultOrderLinePO);
		}

		//
		// 08626: Propagate ProductDescription to C_OrderLine
		resultOrderLine.setProductDescription(candToProcess.getProductDescription());

		resultOrderLine.setLine(candToProcess.getLine());

		// task 08839: attach the olCand's IProductPriceAttributeAware to the new order line, so that the rest of the system can know if it is supposed to guess an ASI and PIIP or not.
		final IAttributeSetInstanceAware orderLineASIAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(resultOrderLinePO);
		Check.assumeNotNull(orderLineASIAware, "We can allways obtain a not-null ASI aware for C_OrderLine {} ", resultOrderLine);

		final Optional<IProductPriceAttributeAware> ppaAware = ProductPriceAttributeAware.ofModel(candToProcess);
		Services.get(IAttributePricingBL.class).setDynAttrProductPriceAttributeAware(orderLineASIAware, ppaAware);

		InterfaceWrapperHelper.save(resultOrderLine);

		candToProcess.setProcessed(true);
		InterfaceWrapperHelper.save(candToProcess);

		return resultOrderLinePO;
	}

	private void completeOrder(final MOrder order, final ILoggable process)
	{
		if (order == null)
		{
			return;
		}

		if (order.processIt(X_C_Order.DOCACTION_Complete))
		{
			order.saveEx();
			logProcess(process, "@Created@ @C_Order_ID@ " + order.getDocumentNo());
		}
		else
		{
			throw new AdempiereException(
					Env.getAD_Language(order.getCtx()),
					OLCandBL.MSG_OL_CAND_PROCESSOR_ORDER_COMPLETION_FAILED_2P,
					new Object[] { order.getDocumentNo(), order.getProcessMsg() });
		}
	}

	private MOrder mkNewOrder(
			final Properties ctx,
			final I_C_OLCandProcessor processor,
			final I_C_OLCand olCand,
			final String trxName)
	{
		final MOrder orderPO = new MOrder(ctx, 0, trxName);

		final I_C_Order order = InterfaceWrapperHelper.create(orderPO, I_C_Order.class);
		order.setDocAction(X_C_Order.DOCACTION_Complete);

		// use the values from 'processor
		order.setDeliveryRule(processor.getDeliveryRule());
		order.setDeliveryViaRule(processor.getDeliveryViaRule());
		order.setM_Shipper_ID(processor.getM_Shipper_ID());

		order.setFreightCostRule(processor.getFreightCostRule());
		order.setPaymentRule(processor.getPaymentRule());
		order.setC_PaymentTerm_ID(processor.getC_PaymentTerm_ID());

		order.setInvoiceRule(processor.getInvoiceRule());
		order.setC_DocTypeTarget_ID(processor.getC_DocTypeTarget_ID());
		order.setM_Warehouse_ID(processor.getM_Warehouse_ID());

		final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		// use the values from 'olCand'
		order.setAD_Org_ID(olCand.getAD_Org_ID());
		order.setC_BPartner_ID(effectiveValuesBL.getC_BPartner_Effective_ID(olCand));
		order.setC_BPartner_Location_ID(effectiveValuesBL.getC_BP_Location_Effective_ID(olCand));
		order.setAD_User_ID(effectiveValuesBL.getAD_User_Effective_ID(olCand));

		// if the olc has no value set, we are not falling back here!
		order.setBill_BPartner_ID(olCand.getBill_BPartner_ID());
		order.setBill_Location_ID(olCand.getBill_Location_ID());
		order.setBill_User_ID(olCand.getBill_User_ID());

		// task 06269 (see KurzBeschreibung)
		// note that C_Order.DatePromised is propagated to C_OrderLine.DatePromised in MOrder.afterSave() and MOrderLine.setOrder()
		// also note that for now we set datepromised only in the header, so different DatePromised values result in differnt orders, and all ol have the same datepromised
		order.setDatePromised(effectiveValuesBL.getDatePromised_Effective(olCand));

		// if the olc has no value set, we are not falling back here!
		// 05617
		order.setDropShip_BPartner_ID(effectiveValuesBL.getDropShip_BPartner_Effective_ID(olCand));
		order.setDropShip_Location_ID(olCand.getDropShip_Location_ID());
		final boolean isDropShip = effectiveValuesBL.getDropShip_BPartner_Effective_ID(olCand) > 0 || effectiveValuesBL.getDropShip_Location_Effective_ID(olCand) > 0;
		order.setIsDropShip(isDropShip);

		order.setHandOver_Location_ID(effectiveValuesBL.getHandOver_Location_Effective_ID(olCand));
		order.setHandOver_Partner_ID(effectiveValuesBL.getHandOver_Partner_Effective_ID(olCand));
		order.setIsUseHandOver_Location(effectiveValuesBL.getHandOver_Location_Effective_ID(olCand) > 0);

		if (olCand.getC_Currency_ID() > 0)
		{
			order.setC_Currency_ID(olCand.getC_Currency_ID());
		}

		order.setPOReference(olCand.getPOReference());

		// pricing-system is not mandatory in 'processor', so we set it either from the processor *or* from the bpartner or BP-GRoup
		order.setM_PricingSystem_ID(getPricingSystemId(ctx, olCand, processor, trxName));

		// task 08926: set the data source; this shall trigger IsEdiEnabled to be set to true, if the data source is "EDI"
		final de.metas.order.model.I_C_Order orderWithDataSource = InterfaceWrapperHelper.create(order, de.metas.order.model.I_C_Order.class);
		orderWithDataSource.setAD_InputDataSource(olCand.getAD_InputDataSource());

		InterfaceWrapperHelper.save(orderPO);
		return orderPO;
	}

	/**
	 * Returning the pricing system to use for the given {@code olCand}.
	 * <ul>
	 * <li>if C_OLCand.M_PricingSystem_ID > 0, then we return that</li>
	 * <li>else, if the processor has a pricing system set, then we return that</li>
	 * <li>else, if the C_OLCand has a bill partner set, then we return that partner's (or her C_BP_Group's) pricing system</li>
	 * <li>else we return the C_OLCand's C_BPartner's (or her C_BP_Group's) pricing system</li>
	 * </ul>
	 *
	 * @param ctx
	 * @param olCand
	 * @param processor may be <code>null</code>
	 * @param trxName
	 * @return
	 * @see IBPartnerDAO#retrievePricingSystemId(Properties, int, boolean, String)
	 */
	private int getPricingSystemId(final Properties ctx, final I_C_OLCand olCand, final I_C_OLCandProcessor processor, final String trxName)
	{
		Check.assumeNotNull(olCand, "Param 'olCand' not null");

		if (olCand.getM_PricingSystem_ID() > 0)
		{
			return olCand.getM_PricingSystem_ID();
		}
		else if (processor != null && processor.getM_PricingSystem_ID() > 0)
		{
			return processor.getM_PricingSystem_ID();
		}
		else
		{
			final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
			final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

			final int bpartnerID = effectiveValuesBL.getBill_BPartner_Effective_ID(olCand);
			final boolean soTrx = true;

			final int pricingSystemId = bPartnerDAO.retrievePricingSystemId(ctx, bpartnerID, soTrx, trxName);
			return pricingSystemId;
		}
	}

	/**
	 * Decides if there needs to be a new order for 'candidate'.
	 *
	 * @param candidate
	 * @param previousCandidate
	 * @param aggAndOrderList
	 * @return <code>true</code> if either
	 *         <ul>
	 *         <li>'candidate' and 'previousCandidate' differ in one of C_BPartnert_ID, C_BPartner_Location_ID, AD_User_ID, AD_Org_ID or any other value that is set to the C_Order record</li>
	 *         <li>if the pricing system used for 'candidate' and 'previousCandidate' differ, see {@link #getPricingSystemId(Properties, I_C_OLCand, I_C_OLCandProcessor, String)}</li>
	 *         <li>'aggAndOrderList' contains a record with isSplitOrder='Y' and 'candidate' differs from 'previousCandidate' in the particular value specified by AD_Column_OLCand_ID.</li>
	 *         </ul>
	 */
	private boolean isOrderSplit(
			final Properties ctx,
			final I_C_OLCand candidate,
			final I_C_OLCand previousCandidate,
			final List<I_C_OLCandAggAndOrder> aggAndOrderList,
			final I_C_OLCandProcessor processor,
			final String trxName)
	{
		Check.assumeNotNull(previousCandidate, "Param 'previousCandidate' != null");

		final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		// We keep this block for the time being because as of now we did not make sure that the aggAndOrderList is complete to ensure that all
		// C_OLCands with different C_Order-"header"-columns will be split into different orders (think of e.g. C_OLCands with different currencies).
		if (previousCandidate.getAD_Org_ID() != candidate.getAD_Org_ID()
				|| !Check.equals(previousCandidate.getPOReference(), candidate.getPOReference())
				|| previousCandidate.getC_Currency_ID() != candidate.getC_Currency_ID()
				|| effectiveValuesBL.getC_BPartner_Effective_ID(previousCandidate) != effectiveValuesBL.getC_BPartner_Effective_ID(candidate)
				|| effectiveValuesBL.getC_BP_Location_Effective_ID(previousCandidate) != effectiveValuesBL.getC_BP_Location_Effective_ID(candidate)
				|| effectiveValuesBL.getAD_User_Effective_ID(previousCandidate) != effectiveValuesBL.getAD_User_Effective_ID(candidate)
				|| effectiveValuesBL.getBill_BPartner_Effective_ID(previousCandidate) != effectiveValuesBL.getBill_BPartner_Effective_ID(candidate)
				|| effectiveValuesBL.getBill_Location_Effective_ID(previousCandidate) != effectiveValuesBL.getBill_Location_Effective_ID(candidate)
				|| effectiveValuesBL.getBill_User_Effective_ID(previousCandidate) != effectiveValuesBL.getBill_User_Effective_ID(candidate)
				// task 06269: note that for now we set DatePromised only in the header, so different DatePromised values result in different orders, and all ols have the same DatePromised
				|| !Check.equals(effectiveValuesBL.getDatePromised_Effective(previousCandidate), effectiveValuesBL.getDatePromised_Effective(candidate))
				|| !Check.equals(effectiveValuesBL.getHandOver_Partner_Effective_ID(previousCandidate), effectiveValuesBL.getHandOver_Partner_Effective_ID(candidate))
				|| !Check.equals(effectiveValuesBL.getHandOver_Location_Effective_ID(previousCandidate), effectiveValuesBL.getHandOver_Location_Effective_ID(candidate))
				|| !Check.equals(effectiveValuesBL.getDropShip_BPartner_Effective_ID(previousCandidate), effectiveValuesBL.getDropShip_BPartner_Effective_ID(candidate))
				|| !Check.equals(effectiveValuesBL.getDropShip_Location_Effective_ID(previousCandidate), effectiveValuesBL.getDropShip_Location_Effective_ID(candidate))
				|| getPricingSystemId(ctx, previousCandidate, processor, trxName) != getPricingSystemId(ctx, candidate, processor, trxName))
		{
			return true;
		}

		for (final I_C_OLCandAggAndOrder aggAndOrder : aggAndOrderList)
		{
			if (!aggAndOrder.isSplitOrder())
			{
				continue;
			}

			final Object value = getValueByColumnId(processor, candidate, aggAndOrder);
			final Object previousValue = getValueByColumnId(processor, previousCandidate, aggAndOrder);
			if (!Check.equals(value, previousValue))
			{
				return true;
			}
		}

		// between 'candidate' and 'previousCandidate' there are no differences that require a new order to be created
		return false;
	}

	/**
	 * Computes a grouping key for the given candidate. The key is made such that two different candidates that should be grouped (i.e. qtys aggregated) end up with the same grouping key.
	 *
	 * @param processor
	 *
	 * @param candidate
	 * @param aggAndOrderList
	 * @return
	 */
	private ArrayKey mkGroupingKey(final I_C_OLCandProcessor processor, final I_C_OLCand candidate, final List<I_C_OLCandAggAndOrder> aggAndOrderList)
	{
		final List<Object> groupingValues = new ArrayList<Object>();

		final List<I_C_OLCandAggAndOrder> aggAndOrderWithGroupBy = new ArrayList<I_C_OLCandAggAndOrder>();
		for (final I_C_OLCandAggAndOrder aggAndOrder : aggAndOrderList)
		{
			if (aggAndOrder.isGroupBy())
			{
				aggAndOrderWithGroupBy.add(aggAndOrder);
			}
		}

		if (aggAndOrderWithGroupBy.isEmpty())
		{
			// each candidate results in its own order line
			return Util.mkKey(candidate.getC_OLCand_ID());
		}

		for (final IOLCandGroupingProvider groupingValuesProvider : groupingValuesProviders)
		{
			groupingValues.addAll(groupingValuesProvider.provideLineGroupingValues(candidate));
		}

		for (final I_C_OLCandAggAndOrder aggAndOrder : aggAndOrderList)
		{
			if (!aggAndOrder.isGroupBy())
			{
				// we don't group by the current column, so all different values result in their own order lines
				groupingValues.add(getValueByColumnId(processor, candidate, aggAndOrder));
			}
			else if (!Check.isEmpty(aggAndOrder.getGranularity()))
			{
				// create a grouping key based on the granularity level
				final String granularity = aggAndOrder.getGranularity();

				if (isTime(granularity))
				{
					final Timestamp timestamp = (Timestamp)getValueByColumnId(processor, candidate, aggAndOrder);
					final Calendar cal = new GregorianCalendar();
					cal.setTime(timestamp);

					if (X_C_OLCandAggAndOrder.GRANULARITY_Tag.equals(granularity)
							|| X_C_OLCandAggAndOrder.GRANULARITY_Woche.equals(granularity)
							|| X_C_OLCandAggAndOrder.GRANULARITY_Monat.equals(granularity))
					{
						extractDay(cal);
					}
					else
					{
						Check.assume(false,
								granularity + " is one of " + X_C_OLCandAggAndOrder.GRANULARITY_Tag + ", " + X_C_OLCandAggAndOrder.GRANULARITY_Woche + " or " + X_C_OLCandAggAndOrder.GRANULARITY_Monat);
					}

					if (X_C_OLCandAggAndOrder.GRANULARITY_Woche.equals(granularity))
					{
						extractWeek(cal);
					}
					else if (X_C_OLCandAggAndOrder.GRANULARITY_Monat.equals(granularity))
					{
						extractMonth(cal);
					}

					final long groupingValue = cal.getTimeInMillis();
					groupingValues.add(groupingValue);
				}
			}

		}

		return Util.mkKey(groupingValues.toArray());
	}

	private boolean isTime(final String granularity)
	{
		return X_C_OLCandAggAndOrder.GRANULARITY_Tag.equals(granularity)
				|| X_C_OLCandAggAndOrder.GRANULARITY_Woche.equals(granularity)
				|| X_C_OLCandAggAndOrder.GRANULARITY_Monat.equals(granularity);
	}

	private void extractWeek(final Calendar cal)
	{
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	}

	private void extractMonth(final Calendar cal)
	{
		cal.set(Calendar.DAY_OF_MONTH, 1);
	}

	private void extractDay(final Calendar cal)
	{
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR, 0);
	}

	/**
	 * Creates a comparator to order a list of {@link I_C_OLCand} records according to the given 'aggAndOrderList'.
	 *
	 * @param processor
	 * @param aggAndOrderList
	 * @return
	 */
	private Comparator<I_C_OLCand> mkComparator(final I_C_OLCandProcessor processor, final List<I_C_OLCandAggAndOrder> aggAndOrderList)
	{
		return new Comparator<I_C_OLCand>()
		{
			@SuppressWarnings("unchecked")
			@Override
			public int compare(final I_C_OLCand o1, final I_C_OLCand o2)
			{
				final int lastSeqNo = 0;
				for (final I_C_OLCandAggAndOrder aggAndOrder : aggAndOrderList)
				{
					if (aggAndOrder.getOrderBySeqNo() <= 0)
					{
						// record is not relevant for ordering
						continue;
					}

					Check.assume(aggAndOrder.getOrderBySeqNo() > lastSeqNo, "Content of " + aggAndOrderList + " is ordered in a not-ambigous way");

					final Object val1 = getValueByColumnId(processor, o1, aggAndOrder);
					final Object val2 = getValueByColumnId(processor, o2, aggAndOrder);

					if (val1 != null && val2 != null)
					{
						Check.assume(val1.getClass() == val2.getClass(), val1 + " and " + val2 + " have the same class");
					}
					// allow null values
					if (val1 == val2)
					{
						continue; // theses ones are identical; try comparing by the next aggAndOrder
					}
					if (val1 == null)
					{
						return -1;
					}
					if (val2 == null)
					{
						return 1;
					}

					if (val1 instanceof Comparable<?>)
					{
						Check.assume(val2 instanceof Comparable<?>, val2 + " instanceof Comparable<?>");

						@SuppressWarnings("rawtypes")
						final int compareResult = ((Comparable)val1).compareTo(val2);

						if (compareResult != 0)
						{
							return compareResult;
						}
					}
				}
				return 0;
			}
		};
	}

	/**
	 * FIXME hardcoded (08691)
	 *
	 * @param ctx
	 * @param processor
	 * @param candidate
	 * @param aggAndOrder
	 * @param trxName
	 * @return effective value OR {@link InterfaceWrapperHelper#getValueByColumnId(Object, int)}
	 */
	private Object getValueByColumnId(final I_C_OLCandProcessor processor,
			final I_C_OLCand candidate,
			final I_C_OLCandAggAndOrder aggAndOrder)
	{
		//
		// Services
		final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
		final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

		final Object value;

		final I_AD_Column olCandColumn = aggAndOrder.getAD_Column_OLCand();
		final String olCandColumnName = olCandColumn.getColumnName();

		if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_BPartner_ID))
		{
			value = effectiveValuesBL.getBill_BPartner_Effective_ID(candidate);
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_Location_ID))
		{
			value = effectiveValuesBL.getBill_Location_Effective_ID(candidate);
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_User_ID))
		{
			value = effectiveValuesBL.getBill_User_Effective_ID(candidate);
		}
		// else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DatePromised_Effective))
		// {
		// value = effectiveValuesBL.getDatePromised_Effective(candidate);
		// }
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DropShip_BPartner_ID))
		{
			value = effectiveValuesBL.getDropShip_BPartner_Effective_ID(candidate);
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DropShip_Location_ID))
		{
			value = effectiveValuesBL.getDropShip_Location_Effective_ID(candidate);
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_M_PricingSystem_ID))
		{
			value = getPricingSystemId(ctx, candidate, processor, trxName);
		}
		else
		{
			value = InterfaceWrapperHelper.getValueByColumnId(candidate, olCandColumn.getAD_Column_ID());
		}
		return value;
	}

	private void logProcess(final ILoggable process, final String msg)
	{
		if (process != null)
		{
			process.addLog(msg);
		}
	}

	@Override
	public String mkRelationTypeInternalName(final I_C_OLCandProcessor processor)
	{
		return I_C_OLCandProcessor.Table_Name + "_" + processor.getC_OLCandProcessor_ID() + "<=>" + I_C_OLCand.Table_Name;
	}


	/**
	 * Returns a list containing only those input candidates which have Processed='N' and Error='N'.
	 *
	 * @param all
	 * @return
	 */
	private List<I_C_OLCand> filterProcessedAndError(final List<I_C_OLCand> all)
	{
		final List<I_C_OLCand> result = new ArrayList<I_C_OLCand>();

		for (final I_C_OLCand cand : all)
		{
			if (cand.isProcessed() || cand.isError())
			{
				continue;
			}
			result.add(cand);
		}
		return result;
	}

	/**
	 * Returns a list containing only those input candidates which have destination Orders
	 *
	 * @param ctx
	 * @param cands
	 * @param trxName
	 * @return list
	 */
	private List<I_C_OLCand> filterValidOrderCandidates(final Properties ctx, final List<I_C_OLCand> cands, final String trxName)
	{
		final List<I_C_OLCand> result = new ArrayList<I_C_OLCand>();

		final I_AD_InputDataSource dataDest =
				Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(
						ctx,
						OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME,
						true, // throwEx
						ITrx.TRXNAME_None);

		final int processorDataDestinationId = dataDest.getAD_InputDataSource_ID();

		// FIXME: instead of having isImportedWithIssues() implemented here, add support for using a filter that is then registered from e.g. a model validator
		// this way, we would further decouple our modules
		for (final I_C_OLCand cand : cands)
		{
			if (!isValidDataDestination(processorDataDestinationId, cand.getAD_DataDestination_ID())
					|| isImportedWithIssues(cand))
			{
				continue;
			}
			result.add(cand);
		}
		return result;
	}

	private boolean isValidDataDestination(final int processorDataDestinationId, final int candDataDestinationId)
	{
		return processorDataDestinationId == candDataDestinationId;
	}

	private boolean isImportedWithIssues(final I_C_OLCand olcand)
	{
		final org.adempiere.process.rpl.model.I_C_OLCand cand = InterfaceWrapperHelper.create(olcand, org.adempiere.process.rpl.model.I_C_OLCand.class);
		return cand.isImportedWithIssues();
	}

	@Override
	public ModelRelationTarget mkModelRelationTarget(
			final I_C_OLCandProcessor processor,
			final int sourceWindowId,
			final String sourceTabName,
			String whereClause)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(processor);
		final ModelRelationTarget model = new ModelRelationTarget();
		// configure the dialog's model parameters

		model.setAdTableSourceId(adTableDAO.retrieveTableId(I_C_OLCandProcessor.Table_Name));
		model.setRecordSourceId(processor.getC_OLCandProcessor_ID());

		// filter by AD_DataDestination_ID
		final I_AD_InputDataSource dataDest =
				Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(ctx, OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME, true, ITrx.TRXNAME_None);

		if (!Check.isEmpty(whereClause, true))
		{
			whereClause += " AND ";
		}
		whereClause += I_C_OLCand.COLUMNNAME_AD_DataDestination_ID + " = " + dataDest.getAD_InputDataSource_ID();

		model.setTargetWhereClause(whereClause);

		model.setAdWindowSourceId(sourceWindowId);

		model.setAdTableTargetId(adTableDAO.retrieveTableId(I_C_OLCand.Table_Name));

		final MTable olCandTable = MTable.get(ctx, I_C_OLCand.Table_Name);
		model.setAdWindowTargetId(olCandTable.getAD_Window_ID());

		model.setRelationTypeInternalName(mkRelationTypeInternalName(processor));

		model.setRelationTypeName(
				(sourceTabName == null ? "" : sourceTabName + " ")
						+ processor.getName()
						+ "<=>"
						+ olCandTable.getName()
				);

		return model;
	}

	@Override
	public void createOrUpdateOCProcessorRelationType(
			final Properties ctx,
			final ModelRelationTarget model,
			final String trxName)
	{
		//
		// Services
		// final IADTableDAO adTableDAO = Services.get(IADTableDAO.class); // TODO remove deprecation

		final String entityType = OrderCandidate_Constants.ENTITY_TYPE;

		final MRelationType retrievedRelType = MRelationType.retrieveForInternalName(ctx, model.getRelationTypeInternalName(), null);

		final MRelationType relType;

		final MReference refSource;
		final MRefTable refTableSource;

		final MReference refTarget;
		final MRefTable refTableTarget;

		final int orgId = 0;

		if (retrievedRelType == null)
		{
			relType = new MRelationType(ctx, 0, trxName);
			relType.setInternalName(model.getRelationTypeInternalName());

			refSource = new MReference(ctx, 0, trxName);
			refSource.setAD_Org_ID(orgId);
			refSource.setEntityType(entityType);
			refSource.setName(mkNameOfSourceRef(ctx, model));
			refSource.setValidationType(X_AD_Reference.VALIDATIONTYPE_TableValidation);
			refSource.saveEx();
			relType.setAD_Reference_Source_ID(refSource.get_ID());

			refTableSource = new MRefTable(ctx, 0, trxName);
			refTableSource.setAD_Org_ID(orgId);
			refTableSource.setAD_Reference_ID(refSource.get_ID());
			refTableSource.setEntityType(entityType);

			refTarget = new MReference(ctx, 0, trxName);
			refTarget.setAD_Org_ID(orgId);
			refTarget.setEntityType(entityType);
			refTarget.setName(mkNameOfTargetRef(ctx, model));
			refTarget.setValidationType(X_AD_Reference.VALIDATIONTYPE_TableValidation);
			refTarget.saveEx();
			relType.setAD_Reference_Target_ID(refTarget.get_ID());

			refTableTarget = new MRefTable(ctx, 0, trxName);
			refTableTarget.setAD_Org_ID(orgId);
			refTableTarget.setAD_Reference_ID(refTarget.get_ID());
			refTableTarget.setEntityType(entityType);
		}
		else
		{
			relType = retrievedRelType;
			refSource = (MReference)relType.getAD_Reference_Source();
			refTableSource = MReference.retrieveRefTable(ctx, refSource.get_ID(), trxName);

			refTarget = (MReference)relType.getAD_Reference_Target();
			refTableTarget = MReference.retrieveRefTable(ctx, refTarget.get_ID(), trxName);

		}
		relType.setIsExplicit(false);
		relType.setName(model.getRelationTypeName());
		relType.setIsDirected(model.isRelationTypeDirected());
		relType.saveEx();

		// source reference
		refTableSource.setAD_Table_ID(model.getAdTableSourceId());
		refTableSource.setAD_Window_ID(model.getAdWindowSourceId());

		final MTable tableSource = MTable.get(ctx, model.getAdTableSourceId());
		final String[] keyColumnsSource = tableSource.getKeyColumns();
		Check.assume(keyColumnsSource.length == 1, "keyColumnsSource=" + keyColumnsSource + " has one element");

		final int keyColumnSourceId = tableSource.getColumn(keyColumnsSource[0]).get_ID();
		refTableSource.setAD_Key(keyColumnSourceId);
		refTableSource.setAD_Display(keyColumnSourceId);

		refTableSource.setWhereClause(keyColumnsSource[0] + "=" + model.getRecordSourceId());
		refTableSource.saveEx();

		// target reference
		refTableTarget.setAD_Table_ID(model.getAdTableTargetId());
		refTableTarget.setAD_Window_ID(model.getAdWindowTargetId());

		final MTable tableTarget = MTable.get(ctx, model.getAdTableTargetId());
		final String[] keyColumnsTarget = tableTarget.getKeyColumns();
		assert keyColumnsTarget.length == 1;

		final int keyColumnTargetId = tableTarget.getColumn(keyColumnsTarget[0]).get_ID();
		refTableTarget.setAD_Key(keyColumnTargetId);
		refTableTarget.setAD_Display(keyColumnTargetId);

		final String targetWhereClauseToUse;
		if (Check.isEmpty(model.getTargetWhereClause()))
		{
			targetWhereClauseToUse = "1=1";
		}
		else
		{
			targetWhereClauseToUse = model.getTargetWhereClause();
		}
		refTableTarget.setWhereClause(targetWhereClauseToUse);
		refTableTarget.saveEx();
	}

	private String mkNameOfSourceRef(
			final Properties ctx,
			final ModelRelationTarget model)
	{
		return "RelType_" + Services.get(IADTableDAO.class).retrieveTableName(model.getAdTableSourceId()) + "_" + model.getRecordSourceId();
	}

	private String mkNameOfTargetRef(
			final Properties ctx,
			final ModelRelationTarget model)
	{
		return "RelType_" + Services.get(IADTableDAO.class).retrieveTableName(model.getAdTableTargetId()) + "_" + model.getRecordSourceId();
	}

	@Override
	public I_C_OLCand invokeOLCandCreator(final PO po)
	{
		final IOLCandCreator olCandCreator = retrieveOlCandCreatorInstance(po.getCtx(), po.get_Table_ID(), po.get_TrxName());
		if (olCandCreator == null)
		{
			final String msg = "Unable to process '" + po + "'; Missing IOLCandCreator implmentation for table '" + Services.get(IADTableDAO.class).retrieveTableName(po.get_Table_ID()) + "'";
			OLCandBL.logger.warn(msg);
			throw new AdempiereException(msg);
		}

		return invokeOLCandCreator(po, olCandCreator);
	}

	@Override
	public I_C_OLCand invokeOLCandCreator(final PO po, final IOLCandCreator olCandCreator)
	{
		Check.assumeNotNull(olCandCreator, "olCandCreator is not null");

		final I_C_OLCand olCand = olCandCreator.createFrom(po);
		if (po.set_ValueOfColumnReturningBoolean("Processed", true))
		{
			po.saveEx();
		}

		if (olCand == null)
		{
			OLCandBL.logger.info(olCandCreator + " returned null for " + po + "; Nothing to do.");
			return null;
		}

		olCand.setAD_Table_ID(po.get_Table_ID());
		olCand.setRecord_ID(po.get_ID());

		InterfaceWrapperHelper.save(olCand);

		Services.get(IWFExecutionFactory.class).notifyActivityPerformed(po, olCand); // 03745

		return olCand;
	}

	private IOLCandCreator retrieveOlCandCreatorInstance(final Properties ctx, final int tableId, final String trxName)
	{
		final I_C_OLCandGenerator olCandGenerator = Services.get(IOLCandDAO.class).retrieveOlCandCreator(ctx, tableId, trxName);
		return Util.getInstance(IOLCandCreator.class, olCandGenerator.getOCGeneratorImpl());
	}

	@Override
	public void registerOLCandListener(final IOLCandListener l)
	{
		olCandListeners.add(l);
	}

	@Override
	public void registerCustomerGroupingValuesProvider(final IOLCandGroupingProvider groupingProvider)
	{
		groupingValuesProviders.add(groupingProvider);
	}

	// 03472
	// Between C_OL_Cand and C_OrderLine I think we have an explicit AD_Relation. It should be replaced with a table
	// similar to C_Invoice_Line_Alloc, so that it is more transparent and we can define decent FK constraints. (and in
	// future we can have m:n if we want to)
	private void createOla(
			final I_C_OLCand orderCand,
			final I_C_OrderLine orderLine,
			final I_C_OLCandProcessor processor,
			final BigDecimal qtyOrdered)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderCand);

		Check.assume(Env.getAD_Client_ID(ctx) == orderCand.getAD_Client_ID(), "AD_Client_ID of " + orderCand + " and of its CTX are the same");

		final I_C_Order_Line_Alloc newOla = InterfaceWrapperHelper.create(ctx, I_C_Order_Line_Alloc.class, trxName);
		newOla.setAD_Org_ID(orderCand.getAD_Org_ID());

		newOla.setC_OLCand_ID(orderCand.getC_OLCand_ID());
		newOla.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		newOla.setQtyOrdered(qtyOrdered);
		newOla.setC_OLCandProcessor_ID(processor.getC_OLCandProcessor_ID());

		InterfaceWrapperHelper.save(newOla);
	}

	@Override
	public String toString()
	{
		return "OLCandBL [olCandListeners=" + olCandListeners + ", groupingValuesProviders=" + groupingValuesProviders + "]";
	}

	@Override
	public IPricingResult computePriceActual(final I_C_OLCand olCand, final BigDecimal qtyOverride, final int pricingSystemIdOverride, final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);

		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setReferencedObject(olCand);

		final IPricingResult pricingResult;
		if (olCand.isManualDiscount() && olCand.isManualPrice())
		{
			// create an empty one. we won't use the pricing engine to fill it.
			pricingResult = Services.get(IPricingBL.class).createInitialResult(pricingCtx);
		}
		else
		{
			final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
			final IProductPA productPA = Services.get(IProductPA.class);

			final int bill_BPartner_ID = effectiveValuesBL.getBill_BPartner_Effective_ID(olCand);
			final int bill_Location_ID = effectiveValuesBL.getBill_Location_Effective_ID(olCand);

			final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

			final BigDecimal qty = qtyOverride != null ? qtyOverride : olCand.getQty();
			final int pricingSystemId = pricingSystemIdOverride > 0 ? pricingSystemIdOverride : getPricingSystemId(ctx, olCand, null, trxName);

			if (pricingSystemId <= 0)
			{
				throw new AdempiereException("@M_PricingSystem@ @NotFound@");
			}
			pricingCtx.setM_PricingSystem_ID(pricingSystemId); // set it to the context that way it will also be in the result, even if the pricing rules won't need it

			pricingCtx.setC_BPartner_ID(bill_BPartner_ID);
			pricingCtx.setQty(qty);
			pricingCtx.setPriceDate(date);
			pricingCtx.setSOTrx(true);

			pricingCtx.setDisallowDiscount(olCand.isManualDiscount());

			final I_M_PriceList pl =
					InterfaceWrapperHelper.create(
							productPA.retrievePriceListByPricingSyst(ctx, pricingSystemId, bill_Location_ID, true, trxName),
							I_M_PriceList.class);

			if (pl == null)
			{
				throw new AdempiereException("@PriceList@ @NotFound@: @M_PricingSystem@ " + pricingSystemId + ", @Bill_Location@ " + bill_Location_ID);
			}
			pricingCtx.setM_PriceList_ID(pl.getM_PriceList_ID());
			pricingCtx.setM_Product_ID(effectiveValuesBL.getM_Product_Effective_ID(olCand));

			pricingResult = pricingBL.calculatePrice(pricingCtx);

			// Just for safety: in case the product price was not found, the code below shall not be reached.
			// The exception shall be already thrown
			// ts 2015-07-03: i think it is not, at least i don't see from where
			if (pricingResult == null || !pricingResult.isCalculated())
			{
				final int documentLineNo = -1; // not needed, the msg will be shown in the line itself
				throw new ProductNotOnPriceListException(pricingCtx, documentLineNo);
			}
		}

		final BigDecimal priceEntered;
		final BigDecimal discount;
		final int currencyId;

		if (olCand.isManualPrice())
		{
			// both price and currency need to be already set in the olCand (only a price amount doesn't make sense with an unspecified currency)
			priceEntered = olCand.getPriceEntered();
			currencyId = olCand.getC_Currency_ID();
		}
		else
		{
			priceEntered = pricingResult.getPriceStd();
			currencyId = pricingResult.getC_Currency_ID();
		}

		if (olCand.isManualDiscount())
		{
			discount = olCand.getDiscount();
		}
		else
		{
			discount = pricingResult.getDiscount();
		}

		if (currencyId <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_Currency@"
					+ "\n Pricing context: " + pricingCtx
					+ "\n Pricing result: " + pricingResult);
		}

		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrency(ctx, currencyId);
		final BigDecimal priceActual = Services.get(IOrderLineBL.class).subtractDiscount(priceEntered, discount, currency.getStdPrecision());

		pricingResult.setPriceStd(priceActual);
		pricingResult.setDiscount(discount);

		return pricingResult;
	}
}
