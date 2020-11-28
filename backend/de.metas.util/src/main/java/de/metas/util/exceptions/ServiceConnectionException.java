package de.metas.util.exceptions;

import javax.annotation.Nullable;

import lombok.Getter;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * This exception is (currently) handled in our async framework. Throw it to indicate that an external service can't be connected to with.
 * If {@link #getRetryAdvisedIMillis()} is greater than zero, the async framework will schedule a retry.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class ServiceConnectionException extends RuntimeException
{
	private static final long serialVersionUID = -353512891270574392L;

	/** A value less or equals 0 means "a retry is not advised". */
	@Getter
	private final int retryAdvisedInMillis;

	@Getter
	private final String serviceURL;

	public ServiceConnectionException(
			@Nullable String serviceURL,
			final int retryAdvisedInMillis,
			@Nullable final Throwable cause)
	{
		super(cause);
		this.serviceURL = serviceURL;
		this.retryAdvisedInMillis = retryAdvisedInMillis;
	}
}
