package de.metas.handlingunits.expectations;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.junit.Assert;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class ShipmentScheduleQtyPickedExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	private I_M_ShipmentSchedule shipmentSchedule;
	private IReference<I_M_HU> luHU;
	private IReference<I_M_HU> tuHU;
	private IReference<I_M_HU> vhu;
	private BigDecimal qtyPicked;
	private I_M_InOutLine inoutLine;
	private boolean inoutLineSet;

	public ShipmentScheduleQtyPickedExpectation()
	{
		this(null);
	}

	public ShipmentScheduleQtyPickedExpectation(final ParentExpectationType parent)
	{
		super(parent);
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> assertExpected(final String message, final I_M_ShipmentSchedule_QtyPicked alloc)
	{
		String prefix = (message == null ? "" : message)
				+ "\n Shipment schedule: " + shipmentSchedule
				+ "\n LU: " + luHU
				+ "\n TU:" + tuHU
				+ "\n VHU: " + vhu;

		prefix += "\n Alloc: " + alloc;

		Assert.assertNotNull(prefix + " alloc null", alloc);

		prefix += "\n\nInvalid ";

		if (shipmentSchedule != null)
		{
			assertModelEquals(prefix + "Shipment schedule", shipmentSchedule, alloc.getM_ShipmentSchedule());
		}

		if (qtyPicked != null)
		{
			assertEquals(prefix + "QtyPicked", qtyPicked, alloc.getQtyPicked());
		}

		if (inoutLineSet)
		{
			assertModelEquals(prefix + "M_InOutLine_ID", inoutLine, alloc.getM_InOutLine());
		}

		if (luHU != null)
		{
			assertModelEquals(prefix + "LU", luHU.getValue(), alloc.getM_LU_HU());
		}
		if (tuHU != null)
		{
			assertModelEquals(prefix + "TU", tuHU.getValue(), alloc.getM_TU_HU());
		}
		if (vhu != null)
		{
			assertModelEquals(prefix + "VHU", vhu.getValue(), alloc.getVHU());
		}
		return this;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> assertExpected(final String message)
	{
		final I_M_ShipmentSchedule_QtyPicked alloc = retriveShipmentScheduleQtyPicked(message);
		return assertExpected(message, alloc);
	}

	private I_M_ShipmentSchedule_QtyPicked retriveShipmentScheduleQtyPicked(final String message)
	{
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited);

		if (shipmentSchedule != null)
		{
			queryBuilder.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID());
		}
		if (tuHU != null)
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, tuHU.getValue().getM_HU_ID());
		}
		if (vhu != null)
		{
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, vhu.getValue().getM_HU_ID());
		}

		final IQuery<I_M_ShipmentSchedule_QtyPicked> query = queryBuilder.create();
		final I_M_ShipmentSchedule_QtyPicked alloc = query.firstOnly(I_M_ShipmentSchedule_QtyPicked.class);

		final String prefix = (message == null ? "" : message)
				+ "\nQuery: " + query
				+ "\n\n Error: ";
		Assert.assertNotNull(prefix + "M_ShipmentSchedule_QtyPicked shall exist", alloc);

		return alloc;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> assertExpected_ShipmentScheduleWithHU(
			final String message,
			final ShipmentScheduleWithHU candidate)
	{
		final String prefix = (message == null ? "" : message)
				+ "\n Candidate: " + candidate
				+ "\n\nInvalid ";

		Assert.assertNotNull(prefix + " candidate not null", candidate);

		if (shipmentSchedule != null)
		{
			assertModelEquals(prefix + "Shipment schedule", shipmentSchedule, candidate.getM_ShipmentSchedule());
		}

		if (qtyPicked != null)
		{
			assertEquals(prefix + "QtyPicked", qtyPicked, candidate.getQtyPicked());
		}

		if (inoutLineSet)
		{
			assertModelEquals(prefix + "M_InOutLine_ID", inoutLine, candidate.getM_InOutLine());
		}

		if (luHU != null)
		{
			assertModelEquals(prefix + "LU", luHU.getValue(), candidate.getM_LU_HU());
		}
		if (tuHU != null)
		{
			assertModelEquals(prefix + "TU", tuHU.getValue(), candidate.getM_TU_HU());
		}
		if (vhu != null)
		{
			assertModelEquals(prefix + "VHU", vhu.getValue(), candidate.getVHU());
		}

		return this;
	}

	public I_M_ShipmentSchedule_QtyPicked createM_ShipmentSchedule_QtyPicked(final IContextAware context)
	{
		final I_M_ShipmentSchedule_QtyPicked alloc = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class, context);
		alloc.setM_ShipmentSchedule(shipmentSchedule);
		alloc.setM_LU_HU(luHU == null ? null : luHU.getValue());
		alloc.setM_TU_HU(tuHU == null ? null : tuHU.getValue());
		alloc.setVHU(vhu == null ? null : vhu.getValue());
		alloc.setQtyPicked(qtyPicked);
		InterfaceWrapperHelper.save(alloc);

		// Make sure our alloc is valid
		assertExpected("created alloc");

		return alloc;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> shipmentSchedule(final I_M_ShipmentSchedule shipmentSchedule)
	{
		this.shipmentSchedule = shipmentSchedule;
		return this;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> lu(final I_M_HU luHU)
	{
		return lu(new Mutable<>(luHU));
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> lu(final IReference<I_M_HU> luHU)
	{
		this.luHU = luHU;
		return this;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> noLU()
	{
		final I_M_HU lu = null;
		return lu(lu);
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> tu(final I_M_HU tuHU)
	{
		return tu(new Mutable<>(tuHU));
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> noTU()
	{
		final I_M_HU tu = null;
		return tu(tu);
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> tu(final IReference<I_M_HU> tuHU)
	{
		this.tuHU = tuHU;
		return this;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> vhu(final I_M_HU vhu)
	{
		return vhu(new Mutable<>(vhu));
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> vhu(final IReference<I_M_HU> vhu)
	{
		this.vhu = vhu;
		return this;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> noHUs()
	{
		final I_M_HU nullHU = null;
		lu(nullHU);
		tu(nullHU);
		vhu(nullHU);

		return this;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> qtyPicked(final BigDecimal qtyPicked)
	{
		this.qtyPicked = qtyPicked;
		return this;
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> qtyPicked(final String qtyPickedStr)
	{
		return qtyPicked(new BigDecimal(qtyPickedStr));
	}

	public ShipmentScheduleQtyPickedExpectation<ParentExpectationType> inoutLine(final I_M_InOutLine inoutLine)
	{
		this.inoutLine = inoutLine;
		this.inoutLineSet = true;
		return this;
	}
}
