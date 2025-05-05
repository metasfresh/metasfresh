/*
 * #%L
 * de.metas.postfinance
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

package de.metas.postfinance.document.export;

import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

@Value
@Builder
public class PostFinanceYbInvoiceRequest
{
	@NonNull TableRecordReference documentReference;

	@NonNull TableRecordReference pInstanceReference;

	@NonNull TableRecordReference docOutboundLogReference;
	@NonNull ClientAndOrgId clientAndOrgId;

	@NonNull
	public DocOutboundLogId getDocOutboundLogId()
	{
		return docOutboundLogReference.getIdAssumingTableName(I_C_Doc_Outbound_Log.Table_Name, DocOutboundLogId::ofRepoId);
	}
}
