package de.metas.bpartner.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerCreditLimitId;
import de.metas.bpartner.service.CreditLimitType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonPropertyOrder(alphabetic = true)
public class BPartnerCreditLimit
{
	public static final String METASFRESH_ID = "metasfreshId";
	public static final String BPARTNER_ID = "bpartnerId";
	public static final String AMOUNT = "amount";
	public static final String DATE_FROM = "dateFrom";
	public static final String CREDITLIMITTYPE = "creditLimitType";

	@Nullable
	private BPartnerCreditLimitId id;

	@Setter(AccessLevel.NONE)
	@JsonIgnore
	@Nullable
	private BPartnerId bpartnerId;

	@JsonInclude(Include.NON_NULL)
	@NonNull
	private BigDecimal amount;

	@JsonInclude(Include.NON_NULL)
	@Nullable
	private LocalDate dateFrom;

	@JsonInclude(Include.NON_NULL)
	@NonNull
	private CreditLimitType creditLimitType;

	@Builder(toBuilder = true)
	private BPartnerCreditLimit(
			@Nullable final BPartnerCreditLimitId id,
			@Nullable final LocalDate dateFrom,
			@NonNull final BigDecimal amount,
			@NonNull final CreditLimitType creditLimitType)
	{
		setId(id);

		this.dateFrom = dateFrom;
		this.amount = amount;
		this.creditLimitType = creditLimitType;
	}

	public BPartnerCreditLimit deepCopy()
	{
		final BPartnerCreditLimit.BPartnerCreditLimitBuilder builder = toBuilder();
		return builder.build();
	}

	public final void setId(@Nullable final BPartnerCreditLimitId id)
	{
		this.id = id;
		this.bpartnerId = id != null ? id.getBpartnerId() : null;
	}

}
