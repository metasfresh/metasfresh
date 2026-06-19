package de.metas.camel.externalsystems.scriptedadapter.convertmsg;

import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import lombok.Builder;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaScriptTestHelper
{
	@NonNull private final String scriptIdentifier;
	@NonNull private final JavaScriptRepo javaScriptRepo;
	@NonNull private final JavaScriptExecutorService javaScriptExecutorService;

	@Builder
	private JavaScriptTestHelper(@NonNull final String scriptIdentifier)
	{
		this.scriptIdentifier = scriptIdentifier;

		final Path projectRoot = Paths.get(System.getProperty("user.dir"));
		final Path scriptDir = projectRoot.resolve("javascript_dynamics365");

		javaScriptRepo = new JavaScriptRepo(scriptDir.toString());
		javaScriptExecutorService = new JavaScriptExecutorService();
	}

	@NonNull
	private String getResourceAsString(final String jsonValidRequest) throws IOException
	{
		final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jsonValidRequest);
		assertThat(inputStream).isNotNull();
		return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
	}

	private String executeScript(String requestResourceName) throws IOException
	{
		return javaScriptExecutorService.executeScript(
				scriptIdentifier,
				javaScriptRepo.get(scriptIdentifier),
				getResourceAsString(requestResourceName)
		);
	}

	public void test(final String givenRequestResourceName, final String expectedResponseResourceName) throws IOException
	{
		final String javaScriptResult = executeScript(givenRequestResourceName);
		assertThat(javaScriptResult).isEqualTo(getResourceAsString(expectedResponseResourceName));
	}
}
