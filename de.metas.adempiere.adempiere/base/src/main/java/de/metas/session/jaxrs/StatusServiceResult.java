package de.metas.session.jaxrs;

import lombok.Data;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * A POJO that is filled by the metasfresh server and send to the client.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Data
public class StatusServiceResult
{
	public static final String RABBITMQ_USE_APPSERVER_HOSTNAME = "<USE-APPSERVER-HOSTNAME>";

	private String dateVersion;
	private String mainVersion;
	private String dbType;
	private String dbHost;
	private int dbPort;
	private String dbName;
	private String connectionURL;
	private String dbUid;
	private String dbPwd;
	private int versionCount;
	private int dataBaseCount;
	private String status;

	private String rabbitmqHost;
	private String rabbitmqPort;
	private String rabbitmqUsername;
	private String rabbitmqPassword;
}
