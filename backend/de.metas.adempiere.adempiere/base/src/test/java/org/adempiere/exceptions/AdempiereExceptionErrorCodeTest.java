/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.exceptions;

import de.metas.error.AdIssueId;
import de.metas.error.impl.ErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.impl.PlainMsgBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the ErrorCode chain: AD_Message.ErrorCode -> {@link AdempiereException#getErrorCode()} -> AD_Issue.ErrorCode.
 */
class AdempiereExceptionErrorCodeTest
{
	private static final AdMessageKey MSG_WITH_ERROR_CODE = AdMessageKey.of("Test_Message_With_ErrorCode");
	private static final String ERROR_CODE = "TEST_ERROR_CODE";

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		// In unit-test mode Msg does not read AD_Message from the DB, so the AD_Message lookup is provided by this stub.
		Services.registerService(IMsgBL.class, new PlainMsgBL()
		{
			@Nullable
			@Override
			public String getErrorCode(@NonNull final AdMessageKey messageKey)
			{
				return MSG_WITH_ERROR_CODE.equals(messageKey) ? ERROR_CODE : null;
			}
		});
	}

	@Test
	void errorCodeOfADMessage_isStoredOnADIssue()
	{
		final AdempiereException exception = new AdempiereException(MSG_WITH_ERROR_CODE);
		assertThat(exception.getErrorCode()).isEqualTo(ERROR_CODE);

		final AdIssueId issueId = new ErrorManager().createIssue(exception);

		final I_AD_Issue issue = load(issueId, I_AD_Issue.class);
		assertThat(issue.getErrorCode()).isEqualTo(ERROR_CODE);
	}

	@Test
	void errorCodeOfWrappedCause_isStoredOnADIssue()
	{
		final RuntimeException wrapper = new RuntimeException("wrapper", new AdempiereException(MSG_WITH_ERROR_CODE));

		final AdIssueId issueId = new ErrorManager().createIssue(wrapper);

		final I_AD_Issue issue = load(issueId, I_AD_Issue.class);
		assertThat(issue.getErrorCode()).isEqualTo(ERROR_CODE);
	}

	@Test
	void messageWithoutErrorCode_leavesADIssueErrorCodeEmpty()
	{
		final AdIssueId issueId = new ErrorManager().createIssue(new AdempiereException(AdMessageKey.of("Test_Message_Without_ErrorCode")));

		final I_AD_Issue issue = load(issueId, I_AD_Issue.class);
		assertThat(issue.getErrorCode()).isNull();
	}
}
