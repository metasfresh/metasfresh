package de.metas.camel.ebay;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import de.metas.camel.externalsystems.ebay.api.model.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Context to store all properties of a single eBay order and processed meta-data 
 * from various stages of the {@link GetEbayOrdersRouteBuilder} camel pipeline.
 * 
 * @author Werner Gaulke
 *
 */
@Data
@Builder
public class EbayImportOrdersRouteContext {
	
	
	@NonNull
	@Builder.Default
	@Setter(AccessLevel.NONE)
	private Set<String> importedExternalHeaderIds = new HashSet<>();
	
	@NonNull
	private final String orgCode;

	@Nullable
	private Order order;

	@Nullable
	private String bpLocationCustomJsonPath;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String shippingBPLocationExternalId;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String billingBPLocationExternalId;
	
	@Nullable
	private LocalDate dateRequired;

	
	@NonNull
	public Order getOrderNotNull()
	{
		if (order == null)
		{
			throw new RuntimeException("order cannot be null at this stage!");
		}

		return order;
	}

	@NonNull
	public String getBillingBPLocationExternalIdNotNull()
	{
		if (billingBPLocationExternalId == null)
		{
			throw new RuntimeException("billingBPLocationExternalId cannot be null at this stage!");
		}

		return billingBPLocationExternalId;
	}

	@NonNull
	public String getShippingBPLocationExternalIdNotNull()
	{
		if (shippingBPLocationExternalId == null)
		{
			throw new RuntimeException("shippingBPLocationExternalId cannot be null at this stage!");
		}

		return shippingBPLocationExternalId;
	}

	public void setOrder(@NonNull final Order order)
	{
		this.order = order;
		importedExternalHeaderIds.add(order.getOrderId());
	}
	
}
