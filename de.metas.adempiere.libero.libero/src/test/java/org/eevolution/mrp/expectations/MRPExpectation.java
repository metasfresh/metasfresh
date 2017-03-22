package org.eevolution.mrp.expectations;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.MRPFirmType;
import org.eevolution.mrp.api.impl.MRPBL;
import org.eevolution.mrp.api.impl.MRPTracer;
import org.eevolution.mrp.api.impl.PlainMRPDAO;
import org.junit.Assert;

public class MRPExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	public static MRPExpectation<Object> newMRPExpectation()
	{
		return new MRPExpectation<Object>();
	}

	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient PlainMRPDAO mrpDAO = (PlainMRPDAO)Services.get(IMRPDAO.class);
	private final transient MRPBL mrpBL = (MRPBL)Services.get(IMRPBL.class);

	//
	// Expectations
	private I_S_Resource _plant;
	private I_M_Warehouse _warehouse;
	private I_M_Product _product;
	private String _typeMRP;
	private I_C_BPartner _bpartner;
	private BigDecimal _qtyDemand;
	private BigDecimal _qtySupply;
	private BigDecimal _qtySupplyIncludingQtyOnHandReserved;
	private BigDecimal _qtySupplyFirm;
	private BigDecimal _qtyOnHandReserved;
	private BigDecimal _qtyInTransitReserved;
	private Boolean _balanced;
	private final Set<Integer> _ddOrderLinesToExclude = new HashSet<>();
	private final Set<Integer> _ddOrderLinesToInclude = new HashSet<>();
	private final Set<Integer> _ppOrdersToInclude = new HashSet<>();
	private MRPFirmType _mrpFirmType;
	private Boolean _mrpRecordsAvailable;

	//
	// Collectors
	private final MRPExpectationDocumentsCollector collectedDocuments = new MRPExpectationDocumentsCollector();

	private MRPExpectation()
	{
		super();
	}

	public MRPExpectation<ParentExpectationType> assertMRPSegmentsBalanced()
	{
		Check.assumeNotNull(_warehouse, "_warehouse not null");
		this.balanced(true); // make sure balanced is set

		ErrorMessage currentErrorMessage = null;
		I_M_Product currentProduct = null;
		final List<I_PP_MRP> currentMRPs = new ArrayList<I_PP_MRP>();
		for (final I_PP_MRP mrp : retrieveMRPRecords())
		{
			//
			// Segment changed
			final I_M_Product mrpProduct = mrp.getM_Product();
			if (currentProduct != null && currentProduct.getM_Product_ID() != mrpProduct.getM_Product_ID())
			{
				final BigDecimal qtyOnHand = mrpDAO.getQtyOnHand(getContext(), _warehouse, currentProduct);
				assertExpected(currentErrorMessage, qtyOnHand, currentMRPs);

				// Reset current segment
				currentProduct = null;
				currentErrorMessage = null;
				currentMRPs.clear();
			}

			//
			// New segment
			if (currentProduct == null)
			{
				currentProduct = mrpProduct;
				currentErrorMessage = newErrorMessage()
						.addContextInfo("Warehouse: ", _warehouse)
						.addContextInfo("Product", currentProduct);
			}

			//
			// Add MRP line to current segment
			currentMRPs.add(mrp);
		}

		//
		// Validate last segment
		if (!currentMRPs.isEmpty())
		{
			final BigDecimal qtyOnHand = mrpDAO.getQtyOnHand(getContext(), _warehouse, currentProduct);
			assertExpected(currentErrorMessage, qtyOnHand, currentMRPs);

			currentProduct = null;
			currentErrorMessage = null;
			currentMRPs.clear();
		}

		return this;
	}

	public MRPExpectation<ParentExpectationType> assertExpected()
	{
		final List<I_PP_MRP> mrps = retrieveMRPRecords();
		final ErrorMessage errorMessage = null;
		final BigDecimal qtyOnHand = null; // N/A
		return assertExpected(errorMessage, qtyOnHand, mrps);
	}

	private MRPExpectation<ParentExpectationType> assertExpected(
			final ErrorMessage errorMessage,
			final BigDecimal qtyOnHand,
			final List<I_PP_MRP> mrps)
	{
		if (mrps.isEmpty())
		{
			return this;
		}

		final List<I_PP_MRP> mrpsWithNotMatchingAvailability = new ArrayList<>();
		final StringBuilder mrpsStr = new StringBuilder();
		BigDecimal qtyDemand = BigDecimal.ZERO;
		BigDecimal qtySupply = BigDecimal.ZERO;
		BigDecimal qtySupplyFirm = BigDecimal.ZERO;
		BigDecimal qtyOnHandReserved = BigDecimal.ZERO;
		BigDecimal qtyInTransitReserved = BigDecimal.ZERO;
		for (final I_PP_MRP mrp : mrps)
		{
			final String typeMRP = mrp.getTypeMRP();
			final BigDecimal qty = mrp.getQty();
			if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
			{
				qtyDemand = qtyDemand.add(qty);
			}
			else if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
			{
				if (X_PP_MRP.ORDERTYPE_QuantityOnHandReservation.equals(mrp.getOrderType()))
				{
					qtyOnHandReserved = qtyOnHandReserved.add(qty);
				}
				else if (X_PP_MRP.ORDERTYPE_QuantityOnHandInTransit.equals(mrp.getOrderType()))
				{
					qtyInTransitReserved = qtyInTransitReserved.add(qty);
				}
				else
				{
					qtySupply = qtySupply.add(qty);
					if (mrpBL.isReleased(mrp))
					{
						qtySupplyFirm = qtySupplyFirm.add(qty);
					}
				}
			}
			else
			{
				throw new LiberoException("Unknown TypeMRP '" + typeMRP + "' for " + mrp);
			}

			mrpsStr.append("\n").append(MRPTracer.toString(mrp));

			doCollect(mrp);
			collectIfAvailabilityNotMatching(mrp, mrpsWithNotMatchingAvailability);
		}

		final BigDecimal qtyBalance = qtyDemand.subtract(qtySupply).subtract(qtyOnHandReserved);

		final ErrorMessage errorMessageToUse = derive(errorMessage)
				.addContextInfo("MRP lines", mrpsStr)
				.addContextInfo("MRPs not matching availability", mrpsWithNotMatchingAvailability)
				.addContextInfo("QtyOnHand", qtyOnHand == null ? "N/A" : qtyOnHand.toString())
				.addContextInfo("QtyOnHandReserved", qtyOnHandReserved)
				.addContextInfo("QtyInTransitReserved", qtyInTransitReserved)
				.addContextInfo("Qty Demand", qtyDemand)
				.addContextInfo("Qty Supply (w/o OnHand)", qtySupply)
				.addContextInfo("Balance (D - S)", qtyBalance);

		//
		// Assert Demand and Supply are balanced
		if (_balanced != null)
		{
			final boolean balancedActual = qtyBalance.signum() == 0;
			assertEquals(errorMessageToUse.expect("MRP balanced"), _balanced, balancedActual);
		}

		if (_qtyDemand != null)
		{
			assertEquals(errorMessageToUse.expect("QtyDemand"), _qtyDemand, qtyDemand);
		}
		if (_qtySupply != null)
		{
			assertEquals(errorMessageToUse.expect("QtySupply"), _qtySupply, qtySupply);
		}
		if (_qtySupplyIncludingQtyOnHandReserved != null)
		{
			final BigDecimal qtySupplyIncludingQtyOnHandReservedActual = qtySupply.add(qtyOnHandReserved);
			assertEquals(errorMessageToUse.expect("QtySupply+QtyOnHandReserved"), _qtySupplyIncludingQtyOnHandReserved, qtySupplyIncludingQtyOnHandReservedActual);
		}
		if (_qtySupplyFirm != null)
		{
			assertEquals(errorMessageToUse.expect("Firm QtySupply"), _qtySupplyFirm, qtySupplyFirm);
		}
		if (_qtyOnHandReserved != null)
		{
			assertEquals(errorMessageToUse.expect("QtyOnHandReserved"), _qtyOnHandReserved, qtyOnHandReserved);
		}
		if (_qtyInTransitReserved != null)
		{
			assertEquals(errorMessageToUse.expect("QtyInTransitReserved"), _qtyInTransitReserved, qtyInTransitReserved);
		}

		assertTrue(errorMessageToUse.expect("MRP records availability is matched"), mrpsWithNotMatchingAvailability.isEmpty());

		return this;
	}

	private final void collectIfAvailabilityNotMatching(final I_PP_MRP mrp, final List<I_PP_MRP> mrpsWithNotMatchingAvailability)
	{
		if (_mrpRecordsAvailable == null)
		{
			return;
		}

		if (mrp.isAvailable() == _mrpRecordsAvailable)
		{
			return;
		}

		mrpsWithNotMatchingAvailability.add(mrp);
	}

	public MRPExpectation<ParentExpectationType> assertMRPDemandsNotAvailable()
	{
		final boolean checkDemands = true;
		final boolean checkSupplies = false;
		return assertMRPRecordsNotAvailable(checkDemands, checkSupplies);
	}

	public MRPExpectation<ParentExpectationType> assertMRPSuppliesNotAvailable()
	{
		final boolean checkDemands = false;
		final boolean checkSupplies = true;
		return assertMRPRecordsNotAvailable(checkDemands, checkSupplies);
	}

	public MRPExpectation<ParentExpectationType> assertMRPDemandsAndSuppliesNotAvailable()
	{
		final boolean checkDemands = true;
		final boolean checkSupplies = true;
		return assertMRPRecordsNotAvailable(checkDemands, checkSupplies);
	}

	private final MRPExpectation<ParentExpectationType> assertMRPRecordsNotAvailable(final boolean checkDemands, final boolean checkSupplies)
	{
		final List<I_PP_MRP> mrps = retrieveMRPRecords();

		final List<I_PP_MRP> mrpDemandsAvailable = new ArrayList<>();
		final List<I_PP_MRP> mrpSuppliesAvailable = new ArrayList<>();
		for (final I_PP_MRP mrp : mrps)
		{
			if (!mrp.isAvailable())
			{
				continue;
			}

			if (mrpBL.isDemand(mrp))
			{
				mrpDemandsAvailable.add(mrp);
			}
			else if (mrpBL.isSupply(mrp))
			{
				mrpSuppliesAvailable.add(mrp);
			}
			else
			{
				Assert.fail("Invalid TypeMRP: " + mrp);
			}
		}

		final ErrorMessage message = newErrorMessage()
				.addContextInfo("MRP lines", MRPTracer.toString(mrpDemandsAvailable));

		if (checkDemands)
		{
			assertTrue(message.expect("All MRP demands have IsAvailable=N"), mrpDemandsAvailable.isEmpty());
		}
		if (checkSupplies)
		{
			assertTrue(message.expect("All MRP supplies have IsAvailable=N"), mrpSuppliesAvailable.isEmpty());
		}

		return this;
	}

	private List<I_PP_MRP> retrieveMRPRecords()
	{
		final IQueryBuilder<I_PP_MRP> queryBuilder = queryBL
				.createQueryBuilder(I_PP_MRP.class, getContext());

		final ICompositeQueryFilter<I_PP_MRP> filters = queryBuilder.getFilters();

		if (_plant != null)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_S_Resource_ID, _plant.getS_Resource_ID());
		}
		if (_warehouse != null)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_M_Warehouse_ID, _warehouse.getM_Warehouse_ID());
		}
		if (_product != null)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_M_Product_ID, _product.getM_Product_ID());
		}
		if (_bpartner != null)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_C_BPartner_ID, _bpartner.getC_BPartner_ID());
		}
		if (_typeMRP != null)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_TypeMRP, _typeMRP);
		}
		if (!_ddOrderLinesToInclude.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_PP_MRP.COLUMN_DD_OrderLine_ID, _ddOrderLinesToInclude);
		}
		if (!_ddOrderLinesToExclude.isEmpty())
		{
			filters.addNotInArrayFilter(I_PP_MRP.COLUMN_DD_OrderLine_ID, _ddOrderLinesToExclude);
		}
		if (!_ppOrdersToInclude.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_PP_MRP.COLUMN_PP_Order_ID, _ppOrdersToInclude);
		}
		if (_mrpFirmType != null && !_mrpFirmType.getDocStatuses().isEmpty())
		{
			filters.addInArrayOrAllFilter(I_PP_MRP.COLUMNNAME_DocStatus, _mrpFirmType.getDocStatuses());
		}

		queryBuilder.orderBy()
				.addColumn(I_PP_MRP.COLUMNNAME_M_Product_ID)
				.addColumn(I_PP_MRP.COLUMNNAME_M_Warehouse_ID)
				.addColumn(I_PP_MRP.COLUMNNAME_PP_MRP_ID);

		return queryBuilder.create().list();
	}

	public MRPExpectation<ParentExpectationType> warehouse(final I_M_Warehouse warehouse)
	{
		this._warehouse = warehouse;
		return this;
	}

	public MRPExpectation<ParentExpectationType> plant(final I_S_Resource plant)
	{
		this._plant = plant;
		return this;
	}

	public MRPExpectation<ParentExpectationType> product(final I_M_Product product)
	{
		this._product = product;
		return this;
	}

	public MRPExpectation<ParentExpectationType> typeMRP(final String typeMRP)
	{
		this._typeMRP = typeMRP;
		return this;
	}

	public MRPExpectation<ParentExpectationType> qtyDemandAndSupply(final BigDecimal qtyDemandOrSupply)
	{
		qtyDemand(qtyDemandOrSupply);
		qtySupplyIncludingQtyOnHandReserved(qtyDemandOrSupply);
		return this;
	}

	public MRPExpectation<ParentExpectationType> qtyDemandOrSupply(final String qtyDemandOrSupply)
	{
		return qtyDemandAndSupply(new BigDecimal(qtyDemandOrSupply));
	}

	public MRPExpectation<ParentExpectationType> qtyDemandOrSupply(final int qtyDemandOrSupply)
	{
		return qtyDemandAndSupply(new BigDecimal(qtyDemandOrSupply));
	}

	public MRPExpectation<ParentExpectationType> qtyDemand(final BigDecimal qtyDemand)
	{
		this._qtyDemand = qtyDemand;
		return this;
	}

	public MRPExpectation<ParentExpectationType> qtyDemand(final String qtyDemand)
	{
		return qtyDemand(new BigDecimal(qtyDemand));
	}

	public MRPExpectation<ParentExpectationType> qtyDemand(final int qtyDemand)
	{
		return qtyDemand(new BigDecimal(qtyDemand));
	}

	public MRPExpectation<ParentExpectationType> qtySupplyIncludingQtyOnHandReserved(final BigDecimal qtySupplyIncludingQtyOnHandReserved)
	{
		this._qtySupplyIncludingQtyOnHandReserved = qtySupplyIncludingQtyOnHandReserved;
		return this;
	}

	public MRPExpectation<ParentExpectationType> qtySupplyIncludingQtyOnHandReserved(final String qtySupplyIncludingQtyOnHandReserved)
	{
		return qtySupplyIncludingQtyOnHandReserved(new BigDecimal(qtySupplyIncludingQtyOnHandReserved));
	}

	public MRPExpectation<ParentExpectationType> qtySupplyIncludingQtyOnHandReserved(final int qtySupplyIncludingQtyOnHandReserved)
	{
		return qtySupplyIncludingQtyOnHandReserved(new BigDecimal(qtySupplyIncludingQtyOnHandReserved));
	}

	/**
	 * @param qtySupply expected Supply Qty (excluding QtyOnHand reservations)
	 * @return this
	 */
	public MRPExpectation<ParentExpectationType> qtySupply(final BigDecimal qtySupply)
	{
		this._qtySupply = qtySupply;
		return this;
	}

	/**
	 * @param qtySupply expected Supply Qty (excluding QtyOnHand reservations)
	 * @return this
	 */
	public MRPExpectation<ParentExpectationType> qtySupply(final String qtySupply)
	{
		return qtySupply(new BigDecimal(qtySupply));
	}

	/**
	 * @param qtySupply expected Supply Qty (excluding QtyOnHand reservations)
	 * @return this
	 */
	public MRPExpectation<ParentExpectationType> qtySupply(final int qtySupply)
	{
		return qtySupply(new BigDecimal(qtySupply));
	}

	public MRPExpectation<ParentExpectationType> qtyOnHandReserved(final BigDecimal qtyOnHandReserved)
	{
		this._qtyOnHandReserved = qtyOnHandReserved;
		return this;
	}

	public MRPExpectation<ParentExpectationType> qtyOnHandReserved(final String qtyOnHandReserved)
	{
		return qtyOnHandReserved(new BigDecimal(qtyOnHandReserved));
	}

	public MRPExpectation<ParentExpectationType> qtyOnHandReserved(final int qtyOnHandReserved)
	{
		return qtyOnHandReserved(new BigDecimal(qtyOnHandReserved));
	}

	public MRPExpectation<ParentExpectationType> qtyInTransitReserved(final BigDecimal qtyInTransitReserved)
	{
		this._qtyInTransitReserved = qtyInTransitReserved;
		return this;
	}

	public MRPExpectation<ParentExpectationType> qtyInTransitReserved(final String qtyInTransitReserved)
	{
		return qtyInTransitReserved(new BigDecimal(qtyInTransitReserved));
	}

	public MRPExpectation<ParentExpectationType> qtyInTransitReserved(final int qtyInTransitReserved)
	{
		return qtyInTransitReserved(new BigDecimal(qtyInTransitReserved));
	}

	public MRPExpectation<ParentExpectationType> balanced(final boolean balanced)
	{
		this._balanced = balanced;
		return this;
	}

	public MRPExpectation<ParentExpectationType> balanced()
	{
		return balanced(true);
	}

	public MRPExpectation<ParentExpectationType> notBalanced()
	{
		return balanced(false);
	}

	public MRPExpectation<ParentExpectationType> addDDOrderLineToInclude(final int ddOrderLineId)
	{
		this._ddOrderLinesToInclude.add(ddOrderLineId);
		return this;
	}

	public MRPExpectation<ParentExpectationType> addDDOrderLineToInclude(final I_DD_OrderLine ddOrderLineToInclude)
	{
		return addDDOrderLineToInclude(Collections.singleton(ddOrderLineToInclude));
	}

	public MRPExpectation<ParentExpectationType> addDDOrderLineToInclude(final Collection<? extends I_DD_OrderLine> ddOrderLinesToInclude)
	{
		Check.assumeNotNull(ddOrderLinesToInclude, "ddOrderLinesToInclude not null");
		for (final I_DD_OrderLine ddOrderLine : ddOrderLinesToInclude)
		{
			addDDOrderLineToInclude(ddOrderLine.getDD_OrderLine_ID());
		}
		return this;
	}

	public MRPExpectation<ParentExpectationType> addDDOrderLineToExclude(final int ddOrderLineId)
	{
		this._ddOrderLinesToExclude.add(ddOrderLineId);
		return this;
	}

	public MRPExpectation<ParentExpectationType> addDDOrderLineToExclude(final Collection<? extends I_DD_OrderLine> ddOrderLinesToExclude)
	{
		Check.assumeNotNull(ddOrderLinesToExclude, "ddOrderLinesToExclude not null");
		for (final I_DD_OrderLine ddOrderLine : ddOrderLinesToExclude)
		{
			addDDOrderLineToExclude(ddOrderLine.getDD_OrderLine_ID());
		}
		return this;
	}

	public MRPExpectation<ParentExpectationType> addPPOrderToInclude(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");
		_ppOrdersToInclude.add(ppOrder.getPP_Order_ID());
		return this;
	}

	public MRPExpectation<ParentExpectationType> firmType(final MRPFirmType mrpFirmType)
	{
		this._mrpFirmType = mrpFirmType;
		return this;
	}

	/**
	 * Ask to collect MRP documents of a given type.
	 *
	 * @param modelClass
	 * @param documentsCollector list where the documents shall be collected; use <code>null</code> if you want to unlink a previously specified list
	 * @return this
	 */
	public <T> MRPExpectation<ParentExpectationType> collectDocuments(final Class<T> modelClass, final List<T> documentsCollector)
	{
		this.collectedDocuments.collect(modelClass, documentsCollector);
		return this;
	}

	private final void doCollect(final I_PP_MRP mrp)
	{
		this.collectedDocuments.add(mrp);
	}

	public MRPExpectation<ParentExpectationType> qtySupplyFirm(final BigDecimal qtySupplyFirm)
	{
		this._qtySupplyFirm = qtySupplyFirm;
		return this;
	}

	public MRPExpectation<ParentExpectationType> qtySupplyFirm(final String qtySupplyFirm)
	{
		return qtySupplyFirm(new BigDecimal(qtySupplyFirm));
	}

	public MRPExpectation<ParentExpectationType> qtySupplyFirm(final int qtySupplyFirm)
	{
		return qtySupplyFirm(new BigDecimal(qtySupplyFirm));
	}

	public MRPExpectation<ParentExpectationType> bpartner(final I_C_BPartner bpartner)
	{
		this._bpartner = bpartner;
		return this;
	}

	public MRPExpectation<ParentExpectationType> notAvailable()
	{
		this._mrpRecordsAvailable = false;
		return this;
	}
}
