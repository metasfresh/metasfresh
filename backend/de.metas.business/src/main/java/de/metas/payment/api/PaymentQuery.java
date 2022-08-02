package de.metas.payment.api;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.money.Money;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.util.Date;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
@Builder
public class PaymentQuery
{
	@NonNull
	@Default
	QueryLimit limit = QueryLimit.ONE_HUNDRED;

	@NonNull
	DocStatus docStatus;

	@Nullable
	Boolean reconciled;

	@Nullable
	PaymentDirection direction;

	@Nullable
	BPartnerId bpartnerId;

	@Nullable
	Money payAmt;

	@Singular
	@NonNull
	ImmutableSet<PaymentId> excludePaymentIds;
	
	@Nullable
	Date dateTrx;
}
