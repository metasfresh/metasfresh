package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.money.CurrencyId;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.UnaryOperator;

@EqualsAndHashCode
@ToString
public class POSOrder
{
	@NonNull @Getter private final POSOrderExternalId externalId;
	@Getter private final int localId;

	@NonNull @Getter POSOrderStatus status;
	@NonNull @Getter private final UserId cashierId;

	@NonNull @Getter private final CurrencyId currencyId;
	@NonNull @Getter BigDecimal totalAmt;

	@NonNull private final ArrayList<POSOrderLine> lines;

	@Builder
	private POSOrder(
			@NonNull final POSOrderExternalId externalId,
			final int localId,
			@Nullable final POSOrderStatus status,
			@NonNull final UserId cashierId,
			@NonNull final CurrencyId currencyId,
			@Nullable final List<POSOrderLine> lines)
	{
		this.externalId = externalId;
		this.localId = localId;
		this.status = status != null ? status : POSOrderStatus.Drafted;
		this.cashierId = cashierId;
		this.currencyId = currencyId;
		this.lines = lines != null ? new ArrayList<>(lines) : new ArrayList<>();

		this.totalAmt = BigDecimal.ZERO;
		updateTotals();
	}

	private void updateTotals()
	{
		this.totalAmt = this.lines.stream()
				.map(POSOrderLine::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public ImmutableList<POSOrderLine> getLines() {return ImmutableList.copyOf(lines);}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isDrafted() {return status.isDrafted();}

	public void assertDrafted()
	{
		if (!isDrafted())
		{
			throw new AdempiereException("Expected " + externalId + " to be Drafted but it is " + status);
		}
	}

	public void voidId()
	{
		if (!isDrafted())
		{
			throw new AdempiereException("Only Drafted orders can be voided");
		}

		this.status = POSOrderStatus.Voided;
	}

	public void createOrUpdateLine(@NonNull final String externalId, @NonNull final UnaryOperator<POSOrderLine> updater)
	{
		final int lineIdx = getLineIndexByExternalId(externalId);
		final POSOrderLine line = lineIdx >= 0 ? lines.get(lineIdx) : null;
		final POSOrderLine lineChanged = updater.apply(line);

		if (lineIdx >= 0)
		{
			lines.set(lineIdx, lineChanged);
		}
		else
		{
			lines.add(lineChanged);
		}

		updateTotals();
	}

	private int getLineIndexByExternalId(@NonNull final String externalId)
	{
		for (int i = 0; i < lines.size(); i++)
		{
			if (lines.get(i).getExternalId().equals(externalId))
			{
				return i;
			}
		}
		return -1;
	}

	public void preserveOnlyLineExternalIdsInOrder(final List<String> lineExternalIdsInOrder)
	{
		final HashMap<String, POSOrderLine> linesByExternalId = lines.stream().collect(GuavaCollectors.toHashMapByKey(POSOrderLine::getExternalId));
		lines.clear();

		for (final String lineExternalId : lineExternalIdsInOrder)
		{
			final POSOrderLine line = linesByExternalId.remove(lineExternalId);
			if (line != null)
			{
				lines.add(line);
			}
		}

		updateTotals();
	}
}
