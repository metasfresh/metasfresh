package de.metas.vertical.pharma.securpharm.service;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Ignore;

import de.metas.attachments.AttachmentEntryService;
import de.metas.handlingunits.HuId;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmConfigRespository;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmResultRepository;

/*
 * #%L
 * metasfresh-pharma.securpharm
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

@Ignore
public class SecurPharmServiceManualTest
{
	public static void main(final String[] args)
	{
		new SecurPharmServiceManualTest().run();
	}

	private SecurPharmService service;

	private void run()
	{
		AdempiereTestHelper.get().init();
		service = createSecurPharmService();

		final DataMatrixCode datamatrix = DataMatrixCode.ofBase64Encoded("Wyk+HjA2HTlOMTExMjM0NTY4NDA4HTFUNDdVNTIxNx1EMjIwODAwHVMxODAxOTczMTUzNzYxMh4E");
		System.out.println("Sending code: " + datamatrix);

		final HuId huId = HuId.ofRepoId(1);
		final SecurPharmProductDataResult result = service.getAndSaveProductData(datamatrix, huId);
		System.out.println("result: " + result);
	}

	private static SecurPharmService createSecurPharmService()
	{
		final SecurPharmConfigRespository configRespository = PlainSecurPharmConfigRespository.ofDefaultSandboxProperties();
		final SecurPharmClientFactory clientFactory = new SecurPharmClientFactory(configRespository);

		final SecurPharmResultRepository resultRepository = new SecurPharmResultRepository();
		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
		final SecurPharmResultService resultService = new SecurPharmResultService(resultRepository, attachmentEntryService);

		return new SecurPharmService(clientFactory, resultService, configRespository);
	}
}
