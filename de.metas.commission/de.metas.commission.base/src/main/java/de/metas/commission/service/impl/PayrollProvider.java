package de.metas.commission.service.impl;

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


import static de.metas.commission.model.I_HR_Movement.COLUMNNAME_C_Currency_ID;
import static de.metas.commission.model.I_HR_Movement.COLUMNNAME_C_Tax_ID;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Location;
import org.compiere.model.MBPartner;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRefList;
import org.compiere.model.MTax;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.util.Msg;
import org.eevolution.model.I_HR_Concept;
import org.eevolution.model.I_HR_Employee;
import org.eevolution.model.I_HR_Movement;
import org.eevolution.model.I_HR_Period;
import org.eevolution.model.I_HR_Process;
import org.eevolution.model.MHRMovement;
import org.eevolution.model.X_HR_Movement;
import org.slf4j.Logger;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.interfaces.I_C_BPartner_Location;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionPayrollLine;
import de.metas.commission.model.MCVATSmallBusiness;
import de.metas.commission.model.MHRAllocationLine;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.IBPartnerDAO;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionTermDAO;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.IPayrollProvider;
import de.metas.logging.LogManager;
import de.metas.tax.api.ITaxBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class PayrollProvider implements IPayrollProvider
{
	private static final String MSG_PARTNER_MISSING_COMMISSION_ADDRESS_1P = "BPartnerMissingCommissionAddress";

	private static final Logger logger = LogManager.getLogger(PayrollProvider.class);

	@Override
	public Collection<I_HR_Movement> createMovements(
			final Properties ctx,
			final I_HR_Process process,
			final I_HR_Concept concept,
			final I_HR_Period period,
			final I_HR_Employee employee,
			final String trxName)
	{
		final Collection<I_HR_Movement> result = new ArrayList<I_HR_Movement>();

		final List<I_C_AdvCommissionTerm> terms = Services.get(ICommissionTermDAO.class).retrieveForPayrollConcept(ctx, concept.getHR_Concept_ID(), trxName);

		for (final I_C_AdvCommissionTerm commissionTerm : terms)
		{
			result.addAll(createForTerm(ctx, process, concept, period, employee, trxName, commissionTerm));
		}
		return result;
	}

	private List<I_HR_Movement> createForTerm(
			final Properties ctx,
			final I_HR_Process process,
			final I_HR_Concept concept,
			final I_HR_Period hrPeriod,
			final I_HR_Employee employee,
			final String trxName,
			final I_C_AdvCommissionTerm commissionTerm)
	{
		// TaxId -> ( CurrencyId -> ( FactType -> Movement ) )
		final Map<Integer, Map<Integer, Map<String, MHRMovement>>> movements = new HashMap<Integer, Map<Integer, Map<String, MHRMovement>>>();

		final List<I_HR_Movement> result = new ArrayList<I_HR_Movement>();

		final int bPartnerID = employee.getC_BPartner_ID();

		final List<MCAdvCommissionPayrollLine> cpls = MCAdvCommissionPayrollLine.retrieveFor(commissionTerm, bPartnerID);

		for (final MCAdvCommissionPayrollLine line : cpls)
		{
			if (line.isCommissionLock())
			{
				logger.info("Skipping " + line);
				continue;
			}

			final int taxId = retrieveTaxId(employee, hrPeriod, line);

			final int currencyId = line.getC_Currency_ID();

			Map<Integer, Map<String, MHRMovement>> currencyId2movement = movements.get(taxId);

			if (currencyId2movement == null)
			{
				currencyId2movement = new HashMap<Integer, Map<String, MHRMovement>>();
				movements.put(taxId, currencyId2movement);
			}

			Map<String, MHRMovement> type2movement = currencyId2movement.get(currencyId);
			if (type2movement == null)
			{
				type2movement = new HashMap<String, MHRMovement>();
				currencyId2movement.put(currencyId, type2movement);
			}

			final String factType = Services.get(ICommissionFactBL.class).getCplFactType(MCAdvCommissionFact.retrieveFacts(line, commissionTerm.getC_AdvCommissionTerm_ID()));

			MHRMovement movement = type2movement.get(factType);
			if (movement == null)
			{
				movement = createMovement(ctx, process, employee, concept, hrPeriod, currencyId, taxId, trxName);
				if (X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrKorrektur.equals(factType))
				{
					movement.setDescription(commissionTerm.getName() + " - " + MRefList.getListName(ctx, X_C_AdvCommissionFact.FACTTYPE_AD_Reference_ID, factType));
				}
				else
				{
					movement.setDescription(commissionTerm.getName());
				}
				type2movement.put(factType, movement);
				result.add(movement);
			}

			final BigDecimal lineAmount = line.getCommissionAmt();

			final BigDecimal value = movement.getAmount();
			if (value == null)
			{
				movement.setAmount(lineAmount);
			}
			else
			{
				movement.setAmount(value.add(lineAmount));
			}

			movement.saveEx();

			final MHRAllocationLine movementAlloc = new MHRAllocationLine(ctx, 0, trxName);
			movementAlloc.setAD_Table_ID(line.get_Table_ID());
			movementAlloc.setRecord_ID(line.get_ID());
			movementAlloc.setHR_Movement_ID(movement.get_ID());
			movementAlloc.setAmount(lineAmount);
			movementAlloc.setDateDoc(line.getC_AdvCommissionPayroll().getDateCalculated());
			movementAlloc.saveEx();

			line.setProcessed(true);
			line.saveEx();
		}
		return result;
	}

	private MHRMovement createMovement(final Properties ctx,
			final I_HR_Process process,
			final I_HR_Employee employee,
			final I_HR_Concept concept,
			final I_HR_Period period,
			final int currencyId,
			final int taxId,
			final String trxName)
	{
		final MHRMovement movement = new MHRMovement(ctx, 0, trxName);

		movement.setC_BPartner_ID(employee.getC_BPartner_ID());
		movement.setHR_Concept_ID(concept.getHR_Concept_ID());
		movement.setHR_Concept_Category_ID(concept.getHR_Concept_Category_ID());
		movement.setHR_Process_ID(process.getHR_Process_ID());
		movement.setHR_Department_ID(employee.getHR_Department_ID());
		movement.setHR_Job_ID(employee.getHR_Job_ID());
		movement.setColumnType(X_HR_Movement.COLUMNTYPE_Amount);

		movement.setValidFrom(period.getStartDate());
		movement.setValidTo(period.getEndDate());
		movement.setIsPrinted(false);
		movement.setIsRegistered(concept.isRegistered());
		movement.setC_Activity_ID(employee.getC_Activity_ID());

		movement.set_ValueOfColumn(COLUMNNAME_C_Currency_ID, currencyId);
		movement.set_ValueOfColumn(COLUMNNAME_C_Tax_ID, taxId);

		return movement;
	}

	// TODO use the shipping date and -origin to get the taxId. Currently we use
	// the commission instance trigger's (ol or il)
	@Override
	public int retrieveTaxId(final I_HR_Employee employee, final I_HR_Period period, final MCAdvCommissionPayrollLine line)
	{
		//
		// get the stuff we will need in any case
		final MBPartner bpPayroll = (MBPartner)employee.getC_BPartner();

		final Timestamp date = period.getDateAcct();

		final IBPartnerDAO bPartnerBl = Services.get(IBPartnerDAO.class);

		Properties ctx = bpPayroll.getCtx();

		final I_C_BPartner_Location payrollLocPO = bPartnerBl.retrieveCommissionToLocation(ctx, bpPayroll.get_ID(), bpPayroll.get_TrxName());

		if (payrollLocPO == null)
		{
			throw CommissionException.inconsistentConfig(
					Msg.getMsg(ctx, MSG_PARTNER_MISSING_COMMISSION_ADDRESS_1P, new Object[] { bpPayroll.getValue() }),
					bpPayroll);
		}
		final I_C_BPartner_Location payrollLoc = InterfaceWrapperHelper.create(payrollLocPO, I_C_BPartner_Location.class);

		final int taxId;

		if (MCVATSmallBusiness.retrieveIsTaxExempt(InterfaceWrapperHelper.create(bpPayroll, I_C_BPartner.class), date))
		{
			// "Kleinunternehmer-Regel"
			taxId = Services.get(ITaxBL.class).getExemptTax(ctx, bpPayroll.getAD_Org_ID());

			assert taxId > 0;
		}
		else
		{
			//
			// see if the commission is generated directly from the actual
			// instance of if the get commission because someone else down the
			// hierarchy got some. In other words: see if there is a
			// "first line"

			final IAdvComInstance instance = InterfaceWrapperHelper.create(line.getC_AdvCommissionInstance(), IAdvComInstance.class);

			final PO instanceTriggerPO = InterfaceWrapperHelper.getPO(Services.get(ICommissionInstanceDAO.class).retrievePO(instance, Object.class));

			final IAdvComInstance instFirstLine = Services.get(ICommissionInstanceDAO.class).retriveFirstLine(instance);

			final IFieldAccessBL faService = Services.get(IFieldAccessBL.class);
			final int triggerTaxId = faService.getTaxId(instanceTriggerPO);

			final Properties instCtx = InterfaceWrapperHelper.getCtx(instance);
			final String instTrxName = InterfaceWrapperHelper.getTrxName(instance);
			
			int triggerTaxCategoryId = MTax.get(instCtx, triggerTaxId).getC_TaxCategory_ID();

			// metas: q&d Fix fuer Steuer 19%
			triggerTaxCategoryId = 1000001;
			// metas end

			final ITaxBL taxBL = Services.get(ITaxBL.class);

			final int orgId = instanceTriggerPO.getAD_Org_ID();

			if (instFirstLine != null)
			{
				// compute tax for locations (instFirstLine -> instance)

				// TODO: C_Tax doesn't contain the data required to decide
				// whether to use the country of bpFirstLine or that of
				// bpPayroll. Until now, the country of bpPayroll happens to be
				// the right one.

				// final MCSponsor sponsorFirstLine = (MCSponsor) instFirstLine
				// .getC_Sponsor_SalesRep();
				// final MBPartner bpFirstLine = sponsorFirstLine
				// .retrieveSalesRepAt(instFirstLine.getDateDoc(), true);

				final int firstLineCountryId = getCommissionCountryId(bpPayroll);

				taxId = taxBL.retrieveTaxIdForCategory(
						instCtx,
						firstLineCountryId,
						orgId,
						payrollLoc,
						date,
						triggerTaxCategoryId,
						false,
						instTrxName, false);

				Check.errorUnless(taxId > 0, 
						"Missing C_Tax_ID for firstLine C_Country_ID={}, AD_Org_ID={}, C_BPartner_Location_ID={}, Date={} and trigger C_TaxCategory_ID={}", 
						firstLineCountryId, orgId, payrollLoc.getC_BPartner_Location_ID(), date);
			}
			else
			{
				// compute tax for locations (shipTo -> instance);
				final int triggerCountryId;

				if (instanceTriggerPO instanceof MOrderLine)
				{
					final IFieldAccessBL faService1 = Services.get(IFieldAccessBL.class);
					final int triggerWarehouseId = faService1.getWarehouseId(instanceTriggerPO);

					triggerCountryId = MWarehouse.get(instCtx, triggerWarehouseId).getC_Location().getC_Country_ID();
				}
				else if (instanceTriggerPO instanceof MInvoiceLine)
				{
					final I_C_Location orgLocation = Services.get(IBPartnerOrgBL.class).retrieveOrgLocation(instCtx, orgId, line.get_TrxName());
					triggerCountryId = orgLocation.getC_Country_ID();
				}
				else
				{
					// TODO -> AD_Message
					throw CommissionException
							.inconsistentData(
									"Only InvoiceLines and OrderLines can be instance triggers",
									instanceTriggerPO);
				}

				taxId = taxBL.retrieveTaxIdForCategory(
						instCtx,
						triggerCountryId,
						orgId,
						payrollLoc,
						date,
						triggerTaxCategoryId,
						false,
						instTrxName, false);

				Check.errorUnless(taxId > 0, 
						"Missing C_Tax_ID for trigger C_Country_ID={}, AD_Org_ID={}, C_BPartner_Location_ID={}, Date={} and trigger C_TaxCategory_ID={}", 
						triggerCountryId, orgId, payrollLoc.getC_BPartner_Location_ID(), date);
			}
		}
		return taxId;
	}

	private int getCommissionCountryId(final MBPartner bPartner)
	{
		final IBPartnerDAO bPartnerBl = Services.get(IBPartnerDAO.class);

		final I_C_BPartner_Location commissiontToLoc =
				bPartnerBl.retrieveCommissionToLocation(
						bPartner.getCtx(),
						bPartner.get_ID(),
						bPartner.get_TrxName());

		if (commissiontToLoc == null)
		{
			// TODO -> AD_Message
			throw CommissionException.inconsistentConfig("Geschaeftspartner "
					+ bPartner.getValue() + " hat keine Provisions-Adresse",
					bPartner);
		}
		return commissiontToLoc.getC_Location().getC_Country_ID();
	}
}
