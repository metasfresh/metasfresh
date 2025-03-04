/*
 * #%L
 * de.metas.printing.base
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

package de.metas.printing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.cache.CCache;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
public class HardwarePrinterRepository
{
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	private final CCache<Integer, HardwarePrinterMap> cache = CCache.<Integer, HardwarePrinterMap>builder()
			.tableName(I_AD_PrinterHW.Table_Name)
			.additionalTableNameToResetFor(I_AD_PrinterHW_MediaTray.Table_Name)
			.build();

	public HardwarePrinter getById(@NonNull final HardwarePrinterId id)
	{
		return getMap().getById(id);
	}

	public Collection<HardwarePrinter> getByIds(@NonNull final Collection<HardwarePrinterId> ids)
	{
		return getMap().getByIds(ids);
	}

	public Stream<HardwarePrinter> streamByIds(@NonNull final Collection<HardwarePrinterId> ids)
	{
		return getMap().streamByIds(ids);
	}

	private HardwarePrinterMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private HardwarePrinterMap retrieveMap()
	{
		final ImmutableListMultimap<HardwarePrinterId, HardwareTray> traysByPrinterId = printingDAO.streamActiveMediaTrays()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						trayRecord -> HardwarePrinterId.ofRepoId(trayRecord.getAD_PrinterHW_ID()),
						HardwarePrinterRepository::fromRecord
				));

		return printingDAO.streamActiveHardwarePrinters()
				.map(printerRecord -> fromRecord(printerRecord, traysByPrinterId))
				.collect(GuavaCollectors.collectUsingListAccumulator(HardwarePrinterMap::new));
	}

	public static void validateOnBeforeSave(@NonNull final I_AD_PrinterHW printerRecord)
	{
		fromRecord(printerRecord, ImmutableListMultimap.of());
	}

	@NonNull
	private static HardwarePrinter fromRecord(
			@NonNull final I_AD_PrinterHW printerRecord,
			@NonNull final ImmutableListMultimap<HardwarePrinterId, HardwareTray> traysByPrinterId)
	{
		final HardwarePrinterId id = HardwarePrinterId.ofRepoId(printerRecord.getAD_PrinterHW_ID());

		return HardwarePrinter.builder()
				.id(id)
				.name(printerRecord.getName())
				.outputType(OutputType.ofCode(printerRecord.getOutputType()))
				.externalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoIdOrNull(printerRecord.getExternalSystem_Config_ID()))
				.ippUrl(extractIPPUrl(printerRecord))
				.trays(traysByPrinterId.get(id))
				.build();
	}

	@Nullable
	private static URI extractIPPUrl(final @NonNull I_AD_PrinterHW printerRecord)
	{
		final String ippUrl = StringUtils.trimBlankToNull(printerRecord.getIPP_URL());
		if (ippUrl == null)
		{
			return null;
		}
		return URI.create(ippUrl);
	}

	@NonNull
	private static HardwareTray fromRecord(@NonNull final I_AD_PrinterHW_MediaTray trayRecord)
	{
		final HardwareTrayId trayId = HardwareTrayId.ofRepoId(trayRecord.getAD_PrinterHW_ID(), trayRecord.getAD_PrinterHW_MediaTray_ID());
		return new HardwareTray(trayId, trayRecord.getName(), trayRecord.getTrayNumber());
	}

	public void deleteCalibrations(@NonNull final HardwarePrinterId printerId) {printingDAO.deleteCalibrations(printerId);}

	public void deleteMediaTrays(@NonNull final HardwarePrinterId hardwarePrinterId) {printingDAO.deleteMediaTrays(hardwarePrinterId);}

	public void deleteMediaSizes(@NonNull final HardwarePrinterId hardwarePrinterId) {printingDAO.deleteMediaSizes(hardwarePrinterId);}

	//
	//
	//
	//
	//

	private static class HardwarePrinterMap
	{
		private final ImmutableMap<HardwarePrinterId, HardwarePrinter> byId;

		private HardwarePrinterMap(final List<HardwarePrinter> list)
		{
			this.byId = Maps.uniqueIndex(list, HardwarePrinter::getId);
		}

		public HardwarePrinter getById(@NonNull final HardwarePrinterId id)
		{
			final HardwarePrinter hardwarePrinter = byId.get(id);
			if (hardwarePrinter == null)
			{
				throw new AdempiereException("No active hardware printer found for id " + id);
			}
			return hardwarePrinter;
		}

		public Collection<HardwarePrinter> getByIds(final @NonNull Collection<HardwarePrinterId> ids)
		{
			return streamByIds(ids).collect(ImmutableList.toImmutableList());
		}

		public Stream<HardwarePrinter> streamByIds(final @NonNull Collection<HardwarePrinterId> ids)
		{
			if (ids.isEmpty())
			{
				return Stream.empty();
			}

			return ids.stream()
					.map(byId::get)
					.filter(Objects::nonNull);
		}

	}
}
