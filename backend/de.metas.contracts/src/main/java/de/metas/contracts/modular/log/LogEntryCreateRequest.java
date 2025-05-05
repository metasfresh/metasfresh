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
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Value
public class LogEntryCreateRequest
{
	@Nullable
	FlatrateTermId contractId;

	@Nullable
	ProductId productId;

	@Nullable
	ProductId initialProductId;

	@NonNull
	String productName;

	@NonNull
	TableRecordReference referencedRecord;

	@Nullable
	LogSubEntryId subEntryId;

	@Nullable
	BPartnerId collectionPointBPartnerId;

	@Nullable
	BPartnerId producerBPartnerId;

	@Nullable
	BPartnerId invoicingBPartnerId;

	@Nullable
	WarehouseId warehouseId;

	@NonNull
	LogEntryDocumentType documentType;

	@NonNull
	LogEntryContractType contractType;

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
	Integer storageDays;

	@Nullable
	BigDecimal userElementNumber1;

	@Nullable
	BigDecimal userElementNumber2;

	@Nullable
	InvoiceCandidateId invoiceCandidateId;

	@NonNull YearId year;

	@Nullable
	String description;

	@Nullable
	ModularContractTypeId modularContractTypeId;

	@NonNull
	ModularContractModuleId configModuleId;

	@Nullable
	ProductPrice priceActual;

	@Nullable
	InvoicingGroupId invoicingGroupId;

	boolean isBillable;

	@NonNull
	public static LogEntryCreateRequest ofEntry(@NonNull final ModularContractLogEntry entry)
	{
		return LogEntryCreateRequest.builder()
				.contractId(entry.getContractId())
				.productId(entry.getProductId())
				.initialProductId(entry.getInitialProductId())
				.productName(entry.getProductName())
				.referencedRecord(entry.getReferencedRecord())
				.collectionPointBPartnerId(entry.getCollectionPointBPartnerId())
				.producerBPartnerId(entry.getProducerBPartnerId())
				.invoicingBPartnerId(entry.getInvoicingBPartnerId())
				.warehouseId(entry.getWarehouseId())
				.documentType(entry.getDocumentType())
				.contractType(entry.getContractType())
				.soTrx(entry.getSoTrx())
				.processed(entry.isProcessed())
				.quantity(entry.getQuantity())
				.amount(entry.getAmount())
				.transactionDate(entry.getTransactionDate())
				.storageDays(entry.getStorageDays())
				.invoiceCandidateId(entry.getInvoiceCandidateId())
				.year(entry.getYear())
				.description(entry.getDescription())
				.modularContractTypeId(entry.getModularContractTypeId())
				.configModuleId(entry.getModularContractModuleId())
				.priceActual(entry.getPriceActual())
				.invoicingGroupId(entry.getInvoicingGroupId())
				.isBillable(entry.isBillable())
				.build();
	}

	@Builder(toBuilder = true)
	public LogEntryCreateRequest(
			@Nullable final FlatrateTermId contractId,
			@Nullable final ProductId productId,
			@Nullable final ProductId initialProductId,
			@NonNull final String productName,
			@NonNull final TableRecordReference referencedRecord,
			@Nullable final LogSubEntryId subEntryId,
			@Nullable final BPartnerId collectionPointBPartnerId,
			@Nullable final BPartnerId producerBPartnerId,
			@Nullable final BPartnerId invoicingBPartnerId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final LogEntryDocumentType documentType,
			@NonNull final LogEntryContractType contractType,
			@NonNull final SOTrx soTrx,
			final boolean processed,
			@Nullable final Quantity quantity,
			@Nullable final Money amount,
			@NonNull final LocalDateAndOrgId transactionDate,
			@Nullable final Integer storageDays,
			@Nullable final InvoiceCandidateId invoiceCandidateId,
			@NonNull final YearId year,
			@Nullable final String description,
			@Nullable final ModularContractTypeId modularContractTypeId,
			@NonNull final ModularContractModuleId configModuleId,
			@Nullable final ProductPrice priceActual,
			@Nullable final InvoicingGroupId invoicingGroupId,
			@Nullable final Boolean isBillable,
			@Nullable BigDecimal userElementNumber1,
			@Nullable BigDecimal userElementNumber2)
	{
		if (amount != null && priceActual != null)
		{
			amount.assertCurrencyId(priceActual.getCurrencyId());
		}

		if (priceActual != null)
		{
			Check.assumeEquals(priceActual.getProductId(), productId);
		}

		this.contractId = contractId;
		this.initialProductId = initialProductId;
		this.productName = productName;
		this.productId = productId;
		this.referencedRecord = referencedRecord;
		this.subEntryId = subEntryId;
		this.collectionPointBPartnerId = collectionPointBPartnerId;
		this.producerBPartnerId = producerBPartnerId;
		this.invoicingBPartnerId = invoicingBPartnerId;
		this.warehouseId = warehouseId;
		this.documentType = documentType;
		this.contractType = contractType;
		this.soTrx = soTrx;
		this.processed = processed;
		this.quantity = quantity;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.storageDays = storageDays;
		this.invoiceCandidateId = invoiceCandidateId;
		this.year = year;
		this.description = description;
		this.modularContractTypeId = modularContractTypeId;
		this.configModuleId = configModuleId;
		this.priceActual = priceActual;
		this.invoicingGroupId = invoicingGroupId;
		this.isBillable = isBillable != null ? isBillable : true;
		this.userElementNumber1 = userElementNumber1;
		this.userElementNumber2 = userElementNumber2;
	}

	@NonNull
	public Optional<Money> getAmount()
	{
		return Optional.ofNullable(amount);
	}

	@NonNull
	public Optional<Quantity> getQuantity()
	{
		return Optional.ofNullable(quantity);
	}

	@NonNull
	public Optional<ProductPrice> getPriceActual()
	{
		return Optional.ofNullable(priceActual);
	}
}
