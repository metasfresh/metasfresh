/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.rest_api.v2.invoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPaymentAllocationLine {
    @NonNull
    @ApiModelProperty(position = 10, required = true,
            dataType = "java.lang.String",
            value = "Identifier of the Invoice in question. Can be\n"
                    + "* a plain `<C_Invoice.C_Invoice_ID>`\n"
                    + "* or something like `doc-<C_Invoice.documentNo>`"
                    + "* or something like `ext-<C_Invoice.ExternalId>`")
    String invoiceIdentifier;

    @ApiModelProperty(position = 20)
    @Nullable
    String docBaseType;

    @ApiModelProperty(position = 30)
    @Nullable
    String docSubType;

    @ApiModelProperty(position = 40)
    @Nullable
    BigDecimal amount;

    @ApiModelProperty(position = 50)
    @Nullable
    BigDecimal discountAmt;

    @ApiModelProperty(position = 60)
    @Nullable
    BigDecimal writeOffAmt;
}
