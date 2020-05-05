/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.config;

import javax.annotation.Nullable;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude = "keystorePassword")
public class SecurPharmConfig
{
	@NonNull
	String authBaseUrl;

	@NonNull
	String pharmaAPIBaseUrl;

	@NonNull
	String keystorePassword;

	@NonNull
	String certificatePath;

	@NonNull
	String applicationUUID;

	@NonNull
	UserId supportUserId;

	@Nullable
	SecurPharmConfigId id;
}
