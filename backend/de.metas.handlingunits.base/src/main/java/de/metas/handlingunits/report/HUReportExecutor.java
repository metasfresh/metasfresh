package de.metas.handlingunits.report;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.Language;
import de.metas.printing.IMassPrintingService;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.report.DocumentReportService;
import de.metas.report.PrintCopies;
import de.metas.report.server.ReportConstants;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * This little class is specialized on executing HU report processes.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@ToString(doNotUseGetters = true, exclude = "ctx")
public class HUReportExecutor
{
	public static HUReportExecutor newInstance(final Properties ctx)
	{
		return new HUReportExecutor(ctx);
	}

	public static HUReportExecutor newInstance()
	{
		return new HUReportExecutor(Env.getCtx());
	}

	private static final String PROCESS_PARAM_AD_Process_ID = "AD_Process_ID";

	@NonNull private final Properties ctx;
	private int windowNo = Env.WINDOW_None;
	@NonNull private PrintCopies numberOfCopies = PrintCopies.ONE;
	@Nullable private AdProcessId adJasperProcessId;
	@NonNull private OptionalBoolean printPreview = OptionalBoolean.UNKNOWN;

	private HUReportExecutor(@NonNull final Properties ctx)
	{
		this.ctx = ctx;
	}

	/**
	 * Give this service a window number. The default is {@link Env#WINDOW_None}.
	 */
	public HUReportExecutor windowNo(final int windowNo)
	{
		this.windowNo = windowNo;
		return this;
	}

	/**
	 * Specify the number of copies. One means one printout. The default is one.
	 */
	public HUReportExecutor numberOfCopies(@NonNull final PrintCopies numberOfCopies)
	{
		Check.assume(numberOfCopies.isGreaterThanZero(), "numberOfCopies > 0");
		this.numberOfCopies = numberOfCopies;
		return this;
	}

	public HUReportExecutor adJasperProcessId(final AdProcessId adJasperProcessId)
	{
		this.adJasperProcessId = adJasperProcessId;
		return this;
	}

	public HUReportExecutor printPreview(final boolean printPreview)
	{
		this.printPreview = OptionalBoolean.ofBoolean(printPreview);
		return this;
	}

	public HUReportExecutorResult executeNow(final AdProcessId adProcessId, @NonNull final List<HUToReport> husToProcess)
	{
		return executeNow(HUReportRequest.builder()
				.ctx(ctx)
				.adProcessId(adProcessId)
				.windowNo(windowNo)
				.copies(numberOfCopies)
				.adJasperProcessId(adJasperProcessId)
				.printPreview(printPreview.toBooleanOrNull())
				.adLanguage(extractReportingLanguageFromHUs(husToProcess).orElse(null))
				.huIdsToProcess(extractHUIds(husToProcess))
				.onErrorThrowException(true)
				.build());
	}

	private static ImmutableSet<HuId> extractHUIds(final Collection<HUToReport> hus)
	{
		return hus.stream()
				.map(HUToReport::getHUId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private Optional<String> extractReportingLanguageFromHUs(final Collection<HUToReport> hus)
	{
		final Set<BPartnerId> huBPartnerIds = hus.stream()
				.map(HUToReport::getBPartnerId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		return extractReportingLanguageFromBPartnerIds(huBPartnerIds);
	}

	private Optional<String> extractReportingLanguageFromBPartnerIds(final Set<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.size() == 1)
		{
			final BPartnerId bpartnerId = bpartnerIds.iterator().next();
			final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
			return bpartnerBL.getLanguage(bpartnerId).map(Language::getAD_Language);
		}
		else
		{
			return Optional.empty();
		}
	}

	private static HUReportExecutorResult executeNow(@NonNull final HUReportRequest request)
	{
		final Properties ctx = request.getCtx();

		final ImmutableSet<HuId> huIdsToProcess = request.getHuIdsToProcess();

		//
		// Make sure the HU QR codes are generated
		SpringContextHolder.instance.getBean(HUQRCodesService.class).generateForExistingHUs(huIdsToProcess);

		final ProcessInfo.ProcessInfoBuilder builder = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(request.getAdProcessId())
				.setWindowNo(request.getWindowNo())
				.setTableName(I_M_HU.Table_Name)
				.setReportLanguage(request.getAdLanguage())
				.addParameter(ReportConstants.REPORT_PARAM_BARCODE_URL, DocumentReportService.getBarcodeServlet(Env.getClientId(ctx), Env.getOrgId(ctx)))
				.addParameter(IMassPrintingService.PARAM_PrintCopies, request.getCopies().toInt());

		if (huIdsToProcess.size() == 1)
		{
			final HuId huId = huIdsToProcess.iterator().next();

			// IMPORTANT: In case there is only one HU provided, also set M_HU_ID parameter.
			// Most of the HU label reports are relying on this.
			builder.addParameter("M_HU_ID", huId.getRepoId());

			// Also set TableName/Record_ID
			// this will land to AD_Archive.AD_Table_ID/Record_ID.
			builder.setRecord(I_M_HU.Table_Name, huId.getRepoId());
		}

		if (request.getAdJasperProcessId() != null)
		{
			builder.addParameter(PROCESS_PARAM_AD_Process_ID, request.getAdJasperProcessId().getRepoId());
		}

		final ProcessExecutor processExecutor = builder.setPrintPreview(request.getPrintPreview())
				//
				// Execute report in a new transaction
				.buildAndPrepareExecution()
				.onErrorThrowException(request.isOnErrorThrowException())
				.callBefore(processInfo -> DB.createT_Selection(processInfo.getPinstanceId(), HuId.toRepoIds(huIdsToProcess), ITrx.TRXNAME_ThreadInherited))
				.executeSync();

		return HUReportExecutorResult.builder()
				.processInfo(processExecutor.getProcessInfo())
				.processExecutionResult(processExecutor.getResult())
				.build();
	}

	@lombok.Value
	private static class HUReportRequest
	{
		@NonNull Properties ctx;
		@NonNull AdProcessId adProcessId;
		int windowNo;
		@NonNull PrintCopies copies;
		AdProcessId adJasperProcessId;
		@NonNull OptionalBoolean printPreview;
		@Nullable String adLanguage;
		boolean onErrorThrowException;
		@NonNull ImmutableSet<HuId> huIdsToProcess;

		@lombok.Builder
		private HUReportRequest(
				@NonNull final Properties ctx,
				@NonNull final AdProcessId adProcessId,
				final int windowNo,
				@NonNull final PrintCopies copies,
				@Nullable final AdProcessId adJasperProcessId,
				@Nullable final Boolean printPreview,
				@Nullable final String adLanguage,
				final boolean onErrorThrowException,
				@NonNull final ImmutableSet<HuId> huIdsToProcess)
		{
			Check.assume(copies.toInt() > 0, "copies > 0");
			Check.assumeNotEmpty(huIdsToProcess, "huIdsToProcess is not empty");

			this.ctx = ctx;
			this.adProcessId = adProcessId;
			this.windowNo = windowNo > 0 ? windowNo : Env.WINDOW_None;
			this.copies = copies;
			this.adJasperProcessId = adJasperProcessId;
			this.printPreview = OptionalBoolean.ofNullableBoolean(printPreview);
			this.adLanguage = adLanguage;
			this.onErrorThrowException = onErrorThrowException;
			this.huIdsToProcess = huIdsToProcess;
		}

	}
}
