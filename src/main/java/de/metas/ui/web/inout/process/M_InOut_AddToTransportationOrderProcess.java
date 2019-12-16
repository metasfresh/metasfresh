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

package de.metas.ui.web.inout.process;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.GuavaCollectors;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Package;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class M_InOut_AddToTransportationOrderProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Param(parameterName = "M_ShipperTransportation_ID", mandatory = true)
	private I_M_ShipperTransportation transportationOrder;

	public static final String ALL_SELECTED_SHIPMENTS_SHOULD_BE_COMPLETED_MSG = "de.metas.ui.web.inout.process.M_InOut_AddToTransportationOrderProcess.AllSelectedShipmentsShouldBeCompleted";

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final List<I_M_InOut> inOuts = getSelectedInOuts();
		for (final I_M_InOut inOut : inOuts)
		{
			final DocStatus docStatus = DocStatus.ofCode(inOut.getDocStatus());
			if (!docStatus.isCompleted())
			{
				return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(ALL_SELECTED_SHIPMENTS_SHOULD_BE_COMPLETED_MSG));
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	private ImmutableList<I_M_InOut> getSelectedInOuts()
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		return getSelectedRowIds()
				.stream()
				.map(rowId -> inOutDAO.getById(InOutId.ofRepoId(rowId.toInt()), I_M_InOut.class))
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * - take all the Shipments
	 * - filter and take all the unshipped HUs for all the shipments
	 * - assume that all the HUs are already packed correctly
	 * - add the HUs to Transportation Order by calling "huShipperTransportationBL.addHUsToShipperTransportation"
	 */
	@Override
	protected String doIt() throws Exception
	{
		final DocStatus docStatus = DocStatus.ofCode(transportationOrder.getDocStatus());
		if (docStatus.isCompleted())
		{
			// this error should not be thrown since we have AD_Val_Rule for the parameter
			throw new AdempiereException("Transportation Order should not be closed");
		}

		final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
		final ImmutableList<I_M_InOut> selectedInOuts = getSelectedInOuts();

		for (final I_M_InOut inOut : selectedInOuts)
		{
			final ImmutableList<I_M_HU> husFiltered = selectOnlyHUsWithoutShipperTransportation(inOut);
			final ShipperTransportationId shipperTransportationId = ShipperTransportationId.ofRepoId(transportationOrder.getM_ShipperTransportation_ID());
			final boolean anyAdded = !huShipperTransportationBL.addHUsToShipperTransportation(shipperTransportationId, husFiltered).isEmpty();
			if (anyAdded)
			{
				inOut.setM_ShipperTransportation_ID(shipperTransportationId.getRepoId());
				InterfaceWrapperHelper.save(inOut);
				Loggables.addLog("HUs added to M_ShipperTransportation_ID={}", shipperTransportationId);
			}
		}

		return MSG_OK;
	}

	@NonNull
	private static ImmutableList<I_M_HU> selectOnlyHUsWithoutShipperTransportation(final I_M_InOut inOut)
	{
		final List<I_M_HU> huList = Services.get(IHUInOutDAO.class).retrieveHandlingUnits(inOut);
		final ImmutableList<Integer> allHuIds = huList.stream().map(I_M_HU::getM_HU_ID).collect(GuavaCollectors.toImmutableList());

		final ImmutableList<Integer> alreadyInHUs = findAllHUsWithAShipperTransportation(allHuIds);

		return huList
				.stream()
				.filter(it -> !alreadyInHUs.contains(it.getM_HU_ID()))
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * Return the list of HU IDs (as integer) which already are in a Shipper Transportation.
	 * <p>
	 * This java-sql query looks butt-ugly because metasfresh IQueryBL doesn't know how to do JOINs, so we must use subQueries with exists.
	 * <p>
	 * - Original (beautiful query)
	 *
	 * <pre>{@code
	 * SELECT phu.m_hu_id
	 * FROM m_package_hu phu
	 *         INNER JOIN m_package p ON p.m_package_id = phu.m_package_id
	 *         INNER JOIN m_shippingpackage sp ON sp.m_package_id = p.m_package_id
	 *         INNER JOIN m_shippertransportation st ON st.m_shippertransportation_id = sp.m_shippertransportation_id
	 * WHERE phu.m_hu_id IN (1000003, 1000006, 1000007);
	 * }</pre>
	 * <p>
	 * <p>
	 * - Metasfresh-like sql query:
	 *
	 * <pre>{@code
	 * SELECT phu.m_hu_id
	 * FROM m_package_hu phu
	 * WHERE phu.m_hu_id IN (1000003, 1000006, 1000007)
	 *  AND exists
	 *    (
	 *        SELECT 1
	 *        FROM m_package p
	 *        WHERE p.m_package_id = phu.m_package_id
	 *          AND exists
	 *            (
	 *                SELECT 1
	 *                FROM m_shippingpackage sp
	 *
	 *                WHERE sp.m_package_id = p.m_package_id
	 *                  AND exists
	 *                    (
	 *                        SELECT 1 FROM m_shippertransportation st WHERE st.m_shippertransportation_id = sp.m_shippertransportation_id
	 *                    )
	 *            )
	 *    )
	 * }</pre>
	 */
	@NonNull
	private static ImmutableList<Integer> findAllHUsWithAShipperTransportation(@NonNull final ImmutableList<Integer> allHuIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_ShipperTransportation> subQuery__M_ShipperTransportation = queryBL
				.createQueryBuilder(I_M_ShipperTransportation.class)
				.create();

		final IQuery<I_M_ShippingPackage> subQuery__M_ShippingPackage__M_ShipperTransportation = queryBL
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addInSubQueryFilter()
				.matchingColumnNames(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
				.subQuery(subQuery__M_ShipperTransportation)
				.end()
				.create();

		final IQuery<I_M_Package> subQuery__M_Package__M_ShippingPackage__M_ShipperTransportation = queryBL
				.createQueryBuilder(I_M_Package.class)
				.addInSubQueryFilter()
				.matchingColumnNames(I_M_Package.COLUMNNAME_M_Package_ID, I_M_ShippingPackage.COLUMNNAME_M_Package_ID)
				.subQuery(subQuery__M_ShippingPackage__M_ShipperTransportation)
				.end()
				.create();

		final List<Integer> list = queryBL
				.createQueryBuilder(I_M_Package_HU.class)
				.addInArrayFilter(I_M_Package_HU.COLUMNNAME_M_HU_ID, allHuIds)
				.addInSubQueryFilter()
				.matchingColumnNames(I_M_Package_HU.COLUMNNAME_M_Package_ID, I_M_Package.COLUMNNAME_M_Package_ID)
				.subQuery(subQuery__M_Package__M_ShippingPackage__M_ShipperTransportation)
				.end()
				.andCollect(I_M_Package_HU.COLUMN_M_HU_ID, I_M_HU.class)
				.create()
				.listIds();

		return ImmutableList.copyOf(list);
	}
}
