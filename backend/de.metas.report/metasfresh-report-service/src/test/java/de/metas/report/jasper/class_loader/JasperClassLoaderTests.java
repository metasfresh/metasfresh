package de.metas.report.jasper.class_loader;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class JasperClassLoaderTests
{
	@Test
	public void isJarInJarURL() throws Exception
	{
		assertThat(JasperClassLoader.isJarInJarURL(new URL("file:///opt/metasfresh/metasfresh-server.jar!/lib/spring-beans-4.2.5.RELEASE.jar"))).isTrue();
		assertThat(JasperClassLoader.isJarInJarURL(new URL("jar:file:/opt/metasfresh/metasfresh_server.jar!/lib/spring-beans-4.2.5.RELEASE.jar!/org/springframework/beans/factory/xml/spring-beans-2.0.xsd"))).isTrue();
		assertThat(JasperClassLoader.isJarInJarURL(new URL("file:///opt/metasfresh/reports/de/metas/docs/direct_costing/report.jasper"))).isFalse();
	}
}
