package de.metas.calendar.simulation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationIdsPredicateTest
{
	final SimulationPlanId SIMULATION1 = SimulationPlanId.ofRepoId(1);

	@Test
	void only()
	{
		assertThat(SimulationIdsPredicate.only(SIMULATION1).toCollection()).containsExactly(SIMULATION1);
		assertThat(SimulationIdsPredicate.only(null)).isSameAs(SimulationIdsPredicate.ACTUAL_DATA_ONLY);
	}

	@Test
	void actualDataAnd()
	{
		assertThat(SimulationIdsPredicate.actualDataAnd(SIMULATION1).toCollection()).containsExactlyInAnyOrder(SIMULATION1, null);
		assertThat(SimulationIdsPredicate.actualDataAnd(null)).isSameAs(SimulationIdsPredicate.ACTUAL_DATA_ONLY);
	}

	@Test
	void ACTUAL_DATA_ONLY_constant()
	{
		assertThat(SimulationIdsPredicate.ACTUAL_DATA_ONLY.toCollection()).isEqualTo(Collections.singletonList(null));
	}
}