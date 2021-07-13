package de.metas.handlingunits.shipping.process;

import com.google.common.collect.ImmutableList;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOut;
import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.server.OutputType;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.lang.impl.TableRecordReference;

import static de.metas.report.ExecuteReportStrategyUtil.PdfDataProvider;
import static de.metas.report.ExecuteReportStrategyUtil.concatenatePDFs;

public class PrintAllShipmentsDocumentsStrategy implements ExecuteReportStrategy
{
	@Override
	public ExecuteReportResult executeReport(final ProcessInfo processInfo, final OutputType outputType)
	{
		final ShipperTransportationId shipperTransportationId = ShipperTransportationId.ofRepoId(processInfo.getRecord_ID());

		final ImmutableList<PdfDataProvider> pdfDataToConcat = retrievePdfDataToConcat(shipperTransportationId);
		final byte[] data = concatenatePDFs(pdfDataToConcat);

		return ExecuteReportResult.of(outputType, data);
	}

	@NonNull
	private ImmutableList<PdfDataProvider> retrievePdfDataToConcat(final ShipperTransportationId shipperTransportationId)
	{

		final ImmutableList.Builder<PdfDataProvider> result = ImmutableList.builder();

		final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final ImmutableList<InOutId> inOutIds = inOutDAO.retrieveByShipperTransportation(shipperTransportationId);
		for (final InOutId io : inOutIds)
		{
			final TableRecordReference inoutRef = TableRecordReference.of(I_M_InOut.Table_Name, io);
			final PdfDataProvider pdfData = archiveBL.getLastArchiveBinaryData(inoutRef)
					.map(PdfDataProvider::forData)
					.orElse(null);
			if(pdfData != null)
			{
				result.add(pdfData);
			}
		}
		return result.build();
	}

	//  // TODO maybe this needs to be deleted. It all depends on what mark sais. for now, i'll leave it here.
	// private ImmutableList<PdfDataProvider> retrievePdfDataToConcat(@NonNull final ShipperTransportationId shipperTransportationId)
	// {
	// 	final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	// final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	//
	// final I_M_Shipper shipper = shipperDAO.getByShipperTransportationId(shipperTransportationId);
	//
	// if (X_M_Shipper.SHIPPERGATEWAY_DPD.equals(shipper.getShipperGateway()))
	// {
	// 	return retrieveArchivesFromDpd(shipperTransportationId);
	// }
	// else if (X_M_Shipper.SHIPPERGATEWAY_DHL.equals(shipper.getShipperGateway()))
	// {
	// 	// return retrieveArchivesFromDhl();
	// }
	// return ImmutableList.of();
	// }
	//
	// private ImmutableList<PdfDataProvider> retrieveArchivesFromDpd(final ShipperTransportationId shipperTransportationId)
	// {
	// 	Services.get(IQueryBL.class)
	// 			.createQueryBuilder(I_DPD_StoreOrder.class???)
	//
	// 	// example code
	// 	final ImmutableList.Builder<PdfDataProvider> result = ImmutableList.builder();
	// 	final List<I_M_ShippingPackage> shippingPackages = shipperTransportationDAO.retrieveShippingPackages(shipperTransportationId);
	// 	for (final I_M_ShippingPackage shippingPackage : shippingPackages)
	// 	{
	//
	// 		shippingPackage.getde
	// 		final List<I_AD_Archive> archives = archiveDAO.retrieveLastArchives(Env.getCtx(), TableRecordReference.of(I_M_ShippingPackage.Table_Name, ShippingPackageId.ofRepoId(shippingPackage.getM_ShippingPackage_ID())), 10000);
	// 		result.addAll(archives.stream().map(it -> PdfDataProvider.forData(it.getBinaryData())).collect(GuavaCollectors.toImmutableList()));
	// 	}
	//
	// 	return result.build();
	// }
}
