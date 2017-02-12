package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.inout.util.CachedObjects;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IUpdatableSchedulesCollector;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;
import de.metas.product.IProductBL;

/**
 * This collector is currently disabled, see the interface's javadoc.
 *
 */
public class UpdatableSchedulesCollector implements IUpdatableSchedulesCollector
{
	private static Logger logger = LogManager.getLogger(UpdatableSchedulesCollector.class);

	/**
	 * Does nothing, just returns <code>seed</code>.
	 */
	@Override
	public List<OlAndSched> collectUpdatableLines(
			final Properties ctx,
			final List<OlAndSched> seed,
			final CachedObjects co,
			final String trxName)
	{
		return seed;
// @formatter:off
//		final CachedObjects coToUse = mkCoToUse(co);
//
//		final Set<Integer> productsSeen = new HashSet<Integer>();
//		inititalInspect(seed, productsSeen, coToUse, trxName);
//
//		final List<OlAndSched> result = new ArrayList<OlAndSched>(seed);
//
//		final Set<Integer> bPartnersExploded = new HashSet<Integer>();
//
//		int level = 1;
//		while (inspectOlAndScheds(ctx, result, productsSeen, bPartnersExploded, coToUse, trxName) > 0)
//		{
//			level++;
//			logger.debug("level=" + level);
//		}
//
//		return result;
// @formatter:on
	}

	/**
	 * Collect all products that are referred to by the order lines of the given <code>olsAndScheds</code>.
	 *
	 * @param olsAndScheds
	 * @param productSeen
	 *            set where the different product ids are stored while the given <code>olsAndScheds</code> are iterated.
	 * @param co
	 *            cache where the lines' BPartners are stored while the given <code>olsAndScheds</code> are iterated.
	 * @param trxName_NOTUSED
	 */
	private void inititalInspect(
			final Collection<OlAndSched> olsAndScheds,
			final Set<Integer> productSeen,
			final CachedObjects co,
			final String trxName_NOTUSED)
	{
		for (final OlAndSched olAndSched : olsAndScheds)
		{
			final I_C_OrderLine ol = olAndSched.getOl();
			if (ol == null)
			{
				// ol has already been deleted
				continue;
			}
			productSeen.add(ol.getM_Product_ID());
			co.retrieveAndCacheBPartner(ol);

		}
		logger.info("Found " + productSeen.size() + " different M_Product_IDs");
	}

	/**
	 * For the given <code>olsAndScheds</code> this method iterates the order lines and checks if further {@link OlAndSched} instances need to be added. This is the case if
	 * <ul>
	 * <li>the order line's bPartner has a portage-free amount</li>
	 * <li>the order line's product is not an item</li>
	 * <li>the order line's product is an item, but the order also has a non-item position</li>
	 * </ul>
	 * In both cases the shipment scheduling doesn't only depend on the given line, but on further lines as well.
	 *
	 *
	 * @param olsAndScheds
	 * @param productsSeen
	 * @param bPartnersSeen
	 * @param co
	 * @param trxName
	 * @return the number of {@link OlAndSched}s that have been added to <code>olsAndScheds</code>.
	 */
	private int inspectOlAndScheds(
			final Properties ctx,
			final Collection<OlAndSched> olsAndScheds,
			final Set<Integer> productsSeen,
			final Set<Integer> bPartnersSeen,
			final CachedObjects co,
			final String trxName)
	{
		final List<OlAndSched> newOlAndScheds = new ArrayList<OlAndSched>();

		for (final OlAndSched olAndSched : olsAndScheds)
		{
			final I_C_OrderLine ol = olAndSched.getOl();
			if (ol == null)
			{
				continue;
			}
			final List<OlAndSched> singleOlResult = inspectSingleOl(ctx, ol, productsSeen, bPartnersSeen, co, trxName);

			newOlAndScheds.addAll(singleOlResult);
		}

		olsAndScheds.addAll(newOlAndScheds);
		return newOlAndScheds.size();
	}

