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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Period;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.MHRMovement;

import de.metas.adempiere.model.IProductAware;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.custom.type.ISalesRefFactCollector;
import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionPayrollLine;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_CondLine;
import de.metas.commission.model.I_HR_Movement;
import de.metas.commission.model.MCAdvComDoc;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionPayrollLine;
import de.metas.commission.model.MCIncidentLine;
import de.metas.commission.model.MHRAllocationLine;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvComSystem_Type;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionLockDAO;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.SuccessiveFactsFinder;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class CommissionFactBL implements ICommissionFactBL
{
	private static final Logger logger = LogManager.getLogger(CommissionFactBL.class);

	@Override
	public Map<IAdvComInstance, MCAdvCommissionFact> createInstanceAndFact(
			final I_C_AdvCommissionTerm term,
			final I_C_AdvCommissionFactCand cand,
			final Object poLine,
			final I_C_Sponsor customerSponsor,
			final I_C_Sponsor salesRepSponsor,
			final int hierarchyLevel,
			final int forecastLevel,
			final int calculationLevel,
			final boolean salesVolume,
			final int adPInstanceId)
	{
		Check.assume(term != null, "Param 'term' is not null");

		final IAdvComInstance instance =
				addNewInstance(
						term, cand, poLine, customerSponsor, salesRepSponsor, hierarchyLevel,
						forecastLevel, calculationLevel, salesVolume, adPInstanceId);

		CommissionFactBL.logger.info("Created " + instance);

		final I_C_AdvComSystem_Type comSystemType = term.getC_AdvComSystem_Type();

		final ICommissionType type = Services.get(ICommissionTypeBL.class).getBusinessLogic(comSystemType.getC_AdvCommissionType(), comSystemType);

		final MCAdvCommissionFact fact = addNewFact(type, cand, poLine, instance);
		fact.setAD_PInstance_ID(adPInstanceId);
		fact.saveEx();

		CommissionFactBL.logger.info("Created " + fact);

		return Collections.singletonMap(instance, fact);
	}

	/**
	 * Created a new instance and saves it.
	 * 
	 * @param termId
	 * @param cand Note: the new fact is created with <code>DateDoc:=cand.retrieveDateAcctOfPO()</code>
	 * @param po
	 * @param customerSponsor
	 * @param salesRepSponsor the sales rep that is going to receive the commission
	 * @param level
	 * @return
	 */
	private IAdvComInstance addNewInstance(
			final I_C_AdvCommissionTerm term,
			final I_C_AdvCommissionFactCand cand,
			final Object poLine,
			final I_C_Sponsor customerSponsor,
			final I_C_Sponsor salesRepSponsor,
			final int hierarchyLevel,
			final int forecastLevel,
			final int calculationLevel,
			final boolean salesVolume,
			final int adPInstanceId)
	{
		final IAdvComInstance instance = Services.get(ICommissionInstanceDAO.class).createNew(poLine);

		instance.setAD_PInstance_ID(adPInstanceId);
		instance.setName(salesRepSponsor.getSponsorNo() + "_" + customerSponsor.getSponsorNo());

		instance.setC_AdvComSystem_Type_ID(term.getC_AdvComSystem_Type_ID());
		instance.setC_AdvCommissionTerm_ID(term.getC_AdvCommissionTerm_ID());
		instance.setC_AdvCommissionRelevantPO_ID(cand.getC_AdvCommissionRelevantPO_ID());
		instance.setC_Sponsor_SalesRep_ID(salesRepSponsor.getC_Sponsor_ID());
		instance.setC_Sponsor_Customer_ID(customerSponsor.getC_Sponsor_ID());

		final MCAdvCommissionFactCand candPO = (MCAdvCommissionFactCand)InterfaceWrapperHelper.getPO(cand); // TODO: replace with DAO/BL service methods

		// upon creation of a new instance and fact, make sure that the PO's
		// dateDoc is used, not the candidate's dateAcct
		final Timestamp dateDoc = Services.get(ICommissionFactCandBL.class).retrieveDateDocOfReferencedPO(candPO);
		instance.setDateDoc(dateDoc);

		instance.setLevelHierarchy(hierarchyLevel);
		instance.setLevelForecast(forecastLevel);
		instance.setLevelCalculation(calculationLevel);
		instance.setIsVolumeOfSales(salesVolume);

		final MCIncidentLine incidentLine = MCIncidentLine.retrieve(poLine);
		instance.setC_IncidentLine_ID(incidentLine.get_ID());

		final boolean isLocked = getIsLocked(Services.get(ICommissionFactCandBL.class).retrievePO(candPO), salesRepSponsor, dateDoc);
		instance.setIsCommissionLock(isLocked);
		InterfaceWrapperHelper.save(instance);

		return instance;
	}

	/**
	 * Creates a single new fact from the given candidate. It is assumes that the candidates PO is an MOrder or an MInvoice.
	 * 
	 * @param type may be null. Otherwise the new fact's percent and factor are set from the term
	 * @param cand Note: the new fact is created with <code>DateDoc:=cand.retrieveDateAcctOfPO()</code>
	 * @param poLine
	 * @param instance the new fact's <code>I_C_AdvCommissionInstance</code>
	 * @param status the new fact's status
	 * @return
	 */
	private MCAdvCommissionFact addNewFact(
			final ICommissionType type,
			final I_C_AdvCommissionFactCand cand,
			final Object poLine,
			final IAdvComInstance instance)
	{
		final PO poToProcess = Services.get(ICommissionFactCandBL.class).retrievePO(cand);

		Check.assume(poToProcess instanceof MOrder || poToProcess instanceof MInvoice,
				"cand.retrievePO() returns a MOrder or MInvoice; cand.retrievePO()=" + poToProcess);
		Check.assume(!cand.isInfo(), cand + " has Info='Y'");

		// we use the sales rep status "forecast", so the commission might be
		// more "optimistic" than what is actually calculated later on.
		final String salesRepFactStatus = X_C_AdvComSalesRepFact.STATUS_Prognose;

		final BigDecimal commissionPointsSum = type.getCommissionPointsSum(
				instance,
				salesRepFactStatus,
				cand.getDateAcct(),
				poLine);

		final MCAdvCommissionFact newFact = MCAdvCommissionFact.createNew(poLine, cand);

		newFact.setCommissionPointsSum(commissionPointsSum);

		final BigDecimal commission = type.getPercent(instance, salesRepFactStatus, cand.getDateAcct());
		newFact.setCommission(commission.multiply(Env.ONEHUNDRED));

		final BigDecimal commissionPoints = commissionPointsSum.multiply(commission);
		newFact.setCommissionPoints(commissionPoints);

		final IFieldAccessBL faService = Services.get(IFieldAccessBL.class);
		final BigDecimal qty = faService.getQty(poLine);
		Check.assume(qty != null, "faService.getQty(poLine) does not return null for poLine=" + poLine);
		newFact.setQty(qty);

		final BigDecimal commissionPointsBase = qty.signum() == 0 ? BigDecimal.ZERO : commissionPointsSum.divide(qty, RoundingMode.HALF_UP);
		newFact.setCommissionPointsBase(commissionPointsBase);

		// with status = X_C_AdvComSalesRepFact.STATUS_Prognose, there is no point in setting amtMultiplier.
		// newFact.setAmtMultiplier(type.getFactor());

		newFact.setC_AdvCommissionInstance_ID(instance.getC_AdvCommissionInstance_ID());
		newFact.setStatus(X_C_AdvCommissionFact.STATUS_Prognostiziert);
		newFact.setSeqNo(cand.getSeqNo());

		newFact.setCommissionAmtBase(faService.getLineNetAmtOrNull(poLine));

		return newFact;
	}

	@Override
	public boolean getIsLocked(final PO po, final I_C_Sponsor salesRepSponsor, final Timestamp date)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(salesRepSponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(salesRepSponsor);

		final I_C_BPartner salesRep = sponsorBL.retrieveSalesRepAt(ctx, date, salesRepSponsor, false, trxName);

		if (salesRep == null)
		{
			return false;
		}

		final boolean salesRepLocked = Services.get(ICommissionLockDAO.class).isLocked(salesRep, date);

		final boolean isLocked;

		if (salesRepLocked)
		{
			isLocked = salesRepLocked;
		}
		else
		{
			if (po.get_ColumnIndex(I_C_Order.IS_COMMISSION_LOCK) >= 0)
			{
				isLocked = po.get_ValueAsBoolean(I_C_Order.IS_COMMISSION_LOCK);
			}
			else
			{
				isLocked = false;
			}
		}
		return isLocked;
	}

	/**
	 * 
	 */
	@Override
	public void recordInstanceTrigger(
			final ICommissionType type,
			final I_C_AdvCommissionFactCand cand,
			final Timestamp dateToUse,
			final Object plusPoLine,
			final IAdvComInstance instance,
			final int adPInstanceId)
	{
		new CommissionFactRecordInstanceTrigger(this)
				.recordInstanceTrigger(type, cand, dateToUse, plusPoLine, instance, adPInstanceId);
	}

	@Override
	public void updateLevelCalculation(
			final ICommissionType typeIsCompressImpl,
			final String compressionMode, final Timestamp date,
			final List<IAdvComInstance> instances)
	{
		if (instances.isEmpty())
		{
			return;
		}

		Check.assume(instancesOK(instances), "instancesOK() returns true; date=" + date + "; type=" + typeIsCompressImpl + "\ninstaces=\n" + instances);

		if (X_C_AdvComSystem_Type.DYNAMICCOMPRESSION_NachObenVerschShift.equals(compressionMode))
		{
			final List<CommissionInstanceBean> beans = createBeans(instances);

			updateLevelsCompressionShift(date, typeIsCompressImpl, beans);
			updateFromBeans(instances, beans);
		}
		else if (X_C_AdvComSystem_Type.DYNAMICCOMPRESSION_VerfallenLassen.equals(compressionMode))
		{
			updateLevelsCompressionDiscard(date, typeIsCompressImpl, instances);
		}
		else if (X_C_AdvComSystem_Type.DYNAMICCOMPRESSION_Keine.equals(compressionMode))
		{
			updateLevelsCompressionOff(instances);
		}
		else
		{
			throw CommissionException.inconsistentConfig(
					"Unknown dynamic compression mode '" + compressionMode + "'", typeIsCompressImpl);
		}

		Check.assume(instancesOK(instances), "instancesOk() returns true; date=" + date + "; term=" + typeIsCompressImpl + "\ninstaces=\n" + instances);
	}

	private void updateLevelsCompressionDiscard(
			final Timestamp dateAcct,
			final ICommissionType typeIsCompressImpl,
			final List<IAdvComInstance> instances)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		for (int i = 0; i < instances.size(); i++)
		{
			final IAdvComInstance instanceToCheck = instances.get(i);

			final I_C_Sponsor sponsorSalesRep = instanceToCheck.getC_Sponsor_SalesRep();
			final ISalesRefFactCollector srfCollector = sponsorBL.retrieveSalesRepFactCollector(
					InterfaceWrapperHelper.getCtx(sponsorSalesRep),
					sponsorSalesRep, dateAcct,
					InterfaceWrapperHelper.getTrxName(sponsorSalesRep));

			final IProductAware productAware = Services.get(ICommissionInstanceDAO.class).retrievePO(instanceToCheck, IProductAware.class);
			Check.assumeNotNull(productAware, "productAware with AD_Table_ID=" + instanceToCheck.getAD_Table_ID() + " and Record_ID=" + instanceToCheck.getRecord_ID() + " not null");

			final org.compiere.model.I_M_Product product = productAware.getM_Product();

			final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsorSalesRep, dateAcct, typeIsCompressImpl,
					srfCollector.getComSystemType().getC_AdvComSystem(), product);

			if (srfCollector.isCompress(commissionCtx))
			{
				updateLevelCalc(instanceToCheck, -1);
			}
			else
			{
				updateLevelCalc(instanceToCheck, instanceToCheck.getLevelForecast());
			}
		}
	}

	private void updateLevelsCompressionOff(final List<IAdvComInstance> instances)
	{
		for (int i = 0; i < instances.size(); i++)
		{
			final IAdvComInstance instance = instances.get(i);
			updateLevelCalc(instance, instance.getLevelForecast());
		}
	}

	private void updateLevelCalc(final IAdvComInstance instance, final int levelCalculation)
	{
		if (instance.getLevelCalculation() != levelCalculation)
		{
			instance.setLevelCalculation(levelCalculation);
			InterfaceWrapperHelper.save(instance);
		}
	}

	private List<CommissionInstanceBean> createBeans(final List<IAdvComInstance> instances)
	{
		final List<CommissionInstanceBean> beans = new ArrayList<CommissionInstanceBean>();

		for (final IAdvComInstance inst : instances)
		{
			final CommissionInstanceBean newBean = new CommissionInstanceBean();

			newBean.setCommissionInstanceId(inst.getC_AdvCommissionInstance_ID());
			newBean.setLevelForecast(inst.getLevelForecast());
			newBean.setLevelCalc(inst.getLevelCalculation());
			newBean.setSponsorSalesRep(inst.getC_Sponsor_SalesRep());

			beans.add(newBean);
		}
		return beans;
	}

	private void updateFromBeans(final List<IAdvComInstance> instances, final List<CommissionInstanceBean> beans)
	{

		assert beans.size() == instances.size();

		for (int i = 0; i < beans.size(); i++)
		{

			final CommissionInstanceBean bean = beans.get(i);
			final IAdvComInstance inst = instances.get(i);

			assert bean.getCommissionInstanceId() == inst.getC_AdvCommissionInstance_ID();

			if (inst.getLevelCalculation() != bean.getLevelCalc())
			{

				inst.setLevelCalculation(bean.getLevelCalc());
				InterfaceWrapperHelper.save(inst);
			}
		}
	}

	/**
	 * Very simple class to be used inside {@link CommissionFactBL#updateLevelsCompressionShift(ICommissionType, MCAdvCommissionFactCand, List)} .
	 * 
	 * Note: We use this class because we didn't come up with another way to make updateLevelsCompressionShift testable.
	 * 
	 * @author ts
	 * 
	 */
	static class CommissionInstanceBean
	{

		private int commissionInstanceId;

		public int getCommissionInstanceId()
		{
			return commissionInstanceId;
		}

		public void setCommissionInstanceId(final int commissionInstanceId)
		{
			this.commissionInstanceId = commissionInstanceId;
		}

		private int level;
		private int levelCalc;

		private I_C_Sponsor sponsorSalesRep;

		public int getLevelForecast()
		{
			return level;
		}

		public void setLevelForecast(final int levelHierarchy)
		{
			level = levelHierarchy;
		}

		public int getLevelCalc()
		{
			return levelCalc;
		}

		public void setLevelCalc(final int levelCalc)
		{
			this.levelCalc = levelCalc;
		}

		public I_C_Sponsor getSponsorSalesRep()
		{
			return sponsorSalesRep;
		}

		public void setSponsorSalesRep(final I_C_Sponsor sponsorSalesRep)
		{
			this.sponsorSalesRep = sponsorSalesRep;
		}

		@Override
		public String toString()
		{
			final StringBuilder sb = new StringBuilder();
			sb.append("id=").append(commissionInstanceId);
			sb.append("; level=").append(level);
			sb.append("; levelCommission=").append(levelCalc);

			return sb.toString();
		}
	}

	void updateLevelsCompressionShift(
			final Timestamp dateAcct,
			final ICommissionType typeIsCompressImpl,
			final List<CommissionInstanceBean> instances)
	{

		final int firstLevel = instances.get(0).getLevelForecast();

		for (final CommissionInstanceBean instance : instances)
		{
			instance.setLevelCalc(instance.getLevelForecast());
		}

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		for (int i = 0; i < instances.size(); i++)
		{

			final CommissionInstanceBean instanceToCheck = instances.get(i);

			//
			// find out if the instance's commission level needs to be changed
			final I_C_Sponsor sponsorSalesRep = instanceToCheck.getSponsorSalesRep();

			final int newLevelCalc;

			final ISalesRefFactCollector srfCollector = sponsorBL.retrieveSalesRepFactCollector(
					InterfaceWrapperHelper.getCtx(sponsorSalesRep),
					sponsorSalesRep, dateAcct,
					InterfaceWrapperHelper.getTrxName(sponsorSalesRep));

			final ICommissionContext commissionCtx =
					Services.get(ICommissionContextFactory.class).create(sponsorSalesRep, dateAcct, typeIsCompressImpl, srfCollector.getComSystemType().getC_AdvComSystem(), null);

			if (srfCollector.isCompress(commissionCtx))
			{
				// set the level to -1 to indicate that this instance has been
				// compressed
				newLevelCalc = -1;
			}
			else
			{

				if (instanceToCheck.getLevelForecast() == firstLevel)
				{

					newLevelCalc = firstLevel;
				}
				else
				{
					newLevelCalc = instanceToCheck.getLevelCalc();
				}
			}

			instanceToCheck.setLevelCalc(newLevelCalc);

			for (int j = i + 1; j < instances.size(); j++)
			{

				final CommissionInstanceBean instance = instances.get(j);

				if (newLevelCalc == -1)
				{

					if (instance.getLevelCalc() != -1)
					{
						// decrease current level by one
						instance.setLevelCalc(instance.getLevelCalc() - 1);
					}
				}
				else
				{

					final CommissionInstanceBean lastBean = instances
							.get(j - 1);
					final int levelDiff = instance.getLevelForecast()
							- lastBean.getLevelForecast();

					instance.setLevelCalc(lastBean.getLevelCalc() + levelDiff);
				}
			}
		}
	}

	/**
	 * Checks if
	 * <ul>
	 * <li>the first instance in the list has level=1</li>
	 * <li>The instances are ordered by their level values</li>
	 * </ul>
	 * 
	 * @param instances
	 * @return
	 */
	@Override
	public boolean instancesOK(final List<IAdvComInstance> instances)
	{
		if (instances.isEmpty())
		{
			return true;
		}

		int levelForecast = -1;
		int levelHierarchy = -1;
		for (final IAdvComInstance instance : instances)
		{

			if (instance.getLevelForecast() < levelForecast && instance.getC_AdvCommissionInstance_ID() >= 10000)
			{
				// note: forecast levels of different instances may be equal
				return false;
			}
			if (instance.getLevelHierarchy() <= levelHierarchy && instance.getC_AdvCommissionInstance_ID() >= 10000)
			{
				return false;
			}
			levelForecast = instance.getLevelForecast();
			levelHierarchy = instance.getLevelHierarchy();
		}
		return true;
	}

	/**
	 * 
	 * @param type
	 * @param dateDoc
	 * @param poLine
	 * @param instance
	 * @param commissionPointsBase
	 * @param qty
	 * @param commissionPointsSum
	 * @param commission
	 * @param commissionPoints
	 * @param factType
	 * @param changeReason
	 * @param adPInstanceId
	 * @return
	 */
	MCAdvCommissionFact createFact(final ICommissionType type,
			final I_C_AdvCommissionFactCand cand,
			final Object poLine,
			final IAdvComInstance instance,
			final BigDecimal commissionPointsBase, final BigDecimal qty,
			final BigDecimal commissionPointsSum, final BigDecimal commission,
			final BigDecimal commissionPoints, final String factType,
			final String changeReason, final int adPInstanceId)
	{
		final MCAdvCommissionFact newFact = MCAdvCommissionFact.createNew(poLine, cand);
		newFact.setAD_PInstance_ID(adPInstanceId);
		newFact.setFactType(factType);
		newFact.setNote(changeReason);

		newFact.setCommissionPointsBase(commissionPointsBase);
		newFact.setQty(qty);
		newFact.setCommissionPointsSum(commissionPointsSum);
		newFact.setCommission(commission);
		newFact.setCommissionPoints(commissionPoints);

		newFact.setC_AdvCommissionInstance_ID(instance.getC_AdvCommissionInstance_ID());

		final IFieldAccessBL faService = Services.get(IFieldAccessBL.class);
		newFact.setCommissionAmtBase(faService.getLineNetAmtOrNull(poLine));

		return newFact;
	}

	private BigDecimal getQtySum(final List<MCAdvCommissionFact> facts)
	{

		BigDecimal qtySum = BigDecimal.ZERO;

		for (final MCAdvCommissionFact fact : facts)
		{

			final BigDecimal qty = fact.getQty();
			if (qty != null)
			{
				qtySum = qtySum.add(qty);
			}

		}
		return qtySum;
	}

	/**
	 * Creates two new facts to document the given cpl.
	 * 
	 * @param cpl
	 * @param cp
	 * @return
	 */
	@Override
	public List<MCAdvCommissionFact> recordCommissionPayrollLine(
			final MCAdvCommissionPayrollLine cpl,
			final Timestamp date,
			final int adPInstanceId,
			final String trxName)
	{
		final IAdvComInstance instance = InterfaceWrapperHelper.create(cpl.getC_AdvCommissionInstance(), IAdvComInstance.class);

		final List<MCAdvCommissionFact> triggerFacts =
				MCAdvCommissionFact.mkQuery(cpl.getCtx(), trxName)
						.setAdvCommissionPayrollLineId(cpl.get_ID())
						.list();

		Check.assume(!triggerFacts.isEmpty(), cpl + " has at least one trigger fact");

		final List<MCAdvCommissionFact> minusFacts = makeCalculationMinusFact(triggerFacts, date, adPInstanceId, trxName);
		for (final MCAdvCommissionFact minusFact : minusFacts)
		{
			minusFact.setCommissionAmtBase(cpl.getCommissionAmtBase());
		}
		// Create a calc fact to add the cpl's points to the "calculated"
		// balance.
		final BigDecimal commissionAmt = cpl.getCommissionAmt();
		final BigDecimal commissionPoints = cpl.getCommissionPoints();

		final MCAdvCommissionFact plusFact = MCAdvCommissionFact.createNew(cpl, null, trxName);
		plusFact.setDateDoc(date);
		plusFact.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet);
		plusFact.setAD_PInstance_ID(adPInstanceId);
		plusFact.setC_AdvCommissionInstance_ID(instance.getC_AdvCommissionInstance_ID());

		plusFact.setCommissionPointsBase(cpl.getCommissionPointsBase());
		plusFact.setQty(cpl.getQty());
		plusFact.setCommissionPointsSum(cpl.getCommissionPointsSum());
		plusFact.setCommission(cpl.getCommission());
		plusFact.setCommissionPoints(commissionPoints);
		plusFact.setAmtMultiplier(cpl.getAmtMultiplier());
		plusFact.setCommissionAmt(commissionAmt);
		plusFact.setC_Currency_ID(cpl.getC_Currency_ID());

		plusFact.setDateFact(date);

		plusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_Provisionsberechnung);

		plusFact.setCommissionAmtBase(cpl.getCommissionAmtBase());

		plusFact.saveEx();

		for (final MCAdvCommissionFact minusFact : minusFacts)
		{
			minusFact.addFollowUpInfo(plusFact);
			minusFact.saveEx();
		}

		final List<MCAdvCommissionFact> result = new ArrayList<MCAdvCommissionFact>();
		result.addAll(minusFacts);
		result.add(plusFact);
		return result;
	}

	@Override
	public void recordEmployeeIl(
			final MInvoiceLine il,
			final I_C_Invoice invoice,
			final BigDecimal factor,
			final int adPInstanceId)
	{
		final MHRMovement payrollMovement = retrieveMovement(il);

		final Map<Integer, List<MCAdvCommissionPayrollLine>> inst2cpl = retrieveInstanceId2Cpls(payrollMovement);

		final int currencyId = payrollMovement.get_ValueAsInt(I_HR_Movement.COLUMNNAME_C_Currency_ID);

		final Timestamp dateInvoiced = il.getParent().getDateInvoiced();

		// add a new calculation and a new 'toPay' fact to each instance.
		// transfer the cpl's commission from STATUS_Berechnet to STATUS_Auszuzahlen
		for (final int instanceId : inst2cpl.keySet())
		{
			final List<MCAdvCommissionFact> factsForIl =
					MCAdvCommissionFact.mkQuery(il.getCtx(), il.get_TrxName())
							.setAdvCommissionInstanceId(instanceId)
							.setTableId(il.get_Table_ID())
							.setRecordId(il.get_ID())
							.setCounterRecord(false)
							.list();

			if (!factsForIl.isEmpty())
			{
				continue;
			}

			final List<MCAdvCommissionFact> newCalcFacts = new ArrayList<MCAdvCommissionFact>();

			BigDecimal payCommissionPoints = BigDecimal.ZERO;
			BigDecimal payCommissionAmt = BigDecimal.ZERO;

			final List<I_C_AdvCommissionFact> allCplFacts = new ArrayList<I_C_AdvCommissionFact>();

			final List<MCAdvCommissionPayrollLine> cpls = inst2cpl.get(instanceId);
			for (final MCAdvCommissionPayrollLine cpl : cpls)
			{
				final List<I_C_AdvCommissionFact> cplFacts = MCAdvCommissionFact.retrieveFacts(cpl, 0);
				allCplFacts.addAll(cplFacts);

				//
				// sum up amounts and find the new factType
				for (final I_C_AdvCommissionFact cplFact : cplFacts)
				{
					if (cplFact.isCounterEntry()
							&& X_C_AdvCommissionFact.FACTTYPE_ProvisionsberKorrektur.equals(cplFact.getFactType()))
					{
						continue;
					}

					payCommissionPoints = payCommissionPoints.add(cplFact.getCommissionPoints());
					payCommissionAmt = payCommissionAmt.add(cplFact.getCommissionAmt());
				}

				final MCAdvCommissionFact newCalcMinusFact = MCAdvCommissionFact.createNew(cpl, null);

				newCalcMinusFact.setAD_PInstance_ID(adPInstanceId);
				newCalcMinusFact.setC_AdvCommissionInstance_ID(instanceId);

				final String factType = getCplFactType(cplFacts);
				if (X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrKorrektur.equals(factType))
				{
					newCalcMinusFact.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen);
				}
				else
				{
					newCalcMinusFact.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet);
				}
				newCalcMinusFact.setFactType(factType);

				newCalcMinusFact.setIsCounterEntry(true);

				newCalcMinusFact.setCommissionPoints(payCommissionPoints.negate());
				newCalcMinusFact.setAmtMultiplier(factor);

				newCalcMinusFact.setCommissionAmt(payCommissionAmt.negate());
				newCalcMinusFact.setC_Currency_ID(currencyId);

				newCalcMinusFact.setDateDoc(cpl.getC_AdvCommissionPayroll().getDateCalculated());
				newCalcMinusFact.setDateFact(dateInvoiced);

				newCalcMinusFact.saveEx();
				CommissionFactBL.logger.debug("Created " + newCalcMinusFact);
				newCalcFacts.add(newCalcMinusFact);

				for (final I_C_AdvCommissionFact cplFact : cplFacts)
				{
					if (cplFact.isCounterEntry()
							&& X_C_AdvCommissionFact.FACTTYPE_ProvisionsberKorrektur.equals(cplFact.getFactType()))
					{
						continue;
					}

					cplFact.setProcessed(true);
					MCAdvCommissionFact.addFollowUpInfo(cplFact, newCalcMinusFact);
					InterfaceWrapperHelper.save(cplFact);
				}
			}

			final MCAdvCommissionFact newPayableFact = MCAdvCommissionFact.createNew(il, null);

			newPayableFact.setAD_PInstance_ID(adPInstanceId);
			newPayableFact.setC_AdvCommissionInstance_ID(instanceId);
			newPayableFact.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen);
			newPayableFact.setIsCounterEntry(false);

			newPayableFact.setCommissionPoints(payCommissionPoints);
			newPayableFact.setAmtMultiplier(factor);
			newPayableFact.setCommissionAmt(payCommissionAmt);
			newPayableFact.setC_Currency_ID(currencyId);
			newPayableFact.setFactType(getCplFactType(allCplFacts));

			newPayableFact.setDateDoc(dateInvoiced);
			newPayableFact.setDateFact(dateInvoiced);

			newPayableFact.saveEx();

			for (final MCAdvCommissionFact newCalcFact : newCalcFacts)
			{
				newCalcFact.addFollowUpInfo(newPayableFact);
				newCalcFact.saveEx();
			}
			CommissionFactBL.logger.debug("Created " + newPayableFact);

		}
	}

	@Override
	public String getCplFactType(final Collection<I_C_AdvCommissionFact> cplFacts)
	{
		String result = null;

		for (final I_C_AdvCommissionFact cplFact : cplFacts)
		{
			Check.assume(cplFact.getAD_Table_ID() == I_C_AdvCommissionPayrollLine.Table_ID, cplFact + " references a C_AdvCommissionPayrollLine record");

			final String cplFactType;

			if (!cplFact.isCounterEntry()
					&& X_C_AdvCommissionFact.STATUS_Berechnet.equals(cplFact.getStatus())
					&& X_C_AdvCommissionFact.FACTTYPE_ProvisionsberKorrektur.equals(cplFact.getFactType()))
			{
				cplFactType = X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrKorrektur;
			}
			else
			{
				cplFactType = X_C_AdvCommissionFact.FACTTYPE_Provisionsabrechnung;
			}

			if (result == null)
			{
				result = cplFactType;
			}
			else
			{
				Check.assume(result.equals(cplFactType), "All facts have a consistent type; cplFacts=" + cplFacts);
			}
		}
		return result;
	}

	private MHRMovement retrieveMovement(final MInvoiceLine il)
	{
		final int payrollMovementId = il.get_ValueAsInt(de.metas.commission.interfaces.I_C_InvoiceLine.COLUMNNAME_HR_Movement_ID);

		if (payrollMovementId <= 0)
		{
			CommissionException.inconsistentData("invoice line has no HR_Movement_ID", il);
		}

		final MHRMovement payrollMovement = new MHRMovement(il.getCtx(), payrollMovementId, il.get_TrxName());
		return payrollMovement;
	}

	private Map<Integer, List<MCAdvCommissionPayrollLine>> retrieveInstanceId2Cpls(final MHRMovement payrollMovement)
	{

		final Map<Integer, List<MCAdvCommissionPayrollLine>> inst2cpl = new HashMap<Integer, List<MCAdvCommissionPayrollLine>>();

		// load commission payroll lines (cpls)
		// and group our cpls by those instances.
		for (final MHRAllocationLine allocLine : MHRAllocationLine.retrieveForMovement(payrollMovement))
		{
			if (allocLine.getAD_Table_ID() != I_C_AdvCommissionPayrollLine.Table_ID)
			{
				continue;
			}

			final MCAdvCommissionPayrollLine cpl = new MCAdvCommissionPayrollLine(
					payrollMovement.getCtx(),
					allocLine.getRecord_ID(),
					payrollMovement.get_TrxName());

			final int instanceId = cpl.getC_AdvCommissionInstance_ID();

			List<MCAdvCommissionPayrollLine> cpls = inst2cpl.get(instanceId);
			if (cpls == null)
			{
				cpls = new ArrayList<MCAdvCommissionPayrollLine>();
				inst2cpl.put(instanceId, cpls);
			}

			Check.assume(cpl.getCommissionAmt().compareTo(allocLine.getAmount()) == 0, "cpl=" + cpl + "; allocLine=" + allocLine);
			cpls.add(cpl);
		}
		return inst2cpl;
	}

	@Override
	public List<MCAdvCommissionFact> retrieveTriggers(final IAdvComInstance instance, final I_C_Period period)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(instance);
		final String trxName = InterfaceWrapperHelper.getTrxName(instance);

		final List<MCAdvCommissionFact> triggersToCalc = new ArrayList<MCAdvCommissionFact>();

		// Retrieve facts with status "to calculate" and filter them a little bit
		final List<MCAdvCommissionFact> factStatusToCalculate =
				MCAdvCommissionFact.mkQuery(ctx, trxName)
						.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
						.setDateFactBefore(period.getEndDate())
						.setStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen, false)
						.setFactType(X_C_AdvCommissionFact.FACTTYPE_Provisionsberechnung, true)
						.setProcessed(false)
						.list();

		for (final MCAdvCommissionFact fact : factStatusToCalculate)
		{
			Check.assume(fact.getC_AdvCommissionPayrollLine_ID() == 0, fact + " has C_AdvCommissionPayrollLine_ID=0");

			Check.assume(X_C_AdvCommissionFact.FACTTYPE_Aenderung.equals(fact.getFactType())
					|| X_C_AdvCommissionFact.FACTTYPE_Umbuchung.equals(fact.getFactType())
					|| X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung.equals(fact.getFactType())
					|| X_C_AdvCommissionFact.FACTTYPE_ZahlungszuordnRueckg.equals(fact.getFactType())
					|| X_C_AdvCommissionFact.FACTTYPE_ProvisionsberRueckabwicklung.equals(fact.getFactType())
					|| X_C_AdvCommissionFact.FACTTYPE_VorgangSchliessen.equals(fact.getFactType()),
					fact + " has correct factType");

			// fact has not been processed yet and has not been created a part of a former calculation
			triggersToCalc.add(fact);
		}
		return triggersToCalc;
	}

	@Override
	public void updateLevelCalculation(
			final ICommissionType typeIsCompressImpl,
			final Timestamp date,
			final I_C_Sponsor sponsor,
			final I_C_Period period)
	{
		final Collection<IAdvComInstance> instancesOfSalesRep = Services.get(ICommissionInstanceDAO.class).retrieveForSalesRep(sponsor, period.getStartDate(), period.getEndDate(), 0);

		for (final IAdvComInstance salesrepInstance : instancesOfSalesRep)
		{
			final String compressionMode = typeIsCompressImpl.getComSystemType().getDynamicCompression();
			Check.assume(!Check.isEmpty(compressionMode), "DynamicCompression of " + typeIsCompressImpl.getComSystemType() + " is not null");

			// get all instances that share salesrepInstance's PO and
			// commission type and update their commission levels.
			final PO salesrepInstancePO = InterfaceWrapperHelper.getPO(Services.get(ICommissionInstanceDAO.class).retrievePO(salesrepInstance, Object.class));
			final List<IAdvComInstance> allInstances = Services.get(ICommissionInstanceDAO.class).retrieveFor(salesrepInstancePO, typeIsCompressImpl.getComSystemType());

			updateLevelCalculation(typeIsCompressImpl, compressionMode, date, allInstances);
		}
	}

	/**
	 * Note: does save the fact.
	 * 
	 * @param triggerFacts
	 * @param dateDoc
	 * @return
	 */
	@Override
	public List<MCAdvCommissionFact> makeCalculationMinusFact(
			final List<MCAdvCommissionFact> allTriggerFacts,
			final Timestamp dateDoc,
			final int adPInstanceId,
			final String trxName)
	{

		final Map<BigDecimal, List<MCAdvCommissionFact>> percent2Facts = new HashMap<BigDecimal, List<MCAdvCommissionFact>>();
		for (final MCAdvCommissionFact triggerFact : allTriggerFacts)
		{
			if (triggerFact.getCommission().signum() == 0)
			{
				continue; // we simply ignore facts with 0% commisison
			}
			List<MCAdvCommissionFact> facts = percent2Facts.get(triggerFact.getCommission());
			if (facts == null)
			{
				facts = new ArrayList<MCAdvCommissionFact>();
				percent2Facts.put(triggerFact.getCommission(), facts);
			}
			facts.add(triggerFact);
		}

		final List<MCAdvCommissionFact> result = new ArrayList<MCAdvCommissionFact>();

		for (final List<MCAdvCommissionFact> triggerFacts : percent2Facts.values())
		{
			final MCAdvCommissionFact lastTriggerFact = triggerFacts.get(triggerFacts.size() - 1);
			// note: we assert that all triggerFacts have the same CommissionPointsBase
			final BigDecimal minusCommissionPointsBase = lastTriggerFact.getCommissionPointsBase();
			final BigDecimal minusQty = getQtySum(triggerFacts).negate();
			final BigDecimal minusCommission = lastTriggerFact.getCommission();

			final BigDecimal minusPointsSum = minusCommissionPointsBase.multiply(minusQty);

			final BigDecimal minusPoints = minusPointsSum.multiply(minusCommission.divide(Env.ONEHUNDRED));

			// Create a forecast fact to subtract the cpl's points from the
			// "forecast" balance.
			final MCAdvCommissionFact minusFact = MCAdvCommissionFact.createNew(lastTriggerFact.retrievePO(), null);
			minusFact.setStatus(lastTriggerFact.getStatus());
			minusFact.setAD_PInstance_ID(adPInstanceId);
			minusFact.setIsCounterEntry(true);
			minusFact.setC_AdvCommissionInstance_ID(lastTriggerFact.getC_AdvCommissionInstance_ID());

			minusFact.setCommissionPointsBase(minusCommissionPointsBase);
			minusFact.setQty(minusQty);
			minusFact.setCommissionPointsSum(minusPointsSum);
			minusFact.setCommission(minusCommission);
			minusFact.setCommissionPoints(minusPoints);

			minusFact.setDateFact(dateDoc);
			minusFact.setDateDoc(dateDoc);

			final String changeReason = Msg.getMsg(lastTriggerFact.getCtx(), MCAdvCommissionFact.MSG_COMMISISON_CALCULATION);
			minusFact.setNote(changeReason);
			minusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_Provisionsberechnung);

			minusFact.setCommissionAmtBase(BigDecimal.ZERO);
			minusFact.setProcessed(true);

			minusFact.saveEx();
			result.add(minusFact);

			for (final MCAdvCommissionFact triggerFact : triggerFacts)
			{
				triggerFact.addFollowUpInfo(minusFact);
				triggerFact.save();
			}
		}
		return result;
	}

	@Override
	public MCAdvCommissionFact unwindCommissionInvoice(
			final I_C_AdvCommissionFact commissionInvoiceFact,
			final I_C_AdvCommissionFact commissionCalculationFact,
			final I_C_AdvCommissionFactCand cand,
			final int adPInstanceId)
	{
		Check.assume(!commissionCalculationFact.isCounterEntry(), commissionCalculationFact + " has CounterEntry='N'");

		final PO commissionInvoiceLine = MCAdvCommissionFact.retrievePO(commissionInvoiceFact);

		Check.assume(commissionInvoiceLine instanceof MInvoiceLine,
				"Referenced PO of " + commissionCalculationFact + " is instanceof MInvoiceLine");

		final I_C_AdvCommissionFactCand cause = Services.get(ICommissionFactCandBL.class).getCause(cand);
		Check.assume(cause.getC_AdvCommissionFactCand_ID() > 0, cand + " has cand.getCause().get_ID() > 0");

		final MCAdvCommissionFact minusFact = MCAdvCommissionFact.createNew(commissionInvoiceLine, null);
		minusFact.setC_AdvCommissionInstance_ID(commissionInvoiceFact.getC_AdvCommissionInstance_ID());

		minusFact.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen);
		minusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrRueckabwicklung);

		minusFact.setCommissionAmtBase(commissionCalculationFact.getCommissionAmtBase());
		minusFact.setC_Currency_ID(commissionCalculationFact.getC_Currency_ID());
		minusFact.setCommissionAmt(commissionCalculationFact.getCommissionAmt().negate());

		minusFact.setAmtMultiplier(commissionInvoiceFact.getAmtMultiplier());
		minusFact.setCommissionPoints(commissionInvoiceFact.getCommissionPoints());

		minusFact.setDateDoc(commissionInvoiceFact.getDateDoc());
		minusFact.setDateFact(cause.getDateAcct());

		minusFact.setC_AdvCommissionFactCand_ID(cause.getC_AdvCommissionFactCand_ID());

		minusFact.setAD_PInstance_ID(adPInstanceId);
		minusFact.setIsCounterEntry(true);
		minusFact.setProcessed(true);
		minusFact.saveEx();

		commissionInvoiceFact.setProcessed(true);
		MCAdvCommissionFact.addFollowUpInfo(commissionInvoiceFact, minusFact);
		InterfaceWrapperHelper.save(commissionInvoiceFact);

		final PO commissionCalculationLine = MCAdvCommissionFact.retrievePO(commissionCalculationFact);
		Check.assume(commissionCalculationLine instanceof MCAdvCommissionPayrollLine,
				"Referenced PO of " + commissionCalculationFact + " is instanceof MCAdvCommissionPayrollLine");

		final MCAdvCommissionFact plusFact = MCAdvCommissionFact.createNew(commissionCalculationLine, null);
		plusFact.setC_AdvCommissionInstance_ID(commissionInvoiceFact.getC_AdvCommissionInstance_ID());

		plusFact.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet);
		plusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrRueckabwicklung);

		plusFact.setCommissionAmtBase(commissionCalculationFact.getCommissionAmtBase());
		plusFact.setCommissionPointsBase(commissionCalculationFact.getCommissionPointsBase());
		plusFact.setQty(commissionCalculationFact.getQty());
		plusFact.setCommissionPointsSum(commissionCalculationFact.getCommissionPointsSum());
		plusFact.setCommission(commissionCalculationFact.getCommission());
		plusFact.setCommissionPoints(commissionCalculationFact.getCommissionPoints());
		plusFact.setAmtMultiplier(commissionCalculationFact.getAmtMultiplier());
		plusFact.setCommissionAmt(commissionCalculationFact.getCommissionAmt());
		plusFact.setC_Currency_ID(commissionCalculationFact.getC_Currency_ID());

		plusFact.setDateDoc(commissionCalculationFact.getDateDoc());
		plusFact.setDateFact(cause.getDateAcct());

		plusFact.setC_AdvCommissionFactCand_ID(cause.getC_AdvCommissionFactCand_ID());

		plusFact.setAD_PInstance_ID(adPInstanceId);
		plusFact.setIsCounterEntry(false);
		plusFact.setIsActive(commissionCalculationFact.isActive());
		plusFact.saveEx();

		minusFact.addFollowUpInfo(plusFact);
		minusFact.saveEx();

		return plusFact;
	}

	@Override
	public MCAdvCommissionFact unwindCommissionCalculation(
			final I_C_AdvCommissionFact commissionCalculationFact,
			final I_C_AdvCommissionFact commissionTriggerFact,
			final I_C_AdvCommissionFactCand cand,
			final int adPInstanceId)
	{
		Check.assume(!commissionTriggerFact.isCounterEntry(), commissionTriggerFact + " has CounterEntry='N'");

		final PO commissionCalculationLine = MCAdvCommissionFact.retrievePO(commissionCalculationFact);

		Check.assume(InterfaceWrapperHelper.isInstanceOf(commissionCalculationLine, I_C_AdvCommissionPayrollLine.class),
				"Referenced PO of " + commissionCalculationFact + " is instanceof MCAdvCommissionPayrollLine");

		final I_C_AdvCommissionFactCand cause = Services.get(ICommissionFactCandBL.class).getCause(cand);
		Check.assume(cause.getC_AdvCommissionFactCand_ID() > 0, cand + " has cand.getCause().get_ID() > 0");

		final MCAdvCommissionFact minusFact = MCAdvCommissionFact.createNew(commissionCalculationLine, null);
		minusFact.setC_AdvCommissionInstance_ID(commissionCalculationFact.getC_AdvCommissionInstance_ID());

		minusFact.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet);
		minusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsberRueckabwicklung);

		minusFact.setCommissionAmtBase(commissionCalculationFact.getCommissionAmtBase());
		minusFact.setCommissionPointsBase(commissionCalculationFact.getCommissionPointsBase());
		minusFact.setQty(commissionCalculationFact.getQty().negate());
		minusFact.setCommissionPointsSum(commissionCalculationFact.getCommissionPointsSum().negate());
		minusFact.setCommission(commissionCalculationFact.getCommission());
		minusFact.setCommissionPoints(commissionCalculationFact.getCommissionPoints().negate());
		minusFact.setAmtMultiplier(commissionCalculationFact.getAmtMultiplier());
		minusFact.setCommissionAmt(commissionCalculationFact.getCommissionAmt().negate());
		minusFact.setC_Currency_ID(commissionCalculationFact.getC_Currency_ID());

		minusFact.setDateDoc(commissionCalculationFact.getDateDoc());
		minusFact.setDateFact(cause.getDateAcct());

		minusFact.setC_AdvCommissionFactCand_ID(cause.getC_AdvCommissionFactCand_ID());

		minusFact.setAD_PInstance_ID(adPInstanceId);
		minusFact.setIsCounterEntry(true);

		// Note: *NOT* setting 'minusFact' to processed='Y', because it still
		// needs to be processed in a commission calculation.
		Check.assume(!minusFact.isProcessed(), "");
		minusFact.saveEx();

		MCAdvCommissionFact.addFollowUpInfo(commissionCalculationFact, minusFact);

		// Note: setting 'commissionCalculationFact' to processed='Y' to indicate
		// that it won't be part of another commission calculation or invoicing
		commissionCalculationFact.setProcessed(true);
		InterfaceWrapperHelper.save(commissionCalculationFact);

		final PO commissionTriggerLine = MCAdvCommissionFact.retrievePO(commissionTriggerFact);

		final MCAdvCommissionFact plusFact = MCAdvCommissionFact.createNew(commissionTriggerLine, null);
		plusFact.setC_AdvCommissionInstance_ID(commissionCalculationFact.getC_AdvCommissionInstance_ID());

		plusFact.setStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		plusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsberRueckabwicklung);

		plusFact.setCommissionAmtBase(commissionTriggerFact.getCommissionAmtBase());
		plusFact.setCommissionPointsBase(commissionTriggerFact.getCommissionPointsBase());
		plusFact.setQty(commissionTriggerFact.getQty());
		plusFact.setCommissionPointsSum(commissionTriggerFact.getCommissionPointsSum());
		plusFact.setCommission(commissionTriggerFact.getCommission());
		plusFact.setCommissionPoints(commissionTriggerFact.getCommissionPoints());
		plusFact.setAmtMultiplier(null);
		plusFact.setCommissionAmt(null);
		plusFact.setC_Currency_ID(0);

		plusFact.setDateDoc(commissionTriggerFact.getDateDoc());
		plusFact.setDateFact(cause.getDateAcct());

		plusFact.setC_AdvCommissionFactCand_ID(cause.getC_AdvCommissionFactCand_ID());

		plusFact.setAD_PInstance_ID(adPInstanceId);
		plusFact.setIsCounterEntry(false);
		plusFact.setIsActive(commissionTriggerFact.isActive());

		plusFact.saveEx();

		minusFact.addFollowUpInfo(plusFact);
		minusFact.saveEx();

		return plusFact;
	}

	public void closeInstanceForStatus(
			final int adPinstanceId,
			final MCAdvCommissionFactCand cand,
			final I_C_Sponsor_CondLine condChangeLine,
			final Timestamp dateDoc,
			final IAdvComInstance instanceToClose,
			final String status)
	{
		final BigDecimal points = Services.get(ICommissionInstanceDAO.class).retrieveBalancePoints(instanceToClose, status);
		final BigDecimal pointsSum = Services.get(ICommissionInstanceDAO.class).retrieveBalancePointsSum(instanceToClose, status);
		final BigDecimal amt = Services.get(ICommissionInstanceDAO.class).retrieveBalanceAmt(instanceToClose, status);
		final BigDecimal qty = Services.get(ICommissionInstanceDAO.class).retrieveBalanceQty(instanceToClose, status);

		if (points.signum() != 0 || pointsSum.signum() != 0 || amt.signum() != 0 || qty.signum() != 0)
		{
			final MCAdvCommissionFact minusFact = MCAdvCommissionFact.createNew(InterfaceWrapperHelper.getPO(condChangeLine), cand);
			minusFact.setStatus(status);
			minusFact.setAD_PInstance_ID(adPinstanceId);
			minusFact.setIsCounterEntry(true);
			minusFact.setC_AdvCommissionInstance_ID(instanceToClose.getC_AdvCommissionInstance_ID());

			minusFact.setCommissionPointsBase(null);
			minusFact.setQty(qty.negate());
			minusFact.setCommissionPointsSum(pointsSum.negate());
			minusFact.setCommission(null);
			minusFact.setCommissionPoints(points.negate());
			minusFact.setAmtMultiplier(null);
			minusFact.setCommissionAmt(null);
			minusFact.setDateDoc(dateDoc);
			minusFact.setDateFact(cand.getDateAcct());

			minusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_VorgangSchliessen);
			minusFact.saveEx();

			CommissionFactBL.logger.debug("Created minus fact " + minusFact);
		}
		else
		{
			CommissionFactBL.logger.debug("No need to create minus fact for status='" + status + "' and " + instanceToClose);
		}
	}

	@Override
	public boolean isCommissionInvoiceFactUnwound(final I_C_AdvCommissionFact fact)
	{
		for (final I_C_AdvCommissionFact followUpFact : MCAdvCommissionFact.retrieveFollowUpFacts(fact))
		{
			if (X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrRueckabwicklung.equals(followUpFact.getFactType()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isCommissionCalculationFactUnwound(final I_C_AdvCommissionFact fact)
	{
		for (final I_C_AdvCommissionFact followUpFact : MCAdvCommissionFact.retrieveFollowUpFacts(fact))
		{
			if (X_C_AdvCommissionFact.FACTTYPE_ProvisionsberRueckabwicklung.equals(followUpFact.getFactType()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isCommissionCalculationFactInvoiced(final I_C_AdvCommissionFact fact)
	{
		for (final I_C_AdvCommissionFact followUpFact : MCAdvCommissionFact.retrieveFollowUpFacts(fact))
		{
			if (X_C_AdvCommissionFact.FACTTYPE_Provisionsabrechnung.equals(followUpFact.getFactType()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void recordComDoc(
			final MCAdvCommissionFactCand cand,
			final MCAdvComDoc comDoc,
			final Object poLine,
			final IAdvComInstance inst,
			final int adPInstanceId)
	{

		final List<MCAdvCommissionFact> triggerFacts =
				MCAdvCommissionFact.mkQuery(comDoc.getCtx(), comDoc.get_TrxName())
						.setAdvCommissionInstanceId(inst.getC_AdvCommissionInstance_ID())
						.setCounterRecord(false)
						.setTableId(MTable.getTable_ID(InterfaceWrapperHelper.getModelTableName(poLine)))
						.setRecordId(InterfaceWrapperHelper.getId(poLine))
						.list();
		Check.assume(!triggerFacts.isEmpty(), "There are C_AdvCommissionFact records for " + inst + " and " + poLine);
		final MCAdvCommissionFact lastTriggerFact = triggerFacts.get(triggerFacts.size() - 1);

		final List<I_C_AdvCommissionFact> successiveFacts = new SuccessiveFactsFinder()
		{
			@Override
			public boolean isOK(final I_C_AdvCommissionFact fact)
			{
				return true;
			}
		}.retrieveSuccessive(comDoc.getCtx(), lastTriggerFact, comDoc.get_TrxName());

		final I_C_AdvCommissionFact oldFact;
		if (successiveFacts.isEmpty())
		{
			oldFact = lastTriggerFact;
		}
		else
		{
			oldFact = successiveFacts.get(successiveFacts.size() - 1);
		}

		// Note: 'STATUS_Prognostiziert' can occur when a payment has been reversed and then DateFact_Override is reset
		// to the actual DateFact value
		if (!X_C_AdvCommissionFact.STATUS_ZuBerechnen.equals(oldFact.getStatus()) && !X_C_AdvCommissionFact.STATUS_Prognostiziert.equals(oldFact.getStatus()))
		{
			// nothing to do
			return;
		}

		final Timestamp newDateFact;
		if (comDoc.getDateFact_Override() == null)
		{
			newDateFact = comDoc.getDateFact();
		}
		else
		{
			newDateFact = comDoc.getDateFact_Override();
		}

		if (oldFact.getDateFact().equals(newDateFact))
		{
			// nothing to do
			return;
		}

		CommissionFactBL.logger.info("Recording DateFact " + oldFact.getDateFact() + " => " + newDateFact);

		final I_C_AdvCommissionFactCand cause = Services.get(ICommissionFactCandBL.class).getCause(cand);

		final MCAdvCommissionFact minusFact = createNewFact(oldFact, true);
		minusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_Aenderung);
		minusFact.setDateFact(oldFact.getDateFact());
		minusFact.setC_AdvCommissionFactCand_ID(cause.getC_AdvCommissionFactCand_ID());
		minusFact.setAD_PInstance_ID(adPInstanceId);

		minusFact.saveEx();

		MCAdvCommissionFact.addFollowUpInfo(oldFact, minusFact);
		InterfaceWrapperHelper.save(oldFact);

		final MCAdvCommissionFact plusFact = createNewFact(oldFact, false);

		plusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_Aenderung);
		final String note = Msg.getMsg(comDoc.getCtx(), MCAdvCommissionFact.MSG_COMMISSION_CHANGE) + " " + Msg.translate(comDoc.getCtx(), I_C_AdvCommissionFact.COLUMNNAME_DateFact);
		plusFact.setNote(note);

		plusFact.setDateFact(newDateFact);
		plusFact.setC_AdvCommissionFactCand_ID(cand.get_ID());
		plusFact.setAD_PInstance_ID(adPInstanceId);

		plusFact.saveEx();

		minusFact.addFollowUpInfo(plusFact);
		minusFact.saveEx();
	}

	/**
	 * Following fields are not set:
	 * <ul>
	 * <li>AD_PInstance_ID</li>
	 * <li>DateFact</li>
	 * <li>C_AdvCommissionFactCand_ID</li>
	 * <li>C_AdvCommissionPayrollLine_ID</li>
	 * </ul>
	 * 
	 * @param fromFact
	 * @return
	 */

	public MCAdvCommissionFact createNewFact(final I_C_AdvCommissionFact fromFact, final boolean counterEntry)
	{
		final MCAdvCommissionFact newFact = MCAdvCommissionFact.createNew(MCAdvCommissionFact.retrievePO(fromFact), null);
		newFact.setStatus(fromFact.getStatus());

		newFact.setIsCounterEntry(counterEntry);
		newFact.setC_AdvCommissionInstance_ID(fromFact.getC_AdvCommissionInstance_ID());

		newFact.setCommissionPointsBase(getBigDecimalOrNull(fromFact, I_C_AdvCommissionFact.COLUMNNAME_CommissionPointsBase, false));
		newFact.setQty(getBigDecimalOrNull(fromFact, I_C_AdvCommissionFact.COLUMNNAME_Qty, counterEntry));
		newFact.setCommissionPointsSum(getBigDecimalOrNull(fromFact, I_C_AdvCommissionFact.COLUMNNAME_CommissionPointsSum, counterEntry));
		newFact.setCommission(getBigDecimalOrNull(fromFact, I_C_AdvCommissionFact.COLUMNNAME_Commission, false));
		newFact.setCommissionPoints(getBigDecimalOrNull(fromFact, I_C_AdvCommissionFact.COLUMNNAME_CommissionPoints, counterEntry));

		newFact.setAmtMultiplier(getBigDecimalOrNull(fromFact, I_C_AdvCommissionFact.COLUMNNAME_AmtMultiplier, false));
		newFact.setCommissionAmt(getBigDecimalOrNull(fromFact, I_C_AdvCommissionFact.COLUMNNAME_CommissionAmt, counterEntry));
		newFact.setCommissionAmtBase(getBigDecimalOrNull(fromFact, I_C_AdvCommissionFact.COLUMNNAME_CommissionAmtBase, false));
		newFact.setC_Currency_ID(fromFact.getC_Currency_ID());

		newFact.setFactType(fromFact.getFactType());
		newFact.setStatus(fromFact.getStatus());
		newFact.setDateDoc(fromFact.getDateDoc());

		return newFact;
	}

	private BigDecimal getBigDecimalOrNull(final I_C_AdvCommissionFact fromFact, final String columnName, final boolean negate)
	{

		final BigDecimal returnVal = (BigDecimal)InterfaceWrapperHelper.getPO(fromFact).get_Value(columnName);
		if (returnVal == null)
		{
			return returnVal;
		}
		if (negate)
		{
			return returnVal.negate();
		}
		return returnVal;
	}

	/**
	 * 
	 * @param poLine
	 * @return
	 */
	Timestamp retrieveDateFact(final Object poLine)
	{
		if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_AllocationLine.class))
		{
			final MAllocationLine allocLine = (MAllocationLine)poLine;
			final MCAdvComDoc comDoc = MCAdvComDoc.retrieveForAllocLine(allocLine);
			if (comDoc != null)
			{
				return comDoc.getDateFact_Override();
			}
			return allocLine.getDateTrx();
		}

		final Object poHeader = Services.get(IFieldAccessBL.class).retrieveHeader(poLine);
		final MCAdvComDoc comDoc = MCAdvComDoc.retrieveForTrigger(poHeader);
		if (comDoc != null && comDoc.isActive())
		{
			return comDoc.getDateFact_Override();
		}

		return null;
	}

	@Override
	public void recordAllocationLine(
			final ICommissionType type,
			final MCAdvCommissionFactCand cand,
			final MAllocationLine line,
			final int adPInstanceId)
	{
		new CommissionFactRecordAllocationLine(this).recordAllocationLine(type, cand, line, adPInstanceId);
	}

	@Override
	public I_C_AdvCommissionFactCand getCause(final I_C_AdvCommissionFactCand cand)
	{
		final I_C_AdvCommissionFactCand cause = cand.getC_AdvComFactCand_Cause();
		if (cause == null)
		{
			return cand;
		}
		return cause;
	}

	@Override
	public void setCause(final I_C_AdvCommissionFactCand cand, final I_C_AdvCommissionFactCand cause)
	{
		cand.setC_AdvComFactCand_Cause(cause);
	}

}
