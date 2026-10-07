/*
 * #%L
 * de.metas.business
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

package de.metas.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_OrderLine;

import javax.annotation.Nullable;

/**
 * Ref-list code wrapper for {@code C_OrderLine.Reason} (AD_Reference 541968 — "Reason for without charge").
 * <p>
 * The {@link #BundleComponent} value 'B' is added by migration {@code 5805140_sys_gh29558_Reason_RefList_BundleComponent.sql}
 * and is auto-set by {@code OrderGroupRepository} on a component order line whose template line has IsWithoutCharge=Y.
 */
@AllArgsConstructor
public enum OrderLineReasonForWithoutCharge implements ReferenceListAwareEnum
{
	Warranty(X_C_OrderLine.REASON_Warranty),
	Goodwill(X_C_OrderLine.REASON_Goodwill),
	FullService(X_C_OrderLine.REASON_FullService),
	PromotionalCampaign(X_C_OrderLine.REASON_PromotionalCampaign),
	InternalUse(X_C_OrderLine.REASON_InternalUse),
	BundleComponent("B"); // AD_Ref_List value for "Bestandteil Handelsstückliste" — added by migration 5805140

	@Getter @NonNull private final String code;

	private static final ValuesIndex<OrderLineReasonForWithoutCharge> index = ReferenceListAwareEnums.index(values());

	@JsonCreator
	public static OrderLineReasonForWithoutCharge ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static OrderLineReasonForWithoutCharge ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@JsonValue
	public String toJson() {return code;}
}
