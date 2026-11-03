package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonInventoryCandidateExpectation;
import de.metas.frontend_testing.expectations.request.JsonProductExpectation;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_Inventory_Candidate;
import com.google.common.collect.ImmutableMap;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** The matchers the HU Manager "create from label" specs use: {@code inventoryCandidates} (Dispose) and {@code products} (a refused scan booked nothing). */
class AssertCreatedFromLabelExpectationsTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static I_M_Inventory_Candidate candidate(final String qtyToDispose, final boolean isWholeHU)
	{
		final I_M_Inventory_Candidate record = InterfaceWrapperHelper.newInstance(I_M_Inventory_Candidate.class);
		record.setQtyToDispose(new BigDecimal(qtyToDispose));
		record.setIsWholeHU(isWholeHU);
		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}

	@Test
	void inventoryCandidates_matching_passes()
	{
		final List<I_M_Inventory_Candidate> candidates = Arrays.asList(candidate("1.000", true));

		assertThatCode(() -> AssertInventoryCandidateExpectationsCommand.assertCandidates(candidates,
				JsonInventoryCandidateExpectation.builder().count(1).qtyToDispose(new BigDecimal("1")).isWholeHU(true).build()))
				.doesNotThrowAnyException();
	}

	@Test
	void inventoryCandidates_noRow_failsEvenForIsWholeHU()
	{
		assertThatThrownBy(() -> AssertInventoryCandidateExpectationsCommand.assertCandidates(Arrays.asList(),
				JsonInventoryCandidateExpectation.builder().isWholeHU(true).build()))
				.isInstanceOf(Throwable.class);
	}

	@Test
	void inventoryCandidates_wrongQty_fails()
	{
		final List<I_M_Inventory_Candidate> candidates = Arrays.asList(candidate("2", true));

		assertThatThrownBy(() -> AssertInventoryCandidateExpectationsCommand.assertCandidates(candidates,
				JsonInventoryCandidateExpectation.builder().qtyToDispose(new BigDecimal("1")).build()))
				.isInstanceOf(Throwable.class);
	}

	@Test
	void products_counts()
	{
		assertThatCode(() -> AssertProductExpectationsCommand.assertCounts(0, 0,
				JsonProductExpectation.builder().huCount(0).inventoryLineCount(0).build()))
				.doesNotThrowAnyException();

		assertThatThrownBy(() -> AssertProductExpectationsCommand.assertCounts(1, 0,
				JsonProductExpectation.builder().huCount(0).build()))
				.isInstanceOf(Throwable.class);

		// failure scenario: a refused scan leaves an inventory line behind although no HU exists
		assertThatThrownBy(() -> AssertProductExpectationsCommand.assertCounts(0, 1,
				JsonProductExpectation.builder().huCount(0).inventoryLineCount(0).build()))
				.isInstanceOf(Throwable.class);
	}

	private static I_M_InventoryLine inventoryLineWithAsi(final int asiId)
	{
		final I_M_InventoryLine line = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
		line.setM_AttributeSetInstance_ID(asiId);
		InterfaceWrapperHelper.saveRecord(line);
		return line;
	}

	private static ImmutableAttributeSet lotAttributes(final String lot)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute.setValue("Lot-Nummer");
		attribute.setName("Lot-Nummer");
		attribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		InterfaceWrapperHelper.saveRecord(attribute);
		return ImmutableAttributeSet.builder().attributeValue(attribute, lot).build();
	}

	@Test
	void inventoryLineAttributes_matching_passes()
	{
		final ImmutableAttributeSet asi = lotAttributes("L2311");
		final List<I_M_InventoryLine> lines = Arrays.asList(inventoryLineWithAsi(1000));

		assertThatCode(() -> AssertInventoryExpectationsCommand.assertLineAttributes(lines, ImmutableMap.of("Lot-Nummer", "L2311"), asiId -> asi))
				.doesNotThrowAnyException();
	}

	/** failure scenario: the booking left the lot off the inventory line (the ASI is empty) */
	@Test
	void inventoryLineAttributes_lotMissingOnLine_fails()
	{
		final List<I_M_InventoryLine> lines = Arrays.asList(inventoryLineWithAsi(0));

		assertThatThrownBy(() -> AssertInventoryExpectationsCommand.assertLineAttributes(lines, ImmutableMap.of("Lot-Nummer", "L2311"), asiId -> ImmutableAttributeSet.EMPTY))
				.isInstanceOf(Throwable.class);
	}

	@Test
	void inventoryLineAttributes_wrongValue_fails()
	{
		final ImmutableAttributeSet asi = lotAttributes("OTHER");
		final List<I_M_InventoryLine> lines = Arrays.asList(inventoryLineWithAsi(1000));

		assertThatThrownBy(() -> AssertInventoryExpectationsCommand.assertLineAttributes(lines, ImmutableMap.of("Lot-Nummer", "L2311"), asiId -> asi))
				.isInstanceOf(Throwable.class);
	}
}
