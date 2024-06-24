/*
 * #%L
 * de.metas.dunning
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

package de.metas.document.archive.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.dunning.DunningTestBase;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocTypePrintOptionsRepository;
import de.metas.report.DocumentPrintOptionDescriptorsRepository;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.PrintFormatRepository;
import de.metas.user.UserRepository;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import static de.metas.document.archive.spi.impl.MockedDocumentReportService.MOCKED_REPORT_FILENAME;

/**
 * Integration test between {@link DefaultModelArchiver} and dunning project.
 */
public class Dunning_DefaultModelArchiverTest extends DunningTestBase
{
	private DefaultModelArchiverTestHelper helper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new DefaultModelArchiverTestHelper();

		Env.setClientId(Env.getCtx(), helper.createClient());
	}

	private MockedDocumentReportService createMockedDocumentReportService()
	{
		final DocumentReportAdvisorUtil util = new DocumentReportAdvisorUtil(
				new BPartnerBL(new UserRepository()),
				new PrintFormatRepository(),
				new DefaultPrintFormatsRepository(), new BPartnerPrintFormatRepository());

		return new MockedDocumentReportService(
				ImmutableList.of(),
				new DocumentPrintOptionDescriptorsRepository(),
				new DocTypePrintOptionsRepository(),
				util);

	}

	/**
	 * Validate requirement: http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing
	 */
	@Test
	public void archiveDunningDoc()
	{
		final BPartnerId bpartnerId = helper.createBPartner("ro_RO");
		final AdProcessId printProcessId = helper.process().build();
		helper.docOutboundConfig()
				.tableName(I_C_DunningDoc.Table_Name)
				.printFormatId(helper.printFormat()
						.printProcessId(printProcessId)
						.build())
				.build();

		final I_C_DunningDoc dunningDoc = InterfaceWrapperHelper.create(Env.getCtx(), I_C_DunningDoc.class, ITrx.TRXNAME_None);
		dunningDoc.setDocumentNo("ExpectedDocumentNo");
		dunningDoc.setC_BPartner_ID(bpartnerId.getRepoId());
		InterfaceWrapperHelper.save(dunningDoc);

		final DefaultModelArchiver archiver = DefaultModelArchiver.of(dunningDoc);
		final MockedDocumentReportService mockedDocumentReportService = createMockedDocumentReportService();
		mockedDocumentReportService.setPinstanceIdToReturn(PInstanceId.ofRepoId(11223344));
		archiver.setDocumentReportService(mockedDocumentReportService);

		final ArchiveResult archiveResult = archiver.archive();

		final I_AD_Archive archiveRecord = archiveResult.getArchiveRecord();
		Assertions.assertThat(archiveRecord.getDocumentNo()).isEqualTo(dunningDoc.getDocumentNo());
		Assertions.assertThat(archiveRecord.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_DunningDoc.class));
		Assertions.assertThat(archiveRecord.getRecord_ID()).isEqualTo(dunningDoc.getC_DunningDoc_ID());
		Assertions.assertThat(archiveRecord.getC_BPartner_ID()).isEqualTo(dunningDoc.getC_BPartner_ID());
		Assertions.assertThat(archiveRecord.getAD_Language()).isEqualTo("ro_RO");
		Assertions.assertThat(archiveRecord.isReport()).isFalse();
		Assertions.assertThat(archiveRecord.getAD_Process_ID()).isEqualTo(printProcessId.getRepoId());
		Assertions.assertThat(archiveRecord.getAD_PInstance_ID()).isEqualTo(11223344);
		Assertions.assertThat(archiveRecord.getName()).isEqualTo(MOCKED_REPORT_FILENAME);
	}

}
