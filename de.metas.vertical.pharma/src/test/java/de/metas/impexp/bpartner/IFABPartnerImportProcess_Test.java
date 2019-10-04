package de.metas.impexp.bpartner;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;

/*
 * #%L
 * metasfresh-pharma
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		// needed to register the spring context with the Adempiere main class
		StartupListener.class, ShutdownListener.class,

		// needed so that the spring context can discover those components
		DBFunctionsRepository.class,
		ImportTableDescriptorRepository.class
})
public class IFABPartnerImportProcess_Test
{
	private Properties ctx;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();
		IIFABPartnerFactory.createCountry("DE");
	}

	@Test
	public void testIFABPartnerImport()
	{
		final List<I_I_Pharma_BPartner> importRecords = prepareImportRecords();

		final IFABPartnerImportProcess importProcess = new IFABPartnerImportProcess();
		importProcess.setCtx(ctx);

		importRecords.forEach(record -> importProcess.importRecord(new Mutable<>(), record, false /* isInsertOnly */));

		importRecords.forEach(record -> IFABPartnerImportTestHelper.assertIFABPartnerImported(record));


	}

	/**
	 * Build a test case for import<br>
	 * <br>
	 * <code>B00SSATZ	B00ADRNR	B00NAME1	B00NAME2	B00NAME3	B00LAND	B00PLZZU1	B00ORTZU	B00STR	B00HNRV	B00HNRVZ	B00HNRB	B00HNRBZ	B00PLZPF1	B00ORTPF	B00PF1		B00TEL1	B00TEL2	B00FAX1	B00FAX2	B00EMAIL	B00EMAIL2	B00HOMEPAG</code><br>
	 * <code>1			9528		testco1		test1		name1		DE			45721	City1		Street1	 12		23			34		45												1234	2343	3453	3432	te@test.com	te1@test.com	www.test.com	</code><br>
	 * <code>1			20		 	testco2		test2		name2		DE			41564	City2		Street2	 12		23			34		45			41553		POBOxCity	20 22 25	1234	2343	3453	3432	te@test.com	te1@test.com	www.test.com	</code><br>
	 *
	 * @param lines
	 */
	private List<I_I_Pharma_BPartner> prepareImportRecords()
	{
		final List<I_I_Pharma_BPartner> records = new ArrayList<>();

		I_I_Pharma_BPartner ifaPartner = IIFABPartnerFactory.builder()
				.B00SSATZ("1")
				.B00ADRNR("9528")
				.B00NAME1("testco1")
				.B00NAME2("test1")
				.B00NAME3("name1")
				.B00LAND("DE")
				.B00PLZZU1("45721")
				.B00ORTZU("City1")
				.B00STR("Street1")
				.B00HNRV("12")
				.B00HNRVZ("23")
				.B00HNRB("34")
				.B00HNRBZ("45")
				.B00PLZPF1("")
				.B00ORTPF("")
				.B00PF1("")
				.B00TEL1("1234")
				.B00TEL2("2343")
				.B00FAX1("3453")
				.B00FAX2("3432")
				.B00EMAIL("te@test.com")
				.B00EMAIL2("te1@test.com")
				.B00HOMEPAG("www@test.com")
				.build();
		records.add(ifaPartner);


		ifaPartner = IIFABPartnerFactory.builder()
				.B00SSATZ("1")
				.B00ADRNR("20")
				.B00NAME1("testco2")
				.B00NAME2("test2")
				.B00NAME3("name2")
				.B00LAND("DE")
				.B00PLZZU1("41564")
				.B00ORTZU("City2")
				.B00STR("Street2")
				.B00HNRV("12")
				.B00HNRVZ("23")
				.B00HNRB("34")
				.B00HNRBZ("45")
				.B00PLZPF1("41553")
				.B00ORTPF("POBOxCity")
				.B00PF1("20 22 25")
				.B00TEL1("1234")
				.B00TEL2("2343")
				.B00FAX1("3453")
				.B00FAX2("3432")
				.B00EMAIL("te@test.com")
				.B00EMAIL2("te1@test.com")
				.B00HOMEPAG("www@test.com")
				.build();
		records.add(ifaPartner);

		return records;
	}
}
