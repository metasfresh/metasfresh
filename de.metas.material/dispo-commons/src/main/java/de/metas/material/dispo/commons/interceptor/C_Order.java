package de.metas.material.dispo.commons.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.event.ModelProductDescriptorExtractor;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free sor version 2 of the
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
@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final OrderAvailableToPromiseTool orderAvailableToPromiseTool;

	public C_Order(
			@NonNull final AvailableToPromiseRepository stockRepository,
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		orderAvailableToPromiseTool = OrderAvailableToPromiseTool.builder()
				.stockRepository(stockRepository)
				.productDescriptorFactory(productDescriptorFactory)
				.build();
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REACTIVATE)
	public void updateQtyAvailableToPromise(final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return; // as of now, ATP values are only of interest for the sales side
		}
		orderAvailableToPromiseTool.updateOrderLineRecords(orderRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void resetQtyAvailableToPromise(final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return; // as of now, ATP values are only of interest for the sales side
		}
		orderAvailableToPromiseTool.resetQtyAvailableToPromise(orderRecord);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_C_Order.COLUMNNAME_PreparationDate })
	public void updateQtyAvailableToPromiseWhenPreparationDateIsChanged(final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return; // as of now, ATP values are only of interest for the sales side
		}
		orderAvailableToPromiseTool.updateOrderLineRecords(orderRecord);
	}
}
