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
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.test.ErrorMessage;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;

/**
 * Note that this is about the weight M_HU_Attributes. Not the storage per-se.
 */
public class HUWeightsExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	// expectations
	private BigDecimal weightGross;
	private BigDecimal weightNet;
	private BigDecimal weightTare;
	private BigDecimal weightTareAdjust;
	private List<HUReceiptScheduleAllocExpectations<HUWeightsExpectation<ParentExpectationType>>> huReceiptScheduleAllocExpectationsList = null;

	public static final BigDecimal DEFAULT_AcceptableWeightErrorMargin = BigDecimal.valueOf(0.001);

	public static final HUWeightsExpectation<Object> newExpectation()
	{
		return new HUWeightsExpectation<>();
	}

	public HUWeightsExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
		setErrorMargin(DEFAULT_AcceptableWeightErrorMargin);
	}

	public HUWeightsExpectation()
	{
		this(null);
	}

	/**
	 * Convenient constructor.
	 */
	public HUWeightsExpectation(final String weightGross,
			final String weightNet,
			final String weightTare,
			final String weightTareAdjust)
	{
		this();
		gross(weightGross);
		net(weightNet);
		tare(weightTare);
		tareAdjust(weightTareAdjust);
	}

	/**
	 * Set expectation values by copying them from given expectation.
	 */
	public HUWeightsExpectation<ParentExpectationType> copyFrom(final HUWeightsExpectation<?> from)
	{
		gross(from.getWeightGross());
		net(from.getWeightNet());
		tare(from.getWeightTare());
		tareAdjust(from.getWeightTareAdjust());

		return this;
	}

	public HUWeightsExpectation<ParentExpectationType> assertExpectedForTU(final ErrorMessage message, final I_M_HU tuHU, final IAttributeStorage attributeStorage)
	{
		// Assert expected weights
		assertExpected(message, attributeStorage);

		//
		// Assert valid Receipt Schedule allocations
		if (huReceiptScheduleAllocExpectationsList != null)
		{
			for (final HUReceiptScheduleAllocExpectations<HUWeightsExpectation<ParentExpectationType>> huReceiptScheduleAllocExpectations : huReceiptScheduleAllocExpectationsList)
			{
				huReceiptScheduleAllocExpectations.assertExpectedForTU(message, tuHU);
			}
		}

		return this;
	}

	public HUWeightsExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final IAttributeStorage attributeStorage)
	{
		final ErrorMessage messageToUse = derive(message)
				.addContextInfo(attributeStorage);

		assertNotNull(messageToUse.expect("attributeStorage not null"), attributeStorage);

		final IWeightable weightable = Weightables.wrap(attributeStorage);
		return assertExpected(messageToUse, weightable);
	}

	public HUWeightsExpectation<ParentExpectationType> assertExpected(final String message, final IAttributeStorage attributeStorage)
	{
		return assertExpected(newErrorMessage(message), attributeStorage);
	}

	public HUWeightsExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final IWeightable weightable)
	{
		final ErrorMessage messageToUse = derive(message)
				.addContextInfo("Weightable", weightable);

		assertNotNull(messageToUse.expect("weightable not null"), weightable);

		if (weightGross != null)
		{
			assertCloseToExpected(messageToUse.expect("WeightGross"), weightGross, weightable.getWeightGross());
		}
		if (weightNet != null)
		{
			assertCloseToExpected(messageToUse.expect("WeightNet"), weightNet, weightable.getWeightNet());
		}
		if (weightTare != null)
		{
			assertCloseToExpected(messageToUse.expect("WeightTare"), weightTare, weightable.getWeightTare());
		}
		if (weightTareAdjust != null)
		{
			assertCloseToExpected(messageToUse.expect("WeightTareAdjust"), weightTareAdjust, weightable.getWeightTareAdjust());
		}

		return this;
	}

	public HUWeightsExpectation<ParentExpectationType> gross(final BigDecimal weightGross)
	{
		this.weightGross = weightGross;
		return this;
	}

	public HUWeightsExpectation<ParentExpectationType> gross(final String weightGross)
	{
		return gross(new BigDecimal(weightGross));
	}

	public BigDecimal getWeightGross()
	{
		return weightGross;
	}

	public HUWeightsExpectation<ParentExpectationType> net(final BigDecimal weightNet)
	{
		this.weightNet = weightNet;
		return this;
	}

	public HUWeightsExpectation<ParentExpectationType> net(final String weightNet)
	{
		return net(new BigDecimal(weightNet));
	}

	public BigDecimal getWeightNet()
	{
		return weightNet;
	}

	public HUWeightsExpectation<ParentExpectationType> tare(final BigDecimal weightTare)
	{
		this.weightTare = weightTare;
		return this;
	}

	public HUWeightsExpectation<ParentExpectationType> tare(final String weightTare)
	{
		return tare(new BigDecimal(weightTare));
	}

	public BigDecimal getWeightTare()
	{
		return weightTare;
	}

	public HUWeightsExpectation<ParentExpectationType> tareAdjust(final BigDecimal weightTareAdjust)
	{
		this.weightTareAdjust = weightTareAdjust;
		return this;
	}

	public HUWeightsExpectation<ParentExpectationType> tareAdjust(final String weightTareAdjust)
	{
		return tareAdjust(new BigDecimal(weightTareAdjust));
	}

	public BigDecimal getWeightTareAdjust()
	{
		return weightTareAdjust;
	}

	public HUReceiptScheduleAllocExpectations<HUWeightsExpectation<ParentExpectationType>> newHUReceiptScheduleAllocExpectations()
	{
		final HUReceiptScheduleAllocExpectations<HUWeightsExpectation<ParentExpectationType>> expectation = new HUReceiptScheduleAllocExpectations<>(this);

		if (huReceiptScheduleAllocExpectationsList == null)
		{
			huReceiptScheduleAllocExpectationsList = new ArrayList<>();
		}
		huReceiptScheduleAllocExpectationsList.add(expectation);

		return expectation;
	}

	public HUWeightsExpectation<ParentExpectationType> receiptScheduleHUQtyAllocated(final I_M_ReceiptSchedule receiptSchedule, final BigDecimal huQtyAllocated)
	{
		return this;
	}
}
