package de.metas.materialtracking.model;

import java.math.BigDecimal;

import org.eevolution.model.X_PP_Cost_Collector;
import org.eevolution.model.X_PP_Order_BOMLine;

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
		 * A raw material issue. Related to {@link X_PP_Cost_Collector#COSTCOLLECTORTYPE_ComponentIssue}
		 */
		RawMaterial(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, X_PP_Order_BOMLine.COMPONENTTYPE_Component, 1),

		/**
		 * Related to {@link X_PP_Cost_Collector#COSTCOLLECTORTYPE_MaterialReceipt}
		 */
		MainProduct(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt, null, 1),

		/**
		 * Related to {@link X_PP_Cost_Collector#COSTCOLLECTORTYPE_MixVariance} with a <b>negative</b> quantity
		 */
		CoProduct(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product,-1),

		/**
		 * Related to {@link X_PP_Cost_Collector#COSTCOLLECTORTYPE_MixVariance} with a <b>negative</b> quantity
		 */
		ByProduct(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product, -1);

		private final String costCollectorType;
		private final String bomLineComponentType;
		private final int factor;

		Type(final String costCollectorType,
				final String bomLineComponentType,
				final int factor)
		{
			this.costCollectorType = costCollectorType;
			this.bomLineComponentType = bomLineComponentType;
			this.factor = factor;
		}

		public String getCostCollectorType()
		{
			return costCollectorType;
		}

		public BigDecimal getFactor()
		{
			return new BigDecimal(factor);
		}

		public String getBomLineComponentType()
		{
			return bomLineComponentType;
		}
	}
}
