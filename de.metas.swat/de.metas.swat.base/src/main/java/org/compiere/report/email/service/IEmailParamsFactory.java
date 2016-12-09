package org.compiere.report.email.service;

import de.metas.process.ProcessInfo;

public interface IEmailParamsFactory {

	IEmailParameters getInstanceForPI(final ProcessInfo pi);

}
