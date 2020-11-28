package de.metas.fresh.picking.form;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableSet;

/**
 * Collect DeliveryDates for {@link TableRow}s
 *
 * @author tsa
 *
 */
class TableRowDeliveryDatesCollector implements Predicate<TableRow>
{
	private Set<LocalDate> deliveryDates = new TreeSet<>();

	/**
	 * Returns always <code>false</code>, but if the given <code>tableRow</code> has a <code>DeliveryDate</code>,
	 * then it adds that date to the result that is returned by {@link #getDeliveryDates()}.
	 *
	 * @return always <code>false</code>.
	 */
	@Override
	public boolean test(final TableRow tableRow)
	{
		LocalDate deliveryDate = TimeUtil.asLocalDate(tableRow.getDeliveryDate());
		if (deliveryDate == null)
		{
			// shall not happen, but skip it
			return false;
		}

		deliveryDates.add(deliveryDate);

		return false;
	}

	/**
	 *
	 * @return collected DeliveryDates
	 */
	public Set<LocalDate> getDeliveryDates()
	{
		return ImmutableSet.copyOf(deliveryDates);
	}
}
