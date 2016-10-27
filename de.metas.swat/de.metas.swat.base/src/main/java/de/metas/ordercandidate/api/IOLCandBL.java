package de.metas.ordercandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.ILoggable;
import org.adempiere.util.ISingletonService;
import org.compiere.model.PO;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.ordercandidate.spi.IOLCandCreator;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.relation.grid.ModelRelationTarget;

/**
 * @author RC
 *
 */
public interface IOLCandBL extends ISingletonService
{
	String MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_0P = "OLCandProcessor.ProcessingError";

	/**
	 * Creates and updates orders.
	 *
	 * @param ctx
	 * @param processor
	 * @param process Optional. If set, progress info is logged to the given instance
	 * @param trxName
	 */
	void process(Properties ctx, I_C_OLCandProcessor processor, ILoggable process, String trxName);

	String mkRelationTypeInternalName(I_C_OLCandProcessor processor);

	/**
	 * Method creates a <code>ModelRelationTarget</code> instance that contains the basic data required to add an explicit relation between the given <code>processor</code> and a set of
	 * {@link I_C_OLCand}s.
	 *
	 * @param processor
	 * @param sourceWindowId
	 * @param sourceTabName
	 * @param whereClause
	 * @return
	 */
	ModelRelationTarget mkModelRelationTarget(I_C_OLCandProcessor processor, int sourceWindowId, String sourceTabName, String whereClause);

	void createOrUpdateOCProcessorRelationType(Properties ctx, ModelRelationTarget model, String trxName);

	/**
	 * Registers a listener to be informed about events regarding order line candidates.
	 *
	 * @param l
	 */
	void registerOLCandListener(IOLCandListener l);

	void registerCustomerGroupingValuesProvider(IOLCandGroupingProvider groupingValuesProvider);

	I_C_OLCand invokeOLCandCreator(PO po);

	I_C_OLCand invokeOLCandCreator(PO po, IOLCandCreator olCandCreator);

	/**
	 * Computes the actual price for the given <code>olc</code>. The outcome depends on
	 * <ul>
	 * <li>{@link I_C_OLCand#isManualDiscount()}</li>
	 * <li>{@link I_C_OLCand#isManualPrice()}</li>
	 * <li>{@link I_C_OLCand#getQty()}</li>
	 * <li>{@link I_C_OLCand#getPriceEntered()}</li>
	 * <li>{@link I_C_OLCand#getDiscount()}</li>
	 * <li>etc</li>
	 * </ul>
	 *
	 * @param olc the order line candidate for which we compute the priceActual
	 * @param qtyOverride if not <code>null</code>, then this value is used instead of {@link I_C_OLCand#getQty()}
	 * @param pricingSystemIdOverride if <code>>0</code>, then this value is used instead of {@link I_C_OLCand#getM_PricingSystem_ID()}
	 * @Param date to be used in retrieving the actual price
	 * @return
	 */
	IPricingResult computePriceActual(I_C_OLCand olCand, BigDecimal qtyOverride, int pricingSystemIdOverride, Timestamp date);
}
