package de.metas.ui.web.product.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.model.I_M_Product_LotNumber_Lock;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * https://github.com/metasfresh/metasfresh/issues/3693
 * This process will search for HUs containing products with LotNo attributes that fit the pairs in the selected I_M_Product_LotNumber_Lock entries.
 * If such HUs are found, they will all be put in a DD_Order and sent to the Quarantine warehouse.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_M_Product_LotNumber_Lock extends ViewBasedProcessTemplate implements IProcessPrecondition
{

	@Override
	protected String doIt() throws Exception
	{
		getView().streamByIds(getSelectedRowIds())
				.map(row -> row.getId().toInt())
				.distinct()
				.forEach(id -> blockHUsForLotNo(id));
		return MSG_OK;
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	private void blockHUsForLotNo(final int lotNoLockId)
	{
		final I_M_Product_LotNumber_Lock lotNoLock = load(lotNoLockId, I_M_Product_LotNumber_Lock.class);
		final I_M_Attribute lotNumberAttr = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberAttribute(getCtx());

		if (lotNumberAttr == null)
		{
			throw new AdempiereException("Not lotNo attribute found.");
		}

		final int productId = lotNoLock.getM_Product_ID();
		final int lotNoAttributeID = lotNumberAttr.getM_Attribute_ID();
		final String lotNoValue = lotNoLock.getLot();

		final List<I_M_HU> husForAttributeStringValue = retrieveHUsForAttributeStringValue(productId, lotNoAttributeID, lotNoValue);

		Services.get(IHUDDOrderBL.class).createBlockDDOrderForHUs(husForAttributeStringValue);

	}

	private List<I_M_HU> retrieveHUsForAttributeStringValue(final int productId, final int attributeId, final String value)
	{
		return Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder()
				.addOnlyWithProductId(productId)
				.list()
				.stream()
				.filter(hu -> hasHUAttributeAndValue(hu, attributeId, value))
				.collect(ImmutableList.toImmutableList());

	}

	private boolean hasHUAttributeAndValue(final I_M_HU hu, final int attributeId, final String value)
	{
		 final I_M_HU_Attribute lotNoHUAttribute = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Attribute.class, hu)
				.filter(new EqualsQueryFilter<I_M_HU_Attribute>(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, hu.getM_HU_ID()))
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_Attribute_ID, attributeId)
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_Value, value)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.firstOnly(I_M_HU_Attribute.class);
		
		return lotNoHUAttribute != null;
	}

}
