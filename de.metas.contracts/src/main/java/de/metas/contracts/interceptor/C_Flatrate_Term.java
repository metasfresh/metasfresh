package de.metas.contracts.interceptor;

import java.sql.Timestamp;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.ModelValidator;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.service.ICalendarDAO;
import de.metas.contracts.Contracts_Constants;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.IFlatrateTermEventService;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.contracts.flatrate.interfaces.I_C_OLCand;
import de.metas.contracts.impl.FlatrateBL;
import de.metas.contracts.model.I_C_Contract_Term_Alloc;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.IDocTypeDAO.DocTypeCreateRequest;
import de.metas.i18n.IMsgBL;
import de.metas.ordercandidate.modelvalidator.C_OLCand;
import lombok.NonNull;

@Interceptor(I_C_Flatrate_Term.class)
public class C_Flatrate_Term
{
	public static C_Flatrate_Term INSTANCE = new C_Flatrate_Term();

	private static final String CONFIG_FLATRATE_TERM_ALLOW_REACTIVATE = "de.metas.contracts.C_Flatrate_Term.allow_reactivate_%s";

	private static final String MSG_TERM_ERROR_PLANNED_QTY_PER_UNIT = "Term_Error_PlannedQtyPerUnit";

	private static final String MSG_TERM_ERROR_YEAR_WITHOUT_PERIODS_2P = "Term_Error_Range_Without_Periods";
	private static final String MSG_TERM_ERROR_PERIOD_END_DATE_BEFORE_TERM_END_DATE_2P = "Term_Error_PeriodEndDate_Before_TermEndDate";
	private static final String MSG_TERM_ERROR_PERIOD_START_DATE_AFTER_TERM_START_DATE_2P = "Term_Error_PeriodStartDate_After_TermStartDate";

	private C_Flatrate_Term()
	{
	}

	@Init
	public void initialize(final IModelValidationEngine engine)
	{
		if (Ini.isClient() == false) // 03429: we only need to check this on server startup
		{
			ensureDocTypesExist(I_C_DocType.DocSubType_Abonnement);
			ensureDocTypesExist(I_C_DocType.DocSubType_Depotgebuehr);
			ensureDocTypesExist(I_C_DocType.DocSubType_Pauschalengebuehr);
		}
	}

	private void ensureDocTypesExist(final String docSubType)
	{
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final List<I_AD_Org> orgs = new Query(Env.getCtx(), I_AD_Org.Table_Name, I_AD_Org.COLUMNNAME_AD_Org_ID + "!=0", null)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_AD_Org.COLUMNNAME_AD_Org_ID)
				.list(I_AD_Org.class);

