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

package de.metas.contracts.interceptor;

import de.metas.acct.GLCategoryRepository;
import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.calendar.standard.ICalendarDAO;
import de.metas.contracts.Contracts_Constants;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.IFlatrateTermEventService;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Contract_Term_Alloc;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.order.ContractOrderService;
import de.metas.contracts.order.UpdateContractOrderStatus;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.IDocTypeDAO.DocTypeCreateRequest;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.modelvalidator.C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.ModelValidator;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

@Interceptor(I_C_Flatrate_Term.class)
public class C_Flatrate_Term
{
	private final static Logger logger = LogManager.getLogger(C_Flatrate_Term.class);

	private static final String CONFIG_FLATRATE_TERM_ALLOW_REACTIVATE = "de.metas.contracts.C_Flatrate_Term.allow_reactivate_%s";

	private static final String MSG_TERM_ERROR_PLANNED_QTY_PER_UNIT = "Term_Error_PlannedQtyPerUnit";

	private static final String MSG_TERM_ERROR_YEAR_WITHOUT_PERIODS_2P = "Term_Error_Range_Without_Periods";
	private static final String MSG_TERM_ERROR_PERIOD_END_DATE_BEFORE_TERM_END_DATE_2P = "Term_Error_PeriodEndDate_Before_TermEndDate";
	private static final String MSG_TERM_ERROR_PERIOD_START_DATE_AFTER_TERM_START_DATE_2P = "Term_Error_PeriodStartDate_After_TermStartDate";
	private static final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private final IBPartnerDAO bparnterDAO = Services.get(IBPartnerDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final IDocumentLocationBL documentLocationBL;

	private final ContractOrderService contractOrderService;
	private final IOLCandDAO candDAO = Services.get(IOLCandDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ADReferenceService adReferenceService;
	private final GLCategoryRepository glCategoryRepository;

	public C_Flatrate_Term(
			@NonNull final ContractOrderService contractOrderService,
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final ADReferenceService adReferenceService,
			@NonNull final GLCategoryRepository glCategoryRepository)
	{
		this.contractOrderService = contractOrderService;
		this.documentLocationBL = documentLocationBL;
		this.adReferenceService = adReferenceService;
		this.glCategoryRepository = glCategoryRepository;
	}

	@Init
	public void initialize(final IModelValidationEngine engine)
	{
		if (Ini.isSwingClient() == false) // 03429: we only need to check this on server startup
		{
			ensureDocTypesExist(I_C_DocType.DocSubType_Abonnement);
			ensureDocTypesExist(I_C_DocType.DocSubType_Depotgebuehr);
			ensureDocTypesExist(I_C_DocType.DocSubType_Pauschalengebuehr);
			ensureDocTypesExist(I_C_DocType.DocSubType_CallOrder);
		}
	}

	private void ensureDocTypesExist(final String docSubType)
	{

		final ClientId clientId = ClientId.METASFRESH;
		final List<I_AD_Org> orgs = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_AD_Org.COLUMNNAME_AD_Client_ID, clientId)
				.addNotEqualsFilter(I_AD_Org.COLUMNNAME_AD_Org_ID, OrgId.ANY)
				.orderBy(I_AD_Org.COLUMNNAME_AD_Org_ID)
				.create()
				.list(I_AD_Org.class);

		for (final I_AD_Org org : orgs)
		{
			// we need to work with a local ctx that contains the current Org's AD_Client_ID
			// otherwise both the lookup of existing DocTypes and the creation of new doc types
			// (MDocType.setGL_Category_ID() ) will fail.
			final Properties localCtx = Env.deriveCtx(Env.getCtx());
			Env.setContext(localCtx, Env.CTXNAME_AD_Client_ID, org.getAD_Client_ID());
			Env.setContext(localCtx, Env.CTXNAME_AD_Org_ID, org.getAD_Org_ID());

			final Optional<org.compiere.model.I_C_DocType> existingDocType = docTypeDAO
					.retrieveDocType(DocTypeQuery.builder()
							.docBaseType(DocBaseType.CustomerContract)
											 .docSubType(docSubType)
											 .adClientId(org.getAD_Client_ID())
											 .adOrgId(org.getAD_Org_ID())
											 .build());
			if (existingDocType.isPresent())
			{
				continue;
			}

			final POInfo docTypePOInfo = POInfo.getPOInfo(I_C_DocType.Table_Name);
			final String name = adReferenceService.retrieveListNameTrl(docTypePOInfo.getColumnReferenceValueId(docTypePOInfo.getColumnIndex(I_C_DocType.COLUMNNAME_DocSubType)), docSubType)
					+ " (" + org.getValue() + ")";
			docTypeDAO.createDocType(DocTypeCreateRequest.builder()
											 .ctx(localCtx)
											 .adOrgId(org.getAD_Org_ID())
											 .entityType(Contracts_Constants.ENTITY_TYPE)
											 .name(name)
											 .printName(name)
					.docBaseType(DocBaseType.CustomerContract)
											 .docSubType(docSubType)
											 .isSOTrx(true)
											 .newDocNoSequenceStartNo(10000)
					.glCategoryId(glCategoryRepository.getDefaultId(clientId).orElseThrow(() -> new AdempiereException("No default GL Category found")))
											 .build());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setFlatrateDataProcessed(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Data flatrateData = term.getC_Flatrate_Data();

		if (term.getBill_BPartner_ID() == 0)
		{ // make sure not to override a Bill_BPartner_ID that is already set via extend-flatrate-term
			term.setBill_BPartner_ID(flatrateData.getC_BPartner_ID());
		}

		if (!flatrateData.isHasContracts())
		{
			flatrateData.setHasContracts(true);
			InterfaceWrapperHelper.save(flatrateData);
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Flatrate_Term.COLUMNNAME_M_PricingSystem_ID, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID
	})
	public void validatePricing(final I_C_Flatrate_Term term)
	{
		if (X_C_Flatrate_Term.DOCSTATUS_Completed.equals(term.getDocStatus()))
		{
			return; // nothing to do
		}

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(term.getType_Conditions()))
		{
			// check if the current products are on the price list
			final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
			flatrateBL.validatePricing(term);
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, I_C_Flatrate_Term.COLUMNNAME_StartDate, I_C_Flatrate_Term.COLUMNNAME_EndDate, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Transition_ID
	})
	public void validatePeriods(final I_C_Flatrate_Term term)
	{
		if (term.getStartDate() != null && !term.isProcessed())
		{
			Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);
			setMasterEndDate(term);
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final List<String> errors = new ArrayList<>();

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		if (term.getStartDate() != null
				&& term.getEndDate() != null
				&& !X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription.equals(term.getType_Conditions()))
		{
			final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);

			final I_C_Calendar invoicingCal = term.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract();

			final OrgId orgId = OrgId.ofRepoId(term.getAD_Org_ID());
			final LocalDateAndOrgId startDate = LocalDateAndOrgId.ofTimestamp(term.getStartDate(), orgId, orgDAO::getTimeZone);
			final LocalDateAndOrgId endDate = LocalDateAndOrgId.ofTimestamp(term.getEndDate(), orgId, orgDAO::getTimeZone);

			final List<I_C_Period> periodsOfTerm = calendarDAO.retrievePeriods(ctx, invoicingCal, startDate, endDate, trxName);

			if (periodsOfTerm.isEmpty())
			{
				errors.add(msgBL.getMsg(ctx, MSG_TERM_ERROR_YEAR_WITHOUT_PERIODS_2P,
										new Object[] { term.getStartDate(), term.getEndDate() }));
			}
			else
			{
				final LocalDateAndOrgId firstPeriodStartDate = LocalDateAndOrgId.ofTimestamp(periodsOfTerm.get(0).getStartDate(), orgId, orgDAO::getTimeZone);
				if (firstPeriodStartDate.compareTo(startDate) > 0)
				{
					errors.add(msgBL.getMsg(ctx, MSG_TERM_ERROR_PERIOD_START_DATE_AFTER_TERM_START_DATE_2P,
											new Object[] { term.getStartDate(), invoicingCal.getName() }));
				}

				final LocalDateAndOrgId lastPeriodEndDate = LocalDateAndOrgId.ofTimestamp(periodsOfTerm.get(periodsOfTerm.size() - 1).getEndDate(), orgId, orgDAO::getTimeZone);
				
				if (lastPeriodEndDate.compareTo(endDate) < 0)
				{
					errors.add(msgBL.getMsg(ctx, MSG_TERM_ERROR_PERIOD_END_DATE_BEFORE_TERM_END_DATE_2P,
							new Object[] { lastPeriodEndDate.toTimestamp(orgDAO::getTimeZone), invoicingCal.getName() }));
				}
			}
		}

		if (!errors.isEmpty())
		{
			throw new AdempiereException(concatStrings(errors)).markAsUserValidationError();
		}
	}

	/**
	 * If the term that is deleted was the last term, remove the "processed"-flag from the term's data record.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void updateFlatrateData(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Data flatrateData = term.getC_Flatrate_Data();
		final List<I_C_Flatrate_Term> terms = flatrateDAO.retrieveTerms(flatrateData);
		if (terms.size() == 1)
		{
			Check.assume(
					terms.get(0).getC_Flatrate_Term_ID() == term.getC_Flatrate_Term_ID(),
					"The one term that is left shoud be the current term, but is " + terms.get(0));

			flatrateData.setHasContracts(false);
			InterfaceWrapperHelper.save(flatrateData);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void unsetNextIdReference(final I_C_Flatrate_Term term)
	{

		final List<I_C_Flatrate_Term> predecessorTerms = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class, term)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID, term.getC_Flatrate_Term_ID())
				.orderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.create()
				.list(I_C_Flatrate_Term.class);

		Check.assume(predecessorTerms.size() <= 1, term + " has max 1 predecessor");
		for (final I_C_Flatrate_Term predecessor : predecessorTerms)
		{
			predecessor.setC_FlatrateTerm_Next_ID(0);
			InterfaceWrapperHelper.save(predecessor);
		}
	}

	/**
	 * If the term that is deleted is referenced from a {@link C_OLCand}, delete the reference and set the cand to <code>processed='N'</code>.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void updateOLCandReference(final I_C_Flatrate_Term term)
	{
		final List<I_C_Contract_Term_Alloc> ctas = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Contract_Term_Alloc.class, term)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Contract_Term_Alloc.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.orderBy(I_C_Contract_Term_Alloc.COLUMNNAME_C_Contract_Term_Alloc_ID)
				.create()
				.list(I_C_Contract_Term_Alloc.class);

		for (final I_C_Contract_Term_Alloc cta : ctas)
		{
			final de.metas.ordercandidate.model.I_C_OLCand olCand = candDAO.retrieveByIds(OLCandId.ofRepoId(cta.getC_OLCand_ID()));
			olCand.setProcessed(false);
			InterfaceWrapperHelper.save(olCand);
			InterfaceWrapperHelper.delete(cta);
		}
	}

	/**
	 * If a C_Flatrate_Term is deleted, then this method deletes the candidates which directly reference that line via <code>AD_Table_ID</code> and <code>Record_ID</code>.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteC_Invoice_Candidates(final I_C_Flatrate_Term term)
	{
		invoiceCandDAO.deleteAllReferencingInvoiceCandidates(term);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID)
	public void updatePricingSystem(final I_C_Flatrate_Term term)
	{
		if (term.getC_Flatrate_Conditions_ID() > 0)
		{
			final int conditionsPricingSystemId = term.getC_Flatrate_Conditions().getM_PricingSystem_ID();
			if (conditionsPricingSystemId > 0)
			{
				term.setM_PricingSystem_ID(conditionsPricingSystemId);
			}
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE
	}, ifColumnsChanged = {
			I_C_Flatrate_Term.COLUMNNAME_EndDate
	})
	public void checkEndDateNotNull(final I_C_Flatrate_Term term)
	{
		if (term.getEndDate() == null)
		{
			throw new AdempiereException("@FillMandatory@:" + I_C_Flatrate_Term.COLUMNNAME_EndDate + " (Should have been set by the system!)");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteEntries(final I_C_Flatrate_Term term)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		queryBL.createQueryBuilder(I_C_Flatrate_DataEntry.class, term)
				.addEqualsFilter(I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.create()
				.delete();
	}

	private String concatStrings(final List<String> errors)
	{
		final StringBuilder sb = new StringBuilder();
		if (errors.isEmpty())
		{
			return null;
		}
		for (final String error : errors)
		{
			sb.append(error);
			sb.append('\n');
		}
		return sb.toString();
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_CLOSE })
	public void prohibitVoidingAndClosing(final I_C_Flatrate_Term term)
	{
		throw new AdempiereException("@" + MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P + "@");
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void prohibitReactivatingUnlessAllowed(final I_C_Flatrate_Term term)
	{
		final String sysConfigName = String.format(CONFIG_FLATRATE_TERM_ALLOW_REACTIVATE, term.getType_Conditions());
		final boolean reactivateIsAllowed = Services.get(ISysConfigBL.class).getBooleanValue(sysConfigName, false, term.getAD_Client_ID(), term.getAD_Org_ID());
		if (reactivateIsAllowed)
		{
			return;
		}
		throw new AdempiereException("@" + MainValidator.MSG_FLATRATE_REACTIVATE_DOC_ACTION_NOT_SUPPORTED_0P + "@");
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void beforeComplete(final I_C_Flatrate_Term term)
	{
		final boolean isFlatFee = X_C_Flatrate_Term.TYPE_CONDITIONS_FlatFee.equals(term.getType_Conditions());
		final boolean isRepordedQty = X_C_Flatrate_Term.TYPE_FLATRATE_ReportedQuantity.equals(term.getType_Flatrate());
		if (isFlatFee && !isRepordedQty)
		{
			if (term.getPlannedQtyPerUnit().signum() <= 0)
			{
				throw new AdempiereException("@" + MSG_TERM_ERROR_PLANNED_QTY_PER_UNIT + "@");
			}
		}
		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(term.getType_Conditions()))
		{
			// first do a number of checks
			final List<String> missingValues = new ArrayList<>();

			if (term.getM_PricingSystem_ID() <= 0)
			{
				missingValues.add(I_C_Flatrate_Term.COLUMNNAME_M_PricingSystem_ID);
			}
			if (term.getDropShip_BPartner_ID() <= 0)
			{
				missingValues.add(I_C_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID);
			}
			if (term.getDropShip_Location_ID() <= 0)
			{
				missingValues.add(I_C_Flatrate_Term.COLUMNNAME_DropShip_Location_ID);
			}
			if (term.getM_Product_ID() <= 0)
			{
				missingValues.add(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID);
			}
			if (term.getPlannedQtyPerUnit().signum() < 0)
			{
				// note that we accept a qty of 0
				missingValues.add(I_C_Flatrate_Term.COLUMNNAME_PlannedQtyPerUnit);
			}
			if (!missingValues.isEmpty())
			{
				throw new FillMandatoryException(missingValues.toArray(new String[0]));
			}
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void afterComplete(@NonNull final I_C_Flatrate_Term term)
	{
		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(term.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_FlatFee.equals(term.getType_Conditions()))
		{
			Check.assume(term.getPlannedQtyPerUnit().signum() >= 0, "@" + MSG_TERM_ERROR_PLANNED_QTY_PER_UNIT + "@");
		}

		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(term.getType_Conditions()))
		{
			Services.get(ISubscriptionBL.class).evalCurrentSPs(term, term.getStartDate());
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_HoldingFee.equals(term.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Refundable.equals(term.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_FlatFee.equals(term.getType_Conditions()))
		{
			Services.get(IFlatrateBL.class).createDataEntriesForTerm(term);
		}
	}

	/**
	 * Updates the <code>EndDate</code> and <code>NoticeDate</code> of the given term's predecessor(s).
	 * <p>
	 * task https://github.com/metasfresh/metasfresh/issues/549
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void updatePredecessorDates(final I_C_Flatrate_Term term)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_C_Flatrate_Term.class, term)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_C_FlatrateTerm_Next_ID, term.getC_Flatrate_Term_ID())
				.create().list() // note that there should be just one predecessor, but here we don't really need to care about this question
				.stream()
				.forEach(predecessorTerm -> {

					final Timestamp newEndDate = TimeUtil.addDays(term.getStartDate(), -1);
					predecessorTerm.setEndDate(newEndDate);
					if (newEndDate.before(predecessorTerm.getNoticeDate()))
					{
						predecessorTerm.setNoticeDate(newEndDate);
					}

					InterfaceWrapperHelper.save(predecessorTerm);
				});
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void beforeReactivateFireFlatrateTermEventListener(final I_C_Flatrate_Term term)
	{
		Services.get(IFlatrateTermEventService.class)
				.getHandler(term.getType_Conditions())
				.beforeFlatrateTermReactivate(term);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID)
	public void copyFromConditions(final I_C_Flatrate_Term term)
	{
		Services.get(IFlatrateBL.class).updateFromConditions(term);
	}

	/**
	 * task #1169
	 * In case the term to be completed overlaps with other term regarding time period and product the user must be announced about this and the new term shall not be completed
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void preventOverlappingTerms_OnComplete(final I_C_Flatrate_Term term)
	{
		// services
		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final boolean overlappingIsOK = flatrateBL.isAllowedToOverlapWithOtherTerms(term);
		if (overlappingIsOK)
		{
			return; // nothing to do
		}

		final boolean hasOverlappingTerms = flatrateBL.hasOverlappingTerms(term);
		if (hasOverlappingTerms)
		{
			final I_C_BPartner billBPartnerRecord = bparnterDAO.getById(term.getBill_BPartner_ID());

			throw new AdempiereException(de.metas.contracts.impl.FlatrateBL.MSG_HasOverlapping_Term, term.getC_Flatrate_Term_ID(), billBPartnerRecord.getValue())
					.markAsUserValidationError();
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID)
	public void updateMasterEndDate(final I_C_Flatrate_Term term)
	{
		setMasterEndDate(term);
	}

	private void setMasterEndDate(final I_C_Flatrate_Term term)
	{
		Timestamp masterEndDate = null;
		if (InterfaceWrapperHelper.isNew(term) && !term.isAutoRenew())
		{
			masterEndDate = term.getEndDate();
		}

		masterEndDate = computeMasterEndDateIfC_FlatrateTerm_Next_IDChanged(term, masterEndDate);

		term.setMasterEndDate(masterEndDate);
	}

	private Timestamp computeMasterEndDateIfC_FlatrateTerm_Next_IDChanged(@NonNull final I_C_Flatrate_Term term, Timestamp masterEndDate)
	{
		if (InterfaceWrapperHelper.isValueChanged(term, I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID))
		{
			if (term.getC_FlatrateTerm_Next_ID() > 0)
			{
				masterEndDate = term.getC_FlatrateTerm_Next().getMasterEndDate();
			}
			else
			{
				masterEndDate = term.getEndDate();
			}
		}

		return masterEndDate;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void setMasterDocumentNo(final I_C_Flatrate_Term term)
	{
		if (!Check.isEmpty(term.getDocumentNo(), true) && Check.isEmpty(term.getMasterDocumentNo(), true))
		{
			final I_C_Flatrate_Term ancestor = flatrateDAO.retrieveAncestorFlatrateTerm(term);
			if (ancestor == null)
			{
				term.setMasterDocumentNo(term.getDocumentNo());
			}
			else
			{
				term.setMasterDocumentNo(ancestor.getMasterDocumentNo());
			}
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_AFTER_CHANGE
	}, ifColumnsChanged = {
			I_C_Flatrate_Term.COLUMNNAME_ContractStatus, I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID
	})
	public void updateContractStatusInOrder(final I_C_Flatrate_Term term)
	{
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);
		final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

		final OrderId orderId = contractOrderService.getContractOrderId(term);
		if (orderId == null)
		{
			return;
		}

		if (InterfaceWrapperHelper.isNew(term) && !contractsDAO.termHasAPredecessor(term))
		{
			final I_C_Order contractOrder = orderDAO.getById(orderId, I_C_Order.class);
			contractOrderService.setOrderContractStatusAndSave(contractOrder, I_C_Order.CONTRACTSTATUS_Active);
			return;
		}

		final Set<OrderId> orderIds = contractOrderService.retrieveAllContractOrderList(orderId);

		final UpdateContractOrderStatus updateContractStatus = UpdateContractOrderStatus.builder()
				.contractOrderService(contractOrderService)
				.orderDAO(orderDAO)
				.contractsDAO(contractsDAO)
				.subscriptionBL(subscriptionBL)
				.build();
		updateContractStatus.updateStatusIfNeededWhenExtendind(term, orderIds);
		updateContractStatus.updateStatusIfNeededWhenCancelling(term, orderIds);
		updateContractStatus.updateStausIfNeededWhenVoiding(term);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID,
			I_C_Flatrate_Term.COLUMNNAME_Bill_Location_ID,
			I_C_Flatrate_Term.COLUMNNAME_Bill_User_ID },
			skipIfCopying = true)
	public void updateBillToAddress(final I_C_Flatrate_Term term)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(ContractDocumentLocationAdapterFactory.billLocationAdapter(term));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID,
			I_C_Flatrate_Term.COLUMNNAME_DropShip_Location_ID,
			I_C_Flatrate_Term.COLUMNNAME_DropShip_User_ID },
			skipIfCopying = true)
	public void updateDropshipAddress(final I_C_Flatrate_Term term)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(ContractDocumentLocationAdapterFactory.dropShipLocationAdapter(term));
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	},
			ifColumnsChanged = {
					I_C_Flatrate_Term.COLUMNNAME_Type_Conditions,
					I_C_Flatrate_Term.COLUMNNAME_StartDate,
					I_C_Flatrate_Term.COLUMNNAME_EndDate,
					I_C_Flatrate_Term.COLUMNNAME_AD_Org_ID,
					I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID
			})
	public void ensureOneContract(@NonNull final I_C_Flatrate_Term term)
	{
		ensureOneContractOfGivenType(term);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void ensureOneContractBeforeComplete(@NonNull final I_C_Flatrate_Term term)
	{
		ensureOneContractOfGivenType(term);
	}

	private void ensureOneContractOfGivenType(@NonNull final I_C_Flatrate_Term term)
	{
		flatrateBL.ensureOneContractOfGivenType(term, TypeConditions.MARGIN_COMMISSION);
		final TypeConditions contractType = TypeConditions.ofCode(term.getType_Conditions());

		switch (contractType)
		{
			case MEDIATED_COMMISSION:
			case MARGIN_COMMISSION:
			case LICENSE_FEE:
				flatrateBL.ensureOneContractOfGivenType(term, contractType);
			default:
				logger.debug("Skipping ensureOneContractOfGivenType check for 'Type_Conditions' =" + contractType);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void setC_Flatrate_Term_Master(@NonNull final I_C_Flatrate_Term term)
	{
		if (term.getC_Flatrate_Term_Master_ID() <= 0)
		{
			final I_C_Flatrate_Term ancestor = flatrateDAO.retrieveAncestorFlatrateTerm(term);
			if (ancestor == null)
			{
				term.setC_Flatrate_Term_Master_ID(term.getC_Flatrate_Term_ID());
			}
		}
	}
}
