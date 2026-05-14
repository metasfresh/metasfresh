/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.bpartner.BPartnerId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class EDIBPartnerConfig
{
	@NonNull BPartnerId bPartnerId;

	boolean isEdiDesadvRecipient;
	@Nullable String ediDesadvRecipientGLN;
	@NonNull EDISendingMode ediDesadvSendingMode;
	@Nullable ExternalSystemParentConfigId ediDesadvExternalSystemParentConfigId;
	boolean isEdiOneEDIDesadvPerShipment;

	boolean isEdiInvoicRecipient;
	@Nullable String ediInvoicRecipientGLN;
	@NonNull EDISendingMode ediInvoicSendingMode;
	@Nullable ExternalSystemParentConfigId ediInvoicExternalSystemParentConfigId;

	public boolean isDESADVReplicationInterfaceRecipient()
	{
		return isEdiDesadvRecipient && ediDesadvSendingMode.isReplicationInterface();
	}

	public boolean isDESADVExternalSystemRecipient()
	{
		return isEdiDesadvRecipient && ediDesadvSendingMode.isExternalSystem() && ediDesadvExternalSystemParentConfigId != null;
	}

	public boolean isDESADVOneDesadvPerShipment()
	{
		return isDESADVExternalSystemRecipient() && isEdiOneEDIDesadvPerShipment;
	}

	public boolean isINVOICReplicationInterfaceRecipient()
	{
		return isEdiInvoicRecipient && ediInvoicSendingMode.isReplicationInterface();
	}

	public boolean isINVOICExternalSystemRecipient()
	{
		return isEdiInvoicRecipient && ediInvoicSendingMode.isExternalSystem() && ediInvoicExternalSystemParentConfigId != null;
	}
}
