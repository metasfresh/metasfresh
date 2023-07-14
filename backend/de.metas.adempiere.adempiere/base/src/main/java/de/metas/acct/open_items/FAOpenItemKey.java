package de.metas.acct.open_items;

import com.google.common.base.Splitter;
import de.metas.acct.AccountConceptualName;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode(exclude = "stringRepresentation")
public final class FAOpenItemKey
{
	@Nullable private final AccountConceptualName accountConceptualName;
	@NonNull private final String tableName;
	private final int recordId;
	private final int lineId;
	private final int subLineId;
	private transient String stringRepresentation; // lazy

	private static final Splitter SPLITTER = Splitter.on("#");

	@Builder(access = AccessLevel.PRIVATE)
	private FAOpenItemKey(
			@Nullable final String stringRepresentation,
			@Nullable final AccountConceptualName accountConceptualName,
			@NonNull final String tableName,
			final int recordId,
			final int lineId,
			final int subLineId)
	{
		final String tableNameNorm = StringUtils.trimBlankToNull(tableName);
		if (tableNameNorm == null)
		{
			throw new AdempiereException("TableName cannot be null or empty");
		}

		this.stringRepresentation = stringRepresentation;
		this.accountConceptualName = accountConceptualName;
		this.tableName = tableNameNorm;
		this.recordId = Math.max(recordId, 0);
		this.lineId = Math.max(lineId, 0);
		this.subLineId = Math.max(subLineId, 0);
	}

	public static FAOpenItemKey invoice(@NonNull final InvoiceId invoiceId, @NonNull final AccountConceptualName accountConceptualName)
	{
		return builder()
				.accountConceptualName(accountConceptualName)
				.tableName(I_C_Invoice.Table_Name)
				.recordId(invoiceId.getRepoId())
				.build();
	}

	public static FAOpenItemKey payment(@NonNull final PaymentId paymentId, @NonNull final AccountConceptualName accountConceptualName)
	{
		return builder()
				.accountConceptualName(accountConceptualName)
				.tableName(I_C_Payment.Table_Name)
				.recordId(paymentId.getRepoId())
				.build();
	}

	public static FAOpenItemKey bankStatementLine(
			@NonNull final BankStatementId bankStatementId,
			@NonNull final BankStatementLineId bankStatementLineId,
			@Nullable final BankStatementLineRefId bankStatementLineRefId,
			@NonNull final AccountConceptualName accountConceptualName)
	{
		return builder()
				.accountConceptualName(accountConceptualName)
				.tableName(I_C_BankStatement.Table_Name)
				.recordId(bankStatementId.getRepoId())
				.lineId(bankStatementLineId.getRepoId())
				.subLineId(BankStatementLineRefId.toRepoId(bankStatementLineRefId))
				.build();
	}

	public static FAOpenItemKey ofTableRecordLineAndSubLineId(
			@Nullable final AccountConceptualName accountConceptualName,
			@NonNull final String tableName,
			final int recordId,
			final int lineId,
			final int subLineId)
	{
		return builder()
				.accountConceptualName(accountConceptualName)
				.tableName(tableName)
				.recordId(recordId)
				.lineId(lineId)
				.subLineId(subLineId)
				.build();
	}

	public static Optional<FAOpenItemKey> parseNullable(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null ? Optional.of(parse(stringNorm)) : Optional.empty();
	}

	public static FAOpenItemKey parse(@NonNull final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			throw new AdempiereException("empty/null Open Item Key is not allowed");
		}

		try
		{
			final List<String> parts = SPLITTER.splitToList(stringNorm);
			return builder()
					.stringRepresentation(stringNorm)
					.accountConceptualName(!"-".equals(parts.get(0)) ? AccountConceptualName.ofString(parts.get(0)) : null)
					.tableName(parts.get(1))
					.recordId(Math.max(NumberUtils.asInt(parts.get(2)), 0))
					.lineId(parts.size() >= 4 ? Math.max(NumberUtils.asInt(parts.get(3)), 0) : 0)
					.subLineId(parts.size() >= 5 ? Math.max(NumberUtils.asInt(parts.get(4)), 0) : 0)
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid open item key: " + stringNorm, ex);
		}

	}

	public static boolean equals(@Nullable FAOpenItemKey o1, @Nullable FAOpenItemKey o2) {return Objects.equals(o1, o2);}

	@Override
	public String toString() {return getAsString();}

	public String getAsString()
	{
		String stringRepresentation = this.stringRepresentation;
		if (stringRepresentation == null)
		{
			stringRepresentation = this.stringRepresentation = buildStringRepresentation();
		}
		return stringRepresentation;
	}

	@NonNull
	private String buildStringRepresentation()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(accountConceptualName != null ? accountConceptualName.getAsString() : "-");
		sb.append("#").append(tableName);
		sb.append("#").append(Math.max(recordId, 0));
		if (lineId > 0)
		{
			sb.append("#").append(lineId);
		}
		if (subLineId > 0)
		{
			sb.append("#").append(subLineId);
		}

		return sb.toString();
	}

	public Optional<InvoiceId> getInvoiceId()
	{
		return I_C_Invoice.Table_Name.equals(tableName)
				? InvoiceId.optionalOfRepoId(recordId)
				: Optional.empty();
	}

	public Optional<PaymentId> getPaymentId()
	{
		return I_C_Payment.Table_Name.equals(tableName)
				? PaymentId.optionalOfRepoId(recordId)
				: Optional.empty();
	}

	public Optional<BankStatementId> getBankStatementId()
	{
		return I_C_BankStatement.Table_Name.equals(tableName)
				? BankStatementId.optionalOfRepoId(recordId)
				: Optional.empty();
	}

	public Optional<BankStatementLineId> getBankStatementLineId()
	{
		return I_C_BankStatement.Table_Name.equals(tableName)
				? BankStatementLineId.optionalOfRepoId(lineId)
				: Optional.empty();
	}

	public Optional<BankStatementLineRefId> getBankStatementLineRefId()
	{
		return I_C_BankStatement.Table_Name.equals(tableName)
				? BankStatementLineRefId.optionalOfRepoId(subLineId)
				: Optional.empty();
	}
}
