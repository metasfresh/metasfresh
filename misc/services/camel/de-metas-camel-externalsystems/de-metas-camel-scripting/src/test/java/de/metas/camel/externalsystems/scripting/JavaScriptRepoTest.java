/*
 * #%L
 * de-metas-camel-scripting
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scripting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JavaScriptRepoTest
{
	private JavaScriptRepo javaScriptRepo;

	@TempDir
	Path tempDir;

	@BeforeEach
	void setUp()
	{
		javaScriptRepo = new JavaScriptRepo(tempDir.toString());
	}

	@Test
	void get_returnsScriptContent_whenFileExists() throws IOException
	{
		// given
		final String identifier = "myTestScript";
		final String scriptContent = "console.log('Hello, Test!');";
		final Path scriptPath = tempDir.resolve(identifier + ".js");
		Files.writeString(scriptPath, scriptContent);

		// when
		final String retrievedContent = javaScriptRepo.get(identifier);

		// then
		assertThat(retrievedContent)
				.as("Retrieved content should match the original script content")
				.isEqualTo(scriptContent);
	}

	@Test
	void get_throwsRuntimeException_whenFileDoesNotExist()
	{
		final String identifier = "nonExistentScript";

		// when
		assertThatThrownBy(() -> javaScriptRepo.get(identifier))
				.isInstanceOf(JavaScriptRepoException.class)
				.hasMessageContaining("Unable to load script with identifier " + identifier)
				.hasCauseInstanceOf(IOException.class);
	}

	@Test
	void save_createsNewFile_withGivenContent() throws IOException
	{
		// given
		final String identifier = "newScript";
		final String scriptContent = "function myFunction() { return 123; }";

		// when
		javaScriptRepo.save(identifier, scriptContent);

		// then
		final Path scriptPath = tempDir.resolve(identifier + ".js");
		assertThat(Files.exists(scriptPath))
				.as("Script file should be created")
				.isTrue();
		assertThat(Files.readString(scriptPath))
				.as("Content of the created script file should match")
				.isEqualTo(scriptContent);
	}

	@Test
	void save_overwritesExistingFile_withNewContent() throws IOException
	{
		// given
		final String identifier = "existingScript";
		final String initialContent = "old content";
		final String newContent = "new and improved content";
		final Path scriptPath = tempDir.resolve(identifier + ".js");
		
		Files.writeString(scriptPath, initialContent);

		// when
		javaScriptRepo.save(identifier, newContent);

		// then
		assertThat(Files.exists(scriptPath))
				.as("Script file should still exist after overwrite")
				.isTrue();
		assertThat(Files.readString(scriptPath))
				.as("Content of the script file should be updated")
				.isEqualTo(newContent);
	}
}