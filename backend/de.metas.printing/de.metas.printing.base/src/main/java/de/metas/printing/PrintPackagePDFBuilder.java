/**
 *
 */
package de.metas.printing;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.client.engine.PrintablePDF;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PrintPackagePDFBuilder
{
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	private I_C_Print_Package printPackage;

	public PrintPackagePDFBuilder setPrintPackage(@NonNull final I_C_Print_Package printPackage)
	{
		this.printPackage = printPackage;
		return this;
	}

	private PrintablePDF createPrintable()
	{
		final byte[] data = printingDAO.retrievePrintPackageData(printPackage).getPrintData();
		if (data == null)
		{
			return null;
		}


		final InputStream in = new ByteArrayInputStream(data);
		final PrintablePDF printable = new PrintablePDF(in);
		return printable;
	}

	public byte[] printToBytes() throws Exception
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		print(out);
		return out.toByteArray();
	}

	public void print(@NonNull final OutputStream bos) throws Exception
	{
		final I_C_Print_Job_Instructions print_Job_Instructions = printPackage.getC_Print_Job_Instructions();
		final Document document = new Document();

		final PdfCopy copy = new PdfCopy(document, bos);

		document.open();

		final PrintablePDF printable = createPrintable();
		if (printable == null)
		{
			return;
		}
		try
		{
			for (final I_C_Print_PackageInfo printPackageInfo : printingDAO.retrievePrintPackageInfos(printPackage))
			{
				final byte[] pdf = print(printPackageInfo, printable);

				final PdfReader reader = new PdfReader(pdf);

				for (int page = 0; page < reader.getNumberOfPages(); )
				{
					copy.addPage(copy.getImportedPage(reader, ++page));
				}
				copy.freeReader(reader);
				reader.close();
			}
			document.close();

			print_Job_Instructions.setErrorMsg(null);
			print_Job_Instructions.setStatus(X_C_Print_Job_Instructions.STATUS_Done);
			InterfaceWrapperHelper.save(print_Job_Instructions);
		}
		catch (final Exception e)
		{
			print_Job_Instructions.setErrorMsg(e.getLocalizedMessage());
			print_Job_Instructions.setStatus(X_C_Print_Job_Instructions.STATUS_Error);
			InterfaceWrapperHelper.save(print_Job_Instructions);

			throw new AdempiereException(e.getLocalizedMessage());
		}
	}

	private byte[] print(@NonNull final I_C_Print_PackageInfo printPackageInfo, @NonNull final PrintablePDF printable)
	{
		printable.setCalX(printPackageInfo.getCalX());
		printable.setCalY(printPackageInfo.getCalY());

		final PrintablePDF clone = printable;
		return PdfPrinter.print(printable, clone);
	}
}
