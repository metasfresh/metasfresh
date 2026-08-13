/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.error.impl;

import de.metas.error.AdIssueId;
import de.metas.error.IssueCreateRequest;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ErrorManagerTest
{

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static I_AD_Issue createIssueAndLoad(final IssueCreateRequest request)
	{
		final AdIssueId issueId = new ErrorManager().createIssue(request);
		return InterfaceWrapperHelper.load(issueId.getRepoId(), I_AD_Issue.class);
	}

	/**
	 * Builds a throwable with a synthetic stacktrace instead of relying on the frames this test actually runs in:
	 * {@code createIssueInTrx} only inspects frames whose {@code toString()} contains {@code "adempiere"}, and a
	 * JUnit-invoked test method produces none.
	 */
	private static Throwable throwableWithStackFrame(
			final String className,
			final String methodName,
			final int lineNo)
	{
		final Throwable throwable = new RuntimeException("boom");
		throwable.setStackTrace(new StackTraceElement[] {
				new StackTraceElement(className, methodName, "SomeSource.java", lineNo) });
		return throwable;
	}

	@Test
	void createIssue()
	{
		final AdIssueId issueId = new ErrorManager().createIssue(new NullPointerException());
		assertThat(issueId).isNotNull();
	}

	/**
	 * The class name and the method name of the failing frame each have to land in their own column. Writing both to
	 * {@code SourceClassName} in sequence overwrites the class name with the method name and leaves
	 * {@code SourceMethodName} empty, which makes both columns useless for triaging AD_Issue.
	 */
	@Test
	void createIssue_splitsSourceClassNameAndSourceMethodName()
	{
		final I_AD_Issue issue = createIssueAndLoad(IssueCreateRequest.builder()
				.throwable(throwableWithStackFrame("org.adempiere.example.SomeService", "doTheThing", 42))
				.build());

		assertThat(issue.getSourceClassName()).isEqualTo("org.adempiere.example.SomeService");
		assertThat(issue.getSourceMethodName()).isEqualTo("doTheThing");
		assertThat(issue.getLineNo()).isEqualTo(42);
	}

	/**
	 * A caller that logs exactly the throwable's own message must not get that message stored twice. Every REST error
	 * takes this path via {@code RestResponseEntityExceptionHandler}, and the message there carries the whole rejected
	 * request payload — so the duplication doubles the largest rows in AD_Issue.
	 */
	@Test
	void createIssue_doesNotDuplicateSummaryThatAlreadyEqualsTheThrowableMessage()
	{
		final I_AD_Issue issue = createIssueAndLoad(IssueCreateRequest.builder()
				.throwable(new RuntimeException("the request payload could not be processed"))
				.summary("the request payload could not be processed")
				.build());

		assertThat(issue.getIssueSummary()).isEqualTo("the request payload could not be processed");
	}

	/** A summary that genuinely adds information is still appended to the throwable's message. */
	@Test
	void createIssue_keepsASummaryThatAddsInformation()
	{
		final I_AD_Issue issue = createIssueAndLoad(IssueCreateRequest.builder()
				.throwable(new RuntimeException("the throwable message"))
				.summary("extra context")
				.build());

		assertThat(issue.getIssueSummary()).isEqualTo("the throwable message extra context");
	}
}