	final List<OlAndSched> inspectSingleOl(
			final Properties ctx,
			final I_C_OrderLine ol,
			final Set<Integer> productsSeen,
			final Set<Integer> bPartnersSeen,
			final CachedObjects co,
			final String trxName)
	{
		Check.assume(ol != null, "Param 'ol' is not null");

		final List<OlAndSched> newOlAndScheds = new ArrayList<OlAndSched>();

		final boolean hasPostageFreeAmt = bPartnerHasPostageFreeAmt(co, ol);

		final boolean nonItemProduct = isNonItemProduct(ctx, co, ol, trxName);

		if (hasPostageFreeAmt || nonItemProduct)
		{
			final int bPartnerId = ol.getC_BPartner_ID();

			if (bPartnersSeen.add(bPartnerId))
			{
				addForBPartnerAndProducts(ctx, bPartnerId, newOlAndScheds, productsSeen, trxName);
			}
		}

		//
		// Check if there is a non-item-product in the same order. If so, it needs to be updated, too.
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		for (final I_C_OrderLine lineOfOrder : Services.get(IOrderPA.class).retrieveOrderLines(ctx, ol.getC_Order_ID(), I_C_OrderLine.class, trxName))
		{
			if (productsSeen.add(lineOfOrder.getM_Product_ID()))
			{
				if (isNonItemProduct(ctx, co, InterfaceWrapperHelper.create(lineOfOrder, I_C_OrderLine.class), trxName))
				{
					final I_M_ShipmentSchedule sched = shipmentSchedulePA.retrieveForOrderLine(ctx, lineOfOrder.getC_OrderLine_ID(), trxName);
					if (sched != null)
					{
						// note: if sched is null, it is no problem, because the updater will create (and update!) any
						// sched that is not yet there
						newOlAndScheds.add(new OlAndSched(InterfaceWrapperHelper.create(lineOfOrder, I_C_OrderLine.class), sched));
					}
				}
			}
		}

		return newOlAndScheds;
	}

	private boolean isNonItemProduct(final Properties ctx, final CachedObjects co, final I_C_OrderLine ol, final String trxName)
	{
		Check.assume(ol != null, "Param 'ol' is not null");

		if (ol.getM_Product_ID() <= 0)
		{
			return false;
		}

		I_M_Product product = co.getProductCache().get(ol.getM_Product_ID());
		if (product == null)
		{
			product = InterfaceWrapperHelper.create(ol.getM_Product(), I_M_Product.class);
			Check.assume(product != null, "C_OrderLine.M_Product_ID has an FK-constraint on M_Product and therefore 'product' is not null");

			co.getProductCache().put(ol.getM_Product_ID(), product);
		}

		return !Services.get(IProductBL.class).isItem(product);
	}

	private boolean bPartnerHasPostageFreeAmt(final CachedObjects co, final I_C_OrderLine ol)
	{
		Check.assume(ol != null, "Param 'ol' is not null");

		final I_C_BPartner bPartner = co.retrieveAndCacheBPartner(ol);

		final BigDecimal postageFreeAmt = bPartner.getPostageFreeAmt();

		final boolean hasPostageFreeAmt = postageFreeAmt != null && postageFreeAmt.signum() > 0;
		return hasPostageFreeAmt;
	}

	/**
	 * Adds those shipment schedules (A) that have the given bPartnerId. For each of theses scheds (A), it then adds all
	 * further shipment schedules (B) that have the same M_Product_ID as (A).
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param newOlAndScheds
	 * @param productsSeen
	 * @param trxName
	 */
	private void addForBPartnerAndProducts(
			final Properties ctx,
			final int bPartnerId,
			final List<OlAndSched> newOlAndScheds,
			final Set<Integer> productsSeen,
			final String trxName)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		final List<OlAndSched> allForBPartner = shipmentSchedulePA.retrieveOlAndSchedsForBPartner(bPartnerId, false, trxName);

		for (final OlAndSched currentForBPartner : allForBPartner)
		{
			final int currentProductId = currentForBPartner.getOl().getM_Product_ID();

			if (!productsSeen.contains(currentProductId))
			{
				productsSeen.add(currentProductId);

				final List<OlAndSched> allForProduct =
						shipmentSchedulePA.retrieveOlAndSchedsForProduct(ctx, currentProductId, trxName);

				newOlAndScheds.addAll(allForProduct);
			}
		}
	}

	private CachedObjects mkCoToUse(final CachedObjects co)
	{
		if (co == null)
		{
			return new CachedObjects();
		}
		return co;
	}
}
