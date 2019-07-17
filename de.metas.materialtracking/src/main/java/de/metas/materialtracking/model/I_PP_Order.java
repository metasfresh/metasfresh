package de.metas.materialtracking.model;

import java.math.BigDecimal;

import org.eevolution.api.BOMComponentType;
import org.eevolution.api.CostCollectorType;

import de.metas.invoicecandidate.model.IIsInvoiceCandidateAware;

/*
 * #%L
 * de.metas.materialtracking
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

public interface I_PP_Order extends
		org.eevolution.model.I_PP_Order,
		IPPOrderQualityFields,
		IMaterialTrackingAware,
		IIsInvoiceCandidateAware
{
	public enum Type
	{
		/**
		 * A raw material issue. Related to {@link CostCollectorType#ComponentIssue}
		 */
		RawMaterial(CostCollectorType.ComponentIssue, BOMComponentType.Component, 1),

		/**
		 * Related to {@link CostCollectorType#MaterialReceipt}
		 */
		MainProduct(CostCollectorType.MaterialReceipt, null, 1),

		/**
		 * Related to {@link CostCollectorType#MixVariance} with a <b>negative</b> quantity
		 */
		CoProduct(CostCollectorType.MixVariance, BOMComponentType.CoProduct,-1),

		/**
		 * Related to {@link CostCollectorType#MixVariance} with a <b>negative</b> quantity
		 */
		ByProduct(CostCollectorType.MixVariance, BOMComponentType.ByProduct, -1);

		private CostCollectorType costCollectorType;
		private BOMComponentType bomLineComponentType;
		private int factor;

		Type(final CostCollectorType costCollectorType,
				final BOMComponentType bomLineComponentType,
				final int factor)
		{
			this.costCollectorType = costCollectorType;
			this.bomLineComponentType = bomLineComponentType;
			this.factor = factor;
		}

		public CostCollectorType getCostCollectorType()
		{
			return costCollectorType;
		}

		public BigDecimal getFactor()
		{
			return new BigDecimal(factor);
		}

		public BOMComponentType getBomLineComponentType()
		{
			return bomLineComponentType;
		}
	}
}
