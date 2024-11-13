package de.metas.contracts.modular.log;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.currency.CurrencyPrecision;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class ModularContractLogEntriesList implements Iterable<ModularContractLogEntry>
{
	private static final String PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG = "ProductPrices of billable modular contract logs shouldn't be null";

	// compute with 12 digit precision, will be rounded on IC creation according to priceList precision
	private final CurrencyPrecision precision = CurrencyPrecision.ofInt(12);

	public static final ModularContractLogEntriesList EMPTY = new ModularContractLogEntriesList(ImmutableList.of());
	@NonNull private final ImmutableList<ModularContractLogEntry> list;

	private ModularContractLogEntriesList(@NonNull final ImmutableList<ModularContractLogEntry> list)
	{
		this.list = list;
	}

	public static ModularContractLogEntriesList ofCollection(@NonNull final Collection<ModularContractLogEntry> list)
	{
		return list.isEmpty() ? ModularContractLogEntriesList.EMPTY : new ModularContractLogEntriesList(ImmutableList.copyOf(list));
	}

	@NonNull
	public static ModularContractLogEntriesList ofSingle(@NonNull final ModularContractLogEntry entry)
	{
		return new ModularContractLogEntriesList(ImmutableList.of(entry));
	}

	public static Collector<ModularContractLogEntry, ?, ModularContractLogEntriesList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ModularContractLogEntriesList::ofCollection);
	}

	public ModularContractLogEntriesList subsetOf(@NonNull final LogEntryDocumentType documentType)
	{
		return subsetOf(ImmutableSet.of(documentType));
	}

	public ModularContractLogEntriesList subsetOf(@NonNull final Collection<LogEntryDocumentType> documentTypes)
	{
		return ModularContractLogEntriesList.ofCollection(
				list.stream()
						.filter(log -> documentTypes.contains(log.getDocumentType()))
						.toList()
		);
	}

	public ModularContractLogEntriesList subsetOfExcluding(@NonNull final Collection<LogEntryDocumentType> documentTypes)
	{
		return ModularContractLogEntriesList.ofCollection(
				list.stream()
						.filter(log -> !documentTypes.contains(log.getDocumentType()))
						.toList()
		);
	}

	public ModularContractLogEntriesList subsetOf(final boolean processed)
	{
		return ModularContractLogEntriesList.ofCollection(list.stream()
																  .filter(log -> log.isProcessed() == processed)
																  .toList());
	}

	@NotNull
	@Override
	public Iterator<ModularContractLogEntry> iterator() {return list.iterator();}

	public boolean isEmpty() {return list.isEmpty();}

	public ModularContractLogEntry getFirstEntry()
	{
		Check.assumeNotEmpty(list, "list is not empty");
		return list.get(0);
	}

	@Nullable
	public ProductPrice getFirstPriceActual()
	{
		return getFirstEntry().getPriceActual();
	}

	public Stream<ModularContractLogEntry> stream() {return list.stream();}

	public ImmutableSet<ModularContractLogEntryId> getIds()
	{
		return list.stream().map(ModularContractLogEntry::getId).collect(ImmutableSet.toImmutableSet());
	}

	public Optional<Money> getAmount()
	{
		return list.stream()
				.map(ModularContractLogEntry::getAmount)
				.filter(Objects::nonNull)
				.reduce(Money::add);
	}

	public ProductId getSingleProductId()
	{
		return CollectionUtils.extractSingleElement(list, ModularContractLogEntry::getProductId);
	}

	public ModularContractModuleId getSingleModuleId()
	{
		return CollectionUtils.extractSingleElement(list, ModularContractLogEntry::getModularContractModuleId);
	}

	@Nullable
	public LocalDateAndOrgId getSingleTransactionDate()
	{
		if(list.isEmpty())
		{
			return null;
		}
		return CollectionUtils.extractSingleElement(list, ModularContractLogEntry::getTransactionDate);
	}

	@Nullable
	public InvoiceCandidateId getSingleInvoiceCandidateIdOrNull()
	{
		return CollectionUtils.extractSingleElementOrDefault(list, ModularContractLogEntry::getInvoiceCandidateId, null);
	}

	@NonNull
	public Quantity getQtySum(@NonNull final UomId targetUomId, @NonNull final QuantityUOMConverter uomConverter)
	{
		if (list.isEmpty())
		{
			return Quantitys.zero(targetUomId);
		}

		return list.stream()
				.map((log) -> log.getQuantity(targetUomId, uomConverter))
				.reduce(Quantity::add)
				.orElseGet(() -> Quantitys.zero(targetUomId));
	}

	@NonNull
	public Quantity getQtyXStorageDaysSum(@NonNull final UomId targetUomId, @NonNull final QuantityUOMConverter uomConverter)
	{
		if (list.isEmpty())
		{
			return Quantitys.zero(targetUomId);
		}

		return list.stream()
				.map((log) -> getQtyXStorageDays(log, targetUomId, uomConverter))
				.reduce(Quantity::add)
				.orElseGet(() -> Quantitys.zero(targetUomId));
	}

	@NonNull
	private Quantity getQtyXStorageDays(
			@NonNull final ModularContractLogEntry log,
			@NonNull final UomId targetUomId,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		return log.getQuantity(targetUomId, uomConverter)
				.multiply(Check.assumeNotNull(log.getStorageDays(), "StorageDays shouldn't be null"));
	}

	public void assertSingleProductId(@NonNull final ProductId expectedProductId)
	{
		Check.assumeEquals(getSingleProductId(), expectedProductId, "All logs entry shall have product {}: {}", expectedProductId, this);
	}

	@NonNull
	public ProductPrice getUniqueProductPriceOrErrorNotNull()
	{
		return getUniqueProductPriceOrError().orElseThrow(() -> new AdempiereException("No product price found"));
	}

	@NonNull
	public Optional<ProductPrice> getUniqueProductPriceOrError()
	{
		if (list.isEmpty())
		{
			return Optional.empty();
		}

		final ProductPrice productPriceToMatch = getFirstPriceActual();
		Check.assumeNotNull(productPriceToMatch, PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG);
		list.forEach(log -> assertProductPricesEquals(log.getPriceActual(), productPriceToMatch));
		return Optional.of(productPriceToMatch);
	}

	private static void assertProductPricesEquals(@Nullable final ProductPrice productPrice, @NonNull final ProductPrice productPriceToMatch)
	{
		Check.assumeNotNull(productPrice, PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG);
		Check.assume(productPrice.isEqualByComparingTo(productPriceToMatch), "ProductPrices of billable modular contract logs should be identical", productPrice, productPriceToMatch);
	}

	private void assertAllUnprocessed()
	{
			Check.assume(list.stream().noneMatch(ModularContractLogEntry::isProcessed), "Some of the log entries are already processed {}", this);
	}

	public ModularContractLogEntriesList withPriceActualAndCalculateAmount(
			@NonNull final ProductPrice price,
			@NonNull final QuantityUOMConverter quantityUOMConverter,
			@NonNull final ModularContractLogHandlerRegistry logHandlerRegistry)
	{
		assertAllUnprocessed();
		return list.stream()
				.map(log -> log.withPriceActualAndCalculateAmount(price, quantityUOMConverter, logHandlerRegistry))
				.collect(collect());
	}

	@NonNull
	public ClientAndOrgId getSingleClientAndOrgId()
	{
		return CollectionUtils.extractSingleElement(list, ModularContractLogEntry::getClientAndOrgId);
	}

	@NonNull
	public Optional<ProductPrice> getAveragePrice(
			@NonNull final ProductId productId,
			@NonNull final UomId targetUOMId,
			@NonNull final QuantityUOMConverter quantityUOMConverter,
			@NonNull final BigDecimal tradeMargin)
	{
		if (list.isEmpty())
		{
			return Optional.empty();
		}

		final Optional<Money> priceSum = getAmountSum();
		final Quantity qtySum = getQtySum(targetUOMId, quantityUOMConverter);

		if(priceSum.isEmpty() || qtySum.isZero())
		{
			return Optional.empty();
		}

		final Money priceAverage = priceSum.get().divide(qtySum.toBigDecimal(), precision).subtract(tradeMargin);

		return Optional.of(ProductPrice.builder()
								   .money(priceAverage)
								   .productId(productId)
								   .uomId(targetUOMId)
								   .build()
		);
    }

	@NonNull
	public Optional<Quantity> getQtySum()
	{
		if (list.isEmpty())
		{
			return Optional.empty();
		}
		return list.stream()
				.map(ModularContractLogEntry::getQuantity)
				.filter(Objects::nonNull)
				.reduce(Quantity::add);
	}

	@NonNull
	public Optional<Money> getAmountSum()
	{
		if (list.isEmpty())
		{
			return Optional.empty();
		}
		return list.stream()
				.map(ModularContractLogEntry::getAmount)
				.filter(Objects::nonNull)
				.reduce(Money::add);
	}
}
