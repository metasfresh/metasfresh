package de.metas.flatrate.modelvalidator;

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
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
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
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.document.IDocumentPA;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.api.IFlatrateHandlersService;
import de.metas.flatrate.interfaces.I_C_DocType;
import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.flatrate.model.I_C_Contract_Term_Alloc;
import de.metas.flatrate.model.I_C_Flatrate_Data;
import de.metas.flatrate.model.I_C_Flatrate_DataEntry;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Conditions;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.ordercandidate.modelvalidator.C_OLCand;

@Validator(I_C_Flatrate_Term.class)
public class C_Flatrate_Term
{

	private static final String MSG_TERM_ERROR_PLANNED_QTY_PER_UNIT = "Term_Error_PlannedQtyPerUnit";

	private static final String MSG_TERM_ERROR_YEAR_WITHOUT_PERIODS_2P = "Term_Error_Range_Without_Periods";
	private static final String MSG_TERM_ERROR_PERIOD_END_DATE_BEFORE_TERM_END_DATE_2P = "Term_Error_PeriodEndDate_Before_TermEndDate";
	private static final String MSG_TERM_ERROR_PERIOD_START_DATE_AFTER_TERM_START_DATE_2P = "Term_Error_PeriodStartDate_After_TermStartDate";

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
		final IDocumentPA documentPA = Services.get(IDocumentPA.class);

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
			Env.setContext(localCtx, "#AD_Client_ID", org.getAD_Client_ID());
			Env.setContext(localCtx, "#AD_Org_ID", org.getAD_Org_ID());

			if (null != documentPA.retrieve(localCtx, org.getAD_Org_ID(), I_C_DocType.DocBaseType_CustomerContract, docSubType, false, null))
			{
				continue;
			}

			final POInfo docTypePOInfo = POInfo.getPOInfo(localCtx, I_C_DocType.Table_Name);
			final String name =
							Services.get(IADReferenceDAO.class).retrieveListNameTrl(localCtx, docTypePOInfo.getColumnReferenceValueId(docTypePOInfo.getColumnIndex(I_C_DocType.COLUMNNAME_DocSubType)), docSubType)
							+ " (" + org.getValue() + ")";
			final org.compiere.model.I_C_DocType newType = documentPA.createDocType(localCtx, "de.metas.flatrate",
					name,
					name,
					I_C_DocType.DocBaseType_CustomerContract, docSubType, 0, 0, 10000, 0, null);

			// newType.setAD_Org_ID(org.getAD_Org_ID());
			newType.setIsSOTrx(true);
			InterfaceWrapperHelper.save(newType);
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

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr.equals(term.getType_Conditions()))
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
		if (term.getStartDate() != null)
		{
			Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final List<String> errors = new ArrayList<String>();

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		if (term.getStartDate() != null
				&& term.getEndDate() != null
				&& !X_C_Flatrate_Conditions.TYPE_CONDITIONS_Abonnement.equals(term.getType_Conditions()))
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

		final List<I_C_Flatrate_Term> predecessorTerms =
				new Query(ctx, I_C_Flatrate_Term.Table_Name, I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID + "=?", trxName)
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

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID)
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
	public void prohibitVoidAndClose(final I_C_Flatrate_Term term)
	{
		throw new AdempiereException("@" + MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P + "@");
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void beforeComplete(final I_C_Flatrate_Term term)
	{
		// TODO: refactor it to specific IFlatrateHandlers

		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Pauschalengebuehr.equals(term.getType_Conditions()))
		{
			if (term.getPlannedQtyPerUnit().signum() <= 0)
			{
				throw new AdempiereException("@" + MSG_TERM_ERROR_PLANNED_QTY_PER_UNIT + "@");
			}
		}
		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement.equals(term.getType_Conditions()))
		{
			// first do a number of checks
			final List<String> missingValues = new ArrayList<String>();

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
		// TODO: refactor it to specific IFlatrateHandlers

		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement.equals(term.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Pauschalengebuehr.equals(term.getType_Conditions()))
		{
			Check.assume(term.getPlannedQtyPerUnit().signum() >= 0, "@" + MSG_TERM_ERROR_PLANNED_QTY_PER_UNIT + "@");
		}

		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement.equals(term.getType_Conditions()))
		{
			if (term.isNewTermCreatesOrder() && term.getC_OrderLine_Term_ID() <= 0)
			{
				Services.get(IFlatrateBL.class).createOrderForTerm(term);
			}
			Services.get(ISubscriptionBL.class).evalCurrentSPs(term, term.getStartDate());
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_Depotgebuehr.equals(term.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Leergutverwaltung.equals(term.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Pauschalengebuehr.equals(term.getType_Conditions()))
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
				});
	}

	/**
	 * When a term is reactivated, it's invoice candidate needs to be deleted. Note that we assume the deletion will fail with a meaningful error message if the invoice candidate has already been
	 * invoiced.
	 *
	 * @param term
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void deleteInvoiceCandidate(final I_C_Flatrate_Term term)
	{
		//
		// Delete invoice candidates
		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);
		for (final I_C_Invoice_Candidate icToDelete : invoiceCandDB.retrieveReferencing(term))
		{
			InterfaceWrapperHelper.delete(icToDelete);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void beforeReactivate(final I_C_Flatrate_Term term)
	{
		Services.get(IFlatrateHandlersService.class)
				.getHandler(term.getType_Conditions())
				.beforeFlatrateTermReactivate(term);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID)
	public void copyFromConditions(final I_C_Flatrate_Term term)
	{
		Services.get(IFlatrateBL.class).updateFromConditions(term);
	}
}
