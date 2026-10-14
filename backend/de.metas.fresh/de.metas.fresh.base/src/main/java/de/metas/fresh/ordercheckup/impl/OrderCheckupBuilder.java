package de.metas.fresh.ordercheckup.impl;

/*
 * #%L
 * de.metas.fresh.base
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

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.fresh.ordercheckup.OrderCheckupBarcode;
import de.metas.fresh.ordercheckup.OrderCheckupDocumentType;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * {@link I_C_Order_MFGWarehouse_Report} builder.
 *
 * @author tsa
 *
 */
public class OrderCheckupBuilder
{
	public static final OrderCheckupBuilder newBuilder()
	{
		return new OrderCheckupBuilder();
	}

	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private boolean _built = false;
	private OrderCheckupDocumentType _documentType = null;
	private I_C_Order _order;
	private WarehouseId _warehouseId;
	private ResourceId _plantId;
	private UserId _reponsibleUserId;
	private final List<I_C_OrderLine> _orderLines = new ArrayList<>();

	private OrderCheckupBuilder()
	{
	}

	/**
	 * Builds the {@link I_C_Order_MFGWarehouse_Report}.
	 *
	 * @return the report, or {@code null} if there were no {@link I_C_OrderLine}s to put on it
	 */
	@Nullable
	public I_C_Order_MFGWarehouse_Report build()
	{
		markAsBuild();

		// Do nothing if there are no order lines
		final List<I_C_OrderLine> orderLines = getOrderLines();
		if (orderLines.isEmpty())
		{
			return null;
		}

		final I_C_Order order = getC_Order();

		//
		// Create report header
		final I_C_Order_MFGWarehouse_Report report = InterfaceWrapperHelper.newInstance(I_C_Order_MFGWarehouse_Report.class, order);
		report.setAD_Org_ID(order.getAD_Org_ID());
		report.setDocumentType(getDocumentType().getCode());
		report.setC_DocType_ID(getDocTypeId().getRepoId());
		report.setC_Order(order);
		report.setC_BPartner_ID(order.getC_BPartner_ID());
		report.setM_Warehouse_ID(WarehouseId.toRepoId(getWarehouseId()));
		report.setPP_Plant_ID(ResourceId.toRepoId(getPlantId()));
		report.setAD_User_Responsible_ID(UserId.toRepoId(getReponsibleUserId()));
		report.setProcessed(false); // we will set it to true when we are done with the lines
		InterfaceWrapperHelper.save(report);

		//
		// Create report lines
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final I_C_Order_MFGWarehouse_ReportLine reportLine = InterfaceWrapperHelper.newInstance(I_C_Order_MFGWarehouse_ReportLine.class, order);
			reportLine.setC_Order_MFGWarehouse_Report(report);
			reportLine.setAD_Org_ID(orderLine.getAD_Org_ID());
			reportLine.setC_OrderLine(orderLine);
			reportLine.setM_Product_ID(orderLine.getM_Product_ID());
			reportLine.setBarcode(OrderCheckupBarcode.ofC_OrderLine_ID(reportLine.getC_OrderLine_ID()).toBarcodeString());
			InterfaceWrapperHelper.save(reportLine);
		}

		//
		// Mark the report as processed
		// NOTE we do this only at the end because this is the moment where doc outbound shall react and create/print the PDF report.
		report.setProcessed(true);
		InterfaceWrapperHelper.save(report);

		return report;
	}

	private final void assertNotBuilt()
	{
		Check.assume(!_built, "not already built");
	}

	private final void markAsBuild()
	{
		assertNotBuilt();
		_built = true;
	}

	public OrderCheckupBuilder setC_Order(final I_C_Order order)
	{
		assertNotBuilt();

		Check.assumeNotNull(order, "order not null");
		this._order = order;
		return this;
	}

	private final I_C_Order getC_Order()
	{
		Check.assumeNotNull(_order, "_order not null");
		return _order;
	}

	public OrderCheckupBuilder addOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		assertNotBuilt();

		Check.assume(getC_Order().getC_Order_ID() == orderLine.getC_Order_ID(), "order line shall be part of provided order");
		_orderLines.add(orderLine);
		return this;
	}

	private final List<I_C_OrderLine> getOrderLines()
	{
		return _orderLines;
	}

	public OrderCheckupBuilder setWarehouseId(@Nullable final WarehouseId warehouseId)
	{
		this._warehouseId = warehouseId;
		return this;
	}

	private WarehouseId getWarehouseId()
	{
		return _warehouseId;
	}

	public OrderCheckupBuilder setPlantId(ResourceId plantId)
	{
		this._plantId = plantId;
		return this;
	}

	private ResourceId getPlantId()
	{
		return _plantId;
	}

	public OrderCheckupBuilder setReponsibleUserId(UserId reponsibleUserId)
	{
		this._reponsibleUserId = reponsibleUserId;
		return this;
	}

	private UserId getReponsibleUserId()
	{
		return _reponsibleUserId;
	}

	public OrderCheckupBuilder setDocumentType(final OrderCheckupDocumentType documentType)
	{
		this._documentType = documentType;
		return this;
	}

	private OrderCheckupDocumentType getDocumentType()
	{
		return Check.assumeNotNull(_documentType, "documentType not null");
	}

	/**
	 * Resolves the {@code C_DocType} for this report's kind, by the {@link DocBaseType} the kind maps to.
	 * The single place that decides the kind-&gt;doctype mapping, so the two {@code OrderCheckupBL} call
	 * sites that choose a kind cannot end up disagreeing on which document type it gets.
	 * <p>
	 * Throws unconditionally when no document type is found, rather than leaving {@code C_DocType_ID}
	 * unset: an unset document type makes the archiver fall back to the generic outbound configuration
	 * and the catch-all printer routing, so both kinds would silently print the same report and the
	 * Packzettel would never reach its printer - with no error anywhere. That silent fallback must not be
	 * reintroduced as a tolerated state.
	 * Unlike the missing-plant guard in {@code OrderCheckupBL}, this one is deliberately NOT sysconfig-
	 * gated: a missing plant is per-instance master data the customer maintains, whereas both document
	 * types ship together in one migration, so their absence means that migration has not been applied.
	 */
	private DocTypeId getDocTypeId()
	{
		final I_C_Order order = getC_Order();

		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(getDocBaseType(getDocumentType()))
				.docSubType(DocTypeQuery.DOCSUBTYPE_NONE)
				.adClientId(order.getAD_Client_ID())
				.adOrgId(order.getAD_Org_ID())
				.build());
	}

	private static DocBaseType getDocBaseType(@NonNull final OrderCheckupDocumentType documentType)
	{
		switch (documentType)
		{
			case Warehouse:
				return DocBaseType.OrderCheckupProduction;
			case Plant:
				return DocBaseType.OrderCheckupOffice;
			default:
				throw new AdempiereException("Unexpected OrderCheckupDocumentType: " + documentType);
		}
	}
}
