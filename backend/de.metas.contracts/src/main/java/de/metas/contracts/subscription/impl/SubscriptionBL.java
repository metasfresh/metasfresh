package de.metas.contracts.subscription.impl;

import com.google.common.base.Preconditions;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.Contracts_Constants;
import de.metas.contracts.FlatrateTermPricing;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.interfaces.I_C_OLCand;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Contract_Term_Alloc;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.order.ContractOrderService;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.monitoring.api.IMonitoringBL;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.organization.OrgId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.PInstanceId;
import de.metas.product.IProductDAO;
import de.metas.product.IProductPA;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.api.IWFExecutionFactory;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MNote;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public class SubscriptionBL implements ISubscriptionBL
{
	private static final String SYSCONFIG_CREATE_SUBSCRIPTIONPROGRESS_IN_PAST_DAYS = "C_Flatrate_Term.Create_SubscriptionProgressInPastDays";

	public static final Logger logger = LogManager.getLogger(SubscriptionBL.class);
	public static final int SEQNO_FIRST_VALUE = 10;
	private final ISubscriptionDAO subscriptionDAO = Services.get(ISubscriptionDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public I_C_Flatrate_Term createSubscriptionTerm(
			@NonNull final I_C_OrderLine ol,
			final boolean completeIt)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);

		final I_C_Flatrate_Conditions cond = ol.getC_Flatrate_Conditions();

		final I_C_Flatrate_Term newTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, ol);

		newTerm.setC_OrderLine_Term_ID(ol.getC_OrderLine_ID());
		newTerm.setC_Order_Term_ID(ol.getC_Order_ID());

		newTerm.setC_Flatrate_Conditions_ID(cond.getC_Flatrate_Conditions_ID());

		// important: we need to use qtyEntered here, because qtyOrdered (which
		// is used for pricing) contains the number of goods to be delivered
		// over the whole subscription term
		newTerm.setPlannedQtyPerUnit(ol.getQtyEntered());
		newTerm.setC_UOM_ID(ol.getPrice_UOM_ID());

		newTerm.setStartDate(order.getDatePromised());
		newTerm.setMasterStartDate(order.getDatePromised());

		newTerm.setDeliveryRule(order.getDeliveryRule());
		newTerm.setDeliveryViaRule(order.getDeliveryViaRule());

		final BPartnerLocationAndCaptureId billToLocationId = orderBL.getBillToLocationId(order);

		final BPartnerContactId billToContactId = BPartnerContactId.ofRepoIdOrNull(billToLocationId.getBpartnerId(), order.getBill_User_ID());
		ContractDocumentLocationAdapterFactory
				.billLocationAdapter(newTerm)
				.setFrom(billToLocationId, billToContactId);

		final BPartnerContactId dropshipContactId = BPartnerContactId.ofRepoIdOrNull(ol.getC_BPartner_ID(), ol.getAD_User_ID());

		final BPartnerLocationAndCaptureId dropshipLocationId = orderBL.getShipToLocationId(order);

		ContractDocumentLocationAdapterFactory
				.dropShipLocationAdapter(newTerm)
				.setFrom(dropshipLocationId, dropshipContactId);

		I_C_Flatrate_Data existingData = fetchFlatrateData(ol, order);
		if (existingData == null)
		{
			existingData = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Data.class, ol);
			existingData.setC_BPartner_ID(order.getBill_BPartner_ID());
			save(existingData);
		}

		newTerm.setC_Flatrate_Data(existingData);

		newTerm.setAD_User_InCharge_ID(order.getSalesRep_ID());

		newTerm.setIsSimulation(cond.isSimulation());

		newTerm.setM_Product_ID(ol.getM_Product_ID());
		Services.get(IAttributeSetInstanceBL.class).cloneASI(ol, newTerm);

		newTerm.setPriceActual(ol.getPriceActual());
		newTerm.setC_Currency_ID(ol.getC_Currency_ID());

		setPricingSystemTaxCategAndIsTaxIncluded(ol, newTerm);

		newTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Waiting);
		newTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);
		newTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);

		save(newTerm);

		linkContractsIfNeeded(newTerm);

		if (completeIt)
		{
			Services.get(IDocumentBL.class).processEx(newTerm, X_C_Flatrate_Term.DOCACTION_Complete, X_C_Flatrate_Term.DOCSTATUS_Completed);
		}

		return newTerm;
	}

	private I_C_Flatrate_Data fetchFlatrateData(final I_C_OrderLine ol, final I_C_Order order)
	{
		final I_C_Flatrate_Data existingData = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Data.class, ol)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Data.COLUMNNAME_C_BPartner_ID, order.getBill_BPartner_ID())
				.addOnlyContextClient()
				.create()
				.firstOnly(I_C_Flatrate_Data.class);

		return existingData;
	}

	private void setPricingSystemTaxCategAndIsTaxIncluded(@NonNull final I_C_OrderLine ol, @NonNull final I_C_Flatrate_Term newTerm)
	{
		final PricingSystemTaxCategoryAndIsTaxIncluded computed = computePricingSystemTaxCategAndIsTaxIncluded(ol, newTerm);
		newTerm.setM_PricingSystem_ID(PricingSystemId.toRepoId(computed.getPricingSystemId()));
		newTerm.setC_TaxCategory_ID(computed.getTaxCategoryId().getRepoId());
		newTerm.setIsTaxIncluded(computed.isTaxIncluded());
	}

	@lombok.Value
	private static class PricingSystemTaxCategoryAndIsTaxIncluded
	{
		PricingSystemId pricingSystemId;
		TaxCategoryId taxCategoryId;
		boolean isTaxIncluded;
	}

	private PricingSystemTaxCategoryAndIsTaxIncluded computePricingSystemTaxCategAndIsTaxIncluded(@NonNull final I_C_OrderLine ol, @NonNull final I_C_Flatrate_Term newTerm)
	{
		final I_C_Flatrate_Conditions cond = ol.getC_Flatrate_Conditions();
		if (cond.getM_PricingSystem_ID() > 0)
		{
			final IPricingResult pricingInfo = calculateFlatrateTermPrice(ol, newTerm);
			return new PricingSystemTaxCategoryAndIsTaxIncluded(
					PricingSystemId.ofRepoId(cond.getM_PricingSystem_ID()),
					pricingInfo.getTaxCategoryId(),
					pricingInfo.isTaxIncluded());
		}
		else
		{
			final org.compiere.model.I_C_Order order = ol.getC_Order();
			return new PricingSystemTaxCategoryAndIsTaxIncluded(
					PricingSystemId.ofRepoId(order.getM_PricingSystem_ID()),
					TaxCategoryId.ofRepoIdOrNull(ol.getC_TaxCategory_ID()),
					order.isTaxIncluded());
		}
	}

	private IPricingResult calculateFlatrateTermPrice(@NonNull final I_C_OrderLine ol, @NonNull final I_C_Flatrate_Term newTerm)
	{
		final org.compiere.model.I_C_Order order = ol.getC_Order();
		return FlatrateTermPricing.builder()
				.termRelatedProductId(ProductId.ofRepoId(ol.getM_Product_ID()))
				.qty(ol.getQtyEntered())
				.term(newTerm)
				.priceDate(TimeUtil.asLocalDate(order.getDateOrdered()))
				.build()
				.computeOrThrowEx();
	}

	private void linkContractsIfNeeded(final I_C_Flatrate_Term newTerm)
	{
		final I_C_Flatrate_Term correspondingTerm = retrieveCorrespondingFlatrateTermFromDifferentOrder(newTerm);
		if (correspondingTerm != null)
		{
			correspondingTerm.setC_FlatrateTerm_Next_ID(newTerm.getC_Flatrate_Term_ID());
			save(correspondingTerm);

			// set correct the master date by the previous flatrate term
			newTerm.setMasterStartDate(correspondingTerm.getMasterStartDate());
			save(newTerm);
		}
	}

	@Override
	public int createMissingTermsForOLCands(
			final Properties ctx,
			final boolean completeIt,
			final PInstanceId AD_PInstance_ID,
			final String trxName)
	{
		final I_AD_InputDataSource dataDest = Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(ctx, Contracts_Constants.DATA_DESTINATION_INTERNAL_NAME, true, trxName);

		final String wc = I_C_OLCand.COLUMNNAME_AD_DataDestination_ID + "=? AND " +
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

				Services.get(ITrxManager.class).run(
						trxName, new TrxRunnable2()
						{
							@Override
							public void run(final String trxName) throws Exception
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

								org.compiere.model.I_AD_User userInCharge = Services.get(IBPartnerOrgBL.class).retrieveUserInChargeOrNull(ctx, olCand.getAD_Org_ID(), trxName);
								if (userInCharge == null)
								{
									userInCharge = InterfaceWrapperHelper.create(ctx, Env.getAD_User_ID(ctx), I_AD_User.class, trxName);
								}

								final MNote note = new MNote(ctx, IOLCandBL.MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_0P, userInCharge.getAD_User_ID(), trxName);
								note.setRecord(InterfaceWrapperHelper.getTableId(I_C_OLCand.class), olCand.getC_OLCand_ID());

								note.setClientOrg(userInCharge.getAD_Client_ID(), userInCharge.getAD_Org_ID());

								note.setTextMsg(t[0].getLocalizedMessage());
								note.saveEx();

								olCand.setIsError(true);
								olCand.setAD_Note_ID(note.getAD_Note_ID());
								olCand.setErrorMsg(t[0].getLocalizedMessage());
							}

							@Override
							public boolean doCatch(final Throwable e) throws Throwable
							{
								t[0] = e; // store 'e'
								return true; // rollback transaction
							}
						});

				save(olCand);
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
			@NonNull final I_C_OLCand olCandRecord,
			final boolean completeIt)
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(olCandRecord);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCandRecord);

		final I_C_Flatrate_Conditions cond = olCandRecord.getC_Flatrate_Conditions();

		final ProductId productId = olCandEffectiveValuesBL.getM_Product_Effective_ID(olCandRecord);
		final ProductAndCategoryId productAndCategoryId = Services.get(IProductDAO.class).retrieveProductAndCategoryIdByProductId(productId);

		final I_C_Flatrate_Matching matching = retrieveMatching(
				ctx,
				olCandRecord.getC_Flatrate_Conditions_ID(),
				productAndCategoryId,
				null);

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

		newTerm.setAD_Org_ID(olCandRecord.getAD_Org_ID());
		newTerm.setC_Flatrate_Conditions_ID(cond.getC_Flatrate_Conditions_ID());

		// important: we need to use qtyEntered here, because qtyOrdered (which
		// is used for pricing) contains the number of goods to be delivered
		// over the whole subscription term

		newTerm.setPlannedQtyPerUnit(deliveryQty.multiply(olCandRecord.getQtyEntered()));
		newTerm.setStartDate(olCandRecord.getDateCandidate());

		newTerm.setDeliveryRule(olCandRecord.getDeliveryRule());
		newTerm.setDeliveryViaRule(olCandRecord.getDeliveryViaRule());

		final I_C_BPartner bill_BPartner = olCandEffectiveValuesBL.getBill_BPartner_Effective(olCandRecord, I_C_BPartner.class);

		final BPartnerLocationAndCaptureId billToLocationId = olCandEffectiveValuesBL.getBillLocationAndCaptureEffectiveId(olCandRecord);

		ContractDocumentLocationAdapterFactory
				.billLocationAdapter(newTerm)
				.setFrom(billToLocationId, olCandEffectiveValuesBL.getBillContactEffectiveId(olCandRecord));

		final BPartnerInfo shipToPartnerInfo = olCandEffectiveValuesBL
				.getDropShipPartnerInfo(olCandRecord)
				.orElseGet(() -> olCandEffectiveValuesBL.getBuyerPartnerInfo(olCandRecord));

		ContractDocumentLocationAdapterFactory
				.dropShipLocationAdapter(newTerm)
				.setFrom(shipToPartnerInfo);

		final I_C_Flatrate_Data existingData = Services.get(IFlatrateDAO.class).retrieveOrCreateFlatrateData(bill_BPartner);

		newTerm.setC_Flatrate_Data(existingData);

		final org.compiere.model.I_AD_User userInCharge = Services.get(IBPartnerOrgBL.class).retrieveUserInChargeOrNull(ctx, olCandRecord.getAD_Org_ID(), trxName);
		if (userInCharge != null)
		{
			newTerm.setAD_User_InCharge_ID(userInCharge.getAD_User_ID());
		}
		else
		{
			newTerm.setAD_User_InCharge_ID(Env.getAD_User_ID(ctx));
		}
		newTerm.setIsSimulation(cond.isSimulation());

		newTerm.setM_Product_ID(ProductId.toRepoId(productId));
		Services.get(IAttributeSetInstanceBL.class).cloneASI(olCandRecord, newTerm);

		newTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Waiting);
		newTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);

		save(newTerm);

		if (olCandRecord.getM_PricingSystem_ID() > 0)
		{
			newTerm.setM_PricingSystem_ID(olCandRecord.getM_PricingSystem_ID());
		}

		final IPricingResult pricingResult = olCandBL.computePriceActual(
				olCandRecord,
				newTerm.getPlannedQtyPerUnit(),
				PricingSystemId.ofRepoIdOrNull(newTerm.getM_PricingSystem_ID()),
				TimeUtil.asLocalDate(olCandRecord.getDateCandidate()));

		newTerm.setPriceActual(pricingResult.getPriceStd());
		newTerm.setC_UOM_ID(UomId.toRepoId(pricingResult.getPriceUomId()));

		// task 03805:
		// Make sure the currency ID for term is the same as the one from olCand
		Check.errorIf(pricingResult.getCurrencyRepoId() != olCandRecord.getC_Currency_ID(), "Currency of olCand differs from the currency computed by the pricing engine; olCand={}; pricingResult={}", olCandRecord, pricingResult);
		newTerm.setC_Currency_ID(pricingResult.getCurrencyRepoId());

		InterfaceWrapperHelper.save(newTerm);

		if (completeIt)
		{
			Services.get(IDocumentBL.class).processEx(newTerm, X_C_Flatrate_Term.DOCACTION_Complete, X_C_Flatrate_Term.DOCSTATUS_Completed);
		}

		return newTerm;
	}

	private I_C_SubscriptionProgress createSubscriptionEntries(@NonNull final I_C_Flatrate_Term term)
	{
		Preconditions.checkArgument(term.getC_Flatrate_Conditions_ID() > 0, "Given term has C_Flatrate_Conditions_ID<=0; term=%s", term);

		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition trans = conditions.getC_Flatrate_Transition();

		final int numberOfRuns = computeNumberOfRuns(trans, term.getStartDate());
		Check.assume(numberOfRuns > 0, trans + " has NumberOfEvents > 0");

		Timestamp eventDate = getEventDate(term);

		int seqNo = SEQNO_FIRST_VALUE;

		final List<I_C_SubscriptionProgress> deliveries = new ArrayList<>();

		for (int i = 0; i < numberOfRuns; i++)
		{
			final I_C_SubscriptionProgress delivery = createDelivery(term, eventDate, seqNo);

			save(delivery);
			deliveries.add(delivery);

			seqNo += 10;
			eventDate = mkNextDate(trans, eventDate);
		}

		return deliveries.get(0);
	}

	private Timestamp getEventDate(@NonNull final I_C_Flatrate_Term term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final int daysInPast = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_CREATE_SUBSCRIPTIONPROGRESS_IN_PAST_DAYS, 0, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));

		final Timestamp minimumEventDate = TimeUtil.addDays(de.metas.common.util.time.SystemTime.asDayTimestamp(), -daysInPast);
		final Timestamp eventDate = term.getStartDate();

		if (minimumEventDate.after(eventDate))
		{
			return minimumEventDate;
		}

		return eventDate;
	}

	@Override
	public void evalCurrentSPs(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp currentDate)
	{

		I_C_SubscriptionProgress currentProgressRecord = retrieveOrCreateSP(term, currentDate);

		// see if there are further SPs to evaluate.
		Timestamp lastSPDate = currentProgressRecord.getEventDate();
		int lastSPSeqNo = currentProgressRecord.getSeqNo();
		int count = 1;

		while (!currentProgressRecord.getEventDate().after(currentDate))
		{
			markPlannedPauseRecordAsExecuted(currentDate, currentProgressRecord);

			updateTermStatus(term, currentProgressRecord);

			final SubscriptionProgressQuery query = SubscriptionProgressQuery.builder()
					.term(term)
					.eventDateNotBefore(currentDate)
					.seqNoNotLessThan(currentProgressRecord.getSeqNo() + 1)
					.excludedStatus(X_C_SubscriptionProgress.STATUS_Done)
					.excludedStatus(X_C_SubscriptionProgress.STATUS_Delivered)
					.build();

			// see if there is an SP for the next loop iteration
			currentProgressRecord = subscriptionDAO.retrieveFirstSubscriptionProgress(query);
			if (currentProgressRecord == null)
			{
				break;
			}

			count++;

			// make sure that we don't get stuck in an infinite loop
			Check.assume(!lastSPDate.after(currentProgressRecord.getEventDate()), "");
			Check.assume(lastSPSeqNo < currentProgressRecord.getSeqNo(), "");

			lastSPSeqNo = currentProgressRecord.getSeqNo();
			lastSPDate = currentProgressRecord.getEventDate();
		}
		save(term);
		logger.info("Evaluated {} {} records", count, I_C_SubscriptionProgress.Table_Name);
	}

	private void updateTermStatus(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final I_C_SubscriptionProgress currentProgressRecord)
	{
		final String currentStatus = term.getContractStatus();

		final boolean dontUpdateTermStatus = //
				X_C_Flatrate_Term.CONTRACTSTATUS_EndingContract.equals(currentStatus)
						|| X_C_Flatrate_Term.CONTRACTSTATUS_Info.equals(currentStatus)
						|| X_C_Flatrate_Term.CONTRACTSTATUS_Quit.equals(currentStatus)
						|| X_C_Flatrate_Term.CONTRACTSTATUS_Voided.equals(currentStatus);
		if (dontUpdateTermStatus)
		{
			return; // we don't want to loose this status in the flatrate term
		}

		term.setContractStatus(currentProgressRecord.getContractStatus());
	}

	/**
	 * @param term
	 * @param currentDate
	 * @return never returns {@code null}
	 */
	private I_C_SubscriptionProgress retrieveOrCreateSP(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp currentDate)
	{
		final ISubscriptionDAO subscriptionPA = Services.get(ISubscriptionDAO.class);

		final SubscriptionProgressQuery query = SubscriptionProgressQuery.builder()
				.term(term)
				.eventDateNotBefore(currentDate)
				.excludedStatus(X_C_SubscriptionProgress.STATUS_Done)
				.excludedStatus(X_C_SubscriptionProgress.STATUS_Delivered)
				.build();

		final I_C_SubscriptionProgress sp = subscriptionPA.retrieveFirstSubscriptionProgress(query);
		if (sp != null)
		{
			return sp;
		}

		try (final IAutoCloseable c = Services.get(IShipmentScheduleBL.class).postponeMissingSchedsCreationUntilClose())
		{
			logger.info("Creating initial SPs for term={}", term);
			return createSubscriptionEntries(term);
		}
	}

	private boolean markPlannedPauseRecordAsExecuted(
			@NonNull final Timestamp currentDate,
			@NonNull final I_C_SubscriptionProgress sp)
	{
		Check.errorIf(sp.getEventDate().after(currentDate),
					  "The event date {} of the given subscriptionProgress is after currentDate={}; subscriptionProgress={}",
					  sp.getEventDate(), currentDate, sp);

		if (isPlannedStartPause(sp))
		{
			sp.setStatus(X_C_SubscriptionProgress.STATUS_Done);
			save(sp);
			return true;

		}
		else if (isPlannedEndPause(sp))
		{
			sp.setStatus(X_C_SubscriptionProgress.STATUS_Done);
			save(sp);
			return true;
		}

		return false;
	}

	private boolean isPlannedStartPause(final I_C_SubscriptionProgress sp)
	{
		return X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause.equals(sp.getEventType())
				&& X_C_SubscriptionProgress.STATUS_Planned.equals(sp.getStatus());
	}

	private boolean isPlannedEndPause(final I_C_SubscriptionProgress sp)
	{
		return X_C_SubscriptionProgress.EVENTTYPE_EndOfPause.equals(sp.getEventType())
				&& X_C_SubscriptionProgress.STATUS_Planned.equals(sp.getStatus());
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
	public void evalDeliveries(final Properties ctx)
	{
		final ISubscriptionDAO subscriptionPA = Services.get(ISubscriptionDAO.class);

		final List<I_C_SubscriptionProgress> deliveries = subscriptionPA.retrievePlannedAndDelayedDeliveries(ctx, SystemTime.asTimestamp(), ITrx.TRXNAME_ThreadInherited);

		logger.debug("Going to add shipment schedule entries for {} subscription deliveries", deliveries.size());

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		//
		final Set<Integer> delayedControlIds = new HashSet<>();

		for (final I_C_SubscriptionProgress sd : deliveries)
		{
			final I_C_Flatrate_Term sc = sd.getC_Flatrate_Term();

			if (delayedControlIds.contains(sd.getC_Flatrate_Term_ID()))
			{
				logger.info("An earlier SP of {} is delayed => also delaying {}", sc, sd);
				sd.setStatus(X_C_SubscriptionProgress.STATUS_Delayed);
				save(sd);
				continue;
			}

			final List<I_M_ShipmentSchedule> openScheds = shipmentSchedulePA.retrieveUnprocessedForRecord(TableRecordReference.of(sd));

			if (openScheds.isEmpty())
			{
				if (X_C_SubscriptionProgress.STATUS_Delayed.equals(sd.getStatus()))
				{
					logger.info("{} is not delayed anymore", sd);
					sd.setStatus(X_C_SubscriptionProgress.STATUS_Planned);
					save(sd);
				}
			}
			else
			{
				logger.debug("{} is deplayed because there is at least on open delivery", sd);
				sd.setStatus(X_C_SubscriptionProgress.STATUS_Delayed);
				save(sd);

				delayedControlIds.add(sd.getC_Flatrate_Term_ID());
				continue;
			}
		}
	}

	@Override
	public BigDecimal computePriceDifference(
			final Properties ctx,
			final PricingSystemId pricingSystemId,
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

			if (!X_C_SubscriptionProgress.EVENTTYPE_Delivery.equals(sp.getEventType()))
			{
				throw new IllegalArgumentException(sp + " has event type " + sp.getEventType());
			}
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(
				deliveries.get(0).getC_Flatrate_Term().getC_OrderLine_Term(),
				I_C_OrderLine.class);

		final BPartnerLocationAndCaptureId bpLocationId = OrderLineDocumentLocationAdapterFactory.locationAdapter(ol).getBPartnerLocationAndCaptureId();

		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		final PriceListId plId = priceListDAO.retrievePriceListIdByPricingSyst(
				pricingSystemId,
				bpLocationId,
				SOTrx.SALES);

		final IProductPA productPA = Services.get(IProductPA.class);
		final CountryId countryId = Services.get(IBPartnerBL.class).getCountryId(bpLocationId);

		final BigDecimal newPrice = productPA.retrievePriceStd(
				OrgId.ofRepoId(ol.getAD_Org_ID()),
				ol.getM_Product_ID(),
				ol.getC_BPartner_ID(),
				plId.getRepoId(),
				countryId,
				qtySum,
				true).multiply(qtySum);

		final BigDecimal oldPrice = ol.getPriceActual().multiply(qtySum);

		return newPrice.subtract(oldPrice);
	}

	private I_C_SubscriptionProgress createDelivery(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp eventDate,
			final int seqNo)
	{
		final I_C_SubscriptionProgress delivery = InterfaceWrapperHelper.newInstance(I_C_SubscriptionProgress.class, term);

		delivery.setAD_Org_ID(term.getAD_Org_ID());
		delivery.setC_Flatrate_Term(term);

		delivery.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		delivery.setStatus(X_C_SubscriptionProgress.STATUS_Planned);

		delivery.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);

		delivery.setEventDate(eventDate);

		setDeliveryDropShipValuesFromTerm(delivery, term);

		delivery.setSeqNo(seqNo);

		final int flatrateConditionsId = term.getC_Flatrate_Conditions_ID();
		final ProductId productId = ProductId.ofRepoIdOrNull(term.getM_Product_ID());
		final ProductAndCategoryId productAndCategoryId = Services.get(IProductDAO.class).retrieveProductAndCategoryIdByProductId(productId);

		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);
		final I_C_Flatrate_Matching matching = retrieveMatching(ctx, flatrateConditionsId, productAndCategoryId, trxName);

		final BigDecimal qtyPerDelivery = matching == null ? BigDecimal.ONE : matching.getQtyPerDelivery();

		final BigDecimal qty = qtyPerDelivery.multiply(term.getPlannedQtyPerUnit());
		delivery.setQty(qty);

		return delivery;
	}

	private void setDeliveryDropShipValuesFromTerm(
			@NonNull final I_C_SubscriptionProgress delivery,
			@NonNull final I_C_Flatrate_Term term)
	{
		Preconditions.checkArgument(term.getDropShip_Location_ID() > 0, "The given term has DropShip_Location_ID<=0; term=%s", term);
		Preconditions.checkArgument(term.getDropShip_BPartner_ID() > 0, "The given term has DropShip_BPartner_ID<=0; term=%s", term);

		delivery.setDropShip_Location_ID(term.getDropShip_Location_ID());
		delivery.setDropShip_BPartner_ID(term.getDropShip_BPartner_ID());
		delivery.setDropShip_User_ID(term.getDropShip_User_ID());
	}

	@Override
	public I_C_Flatrate_Matching retrieveMatching(
			final Properties ctx,
			final int flatrateConditionsId,
			@NonNull final ProductAndCategoryId productAndCategoryId,
			final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_C_Flatrate_Matching.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Matching.COLUMNNAME_C_Flatrate_Conditions_ID, flatrateConditionsId)
				.addInArrayFilter(I_C_Flatrate_Matching.COLUMNNAME_M_Product_Category_Matching_ID, productAndCategoryId.getProductCategoryId(), null)
				.addInArrayFilter(I_C_Flatrate_Matching.COLUMNNAME_M_Product_ID, productAndCategoryId.getProductId(), null)
				.create()
				.setClient_ID()
				.firstOnly(I_C_Flatrate_Matching.class);
	}

	@Override
	public int computeNumberOfRuns(final I_C_Flatrate_Transition trans, final Timestamp date)
	{
		final Calendar calTermEnd = addDurationToDate(trans, date);

		final Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		Check.assume(calTermEnd.after(cal), "'calTermEnd'=" + calTermEnd + " is after 'cal'=" + cal);

		final int deliveryInterval = trans.getDeliveryInterval();
		if (deliveryInterval <= 0)
		{
			throw new AdempiereException("Invalid deliveryInterval=" + deliveryInterval + " for " + trans);
		}

		final String deliveryIntervalUnit = trans.getDeliveryIntervalUnit();

		int numberOfRuns = 0;
		while (cal.before(calTermEnd))
		{
			if (X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_JahrE.equals(deliveryIntervalUnit))
			{
				cal.add(Calendar.YEAR, deliveryInterval);
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
				Check.assume(false, trans + " has unsupported FrequencyType=" + deliveryIntervalUnit);
			}

			numberOfRuns++;
		}
		return numberOfRuns;
	}

	private Calendar addDurationToDate(final I_C_Flatrate_Transition trans, final Timestamp date)
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
		return calTermEnd;
	}

	@Override
	public I_C_Flatrate_Term createTermForOLCand(final Properties ctx, final I_C_OLCand olCand, final PInstanceId AD_PInstance_ID, final boolean completeIt, final String trxName)
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
		alloc.setAD_PInstance_ID(PInstanceId.toRepoId(AD_PInstance_ID));
		save(alloc);

		olCand.setProcessed(true);
		save(olCand);

		Services.get(IWFExecutionFactory.class).notifyActivityPerformed(olCand, newTerm); // 03745

		return newTerm;
	}

	private I_C_Flatrate_Term retrieveCorrespondingFlatrateTermFromDifferentOrder(@NonNull final I_C_Flatrate_Term newTerm)
	{
		final OrderId currentOrderId = OrderId.ofRepoId(newTerm.getC_OrderLine_Term().getC_Order_ID());

		final ContractOrderService contractOrderService = Adempiere.getBean(ContractOrderService.class);
		final OrderId orderId = contractOrderService.retrieveLinkedFollowUpContractOrder(currentOrderId);

		if (orderId == null)
		{
			return null;
		}

		final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);
		final List<I_C_Flatrate_Term> orderTerms = contractsDAO.retrieveFlatrateTermsForOrderIdLatestFirst(orderId);
		final I_C_Flatrate_Term suitableTerm = orderTerms
				.stream()
				.filter(oldTerm -> oldTerm.getM_Product_ID() == newTerm.getM_Product_ID()
						&& oldTerm.getC_Flatrate_Conditions_ID() == newTerm.getC_Flatrate_Conditions_ID())
				.findFirst()
				.orElse(null);

		// check if there is an extended term
		if (suitableTerm == null)
		{
			return null;
		}

		final I_C_Flatrate_Term topTerm = contractOrderService.retrieveTopExtendedTerm(suitableTerm);

		return topTerm == null ? suitableTerm : topTerm;
	}

	@Override
	public I_C_Flatrate_Term retrieveLastFlatrateTermFromOrder(@NonNull final de.metas.contracts.order.model.I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

		final List<I_C_Flatrate_Term> orderTerms = contractsDAO.retrieveFlatrateTermsForOrderIdLatestFirst(orderId);
		return orderTerms.isEmpty() ? null : orderTerms.get(0);
	}

	@Override
	public boolean isActiveTerm(@NonNull final I_C_Flatrate_Term term)
	{
		final String status = term.getContractStatus();
		final boolean isCancelledOrVoided = X_C_Flatrate_Term.CONTRACTSTATUS_Voided.equals(status)
				|| X_C_Flatrate_Term.CONTRACTSTATUS_Quit.equals(status)
				|| X_C_Flatrate_Term.CONTRACTSTATUS_EndingContract.equals(status);

		return !isCancelledOrVoided;
	}

	@Override
	public void updateQtysAndPrices(
			@NonNull final I_C_OrderLine ol,
			@NonNull final SOTrx soTrx,
			final boolean updatePriceEnteredAndDiscountOnlyIfNotAlreadySet)
	{
		final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final I_C_Flatrate_Conditions flatrateConditions = ol.getC_Flatrate_Conditions();
		final org.compiere.model.I_C_Order order = ol.getC_Order();

		final PricingSystemId pricingSysytemId;

		if (flatrateConditions.getM_PricingSystem_ID() > 0)
		{
			pricingSysytemId = PricingSystemId.ofRepoId(flatrateConditions.getM_PricingSystem_ID());
		}
		else
		{
			pricingSysytemId = PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());
		}

		final BPartnerLocationAndCaptureId bpLocationId = OrderLineDocumentLocationAdapterFactory.locationAdapter(ol).getBPartnerLocationAndCaptureId();
		final Timestamp date = order.getDateOrdered();

		final PriceListId subscriptionPLId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSysytemId, bpLocationId, soTrx);

		final int numberOfRuns = subscriptionBL.computeNumberOfRuns(flatrateConditions.getC_Flatrate_Transition(), date);

		final Properties ctx = Env.getCtx();
		final ProductId productId = ProductId.ofRepoIdOrNull(ol.getM_Product_ID());
		final ProductAndCategoryId productAndCategoryId = Services.get(IProductDAO.class).retrieveProductAndCategoryIdByProductId(productId);
		final I_C_Flatrate_Matching matching = subscriptionBL.retrieveMatching(
				ctx,
				ol.getC_Flatrate_Conditions_ID(),
				productAndCategoryId,
				ITrx.TRXNAME_None);

		final Quantity qtyEntered = orderLineBL.getQtyEntered(ol);
		final Quantity qtyOrdered = uomConversionBL.convertToProductUOM(qtyEntered, productId);

		final Quantity qtyOrderedPerRun;
		if (matching != null && matching.getQtyPerDelivery().signum() > 0)
		{
			final Quantity qtyPerDelivery = Quantity.of(matching.getQtyPerDelivery(), qtyOrdered.getUOM());
			qtyOrderedPerRun = qtyPerDelivery.min(qtyOrdered);
		}
		else
		{
			qtyOrderedPerRun = qtyOrdered;
		}

		// priceQty is the qty do be delivered during one complete subscription term
		final Quantity priceQty = qtyOrderedPerRun.multiply(numberOfRuns);

		final UomId olUomId = UomId.ofRepoId(ol.getC_UOM_ID());
		final Quantity orderLineQty = uomConversionBL.convertQuantityTo(priceQty, productId, olUomId);
		final BigDecimal olQty = orderLineQty.toBigDecimal();
		// qty ordered needs to be set because it will be used to compute the
		// line's NetLineAmount in MOrderLine.beforeSave()
		ol.setQtyOrdered(olQty);

		ol.setQtyEnteredInPriceUOM(olQty);

		// now compute the new prices
		orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
										 .orderLine(ol)
										 .priceListIdOverride(subscriptionPLId)
										 .qtyOverride(orderLineQty)
										 .resultUOM(OrderLinePriceUpdateRequest.ResultUOM.PRICE_UOM)
										 .updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(updatePriceEnteredAndDiscountOnlyIfNotAlreadySet)
										 .updateLineNetAmt(true)
										 .build());
	}

	@Override
	public void createPriceChange(final @NonNull I_C_Flatrate_Term term)
	{
		insertSubscriptionProgressEvent(term, X_C_SubscriptionProgress.EVENTTYPE_Price, null);
	}

	@Override
	public void createQtyChange(final @NonNull I_C_Flatrate_Term term, @Nullable final BigDecimal newQty)
	{
		insertSubscriptionProgressEvent(term, X_C_SubscriptionProgress.EVENTTYPE_Quantity, newQty);
	}

	private void insertSubscriptionProgressEvent(@NonNull final I_C_Flatrate_Term term,
			@NonNull final String eventType,
			@Nullable final Object eventValue)
	{
		final Timestamp today = SystemTime.asDayTimestamp();
		final List<I_C_SubscriptionProgress> subscriptionProgressList = subscriptionDAO.retrieveSubscriptionProgresses(SubscriptionProgressQuery.builder()
																															   .term(term).build());
		final int seqNoToUse = getSeqNoToUse(today, subscriptionProgressList);//default start seq number. Should not happen.
		final I_C_SubscriptionProgress changeEvent = newInstance(I_C_SubscriptionProgress.class);

		changeEvent.setEventType(eventType);
		changeEvent.setC_Flatrate_Term(term);
		changeEvent.setStatus(X_C_SubscriptionProgress.STATUS_Planned);
		changeEvent.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
		changeEvent.setEventDate(today);
		changeEvent.setSeqNo(seqNoToUse);
		changeEvent.setDropShip_BPartner_ID(term.getDropShip_BPartner_ID());
		changeEvent.setDropShip_Location_ID(term.getDropShip_Location_ID());
		addEventValue(changeEvent, eventType, eventValue);
		save(changeEvent);

		subscriptionProgressList.stream()
				.filter(sp -> today.before(sp.getEventDate()))
				.forEach(this::incrementSeqNoAndSave);
	}

	private int getSeqNoToUse(final Timestamp today, final List<I_C_SubscriptionProgress> subscriptionProgressList)
	{
		return subscriptionProgressList.stream()
				.filter(sp -> today.before(sp.getEventDate()))
				.mapToInt(I_C_SubscriptionProgress::getSeqNo)
				.min()//smallest seqNo after today's date
				.orElse(subscriptionProgressList.stream()
								.mapToInt(I_C_SubscriptionProgress::getSeqNo)
								.map(Math::incrementExact)
								.max()//greatest seqNo + 1 before today
								.orElse(SEQNO_FIRST_VALUE));
	}

	private void addEventValue(final I_C_SubscriptionProgress changeEvent, final String eventType, @Nullable final Object eventValue)
	{
		if (Objects.equals(eventType, X_C_SubscriptionProgress.EVENTTYPE_Quantity) && eventValue != null)
		{
			changeEvent.setQty((BigDecimal)eventValue);
		}
	}

	private void incrementSeqNoAndSave(final I_C_SubscriptionProgress subscriptionProgress)
	{
		subscriptionProgress.setSeqNo(subscriptionProgress.getSeqNo() + 1);
		save(subscriptionProgress);
	}

	public boolean isSubscription(@NonNull final I_C_OrderLine ol)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(ol.getC_Flatrate_Conditions_ID());

		if (conditionsId == null)
		{
			return false;
		}

		final I_C_Flatrate_Conditions typeConditions = flatrateDAO.getConditionsById(conditionsId);

		return X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(typeConditions.getType_Conditions());
	}
}
