package de.metas.commission.service;

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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Period;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.PO;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionPayrollLine;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvComDoc;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionPayrollLine;
import de.metas.commission.model.X_C_AdvCommissionFact;

/**
 * Interface with generic methods for operations on {@value I_C_AdvCommissionFact#Table_Name} and {@value IAdvComInstance#Table_Name}.
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public interface ICommissionFactBL extends ISingletonService
{

	/**
	 * 
	 * @param term
	 * @param cand
	 * @param poLine
	 * @param customerSponsor
	 * @param salesRepSponsor
	 * @param hierarchyLevel
	 * @param forecastLevel
	 * @param calculationLevel
	 * @param adPInstanceId
	 * @return
	 */
	Map<IAdvComInstance, MCAdvCommissionFact> createInstanceAndFact(
			I_C_AdvCommissionTerm term,
			I_C_AdvCommissionFactCand cand,
			Object poLine,
			I_C_Sponsor customerSponsor,
			I_C_Sponsor salesRepSponsor,
			int hierarchyLevel, int forecastLevel, int calculationLevel,
			boolean salesVolume,
			int adPInstanceId);

	boolean getIsLocked(PO po, I_C_Sponsor salesRepSponsor, Timestamp date);

	/**
	 * Updates an existing commission instance for an invoice line or order line. Adds two new records for the given poLine to the given instance. The first fact subtracts the "old" amount of points,
	 * the second one adds the new amount. If there is no change, nothing is added. There are two cases to distinguish:
	 * 
	 * <ul>
	 * <li>poLine has already been recorded with the given instance and some commission relevant change has occurred</li>
	 * <li>poLine has not yet been recorded with the given instance. In that case it is assumed that commission points need to be transferred from the instance trigger to the new line (order line to
	 * invoice line)</li>
	 * </ul>
	 * 
	 * @param type
	 * @param cand
	 * @param dateToUse date that is used when computing base commission points and commission percent for 'poLine'.
	 * @param poLine PO to record. Needs to be {@link MInvoiceLine} or {@link MOrderLine}
	 * @param instance
	 * @param adPInstanceId if new records are created, the given ID is recorded in their {@link I_C_AdvCommissionFact#COLUMNNAME_AD_PInstance_ID} column for documentation. If there is no
	 *            AD_PInstance_ID id available, use {@link #NO_AD_PINSTANCE_ID}.
	 */
	void recordInstanceTrigger(ICommissionType type, I_C_AdvCommissionFactCand cand, Timestamp dateToUse, Object poLine, IAdvComInstance instance, int adPInstanceId);

	void recordAllocationLine(ICommissionType type, MCAdvCommissionFactCand cand, MAllocationLine line, int adPInstanceId);

	/**
	 * Collects commission triggers, i.e. commission facts with
	 * <ul>
	 * <li>Status={@link X_C_AdvCommissionFact#STATUS_ZuBerechnen}</li>
	 * <li>Processed='N'</li>
	 * <li>FactType is neither {@link X_C_AdvCommissionFact#FACTTYPE_Provisionsberechnung} nor {@link X_C_AdvCommissionFact#FACTTYPE_ProvisionsberRueckabwicklung}</li>
	 * </ul>
	 * 
	 * <ul>
	 * <li>multiple facts may have the same po and may all be commission triggers, but there will be only one payroll line.</li>
	 * </ul>
	 * 
	 * @param instance
	 * 
	 * @param period if not null, only those facts that have their <code>DateDoc</code> value within this period are evaluated.
	 * @return a list containing facts that are commission triggers
	 */
	List<MCAdvCommissionFact> retrieveTriggers(IAdvComInstance instance, I_C_Period period);

	/**
	 * Updates existing commission instances with a commission payroll line (i.e. one result of a commission calculation)
	 * 
	 * @param cpl
	 * @param payroll
	 * @param adPInstanceId
	 * @param trxName
	 * @return
	 */
	List<MCAdvCommissionFact> recordCommissionPayrollLine(MCAdvCommissionPayrollLine cpl, Timestamp date, int adPInstanceId, String trxName);

	/**
	 * Updates existing commission instances if a commission invoice line (an invoice with SOTrx='N') for a sales rep.
	 * 
	 * @param il
	 * @param invoice
	 * @param factor value to record as amtMultiplier when creating new facts
	 * @param adPInstanceId
	 * @return
	 */
	void recordEmployeeIl(MInvoiceLine il, I_C_Invoice invoice, BigDecimal factor, int adPInstanceId);

	/**
	 * Updates existing commission instances with changed data from a comDoc instance
	 * 
	 * @param comDoc
	 * @param poLine a poLine of whose parent is referenced by the given <code>comDoc</code>
	 * @param inst
	 */
	void recordComDoc(MCAdvCommissionFactCand cand, MCAdvComDoc comDoc, Object poLine, IAdvComInstance inst, int adPInstanceId);

	/**
	 * 
	 * @param typeIsCompressImpl the type whose {@link ICommissionType#isCompress(I_C_Sponsor, Timestamp)} method is called
	 * @param compressionMode the compression mode to apply
	 * @param date
	 * @param instances
	 */
	void updateLevelCalculation(
			ICommissionType typeIsCompressImpl,
			String compressionMode,
			Timestamp date,
			List<IAdvComInstance> instances);

	/**
	 * Method can be used as assert argument
	 * 
	 * @param instances
	 * @return
	 */
	boolean instancesOK(List<IAdvComInstance> instances);

	public void updateLevelCalculation(ICommissionType typeIsCompressImpl, Timestamp date, I_C_Sponsor sponsor, I_C_Period period);

	List<MCAdvCommissionFact> makeCalculationMinusFact(List<MCAdvCommissionFact> triggerFacts, Timestamp dateDoc, int adPInstance, String trxName);

	/**
	 * Unwinds the given <code>commissionCalculationFact</code>
	 * 
	 * @param instance
	 * 
	 * @param commissionCalculationFact
	 * @param commissionTriggerFact
	 * @param cand
	 * @param adPInstanceId
	 * @return
	 */
	MCAdvCommissionFact unwindCommissionCalculation(
			I_C_AdvCommissionFact commissionCalculationFact,
			I_C_AdvCommissionFact commissionTriggerFact,
			I_C_AdvCommissionFactCand cand,
			int adPInstanceId);

	/**
	 * Finds out if the given fact has at least one follow-up fact with <code>FactType=ProvisionsberRueckabwicklung</code>. If that's the case, this method returns true.
	 * 
	 * @param fact
	 * @return
	 */
	boolean isCommissionCalculationFactUnwound(I_C_AdvCommissionFact fact);

	/**
	 * Unwinds the given <code>commissionInvoiceFact</code>
	 * 
	 * @param commissionInvoiceFact the actual fact to unwind
	 * @param commissionCalculationFact the logical predecessor of 'commissionInvoiceFact'. The new plus fact will be similar to this fact
	 * @param cand
	 * @param adPInstanceId
	 * @return
	 */
	MCAdvCommissionFact unwindCommissionInvoice(
			I_C_AdvCommissionFact commissionInvoiceFact,
			I_C_AdvCommissionFact commissionCalculationFact,
			I_C_AdvCommissionFactCand cand,
			int adPInstanceId);

	/**
	 * Finds out if the given fact has at least one follow-up fact with <code>FactType=ProvisionsabrRueckabwicklung</code>. If that's the case, this method returns true.
	 * 
	 * @param fact
	 * @return
	 */
	boolean isCommissionInvoiceFactUnwound(I_C_AdvCommissionFact fact);

	/**
	 * Finds out if the given fact has at least one follow-up fact with <code>FactType=Provisionsabrechnung</code>. If that's the case, this method returns true.
	 * 
	 * @param fact
	 * @return
	 */
	boolean isCommissionCalculationFactInvoiced(I_C_AdvCommissionFact commissionCalculationFact);

	/**
	 * For a given collection of commission fact that reference a {@link I_C_AdvCommissionPayrollLine}, this method returns the FactType that should be used when recording a commission invoice line.
	 * 
	 * @param cplFact
	 * @return
	 */
	String getCplFactType(Collection<I_C_AdvCommissionFact> cplFacts);

	I_C_AdvCommissionFactCand getCause(I_C_AdvCommissionFactCand cand);

	void setCause(I_C_AdvCommissionFactCand cand, I_C_AdvCommissionFactCand cause);
}
