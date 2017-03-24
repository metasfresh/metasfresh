package de.metas.document.archive.esb.test.integration;

/*
 * #%L
 * de.metas.document.archive.esb.ait
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Properties;
import java.util.Random;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.api.IArchiveStorageFactory.AccessMode;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.archive.spi.impl.DBArchiveStorage;
import org.adempiere.archive.spi.impl.FilesystemArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.test.JMSServer;
import org.apache.camel.spring.SpringCamelContext;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Session;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.metas.document.archive.esb.test.util.ArchiveImportHelper;
import de.metas.document.archive.esb.test.util.MockedAdempiereImportProcessor;
import de.metas.document.archive.spi.impl.RemoteArchiveStorage;
import de.metas.document.archive.spi.impl.RestHttpArchiveEndpoint;

/**
 *
 * @author tsa
 *
 */
public class IntegrationTest
{
	public final String RESTHTTP_ServerURL = "http://localhost:7654"; // changed from 8181 to avoid problems with other jetty-running tests
	private static final String JMS_ServerURL = "tcp://localhost:61234";
	public static final String JMS_TopicName = "de.metas.esb.to.adempiere.nondurable";
	public static final boolean JMS_DurableSubscription = true;

	private Properties ctx;
	private IArchiveStorageFactory storageFactory;
	private I_AD_Client client;

	@Rule
	public TemporaryFolder storageFolder = new TemporaryFolder();

	@BeforeClass
	public static void initStatic() throws Exception
	{
		// set it up only once, to avoid
		// org.apache.camel.RuntimeCamelException: org.apache.cxf.interceptor.Fault: Could not add cxf jetty handler for url http://localhost:7654/ to Jetty server, as the path / is still in use.
		setupCamelRouter();
	}

	@Before
	public void init() throws Exception
	{
		AdempiereTestHelper.get().init();

		setupAdempiereClient();
		setupArchiveRestHttpClient();
		setupArchiveStorage();
		setupAdempiereImportProcessor();
	}

