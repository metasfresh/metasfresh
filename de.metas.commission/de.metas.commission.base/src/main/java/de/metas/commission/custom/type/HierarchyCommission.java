package de.metas.commission.custom.type;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.MBPartner;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_C_DocType;
import org.compiere.util.CLogger;

import de.metas.adempiere.model.IProductAware;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionRelevantPO;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvComRelevantPOType;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionInstance;
import de.metas.commission.model.MCAdvCommissionRelevantPO;
import de.metas.commission.model.MCIncidentLine;
import de.metas.commission.model.MCIncidentLineFact;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvComSystem_Type;
import de.metas.commission.service.IComRelevantPoBL;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.HierarchyAscender;
import de.metas.commission.util.HierarchyClimber.Result;
import de.metas.commission.util.HierarchyDescender;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * Common subclass for hierarchy based commission types. Basically, for a given candidate and poLine, this class retrieves the customer, ascends the upline and creates/updates the commission instances
 * for each level thus distributing or redistributing the commission for the given poLine
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public abstract class HierarchyCommission extends BaseCommission
{

	public static final BigDecimal FACTOR = BigDecimal.ONE;

	private static final CLogger logger = CLogger.getCLogger(HierarchyCommission.class);

	/**
	 * @param cand the candidate to evaluate
	 * @param poLine can be the result of {@link MCAdvCommissionFactCand#retrievePO()}, might might also be a line of that PO (e.g. an order line)
	 * @param status one of {@link X_C_AdvComSalesRepFact#STATUS_Prognose} or {@link X_C_AdvComSalesRepFact#STATUS_Prov_Relevant}. If status is <code>STATUS_Prov_Relevant</code>, then an instance will
	 *            be created, even if there is a commission only in the actual commission calculation, but not during the forecast.
	 */
	@Override
	protected final void createInstanceAndFact(final MCAdvCommissionFactCand cand, final PO poLine, final String status, final int adPInstanceId)
	{
		HierarchyCommission.logger.config("cand=" + cand + "; poLine=" + poLine);

		// Note: in the end, 'posToHandle' might contain more (or different) cands
		// and POs than the given ones (due to retroactive evaluation)
		final Map<PO, MCAdvCommissionFactCand> posToHandle = new HashMap<PO, MCAdvCommissionFactCand>();

		if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_AdvComSalesRepFact.class))
		{
			// check if existing C_AdvCommissionInstances need updating
			final I_C_AdvComSalesRepFact salesRepFact = InterfaceWrapperHelper.create(poLine, I_C_AdvComSalesRepFact.class);

			final boolean actual = X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(salesRepFact.getStatus());
			final boolean salaryGroupChange = X_C_AdvComSalesRepFact.NAME_VG_Aenderung.equals(salesRepFact.getName());

			if (actual && salaryGroupChange)
			{
				final I_C_Sponsor sponsor = salesRepFact.getC_Sponsor();

				// Add the POs that might need updating due to the change.
				// Note: we don't add 'salesRepFact' itself
				posToHandle.putAll(retrieveDataToUpdate(sponsor, cand));
			}
			HierarchyCommission.logger.info("C_AdvComSystem_Type_ID " + getComSystemType().getC_AdvComSystem_Type_ID() + ": " + posToHandle.size() + " incident(s) to (re)evaluate due to " + poLine);
		}
		else
		{
			posToHandle.put(poLine, cand);
		}

		for (final Map.Entry<PO, MCAdvCommissionFactCand> entry : posToHandle.entrySet())
		{
			if (entry.getKey() instanceof MInvoiceLine)
			{
				final MInvoiceLine il = (MInvoiceLine)entry.getKey();
				final MOrderLine ol = (MOrderLine)il.getC_OrderLine();

				final IFieldAccessBL fieldAccessBL = Services.get(IFieldAccessBL.class);

				if (ol != null && ol.get_ID() > 0 && !fieldAccessBL.isCreditMemo(il))
				{
					// il has an ol and is thus not an instance trigger.
					// Retrieve the ol's instances
					final List<IAdvComInstance> instancesOfOl = Services.get(ICommissionInstanceDAO.class).retrieveAllFor(ol, getComSystemType());

					// Now update the ol's instance with the invoice line
					for (final IAdvComInstance instance : instancesOfOl)
					{
						final I_C_Sponsor salesRepSponsor = instance.getC_Sponsor_SalesRep();

						final Timestamp dateToUse = Services.get(ISponsorBL.class).retrieveDateTo(salesRepSponsor, getComSystemType(), instance.getDateDoc());

						// also update the instance with the ol. This is required if the invoice is created during the
						// order's completeIt process
						BaseCommission.commissionFactBL.recordInstanceTrigger(this, cand, dateToUse, ol, instance, adPInstanceId);
						BaseCommission.commissionFactBL.recordInstanceTrigger(this, cand, dateToUse, il, instance, adPInstanceId);
					}
				}
				else
				{
					// il doesn't have an order line or il belongs to a credit memo.
					// In both cases it is an instance trigger.
					handleCommissionRelevantPO(entry.getValue(), entry.getKey(), status, adPInstanceId);
				}
			}
			else
			{
				Check.assume(entry.getKey() instanceof MOrderLine, entry.toString());
				final MOrderLine ol = (MOrderLine)entry.getKey();

				//
				// do the actual handling of the current ol
				handleCommissionRelevantPO(entry.getValue(), ol, status, adPInstanceId);

				if (Services.get(IPrepayOrderBL.class).isPrepayOrder(cand.getCtx(), ol.getC_Order_ID(), cand.get_TrxName()))
				{
					// ol belongs to a prepay order. That means that its invoice line is just nice to have.
					// There we don't make sure that it is recorded (we would have to male sure that the allocation line
					// is also recorded),
				}
				else
				{
					//
					// Check if there is an invoice line for the ol we just processed.
					// If there is a relevant il, make sure that it is recorded (this is required when using warehouse
					// orders).
					final List<MInvoiceLine> nonCreditMemoIlsForNewOl = retrieveNonCreditmemoIls(ol);
					for (final MInvoiceLine il : nonCreditMemoIlsForNewOl)
					{
						//
						// check if the invoice is commission relevant for this particular commission type
						final I_C_Invoice invoice = il.getC_Invoice();
						final MCAdvCommissionRelevantPO relevantPODef = MCAdvCommissionRelevantPO.retrieveIfRelevant(invoice);
						if (relevantPODef != null)
						{
							if (!MCAdvComRelevantPOType.retrieveFor(relevantPODef, getComSystemType()).isEmpty())
							{
								for (final IAdvComInstance instanceOfOl : Services.get(ICommissionInstanceDAO.class).retrieveAllFor(ol, getComSystemType()))
								{
									final I_C_Sponsor salesRepSponsor = instanceOfOl.getC_Sponsor_SalesRep();

									final Timestamp dateToUse = Services.get(ISponsorBL.class).retrieveDateTo(salesRepSponsor, getComSystemType(), instanceOfOl.getDateDoc());

									BaseCommission.commissionFactBL.recordInstanceTrigger(this, cand, dateToUse, il, instanceOfOl, adPInstanceId);
								}
							}
						}
					}
				}
			}
		}
	}

	@Cached
	private List<MInvoiceLine> retrieveNonCreditmemoIls(final MOrderLine ol)
	{
		// select those invoice lines that have the given order line id and whose invoice is not a credit memo
		final String wc =
				org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID + "=? "
						+ " AND " + org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID + " NOT IN ("
						+ "    select " + I_C_Invoice.COLUMNNAME_C_Invoice_ID
						+ "    from " + I_C_Invoice.Table_Name + " i"
						+ "       join " + I_C_DocType.Table_Name + " dt on dt." + I_C_DocType.COLUMNNAME_C_DocType_ID + "=i." + I_C_Invoice.COLUMNNAME_C_DocType_ID
						+ "    where dt." + I_C_DocType.COLUMNNAME_DocBaseType + "='" + X_C_DocType.DOCBASETYPE_ARCreditMemo + "'"
						+ " )";

		final List<MInvoiceLine> nonCreditMemoIlsForNewOl =
				new Query(ol.getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name, wc, ol.get_TrxName())
						.setParameters(ol.get_ID())
						.setClient_ID()
						.setOrderBy(org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID)
						.list();
		return nonCreditMemoIlsForNewOl;
	}

	/**
	 * 
	 * @param cand
	 * @param poLine must be an instance trigger (either C_OrderLine or C_InvoiceLine <b>without</b> a C_OrderLine reference)
	 * @param status status to be used when it is decided if a new {@link MCAdvCommissionInstance} should be created. Value can be either {@link X_C_AdvComSalesRepFact#STATUS_Prognose} or
	 *            {@link X_C_AdvComSalesRepFact#STATUS_Prov_Relevant}. If status is <code>STATUS_Prov_Relevant</code>, then an instance will be created, even if there is a commission only in the
	 *            actual commission calculation, but not during the forecast.
	 */
	private void handleCommissionRelevantPO(
			final MCAdvCommissionFactCand cand, final PO poLine, final String status, final int adPInstanceId)
	{
		final List<IAdvComInstance> instances = new ArrayList<IAdvComInstance>();

		final Timestamp date = cand.getDateAcct();

		final MBPartner customer = cand.retrieveBPartner();
		Check.assume(customer != null, "customer of " + cand + " is not null");

		final I_C_Sponsor customerSponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(customer, true);

		final int maxLevel = Integer.MAX_VALUE;

		// level counters to be used inside actOnLevel
		final int[] forecastLevel = { 0 };
		final int[] calculationLevel = { 0 };

		new HierarchyAscender()
		{
			@Override
			public Result actOnLevel(
					final I_C_Sponsor salesRepSponsor,
					final int logicalLevel,
					final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{
				//
				// Get the date to use when evaluating the candidate with the current sponsor
				// Note: if the commission type's retroactive-type is "period", then this date depends on the sponsor's
				// contract.
				final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
				final Timestamp dateToUse = sponsorBL.retrieveDateTo(salesRepSponsor, getComSystemType(), date);

				//
				// check if sponsorCurrentLevel's rank meets the minimum criteria

				final I_C_BPartner bPartner = sponsorBL.retrieveSalesRepAt(
						InterfaceWrapperHelper.getCtx(salesRepSponsor),
						dateToUse, salesRepSponsor, false,
						InterfaceWrapperHelper.getTrxName(salesRepSponsor));

				if (bPartner == null)
				{
					// 'salesRepsponsor' is not a sales rep at all
					HierarchyCommission.logger.fine(salesRepSponsor + " represents no sales rep at " + dateToUse);

					final boolean skipNonSalesReps = isSkipOrphanedSponsor(salesRepSponsor, date);

					if (skipNonSalesReps)
					{
						HierarchyCommission.logger.fine("Skipping " + salesRepSponsor + " because it has no assigned C_BPartner");
						return Result.SKIP_EXTEND;
					}
				}

				// 04639
				// use product parameter as well
				final IProductAware productAware = InterfaceWrapperHelper.create(poLine, IProductAware.class);
				final I_M_Product product = productAware.getM_Product();

				final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(salesRepSponsor, cand.getDateAcct(), HierarchyCommission.this, product);

				final I_C_AdvCommissionTerm term = sponsorBL.retrieveTerm(commissionCtx, false);
				if (term == null)
				{
					// 'salesRepSponsor' is a real sales rep, but has no contract for the given product/category, so according to this particular poLine, he's not to be threated as sales rep
					return Result.SKIP_EXTEND;
				}
				
				final boolean compressed;

				// check if there is an existing instance
				IAdvComInstance instance = Services.get(ICommissionInstanceDAO.class).retrieveNonClosedFor(poLine, customerSponsor, salesRepSponsor, term.getC_AdvCommissionTerm_ID());

				if (instance == null)
				{
					final IInstanceTriggerBL instanceTriggerBL = Services.get(IInstanceTriggerBL.class);

					if (!instanceTriggerBL.isInCorrectProductCategory(term, salesRepSponsor, poLine))
					{
						return Result.SKIP_EXTEND;
					}

					//
					// Check if the new instance would have qty and commissionPointsBase
					// This improves performance by avoiding to call the specific type's code when there's no point
					final IFieldAccessBL fieldAccessBL = Services.get(IFieldAccessBL.class);

					final BigDecimal potentialPoints = fieldAccessBL.getCommissionPoints(poLine, true);
					final BigDecimal potentialQty = fieldAccessBL.getQty(poLine);
					final boolean hasMinimumSG = hasMinimumRank(dateToUse, salesRepSponsor);

					if (!hasMinimumSG || potentialPoints.signum() == 0 || potentialQty.signum() == 0)
					{
						// create no instance for this sponsorCurrentLevel
						return Result.SKIP_EXTEND;
					}

					// do the actual work of creating a new instance for the
					// given level (the method might also return null)

					instance = createNewInstance(
							cand, poLine, dateToUse,
							customerSponsor, salesRepSponsor,
							term,
							hierarchyLevel, forecastLevel[0], calculationLevel[0],
							status, adPInstanceId,
							contextInfo
							);

					if (instance == null)
					{
						// No instance has been created.
						// Missing instances are counted just like uncompressed instances.
						compressed = false;
					}
					else
					{
						compressed = instance.getLevelCalculation() == -1;
					}
				}
				else
				{
					compressed = instance.getLevelCalculation() == -1;

					final int levelCalc = compressed ? -1 : calculationLevel[0];

					//
					// update the existing instance
					if (instance.getLevelForecast() != forecastLevel[0] || instance.getLevelCalculation() != levelCalc || instance.getLevelHierarchy() != hierarchyLevel)
					{
						instance.setLevelForecast(forecastLevel[0]);
						instance.setLevelCalculation(levelCalc);
						instance.setLevelHierarchy(hierarchyLevel);

						InterfaceWrapperHelper.save(instance);
					}
					BaseCommission.commissionFactBL.recordInstanceTrigger(HierarchyCommission.this, cand, dateToUse, poLine, instance, adPInstanceId);
				}

				if (instance != null)
				{
					instances.add(instance);
				}

				//
				// decide how to go on
				final Result invocationResult = getResult(salesRepSponsor, dateToUse, instance);

				if (invocationResult != Result.SKIP_IGNORE)
				{
					forecastLevel[0] += 1;
					if (!compressed)
					{
						calculationLevel[0] += 1;
					}
					else if (forecastLevel[0] >= 1 && calculationLevel[0] == 0)
					{
						calculationLevel[0] = 1;
					}
				}
				return invocationResult;
			}
		}.setDate(date).climb(customerSponsor, maxLevel);
	}

	IAdvComInstance createNewInstance(
			final I_C_AdvCommissionFactCand cand,
			final PO poLine,
			final Timestamp dateToUse,
			final I_C_Sponsor customer,
			final I_C_Sponsor salesRep,
			final I_C_AdvCommissionTerm term,
			final int hierarchyLevel,
			final int forecastLevel,
			final int calculationLevel,
			final String status,
			final int adPInstanceId,
			final Map<String, Object> contextInfo
			)
	{
		final ICommissionFactBL commissionFactBL = Services.get(ICommissionFactBL.class);

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final boolean compress;
		if (X_C_AdvComSystem_Type.DYNAMICCOMPRESSION_Keine.equals(getComSystemType().getDynamicCompression()))
		{
			// no need to retrieve the srf collector and check if compress
			compress = false;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(cand);
			final String trxName = InterfaceWrapperHelper.getTrxName(cand);
			final ISalesRefFactCollector srfCollector = sponsorBL.retrieveSalesRepFactCollector(ctx, salesRep, dateToUse, trxName);

			final IProductAware productAware = InterfaceWrapperHelper.create(poLine, IProductAware.class);
			final org.compiere.model.I_M_Product product = productAware.getM_Product();

			final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(salesRep, dateToUse, this, product);

			compress = srfCollector.isCompress(commissionCtx);
		}

		final int newInstCalcLevel = compress ? -1 : calculationLevel;

		final BigDecimal commission = getPercent(salesRep, customer, poLine, X_C_AdvComSalesRepFact.STATUS_Prognose, dateToUse, forecastLevel);
		final BigDecimal actualCommission;

		if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(status))
		{
			actualCommission = getPercent(salesRep, customer, poLine, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, dateToUse, newInstCalcLevel);
		}
		else
		{
			actualCommission = commission;
		}

		//
		// Note: if compress==true, we create an instance even if commission=0.
		// Because if the instance is compressed, it can still affect the
		// LevelCalculation value of other instances (that don't have
		// commission=0 due to their sponsor's higher rank).
		if (!compress && commission.signum() == 0 && actualCommission.signum() == 0)
		{
			return null;
		}

		final Map<IAdvComInstance, MCAdvCommissionFact> instanceAndFact =
				commissionFactBL.createInstanceAndFact(
						term, cand, poLine,
						customer, salesRep,
						hierarchyLevel, forecastLevel, newInstCalcLevel,
						false,
						adPInstanceId);

		return instanceAndFact.keySet().iterator().next();
	}

	/**
	 * Subclasses implement this method to decide what to to next after the current level's iteration
	 * 
	 * @param salesRepCurrentLevel
	 * @param date the candidate's dateAcct
	 * @param instance the instance as returned by {@link #createNewInstance(I_C_AdvCommissionFactCand, PO, I_C_Sponsor, I_C_Sponsor, int, int, Map)} .
	 * @return
	 */
	abstract Result getResult(I_C_Sponsor salesRepCurrentLevel, Timestamp date, IAdvComInstance instance);

	abstract BigDecimal getPercent(I_C_Sponsor salesRep, I_C_Sponsor customer, PO poLine, String status, Timestamp date, int level);

	@Override
	public BigDecimal getFactor()
	{
		return HierarchyCommission.FACTOR;
	}

	/**
	 * Returns the poLines that need to be reevaluated due to a rank- (a.k.a salary group) change:
	 * 
	 * <ul>
	 * <li>retrieves a "start date" depending on given <code>dateChange</code> the retroactive value (which in turn depends on <code>sponsor</code> and this commission type)</li>
	 * <li>descends the downline from the given <code>sponsor</code> for that "start date" and retrieves each sponsor's customer</li>
	 * <li>for each customer in the downline, those incidents are loaded that have their dateDoc after the "start date"</li>
	 * <li>from those incidents, new key-value pairs for the return value are created</li>
	 * </ul>
	 * 
	 * @param sponsor
	 * @param dateChange
	 * @return a map (poLine -> cand)
	 * @see IContractBL#getRetroactive(java.util.Properties, ICommissionType, String)
	 * @see ISponsorBL#retrieveDateFrom(I_C_Sponsor, Timestamp, String)
	 */
	private Map<PO, MCAdvCommissionFactCand> retrieveDataToUpdate(final I_C_Sponsor sponsor, final I_C_AdvCommissionFactCand cand)
	{
		final int maxLevel = Integer.MAX_VALUE;

		final Timestamp dateChange = cand.getDateAcct();

		final Map<PO, MCAdvCommissionFactCand> result = new HashMap<PO, MCAdvCommissionFactCand>();
		if (X_C_AdvComSystem_Type.RETROACTIVEEVALUATION_Keine.equals(getComSystemType().getRetroactiveEvaluation()))
		{
			HierarchyCommission.logger.fine(getComSystemType() + " has no retroactive evaluation -> returning empty map");
			return Collections.emptyMap();
		}
		// no need to update anything that happened after start date
		final Timestamp startDate = Services.get(ISponsorBL.class).retrieveDateFrom(sponsor, getComSystemType(), dateChange);

		new HierarchyDescender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
					final int logicalLevel, final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{
				final I_C_BPartner customerCurrentLevel = Services.get(ISponsorDAO.class).getCustomer(sponsorCurrentLevel, startDate);

				final List<MCIncidentLineFact> lineFacts = retrieveOlsAndIls(customerCurrentLevel, startDate);

				HierarchyCommission.logger.fine("Got " + lineFacts.size() + " indicent line facts for customer" + customerCurrentLevel);

				final Map<PO, MCAdvCommissionFactCand> resultTmp = mkCandidates(startDate, lineFacts, cand);

				Check.assume(resultTmp.size() <= lineFacts.size(), "The number of candidates is <= the number of lineFacts");

				result.putAll(resultTmp);

				return Result.GO_ON;
			}
		}
				.setDateFrom(startDate) // also iterates children that were added after startDate
				.climb(sponsor, maxLevel);

		return result;
	}

	private List<MCIncidentLineFact> retrieveOlsAndIls(final I_C_BPartner bPartner, final Timestamp dateStart)
	{
		final List<MCIncidentLine> incidentLines =
				MCIncidentLine.retrieveForBPartner(bPartner, dateStart);

		final List<MCIncidentLineFact> lineFacts = new ArrayList<MCIncidentLineFact>();

		for (final MCIncidentLine line : incidentLines)
		{
			final List<MCIncidentLineFact> facts = MCIncidentLineFact.retrieveForLine(line);
			Check.assume(!facts.isEmpty(), line + " has MCIncidentLineFacts");

			for (final MCIncidentLineFact lineFact : facts)
			{
				final boolean ol = lineFact.getAD_Table_ID() == MTable.getTable_ID(I_C_OrderLine.Table_Name);

				final boolean il = lineFact.getAD_Table_ID() == MTable.getTable_ID(org.compiere.model.I_C_InvoiceLine.Table_Name);

				if (ol || il)
				{
					lineFacts.add(lineFact);
				}
			}
		}
		return lineFacts;
	}

	/**
	 * 
	 * @param dateStart the minimum dateDoc that a fact's PO must have to be relevant
	 * @param dateCand
	 * @param lineFacts
	 * @return
	 */
	private Map<PO, MCAdvCommissionFactCand> mkCandidates(
			final Timestamp dateStart,
			final List<MCIncidentLineFact> lineFacts,
			final I_C_AdvCommissionFactCand cause)
	{
		final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);

		final IComRelevantPoBL comRelevantPoBL = Services.get(IComRelevantPoBL.class);

		final Map<PO, MCAdvCommissionFactCand> resultTmp = new HashMap<PO, MCAdvCommissionFactCand>();

		for (final MCIncidentLineFact fact : lineFacts)
		{
			if (fact.getDateDoc().before(dateStart))
			{
				continue;
			}

			final PO factPO = fact.retrievePO();

			if (factPO == null)
			{
				HierarchyCommission.logger.warning(fact + " has PO = null");
				continue;
			}
			final Object headerPO = faBL.retrieveHeader(factPO);

			if (InterfaceWrapperHelper.isInstanceOf(headerPO, I_C_Invoice.class))
			{
				final I_C_Invoice invoice = InterfaceWrapperHelper.create(headerPO, I_C_Invoice.class);
				if (!invoice.isSOTrx()
						|| !comRelevantPoBL.isRelevantInvoiceLine(invoice, (MInvoiceLine)factPO, this))
				{
					// header is not an SOTrx (e.g. Commission Invoices) -> nothing to update
					continue;
				}
			}

			final I_C_AdvCommissionRelevantPO commissionRelevantPO = fact.getC_AdvCommissionRelevantPO();
			assert commissionRelevantPO.getC_AdvCommissionRelevantPO_ID() > 0 : "fact=" + fact;

			final MCAdvCommissionFactCand newCand = MCAdvCommissionFactCand.createNoSave(headerPO, commissionRelevantPO);
			Services.get(ICommissionFactBL.class).setCause(newCand, cause);

			if (comRelevantPoBL.isSameComSystem(getComSystemType(), newCand, true))
			{
				resultTmp.put(factPO, newCand);
			}
		}
		return resultTmp;
	}

	boolean hasMinimumRank(final Timestamp date, final I_C_Sponsor sponsor)
	{
		final I_C_AdvCommissionSalaryGroup minimumRank = getComSystemType().getC_AdvComRank_Min();

		if (minimumRank == null || minimumRank.getC_AdvCommissionSalaryGroup_ID() <= 0)
		{
			// there is no minimum rank constraint
			return true;
		}

		final String minimumRankStatus = getComSystemType().getC_AdvComRank_Min_Status();

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		final boolean hasMinimumSG = srfBL.isRankGE(
				comSystem,
				minimumRank.getValue(),
				sponsor,
				minimumRankStatus,
				date);

		if (!hasMinimumSG && X_C_AdvComSalesRepFact.STATUS_Prognose.equals(minimumRankStatus))
		{
			// "forecast" rank is too low, but perhaps there is already an
			// "actual" rank that is sufficient
			return srfBL.isRankGE(
					comSystem,
					minimumRank.getValue(),
					sponsor,
					X_C_AdvComSalesRepFact.STATUS_Prov_Relevant,
					date);
		}
		return hasMinimumSG;
	}

	protected boolean isSkipOrphanedSponsor(final I_C_Sponsor salesRepSponsor, final Timestamp date)
	{
		final IContractBL contractBL = Services.get(IContractBL.class);

		final boolean skipNonSalesReps =
				(Boolean)contractBL.retrieveInstanceParam(
						InterfaceWrapperHelper.getCtx(salesRepSponsor),
						getComSystemType(),
						ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS,
						InterfaceWrapperHelper.getTrxName(salesRepSponsor));

		return skipNonSalesReps;
	}
}
