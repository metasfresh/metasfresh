package de.metas.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleIdTest
{
	@Test
	void isSystem()
	{
		assertThat(RoleId.SYSTEM.isSystem()).isTrue();
		assertThat(RoleId.ofRepoId(123).isSystem()).isFalse();
	}

	@Test
	void isRegular()
	{
		assertThat(RoleId.SYSTEM.isRegular()).isFalse();
		assertThat(RoleId.ofRepoId(123).isRegular()).isTrue();
	}
}