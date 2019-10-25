package de.metas.contracts.process;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.contracts
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.api.IParams;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfigQuery;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.product.ProductId;
import de.metas.util.Services;

public class C_Flatrate_Term_Create_For_BPartners extends C_Flatrate_Term_Create
{
	private final RefundConfigRepository refundConfigRepository = SpringContextHolder.instance.getBean(RefundConfigRepository.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private int p_flatrateconditionsID;

	private Timestamp p_startDate;

	private int p_adUserInChargeId;

	@Override
	protected void prepare()
	{
		final IParams para = getParameterAsIParams();

		p_flatrateconditionsID = para.getParameterAsInt(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, -1);
		p_adUserInChargeId = para.getParameterAsInt(I_C_Flatrate_Term.COLUMNNAME_AD_User_InCharge_ID, -1);
		p_startDate = para.getParameterAsTimestamp(I_C_Flatrate_Term.COLUMNNAME_StartDate);

		final I_C_Flatrate_Conditions conditions = loadOutOfTrx(p_flatrateconditionsID, I_C_Flatrate_Conditions.class);
		setConditions(conditions);

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund.equals(conditions.getType_Conditions()))
		{
			final ConditionsId conditionsId = ConditionsId.ofRepoId(conditions.getC_Flatrate_Conditions_ID());

			final RefundConfigQuery query = RefundConfigQuery.builder()
					.conditionsId(conditionsId)
					.build();

			final List<ProductId> productIds = refundConfigRepository
					.getByQuery(query)
					.stream()
					.map(RefundConfig::getProductId)
					.distinct()
					.collect(Collectors.toCollection(ArrayList::new));
			for (final ProductId productId : productIds)
			{
				if (productId == null)
				{
					addProduct(null);
				}
				else
				{
					final I_M_Product product = loadOutOfTrx(productId, I_M_Product.class);
					addProduct(product);
				}
			}
		}
		else if  (CommissionConstants.TYPE_CONDITIONS_COMMISSION.equals(conditions.getType_Conditions()))
		{
			addProduct(null);
		}
		else
		{
			final List<I_C_Flatrate_Matching> matchings = flatrateDAO.retrieveFlatrateMatchings(conditions);
			if (matchings.size() == 1 && matchings.get(0).getM_Product_ID() > 0)
			{
				// this is the case for quality-based contracts
				addProduct(matchings.get(0).getM_Product());
			}
		}

		if (p_adUserInChargeId > 0)
		{
			final I_AD_User userInCharge = loadOutOfTrx(p_adUserInChargeId, I_AD_User.class);
			setUserInCharge(userInCharge);
		}
		setStartDate(p_startDate);
	}

	@Override
	protected Iterable<I_C_BPartner> getBPartners()
	{
		final IQueryFilter<I_C_BPartner> selectedPartners = getProcessInfo().getQueryFilter();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_BPartner> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner.class, getCtx(), ITrx.TRXNAME_ThreadInherited);

		final Iterator<I_C_BPartner> it = queryBuilder
				.filter(selectedPartners)
				.addOnlyActiveRecordsFilter()
				.orderBy().addColumn(I_C_BPartner.COLUMNNAME_Value).endOrderBy() // to make it easier for the user to browse the logging.
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_BPartner.class);

		return () -> it;
	}
}
