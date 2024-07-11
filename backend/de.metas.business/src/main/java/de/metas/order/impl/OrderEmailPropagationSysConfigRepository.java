/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.order.impl;

import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Repository;

@Repository
public class OrderEmailPropagationSysConfigRepository
{
	private final ISysConfigBL sysConfigBL;

	public static final String SYS_CONFIG_C_Order_Email_Propagation = "de.metas.order.C_Order_Email_Propagation";
	private static final String SYS_CONFIG_C_Order_Email_Propagation_Default = "N";

	public OrderEmailPropagationSysConfigRepository(@NonNull final ISysConfigBL sysConfigBL)
	{
		this.sysConfigBL = sysConfigBL;
	}

	final String getValue(final ClientAndOrgId clientAndOrgId)
	{
		return sysConfigBL.getValue(SYS_CONFIG_C_Order_Email_Propagation,
									SYS_CONFIG_C_Order_Email_Propagation_Default,
									clientAndOrgId);
	}

	public boolean isPropagateToDocOutboundLog(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final String value = getValue(clientAndOrgId);

		return value.contains("C_Doc_Outbound_Log"); // #12448 It should be de.metas.document.archive.model.I_C_Doc_Outbound_Log.Table_Name but that's not available in de.metas.business
	}

	public boolean isPropagateToMInOut(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final String value = getValue(clientAndOrgId);

		return value.contains(I_M_InOut.Table_Name);
	}

	public boolean isPropagateToCInvoice(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final String value = getValue(clientAndOrgId);

		return value.contains(I_C_Invoice.Table_Name);
	}
}
