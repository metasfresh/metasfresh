package de.metas.handlingunits.rest_api.createfromlabel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/**
 * What the HU Manager "New HU" preview shows for a scanned product label. Either a proposal ({@code product} set) or a
 * {@link JsonRejection}; never both. Proposing writes nothing.
 */
@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonHUProposal
{
	@Nullable JsonProduct product;
	/** proposed quantity, in the product's stock UOM */
	@Nullable BigDecimal qty;
	/** the stock UOM's symbol ({@code C_UOM.UOMSymbol}) in the user's language, e.g. {@code Stk}, {@code kg}; for display only */
	@Nullable String uom;
	@NonNull @Singular List<JsonAttribute> attributes;
	/** the label's net weight in kg; set only when the weight becomes the CU's net weight (catch-weight product) */
	@Nullable BigDecimal weightKg;
	/** translated note shown instead of the weight when the label carries a weight that this product does not use */
	@Nullable String weightNote;
	/** proposed target location (a global QR code, as a scanned location QR code would be); null = none, OK stays disabled until one is scanned */
	@Nullable ScannedCode locatorQRCode;
	@Nullable String locatorCaption;
	@Nullable JsonRejection rejection;

	public static JsonHUProposal ofRejection(@NonNull final JsonRejection rejection)
	{
		return builder().rejection(rejection).attributes(ImmutableList.of()).build();
	}

	@Value
	@Builder
	@Jacksonized
	public static class JsonProduct
	{
		int productId;
		@NonNull String value;
		@NonNull String name;
	}

	@Value
	@Builder
	@Jacksonized
	public static class JsonAttribute
	{
		@NonNull String code;
		@NonNull String caption;
		/** display value, already formatted for the user's language */
		@NonNull String valueDisplay;
	}

	@Value
	@Builder
	@Jacksonized
	public static class JsonRejection
	{
		/** {@code AD_Message.ErrorCode}, or the message key if the message has none */
		@NonNull String errorCode;
		/** translated, one plain-language message for the user */
		@NonNull String message;
	}
}
