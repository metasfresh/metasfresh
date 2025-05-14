/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.workpackage.impl;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ProductPriceWithFlags;
import de.metas.contracts.modular.computing.salescontract.interest.InterestComputingMethod;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.ExplainedOptional;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public abstract class AbstractInterestLogHandler extends AbstractShipmentLogHandler
{
	// use low precision in Logs to be more accurate on aggregation of logs on IC creation with lower precision
	private static final CurrencyPrecision precision = CurrencyPrecision.ofInt(12);
	@NonNull private final  IModularContractLogHandler interestBaseLogHandler;
	@NonNull private final ComputingMethodType interestBaseComputingMethodType;
	public AbstractInterestLogHandler(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final InterestComputingMethod computingMethod,
			@NonNull final IModularContractLogHandler interestBaseLogHandler,
			@NonNull final ComputingMethodType interestBaseComputingMethodType)
	{
		super(modularContractService, modCntrInvoicingGroupRepository, computingMethod);
		this.interestBaseLogHandler = interestBaseLogHandler;
		this.interestBaseComputingMethodType = interestBaseComputingMethodType;
	}

	@Override
	public ComputingMethodType getBaseComputingMethodType(){return interestBaseComputingMethodType;}

	@Override
	public boolean applies(final @NonNull CreateLogRequest request)
	{
		final ModuleConfig baseModuleConfig = request.getBaseModuleConfig();
		if ( baseModuleConfig == null || !baseModuleConfig.isMatching(interestBaseComputingMethodType))
		{
			return false;
		}

		return super.applies(request);
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final LogEntryCreateRequest interestBaseRequest = interestBaseLogHandler.createLogEntryCreateRequest(createLogRequest.toBaseModuleCreateLogRequest()).get();
		final Money interestBaseAmount = Check.assumePresent(interestBaseRequest.getAmount(), "Amount should be present");
		final ProductPrice interestBaseAmountAsProductPrice = ProductPrice.builder()
				.productId(createLogRequest.getProductId())
				.uomId(Check.assumePresent(interestBaseRequest.getPriceActual(), "Base Price Actual should be present").getUomId())
				.money(interestBaseAmount)
				.build();
		final int interestDays = computeInterestDays(createLogRequest, interestBaseRequest.getTransactionDate(), interestBaseRequest.getPhysicalClearanceDate());
		final Percent interestPercent = createLogRequest.getModularContractSettings().getInterestPercent();


		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(createLogRequest.getContractId())
				.productId(createLogRequest.getProductId())
				.productName(createLogRequest.getProductName())
				.initialProductId(interestBaseRequest.getInitialProductId())
				.referencedRecord(interestBaseRequest.getReferencedRecord())
				.collectionPointBPartnerId(interestBaseRequest.getCollectionPointBPartnerId())
				.producerBPartnerId(interestBaseRequest.getProducerBPartnerId())
				.invoicingBPartnerId(interestBaseRequest.getInvoicingBPartnerId())
				.warehouseId(interestBaseRequest.getWarehouseId())
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(getSOTrx())
				.processed(false)
				.isBillable(true)
				.quantity(interestBaseRequest.getQuantity().orElse(null))
				.storageDays(null)
				.interestDays(interestDays)
				.interestPercent(createLogRequest.getModularContractSettings().getInterestPercent())
				.amount(computeAmount(interestBaseAmountAsProductPrice,interestDays,interestPercent))
				.transactionDate(interestBaseRequest.getTransactionDate())
				.physicalClearanceDate(interestBaseRequest.getPhysicalClearanceDate())
				.priceActual(interestBaseAmountAsProductPrice)
				.year(interestBaseRequest.getYear())
				.description(interestBaseRequest.getDescription())
				.modularContractTypeId(createLogRequest.getTypeId())
				.configModuleId(createLogRequest.getModularContractModuleId())
				.baseConfigModuleId(createLogRequest.getBaseModularContractModuleId())
				.build());
	}

	private int computeInterestDays(
			final @NotNull CreateLogRequest createLogRequest,
			final @NonNull LocalDateAndOrgId transactionDate,
			final @Nullable LocalDateAndOrgId physicalClearanceDate)
	{
		Check.assumeNotNull(physicalClearanceDate, "Physical Clearance Date shouldn't be null");
		final int daysBetween = (int)TimeUtil.getDaysBetween360(physicalClearanceDate.toInstant(orgDAO::getTimeZone), transactionDate.toInstant(orgDAO::getTimeZone));
		final int daysFree = createLogRequest.getModularContractSettings().getFreeInterestDays();
		return Math.max(daysBetween - daysFree, 0);
	}

	@NonNull
	private static Money computeAmount(
			final @NotNull ProductPrice productPrice,
			final @NonNull Integer interestDays,
			final @NonNull Percent interestPercent)
	{
		return productPrice.toMoney().multiply(interestPercent, precision).multiply(interestDays).divide(BigDecimal.valueOf(360), precision);
	}

	@Override
	public @NonNull ModularContractLogEntry calculateAmountWithNewPrice(
			final @NonNull ModularContractLogEntry logEntry,
			final @NonNull ProductPriceWithFlags newPrice,
			final @NonNull QuantityUOMConverter uomConverter)
	{
		final Percent interestPercent = Check.assumeNotNull(logEntry.getInterestPercent(), "InterestRate shouldn't be null");
		final int interestDays = Check.assumeNotNull(logEntry.getInterestDays(), "InterestDays shouldn't be null");
		final ProductPrice price = newPrice.toProductPrice();
		return logEntry.toBuilder()
				.priceActual(price)
				.amount(computeAmount(price, interestDays, interestPercent))
				.build();
	}

	protected SOTrx getSOTrx()
	{
		return SOTrx.SALES;
	}
}