		for (final I_AD_Org org : orgs)
		{
			// we need to work with a local ctx that contains the current Org's AD_Client_ID
			// otherwise both the lookup of existing DocTypes and the creation of new doc types
			// (MDocType.setGL_Category_ID() ) will fail.
			final Properties localCtx = Env.deriveCtx(Env.getCtx());
			Env.setContext(localCtx, Env.CTXNAME_AD_Client_ID, org.getAD_Client_ID());
			Env.setContext(localCtx, Env.CTXNAME_AD_Org_ID, org.getAD_Org_ID());

			final org.compiere.model.I_C_DocType existingDocType = docTypeDAO
					.getDocTypeOrNull(DocTypeQuery.builder()
							.docBaseType(I_C_DocType.DocBaseType_CustomerContract)
							.docSubType(docSubType)
							.adClientId(org.getAD_Client_ID())
							.adOrgId(org.getAD_Org_ID())
							.build());
			if (existingDocType != null)
			{
				continue;
			}

			final POInfo docTypePOInfo = POInfo.getPOInfo(I_C_DocType.Table_Name);
			final String name = Services.get(IADReferenceDAO.class).retrieveListNameTrl(docTypePOInfo.getColumnReferenceValueId(docTypePOInfo.getColumnIndex(I_C_DocType.COLUMNNAME_DocSubType)), docSubType)
					+ " (" + org.getValue() + ")";
			docTypeDAO.createDocType(DocTypeCreateRequest.builder()
					.ctx(localCtx)
					.adOrgId(org.getAD_Org_ID())
					.entityType(Contracts_Constants.ENTITY_TYPE)
					.name(name)
					.printName(name)
					.docBaseType(I_C_DocType.DocBaseType_CustomerContract)
					.docSubType(docSubType)
					.isSOTrx(true)
					.newDocNoSequenceStartNo(10000)
					.build());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setFlatrateDataProcessed(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Data flatrateData = term.getC_Flatrate_Data();

		term.setBill_BPartner_ID(flatrateData.getC_BPartner_ID());

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

			final List<I_C_Period> periodsOfTerm = calendarDAO.retrievePeriods(ctx, invoicingCal, term.getStartDate(), term.getEndDate(), trxName);

			if (periodsOfTerm.isEmpty())
			{
				errors.add(msgBL.getMsg(ctx, MSG_TERM_ERROR_YEAR_WITHOUT_PERIODS_2P,
						new Object[] { term.getStartDate(), term.getEndDate() }));
			}
			else
			{
				if (periodsOfTerm.get(0).getStartDate().after(term.getStartDate()))
				{
					errors.add(msgBL.getMsg(ctx, MSG_TERM_ERROR_PERIOD_START_DATE_AFTER_TERM_START_DATE_2P,
							new Object[] { term.getStartDate(), invoicingCal.getName() }));
				}
				final I_C_Period lastPeriodOfTerm = periodsOfTerm.get(periodsOfTerm.size() - 1);
				if (lastPeriodOfTerm.getEndDate().before(term.getEndDate()))
				{
					errors.add(msgBL.getMsg(ctx, MSG_TERM_ERROR_PERIOD_END_DATE_BEFORE_TERM_END_DATE_2P,
							new Object[] { lastPeriodOfTerm.getEndDate(), invoicingCal.getName() }));
				}
			}
		}

		if (!errors.isEmpty())
		{
			throw new AdempiereException(concatStrings(errors));
		}
	}

	/**
	 * If the term that is deleted was the last term, remove the "processed"-flag from the term's data record.
	 *
	 * @param term
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void updateFlatrateData(final I_C_Flatrate_Term term)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final I_C_Flatrate_Data flatrateData = term.getC_Flatrate_Data();
		final List<I_C_Flatrate_Term> terms = flatrateDB.retrieveTerms(flatrateData);
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
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final List<I_C_Flatrate_Term> predecessorTerms = new Query(ctx, I_C_Flatrate_Term.Table_Name, I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID + "=?", trxName)
				.setParameters(term.getC_Flatrate_Term_ID())
				.setOrderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
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
	 *
	 * @param term
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void updateOLCandReference(final I_C_Flatrate_Term term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final List<I_C_Contract_Term_Alloc> ctas = new Query(ctx, I_C_Contract_Term_Alloc.Table_Name, I_C_Contract_Term_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "=?", trxName)
				.setParameters(term.getC_Flatrate_Term_ID())
				.setOrderBy(I_C_Contract_Term_Alloc.COLUMNNAME_C_Contract_Term_Alloc_ID)
				.list(I_C_Contract_Term_Alloc.class);
		for (final I_C_Contract_Term_Alloc cta : ctas)
		{
			final I_C_OLCand olCand = InterfaceWrapperHelper.create(cta.getC_OLCand(), I_C_OLCand.class);
			olCand.setProcessed(false);
			InterfaceWrapperHelper.save(olCand);
			InterfaceWrapperHelper.delete(cta);
		}
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
		if (X_C_Flatrate_Term.TYPE_CONDITIONS_FlatFee.equals(term.getType_Conditions()))
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
	public void afterComplete(final I_C_Flatrate_Term term)
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
	 *
	 * @param term
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/549
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
	 *
	 * @param term
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void preventOverlappingTerms_OnComplete(final I_C_Flatrate_Term term)
	{
		// services
		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final boolean hasOverlappingTerms = flatrateBL.hasOverlappingTerms(term);
		if (hasOverlappingTerms)
		{
			throw new AdempiereException(FlatrateBL.MSG_HasOverlapping_Term, new Object[] { term.getC_Flatrate_Term_ID(), term.getBill_BPartner().getValue() });
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
		if (InterfaceWrapperHelper.isValueChanged(term, I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID) )
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
			final I_C_Flatrate_Term ancestor = Services.get(IFlatrateDAO.class).retrieveAncestorFlatrateTerm(term);
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
}
