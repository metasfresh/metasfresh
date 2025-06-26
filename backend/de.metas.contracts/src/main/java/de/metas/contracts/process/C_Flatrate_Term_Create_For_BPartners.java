package de.metas.contracts.process;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfigQuery;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.Param;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_Commission;
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

public class C_Flatrate_Term_Create_For_BPartners extends C_Flatrate_Term_Create implements IProcessParametersCallout
{
	private final RefundConfigRepository refundConfigRepository = SpringContextHolder.instance.getBean(RefundConfigRepository.class);
	private final CommissionProductService commissionProductService = SpringContextHolder.instance.getBean(CommissionProductService.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	private static final String PARAM_C_FLATRATE_CONDITIONS_ID = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID;
	@Param(parameterName = PARAM_C_FLATRATE_CONDITIONS_ID, mandatory = true)
	private int p_flatrateconditionsID;

	private Timestamp p_startDate;

	private int p_adUserInChargeId;

	private static final String PARAM_IS_SIMULATION = I_C_Flatrate_Term.COLUMNNAME_IsSimulation;
	@Param(parameterName = PARAM_IS_SIMULATION, mandatory = true)
	private String isSimulation;

	private static final String PARAM_SHOW_SIMULATION_FLAG = "showSimulationFlag";
	@Param(parameterName = PARAM_SHOW_SIMULATION_FLAG)
	private boolean showSimulationFlag;

	private static final String PARAM_IS_COMPLETE_DOCUMENT = "IsComplete";
	@Param(parameterName = PARAM_IS_COMPLETE_DOCUMENT, mandatory = true)
	private boolean p_completeDocument;

	@Override
	protected void prepare()
	{
		final IParams para = getParameterAsIParams();

		p_flatrateconditionsID = para.getParameterAsInt(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, -1);
		p_adUserInChargeId = para.getParameterAsInt(I_C_Flatrate_Term.COLUMNNAME_AD_User_InCharge_ID, -1);
		p_startDate = para.getParameterAsTimestamp(I_C_Flatrate_Term.COLUMNNAME_StartDate);
		p_completeDocument = para.getParameterAsBool(PARAM_IS_COMPLETE_DOCUMENT);

		setIsCompleteDocument(p_completeDocument);

		final I_C_Flatrate_Conditions conditions = flatrateDAO.getConditionsById(p_flatrateconditionsID);
		setConditions(conditions);

		final ConditionsId conditionsId = ConditionsId.ofRepoId(conditions.getC_Flatrate_Conditions_ID());

		final TypeConditions typeConditions = TypeConditions.ofCode(conditions.getType_Conditions());

		switch (typeConditions)
		{
			case FLAT_FEE:
				final I_M_Product flatFeeProduct = loadOutOfTrx(conditions.getM_Product_Flatrate_ID(), I_M_Product.class);
				addProduct(flatFeeProduct);
				break;
			case REFUND:
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
						final I_M_Product refundProduct = loadOutOfTrx(productId, I_M_Product.class);
						addProduct(refundProduct);
					}
				}
				break;
			case COMMISSION:
			case MEDIATED_COMMISSION:
			case MARGIN_COMMISSION:
			case LICENSE_FEE:
				final I_M_Product commissionProductRecord = loadOutOfTrx(commissionProductService.getCommissionProduct(conditionsId), I_M_Product.class);
				addProduct(commissionProductRecord);
				break;
			case REFUNDABLE:
				addProduct(null);
				break;
			default:
				final List<I_C_Flatrate_Matching> matchings = flatrateDAO.retrieveFlatrateMatchings(conditions);
				if (matchings.size() == 1 && matchings.get(0).getM_Product_ID() > 0)
				{
					// this is the case for quality-based contracts
					final I_M_Product productRecord = productDAO.getById(matchings.get(0).getM_Product_ID());
					addProduct(productRecord);
				}
		}

		if (p_adUserInChargeId > 0)
		{
			final I_AD_User userInCharge = loadOutOfTrx(p_adUserInChargeId, I_AD_User.class);
			setUserInCharge(userInCharge);
		}
		setStartDate(p_startDate);

		//so far via this process, only commission type contracts can be created as a `Simulation`.
		if(TYPE_CONDITIONS_Commission.equals(conditions.getType_Conditions()))
		{
			setIsSimulation(StringUtils.toBoolean(isSimulation));
		}
	}

	@Override
	protected Iterable<I_C_BPartner> getBPartners()
	{
		final IQueryFilter<I_C_BPartner> selectedPartners = getProcessInfo().getQueryFilterOrElseFalse();

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

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_C_FLATRATE_CONDITIONS_ID.equals(parameterName))
		{
			showSimulationFlag = p_flatrateconditionsID > 0
					&& TYPE_CONDITIONS_Commission.equals(flatrateDAO.getConditionsById(p_flatrateconditionsID).getType_Conditions());
		}
	}
}
