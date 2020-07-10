package de.metas.ui.web.pricing.process;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_Product;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class M_DiscountSchemaBreak_CopyToOtherSchema_Product extends ViewBasedProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private static final AdMessageKey MSG = AdMessageKey.of("de.metas.ui.web.pricing.process.M_DiscountSchemaBreak_CopyToOtherSchema_Product.NoSingleProduct");

	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);

	final String PARAM_M_Product_ID = I_M_Product.COLUMNNAME_M_Product_ID;

	@Param(parameterName = I_M_DiscountSchema.COLUMNNAME_M_DiscountSchema_ID, mandatory = true)
	private int p_PricingConditionsId;

	@Param(parameterName = PARAM_M_Product_ID, mandatory = true)
	private int p_ProductId;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!rowsHaveSingleProductId())
		{
			final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG);
			return ProcessPreconditionsResolution.reject(msg);
		}

		return ProcessPreconditionsResolution.accept();
	}

	private boolean rowsHaveSingleProductId()
	{
		// getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false)); doesn't work from checkPreconditionsApplicable
		final SqlViewRowsWhereClause viewSqlWhereClause = getViewSqlWhereClause(getSelectedRowIds());
		final IQueryFilter<I_M_DiscountSchemaBreak> selectionQueryFilter = viewSqlWhereClause.toQueryFilter();

		return pricingConditionsRepo.isSingleProductId(selectionQueryFilter);
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();
		if (PARAM_M_Product_ID.equals(parameterName))
		{
			final IQueryFilter<I_M_DiscountSchemaBreak> selectionQueryFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

			final ProductId uniqueProductIdForSelection = pricingConditionsRepo.retrieveUniqueProductIdForSelectionOrNull(selectionQueryFilter);

			if (uniqueProductIdForSelection == null)
			{
				// should not happen because of the preconditions above
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
			}

			return uniqueProductIdForSelection.getRepoId();
		}

		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	private SqlViewRowsWhereClause getViewSqlWhereClause(@NonNull final DocumentIdsSelection rowIds)
	{
		final String breaksTableName = I_M_DiscountSchemaBreak.Table_Name;
		return getView().getSqlWhereClause(rowIds, SqlOptions.usingTableName(breaksTableName));
	}

	@Override
	protected String doIt()
	{
		final IQueryFilter<I_M_DiscountSchemaBreak> queryFilter = getProcessInfo().getQueryFilterOrElse(null);
		if (queryFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		final boolean allowCopyToSameSchema = true;

		pricingConditionsRepo.copyDiscountSchemaBreaksWithProductId(queryFilter,
				PricingConditionsId.ofRepoId(p_PricingConditionsId),
				ProductId.ofRepoId(p_ProductId),
				allowCopyToSameSchema);

		return MSG_OK;
	}

}
