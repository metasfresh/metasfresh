package de.metas.migration.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github.zafarkhaja.semver.Version;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class SemverTests
{
	@Test
	public void testSimpleaCase()
	{
		assertThat(Version.valueOf("5.20.4")).isLessThan(Version.valueOf("5.20.5"));
	}

	@Test
	public void testWhichIsWhich()
	{
		final Version version = Version.valueOf("5.20.4-1+a");

		assertThat(version.getMajorVersion()).isEqualByComparingTo(5);
		assertThat(version.getMinorVersion()).isEqualByComparingTo(20);
		assertThat(version.getPatchVersion()).isEqualByComparingTo(4);

		assertThat(version.getPreReleaseVersion()).isEqualTo("1");
		assertThat(version.getBuildMetadata()).isEqualTo("a");
	}

	@Test
	public void testPreReleaseVersionMatters()
	{
		assertThat(Version.valueOf("5.20.4-1")).isLessThan(Version.valueOf("5.20.4-2"));
		assertThat(Version.valueOf("5.20.4")).isGreaterThan(Version.valueOf("5.20.3"));
		assertThat(Version.valueOf("5.20.4-1+buid")).isLessThan(Version.valueOf("5.20.4-2+build"));
	}

	@Test
	public void testVersionToString()
	{
		String versionString = "5.20.4-1+a";
		final Version version = Version.valueOf(versionString);

		assertThat(version.toString()).isEqualTo(versionString);
	}

	@Test
	public void testEmptyStringsNotNull()
	{
		assertThat(Version.valueOf("5.20.4+buildMetadata").getPreReleaseVersion()).isNotNull();
		assertThat(Version.valueOf("5.20.4-preRelease").getBuildMetadata()).isNotNull();

		assertThat(Version.valueOf("5.20.4").getPreReleaseVersion()).isNotNull();
		assertThat(Version.valueOf("5.20.4").getBuildMetadata()).isNotNull();
	}

	@Test
	public void testBuildMetadataDoesntMatter()
	{
		assertThat(Version.valueOf("5.20.4-1+a")).isEqualByComparingTo(Version.valueOf("5.20.4-1+b"));
		assertThat(Version.valueOf("5.20.4-2+a")).isGreaterThan(Version.valueOf("5.20.4-1+b"));
		assertThat(Version.valueOf("5.20.4-1+a")).isLessThan(Version.valueOf("5.20.4-2+b"));
	}

	@Test
	public void testAddMetaInfo()
	{
		final Version versionWithoutMetadata = Version.valueOf("1.1.1");
		final String metadata = "metadata";

		final Version versionWithMetadata = new Version.Builder(versionWithoutMetadata.toString())
				.setBuildMetadata(versionWithoutMetadata.getBuildMetadata() + "-" + metadata)
				.build();

		assertThat(versionWithMetadata.toString()).isEqualTo("1.1.1" + "+" + metadata);
	}
}