	private static void setupCamelRouter() throws Exception
	{
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF/spring/beans.xml",
				"META-INF/spring/props-locations-test.xml"
				);
		SpringCamelContext.springCamelContext(applicationContext);
	}

	private void setupAdempiereClient()
	{
		ctx = Env.getCtx();

		//
		// Configure client level settings
		client = InterfaceWrapperHelper.create(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
		client.setWindowsArchivePath(storageFolder.getRoot().getAbsolutePath());
		client.setUnixArchivePath(client.getWindowsArchivePath());
		client.setStoreArchiveOnFileSystem(true);
		InterfaceWrapperHelper.save(client);
		Env.setContext(ctx, "#AD_Client_ID", client.getAD_Client_ID());
		Ini.setClient(true);

		//
		// Configure AD_Session (client)
		final I_AD_Session session = InterfaceWrapperHelper.create(ctx, I_AD_Session.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(session);
		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, session.getAD_Session_ID());
	}

	private void setupArchiveRestHttpClient()
	{
		//
		// Configure REST HTTP endpoint
		Services.get(ISysConfigBL.class).setValue(RestHttpArchiveEndpoint.SYSCONFIG_ServerUrl,
				RESTHTTP_ServerURL,
				0 // AD_Org_ID
				);

		// Configure endpoint: REST HTTP
		Services.get(ISysConfigBL.class).setValue(RemoteArchiveStorage.SYSCONFIG_ArchiveEndpoint,
				RestHttpArchiveEndpoint.class.getName(),
				0 // AD_Org_ID
				);
	}

	private void setupArchiveStorage()
	{
		storageFactory = Services.get(IArchiveStorageFactory.class);
		storageFactory.registerArchiveStorage(
				IArchiveStorageFactory.STORAGETYPE_Database,
				AccessMode.ALL,
				DBArchiveStorage.class);
		storageFactory.registerArchiveStorage(
				IArchiveStorageFactory.STORAGETYPE_Filesystem,
				AccessMode.SERVER,
				FilesystemArchiveStorage.class);
		storageFactory.registerArchiveStorage(
				IArchiveStorageFactory.STORAGETYPE_Filesystem,
				AccessMode.CLIENT,
				RemoteArchiveStorage.class);
	}

	private void setupAdempiereImportProcessor() throws Exception
	{
		// IMPORTANT: instead of using default IImportHelper use our mocked version
		Services.get(IIMPProcessorBL.class).setImportHelperClass(ArchiveImportHelper.class);

		// Make sure ActiveMQ broker is created and started
		JMSServer.getInstance().start(JMS_ServerURL);

		// Create and start replication processor
		MockedAdempiereImportProcessor
				.getInstance()
				.getCreateReplicationProcessor(JMS_ServerURL, JMS_TopicName, JMS_DurableSubscription)
				.start();
	}

	@After
	public void cleanup()
	{
		MockedAdempiereImportProcessor.getInstance().clearAllReplicationProcessors();
	}

	private final Random random = new Random(System.currentTimeMillis());

	private byte[] createTestBinaryData()
	{
		// return createTestBinaryData(1024 * 100);
		return createTestBinaryData(10);
	}

	private byte[] createTestBinaryData(final int size)
	{
		final byte[] data = new byte[size];
		random.nextBytes(data);
		return data;
	}

	@Test
	public void test_getBinaryData() throws Exception
	{
		Ini.setClient(true);

		final byte[] data = createTestBinaryData();

		final org.compiere.model.I_AD_Archive archive;

		// Create archive on server
		{
			Ini.setClient(false);
			final IArchiveStorage storage = storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.STORAGETYPE_Filesystem);
			archive = storage.newArchive(ctx, ITrx.TRXNAME_None);
			storage.setBinaryData(archive, data);
			InterfaceWrapperHelper.save(archive);
		}

		// Retrieve archive on client
		{
			Ini.setClient(true);
			final IArchiveStorage storage = storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.STORAGETYPE_Filesystem);
			final byte[] dataActual = storage.getBinaryData(archive);
			Assert.assertArrayEquals("Invalid binary data", data, dataActual);
		}
	}

	@Test
	public void test_getBinaryData_MissingArchive() throws Exception
	{
		Ini.setClient(true);

		final byte[] data = createTestBinaryData();

		final org.compiere.model.I_AD_Archive archive;

		// Create archive on server
		{
			Ini.setClient(false);
			final IArchiveStorage storage = storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.STORAGETYPE_Filesystem);
			archive = storage.newArchive(ctx, ITrx.TRXNAME_None);
			storage.setBinaryData(archive, data);
			InterfaceWrapperHelper.save(archive);
		}

		InterfaceWrapperHelper.delete(archive);
		boolean fail = true;
		// 05481: We need to make sure that the proper error message is thrown when we have a missing archive.
		{
			try
			{
				Ini.setClient(true);
				final IArchiveStorage storage = storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.STORAGETYPE_Filesystem);
				storage.getBinaryData(archive);
			}
			catch (Exception e)
			{
				final String message = e.getMessage();
				Assert.assertTrue("Wrong error code. Expected 204", (message != null && message.contains("204")));
				fail = false;
			}
		}

		if (fail)
		{
			Assert.assertTrue("Exception was not thrown", 1 == 0);
		}
	}

	@Test
	public void test_setBinaryData()
	{
		Ini.setClient(true);

		final byte[] data = createTestBinaryData();

		final IArchiveStorage storage = storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.STORAGETYPE_Filesystem, AccessMode.CLIENT);
		final org.compiere.model.I_AD_Archive archive = storage.newArchive(ctx, ITrx.TRXNAME_None);
		storage.setBinaryData(archive, data);
		InterfaceWrapperHelper.save(archive);

		final byte[] dataActual = storage.getBinaryData(archive);
		Assert.assertArrayEquals("Invalid binary data", data, dataActual);
	}
}
