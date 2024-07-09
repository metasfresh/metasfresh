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
import de.metas.contracts.modular.ProductPriceWithFlags;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Add further properties as needed.
 */
@Value
public class ModularContractLogEntry
{
	@NonNull
	ModularContractLogEntryId id;

	@NonNull
	ClientAndOrgId clientAndOrgId;

	@NonNull
	LogEntryContractType contractType;

	@Nullable
	FlatrateTermId contractId;

	@Nullable
	ProductId productId;

	@Nullable
	ProductId initialProductId;
	@NonNull
	String productName;
	@Nullable
	ModularContractTypeId modularContractTypeId;

	@NonNull
	TableRecordReference referencedRecord;

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
	InvoiceCandidateId invoiceCandidateId;

	@NonNull YearId year;

	@Nullable String description;

	@Nullable
	ProductPrice priceActual;

	@Nullable
	InvoicingGroupId invoicingGroupId;

	boolean isBillable;

	@NonNull ModularContractModuleId modularContractModuleId;

	@Nullable BigDecimal userElementNumber1;
	@Nullable BigDecimal userElementNumber2;

	@Builder(toBuilder = true)
	private ModularContractLogEntry(
			@NonNull final ModularContractLogEntryId id,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final LogEntryContractType contractType,
			@Nullable final FlatrateTermId contractId,
			@Nullable final ProductId productId,
			@Nullable final ProductId initialProductId,
			@NonNull final String productName,
			@Nullable final ModularContractTypeId modularContractTypeId,
			@NonNull final TableRecordReference referencedRecord,
			@Nullable final BPartnerId collectionPointBPartnerId,
			@Nullable final BPartnerId producerBPartnerId,
			@Nullable final BPartnerId invoicingBPartnerId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final LogEntryDocumentType documentType,
			@NonNull final SOTrx soTrx,
			final boolean processed,
			@Nullable final Quantity quantity,
			@Nullable final Money amount,
			@NonNull final LocalDateAndOrgId transactionDate,
			@Nullable final Integer storageDays,
			@Nullable final InvoiceCandidateId invoiceCandidateId,
			@NonNull final YearId year,
			@Nullable final String description,
			@Nullable final ProductPrice priceActual,
			@Nullable final InvoicingGroupId invoicingGroupId,
			final boolean isBillable,
			final @NonNull ModularContractModuleId modularContractModuleId,
			final @Nullable BigDecimal userElementNumber1,
			final @Nullable BigDecimal userElementNumber2
			)
	{
		if (amount != null && priceActual != null)
		{
			amount.assertCurrencyId(priceActual.getCurrencyId());
		}

		if (priceActual != null)
		{
			Check.assumeEquals(priceActual.getProductId(), productId);
		}

		this.id = id;
		this.clientAndOrgId = clientAndOrgId;
		this.contractType = contractType;
		this.contractId = contractId;
		this.productId = productId;
		this.initialProductId = initialProductId;
		this.productName = productName;
		this.modularContractTypeId = modularContractTypeId;
		this.referencedRecord = referencedRecord;
		this.collectionPointBPartnerId = collectionPointBPartnerId;
		this.producerBPartnerId = producerBPartnerId;
		this.invoicingBPartnerId = invoicingBPartnerId;
		this.warehouseId = warehouseId;
		this.documentType = documentType;
		this.soTrx = soTrx;
		this.processed = processed;
		this.quantity = quantity;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.storageDays = storageDays;
		this.invoiceCandidateId = invoiceCandidateId;
		this.year = year;
		this.description = description;
		this.priceActual = priceActual;
		this.invoicingGroupId = invoicingGroupId;
		this.isBillable = isBillable;
		this.modularContractModuleId = modularContractModuleId;
		this.userElementNumber1 = userElementNumber1;
		this.userElementNumber2 = userElementNumber2;
	}

	@NonNull
	public Quantity getQuantity(@NonNull final UomId targetUomId, @NonNull final QuantityUOMConverter uomConverter)
	{
		Check.assumeNotNull(quantity, "Quantity of billable modular contract log shouldn't be null");
		return uomConverter.convertQuantityTo(quantity, productId, targetUomId);
	}

	@NonNull
	public ModularContractLogEntry withPriceActualAndCalculateAmount(
			@NonNull final ProductPrice price,
			@NonNull final QuantityUOMConverter uomConverter,
			@NonNull final ModularContractLogHandlerRegistry logHandlerRegistry)
	{
		final IModularContractLogHandler handler = logHandlerRegistry.getApplicableHandlerForOrError(this);
		final ProductPriceWithFlags productPriceWithFlags = handler.getPriceActualWithFlags(price, this);

		return handler.calculateAmountWithNewPrice(this, productPriceWithFlags, uomConverter);
	}
}
