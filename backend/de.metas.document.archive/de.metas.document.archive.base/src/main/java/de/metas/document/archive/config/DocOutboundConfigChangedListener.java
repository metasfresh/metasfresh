package de.metas.document.archive.config;

@FunctionalInterface
public interface DocOutboundConfigChangedListener
{
	void onConfigChanged();
}
