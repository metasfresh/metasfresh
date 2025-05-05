package de.metas.invoicecandidate.api;

import com.google.common.collect.ImmutableSet;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.process.PInstanceId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class InvoiceCandidateIdsSelectionTest
{
	@Test
	void isEmpty()
	{
		Assertions.assertThat(InvoiceCandidateIdsSelection.ofIdsSet(ImmutableSet.of()).isEmpty()).isTrue();
		Assertions.assertThat(InvoiceCandidateIdsSelection.ofIdsSet(ImmutableSet.of(InvoiceCandidateId.ofRepoId(1))).isEmpty()).isFalse();
		Assertions.assertThat(InvoiceCandidateIdsSelection.ofSelectionId(PInstanceId.ofRepoId(1)).isEmpty()).isFalse();
	}
}