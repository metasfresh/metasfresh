package de.metas.contracts.subscription.impl;

/*
 * #%L
 * de.metas.contracts
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
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_Product;
import org.compiere.model.MNote;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPriceList;
import org.compiere.model.MProductPricing;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.document.engine.IDocActionBL;
import de.metas.flatrate.Contracts_Constants;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.interfaces.I_C_BPartner;
import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.flatrate.model.I_C_Contract_Term_Alloc;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Data;
import de.metas.flatrate.model.I_C_Flatrate_Matching;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_Flatrate_Conditions;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Transition;
import de.metas.flatrate.model.X_C_SubscriptionProgress;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.monitoring.api.IMonitoringBL;
import de.metas.order.IOrderPA;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.product.IProductPA;
import de.metas.workflow.api.IWFExecutionFactory;

public class SubscriptionBL implements ISubscriptionBL
{

	private static final String ERR_NEW_CONDITIONS_PRICE_MISSING_1P = "de.metas.flatrate.NewConditions.Price_Missing";
	public static final Logger logger = LogManager.getLogger(SubscriptionBL.class);

	final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

	final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

	@Override
	public I_C_Flatrate_Term createSubscriptionTerm(final I_C_OrderLine ol,
			final boolean completeIt,
			I_C_Order order)
	{
		if (order == null)
		{
			order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
		}
		else
		{
			Check.assume(order.getC_Order_ID() == ol.getC_Order_ID(), ol + " does not belong to " + order);
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);

		final I_C_Flatrate_Conditions cond = ol.getC_Flatrate_Conditions();

		final I_C_Flatrate_Term newTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, ol);

		newTerm.setC_OrderLine_Term_ID(ol.getC_OrderLine_ID());
		newTerm.setC_Flatrate_Conditions_ID(cond.getC_Flatrate_Conditions_ID());

		if (cond.getM_PricingSystem_ID() > 0)
		{
			newTerm.setM_PricingSystem_ID(cond.getM_PricingSystem_ID());
		}
		else
		{
			newTerm.setM_PricingSystem_ID(order.getM_PricingSystem_ID());
		}

		// important: we need to use qtyEntered here, because qtyOrdered (which
		// is used for pricing) contains the number of goods to be delivered
		// over the whole subscription term
		newTerm.setC_OrderLine_Term_ID(ol.getC_OrderLine_ID());
		newTerm.setPlannedQtyPerUnit(ol.getQtyEntered());
		newTerm.setC_UOM_ID(ol.getC_UOM_ID());

		newTerm.setStartDate(order.getDateOrdered());

		newTerm.setDeliveryRule(order.getDeliveryRule());
		newTerm.setDeliveryViaRule(order.getDeliveryViaRule());

		newTerm.setBill_BPartner_ID(order.getBill_BPartner_ID());
		newTerm.setBill_Location_ID(order.getBill_Location_ID());
		newTerm.setBill_User_ID(order.getBill_User_ID());

		newTerm.setDropShip_BPartner_ID(ol.getC_BPartner_ID());
		newTerm.setDropShip_Location_ID(ol.getC_BPartner_Location_ID());
		newTerm.setDropShip_User_ID(ol.getAD_User_ID());

		final String wcData = I_C_Flatrate_Data.COLUMNNAME_C_BPartner_ID + "=?";
		I_C_Flatrate_Data existingData = new Query(ctx, I_C_Flatrate_Data.Table_Name, wcData, trxName)
				.setParameters(order.getBill_BPartner_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_Flatrate_Data.class);
		if (existingData == null)
		{
			existingData = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Data.class, ol);
			existingData.setC_BPartner_ID(order.getBill_BPartner_ID());
			InterfaceWrapperHelper.save(existingData);
		}

		newTerm.setC_Flatrate_Data(existingData);

		newTerm.setAD_User_InCharge_ID(order.getSalesRep_ID());

		newTerm.setIsSimulation(cond.isSimulation());
		newTerm.setM_Product_ID(ol.getM_Product_ID());

		final boolean postageFree;

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(order.getBill_BPartner(), I_C_BPartner.class);
		final String bpPostageFree = bPartner.getPostageFree();

		if (I_C_BPartner.POSTAGEFREE_Always.equals(bpPostageFree)
				|| I_C_BPartner.POSTAGEFREE_Subscription.equals(bpPostageFree))
		{
			postageFree = true;
		}
		else
		{
			postageFree = false;
		}

		newTerm.setIsPostageFree(postageFree);
		newTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_NochNichtBegonnen);
		newTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);

		InterfaceWrapperHelper.save(newTerm);

		if (completeIt)
		{
			Services.get(IDocActionBL.class).processEx(newTerm, X_C_Flatrate_Term.DOCACTION_Complete, X_C_Flatrate_Term.DOCSTATUS_Completed);
		}

		return newTerm;
	}

	@Override
	public int createMissingTermsForOLCands(
			final Properties ctx,
			final boolean completeIt,
			final int AD_PInstance_ID,
			final String trxName)
	{
		final I_AD_InputDataSource dataDest =
				Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(ctx, Contracts_Constants.DATA_DESTINATION_INTERNAL_NAME, true, trxName);

		final String wc =
				I_C_OLCand.COLUMNNAME_AD_DataDestination_ID + "=? AND " +
						I_C_OLCand.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND " +
						I_C_OLCand.COLUMNNAME_IsError + "=" + DB.TO_STRING("N") + " AND " +
						" NOT EXISTS (" +
						"   select 1 from " + I_C_Contract_Term_Alloc.Table_Name + " ta " +
						"   where " +
						"      ta." + I_C_Contract_Term_Alloc.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND " +
						"      ta." + I_C_Contract_Term_Alloc.COLUMNNAME_AD_Client_ID + "=" + I_C_OLCand.Table_Name + "." + I_C_OLCand.COLUMNNAME_AD_Client_ID + " AND " +
						"      ta." + I_C_Contract_Term_Alloc.COLUMNNAME_C_OLCand_ID + "=" + I_C_OLCand.Table_Name + "." + I_C_OLCand.COLUMNNAME_C_OLCand_ID +
						" )";

		List<I_C_OLCand> candidates = retrieveList(ctx, wc, dataDest, trxName);

		int counter = 0;

		while (!candidates.isEmpty())
		{
			for (final I_C_OLCand olCand : candidates)
			{
				final Throwable[] t = new Throwable[1];

				Trx.run(trxName, new TrxRunnable2()
				{
					@Override
					public void run(String trxName) throws Exception
					{
						createTermForOLCand(ctx, olCand, AD_PInstance_ID, completeIt, trxName);
						Services.get(IMonitoringBL.class).createOrGet(Contracts_Constants.ENTITY_TYPE, "SubscriptionBL.createMissingTermsForOLCands()_Done").plusOne();
					}

					@Override
					public void doFinally()
					{
						if (t[0] == null)
						{
							return;
						}

						I_AD_User userInCharge = Services.get(IBPartnerOrgBL.class).retrieveUserInChargeOrNull(ctx, olCand.getAD_Org_ID(), trxName);
						if (userInCharge == null)
						{
							userInCharge = InterfaceWrapperHelper.create(ctx, Env.getAD_User_ID(ctx), I_AD_User.class, trxName);
						}

						final MNote note = new MNote(ctx, IOLCandBL.MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_0P, userInCharge.getAD_User_ID(), trxName);
						note.setRecord(MTable.getTable_ID(I_C_OLCand.Table_Name), olCand.getC_OLCand_ID());

						note.setClientOrg(userInCharge.getAD_Client_ID(), userInCharge.getAD_Org_ID());

						note.setTextMsg(t[0].getLocalizedMessage());
						note.saveEx();

						olCand.setIsError(true);
						olCand.setAD_Note_ID(note.getAD_Note_ID());
						olCand.setErrorMsg(t[0].getLocalizedMessage());
					}

					@Override
					public boolean doCatch(Throwable e) throws Throwable
					{
						t[0] = e; // store 'e'
						return true; // rollback transaction
					}
				});

				InterfaceWrapperHelper.save(olCand);
				counter++;
			}

			Check.assume(Trx.get(trxName, false).commit(), "commit of trx with name=" + trxName + " was OK");

			// get next items
			candidates = retrieveList(ctx, wc, dataDest, trxName);
		}

		return counter;
	}

	private List<I_C_OLCand> retrieveList(final Properties ctx, final String wc, final I_AD_InputDataSource dataDest, final String trxName)
	{
		final List<I_C_OLCand> candidates = new Query(ctx, I_C_OLCand.Table_Name, wc, trxName)
				.setParameters(dataDest.getAD_InputDataSource_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_OLCand.COLUMNNAME_C_OLCand_ID)
				.setLimit(50)
				.list(I_C_OLCand.class);
		return candidates;
	}

	@Override
	public I_C_Flatrate_Term createSubscriptionTerm(
			final I_C_OLCand olCand,
			final boolean completeIt)
	{

		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final I_C_Flatrate_Conditions cond = olCand.getC_Flatrate_Conditions();

		final I_C_Flatrate_Matching matching = retrieveMatching(ctx, olCand.getC_Flatrate_Conditions_ID(), olCandEffectiveValuesBL.getM_Product_Effective(olCand), null);

		final BigDecimal deliveryQty;
		if (matching != null)
		{
			deliveryQty = matching.getQtyPerDelivery();
		}
		else
		{
			deliveryQty = BigDecimal.ONE;
		}

		final I_C_Flatrate_Term newTerm = InterfaceWrapperHelper.create(ctx, I_C_Flatrate_Term.class, trxName);

		newTerm.setAD_Org_ID(olCand.getAD_Org_ID());
		newTerm.setC_Flatrate_Conditions_ID(cond.getC_Flatrate_Conditions_ID());

		// important: we need to use qtyEntered here, because qtyOrdered (which
		// is used for pricing) contains the number of goods to be delivered
		// over the whole subscription term

		newTerm.setPlannedQtyPerUnit(deliveryQty.multiply(olCand.getQty()));
		newTerm.setStartDate(olCand.getDateCandidate());

		newTerm.setDeliveryRule(olCand.getDeliveryRule());
		newTerm.setDeliveryViaRule(olCand.getDeliveryViaRule());

		final I_C_BPartner bill_BPartner = olCandEffectiveValuesBL.getBill_BPartner_Effective(olCand, I_C_BPartner.class);
		final int bill_Location_ID = olCandEffectiveValuesBL.getBill_Location_Effective_ID(olCand);
		final int bill_User_ID = olCandEffectiveValuesBL.getBill_User_Effective_ID(olCand);

		newTerm.setBill_BPartner_ID(bill_BPartner.getC_BPartner_ID());
		newTerm.setBill_Location_ID(bill_Location_ID);
		newTerm.setBill_User_ID(bill_User_ID);

		newTerm.setDropShip_BPartner_ID(olCandEffectiveValuesBL.getDropShip_BPartner_Effective_ID(olCand));
		newTerm.setDropShip_Location_ID(olCandEffectiveValuesBL.getDropShip_Location_Effective_ID(olCand));
		newTerm.setDropShip_User_ID(olCandEffectiveValuesBL.getDropShip_User_Effective_ID(olCand));

		I_C_Flatrate_Data existingData = Services.get(IFlatrateDAO.class).retriveOrCreateFlatrateData(bill_BPartner);

		newTerm.setC_Flatrate_Data(existingData);

		final I_AD_User userInCharge = Services.get(IBPartnerOrgBL.class).retrieveUserInChargeOrNull(ctx, olCand.getAD_Org_ID(), trxName);
		if (userInCharge != null)
		{
			newTerm.setAD_User_InCharge_ID(userInCharge.getAD_User_ID());
		}
		else
		{
			newTerm.setAD_User_InCharge_ID(Env.getAD_User_ID(ctx));
		}
		newTerm.setIsSimulation(cond.isSimulation());
		newTerm.setM_Product_ID(olCandEffectiveValuesBL.getM_Product_Effective_ID(olCand));

		final boolean postageFree;

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(olCand.getBill_BPartner(), I_C_BPartner.class);
		final String bpPostageFree = bPartner.getPostageFree();

		if (I_C_BPartner.POSTAGEFREE_Always.equals(bpPostageFree)
				|| I_C_BPartner.POSTAGEFREE_Subscription.equals(bpPostageFree))
		{
			postageFree = true;
		}
		else
		{
			postageFree = false;
		}

		newTerm.setIsPostageFree(postageFree);
		newTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_NochNichtBegonnen);
		newTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);

		InterfaceWrapperHelper.save(newTerm);

		if (olCand.getM_PricingSystem_ID() > 0)
		{
			newTerm.setM_PricingSystem_ID(olCand.getM_PricingSystem_ID());
		}

		final IPricingResult pricingResult =
				olCandBL.computePriceActual(olCand, newTerm.getPlannedQtyPerUnit(), newTerm.getM_PricingSystem_ID(), olCand.getDateCandidate());

		newTerm.setPriceActual(pricingResult.getPriceStd());
		newTerm.setC_UOM_ID(pricingResult.getPrice_UOM_ID());

		// task 03805:
		// Make sure the currency ID for term is the same as the one from olCand
		final int currencyID = olCand.getC_Currency_ID();
		newTerm.setC_Currency_ID(currencyID);

		InterfaceWrapperHelper.save(newTerm);

		if (completeIt)
		{
			Services.get(IDocActionBL.class).processEx(newTerm, X_C_Flatrate_Term.DOCACTION_Complete, X_C_Flatrate_Term.DOCSTATUS_Completed);
		}

		return newTerm;
	}

	@Override
	public I_C_SubscriptionProgress createSubscriptionEntries(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Transition trans = term.getC_Flatrate_Transition();

		final int numberOfRuns = computeNumberOfRuns(trans, term.getStartDate());
		Check.assume(numberOfRuns > 0, trans + " has NumberOfEvents > 0");

		Timestamp eventDate = term.getStartDate();
		int seqNo = 10;

		final List<I_C_SubscriptionProgress> deliveries = new ArrayList<I_C_SubscriptionProgress>();

		for (int i = 0; i < numberOfRuns; i++)
		{
			final I_C_SubscriptionProgress delivery = createDelivery(term, eventDate, seqNo);

			InterfaceWrapperHelper.save(delivery);
			deliveries.add(delivery);

			seqNo += 10;
			eventDate = mkNextDate(trans, eventDate);
		}

		return deliveries.get(0);
	}

	@Override
	public void evalCurrentSPs(final I_C_Flatrate_Term term, final Timestamp currentDate)
	{
		final ISubscriptionDAO subscriptionPA = Services.get(ISubscriptionDAO.class);

		I_C_SubscriptionProgress sp = subscriptionPA.retrieveNextSP(term, currentDate, 0);

		logger.debug("next SP: " + sp);

		if (sp == null)
		{
			logger.info("Creating initial SPs for " + term);
			sp = createSubscriptionEntries(term);
		}

		// see if there are further SPs to evaluate.
		Timestamp lastSPDate = sp.getEventDate();
		int lastSPSeqNo = sp.getSeqNo();
		int count = 0;
		while (true)
		{
			//
			// check if we can exit the loop
			if (sp == null)
			{
				logger.info("There are no more SPs to evaluate");
				// sc.setLastEval(SystemTime.asTimestamp());
				// sc.saveEx();
				return;
			}
			if (sp.getEventDate().after(currentDate))
			{
				logger.info("CurrentDate=" + currentDate + " -> Still too early to process " + term);
				// sc.setLastEval(SystemTime.asTimestamp());
				// sc.saveEx();
				break;
			}

			//
			// do the actual work
			evalCurrentSP(term, currentDate, sp);

			// see if there is an SP for the next loop iteration
			sp = subscriptionPA.retrieveNextSP(term, currentDate, sp.getSeqNo() + 1);
			count++;

			//
			// make sure that we don't get stuck in an infinite loop
			if (sp != null)
			{
				Check.assume(!lastSPDate.after(sp.getEventDate()), "");
				Check.assume(lastSPSeqNo < sp.getSeqNo(), "");

				lastSPSeqNo = sp.getSeqNo();
				lastSPDate = sp.getEventDate();
			}
		} // while

		logger.info("Evaluated " + count + " " + I_C_SubscriptionProgress.Table_Name + " records");
	}

	private boolean evalCurrentSP(final I_C_Flatrate_Term sc, final Timestamp currentDate, I_C_SubscriptionProgress sp)
	{
		Check.assume(!sp.getEventDate().after(currentDate), sp.toString());

		boolean spUpdated = false;

		// if (isPlannedExtendSubscription(sp))
		// {
		// extendSubscription(sc, currentDate, sc.get_TrxName(), sp);
		// sp.setStatus(STATUS_Ausgefuehrt);
		// spUpdated = true;
		// }
		// else
		if (isPlannedStartPause(sp))
		{
			Check.assume(!isWaitForConfirm(sp), sp + "is not waiting for confirmation anymore");
			sp.setStatus(X_C_SubscriptionProgress.STATUS_Ausgefuehrt);
			spUpdated = true;

		}
		else if (isPlannedEndPause(sp))
		{
			Check.assume(!isWaitForConfirm(sp), sp + "is not waiting for confirmation anymore");
			sp.setStatus(X_C_SubscriptionProgress.STATUS_Ausgefuehrt);
			spUpdated = true;

		}
		// else if (isPlannedStart(sp))
		// {
		// sp.setStatus(STATUS_Ausgefuehrt);
		// spUpdated = true;
		// }
		// else if (isPlannedEnd(sp))
		// {
		// sp.setStatus(STATUS_Ausgefuehrt);
		// spUpdated = true;
		// }

		if (isWaitForConfirm(sp))
		{
			if (sp.isSubscriptionConfirmed())
			{
				logger.info(sp + " has been confirmed");
				sp.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Laufend);
				sp.setStatus(X_C_SubscriptionProgress.STATUS_Geplant);
				spUpdated = true;

			}
			else if (!X_C_SubscriptionProgress.STATUS_Verzoegert.equals(sp.getStatus()))
			{
				sp.setStatus(X_C_SubscriptionProgress.STATUS_Verzoegert);
				spUpdated = true;
			}
		}

		if (spUpdated)
		{
			InterfaceWrapperHelper.save(sp);
		}

		sc.setContractStatus(sp.getContractStatus());
		InterfaceWrapperHelper.save(sc);

		return spUpdated;
	}

	private boolean isWaitForConfirm(final I_C_SubscriptionProgress sp)
	{
		return X_C_SubscriptionProgress.CONTRACTSTATUS_WartetAufBestaetigung.equals(sp.getContractStatus());
	}

	// private void extendSubscription(
	// final MSubscriptionControl sc,
	// final Timestamp currentDate,
	// final String trxName,
	// final I_C_SubscriptionProgress subscriptionProgress)
	// {
	//
	// logger.info("Extending subscription for " + sc);
	//
	// final I_C_OrderLine oldSubscriptionOl = POWrapper.create(subscriptionProgress.getC_OrderLine(),
	// I_C_OrderLine.class);
	//
	// final IPOService poService = Services.get(IPOService.class);
	//
	// final MOrderLine newSubscriptionOlPO =
	// createNewOrderAndOl(oldSubscriptionOl, currentDate, subscriptionProgress.getEventDate(), trxName);
	//
	// final I_C_OrderLine newSubscriptionOl = POWrapper.create(newSubscriptionOlPO, I_C_OrderLine.class);
	//
	// setSubscription(newSubscriptionOl, oldSubscriptionOl.getC_Subscription_ID(), Env.getCtx(), trxName);
	//
	// final I_C_Order newOrder = POWrapper.create(newSubscriptionOl.getC_Order(), I_C_Order.class);
	// final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	// if (!docActionBL.processIt(newOrder, DocAction.ACTION_Complete))
	// {
	//
	// throw new AdempiereException("CompleteIt failed for order "
	// + newOrder + "\n" + MiscUtils.loggerMsgsUser());
	// }
	// logger.info("Completed new order " + newOrder + " for " + sc);
	// poService.save(newOrder, trxName);
	//
	// newSubscription(newSubscriptionOl, sc, trxName);
	//
	// subscriptionProgress.setStatus(STATUS_Ausgefuehrt);
	// poService.save(subscriptionProgress, trxName);
	// }

	// private boolean isPlannedExtendSubscription(final I_C_SubscriptionProgress sp)
	// {
	//
	// return STATUS_Geplant.equals(sp.getStatus()) && ( //
	// EVENTTYPE_Abo_Autoverlaengerung.equals(sp.getEventType()) //
	// || EVENTTYPE_Abowechsel.equals(sp.getEventType()));
	// }

	private boolean isPlannedStartPause(final I_C_SubscriptionProgress sp)
	{
		return X_C_SubscriptionProgress.EVENTTYPE_Abopause_Beginn.equals(sp.getEventType())
				&& X_C_SubscriptionProgress.STATUS_Geplant.equals(sp.getStatus());
	}

	private boolean isPlannedEndPause(final I_C_SubscriptionProgress sp)
	{
		return X_C_SubscriptionProgress.EVENTTYPE_Abopause_Ende.equals(sp.getEventType())
				&& X_C_SubscriptionProgress.STATUS_Geplant.equals(sp.getStatus());
	}

	// private boolean isPlannedStart(final I_C_SubscriptionProgress sp)
	// {
	//
	// return EVENTTYPE_Abo_Beginn.equals(sp.getEventType())
	// && STATUS_Geplant.equals(sp.getStatus());
	// }

	// private boolean isPlannedEnd(final I_C_SubscriptionProgress sp)
	// {
	//
	// return EVENTTYPE_Abo_Ende.equals(sp.getEventType())
	// && STATUS_Geplant.equals(sp.getStatus());
	// }

	/**
	 *
	 * @param oldOl
	 * @param oldOrder
	 * @return
	 */
	@Override
	public I_C_OrderLine createNewOrderAndOl(
			final I_C_OrderLine oldOl,
			final Timestamp dateOrdered,
			final Timestamp datePromised,
			final String trxName)
	{
		final I_C_Order oldOrder = InterfaceWrapperHelper.create(oldOl.getC_Order(), I_C_Order.class);

		//
		// create a new order and order line for the new subscription
		final IOrderPA orderPA = Services.get(IOrderPA.class);
		final MOrder newOrder = (MOrder)orderPA.copyOrder(oldOrder, false, trxName);
		newOrder.setDateOrdered(dateOrdered);
		newOrder.setDatePromised(datePromised);
		newOrder.setIsSOTrx(true);
		newOrder.setDocAction(DocAction.ACTION_Complete);
		newOrder.saveEx(trxName);

		logger.debug("Created new order " + newOrder);

		final MOrderLine newOl = new MOrderLine(newOrder);

		newOl.setM_Product_ID(oldOl.getM_Product_ID());
		newOl.setQtyEntered(oldOl.getQtyEntered());
		newOl.saveEx(trxName);

		logger.debug("Created new order line" + newOl);
		return InterfaceWrapperHelper.create(newOl, I_C_OrderLine.class);
	}

	private Timestamp mkNextDate(final I_C_Flatrate_Transition trans, final Timestamp currentDate)
	{
		final int deliveryInterval = trans.getDeliveryInterval();
		final String deliveryIntervalUnit = trans.getDeliveryIntervalUnit();

		return mkNextDate(deliveryIntervalUnit, deliveryInterval, currentDate);
	}

	@Override
	public Timestamp mkNextDate(
			final String deliveryIntervalUnit,
			final int deliveryInterval,
			final Timestamp currentDate)
	{
		final Calendar cal = new GregorianCalendar();
		cal.setTime(currentDate);

		if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_JahrE.equals(deliveryIntervalUnit))
		{
			cal.add(Calendar.DAY_OF_YEAR, deliveryInterval);
		}
		else if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_MonatE.equals(deliveryIntervalUnit))
		{
			cal.add(Calendar.MONTH, deliveryInterval);
		}
		else if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_WocheN.equals(deliveryIntervalUnit))
		{
			cal.add(Calendar.WEEK_OF_YEAR, deliveryInterval);
		}
		else if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_TagE.equals(deliveryIntervalUnit))
		{
			cal.add(Calendar.DAY_OF_YEAR, deliveryInterval);
		}
		else
		{
			throw new AdempiereException("Can't handle unknow frequency type '" + deliveryIntervalUnit + "'");
		}

		return new Timestamp(cal.getTimeInMillis());
	}

	@Override
	public void evalDeliveries(final Properties ctx, final String trxName)
	{

		final ISubscriptionDAO subscriptionPA = Services.get(ISubscriptionDAO.class);

		final List<I_C_SubscriptionProgress> deliveries =
				subscriptionPA.retrievePlannedAndDelayedDeliveries(ctx, SystemTime.asTimestamp(), trxName);

		logger.debug("Going to add shipment schedule entries for " + deliveries.size() + " subscription deliveries");

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		//
		final Set<Integer> delayedControlIds = new HashSet<Integer>();

		for (final I_C_SubscriptionProgress sd : deliveries)
		{
			final I_C_Flatrate_Term sc = sd.getC_Flatrate_Term();

			if (delayedControlIds.contains(sd.getC_Flatrate_Term_ID()))
			{
				logger.info("An earlier SP of " + sc + " is delayed => also delaying " + sd);
				sd.setStatus(X_C_SubscriptionProgress.STATUS_Verzoegert);
				InterfaceWrapperHelper.save(sd);
				continue;
			}

			if (isWaitForConfirm(sd))
			{
				if (sd.isSubscriptionConfirmed())
				{
					sd.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Laufend);
					sd.setStatus(X_C_SubscriptionProgress.STATUS_Geplant);
				}
				else
				{
					logger.info(sd + " is still delayed due to missing confirmation");
					delayedControlIds.add(sd.getC_Flatrate_Term_ID());
					continue;
				}
			}

			final List<I_M_ShipmentSchedule> openScheds =
					shipmentSchedulePA.retrieveUnprocessedForRecord(ctx, MTable.getTable_ID(I_C_SubscriptionProgress.Table_Name), sd.getC_SubscriptionProgress_ID(), trxName);

			if (openScheds.isEmpty())
			{
				if (X_C_SubscriptionProgress.STATUS_Verzoegert.equals(sd.getStatus()))
				{
					logger.info(sd + " is not delayed anymore");
					sd.setStatus(X_C_SubscriptionProgress.STATUS_Geplant);
					InterfaceWrapperHelper.save(sd);
				}
			}
			else
			{
				logger.debug(sd + " is deplayed because there is at least on open delivery");
				sd.setStatus(X_C_SubscriptionProgress.STATUS_Verzoegert);
				InterfaceWrapperHelper.save(sd);

				delayedControlIds.add(sd.getC_Flatrate_Term_ID());
				continue;
			}

			// final I_C_OrderLine ol = POWrapper.create(sd.getC_OrderLine(), I_C_OrderLine.class);
			//
			// createSchedule(ctx, sd, sc, ol, trxName);
			//
			// logger.debug("invalidating related schedule entries");
			// Services.get(IInOutCandHandlerBL.class).invalidateCandidatesFor(ol, I_C_OrderLine.Table_Name);
			//
			// try
			// {
			// Trx.get(trxName, false).commit(true);
			// }
			// catch (SQLException e)
			// {
			// throw new AdempiereException(e);
			// }
			//
			// logger.debug("Setting subscription delivery " + sd + " to status " + STATUS_LieferungOffen);
			// sd.setStatus(STATUS_LieferungOffen);
			// poService.save(sd, trxName);
		}
	}

	@Override
	public BigDecimal computePriceDifference(
			final Properties ctx,
			final int mPricingSystemId,
			final List<I_C_SubscriptionProgress> deliveries,
			final String trxName)
	{
		if (deliveries.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		// sum up the qty
		BigDecimal qtySum = BigDecimal.ZERO;

		for (final I_C_SubscriptionProgress sp : deliveries)
		{
			qtySum = qtySum.add(sp.getQty());

			if (!X_C_SubscriptionProgress.EVENTTYPE_Lieferung.equals(sp.getEventType()))
			{
				throw new IllegalArgumentException(sp.toString() + " has event type " + sp.getEventType());
			}
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(
				deliveries.get(0).getC_Flatrate_Term().getC_OrderLine_Term(),
				I_C_OrderLine.class);

		final IProductPA productPA = Services.get(IProductPA.class);
		final I_M_PriceList pl =
				InterfaceWrapperHelper.create(
						productPA.retrievePriceListByPricingSyst(
								ctx,
								mPricingSystemId,
								ol.getC_BPartner_Location_ID(),
								true,
								trxName),
						I_M_PriceList.class);

		final BigDecimal newPrice =
				productPA.retrievePriceStd(
						ol.getM_Product_ID(),
						ol.getC_BPartner_ID(),
						pl.getM_PriceList_ID(),
						qtySum,
						true).multiply(qtySum);

		final BigDecimal oldPrice = ol.getPriceActual().multiply(qtySum);

		return newPrice.subtract(oldPrice);
	}

	public void setPrices(final I_C_OrderLine ol, final int priceListId, final BigDecimal priceQty, final BigDecimal factor)
	{
		final int productId = ol.getM_Product_ID();

		final int bPartnerId = ol.getC_BPartner_ID();

		if (productId == 0 || bPartnerId == 0)
		{
			return;
		}

		final MProductPricing pp = new MProductPricing(productId, bPartnerId, priceQty, true);
		pp.setReferencedObject(ol); // 03152: setting the 'ol' to allow the subscription system to compute the
									// right price

		pp.setM_PriceList_ID(priceListId);

		if (!pp.calculatePrice())
		{
			throw new ProductNotOnPriceListException(pp, ol.getLine());
		}

		ol.setPriceList(pp.getPriceList());
		ol.setPriceLimit(pp.getPriceLimit());
		ol.setPriceActual(pp.getPriceStd());
		ol.setPriceEntered(pp.getPriceStd());
		ol.setC_Currency_ID(pp.getC_Currency_ID());
		ol.setDiscount(pp.getDiscount());
		ol.setPrice_UOM_ID(pp.getC_UOM_ID()); // task 06942

		// 05129 : also set the tax category
		ol.setC_TaxCategory_ID(pp.getC_TaxCategory_ID());

		if (priceQty != null)
		{
			// this code has been borrowed from
			// org.compiere.model.CalloutOrder.amt
			// TODO: check if we can invoke that callout instead
			final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
			final int StdPrecision = MPriceList.getStandardPrecision(ctx, priceListId);

			BigDecimal LineNetAmt = priceQty.multiply(factor.multiply(pp.getPriceStd()));
			if (LineNetAmt.scale() > StdPrecision)
				LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			logger.info("LineNetAmt=" + LineNetAmt);
			ol.setLineNetAmt(LineNetAmt);
		}
	}

	@Override
	public void setSubscription(final I_C_OrderLine ol, final I_C_Flatrate_Conditions subscription)
	{
		Check.assume(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Abonnement.equals(subscription.getType_Conditions()), "");
		Check.assume(ol.getM_Product_ID() > 0, "");

		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);

		ol.setC_Flatrate_Conditions_ID(subscription.getC_Flatrate_Conditions_ID());

		final int pricingSystemIdToUse =
				subscription.getM_PricingSystem_ID() > 0
						? subscription.getM_PricingSystem_ID()
						: InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class).getM_PricingSystem_ID();

		final IProductPA productPA = Services.get(IProductPA.class);

		final I_M_PriceList subscriptionPL =
				InterfaceWrapperHelper.create(
						productPA.retrievePriceListByPricingSyst(
								ctx,
								pricingSystemIdToUse,
								ol.getC_BPartner_Location_ID(),
								true,
								trxName),
						I_M_PriceList.class);

		if (subscriptionPL == null)
		{
			throw new AdempiereException(
					Env.getAD_Language(ctx), ERR_NEW_CONDITIONS_PRICE_MISSING_1P,
					new Object[] { subscription.getM_PricingSystem().getName() });
		}

		final int numberOfRuns = computeNumberOfRuns(subscription.getC_Flatrate_Transition(), ol.getC_Order().getDateOrdered());

		final I_M_Product olProduct = ol.getM_Product();
		final I_C_Flatrate_Matching matching = retrieveMatching(ctx, ol.getC_Flatrate_Conditions_ID(), olProduct, null);

		// allowing the matching to be null
		// Check.assume(matching != null, "Missing C_Flatrate_Matching for C_FlatrateConditions_ID=" +
		// ol.getC_Flatrate_Conditions_ID() + " and product " + olProduct);
		final BigDecimal qtyPerRun = matching == null ? BigDecimal.ONE : matching.getQtyPerDelivery();

		final BigDecimal priceQty = qtyPerRun.multiply(new BigDecimal(numberOfRuns));

		// qtyEntered is the "number of subscriptions"
		// qty ordered needs to be set because it will be used to compute the
		// line's NetLineAmount in MOrderLine.beforeSave()
		ol.setQtyOrdered(priceQty.multiply(ol.getQtyEntered()));

		logger.debug("Computing new prices for " + ol);

		// TODO why not use OrderLineBL to compute prices?
		setPrices(ol, subscriptionPL.getM_PriceList_ID(), priceQty, ol.getQtyEntered());
		InterfaceWrapperHelper.save(ol);
	}

	private I_C_SubscriptionProgress createDelivery(
			final I_C_Flatrate_Term sc,
			final Timestamp eventDate,
			final int seqNo)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sc);
		final String trxName = InterfaceWrapperHelper.getTrxName(sc);

		final I_C_SubscriptionProgress delivery = InterfaceWrapperHelper.newInstance(I_C_SubscriptionProgress.class, sc);

		delivery.setAD_Org_ID(sc.getAD_Org_ID());
		delivery.setC_Flatrate_Term(sc);

		delivery.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Lieferung);
		delivery.setStatus(X_C_SubscriptionProgress.STATUS_Geplant);

		// we need to handle the whole issue of confirmation when we want to run this at
		// if (POWrapper.create(sc.getBill_BPartner(),
		// de.metas.flatrate.interfaces.I_C_BPartner.class).isSubscriptionConfirmRequired())
		// {
		// delivery.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_WartetAufBestaetigung);
		// }
		// else
		// {
		delivery.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Laufend);
		// }

		delivery.setEventDate(eventDate);

		delivery.setDropShip_Location_ID(sc.getDropShip_Location_ID());
		delivery.setDropShip_BPartner_ID(sc.getDropShip_BPartner_ID());
		delivery.setDropShip_User_ID(sc.getDropShip_User_ID());

		delivery.setSeqNo(seqNo);

		final int flatrateConditionsId = sc.getC_Flatrate_Conditions_ID();
		final I_M_Product product = sc.getM_Product();

		final I_C_Flatrate_Matching matching = retrieveMatching(ctx, flatrateConditionsId, product, trxName);

		final BigDecimal qtyPerDelivery = matching == null ? BigDecimal.ONE : matching.getQtyPerDelivery();

		final BigDecimal qty = qtyPerDelivery.multiply(sc.getPlannedQtyPerUnit());
		delivery.setQty(qty);

		return delivery;
	}

	@Override
	public I_C_Flatrate_Matching retrieveMatching(final Properties ctx, final int flatrateConditionsId, final I_M_Product product, final String trxName)
	{
		Check.assume(product != null, "Param 'product' is null");

		final String wc = I_C_Flatrate_Matching.COLUMNNAME_C_Flatrate_Conditions_ID + "=? AND " +
				"COALESCE (" + I_C_Flatrate_Matching.COLUMNNAME_M_Product_Category_Matching_ID + ",0) IN (0,?) AND " +
				"COALESCE (" + I_C_Flatrate_Matching.COLUMNNAME_M_Product_ID + ",0) IN (0,?)";

		final I_C_Flatrate_Matching matching = new Query(ctx, I_C_Flatrate_Matching.Table_Name, wc, trxName)
				.setParameters(flatrateConditionsId, product.getM_Product_Category_ID(), product.getM_Product_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_Flatrate_Matching.class);

		return matching;
	}

	@Override
	public int computeNumberOfRuns(final I_C_Flatrate_Transition trans, final Timestamp date)
	{
		final Calendar calTermEnd = new GregorianCalendar();
		calTermEnd.setTime(date);
		if (X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE.equals(trans.getTermDurationUnit()))
		{
			calTermEnd.add(Calendar.YEAR, trans.getTermDuration());
		}
		else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE.equals(trans.getTermDurationUnit()))
		{
			calTermEnd.add(Calendar.MONTH, trans.getTermDuration());
		}
		else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_WocheN.equals(trans.getTermDurationUnit()))
		{
			calTermEnd.add(Calendar.WEEK_OF_YEAR, trans.getTermDuration());
		}
		else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_TagE.equals(trans.getTermDurationUnit()))
		{
			calTermEnd.add(Calendar.DAY_OF_YEAR, trans.getTermDuration());
		}
		else
		{
			Check.assume(false, trans + " has unsupported TermDurationUnit=" + trans.getTermDurationUnit());
		}
		calTermEnd.add(Calendar.DAY_OF_YEAR, -1);

		final Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		Check.assume(calTermEnd.after(cal), "'calTermEnd'=" + calTermEnd + " is after 'cal'=" + cal);

		int numberOfRuns = 0;
		while (cal.before(calTermEnd))
		{
			if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_JahrE.equals(trans.getDeliveryIntervalUnit()))
			{
				cal.add(Calendar.YEAR, trans.getDeliveryInterval());
			}
			else if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_MonatE.equals(trans.getDeliveryIntervalUnit()))
			{
				cal.add(Calendar.MONTH, trans.getDeliveryInterval());
			}
			else if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_WocheN.equals(trans.getDeliveryIntervalUnit()))
			{
				cal.add(Calendar.WEEK_OF_YEAR, trans.getDeliveryInterval());
			}
			else if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_TagE.equals(trans.getDeliveryIntervalUnit()))
			{
				cal.add(Calendar.DAY_OF_YEAR, trans.getDeliveryInterval());
			}
			else
			{
				Check.assume(false, trans + " has unsupported FrequencyType=" + trans.getDeliveryIntervalUnit());
			}

			numberOfRuns++;
		}
		return numberOfRuns;
	}

	@Override
	public I_C_Flatrate_Term createTermForOLCand(final Properties ctx, final I_C_OLCand olCand, final int AD_PInstance_ID, final boolean completeIt, String trxName)
	{
		if (olCand.getC_Flatrate_Conditions_ID() <= 0)
		{
			// found inconsistency
			olCand.setIsError(true);
			olCand.setErrorMsg(Services.get(IMsgBL.class).translate(ctx, "@Missing@ @C_Flatrate_Conditions_ID@"));
			return null;
		}

		final I_C_Flatrate_Term newTerm = Services.get(ISubscriptionBL.class).createSubscriptionTerm(olCand, completeIt);

		final I_C_Contract_Term_Alloc alloc = InterfaceWrapperHelper.create(ctx, I_C_Contract_Term_Alloc.class, trxName);
		alloc.setC_OLCand_ID(olCand.getC_OLCand_ID());
		alloc.setC_Flatrate_Term_ID(newTerm.getC_Flatrate_Term_ID());
		alloc.setAD_PInstance_ID(AD_PInstance_ID);
		InterfaceWrapperHelper.save(alloc);

		olCand.setProcessed(true);
		InterfaceWrapperHelper.save(olCand);

		Services.get(IWFExecutionFactory.class).notifyActivityPerformed(olCand, newTerm); // 03745

		return newTerm;
	}
}
