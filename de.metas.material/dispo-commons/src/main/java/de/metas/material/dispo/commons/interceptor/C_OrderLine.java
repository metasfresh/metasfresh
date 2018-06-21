package de.metas.material.dispo.commons.interceptor;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.material.dispo.commons.model.I_C_OrderLine;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery.AvailableToPromiseQueryBuilder;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.event.commons.AttributesKey;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Interceptor(I_C_OrderLine.class)
@Component("MaterialDispoOrderLineInterceptor")
public class C_OrderLine
{
	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW })
	public void updateQtyAvailableToPromise(final I_C_OrderLine ol)
	{
		 final AvailableToPromiseRepository stockRepository = Adempiere.getBean(AvailableToPromiseRepository.class);
		 final AvailableToPromiseQueryBuilder stockQueryBuilder = AvailableToPromiseQuery.builder()
					.warehouseIds(ImmutableSet.of(ol.getM_Warehouse_ID()))
					.productId(ol.getM_Product_ID())
					.bpartnerId(ol.getC_BPartner_ID())
					.date(TimeUtil.asLocalDateTime(ol.getDatePromised()));

			// Add query attributes
			final int asiId = ol.getM_AttributeSetInstance_ID();
			if (asiId > 0)
			{
				stockQueryBuilder.storageAttributesKey(AttributesKeys
						.createAttributesKeyFromASIStorageAttributes(asiId)
						.orElse(AttributesKey.ALL));
			}

		 final AvailableToPromiseQuery query = stockQueryBuilder.build();
		final BigDecimal qtyAvailableToPromise = stockRepository.retrieveAvailableStockQtySum(query);
		ol.setQty_AvailableToPromise(qtyAvailableToPromise);
	}
}
