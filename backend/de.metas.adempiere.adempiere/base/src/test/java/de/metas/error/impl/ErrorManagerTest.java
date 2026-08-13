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
import org.junit.jupiter.api.Nested;
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
		return throwableWithStackFrames(new StackTraceElement(className, methodName, "SomeSource.java", lineNo));
	}

	private static Throwable throwableWithStackFrames(final StackTraceElement... frames)
	{
		final Throwable throwable = new RuntimeException("boom");
		throwable.setStackTrace(frames);
		return throwable;
	}

	@Nested
	class CreateIssue
	{
		@Test
		void returnsAnIssueId()
		{
			final AdIssueId issueId = new ErrorManager().createIssue(new NullPointerException());
			assertThat(issueId).isNotNull();
		}

		/**
		 * The class name and the method name of the failing frame each have to land in their own column. Writing both
		 * to {@code SourceClassName} in sequence overwrites the class name with the method name and leaves
		 * {@code SourceMethodName} empty, which makes both columns useless for triaging AD_Issue.
		 */
		@Test
		void splitsSourceClassNameAndSourceMethodName()
		{
			final I_AD_Issue issue = createIssueAndLoad(IssueCreateRequest.builder()
					.throwable(throwableWithStackFrame("org.adempiere.example.SomeService", "doTheThing", 42))
					.build());

			assertThat(issue.getSourceClassName()).isEqualTo("org.adempiere.example.SomeService");
			assertThat(issue.getSourceMethodName()).isEqualTo("doTheThing");
			assertThat(issue.getLineNo()).isEqualTo(42);
		}

		/**
		 * A caller that logged the throwable's own message must not get that message stored twice. Every REST error
		 * takes this path via {@code RestResponseEntityExceptionHandler}, and the message there carries the whole
		 * rejected request payload — so the duplication doubles the largest rows in AD_Issue.
		 */
		@Test
		void doesNotDuplicateASummaryThatAlreadyEqualsTheThrowableMessage()
		{
			final I_AD_Issue issue = createIssueAndLoad(IssueCreateRequest.builder()
					.throwable(new RuntimeException("the request payload could not be processed"))
					.summary("the request payload could not be processed")
					.build());

			assertThat(issue.getIssueSummary()).isEqualTo("the request payload could not be processed");
		}

		/**
		 * The frame that identifies the failing code is normally a {@code de.metas} one — the legacy
		 * {@code org.adempiere} / {@code org.compiere} packages are mostly framework plumbing now. Selecting frames by
		 * the substring {@code "adempiere"} therefore skips the application code entirely and reports the first
		 * framework frame below it, which is identical for every failure routed through that framework.
		 */
		@Test
		void picksTheApplicationFrameRatherThanTheFrameworkOneBelowIt()
		{
			final I_AD_Issue issue = createIssueAndLoad(IssueCreateRequest.builder()
					.throwable(throwableWithStackFrames(
							new StackTraceElement("de.metas.order.compensationGroup.GroupCompensationAmtType",
									"ofAD_Ref_List_Value", "GroupCompensationAmtType.java", 48),
							new StackTraceElement("de.metas.order.compensationGroup.OrderGroupRepository",
									"toGroupCompensationLine", "OrderGroupRepository.java", 343),
							new StackTraceElement("org.adempiere.ad.callout.api.impl.CalloutExecutor",
									"execute", "CalloutExecutor.java", 258)))
					.build());

			assertThat(issue.getSourceClassName()).isEqualTo("de.metas.order.compensationGroup.GroupCompensationAmtType");
			assertThat(issue.getSourceMethodName()).isEqualTo("ofAD_Ref_List_Value");
			assertThat(issue.getLineNo()).isEqualTo(48);
			assertThat(issue.getErrorTrace())
					.as("the application frames belong in the trace, not just the framework ones")
					.contains("GroupCompensationAmtType.ofAD_Ref_List_Value")
					.contains("OrderGroupRepository.toGroupCompensationLine");
		}

		/** The legacy packages are still metasfresh code and must keep being selected. */
		@Test
		void stillPicksLegacyAdempiereAndCompiereFrames()
		{
			final I_AD_Issue issue = createIssueAndLoad(IssueCreateRequest.builder()
					.throwable(throwableWithStackFrames(
							new StackTraceElement("java.util.Optional", "orElseThrow", "Optional.java", 408),
							new StackTraceElement("org.compiere.model.MTree", "loadNodes", "MTree.java", 290),
							new StackTraceElement("org.adempiere.ad.callout.api.impl.CalloutExecutor",
									"execute", "CalloutExecutor.java", 258)))
					.build());

			assertThat(issue.getSourceClassName()).isEqualTo("org.compiere.model.MTree");
			assertThat(issue.getSourceMethodName()).isEqualTo("loadNodes");
		}

		/** A summary that genuinely adds information is still appended to the throwable's message. */
		@Test
		void keepsASummaryThatAddsInformation()
		{
			final I_AD_Issue issue = createIssueAndLoad(IssueCreateRequest.builder()
					.throwable(new RuntimeException("the throwable message"))
					.summary("extra context")
					.build());

			assertThat(issue.getIssueSummary()).isEqualTo("the throwable message extra context");
		}
	}
}
