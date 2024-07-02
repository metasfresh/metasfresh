/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.document.archive.postfinance;

import de.metas.document.archive.model.X_C_Doc_Outbound_Log;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@Getter
@AllArgsConstructor
public enum PostFinanceStatus implements ReferenceListAwareEnum
{
    OK(X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_OK),
    DATA_ERROR(X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_Error),
    TRANSMISSION_ERROR(X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_TransmissionError),
    NOT_SEND(X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_NotSent),
    DO_NOT_SEND(X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_DontSend);

    private static final ReferenceListAwareEnums.ValuesIndex<PostFinanceStatus> index = ReferenceListAwareEnums.index(values());

    @NonNull
    private final String code;

    @NonNull
    public static PostFinanceStatus ofCode(@NonNull final String code)
    {
        return index.ofCode(code);
    }

    @Nullable
    public static PostFinanceStatus ofNullableCode(@Nullable final String code)
    {
        return code != null ? ofCode(code) : null;
    }
}
