package de.metas.invoicecandidate.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.project.ProjectId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

/**
 * Tests for {@link InvoiceCandBL#extractCommonProjectId(Collection)}.
 * <p>
 * The method extracts a common projectId from invoice candidates. Nulls are considered to match any projectId.
 * <p>
 * Test cases:
 * <ul>
 *     <li>(G1, null) => G1 - Nulls don't prevent inheritance</li>
 *     <li>(G1, G2) => null - Different projects return empty</li>
 *     <li>(null, null) => null - All nulls return empty</li>
 * </ul>
 */
class InvoiceCandBL_extractCommonProjectId_Test
{
	private static final ProjectId PROJECT_ID_1 = ProjectId.ofRepoId(100);
	private static final ProjectId PROJECT_ID_2 = ProjectId.ofRepoId(200);

	private InvoiceCandBL invoiceCandBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		invoiceCandBL = new InvoiceCandBL();
	}

	/**
	 * Case 1: (G1, null) => G1
	 * <p>
	 * When some candidates have a project and others have null,
	 * the common project should be extracted.
	 * Nulls should not prevent project inheritance.
	 */
	@Test
	void extractCommonProjectId_oneProjectWithNulls_returnsProject()
	{
		// given
		final I_C_Invoice_Candidate icWithProject1 = createInvoiceCandidate(PROJECT_ID_1);
		final I_C_Invoice_Candidate icWithNull1 = createInvoiceCandidate(null);
		final I_C_Invoice_Candidate icWithProject2 = createInvoiceCandidate(PROJECT_ID_1);
		final I_C_Invoice_Candidate icWithNull2 = createInvoiceCandidate(null);

		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(
				icWithProject1,
				icWithNull1,
				icWithProject2,
				icWithNull2
		);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Case 1 variant: Single project with null
	 * <p>
	 * When only one candidate has a project and another has null,
	 * the project should be extracted.
	 */
	@Test
	void extractCommonProjectId_singleProjectWithNull_returnsProject()
	{
		// given
		final I_C_Invoice_Candidate icWithProject = createInvoiceCandidate(PROJECT_ID_1);
		final I_C_Invoice_Candidate icWithNull = createInvoiceCandidate(null);

		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(
				icWithProject,
				icWithNull
		);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Case 1 variant: All same project (no nulls)
	 * <p>
	 * When all candidates have the same project, it should be extracted.
	 */
	@Test
	void extractCommonProjectId_allSameProject_returnsProject()
	{
		// given
		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(PROJECT_ID_1);
		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(PROJECT_ID_1);
		final I_C_Invoice_Candidate ic3 = createInvoiceCandidate(PROJECT_ID_1);

		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(ic1, ic2, ic3);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Case 1 variant: Single candidate with project
	 * <p>
	 * When there's only one candidate with a project, it should be extracted.
	 */
	@Test
	void extractCommonProjectId_singleCandidateWithProject_returnsProject()
	{
		// given
		final I_C_Invoice_Candidate ic = createInvoiceCandidate(PROJECT_ID_1);
		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(ic);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Case 2: (G1, G2) => null
	 * <p>
	 * When candidates reference different projects,
	 * no common project can be extracted.
	 */
	@Test
	void extractCommonProjectId_multipleProjects_returnsEmpty()
	{
		// given
		final I_C_Invoice_Candidate icWithProject1 = createInvoiceCandidate(PROJECT_ID_1);
		final I_C_Invoice_Candidate icWithProject2 = createInvoiceCandidate(PROJECT_ID_2);

		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(
				icWithProject1,
				icWithProject2
		);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isEmpty();
	}

	/**
	 * Case 2 variant: Multiple projects with nulls
	 * <p>
	 * When candidates reference different projects (even with nulls present),
	 * no common project can be extracted.
	 */
	@Test
	void extractCommonProjectId_multipleProjectsWithNulls_returnsEmpty()
	{
		// given
		final I_C_Invoice_Candidate icWithProject1 = createInvoiceCandidate(PROJECT_ID_1);
		final I_C_Invoice_Candidate icWithNull = createInvoiceCandidate(null);
		final I_C_Invoice_Candidate icWithProject2 = createInvoiceCandidate(PROJECT_ID_2);

		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(
				icWithProject1,
				icWithNull,
				icWithProject2
		);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isEmpty();
	}

	/**
	 * Case 2 variant: Three different projects
	 * <p>
	 * When candidates reference multiple different projects,
	 * no common project can be extracted.
	 */
	@Test
	void extractCommonProjectId_threeDifferentProjects_returnsEmpty()
	{
		// given
		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(PROJECT_ID_1);
		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(PROJECT_ID_2);
		final I_C_Invoice_Candidate ic3 = createInvoiceCandidate(ProjectId.ofRepoId(300));

		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(ic1, ic2, ic3);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isEmpty();
	}

	/**
	 * Case 3: (null, null) => null
	 * <p>
	 * When all candidates have null projects,
	 * no common project can be extracted.
	 */
	@Test
	void extractCommonProjectId_allNulls_returnsEmpty()
	{
		// given
		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(null);
		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(null);
		final I_C_Invoice_Candidate ic3 = createInvoiceCandidate(null);

		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(ic1, ic2, ic3);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isEmpty();
	}

	/**
	 * Case 3 variant: Single candidate with null
	 * <p>
	 * When there's only one candidate with null project,
	 * no common project can be extracted.
	 */
	@Test
	void extractCommonProjectId_singleCandidateWithNull_returnsEmpty()
	{
		// given
		final I_C_Invoice_Candidate ic = createInvoiceCandidate(null);
		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of(ic);

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isEmpty();
	}

	/**
	 * Edge case: Empty collection
	 * <p>
	 * When no candidates are provided, no common project can be extracted.
	 */
	@Test
	void extractCommonProjectId_emptyCollection_returnsEmpty()
	{
		// given
		final Collection<I_C_Invoice_Candidate> candidates = ImmutableList.of();

		// when
		final Optional<ProjectId> result = invoiceCandBL.extractCommonProjectId(candidates);

		// then
		assertThat(result).isEmpty();
	}

	/**
	 * Helper method to create an invoice candidate with the specified project ID.
	 *
	 * @param projectId the project ID to set, or null for no project
	 * @return the created invoice candidate
	 */
	private I_C_Invoice_Candidate createInvoiceCandidate(@Nullable final ProjectId projectId)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setC_Project_ID(ProjectId.toRepoId(projectId));
		return ic;
	}
}
