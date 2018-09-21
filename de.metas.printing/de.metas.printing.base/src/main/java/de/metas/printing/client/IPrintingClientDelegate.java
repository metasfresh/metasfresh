package de.metas.printing.client;

import de.metas.util.ISingletonService;

public interface IPrintingClientDelegate extends ISingletonService
{
	boolean isStarted();

	void start();

	void stop();

	void destroy();

	void restart();
}
