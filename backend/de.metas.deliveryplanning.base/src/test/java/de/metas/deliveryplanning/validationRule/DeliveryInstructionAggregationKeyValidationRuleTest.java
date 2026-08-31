/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning.validationRule;

import de.metas.shipping.model.I_M_ShipperTransportation;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * That the target picker offers exactly the instructions the admissibility rule would accept — in particular
 * that an EMPTY value is a value, so "both empty" matches and "one empty" does not.
 */
class DeliveryInstructionAggregationKeyValidationRuleTest
{
	private static final int ORG_ID = 1000000;
	private static final int FORWARDER_A = 540001;
	private static final int FORWARDER_B = 540002;
	private static final int LOADING_LOCATION_ID = 540021;
	private static final int DELIVERY_LOCATION_ID = 540022;

	private DeliveryInstructionAggregationKeyValidationRule rule;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		rule = new DeliveryInstructionAggregationKeyValidationRule();
	}

	// ------------------------------------------------------------------ helpers

	/** The selection's condensed values, as the hidden process parameters deliver them to the rule. */
	private static class SelectionContext implements IValidationContext
	{
		private final Map<String, String> values = new HashMap<>();

		SelectionContext with(final String columnName, @Nullable final Object value)
		{
			values.put(columnName, value == null ? null : String.valueOf(value));
			return this;
		}

		@Override
		public String getTableName()
		{
			return I_M_ShipperTransportation.Table_Name;
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			return values.get(variableName);
		}
	}

	private static SelectionContext selectionWithForwarder(final int forwarderId)
	{
		return new SelectionContext()
				.with(I_M_ShipperTransportation.COLUMNNAME_AD_Org_ID, ORG_ID)
				.with(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID, forwarderId)
				.with(I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID, LOADING_LOCATION_ID)
				.with(I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID, DELIVERY_LOCATION_ID);
	}

	/** A candidate the SQL sibling rule has already accepted; only the seven key fields are left to judge. */
	private NamePair candidateInstruction(final int forwarderId, @Nullable final String incotermLocation)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setAD_Org_ID(ORG_ID);
		record.setM_Shipper_ID(forwarderId);
		record.setC_BPartner_Location_Loading_ID(LOADING_LOCATION_ID);
		record.setC_BPartner_Location_Delivery_ID(DELIVERY_LOCATION_ID);
		record.setIncotermLocation(incotermLocation);
		InterfaceWrapperHelper.save(record);
		return new KeyNamePair(record.getM_ShipperTransportation_ID(), "DI");
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("an instruction agreeing on every key field is offered")
	void agreeingInstructionIsOffered()
	{
		assertThat(rule.accept(selectionWithForwarder(FORWARDER_A), candidateInstruction(FORWARDER_A, null)))
				.isTrue();
	}

	@Test
	@DisplayName("an instruction with a different forwarder is not offered")
	void differingForwarderIsNotOffered()
	{
		assertThat(rule.accept(selectionWithForwarder(FORWARDER_A), candidateInstruction(FORWARDER_B, null)))
				.as("offering it would let the planner pick a target the admissibility check then refuses")
				.isFalse();
	}

	@Test
	@DisplayName("empty counts as a value: both empty matches")
	void bothEmptyMatches()
	{
		final SelectionContext selection = selectionWithForwarder(FORWARDER_A)
				.with(I_M_ShipperTransportation.COLUMNNAME_C_Incoterms_ID, null)
				.with(I_M_ShipperTransportation.COLUMNNAME_IncotermLocation, null);

		assertThat(rule.accept(selection, candidateInstruction(FORWARDER_A, null)))
				.as("a selection with no incoterms belongs on an instruction with none either")
				.isTrue();
	}

	@Test
	@DisplayName("empty counts as a value: only the instruction empty does NOT match")
	void onlyTheInstructionEmptyDoesNotMatch()
	{
		final SelectionContext selection = selectionWithForwarder(FORWARDER_A)
				.with(I_M_ShipperTransportation.COLUMNNAME_IncotermLocation, "Hamburg");

		assertThat(rule.accept(selection, candidateInstruction(FORWARDER_A, null)))
				.as("this is the asymmetry a COALESCE-to-blank SQL comparison would silently accept")
				.isFalse();
	}

	@Test
	@DisplayName("empty counts as a value: only the selection empty does NOT match")
	void onlyTheSelectionEmptyDoesNotMatch()
	{
		assertThat(rule.accept(selectionWithForwarder(FORWARDER_A), candidateInstruction(FORWARDER_A, "Hamburg")))
				.isFalse();
	}

	@Test
	@DisplayName("blank and null are the same value, so they match each other")
	void blankAndNullAreTheSameValue()
	{
		final SelectionContext selection = selectionWithForwarder(FORWARDER_A)
				.with(I_M_ShipperTransportation.COLUMNNAME_IncotermLocation, "   ");

		assertThat(rule.accept(selection, candidateInstruction(FORWARDER_A, null))).isTrue();
	}

	@Test
	@DisplayName("without a validation context the list is left as the SQL rule produced it")
	void noContextLeavesTheListAlone()
	{
		assertThat(rule.accept(IValidationContext.DISABLED, candidateInstruction(FORWARDER_A, null)))
				.as("emptying the picker when there is nothing to judge against would look like a broken action")
				.isTrue();
	}

	@Test
	@DisplayName("the rule declares every parameter it reads, so the framework re-evaluates it when one changes")
	void everyParameterRead_isDeclared()
	{
		assertThat(rule.getParameters(null))
				.containsExactlyInAnyOrder(
						I_M_ShipperTransportation.COLUMNNAME_AD_Org_ID,
						I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID,
						I_M_ShipperTransportation.COLUMNNAME_C_Incoterms_ID,
						I_M_ShipperTransportation.COLUMNNAME_IncotermLocation,
						I_M_ShipperTransportation.COLUMNNAME_M_MeansOfTransportation_ID,
						I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID,
						I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID);
	}
}
