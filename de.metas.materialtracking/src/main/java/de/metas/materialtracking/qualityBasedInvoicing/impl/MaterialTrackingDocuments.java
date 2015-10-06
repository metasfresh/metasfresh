package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;

/* package */class MaterialTrackingDocuments implements IMaterialTrackingDocuments
{
	// Services
	private final transient IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
	private final transient IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

	// Parameters
	private final I_M_Material_Tracking _materialTracking;

	// Loaded values
	private List<IQualityInspectionOrder> _qualityInspectionOrders = null;

	public MaterialTrackingDocuments(final I_M_Material_Tracking materialTracking)
	{
		super();

		Check.assumeNotNull(materialTracking, "materialTracking not null");
		_materialTracking = materialTracking;
	}

	@Override
	public I_M_Material_Tracking getM_Material_Tracking()
	{
		return _materialTracking;
	}

	@Override
	public List<IQualityInspectionOrder> getQualityInspectionOrders()
	{
		if (_qualityInspectionOrders == null)
		{
			_qualityInspectionOrders = Collections.unmodifiableList(retrieveQualityInspectionOrders());
		}

		return _qualityInspectionOrders;
	}

	private List<IQualityInspectionOrder> retrieveQualityInspectionOrders()
	{
		final I_M_Material_Tracking materialTracking = getM_Material_Tracking();

		//
		// Iterate all referenced orders and create quality inspection order documents
		int nextInspectionNumber = 1;
		final List<IQualityInspectionOrder> qiOrders = new ArrayList<>();
		List<IQualityInspectionOrder> previousOrders = new ArrayList<>();

		// task 08848: we need to order the QualityInspections by their production dates.
		// Just ordering them by M_Material_Tracking_Ref_ID is not OK because some of data records in might have been created late.
		final ArrayList<IQualityInspectionOrder> qualityInspectionOrders = new ArrayList<IQualityInspectionOrder>();
		for (final I_PP_Order ppOrder : materialTrackingDAO.retrieveReferences(materialTracking, I_PP_Order.class))
		{
			qualityInspectionOrders.add(new QualityInspectionOrder(ppOrder, materialTracking));
		}
		Collections.sort(qualityInspectionOrders, new Comparator<IQualityInspectionOrder>()
		{
			@Override
			public int compare(IQualityInspectionOrder o1, IQualityInspectionOrder o2)
			{
				return o1.getDateOfProduction().compareTo(o2.getDateOfProduction());
			}
		});

		for (final IQualityInspectionOrder qiOrderInterface : qualityInspectionOrders)
		{
			QualityInspectionOrder qiOrder = (QualityInspectionOrder)qiOrderInterface;

			//
			// Case: current order is an inspection order
			final boolean isQualityInspection = qiOrder.isQualityInspection();
			if (isQualityInspection)
			{

				qiOrder.setInspectionNumber(nextInspectionNumber);
				nextInspectionNumber++;

				qiOrder.setPreceedingOrders(previousOrders);
				previousOrders = new ArrayList<>();

				qiOrder.setAllOrders(qualityInspectionOrders);

				qiOrders.add(qiOrder);
			}

			//
			// Collect preceeding orders
			{
				previousOrders.add(qiOrder);
			}
		}

		return qiOrders;
	}

	@Override
	public IQualityInspectionOrder getQualityInspectionOrder(final org.eevolution.model.I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");

		final int ppOrderId = ppOrder.getPP_Order_ID();
		for (final IQualityInspectionOrder order : getQualityInspectionOrders())
		{
			if (order.getPP_Order().getPP_Order_ID() == ppOrderId)
			{
				return order;
			}
		}

		throw new AdempiereException("@NotFound@ @PP_Order_ID@");
	}

	@Override
	public void linkModelToMaterialTracking(final Object model)
	{
		final I_M_Material_Tracking materialTracking = getM_Material_Tracking();
		materialTrackingBL.linkModelToMaterialTracking(
				MTLinkRequest.builder()
						.setModel(model)
						.setMaterialTracking(materialTracking)
						.build());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
