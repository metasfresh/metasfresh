package de.metas.payment.sepa.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Every {@code de.metas.payment.sepa.SEPA_Export_*} message key used by this module must be created by one of the module's migration scripts.
 * A key without an {@code AD_Message} is shown to the user as the raw key, e.g. the outer message of an invalid reference in the pain.001.001.09.ch.03 export.
 */
class SepaExportMessagesMigrationTest
{
	private static final Path MODULE_DIR = Paths.get("").toAbsolutePath();
	private static final Pattern MESSAGE_KEY = Pattern.compile("AdMessageKey\\.of\\(\"(de\\.metas\\.payment\\.sepa\\.SEPA_Export_[A-Za-z0-9_]+)\"\\)");

	@Test
	void everySepaExportMessageKeyHasAnAdMessageMigration() throws IOException
	{
		final String allMigrations = readAll(MODULE_DIR.resolve("src/main/sql"), ".sql");
		final String allJava = readAll(MODULE_DIR.resolve("src/main/java"), ".java");

		final Set<String> usedKeys = new TreeSet<>();
		final Matcher matcher = MESSAGE_KEY.matcher(allJava);
		while (matcher.find())
		{
			usedKeys.add(matcher.group(1));
		}
		assertThat(usedKeys).as("message keys found in the module sources").isNotEmpty();

		final Set<String> keysWithoutMigration = new TreeSet<>();
		for (final String key : usedKeys)
		{
			if (!allMigrations.contains("'" + key + "'"))
			{
				keysWithoutMigration.add(key);
			}
		}

		assertThat(keysWithoutMigration).as("message keys without an AD_Message migration").isEmpty();
	}

	private static String readAll(final Path dir, final String suffix) throws IOException
	{
		try (final Stream<Path> files = Files.walk(dir))
		{
			return files.filter(p -> p.getFileName().toString().endsWith(suffix))
					.map(SepaExportMessagesMigrationTest::read)
					.collect(Collectors.joining("\n"));
		}
	}

	private static String read(final Path file)
	{
		try
		{
			return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
		}
		catch (final IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
}
