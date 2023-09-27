/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.security.user_2fa.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.user_2fa.User2FAService;
import de.metas.security.user_2fa.totp.TOTPInfo;
import de.metas.user.UserId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class AD_User_Enable2FA extends JavaProcess implements IProcessPrecondition
{
	private final User2FAService user2FAService = SpringContextHolder.instance.getBean(User2FAService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final UserId loggedUserId = getLoggedUserId();
		final UserId userId = UserId.ofRepoId(context.getSingleSelectedRecordId());
		if (!UserId.equals(userId, loggedUserId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not logged in user");
		}

		// if (user2FAService.isEnabled(userId))
		// {
		// 	return ProcessPreconditionsResolution.rejectWithInternalReason("Already enabled");
		// }

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final UserId userId = UserId.ofRepoId(getRecord_ID());
		final TOTPInfo totpInfo = user2FAService.enable(userId, false);

		getResult().setDisplayQRCode(ProcessExecutionResult.DisplayQRCode.builder()
				.code(totpInfo.toQRCodeString())
				.build());

		return MSG_OK;
	}
}
