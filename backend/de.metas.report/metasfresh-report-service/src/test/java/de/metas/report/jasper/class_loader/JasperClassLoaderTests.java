package de.metas.report.jasper.class_loader;

import com.google.common.io.BaseEncoding;
import de.metas.attachments.AttachmentEntryService;
import de.metas.image.AdImageId;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.report.jasper.class_loader.images.ImageUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractByteArrayAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class JasperClassLoaderTests
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private OrgId createOrg()
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		InterfaceWrapperHelper.save(org);

		final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.newInstance(I_AD_OrgInfo.class);
		orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode()); // just to not get NPE
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(orgInfo);

		return OrgId.ofRepoId(org.getAD_Org_ID());
	}

	private AdImageId createImage(@Nullable byte... contentData)
	{
		I_AD_Image record = InterfaceWrapperHelper.newInstance(I_AD_Image.class);
		record.setBinaryData(contentData);
		InterfaceWrapperHelper.save(record);
		return AdImageId.ofRepoId(record.getAD_Image_ID());
	}

	private static AbstractStringAssert<?> assertContentAsBase64(@Nullable final URL url) throws IOException
	{
		assertThat(url).isNotNull();
		final byte[] contentBytes = Util.readBytes(url.openStream());
		return assertThat(BaseEncoding.base64().encode(contentBytes));
	}

	private static AbstractByteArrayAssert<?> assertContentAsBytes(@Nullable final URL url) throws IOException
	{
		assertThat(url).isNotNull();
		final byte[] contentBytes = Util.readBytes(url.openStream());
		return assertThat(contentBytes);
	}

	@Test
	public void isJarInJarURL() throws Exception
	{
		assertThat(JasperClassLoader.isJarInJarURL(new URL("file:///opt/metasfresh/metasfresh-server.jar!/lib/spring-beans-4.2.5.RELEASE.jar"))).isTrue();
		assertThat(JasperClassLoader.isJarInJarURL(new URL("jar:file:/opt/metasfresh/metasfresh_server.jar!/lib/spring-beans-4.2.5.RELEASE.jar!/org/springframework/beans/factory/xml/spring-beans-2.0.xsd"))).isTrue();
		assertThat(JasperClassLoader.isJarInJarURL(new URL("file:///opt/metasfresh/reports/de/metas/docs/direct_costing/report.jasper"))).isFalse();
	}

	@Nested
	class AD_Image
	{
		private JasperClassLoader jasperClassLoader;

		@BeforeEach
		void beforeEach()
		{
			final OrgId orgId = createOrg();
			jasperClassLoader = JasperClassLoader.builder()
					.attachmentEntryService(AttachmentEntryService.createInstanceForUnitTesting())
					.parent(Thread.currentThread().getContextClassLoader())
					.adOrgId(orgId)
					.build();

		}

		@Test
		public void existingImage() throws IOException
		{
			final AdImageId adImageId1 = createImage(new byte[] { 1, 2, 3, 4, 1 });
			final AdImageId adImageId2 = createImage(new byte[] { 1, 2, 3, 4, 2 });

			assertContentAsBytes(jasperClassLoader.getResource("/bla/bla2/AD_Image-" + adImageId1.getRepoId() + ".png"))
					.isEqualTo(new byte[] { 1, 2, 3, 4, 1 });

			assertContentAsBytes(jasperClassLoader.getResource("/bla/bla2/AD_Image-" + adImageId2.getRepoId() + ".png"))
					.isEqualTo(new byte[] { 1, 2, 3, 4, 2 });
		}

		@Test
		public void notParsable() throws IOException
		{
			final URL url = jasperClassLoader.getResource("/bla/bla2/AD_Image-NotANumber.png");
			assertContentAsBase64(url).isEqualTo(ImageUtils.emptyPNGBase64Encoded);
		}

		@Test
		public void missingId() throws IOException
		{
			final URL url = jasperClassLoader.getResource("/bla/bla2/AD_Image-1234.png");
			assertContentAsBase64(url).isEqualTo(ImageUtils.emptyPNGBase64Encoded);
		}

		@Test
		public void nullData() throws IOException
		{
			final AdImageId adImageId = createImage((byte[])null);
			final URL url = jasperClassLoader.getResource("/bla/bla2/AD_Image-" + adImageId.getRepoId() + ".png");
			assertContentAsBase64(url).isEqualTo(ImageUtils.emptyPNGBase64Encoded);
		}

		@Test
		public void emptyData() throws IOException
		{
			final AdImageId adImageId = createImage();
			final URL url = jasperClassLoader.getResource("/bla/bla2/AD_Image-" + adImageId.getRepoId() + ".png");
			assertContentAsBase64(url).isEqualTo(ImageUtils.emptyPNGBase64Encoded);
		}
	}
}
