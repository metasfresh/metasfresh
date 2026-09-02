package de.metas.handlingunits.picking.job.model;

import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public enum QtyAvailableStatus
{
	// NOTE: keep in sync with QtyAvailableStatus.js
	NOT_AVAILABLE,
	PARTIALLY_AVAILABLE,
	FULLY_AVAILABLE,
	;

	public static QtyAvailableStatus computeOfQtyRequiredAndQtyAvailable(@NonNull final Quantity qtyRequired, @NonNull final Quantity qtyAvailable)
	{
		final Quantity qtyNotAvailable = qtyRequired.subtract(qtyAvailable);

		if (qtyNotAvailable.signum() <= 0)
		{
			return QtyAvailableStatus.FULLY_AVAILABLE;
		}
		else if (qtyAvailable.signum() > 0)
		{
			return QtyAvailableStatus.PARTIALLY_AVAILABLE;
		}
		else
		{
			return QtyAvailableStatus.NOT_AVAILABLE;
		}
	}

	public static <T> Optional<QtyAvailableStatus> computeOfLines(
			@NonNull final Collection<T> lines,
			@NonNull final Function<T, QtyAvailableStatus> getStatusFunc)
	{
		if (lines.isEmpty())
		{
			return Optional.empty();
		}

		boolean hasNotAvailableProducts = false;
		boolean hasFullyAvailableProducts = false;

		for (final T line : lines)
		{
			@Nullable final QtyAvailableStatus lineStatus = getStatusFunc.apply(line);
			if (lineStatus == null)
			{
				return Optional.empty();
			}

			switch (lineStatus)
			{
				case NOT_AVAILABLE:
					hasNotAvailableProducts = true;
					break;
				case PARTIALLY_AVAILABLE:
					return Optional.of(QtyAvailableStatus.PARTIALLY_AVAILABLE);
				case FULLY_AVAILABLE:
					hasFullyAvailableProducts = true;
					break;
				default:
					throw new AdempiereException("Unknown QtyAvailableStatus: " + lineStatus);
			}
		}

		if (hasFullyAvailableProducts)
		{
			return hasNotAvailableProducts ? Optional.of(QtyAvailableStatus.PARTIALLY_AVAILABLE) : Optional.of(QtyAvailableStatus.FULLY_AVAILABLE);
		}
		else
		{
			return Optional.of(QtyAvailableStatus.NOT_AVAILABLE);
		}

	}

	public boolean isPartialOrFullyAvailable() {return this == PARTIALLY_AVAILABLE || this == FULLY_AVAILABLE;}
}
