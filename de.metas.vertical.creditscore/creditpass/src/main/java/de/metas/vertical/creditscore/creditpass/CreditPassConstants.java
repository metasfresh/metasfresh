package de.metas.vertical.creditscore.creditpass;

/*
 * #%L
 * de.metas.vertical.creditscore.creditpass
 * %%
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

public final class CreditPassConstants
{

	public static final String AMOUNT_DEFAULT_VALUE = "10";

	public static final String SYSCONFIG_TRANSACTION_TYPE = "de.metas.vertical.creditscore.creditpass.TransactionType";

	public static final String SYSCONFIG_PROCESSING_CODE = "de.metas.vertical.creditscore.creditpass.ProcessingCode";

	public static final String SYSCONFIG_AMOUNT_DEFAULT_VALUE = "de.metas.vertical.creditscore.creditpass.AmountDefault";

	public static final String PROCESS_PAYMENT_RULE_PARAM = "paymentRule";

	public static final String PROCESS_RESULT_OVERRIDE_PARAM = "resultOverride";

	public static final int DEFAULT_TRANSACTION_ID = 11920;

	public static final int DEFAULT_PROCESSING_CODE = 8;

	public static final String DEFAULT_RESULT_TEXT = "Default result";

	public static final String CREDITPASS_NOTIFICATION_MESSAGE_KEY = "CreditpassNotificationMessage";

	public static final String CREDITPASS_STATUS_SUCCESS_MESSAGE_KEY = "CreditpassStatus.Success";

	public static final String CREDITPASS_STATUS_FAILURE_MESSAGE_KEY = "CreditpassStatus.Failure";

	public static final String CREDITPASS_REQUEST_NEEDED_MESSAGE_KEY = "CreditpassStatus.RequestNeeded";

	public static final String CREDITPASS_REQUEST_NOT_NEEDED_MESSAGE_KEY = "CreditpassStatus.RequestNotNeeded";

	public static final String ORDER_COMPLETED_CREDITPASS_ERROR = "OrderCompleted.CreditpassError";

}
