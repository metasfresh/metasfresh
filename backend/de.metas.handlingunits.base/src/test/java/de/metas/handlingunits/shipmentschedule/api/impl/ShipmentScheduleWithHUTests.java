package de.metas.handlingunits.shipmentschedule.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IAttributeValueListener;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.compiere.util.NamePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ShipmentScheduleWithHUTests
{

	private PlainContextAware contextProvider;

	private static final String typeLU = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;
	private static final String typeTU = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;

	private ProductId productId;

	private LUTUProducerDestinationTestSupport testSupport;

	private IHUContext huContext;

	private StockQtyAndUOMQty ninetyNineNoCatch;

	@BeforeEach
	public void init()
	{
		this.testSupport = new LUTUProducerDestinationTestSupport();
		contextProvider = PlainContextAware.newOutOfTrx(Env.getCtx());

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(testSupport.helper.uomEach.getC_UOM_ID());
		saveRecord(product);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		huContext = Services.get(IHUContextFactory.class).createMutableHUContext();

		ninetyNineNoCatch = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("99"), productId);
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Complete()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(
						huContext,
						schedule,
						ninetyNineNoCatch,
						M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo("2");
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Reverse()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Reversed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(
						huContext,
						schedule,
						ninetyNineNoCatch,
						M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ZERO);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ZERO);
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Draft()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Drafted);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(
						huContext,
						schedule,
						ninetyNineNoCatch,
						M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ZERO);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ZERO);
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Void()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Voided);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(
						huContext,
						schedule,
						ninetyNineNoCatch,
						M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ZERO);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ZERO);
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Closed()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Closed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(
						huContext,
						schedule,
						ninetyNineNoCatch,
						M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo("2");
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveLUTU_1TU()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 1);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(-99)); // this doesn't matter. the HUs are counted

		final I_M_HU lu = createHU(typeLU);
		final I_M_HU tu = createHU(typeTU);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleQtyPicked(
						createShipmentScheduleQtyPicked(schedule, iol, lu, tu),
						huContext)
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ONE);

		// assertThat(iol.getQtyEnteredTU(), comparesEqualTo(BigDecimal.ONE)); iol.getQtyEnteredTU() is not updated by the allocaction
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveLUTU_2TU()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2)); // this doesn't matter. the HUs are counted

		final I_M_HU lu = createHU(typeLU);
		final I_M_HU tu = createHU(typeTU);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleQtyPicked(
						createShipmentScheduleQtyPicked(schedule, iol, lu, tu),
						huContext)
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ONE);
	}

	@Test
	public void testupdateHUDeliveryQuantities_LU_With_AggregatedTU()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 48);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2)); // this doesn't matter. the HUs are counted

		final IPair<I_M_HU, I_M_HU> luAndTU = createLUAndTU(48);
		final I_M_HU lu = luAndTU.getLeft();
		final I_M_HU tu = luAndTU.getRight();

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleQtyPicked(
						createShipmentScheduleQtyPicked(schedule, iol, lu, tu),
						huContext)
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo("48");
	}

	private I_M_HU createHU(final String unitType)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, contextProvider);

		final I_M_HU_PI_Version version = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class, contextProvider);
		version.setHU_UnitType(unitType);
		InterfaceWrapperHelper.save(version);

		hu.setM_HU_PI_Version(version);

		InterfaceWrapperHelper.save(hu);

		return hu;
	}

	private IPair<I_M_HU, I_M_HU> createLUAndTU(final int qtyTU)
	{
		final I_M_HU lu = testSupport.createLU(qtyTU, 5);
		final List<I_M_HU> tus = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(lu);
		final I_M_HU aggregatedTU = CollectionUtils.singleElement(tus);

		return ImmutablePair.of(lu, aggregatedTU);
	}

	private I_M_ShipmentSchedule_QtyPicked createShipmentScheduleQtyPicked(
			I_M_ShipmentSchedule schedule,
			I_M_InOutLine iol,
			final I_M_HU lu,
			final I_M_HU tu)

	{
		final I_M_ShipmentSchedule_QtyPicked alloc = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class, contextProvider);

		alloc.setM_LU_HU(lu);
		alloc.setM_TU_HU(tu);
		alloc.setM_InOutLine(iol);
		alloc.setM_ShipmentSchedule(schedule);

		InterfaceWrapperHelper.save(alloc);

		return alloc;
	}

	private I_M_InOutLine createInOutLine(I_M_InOut io, final BigDecimal qtyEnteredTU)
	{
		final I_M_InOutLine iol = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, contextProvider);

		iol.setM_InOut(io);
		iol.setQtyEnteredTU(qtyEnteredTU);

		InterfaceWrapperHelper.save(iol);

		return iol;

	}

	private I_M_InOut createInOut(final String docStatus)
	{
		final I_M_InOut io = InterfaceWrapperHelper.newInstance(I_M_InOut.class, contextProvider);

		io.setDocStatus(docStatus);

		InterfaceWrapperHelper.save(io);

		return io;
	}

	private I_M_ShipmentSchedule createScheduleWithQtyOrderedLUandQtyOrderedTU(
			final int qtyOrderedLU,
			final int qtyOrderedTU)
	{
		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, contextProvider);
		sched.setM_Product_ID(productId.getRepoId());
		sched.setQtyOrdered_LU(new BigDecimal(qtyOrderedLU));
		sched.setQtyOrdered_TU(new BigDecimal(qtyOrderedTU));

		InterfaceWrapperHelper.save(sched);
		return sched;
	}

	// -------------------------------------------------------------------
	// Tests for mergeAttributeValues
	// -------------------------------------------------------------------

	@Nested
	class MergeAttributeValuesTests
	{
		@Test
		void schedASI_takes_precedence_over_HU()
		{
			final IAttributeValue huAttr = stubAttributeValue(1, "P17", "", true);
			final IAttributeValue schedAttr = stubAttributeValue(1, "P13", "", true);

			final ImmutableList<IAttributeValue> result = ShipmentScheduleWithHU.mergeAttributeValues(
					Arrays.asList(huAttr),
					Arrays.asList(schedAttr));

			assertThat(result).hasSize(1);
			assertThat(result.get(0).getValue()).isEqualTo("P13");
		}

		@Test
		void schedASI_without_HU()
		{
			final IAttributeValue schedAttr = stubAttributeValue(1, "P13", "", true);

			final ImmutableList<IAttributeValue> result = ShipmentScheduleWithHU.mergeAttributeValues(
					Collections.emptyList(),
					Arrays.asList(schedAttr));

			assertThat(result).hasSize(1);
			assertThat(result.get(0).getValue()).isEqualTo("P13");
		}

		@Test
		void emptySchedASI_falls_back_to_HU()
		{
			final IAttributeValue huAttr = stubAttributeValue(1, "P17", "", true);
			// schedule ASI value equals the empty value -> should be treated as empty
			final IAttributeValue schedAttr = stubAttributeValue(1, "", "", true);

			final ImmutableList<IAttributeValue> result = ShipmentScheduleWithHU.mergeAttributeValues(
					Arrays.asList(huAttr),
					Arrays.asList(schedAttr));

			assertThat(result).hasSize(1);
			assertThat(result.get(0).getValue()).isEqualTo("P17");
		}

		@Test
		void emptySchedASI_and_no_HU()
		{
			// schedule ASI value equals the empty value, and no HU attribute -> nothing in result
			final IAttributeValue schedAttr = stubAttributeValue(1, "", "", true);

			final ImmutableList<IAttributeValue> result = ShipmentScheduleWithHU.mergeAttributeValues(
					Collections.emptyList(),
					Arrays.asList(schedAttr));

			assertThat(result).isEmpty();
		}

		@Test
		void noSchedASI_uses_HU()
		{
			final IAttributeValue huAttr = stubAttributeValue(1, "P17", "", true);

			final ImmutableList<IAttributeValue> result = ShipmentScheduleWithHU.mergeAttributeValues(
					Arrays.asList(huAttr),
					Collections.emptyList());

			assertThat(result).hasSize(1);
			assertThat(result.get(0).getValue()).isEqualTo("P17");
		}

		@Test
		void partial_empty_merges()
		{
			// Attribute A (id=1): schedASI="P13", HU="P17" -> schedASI wins
			// Attribute B (id=2): schedASI=empty, HU="K1" -> HU wins (fallback)
			final IAttributeValue huAttrA = stubAttributeValue(1, "P17", "", true);
			final IAttributeValue huAttrB = stubAttributeValue(2, "K1", "", true);
			final IAttributeValue schedAttrA = stubAttributeValue(1, "P13", "", true);
			final IAttributeValue schedAttrB = stubAttributeValue(2, "", "", true);

			final ImmutableList<IAttributeValue> result = ShipmentScheduleWithHU.mergeAttributeValues(
					Arrays.asList(huAttrA, huAttrB),
					Arrays.asList(schedAttrA, schedAttrB));

			assertThat(result).hasSize(2);

			// Find by attribute ID and verify values
			final IAttributeValue resultA = result.stream()
					.filter(av -> av.getM_Attribute().getM_Attribute_ID() == 1)
					.findFirst().orElseThrow(() -> new AssertionError("Attribute A not found"));
			final IAttributeValue resultB = result.stream()
					.filter(av -> av.getM_Attribute().getM_Attribute_ID() == 2)
					.findFirst().orElseThrow(() -> new AssertionError("Attribute B not found"));

			assertThat(resultA.getValue()).isEqualTo("P13");
			assertThat(resultB.getValue()).isEqualTo("K1");
		}

		@Test
		void schedASI_with_null_value_falls_back_to_HU()
		{
			final IAttributeValue huAttr = stubAttributeValue(1, "P17", null, true);
			// schedule ASI value is null, empty value is also null -> treated as empty
			final IAttributeValue schedAttr = stubAttributeValue(1, null, null, true);

			final ImmutableList<IAttributeValue> result = ShipmentScheduleWithHU.mergeAttributeValues(
					Arrays.asList(huAttr),
					Arrays.asList(schedAttr));

			assertThat(result).hasSize(1);
			assertThat(result.get(0).getValue()).isEqualTo("P17");
		}

		/**
		 * Creates a stub {@link IAttributeValue} with the given attribute ID, value, empty value, and useInASI flag.
		 * Uses POJOWrapper-backed {@link I_M_Attribute} instances from the test environment.
		 */
		private IAttributeValue stubAttributeValue(
				final int attributeId,
				@Nullable final Object value,
				@Nullable final Object emptyValue,
				final boolean useInASI)
		{
			final I_M_Attribute attribute = newInstance(I_M_Attribute.class);
			attribute.setM_Attribute_ID(attributeId);
			attribute.setValue("Attr" + attributeId);
			attribute.setName("Attr" + attributeId);
			saveRecord(attribute);

			return new StubAttributeValue(attribute, value, emptyValue, useInASI);
		}
	}

	/**
	 * Minimal stub implementation of {@link IAttributeValue} for unit-testing the merge logic.
	 * Only the methods actually used by {@link ShipmentScheduleWithHU#mergeAttributeValues} are implemented;
	 * the rest throw {@link UnsupportedOperationException}.
	 */
	private static class StubAttributeValue implements IAttributeValue
	{
		private final I_M_Attribute attribute;
		private final Object value;
		private final Object emptyValue;
		private final boolean useInASI;

		StubAttributeValue(
				@NonNull final I_M_Attribute attribute,
				@Nullable final Object value,
				@Nullable final Object emptyValue,
				final boolean useInASI)
		{
			this.attribute = attribute;
			this.value = value;
			this.emptyValue = emptyValue;
			this.useInASI = useInASI;
		}

		@Override public I_M_Attribute getM_Attribute() { return attribute; }
		@Override public Object getValue() { return value; }
		@Override @Nullable public Object getEmptyValue() { return emptyValue; }
		@Override public boolean isUseInASI() { return useInASI; }
		@Override public boolean isEmpty() { return java.util.Objects.equals(value, emptyValue); }
		@Override public AttributeCode getAttributeCode() { return AttributeCode.ofString(attribute.getValue()); }

		// --- Below: not needed for the merge logic, throw UnsupportedOperationException ---
		@Override public String getAttributeValueType() { throw new UnsupportedOperationException(); }
		@Override public void setValue(final IAttributeValueContext ctx, final Object val) { throw new UnsupportedOperationException(); }
		@Override public Object getValueInitial() { throw new UnsupportedOperationException(); }
		@Override public void setValueInitial(final Object valueInitial) { throw new UnsupportedOperationException(); }
		@Override public BigDecimal getValueAsBigDecimal() { throw new UnsupportedOperationException(); }
		@Override public int getValueAsInt() { throw new UnsupportedOperationException(); }
		@Override public BigDecimal getValueInitialAsBigDecimal() { throw new UnsupportedOperationException(); }
		@Override public String getValueAsString() { throw new UnsupportedOperationException(); }
		@Override public String getValueInitialAsString() { throw new UnsupportedOperationException(); }
		@Override public Date getValueAsDate() { throw new UnsupportedOperationException(); }
		@Override public Date getValueInitialAsDate() { throw new UnsupportedOperationException(); }
		@Override public boolean isNumericValue() { throw new UnsupportedOperationException(); }
		@Override public boolean isStringValue() { throw new UnsupportedOperationException(); }
		@Override public boolean isDateValue() { throw new UnsupportedOperationException(); }
		@Override public String getPropagationType() { throw new UnsupportedOperationException(); }
		@Override public IAttributeAggregationStrategy retrieveAggregationStrategy() { throw new UnsupportedOperationException(); }
		@Override public IAttributeSplitterStrategy retrieveSplitterStrategy() { throw new UnsupportedOperationException(); }
		@Override public IAttributeValueCallout getAttributeValueCallout() { throw new UnsupportedOperationException(); }
		@Override public IAttributeValueGenerator getAttributeValueGeneratorOrNull() { throw new UnsupportedOperationException(); }
		@Override public IHUAttributeTransferStrategy retrieveTransferStrategy() { throw new UnsupportedOperationException(); }
		@Override public void addAttributeValueListener(final IAttributeValueListener listener) { throw new UnsupportedOperationException(); }
		@Override public void removeAttributeValueListener(final IAttributeValueListener listener) { throw new UnsupportedOperationException(); }
		@Override public boolean isList() { throw new UnsupportedOperationException(); }
		@Override public List<? extends NamePair> getAvailableValues() { throw new UnsupportedOperationException(); }
		@Override public IAttributeValuesProvider getAttributeValuesProvider() { throw new UnsupportedOperationException(); }
		@Override public I_C_UOM getC_UOM() { throw new UnsupportedOperationException(); }
		@Override public boolean isReadonlyUI() { throw new UnsupportedOperationException(); }
		@Override public boolean isDisplayedUI() { throw new UnsupportedOperationException(); }
		@Override public boolean isMandatory() { throw new UnsupportedOperationException(); }
		@Override public boolean isOnlyIfInProductAttributeSet() { throw new UnsupportedOperationException(); }
		@Override public int getDisplaySeqNo() { throw new UnsupportedOperationException(); }
		@Override public boolean isDefinedByTemplate() { throw new UnsupportedOperationException(); }
		@Override public NamePair getNullAttributeValue() { throw new UnsupportedOperationException(); }
		@Override public boolean isNew() { throw new UnsupportedOperationException(); }
	}
}
