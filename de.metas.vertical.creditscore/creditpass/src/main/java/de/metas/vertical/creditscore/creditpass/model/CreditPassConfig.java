package de.metas.vertical.creditscore.creditpass.model;

import java.util.List;

import de.metas.user.UserId;

/*
 * #%L
 * de.metas.vertical.creditscore.creditpass.model
 *
 * Copyright (C) 2018 metas GmbH
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

import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreditPassConfig
{
	@NonNull
	private String restApiBaseUrl;

	@NonNull
	private String authId;

	@NonNull
	private String authPassword;

	private int transactionType;

	private int processingCode;

	@NonNull
	private ResultCode resultCode;

	@NonNull
	private UserId notificationUserId;

	private int retryDays;

	@NonNull
	private String requestReason;

	@NonNull
	private CreditPassConfigId creditPassConfigId;

	private List<CreditPassConfigPaymentRule> creditPassConfigPaymentRuleList;

}
