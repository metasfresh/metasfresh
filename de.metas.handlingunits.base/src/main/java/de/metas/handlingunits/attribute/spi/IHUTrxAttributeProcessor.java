package de.metas.handlingunits.attribute.spi;

/*
 * #%L
 * de.metas.handlingunits.base
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


import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.hutransaction.IHUTransactionProcessor;
import de.metas.handlingunits.model.I_M_HU_Trx_Attribute;
import de.metas.handlingunits.model.X_M_HU_Trx_Attribute;

/**
 * Implementations of this interface are responsible for processing a given {@link I_M_HU_Trx_Attribute}.
 * <p>
 * This interface and its implementors are currently intended to be used by {@link IHUTransactionProcessor}. Note that <code>IHUTransactionProcessor</code> currently has no register methods, because
 * we think they are not yet needed.
 *
 * @author tsa
 *
 */
public interface IHUTrxAttributeProcessor
{
	/**
	 * Process an {@link I_M_HU_Trx_Attribute} which has {@link X_M_HU_Trx_Attribute#OPERATION_Save} operation.
	 *
	 * @param huContext
	 * @param huTrxAttribute
	 * @param referencedModel
	 */
	public void processSave(IHUContext huContext, I_M_HU_Trx_Attribute huTrxAttribute, Object referencedModel);

	/**
	 * Process an {@link I_M_HU_Trx_Attribute} which has {@link X_M_HU_Trx_Attribute#OPERATION_Drop} operation.
	 *
	 * @param huContext
	 * @param huTrxAttribute
	 * @param referencedModel
	 */
	public void processDrop(IHUContext huContext, I_M_HU_Trx_Attribute huTrxAttribute, Object referencedModel);
}
