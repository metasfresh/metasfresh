/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.workflow;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_WF_Node;

import javax.annotation.Nullable;

public enum WFNodeEmailRecipient implements ReferenceListAwareEnum
{
	DocumentOwner(X_AD_WF_Node.EMAILRECIPIENT_DocumentOwner),
	DocumentBusinessPartner(X_AD_WF_Node.EMAILRECIPIENT_DocumentBusinessPartner),
	WorkflowResponsible(X_AD_WF_Node.EMAILRECIPIENT_WFResponsible);

	private static final ReferenceListAwareEnums.ValuesIndex<WFNodeEmailRecipient> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	WFNodeEmailRecipient(@NonNull final String code)
	{
		this.code = code;
	}

	public static WFNodeEmailRecipient ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	public static WFNodeEmailRecipient ofNullableCode(@Nullable final String code)
	{
		return typesByCode.ofNullableCode(code);
	}
}
