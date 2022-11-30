package de.metas.inoutcandidate.spi;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

import java.util.List;

/**
 * Implementations of this class are responsible for creating/updating/deleting receipt schedules from given <code>model</code>
 * 
 * @author tsa
 * 
 */
public interface IReceiptScheduleProducer
{
	/**
	 * Sets factory who instantiated this producer.
	 * 
	 * NOTE: don't call it directly, it will be called by API on instantiation
	 */
	void setReceiptScheduleProducerFactory(final IReceiptScheduleProducerFactory factory);

	IReceiptScheduleProducerFactory getReceiptScheduleProducerFactory();

	/**
	 * @return receipt schedules which were created by this method; If implementations of this method are not creating any new receipt schedules but only update <code>previousSchedules</code> this
	 *         method will return an empty list
	 */
	List<I_M_ReceiptSchedule> createOrUpdateReceiptSchedules(Object model, List<I_M_ReceiptSchedule> previousSchedules);

	/**
	 * Called by API when underlying document(line) was changed.
	 * 
	 * Compared with {@link #createOrUpdateReceiptSchedules(Object, List)} this method is just updating existing receipt schedules. Will never create new ones.
	 */
	void updateReceiptSchedules(Object model);

	/**
	 * Called by API when underlying document was deleted or re-activated.
	 */
	void inactivateReceiptSchedules(Object model);
}
