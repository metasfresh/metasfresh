/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.contracts.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.calendar.standard.ICalendarBL;
import de.metas.calendar.standard.ICalendarDAO;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermPricing;
import de.metas.contracts.FlatrateTermRequest.CreateFlatrateTermRequest;
import de.metas.contracts.FlatrateTermRequest.FlatrateTermBillPartnerRequest;
import de.metas.contracts.FlatrateTermRequest.FlatrateTermPriceRequest;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.IFlatrateTermEventService;
import de.metas.contracts.event.FlatrateUserNotificationsProducer;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.invoicecandidate.FlatrateDataEntryHandler;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsQuery;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.copy_with_details.CopyRecordSupport;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PricingSystemId;
import de.metas.process.PInstanceId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.time.InstantInterval;
import de.metas.workflow.api.IWFExecutionFactory;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

import static de.metas.contracts.model.X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_ExtensionNotAllowed;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.disableReadOnlyColumnCheck;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getPO;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

public class FlatrateBL implements IFlatrateBL
{
	private static final Logger logger = LogManager.getLogger(FlatrateBL.class);

	private static final AdMessageKey MSG_FLATRATEBL_INVOICING_ENTRY_NOT_CO_3P = AdMessageKey.of("FlatrateBL_InvoicingEntry_Not_CO");

	private static final AdMessageKey MSG_FLATRATEBL_INVOICE_CANDIDATE_TO_RECOMPUTE_1P = AdMessageKey.of("FlatrateBL_InvoicingCand_ToRecompute");

	private static final AdMessageKey MSG_FLATRATEBL_INVOICE_CANDIDATE_QTY_TO_INVOICE_1P = AdMessageKey.of("FlatrateBL_InvoicingCand_QtyToInvoice");

	private static final AdMessageKey MSG_DATA_ENTRY_CREATE_FLAT_FEE_4P = AdMessageKey.of("DataEntry_Create_FlatFee");

	private static final AdMessageKey MSG_DATA_ENTRY_CREATE_HOLDING_FEE_3P = AdMessageKey.of("DataEntry_Create_HoldingFee");

	private static final AdMessageKey MSG_TERM_NEW_UNCOMPLETED_0P = AdMessageKey.of("FlatrateTerm_New_Uncompleted_Term");
	private static final AdMessageKey MSG_TERM_NEW_COMPLETED_0P = AdMessageKey.of("FlatrateTerm_New_Completed_Term");
	private static final AdMessageKey MSG_TERM_NO_NEW_0P = AdMessageKey.of("FlatrateTerm_No_New_Term");

	private static final AdMessageKey MSG_ORG_WAREHOUSE_MISSING = AdMessageKey.of("de.metas.flatrate.Org.Warehouse_Missing");

	/**
	 * Message for announcing the user that there are overlapping terms for the term they want to complete
	 */
	public static final AdMessageKey MSG_HasOverlapping_Term = AdMessageKey.of("de.metas.flatrate.process.C_Flatrate_Term_Create.OverlappingTerm");

	public static final AdMessageKey MSG_INFINITE_LOOP = AdMessageKey.of("de.metas.contracts.impl.FlatrateBL.extendContract.InfinitLoopError");

	public final static AdMessageKey MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED = AdMessageKey.of("MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED");

