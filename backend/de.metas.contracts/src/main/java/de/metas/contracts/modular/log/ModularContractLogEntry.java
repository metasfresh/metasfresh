/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.log;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.settings.ModuleConfigId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

/**
 * Add further properties as needed.
 */
@Value
@Builder
public class ModularContractLogEntry
{
	@NonNull
	ModularContractLogEntryId id;

	@NonNull
	FlatrateTermId contractId;

	@NonNull
	ProductId productId;

	@NonNull
	TableRecordReference referencedRecord;

	@NonNull
	BPartnerId collectionPointBPartnerId;

	@Nullable
	BPartnerId producerBPartnerId;

	@Nullable
	BPartnerId invoicingBPartnerId;

	@NonNull
	WarehouseId collectionPoint;

	@NonNull
	ModuleConfigId moduleConfigId;

	@NonNull
	LogEntryDocumentType documentType;

	@NonNull
	SOTrx soTrx;

	boolean processed;

	@Nullable
	Quantity quantity;

	@Nullable
	Money amount;

	@NonNull
	LocalDateAndOrgId transactionDate;

	@Nullable
	InvoiceCandidateId invoiceCandidateId;

/*
	Contract module	type:C_Flatrate_Term.Contract_module_Type_ID See	https://github.com/metasfresh/me03/issues/15645 TODO
	Active:Y: instead of having this, maybe just ignore inactive record in the DAO
*/
}
