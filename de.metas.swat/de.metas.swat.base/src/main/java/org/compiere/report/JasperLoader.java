package org.compiere.report;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.misc.service.IClientOrgPA;
import org.adempiere.misc.service.IPOService;
import org.adempiere.util.CustomColNames;
import org.adempiere.util.Services;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.util.CLogger;
import org.compiere.util.Util;

public final class JasperLoader extends ClassLoader {

	private static final CLogger logger = CLogger
			.getCLogger(JasperLoader.class);

	public static final String PLACEHOLDER = "@PREFIX@";

	@Override
	public URL getResource(String name) {

		final String url = urlString(name);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			logger.warning("MalformedURLExpection. url " + url);
		}
		return null;
	}

	@Override
	public InputStream getResourceAsStream(final String name) {

		final String url = urlString(name);

		try {

			final FileSystemManager fsManager = VFS.getManager();

			// TODO add provider for ADempiere attachments

			final FileObject jasperFile = fsManager.resolveFile(url);

			final FileContent jasperData = jasperFile.getContent();

			final InputStream is = jasperData.getInputStream();

			// copy the stream data to a local stream
			final ByteArrayOutputStream out = new ByteArrayOutputStream();

			byte[] buffer = new byte[512];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			is.close();
			jasperFile.close();
			
			final InputStream result = new ByteArrayInputStream(out
					.toByteArray());
			
			return result;
			
		} catch (FileSystemException e1) {
			return null;
		} catch (IOException e) {
			throw new AdempiereException("Unable to load jasper", e);
		}
	}

	private String prefix;

	private boolean alwaysPrependPrefix = false;

	public JasperLoader(final int orgId, final String trxName) {

		final IClientOrgPA orgPA = Services.get(IClientOrgPA.class);

		final I_AD_OrgInfo orgInfo = orgPA.retrieveOrgInfo(orgId, trxName);
		prefix = (String) Services.get(IPOService.class).getValue(orgInfo,
				CustomColNames.AD_OrgInfo_REPORT_PREFIX);
	}

	String urlString(final String string) {

		if (Util.isEmpty(prefix)) {
			return string;
		}

		final StringBuffer sb = new StringBuffer();

		if (string.startsWith(PLACEHOLDER)) {

			if (string.startsWith(PLACEHOLDER + "/")) {

				sb.append(string.replace(PLACEHOLDER, prefix));

			} else {

				if (prefix.endsWith("/")) {
					sb.append(string.replace(PLACEHOLDER, prefix));
				} else {
					sb.append(string.replace(PLACEHOLDER, prefix + "/"));
				}
			}
			alwaysPrependPrefix = true;
			return sb.toString();

		} else {

			final Pattern pattern = Pattern.compile("@([\\S]+)@([\\S]+)");
			final Matcher matcher = pattern.matcher(string);

			if (matcher.find()) {

				prefix = matcher.group(1);
				sb.append(prefix);

				final String report = matcher.group(2);

				if (!prefix.endsWith("/") && !report.startsWith("/")) {
					sb.append("/");
				}
				sb.append(report);
				alwaysPrependPrefix = true;

				return sb.toString();
			}
		}

		if (alwaysPrependPrefix) {
			sb.append(prefix);

			if (!prefix.endsWith("/") && !string.startsWith("/")) {
				sb.append("/");
			}
		}
		sb.append(string);
		return sb.toString();
	}
}
