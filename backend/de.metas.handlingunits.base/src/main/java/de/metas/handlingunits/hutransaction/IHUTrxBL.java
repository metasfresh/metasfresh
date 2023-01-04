/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.hutransaction;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IHUTrxBL extends ISingletonService
{
	/**
	 * Transfer <code>request</code> from <code>source</code> to <code>destination</code>
	 */
	void transfer(IHUContext huContext, IAllocationSource source, IAllocationDestination destination, IAllocationRequest request);

	/**
	 * Create and <b>process</b> transaction lines for the candidates (i.e. {@link IHUTransactionCandidate}s) included in the given {@code result}.
	 */
	void createTrx(IHUContext huContext, IAllocationResult result);

	Date getDateTrx(I_M_HU_Trx_Line line);

	/**
	 * Register given {@link IHUTrxListener}.
	 * <p>
	 * If <code>trxListener</code> was already registered then it won't be registered again.
	 */
	void addListener(IHUTrxListener trxListener);

	/**
	 * @return all system registered transaction listeners
	 */
	List<IHUTrxListener> getHUTrxListenersList();

	/**
	 * @return the record that is referenced by the given {@code trxLine} via its {@code AD_Table_ID} and {@code Record_ID} values, or <code>null</code>.
	 */
	<T> T getReferencedObjectOrNull(I_M_HU_Trx_Line trxLine, Class<T> modelClass);

	void setReferencedObject(I_M_HU_Trx_Line trxLine, Object referencedModel);

	/**
	 * Create reversals of given transaction lines and process them.
	 * <p>
	 * All reversals will be grouped in one transaction header.
	 *
	 * <code>trxLines</code> shall contain the transaction but also the counterpart transaction. If not, an error will be thrown.
	 */
	void reverseTrxLines(IHUContext huContext, List<I_M_HU_Trx_Line> trxLines);
	
	default void unlinkFromParentBeforeDestroy(
			final IHUContext huContext,
			@NonNull final I_M_HU hu,
			final boolean destroyOldParentIfEmptyStorage)
	{
		setParentHU(ChangeParentHURequest.builder()
				.huContext(huContext)
				.parentHUItem(null)
				.hu(hu)
				.destroyOldParentIfEmptyStorage(destroyOldParentIfEmptyStorage)
				.failIfAggregateTU(false)
				.build());
	}

	default void setParentHU(final IHUContext huContext, @Nullable final I_M_HU_Item parentHUItem, final I_M_HU hu)
	{
		setParentHU(ChangeParentHURequest.builder()
				.huContext(huContext)
				.parentHUItem(parentHUItem)
				.hu(hu)
				.build());
	}

	@Value
	@Builder
	class ChangeParentHURequest
	{
		@Nullable IHUContext huContext;

		/**
		 * Can be <code>null</code> to indicate that the HU shall be removed from its parent.
		 * <p>
		 * If the given item has the same <code>M_HU_Item_ID</code> as the given <code>hu</code>'s <code>M_HU_Item_Parent_ID</code>,
		 * or if both this parameter and the hu's parent item are <code>null</code>, then the method does nothing.
		 */
		@Nullable I_M_HU_Item parentHUItem;
		@NonNull I_M_HU hu;

		/**
		 * if true, it will check if old parent is empty after removing given HU from it and if yes, it will destroy it
		 */
		@Builder.Default
		boolean destroyOldParentIfEmptyStorage = true;

		@Builder.Default
		boolean failIfAggregateTU = true;
	}

	/**
	 * Link a given {@code hu} to its parent.
	 */
	void setParentHU(ChangeParentHURequest request);

	/**
	 * Take out the given HU from it's parent (if it's not already a top level HU)
	 */
	void extractHUFromParentIfNeeded(I_M_HU hu);

	void extractHUFromParentIfNeeded(@Nullable IHUContext huContext, @NonNull I_M_HU hu);

	IHUContextProcessorExecutor createHUContextProcessorExecutor(IHUContext huContext);

	IHUContextProcessorExecutor createHUContextProcessorExecutor(IContextAware context);

	/**
	 * @return executor which will run using current environment and thread inherited trx
	 */
	default IHUContextProcessorExecutor createHUContextProcessorExecutor()
	{
		return createHUContextProcessorExecutor(PlainContextAware.newWithThreadInheritedTrx());
	}

	/**
	 * Convenient method to process using current env and thread inherited trx.
	 */
	default void process(final Consumer<IHUContext> processor)
	{
		createHUContextProcessorExecutor().run(processor);
	}

	default <R> R process(final Function<IHUContext, R> procesor)
	{
		final Mutable<R> resultHolder = new Mutable<>();
		createHUContextProcessorExecutor().run(huContext -> {
			final R result = procesor.apply(huContext);
			resultHolder.setValue(result);
		});

		return resultHolder.getValue();
	}

	/**
	 * Iterate the {@link IHUTransactionCandidate}s that were added so far and aggregate those that only differ in their quantity.
	 * In other words, group the them by their properties (besides qty) and store a new list with summed-up qtys. The new candidates have unique properties.
	 */
	List<IHUTransactionCandidate> aggregateTransactions(List<IHUTransactionCandidate> transactions);

	static I_C_UOM extractUOMOrNull(@NonNull final I_M_HU_Trx_Line trxLine)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(trxLine.getC_UOM_ID());
		return uomId != null
				? Services.get(IUOMDAO.class).getById(uomId)
				: null;
	}

	static I_M_Product extractProductOrNull(@NonNull final I_M_HU_Trx_Line trxLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(trxLine.getM_Product_ID());
		return productId != null
				? Services.get(IProductDAO.class).getById(productId)
				: null;
	}
}
