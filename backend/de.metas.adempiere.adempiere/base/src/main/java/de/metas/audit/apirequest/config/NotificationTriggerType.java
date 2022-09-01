/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.audit.apirequest.config;

import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

import static org.compiere.model.X_API_Audit_Config.NOTIFYUSERINCHARGE_AllenAufrufen;
import static org.compiere.model.X_API_Audit_Config.NOTIFYUSERINCHARGE_AufrufenMitFehler;
import static org.compiere.model.X_API_Audit_Config.NOTIFYUSERINCHARGE_Niemals;

@AllArgsConstructor
@Getter
public enum NotificationTriggerType implements ReferenceListAwareEnum
{
	ALWAYS(NOTIFYUSERINCHARGE_AllenAufrufen),
	NEVER(NOTIFYUSERINCHARGE_Niemals),
	ONLY_ON_ERROR(NOTIFYUSERINCHARGE_AufrufenMitFehler);

	private final String code;

	public static NotificationTriggerType ofCode(@NonNull final String code)
	{
		return Arrays.stream(values())
				.filter(value -> value.getCode().equals(code))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No NotificationTriggerType could be found for code!")
						.appendParametersToMessage()
						.setParameter("code", code));
	}

	@Nullable
	public static NotificationTriggerType ofNullableCode(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}

		return ofCode(code);
	}
}