	public final static String MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS = "@MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS@";

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_Flatrate_Conditions getConditionsById(final ConditionsId flatrateConditionsId)
	{
		return flatrateDAO.getConditionsById(flatrateConditionsId);
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(
			final I_C_BPartner bpartner,
			final I_C_Flatrate_Conditions flatrateConditions)
	{
		return flatrateDAO.retrieveTerms(bpartner, flatrateConditions);
	}

	@Override
	public void save(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		flatrateDAO.save(flatrateTerm);
	}

	@Override
	@Nullable
	public String beforeCompleteDataEntry(final I_C_Flatrate_DataEntry dataEntry)
	{
		Check.assume(!dataEntry.isSimulation(), dataEntry + " has IsSimulation='N'");

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final I_C_Flatrate_Term term = dataEntry.getC_Flatrate_Term();
		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		if (fc == null)
		{
			// nothing to do
			return null;
		}

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(fc.getType_Conditions())
				&& fc.getC_UOM_ID() != dataEntry.getC_UOM_ID())
		{
			// nothing to do
			return null;
		}

		final BigDecimal qtyToInvoice;

		if (X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()))
		{
			final List<I_C_Flatrate_DataEntry> invoicingEntries = flatrateDB.retrieveDataEntries(
					dataEntry.getC_Flatrate_Term(),
					X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased,
					UomId.ofRepoIdOrNull(dataEntry.getC_UOM_ID()));

			for (final I_C_Flatrate_DataEntry invoicingEntry : invoicingEntries)
			{
				// make sure that all invoicing entries are complete
				if (!X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(invoicingEntry.getDocStatus()))
				{
					final Properties ctx = getCtx(dataEntry);

					final ADReferenceService adReferenceService = ADReferenceService.get();
					final ITranslatableString competed = adReferenceService
							.retrieveListNameTranslatableString(
									X_C_Flatrate_DataEntry.DOCSTATUS_AD_Reference_ID,
									X_C_Flatrate_DataEntry.DOCSTATUS_Completed);
					final ITranslatableString uomName = uomDAO.getName(UomId.ofRepoId(invoicingEntry.getC_UOM_ID()));
					return msgBL.getMsg(ctx,
							FlatrateBL.MSG_FLATRATEBL_INVOICING_ENTRY_NOT_CO_3P,
							new Object[] {
									invoicingEntry.getC_Period().getName(),
									uomName,
									competed.translate(Env.getAD_Language()) });
				}
			}

			// Note: we won't invoice the complete Qty_Reported, but only the difference to the qtys that we already
			// invoiced
			qtyToInvoice = dataEntry.getQty_Reported().subtract(dataEntry.getQty_Planned());
		}
		else
		{
			if (fc.isClosingWithActualSum() && !fc.isSimulation())
			{
				//
				// make sure that all invoice candidates to clear are up to date
				final StringBuilder sb = new StringBuilder();
				for (final I_C_Invoice_Clearing_Alloc ica : flatrateDB.retrieveClearingAllocs(dataEntry))
				{
					final I_C_Invoice_Candidate invoiceCandToClear = ica.getC_Invoice_Cand_ToClear();
					if (invoiceCandToClear.isToRecompute())
					{
						if (sb.length() != 0)
						{
							sb.append(", ");
						}
						sb.append(ica.getC_Invoice_Cand_ToClear_ID());
					}
				}
				if (sb.length() > 0)
				{
					final Properties ctx = getCtx(dataEntry);
					return msgBL.getMsg(ctx,
							FlatrateBL.MSG_FLATRATEBL_INVOICE_CANDIDATE_TO_RECOMPUTE_1P,
							new Object[] {
									sb.toString() });
				}

				//
				// Make sure that all invoice candidates to clear are ready to invoice
				// Note: If we allow 'dataEntry' to be completed now, there will be a problem later on
				// when QtyToInvoice is adjusted, because C_Flatrate_DataEntry.ActualQty cannot be updated
				// anymore after completing.
				for (final I_C_Invoice_Clearing_Alloc ica : flatrateDB.retrieveClearingAllocs(dataEntry))
				{
					final I_C_Invoice_Candidate invoiceCandToClear = ica.getC_Invoice_Cand_ToClear();
					if (invoiceCandToClear.getQtyOrdered().compareTo(invoiceCandToClear.getQtyToInvoice()) > 0)
					{
						if (sb.length() != 0)
						{
							sb.append(", ");
						}
						sb.append(ica.getC_Invoice_Cand_ToClear_ID());
					}
				}
				if (sb.length() > 0)
				{
					final Properties ctx = getCtx(dataEntry);
					return msgBL.getMsg(ctx,
							FlatrateBL.MSG_FLATRATEBL_INVOICE_CANDIDATE_QTY_TO_INVOICE_1P,
							new Object[] {
									sb.toString() });
				}
			}
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()),
					dataEntry + "has type=" + X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
			qtyToInvoice = dataEntry.getQty_Reported();
		}

		final List<I_C_Invoice_Candidate> newCands;
		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(fc.getType_Conditions()))
		{
			newCands = createCandForFlatFeeDataEntry(fc, dataEntry);
		}
		else
		{
			newCands = Collections.singletonList(createCandForDataEntry(term, dataEntry, qtyToInvoice));
		}

		if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
		{
			for (final I_C_Invoice_Candidate newCand : newCands)
			{
				if (newCand.getM_Product_ID() != fc.getM_Product_Flatrate_ID())
				{
					continue;
				}

				final List<I_C_Invoice_Clearing_Alloc> allocLines = flatrateDB.retrieveClearingAllocs(dataEntry);

				for (final I_C_Invoice_Clearing_Alloc alloc : allocLines)
				{
					// consider the invoice candidate that awaits clearing as invoiced
					final I_C_Invoice_Candidate candToClear = alloc.getC_Invoice_Cand_ToClear();

					candToClear.setQtyInvoiced(candToClear.getQtyToInvoice());
					candToClear.setQtyToInvoice(BigDecimal.ZERO);

					InterfaceWrapperHelper.saveRecord(candToClear);

					// C_Flatrate_DataEntry_ID and QtyCleared have already been set by InvoiceCandidateValidator
					Check.assume(alloc.getC_Flatrate_DataEntry_ID() == dataEntry.getC_Flatrate_DataEntry_ID(),
							alloc + " sould have C_Flatrate_DataEntry_ID=" + dataEntry.getC_Flatrate_DataEntry_ID() + " but has " + alloc.getC_Flatrate_DataEntry_ID());

					// update the allocation record
					alloc.setC_Invoice_Candidate_ID(newCand.getC_Invoice_Candidate_ID());
					InterfaceWrapperHelper.saveRecord(alloc);
				}
			}
		}
		return null;
	}

	private List<I_C_Invoice_Candidate> createCandForFlatFeeDataEntry(
			final I_C_Flatrate_Conditions fc,
			final I_C_Flatrate_DataEntry dataEntry)
	{
		Check.assume(!dataEntry.isSimulation(), dataEntry + " has IsSimulation='N'");

		Check.assume(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(fc.getType_Conditions())
						|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable.equals(fc.getType_Conditions()),
				fc + " has Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee
						+ " or " + X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable);

		final Properties ctx = getCtx(fc);
		final String trxName = getTrxName(fc);

		final I_C_Flatrate_Term term = dataEntry.getC_Flatrate_Term();

		final List<I_C_Invoice_Candidate> result = new ArrayList<>();

		final int productIdCand;
		final int productIdCorrCand = fc.getM_Product_Actual_ID();

		if (X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()))
		{
			productIdCand = fc.getM_Product_Correction_ID();
		}
		else
		{
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()), dataEntry + " has type " + X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
			productIdCand = fc.getM_Product_Flatrate_ID();
		}

		if (productIdCorrCand == productIdCand)
		{
			final BigDecimal priceActual;
			if (fc.isCorrectionAmtAtClosing() && X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
			{
				// we will invoice only the flat rate amount (the correction will be done at closing)
				priceActual = dataEntry.getFlatrateAmt();
			}
			else
			{
				// we will invoice the flat rate amount plus the correction amount
				priceActual = dataEntry.getFlatrateAmt().add(dataEntry.getFlatrateAmtCorr());
			}

			final int productId = fc.getM_Product_Flatrate_ID();
			Check.assume(productId > 0,
					fc + " with Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee + " has no M_Product_Flatrate");

			final I_C_Invoice_Candidate newIc = createCand(ctx, term, dataEntry, productId, priceActual, trxName);
			result.add(newIc);

			// note: we assume that 'dataEntry' is saved somewhere outside this method!
			dataEntry.setC_Invoice_Candidate_ID(newIc.getC_Invoice_Candidate_ID());
		}
		else
		{
			final I_C_Invoice_Candidate newFlatrateIc = createCand(ctx, term, dataEntry, productIdCand, dataEntry.getFlatrateAmt(), trxName);
			result.add(newFlatrateIc);

			// note: we assume that 'dataEntry' is saved somewhere outside this method!
			dataEntry.setC_Invoice_Candidate_ID(newFlatrateIc.getC_Invoice_Candidate_ID());

			if (dataEntry.getFlatrateAmtCorr().signum() != 0)
			{
				if (fc.isCorrectionAmtAtClosing() && X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType())
						|| !fc.isCorrectionAmtAtClosing() && X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))

				{
					final I_C_Invoice_Candidate newCorrectionIc = createCand(ctx, term, dataEntry, productIdCorrCand, dataEntry.getFlatrateAmtCorr(), trxName);
					result.add(newCorrectionIc);

					dataEntry.setC_Invoice_Candidate_Corr_ID(newCorrectionIc.getC_Invoice_Candidate_ID());
				}
			}
		}

		return result;
	}

	private I_C_Invoice_Candidate createCand(
			final Properties ctx,
			final I_C_Flatrate_Term term,
			final I_C_Flatrate_DataEntry dataEntry,
			final int productId,
			final BigDecimal priceActual,
			final String trxName)
	{
		final I_C_Invoice_Candidate newCand = create(ctx, I_C_Invoice_Candidate.class, trxName);
		Check.assume(newCand.getAD_Client_ID() == dataEntry.getAD_Client_ID(), "ctx contains the correct AD_Client_ID");

		final OrgId orgId = OrgId.ofRepoId(dataEntry.getAD_Org_ID());
		newCand.setAD_Org_ID(orgId.getRepoId());

		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();
		newCand.setM_PricingSystem_ID(fc.getM_PricingSystem_ID());

		newCand.setM_Product_ID(productId);
		newCand.setQtyOrdered(BigDecimal.ONE);

		newCand.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		newCand.setPriceActual(priceActual);
		newCand.setPrice_UOM_ID(term.getC_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		newCand.setPriceEntered(priceActual); // cg : task 04917 -- same as price actual

		newCand.setDiscount(BigDecimal.ZERO);

		newCand.setInvoiceRule(fc.getInvoiceRule());
		newCand.setC_Currency_ID(term.getC_Currency_ID());

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(newCand)
				.setFrom(ContractLocationHelper.extractBillLocation(term));

		newCand.setDateOrdered(dataEntry.getC_Period().getEndDate());

		newCand.setAD_Table_ID(adTableDAO.retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
		newCand.setRecord_ID(dataEntry.getC_Flatrate_DataEntry_ID());

		// 07442 activity and tax
		final ActivityId activityId = findActivityIdOrNull(term, productId);

		newCand.setC_Activity_ID(ActivityId.toRepoId(activityId));
		newCand.setIsTaxIncluded(term.isTaxIncluded());

		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(term.getC_TaxCategory_ID());

		final BPartnerLocationAndCaptureId shipToLocationId = CoalesceUtil.coalesceSuppliers(
				() -> ContractLocationHelper.extractDropshipLocationId(term),
				() -> ContractLocationHelper.extractBillToLocationId(term));
		final VatCodeId vatCodeId = null;

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				term,
				taxCategoryId,
				productId,
				dataEntry.getDate_Reported(),// shipDate
				orgId,
				null,
				shipToLocationId,
				SOTrx.SALES,
				vatCodeId);

		newCand.setC_Tax_ID(taxId.getRepoId());

		setILCandHandler(ctx, newCand);

		InterfaceWrapperHelper.saveRecord(newCand);

		return newCand;
	}

	private ActivityId findActivityIdOrNull(final I_C_Flatrate_Term term, final int productId)
	{
		return Services.get(IProductActivityProvider.class).getActivityForAcct(
				ClientId.ofRepoId(term.getAD_Client_ID()),
				OrgId.ofRepoId(term.getAD_Org_ID()),
				ProductId.ofRepoId(productId));
	}

	private void setILCandHandler(final Properties ctx, final I_C_Invoice_Candidate newCand)
	{
		boolean handlerFound = false;

		final List<I_C_ILCandHandler> handlers = Services.get(IInvoiceCandidateHandlerDAO.class).retrieveForClass(ctx, FlatrateDataEntryHandler.class);
		for (final I_C_ILCandHandler handler : handlers)
		{
			if (handler.isActive())
			{
				Check.errorIf(handlerFound, "The handler list contains more than one active handler for the class {}; list={}", FlatrateDataEntryHandler.class, handlers);
				newCand.setC_ILCandHandler(handler);
				handlerFound = true;
			}
		}

		Check.errorIf(!handlerFound, "There is no (aktive) active C_ILCandHandler record for the class {}", FlatrateDataEntryHandler.class);
	}

	private I_C_Invoice_Candidate createCandForDataEntry(
			final I_C_Flatrate_Term term,
			final I_C_Flatrate_DataEntry dataEntry,
			final BigDecimal qtyToInvoice)
	{
		Check.assume(!dataEntry.isSimulation(), dataEntry + " has IsSimulation='N'");
		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		final Properties ctx = getCtx(fc);
		final String trxName = getTrxName(fc);

		final I_C_Invoice_Candidate newCand = create(ctx, I_C_Invoice_Candidate.class, trxName);
		newCand.setAD_Org_ID(dataEntry.getAD_Org_ID());

		newCand.setM_PricingSystem_ID(fc.getM_PricingSystem_ID());

		final int productIdForIc;
		final BigDecimal priceActual;

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee.equals(fc.getType_Conditions())
				|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable.equals(fc.getType_Conditions()))
		{
			final ProductId productId = ProductId.ofRepoIdOrNull(dataEntry.getM_Product_DataEntry_ID());
			Check.assume(productId != null,
					dataEntry + " has no M_Product_DataEntry, despite " + fc + "has Type_Conditions=" + fc.getType_Conditions());

			productIdForIc = productId.getRepoId();

			priceActual = FlatrateTermPricing.builder()
					.term(term)
					.termRelatedProductId(productId)
					.priceDate(getPeriodStartDate(dataEntry))
					.qty(qtyToInvoice)
					.build().computeOrThrowEx().getPriceStd();
		}
		else
		{
			Check.assume(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(fc.getType_Conditions()),
					fc + " has Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee);

			if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
			{
				productIdForIc = fc.getM_Product_Flatrate_ID();
			}
			else
			{
				Check.assume(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()),
						"dataEntry has type " + X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased);
				productIdForIc = fc.getM_Product_Correction_ID();
			}
			Check.assume(productIdForIc > 0,
					fc + " with Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee + " has no M_Product_Flatrate");

			priceActual = dataEntry.getFlatrateAmt();
		}

		newCand.setM_Product_ID(productIdForIc);
		newCand.setQtyOrdered(qtyToInvoice);

		newCand.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		newCand.setPriceActual(priceActual);
		newCand.setPriceEntered(priceActual); // cg : task 04917 -- same as price actual
		newCand.setPrice_UOM_ID(term.getC_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.

		newCand.setDiscount(BigDecimal.ZERO);

		newCand.setInvoiceRule(fc.getInvoiceRule());
		newCand.setC_Currency_ID(term.getC_Currency_ID());

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(newCand)
				.setFrom(ContractLocationHelper.extractBillLocation(term));

		newCand.setAD_Table_ID(adTableDAO.retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
		newCand.setRecord_ID(dataEntry.getC_Flatrate_DataEntry_ID());

		// 07442 activity and tax
		final ActivityId activityId = findActivityIdOrNull(term, productIdForIc);

		newCand.setC_Activity_ID(ActivityId.toRepoId(activityId));
		newCand.setIsTaxIncluded(term.isTaxIncluded());

		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(term.getC_TaxCategory_ID());

		final BPartnerLocationAndCaptureId shipToLocationId = CoalesceUtil.coalesceSuppliers(
				() -> ContractLocationHelper.extractDropshipLocationId(term),
				() -> ContractLocationHelper.extractBillToLocationId(term));
		final VatCodeId vatCodeId = null;

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				term,
				taxCategoryId,
				productIdForIc,
				dataEntry.getDate_Reported(), // shipDate
				OrgId.ofRepoId(dataEntry.getAD_Org_ID()),
				null,
				shipToLocationId,
				SOTrx.SALES,
				vatCodeId);

		newCand.setC_Tax_ID(TaxId.toRepoId(taxId)); // guard against NPEs in unit tests

		setILCandHandler(ctx, newCand);

		return newCand;
	}

	private LocalDate getPeriodStartDate(final I_C_Flatrate_DataEntry dataEntry)
	{
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(dataEntry.getAD_Org_ID()));
		return TimeUtil.asLocalDate(dataEntry.getC_Period().getStartDate(), timeZone);
	}

	/**
	 * Returns the price for one unit, given a flatrate term, qty (to consider discounts) and data entry.
	 */
	BigDecimal getFlatFeePricePerUnit(
			final I_C_Flatrate_Term flatrateTerm,
			final BigDecimal qty,
			final I_C_Flatrate_DataEntry dataEntry)
	{
		final I_C_Flatrate_Conditions flatrateCond = flatrateTerm.getC_Flatrate_Conditions();

		final ProductId flatrateProductId;
		if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
		{
			flatrateProductId = ProductId.ofRepoId(flatrateCond.getM_Product_Flatrate_ID());
		}
		else
		{
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()), "");
			flatrateProductId = ProductId.ofRepoId(flatrateCond.getM_Product_Correction_ID());
		}

		return FlatrateTermPricing.builder()
				.term(flatrateTerm)
				.termRelatedProductId(flatrateProductId)
				.priceDate(getPeriodStartDate(dataEntry))
				.qty(qty)
				.build().computeOrThrowEx().getPriceStd();
	}

	@Override
	public void validatePricing(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		final LocalDate date = SystemTime.asLocalDate();

		final ProductId flatrateProduct = ProductId.ofRepoId(fc.getM_Product_Flatrate_ID());
		validatePricingForProduct(term, flatrateProduct, date);

		if (fc.isClosingWithActualSum() && X_C_Flatrate_Conditions.TYPE_FLATRATE_Corridor_Percent.equals(fc.getType_Flatrate()))
		{
			Check.assume(fc.getM_Product_Actual_ID() > 0, fc + " has no product to invoice the flatRateCorrectionAmt");
			final ProductId actualProduct = ProductId.ofRepoId(fc.getM_Product_Actual_ID());
			validatePricingForProduct(term, actualProduct, date);
		}
		if (fc.isClosingWithCorrectionSum())
		{
			Check.assume(fc.getM_Product_Correction_ID() > 0, fc + " has no product to invoice the corrected qty_reported");
			final ProductId correctionProduct = ProductId.ofRepoId(fc.getM_Product_Correction_ID());
			validatePricingForProduct(term, correctionProduct, date);
		}
	}

	private void validatePricingForProduct(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final ProductId productId,
			@NonNull final LocalDate date)
	{
		FlatrateTermPricing.builder()
				.term(term)
				.termRelatedProductId(productId)
				.priceDate(date)
				.qty(BigDecimal.ONE)
				.build()
				.computeOrThrowEx();
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveAndCheckInvoicingEntries(
			final I_C_Flatrate_Term flatrateTerm,
			final LocalDateAndOrgId startDate,
			final LocalDateAndOrgId endDate,
			final I_C_UOM uom,
			final List<String> errors)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final Properties ctx = getCtx(flatrateTerm);
		final String trxName = getTrxName(flatrateTerm);

		final boolean auxEntry = flatrateTerm.getC_Flatrate_Conditions().getC_UOM_ID() != uom.getC_UOM_ID();

		List<I_C_Flatrate_DataEntry> result = new ArrayList<>();

		final List<I_C_Flatrate_DataEntry> invoicingEntries = flatrateDB.retrieveInvoicingEntries(
				flatrateTerm,
				startDate,
				endDate,
				UomId.ofRepoId(uom.getC_UOM_ID()));

		final List<I_C_Period> periodsOfTerm = Services.get(ICalendarDAO.class).retrievePeriods(
				ctx, flatrateTerm.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract(), startDate, endDate, trxName);

		for (final I_C_Period periodOfTerm : periodsOfTerm)
		{
			boolean entryForPeriodMissing = true;
			for (final I_C_Flatrate_DataEntry invoicingEntry : invoicingEntries)
			{
				//
				// for each period of the term, we check if there is a completed data that has been fully invoiced
				if (invoicingEntry.getC_Period_ID() == periodOfTerm.getC_Period_ID())
				{
					entryForPeriodMissing = false;
					if (X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(invoicingEntry.getDocStatus()))
					{
						// ok
						final I_C_Invoice_Candidate ic = this.invoiceCandDAO.getById(InvoiceCandidateId.ofRepoId(invoicingEntry.getC_Invoice_Candidate_ID()));
						if (auxEntry
								|| invoicingEntry.isSimulation()
								|| ic != null && ic.getC_Invoice_Candidate_ID() > 0 && ic.isProcessed())
						{
							if (result != null)
							{
								result.add(invoicingEntry);
							}
						}
						else
						{
							final String msg = msgBL.getMsg(ctx, "FlatrateBL_InvoicingEntry_Not_Invoiced",
									new Object[] { periodOfTerm.getName(), uom.getName() });
							errors.add(msg);
							result = null;
						}
					}
					else
					{
						final String msg = msgBL.getMsg(ctx, "PrepareClosing_InvoicingEntry_Not_CO",
								new Object[] { uom.getName(), periodOfTerm.getName() });
						errors.add(msg);
						result = null;
					}
					break;
				}
			}
			if (entryForPeriodMissing)
			{
				result = null;

				final String msg = msgBL.getMsg(ctx, "PrepareClosing_InvoicingEntry_Missing", new Object[] { uom.getName(), periodOfTerm.getName() });
				errors.add(msg);
			}
		}
		return result;
	}

	@Override
	public void createDataEntriesForTerm(
			final I_C_Flatrate_Term flatrateTerm)
	{
		final Properties ctx = getCtx(flatrateTerm);
		final String trxName = getTrxName(flatrateTerm);

		if (X_C_Flatrate_Term.TYPE_CONDITIONS_FlatFee.equals(flatrateTerm.getType_Conditions()))
		{
			createEntriesForFlatFee(ctx, flatrateTerm, trxName);
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(flatrateTerm.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_HoldingFee.equals(flatrateTerm.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Refundable.equals(flatrateTerm.getType_Conditions()))
		{
			createEntriesForHoldingFee(ctx, flatrateTerm, trxName);
		}
	}

	private void createEntriesForHoldingFee(
			final Properties ctx,
			final I_C_Flatrate_Term flatrateTerm,
			final String trxName)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final List<I_M_Product> products = flatrateDB.retrieveHoldingFeeProducts(flatrateTerm.getC_Flatrate_Conditions());

		int counter = 0;

		final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);

		final List<I_C_Period> periods = getPeriodList(ctx, flatrateTerm, trxName, calendarDAO);
		for (final I_C_Period period : periods)
		{
			for (final I_M_Product product : products)
			{
				final I_C_UOM uom = uomDAO.getById(product.getC_UOM_ID());

				final I_C_Flatrate_DataEntry existingEntry = flatrateDB.retrieveDataEntryOrNull(flatrateTerm, period, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, uom);
				if (existingEntry != null)
				{
					continue;
				}

				final I_C_Flatrate_DataEntry newDataEntry = create(ctx, I_C_Flatrate_DataEntry.class, trxName);
				newDataEntry.setAD_Org_ID(flatrateTerm.getAD_Org_ID());

				newDataEntry.setC_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());

				newDataEntry.setC_Period_ID(period.getC_Period_ID());
				newDataEntry.setType(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
				newDataEntry.setM_Product_DataEntry_ID(product.getM_Product_ID());
				newDataEntry.setC_UOM_ID(uom.getC_UOM_ID());

				InterfaceWrapperHelper.saveRecord(newDataEntry);
				counter++;
			}
		}

		final String msg = msgBL.getMsg(ctx, FlatrateBL.MSG_DATA_ENTRY_CREATE_HOLDING_FEE_3P,
				new Object[] {
						counter,
						flatrateTerm.getStartDate(),
						flatrateTerm.getEndDate() });
		Loggables.withLogger(logger, Level.INFO).addLog(msg);
	}

	private List<I_C_Period> getPeriodList(final Properties ctx, final I_C_Flatrate_Term flatrateTerm, final String trxName, final ICalendarDAO calendarDAO)
	{
		final OrgId orgId = OrgId.ofRepoId(flatrateTerm.getAD_Org_ID());
		final LocalDateAndOrgId startDate = LocalDateAndOrgId.ofTimestamp(flatrateTerm.getStartDate(), orgId, orgDAO::getTimeZone);
		final LocalDateAndOrgId endDate = LocalDateAndOrgId.ofTimestamp(flatrateTerm.getEndDate(), orgId, orgDAO::getTimeZone);

		final List<I_C_Period> periods = calendarDAO.retrievePeriods(
				ctx, flatrateTerm.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract(), startDate, endDate, trxName);
		return periods;
	}

	private void createEntriesForFlatFee(
			final Properties ctx,
			final I_C_Flatrate_Term flatrateTerm,
			final String trxName)
	{
		int counter = 0;

		final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final List<I_C_UOM> uoms = flatrateDB.retrieveUOMs(ctx, flatrateTerm, trxName);

		final List<I_C_Period> periods = getPeriodList(ctx, flatrateTerm, trxName, calendarDAO);
		for (final I_C_Period period : periods)
		{
			for (final I_C_UOM uom : uoms)
			{
				final I_C_Flatrate_DataEntry existingEntry = flatrateDB.retrieveDataEntryOrNull(flatrateTerm, period, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, uom);
				if (existingEntry != null)
				{
					continue;
				}

				final I_C_Flatrate_DataEntry newDataEntry = create(ctx, I_C_Flatrate_DataEntry.class, trxName);
				newDataEntry.setAD_Org_ID(flatrateTerm.getAD_Org_ID());

				newDataEntry.setC_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());

				newDataEntry.setC_Period_ID(period.getC_Period_ID());
				newDataEntry.setType(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
				newDataEntry.setC_UOM_ID(uom.getC_UOM_ID());

				InterfaceWrapperHelper.saveRecord(newDataEntry);
				counter++;
			}
		}

		final ADReferenceService adReferenceService = ADReferenceService.get();
		final String uomTypeTrl = adReferenceService.retrieveListNameTrl(ctx, ReferenceId.ofRepoId(X_C_Flatrate_Term.UOMTYPE_AD_Reference_ID), flatrateTerm.getUOMType());

		final String msg = msgBL.getMsg(ctx, FlatrateBL.MSG_DATA_ENTRY_CREATE_FLAT_FEE_4P,
				new Object[] {
						counter,
						flatrateTerm.getStartDate(),
						flatrateTerm.getEndDate(),
						uomTypeTrl });
		Loggables.withLogger(logger, Level.INFO).addLog(msg);
	}

	@Override
	public void updateEntry(final I_C_Flatrate_DataEntry dataEntry)
	{
		final I_C_Flatrate_Term term = dataEntry.getC_Flatrate_Term();
		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();

		final int scale = 2;

		if (term.getC_Flatrate_Conditions_ID() < 0)
		{
			// nothing to do
			return;
		}

		final boolean auxEntry = dataEntry.getC_UOM_ID() != term.getC_UOM_ID();

		final BigDecimal actualQtyOfUnits;
		final boolean correctionWithoutActualQty;

		if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
		{
			actualQtyOfUnits = dataEntry.getQty_Reported();
			correctionWithoutActualQty = false;
		}
		else
		{
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()),
					dataEntry + " has Type=" + X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased);

			actualQtyOfUnits = dataEntry.getQty_Reported().subtract(dataEntry.getQty_Planned());
			correctionWithoutActualQty = !conditions.isCorrectionAmtAtClosing();
		}

		final BigDecimal actualQtyOfProducts = dataEntry.getActualQty();

		final BigDecimal plannedQtyPerUnit = term.getPlannedQtyPerUnit();

		final BigDecimal pricePerUnit;

		final BigDecimal actualQtyPerUnit;
		final BigDecimal diffPercent;

		final BigDecimal flatrateAmt;
		final BigDecimal expectedQtyOfProducts;
		final BigDecimal actualQtyDiffAbs;
		final BigDecimal actualDiffPerUnit;

		if (actualQtyOfProducts.signum() == 0 || actualQtyOfUnits.signum() == 0)
		{
			actualQtyPerUnit = BigDecimal.ZERO;
		}
		else
		{
			actualQtyPerUnit = actualQtyOfProducts.divide(actualQtyOfUnits, scale, RoundingMode.HALF_UP);
		}

		if (actualQtyOfUnits.signum() == 0)
		{
			pricePerUnit = auxEntry ? null : getFlatFeePricePerUnit(term, BigDecimal.ONE, dataEntry);

			flatrateAmt = auxEntry ? null : BigDecimal.ZERO;
		}
		else
		{
			pricePerUnit = auxEntry ? null : getFlatFeePricePerUnit(term, actualQtyOfUnits, dataEntry);

			flatrateAmt = auxEntry ? null : actualQtyOfUnits.multiply(pricePerUnit).setScale(scale, RoundingMode.HALF_UP);
		}

		actualDiffPerUnit = auxEntry || correctionWithoutActualQty ? null : actualQtyPerUnit.subtract(plannedQtyPerUnit).setScale(scale, RoundingMode.HALF_UP);

		if (auxEntry || correctionWithoutActualQty)
		{
			diffPercent = null;
		}
		else if (actualQtyOfUnits.signum() == 0)
		{
			diffPercent = BigDecimal.ZERO;
		}
		else
		{
			diffPercent = actualDiffPerUnit
					.divide(plannedQtyPerUnit, scale * 2, RoundingMode.HALF_UP)
					.multiply(Env.ONEHUNDRED)
					.setScale(scale, RoundingMode.HALF_UP);
		}

		expectedQtyOfProducts = auxEntry ? null : actualQtyOfUnits.multiply(plannedQtyPerUnit);

		actualQtyDiffAbs = auxEntry || correctionWithoutActualQty ? null : actualQtyOfProducts.subtract(expectedQtyOfProducts);

		dataEntry.setActualQtyPerUnit(actualQtyPerUnit);
		dataEntry.setActualQtyDiffPercent(diffPercent);
		dataEntry.setFlatrateAmt(flatrateAmt);
		dataEntry.setFlatrateAmtPerUOM(pricePerUnit);

		dataEntry.setActualQtyDiffAbs(actualQtyDiffAbs);
		dataEntry.setActualQtyDiffPerUOM(actualDiffPerUnit);

		final int currencyId;
		final BigDecimal amtCorrection;
		final BigDecimal effectiveDiffPercent;

		if (auxEntry)
		{
			currencyId = 0;
			amtCorrection = null;
			effectiveDiffPercent = null;
		}
		else
		{
			currencyId = term.getC_Currency_ID();

			if (X_C_Flatrate_Conditions.TYPE_FLATRATE_Corridor_Percent.equals(conditions.getType_Flatrate())
					&& !correctionWithoutActualQty)
			{

				final BigDecimal percentSubtrahent;
				final boolean marginExceeded;

				if (diffPercent.signum() > 0 && diffPercent.abs().compareTo(conditions.getMargin_Max()) > 0)
				{
					percentSubtrahent = conditions.getMargin_Max();
					marginExceeded = true;
				}
				else if (diffPercent.signum() < 0 && diffPercent.abs().compareTo(conditions.getMargin_Min()) > 0)
				{
					percentSubtrahent = conditions.getMargin_Min().negate();
					marginExceeded = true;
				}
				else
				{
					percentSubtrahent = BigDecimal.ZERO;
					marginExceeded = false;
				}

				if (marginExceeded)
				{
					if (X_C_Flatrate_Conditions.TYPE_CLEARING_Complete.equals(conditions.getType_Clearing()))
					{
						effectiveDiffPercent = diffPercent;
					}
					else
					{
						Check.assume(X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding.equals(conditions.getType_Clearing()),
								conditions + " has either Type_Clearing '" + X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding + "' or '"
										+ X_C_Flatrate_Conditions.TYPE_CLEARING_Complete
										+ "'");
						effectiveDiffPercent = diffPercent.subtract(percentSubtrahent);
					}

					// if
					// (X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Pauschalenpreis.equals(conditions.getClearingAmtBaseOn()))
					// {
					Check.assume(X_C_Flatrate_Conditions.CLEARINGAMTBASEON_FlatrateAmount.equals(conditions.getClearingAmtBaseOn()),
							conditions + " has ClearingAmtBaseOn='" + X_C_Flatrate_Conditions.CLEARINGAMTBASEON_FlatrateAmount + "'");

					amtCorrection = flatrateAmt
							.multiply(effectiveDiffPercent.divide(Env.ONEHUNDRED, scale * 2, RoundingMode.HALF_UP))
							.setScale(scale, RoundingMode.HALF_UP);
					// }
					// else if
					// (X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Produktpreis.equals(conditions.getClearingAmtBaseOn()))
					// {
					// throw new NotImplementedException();
					// }
					// else
					// {
					// throw new IllegalStateException(
					// "Expected '" + conditions + "' to have ClearingAmtBaseOn either'"
					// + X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Pauschalenpreis
					// + "' or '" + X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Produktpreis + "'");
					// }
				}
				else
				{
					amtCorrection = BigDecimal.ZERO;
					effectiveDiffPercent = BigDecimal.ZERO;
				}
			}
			else
			{
				amtCorrection = null;
				effectiveDiffPercent = null;
			}
		}

		dataEntry.setActualQtyDiffPercentEff(effectiveDiffPercent);
		dataEntry.setC_Currency_ID(currencyId);
		dataEntry.setFlatrateAmtCorr(amtCorrection);
	}

	@Override
	public void extendContractAndNotifyUser(final @NonNull ContractExtendingRequest request)
	{
		if (!isExtendableContract(request.getContract()))
		{
			throw new AdempiereException(MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED, request.getContract());
		}

		final Map<Integer, String> seenFlatrateCondition = new LinkedHashMap<>();
		final I_C_Flatrate_Conditions currentConditions = request.getContract().getC_Flatrate_Conditions();
		seenFlatrateCondition.put(currentConditions.getC_Flatrate_Conditions_ID(), currentConditions.getName());

		ContractExtendingRequest currentRequest = request;
		I_C_Flatrate_Transition nextTransition = null;
		final List<I_C_Flatrate_Term> contracts = new ArrayList<>();

		contracts.add(currentRequest.getContract());
		do
		{
			extendContractAndNotifyUserIfRequired(currentRequest);

			final I_C_Flatrate_Term currentTerm = currentRequest.getContract();
			currentTerm.setAD_PInstance_EndOfTerm_ID(PInstanceId.toRepoId(currentRequest.getAD_PInstance_ID()));
			save(currentTerm);
			if (currentTerm.getC_FlatrateTerm_Next_ID() <= 0)
			{
				// https://github.com/metasfresh/metasfresh/issues/4022 avoid NPE if currentTerm was actually *not* extended by extendContractIfRequired
				break;
			}

			final I_C_Flatrate_Term nextTerm = currentTerm.getC_FlatrateTerm_Next();
			final I_C_Flatrate_Conditions nextConditions = nextTerm.getC_Flatrate_Conditions();
			Check.assumeNotNull(nextConditions, "C_Flatrate_Conditions shall not be null!");

			nextTransition = nextConditions.getC_Flatrate_Transition();
			Check.assumeNotNull(nextTransition, "C_Flatrate_Transition shall not be null!");

			// infinite loop detection
			if (X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll.equals(nextTransition.getExtensionType()) && seenFlatrateCondition.containsKey(nextConditions.getC_Flatrate_Conditions_ID()))
			{
				throw new AdempiereException(MSG_INFINITE_LOOP, nextConditions.getName(), seenFlatrateCondition.values());
			}
			seenFlatrateCondition.put(nextConditions.getC_Flatrate_Conditions_ID(), nextConditions.getName());

			currentRequest = currentRequest.toBuilder()
					.AD_PInstance_ID(request.getAD_PInstance_ID())
					.contract(nextTerm).build();

			contracts.add(nextTerm);
		}
		while (X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll.equals(nextTransition.getExtensionType()) && nextTransition.getC_Flatrate_Conditions_Next_ID() > 0);

		updateMasterEndDateIfNeeded(contracts, request.getContract());
	}

	/**
	 * Update <code>masterenddate</code> only for contract of which we know the entire period
	 */
	private void updateMasterEndDateIfNeeded(final List<I_C_Flatrate_Term> contracts, final I_C_Flatrate_Term initialContract)
	{
		final I_C_Flatrate_Conditions initialConditions = initialContract.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition initialTransition = initialConditions.getC_Flatrate_Transition();
		if (X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll.equals(initialTransition.getExtensionType()))
		{
			final Timestamp endDate = contracts.stream()
					.sorted(Comparator.comparing(I_C_Flatrate_Term::getEndDate).reversed())
					.findFirst()
					.map(I_C_Flatrate_Term::getEndDate)
					.orElse(null);

			contracts.forEach(contract -> {
				contract.setMasterEndDate(endDate);
				save(contract);
			});
		}
	}

	private void extendContractAndNotifyUserIfRequired(final @NonNull ContractExtendingRequest request)
	{
		final I_C_Flatrate_Term currentTerm = request.getContract();
		final boolean forceExtend = request.isForceExtend();
		final Boolean forceComplete = request.getForceComplete();

		final I_C_Flatrate_Conditions conditions = currentTerm.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition currentTransition = conditions.getC_Flatrate_Transition();

		I_C_Flatrate_Term termToReferenceInNote = null;
		AdMessageKey msgValue = null;

		if (currentTerm.isAutoRenew() || forceExtend)
		{
			final I_C_Flatrate_Term nextTerm = createNewTerm(request);

			// the conditions were set via de.metas.flatrate.modelvalidator.C_Flatrate_Term.copyFromConditions(term)
			Check.errorUnless(currentTerm.getType_Conditions().equals(nextTerm.getType_Conditions()),
					"currentTerm has Type_Conditions={} while nextTerm has Type_Conditions={}; currentTerm={}",
					currentTerm.getType_Conditions(), nextTerm.getType_Conditions(), currentTerm);

			currentTerm.setC_FlatrateTerm_Next_ID(nextTerm.getC_Flatrate_Term_ID());
			save(currentTerm);

			// gh #549: notify that handler so it might do additional things. In the case of this task, it shall create C_Flatrate_DataEntry records
			final IFlatrateTermEventService flatrateHandlersService = Services.get(IFlatrateTermEventService.class);
			flatrateHandlersService
					.getHandler(nextTerm.getType_Conditions())
					.afterSaveOfNextTermForPredecessor(nextTerm, currentTerm);

			// gh #549: if forceComplete was set, then use it; otherwise fall back to the setting of currentTransition
			final boolean completeNextTerm = forceComplete != null ? forceComplete : currentTransition.isAutoCompleteNewTerm();

			if (completeNextTerm)
			{
				completeIfValid(nextTerm);

				if (currentTransition.isNotifyUserInCharge())
				{
					msgValue = FlatrateBL.MSG_TERM_NEW_COMPLETED_0P;
					termToReferenceInNote = nextTerm;
				}
			}
			else
			{
				if (currentTransition.isNotifyUserInCharge())
				{
					msgValue = FlatrateBL.MSG_TERM_NEW_UNCOMPLETED_0P;
					termToReferenceInNote = nextTerm;
				}
			}

			final IWFExecutionFactory wfExecutionFactory = Services.get(IWFExecutionFactory.class);
			wfExecutionFactory.notifyActivityPerformed(currentTerm, nextTerm); // 03745
		}
		else
		{
			final IFlatrateTermEventService flatrateHandlersService = Services.get(IFlatrateTermEventService.class);
			flatrateHandlersService
					.getHandler(currentTerm.getType_Conditions())
					.afterFlatrateTermEnded(currentTerm);

			if (currentTransition.isNotifyUserInCharge())
			{
				msgValue = FlatrateBL.MSG_TERM_NO_NEW_0P;
				termToReferenceInNote = currentTerm;
			}
		}

		if (msgValue != null)
		{
			Check.assume(currentTerm.getAD_User_InCharge_ID() > 0, conditions + " has AD_User_InCharge_ID > 0");
			Check.assume(termToReferenceInNote != null, "");

			notifyUser(currentTerm, msgValue);
		}
	}

	private void notifyUser(@NonNull final I_C_Flatrate_Term currentTerm, @NonNull final AdMessageKey msgValue)
	{
		final FlatrateUserNotificationsProducer flatrateGeneratedEventBus = FlatrateUserNotificationsProducer.newInstance();

		final UserId recipientUserId = currentTerm.getAD_User_InCharge_ID() > 0 ? UserId.ofRepoId(currentTerm.getAD_User_InCharge_ID()) : null;
		if (recipientUserId == null)
		{
			return;
		}

		flatrateGeneratedEventBus.notifyUser(currentTerm, recipientUserId, msgValue);
	}

	private I_C_Flatrate_Term createNewTerm(final @NonNull ContractExtendingRequest context)
	{
		final I_C_Flatrate_Term currentTerm = context.getContract();
		final Timestamp nextTermStartDate = context.getNextTermStartDate();

		Check.errorIf(currentTerm.getC_FlatrateTerm_Next_ID() > 0,
				"{} has C_FlatrateTerm_Next_ID = {} (should be <= 0)", currentTerm, currentTerm.getC_FlatrateTerm_Next_ID());

		final I_C_Flatrate_Conditions conditions = currentTerm.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition currentTransition = conditions.getC_Flatrate_Transition();

		final I_C_Flatrate_Conditions nextConditions;
		if (currentTransition.getC_Flatrate_Conditions_Next_ID() > 0)
		{
			nextConditions = currentTransition.getC_Flatrate_Conditions_Next();
		}
		else
		{
			nextConditions = currentTerm.getC_Flatrate_Conditions();
		}

		final I_C_Flatrate_Term nextTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, currentTerm);
		nextTerm.setAD_Org_ID(currentTerm.getAD_Org_ID());

		nextTerm.setC_Flatrate_Data_ID(currentTerm.getC_Flatrate_Data_ID());
		nextTerm.setC_Flatrate_Conditions_ID(nextConditions.getC_Flatrate_Conditions_ID());
		nextTerm.setPlannedQtyPerUnit(currentTerm.getPlannedQtyPerUnit());
		nextTerm.setIsSimulation(currentTerm.isSimulation());

		final BPartnerContactId billContactId = BPartnerContactId.ofRepoIdOrNull(currentTerm.getBill_BPartner_ID(), currentTerm.getBill_User_ID());
		ContractDocumentLocationAdapterFactory
				.billLocationAdapter(nextTerm)
				.setFrom(ContractLocationHelper.extractBillToLocationId(currentTerm),
						billContactId);

		final BPartnerContactId dropshipContactId = BPartnerContactId.ofRepoIdOrNull(currentTerm.getDropShip_BPartner_ID(), currentTerm.getDropShip_User_ID());

		ContractDocumentLocationAdapterFactory
				.dropShipLocationAdapter(nextTerm)
				.setFrom(ContractLocationHelper.extractDropshipLocationId(currentTerm), dropshipContactId);

		nextTerm.setAD_User_InCharge_ID(currentTerm.getAD_User_InCharge_ID());
		final I_C_Flatrate_Transition nextTransition = nextConditions.getC_Flatrate_Transition();

		final Timestamp firstDayOfNewTerm = computeStartDate(currentTerm, nextTermStartDate);
		nextTerm.setStartDate(firstDayOfNewTerm);
		nextTerm.setMasterStartDate(currentTerm.getMasterStartDate());

		final OrderAndLineId orderAndLineTermId = OrderAndLineId.ofRepoIdsOrNull(currentTerm.getC_Order_Term_ID(), currentTerm.getC_OrderLine_Term_ID());

		if (orderAndLineTermId != null)
		{
			nextTerm.setC_OrderLine_Term_ID(orderAndLineTermId.getOrderLineRepoId());
			nextTerm.setC_Order_Term_ID(orderAndLineTermId.getOrderRepoId());
		}

		updateEndDate(nextTransition, nextTerm);
		updateNoticeDate(nextTransition, nextTerm);

		nextTerm.setM_PricingSystem_ID(currentTerm.getM_PricingSystem_ID());

		nextTerm.setM_Product_ID(currentTerm.getM_Product_ID());
		attributeSetInstanceBL.cloneASI(currentTerm, nextTerm);

		nextTerm.setDeliveryRule(currentTerm.getDeliveryRule());
		nextTerm.setDeliveryViaRule(currentTerm.getDeliveryViaRule());

		nextTerm.setC_UOM_ID(currentTerm.getC_UOM_ID());

		nextTerm.setC_Currency_ID(currentTerm.getC_Currency_ID());

		nextTerm.setC_Flatrate_Term_Master_ID(currentTerm.getC_Flatrate_Term_Master_ID());

		final IFlatrateTermEventService flatrateHandlersService = Services.get(IFlatrateTermEventService.class);
		flatrateHandlersService
				.getHandler(nextConditions.getType_Conditions()) // nextterm is not saved yet, so type will be null in this moment
				.beforeSaveOfNextTermForPredecessor(nextTerm, currentTerm);

		nextTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Prepare);
		nextTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);
		save(nextTerm);

		return nextTerm;
	}

	private Timestamp computeStartDate(@NonNull final I_C_Flatrate_Term contract, final Timestamp nextTermStartDate)
	{

		// gh #549: if an explicit start date was given, then use it (if it is OK).
		final Timestamp dayAfterEndDate = TimeUtil.addDays(contract.getEndDate(), 1);
		if (nextTermStartDate != null)
		{
			if (nextTermStartDate.before(contract.getStartDate()) || nextTermStartDate.after(dayAfterEndDate))
			{
				Loggables.addLog(
						"Ignore nextTermStartDate={} because if is not between currentTerm's StartDate={} and DayAfterEndDate={}. Instead, use dayAfterEndDate",
						nextTermStartDate, contract.getStartDate(), dayAfterEndDate);
				return dayAfterEndDate;
			}

			return nextTermStartDate;
		}

		return dayAfterEndDate;
	}

	@Override
	public void updateNoticeDateAndEndDate(final I_C_Flatrate_Term term)
	{
		Check.assume(!X_C_Flatrate_Term.DOCSTATUS_Completed.equals(term.getDocStatus()), term + " is not yet completed");

		final I_C_Flatrate_Transition transition = getTransitionForTerm(term);

		final boolean isAutorenew = X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne.equals(transition.getExtensionType())
				|| X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll.equals(transition.getExtensionType());
		term.setIsAutoRenew(isAutorenew);
		updateEndDate(transition, term);
		updateNoticeDate(transition, term);
	}

	private I_C_Flatrate_Transition getTransitionForTerm(@NonNull final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Transition transition;
		if (term.getC_Flatrate_Transition_ID() > 0)
		{
			transition = term.getC_Flatrate_Transition();
		}
		else
		{
			// Note: C_Flatrate_Term.C_Flatrate_Transition_ID is a SQL column pointing to
			// C_Flatrate_Conditions.C_Flatrate_Transition_ID.
			// Before the first save, that value has not been loaded yet and is null.
			transition = term.getC_Flatrate_Conditions().getC_Flatrate_Transition();
		}

		Check.errorUnless(transition != null, "{} shall have transition set", term);

		return transition;

	}

	private void updateEndDate(final I_C_Flatrate_Transition transition, final I_C_Flatrate_Term term)
	{
		final Timestamp endDate = computeEndDate(transition, term);
		term.setEndDate(endDate);
	}

	@Nullable
	private Timestamp computeEndDate(final I_C_Flatrate_Transition transition, final I_C_Flatrate_Term term)
	{
		final Timestamp firstDayOfTerm = term.getStartDate();

		Timestamp lastDayOfTerm = null;

		// metas: rc: start 03742
		// Condition added to handle "EnsWithCalendarYear" flag for transitions
		if (transition.isEndsWithCalendarYear())
		{
			final int termDuration; // term duration in years

			if (X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE.equals(transition.getTermDurationUnit()))
			{
				termDuration = transition.getTermDuration();
			}
			else
			{
				// make sure that durationUnit==month and duration==(n times 12); note that we have a model interceptor which enforces this.
				Check.errorUnless(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE.equals(transition.getTermDurationUnit()),
						"The term duration unit is not suitable for a term that ends with calendar year");
				Check.errorUnless(transition.getTermDuration() % 12 == 0, "Term duration not suitable for a term that ends with calendar year");

				termDuration = transition.getTermDuration() / 12;
			}

			final I_C_Calendar calendar = transition.getC_Calendar_Contract();
			final OrgId orgId = OrgId.ofRepoId(term.getAD_Org_ID());

			LocalDateAndOrgId currentFirstDay = LocalDateAndOrgId.ofTimestamp(firstDayOfTerm, orgId, orgDAO::getTimeZone); // first day of term or first day of new year, not including hours
			for (int i = 0; i < termDuration; i++)
			{
				final List<I_C_Period> periodsContainingDay = Services.get(ICalendarDAO.class).retrievePeriods(
						getCtx(transition), calendar, currentFirstDay, currentFirstDay, getTrxName(transition));

				Check.errorIf(periodsContainingDay.isEmpty(), "Date {} does not exist in calendar={}", currentFirstDay, calendar);
				Check.errorIf(periodsContainingDay.size() > 1, "Date {} is contained in more than one period of calendar={}; periodsContainingDay={}", currentFirstDay, calendar, periodsContainingDay);

				final I_C_Period period = CollectionUtils.singleElement(periodsContainingDay);
				final I_C_Year year = period.getC_Year();

				lastDayOfTerm = Services.get(ICalendarBL.class).getLastDayOfYear(year);

				currentFirstDay = LocalDateAndOrgId.ofTimestamp(TimeUtil.addDays(lastDayOfTerm, 1), orgId, orgDAO::getTimeZone);
			}
		}
		// Case: If TermDuration is ZERO, we shall not calculate the EndDate automatically,
		// but rely on what was set
		else if (transition.getTermDuration() == 0)
		{
			return term.getEndDate();
		}
		else
		{
			if (X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE.equals(transition.getTermDurationUnit()))
			{
				lastDayOfTerm = TimeUtil.addDays(TimeUtil.addYears(firstDayOfTerm, transition.getTermDuration()), -1);
			}
			else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE.equals(transition.getTermDurationUnit()))
			{
				lastDayOfTerm = TimeUtil.addDays(TimeUtil.addMonths(firstDayOfTerm, transition.getTermDuration()), -1);
			}
			else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_WocheN.equals(transition.getTermDurationUnit()))
			{
				lastDayOfTerm = TimeUtil.addDays(TimeUtil.addWeeks(firstDayOfTerm, transition.getTermDuration()), -1);
			}
			else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_TagE.equals(transition.getTermDurationUnit()))
			{
				lastDayOfTerm = TimeUtil.addDays(TimeUtil.addDays(firstDayOfTerm, transition.getTermDuration()), -1);
			}
			else
			{
				Check.assume(false, "TermDurationUnit " + transition.getTermDuration() + " doesn't exist");
				lastDayOfTerm = null; // code won't be reached
			}
		}

		return lastDayOfTerm;
	}

	/**
	 * Update NoticeDate of the given term. Uses the given transition and the term's EndDate.
	 */
	private void updateNoticeDate(final I_C_Flatrate_Transition transition, final I_C_Flatrate_Term term)
	{
		final Timestamp lastDayOfNewTerm = term.getEndDate();

		final Timestamp noticeDate;
		if (lastDayOfNewTerm == null)
		{
			// If Last day of new term is not set, don't calculate the noticeDate
			noticeDate = null;
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE.equals(transition.getTermOfNoticeUnit()))
		{
			noticeDate = TimeUtil.addMonths(lastDayOfNewTerm, transition.getTermOfNotice() * -1);
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_WocheN.equals(transition.getTermOfNoticeUnit()))
		{
			noticeDate = TimeUtil.addWeeks(lastDayOfNewTerm, transition.getTermOfNotice() * -1);
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_TagE.equals(transition.getTermOfNoticeUnit()))
		{
			noticeDate = TimeUtil.addDays(lastDayOfNewTerm, transition.getTermOfNotice() * -1);
		}
		else
		{
			Check.assume(false, "TermOfNoticeDuration " + transition.getTermOfNoticeUnit() + " doesn't exist");
			noticeDate = null; // code won't be reached
		}

		term.setNoticeDate(noticeDate);
	}

	/**
	 * @param term the contract term that the method retrieves the doc type for. Note that we can assume such a doc type exists, because it should have been made sure by {@link C_Flatrate_Term}.
	 * @return the doc type for the given term
	 */
	@Override
	public I_C_DocType getDocTypeFor(final I_C_Flatrate_Term term)
	{
		final String subType;
		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(term.getType_Conditions()))
		{
			subType = de.metas.contracts.flatrate.interfaces.I_C_DocType.DocSubType_Abonnement;
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_HoldingFee.equals(term.getType_Conditions()))
		{
			subType = de.metas.contracts.flatrate.interfaces.I_C_DocType.DocSubType_Depotgebuehr;
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_FlatFee.equals(term.getType_Conditions()))
		{
			subType = de.metas.contracts.flatrate.interfaces.I_C_DocType.DocSubType_Pauschalengebuehr;
		}
		else
		{
			Check.assume(false, term + " has unexpected Type_Conditions=" + term.getType_Conditions());
			subType = null;
		}

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.adClientId(term.getAD_Client_ID())
				.adOrgId(term.getAD_Org_ID())
				.docBaseType(DocBaseType.CustomerContract)
				.docSubType(subType)
				.build();

		return Services.get(IDocTypeDAO.class)
				.retrieveDocType(docTypeQuery)
				.orElseThrow(() -> new DocTypeNotFoundException(docTypeQuery));
	}

	@Override
	public WarehouseId getWarehouseId(final I_C_Flatrate_Term term)
	{
		final Properties ctx = getCtx(term);

		final int warehouseId;
		if (term.getC_OrderLine_Term_ID() > 0)
		{
			warehouseId = term.getC_OrderLine_Term().getM_Warehouse_ID();
		}
		else
		{
			final List<I_M_Warehouse> warehousesForOrg = Services.get(IWarehouseDAO.class).getByOrgId(OrgId.ofRepoId(term.getAD_Org_ID()));
			if (warehousesForOrg.isEmpty())
			{
				throw new AdempiereException(
						MSG_ORG_WAREHOUSE_MISSING,
						msgBL.translate(ctx, I_AD_Org.COLUMNNAME_AD_Org_ID), loadOutOfTrx(term.getAD_Org_ID(), I_AD_Org.class));
			}
			warehouseId = warehousesForOrg.get(0).getM_Warehouse_ID();
		}

		return WarehouseId.ofRepoIdOrNull(warehouseId);
	}

	@Override
	public void updateFromConditions(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();
		if (conditions == null)
		{
			return;
		}

		term.setType_Conditions(conditions.getType_Conditions());
		// NOTE: do not save, it will be used in model validators and callouts too
	}

	@Override
	public void updateFlatrateDataEntryQty(
			final org.compiere.model.I_M_Product product,
			final BigDecimal qty,
			final I_M_InOutLine document,
			final boolean substract)
	{
		final int partnerId = document.getM_InOut().getC_BPartner_ID();
		final Timestamp movementDate = document.getM_InOut().getMovementDate();

		final I_C_Flatrate_DataEntry entry = flatrateDAO.retrieveRefundableDataEntry(partnerId, movementDate, product);

		final BigDecimal documentAmount = substract ? qty.negate() : qty;

		if (null != entry)
		{
			entry.setActualQty(entry.getActualQty().add(documentAmount));
			InterfaceWrapperHelper.saveRecord(entry);
		}
	}

	@Override
	public I_C_Flatrate_Term createTerm(@NonNull final CreateFlatrateTermRequest request)
	{
		final Properties ctx = request.getContext().getCtx();
		final String trxName = request.getContext().getTrxName();

		final I_C_BPartner bPartner = request.getBPartner();
		final I_C_Flatrate_Conditions conditions = request.getConditions();
		final Timestamp startDate = request.getStartDate();
		final Timestamp endDate = request.getEndDate();
		final I_AD_User userInCharge = request.getUserInCharge();
		final ProductAndCategoryId productAndCategoryId = request.getProductAndCategoryId();

		boolean dontCreateTerm = false;
		final StringBuilder notCreatedReason = new StringBuilder();

		if (!bPartner.isCustomer() && !bPartner.isVendor())
		{
			notCreatedReason.append(" is neither customer nor vendor;");
			dontCreateTerm = true;
		}

		final I_C_BPartner_Location billPartnerLocation = bPartnerDAO.retrieveBillToLocation(ctx, bPartner.getC_BPartner_ID(),
				true,            // alsoTryBPartnerRelation
				trxName);

		if (billPartnerLocation == null)
		{
			notCreatedReason.append(" has no billTo location;");
			dontCreateTerm = true;
		}

		if (productAndCategoryId == null)
		{
			if (!flatrateDAO.retrieveTerms(bPartner, conditions).isEmpty())
			{
				notCreatedReason.append(" already has a term;");
				dontCreateTerm = true;
			}
		}
		else
		{
			if (!flatrateDAO.retrieveTerms(ctx,
					OrgId.ofRepoId(bPartner.getAD_Org_ID()),
					bPartner.getC_BPartner_ID(),
					null,
					productAndCategoryId.getProductCategoryId().getRepoId(),
					productAndCategoryId.getProductId().getRepoId(),
					-1,
					trxName).isEmpty())
			{
				notCreatedReason.append(" already has a term;");
				dontCreateTerm = true;
			}
		}

		if (dontCreateTerm)
		{
			throw new AdempiereException("@NotCreated@ @C_Flatrate_Term_ID@ (@C_BPartner_ID@: " + bPartner.getValue() + "): " + notCreatedReason).markAsUserValidationError();
		}

		final I_C_Flatrate_Term newTerm = newInstance(I_C_Flatrate_Term.class, bPartner);
		newTerm.setC_Flatrate_Conditions(conditions);
		newTerm.setC_UOM_ID(conditions.getC_UOM_ID());
		newTerm.setM_PricingSystem_ID(conditions.getM_PricingSystem_ID());
		newTerm.setAD_Org_ID(bPartner.getAD_Org_ID());

		newTerm.setStartDate(startDate);
		newTerm.setEndDate(endDate != null ? endDate : startDate);

		final BPartnerLocationAndCaptureId billToLocationId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(billPartnerLocation.getC_BPartner_ID(),// note that in case of bPartner relations, this might be a different partner than 'bPartner'.
				billPartnerLocation.getC_BPartner_Location_ID(),
				billPartnerLocation.getC_Location_ID());
		ContractDocumentLocationAdapterFactory.billLocationAdapter(newTerm)
				.setFrom(billToLocationId);

		final IBPartnerDAO.BPartnerLocationQuery bPartnerLocationQuery = IBPartnerDAO.BPartnerLocationQuery.builder()
				.bpartnerId(BPartnerId.ofRepoId(bPartner.getC_BPartner_ID()))
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.applyTypeStrictly(true)
				.build();

		final I_C_BPartner_Location shipToLocationRecord = bPartnerDAO.retrieveBPartnerLocation(bPartnerLocationQuery);

		if (shipToLocationRecord != null)
		{
			final BPartnerLocationAndCaptureId shipToLocationId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(shipToLocationRecord.getC_BPartner_ID(),
					shipToLocationRecord.getC_BPartner_Location_ID(),
					shipToLocationRecord.getC_Location_ID());

			ContractDocumentLocationAdapterFactory.dropShipLocationAdapter(newTerm)
					.setFrom(shipToLocationId);
		}
		else
		{
			newTerm.setDropShip_BPartner_ID(bPartner.getC_BPartner_ID()); // keep the previous behavior
		}
		if (userInCharge == null)
		{
			newTerm.setAD_User_InCharge_ID(bPartner.getSalesRep_ID());
		}
		else
		{
			newTerm.setAD_User_InCharge_ID(userInCharge.getAD_User_ID());
		}

		final I_C_Flatrate_Data data = flatrateDAO.retrieveOrCreateFlatrateData(bPartner);
		newTerm.setC_Flatrate_Data(data);

		newTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Prepare);
		newTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);
		newTerm.setIsSimulation(request.isSimulation());

		if (productAndCategoryId != null)
		{
			newTerm.setM_Product_ID(productAndCategoryId.getProductId().getRepoId());
		}

		save(newTerm);

		if (request.isCompleteIt())
		{
			complete(newTerm);
		}

		final CacheInvalidateMultiRequest cacheInvalidateMultiRequest = CacheInvalidateMultiRequest.allRecordsForTable(I_C_Flatrate_Term.Table_Name);

		CacheMgt.get().reset(cacheInvalidateMultiRequest);

		return newTerm;
	}

	@Override
	public void complete(final I_C_Flatrate_Term term)
	{
		// NOTE: the whole reason why we have this method is for readability ease of refactoring.
		Services.get(IDocumentBL.class).processEx(term, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@Override
	public void completeIfValid(final I_C_Flatrate_Term term)
	{
		if (!isAllowedToOverlapWithOtherTerms(term))
		{
			final boolean hasOverlappingTerms = hasOverlappingTerms(term);
			if (hasOverlappingTerms)
			{
				final I_C_BPartner billBPartnerRecord = bPartnerDAO.getById(term.getBill_BPartner_ID());

				Loggables.addLog(Services.get(IMsgBL.class).getMsg(
						Env.getCtx(),
						MSG_HasOverlapping_Term,
						new Object[] { term.getC_Flatrate_Term_ID(), billBPartnerRecord.getValue() }));
				return;
			}
		}
		complete(term);
	}

	@Override
	public void voidIt(final I_C_Flatrate_Term term)
	{
		// NOTE: the whole reason why we have this method is for readability ease of refactoring.
		Services.get(IDocumentBL.class).processEx(term, IDocument.ACTION_Void, IDocument.STATUS_Voided);

	}

	@Override
	public boolean isAllowedToOverlapWithOtherTerms(
			@NonNull final I_C_Flatrate_Term term)
	{
		final String typeConditions = term.getType_Conditions();

		// These contract types do not match "other" ICs such as ICs that trigger a commission, or IC that belong to a vendor's empty package (pallette/TU).
		// Therefore they can overlap without causing us any problems.
		final boolean allowedToOverlapWithOtherTerms = X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(typeConditions)
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Procurement.equals(typeConditions)
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_CallOrder.equals(typeConditions);
		return allowedToOverlapWithOtherTerms;
	}

	@Override
	public boolean hasOverlappingTerms(final I_C_Flatrate_Term newTerm)
	{
		// overlapping terms must be for the same flatrate data
		final I_C_Flatrate_Data flatrateData = newTerm.getC_Flatrate_Data();

		final List<I_C_Flatrate_Term> terms = flatrateDAO.retrieveTerms(flatrateData);

		// Note: This list also contains the given term. It will have to be excluded from the list when searching for overlapping terms.
		terms.remove(newTerm);

		// there are no other terms for the given flatrate data
		if (terms.isEmpty())
		{
			return false;
		}

		for (final I_C_Flatrate_Term term : terms)
		{
			// Only consider completed terms
			if (!X_C_Flatrate_Term.DOCSTATUS_Completed.equals(term.getDocStatus()))
			{
				continue;
			}

			if (X_C_Flatrate_Term.CONTRACTSTATUS_Voided.equals(term.getContractStatus()))
			{
				continue;
			}

			// Only consider terms with the same org.
			// C_Flatrate_Term has access-level=Org, so there is no term with Org=*
			// Also note that when finding a term for an invoice-candidate, that IC's org is used as a matching criterion
			if (term.getAD_Org_ID() != newTerm.getAD_Org_ID())
			{
				continue;
			}

			if (!Objects.equals(term.getType_Conditions(), newTerm.getType_Conditions()))
			{
				continue;
			}

			if (TypeConditions.ofCode(newTerm.getType_Conditions()).isModularOrInterim()
					&& term.getC_Flatrate_Conditions_ID() != newTerm.getC_Flatrate_Conditions_ID())
			{
				continue;
			}

			if (periodsOverlap(newTerm, term) && productsOverlap(newTerm, term))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if 2 flatrate terms overlap in product or product category
	 */
	private boolean productsOverlap(final I_C_Flatrate_Term newTerm, final I_C_Flatrate_Term term)
	{
		// services
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

		final ProductId newProductId = ProductId.ofRepoIdOrNull(newTerm.getM_Product_ID());
		final ProductId productId = ProductId.ofRepoIdOrNull(term.getM_Product_ID());
		if (newProductId != null && productId != null)
		{
			return ProductId.equals(newProductId, productId);
		}
		else if (newProductId != null)
		{
			final ProductCategoryId newProductCategoryId = productDAO.retrieveProductCategoryByProductId(newProductId);
			final List<I_C_Flatrate_Matching> flatrateMatchings = flatrateDAO.retrieveFlatrateMatchings(term.getC_Flatrate_Conditions());

			for (final I_C_Flatrate_Matching matching : flatrateMatchings)
			{
				if (newProductId.getRepoId() == matching.getM_Product_ID())
				{
					// there is one matching for the same product as the one from the new term
					return true;
				}

				if (matching.getM_Product_ID() <= 0 && (newProductCategoryId.getRepoId() == matching.getM_Product_Category_Matching_ID()))
				{
					// there is one matching with the same category as the given product
					return true;
				}
			}
		}

		// there is no product in the first term but there is a product set in the second term
		else if (productId != null)
		{
			final ProductCategoryId productCategoryId = productDAO.retrieveProductCategoryByProductId(productId);
			final List<I_C_Flatrate_Matching> flatrateMatchings = flatrateDAO.retrieveFlatrateMatchings(newTerm.getC_Flatrate_Conditions());

			for (final I_C_Flatrate_Matching matching : flatrateMatchings)
			{
				if (productId.getRepoId() == matching.getM_Product_ID())
				{
					// there is one matching for the same product as the one from the second term
					return true;
				}

				if (matching.getM_Product_ID() <= 0 && (productCategoryId.getRepoId() == matching.getM_Product_Category_Matching_ID()))
				{

					// there is one matching with the same category as the given product
					return true;
				}
			}
		}

		// both terms have no product
		else
		{
			final List<I_C_Flatrate_Matching> newFlatrateMatchings = flatrateDAO.retrieveFlatrateMatchings(newTerm.getC_Flatrate_Conditions());
			final List<I_C_Flatrate_Matching> flatrateMatchings = flatrateDAO.retrieveFlatrateMatchings(term.getC_Flatrate_Conditions());

			for (final I_C_Flatrate_Matching newFlatrateMatching : newFlatrateMatchings)
			{
				if (newFlatrateMatching.getM_Product_ID() > 0)
				{
					final org.compiere.model.I_M_Product newFMProduct = productDAO.getById(newFlatrateMatching.getM_Product_ID());
					for (final I_C_Flatrate_Matching flatrateMatching : flatrateMatchings)
					{
						if (newFMProduct.getM_Product_ID() == flatrateMatching.getM_Product_ID())
						{
							// matchings with the same product
							return true;
						}

						if (flatrateMatching.getM_Product_ID() <= 0 && (newFMProduct.getM_Product_Category_ID() == flatrateMatching.getM_Product_Category_Matching_ID()))
						{
							// there is a matching for the category if the given products

							return true;
						}
					}
				}
				// product is null. Check the product category
				else
				{
					final org.compiere.model.I_M_Product_Category newFMProductCategory = productDAO.getProductCategoryById(ProductCategoryId.ofRepoId(newFlatrateMatching.getM_Product_Category_Matching_ID()));

					for (final I_C_Flatrate_Matching flatrateMatching : flatrateMatchings)
					{
						final org.compiere.model.I_M_Product matchingProduct = productDAO.getById(flatrateMatching.getM_Product_ID());

						if (flatrateMatching.getM_Product_ID() > 0 && (matchingProduct.getM_Product_Category_ID() == newFMProductCategory.getM_Product_Category_ID()))
						{
							// the term is for a product that matches the given product category
							return true;
						}

						if (flatrateMatching.getM_Product_ID() > 0)
						{
							// the matching is only for the product set. Doesn't fit the new matching product.
							continue;
						}

						if (flatrateMatching.getM_Product_Category_Matching_ID() == newFMProductCategory.getM_Product_Category_ID())
						{
							// the terms have the same product category and no product. => overlap
							return true;
						}
					}
				}
			}
		}

		return false;

	}

	/**
	 * Check if the startDate and endDate of 2 terms overlap.
	 */
	private boolean periodsOverlap(
			@NonNull final I_C_Flatrate_Term term1,
			@NonNull final I_C_Flatrate_Term term2)
	{
		final Timestamp startDate1 = term1.getStartDate();
		final Timestamp startDate2 = term2.getStartDate();

		final Timestamp endDate1 = term1.getEndDate();
		final Timestamp endDate2 = term2.getEndDate();

		final boolean overlaps = isOverlapping(startDate1, endDate1, startDate2, endDate2);

		return overlaps;
	}

	private boolean isOverlapping(
			@NonNull final Timestamp start_1,
			@NonNull final Timestamp end_1,
			@NonNull final Timestamp start_2,
			@NonNull final Timestamp end_2)
	{
		if (end_1.before(start_1))
		{
			throw new UnsupportedOperationException("TimeUtil.inRange End_1=" + end_1 + " before Start_1=" + start_1);
		}
		if (end_2.before(start_2))
		{
			throw new UnsupportedOperationException("TimeUtil.inRange End_2=" + end_2 + " before Start_2=" + start_2);
		}
		if (TimeUtil.isBetween(start_1, start_2, end_2))
		{
			return true;
		}
		if (TimeUtil.isBetween(end_1, start_2, end_2))
		{
			return true;
		}
		return false;
	}

	@Override
	public I_C_Flatrate_Term getInitialFlatrateTerm(
			@NonNull final I_C_Flatrate_Term term)
	{
		I_C_Flatrate_Term ancestor = flatrateDAO.retrieveAncestorFlatrateTerm(term);

		if (ancestor != null)
		{
			final I_C_Flatrate_Term nextAncestor = getInitialFlatrateTerm(ancestor);
			if (nextAncestor != null)
			{
				ancestor = nextAncestor;
			}
		}

		return ancestor;
	}

	public void ensureOneContractOfGivenType(@NonNull final I_C_Flatrate_Term term, @NonNull final TypeConditions targetConditions)
	{
		if (!targetConditions.getCode().equals(term.getType_Conditions()))
		{
			return;
		}

		if (term.getEndDate() == null)
		{
			return; //not ready yet
		}

		final OrgId orgId = OrgId.ofRepoId(term.getAD_Org_ID());
		final BPartnerId billPartnerId = BPartnerId.ofRepoId(term.getBill_BPartner_ID());

		final List<I_C_Flatrate_Term> existingContracts = flatrateDAO.retrieveTerms(billPartnerId, orgId, targetConditions);

		final InstantInterval newContractInterval = InstantInterval.of(TimeUtil.asInstantNonNull(term.getStartDate()), TimeUtil.asInstantNonNull(term.getEndDate()));

		final List<Integer> existingContractsOfTargetType = existingContracts.stream()
				.filter(existingContract -> targetConditions.getCode().equals(existingContract.getType_Conditions()))
				.filter(existingContract -> existingContract.getC_Flatrate_Term_ID() != term.getC_Flatrate_Term_ID())
				.filter(existingContract -> existingContract.getEndDate() != null)
				.filter(existingContract -> {
					final InstantInterval existingContractInterval = InstantInterval.of(TimeUtil.asInstantNonNull(existingContract.getStartDate()), TimeUtil.asInstantNonNull(existingContract.getEndDate()));
					return newContractInterval.getIntersectionWith(existingContractInterval).isPresent();
				})
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.collect(ImmutableList.toImmutableList());

		if (Check.isEmpty(existingContractsOfTargetType))
		{
			return;
		}

		throw new AdempiereException("There are already identical contracts in place for the given typeConditions, org, bpartner and period!")
				.appendParametersToMessage()
				.setParameter("TypeConditions", targetConditions.getCode())
				.setParameter("startDate", term.getStartDate())
				.setParameter("bpartnerId", billPartnerId)
				.setParameter("orgId", term.getAD_Org_ID())
				.setParameter("existingContractIds", existingContractsOfTargetType);
	}

	@Override
	public void updateFlatrateTermProductAndPrice(@NonNull final FlatrateTermPriceRequest request)
	{
		final IPricingResult result = computeFlatrateTermPrice(request);

		final I_C_Flatrate_Term term = request.getFlatrateTerm();
		term.setM_Product_ID(request.getProductId().getRepoId());
		term.setPriceActual(result.getPriceStd());
		flatrateDAO.save(term);

		updateProductForInvoiceCandidate(request);
		invoiceCandidateHandlerBL.invalidateCandidatesFor(term);
	}

	private void updateProductForInvoiceCandidate(@NonNull final FlatrateTermPriceRequest request)
	{
		final I_C_Flatrate_Term term = request.getFlatrateTerm();
		final ProductId productId = request.getProductId();

		final I_C_Invoice_Candidate ic = flatrateDAO.retrieveInvoiceCandidate(term);

		if (ic == null)
		{
			return;
		}

		disableReadOnlyColumnCheck(ic); // disable it because M_Product_ID is not updateable
		ic.setM_Product_ID(productId.getRepoId());
		invoiceCandDAO.save(ic);
	}

	@Override
	public void updateFlatrateTermBillBPartner(@NonNull final FlatrateTermBillPartnerRequest request)
	{
		final I_C_Flatrate_Term term = flatrateDAO.getById(request.getFlatrateTermId());

		final int bPartnerId = request.getBillBPartnerId().getRepoId();
		term.setBill_BPartner_ID(bPartnerId);
		term.setBill_Location_ID(request.getBillLocationId().getRepoId());

		term.setBill_User_ID(BPartnerContactId.toRepoId(request.getBillUserId()));

		final int oldFlatrateDataId = term.getC_Flatrate_Data_ID();
		disableReadOnlyColumnCheck(term); // disable it because C_Flatrate_Data_ID is not updateable

		final I_C_Flatrate_Data data = flatrateDAO.retrieveOrCreateFlatrateData(bPartnerDAO.getById(bPartnerId));
		final int newFlatrateDataId = data.getC_Flatrate_Data_ID();
		term.setC_Flatrate_Data_ID(newFlatrateDataId);

		flatrateDAO.save(term);

		if (!request.isTermHasInvoices())
		{
			updateBillBPartnerForInvoiceCandidate(request);
			invoiceCandidateHandlerBL.invalidateCandidatesFor(term);
		}

		// we can't have this as a field,
		// because metasfresh might try to instantiate FlatrateBL before the spring context is ready
		final ModelCacheInvalidationService modelCacheInvalidationService = ModelCacheInvalidationService.get();

		modelCacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.of(
						CacheInvalidateRequest.rootRecord(I_C_Flatrate_Data.Table_Name, oldFlatrateDataId),
						CacheInvalidateRequest.allChildRecords(I_C_Flatrate_Data.Table_Name, oldFlatrateDataId, I_C_Flatrate_Term.Table_Name)),
				ModelCacheInvalidationTiming.AFTER_CHANGE);

		modelCacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.of(
						CacheInvalidateRequest.rootRecord(I_C_Flatrate_Data.Table_Name, newFlatrateDataId),
						CacheInvalidateRequest.allChildRecords(I_C_Flatrate_Data.Table_Name, newFlatrateDataId, I_C_Flatrate_Term.Table_Name)),
				ModelCacheInvalidationTiming.AFTER_CHANGE);

	}

	private void updateBillBPartnerForInvoiceCandidate(@NonNull final FlatrateTermBillPartnerRequest request)
	{
		final I_C_Flatrate_Term term = flatrateDAO.getById(request.getFlatrateTermId());

		final I_C_Invoice_Candidate ic = flatrateDAO.retrieveInvoiceCandidate(term);

		if (ic == null)
		{
			return;
		}

		disableReadOnlyColumnCheck(ic); // disable it because Bill_BPartner_ID is not updateable

		ic.setBill_BPartner_ID(request.getBillBPartnerId().getRepoId());
		ic.setBill_Location_ID(request.getBillLocationId().getRepoId());

		ic.setBill_User_ID(BPartnerContactId.toRepoId(request.getBillUserId()));

		invoiceCandDAO.save(ic);

	}

	private IPricingResult computeFlatrateTermPrice(@NonNull final FlatrateTermPriceRequest request)
	{
		return FlatrateTermPricing.builder()
				.term(request.getFlatrateTerm())
				.termRelatedProductId(request.getProductId())
				.priceDate(request.getPriceDate())
				.qty(BigDecimal.ONE)
				.build()
				.computeOrThrowEx();
	}

	@Override
	public I_C_Flatrate_Term getById(@NonNull final FlatrateTermId flatrateTermId)
	{
		return flatrateDAO.getById(flatrateTermId);
	}

	@Override
	public ImmutableList<I_C_Flatrate_Term> retrieveNextFlatrateTerms(@NonNull final I_C_Flatrate_Term term)
	{
		I_C_Flatrate_Term currentTerm = term;

		final ImmutableList.Builder<I_C_Flatrate_Term> nextFTsBuilder = ImmutableList.<I_C_Flatrate_Term>builder();

		while (currentTerm.getC_FlatrateTerm_Next_ID() > 0)
		{
			nextFTsBuilder.add(currentTerm.getC_FlatrateTerm_Next());
			currentTerm = currentTerm.getC_FlatrateTerm_Next();
		}

		return nextFTsBuilder.build();
	}

	@Override
	public final boolean existsTermForOrderLine(final I_C_OrderLine ol)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class, ol)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_C_OrderLine_Term_ID, ol.getC_OrderLine_ID())
				.create()
				.anyMatch();
	}

	@Override
	public boolean isModularContract(@NonNull final ConditionsId conditionsId)
	{
		final I_C_Flatrate_Conditions conditions = flatrateDAO.getConditionsById(conditionsId);

		return TypeConditions.ofCode(conditions.getType_Conditions()).isModularContractType();
	}

	@Override
	public I_C_Flatrate_Term createContractForOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		final I_C_Flatrate_Term newTerm = newInstance(I_C_Flatrate_Term.class, orderLine);

		newTerm.setC_OrderLine_Term_ID(orderLine.getC_OrderLine_ID());
		newTerm.setC_Order_Term_ID(orderLine.getC_Order_ID());

		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(orderLine.getC_Flatrate_Conditions_ID());
		Check.assume(conditionsId != null, "C_Flatrate_Conditions_ID must be set!");
		final I_C_Flatrate_Conditions conditions = flatrateDAO.getConditionsById(conditionsId);
		newTerm.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		newTerm.setType_Conditions(conditions.getType_Conditions());
		newTerm.setIsSimulation(conditions.isSimulation());

		// important: we need to use qtyEntered here, because qtyOrdered (which
		// is used for pricing) contains the number of goods to be delivered
		// over the whole subscription term
		newTerm.setPlannedQtyPerUnit(orderLine.getQtyEntered());
		newTerm.setC_UOM_ID(orderLine.getPrice_UOM_ID());

		newTerm.setStartDate(order.getDatePromised());
		newTerm.setMasterStartDate(order.getDatePromised());

		newTerm.setDeliveryRule(order.getDeliveryRule());
		newTerm.setDeliveryViaRule(order.getDeliveryViaRule());

		final BPartnerLocationAndCaptureId billToLocationId = orderBL.getBillToLocationId(order);

		final BPartnerContactId billToContactId = BPartnerContactId.ofRepoIdOrNull(billToLocationId.getBpartnerId(), order.getBill_User_ID());
		ContractDocumentLocationAdapterFactory
				.billLocationAdapter(newTerm)
				.setFrom(billToLocationId, billToContactId);

		final BPartnerContactId dropshipContactId = BPartnerContactId.ofRepoIdOrNull(orderLine.getC_BPartner_ID(), orderLine.getAD_User_ID());

		final BPartnerLocationAndCaptureId dropshipLocationId = orderBL.getShipToLocationId(order);

		ContractDocumentLocationAdapterFactory
				.dropShipLocationAdapter(newTerm)
				.setFrom(dropshipLocationId, dropshipContactId);

		final I_C_BPartner billPartner = bPartnerDAO.getById(billToLocationId.getBpartnerId());
		final I_C_Flatrate_Data existingData = flatrateDAO.retrieveOrCreateFlatrateData(billPartner);
		newTerm.setC_Flatrate_Data(existingData);

		newTerm.setAD_User_InCharge_ID(order.getSalesRep_ID());

		newTerm.setM_Product_ID(orderLine.getM_Product_ID());
		attributeSetInstanceBL.cloneASI(orderLine, newTerm);

		newTerm.setPriceActual(orderLine.getPriceActual());
		newTerm.setC_Currency_ID(orderLine.getC_Currency_ID());

		setPricingSystemTaxCategAndIsTaxIncluded(orderLine, newTerm);

		newTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Waiting);
		newTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);
		newTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);

		save(newTerm);

		return newTerm;
	}

	@Override
	public boolean isModularContract(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term flatrateTermRecord = getById(flatrateTermId);

		return isModularContract(ConditionsId.ofRepoId(flatrateTermRecord.getC_Flatrate_Conditions_ID()));
	}

	private void setPricingSystemTaxCategAndIsTaxIncluded(@NonNull final I_C_OrderLine ol, @NonNull final I_C_Flatrate_Term newTerm)
	{
		final PricingSystemTaxCategoryAndIsTaxIncluded computed = computePricingSystemTaxCategAndIsTaxIncluded(ol, newTerm);
		newTerm.setM_PricingSystem_ID(PricingSystemId.toRepoId(computed.getPricingSystemId()));
		newTerm.setC_TaxCategory_ID(computed.getTaxCategoryId().getRepoId());
		newTerm.setIsTaxIncluded(computed.isTaxIncluded());
	}

	@Value
	private static class PricingSystemTaxCategoryAndIsTaxIncluded
	{
		PricingSystemId pricingSystemId;
		TaxCategoryId taxCategoryId;
		boolean isTaxIncluded;
	}

	private PricingSystemTaxCategoryAndIsTaxIncluded computePricingSystemTaxCategAndIsTaxIncluded(@NonNull final I_C_OrderLine ol, @NonNull final I_C_Flatrate_Term newTerm)
	{
		final I_C_Flatrate_Conditions cond = flatrateDAO.getConditionsById(ol.getC_Flatrate_Conditions_ID());
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
			final I_C_Order order = ol.getC_Order();
			return new PricingSystemTaxCategoryAndIsTaxIncluded(
					PricingSystemId.ofRepoId(order.getM_PricingSystem_ID()),
					TaxCategoryId.ofRepoIdOrNull(ol.getC_TaxCategory_ID()),
					order.isTaxIncluded());
		}
	}

	private IPricingResult calculateFlatrateTermPrice(@NonNull final I_C_OrderLine ol, @NonNull final I_C_Flatrate_Term newTerm)
	{
		final I_C_Order order = ol.getC_Order();
		return FlatrateTermPricing.builder()
				.termRelatedProductId(ProductId.ofRepoId(ol.getM_Product_ID()))
				.qty(ol.getQtyEntered())
				.term(newTerm)
				.priceDate(TimeUtil.asLocalDate(order.getDateOrdered()))
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.build()
				.computeOrThrowEx();
	}

	@NonNull
	private I_ModCntr_Settings cloneModularContractSettingsToNewYear(@NonNull final I_ModCntr_Settings settings, @NonNull final I_C_Year year)
	{
		// don't make it a field; the SpringApplicationContext might not be configured yet
		final ModularContractSettingsDAO modularContractSettingsDAO = SpringContextHolder.instance.getBean(ModularContractSettingsDAO.class);

		final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoId(year.getC_Year_ID(), year.getC_Calendar_ID());
		final ProductId productId = ProductId.ofRepoId(settings.getM_Product_ID());
		if (modularContractSettingsDAO.isSettingsExist(ModularContractSettingsQuery.builder()
				.yearAndCalendarId(yearAndCalendarId)
				.productId(productId)
				.build()))
		{
			throw new AdempiereException(MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS);
		}

		final I_ModCntr_Settings newModCntrSettings = newInstance(I_ModCntr_Settings.class, settings);

		final PO from = getPO(settings);
		final PO to = getPO(newModCntrSettings);

		PO.copyValues(from, to, true);

		newModCntrSettings.setC_Year_ID(year.getC_Year_ID());

		InterfaceWrapperHelper.saveRecord(newModCntrSettings);

		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(I_ModCntr_Settings.Table_Name);
		childCRS.copyChildren(to, from); // note that the method expects the copy-*target* as first parameter

		return newModCntrSettings;
	}

	@Override
	public ConditionsId cloneConditionsToNewYear(@NonNull final ConditionsId conditionsId, @NonNull final YearId newYearId)
	{
		//
		// make sure it's Modular Contracts first
		if (!isModularContract(conditionsId))
		{
			throw new AdempiereException("Not Modular Contract Term");
		}

		final I_C_Flatrate_Conditions conditions = flatrateDAO.getConditionsById(conditionsId);
		final I_C_Flatrate_Conditions newConditions = newInstance(I_C_Flatrate_Conditions.class, conditions);

		final PO from = getPO(conditions);
		final PO to = getPO(newConditions);

		PO.copyValues(from, to, true);

		final I_C_Year newYear = load(newYearId, I_C_Year.class);
		newConditions.setName(conditions.getName().concat("-" + newYear.getFiscalYear()));

		final I_ModCntr_Settings modCntrSettings = cloneModularContractSettingsToNewYear(conditions.getModCntr_Settings(), newYear);

		newConditions.setModCntr_Settings_ID(modCntrSettings.getModCntr_Settings_ID());
		newConditions.setDocStatus(X_C_Flatrate_Conditions.DOCSTATUS_Drafted);

		InterfaceWrapperHelper.saveRecord(newConditions);

		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(I_C_Flatrate_Conditions.Table_Name);
		childCRS.copyChildren(from, to);

		return ConditionsId.ofRepoId(newConditions.getC_Flatrate_Conditions_ID());

	}

	@Override
	public boolean isExtendableContract(@NonNull final I_C_Flatrate_Term contract)
	{
		return !isModularContract(FlatrateTermId.ofRepoId(contract.getC_Flatrate_Term_ID()))
				&& !ONFLATRATETERMEXTEND_ExtensionNotAllowed.equals(contract.getC_Flatrate_Conditions().getOnFlatrateTermExtend());
	}

	@NonNull
	@Override
	public Stream<I_C_Flatrate_Term> streamModularFlatrateTermsByQuery(@NonNull final ModularFlatrateTermQuery query)
	{
		return flatrateDAO.getModularFlatrateTermsByQuery(query).stream();
	}

	@Override
	public boolean isModularContractInProgress(@NonNull final ModularFlatrateTermQuery query)
	{
		return !flatrateDAO.getModularFlatrateTermsByQuery(query).isEmpty();
	}

	@NonNull
	@Override
	public Stream<FlatrateTermId> streamModularFlatrateTermIdsByQuery(@NonNull final ModularFlatrateTermQuery query)
	{
		return streamModularFlatrateTermsByQuery(query).map(FlatrateBL::extractId);
	}

	private static FlatrateTermId extractId(final I_C_Flatrate_Term record) {return FlatrateTermId.ofRepoId(record.getC_Flatrate_Term_ID());}

	@Nullable
	@Override
	public FlatrateTermId getInterimContractIdByModularContractIdAndDate(@NonNull final FlatrateTermId modularFlatrateTermId, @NonNull final Instant date)
	{
		return FlatrateTermId.ofRepoIdOrNull(queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Modular_Flatrate_Term_ID, modularFlatrateTermId.getRepoId())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.INTERIM_INVOICE)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, CompareQueryFilter.Operator.LESS_OR_EQUAL, date)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, CompareQueryFilter.Operator.GREATER_OR_EQUAL, date)
				.create()
				.firstIdOnly());
	}
}
